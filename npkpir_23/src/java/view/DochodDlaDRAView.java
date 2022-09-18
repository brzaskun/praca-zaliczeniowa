/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.KsiegaBean;
import comparator.Podatnikcomparator;
import comparator.WierszDRAcomparator;
import dao.DokDAO;
import dao.DraSumyDAO;
import dao.PitDAO;
import dao.PodatnikDAO;
import dao.PodatnikOpodatkowanieDAO;
import dao.PodatnikUdzialyDAO;
import dao.PodmiotDAO;
import dao.RyczDAO;
import dao.SMTPSettingsDAO;
import dao.WierszDRADAO;
import dao.ZusmailDAO;
import daoplatnik.UbezpZusrcaDAO;
import daoplatnik.ZusdraDAO;
import daoplatnik.ZusrcaDAO;
import daosuperplace.FirmaFacade;
import daosuperplace.RokFacade;
import data.Data;
import embeddable.Mce;
import entity.Dok;
import entity.DraSumy;
import entity.Pitpoz;
import entity.Podatnik;
import entity.PodatnikOpodatkowanieD;
import entity.PodatnikUdzialy;
import entity.Podmiot;
import entity.Ryczpoz;
import entity.WierszDRA;
import entity.Zusmail;
import entityplatnik.UbezpZusrca;
import entityplatnik.Zusdra;
import entityplatnik.Zusrca;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import kadryiplace.Firma;
import kadryiplace.Okres;
import kadryiplace.Place;
import kadryiplace.Rok;
import mail.MaiManager;
import msg.Msg;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class DochodDlaDRAView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private PodmiotDAO podmiotDAO;
    @Inject
    private DraSumyDAO draSumyDAO;
    @Inject
    private PodatnikUdzialyDAO podatnikUdzialyDAO;
    @Inject
    private PodatnikOpodatkowanieDAO podatnikOpodatkowanieDDAO;
    @Inject
    private PitDAO pitDAO;
    @Inject
    private RyczDAO ryczDAO;
    private String rok;
    private String mc;
    private String maxmc;
    private String opodatkowanie;
    private List<WierszDRA> wiersze;
    private List<WierszDRA> wierszerok;
    private List<WierszDRA> wierszeFiltered;
    private List<WierszDRA> wierszeN;
    private WierszDRA selected;
    private DraSumy selected1;
    @Inject
    private WierszDRADAO wierszDRADAO;
    @Inject
    private ZusdraDAO zusdraDAO;
    @Inject
    private ZusrcaDAO zusrcaDAO;
    @Inject
    private UbezpZusrcaDAO ubezpZusrcaDAO;
    @Inject
    private ZusmailDAO zusmailDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    @Inject
    private FirmaFacade firmaFacade;
    @Inject
    private RokFacade rokFacade;
    private Podatnik podatnik;
//    private List<Zusdra> zusdralista;
//    private List<Zusrca> zusrcalista;
    private boolean pokazzrobione;
    private List<DraSumy> drasumy;
    private int razemubezpieczeni;
    private int razemprzedsiebiorcy;
    private int razempracownicy;
    private int razemzleceniobiorcy;
    private int razeminne;

    @PostConstruct
    public void start() {
        rok = "2022";
        maxmc = Data.poprzedniMc();
    }
    
    
    public void przeliczRok() {
        if (podatnik!=null) {
            for (String mic : Mce.getMiesiaceGranica(maxmc)) {
                mc = mic;
                przelicz(podatnik.getNip());
            }
            pobierzrokByNip();
            Msg.dP();
        }
    }
    
    public void modyfikujliste() {
        if (opodatkowanie!=null) {
            if (opodatkowanie.equals("skala")) {
                pobierz();
                wiersze = wiersze.stream().filter(p->p.getOpodatkowanie().equals("zasady ogólne")).collect(Collectors.toList());
            } else if (opodatkowanie.equals("liniowy")) {
                pobierz();
                wiersze = wiersze.stream().filter(p->p.getOpodatkowanie().equals("podatek liniowy")).collect(Collectors.toList());
            } else if (opodatkowanie.equals("ryczałt")) {
                pobierz();
                wiersze = wiersze.stream().filter(p->p.getOpodatkowanie().equals("ryczałt")).collect(Collectors.toList());
            }
        } else {
            pobierz();
        }
    }
    
    public void przelicz(String nip) {
        List<WierszDRA> wierszebaza = wierszDRADAO.findByRok(rok);
        if (wierszebaza==null) {
            wierszebaza = new ArrayList<>();
        }
        if (rok!=null&&mc!=null) {
            String[] poprzedniOkres = Data.poprzedniOkres(mc, rok);
            String mcod = poprzedniOkres[0];
            String mcdo = poprzedniOkres[0];
            String rokpkpir = poprzedniOkres[1];
            if (!mc.equals("01")) {
                rokpkpir = rok;
            }
            List<Podatnik> podatnicy = null;
            if (nip!=null&&!nip.equals("")) {
                podatnicy = new ArrayList<>();
                podatnicy.add(podatnikDAO.findPodatnikByNIP(nip));
            } else {
                podatnicy = podatnikDAO.findPodatnikNieFK();
            }
            //Podatnik podat = podatnikDAO.findPodatnikByNIP("9552379284");
            //List<Podatnik> podatnicy = new ArrayList<>();
            //podatnicy.add(podat);
            for (Iterator<Podatnik> it = podatnicy.iterator();it.hasNext();) {
                Podatnik p = it.next();
                if (p.getNip()!=null&&p.getNip().equals("8511005008")) {
                    it.remove();
                }
            }
            Collections.sort(podatnicy, new Podatnikcomparator());
            double podatnikprocentudzial = 100.0;
            if (nip==null) {
                this.wiersze = new ArrayList<>();
            }
            int i = 1;
            for (Podatnik podatnik : podatnicy) {
                //if (podatnik.getNip().equals("9552563450")) {
                //if (podatnik.getNip().equals("8511005008")||podatnik.getNip().equals("8511054159")||podatnik.getNip().equals("8792611113")||podatnik.getNip().equals("9551392851")||podatnik.getNip().equals("9281839264")) {
                    PodatnikOpodatkowanieD opodatkowanie = zwrocFormaOpodatkowania(podatnik, rok, mc);
                    if (opodatkowanie != null) {
                        String formaopodatkowania = opodatkowanie.getFormaopodatkowania();
                        List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(podatnik);
                        if (udzialy != null && udzialy.size() > 0) {
                            for (PodatnikUdzialy u : udzialy) {
                                if (u.getPodatnikObj()!=null) {
                                    String rozpoczecie = u.getDatarozpoczecia();
                                    String zakonczenie = u.getDatazakonczenia();
                                    if (zakonczenie==null ||zakonczenie.equals("")) {
                                        zakonczenie = "2050-12-31";
                                    }
                                    if (Data.czyjestpomiedzy(rozpoczecie, zakonczenie, rok, mc)) {
                                        String imieinazwisko = u.getNazwiskoimie();
                                        WierszDRA wiersz = pobierzwiersz(wierszebaza, u, rok, mc);
                                        wiersz.setPodatnikudzial(u);
                                        wiersz.setOpodatkowanie(formaopodatkowania);
                                        podatnikprocentudzial = Double.valueOf(wiersz.getPodatnikudzial().getUdzial());
                                        if (formaopodatkowania.contains("ryczałt")) {
                                            //oblicz przychod
                                            double przychod = pobierzprzychod(podatnik, rok, mc, wiersz);
                                            if (podatnikprocentudzial != 100.0) {
                                                przychod = Z.z(przychod * podatnikprocentudzial / 100.0);
                                            }
                                            wiersz.setWynikpodatkowymc(przychod);
                                            wiersz.setWynikpodatkowynar(przychod);
                                            wiersz.setDochodzus(przychod > 0.0 ? przychod : 0.0);
                                            //wiersz.setDochodzusnetto(przychod);
                                            wiersz.setDochodzusnar(przychod > 0.0 ? przychod : 0.0);
                                            //wiersz.setDochodzusnettonar(przychod > 0.0 ? przychod : 0.0);
                                            boolean jestpit = pobierzrycz(rok, mc, podatnik.getNazwapelna());
                                            wiersz.setJestpit(jestpit);
                                            //Msg.msg("Obliczono przychód za mc");
                                            if (Mce.getMiesiacToNumber().get(mc) > 1) {
                                                WierszDRA wierszmcpop = pobierzwierszmcpop(wierszebaza, wiersz.getPodatnikudzial(), rok, mcod);
                                                if (wierszmcpop != null) {
                                                    wiersz.setWynikpodatkowynar(Z.z(wierszmcpop.getWynikpodatkowynar()+ wiersz.getWynikpodatkowymc()));
                                                    wiersz.setDochodzus(Z.z(wiersz.getWynikpodatkowymc()));
                                                    //wiersz.setDochodzusnetto(Z.z(wiersz.getWynikpodatkowymc()));
                                                    wiersz.setDochodzusnar(Z.z(wiersz.getWynikpodatkowynar()));
                                                    //wiersz.setDochodzusnettonar(Z.z(wierszmcpop.getDochodzusnettonar() + wiersz.getDochodzusnetto()));
                                                }
                                            }
                                        } else {
                                            //oblicz dochod
                                            double dochod = pobierzdochod(podatnik, rokpkpir, mcdo, wiersz);
                                            if (podatnikprocentudzial != 100.0) {
                                                dochod = Z.z(dochod * podatnikprocentudzial / 100.0);
                                            }
                                            wiersz.setWynikpodatkowymc(dochod);
                                            wiersz.setWynikpodatkowynar(dochod);
                                            wiersz.setDochodzus(dochod > 0.0 ? dochod : 0.0);
                                            wiersz.setDochodzusnar(dochod > 0.0 ? dochod : 0.0);
                                            //wiersz.setDochodzusnetto(dochod > 0.0 ? dochod : 0.0);
                                            //wiersz.setDochodzusnettonar(dochod > 0.0 ? dochod : 0.0);
                                            boolean jestpit = pobierzpit(rokpkpir, mcod, podatnik.getNazwapelna());
                                            wiersz.setJestpit(jestpit);
                                                //Msg.msg("Obliczono dochód za mc");
                                            if (Mce.getMiesiacToNumber().get(mc) > 2) {
                                                WierszDRA wierszmcpop = pobierzwierszmcpop(wierszebaza, wiersz.getPodatnikudzial(),rok, mcod);
                                                if (wierszmcpop != null) {
                                                    wiersz.setWynikpodatkowynar(Z.z(wierszmcpop.getWynikpodatkowynar() + wiersz.getWynikpodatkowymc()));
                                                    double dochodzus = Z.z(wiersz.getWynikpodatkowynar() - wierszmcpop.getDochodzusnar());
                                                    wiersz.setDochodzus(dochodzus > 0.0 ? dochodzus : 0.0);
                                                    //wiersz.setDochodzusnetto(dochodzus > 0.0 ? dochodzus : 0.0);
                                                    wiersz.setDochodzusnar(Z.z(wiersz.getDochodzus() + wierszmcpop.getDochodzusnar()));
                                                    //wiersz.setDochodzusnettonar(Z.z(wierszmcpop.getDochodzusnettonar() + wiersz.getDochodzusnetto()));
                                                }
                                            }
                                        }
                                        if (wiersz.getId()==null) {
                                            wiersz.setData(new Date());
                                            //System.out.println("create "+wiersz.getImienazwisko());
                                            wierszDRADAO.create(wiersz);
                                        } else {
                                            //System.out.println("edit "+wiersz.getImienazwisko());
                                            wierszDRADAO.edit(wiersz);
                                        }
                                        if (nip==null) {
                                            this.wiersze.add(wiersz);
                                        }
                                    } else if (udzialy.size()==1){
                                        Msg.msg("e","Sprawdź udziały w firmie "+podatnik.getPrintnazwa());
                                    }
                                }
                            }
                        }

                    } 
                    i++;
                //}
            }
//            if (this.wiersze!=null&&this.wiersze.size()>0) {
//                List<WierszDRA> wierszeglowne = wiersze.stream().filter(p->p.getPodatnikudzial()!=null&&p.getPodatnikudzial().isPit()).collect(Collectors.toList());
//                List<WierszDRA> wierszedodatkowe = wiersze.stream().filter(p->p.getPodatnikudzial()!=null&&p.getPodatnikudzial().isPit()==false).collect(Collectors.toList());
//                for (WierszDRA d : wierszedodatkowe) {
//                    for (WierszDRA g : wierszeglowne) {
//                        if (d.getPodatnikudzial().getPodmiot().equals(d.getPodatnikudzial().getPodmiot())&&d.getRok().equals(g.getRok())&&d.getMc().equals(g.getMc())) {
//                            dodajdoglownego(d,g, g.getOpodatkowanie(), wierszebaza, mcod);
//                            wierszDRADAO.edit(g);
//                        }
//                    }
//                }
//            }
            Msg.msg("Pobrano i przeliczono dane za mc"+mc);
            
        } else {
            Msg.msg("e","Nie wybrano okresu");
        }
    }
    
    private void dodajdoglownego(WierszDRA d, WierszDRA wiersz, String formaopodatkowania, List<WierszDRA> wierszebaza, String mcod) {
           int odkiedy = 2;
            if (formaopodatkowania.contains("ryczałt")) {
                odkiedy = 1;
            }
            wiersz.setWynikpodatkowymcInne(d.getWynikpodatkowymc());
            wiersz.setWynikpodatkowynarInne(d.getWynikpodatkowynar());
            double dochodmiesiacsuma = wiersz.getWynikpodatkowymc()+wiersz.getWynikpodatkowymcInne();
            double dochodmiesiacsumanra = wiersz.getWynikpodatkowynar()+wiersz.getWynikpodatkowynarInne();
            wiersz.setDochodzus(dochodmiesiacsuma > 0.0 ? dochodmiesiacsuma : 0.0);
            wiersz.setDochodzusnar(dochodmiesiacsumanra > 0.0 ? dochodmiesiacsumanra : 0.0);
            if (Mce.getMiesiacToNumber().get(mc) > odkiedy) {
                WierszDRA wierszmcpop = pobierzwierszmcpop(wierszebaza, wiersz.getPodatnikudzial(), rok, mcod);
                if (wierszmcpop != null) {
                    wiersz.setWynikpodatkowynar(Z.z(wierszmcpop.getWynikpodatkowynar() + wiersz.getWynikpodatkowymc()));
                    wiersz.setWynikpodatkowynarInne(wiersz.getWynikpodatkowynarInne()+d.getWynikpodatkowynar());
                    dochodmiesiacsuma = wiersz.getWynikpodatkowymc()+wiersz.getWynikpodatkowymcInne();
                    dochodmiesiacsumanra = wiersz.getWynikpodatkowynar()+wiersz.getWynikpodatkowynarInne();
                    wiersz.setDochodzus(dochodmiesiacsuma > 0.0 ? dochodmiesiacsuma : 0.0);
                    wiersz.setDochodzusnar(dochodmiesiacsumanra > 0.0 ? dochodmiesiacsumanra : 0.0);
                }
            }
    }
    
     public void pobierzrokByNip() {
        if (podatnik!=null) {
            wierszerok = wierszDRADAO.findByRokPodatnik(rok, podatnik);
            if (wierszerok==null) {
                wierszerok = new ArrayList<>();
            }
            Collections.sort(wierszerok, new WierszDRAcomparator());
            Msg.msg("Pobrano i przeliczono dane");
        }
    }
    
    
//    public void pobierzrok() {
//        List<WierszDRA> wierszebaza = wierszDRADAO.findByRok(rok);
//        if (wierszebaza==null) {
//            wierszebaza = new ArrayList<>();
//        }
//        Collections.sort(wierszebaza, new WierszDRAcomparator());
//        Map<String, List<WierszDRA>> kotek = new TreeMap<>();
//        for (WierszDRA p : wierszebaza) {
//            if (kotek.containsKey(p.getImienazwisko())) {
//                kotek.get(p.getImienazwisko()).add(p);
//            } else {
//                List<WierszDRA> nowalista = new ArrayList<>();
//                nowalista.add(p);
//                kotek.put(p.getImienazwisko(), nowalista);
//            }
//        }
//        mapa = new ArrayList<>();
//        for (List<WierszDRA> k : kotek.values()) {
//            mapa.add(k);
//        }
//        Msg.msg("Pobrano i przeliczono dane");
//    }
//    
    public void edytuj(WierszDRA wiersz) {
        if (wiersz!=null) {
            wierszDRADAO.edit(wiersz);
            Msg.msg("Odhaczono");
        }
    }
    
    public void pobierz() {
        String[] zwiekszmiesiac = Mce.zwiekszmiesiac(rok, mc);
        List<Zusmail> maile = zusmailDAO.findZusRokMc(zwiekszmiesiac[0], zwiekszmiesiac[1]);
        wiersze = wierszDRADAO.findByRokMc(rok, mc);
        if (pokazzrobione==false) {
            for (Iterator<WierszDRA> it = wiersze.iterator();it.hasNext();) {
                WierszDRA w = it.next();
                if (w.isZrobiony()) {
                    it.remove();
                }
            }
        }
        String okres = mc+rok;
        List<DraSumy> dralistatmp = draSumyDAO.zwrocRokMc(rok,mc);
        List<DraSumy> drasumytabela = przetworzZusdra(dralistatmp);
        List<kadryiplace.Firma> firmy = firmaFacade.findAll();
        for (WierszDRA w : wiersze) {
            DraSumy znaleziona = drasumytabela.stream().filter(p->p.getPodatnik()!=null&&p.getPodatnik().equals(w.getPodatnikudzial().getPodatnikObj())).findFirst().orElse(null);
            if (znaleziona!=null) {
                w.setDraSumy(znaleziona);
                if (w.getOpodatkowanie().equals("ryczałt")) {
                    if (w.getWynikpodatkowynar()==znaleziona.getDraprzychody()) {
                        w.setBlad(false);
                    } else if (w.getWynikpodatkowynar()!=znaleziona.getDraprzychody()) {
                        w.setBlad(true);
                    }
                } else {
                    if (w.getWynikpodatkowymc()>0) {
                        if (w.getWynikpodatkowymc()==znaleziona.getDraprzychody()) {
                            w.setBlad(false);
                        } else if (w.getWynikpodatkowymc()!=znaleziona.getDraprzychody()) {
                            w.setBlad(true);
                        }
                    } else if (w.getWynikpodatkowymc()<0 && znaleziona.getDraprzychody()>0) {
                        w.setBlad(true);
                    } else {
                        w.setBlad(false);
                    }
                }
                dodajpit4DRA(znaleziona, firmy);
                przygotujmail(w, maile,zwiekszmiesiac[0],zwiekszmiesiac[1]);
            }
        }
         if (opodatkowanie!=null) {
            if (opodatkowanie.equals("skala")) {
                pobierz();
                wiersze = wiersze.stream().filter(p->p.getOpodatkowanie().equals("zasady ogólne")).collect(Collectors.toList());
            } else if (opodatkowanie.equals("liniowy")) {
                pobierz();
                wiersze = wiersze.stream().filter(p->p.getOpodatkowanie().equals("podatek liniowy")).collect(Collectors.toList());
            } else if (opodatkowanie.equals("ryczałt")) {
                pobierz();
                wiersze = wiersze.stream().filter(p->p.getOpodatkowanie().equals("ryczałt")).collect(Collectors.toList());
            }
        }
        Collections.sort(wiersze, new WierszDRAcomparator());
        Msg.msg("Pobrano dane");
    }

    private void dodajpit4DRA(DraSumy w, List<kadryiplace.Firma> firmy) {
        if (w.getPodatnik()!=null) {
            Firma firma = null;
            for (Firma f : firmy) {
                if (f.getFirNip()!=null) {
                    if (f.getFirNip().replace("-", "").equals(w.getPodatnik().getNip())) {
                        firma = f;
                        break;
                    }
                }
            }
            if (firma != null) {
                Rok rok = rokFacade.findByFirmaRok(firma, Integer.parseInt(w.getRok()));
                kadryiplace.Okres okres = null;
                for (Okres o : rok.getOkresList()) {
                    if (o.getOkrMieNumer() == Mce.getMiesiacToNumber().get(w.getMc())) {
                        okres = o;
                        break;
                    }
                }
                List<Place> placeList = okres.getPlaceList();
                int studenci = 0;
                double podatekpraca = 0.0;
                for (Place p : placeList) {
                    podatekpraca = podatekpraca+p.getLplZalDoch().doubleValue();
                    if (p.getLplKodTytU12().equals("0411") && p.getLplPdstZus().doubleValue()==0.0 && p.getLplZalDoch().doubleValue() == 0.0) {
                        studenci = studenci + 1;
                    }
                }
                w.setStudenci(studenci);
                w.setUbezpieczeni(w.getUbezpieczeni()+w.getStudenci());
                w.setPit4(podatekpraca);
            }
        }
    }

    private List<DraSumy> przetworzZusdra(List<DraSumy> zusdra) {
       Map<String,DraSumy> nowe = new HashMap<>();
        if (zusdra!=null) {
            for (DraSumy p : zusdra) {
                if (p.getPodatnik()!=null) {
                    if (!nowe.containsKey(p.getPodatnik().getNip())) {
                        nowe.put(p.getPodatnik().getNip(), p);
                    } else {
                        DraSumy stara = nowe.get(p.getPodatnik().getNip());
                        if (Integer.valueOf(stara.getNr())<Integer.valueOf(p.getNr())) {
                            nowe.remove(p.getPodatnik().getNip());
                            nowe.put(p.getPodatnik().getNip(), p);
                        }
                    }
                }
            }
        }
        return new ArrayList<DraSumy>(nowe.values());
    }
    
    
    
    public void podsumujDRA() {
        if (mc==null) {
            mc = Data.poprzedniMc();
        }
        List<DraSumy> bazadanych = draSumyDAO.zwrocRokMc(rok, mc);
        List<kadryiplace.Firma> firmy = firmaFacade.findAll();
        String mc1 = mc;
        if (mc1==null) {
            mc1 = Data.poprzedniMc();
        }
        podsumujDRAF(mc1, rok, bazadanych, firmy);
     }
    
    
    public void podsumujDRAF(String mc, String rok, List<DraSumy> bazadanych,List<kadryiplace.Firma> firmy) {
        drasumy = Collections.synchronizedList(new ArrayList<>());
        String okres = mc+rok;
        List<Zusdra> zusdra = zusdraDAO.findByOkres(okres);
        List<Zusrca> zusrca = zusrcaDAO.findByOkres(okres);
        List<Podatnik> podatnicy = podatnikDAO.findAllManager();
        List<Podmiot> podmioty = podmiotDAO.findAll();
        List<DraSumy> dralistatmp = draSumyDAO.zwrocRokMc(rok,mc);
        List<DraSumy> zusdralista = przetworzZusdra(dralistatmp);
        int i = 1;
        zusdra.stream().forEach(z-> {
            DraSumy dras = zusdralista.stream().filter(p->p.getIddokument()==z.getIdDokument()).findFirst().orElse(null);
            if (dras==null) {
                dras = new DraSumy();
                dras.setRok(rok);
                dras.setMc(mc);
                for (Podatnik za : podatnicy) {
                    if (za.getNip().equals(z.getIi1Nip())) {
                        dras.setPodatnik(za);
                        break;
                    }
                }
                if (dras.getPodatnik()==null) {
                    for (Podatnik za : podatnicy) {
                        if (za.getPesel()!=null && za.getPesel().equals(z.getIi3Pesel())) {
                            dras.setPodatnik(za);
                            break;
                        }
                    }
                }
                if (dras.getPodatnik()==null && podmioty!=null) {
                    for (Podmiot za : podmioty) {
                        if (za.getNip().equals(z.getIi1Nip())) {
                            dras.setPodmiot(za);
                            break;
                        }
                    }
                    if (dras.getPodmiot()==null) {
                        for (Podmiot za : podmioty) {
                            if (za.getPesel()!=null && za.getPesel().equals(z.getIi3Pesel())) {
                                dras.setPodmiot(za);
                                break;
                            }
                        }
                    }
                }
                dras.setIddokument(z.getIdDokument());
            }
            dras.setZusdra(z);
            dras.setNazwa(dras.getNazwaF());
            if (bazadanych!=null&&!bazadanych.isEmpty()) {
                dras = pobierzbaza(dras,bazadanych);
            }
            dras.setZusdra(z);
            for (Zusrca r : zusrca) {
                if (r.getI12okrrozl().equals(z.getI22okresdeklar()) && r.getIdPlatnik()==z.getIdPlatnik()) {
                    dras.setZusrca(r);
                    List<UbezpZusrca> zalezne = ubezpZusrcaDAO.findByIdDokNad(r);
                    dras.setUbezpZusrcaList(zalezne);
                    break;
                }
            }
            if (dras.getUbezpZusrcaList()!=null && !dras.getUbezpZusrcaList().isEmpty()) {
                for (UbezpZusrca u : dras.getUbezpZusrcaList()) {
                    if (u.getIiiA4Identyfik().equals(z.getIi3Pesel())) {
                        dras.setUbezpZusrca(u);
                    }
                }
            }
            dras.setUbezpieczeni(dras.getUbezpieczeniF());
            dras.setPrzedsiebiorcy(dras.getPrzedsiebiorcyF());
            dras.setPracownicy(dras.getPracownicyF());
            dras.setZleceniobiorcy(dras.getZleceniobiorcyF());
            dras.setZleceniobiorcyzerowi(dras.getZleceniobiorcyZerowiF());
            dras.setInnetytuly(dras.getInnetytulyF());
            dras.setKod(dras.getKodF());
            dras.setSpoleczne(dras.getSpoleczneF());
            dras.setZdrowotne(dras.getZdrowotneF());
            dras.setData(Data.data_yyyyMMdd(z.getXii8Datawypel()));
            dras.setNr(z.getI21iddekls());
            dras.setOkres(z.getI22okresdeklar());
            dras.setDraprzychody(dras.getDraprzychodyF());
            dras.setDraprzychodyRR(dras.getDraprzychodyRRF());
            double kwota = z.getIx2Kwdozaplaty()!=null?z.getIx2Kwdozaplaty().doubleValue():0.0;
            dras.setDozaplaty(kwota);
            double kwotafp = z.getViii3KwzaplViii()!=null?z.getViii3KwzaplViii().doubleValue():0.0;
            dras.setFp(kwotafp);
            dodajpit4DRA(dras, firmy);
            drasumy.add(dras);
        });
        sumujdra();
        draSumyDAO.editList(drasumy);
        System.out.println("");
    }
    
    private DraSumy pobierzbaza(DraSumy dras, List<DraSumy> bazadanych) {
        DraSumy zwrot = dras;
        for (DraSumy p : bazadanych) {
            if (p.getIddokument() == dras.getIddokument()) {
                zwrot = p;
                break;
            } else {
                if (p.getPodatnik() != null) {
                    if (p.getPodatnik().equals(dras.getPodatnik()) && p.getNr().equals(dras.getNr())) {
                        zwrot = p;
                        break;
                    }
                } else {
                    if (p.getPodmiot() != null) {
                        if (p.getPodmiot().equals(dras.getPodmiot()) && p.getNr().equals(dras.getNr())) {
                            zwrot = p;
                            break;
                        }
                    } else {
                        if (p.getNazwa().equals(dras.getNazwa()) && p.getNr().equals(dras.getNr())) {
                            zwrot = p;
                            break;
                        }
                    }
                }
            }
        }
        return zwrot;
    }
    
    private void sumujdra() {
        if (drasumy!=null) {
            razemprzedsiebiorcy = 0;
            razemubezpieczeni = 0;
            razempracownicy = 0;
            razemzleceniobiorcy = 0;
            razeminne = 0;
            List<DraSumy> przetworzone = przetworz(drasumy);
            for (DraSumy p : przetworzone) {
                razemubezpieczeni = razemubezpieczeni+p.getUbezpieczeni();
                razemprzedsiebiorcy = razemprzedsiebiorcy+p.getPrzedsiebiorcy();
                razempracownicy = razempracownicy+p.getPracownicy();
                razemzleceniobiorcy = razemzleceniobiorcy+p.getZleceniobiorcy();
                razeminne = razeminne+p.getInnetytuly();
            }
            
        }
    }
    
    private List<DraSumy> przetworz(List<DraSumy> drasumy) {
        Map<String,DraSumy> nowe = new HashMap<>();
        for (DraSumy p : drasumy) {
            if (!nowe.containsKey(p.getZusdra().getIi1Nip())) {
                nowe.put(p.getZusdra().getIi1Nip(), p);
            } else {
                nowe.remove(p.getZusdra().getIi1Nip());
                nowe.put(p.getZusdra().getIi1Nip(), p);
            }
        }
        return new ArrayList<DraSumy>(nowe.values());
    }
    
    private void dodajpit4(WierszDRA w, List<kadryiplace.Firma> firmy) {
        Firma firma = null;
        for (Firma f : firmy) {
            if (f.getFirNip().replace("-", "").equals(w.getPodatnikudzial().getPodatnikObj().getNip())) {
                firma = f;
                break;
            }
        }
        if (firma!=null) {
            Rok rok = rokFacade.findByFirmaRok(firma, Integer.parseInt(w.getRok()));
            kadryiplace.Okres okres = null;
            for (Okres o : rok.getOkresList()) {
                if (o.getOkrMieNumer()==Mce.getMiesiacToNumber().get(w.getMc())) {
                    okres = o;
                    break;
                }
            }
            List<Place> placeList = okres.getPlaceList();
            double podatekpraca = 0.0;
            for (Place p : placeList) {
                podatekpraca = podatekpraca+p.getLplZalDoch().doubleValue();
            }

        }
     }
    
    
    
    private boolean pobierzpit(String rokpkpir, String mcod, String nazwapelna) {
        boolean zwrot = false;
        try {
            List<Pitpoz> pitlList = pitDAO.findList(rokpkpir, mcod, nazwapelna);
            if (pitlList!=null&&!pitlList.isEmpty()) {
                zwrot = true;
            }
        } catch (Exception ew){}
        return zwrot;
    }
    
    private boolean pobierzrycz(String rokpkpir, String mcod, String nazwapelna) {
        boolean zwrot = false;
        try {
            List<Ryczpoz> ryczlList = ryczDAO.findList(rokpkpir, mcod, nazwapelna);
            if (ryczlList!=null&&!ryczlList.isEmpty()) {
                zwrot = true;
            }
        } catch (Exception ew){}
        return zwrot;
    }
    
    private WierszDRA pobierzwierszmcpop(List<WierszDRA> wiersze, PodatnikUdzialy podatnikUdzialy, String rok, String mc) {
        WierszDRA zwrot = null;
        if (wiersze.size()>0) {
            for (WierszDRA w : wiersze) {
                if (w.getPodatnikudzial().equals(podatnikUdzialy)&&w.getMc().equals(mc)&&w.getRok().equals(rok)) {
                    zwrot = w;
                    break;
                }
            }
        }
        return zwrot;
    }
 
    private WierszDRA pobierzwiersz(List<WierszDRA> wiersze, PodatnikUdzialy podatnikUdzialy, String rok, String mc) {
        WierszDRA zwrot = new WierszDRA(podatnikUdzialy, rok, mc);
        boolean dodac = true;
        if (wiersze.size()>0) {
            for (WierszDRA w : wiersze) {
                if (w.getPodatnikudzial().equals(podatnikUdzialy)&&w.getRok().equals(rok)&&w.getMc().equals(mc)) {
                    zwrot = w;
                    dodac = false;
                    break;
                }
            }
        }
        if (dodac) {
            wiersze.add(zwrot);
        }
        return zwrot;
    }

    public PodatnikOpodatkowanieD zwrocFormaOpodatkowania(Podatnik podatnik, String rok, String mc) {
        PodatnikOpodatkowanieD zwrot = null;
        boolean jedno = ilerodzajowopodatkowania(podatnik, rok);
        if (jedno) {
            zwrot = podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRok(podatnik, rok);
        } else {
            List<PodatnikOpodatkowanieD> lista = podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRokWiele(podatnik, rok);
            for (PodatnikOpodatkowanieD p : lista) {
                boolean jestmiedzy = Data.czyjestpomiedzy(p.getDatarozpoczecia(), p.getDatazakonczenia(), rok, mc);
                if (jestmiedzy) {
                    zwrot = p;
                    break;
                }
            }
        }
        return zwrot;
    }

    private boolean ilerodzajowopodatkowania(Podatnik podatnik, String rok) {
        boolean jedno = true;
        List lista = podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRokWiele(podatnik, rok);
        if (lista != null && lista.size() > 1) {
            jedno = false;
        }
        return jedno;
    }

    private double pobierzdochod(Podatnik podatnik, String rok, String mc, WierszDRA wiersz) {
        double dochod = 0.0;
        try {
             Pitpoz pit = pitDAO.find(rok, mc, podatnik.getNazwapelna());
            
            if (pit!=null) {
                dochod = pit.getPrzychodyudzialmc();
                wiersz.setBrakdokumentow(false);
            } else {
                wiersz.setBrakdokumentow(true);
            }
        } catch (Exception e) {
            E.e(e);
        }
        return dochod;
    }

    private double pobierzprzychod(Podatnik podatnik, String rok, String mc, WierszDRA wiersz) {
        double przychod = 0.0;
        List<Dok> lista = KsiegaBean.pobierzdokumentyRok(dokDAO, podatnik, Integer.parseInt(rok), mc, mc);
        if (lista!=null&&!lista.isEmpty()) {
            for (Iterator<Dok> it = lista.iterator(); it.hasNext();) {
                Dok tmpx = it.next();
                if (tmpx.getRodzajedok().isTylkojpk()) {
                    it.remove();
                }
            }
            for (Dok dokument : lista) {
                try {
                    przychod = przychod + dokument.getNetto();
                    
                } catch (Exception e) {
                    E.e(e);
                }
            }
            wiersz.setBrakdokumentow(false);
        } else {
            wiersz.setBrakdokumentow(true);
        }
        return przychod;
    }
    private static final String trescmaila = "<p> Dzień dobry</p> <p> Przesyłamy informacje o naliczonych kwoty zobowiązań z tytułu ZUS I PIT-4</p> "
            + "<p> dla firmy <span style=\"color:#008000;\">%s</span> NIP %s</p> "
            + "<p> do zapłaty/przelania w miesiącu <span style=\"color:#008000;\">%s/%s</span></p> "
            + " <table align=\"left\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width: 500px;\"> <caption> zestawienie zobowiązań</caption> <thead> <tr> "
            + "<th scope=\"col\"> lp</th> <th scope=\"col\"> tytuł</th> <th scope=\"col\"> kwota</th> </tr> </thead> <tbody> "
            + "<tr><td style=\"text-align: center;\"> 1</td><td><span style='font-weight: bold'>ZUS</span></td> <td style=\"text-align: right;\"><span style=\"text-align: right;font-weight: bold\"> %.2f</span></td> </tr> "
            + "<tr> <td style=\"text-align: center;\"> 2</td><td><span style='font-weight: bold'>PIT-4</span></td> <td style=\"text-align: right;\"> <span style=\"text-align: right;font-weight: bold\">%.2f</span></td> </tr> "
            + "<td style=\"text-align: center;\"> 3</td> <td><span style='font-weight: bold'>PIT-8AR</span></td> <td style=\"text-align: right;\"> <span style=\"text-align: right;font-weight: bold\">%.2f</span></td></tr> "
            + "</tbody> </table>"
            + " <p> &nbsp;</p> <p> &nbsp;</p> <p> &nbsp;</p> <p> &nbsp;</p> <p> &nbsp;</p><br/> "
            + "<p> Ważne! Przelew do ZUS od stycznia 2018 robimy jedną kwotą na JEDNO indywidualne konto wskazane przez ZUS.</p>"
            + "<p> To samo dotyczy podatków</p>"
            + "<p> Przypominamy o terminach płatności ZUS:</p>"
            + " <p> do <span style=\"color:#008000;\">15-go</span> - dla firm z osobowością prawną (sp. z o.o.)</p>"
            + " <p> do <span style=\"color:#008000;\">20-go</span> &nbsp;- dla pozostałych firm</p>"
            + " <p> Termin płatności podatku:</p>"
            + " <p> do <span style=\"color:#006400;\">20-go</span> - PIT-4/PIT-8 od wynagrodzeń pracownik&oacute;w</p>"
            + " <p> &nbsp;</p>";
    
     private void przygotujmail(WierszDRA wierszDRA, List<Zusmail> maile, String rok, String mc) {
        if (wierszDRA != null) {
            try {
                Zusmail zusmail = null;
                if (wierszDRA.getZusmail() != null) {
                    if (wierszDRA.getZusmail().getId() != null && wierszDRA.getRok().equals(rok) && wierszDRA.getMc().equals(mc)) {
                        zusmail = wierszDRA.getZusmail();
                    } else {
                        zusmail = null;
                        for (Zusmail l : maile) {
                            if (l.getPodatnik().equals(wierszDRA.getPodatnikudzial().getPodatnikObj())) {
                                zusmail = l;
                                break;
                            }
                        }
                    }
                } else {
                    for (Zusmail l : maile) {
                        if (l.getPodatnik().equals(wierszDRA.getPodatnikudzial().getPodatnikObj())) {
                            zusmail = l;
                            break;
                        }
                    }
                }
                if (zusmail == null) {
                    zusmail = new Zusmail(wierszDRA.getPodatnikudzial().getPodatnikObj(), rok, mc);
                }
                zusmail.setZus(wierszDRA.getDraSumy().getDozaplaty());
                zusmail.setPit4(wierszDRA.getDraSumy().getPit4());
                zusmail.setPit8(null);
                double zus = 0;
                double pit4 = 0;
                double pit8 = 0;
                zus = zusmail.getZus() != null ? zusmail.getZus() : 0;
                //            pit4 = zusmail.getPit4()!= null ? zusmail.getPit4(): 0;
                //            pit8 = zusmail.getPit8()!= null ? zusmail.getPit8(): 0;//kom
                zusmail.setTytul(String.format("Taxman - zestawienie kwot ZUS/PIT4 za %s/%s", wierszDRA.getRok(), wierszDRA.getMc()));
                String mnastepny = Mce.getNumberToMiesiac().get(Integer.valueOf(wierszDRA.getMc())+1);
                zusmail.setTresc(String.format(new Locale("pl_PL"), trescmaila, wierszDRA.getPodatnikudzial().getPodatnikObj().getPrintnazwa(), wierszDRA.getPodatnikudzial().getPodatnikObj().getNip(), wierszDRA.getRok(),mnastepny, zus, pit4, pit8));
                zusmail.setAdresmail(wierszDRA.getPodatnikudzial().getPodatnikObj().getEmail());
                zusmail.setWysylajacy("manager");
                wierszDRA.setZusmail(zusmail);
                wierszDRADAO.edit(wierszDRA);
            } catch (Exception e) {
                System.out.println(E.e(e));
            }
        }
    }
     
      public void wyslijMailZUS(WierszDRA wierszDRA) {
          Zusmail zusmail = wierszDRA.getZusmail();
        try {
            MaiManager.mailManagerZUSPIT(wierszDRA.getZusmail().getAdresmail(), zusmail.getTytul(), zusmail.getTresc(), "n.sinkiewicz@taxman.biz.pl", null, sMTPSettingsDAO.findSprawaByDef());
//            MaiManager.mailManagerZUSPIT(zusmail.getAdresmail(), zusmail.getTytul(), zusmail.getTresc(), zusmail.getPodatnik().getEmail(), null, sMTPSettingsDAO.findSprawaByDef());
            usuzpelnijdane(zusmail);
        } catch (Exception e) {
            E.m(e);
            Msg.msg("e", "Blad nie wyslano wiadomosci! " + e.toString());
        }
    }
    
     
    
    private void usuzpelnijdane(Zusmail zusmail) {
        try {
            if (zusmail != null) {
               zusmail.setNrwysylki(zusmail.getNrwysylki()+1);
               zusmail.setDatawysylki(new Date());
               zusmailDAO.edit(zusmail);
               Msg.msg("Wysłano zusmail");
            }
        } catch (Exception e) {
            E.m(e);
            Msg.msg("e", "Wystąpił błąd. Mail z ZUS nie został wysłany");
        }
    }
    
    
    public static void main(String[] args) {
        String format = String.format(new Locale("pl_PL"),"Taxman - zestawienie kwot ZUS/PIT4 za %,.2f",12544.22);
        System.out.println(format);
    }

    public List<WierszDRA> getWiersze() {
        return wiersze;
    }

    public void setWiersze(List<WierszDRA> wiersze) {
        this.wiersze = wiersze;
    }

    public WierszDRA getSelected() {
        return selected;
    }

    public void setSelected(WierszDRA selected) {
        this.selected = selected;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }


    public boolean isPokazzrobione() {
        return pokazzrobione;
    }

    public void setPokazzrobione(boolean pokazzrobione) {
        this.pokazzrobione = pokazzrobione;
    }

    public List<DraSumy> getDrasumy() {
        return drasumy;
    }

    public void setDrasumy(List<DraSumy> drasumy) {
        this.drasumy = drasumy;
    }

    public DraSumy getSelected1() {
        return selected1;
    }

    public void setSelected1(DraSumy selected1) {
        this.selected1 = selected1;
    }

    public int getRazemubezpieczeni() {
        return razemubezpieczeni;
    }

    public void setRazemubezpieczeni(int razemubezpieczeni) {
        this.razemubezpieczeni = razemubezpieczeni;
    }

    public int getRazemprzedsiebiorcy() {
        return razemprzedsiebiorcy;
    }

    public void setRazemprzedsiebiorcy(int razemprzedsiebiorcy) {
        this.razemprzedsiebiorcy = razemprzedsiebiorcy;
    }

    public int getRazempracownicy() {
        return razempracownicy;
    }

    public void setRazempracownicy(int razempracownicy) {
        this.razempracownicy = razempracownicy;
    }

    public int getRazemzleceniobiorcy() {
        return razemzleceniobiorcy;
    }

    public void setRazemzleceniobiorcy(int razemzleceniobiorcy) {
        this.razemzleceniobiorcy = razemzleceniobiorcy;
    }

    public int getRazeminne() {
        return razeminne;
    }

    public void setRazeminne(int razeminne) {
        this.razeminne = razeminne;
    }

    public List<WierszDRA> getWierszeFiltered() {
        return wierszeFiltered;
    }

    public void setWierszeFiltered(List<WierszDRA> wierszeFiltered) {
        this.wierszeFiltered = wierszeFiltered;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public List<WierszDRA> getWierszerok() {
        return wierszerok;
    }

    public void setWierszerok(List<WierszDRA> wierszerok) {
        this.wierszerok = wierszerok;
    }

    public String getMaxmc() {
        return maxmc;
    }

    public void setMaxmc(String maxmc) {
        this.maxmc = maxmc;
    }

    public String getOpodatkowanie() {
        return opodatkowanie;
    }

    public void setOpodatkowanie(String opodatkowanie) {
        this.opodatkowanie = opodatkowanie;
    }

    

    

    

    

    
    
}
