/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.KsiegaBean;
import comparator.Dokcomparator;
import comparator.Podatnikcomparator;
import comparator.WierszDRAcomparator;
import dao.DokDAO;
import dao.PitDAO;
import dao.PodatnikDAO;
import dao.PodatnikOpodatkowanieDAO;
import dao.PodatnikUdzialyDAO;
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
import embeddable.DRASumy;
import embeddable.Mce;
import embeddable.WierszPkpir;
import embeddable.WierszRyczalt;
import entity.Dok;
import entity.KwotaKolumna1;
import entity.Pitpoz;
import entity.Podatnik;
import entity.PodatnikOpodatkowanieD;
import entity.PodatnikUdzialy;
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
import java.util.TreeMap;
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
    private PodatnikUdzialyDAO podatnikUdzialyDAO;
    @Inject
    private PodatnikOpodatkowanieDAO podatnikOpodatkowanieDDAO;
    @Inject
    private PitDAO pitDAO;
    @Inject
    private RyczDAO ryczDAO;
    private String rok;
    private String mc;
    private List<WierszDRA> wiersze;
    private List<List<WierszDRA>> mapa;
    private WierszDRA selected;
    private DRASumy selected1;
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
    private List<Zusdra> zusdralista;
    private List<Zusrca> zusrcalista;
    private boolean pokazzrobione;
    private List<DRASumy> drasumy;
    private int razemubezpieczeni;
    private int razemprzedsiebiorcy;
    private int razempracownicy;
    private int razemzleceniobiorcy;
    private int razeminne;

    @PostConstruct
    public void start() {
        rok = "2022";
        mapa = new ArrayList<>();
    }
    
    
    public void przelicz() {
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
            List<Podatnik> podatnicy = podatnikDAO.findPodatnikNieFK();
            for (Iterator<Podatnik> it = podatnicy.iterator();it.hasNext();) {
                Podatnik p = it.next();
                if (p.getNip().equals("8511005008")) {
                    it.remove();
                }
            }
            Collections.sort(podatnicy, new Podatnikcomparator());
            double podatnikprocentudzial = 100.0;
            this.wiersze = new ArrayList<>();
            int i = 1;
            for (Podatnik podatnik : podatnicy) {
                //if (podatnik.getNip().equals("8431472104")) {
                //if (podatnik.getNip().equals("8511005008")||podatnik.getNip().equals("8511054159")||podatnik.getNip().equals("8792611113")||podatnik.getNip().equals("9551392851")||podatnik.getNip().equals("9281839264")) {
                    PodatnikOpodatkowanieD opodatkowanie = zwrocFormaOpodatkowania(podatnik, rok, mc);
                    if (opodatkowanie != null) {
                        String formaopodatkowania = opodatkowanie.getFormaopodatkowania();
                        List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(podatnik);
                        if (udzialy != null && udzialy.size() > 0) {
                            for (PodatnikUdzialy u : udzialy) {
                                String rozpoczecie = u.getDatarozpoczecia();
                                String zakonczenie = u.getDatazakonczenia();
                                if (zakonczenie==null ||zakonczenie=="") {
                                    zakonczenie = "2050-12-31";
                                }
                                if (Data.czyjestpomiedzy(rozpoczecie, zakonczenie, rok, mc)) {
                                    String imieinazwisko = u.getNazwiskoimie();
                                    WierszDRA wiersz = pobierzwiersz(wierszebaza, podatnik, imieinazwisko, rok, mc, Mce.getStringToNazwamiesiaca().get(mc));
                                    wiersz.setOpodatkowanie(formaopodatkowania);
                                    wiersz.setImienazwisko(imieinazwisko);
                                    wiersz.setPesel(u.getPesel());
                                    wiersz.setUdzial(Double.parseDouble(u.getUdzial()));
                                    podatnikprocentudzial = wiersz.getUdzial();
                                    if (formaopodatkowania.contains("ryczałt")) {
                                        //oblicz przychod
                                        double przychod = pobierzprzychod(podatnik, rok, mc, wiersz);
                                        if (podatnikprocentudzial != 100.0) {
                                            przychod = Z.z(przychod * podatnikprocentudzial / 100.0);
                                        }
                                        wiersz.setPrzychod(przychod);
                                        wiersz.setPrzychodnar(przychod);
                                        wiersz.setDochodzus(przychod > 0.0 ? przychod : 0.0);
                                        wiersz.setDochodzusnetto(przychod);
                                        wiersz.setDochodzusnar(przychod > 0.0 ? przychod : 0.0);
                                        wiersz.setDochodzusnettonar(przychod > 0.0 ? przychod : 0.0);
                                        boolean jestpit = pobierzrycz(rok, mc, podatnik.getNazwapelna());
                                        wiersz.setJestpit(jestpit);
                                        //Msg.msg("Obliczono przychód za mc");
                                        if (Mce.getMiesiacToNumber().get(mc) > 1) {
                                            WierszDRA wierszmcpop = pobierzwierszmcpop(wierszebaza, podatnik, imieinazwisko, rok, mcod);
                                            if (wierszmcpop != null) {
                                                wiersz.setPrzychodnar(Z.z(wierszmcpop.getPrzychodnar() + wiersz.getPrzychod()));
                                                wiersz.setDochodzus(Z.z(wiersz.getPrzychod()));
                                                wiersz.setDochodzusnetto(Z.z(wiersz.getPrzychod()));
                                                wiersz.setDochodzusnar(Z.z(wiersz.getPrzychodnar()));
                                                wiersz.setDochodzusnettonar(Z.z(wierszmcpop.getDochodzusnettonar() + wiersz.getDochodzusnetto()));
                                            }
                                        }
                                    } else {
                                        //oblicz dochod
                                        double dochod = pobierzdochod(podatnik, rokpkpir, mcdo, mcdo, wiersz);
                                        if (podatnikprocentudzial != 100.0) {
                                            dochod = Z.z(dochod * podatnikprocentudzial / 100.0);
                                        }
                                        wiersz.setWynikpodatkowymc(dochod);
                                        wiersz.setWynikpodatkowynar(dochod);
                                        wiersz.setDochodzus(dochod > 0.0 ? dochod : 0.0);
                                        wiersz.setDochodzusnar(dochod > 0.0 ? dochod : 0.0);
                                        wiersz.setDochodzusnetto(dochod > 0.0 ? dochod : 0.0);
                                        wiersz.setDochodzusnettonar(dochod > 0.0 ? dochod : 0.0);
                                        boolean jestpit = pobierzpit(rokpkpir, mcod, podatnik.getNazwapelna());
                                        wiersz.setJestpit(jestpit);
                                            //Msg.msg("Obliczono dochód za mc");
                                        if (Mce.getMiesiacToNumber().get(mc) > 2) {
                                            WierszDRA wierszmcpop = pobierzwierszmcpop(wierszebaza, podatnik, imieinazwisko, rok, mcod);
                                            if (wierszmcpop != null) {
                                                wiersz.setWynikpodatkowynar(Z.z(wierszmcpop.getWynikpodatkowynar() + wiersz.getWynikpodatkowymc()));
                                                double dochodzus = Z.z(wiersz.getWynikpodatkowynar() - wierszmcpop.getDochodzusnettonar());
                                                wiersz.setDochodzus(dochodzus > 0.0 ? dochodzus : 0.0);
                                                wiersz.setDochodzusnetto(dochodzus > 0.0 ? dochodzus : 0.0);
                                                wiersz.setDochodzusnar(Z.z(wiersz.getDochodzus() + wierszmcpop.getDochodzusnar()));
                                                wiersz.setDochodzusnettonar(Z.z(wierszmcpop.getDochodzusnettonar() + wiersz.getDochodzusnetto()));
                                            }
                                        }
                                    }
                                    if (wiersz.getId()==null) {
                                        //wiersz.setData(new Date());
                                        wierszDRADAO.create(wiersz);
                                    } else {
                                        wierszDRADAO.edit(wiersz);
                                    }
                                    this.wiersze.add(wiersz);
                                }
                            }
                        } else {
                            String imieinazwisko = podatnik.getNazwisko() + " " + podatnik.getImie();
                            WierszDRA wiersz = pobierzwiersz(wierszebaza, podatnik, imieinazwisko, rok, mc, Mce.getStringToNazwamiesiaca().get(mc));
                            wiersz.setOpodatkowanie(formaopodatkowania);
                            wiersz.setImienazwisko(imieinazwisko);
                            wiersz.setUdzial(100);
                            if (formaopodatkowania.contains("ryczałt")) {
                                //oblicz przychod
                                double przychod = pobierzprzychod(podatnik, rok, mc, wiersz);
                                wiersz.setPrzychod(przychod);
                                wiersz.setDochodzus(przychod);
                                boolean jestpit = pobierzrycz(rok, mcod, podatnik.getNazwapelna());
                                wiersz.setJestpit(jestpit);
                            } else {
                                //oblicz dochod
                                double dochod = pobierzdochod(podatnik, rokpkpir, mcdo, mcdo, wiersz);
                                wiersz.setWynikpodatkowymc(dochod);
                                wiersz.setDochodzus(dochod>0.0?dochod:0.0);
                                boolean jestpit = pobierzpit(rokpkpir, mcod, podatnik.getNazwapelna());
                                wiersz.setJestpit(jestpit);
                            }
                            this.wiersze.add(wiersz);
                        }
                    } else {
                        String imieinazwisko = podatnik.getNazwisko() + " " + podatnik.getImie();
                        WierszDRA wiersz = pobierzwiersz(wierszebaza, podatnik, imieinazwisko, rok, mc, Mce.getStringToNazwamiesiaca().get(mc));
                        
                        wiersz.setOpodatkowanie("brak opodatkowania");
                        wiersz.setImienazwisko(imieinazwisko);
                        wiersz.setUdzial(100);
                        this.wiersze.add(wiersz);
                    }
                    i++;
                //}
            }
            wierszebaza = wierszDRADAO.findByRok(rok);
            Collections.sort(wierszebaza, new WierszDRAcomparator());
            Map<String, List<WierszDRA>> kotek = new TreeMap<>();
            for (WierszDRA p : wierszebaza) {
                if (kotek.containsKey(p.getImienazwisko())) {
                    kotek.get(p.getImienazwisko()).add(p);
                } else {
                    List<WierszDRA> nowalista = new ArrayList<>();
                    nowalista.add(p);
                    kotek.put(p.getImienazwisko(), nowalista);
                }
            }
            mapa = new ArrayList<>();
            for (List<WierszDRA> k : kotek.values()) {
                mapa.add(k);
            }
            Msg.msg("Pobrano i przeliczono dane");
            
        } else {
            Msg.msg("e","Nie wybrano okresu");
        }
    }
    
    public void edytuj(WierszDRA wiersz) {
        if (wiersz!=null) {
            wierszDRADAO.edit(wiersz);
            Msg.msg("Odhaczono");
        }
    }
    
    public void pobierz() {
        String[] zwiekszmiesiac = Mce.zwiekszmiesiac(rok, mc);
        List<Zusmail> maile = zusmailDAO.findZusRokMc(zwiekszmiesiac[0], zwiekszmiesiac[1]);
        wiersze = wierszDRADAO.findByRok(rok);
        Collections.sort(wiersze, new WierszDRAcomparator());
            Map<String, List<WierszDRA>> kotek = new TreeMap<>();
            for (WierszDRA p : wiersze) {
                if (kotek.containsKey(p.getImienazwisko())) {
                    kotek.get(p.getImienazwisko()).add(p);
                } else {
                    List<WierszDRA> nowalista = new ArrayList<>();
                    nowalista.add(p);
                    kotek.put(p.getImienazwisko(), nowalista);
                }
            }
            mapa = new ArrayList<>();
            for (List<WierszDRA> k : kotek.values()) {
                mapa.add(k);
            }
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
        List<Zusdra> dralistatmp = zusdraDAO.findByOkres(okres);
        zusdralista = przetworzZusdra(dralistatmp);
        zusrcalista = zusrcaDAO.findByOkres(okres);
        List<kadryiplace.Firma> firmy = firmaFacade.findAll();
        for (WierszDRA w : wiersze) {
            
            for (Zusdra z : zusdralista) {
                if (w.getPodatnik().getNip().equals(z.getIi1Nip())) {
                    w.setZusdra(z);
                    break;
                }
            }
            if (w.getZusdra()==null && w.getPodatnik().getPesel()!=null) {
                for (Zusdra z : zusdralista) {
                    if (w.getPodatnik().getPesel().equals(z.getIi3Pesel())) {
                        w.setZusdra(z);
                        break;
                    }
                }
            }
            if (w.getZusdra()==null && w.getPesel()!=null) {
                for (Zusdra z : zusdralista) {
                    if (w.getPesel().equals(z.getIi3Pesel())) {
                        w.setZusdra(z);
                        break;
                    }
                }
            }
            for (Zusrca z : zusrcalista) {
                if (w.getPodatnik().getNip().equals(z.getIi1Nip())) {
                    w.setZusrca(z);
                    List<UbezpZusrca> zalezne = ubezpZusrcaDAO.findByIdDokNad(z.getIdDokument());
                    if (zalezne!=null && !zalezne.isEmpty()) {
                        z.setRcalista(zalezne);
                        for (UbezpZusrca u : zalezne) {
                            if (u.getIiiA4Identyfik().equals(z.getIi3Pesel())) {
                                w.setUbezpZusrca(u);
                            }
                        }
                    }
                    break;
                }
            }
            w.getPrzychodDochod();
            if (w.getOpodatkowanie().equals("ryczałt")) {
                if (w.getPrzychodnar()!=w.getPrzychoddra()) {
                    w.setBlad(true);
                }
                } else {
                    if (w.getDochodzus()!=w.getPrzychoddra()) {
                         w.setBlad(true);
                    }
                }
            dodajpit4(w, firmy);
            przygotujmail(w, maile,zwiekszmiesiac[0],zwiekszmiesiac[1]);
            }
        Collections.sort(wiersze, new WierszDRAcomparator());
        Msg.msg("Pobrano dane");
    }

    private List<Zusdra> przetworzZusdra(List<Zusdra> zusdra) {
       Map<String,Zusdra> nowe = new HashMap<>();
        if (zusdra!=null) {
            for (Zusdra p : zusdra) {
                if (!nowe.containsKey(p.getIi1Nip())) {
                    nowe.put(p.getIi1Nip(), p);
                } else {
                    Zusdra stara = nowe.get(p.getIi1Nip());
                    if (Integer.valueOf(stara.getI21iddekls())<Integer.valueOf(p.getI21iddekls())) {
                        nowe.remove(p.getIi1Nip());
                        nowe.put(p.getIi1Nip(), p);
                    }
                }
            }
        }
        return new ArrayList<Zusdra>(nowe.values());
    }
    public void podsumujDRA() {
        drasumy = new ArrayList<>();
        if (mc==null) {
            mc = Data.poprzedniMc();
        }
        String okres = mc+rok;
        List<Zusdra> zusdra = zusdraDAO.findByOkres(okres);
        List<Zusrca> zusrca = zusrcaDAO.findByOkres(okres);
        int i = 1;
        for (Zusdra z : zusdra) {
            DRASumy dras = new DRASumy();
            dras.setId(i++);
            dras.setRok(rok);
            dras.setMc(mc);
            dras.setZusdra(z);
            for (Zusrca r : zusrca) {
                if (r.getI12okrrozl().equals(z.getI22okresdeklar()) && r.getIdPlatnik()==z.getIdPlatnik()) {
                    dras.setZusrca(r);
                    List<UbezpZusrca> zalezne = ubezpZusrcaDAO.findByIdDokNad(r.getIdDokument());
                    dras.setUbezpZusrca(zalezne);
                    break;
                }
            }
            drasumy.add(dras);
            
        }
        sumujdra();
        System.out.println("");
    }
    
    private void sumujdra() {
        if (drasumy!=null) {
            razemprzedsiebiorcy = 0;
            razemubezpieczeni = 0;
            razempracownicy = 0;
            razemzleceniobiorcy = 0;
            razeminne = 0;
            List<DRASumy> przetworzone = przetworz(drasumy);
            for (DRASumy p : przetworzone) {
                razemubezpieczeni = razemubezpieczeni+p.getUbezpieczeni();
                razemprzedsiebiorcy = razemprzedsiebiorcy+p.getPrzedsiebiorcy();
                razempracownicy = razempracownicy+p.getPracownicy();
                razemzleceniobiorcy = razemzleceniobiorcy+p.getZleceniobiorcy();
                razeminne = razeminne+p.getInnetytuly();
            }
            
        }
    }
    
    private List<DRASumy> przetworz(List<DRASumy> drasumy) {
        Map<String,DRASumy> nowe = new HashMap<>();
        for (DRASumy p : drasumy) {
            if (!nowe.containsKey(p.getZusdra().getIi1Nip())) {
                nowe.put(p.getZusdra().getIi1Nip(), p);
            } else {
                nowe.remove(p.getZusdra().getIi1Nip());
                nowe.put(p.getZusdra().getIi1Nip(), p);
            }
        }
        return new ArrayList<DRASumy>(nowe.values());
    }
    
    private void dodajpit4(WierszDRA w, List<kadryiplace.Firma> firmy) {
        Firma firma = null;
        for (Firma f : firmy) {
            if (f.getFirNip().replace("-", "").equals(w.getPodatnik().getNip())) {
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
            w.setPit4(podatekpraca);
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
    
    private WierszDRA pobierzwierszmcpop(List<WierszDRA> wiersze, Podatnik podatnik, String imienazwisko, String rok, String mc) {
        WierszDRA zwrot = null;
        if (wiersze.size()>0) {
            for (WierszDRA w : wiersze) {
                if (w.getPodatnik().equals(podatnik)&&w.getImienazwisko().equals(imienazwisko)&&w.getMc().equals(mc)) {
                    zwrot = w;
                }
            }
        }
        return zwrot;
    }
 
    private WierszDRA pobierzwiersz(List<WierszDRA> wiersze, Podatnik podatnik, String imienazwisko, String rok, String mc, String get) {
        WierszDRA zwrot = new WierszDRA(podatnik, rok, mc, Mce.getStringToNazwamiesiaca().get(mc));
        zwrot.setImienazwisko(imienazwisko);
        boolean dodac = true;
        if (wiersze.size()>0) {
            for (WierszDRA w : wiersze) {
                if (w.getPodatnik().equals(podatnik)&&w.getImienazwisko().equals(imienazwisko)&&w.getRok().equals(rok)&&w.getMc().equals(mc)) {
                    zwrot = w;
                    dodac = false;
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

    private double pobierzdochod(Podatnik podatnik, String rok, String mcod, String mcdo, WierszDRA wiersz) {
        double dochod = 0.0;
        try {
             List<Dok> lista = null;
            try {
                lista = dokDAO.zwrocBiezacegoKlientaRokOdMcaDoMca(podatnik, rok, mcdo, mcod);
                Collections.sort(lista, new Dokcomparator());
            } catch (Exception e) { 
                E.e(e); 
            }
            if (lista!=null&&!lista.isEmpty()) {
                for (Iterator<Dok> it = lista.iterator(); it.hasNext();) {
                    Dok tmpx = it.next();
                    if (tmpx.getRodzajedok().isTylkojpk()) {
                        it.remove();
                    }
                }
                WierszPkpir wierszPkpir = new WierszPkpir(1, rok, mc, "dla DRA");
                for (Dok dokument : lista) {
                    try {
                        if (dokument.getUsunpozornie() == false) {
                            List<KwotaKolumna1> szczegol = dokument.getListakwot1();
                            for (KwotaKolumna1 tmp : szczegol) {
                                String selekcja = dokument.getPkpirM();
                                String selekcja2 = tmp.getNazwakolumny();
                                if (selekcja2 == null) {
                                    error.E.s("");
                                }
                                Double kwota = tmp.getNetto();
                                Double temp = 0.0;
                                switch (selekcja2) {
                                    case "przych. sprz":
                                        temp = wierszPkpir.getKolumna7() + kwota;
                                        wierszPkpir.setKolumna7(temp);
                                        break;
                                    case "pozost. przych.":
                                        temp = wierszPkpir.getKolumna8() + kwota;
                                        wierszPkpir.setKolumna8(temp);
                                        break;
                                    case "zakup tow. i mat.":
                                        temp = wierszPkpir.getKolumna10() + kwota;
                                        wierszPkpir.setKolumna10(temp);
                                        break;
                                    case "koszty ub.zak.":
                                        temp = wierszPkpir.getKolumna11() + kwota;
                                        wierszPkpir.setKolumna11(temp);
                                        break;
                                    case "wynagrodzenia":
                                        temp = wierszPkpir.getKolumna12() + kwota;
                                        wierszPkpir.setKolumna12(temp);
                                        break;
                                    case "poz. koszty":
                                        temp = wierszPkpir.getKolumna13() + kwota;
                                        wierszPkpir.setKolumna13(temp);
                                        break;
                                    case "inwestycje":
                                        temp = wierszPkpir.getKolumna15() + kwota;
                                        wierszPkpir.setKolumna15(temp);
                                        break;
                                }
                            }
                            //pobierzPity();
                        } else {
                        }
                    } catch (Exception e) {
                        E.e(e);
                    }
                }
                dochod = wierszPkpir.getRazemdochod();
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
            WierszRyczalt wierszRyczalt = new WierszRyczalt(1, rok, mc, "dla DRA");
            for (Dok dokument : lista) {
                try {
                    List<KwotaKolumna1> szczegol = dokument.getListakwot1();
                    for (KwotaKolumna1 tmp : szczegol) {
                        String nazwakolumny = tmp.getNazwakolumny();
                        Double kwota = tmp.getNetto();
                        Double temp = 0.0;
                        switch (nazwakolumny) {
                            case "17%":
                                temp = wierszRyczalt.getKolumna_17i0() + kwota;
                                wierszRyczalt.setKolumna_17i0(temp);
                                break;
                            case "15%":
                                temp = wierszRyczalt.getKolumna_15i0() + kwota;
                                wierszRyczalt.setKolumna_15i0(temp);
                                break;
                            case "14%":
                                temp = wierszRyczalt.getKolumna_14i0() + kwota;
                                wierszRyczalt.setKolumna_14i0(temp);
                                break;
                            case "12.5%":
                                temp = wierszRyczalt.getKolumna_12i5() + kwota;
                                wierszRyczalt.setKolumna_12i5(temp);
                                break;
                            case "12%":
                                temp = wierszRyczalt.getKolumna_12i0() + kwota;
                                wierszRyczalt.setKolumna_12i0(temp);
                                break;
                            case "10%":
                                temp = wierszRyczalt.getKolumna_10i0() + kwota;
                                wierszRyczalt.setKolumna_10i0(temp);
                                break;
                            case "8.5%":
                                temp = wierszRyczalt.getKolumna_8i5() + kwota;
                                wierszRyczalt.setKolumna_8i5(temp);
                                break;
                            case "5.5%":
                                temp = wierszRyczalt.getKolumna_5i5() + kwota;
                                wierszRyczalt.setKolumna_5i5(temp);
                                break;
                            case "3%":
                                temp = wierszRyczalt.getKolumna_3i0() + kwota;
                                wierszRyczalt.setKolumna_3i0(temp);
                                break;
                            case "2%":
                                temp = wierszRyczalt.getKolumna_2i0() + kwota;
                                wierszRyczalt.setKolumna_2i0(temp);
                                break;
                        }
                    }
                } catch (Exception e) {
                    E.e(e);
                }
                przychod = wierszRyczalt.getRazem();
            }
             wiersz.setBrakdokumentow(false);
        } else {
            wiersz.setBrakdokumentow(true);
        }
        return przychod;
    }
    private static final String trescmaila = "<p> Dzień dobry</p> <p> Przesyłąmy informacje o naliczonych kwoty zobowiązań z tytułu ZUS I PIT-4</p> "
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
        if (wierszDRA != null && wierszDRA.getZusdra() != null && wierszDRA.getZusdra().getIx2Kwdozaplaty() != null) {
            try {
                Zusmail zusmail = null;
                if (wierszDRA.getZusmail() != null) {
                    if (wierszDRA.getZusmail().getId() != null && wierszDRA.getRok().equals(rok) && wierszDRA.getMc().equals(mc)) {
                        zusmail = wierszDRA.getZusmail();
                    } else {
                        zusmail = null;
                        for (Zusmail l : maile) {
                            if (l.getPodatnik().equals(wierszDRA.getPodatnik())) {
                                zusmail = l;
                                break;
                            }
                        }
                    }
                } else {
                    for (Zusmail l : maile) {
                        if (l.getPodatnik().equals(wierszDRA.getPodatnik())) {
                            zusmail = l;
                            break;
                        }
                    }
                }
                if (zusmail == null) {
                    zusmail = new Zusmail(wierszDRA.getPodatnik(), rok, mc);
                }
                zusmail.setZus(wierszDRA.getZusdra().getIx2Kwdozaplaty().doubleValue());
                zusmail.setPit4(wierszDRA.getPit4());
                zusmail.setPit8(null);
                double zus = 0;
                double pit4 = 0;
                double pit8 = 0;
                zus = zusmail.getZus() != null ? zusmail.getZus() : 0;
                //            pit4 = zusmail.getPit4()!= null ? zusmail.getPit4(): 0;
                //            pit8 = zusmail.getPit8()!= null ? zusmail.getPit8(): 0;//kom
                zusmail.setTytul(String.format("Taxman - zestawienie kwot ZUS/PIT4 za %s/%s", wierszDRA.getRok(), wierszDRA.getMc()));
                zusmail.setTresc(String.format(new Locale("pl_PL"), trescmaila, wierszDRA.getPodatnik().getPrintnazwa(), wierszDRA.getPodatnik().getNip(), wierszDRA.getRok(), wierszDRA.getMc(), zus, pit4, pit8));
                zusmail.setAdresmail(wierszDRA.getPodatnik().getEmail());
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

    public List<List<WierszDRA>> getMapa() {
        return mapa;
    }

    public void setMapa(List<List<WierszDRA>> mapa) {
        this.mapa = mapa;
    }

    public boolean isPokazzrobione() {
        return pokazzrobione;
    }

    public void setPokazzrobione(boolean pokazzrobione) {
        this.pokazzrobione = pokazzrobione;
    }

    public List<DRASumy> getDrasumy() {
        return drasumy;
    }

    public void setDrasumy(List<DRASumy> drasumy) {
        this.drasumy = drasumy;
    }

    public DRASumy getSelected1() {
        return selected1;
    }

    public void setSelected1(DRASumy selected1) {
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

    

    

    
    
}
