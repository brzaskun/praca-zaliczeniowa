/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.KsiegaBean;
import dao.DokDAO;
import dao.PodStawkiDAO;
import dao.PodatnikDAO;
import dao.PodatnikUdzialyDAO;
import dao.RyczDAO;
import dao.StrataDAO;
import dao.ZobowiazanieDAO;
import embeddable.Mce;
import embeddable.RyczaltPodatek;
import embeddable.ZestawienieRyczalt;
import entity.Dok;
import entity.KwotaKolumna1;
import entity.Pitpoz;
import entity.Podatnik;
import entity.PodatnikUdzialy;
import entity.Ryczpoz;
import entity.Strata;
import entity.StrataWykorzystanie;
import entity.Zobowiazanie;
import entity.Zusstawki;
import error.E;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
import msg.Msg;
import org.primefaces.PrimeFaces;
import pdf.PdfPIT28;
import pdf.PdfZestRok;
 import waluty.Z;

/**
 *
 * @author Osito
 */
@Named(value = "ZestawienieRyczaltView")
@ViewScoped
public class ZestawienieRyczaltView implements Serializable {
    //dane niezbedne do wyliczania pit
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
    Map<Integer,List<Double>> miesiace;
    private List<Dok> lista;
    private List<Ryczpoz> pobierzPity;
    private List<List> zebranieMcy;
    List<Double> Ipolrocze;
    List<Double> IIpolrocze;
    List<Double> rok;
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

    public ZestawienieRyczaltView() {
        miesiace = new HashMap<>();
        pobierzPity = Collections.synchronizedList(new ArrayList<>());
        zebranieMcy = Collections.synchronizedList(new ArrayList<>());
        listapit = Collections.synchronizedList(new ArrayList<>());
        listawybranychudzialowcow = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    public void init() { //E.m(this);
        if (wpisView.getPodatnikWpisu() != null && !wpisView.isKsiegaryczalt()) {
            for (int i = 0; i < 12; i++) {
                miesiace.put(i, nowalista());
            }
            pobierzPity = Collections.synchronizedList(new ArrayList<>());
            zebranieMcy = Collections.synchronizedList(new ArrayList<>());
            listapit = Collections.synchronizedList(new ArrayList<>());
            listawybranychudzialowcow = Collections.synchronizedList(new ArrayList<>());
            Podatnik pod = podatnikDAO.findByNazwaPelna(wpisView.getPodatnikWpisu());
            List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView);
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
               
                for (Dok dokument : lista) {
                    try {
                        List<KwotaKolumna1> szczegol = dokument.getListakwot1();
                        for (KwotaKolumna1 tmp : szczegol) {
                            Integer miesiac = Mce.getMiesiacToNumber().get(dokument.getPkpirM())-1;
                            String nazwakolumny = tmp.getNazwakolumny();
                            Double kwota = tmp.getNetto();
                            Double temp = 0.0;
                            switch (nazwakolumny) {
                                case "17%":
                                    temp = miesiace.get(miesiac).get(0) + kwota;
                                    miesiace.get(miesiac).set(0, temp);
                                    break;
                                case "15%":
                                    temp = miesiace.get(miesiac).get(1) + kwota;
                                    miesiace.get(miesiac).set(1, temp);
                                    break;
                                case "12.5%":
                                    temp = miesiace.get(miesiac).get(2) + kwota;
                                    miesiace.get(miesiac).set(2, temp);
                                    break;
                                case "10%":
                                    temp = miesiace.get(miesiac).get(3) + kwota;
                                    miesiace.get(miesiac).set(3, temp);
                                    break;
                                case "8.5%":
                                    temp = miesiace.get(miesiac).get(4) + kwota;
                                    miesiace.get(miesiac).set(4, temp);
                                    break;
                                case "5.5%":
                                    temp = miesiace.get(miesiac).get(5) + kwota;
                                    miesiace.get(miesiac).set(5, temp);
                                    break;
                               case "3%":
                                    temp = miesiace.get(miesiac).get(6) + kwota;
                                    miesiace.get(miesiac).set(6, temp);
                                    break;
                            }
                        }
                    } catch (Exception e) {
                        E.e(e);
                    }
                }
                for (int i = 0; i < 12; i++) {
                    zebranieMcy.add(miesiace.get(i));
                }
                Ipolrocze = nowalista();
                IIpolrocze = nowalista();
                rok = new ArrayList<>();
                for (int j = 0; j < 7; j++) {
                    for (int i = 0; i < 6; i++) {
                        double temp = Ipolrocze.get(j) + miesiace.get(i).get(j);
                        Ipolrocze.set(j, temp);
                    }
                }
                for (int j = 0; j < 7; j++) {
                    for (int i = 6; i < 12; i++) {
                        double temp = IIpolrocze.get(j) + miesiace.get(i).get(j);
                        IIpolrocze.set(j, temp);
                    }
                }
                for (int i = 0; i < 7; i++) {
                    rok.add(Ipolrocze.get(i) + IIpolrocze.get(i));
                }
            }
        }
    }
   
    private List<Double> nowalista() {
        List<Double> zwrot = Arrays.asList(new Double[7]);
         for (int i = 0; i < 7; i++) {
            zwrot.set(i, 0.0);
        }
        return zwrot;
    }
    
    //oblicze pit ryczałtowca  i wkleja go do biezacego Pitu w celu wyswietlenia, nie zapisuje
    public void obliczPit() {
        if (!wybranyudzialowiec.equals("wybierz osobe")) {
                Podatnik tmpP = podatnikDAO.findByNazwaPelna(wpisView.getPodatnikWpisu());
                List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView);
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
                it = selected.getZusparametr().iterator();
                if(zus51zreki==false){
                while (it.hasNext()) {
                    Zusstawki tmpX = (Zusstawki) it.next();
                    if (tmpX.getZusstawkiPK().getRok().equals(wpisView.getRokWpisu().toString())
                            && tmpX.getZusstawkiPK().getMiesiac().equals(wpisView.getMiesiacWpisu())) {
                        if (selected.isOdliczeniezus51() == true) {
                            if (tmpX.getZus51ch() != null && tmpX.getZus51ch() > 0.0) {
                                biezacyPit.setZus51(BigDecimal.valueOf(tmpX.getZus51ch()));
                            } else {
                                biezacyPit.setZus51(BigDecimal.valueOf(tmpX.getZus51bch()));
                            }
                        } else {
                            biezacyPit.setZus51(new BigDecimal(0));
                        }
                        if(zus52zreki==false){
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
        if (tmp.getNowewykorzystanie() != null) {
            for (StrataWykorzystanie s : tmp.getNowewykorzystanie()) {
                if (Integer.parseInt(s.getRokwykorzystania()) < wpisView.getRokWpisu()) {
                    sumabiezace += s.getKwotawykorzystania();
                }
            }
        } else {
            tmp.setNowewykorzystanie(new ArrayList<StrataWykorzystanie>());

        }
        zostalo += Z.z(tmp.getKwota() - tmp.getWykorzystano() - Z.z(sumabiezace));
        return zostalo;
    }
    
    
    public void zachowajPit() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        if (biezacyPit.getWynik() != null) {
            try {
                Ryczpoz find = pitDAO.find(biezacyPit.getPkpirR(), biezacyPit.getPkpirM(), biezacyPit.getPodatnik(), biezacyPit.getUdzialowiec());
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
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 12,5%", 0.125, miesiacint, 2));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 10%", 0.10, miesiacint, 3));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 8,5%", 0.085, miesiacint, 4));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 5,5%", 0.055, miesiacint, 5));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 3%", 0.03, miesiacint, 6));
        biezacyPit.setListapodatkow(podatkibiezace);
    }

    private RyczaltPodatek pobranieprzychodu(String opis, double stawka, int miesiac, int pozycja){
        BigDecimal suma = new BigDecimal(0);
        BigDecimal podatek = new BigDecimal(0);
        if (zebranieMcy!=null && zebranieMcy.size()>0) {
            suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(miesiac).get(pozycja).toString())));
            suma = suma.setScale(2, RoundingMode.HALF_EVEN);
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
            BigDecimal wynik = (new BigDecimal(p.getPrzychod())).subtract(new BigDecimal(p.getZmniejszenie()));
            Double udzial = Double.valueOf(biezacyPit.getUdzial())/100;
            wynik = wynik.multiply(new BigDecimal(udzial));
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
            List<ZestawienieRyczalt> lista = stworzliste();
            PdfZestRok.drukujRyczalt(wpisView, lista);
        } catch (Exception e) { E.e(e); 
            
        }
    }
    
    private List<ZestawienieRyczalt> stworzliste() {
        List<ZestawienieRyczalt> lista = Collections.synchronizedList(new ArrayList<>());
        lista.add(new ZestawienieRyczalt(1, "styczeń", miesiace.get(0)));
        lista.add(new ZestawienieRyczalt(1, "luty", miesiace.get(1)));
        lista.add(new ZestawienieRyczalt(1, "marzec", miesiace.get(2)));
        lista.add(new ZestawienieRyczalt(1, "kwiecień", miesiace.get(3)));
        lista.add(new ZestawienieRyczalt(1, "maj", miesiace.get(4)));
        lista.add(new ZestawienieRyczalt(1, "czerwiec", miesiace.get(5)));
        lista.add(new ZestawienieRyczalt(1, "I półrocze", Ipolrocze.get(0), Ipolrocze.get(1), Ipolrocze.get(2), Ipolrocze.get(3), Ipolrocze.get(4), Ipolrocze.get(5), Ipolrocze.get(6)));
        lista.add(new ZestawienieRyczalt(1, "lipiec", miesiace.get(6)));
        lista.add(new ZestawienieRyczalt(1, "sierpień", miesiace.get(7)));
        lista.add(new ZestawienieRyczalt(1, "wrzesień", miesiace.get(8)));
        lista.add(new ZestawienieRyczalt(1, "październik", miesiace.get(9)));
        lista.add(new ZestawienieRyczalt(1, "listopad", miesiace.get(10)));
        lista.add(new ZestawienieRyczalt(1, "grudzień", miesiace.get(11)));
        lista.add(new ZestawienieRyczalt(1, "II półrocze", IIpolrocze.get(0), IIpolrocze.get(1), IIpolrocze.get(2), IIpolrocze.get(3), IIpolrocze.get(4), IIpolrocze.get(5), IIpolrocze.get(6)));
        lista.add(new ZestawienieRyczalt(1, "rok", rok.get(0), rok.get(1), rok.get(2), rok.get(3), rok.get(4), rok.get(5), rok.get(6)));
        return lista;
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

    public List<Double> getStyczen() {
        return miesiace.get(0);
    }

    public List<Double> getLuty() {
        return miesiace.get(1);
    }

    public List<Double> getMarzec() {
        return miesiace.get(2);
    }

    public List<Double> getKwiecien() {
        return miesiace.get(3);
    }

    public List<Double> getMaj() {
        return miesiace.get(4);
    }

    public List<Double> getCzerwiec() {
        return miesiace.get(5);
    }

    public List<Double> getLipiec() {
        return miesiace.get(6);
    }

    public List<Double> getSierpien() {
        return miesiace.get(7);
    }

    public List<Double> getWrzesien() {
        return miesiace.get(8);
    }

    public List<Double> getPazdziernik() {
        return miesiace.get(9);
    }

    public List<Double> getListopad() {
        return miesiace.get(10);
    }

    public List<Double> getGrudzien() {
        return miesiace.get(11);
    }

    public List<Double> getIpolrocze() {
        return Ipolrocze;
    }

    public void setIpolrocze(List<Double> Ipolrocze) {
        this.Ipolrocze = Ipolrocze;
    }

    public List<Double> getIIpolrocze() {
        return IIpolrocze;
    }

    public void setIIpolrocze(List<Double> IIpolrocze) {
        this.IIpolrocze = IIpolrocze;
    }

    public List<Double> getRok() {
        return rok;
    }

    public void setRok(List<Double> rok) {
        this.rok = rok;
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

    public List<List> getZebranieMcy() {
        return zebranieMcy;
    }

    public void setZebranieMcy(List<List> zebranieMcy) {
        this.zebranieMcy = zebranieMcy;
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
