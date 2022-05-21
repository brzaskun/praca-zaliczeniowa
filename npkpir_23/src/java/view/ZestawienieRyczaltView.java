/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.KsiegaBean;
import dao.DokDAO;
import dao.FakturaDAO;
import dao.PodStawkiDAO;
import dao.PodatnikDAO;
import dao.PodatnikUdzialyDAO;
import dao.RyczDAO;
import dao.StrataDAO;
import dao.ZobowiazanieDAO;
import embeddable.Mce;
import embeddable.RyczaltPodatek;
import embeddable.WierszRyczalt;
import entity.Dok;
import entity.Faktura;
import entity.KwotaKolumna1;
import entity.Pitpoz;
import entity.Podatnik;
import entity.PodatnikUdzialy;
import entity.Ryczpoz;
import entity.Strata;
import entity.StrataWykorzystanie;
import entity.Zobowiazanie;
import entity.Zusstawkinew;
import error.E;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import msg.Msg;
import org.primefaces.PrimeFaces;
import pdf.PdfPIT28;
import pdf.PdfZestRok;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named(value = "zestawienieRyczaltView")
@ViewScoped
public class ZestawienieRyczaltView implements Serializable {
    //dane niezbedne do wyliczania pit
    private List<Double> wstyczen;
    private List<Double> wluty;
    private List<Double> wmarzec;
    private List<Double> wkwiecien;
    private List<Double> wmaj;
    private List<Double> wczerwiec;
    private List<Double> wlipiec;
    private List<Double> wsierpien;
    private List<Double> wwrzesien;
    private List<Double> wpazdziernik;
    private List<Double> wlistopad;
    private List<Double> wgrudzien;
    private List<Double> wIpolrocze;
    private List<Double> wIIpolrocze;
    private List<Double> wrok;
    private WierszRyczalt styczen;
    private WierszRyczalt luty;
    private WierszRyczalt marzec;
    private WierszRyczalt kwiecien;
    private WierszRyczalt maj;
    private WierszRyczalt czerwiec;
    private WierszRyczalt lipiec;
    private WierszRyczalt sierpien;
    private WierszRyczalt wrzesien;
    private WierszRyczalt pazdziernik;
    private WierszRyczalt listopad;
    private WierszRyczalt grudzien;
    private WierszRyczalt Ipolrocze;
    private WierszRyczalt IIpolrocze;
    private WierszRyczalt rok;
    private String wybranyudzialowiec;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private RyczDAO pitDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private StrataDAO strataDAO;
    //bieżący pit
    private Ryczpoz pitpoz;
    //sumowanie poprzednich pitów jeżeli są zachowane
    private Ryczpoz narPitpoz;
    //lista pitow
    private List<Ryczpoz> listapit;
    @Inject
    private WpisView wpisView;
    private List<Dok> lista;
    private List<Ryczpoz> pobierzPity;
    private List<WierszRyczalt> zebranieMcy;
    @Inject private Ryczpoz biezacyPit;
    @Inject private PodStawkiDAO podstawkiDAO;
    @Inject private ZobowiazanieDAO zobowiazanieDAO;
    private String wybranyprocent;
    private List<String> listawybranychudzialowcow;
     //z reki
    private boolean zus51zreki;
    private boolean zus52zreki;
    @Inject
    private PodatnikUdzialyDAO podatnikUdzialyDAO;
    @Inject
    private FakturaDAO fakturaDAO;
    private Podatnik taxman;

    public ZestawienieRyczaltView() {
        pobierzPity = Collections.synchronizedList(new ArrayList<>());
        zebranieMcy = Collections.synchronizedList(new ArrayList<>());
        listapit = Collections.synchronizedList(new ArrayList<>());
        listawybranychudzialowcow = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    public void init() { //E.m(this);
        taxman = podatnikDAO.findPodatnikByNIP("8511005008");
        if (wpisView.getPodatnikWpisu() != null && !wpisView.isKsiegaryczalt()) {
            styczen = new WierszRyczalt(1, wpisView.getRokWpisuSt(), "01", "styczeń");
            luty = new WierszRyczalt(2, wpisView.getRokWpisuSt(), "02", "luty");
            marzec = new WierszRyczalt(3, wpisView.getRokWpisuSt(), "03", "marzec");
            kwiecien = new WierszRyczalt(4, wpisView.getRokWpisuSt(), "04", "kwiecień");
            maj = new WierszRyczalt(5, wpisView.getRokWpisuSt(), "05", "maj");
            czerwiec = new WierszRyczalt(6, wpisView.getRokWpisuSt(), "06", "czerwiec");
            lipiec = new WierszRyczalt(7, wpisView.getRokWpisuSt(), "07", "lipiec");
            sierpien = new WierszRyczalt(8, wpisView.getRokWpisuSt(), "08", "sierpień");
            wrzesien = new WierszRyczalt(9, wpisView.getRokWpisuSt(), "09", "wrzesień");
            pazdziernik = new WierszRyczalt(10, wpisView.getRokWpisuSt(), "10", "październik");
            listopad = new WierszRyczalt(11, wpisView.getRokWpisuSt(), "11", "listopad");
            grudzien = new WierszRyczalt(12, wpisView.getRokWpisuSt(), "12", "grudzień");
            pobierzPity = Collections.synchronizedList(new ArrayList<>());
            zebranieMcy = Collections.synchronizedList(new ArrayList<>());
            listapit = Collections.synchronizedList(new ArrayList<>());
            listawybranychudzialowcow = Collections.synchronizedList(new ArrayList<>());
            Podatnik pod = podatnikDAO.findByNazwaPelna(wpisView.getPodatnikWpisu());
            List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView.getPodatnikObiekt());
            try {
                for (PodatnikUdzialy p : udzialy) {
                    listawybranychudzialowcow.add(p.getNazwiskoimie());

                }
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Nie uzupełniony wykaz udziałów", "formpit:messages");
            }
            try {
                lista = KsiegaBean.pobierzdokumentyRok(dokDAO, pod, wpisView.getRokWpisu(), wpisView.getMiesiacWpisu(), wpisView.getOdjakiegomcdok());
            } catch (Exception e) {
                E.e(e);
            }
            if (lista != null) {
                zebranieMcy.add(styczen);
                zebranieMcy.add(luty);
                zebranieMcy.add(marzec);
                zebranieMcy.add(kwiecien);
                zebranieMcy.add(maj);
                zebranieMcy.add(czerwiec);
                zebranieMcy.add(lipiec);
                zebranieMcy.add(sierpien);
                zebranieMcy.add(wrzesien);
                zebranieMcy.add(pazdziernik);
                zebranieMcy.add(listopad);
                zebranieMcy.add(grudzien);
                for (Dok dokument : lista) {
                    try {
                        List<KwotaKolumna1> szczegol = dokument.getListakwot1();
                        for (KwotaKolumna1 tmp : szczegol) {
                            Integer miesiac = Mce.getMiesiacToNumber().get(dokument.getPkpirM())-1;
                            WierszRyczalt miesiace = zebranieMcy.get(miesiac);
                            String nazwakolumny = tmp.getNazwakolumny();
                            Double kwota = tmp.getNetto();
                            Double temp = 0.0;
                            switch (nazwakolumny) {
                                case "17%":
                                    temp = miesiace.getKolumna_17i0() + kwota;
                                    miesiace.setKolumna_17i0(temp);
                                    break;
                                case "15%":
                                    temp = miesiace.getKolumna_15i0() + kwota;
                                    miesiace.setKolumna_15i0(temp);
                                    break;
                                case "14%":
                                    temp = miesiace.getKolumna_14i0()+ kwota;
                                    miesiace.setKolumna_14i0(temp);
                                    break;
                                case "12.5%":
                                    temp = miesiace.getKolumna_12i5() + kwota;
                                    miesiace.setKolumna_12i5(temp);
                                    break;
                                case "12%":
                                    temp = miesiace.getKolumna_12i0() + kwota;
                                    miesiace.setKolumna_12i0(temp);
                                    break;
                                case "10%":
                                    temp = miesiace.getKolumna_10i0() + kwota;
                                    miesiace.setKolumna_10i0(temp);
                                    break;
                                case "8.5%":
                                    temp = miesiace.getKolumna_8i5() + kwota;
                                    miesiace.setKolumna_8i5(temp);
                                    break;
                                case "5.5%":
                                    temp = miesiace.getKolumna_5i5() + kwota;
                                    miesiace.setKolumna_5i5(temp);
                                    break;
                               case "3%":
                                    temp = miesiace.getKolumna_3i0() + kwota;
                                    miesiace.setKolumna_3i0(temp);
                                    break;
                                case "2%":
                                    temp = miesiace.getKolumna_2i0() + kwota;
                                    miesiace.setKolumna_2i0(temp);
                                    break;
                            }
                        }
                    } catch (Exception e) {
                        E.e(e);
                    }
                }
                Ipolrocze = new WierszRyczalt(13, wpisView.getRokWpisuSt(), "13", "I półrocze");
                IIpolrocze = new WierszRyczalt(14, wpisView.getRokWpisuSt(), "14", "II półrocze");
                rok = new WierszRyczalt(15, wpisView.getRokWpisuSt(), "15", "rok");
                for (WierszRyczalt p : zebranieMcy) {
                    if (p.getId() < 7) {
                        Ipolrocze.dodaj(p);
                    } else {
                        IIpolrocze.dodaj(p);
                    }
                    rok.dodaj(p);
                }
            }
        wstyczen = new ArrayList<>();
        wluty = new ArrayList<>();
        wmarzec = new ArrayList<>();
        wkwiecien = new ArrayList<>();
        wmaj = new ArrayList<>();
        wczerwiec = new ArrayList<>();
        wlipiec = new ArrayList<>();
        wsierpien = new ArrayList<>();
        wwrzesien = new ArrayList<>();
        wpazdziernik = new ArrayList<>();
        wlistopad = new ArrayList<>();
        wgrudzien = new ArrayList<>();
        wIpolrocze = new ArrayList<>();
        wIIpolrocze = new ArrayList<>();
        wrok = new ArrayList<>();
        naniesnaliste(wstyczen, zebranieMcy.get(0));
        naniesnaliste(wluty, zebranieMcy.get(1));
        naniesnaliste(wmarzec, zebranieMcy.get(2));
        naniesnaliste(wkwiecien, zebranieMcy.get(3));
        naniesnaliste(wmaj, zebranieMcy.get(4));
        naniesnaliste(wczerwiec, zebranieMcy.get(5));
        naniesnaliste(wlipiec, zebranieMcy.get(6));
        naniesnaliste(wsierpien, zebranieMcy.get(7));
        naniesnaliste(wwrzesien, zebranieMcy.get(8));
        naniesnaliste(wpazdziernik, zebranieMcy.get(9));
        naniesnaliste(wlistopad, zebranieMcy.get(10));
        naniesnaliste(wgrudzien, zebranieMcy.get(11));
        naniesnaliste(wIpolrocze, Ipolrocze);
        naniesnaliste(wIIpolrocze, IIpolrocze);
        naniesnaliste(wrok, rok);
        }
    }
   
    private void naniesnaliste(List<Double> wstyczen, WierszRyczalt get) {
        wstyczen.add(get.getKolumna_17i0());
        wstyczen.add(get.getKolumna_15i0());
        wstyczen.add(get.getKolumna_14i0());
        wstyczen.add(get.getKolumna_12i5());
        wstyczen.add(get.getKolumna_12i0());
        wstyczen.add(get.getKolumna_10i0());
        wstyczen.add(get.getKolumna_8i5());
        wstyczen.add(get.getKolumna_5i5());
        wstyczen.add(get.getKolumna_3i0());
        wstyczen.add(get.getKolumna_2i0());
        wstyczen.add(get.getRazem());
    }
    
    //oblicze pit ryczałtowca  i wkleja go do biezacego Pitu w celu wyswietlenia, nie zapisuje
    public void obliczPit() {
        if (!wybranyudzialowiec.equals("wybierz osobe")) {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            String uzernazwa = principal.getName();
            if (!uzernazwa.equals("szef")) {
                List<Faktura> czywystawionofakture = fakturaDAO.findbyKontrahentNipRokMc(wpisView.getPodatnikObiekt().getNip(), taxman, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                if (czywystawionofakture==null||czywystawionofakture.isEmpty()) {
                    Msg.msg("e","Nie wystawiono faktury dla firmy. Nie można zakończyć miesiąca");
                    return;
                }
            }
                Podatnik tmpP = podatnikDAO.findByNazwaPelna(wpisView.getPodatnikWpisu());
                List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView.getPodatnikObiekt());
                for (PodatnikUdzialy p : udzialy) {
                    if (p.getNazwiskoimie().equals(wybranyudzialowiec)) {
                        wybranyprocent = p.getUdzial();
                        break;
                    }
                }
                biezacyPit.setPodatnik(wpisView.getPodatnikWpisu());
                biezacyPit.setPkpirR(wpisView.getRokWpisu().toString());
                biezacyPit.setPkpirM(wpisView.getMiesiacWpisu());
                obliczprzychod();
                wyciagnijprzychodsuma();
                double procent = Double.parseDouble(wybranyprocent) / 100;
                biezacyPit.setPrzychodyudzial(biezacyPit.getPrzychody().multiply(new BigDecimal(procent)));
                biezacyPit.setWynik(biezacyPit.getPrzychodyudzial());
                biezacyPit.setUdzialowiec(wybranyudzialowiec);
                biezacyPit.setUdzial(wybranyprocent);
                try {
                Podatnik selected = wpisView.getPodatnikObiekt();
                Iterator it;
                it = selected.getZusstawkinowe().iterator();
                    if (zus51zreki == false) {
                        while (it.hasNext()) {
                            Zusstawkinew tmpX = (Zusstawkinew) it.next();
                            if (tmpX.getRok().equals(wpisView.getRokWpisu().toString())
                                    && tmpX.getMiesiac().equals(wpisView.getMiesiacWpisu())) {
                                if (selected.isOdliczeniezus51() == true) {
                                    if (tmpX.getZus51ch() != 0.0) {
                                        biezacyPit.setZus51(BigDecimal.valueOf(tmpX.getZus51ch()));
                                    } else {
                                        biezacyPit.setZus51(BigDecimal.valueOf(tmpX.getZus51bch()));
                                    }
                                } else {
                                    biezacyPit.setZus51(new BigDecimal(0));
                                }
                                if (zus52zreki == false) {
                                    biezacyPit.setZus52(BigDecimal.valueOf(tmpX.getZus52odl()));
                                }
                                break;
                            }
                        }
                    }
                } catch (Exception e) { E.e(e); 
                    Msg.msg("e", "Brak wpisanych stawek ZUS-51,52 indywidualnych dla danego klienta. Jeżeli ZUS 51 nie ma być odliczany, sprawdź czy odpowiednia opcja jest wybrana w ustwieniach klienta");
                    biezacyPit = new Ryczpoz();
                    wybranyudzialowiec = "wybierz osobe";
                    return;
                }
             try {
                rozliczstrate();
                obliczpodatek();
                if(biezacyPit.getZus52() != null) {
                    if (biezacyPit.getPodatek().subtract(biezacyPit.getZus52()).signum()==1){
                        BigDecimal tmpX = biezacyPit.getPodatek().subtract(biezacyPit.getZus52());
                        tmpX = tmpX.setScale(0, RoundingMode.HALF_EVEN);
                        biezacyPit.setNaleznazal(tmpX);
                    } else {
                        biezacyPit.setNaleznazal(BigDecimal.ZERO);
                    }
                } else {
                    biezacyPit.setNaleznazal(biezacyPit.getPodatek());
                }
                if (biezacyPit.getNaleznazal().compareTo(BigDecimal.ZERO) == 1) {
                    biezacyPit.setDozaplaty(biezacyPit.getNaleznazal());
                } else {
                    biezacyPit.setDozaplaty(BigDecimal.ZERO);
                }
            } catch (Exception e) { E.e(e); 
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Brak wprowadzonych stawek podatkowych na dany rok! Nie można przeliczyć ryczałtu za okres ", biezacyPit.getPkpirM());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                biezacyPit = new Ryczpoz();
                wybranyudzialowiec = "wybierz osobe";
            }
            try {
                Zobowiazanie data = zobowiazanieDAO.find(biezacyPit.getPkpirR(), biezacyPit.getPkpirM());
                biezacyPit.setTerminwplaty(data.getZobowiazaniePK().getRok() + "-" + data.getZobowiazaniePK().getMc() + "-" + data.getPitday());
            } catch (Exception e) { E.e(e); 
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Brak wprowadzonych dat płatności zobowiazan  w danym okresie! Nie można przeliczyć ryczałtu", biezacyPit.getPkpirM());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                biezacyPit = new Ryczpoz();
                wybranyudzialowiec = "wybierz osobe";
            }
            Msg.msg("Przeliczono PIT");
        }
    }

    private void rozliczstrate() {
        List<Strata> straty = strataDAO.findPodatnik(wpisView.getPodatnikObiekt());
        double sumastrat = 0.0;
        try {
            for (Strata p : straty) {
                double zostalo = wyliczStrataZostalo(p);
                double wyliczmaks = zostalo - p.getPolowakwoty();
                if (wyliczmaks > 0) {
                    sumastrat += p.getPolowakwoty();
                } else {
                    sumastrat += zostalo;
                }
            }
            BigDecimal wynikpozus = BigDecimal.ZERO;
            if (biezacyPit.getZus51() != null) {
                wynikpozus = biezacyPit.getWynik().subtract(biezacyPit.getZus51());
            } else {
                wynikpozus = biezacyPit.getWynik();
            }
            if (wynikpozus.signum() == 1) {
                BigDecimal stratadoujecia = wynikpozus.subtract(new BigDecimal(sumastrat));
                if (stratadoujecia.signum() == -1) {
                    biezacyPit.setStrata(wynikpozus);
                } else {
                    biezacyPit.setStrata(new BigDecimal(sumastrat));
                }
            } else {
                biezacyPit.setStrata(BigDecimal.ZERO);
            }
        } catch (Exception e) {
            E.e(e);
            biezacyPit.setStrata(BigDecimal.ZERO);
        }
    }

    //wyliczenie niezbedne przy wracaniu do historycznych pitow pojedynczo dla kazdego pitu
    private double wyliczStrataZostalo(Strata tmp) {
        double zostalo = 0.0;
        double sumabiezace = 0.0;
        if (tmp.getListawykorzystanie() != null) {
            for (StrataWykorzystanie s : tmp.getListawykorzystanie()) {
                if (Integer.parseInt(s.getRok()) < wpisView.getRokWpisu()) {
                    sumabiezace += s.getKwotawykorzystania();
                }
            }
        } else {
            tmp.setListawykorzystanie(new ArrayList<StrataWykorzystanie>());

        }
        zostalo += Z.z(tmp.getKwota() - tmp.getWykorzystano() - Z.z(sumabiezace));
        return zostalo;
    }
    
    
    public void zachowajPit() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        if (biezacyPit.getWynik() != null) {
            try {
                Ryczpoz find = pitDAO.findByUdzialowiec(biezacyPit.getPkpirR(), biezacyPit.getPkpirM(), biezacyPit.getPodatnik(), biezacyPit.getUdzialowiec());
                pitDAO.remove(find);
                pitDAO.create(biezacyPit);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edytowano PIT " + biezacyPit.getUdzialowiec() + " za m-c:" + biezacyPit.getPkpirM(), null);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception e) { E.e(e); 
                pitDAO.create(biezacyPit);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Zachowano PIT " + biezacyPit.getUdzialowiec() + " za m-c:" + biezacyPit.getPkpirM(), null);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie można zachować. PIT nie wypełniony", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

     public void zachowajPit13() {
        biezacyPit.setPkpirM("13");
        zachowajPit();
    }
     
      public void aktualizujPIT(AjaxBehaviorEvent e) {
        aktualizuj();
        PrimeFaces.current().ajax().update("formpit1");
        wybranyudzialowiec = "wybierz osobe";
        Msg.msg("i", "Zmieniono miesiąc obrachunkowy.");
    }
    
    private void aktualizuj(){
        wpisView.naniesDaneDoWpis();
    }

    

    private void obliczprzychod() {
        List<RyczaltPodatek> podatkibiezace = Collections.synchronizedList(new ArrayList<>());
        String selekcja = wpisView.getMiesiacWpisu();
        int miesiacint = Mce.getMiesiacToNumber().get(selekcja)-1  ;
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 17%", 0.17, miesiacint, 0));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 15%", 0.15, miesiacint, 1));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 14%", 0.14, miesiacint, 2));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 12,5%", 0.125, miesiacint, 3));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 12%", 0.12, miesiacint, 4));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 10%", 0.10, miesiacint, 5));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 8,5%", 0.085, miesiacint, 6));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 5,5%", 0.055, miesiacint, 7));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 3%", 0.03, miesiacint, 8));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 2%", 0.02, miesiacint, 9));
        biezacyPit.setListapodatkow(podatkibiezace);
    }

    private RyczaltPodatek pobranieprzychodu(String opis, double stawka, int miesiac, int pozycja){
        BigDecimal suma = new BigDecimal(0);
        BigDecimal podatek = new BigDecimal(0);
        if (zebranieMcy!=null && zebranieMcy.size()>0) {
            switch (pozycja){
                case 0:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_17i0()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 1:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_15i0()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 2:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_14i0()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 3:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_12i5()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 4:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_12i0()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 5:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_10i0()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 6:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_8i5()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 7:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_5i5()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 8:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_3i0()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 9:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_2i0()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 10:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getRazem()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                    
            }
        }
        RyczaltPodatek podtk = new RyczaltPodatek();
        podtk.setOpis(opis);
        podtk.setStawka(stawka);
        podtk.setPrzychod(suma.doubleValue());
        podtk.setUdzialprocentowy(0.0);
        podtk.setPodstawa(0.0);
        podtk.setZmniejszenie(0.0);
        podtk.setPodatek(0.0);
        return podtk;
    }
    
    private void wyciagnijprzychodsuma(){
         List<RyczaltPodatek> podatkibiezace = Collections.synchronizedList(new ArrayList<>());
         BigDecimal suma = new BigDecimal(0);
         podatkibiezace = biezacyPit.getListapodatkow();
         for(RyczaltPodatek p : podatkibiezace){
             suma = suma.add(new BigDecimal(p.getPrzychod()));
         }
         biezacyPit.setPrzychody(suma);
    }
    
    private void obliczpodatek() {
        wyliczprocent(biezacyPit.getPrzychody());
        wyliczodliczenieZmniejszenia();
        wyliczpodatek();
    }
    
    private void wyliczprocent(BigDecimal suma){
        try{
        for(RyczaltPodatek p : biezacyPit.getListapodatkow()){
            BigDecimal wartosc = new BigDecimal(p.getPrzychod());
            BigDecimal procent = wartosc.divide(suma, 20, RoundingMode.HALF_EVEN);
            procent = procent.setScale(4, RoundingMode.HALF_EVEN);
            p.setUdzialprocentowy(procent.doubleValue());
        }
        } catch (Exception e) { E.e(e); 
           FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Brak przychodow w miesiacu", "");
           FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    private void wyliczodliczenieZmniejszenia(){
        for(RyczaltPodatek p : biezacyPit.getListapodatkow()){
            BigDecimal wartosc = new BigDecimal(p.getUdzialprocentowy());
            BigDecimal tmp = new BigDecimal(0.0);
            try {
                BigDecimal zus51 = wartosc.multiply(biezacyPit.getZus51());
                zus51 = zus51.setScale(2, RoundingMode.HALF_EVEN);
                tmp = zus51;
            } catch (Exception e1) {
                
            }
            try {
                BigDecimal strata = wartosc.multiply(biezacyPit.getStrata());
                strata = strata.setScale(2, RoundingMode.HALF_EVEN);
                tmp = tmp.add(strata);
                p.setZmniejszenie(tmp.doubleValue());
            } catch (Exception e1) {
                
            }
        }
    }
    
    private void wyliczpodatek() {
        BigDecimal podateksuma = new BigDecimal(BigInteger.ZERO);
        BigDecimal podstawasuma = new BigDecimal(BigInteger.ZERO);
        for (RyczaltPodatek p : biezacyPit.getListapodatkow()) {
            BigDecimal wynik = (new BigDecimal(p.getPrzychod()));
            Double udzial = Double.valueOf(biezacyPit.getUdzial())/100;
            wynik = wynik.multiply(new BigDecimal(udzial));
            wynik = wynik.subtract(new BigDecimal(p.getZmniejszenie()));
            wynik = wynik.setScale(0, RoundingMode.HALF_EVEN);
            podstawasuma = podstawasuma.add(wynik);
            p.setPodstawa(wynik.doubleValue());
            BigDecimal podatek = wynik.multiply(new BigDecimal(p.getStawka()));
            podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
            podateksuma = podateksuma.add(podatek);
            p.setPodatek(podatek.doubleValue());
        }
        biezacyPit.setPodstawa(podstawasuma);
        biezacyPit.setPodatek(podateksuma);
    }

    public void pobierzPity() {
        try {
            pobierzPity.addAll(pitDAO.findAll());
        } catch (Exception e) { E.e(e); 
        }
        narPitpoz = new Ryczpoz();
        int index = 0;
        Iterator it;
        it = pobierzPity.iterator();
        while (it.hasNext()) {
            Pitpoz tmpX = (Pitpoz) it.next();
            if (tmpX.getPkpirR().equals(wpisView.getRokWpisu().toString())
                    && tmpX.getPkpirM().equals(wpisView.getMiesiacWpisu())) {
                index = tmpX.getId() - 1;
                break;
            }
        }
        //narPitpoz = pobierzPity.get(Mce.getMapamcyX().get(wpisView.getMiesiacWpisu()));
        narPitpoz = pobierzPity.get(index);
        biezacyPit = narPitpoz;
    }
    
    public void drukujRyczalt() {
        try {
            PdfPIT28.drukuj(biezacyPit, wpisView, podatnikDAO);
        } catch (Exception e) { E.e(e); 
            
        }
    }
   
    public void drukujPodsumowanieRoczne() {
        try {
            zebranieMcy.add(Ipolrocze);
            zebranieMcy.add(IIpolrocze);
            zebranieMcy.add(rok);
            PdfZestRok.drukujRyczalt(wpisView, zebranieMcy);
        } catch (Exception e) { E.e(e); 
            
        }
    }
    
   
    

    public DokDAO getDokDAO() {
        return dokDAO;
    }

    public void setDokDAO(DokDAO dokDAO) {
        this.dokDAO = dokDAO;
    }

    public RyczDAO getPitDAO() {
        return pitDAO;
    }

    public void setRyczDAO(RyczDAO pitDAO) {
        this.pitDAO = pitDAO;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Dok> getLista() {
        return lista;
    }

    public void setLista(List<Dok> lista) {
        this.lista = lista;
    }

    public List<Double> getWstyczen() {
        return wstyczen;
    }

    public void setWstyczen(List<Double> wstyczen) {
        this.wstyczen = wstyczen;
    }

    public List<Double> getWluty() {
        return wluty;
    }

    public void setWluty(List<Double> wluty) {
        this.wluty = wluty;
    }

    public List<Double> getWmarzec() {
        return wmarzec;
    }

    public void setWmarzec(List<Double> wmarzec) {
        this.wmarzec = wmarzec;
    }

    public List<Double> getWkwiecien() {
        return wkwiecien;
    }

    public void setWkwiecien(List<Double> wkwiecien) {
        this.wkwiecien = wkwiecien;
    }

    public List<Double> getWmaj() {
        return wmaj;
    }

    public void setWmaj(List<Double> wmaj) {
        this.wmaj = wmaj;
    }

    public List<Double> getWczerwiec() {
        return wczerwiec;
    }

    public void setWczerwiec(List<Double> wczerwiec) {
        this.wczerwiec = wczerwiec;
    }

    public List<Double> getWlipiec() {
        return wlipiec;
    }

    public void setWlipiec(List<Double> wlipiec) {
        this.wlipiec = wlipiec;
    }

    public List<Double> getWsierpien() {
        return wsierpien;
    }

    public void setWsierpien(List<Double> wsierpien) {
        this.wsierpien = wsierpien;
    }

    public List<Double> getWwrzesien() {
        return wwrzesien;
    }

    public void setWwrzesien(List<Double> wwrzesien) {
        this.wwrzesien = wwrzesien;
    }

    public List<Double> getWpazdziernik() {
        return wpazdziernik;
    }

    public void setWpazdziernik(List<Double> wpazdziernik) {
        this.wpazdziernik = wpazdziernik;
    }

    public List<Double> getWlistopad() {
        return wlistopad;
    }

    public void setWlistopad(List<Double> wlistopad) {
        this.wlistopad = wlistopad;
    }

    public List<Double> getWgrudzien() {
        return wgrudzien;
    }

    public void setWgrudzien(List<Double> wgrudzien) {
        this.wgrudzien = wgrudzien;
    }

    public List<Double> getwIpolrocze() {
        return wIpolrocze;
    }

    public void setwIpolrocze(List<Double> wIpolrocze) {
        this.wIpolrocze = wIpolrocze;
    }

    public List<Double> getwIIpolrocze() {
        return wIIpolrocze;
    }

    public void setwIIpolrocze(List<Double> wIIpolrocze) {
        this.wIIpolrocze = wIIpolrocze;
    }

    public List<Double> getWrok() {
        return wrok;
    }

    public void setWrok(List<Double> wrok) {
        this.wrok = wrok;
    }

    public WierszRyczalt getWrzesien() {
        return wrzesien;
    }

    public void setWrzesien(WierszRyczalt wrzesien) {
        this.wrzesien = wrzesien;
    }

   

    public Ryczpoz getRyczpoz() {
        return pitpoz;
    }

    public void setRyczpoz(Ryczpoz pitpoz) {
        this.pitpoz = pitpoz;
    }

    public Ryczpoz getNarRyczpoz() {
        return narPitpoz;
    }

    public void setNarRyczpoz(Ryczpoz narRyczpoz) {
        this.narPitpoz = narRyczpoz;
    }

    public List<Ryczpoz> getPobierzPity() {
        return pobierzPity;
    }

    public void setPobierzPity(List<Ryczpoz> pobierzPity) {
        this.pobierzPity = pobierzPity;
    }

   
    public Ryczpoz getBiezacyPit() {
        return biezacyPit;
    }

    public void setBiezacyPit(Ryczpoz biezacyPit) {
        this.biezacyPit = biezacyPit;
    }

    public PodatnikDAO getPodatnikDAO() {
        return podatnikDAO;
    }

    public void setPodatnikDAO(PodatnikDAO podatnikDAO) {
        this.podatnikDAO = podatnikDAO;
    }

    public PodStawkiDAO getPodstawkiDAO() {
        return podstawkiDAO;
    }

    public void setPodstawkiDAO(PodStawkiDAO podstawkiDAO) {
        this.podstawkiDAO = podstawkiDAO;
    }

    public ZobowiazanieDAO getZobowiazanieDAO() {
        return zobowiazanieDAO;
    }

    public void setZobowiazanieDAO(ZobowiazanieDAO zobowiazanieDAO) {
        this.zobowiazanieDAO = zobowiazanieDAO;
    }

    public String getWybranyudzialowiec() {
        return wybranyudzialowiec;
    }

    public void setWybranyudzialowiec(String wybranyudzialowiec) {
        this.wybranyudzialowiec = wybranyudzialowiec;
    }

   

    public String getWybranyprocent() {
        return wybranyprocent;
    }

    public void setWybranyprocent(String wybranyprocent) {
        this.wybranyprocent = wybranyprocent;
    }

    public List<String> getListawybranychudzialowcow() {
        return listawybranychudzialowcow;
    }

    public void setListawybranychudzialowcow(List<String> listawybranychudzialowcow) {
        this.listawybranychudzialowcow = listawybranychudzialowcow;
    }

    public boolean isZus51zreki() {
        return zus51zreki;
    }

    public void setZus51zreki(boolean zus51zreki) {
        this.zus51zreki = zus51zreki;
    }

    public boolean isZus52zreki() {
        return zus52zreki;
    }

    public void setZus52zreki(boolean zus52zreki) {
        this.zus52zreki = zus52zreki;
    }

    

    
    
}
