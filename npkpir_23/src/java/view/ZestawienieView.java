/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.KsiegaBean;
import beansPIT.WyliczPodatekZasadyOgolne;
import comparator.Dokcomparator;
import comparator.Stratacomparator;
import dao.AmoDokDAO;
import dao.CechazapisuDAOfk;
import dao.DokDAO;
import dao.FakturaDAO;
import dao.PitDAO;
import dao.PodStawkiDAO;
import dao.PodatnikDAO;
import dao.PodatnikOpodatkowanieDAO;
import dao.PodatnikUdzialyDAO;
import dao.SMTPSettingsDAO;
import dao.StrataDAO;
import dao.ZobowiazanieDAO;
import embeddable.Kwartaly;
import embeddable.Mce;
import embeddable.WierszPkpir;
import entity.Amodok;
import entity.Dok;
import entity.Faktura;
import entity.KwotaKolumna1;
import entity.Pitpoz;
import entity.Podatnik;
import entity.PodatnikOpodatkowanieD;
import entity.PodatnikUdzialy;
import entity.Podstawki;
import entity.Strata;
import entity.StrataWykorzystanie;
import entity.Zobowiazanie;
import entity.Zusstawkinew;
import entityfk.Cechazapisu;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import mail.MaiManager;
import mail.Mail;
import mail.MailOther;
import msg.Msg;
import org.apache.commons.collections4.CollectionUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import pdf.PdfDok;
import pdf.PdfPIT5;
import pdf.PdfZestRok;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named(value = "zestawienieView")
@ViewScoped
public class ZestawienieView implements Serializable {

    private static final long serialVersionUID = 1L;
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
    private WierszPkpir styczen;
    private WierszPkpir luty;
    private WierszPkpir marzec;
    private WierszPkpir kwiecien;
    private WierszPkpir maj;
    private WierszPkpir czerwiec;
    private WierszPkpir lipiec;
    private WierszPkpir sierpien;
    private WierszPkpir wrzesien;
    private WierszPkpir pazdziernik;
    private WierszPkpir listopad;
    private WierszPkpir grudzien;
    private WierszPkpir Ipolrocze;
    private WierszPkpir IIpolrocze;
    private WierszPkpir rok;
    private List<WierszPkpir> zebranieMcy;
    //dane niezbedne do wyliczania pit
    private PodatnikUdzialy wybranyudzialowiec;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private PitDAO pitDAO;
    @Inject
    private CechazapisuDAOfk cechazapisuDAOfk;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private AmoDokDAO amoDokDAO;
    @Inject
    private StrataDAO strataDAO;
    //bieżący pit
    private Pitpoz pitpoz;
    //sumowanie poprzednich pitów jeżeli są zachowane
    private Pitpoz narPitpoz;
    //lista pitow
    private List<Pitpoz> listapit;
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
    private List<PodatnikUdzialy> listawybranychudzialowcow;
    //z reki
    private boolean zus51zreki;
    private boolean zus52zreki;
    private double zus51zrekivalue;
    private boolean pierwszypitwroku;
    private boolean pierwszypitwrokuzaznacz;
    @Inject
    private PodatnikUdzialyDAO podatnikUdzialyDAO;
    @Inject
    private PodatnikOpodatkowanieDAO podatnikOpodatkowanieDDAO;
    private String komunikatblad;
    private List<Cechazapisu> pobranecechypodatnik;
    private Cechazapisu wybranacechadok;
    @Inject
    private FakturaDAO fakturaDAO;
    private Podatnik taxman;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    private List<Pitpoz> pitydlapita;

    private int flaga = 0;

    //rysuje wykres
    private LineChartModel linearModel;

    public ZestawienieView() {

    }

    @PostConstruct
    public void init() { //E.m(this);
        styczen = new WierszPkpir(1, wpisView.getRokWpisuSt(), "01", "styczeń");
        luty = new WierszPkpir(2, wpisView.getRokWpisuSt(), "02", "luty");
        marzec = new WierszPkpir(3, wpisView.getRokWpisuSt(), "03", "marzec");
        kwiecien = new WierszPkpir(4, wpisView.getRokWpisuSt(), "04", "kwiecień");
        maj = new WierszPkpir(5, wpisView.getRokWpisuSt(), "05", "maj");
        czerwiec = new WierszPkpir(6, wpisView.getRokWpisuSt(), "06", "czerwiec");
        lipiec = new WierszPkpir(7, wpisView.getRokWpisuSt(), "07", "lipiec");
        sierpien = new WierszPkpir(8, wpisView.getRokWpisuSt(), "08", "sierpień");
        wrzesien = new WierszPkpir(9, wpisView.getRokWpisuSt(), "09", "wrzesień");
        pazdziernik = new WierszPkpir(10, wpisView.getRokWpisuSt(), "10", "październik");
        listopad = new WierszPkpir(11, wpisView.getRokWpisuSt(), "11", "listopad");
        grudzien = new WierszPkpir(12, wpisView.getRokWpisuSt(), "12", "grudzień");
        Ipolrocze = new WierszPkpir(13, wpisView.getRokWpisuSt(), "13", "I półrocze");
        IIpolrocze = new WierszPkpir(14, wpisView.getRokWpisuSt(), "14", "II półrocze");
        rok = new WierszPkpir(15, wpisView.getRokWpisuSt(), "15", "rok");
        pitydlapita = new ArrayList<>();
        taxman = podatnikDAO.findPodatnikByNIP("8511005008");
        pobierzPity = Collections.synchronizedList(new ArrayList<>());
        zebranieMcy = Collections.synchronizedList(new ArrayList<>());
        listapit = Collections.synchronizedList(new ArrayList<>());
        listawybranychudzialowcow = new ArrayList<>();
        if (wpisView.getPodatnikObiekt() != null && wpisView.isKsiegaryczalt()) {
            pobranecechypodatnik = cechazapisuDAOfk.findPodatnikOnlyAktywne(wpisView.getPodatnikObiekt());
            Podatnik pod = wpisView.getPodatnikObiekt();
            try {
                listawybranychudzialowcow = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView.getPodatnikObiekt());
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Nie uzupełnione parametry podatnika", "formpit:messages");
            }

        }
        sumowaniemiesiecy(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        if (listawybranychudzialowcow.size() == 1 && CollectionUtils.isNotEmpty(lista)) {
            wybranyudzialowiec = listawybranychudzialowcow.get(0);
            obliczPit();
        }
        createLinearModel();
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
    
    private void sumowaniemiesiecy(Podatnik podatnik, String rokint, String mcints) {
        try {
            styczen = new WierszPkpir(1, rokint, "01", "styczeń");
            luty = new WierszPkpir(2, rokint, "02", "luty");
            marzec = new WierszPkpir(3, rokint, "03", "marzec");
            kwiecien = new WierszPkpir(4, rokint, "04", "kwiecień");
            maj = new WierszPkpir(5, rokint, "05", "maj");
            czerwiec = new WierszPkpir(6, rokint, "06", "czerwiec");
            lipiec = new WierszPkpir(7, rokint, "07", "lipiec");
            sierpien = new WierszPkpir(8, rokint, "08", "sierpień");
            wrzesien = new WierszPkpir(9, rokint, "09", "wrzesień");
            pazdziernik = new WierszPkpir(10, rokint, "10", "październik");
            listopad = new WierszPkpir(11, rokint, "11", "listopad");
            grudzien = new WierszPkpir(12, rokint, "12", "grudzień");
            Ipolrocze = new WierszPkpir(13, rokint, "13", "I półrocze");  
            IIpolrocze = new WierszPkpir(14, rokint, "14", "II półrocze");
            rok = new WierszPkpir(15, rokint, "15", "rok");
            zebranieMcy = Collections.synchronizedList(new ArrayList<>());
                lista = KsiegaBean.pobierzdokumentyRok(dokDAO, podatnik, Integer.parseInt(rokint), mcints, wpisView.getOdjakiegomcdok());
                if (wybranacechadok != null) {
                    for (Iterator<Dok> it = lista.iterator(); it.hasNext();) {
                        Dok p = it.next();
                        if (p.getListaCech() != null && !p.getListaCech().contains(wybranacechadok.getNazwacechy())) {
                            it.remove();
                        }
                    }
                }
                for (Iterator<Dok> it = lista.iterator(); it.hasNext();) {
                    Dok tmpx = it.next();
                    if (tmpx.getRodzajedok().isTylkojpk()) {
                        it.remove();
                    }
                }
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
                                int mcint = Mce.getMiesiacToNumber().get(selekcja) - 1;
                                WierszPkpir listabiezaca = zebranieMcy.get(mcint);
                                switch (selekcja2) {
                                    case "przych. sprz":
                                        temp = listabiezaca.getKolumna7() + kwota;
                                        listabiezaca.setKolumna7(temp);
                                        break;
                                    case "pozost. przych.":
                                        temp = listabiezaca.getKolumna8() + kwota;
                                        listabiezaca.setKolumna8(temp);
                                        break;
                                    case "zakup tow. i mat.":
                                        temp = listabiezaca.getKolumna10() + kwota;
                                        listabiezaca.setKolumna10(temp);
                                        break;
                                    case "koszty ub.zak.":
                                        temp = listabiezaca.getKolumna11() + kwota;
                                        listabiezaca.setKolumna11(temp);
                                        break;
                                    case "wynagrodzenia":
                                        temp = listabiezaca.getKolumna12() + kwota;
                                        listabiezaca.setKolumna12(temp);
                                        break;
                                    case "poz. koszty":
                                        temp = listabiezaca.getKolumna13() + kwota;
                                        listabiezaca.setKolumna13(temp);
                                        break;
                                    case "inwestycje":
                                        temp = listabiezaca.getKolumna15() + kwota;
                                        listabiezaca.setKolumna15(temp);
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
                for (WierszPkpir p : zebranieMcy) {
                    if (p.getId() < 7) {
                        Ipolrocze.dodaj(p);
                    } else {
                        IIpolrocze.dodaj(p);
                    }
                    rok.dodaj(p);
                }
            }
    }

    private void naniesnaliste(List<Double> wstyczen, WierszPkpir get) {
        wstyczen.add(get.getKolumna7());
        wstyczen.add(get.getKolumna8());
        wstyczen.add(get.getKolumna10());
        wstyczen.add(get.getKolumna11());
        wstyczen.add(get.getKolumna12());
        wstyczen.add(get.getKolumna13());
        wstyczen.add(get.getKolumna15());
        wstyczen.add(get.getRazemprzychody());
        wstyczen.add(get.getRazemkoszty());
        wstyczen.add(get.getRazemdochod());
    }

    public LineChartModel getLinearModel() {
        return linearModel;
    }

    public void setLinearModel(LineChartModel linearModel) {
        this.linearModel = linearModel;
    }

    private void createLinearModel() {
        linearModel = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("przychody");
        series1.setMarkerStyle("circle");

        series1.set("styczeń", styczen.getRazemprzychody());
        series1.set("luty", luty.getRazemprzychody());
        series1.set("marzec", marzec.getRazemprzychody());
        series1.set("kwiecień", kwiecien.getRazemprzychody());
        series1.set("maj", maj.getRazemprzychody());
        series1.set("czerwiec", czerwiec.getRazemprzychody());
        series1.set("lipiec", lipiec.getRazemprzychody());
        series1.set("sierpień", sierpien.getRazemprzychody());
        series1.set("wrzesień", wrzesien.getRazemprzychody());
        series1.set("październik", pazdziernik.getRazemprzychody());
        series1.set("listopad", listopad.getRazemprzychody());
        series1.set("grudzień", grudzien.getRazemprzychody());

        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("koszty");
        series2.setMarkerStyle("diamond");

        series2.set("styczeń", styczen.getRazemkoszty());
        series2.set("luty", luty.getRazemkoszty());
        series2.set("marzec", marzec.getRazemkoszty());
        series2.set("kwiecień", kwiecien.getRazemkoszty());
        series2.set("maj", maj.getRazemkoszty());
        series2.set("czerwiec", czerwiec.getRazemkoszty());
        series2.set("lipiec", lipiec.getRazemkoszty());
        series2.set("sierpień", sierpien.getRazemkoszty());
        series2.set("wrzesień", wrzesien.getRazemkoszty());
        series2.set("październik", pazdziernik.getRazemkoszty());
        series2.set("listopad", listopad.getRazemkoszty());
        series2.set("grudzień", grudzien.getRazemkoszty());

        LineChartSeries series3 = new LineChartSeries();
        series3.setLabel("wynik");
        series3.setMarkerStyle("filledSquare");

        series3.set("styczeń", styczen.getRazemdochod());
        series3.set("luty", luty.getRazemdochod());
        series3.set("marzec", marzec.getRazemdochod());
        series3.set("kwiecień", kwiecien.getRazemdochod());
        series3.set("maj", maj.getRazemdochod());
        series3.set("czerwiec", czerwiec.getRazemdochod());
        series3.set("lipiec", lipiec.getRazemdochod());
        series3.set("sierpień", sierpien.getRazemdochod());
        series3.set("wrzesień", wrzesien.getRazemdochod());
        series3.set("październik", pazdziernik.getRazemdochod());
        series3.set("listopad", listopad.getRazemdochod());
        series3.set("grudzień", grudzien.getRazemdochod());

        linearModel.addSeries(series1);
        linearModel.addSeries(series2);
        linearModel.addSeries(series3);
        linearModel.setAnimate(true);
        linearModel.setShadow(true);
        linearModel.setBreakOnNull(true);
        Axis yAxis = linearModel.getAxis(AxisType.Y);
        yAxis.setMin(wyliczminimalna());
        yAxis.setMax(wyliczmaksymalna());
        yAxis.setLabel("kwota");
        linearModel.getAxes().put(AxisType.X, new CategoryAxis("miesiące"));
        Axis xAxis = linearModel.getAxis(AxisType.X);
        xAxis.setLabel("miesiące");
        linearModel.setTitle("Zestawienie za rok");
        linearModel.setLegendPosition("e");
        linearModel.setZoom(true);
    }

    private double wyliczmaksymalna() {
        double maxliczbadowykresu = 0;
        try {
            List<Double> lista = Collections.synchronizedList(new ArrayList<>());
            for (WierszPkpir p : zebranieMcy) {
                lista.add(p.getRazemprzychody());
                lista.add(p.getRazemkoszty());
                lista.add(p.getRazemdochod());
            }
            for (Double p : lista) {
                if (maxliczbadowykresu < p) {
                    maxliczbadowykresu = p;
                }
            }
            if (maxliczbadowykresu < 10000) {
                maxliczbadowykresu = maxliczbadowykresu + maxliczbadowykresu * 0.05;
                maxliczbadowykresu = maxliczbadowykresu / 1000;
                maxliczbadowykresu = Math.round(maxliczbadowykresu);
                maxliczbadowykresu = maxliczbadowykresu * 1000;
            } else {
                maxliczbadowykresu = maxliczbadowykresu + maxliczbadowykresu * 0.02;
                maxliczbadowykresu = maxliczbadowykresu / 10000;
                maxliczbadowykresu = Math.round(maxliczbadowykresu);
                maxliczbadowykresu = maxliczbadowykresu * 10000;
            }
        } catch (Exception ex) {
        }

        return maxliczbadowykresu;
    }

    private double wyliczminimalna() {
        double minliczbadowykresu = 0;
        try {
            List<Double> lista = Collections.synchronizedList(new ArrayList<>());
            for (WierszPkpir p : zebranieMcy) {
                lista.add(p.getRazemprzychody());
                lista.add(p.getRazemkoszty());
                lista.add(p.getRazemdochod());
            }
            for (double p : lista) {
                if (minliczbadowykresu > p) {
                    minliczbadowykresu = p;
                }
            }
            if (minliczbadowykresu < -10000) {
                minliczbadowykresu = minliczbadowykresu + minliczbadowykresu * 0.05;
                minliczbadowykresu = minliczbadowykresu / 1000;
                minliczbadowykresu = Math.round(minliczbadowykresu);
                minliczbadowykresu = minliczbadowykresu * 1000;
            } else {
                minliczbadowykresu = minliczbadowykresu + minliczbadowykresu * 0.02;
                minliczbadowykresu = minliczbadowykresu / 10000;
                minliczbadowykresu = Math.round(minliczbadowykresu);
                minliczbadowykresu = minliczbadowykresu * 10000;
            }
        } catch (Exception ex) {
        }
        return minliczbadowykresu;
    }

    public void obliczPitCecha() {
        biezacyPit = new Pitpoz();
        init();
        biezacyPit.setCechazapisu(wybranacechadok);
        obliczPit();
    }

    //oblicze pit i wkleja go do biezacego Pitu w celu wyswietlenia, nie zapisuje
    public void obliczPit() {
        biezacyPit = new Pitpoz();
        if (zus51zreki) {
            biezacyPit.setZus51(BigDecimal.valueOf(zus51zrekivalue));
        }
        sumowaniemiesiecy(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        String uzernazwa = principal.getName();
        if (!uzernazwa.equals("szef")) {
            List<Faktura> czywystawionofakture = fakturaDAO.findbyKontrahentNipRokMc(wpisView.getPodatnikObiekt().getNip(), taxman, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            if (czywystawionofakture==null||czywystawionofakture.isEmpty()) {
                Msg.msg("e","Nie wystawiono faktury dla firmy. Nie można zakończyć miesiąca");
                Mail.wykrytobrakfaktury("info@taxman.biz.pl", wpisView.getPrintNazwa(), null, sMTPSettingsDAO.findSprawaByDef(), wpisView.getUzer().getImieNazwisko());
                //return;
            }
        }
        komunikatblad = null;
        if (listawybranychudzialowcow.size() == 1) {
            wybranyudzialowiec = listawybranychudzialowcow.get(0);
        }
        if (wybranyudzialowiec==null) {
            Msg.msg("e", "Nie wybrałeś podatnika");
            return;
        } else {
            if (pierwszypitwrokuzaznacz == false) {
                if (sprawdzczyjestpitwpoprzednimmiesiacu() != 0) {
                    return;
                }
            }
            sprawdzczyzaksiegowanoamortyzacje();
            if (flaga == 0) {
                biezacyPit.setPodatnik(wpisView.getPodatnikWpisu());
                biezacyPit.setPodatnik1(wpisView.getPodatnikObiekt());
                biezacyPit.setPodmiot(wybranyudzialowiec.getPodmiot());
                biezacyPit.setPkpirR(wpisView.getRokWpisu().toString());
                biezacyPit.setPkpirM(wpisView.getMiesiacWpisu());
                biezacyPit.setPrzychody(obliczprzychod(biezacyPit));
                double procent = Double.parseDouble(wybranyudzialowiec.getUdzial()) / 100;
                biezacyPit.setPrzychodyudzial(biezacyPit.getPrzychody().multiply(new BigDecimal(procent)));
                biezacyPit.setPrzychodyudzialmc(Z.z(biezacyPit.getPrzychodymc()*procent));
                biezacyPit.setKoszty(obliczkoszt(biezacyPit));
                if (wpisView.getMiesiacWpisu().equals("12")) {
                    BigDecimal roznicaremanentow = new BigDecimal(remanentView.getRoznica());
                    biezacyPit.setRemanent(roznicaremanentow);
                    BigDecimal kosztypokorekcie = biezacyPit.getKoszty().add(roznicaremanentow);
                    biezacyPit.setKosztymc(Z.z(biezacyPit.getKosztymc()+roznicaremanentow.doubleValue()));
                    biezacyPit.setKosztyudzial(kosztypokorekcie.multiply(new BigDecimal(procent)));
                    biezacyPit.setKosztyudzialmc(Z.z(biezacyPit.getKosztymc()*procent));
                } else {
                    biezacyPit.setKosztyudzial(biezacyPit.getKoszty().multiply(new BigDecimal(procent)));
                    biezacyPit.setKosztyudzialmc(Z.z(biezacyPit.getKosztymc()*procent));
                }
                biezacyPit.setWynik(biezacyPit.getPrzychodyudzial().subtract(biezacyPit.getKosztyudzial()));
                biezacyPit.setUdzialowiec(wybranyudzialowiec.getNazwiskoimie());
                biezacyPit.setUdzial(wybranyudzialowiec.getUdzial());
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
                    wybranyudzialowiec = null;
                    pierwszypitwroku = false;
                    pierwszypitwrokuzaznacz = false;
                    PrimeFaces.current().ajax().update("formpit:");
                    return;
                }
                Podstawki skalaPodatkowaZaDanyRok;
                try {
                    skalaPodatkowaZaDanyRok = podstawkiDAO.find(Integer.parseInt(biezacyPit.getPkpirR()), biezacyPit.getPkpirM());
                } catch (Exception e) {
                    E.e(e);
                    biezacyPit = new Pitpoz();
                    wybranyudzialowiec = null;
                    pierwszypitwroku = false;
                    pierwszypitwrokuzaznacz = false;
                    komunikatblad = "Brak wprowadzonej skali opodatkowania dla wszystkich podatników na obecny rok. Przerywam wyliczanie PIT-u";
                    Msg.msg("e", "Brak wprowadzonej skali opodatkowania dla wszystkich podatników na obecny rok. Przerywam wyliczanie PIT-u");
                    return;
                }
                PodatnikOpodatkowanieD podatniopodatkowanie = wpisView.zwrocFormaOpodatkowania(wpisView.getRokWpisuSt());
                String opodatkowanie = podatniopodatkowanie.getFormaopodatkowania();
                boolean mc0kw1 = podatniopodatkowanie.isMc0kw1();
                String rodzajop = opodatkowanie;
                if (czynaliczacpodatek(wpisView.getMiesiacWpisu(), mc0kw1)) {
                    Double stawka = 0.0;
                    BigDecimal podatek = BigDecimal.ZERO;
                    BigDecimal dochód = biezacyPit.getPodstawa();
                    BigDecimal przychody = biezacyPit.getPrzychody();
                    try {
                        switch (rodzajop) {
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
    //                        case "ryczałt":
    //                            stawka = skalaPodatkowaZaDanyRok.getStawkaryczalt1();
    //                            podatek = (przychody.multiply(BigDecimal.valueOf(stawka)));
    //                            podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
    //                            break;
    //                        case "ryczałt bez VAT":
    //                            stawka = skalaPodatkowaZaDanyRok.getStawkaryczalt1();
    //                            podatek = (przychody.multiply(BigDecimal.valueOf(stawka)));
    //                            podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
    //                            break;
                        }
                    } catch (Exception e) {
                        E.e(e);
                        komunikatblad = "Brak wprowadzonych terminów płatności podatków w danym okresie rozliczeniowym! Nie można przeliczyć PIT-u";
                        Msg.msg("e", "Brak wprowadzonego rodzaju opodatkowania dla danego podatnika!! Nie można przeliczyć PIT za: " + biezacyPit.getPkpirM());
                        biezacyPit = new Pitpoz();
                        wybranyudzialowiec = null;
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
                    if (wpisView.getRokWpisu()<2022) {
                        BigDecimal tmpX = podatek.subtract(biezacyPit.getZus52());
                        tmpX = tmpX.setScale(0, RoundingMode.HALF_EVEN);
                        if (tmpX.signum() == -1) {
                            biezacyPit.setPododpoczrok(BigDecimal.ZERO);
                        } else {
                            biezacyPit.setPododpoczrok(tmpX);
                        }
                    } else {
                        if (podatek.signum() == 1) {
                            biezacyPit.setPododpoczrok(podatek);
                        } else {
                            biezacyPit.setPododpoczrok(BigDecimal.ZERO);
                        }
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
                        wybranyudzialowiec = null;
                        pierwszypitwroku = false;
                        pierwszypitwrokuzaznacz = false;
                    }
                    Msg.msg("Przeliczono PIT");
                } else {
                    Msg.msg("w","Podatek naliczany kwartalnie");
                }
            }
        }
        if (wybranyudzialowiec!=null) {
            List<Pitpoz> pitpozlistapobrane = pitDAO.findByPodmiotRok(wybranyudzialowiec.getPodmiot(), wpisView.getRokWpisuSt());
            if (pitpozlistapobrane!=null) {
                List<Pitpoz> pitpozlista = new ArrayList<>();
                for (Pitpoz p : pitpozlistapobrane) {
                    if (!p.getPodatnik().equals(wpisView.getPodatnikObiekt())) {
                        pitpozlista.add(p);
                    }
                }
                pitydlapita = pitpozlista.stream()
                    .filter(p->!p.getPodatnik1().equals(wpisView.getPodatnikObiekt()))
                    .filter(p->p.getPkpirM().equals(wpisView.getMiesiacWpisu()))
                    .collect(Collectors.toList());
            }
        }
    }
    
    private boolean czynaliczacpodatek(String miesiacWpisu, boolean mc0kw1) {
        boolean zwrot = true;
        if (mc0kw1) {
            if (miesiacWpisu.equals("03")||miesiacWpisu.equals("06")||miesiacWpisu.equals("09")||miesiacWpisu.equals("12")) {
                zwrot = true;
            } else {
                zwrot = false;
            }
        }
        return zwrot;
    }
    
    //oblicze pit i wkleja go do biezacego Pitu w celu wyswietlenia, nie zapisuje
    public void obliczPitDRA(String rok, String mc, Podatnik podatnik, PodatnikUdzialy udzialowiec) {
        sumowaniemiesiecy(podatnik,rok,mc);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        String uzernazwa = principal.getName();
        if (flaga == 0) {
            Pitpoz biezacyPit = pitDAO.find(rok, mc, podatnik.getNazwapelna(), udzialowiec.getNazwiskoimie(), null);
            if (biezacyPit!=null) {
                biezacyPit.setPrzychodymc(Z.z(obliczprzychodDRA(mc)));
                biezacyPit.setKosztymc(Z.z(obliczkosztDRA(mc)));
                double procent = Z.z4(Double.parseDouble(wybranyudzialowiec.getUdzial()) / 100);
                biezacyPit.setPrzychodyudzialmc(Z.z(biezacyPit.getPrzychodymc()*procent));
                biezacyPit.setKosztyudzialmc(Z.z(biezacyPit.getKosztymc()*procent));
                pitDAO.edit(biezacyPit);
                Msg.msg("podatnik "+podatnik.getPrintnazwa());
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
        double suma52 = 0;
        try {
            Podatnik p = wpisView.getPodatnikObiekt();
            Iterator it;
            it = p.getZusstawkinowe().iterator();

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
        } catch (Exception e) {
        }
        return BigDecimal.valueOf(suma52);
    }

    private void sprawdzczyzaksiegowanoamortyzacje() {
        Amodok amortyzacjawmiesiacu = null;
        Dok dokumentamo = null;
        try {
            amortyzacjawmiesiacu = amoDokDAO.findMR(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
            if (amortyzacjawmiesiacu!=null&&amortyzacjawmiesiacu.getUmorzenia().isEmpty()) {
                amortyzacjawmiesiacu = null;
            }
        } catch (Exception e) {
            E.e(e);
        }
        try {
            dokumentamo = dokDAO.findDokMC("AMO", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu().toString(), wpisView.getMiesiacWpisu());
        } catch (Exception e) {
            E.e(e);
        }
        if (amortyzacjawmiesiacu != null && dokumentamo == null) {
            Msg.msg("e", "Brak zaksięgowanej amortyzacji mimo istnienia dokumentu umorzeniowego za miesiąc!");
            flaga = 1;
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
        if (sumastrat>0) {
            sumastrat = naniesrozliczenia(straty,sumastrat);
            strataDAO.editList(straty);
        } else {
            List<Strata> stratypodatnika = strataDAO.findPodatnik(wpisView.getPodatnikObiekt());
            if (stratypodatnika!=null) {
                for (Strata s : stratypodatnika) {
                    for (Iterator<StrataWykorzystanie> it = s.getListawykorzystanie().iterator();it.hasNext();) {
                        StrataWykorzystanie st = it.next();
                        if (st.getRok().equals(wpisView.getRokWpisuSt())&&st.getMc().equals(wpisView.getMiesiacWpisu())) {
                            it.remove();
                            strataDAO.edit(s);
                        }
                    }
                    
                }
            }
        }
        } catch (Exception e) {
            E.e(e);
            biezacyPit.setStrata(BigDecimal.ZERO);
        }
    }
    
    private double naniesrozliczenia(List<Strata> straty, double rozliczyc) {
        double naniesiono = 0.0;
        Collections.sort(straty, new Stratacomparator());
        for (Strata p : straty) {
            double zostalo = Z.z(p.getKwota()-p.getWykorzystano(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
            if (naniesiono<rozliczyc) {
                StrataWykorzystanie wykorzystanie = p.pobierzWykorzystanie(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                if (zostalo>0.0) {
                    if (zostalo>=rozliczyc) {
                        wykorzystanie.setKwotawykorzystania(rozliczyc);
                        naniesiono = Z.z(naniesiono+rozliczyc);
                    } else {
                        wykorzystanie.setKwotawykorzystania(zostalo);
                        naniesiono = Z.z(naniesiono+zostalo);
                    }
                } else {
                    p.getListawykorzystanie().remove(wykorzystanie);
                    break;
                }
            }
        }
        return naniesiono;
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

    @Inject
    private PitView pitView;
    
    public void zachowajPit() {
        if (biezacyPit.getWynik() != null) {
            try {
                Pitpoz find = pitDAO.find(biezacyPit.getPkpirR(), biezacyPit.getPkpirM(), biezacyPit.getPodatnik(), biezacyPit.getUdzialowiec(), wybranacechadok);
                if (find != null) {
                    pitDAO.remove(find);
                }
                pitDAO.create(biezacyPit);
                pitView.init();
                String wiad = String.format("Edytowano PIT %s za m-c:%s", biezacyPit.getUdzialowiec(), biezacyPit.getPkpirM());
                Msg.msg("i", wiad);
                if (wpisView.getPodatnikObiekt().isWysylaczestdok()) {
                    mailzestawieniedokklient(biezacyPit);
                }
            } catch (Exception e) {
                E.e(e);
                String wiad = String.format("Błąd podczas zachowywania PIT %s za m-c:%s", biezacyPit.getUdzialowiec(), biezacyPit.getPkpirM());
                Msg.msg("e", wiad);
            }

        } else {
            Msg.msg("e", "Nie można zachować. PIT nie wypełniony");
        }
    }
    
     
    private void mailzestawieniedokklient(Pitpoz biezacyPit) {
         try {
            List<Dok> dokumentypobrane = dokDAO.zwrocBiezacegoKlientaRokMC(biezacyPit.getPodatnik1(), biezacyPit.getPkpirR(), biezacyPit.getPkpirM());
            //sortowanie dokumentów
            if (dokumentypobrane!=null) {
                for (Iterator<Dok> it = dokumentypobrane.iterator(); it.hasNext();) {
                    Dok tmpx = it.next();
                    if (tmpx.getRodzajedok().isTylkojpk()) {
                        it.remove();
                    }
                }
            }
            Collections.sort(dokumentypobrane, new Dokcomparator());
            byte[] drukujDok = PdfDok.drukujDok(dokumentypobrane, wpisView,0, null, "dokupr");
            MailOther.pkpirdokumenty(wpisView, sMTPSettingsDAO.findSprawaByDef(), drukujDok, "zestawienie_dok.pdf");
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    
    
    
     private static final String trescmaila = "<p> Dzień dobry</p> <p> Przesyłamy informacje o naliczonych kwoty zobowiązań z tytułu podatku dochodowego</p> "
            + "<p> dla firmy <span style=\"color:#008000;\">%s</span> NIP %s</p> "
            + "<p> do zapłaty/przelania w miesiącu <span style=\"color:#008000;\">%s/%s</span></p> "
            + " <table align=\"left\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width: 500px;\"> <caption> zestawienie zobowiązań</caption> <thead> <tr> "
            + "<th scope=\"col\"> lp</th> <th scope=\"col\"> tytuł</th> <th scope=\"col\"> kwota</th> </tr> </thead> <tbody> "
             + "<tr><td style=\"text-align: center;\"> 1</td><td><span >przychody od pocz rok</span></td> <td style=\"text-align: right;\"><span style=\"text-align: right;font-weight: bold\"> %.2f</span></td> </tr> "
             + "<tr><td style=\"text-align: center;\"> 2</td><td><span >koszty od pocz rok</span></td> <td style=\"text-align: right;\"><span style=\"text-align: right;font-weight: bold\"> %.2f</span></td> </tr> "
             + "<tr><td style=\"text-align: center;\"> 3</td><td><span >dochód od pocz rok</span></td> <td style=\"text-align: right;\"><span style=\"text-align: right;font-weight: bold\"> %.2f</span></td> </tr> "
            + "<tr><td style=\"text-align: center;\"> 4</td><td><span style='font-weight: bold'>podatek do zapłaty</span></td> <td style=\"text-align: right;\"><span style=\"text-align: right;font-weight: bold\"> %.2f</span></td> </tr> "
            + "</tbody> </table>"
            + " <p> &nbsp;</p> <p> &nbsp;</p> <p> &nbsp;</p><br/> "
            + "<p> Ważne! Przelew do US jedną kwotą na JEDNO indywidualne konto wskazane przez US.</p>"
            + "<p> Przypominamy o terminie płatności PIT:</p>"
            + " <p> do <span style=\"color:#008000;\">20-go</span> &nbsp; następnego miesiąca za miesiąc poprzedni</p>"
            + " <p> &nbsp;</p>";
    
    public void wyslijPit() {
        try {
            String tytuł = String.format("Taxman - zestawienie kwot podatek dochodowy za %s/%s", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            String tresc = String.format(new Locale("pl_PL"), trescmaila, wpisView.getPodatnikObiekt().getPrintnazwa(), wpisView.getPodatnikObiekt().getNip(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(),
                    biezacyPit.getPrzychodyudzial().doubleValue(), biezacyPit.getKosztyudzial().doubleValue(), biezacyPit.getPodstawa(),biezacyPit.getDozaplaty().doubleValue());
            MaiManager.mailManagerZUSPIT(wpisView.getPodatnikObiekt().getEmail(), tytuł, tresc, wpisView.getUzer().getEmail(), null, sMTPSettingsDAO.findSprawaByDef());
            msg.Msg.msg("Wysłano do klienta informacje o podatku");
        } catch (Exception e){
            
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
        wybranyudzialowiec = null;
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

    public Pitpoz skumulujpity(String mcDo, PodatnikUdzialy udzialowiec) {
        Pitpoz tmp = new Pitpoz("zus");
        try {
            Collection c = pitDAO.findPitPod(wpisView.getRokWpisu().toString(), wpisView.getPodatnikWpisu(), wybranacechadok);
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
                if (miesiacPituPobranego <= poprzednimc && tmpX.getUdzialowiec().equals(udzialowiec.getNazwiskoimie())) {
                    if (tmpX.getNaleznazal().signum() == 1) {
                        tmp.setNalzalodpoczrok(tmp.getNalzalodpoczrok().add(tmpX.getNaleznazal()));
                    }
                }
                if (tmpX.getPkpirM().equals(starymcS) && tmpX.getUdzialowiec().equals(udzialowiec.getNazwiskoimie())) {
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

    private BigDecimal obliczprzychod(Pitpoz pitpoz) {
        try {
            BigDecimal suma = new BigDecimal(0);
            String selekcja = wpisView.getMiesiacWpisu();
            int granica = Mce.getMiesiacToNumber().get(selekcja);
            for (WierszPkpir p : zebranieMcy) {
                if (p.getId() < granica) {
                    suma = suma.add(BigDecimal.valueOf(p.getRazemprzychody()));
                } else if (p.getId() == granica) {
                    suma = suma.add(BigDecimal.valueOf(p.getRazemprzychody()));
                    pitpoz.setPrzychodymc(Z.z(p.getRazemprzychody()));
                } else {
                    break;
                }
            }
            return suma;
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie było w tym roku żadnych przychodów");
            return new BigDecimal(BigInteger.ZERO);
        }
    }
    
    private double obliczprzychodDRA(String mc) {
        try {
            double suma = 0.0;
            int granica = Mce.getMiesiacToNumber().get(mc);
            for (WierszPkpir p : zebranieMcy) {
                if (p.getId() == granica) {
                    suma = p.getRazemprzychody();
                    break;
                }
            }
            return suma;
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie było w tym roku żadnych przychodów");
            return 0.0;
        }
    }

    private BigDecimal obliczkoszt(Pitpoz pitpoz) {
        try {
            BigDecimal suma = new BigDecimal(0);
            String selekcja = wpisView.getMiesiacWpisu();
            int granica = Mce.getMiesiacToNumber().get(selekcja);
            for (WierszPkpir p : zebranieMcy) {
                if (p.getId() < granica) {
                    suma = suma.add(BigDecimal.valueOf(p.getRazemkoszty()));
                } else if (p.getId() == granica) {
                    suma = suma.add(BigDecimal.valueOf(p.getRazemkoszty()));
                    pitpoz.setKosztymc(Z.z(p.getRazemkoszty()));
                } else {
                    break;
                }
            }
            return suma;
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie było w tym roku żadnych kosztów");
            return new BigDecimal(BigInteger.ZERO);
        }
    }
    
     private double obliczkosztDRA(String mc) {
        try {
            double suma = 0.0;
            int granica = Mce.getMiesiacToNumber().get(mc);
            for (WierszPkpir p : zebranieMcy) {
                if (p.getId() == granica) {
                    suma = p.getRazemkoszty();
                    break;
                }
            }
            return suma;
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie było w tym roku żadnych kosztów");
            return 0.0;
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
            PdfZestRok.drukuj(wpisView, this);
        } catch (Exception e) {
            E.e(e);

        }
    }

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

    public WierszPkpir getWrzesien() {
        return wrzesien;
    }

    public void setWrzesien(WierszPkpir wrzesien) {
        this.wrzesien = wrzesien;
    }

    public PodatnikUdzialy getWybranyudzialowiec() {
        return wybranyudzialowiec;
    }

    public void setWybranyudzialowiec(PodatnikUdzialy wybranyudzialowiec) {
        this.wybranyudzialowiec = wybranyudzialowiec;
    }


    public List<PodatnikUdzialy> getListawybranychudzialowcow() {
        return listawybranychudzialowcow;
    }

    public void setListawybranychudzialowcow(List<PodatnikUdzialy> listawybranychudzialowcow) {
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

    private int sprawdzczyjestpitwpoprzednimmiesiacu() {
        if (pierwszypitwroku == true) {
            return 0;
        }
        if (wpisView.isMc0kw1()) {
            if (!wpisView.getMiesiacWpisu().equals("03") && wybranyudzialowiec!=null) {
                int numermiesiaca = Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu());
                String numermiesiacaS = Mce.getNumberToMiesiac().get(numermiesiaca - 3);
                try {
                    Pitpoz poprzednipit = pitDAO.find(wpisView.getRokWpisuSt(), numermiesiacaS, wpisView.getPodatnikWpisu(), wybranyudzialowiec.getNazwiskoimie(), wybranacechadok);
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
            if (!wpisView.getMiesiacWpisu().equals("01") && wybranyudzialowiec!=null) {
                try {
                    Pitpoz poprzednipit = pitDAO.find(wpisView.getRokWpisuSt(), wpisView.getMiesiacUprzedni(), wpisView.getPodatnikWpisu(), wybranyudzialowiec.getNazwiskoimie(), wybranacechadok);
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

    public CechazapisuDAOfk getCechazapisuDAOfk() {
        return cechazapisuDAOfk;
    }

    public void setCechazapisuDAOfk(CechazapisuDAOfk cechazapisuDAOfk) {
        this.cechazapisuDAOfk = cechazapisuDAOfk;
    }

    public List<Cechazapisu> getPobranecechypodatnik() {
        return pobranecechypodatnik;
    }

    public void setPobranecechypodatnik(List<Cechazapisu> pobranecechypodatnik) {
        this.pobranecechypodatnik = pobranecechypodatnik;
    }

    public Cechazapisu getWybranacechadok() {
        return wybranacechadok;
    }

    public void setWybranacechadok(Cechazapisu wybranacechadok) {
        this.wybranacechadok = wybranacechadok;
    }

    public WierszPkpir getStyczen() {
        return styczen;
    }

    public void setStyczen(WierszPkpir styczen) {
        this.styczen = styczen;
    }

    public WierszPkpir getLuty() {
        return luty;
    }

    public void setLuty(WierszPkpir luty) {
        this.luty = luty;
    }

    public WierszPkpir getMarzec() {
        return marzec;
    }

    public void setMarzec(WierszPkpir marzec) {
        this.marzec = marzec;
    }

    public WierszPkpir getKwiecien() {
        return kwiecien;
    }

    public void setKwiecien(WierszPkpir kwiecien) {
        this.kwiecien = kwiecien;
    }

    public WierszPkpir getMaj() {
        return maj;
    }

    public void setMaj(WierszPkpir maj) {
        this.maj = maj;
    }

    public WierszPkpir getCzerwiec() {
        return czerwiec;
    }

    public void setCzerwiec(WierszPkpir czerwiec) {
        this.czerwiec = czerwiec;
    }

    public WierszPkpir getLipiec() {
        return lipiec;
    }

    public void setLipiec(WierszPkpir lipiec) {
        this.lipiec = lipiec;
    }

    public WierszPkpir getSierpien() {
        return sierpien;
    }

    public void setSierpien(WierszPkpir sierpien) {
        this.sierpien = sierpien;
    }

    public WierszPkpir getPazdziernik() {
        return pazdziernik;
    }

    public void setPazdziernik(WierszPkpir pazdziernik) {
        this.pazdziernik = pazdziernik;
    }

    public WierszPkpir getListopad() {
        return listopad;
    }

    public void setListopad(WierszPkpir listopad) {
        this.listopad = listopad;
    }

    public WierszPkpir getGrudzien() {
        return grudzien;
    }

    public void setGrudzien(WierszPkpir grudzien) {
        this.grudzien = grudzien;
    }

    public WierszPkpir getIpolrocze() {
        return Ipolrocze;
    }

    public void setIpolrocze(WierszPkpir Ipolrocze) {
        this.Ipolrocze = Ipolrocze;
    }

    public WierszPkpir getIIpolrocze() {
        return IIpolrocze;
    }

    public void setIIpolrocze(WierszPkpir IIpolrocze) {
        this.IIpolrocze = IIpolrocze;
    }

    public WierszPkpir getRok() {
        return rok;
    }

    public void setRok(WierszPkpir rok) {
        this.rok = rok;
    }

    public List<Pitpoz> getPitydlapita() {
        return pitydlapita;
    }

    public void setPitydlapita(List<Pitpoz> pitydlapita) {
        this.pitydlapita = pitydlapita;
    }

    public double getZus51zrekivalue() {
        return zus51zrekivalue;
    }

    public void setZus51zrekivalue(double zus51zrekivalue) {
        this.zus51zrekivalue = zus51zrekivalue;
    }


public void nowypit() {
    Msg.dP();
    List<Podatnik> podatnicy = podatnikDAO.findPodatnikNieFK();
    Integer rok = 2022;
    String rokS = "2022";
    String mc = "01";
    int i = 1;
    for (Podatnik p :podatnicy) {
        wpisView.setPodatnikObiekt(p);
        wpisView.setRokWpisu(rok);
        wpisView.setRokWpisuSt(rokS);
        wpisView.setMiesiacWpisu(mc);
        wpisView.pobierzOpodatkowanie();
        wpisView.naniesDaneDoWpis();
        System.out.println("podatnik "+p.getPrintnazwa());
        try {
            PodatnikOpodatkowanieD podatniopodatkowanie = wpisView.zwrocFormaOpodatkowania(rokS);
            String opodatkowanie = podatniopodatkowanie.getFormaopodatkowania();
            if (!opodatkowanie.contains("ryczałt")) {
                boolean mc0kw1 = podatniopodatkowanie.isMc0kw1();
                if (mc0kw1) {
                    for (String mies : Mce.getMiesiaceGranica("08")) {
                        mc =mies;
                        if (mc.equals("01")) {
                            pierwszypitwroku = true;
                        } else {
                            pierwszypitwroku = false;
                        }
                        if (czynaliczacpodatek(mc, mc0kw1)==false) {
                            wpisView.setMiesiacWpisu(mc);
                            listawybranychudzialowcow = podatnikUdzialyDAO.findUdzialyPodatnik(p);
                            for (PodatnikUdzialy r :listawybranychudzialowcow) {
                                wybranyudzialowiec = r;
                                obliczPit();
                                zachowajPit();
                            }
                        }
                    }
                }
                i++;
            }
        } catch (Exception e){}
    //        if (i==10) {
    //            break;
    //        }
    }
    Msg.dP();
    System.out.println("Koniec");
}

    
    

}
