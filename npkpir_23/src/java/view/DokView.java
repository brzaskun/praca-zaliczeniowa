/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.Kolmn;
import beansDok.ListaEwidencjiVat;
import beansDok.VAT;
import beansFK.DokFKBean;
import beansFK.TabelaNBPBean;
import beansFaktura.FakturaBean;
import beansRegon.SzukajDaneBean;
import beansSrodkiTrwale.SrodkiTrwBean;
import comparator.Rodzajedokcomparator;
import dao.AmoDokDAO;
import dao.DokDAO;
import dao.EvewidencjaDAO;
import dao.InwestycjeDAO;
import dao.KlienciDAO;
import dao.OstatnidokumentDAO;
import dao.PodatnikDAO;
import dao.PodatnikOpodatkowanieDDAO;
import dao.RodzajedokDAO;
import dao.SrodkikstDAO;
import dao.StornoDokDAO;
import dao.UzDAO;
import dao.WpisDAO;
import daoFK.CechazapisuDAOfk;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import data.Data;
import embeddable.EwidencjaAddwiad;
import embeddable.Mce;
import embeddable.PanstwaMap;
import entity.Amodok;
import entity.Dok;
import entity.EVatwpis1;
import entity.Evewidencja;
import entity.Inwestycje;
import entity.Inwestycje.Sumazalata;
import entity.Klienci;
import entity.KlienciSuper;
import entity.Kolumna1Rozbicie;
import entity.KwotaKolumna1;
import entity.Ostatnidokument;
import entity.Podatnik;
import entity.Rodzajedok;
import entity.SrodekTrw;
import entity.Srodkikst;
import entity.StornoDok;
import entityfk.Cechazapisu;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import error.E;
import gus.GUSView;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import msg.Msg;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import params.Params;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "DokumentView")
@ViewScoped
public class DokView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private Dok selDokument;
    @Inject
    private Dok wysDokument;
    @Inject
    private Klienci selectedKontr;
    @Inject
    private Srodkikst srodekkategoria;
    @Inject
    private AmoDokDAO amoDokDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private SrodkikstDAO srodkikstDAO;
    @Inject
    private OstatnidokumentDAO ostatnidokumentDAO;
    @Inject
    private WpisDAO wpisDAO;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private KlienciDAO klDAO;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private StornoDokDAO stornoDokDAO;
    @Inject
    private InwestycjeDAO inwestycjeDAO;
    @Inject
    private TabelanbpDAO tabelanbpDAO;

    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
//    @ManagedProperty(value = "#{KlView}")
//    private KlView klView;
    @ManagedProperty(value = "#{srodkiTrwaleView}")
    private SrodkiTrwaleView sTRView;
    @ManagedProperty(value = "#{STRTableView}")
    private STRTabView sTRTableView;
    @ManagedProperty(value = "#{DokTabView}")
    private DokTabView dokTabView;
    @ManagedProperty(value = "#{gUSView}")
    private GUSView gUSView;
    @Inject
    private ListaEwidencjiVat listaEwidencjiVat;
    /*Środki trwałe*/
    @Inject
    private SrodekTrw selectedSTR;
    /*Środki trwałe*/
    private boolean pokazEST;//pokazuje wykaz srodkow dla sprzedazy
    private List<Cechazapisu> pobranecechypodatnik;
    private Cechazapisu cechadomyslna;
    private Cechazapisu cechastala;
    @Inject
    private Srodkikst srodekkategoriawynik;
    //automatyczne ksiegowanie Storna
    private boolean rozliczony;
    private List<Rodzajedok> rodzajedokKlienta;
    //przechowuje ostatni dokumnet
    private String typdokumentu;
    private String typpoprzedniegodokumentu;
    private boolean nieVatowiec;
    //pobieram wykaz KŚT
    /**
     * Lista gdzie przechowywane są wartości netto i opis kolumny wporwadzone w
     * formularzy na stronie add_wiad.xhtml
     */
    //private List<KwotaKolumna1> selDokument.getListakwot1();
    /**
     * Lista gdzie przechowywane są wartości ewidencji vat wprowadzone w
     * formularzy na stronie add_wiad.xhtml
     */
    private List<EwidencjaAddwiad> ewidencjaAddwiad;
    private double sumbrutto;
    private int liczbawierszy;
    private List<String> kolumny;
    public boolean renderujwysz;
    @Inject
    private Klienci selectedKlient;
    @Inject
    private PanstwaMap panstwaMapa;
    private boolean ukryjEwiencjeVAT;//ukrywa ewidencje VAT
    @Inject
    private Evewidencja nazwaEwidencjiwPoprzednimDok;
    @Inject
    private PodatnikOpodatkowanieDDAO podatnikOpodatkowanieDDAO;
    @Inject
    private CechazapisuDAOfk cechazapisuDAOfk;
    private String symbolwalutydowiersza;
    private List<Waluty> wprowadzonesymbolewalut;
    @Inject
    private WalutyDAOfk walutyDAOfk;
    private String symbolWalutyNettoVat;
    private Klienci biezacyklientdodok;
    private List<Kolumna1Rozbicie> kolumna1rozbicielista;
    @Inject
    private Kolumna1Rozbicie sumarozbicie;
    private double stawkaVATwPoprzednimDok;
    private Tabelanbp domyslatabela;
    

    public DokView() {
        setWysDokument(null);
        wpisView = new WpisView();
        ewidencjaAddwiad = new ArrayList<>();
        liczbawierszy = 1;
        stawkaVATwPoprzednimDok = 0.0;
        this.wprowadzonesymbolewalut = new ArrayList<>();
        this.kolumna1rozbicielista = new ArrayList<>();
        this.kolumna1rozbicielista.add(new Kolumna1Rozbicie());
        this.pobranecechypodatnik = new ArrayList<>();
    }
    
    private void obsluzcechydokumentu() {
        //usuwamy z listy dostepnych cech te, ktore sa juz przyporzadkowane do dokumentu
        if (pobranecechypodatnik != null) {
            List<Cechazapisu> cechyuzyte = null;
            if (selDokument != null) {
                if (selDokument.getCechadokumentuLista() == null) {
                    cechyuzyte = new ArrayList<>();
                } else {
                    cechyuzyte = selDokument.getCechadokumentuLista();
                }
                for (Cechazapisu c : cechyuzyte) {
                    pobranecechypodatnik.remove(c);
                }
            }
            RequestContext.getCurrentInstance().update("formCH");
        }
    }

    public void dodajwierszpkpir() {
        if (liczbawierszy < 4) {
            KwotaKolumna1 p = new KwotaKolumna1();
            p.setNetto(0.0);
            p.setNazwakolumny("nie ma");
            selDokument.getListakwot1().add(p);
            liczbawierszy++;
            if (liczbawierszy == 2) {
                selDokument.getListakwot1().get(0).setNazwakolumny("koszty ub.zak.");
                selDokument.getListakwot1().get(1).setNazwakolumny("zakup tow. i mat.");
            }
        } else {
            Msg.msg("w", "Osiągnięto maksymalną liczbę wierszy", "dodWiad:mess_add");
        }
    }

    public void usunwierszpkpir() {
        if (liczbawierszy > 1) {
            int wielkosctabeli = selDokument.getListakwot1().size();
            selDokument.getListakwot1().remove(wielkosctabeli - 1);
            liczbawierszy--;
        } else {
            Msg.msg("w", "Osiągnięto minimalną liczbę wierszy", "dodWiad:mess_add");
        }
    }

    @PostConstruct
    private void init() {
        rodzajedokKlienta = new ArrayList<>();
        Podatnik podX = wpisView.getPodatnikObiekt();
        symbolWalutyNettoVat = " zł";
        biezacyklientdodok = klDAO.findKlientByNip(podX.getNip());
        pobranecechypodatnik = cechazapisuDAOfk.findPodatnikOnly(wpisView.getPodatnikObiekt());
        if (pobranecechypodatnik != null && pobranecechypodatnik.size() ==1) {
            cechadomyslna = pobranecechypodatnik.get(0);
        }
        try {
            wprowadzonesymbolewalut.addAll(walutyDAOfk.findAll());
            rodzajedokKlienta.addAll(pobierzrodzajedok());
            if (!wpisView.isVatowiec()) {
                selDokument.setDokumentProsty(true);
                ukryjEwiencjeVAT = true;
            }
        } catch (Exception e) {
            E.e(e);
            String pod = "GRZELCZYK";
            podX = podatnikDAO.find(pod);
            rodzajedokKlienta.addAll(rodzajedokDAO.findListaPodatnik(podX));
        }
        //pobranie ostatniego dokumentu
        try {
            //czasami dokument ostatni jest zle zapisany, w przypadku bleduy nalezy go usunac
            wysDokument = ostatnidokumentDAO.pobierz(wpisView.getWprowadzil().getLogin());
            if (wysDokument.getEwidencjaVAT1() != null && !wysDokument.getEwidencjaVAT1().isEmpty()) {
                Iterator it = wysDokument.getEwidencjaVAT1().iterator();
                while (it.hasNext()) {
                    EVatwpis1 p = (EVatwpis1) it.next();
                    if (p.getNetto() == 0.0 && p.getVat() == 0.0) {
                        it.remove();
                    }
                }
            }
            if (wysDokument != null) {
                typdokumentu = wysDokument.getRodzajedok().getSkrot();
                selDokument.setRodzajedok(wysDokument.getRodzajedok());
                selDokument.setOpis(wysDokument.getOpis());
                wygenerujnumerkolejny();
            } else {
                this.typdokumentu = "ZZ";
                selDokument.setRodzajedok(rodzajedokDAO.find("ZZ", wpisView.getPodatnikObiekt()));
            }
        } catch (Exception e) {
            this.typdokumentu = "ZZ";
            selDokument.setRodzajedok(rodzajedokDAO.find("ZZ", wpisView.getPodatnikObiekt()));
            E.e(e);
        }
        selDokument.setKontr1(wstawKlientaDoNowegoDok());
        try {
            selDokument.setVatM(wpisView.getMiesiacWpisu());
            selDokument.setVatR(wpisView.getRokWpisuSt());
        } catch (Exception e) {
            E.e(e);
        }
        Klienci klient = klDAO.findKlientByNip(podX.getNip());
        selDokument.setKontr1(klient);
        podepnijEwidencjeVat();
        domyslatabela = DokFKBean.dodajWaluteDomyslnaDoDokumentu(walutyDAOfk, tabelanbpDAO, selDokument);
        selDokument.setTabelanbp(domyslatabela);
        RequestContext.getCurrentInstance().update("dodWiad");
        //ukrocmiesiace();

    }
    
    public void dodajcechedodokumentu(Cechazapisu c) {
        pobranecechypodatnik.remove(c);
        selDokument.getCechadokumentuLista().add(c);
        c.getDokLista().add(selDokument);
    }
    public void usuncechedodokumentu(Cechazapisu c) {
        pobranecechypodatnik.add(c);
        selDokument.getCechadokumentuLista().remove(c);
        c.getDokfkLista().remove(selDokument);
    }

    public void cechadomyslnaobsluz() {
        if (cechadomyslna != null) {
            if (selDokument.getCechadokumentuLista().contains(cechadomyslna)) {
                selDokument.getCechadokumentuLista().remove(cechadomyslna);
                cechadomyslna.getDokfkLista().remove(selDokument);
                Msg.msg("e","Usunięto oznaczenie dokumentu cechą");
            } else {
                selDokument.getCechadokumentuLista().add(cechadomyslna);
                cechadomyslna.getDokLista().add(selDokument);
                Msg.msg("Oznaczono dokument cechą");
            }
        }
    }    
    
    
    private List<Rodzajedok> pobierzrodzajedok() {
        List<Rodzajedok> rodzajedokumentow = rodzajedokDAO.findListaPodatnik(wpisView.getPodatnikObiekt());
        Collections.sort(rodzajedokumentow, new Rodzajedokcomparator());
        return rodzajedokumentow;
    }

    public void dodajklientaautomat() {
        try {
            if (selDokument.getKontr().getNpelna().equals("dodaj klienta automatycznie")) {
                Klienci dodany = SzukajDaneBean.znajdzdaneregonAutomat(selDokument.getKontr().getNip(), gUSView);
                selDokument.setKontr(dodany);
                if (!dodany.getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
                    klDAO.dodaj(dodany);
                }
                RequestContext.getCurrentInstance().update("dodWiad:acForce");
            }
        } catch (Exception e) {
            
        }
    }
    
    //edytuje ostatni dokument celem wykorzystania przy wpisie
    public void edytujdokument() {
        try {
            selDokument = ostatnidokumentDAO.pobierz(wpisView.getWprowadzil().getLogin());
            typdokumentu = selDokument.getRodzajedok().getSkrot();
            dokDAO.destroy(selDokument);
        } catch (Exception e) {
            E.e(e);
        }
        RequestContext.getCurrentInstance().update("dodWiad:wprowadzanie");
    }

       
    public void podepnijListe() {
        String transakcjiRodzaj = selDokument.getRodzajedok().getRodzajtransakcji();
        if (wpisView.isKsiegaryczalt()) {
            kolumny = Kolmn.zwrockolumny(transakcjiRodzaj);
        } else {
            kolumny = Kolmn.zwrockolumnyR(transakcjiRodzaj);
        }
        if (transakcjiRodzaj.equals("srodek trw sprzedaz")){
                    setPokazEST(true);
                    RequestContext.getCurrentInstance().update("dodWiad:panelewidencji");
            }
    }



    public void podepnijEwidencjeVat() {
//        if (selDokument.getTabelanbp() != null && !selDokument.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
//            ukryjEwiencjeVAT = true;
//            sumujnetto();
//            ewidencjaAddwiad = new ArrayList<>();
//        } else 
        if (selDokument.isDokumentProsty() && !selDokument.getRodzajedok().getSkrot().equals("IU")) {
            ukryjEwiencjeVAT = true;
            sumujnetto();
            ewidencjaAddwiad = new ArrayList<>();
        } else {
            ukryjEwiencjeVAT = false;
            String typdok = selDokument.getRodzajedok().getSkrot();
            String transakcjiRodzaj = selDokument.getRodzajedok().getRodzajtransakcji();
            if (wpisView.isVatowiec() == true || typdok.equals("IU")) {
                /*wyswietlamy ewidencje VAT*/
                List<Evewidencja> opisewidencji = new ArrayList<>();
                selDokument.setDokumentProsty(false);
                opisewidencji.addAll(listaEwidencjiVat.pobierzEvewidencje(transakcjiRodzaj));
                if (typdok.equals("UPTK")) {
                    for (Iterator<Evewidencja> it = opisewidencji.iterator(); it.hasNext();) {
                        Evewidencja p = it.next();
                        if (p.getNazwa().equals("usługi świad. poza ter.kraju art. 100 ust.1 pkt 4")) {
                            it.remove();
                        }
                    }
                } else if (typdok.equals("UPTK100")) {
                    for (Iterator<Evewidencja> it = opisewidencji.iterator(); it.hasNext();) {
                        Evewidencja p = it.next();
                        if (p.getNazwa().equals("usługi świad. poza ter.kraju")) {
                            it.remove();
                        }
                    }
                }
                double sumanetto = sumujnetto();
                Tabelanbp t = selDokument.getTabelanbp();
                if (t != null && !t.getWaluta().getSymbolwaluty().equals("PLN")) {
                    sumanetto = Z.z(sumanetto * t.getKurssredni());
                }
                ewidencjaAddwiad = new ArrayList<>();
                int k = 0;
                for (Evewidencja p : opisewidencji) {
                    EwidencjaAddwiad ewidencjaAddwiad = new EwidencjaAddwiad();
                    ewidencjaAddwiad.setLp(k++);
                    ewidencjaAddwiad.setEvewidencja(p);
                    ewidencjaAddwiad.setNetto(0.0);
                    ewidencjaAddwiad.setVat(0.0);
                    ewidencjaAddwiad.setBrutto(0.0);
                    ewidencjaAddwiad.setOpzw("op");
                    this.ewidencjaAddwiad.add(ewidencjaAddwiad);
                }
                //obliczam 23% dla pierwszego
                ewidencjaAddwiad.get(0).setNetto(sumanetto);
                if (transakcjiRodzaj.equals("WDT") || transakcjiRodzaj.equals("usługi poza ter.")
                        || transakcjiRodzaj.equals("eksport towarów") || transakcjiRodzaj.equals("odwrotne obciążenie sprzedawca")) {
                    ewidencjaAddwiad.get(0).setVat(0.0);
                } else if (selDokument.getRodzajedok().getProcentvat() != 0.0) {
                    ewidencjaAddwiad.get(0).setVat(Z.z((ewidencjaAddwiad.get(0).getNetto() * 0.23) / 2));
                    ewidencjaAddwiad.get(0).setBrutto(ewidencjaAddwiad.get(0).getNetto() + Z.z(((ewidencjaAddwiad.get(0).getNetto() * 0.23) / 2)));
                    sumbrutto = ewidencjaAddwiad.get(0).getNetto() + Z.z(ewidencjaAddwiad.get(0).getNetto() * 0.23);
                } else if (transakcjiRodzaj.equals("sprzedaz")) {
                    try {
                        String ne = nazwaEwidencjiwPoprzednimDok.getNazwa();
                        switch (ne) {
                            case "sprzedaż 23%":
                                ewidencjaAddwiad.get(0).setNetto(sumanetto);
                                ewidencjaAddwiad.get(0).setVat(Z.z(ewidencjaAddwiad.get(0).getNetto() * 0.23));
                                ewidencjaAddwiad.get(0).setBrutto(ewidencjaAddwiad.get(0).getNetto() + ewidencjaAddwiad.get(0).getVat());
                                sumbrutto = ewidencjaAddwiad.get(0).getBrutto();
                                break;
                            case "sprzedaż 8%":
                                ewidencjaAddwiad.get(0).setNetto(0.0);
                                ewidencjaAddwiad.get(1).setNetto(sumanetto);
                                ewidencjaAddwiad.get(1).setVat(Z.z(ewidencjaAddwiad.get(1).getNetto() * 0.08));
                                ewidencjaAddwiad.get(1).setBrutto(ewidencjaAddwiad.get(1).getNetto() + ewidencjaAddwiad.get(1).getVat());
                                sumbrutto = ewidencjaAddwiad.get(1).getBrutto();
                                break;
                            case "sprzedaż 5%":
                                ewidencjaAddwiad.get(0).setNetto(0.0);
                                ewidencjaAddwiad.get(2).setNetto(sumanetto);
                                ewidencjaAddwiad.get(2).setVat(Z.z(ewidencjaAddwiad.get(2).getNetto() * 0.05));
                                ewidencjaAddwiad.get(2).setBrutto(ewidencjaAddwiad.get(2).getNetto() + ewidencjaAddwiad.get(2).getVat());
                                sumbrutto = ewidencjaAddwiad.get(2).getBrutto();
                                break;
                            case "sprzedaż 0%":
                                ewidencjaAddwiad.get(0).setNetto(0.0);
                                ewidencjaAddwiad.get(3).setNetto(sumanetto);
                                ewidencjaAddwiad.get(3).setVat(0.0);
                                sumbrutto = ewidencjaAddwiad.get(3).getBrutto();
                                break;
                            case "sprzedaż zw":
                                ewidencjaAddwiad.get(0).setNetto(0.0);
                                ewidencjaAddwiad.get(4).setNetto(sumanetto);
                                ewidencjaAddwiad.get(4).setVat(0.0);
                                sumbrutto = ewidencjaAddwiad.get(4).getBrutto();
                                break;
                        }
                    } catch (Exception e) {
                        E.e(e);
                        ewidencjaAddwiad.get(0).setVat(Z.z(ewidencjaAddwiad.get(0).getNetto() * 0.23));
                        ewidencjaAddwiad.get(0).setBrutto(ewidencjaAddwiad.get(0).getNetto() + ewidencjaAddwiad.get(0).getVat());
                        sumbrutto = ewidencjaAddwiad.get(0).getBrutto();
                    }
                } else {
                    if (stawkaVATwPoprzednimDok > 0.0 && selDokument.getRodzajedok().getSkrot().equals(typpoprzedniegodokumentu)) {
                        ewidencjaAddwiad.get(0).setVat((ewidencjaAddwiad.get(0).getNetto() * stawkaVATwPoprzednimDok));
                    } else {
                        ewidencjaAddwiad.get(0).setVat(Z.z(ewidencjaAddwiad.get(0).getNetto() * 0.23));
                    }
                    ewidencjaAddwiad.get(0).setBrutto(ewidencjaAddwiad.get(0).getNetto() + ewidencjaAddwiad.get(0).getVat());
                    sumbrutto = ewidencjaAddwiad.get(0).getBrutto();
                }
                nazwaEwidencjiwPoprzednimDok = new Evewidencja();
            }
        }
    }

    private double sumujnetto() {
        sumbrutto = 0.0;
        boolean czyjestinnawaluta = selDokument.getTabelanbp() != null && !selDokument.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN");
        for (KwotaKolumna1 p : selDokument.getListakwot1()) {
            if (czyjestinnawaluta) {
                sumbrutto += p.getNettowaluta();
            } else {
                sumbrutto += p.getNetto();
            }
        }
        return sumbrutto;
    }

    public void updatenetto(EwidencjaAddwiad e) {
        String skrotRT = (String) Params.params("dodWiad:rodzajTrans");
        int lp = e.getLp();
        double stawkavat = e.getEvewidencja().getStawkavat();
        try {
            e.setVat(Z.z(e.getNetto() * stawkavat/100));
        } catch (Exception ex) {
            Rodzajedok r = rodzajedokDAO.find(skrotRT, wpisView.getPodatnikObiekt());
        }
        e.setBrutto(e.getNetto() + e.getVat());
        sumbruttoAddwiad();
        String update = "dodWiad:tablicavat:" + lp + ":vat";
        RequestContext.getCurrentInstance().update(update);
        update = "dodWiad:tablicavat:" + lp + ":brutto";
        RequestContext.getCurrentInstance().update(update);
        update = "dodWiad:sumbrutto";
        RequestContext.getCurrentInstance().update(update);
        String activate = "document.getElementById('dodWiad:tablicavat:" + lp + ":vat_input').select();";
        RequestContext.getCurrentInstance().execute(activate);
    }

    public void updatevat(EwidencjaAddwiad e) {
        try {
            int lp = e.getLp();
            double vat = 0.0;
            try {
                String ne = e.getEvewidencja().getNazwa();
                double n = Math.abs(e.getNetto());
                switch (ne) {
                    case "sprzedaż 23%":
                        vat = (n * 0.23) + 0.02;
                        break;
                    case "sprzedaż 8%":
                        vat = (n * 0.08) + 0.02;
                        break;
                    case "sprzedaż 5%":
                        vat = (n * 0.05) + 0.02;
                        break;
                    case "sprzedaż 0%":
                    case "sprzedaż zw":
                        break;
                    default:
                        vat = (n * 0.23) + 0.02;
                        break;
                }
            } catch (Exception e2) {

            }
            if (Math.abs(e.getVat()) > vat) {
                e.setVat(0.0);
                e.setBrutto(0.0);
                Msg.msg("e", "VAT jest za duży od wyliczonej kwoty");
                String update = "dodWiad:tablicavat:" + lp + ":vat";
                RequestContext.getCurrentInstance().update(update);
                update = "dodWiad:tablicavat:" + lp + ":brutto";
                RequestContext.getCurrentInstance().update(update);
                update = "dodWiad:sumbrutto";
                RequestContext.getCurrentInstance().update(update);
            } else {
                ewidencjaAddwiad.get(lp).setBrutto(e.getNetto() + e.getVat());
                String skrotRT = (String) Params.params("dodWiad:rodzajTrans");
                Rodzajedok r = rodzajedokDAO.find(skrotRT, wpisView.getPodatnikObiekt());
                if (r.getProcentvat() != 0.0) {
                    sumbrutto = e.getNetto() + (e.getVat() * 2);
                } else {
                    sumbruttoAddwiad();
                }
                String update = "dodWiad:tablicavat:" + lp + ":brutto";
                RequestContext.getCurrentInstance().update(update);
                update = "dodWiad:sumbrutto";
                RequestContext.getCurrentInstance().update(update);
            }
        } catch (Exception ex) {
        }
    }

    private void sumbruttoAddwiad() {
        sumbrutto = 0.0;
        for (EwidencjaAddwiad p : ewidencjaAddwiad) {
            sumbrutto += p.getBrutto();
        }
    }

    public void pobierzwprowadzonynumer() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String wprowadzonynumer = params.get("dodWiad:numerwlasny");
        selDokument.setNrWlDk(wprowadzonynumer);
    }
// powoduje zamieszanie bo przy zmianie rodzaju dokumentu niszczy celowo ustawionego wlasciwego kontrahenta, taki nadmiar kontroli
//    public void wybranydokument(ValueChangeEvent e) {
//        String typdokum = (String) e.getNewValue();
//        typdokumentu = typdokum;
//        if (typdokum.equals("AMO") || typdokum.equals("PK") || typdokum.equals("LP") || typdokum.equals("ZUS")) {
//            Klienci klient = klDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
//            selDokument.setKontr1(klient);
//        }
//        RequestContext.getCurrentInstance().update("dodWiad:acForce");
//    }

    public void wygenerujnumerkolejnyonblur() {
        if (selDokument.getNrWlDk() == null || selDokument.getNrWlDk().equals("")) {
            wygenerujnumerkolejny();
        }
    }
    
    public void wygenerujnumerkolejny() {
        String nowynumer = "";
        String wzorzec = selDokument.getRodzajedok().getWzorzec();
        if (wzorzec != null && !wzorzec.equals("")) {
            try {
                nowynumer = FakturaBean.uzyjwzorcagenerujnumerDok(wzorzec, selDokument.getRodzajedok().getSkrot(), wpisView, dokDAO);
            } catch (Exception e) {
                nowynumer = wzorzec;
            }
        }
        renderujwyszukiwarke(selDokument.getRodzajedok());
        renderujtabele(selDokument.getRodzajedok());
        selDokument.setNrWlDk(nowynumer);
        RequestContext.getCurrentInstance().update("dodWiad:numerwlasny");
    }

    private void renderujwyszukiwarke(Rodzajedok rodzajdok) {
        if (rodzajdok.getSkrotNazwyDok().equals("OT")) {
            setRenderujwysz(true);
        } else {
            setRenderujwysz(false);
        }
        RequestContext.getCurrentInstance().update("dodWiad:panelwyszukiwarki");
        RequestContext.getCurrentInstance().update("dodWiad:nowypanelsrodki");
    }

    private void renderujtabele(Rodzajedok rodzajdok) {
        if (rodzajdok.getSkrotNazwyDok().equals("OTS")) {
            sTRTableView.init();
            setPokazEST(true);
        } else {
            setPokazEST(false);
        }
        RequestContext.getCurrentInstance().update("dodWiad:panelewidencji");
    }

    /**
     * NE zmienia wlasciwosci pol wprowadzajacych dane kontrahenta
     */
    public void dokumentEwidencjaVAT(ValueChangeEvent e) {
        Boolean toJestdokumentProsty = (Boolean) e.getNewValue();
        if (toJestdokumentProsty) {
            sumbrutto = 0.0;
            sumujnetto();
            selDokument.setEwidencjaVAT1(null);
            ewidencjaAddwiad = new ArrayList<>();
            ukryjEwiencjeVAT = true;
        } else {
            podepnijEwidencjeVat();
        }
    }

    /**
     * Dodawanie dokumentu wprowadzonego w formularzu na stronie add_wiad.html
     */
    public void dodaj(int rodzajdodawania) {
        try {
            if (selDokument.getSymbolinwestycji() != null && selDokument.getSymbolinwestycji().equals("wybierz") && typdokumentu.equals("IN")) {
                Msg.msg("e", "Błąd. Nie wybrano nazwy inwestycji podczas wprowadzania dokumentu inwestycyjnego. Dokument niewprowadzony");
                return;
            }
        } catch (Exception e) {
            E.e(e);
        }
        selDokument.setWprowadzil(wpisView.getWprowadzil().getLogin());
        selDokument.setPkpirM(wpisView.getMiesiacWpisu());
        selDokument.setPkpirR(wpisView.getRokWpisu().toString());
        selDokument.setPodatnik(wpisView.getPodatnikObiekt());
        if (selDokument.getInwestycja() != null) {
            selDokument.setSymbolinwestycji(selDokument.getInwestycja().getSymbol());
        } else {
            selDokument.setSymbolinwestycji(null);
        }
        VAT.zweryfikujokresvat(selDokument);
        Double kwotavat = 0.0;
        try {
            if (wpisView.isVatowiec() || (selDokument.isDokumentProsty() == false)) {
                List<EVatwpis1> ewidencjeDokumentu = new ArrayList<>();
                for (EwidencjaAddwiad p : ewidencjaAddwiad) {
                    if (p.getNetto() != 0.0 || p.getVat() != 0.0) {
                        EVatwpis1 eVatwpis = new EVatwpis1();
                        eVatwpis.setEwidencja(p.getEvewidencja());
                        eVatwpis.setNetto(p.getNetto());
                        eVatwpis.setVat(p.getVat());
                        eVatwpis.setEstawka(p.getOpzw());
                        eVatwpis.setDok(selDokument);
                        eVatwpis.setMcEw(selDokument.getVatM());
                        eVatwpis.setRokEw(selDokument.getVatR());
                        ewidencjeDokumentu.add(eVatwpis);
                        //to musi być bo inaczej nie obliczy kwoty vat;
                        kwotavat += Z.z(p.getVat());
                    }
                }
                if (ewidencjeDokumentu.isEmpty()) {
                    selDokument.setEwidencjaVAT1(null);
                } else {
                    selDokument.setEwidencjaVAT1(ewidencjeDokumentu);
                }
            } else {
                selDokument.setEwidencjaVAT1(null);
            }
            selDokument.setStatus("bufor");
            //Usuwa puste kolumy w przypadku bycia takiej po skopiowaniu poprzednio zaksiegowanego dokumentu
            double kwotanetto = 0.0;
            for (Iterator<KwotaKolumna1> it = selDokument.getListakwot1().iterator();it.hasNext();) {
                KwotaKolumna1 p = it.next();
                if (p.getNetto() == 0.0) {
                    it.remove();
                } else {
                    p.setDok(selDokument);
                    kwotanetto += p.getNetto();
                }
            }
            selDokument.setNetto(Z.z(kwotanetto));
            selDokument.setOpis(selDokument.getOpis().toLowerCase());
            //dodaje kolumne z dodatkowym vatem nieodliczonym z faktur za paliwo
            if (selDokument.getRodzajedok().getProcentvat() != 0.0 && !wpisView.getRodzajopodatkowania().contains("ryczałt") && kwotanetto != 0.0) {
                KwotaKolumna1 kwotaKolumna = new KwotaKolumna1(Z.z(kwotavat), "poz. koszty");
                kwotaKolumna.setDok(selDokument);
                kwotanetto = Z.z(kwotanetto + kwotaKolumna.getNetto());
                selDokument.getListakwot1().add(kwotaKolumna);
            }
            //koniec obliczania netto to bylo potrzebne do 2016
            //dodajdatydlaStorno();
//            if (selDokument.getRozliczony() == true) {
//                Rozrachunek1 rozrachunekx = new Rozrachunek1(selDokument.getTerminPlatnosci(), kwotanetto, 0.0, selDokument.getWprowadzil(), new Date());
//                rozrachunekx.setDok(selDokument);
//                ArrayList<Rozrachunek1> lista = new ArrayList<>();
//                lista.add(rozrachunekx);
//                selDokument.setRozrachunki1(lista);
//            }
            //dodaje zaplate faktury gdy faktura jest uregulowana
            double kwotabrutto = 0.0;
            kwotabrutto = Z.z(kwotanetto + kwotavat);
            selDokument.setBrutto(kwotabrutto);
            selDokument.setUsunpozornie(false);
            //jezeli jest edytowany dokument to nie dodaje a edytuje go w bazie danych
            Rodzajedok rodzajdokdoprzeniesienia = selDokument.getRodzajedok();
            if (rodzajdodawania == 1) {
                sprawdzCzyNieDuplikat(selDokument);
                if (cechastala != null) {
                    dodajcechedodokumentu(cechastala);
                }
                dokDAO.dodaj(selDokument);
                //wpisywanie do bazy ostatniego dokumentu
                Ostatnidokument temp = new Ostatnidokument();
                temp.setUzytkownik(wpisView.getWprowadzil().getLogin());
                temp.setDokument(selDokument);
                ostatnidokumentDAO.edit(temp);
                try {
                    String probsymbolu = selDokument.getSymbolinwestycji();
                    if (probsymbolu != null && !probsymbolu.equals("wybierz")) {
                        aktualizujInwestycje(selDokument);
                    }
                } catch (Exception e) {
                    E.e(e);
                }
                wysDokument = ostatnidokumentDAO.pobierz(selDokument.getWprowadzil());
                liczbawierszy = 1;
                RequestContext.getCurrentInstance().update("zobWiad:ostatniUzytkownik");
                Msg.msg("i", "Nowy dokument zachowany" + selDokument.toString2());
                /**
                 * resetowanie pola do wpisywania kwoty netto
                 */
                //selDokument.getListakwot1().clear(); to jest niepoczebne
            } else {
                selDokument.setSprawdzony(0);
                dokDAO.edit(selDokument);
            }

            //robienie srodkow trwalych
            if (selectedSTR.getStawka() != null) {
                dodajSrodekTrwaly();
                srodekkategoria = new Srodkikst();
            }
            if (rodzajdodawania == 1) {
                selDokument = new Dok();
                selDokument.setVatM(wpisView.getMiesiacWpisu());
                selDokument.setVatR(wpisView.getRokWpisuSt());
                selDokument.setKontr1(wstawKlientaDoNowegoDok());
                selDokument.setTabelanbp(domyslatabela);
                selDokument.setWalutadokumentu(domyslatabela.getWaluta());
                selDokument.setRodzajedok(rodzajdokdoprzeniesienia);
                selectedSTR = new SrodekTrw();
                if (!wpisView.isVatowiec() && !selDokument.getRodzajedok().getSkrot().equals("IU")) {
                    selDokument.setDokumentProsty(true);
                    ewidencjaAddwiad.clear();
                    ukryjEwiencjeVAT = true;
                    RequestContext.getCurrentInstance().update("dodWiad:tablicavat");
                } else {
                    podepnijEwidencjeVat();
                }
                selDokument.setOpis(wysDokument.getOpis());
                setRenderujwysz(false);
                setPokazEST(false);
                //wygenerujnumerkolejny();nie potrzebne jest generowane w xhtml
                int i = 0;
                try {
                    if (wysDokument.getListakwot1() != null) {
                        for (KwotaKolumna1 p : wysDokument.getListakwot1()) {
                            if (selDokument.getListakwot1().size() < i + 2) {
                                selDokument.getListakwot1().get(i++).setNazwakolumny(p.getNazwakolumny());
                            }
                        }
                    }
                } catch (Exception e) {

                }
            } else {
                selectedSTR = new SrodekTrw();
                ewidencjaAddwiad.clear();
                RequestContext.getCurrentInstance().update("dodWiad:tablicavat");
                setRenderujwysz(false);
                setPokazEST(false);
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Dokument nie został zaksiegowany " + e.getMessage() + " " + e.getStackTrace().toString());
        }
    }

    private Klienci wstawKlientaDoNowegoDok() {
        Klienci nowyklient = null;
        if (wysDokument != null) {
            nowyklient = wysDokument.getKontr1();
        } else {
            nowyklient = biezacyklientdodok;
        }
        return nowyklient;
    }

    private void dodajSrodekTrwaly() {
        double vat = 0.0;
        //dla dokumentu bez vat bedzie blad
        try {
            for (EVatwpis1 p : selDokument.getEwidencjaVAT1()) {
                vat += p.getVat();
            }
        } catch (Exception e) {
            E.e(e);
        }
        try {
            selectedSTR.setNetto(selDokument.getNetto());
            selectedSTR.setVat(vat);
            selectedSTR.setDatazak(selDokument.getDataWyst());
            selectedSTR.setUmorzeniezaksiegowane(Boolean.FALSE);
            selectedSTR.setNrwldokzak(selDokument.getNrWlDk());
            selectedSTR.setZlikwidowany(0);
            selectedSTR.setDatasprzedazy("");
            dodajSTR();

        } catch (Exception e) {
            E.e(e);
        }
    }

    private Double extractDouble(String wiersz) {
        String prices = wiersz.replaceAll("\\s", "");
        Pattern p = Pattern.compile("(-?(\\d+(?:\\.\\d+)))");
        Matcher m = p.matcher(prices);
        while (m.find()) {
            return Double.parseDouble(m.group());
        }
        return 0.0;
    }

    //dodaje wyliczone daty platnosci dla obliczenia pozniej czy trzeba stornowac
    public void dodajdatydlaStorno() throws ParseException {
        String data;
        switch (wpisView.getMiesiacWpisu()) {
            case "01":
            case "03":
            case "05":
            case "07":
            case "08":
            case "10":
            case "12":
                data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-31";
                break;
            case "02":
                data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-28";
                break;
            default:
                data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-30";
                break;
        }
        String dataWyst = selDokument.getDataWyst();
        String dataPlat = selDokument.getTerminPlatnosci();
        Calendar c = Calendar.getInstance();
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datawystawienia = formatter.parse(dataWyst);
        Date terminplatnosci = formatter.parse(dataPlat);
        Date dataujeciawkosztach = formatter.parse(data);
        if (roznicaDni(datawystawienia, terminplatnosci) == true) {
            c.setTime(terminplatnosci);
            c.add(Calendar.DAY_OF_MONTH, 30);
            String nd30 = formatter.format(c.getTime());
            selDokument.setTermin30(nd30);
            selDokument.setTermin90("");
        } else {
            c.setTime(dataujeciawkosztach);
            c.add(Calendar.DAY_OF_MONTH, 90);
            String nd90 = formatter.format(c.getTime());
            selDokument.setTermin90(nd90);
            selDokument.setTermin30("");
        }
        c.setTime(terminplatnosci);
        c.add(Calendar.DAY_OF_MONTH, 150);
        String nd150 = formatter.format(c.getTime());
        selDokument.setTermin150(nd150);
    }

    private boolean roznicaDni(Date date_od, Date date_do) {
        long x = date_do.getTime();
        long y = date_od.getTime();
        long wynik = Math.abs(x - y);
        wynik = wynik / (1000 * 60 * 60 * 24);
        if (wynik <= 61) {
            return true;
        } else {
            return false;
        }
    }
    //generowanie dokumentu amortyzacji

    public void dodajNowyWpisAutomatyczny() {
        double kwotaumorzenia = 0.0;
        Amodok amodokBiezacy = amoDokDAO.amodokBiezacy(wpisView.getPodatnikWpisu(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisu());
        if (amodokBiezacy == null) {
            Msg.msg("e", "Brak naliczeń odpisów za bieżący miesiąc. Nie można utworzyć dokumentu AMO");
            return;
        }
        Dok znalezionyBiezacy = dokDAO.findDokMC("AMO", wpisView.getPodatnikObiekt(), String.valueOf(amodokBiezacy.getAmodokPK().getRok()), Mce.getNumberToMiesiac().get(amodokBiezacy.getAmodokPK().getMc()));
        if (znalezionyBiezacy == null) {
            String[] poprzedniOkres = Data.poprzedniOkres(wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            Amodok amodokPoprzedni = amoDokDAO.amodokBiezacy(wpisView.getPodatnikWpisu(), poprzedniOkres[0], Integer.parseInt(poprzedniOkres[1]));
            //wyliczam kwote umorzenia
            kwotaumorzenia = SrodkiTrwBean.sumujumorzenia(amodokBiezacy.getPlanumorzen());
            try {
                if (amodokPoprzedni != null) {
                    if (amodokPoprzedni.getZaksiegowane() != true && amodokPoprzedni.getUmorzenia().size() > 0) {
                        //szukamy w dokumentach a nuz jest. jak jest to naprawiam ze nie naniesiono ze zaksiegowany
                        Dok znaleziony = dokDAO.findDokMC("AMO", wpisView.getPodatnikObiekt(), String.valueOf(amodokPoprzedni.getAmodokPK().getRok()), Mce.getNumberToMiesiac().get(amodokPoprzedni.getAmodokPK().getMc()));
                        double umorzeniepoprzedni = SrodkiTrwBean.sumujumorzenia(amodokPoprzedni.getPlanumorzen());
                        if (znaleziony instanceof Dok && znaleziony.getNetto() == umorzeniepoprzedni) {
                            amodokPoprzedni.setZaksiegowane(true);
                            amoDokDAO.edit(amodokPoprzedni);
                        } else {
                            throw new Exception();
                        }
                    }
                }
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd. Nie ma zaksięgowanego odpisu w poprzednim miesiącu, a jest dokument umorzeniowy za ten okres!");
                return;
            }
            try {
                selDokument.setEwidencjaVAT1(null);
                HttpServletRequest request;
                request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                Principal principal = request.getUserPrincipal();
                selDokument.setWprowadzil(principal.getName());
                selDokument.setPkpirM(wpisView.getMiesiacWpisu());
                selDokument.setPkpirR(wpisView.getRokWpisu().toString());
                selDokument.setVatM(wpisView.getMiesiacWpisu());
                selDokument.setVatR(wpisView.getRokWpisu().toString());
                selDokument.setPodatnik(wpisView.getPodatnikObiekt());
                selDokument.setStatus("bufor");
                selDokument.setUsunpozornie(false);
                selDokument.setDataWyst(Data.ostatniDzien(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
                selDokument.setKontr(new Klienci("", "dowód wewnętrzny"));
                Rodzajedok amodok = rodzajedokDAO.find("AMO", wpisView.getPodatnikObiekt());
                selDokument.setRodzajedok(amodok);
                selDokument.setNrWlDk(wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisu().toString());
                selDokument.setOpis("umorzenie za miesiac");
                List<KwotaKolumna1> listaX = new ArrayList<>();
                KwotaKolumna1 tmpX = new KwotaKolumna1();
                tmpX.setDok(selDokument);
                tmpX.setNetto(kwotaumorzenia);
                tmpX.setVat(0.0);
                tmpX.setNazwakolumny("poz. koszty");
                listaX.add(tmpX);
                selDokument.setListakwot1(listaX);
                selDokument.setNetto(kwotaumorzenia);
                selDokument.setRozliczony(true);
                sprawdzCzyNieDuplikat(selDokument);
                if (selDokument.getNetto() > 0) {
                    dokDAO.edit(selDokument);
                    String wiadomosc = "Nowy dokument umorzenia zachowany: " + selDokument.getPkpirR() + "/" + selDokument.getPkpirM() + " kwota: " + selDokument.getNetto();
                    Msg.msg("i", wiadomosc);
                    amodokBiezacy.setZaksiegowane(true);
                    amoDokDAO.edit(amodokBiezacy);
                    Msg.msg("i", "Informacje naniesione na dokumencie umorzenia");
                } else {
                    Msg.msg("e", "Kwota umorzenia wynosi 0zł. Dokument nie został zaksiegowany!");
                }
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd, dokument AMO nie zaksięgowany!");
            }
        } else {
            Msg.msg("e", "Amortyzacja została juz wcześniej zaksięgowana w bieżacym miesiącu!");
        }
    }

    public void dodajNowyWpisAutomatycznyStorno() {
        selDokument = new Dok();
        double kwotastorno = 0.0;
        ArrayList<Dok> lista = new ArrayList<>();
        Integer rok = wpisView.getRokWpisu();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        StornoDok tmp = new StornoDok();
        try {
            tmp = stornoDokDAO.find(rok, mc, podatnik);
            lista = (ArrayList<Dok>) tmp.getDokument();
            Iterator itx;
            itx = lista.iterator();
            while (itx.hasNext()) {
                Dok tmpx = (Dok) itx.next();
                kwotastorno = kwotastorno + tmpx.getStorno().get(tmpx.getStorno().size() - 1).getKwotawplacona();
            }

            selDokument.setEwidencjaVAT1(null);
            HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            selDokument.setWprowadzil(principal.getName());
            selDokument.setPkpirM(wpisView.getMiesiacWpisu());
            selDokument.setPkpirR(wpisView.getRokWpisu().toString());
            selDokument.setVatM("");
            selDokument.setVatR("");
            selDokument.setPodatnik(wpisView.getPodatnikObiekt());
            selDokument.setStatus("bufor");
            String data;
            switch (wpisView.getMiesiacWpisu()) {
                case "01":
                case "03":
                case "05":
                case "07":
                case "08":
                case "10":
                case "12":
                    data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-31";
                    break;
                case "02":
                    data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-28";
                    break;
                default:
                    data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-30";
                    break;
            }
            selDokument.setDataWyst(data);
            selDokument.setKontr(new Klienci("111111111", "wlasny"));
//            selDokument.setRodzTrans("storno niezapłaconych faktur");
            selDokument.setNrWlDk(wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisu().toString());
            selDokument.setOpis("storno za miesiac");
            List<KwotaKolumna1> listaX = new ArrayList<>();
            KwotaKolumna1 tmpX = new KwotaKolumna1();
            tmpX.setNetto(kwotastorno);
            tmpX.setVat(0.0);
            tmpX.setNazwakolumny("poz. koszty");
            listaX.add(tmpX);
            selDokument.setListakwot1(listaX);
            selDokument.setRozliczony(true);
            selDokument.setUsunpozornie(false);
            //sprawdzCzyNieDuplikat(selDokument);
            if (selDokument.getNetto() != 0) {
                sprawdzCzyNieDuplikat(selDokument);
                dokDAO.dodaj(selDokument);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nowy dokument storno zachowany", selDokument.getIdDok().toString());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                tmp.setZaksiegowane(true);
                stornoDokDAO.edit(tmp);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kwota storno wynosi 0zł. Dokument nie został zaksiegowany", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd, dokument strono nie zaksięgowany!");
//        }
        }
    }

    public void sprawdzCzyNieDuplikat(Dok selD) throws Exception {
        Dok tmp = null;
        tmp = dokDAO.znajdzDuplikatwtrakcie(selD, wpisView.getPodatnikObiekt(), selD.getRodzajedok().getSkrot());
        if (tmp instanceof Dok) {
            String wiadomosc = "Dokument " + selD.getRodzajedok().getSkrot() + " dla tego klienta, o nr " + selD.getNrWlDk() + " i kwocie netto " + selD.getNetto() + " jest zaksiegowany u pod: " + tmp.getPodatnik() + " rok/mc: " + tmp.getPkpirR() + "/" + tmp.getPkpirM() + " dnia: " + Data.data_ddMMMMyyyy(tmp.getDataK());
            Msg.msg("e", wiadomosc);
            throw new Exception();
        }
    }

    public void sprawdzCzyNieDuplikatwtrakcie(AjaxBehaviorEvent ex) {
        try {
            Dok selD = null;
            selD = dokDAO.znajdzDuplikatwtrakcie(selDokument, wpisView.getPodatnikObiekt(), (String) Params.params("dodWiad:rodzajTrans"));
            if (selD instanceof Dok) {
                String wiadomosc = "Dokument typu " + selD.getRodzajedok().getSkrot() + " dla tego klienta, o numerze " + selD.getNrWlDk() + " i kwocie netto " + selD.getNetto() + " jest juz zaksiegowany u podatnika: " + selD.getPodatnik() + " w miesiącu " + selD.getPkpirM();
                Msg.msg("e", wiadomosc);
                RequestContext.getCurrentInstance().execute("$('#dodWiad\\\\:numerwlasny').select();");
            } else {
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("w", "Blad w DokView sprawdzCzyNieDuplikatwtrakcie");
        }
    }
//archeo
//    public void podlaczPierwszaKolumne() {
//        if (liczbawierszy < 1) {
//            liczbawierszy++;
//        }
//    }

    //przekazuje zeby pobrac jego domyslna kolumne do listy kolumn
//    public void przekazKontrahenta(ValueChangeEvent e) throws Exception {
//        AutoComplete anAutoComplete = (AutoComplete) e.getComponent();
//        przekazKontr = (Klienci) anAutoComplete.getValue();
//        selDokument.setKontr(przekazKontr);
//        RequestContext.getCurrentInstance().update("dodWiad:acForce");
//        if (podX.getPodatekdochodowy().get(podX.getPodatekdochodowy().size() - 1).getParametr().contains("VAT")) {
//            selDokument.setDokumentProsty(true);
//            RequestContext.getCurrentInstance().update("dodWiad:dokumentprosty");
//        }
//    }
    public void zmienokresVAT() {
        String datafaktury = (String) Params.params("dodWiad:dataPole");
        String dataobowiazku = (String) Params.params("dodWiad:dataSPole");
        int porownaniedat = Data.compare(datafaktury, dataobowiazku);
        String rok;
        String mc;
        if (porownaniedat >= 0) {
            rok = dataobowiazku.substring(0, 4);
            mc = dataobowiazku.substring(5, 7);
        } else {
            rok = datafaktury.substring(0, 4);
            mc = datafaktury.substring(5, 7);
        }
        selDokument.setVatR(rok);
        selDokument.setVatM(mc);
        RequestContext.getCurrentInstance().update("dodWiad:ostatnipanel");
    }

    public void dodajSTR() {
        String podatnik = wpisView.getPodatnikWpisu();
        selectedSTR.setPodatnik(podatnik);
        sTRView.dodajSrodekTrwaly(selectedSTR);
        RequestContext.getCurrentInstance().update("srodki:panelekXA");
    }

    public void skopiujSTR() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String nazwa = params.get("dodWiad:acForce1_input");
        if (!nazwa.isEmpty()) {
            try {
                srodekkategoriawynik = srodkikstDAO.finsStr1(nazwa);
                selectedSTR.setDataprzek(selDokument.getDataWyst());
                selectedSTR.setKst(srodekkategoriawynik.getSymbol());
                selectedSTR.setUmorzeniepoczatkowe(0.0);
                selectedSTR.setStawka(Double.parseDouble(srodekkategoriawynik.getStawka()));
                RequestContext.getCurrentInstance().update("dodWiad:nowypanelsrodki");
            } catch (Exception e) {
                E.e(e);
            }
        }
    }

    public void przekierowanieWpisKLienta() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("klienci.xhtml");
    }

    /**
     * stare do edycji dokumentu
     *
     * @param wpis
     * @throws IOException
     */
    public void przekierowanieEdytujDokument(Dok wpis) throws IOException {
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        Ostatnidokument temp = new Ostatnidokument();
        temp.setUzytkownik(principal.getName());
        temp.setDokument(wpis);
        try {
            ostatnidokumentDAO.dodaj(temp);
        } catch (Exception e) {
            E.e(e);
            ostatnidokumentDAO.edit(temp);
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaIndex.xhtml?faces-redirect=true<");
    }

    public void przekierowanieWpis() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaIndex.xhtml?faces-redirect=true<");
    }

    private void aktualizujInwestycje(Dok dok) {
        try {
            List<Inwestycje> inwestycje = inwestycjeDAO.findInwestycje(wpisView.getPodatnikWpisu(), false);
            String symbol = dok.getSymbolinwestycji();
            if (!symbol.equals("wybierz")) {
                Inwestycje biezaca = null;
                for (Inwestycje p : inwestycje) {
                    if (p.getSymbol().equals(symbol)) {
                        biezaca = p;
                        break;
                    }
                }
                biezaca.setTotal(biezaca.getTotal() + dok.getNetto());
                List<Inwestycje.Sumazalata> sumazalata = biezaca.getSumazalata();
                if (sumazalata.isEmpty()) {
                    Inwestycje x = new Inwestycje();
                    Inwestycje.Sumazalata sum = x.new Sumazalata();
                    sum.setRok(wpisView.getRokWpisu().toString());
                    sum.setKwota(0.0);
                    sumazalata.add(sum);
                } else {
                    List<String> roki = new ArrayList<>();
                    for (Inwestycje.Sumazalata p : sumazalata) {
                        roki.add(p.getRok());
                    }
                    if (!roki.contains(wpisView.getRokWpisu().toString())) {
                        Inwestycje x = new Inwestycje();
                        Inwestycje.Sumazalata sum = x.new Sumazalata();
                        sum.setRok(wpisView.getRokWpisu().toString());
                        sum.setKwota(0.0);
                        sumazalata.add(sum);
                    }
                }
                for (Inwestycje.Sumazalata p : sumazalata) {
                    if (p.getRok().equals(dok.getPkpirR())) {
                        p.setKwota(p.getKwota() + dok.getNetto());
                        biezaca.setSumazalata(sumazalata);
                        break;
                    }
                }
                biezaca.getDoklist().add(dok);
                inwestycjeDAO.edit(biezaca);
                Msg.msg("i", "Aktualizuje inwestycje " + symbol, "dodWiad:mess_add");

            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Błąd nie zaktualizowałem inwestycji!", "dodWiad:mess_add");
        }
    }

    //kopiuje ostatni dokument celem wykorzystania przy wpisie
    public void skopiujdokument() {
        try {
            selDokument = ostatnidokumentDAO.pobierz(wpisView.getWprowadzil().getLogin());
            liczbawierszy = selDokument.getListakwot1().size();
            Rodzajedok rodzajdok = selDokument.getRodzajedok();
            typdokumentu = rodzajdok.getSkrot();
            selDokument.setNrWlDk(null);
            podepnijListe();
            renderujwyszukiwarke(rodzajdok);
            renderujtabele(rodzajdok);
        } catch (Exception e) {
            E.e(e);
        }
        RequestContext.getCurrentInstance().update("dodWiad:wprowadzanie");
        RequestContext.getCurrentInstance().execute("$(document.getElementById('dodWiad:dataPole')).select();");
    }

    public void skopiujdoedycji() {
        selDokument = dokTabView.getGosciuwybral().get(0);
    }

    private void skopiujdoedycjidane() {
        selDokument = dokTabView.getGosciuwybral().get(0);
        liczbawierszy = selDokument.getListakwot1().size();
        podepnijListe();//to jest wybor kolumn do selectOneMenu bez tego nie ma selectedItems
        if (selDokument.getListakwot1().isEmpty()) {
            KwotaKolumna1 kwotaKolumna1 = new KwotaKolumna1();
            kwotaKolumna1.setDok(selDokument);
            kwotaKolumna1.setNetto(selDokument.getNetto());
            selDokument.getListakwot1().add(kwotaKolumna1);
        }
        ewidencjaAddwiad.clear();;
        sumbrutto = 0.0;
        int j = 0;
        try {//trzeba ignorowac w przypadku dokumentow prostych
            for (EVatwpis1 s : selDokument.getEwidencjaVAT1()) {
                EwidencjaAddwiad ewidencjaAddwiad = new EwidencjaAddwiad();
                ewidencjaAddwiad.setEvewidencja(s.getEwidencja());
                ewidencjaAddwiad.setOpzw(s.getEwidencja().getRodzajzakupu());
                ewidencjaAddwiad.setNetto(s.getNetto());
                ewidencjaAddwiad.setVat(s.getVat());
                ewidencjaAddwiad.setBrutto(s.getNetto() + s.getVat());
                ewidencjaAddwiad.setLp(j++);
                sumbrutto += s.getNetto() + s.getVat();
                this.ewidencjaAddwiad.add(ewidencjaAddwiad);
            }
        } catch (Exception e) {
            E.e(e);
            for (KwotaKolumna1 p : selDokument.getListakwot1()) {
                sumbrutto += p.getNetto();
            }
        }
        renderujwyszukiwarke(selDokument.getRodzajedok());
        renderujtabele(selDokument.getRodzajedok());
        if (ewidencjaAddwiad.isEmpty()) {
            ukryjEwiencjeVAT = false;
            RequestContext.getCurrentInstance().update("dodWiad:panelewidencjivat");
        }
        RequestContext.getCurrentInstance().update("dialogEdycja");
    }

    public void sprawdzczywybranodokumentdoedycji() {
        skopiujdoedycjidane();
        obsluzcechydokumentu();
        if (selDokument.getRodzajedok().getSkrot().equals("OT")) {
            Msg.msg("e", "Nie można edytować dokumnetu zakupu środków trwałych!");
            RequestContext.getCurrentInstance().execute("PF('dialogEdycjaZaksiegowanychDokumentow').hide();");
            return;
        }
        if (selDokument.getNetto() != null) {
            RequestContext.getCurrentInstance().execute("PF('dialogEdycjaZaksiegowanychDokumentow').show();");
        } else {
            Msg.msg("e", "Nie wybrano dokumentu do edycji!");
            RequestContext.getCurrentInstance().execute("PF('dialogEdycjaZaksiegowanychDokumentow').hide();");
        }
    }

    public void dodajKlienta() {
        KlienciSuper tmpk = (KlienciSuper) selectedKlient;

        try {
            if (selectedKlient.getNip().isEmpty()) {
                wygenerujnip();
            }
            //Usunalem formatowanie pelnej nazwy klienta bo przeciez imie i nazwiko pisze sie wielkimi a ten zmniejszal wszystko
//        String formatka = selectedKlient.getNpelna().substring(0, 1).toUpperCase();
//        formatka = formatka.concat(selectedKlient.getNpelna().substring(1).toLowerCase());
//        selectedKlient.setNpelna(formatka);
            String formatka = selectedKlient.getNskrocona().toUpperCase();
            selectedKlient.setNskrocona(formatka);
            formatka = selectedKlient.getUlica().substring(0, 1).toUpperCase();
            formatka = formatka.concat(selectedKlient.getUlica().substring(1).toLowerCase());
            selectedKlient.setUlica(formatka);
            try {
                selectedKlient.getKrajnazwa();
            } catch (Exception e) {
                E.e(e);
                selectedKlient.setKrajnazwa("Polska");
            }
            String kraj = selectedKlient.getKrajnazwa();
            String symbol = panstwaMapa.getWykazPanstwSX().get(kraj);
            selectedKlient.setKrajkod(symbol);
            poszukajnip();
            klDAO.dodaj(selectedKlient);
            selDokument.setKontr(selectedKlient);
            selectedKlient = new Klienci();
            RequestContext.getCurrentInstance().update("dodWiad:acForce");
            RequestContext.getCurrentInstance().update("formX");
            RequestContext.getCurrentInstance().update("formY:tabelaKontr");
            Msg.msg("i", "Dodano nowego klienta" + selectedKlient.getNpelna(), "formX:mess_add");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie dodano nowego klienta. Klient o takim Nip juz istnieje", "formX:mess_add");
        }

    }

    private void poszukajnip() throws Exception {
        String nippoczatkowy = selectedKlient.getNip();
        if (!nippoczatkowy.equals("0000000000")) {
            List<Klienci> kliencitmp = new ArrayList<>();
            kliencitmp = klDAO.findAll();
            List<String> kliencinip = new ArrayList<>();
            for (Klienci p : kliencitmp) {
                if (p.getNip().equals(nippoczatkowy)) {
                    throw new Exception();
                }
            }
        }
    }

    private void wygenerujnip() {
        List<Klienci> kliencitmp = klDAO.findAll();
        List<Klienci> kliencinip = new ArrayList<>();
        //odnajduje klientow jednorazowych
        for (Klienci p : kliencitmp) {
            if (p.getNip().startsWith("XX")) {
                kliencinip.add(p);
            }
        }
        //wyciaga nipy
        List<Integer> nipy = new ArrayList<>();
        for (Klienci p : kliencinip) {
            nipy.add(Integer.parseInt(p.getNip().substring(2)));
        }
        Collections.sort(nipy);
        Integer max;
        if (nipy.size() > 0) {
            max = nipy.get(nipy.size() - 1);
            max++;
        } else {
            max = 0;
        }
        //uzupelnia o zera i robi stringa;
        String wygenerowanynip = max.toString();
        while (wygenerowanynip.length() < 10) {
            wygenerowanynip = "0" + wygenerowanynip;
        }
        wygenerowanynip = "XX" + wygenerowanynip;
        selectedKlient.setNip(wygenerowanynip);
    }

    public void pobierzdaneZpoprzedniegoDokumentu(ValueChangeEvent e1) {
        stawkaVATwPoprzednimDok = 0.0;
        Klienci klient = (Klienci) e1.getNewValue();
        String klientnip = klient.getNip();
        if (!klientnip.equals(wpisView.getPodatnikObiekt().getNip())) {
            try {
                Dok poprzedniDokument = dokDAO.findDokLastofaKontrahent(wpisView.getPodatnikObiekt(), klient, wpisView.getRokWpisuSt());
                if (poprzedniDokument == null) {
                    poprzedniDokument = dokDAO.findDokLastofaKontrahent(wpisView.getPodatnikObiekt(), klient, wpisView.getRokUprzedniSt());
                }
                if (poprzedniDokument != null) {
                    selDokument.setTypdokumentu(poprzedniDokument.getRodzajedok().getSkrot());
                    selDokument.setRodzajedok(poprzedniDokument.getRodzajedok());
                    typdokumentu = poprzedniDokument.getRodzajedok().getSkrot();
                    typpoprzedniegodokumentu = poprzedniDokument.getRodzajedok().getSkrot();
                    selDokument.setOpis(poprzedniDokument.getOpis());
                    if (typpoprzedniegodokumentu.startsWith("S")) {
                        List<EVatwpis1> e = poprzedniDokument.getEwidencjaVAT1();
                        for (EVatwpis1 p : e) {
                            if (p.getNetto() != 0) {
                                nazwaEwidencjiwPoprzednimDok = p.getEwidencja();
                                break;
                            }
                        }
                    } else {
                        List<EVatwpis1> e = poprzedniDokument.getEwidencjaVAT1();
                        if (e.size()==1) {
                            for (EVatwpis1 p : e) {
                                if (p.getNetto() != 0) {
                                    stawkaVATwPoprzednimDok = obliczstawke(p);
                                    break;
                                }
                            }
                        }
                    }
                    int i = 0;
                    try {
                        if (poprzedniDokument.getListakwot1() != null) {
                            for (KwotaKolumna1 p : poprzedniDokument.getListakwot1()) {
                                if (selDokument.getListakwot1().size() < i + 2) {
                                    selDokument.getListakwot1().get(i++).setNazwakolumny(p.getNazwakolumny());
                                }
                            }
                        }
                    } catch (Exception e) {

                    }
                }else {
                    typdokumentu = selDokument.getRodzajedok().getSkrot();
                }
            } catch (Exception e) {
                E.e(e);
            }
        }
    }
    
    private double obliczstawke(EVatwpis1 p) {
        double zwrot = 0.23;
        if (p != null) {
            zwrot = Z.z4(p.getVat()/p.getNetto());
        }
        return zwrot;
    }
    
    public void pobierzkursNBPRozbicie(Kolumna1Rozbicie item, int id) {
        if (!item.getData().equals("błędna data")) {
            DateTime dzienposzukiwany = new DateTime(item.getData());
            if (selDokument.getWalutadokumentu().getSymbolwaluty().equals("PLN")) {
                item.setTabelanbp(tabelanbpDAO.findByDateWaluta("2012-01-01", "PLN"));
            } else {
                item.setTabelanbp(TabelaNBPBean.pobierzTabeleNBP(dzienposzukiwany, tabelanbpDAO, selDokument.getWalutadokumentu().getSymbolwaluty()));
            }
            String up1 = "formdialog_add_wiad_kolumna1rozbicie:rozbicietabeladane:"+id+":rozbicietabela";
            String up2 = "formdialog_add_wiad_kolumna1rozbicie:rozbicietabeladane:"+id+":rozbiciekurs";
            RequestContext.getCurrentInstance().update(up1);
            RequestContext.getCurrentInstance().update(up2);
            }
    }
    
    
    public void przeliczkwoteRozbicie(Kolumna1Rozbicie item, int id) {
        if (selDokument.getWalutadokumentu().getSymbolwaluty().equals("PLN")) {
            item.setNetto(item.getNettowaluta());
        } else {
            item.setNetto(Z.z(item.getNettowaluta()*item.getTabelanbp().getKurssredni()));
        }
        sumarozbicie.setNetto(0.0);
        sumarozbicie.setNettowaluta(0.0);
        for (Kolumna1Rozbicie t : kolumna1rozbicielista) {
            sumarozbicie.setNetto(sumarozbicie.getNetto() + t.getNetto());
            sumarozbicie.setNettowaluta(sumarozbicie.getNettowaluta() + t.getNettowaluta());
        }
        String up3 = "formdialog_add_wiad_kolumna1rozbicie:rozbicietabeladane:"+id+":rozbiciekwotawpln";
        String up4 = "formdialog_add_wiad_kolumna1rozbicie:podsumowanierozbicia";
        RequestContext.getCurrentInstance().update(up3);
        RequestContext.getCurrentInstance().update(up4);
    }
    
    public void przygotujrozbicie() {
        if (selDokument.getListakwot1().size() > 0) {
            KwotaKolumna1 p = selDokument.getListakwot1().get(0);
            if (!p.getListaKolumna1Rozbicie().isEmpty()) {
                kolumna1rozbicielista = p.getListaKolumna1Rozbicie();
                sumarozbicie.setNetto(0.0);
                sumarozbicie.setNettowaluta(0.0);
                for (Kolumna1Rozbicie t : kolumna1rozbicielista) {
                    sumarozbicie.setNetto(sumarozbicie.getNetto() + t.getNetto());
                    sumarozbicie.setNettowaluta(sumarozbicie.getNettowaluta() + t.getNettowaluta());
                }
            } else {
                p.setListaKolumna1Rozbicie(kolumna1rozbicielista);
            }
        }
    }
    
    public void dodajwierszrozbicie() {
        kolumna1rozbicielista.add(new Kolumna1Rozbicie());
    }
    public void anulujwierszrozbicie() {
        KwotaKolumna1 p = selDokument.getListakwot1().get(0);
        p.setListaKolumna1Rozbicie(new ArrayList<>());
        p.setNetto(0.0);
        p.setNettowaluta(0.0);
    }
    public void sumujwierszrozbicie() {
        KwotaKolumna1 p = selDokument.getListakwot1().get(0);
        p.setNetto(sumarozbicie.getNetto());
        p.setNettowaluta(sumarozbicie.getNettowaluta());
        symbolWalutyNettoVat = "zł";
        String up4 = "dodWiad:tabelapkpir";
        RequestContext.getCurrentInstance().update(up4);
    }
    

    public void pobierzkursNBP(ValueChangeEvent el) {
        try {
            symbolwalutydowiersza = ((Waluty) el.getNewValue()).getSymbolwaluty();
            String nazwawaluty = ((Waluty) el.getNewValue()).getSymbolwaluty();
            String staranazwa = ((Waluty) el.getOldValue()).getSymbolwaluty();
            if (!nazwawaluty.equals("PLN")) {
                String datadokumentu = selDokument.getDataWyst();
                DateTime dzienposzukiwany = new DateTime(datadokumentu);
                selDokument.setTabelanbp(TabelaNBPBean.pobierzTabeleNBP(dzienposzukiwany, tabelanbpDAO, nazwawaluty));
//                if (staranazwa != null && selDokument.getListawierszy().get(0).getStronaWn().getKwota()) {
//                    DokFKWalutyBean.przewalutujzapisy(staranazwa, nazwawaluty, selected, walutyDAOfk);
//                    RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
//                    selDokument.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty));
//                } else {
//                    selDokument.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty));
//                    //wpisuje kurs bez przeliczania, to jest dla nowego dokumentu jak sie zmieni walute na euro
//                }
                symbolWalutyNettoVat = " " + selDokument.getTabelanbp().getWaluta().getSkrotsymbolu();
                //ukryjEwiencjeVAT = true;
                //selDokument.setDokumentProsty(true);
            } else {
                //najpierw trzeba przewalutowac ze starym kursem, a potem wlepis polska tabele
//                if (staranazwa != null && selDokument.getListawierszy().get(0).getStronaWn().getKwota() != 0.0) {
//                    DokFKWalutyBean.przewalutujzapisy(staranazwa, nazwawaluty, selected, walutyDAOfk);
//                    RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
//                    selDokument.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty));
//                } else {
//                    selDokument.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty));
//                    //wpisuje kurs bez przeliczania, to jest dla nowego dokumentu jak sie zmieni walute na euro
//                }
                selDokument.setTabelanbp(domyslatabela);
                symbolWalutyNettoVat = " " + selDokument.getTabelanbp().getWaluta().getSkrotsymbolu();
                //ukryjEwiencjeVAT = false;
                //selDokument.setDokumentProsty(false);
            }
            RequestContext.getCurrentInstance().update("dodWiad:panelewidencjivat");
            RequestContext.getCurrentInstance().execute("r('dodWiad:acForce').select();");
        } catch (Exception e) {

        }
    }

    public void skopiujwartosc(int lp) {
        KwotaKolumna1 wiersz = selDokument.getListakwot1().get(lp);
        wiersz.setNettowaluta(wiersz.getNetto());
        String s = "dodWiad:tabelapkpir:" + lp + ":kwotaPkpir1";
        RequestContext.getCurrentInstance().update(s);
    }

    public void przewalutuj(int lp) {
        KwotaKolumna1 wiersz = selDokument.getListakwot1().get(lp);
        if (wiersz.getListaKolumna1Rozbicie().isEmpty()) {
            Tabelanbp t = selDokument.getTabelanbp();
            if (t != null) {
                double d = wiersz.getNettowaluta();
                wiersz.setNetto(d * t.getKurssredniPrzelicznik());
                symbolWalutyNettoVat = " zł";
                sumujnetto();
                String s = "dodWiad:tabelapkpir:" + lp + ":kwotaPkpir";
                RequestContext.getCurrentInstance().update(s);
            }
        }

    }
    
    public void konopiujinwestycje() {
        List<Dok> dok = dokDAO.findDokByInwest();
        List<Inwestycje> inwest = inwestycjeDAO.findAll();
        for (Dok p : dok) {
            for (Inwestycje r : inwest) {
                if (p.getSymbolinwestycji().equals(r.getSymbol())) {
                    p.setInwestycja(r);
                    dokDAO.edit(p);
                    r.getDoklist().add(p);
                    inwestycjeDAO.edit(r);;
                    break;
                }
            }
        }
    }
    
    public void znajdzdaneregon(String formularz) {
        try {
            SzukajDaneBean.znajdzdaneregon(formularz, selectedKlient, gUSView);
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    
    

    public Klienci getSelectedKlient() {
        return selectedKlient;
    }

    public void setSelectedKlient(Klienci selectedKlient) {
        this.selectedKlient = selectedKlient;
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public boolean isUkryjEwiencjeVAT() {
        return ukryjEwiencjeVAT;
    }

    public void setUkryjEwiencjeVAT(boolean ukryjEwiencjeVAT) {
        this.ukryjEwiencjeVAT = ukryjEwiencjeVAT;
    }

    public List<EwidencjaAddwiad> getEwidencjaAddwiad() {
        return ewidencjaAddwiad;
    }

    public void setEwidencjaAddwiad(List<EwidencjaAddwiad> ewidencjaAddwiad) {
        this.ewidencjaAddwiad = ewidencjaAddwiad;
    }

//    public KlView getKlView() {
//        return klView;
//    }
//
//    public void setKlView(KlView klView) {
//        this.klView = klView;
//    }

    public SrodekTrw getSelectedSTR() {
        return selectedSTR;
    }

    public void setSelectedSTR(SrodekTrw selectedSTR) {
        this.selectedSTR = selectedSTR;
    }

    public DokDAO getDokDAO() {
        return dokDAO;
    }

    public void setDokDAO(DokDAO dokDAO) {
        this.dokDAO = dokDAO;
    }

    public Klienci getSelectedKontr() {
        return selectedKontr;
    }

    public void setSelectedKontr(Klienci selectedKontr) {
        this.selectedKontr = selectedKontr;
    }

    public Dok getSelDokument() {
        return selDokument;
    }

    public void setSelDokument(Dok selDokument) {
        this.selDokument = selDokument;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Dok getWysDokument() {
        return wysDokument;
    }

    public void setWysDokument(Dok wysDokument) {
        this.wysDokument = wysDokument;
    }

    public boolean isRozliczony() {
        return rozliczony;
    }

    public void setRozliczony(boolean rozliczony) {
        this.rozliczony = rozliczony;
    }

    public String getTypdokumentu() {
        return typdokumentu;
    }

    public void setTypdokumentu(String typdokumentu) {
        this.typdokumentu = typdokumentu;
    }

    public List<Rodzajedok> getRodzajedokKlienta() {
        return rodzajedokKlienta;
    }

    public void setRodzajedokKlienta(List<Rodzajedok> rodzajedokKlienta) {
        this.rodzajedokKlienta = rodzajedokKlienta;
    }

    public Srodkikst getSrodekkategoria() {
        return srodekkategoria;
    }

    public void setSrodekkategoria(Srodkikst srodekkategoria) {
        this.srodekkategoria = srodekkategoria;
    }

    public Srodkikst getSrodekkategoriawynik() {
        return srodekkategoriawynik;
    }

    public void setSrodekkategoriawynik(Srodkikst srodekkategoriawynik) {
        this.srodekkategoriawynik = srodekkategoriawynik;
    }

    public boolean isPokazEST() {
        return pokazEST;
    }

    public void setPokazEST(boolean pokazEST) {
        this.pokazEST = pokazEST;
    }

    public double getSumbrutto() {
        return sumbrutto;
    }

    public void setSumbrutto(double sumbrutto) {
        this.sumbrutto = sumbrutto;
    }

    public SrodkiTrwaleView getsTRView() {
        return sTRView;
    }

    public void setsTRView(SrodkiTrwaleView sTRView) {
        this.sTRView = sTRView;
    }

    public String getSymbolwalutydowiersza() {
        return symbolwalutydowiersza;
    }

    public void setSymbolwalutydowiersza(String symbolwalutydowiersza) {
        this.symbolwalutydowiersza = symbolwalutydowiersza;
    }

    public List<String> getKolumny() {
        return kolumny;
    }

    public void setKolumny(List<String> kolumny) {
        this.kolumny = kolumny;
    }

    public int getLiczbawierszy() {
        return liczbawierszy;
    }

    public void setLiczbawierszy(int liczbawierszy) {
        this.liczbawierszy = liczbawierszy;
    }

    public boolean isRenderujwysz() {
        return renderujwysz;
    }

    public void setRenderujwysz(boolean renderujwysz) {
        this.renderujwysz = renderujwysz;
    }

    public DokTabView getDokTabView() {
        return dokTabView;
    }

    public void setDokTabView(DokTabView dokTabView) {
        this.dokTabView = dokTabView;
    }

    public STRTabView getsTRTableView() {
        return sTRTableView;
    }

    public void setsTRTableView(STRTabView sTRTableView) {
        this.sTRTableView = sTRTableView;
    }

    public List<Waluty> getWprowadzonesymbolewalut() {
        return wprowadzonesymbolewalut;
    }

    public void setWprowadzonesymbolewalut(List<Waluty> wprowadzonesymbolewalut) {
        this.wprowadzonesymbolewalut = wprowadzonesymbolewalut;
    }

    public WalutyDAOfk getWalutyDAOfk() {
        return walutyDAOfk;
    }

    public void setWalutyDAOfk(WalutyDAOfk walutyDAOfk) {
        this.walutyDAOfk = walutyDAOfk;
    }

    public String getSymbolWalutyNettoVat() {
        return symbolWalutyNettoVat;
    }

    public void setSymbolWalutyNettoVat(String symbolWalutyNettoVat) {
        this.symbolWalutyNettoVat = symbolWalutyNettoVat;
    }

    public boolean isNieVatowiec() {
        return nieVatowiec;
    }

    public void setNieVatowiec(boolean nieVatowiec) {
        this.nieVatowiec = nieVatowiec;
    }

    public List<Kolumna1Rozbicie> getKolumna1rozbicielista() {
        return kolumna1rozbicielista;
    }

    public Kolumna1Rozbicie getSumarozbicie() {
        return sumarozbicie;
    }

    public void setSumarozbicie(Kolumna1Rozbicie sumarozbicie) {
        this.sumarozbicie = sumarozbicie;
    }

    public void setKolumna1rozbicielista(List<Kolumna1Rozbicie> kolumna1rozbicielista) {
        this.kolumna1rozbicielista = kolumna1rozbicielista;
    }

    public List<Cechazapisu> getPobranecechypodatnik() {
        return pobranecechypodatnik;
    }

    public void setPobranecechypodatnik(List<Cechazapisu> pobranecechypodatnik) {
        this.pobranecechypodatnik = pobranecechypodatnik;
    }

    public GUSView getgUSView() {
        return gUSView;
    }

    public void setgUSView(GUSView gUSView) {
        this.gUSView = gUSView;
    }

    public Cechazapisu getCechadomyslna() {
        return cechadomyslna;
    }

    public void setCechadomyslna(Cechazapisu cechadomyslna) {
        this.cechadomyslna = cechadomyslna;
    }

    public Cechazapisu getCechastala() {
        return cechastala;
    }

    public void setCechastala(Cechazapisu cechastala) {
        this.cechastala = cechastala;
    }
    
    

    //<editor-fold defaultstate="collapsed" desc="comment">
//   public DokTabView getDokTabView() {
//       return dokTabView;
//   }
//
//   public void setDokTabView(DokTabView dokTabView) {
//       this.dokTabView = dokTabView;
//   }
//
    //    public static void main(String[] args) throws ParseException{
    //        String data = "2012-02-02";
    //        Calendar c = Calendar.getInstance();
    //        DateFormat formatter;
    //        formatter = new SimpleDateFormat("yyyy-MM-dd");
    //        Date terminplatnosci = (Date) formatter.parse(data);
    //        c.setTime(terminplatnosci);
    //        c.add(Calendar.DAY_OF_MONTH, 30);
    //        String nd30 = formatter.format(c.getTime());
    ////        selDokument.setTermin30(nd30);
    //        c.setTime(terminplatnosci);
    //        c.add(Calendar.DAY_OF_MONTH, 90);
    //        String nd90 = formatter.format(c.getTime());
    //      //  selDokument.setTermin90(nd90);
    //        c.setTime(terminplatnosci);
    //        c.add(Calendar.DAY_OF_MONTH, 150);
    //        String nd150 = formatter.format(c.getTime());
    //        //selDokument.setTermin150(nd150);
    //    }
    public static void main(String[] args) {
        Map<String, Object> lolo = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        //        addDays("2008-03-08");
        //        addDays("2009-03-07");
        //        addDays("2010-03-13");
    }
    //
    //    public static void addDays(String dateString) {
    //        System.out.println("Got dateString: " + dateString);
    //
    //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    //
    //        Calendar calendar = Calendar.getInstance();
    //        try {
    //            calendar.setTime(sdf.parse(dateString));
    //            Date day1 = calendar.getTime();
    //            System.out.println("  day1 = " + sdf.format(day1));
    //
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
    //            Date day2 = calendar.getTime();
    //            System.out.println("  day2 = " + sdf.format(day2));
    //
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
    //            Date day3 = calendar.getTime();
    //            System.out.println("  day3 = " + sdf.format(day3));
    //
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
    //            Date day4 = calendar.getTime();
    //            System.out.println("  day4 = " + sdf.format(day4));
    //
    //            // Skipping a few days ahead:
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 235);
    //            Date day5 = calendar.getTime();
    //            System.out.println("  day5 = " + sdf.format(day5));
    //
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
    //            Date day6 = calendar.getTime();
    //            System.out.println("  day6 = " + sdf.format(day6));
    //
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
    //            Date day7 = calendar.getTime();
    //            System.out.println("  day7 = " + sdf.format(day7));
    //
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
    //            Date day8 = calendar.getTime();
    //            System.out.println("  day8 = " + sdf.format(day8));
    //
    //        } catch (Exception e) { E.e(e); 
    //        }
    //    }
    //      public void uporzadkujbrutto(){
    //          List<Dok> lista = dokDAO.findAll();
    //          for(Dok sel : lista){
    //                Double kwota = sel.getKwota();
    //                try{
    //                kwota = kwota + sel.getKwotaX();
    //                } catch (Exception e) { E.e(e); }
    //
    //                double kwotavat = 0;
    //                try{
    //                    List<EVatwpis1> listavat = sel.getEwidencjaVAT1();
    //                    for(EVatwpis1 p : listavat){
    //                        kwotavat = kwotavat + p.getVat();
    //                    }
    //                } catch (Exception e) { E.e(e); }
    //                try{
    //                kwota = kwota + kwotavat;
    //                } catch (Exception e) { E.e(e); }
    //                sel.setBrutto(kwota);
    //                dokDAO.edit(sel);
    //          }
    //      }
    //       public void uporzadkujekstra(){
    //          List<Dok> lista = dokDAO.zwrocBiezacegoKlienta("EKSTRA S.C.");
    //          for(Dok sel : lista){
    //                Double kwota = sel.getKwota();
    //                if(sel.getPodatnik().equals("EKSTRA S.C.")){
    //                    sel.setPodatnik("EKSTRA S.C. EWA CYBULSKA, HELENA JAKUBIAK");
    //                }
    //                System.out.println("Zmienilem dokument");
    //                dokDAO.edit(sel);
    //          }
    //      }
    //      public void przeksiegujkwoty(){
    //          List<Dok> lista = dokDAO.findAll();
    //          for(Dok p : lista){
    //              List<KwotaKolumna1> wiersz = new ArrayList<>();
    //              KwotaKolumna1 pierwszy = new KwotaKolumna1();
    //              KwotaKolumna1 drugi = new KwotaKolumna1();
    //              try {
    //                  pierwszy.setNetto(p.getKwota());
    //                  BigDecimal tmp1 = BigDecimal.valueOf((p.getBrutto()-p.getNetto()));
    //                  tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
    //                  pierwszy.setVat(tmp1.doubleValue());
    //                  tmp1 = BigDecimal.valueOf(p.getBrutto());
    //                  tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
    //                  pierwszy.setBrutto(tmp1.doubleValue());
    //                  pierwszy.setNazwakolumny(p.getPkpirKol());
    //                  wiersz.add(pierwszy);
    //              } catch (Exception e) { E.e(e); }
    //              try {
    //                  drugi.setNetto(p.getKwotaX());
    //                  drugi.setVat(0.0);
    //                  drugi.setBrutto(p.getKwotaX().doubleValue());
    //                  drugi.setNazwakolumny(p.getPkpirKolX());
    //                  drugi.setDowykorzystania("dosprawdzenia");
    //                  wiersz.add(drugi);
    //              } catch (Exception e) { E.e(e); }
    //              p.setListakwot1(wiersz);
    //              dokDAO.edit(p);
    //              System.out.println("Przearanżowano "+p.getNrWlDk()+" - "+p.getPodatnik());
    //          }
    //      }
    //
//</editor-fold>

}
