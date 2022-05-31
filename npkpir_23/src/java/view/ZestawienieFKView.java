/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansPIT.WyliczPodatekZasadyOgolne;
import dao.DokDAO;
import dao.PitDAO;
import dao.PodStawkiDAO;
import dao.PodatnikDAO;
import dao.PodatnikOpodatkowanieDAO;
import dao.PodatnikUdzialyDAO;
import dao.StrataDAO;
import dao.WynikFKRokMcDAO;
import dao.ZobowiazanieDAO;
import embeddable.Kwartaly;
import embeddable.Mce;
import entity.Dok;
import entity.Pitpoz;
import entity.Podatnik;
import entity.PodatnikUdzialy;
import entity.Podstawki;
import entity.Strata;
import entity.StrataWykorzystanie;
import entity.Zobowiazanie;
import entity.Zusstawkinew;
import entityfk.WynikFKRokMc;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.PrimeFaces;
 import pdf.PdfPIT5;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ZestawienieFKView implements Serializable {
    private static final long serialVersionUID = 1L;
    //dane niezbedne do wyliczania pit
    private String wybranyudzialowiec;
    private String wybranyudzialowiecopodatkowanie;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private PitDAO pitDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private StrataDAO strataDAO;
    @Inject
    private WynikFKRokMcDAO wynikFKRokMcDAO;
    //bieżący pit
    private Pitpoz pitpoz;
    //sumowanie poprzednich pitów jeżeli są zachowane
    private Pitpoz narPitpoz;
    @Inject
    private WpisView wpisView;
    @Inject
    private RemanentView remanentView;
    private List<Dok> lista;
    private List<Pitpoz> pobierzPity;
    @Inject
    private Pitpoz biezacyPit;
    @Inject
    private PodStawkiDAO podstawkiDAO;
    @Inject
    private ZobowiazanieDAO zobowiazanieDAO;
    private String wybranyprocent;
    private List<String> listawybranychudzialowcow;
    //z reki
    private boolean zus51zreki;
    private boolean zus52zreki;
    private boolean pierwszypitwroku;
    private boolean pierwszypitwrokuzaznacz;
    @Inject
    private PodatnikUdzialyDAO podatnikUdzialyDAO;
    @Inject
    private PodatnikOpodatkowanieDAO podatnikOpodatkowanieDDAO;
    private String komunikatblad;

    private int flaga = 0;

    public ZestawienieFKView() {
    }

    @PostConstruct
    public void init() { //E.m(this);
        if (wpisView.getPodatnikWpisu() != null && wpisView.isKsiegaryczalt()) {
            listawybranychudzialowcow = Collections.synchronizedList(new ArrayList<>());
            try {
                List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView.getPodatnikObiekt());
                for (PodatnikUdzialy p : udzialy) {
                    listawybranychudzialowcow.add(p.getNazwiskoimie());
                }
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Nie uzupełnione parametry podatnika", "formpit:messages");
            }
        }
        //sprawa nie dokonczona wylaczam
//        if (listawybranychudzialowcow.size() == 1) {
//            wybranyudzialowiec = listawybranychudzialowcow.get(0);
//            obliczPit();
//        }
    }

    
    //oblicze pit i wkleja go do biezacego Pitu w celu wyswietlenia, nie zapisuje
    public void obliczPit() {
        komunikatblad = null;
        if (listawybranychudzialowcow.size() == 1) {
            wybranyudzialowiec = listawybranychudzialowcow.get(0);
        }
        if (wybranyudzialowiec.equals("wybierz osobe")) {
            Msg.msg("e", "Nie wybrałeś podatnika");
        } else {
            if (pierwszypitwrokuzaznacz == false) {
                if (sprawdzczyjestpitwpoprzednimmiesiacu() != 0) {
                    return;
                }
            }
            if (flaga == 0) {
                List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView.getPodatnikObiekt());
                for (PodatnikUdzialy p : udzialy) {
                    if (p.getNazwiskoimie().equals(wybranyudzialowiec)) {
                        wybranyprocent = p.getUdzial();
                        wybranyudzialowiecopodatkowanie = p.getOpodatkowanie();
                        break;
                    }
                }
                biezacyPit.setPodatnik(wpisView.getPodatnikWpisu());
                biezacyPit.setPodatnik1(wpisView.getPodatnikObiekt());
                biezacyPit.setPkpirR(wpisView.getRokWpisuSt());
                biezacyPit.setPkpirM(wpisView.getMiesiacWpisu());
                WynikFKRokMc znalezione = wynikFKRokMcDAO.findWynikFKPodatnikRokUdzialowiec(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), wybranyudzialowiec);
                biezacyPit.setPrzychody(new BigDecimal(znalezione.getPrzychodyPodatkowe()));
                biezacyPit.setPrzychodyudzial(biezacyPit.getPrzychody());
                biezacyPit.setKoszty(new BigDecimal(znalezione.getKosztyPodatkowe()));
                if (wpisView.getMiesiacWpisu().equals("12")) {
                    BigDecimal roznicaremanentow = new BigDecimal(remanentView.getRoznica());
                    biezacyPit.setRemanent(roznicaremanentow);
                    BigDecimal kosztypokorekcie = biezacyPit.getKoszty().add(roznicaremanentow);
                    biezacyPit.setKosztyudzial(kosztypokorekcie);
                } else {
                    biezacyPit.setKosztyudzial(biezacyPit.getKoszty());
                }
                biezacyPit.setWynik(biezacyPit.getPrzychodyudzial().subtract(biezacyPit.getKosztyudzial()));
                biezacyPit.setUdzialowiec(wybranyudzialowiec);
                biezacyPit.setUdzial(wybranyprocent);
                Podatnik selected = wpisView.getPodatnikObiekt();
                Pitpoz sumapoprzednichmcy;
                try {
                    if (selected.isOdliczeniezus51() == true && zus51zreki == false) {
                        biezacyPit.setZus51(pobierzZUS51());
                    } else {
                        if (biezacyPit.getWynik() != null && biezacyPit.getZus51() != null && biezacyPit.getZus51().compareTo(biezacyPit.getWynik()) == 1) {
                            biezacyPit.setZus51(biezacyPit.getWynik());
                        }
                    }
                    if (selected.isOdliczeniezus52() == true && zus52zreki == false) {
                        biezacyPit.setZus52(pobierzZUS52());
                    }
                    sumapoprzednichmcy = skumulujpity(biezacyPit.getPkpirM(), wybranyudzialowiec);
                    if (selected.isOdliczeniezus51() == true && pierwszypitwroku == false && zus51zreki == false) {
                        biezacyPit.setZus51(biezacyPit.getZus51().add(sumapoprzednichmcy.getZus51()));
                    }
                    rozliczstrate();
                    BigDecimal tmp = biezacyPit.getWynik().subtract(biezacyPit.getStrata());
                    if (biezacyPit.getZus51() != null) {
                        tmp = tmp.subtract(biezacyPit.getZus51());
                    }
                    tmp = tmp.setScale(0, RoundingMode.HALF_EVEN);
                    if (tmp.signum() == -1) {
                        biezacyPit.setPodstawa(BigDecimal.ZERO);
                    } else {
                        //wyliczenie podatku poczatek
                        biezacyPit.setPodstawa(tmp);
                    }
                } catch (Exception e) {
                    E.e(e);
                    komunikatblad = "Brak wpisanych stawek ZUS-51,52 indywidualnych dla danego klienta. Jeżeli ZUS 51 nie ma być odliczany, sprawdź czy odpowiednia opcja jest wybrana w ustwieniach klienta";
                    Msg.msg("e", "Brak wpisanych stawek ZUS-51,52 indywidualnych dla danego klienta. Jeżeli ZUS 51 nie ma być odliczany, sprawdź czy odpowiednia opcja jest wybrana w ustwieniach klienta");
                    biezacyPit = new Pitpoz();
                    wybranyudzialowiec = "wybierz osobe";
                    pierwszypitwroku = false;
                    pierwszypitwrokuzaznacz = false;
                    PrimeFaces.current().ajax().update("formpit:");
                    return;
                }
                Podstawki skalaPodatkowaZaDanyRok;
                try {
                    skalaPodatkowaZaDanyRok = podstawkiDAO.find(Integer.parseInt(biezacyPit.getPkpirR()));
                } catch (Exception e) {
                    E.e(e);
                    biezacyPit = new Pitpoz();
                    wybranyudzialowiec = "wybierz osobe";
                    pierwszypitwroku = false;
                    pierwszypitwrokuzaznacz = false;
                    komunikatblad = "Brak wprowadzonej skali opodatkowania dla wszystkich podatników na obecny rok. Przerywam wyliczanie PIT-u";
                    Msg.msg("e", "Brak wprowadzonej skali opodatkowania dla wszystkich podatników na obecny rok. Przerywam wyliczanie PIT-u");
                    return;
                }
               // String opodatkowanie = podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRok(wpisView).getFormaopodatkowania();
                //String rodzajop = opodatkowanie;
                Double stawka = 0.0;
                BigDecimal podatek = BigDecimal.ZERO;
                BigDecimal dochód = biezacyPit.getPodstawa();
                BigDecimal przychody = biezacyPit.getPrzychody();
                try {
                    switch (wybranyudzialowiecopodatkowanie) {
                        case "zasady ogólne":
                            podatek = WyliczPodatekZasadyOgolne.wyliczopodatek(skalaPodatkowaZaDanyRok, dochód);
                            break;
                        case "zasady ogólne bez VAT":
                            podatek = WyliczPodatekZasadyOgolne.wyliczopodatek(skalaPodatkowaZaDanyRok, dochód);
                            break;
                        case "podatek liniowy":
                            stawka = skalaPodatkowaZaDanyRok.getStawkaliniowy();
                            podatek = (dochód.multiply(BigDecimal.valueOf(stawka)));
                            podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                            break;
                        case "podatek liniowy bez VAT":
                            stawka = skalaPodatkowaZaDanyRok.getStawkaliniowy();
                            podatek = (dochód.multiply(BigDecimal.valueOf(stawka)));
                            podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                            break;
                        case "ryczałt":
                            stawka = skalaPodatkowaZaDanyRok.getStawkaryczalt1();
                            podatek = (przychody.multiply(BigDecimal.valueOf(stawka)));
                            podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                            break;
                        case "ryczałt bez VAT":
                            stawka = skalaPodatkowaZaDanyRok.getStawkaryczalt1();
                            podatek = (przychody.multiply(BigDecimal.valueOf(stawka)));
                            podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                            break;
                    }
                } catch (Exception e) {
                    E.e(e);
                    komunikatblad = "Brak wprowadzonych terminów płatności podatków w danym okresie rozliczeniowym! Nie można przeliczyć PIT-u";
                    Msg.msg("e", "Brak wprowadzonego rodzaju opodatkowania dla danego podatnika!! Nie można przeliczyć PIT za: " + biezacyPit.getPkpirM());
                    biezacyPit = new Pitpoz();
                    wybranyudzialowiec = "wybierz osobe";
                    pierwszypitwroku = false;
                    pierwszypitwrokuzaznacz = false;
                    return;
                }
                if (podatek.signum() == 1) {
                    biezacyPit.setPodatek(podatek);
                } else {
                    biezacyPit.setPodatek(BigDecimal.ZERO);
                }
                if (zus52zreki == false) {
                    biezacyPit.setZus52(pobierzZUS52());
                    biezacyPit.setZus52(biezacyPit.getZus52().add(sumapoprzednichmcy.getZus52()));
                }
                BigDecimal tmpX = podatek.subtract(biezacyPit.getZus52());
                tmpX = tmpX.setScale(0, RoundingMode.HALF_EVEN);
                if (tmpX.signum() == -1) {
                    biezacyPit.setPododpoczrok(BigDecimal.ZERO);
                } else {
                    biezacyPit.setPododpoczrok(tmpX);
                }
                //wyliczenie podatku koniec

                biezacyPit.setNalzalodpoczrok(sumapoprzednichmcy.getNalzalodpoczrok());
                if (biezacyPit.getPododpoczrok().subtract(biezacyPit.getNalzalodpoczrok()).signum() == 1) {
                    biezacyPit.setNaleznazal(biezacyPit.getPododpoczrok().subtract(biezacyPit.getNalzalodpoczrok()));
                } else {
                    biezacyPit.setNaleznazal(BigDecimal.ZERO);
                }
                if (biezacyPit.getNaleznazal().compareTo(BigDecimal.ZERO) == 1) {
                    biezacyPit.setDozaplaty(biezacyPit.getNaleznazal());
                } else {
                    biezacyPit.setDozaplaty(BigDecimal.ZERO);
                }
                try {
                    Zobowiazanie data = zobowiazanieDAO.find(biezacyPit.getPkpirR(), biezacyPit.getPkpirM());
                    biezacyPit.setTerminwplaty(data.getZobowiazaniePK().getRok() + "-" + data.getZobowiazaniePK().getMc() + "-" + data.getPitday());
                    pierwszypitwroku = false;
                    pierwszypitwrokuzaznacz = false;
                } catch (Exception e) {
                    E.e(e);
                    komunikatblad = "Brak wprowadzonych terminów płatności podatków w danym okresie rozliczeniowym! Nie można przeliczyć PIT-u";
                    Msg.msg("e", "Brak wprowadzonych terminów płatności podatków w danym okresie rozliczeniowym! Nie można przeliczyć PIT-u");
                    biezacyPit = new Pitpoz();
                    wybranyudzialowiec = "wybierz osobe";
                    pierwszypitwroku = false;
                    pierwszypitwrokuzaznacz = false;
                }
                Msg.msg("Przeliczono PIT");
            }
        }
    }

    private BigDecimal pobierzZUS51() {
        Podatnik p = wpisView.getPodatnikObiekt();
        Iterator it;
        it = p.getZusstawkinowe().iterator();
        double suma51 = 0;
        while (it.hasNext()) {
            Zusstawkinew tmpX = (Zusstawkinew) it.next();
            if (wpisView.isMc0kw1()) {
                List<String> miesiaceWkwartale = Kwartaly.mctoMcewKw(wpisView.getMiesiacWpisu());
                if (tmpX.getRok().equals(wpisView.getRokWpisuSt())
                        && miesiaceWkwartale.contains(tmpX.getMiesiac())) {
                    if (p.isOdliczeniezus51() == true) {
                        if (tmpX.getZus51ch() != 0.0) {
                            suma51 += tmpX.getZus51ch();
                        } else {
                            suma51 += tmpX.getZus51bch();
                        }
                    }
                }
            } else {
                if (tmpX.getRok().equals(wpisView.getRokWpisuSt())
                        && tmpX.getMiesiac().equals(wpisView.getMiesiacWpisu())) {
                    if (p.isOdliczeniezus51() == true) {
                        if (tmpX.getZus51ch() != 0.0) {
                            suma51 += tmpX.getZus51ch();
                        } else {
                            suma51 += tmpX.getZus51bch();
                        }
                    }
                }
            }
        }
        return BigDecimal.valueOf(suma51);
    }

    private BigDecimal pobierzZUS52() {
        Podatnik p = wpisView.getPodatnikObiekt();
        Iterator it;
        it = p.getZusstawkinowe().iterator();
        double suma52 = 0;
        List<String> miesiaceWkwartale = Kwartaly.mctoMcewKw(wpisView.getMiesiacWpisu());
        while (it.hasNext()) {
            Zusstawkinew tmpX = (Zusstawkinew) it.next();
            if (wpisView.isMc0kw1()) {
                if (tmpX.getRok().equals(wpisView.getRokWpisuSt())
                        && miesiaceWkwartale.contains(tmpX.getMiesiac())) {
                    suma52 += tmpX.getZus52odl();
                }
            } else {
                if (tmpX.getRok().equals(wpisView.getRokWpisuSt())
                        && tmpX.getMiesiac().equals(wpisView.getMiesiacWpisu())) {
                    suma52 += tmpX.getZus52odl();
                }
            }
        }
        return BigDecimal.valueOf(suma52);
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
        if (biezacyPit.getWynik() != null) {
            try {
                Pitpoz find = pitDAO.find(biezacyPit.getPkpirR(), biezacyPit.getPkpirM(), biezacyPit.getPodatnik(), biezacyPit.getUdzialowiec(), null);
                pitDAO.remove(find);
                pitDAO.create(biezacyPit);
                String wiad = String.format("Edytowano PIT %s za m-c:%s", biezacyPit.getUdzialowiec(), biezacyPit.getPkpirM());
                Msg.msg("i", wiad);
            } catch (Exception e) {
                E.e(e);
                pitDAO.create(biezacyPit);
                String wiad = String.format("Zachowano PIT %s za m-c:%s", biezacyPit.getUdzialowiec(), biezacyPit.getPkpirM());
                Msg.msg("i", wiad);
            }

        } else {
            Msg.msg("e", "Nie można zachować. PIT nie wypełniony");
        }
    }
    
    public void zus51zrekiF() {
        zus51zreki = true;
    }

    public void zachowajPit13() {
        biezacyPit.setPkpirM("13");
        BigDecimal roznicaremanentow = new BigDecimal(remanentView.getRoznica());
        biezacyPit.setRemanent(roznicaremanentow);
        zachowajPit();
    }

    public void aktualizujPIT(AjaxBehaviorEvent e) {
        wybranyudzialowiec = "wybierz osobe";
        PrimeFaces.current().ajax().update("formpit");
        aktualizuj();
        Msg.msg("i", "Zmieniono miesiąc obrachunkowy.");
    }

    private void aktualizujGuest() {
        wpisView.naniesDaneDoWpis();
    }

    private void aktualizuj() {
        wpisView.naniesDaneDoWpis();
    }

    public void aktualizujGuest(String strona) throws IOException {
        aktualizujGuest();
        aktualizuj();
        init();
        //FacesContext.getCurrentInstance().getExternalContext().redirect(strona);
    }

    public Pitpoz skumulujpity(String mcDo, String udzialowiec) {
        Pitpoz tmp = new Pitpoz("zus");
        try {
            Collection c = pitDAO.findPitPod(wpisView.getRokWpisu().toString(), wpisView.getPodatnikWpisu(), null);
            Iterator it = c.iterator();
            int poprzednimc = Mce.getMiesiacToNumber().get(mcDo);
            if (wpisView.isMc0kw1()) {
                poprzednimc = poprzednimc - 3;
            } else {
                poprzednimc = poprzednimc - 1;
            }
            String starymcS = Mce.getNumberToMiesiac().get(poprzednimc);
            while (it.hasNext()) {
                Pitpoz tmpX = (Pitpoz) it.next();
                int miesiacPituPobranego = Integer.parseInt(tmpX.getPkpirM());
                if (miesiacPituPobranego <= poprzednimc && tmpX.getUdzialowiec().equals(udzialowiec)) {
                    if (tmpX.getNaleznazal().signum() == 1) {
                        tmp.setNalzalodpoczrok(tmp.getNalzalodpoczrok().add(tmpX.getNaleznazal()));
                    }
                }
                if (tmpX.getPkpirM().equals(starymcS) && tmpX.getUdzialowiec().equals(udzialowiec)) {
                    if (tmpX.getZus51() != null) {
                        tmp.setZus51(tmp.getZus51().add(tmpX.getZus51()));
                    } else {
                        tmp.setZus51(BigDecimal.ZERO);
                    }
                    if (tmpX.getZus52() != null) {
                        tmp.setZus52(tmp.getZus52().add(tmpX.getZus52()));
                    } else {
                        tmp.setZus52(BigDecimal.ZERO);
                    }
                }
            }
        } catch (Exception e) {
            E.e(e);
        } finally {
            return tmp;
        }
    }

 
    public void pobierzPity() {
        try {
            pobierzPity.addAll(pitDAO.findAll());
        } catch (Exception e) {
            E.e(e);
        }
        narPitpoz = new Pitpoz();
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

    public void drukujbiezacypit() {
        try {
            PdfPIT5.drukuj(biezacyPit, wpisView, podatnikDAO);
        } catch (Exception e) {
            E.e(e);

        }
    }

    public void drukujPodsumowanieRoczne() {
        try {
            //PdfZestRok.drukuj(wpisView, this);
        } catch (Exception e) {
            E.e(e);

        }
    }

    private int sprawdzczyjestpitwpoprzednimmiesiacu() {
        if (pierwszypitwroku == true) {
            return 0;
        }
        if (wpisView.isMc0kw1()) {
            if (!wpisView.getMiesiacWpisu().equals("03") || wybranyudzialowiec.equals("wybierz osobe")) {
                int numermiesiaca = Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu());
                String numermiesiacaS = Mce.getNumberToMiesiac().get(numermiesiaca - 3);
                try {
                    Pitpoz poprzednipit = pitDAO.find(wpisView.getRokWpisuSt(), numermiesiacaS, wpisView.getPodatnikWpisu(), wybranyudzialowiec, null);
                    if (poprzednipit == null) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    E.e(e);
                    komunikatblad = "Brak PIT-u w poprzednim okresie. Nie można wyliczyć bieżącego okresu. Czy to jest pierwszy PIT w roku?";
                    pierwszypitwrokuzaznacz = true;
                    return 1;
                }
            }

        } else {
            if (!wpisView.getMiesiacWpisu().equals("01") || wybranyudzialowiec.equals("wybierz osobe")) {
                try {
                    Pitpoz poprzednipit = pitDAO.find(wpisView.getRokWpisuSt(), wpisView.getMiesiacUprzedni(), wpisView.getPodatnikWpisu(), wybranyudzialowiec, null);
                } catch (Exception e) {
                    E.e(e);
                    Msg.msg("w", "Brak PIT-u w miesiącu poprzednim. Nie można wyliczyć bieżącego miesiąca");
                    pierwszypitwrokuzaznacz = true;
                    return 1;
                }
            }
        }
        return 0;
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public void ustawZus51() {
        setZus51zreki(true);
    }
    
    public void ustawZus52() {
        setZus52zreki(true);
    }
    
    public DokDAO getDokDAO() {
        return dokDAO;
    }
    
    public void setDokDAO(DokDAO dokDAO) {
        this.dokDAO = dokDAO;
    }
    
    public PitDAO getPitDAO() {
        return pitDAO;
    }
    
    public void setPitDAO(PitDAO pitDAO) {
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
    
    
    public Pitpoz getPitpoz() {
        return pitpoz;
    }
    
    public void setPitpoz(Pitpoz pitpoz) {
        this.pitpoz = pitpoz;
    }
    
    public Pitpoz getNarPitpoz() {
        return narPitpoz;
    }
    
    public void setNarPitpoz(Pitpoz narPitpoz) {
        this.narPitpoz = narPitpoz;
    }
    
    public List<Pitpoz> getPobierzPity() {
        return pobierzPity;
    }
    
    public void setPobierzPity(List<Pitpoz> pobierzPity) {
        this.pobierzPity = pobierzPity;
    }
    
    public Pitpoz getBiezacyPit() {
        return biezacyPit;
    }
    
    public void setBiezacyPit(Pitpoz biezacyPit) {
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
    
    public boolean isPierwszypitwroku() {
        return pierwszypitwroku;
    }
    
    public void setPierwszypitwroku(boolean pierwszypitwroku) {
        this.pierwszypitwroku = pierwszypitwroku;
    }
    
    public boolean isPierwszypitwrokuzaznacz() {
        return pierwszypitwrokuzaznacz;
    }
    
    public void setPierwszypitwrokuzaznacz(boolean pierwszypitwrokuzaznacz) {
        this.pierwszypitwrokuzaznacz = pierwszypitwrokuzaznacz;
    }
    
    
    public RemanentView getRemanentView() {
        return remanentView;
    }
    
    public void setRemanentView(RemanentView remanentView) {
        this.remanentView = remanentView;
    }
    
    public String getKomunikatblad() {
        return komunikatblad;
    }
    
    public void setKomunikatblad(String komunikatblad) {
        this.komunikatblad = komunikatblad;
    }
    
    public String getWybranyudzialowiecopodatkowanie() {
        return wybranyudzialowiecopodatkowanie;
    }

    public void setWybranyudzialowiecopodatkowanie(String wybranyudzialowiecopodatkowanie) {
        this.wybranyudzialowiecopodatkowanie = wybranyudzialowiecopodatkowanie;
    }
    
    
//</editor-fold>

    

}
