/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansDok.ListaEwidencjiVat;
import beansFK.DialogWpisywanie;
import beansFK.DokFKBean;
import beansFK.DokFKTransakcjeBean;
import beansFK.DokFKVATBean;
import static beansFK.DokFKVATBean.pobierzKontoRozrachunkowe;
import static beansFK.DokFKVATBean.podsumujwartosciVAT;
import static beansFK.DokFKVATBean.podsumujwartosciVATRK;
import static beansFK.DokFKVATBean.rozliczEdytujVatKosztRK;
import static beansFK.DokFKVATBean.rozliczEdytujVatPrzychodRK;
import static beansFK.DokFKVATBean.rozliczVatKoszt;
import static beansFK.DokFKVATBean.rozliczVatKosztEdycja;
import static beansFK.DokFKVATBean.rozliczVatKosztRK;
import static beansFK.DokFKVATBean.rozliczVatPrzychod;
import static beansFK.DokFKVATBean.rozliczVatPrzychodEdycja;
import static beansFK.DokFKVATBean.rozliczVatPrzychodRK;
import static beansFK.DokFKVATBean.ustawvat;
import beansFK.DokFKWalutyBean;
import beansFK.DokumentFKBean;
import beansFK.StronaWierszaBean;
import beansFK.TabelaNBPBean;
import beansFK.WartosciVAT;
import beansPdf.PdfDokfk;
import beansRegon.SzukajDaneBean;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import comparator.DokfkLPcomparator;
import comparator.Dokfkcomparator;
import comparator.Rodzajedokcomparator;
import comparator.Transakcjacomparator;
import comparator.TransakcjacomparatorKwota;
import dao.EvewidencjaDAO;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import dao.STRDAO;
import dao.StronaWierszaDAO;
import daoFK.CechazapisuDAOfk;
import daoFK.DokDAOfk;
import daoFK.EVatwpisFKDAO;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import daoFK.RMKDAO;
import daoFK.TabelanbpDAO;
import daoFK.TransakcjaDAO;
import daoFK.WalutyDAOfk;
import daoFK.WierszBODAO;
import daoFK.WierszDAO;
import data.Data;
import embeddable.Mce;
import embeddable.Parametr;
import embeddable.Roki;
import entity.EVatwpisSuper;
import entity.Evewidencja;
import entity.Klienci;
import entity.Rodzajedok;
import entity.SrodekTrw;
import entity.Uz;
import entityfk.Cechazapisu;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.Konto;
import entityfk.RMK;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Transakcja;
import entityfk.Waluty;
import entityfk.Wiersz;
import entityfk.WierszBO;
import error.E;
import gus.GUSView;
import java.io.File;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import msg.Msg;
import org.joda.time.DateTime;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.inputnumber.InputNumber;
import org.primefaces.context.RequestContext;
import params.Params;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaA4Portrait;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;
import view.KlienciConverterView;
import view.ParametrView;
import view.WpisView;
import viewfk.subroutines.ObslugaWiersza;
import viewfk.subroutines.UzupelnijWierszeoDane;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DokfkView implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer lpWierszaWpisywanie;
    private String wnmadoprzeniesienia;
    protected Dokfk selected;
    private List<Dokfk> selectedlist;
    protected Dokfk selectedimport;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private KlienciDAO klDAO;
    @Inject
    private KlienciDAO klienciDAO;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private EVatwpisFKDAO eVatwpisFKDAO;
    @Inject
    private KliencifkDAO kliencifkDAO;
    @Inject
    private ListaEwidencjiVat listaEwidencjiVat;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private STRDAO strDAO;
    @Inject
    private RMKDAO rmkDAO;
    private boolean zapisz0edytuj1;
//    private String wierszid;
//    private String wnlubma;
    private List<Dokfk> wykazZaksiegowanychDokumentow;
    private List<Dokfk> wykazZaksiegowanychDokumentowimport;
    //a to jest w dialog_zapisywdokumentach
    @Inject
    private Wiersz wiersz;
    private int wierszDoPodswietlenia;
    //************************************zmienne dla rozrachunkow
//    @Inject
//    protected RozrachunekfkDAO rozrachunekfkDAO;
    @Inject
    protected TransakcjaDAO transakcjaDAO;
    private StronaWiersza aktualnyWierszDlaRozrachunkow;
    private int rodzaj;
    //a to sa listy do sortowanie transakcji na poczatku jedna mega do zbiorki wszystkich dodanych coby nie ginely
    private List<Transakcja> biezacetransakcje;
    private List<Transakcja> transakcjejakosparowany;
    private boolean zablokujprzyciskzapisz;
    private boolean pokazPanelWalutowy;
    private boolean pokazRzadWalutowy;
    //waltuty
    //waluta wybrana przez uzytkownika
    @Inject
    private WalutyDAOfk walutyDAOfk;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private WierszDAO wierszDAO;
    private String wybranawaluta;
    private String symbolwalutydowiersza;
    private List<Waluty> wprowadzonesymbolewalut;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{kontoZapisFKView}")
    private KontoZapisFKView kontoZapisFKView;
    private String rachunekCzyPlatnosc;
    private int typwiersza;
    private Wiersz wybranyWiersz;
    @Inject
    private KontoDAOfk kontoDAOfk;

    private int rodzajBiezacegoDokumentu;
    private String symbolWalutyNettoVat;
    //ewidencja vat raport kasowy
    private EVatwpisFK ewidencjaVatRK;
    private Wiersz wierszRK;
    private int wierszRKindex;
    private List<Evewidencja> listaewidencjivatRK;
    //powiazalem tabele z dialog_wpisu ze zmienna
    private boolean wlaczZapiszButon;
    private boolean pokazzapisywzlotowkach;
    @Inject
    private CechazapisuDAOfk cechazapisuDAOfk;
    private List<Cechazapisu> pobranecechypodatnik;
    private List<Cechazapisu> pobranecechypodatnikzapas;
    private StronaWiersza stronaWierszaCechy;
    private List<Dokfk> filteredValue;
    private List<Dokfk> filteredValueimport;
    private String wybranakategoriadok;
    private String wybranacechadok;
    private List cechydokzlisty;
    private String wybranakategoriadokimport;
    private boolean ewidencjaVATRKzapis0edycja1;
    private Dokfk dokumentdousuniecia;
    private boolean niedodawajkontapole;
    private Integer nrgrupywierszy;
    private Integer nrgrupyaktualny;
    private boolean potraktujjakoNowaTransakcje;
    private List<Tabelanbp> tabelenbp;
    private Tabelanbp tabelanbprecznie;
    @Inject
    private Tabelanbp wybranaTabelanbp;
    private Tabelanbp domyslnaTabelanbp;
    private String wierszedytowany;
    private List dokumentypodatnika;
    private List rodzajedokumentowPodatnika;
    private double saldoBO;
    private int jest1niema0_konto;
    private String komunikatywpisdok;
    private Integer lpwierszaRK;
    private Klienci klientdlaPK;
    private DataTable dataTablezaksiegowane;
    private StronaWiersza selectedStronaWiersza;
    private Double podsumowaniewybranych;
    private boolean totylkoedycjazapis;
    private boolean totylkoedycjaanalityczne;
    private int idwierszedycjaodswiezenie;
    private int duzyidwierszedycjaodswiezenie;
    private Evewidencja ewidencjadlaRKDEL;
    private boolean pokazwszystkiedokumenty;
    private boolean pokazsrodkitrwale;
    private boolean pokazrmk;
    private String miesiacWpisuPokaz;
    private Map<String, Konto> kontadlaewidencji;
    private Konto kontoRozrachunkowe;
    private Dokfk poprzedniDokument ;
    private double[] sumadokbo;
    @ManagedProperty(value = "#{gUSView}")
    private GUSView gUSView;
    @ManagedProperty(value = "#{klienciConverterView}")
    private KlienciConverterView klienciConverterView;
    private Cechazapisu cechazapisudododania;
    private String linijkaewidencjiupdate;
    private Cechazapisu nkup;
    

    public DokfkView() {
         //E.m(this);
        this.wykazZaksiegowanychDokumentow = Collections.synchronizedList(new ArrayList<>());
        this.biezacetransakcje = Collections.synchronizedList(new ArrayList<>());
        this.transakcjejakosparowany = Collections.synchronizedList(new ArrayList<>());
        this.zablokujprzyciskzapisz = false;
        this.wprowadzonesymbolewalut = Collections.synchronizedList(new ArrayList<>());
        this.symbolwalutydowiersza = "";
        this.zapisz0edytuj1 = false;
        this.listaewidencjivatRK = Collections.synchronizedList(new ArrayList<>());
        this.pobranecechypodatnik = Collections.synchronizedList(new ArrayList<>());
        this.pobranecechypodatnikzapas = Collections.synchronizedList(new ArrayList<>());
        this.dokumentypodatnika = Collections.synchronizedList(new ArrayList<>());
        this.cechydokzlisty = Collections.synchronizedList(new ArrayList<>());
        this.kontadlaewidencji = new ConcurrentHashMap<>();
    }

    //to zostaje bo tu i tak nie pobiera dokumentow
    @PostConstruct
    public void init() {
        //E.m(this);
        try {
            if (wpisView.isKsiegirachunkowe()) {
                //resetujDokument(); //to jest chyba niepotrzebne bo ta funkcja jest wywolywana jak otwieram okienko wpisu i potem po kazdym zachowaniu
                //obsluzcechydokumentu();
                stworzlisteewidencjiRK();
                //RequestContext.getCurrentInstance().update("ewidencjavatRK");
                dokumentypodatnika = rodzajedokDAO.findListaPodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                //wykazZaksiegowanychDokumentowSrodkiTrw = dokDAOfk.findDokfkPodatnikRokSrodkiTrwale(wpisView);
                //wykazZaksiegowanychDokumentowRMK = dokDAOfk.findDokfkPodatnikRokRMK(wpisView);
                wprowadzonesymbolewalut.addAll(walutyDAOfk.findAll());
                ewidencjadlaRKDEL = evewidencjaDAO.znajdzponazwie("zakup");
                domyslnaTabelanbp = DokFKBean.pobierzWaluteDomyslnaDoDokumentu(walutyDAOfk, tabelanbpDAO);
                wybranacechadok = null;
                pobranecechypodatnik = cechazapisuDAOfk.findPodatnik(wpisView.getPodatnikObiekt());
                pobranecechypodatnikzapas.addAll(pobranecechypodatnik);
                miesiacWpisuPokaz = wpisView.getMiesiacWpisu();
                kontadlaewidencji.put("221-3", kontoDAOfk.findKonto("221-3", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
                kontadlaewidencji.put("221-1", kontoDAOfk.findKonto("221-1", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
                kontadlaewidencji.put("149-3", kontoDAOfk.findKonto("149-3", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
                kontadlaewidencji.put("404-2", kontoDAOfk.findKonto("404-2", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
                //kontadlaewidencji.put("490", kontoDAOfk.findKonto("490", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
                nkup = cechazapisuDAOfk.findPodatniknkup();
                klientdlaPK = klDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
                if (klientdlaPK == null) {
                    klientdlaPK = new Klienci("222222222222222222222", "BRAK FIRMY JAKO KONTRAHENTA!!!");
                } 
                resetujDokumentOpen();
            }
        } catch (Exception e) {
            Msg.msg("e","Brak planu kont!");
            E.e(e);
        }
    }

//    //<editor-fold defaultstate="collapsed" desc="schowane podstawowe funkcje jak dodaj usun itp">
//
//    //********************************************funkcje dla ksiegowania dokumentow
//    //RESETUJ DOKUMNETFK
    
    public void resetujDokumentOpen() {
        if (zapisz0edytuj1 == false) {
            resetujDokument();
        } else if (selected.getRodzajedok().getSkrotNazwyDok().equals("BO")) {
            sumadokbo = ObslugaWiersza.sumujwierszeBO(selected);
            RequestContext.getCurrentInstance().update("formwpisdokument:panelwpisbutton");
        }
    }
    public void resetujDokument() {
        //pobieram dane ze starego dokumentu, jeżeli jest
        String symbolPoprzedniegoDokumentu = null;
        Rodzajedok rodzajDokPoprzedni = null;
        Klienci ostatniklient = null;
        String datadokumentu = null;
        String dataoperacji = null;
        komunikatywpisdok = null;
        if (selected != null) {
            symbolPoprzedniegoDokumentu = selected.pobierzSymbolPoprzedniegoDokfk();
            rodzajDokPoprzedni = selected.getRodzajedok();
            ostatniklient = selected.getKontr();
            if (selected.getDatadokumentu()!=null) {
                datadokumentu = selected.getDatadokumentu();
                dataoperacji = selected.getDataoperacji();
            }
            //RequestContext.getCurrentInstance().update("zestawieniedokumentow:dataList");
            //RequestContext.getCurrentInstance().update("zestawieniedokumentowimport:dataListImport");
        } else {
            rodzajDokPoprzedni = rodzajedokDAO.find("ZZ", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            symbolPoprzedniegoDokumentu = "ZZ";
        }
        try {
            //?????????????????????????????????co to jest to po-winno byc gdzie indziej
            if (ostatniklient == null) {
                ostatniklient = klientdlaPK;
            }
        } catch (Exception e) {
            E.e(e);
        }
        //tworze nowy dokument
        selected = new Dokfk(symbolPoprzedniegoDokumentu, rodzajDokPoprzedni, wpisView, ostatniklient);
        if (datadokumentu!=null) {
            selected.setDatadokumentu(datadokumentu);
            selected.setDataoperacji(dataoperacji);
        }
        selected.setWprowadzil(wpisView.getWprowadzil().getLogin());
        selected.setwTrakcieEdycji(false);
        //po co to na dziendobry?
        //kontoRozrachunkowe = DokFKVATBean.pobierzKontoRozrachunkowe(kliencifkDAO, selected, wpisView, kontoDAOfk);
        wygenerujnumerkolejny();
        podepnijEwidencjeVat(0);
        try {
            DokFKBean.dodajWaluteDomyslnaDoDokumentu(domyslnaTabelanbp, selected);
            resetprzyciskow();
        } catch (Exception e) {
            E.e(e);
        }
        obsluzcechydokumentu();
        pobierzopiszpoprzedniegodokItemSelect();
        rodzajBiezacegoDokumentu = 1;
        RequestContext.getCurrentInstance().update("formwpisdokument");
        RequestContext.getCurrentInstance().update("wpisywaniefooter");
        RequestContext.getCurrentInstance().execute("$(document.getElementById('formwpisdokument:data2DialogWpisywanie')).select();");
    }
    
    public String charakterdokumentu (Dokfk d) {
        String zwrot = "";
        if (d != null) {
            if (d.isWzorzec()) {
                zwrot += "wzr";
            }
            if (d.isZawierarmk()) {
                zwrot += " RMK";
            }
            if (d.isZawierasrodkitrw()) {
                zwrot += " ŚTR";
            }
        }
        return zwrot;
    }

    private void resetprzyciskow() {
        pokazPanelWalutowy = false;
        pokazRzadWalutowy = false;
        biezacetransakcje = null;
        zapisz0edytuj1 = false;
        wlaczZapiszButon = true;
        niedodawajkontapole = false;
    }
    
    public String[] getNaglowekWpisDokumentu() {
        String[] zwrot = new String[2];
        String text = "Wprowadzanie dokumentu. Podatnik: "+wpisView.getPodatnikWpisu();
        String css = "";
//        FacesContext context = FacesContext.getCurrentInstance();
//        PlanKontView bean = context.getApplication().evaluateExpressionGet(context, "#{kontoConv}", PlanKontView.class);
//        bean.init();
//        text += bean.getInfozebrakslownikowych();
        String rokbiezacy = Data.aktualnyRok();
        if (!wpisView.getRokWpisuSt().equals(rokbiezacy)) {
            text += " UWAGA: rok odmienny od bieżącego!";
            css = "color: red;";
        }
        zwrot[0] = text;
        zwrot[1] = css;
        return zwrot;
    }

//    //dodaje wiersze do dokumentu
//<editor-fold defaultstate="collapsed" desc="nie uzywane funkcje">
    //nie sa uzywane
//    public void niewybranokontaStronaWn(Wiersz wiersz, int indexwiersza) {
//        if (!(wiersz.getStronaWn().getKonto() instanceof Konto)) {
//            if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
//             try {
//               Konto k = selected.getRodzajedok().getKontorozrachunkowe();
//               StronaWiersza wierszBiezacy = wiersz.getStronaWn();
//               wierszBiezacy.setKonto(serialclone.SerialClone.clone(k));
//             } catch (Exception e) {  E.e(e);
//
//             }
//            } else {
//                skopiujKontoZWierszaWyzej(indexwiersza, "Wn");
//            }
//        }
//    }
//    public void niewybranokontaStronaMa(Wiersz wiersz, int indexwiersza) {
//        if (!(wiersz.getStronaMa().getKonto() instanceof Konto)) {
//            if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
//             try {
//               Konto k = selected.getRodzajedok().getKontorozrachunkowe();
//               StronaWiersza wierszBiezacy = wiersz.getStronaMa();
//               wierszBiezacy.setKonto(serialclone.SerialClone.clone(k));
//             } catch (Exception e) {  E.e(e);
//
//             }
//            } else {
//                skopiujKontoZWierszaWyzej(indexwiersza, "Ma");
//            }
//        }
//    }
    public void dodajNowyWierszStronaWnPiatka(Wiersz wiersz) {
        int indexwTabeli = wiersz.getIdporzadkowy() - 1;
        if (wiersz.getStronaWn().getKonto().getPelnynumer().startsWith("4") && wiersz.getPiatki().size() == 0) {
            ObslugaWiersza.dolaczNowyWierszPiatka(indexwTabeli, true, selected, kontoDAOfk, wpisView);
            //RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            return;
        }
        if (wiersz.getTypWiersza() != 0) {
            int licznbawierszy = selected.getListawierszy().size();
            if (licznbawierszy > 1) {
                if (wiersz.getTypWiersza() == 5 || wiersz.getTypWiersza() == 6 || wiersz.getTypWiersza() == 7) {
                    ObslugaWiersza.dolaczNowyWierszPiatka(indexwTabeli, true, selected, kontoDAOfk, wpisView);
                }
            }
        }
    }
//    //sprawdza czy wiersz po stronie wn z kwotami takimi samymi po stronie wn i ma
//    private boolean rowneStronyWnMa (Wiersz wiersz) {
//        StronaWiersza wn = wiersz.getStronaWn();
//        StronaWiersza ma = wiersz.getStronaMa();
//        if (wn.getKwota() == ma.getKwota()) {
//            return true;
//        }
//        return false;
//    }
//    public void lisnerCzyNastapilaZmianaKontaMa(ValueChangeEvent e) {
//        Konto stare = (Konto) e.getOldValue();
//        Konto nowe = (Konto) e.getNewValue();
//        try {
//            if (!stare.equals(nowe)) {
//            }
//        } catch (Exception er) {
//
//        }
//    }
//</editor-fold>
    public void zdarzeniaOnBlurStronaKwotaWn(ValueChangeEvent e) {
        double kwotastara = (double) e.getOldValue();
        double kwotanowa = (double) e.getNewValue();
        if (Z.z(kwotastara) != Z.z(kwotanowa)) {
            String clientID = ((InputNumber) e.getSource()).getClientId();
            String indexwiersza = clientID.split(":")[2];
            try {
                Wiersz wiersz = selected.getListawierszy().get(Integer.parseInt(indexwiersza));
                wiersz.getStronaWn().setKwota(kwotanowa);
                przepiszWaluty(wiersz);
                if (kwotastara!=0.0) {
                    sprawdzwartoscigrupy(wiersz);
                }
            } catch (Exception e1) {
                E.e(e1);
            }
            if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
                rozliczsaldoWBRK(Integer.parseInt(indexwiersza));
            }
        }
    }

    public void zdarzeniaOnBlurStronaKwotaMa(ValueChangeEvent e) {
        double kwotastara = (double) e.getOldValue();
        double kwotanowa = (double) e.getNewValue();
        if (Z.z(kwotastara) != Z.z(kwotanowa)) {
            String clientID = ((InputNumber) e.getSource()).getClientId();
            String indexwiersza = clientID.split(":")[2];
            try {
                Wiersz wiersz = selected.getListawierszy().get(Integer.parseInt(indexwiersza));
                wiersz.getStronaMa().setKwota(kwotanowa);
                przepiszWaluty(wiersz);
                if (kwotastara!=0.0) {
                    sprawdzwartoscigrupy(wiersz);
                }
            } catch (Exception e1) {
                E.e(e1);
            }
            if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
                rozliczsaldoWBRK(Integer.parseInt(indexwiersza));
            }
        }
    }
    
    public void skopiujopis(int nrbiezacegowiersza) {
        if(nrbiezacegowiersza==1){
            return;
        } else {
            int nrstaregowiersza = nrbiezacegowiersza-2;
            nrbiezacegowiersza = nrbiezacegowiersza-1;
            String biezacywiersz = selected.getListawierszy().get(nrbiezacegowiersza).getOpisWiersza();
            String poprzedniopisval = selected.getListawierszy().get(nrstaregowiersza).getOpisWiersza();
            if (biezacywiersz==null || biezacywiersz.equals("")) {
                selected.getListawierszy().get(nrbiezacegowiersza).setOpisWiersza(poprzedniopisval);
            }
        }
    }

    public void dodajPustyWierszNaKoncu() {
        int wynik = DialogWpisywanie.dodajPustyWierszNaKoncu(selected);
        //selected.przeliczKwotyWierszaDoSumyDokumentu();
        RequestContext.getCurrentInstance().update("formwpisdokument:panelwpisbutton");
        if (wynik == 1) {
            Msg.msg("w", "Uzupełnij dane przed dodaniem nowego wiersza");
        }
    }

    public void rozliczsaldoWBRK(int indexwTabeli) {
        Konto kontorozrachunkowe = selected.getRodzajedok().getKontorozrachunkowe();
        if (selected.getRodzajedok().getKategoriadokumentu() == 0 && kontorozrachunkowe != null) {
            int koncowyindex = selected.getListawierszy().size();
            for (int i = indexwTabeli; i < koncowyindex; i++) {
                DialogWpisywanie.rozliczPojedynczeSaldoWBRK(selected, i, kontorozrachunkowe);
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList:" + i + ":saldo");
            }
        }
    }

    
//////////////////////EWIDENCJE VAT
    
    public void podepnijEwidencjeVatDok(int rodzaj) {
        if (zapisz0edytuj1 == false && selected.getEwidencjaVAT() != null) {
            podepnijEwidencjeVat(rodzaj);
        } else {
            if (selected.getRodzajedok().getKategoriadokumentu() == 0 || selected.getRodzajedok().getKategoriadokumentu() == 5) {
                selected.setEwidencjaVAT(null);
            } else {
                podepnijEwidencjeVat(rodzaj);
            }
        }
    }
    
    public void podepnijEwidencjeVatDokBlur(int rodzaj) {
        if (zapisz0edytuj1 == false && selected.getEwidencjaVAT() != null 
                && selected.isDwarejestry() && czyrozjechalysiemce() && 
                (selected.getRodzajedok().getSkrot().equals("WNT") && selected.getEwidencjaVAT().size() != 2 ||
                selected.getRodzajedok().getSkrot().equals("RVC") && selected.getEwidencjaVAT().size() != 2 ||
                selected.getRodzajedok().getSkrot().equals("IU") && selected.getEwidencjaVAT().size() != 4)) {
            podepnijEwidencjeVat(rodzaj);
        }
        if (zapisz0edytuj1 == false && selected.getEwidencjaVAT() != null 
                && selected.isDwarejestry() && !czyrozjechalysiemce() && 
                (selected.getRodzajedok().getSkrot().equals("WNT") && selected.getEwidencjaVAT().size() == 2 ||
                selected.getRodzajedok().getSkrot().equals("RVC") && selected.getEwidencjaVAT().size() == 2 ||
                selected.getRodzajedok().getSkrot().equals("IU") && selected.getEwidencjaVAT().size() == 4)) {
            podepnijEwidencjeVat(rodzaj);
        }
    }
    
    public void podepnijEwidencjeVat(int rodzaj) {
            if (wpisView.isVatowiec() && selected.getRodzajedok().getKategoriadokumentu() != 0 && selected.getRodzajedok().getKategoriadokumentu() != 5) {
                    //0 jest przay wpisie
                    int k = 0;
                    if (rodzaj == 0) {
                        this.selected.setEwidencjaVAT(new ArrayList<EVatwpisFK>());
                    } else {
                        k = this.selected.getEwidencjaVAT().size();
                    }
                    if (selected.getTabelanbp() == null) {
                        symbolWalutyNettoVat = " zł";
                    } else {
                        symbolWalutyNettoVat = " " + selected.getTabelanbp().getWaluta().getSkrotsymbolu();
                    }
                    Evewidencja ewidencjazakupu = evewidencjaDAO.znajdzponazwie("zakup");;
                    List<Evewidencja> opisewidencji = pobierzewidencje();
                    for (Evewidencja p : opisewidencji) {
                        if(selected.isDwarejestry() && czyrozjechalysiemce()) {
                            EVatwpisFK pierwszaewid = new EVatwpisFK(k++, p, selected);
                            pierwszaewid.setNieduplikuj(true);
                            this.selected.getEwidencjaVAT().add(pierwszaewid);
                            String[] rokmiesiacduplikatu = rokmiesiacduplikatu();
                            EVatwpisFK drugaewid = (EVatwpisFK) beansVAT.EwidencjaVATSporzadzanie.duplikujEVatwpisSuper(pierwszaewid,ewidencjazakupu);
                            drugaewid.setLp(k++);
                            drugaewid.setNieduplikuj(true);
                            drugaewid.setRokEw(rokmiesiacduplikatu[0]);
                            drugaewid.setMcEw(rokmiesiacduplikatu[1]);
                            this.selected.getEwidencjaVAT().add(drugaewid);
                        } else {
                            EVatwpisFK pierwszaewid = new EVatwpisFK(k++, p, selected);
                            if (selected.getRodzajedok().isTylkovatnalezny()) {
                                pierwszaewid.setNieduplikuj(true);
                            }
                            this.selected.getEwidencjaVAT().add(pierwszaewid);
                        }
                    }
                    //niepotrzebne renderuje 15 razy
                    //RequestContext.getCurrentInstance().update("formwpisdokument:panelzewidencjavat");
            } else {
                this.selected.setEwidencjaVAT(new ArrayList<EVatwpisFK>());
            }
    }
    
    private boolean czyrozjechalysiemce() {
        boolean zwrot = false;
        if (selected.getDataoperacji() != null && selected.getDatawplywu() != null) {
            String dataoperacji = selected.getDataoperacji();
            String datawplywu = selected.getDatawplywu();
            if (Data.getMc(dataoperacji).equals(Data.getMc(datawplywu))) {
                return false;
            }
            int ilemiesiecy = Mce.odlegloscMcy(Data.getMc(dataoperacji), Data.getRok(dataoperacji), Data.getMc(datawplywu), Data.getRok(datawplywu));
            if (ilemiesiecy > 3) {
                zwrot = true;
            }
        }
        return zwrot;
    }
    
    private String[] rokmiesiacduplikatu() {
        String[] zwrot = new String[2];
        String dzienwplywu = Data.getDzien(selected.getDatawplywu());
        String miesiacwplywu = Data.getMc(selected.getDatawplywu());
        String rokwplywu = Data.getRok(selected.getDatawplywu());
        if (Integer.valueOf(dzienwplywu) < 25) {
                if (miesiacwplywu.equals("01")) {
                    zwrot[0] = Roki.rokPoprzedni(rokwplywu);
                } else {
                    zwrot[0] = rokwplywu;
                }
                zwrot[1] = Mce.zmniejszmiesiac(rokwplywu, miesiacwplywu)[1];
        } else {
                zwrot[0] = rokwplywu;
                zwrot[1] = miesiacwplywu;
        }
        return zwrot;
    }
    
    private List<Evewidencja> pobierzewidencje() {
        List<Evewidencja> l = Collections.synchronizedList(new ArrayList<>());
        l.addAll(listaEwidencjiVat.pobierzEvewidencje(selected.getRodzajedok().getRodzajtransakcji()));
        if (selected.getSeriadokfk().equals("UPTK")) {
            for (Iterator<Evewidencja> it = l.iterator();it.hasNext();) {
                Evewidencja p = it.next();
                if (p.getNazwa().equals("usługi świad. poza ter.kraju art. 100 ust.1 pkt 4")) {
                    it.remove();
                }
            }
        } else if (selected.getSeriadokfk().equals("UPTK100")) {
            for (Iterator<Evewidencja> it = l.iterator();it.hasNext();) {
                Evewidencja p = it.next();
                if (p.getNazwa().equals("usługi świad. poza ter.kraju")) {
                    it.remove();
                }
            }
        }
        return l;
    }

    public void usunEwidencjeDodatkowa(EVatwpisFK eVatwpisFK) {
        if (selected.getEwidencjaVAT() != null && selected.getEwidencjaVAT().size() > 1) {
            for (Iterator<EVatwpisFK> it = selected.getEwidencjaVAT().iterator(); it.hasNext();) {
                EVatwpisFK p = (EVatwpisFK) it.next();
                if (p.equals(eVatwpisFK)) {
                    it.remove();
                }
                if (selected.getEwidencjaVAT().size()==1) {
                    break;
                }
            }
        }
    }

    private void stworzlisteewidencjiRK() {
        List<String> nazwyewidencji = Collections.synchronizedList(new ArrayList<>());
        nazwyewidencji.add("zakup");
        nazwyewidencji.add("sprzedaż 23%");
        nazwyewidencji.add("sprzedaż 8%");
        nazwyewidencji.add("sprzedaż 5%");
        nazwyewidencji.add("sprzedaż 0%");
        nazwyewidencji.add("sprzedaż zw");
        for (String p : nazwyewidencji) {
            listaewidencjivatRK.add(evewidencjaDAO.znajdzponazwie(p));
        }
    }

    public void dolaczWierszeZKwotami(EVatwpisFK evatwpis) {
        boolean niesumuj = evatwpis.isNieduplikuj() && evatwpis.getEwidencja().getNazwa().equals("zakup");
        if (!selected.iswTrakcieEdycji() && !niesumuj && !selected.getRodzajedok().isTylkojpk()){
            Rodzajedok rodzajdok = selected.getRodzajedok();
            WartosciVAT wartosciVAT = podsumujwartosciVAT(selected.getEwidencjaVAT());
            if (selected.getListawierszy().size() == 1 && selected.isImportowany() == false) {
                if (kontoRozrachunkowe == null) {
                    kontoRozrachunkowe = pobierzKontoRozrachunkowe(kliencifkDAO, selected, wpisView, kontoDAOfk);
                }
                if (rodzajdok.getKategoriadokumentu() == 1) {
                    if (selected.getRodzajedok().getProcentvat() != 0.0 && evatwpis.getEwidencja().getTypewidencji().equals("z")) {
                        //oblicza polowe vat dla faktur samochody osobowe
                        evatwpis.setVat(Z.z(wartosciVAT.getVatPlndodoliczenia()));
                        evatwpis.setBrutto(Z.z(evatwpis.getNetto() + evatwpis.getVat()));
                        RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:" + evatwpis.getLp() + ":vat");
                        RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:" + evatwpis.getLp() + ":brutto");
                    }
                    rozliczVatKoszt(evatwpis, wartosciVAT, selected, kontadlaewidencji, wpisView, poprzedniDokument, kontoRozrachunkowe, nkup);
                } else if (selected.getListawierszy().get(0).getStronaWn().getKonto() == null && rodzajdok.getKategoriadokumentu() == 2) {
                    rozliczVatPrzychod(evatwpis, wartosciVAT, selected, kontadlaewidencji, wpisView, poprzedniDokument, kontoRozrachunkowe);
                }
            } else if (selected.getListawierszy().size() > 1 && rodzajdok.getKategoriadokumentu() == 1) {
                rozliczVatKosztEdycja(evatwpis, wartosciVAT, selected, wpisView, nkup);
            } else if (selected.getListawierszy().size() > 1 && rodzajdok.getKategoriadokumentu() == 2) {
                rozliczVatPrzychodEdycja(evatwpis, wartosciVAT, selected, wpisView);
            }
            selected.setZablokujzmianewaluty(true);
            RequestContext.getCurrentInstance().update("formwpisdokument:panelwalutowywybor");
            RequestContext.getCurrentInstance().update("formwpisdokument:panelwpisbutton");
        } else {
            Msg.msg("w", "Dokument w trybie edycji. Automatyczne dodawanie wierszy wyłączone");
        }
    }

    public void dolaczWierszZKwotamiRK() {
        if (!selected.getEwidencjaVAT().contains(ewidencjaVatRK)) {
            selected.getEwidencjaVAT().add(ewidencjaVatRK);
        }
        String dzien = ewidencjaVatRK.getDatadokumentu().split("-")[2];
        wierszRK.setDataWalutyWiersza(dzien);
        EVatwpisFK evatwpis = ewidencjaVatRK;
        Wiersz w = evatwpis.getWiersz();
        double[] wartosciVAT = podsumujwartosciVATRK(ewidencjaVatRK);
        List<Wiersz> dodanewiersze = null;
        if (ewidencjaVatRK.getEwidencja().getNazwa().equals("zakup")) {
            dodanewiersze = rozliczVatKosztRK(evatwpis, wartosciVAT, selected, wpisView, wierszRKindex, kontoDAOfk, kontadlaewidencji, nkup);
        } else if (!ewidencjaVatRK.getEwidencja().getNazwa().equals("zakup")) {
            dodanewiersze = rozliczVatPrzychodRK(evatwpis, wartosciVAT, selected, wpisView, wierszRKindex, kontoDAOfk, kontadlaewidencji);
        }
        for (Wiersz p : dodanewiersze) {
            przepiszWaluty(p);
        }
        String update = "formwpisdokument:dataList";
        RequestContext.getCurrentInstance().update(update);
        ewidencjaVatRK = new EVatwpisFK();
        selected.przeliczKwotyWierszaDoSumyDokumentu();
        RequestContext.getCurrentInstance().update("formwpisdokument:panelwpisbutton");
        Msg.msg("Zachowano zapis w ewidencji VAT");
    }

    public void edytujWierszZKwotamiRK() {
        if (ewidencjaVatRK.getNetto() == 0.0 && ewidencjaVatRK.getVat() == 0.0) {
            selected.getEwidencjaVAT().remove(ewidencjaVatRK);
            Msg.msg("Usunieto ewidencje VAT do bieżącego wiersza");
        } else {
            String dzien = ewidencjaVatRK.getDatadokumentu().split("-")[2];
            wierszRK.setDataWalutyWiersza(dzien);
            EVatwpisFK e = ewidencjaVatRK;
            Wiersz w = e.getWiersz();
            double[] wartosciVAT = podsumujwartosciVATRK(ewidencjaVatRK);
            List<Wiersz> dodanewiersze = null;
            if (ewidencjaVatRK.getEwidencja().getNazwa().equals("zakup")) {
                dodanewiersze = rozliczEdytujVatKosztRK(e, wartosciVAT, selected, wierszRKindex, kontadlaewidencji, nkup);
            } else if (!ewidencjaVatRK.getEwidencja().getNazwa().equals("zakup")) {
                dodanewiersze = rozliczEdytujVatPrzychodRK(e, wartosciVAT, selected, wierszRKindex);
            }
            for (Wiersz p : dodanewiersze) {
                przepiszWaluty(p);
            }
            ObslugaWiersza.przenumerujSelected(selected);
            String update = "formwpisdokument:dataList";
            RequestContext.getCurrentInstance().update(update);
            ewidencjaVatRK = new EVatwpisFK();
            Msg.msg("Zachowano zapis w ewidencji VAT");
        }
    }

    public void updatenetto(EVatwpisFK evatwpis, String form) {
        ustawvat(evatwpis, selected);
        evatwpis.setBrutto(Z.z(evatwpis.getNetto() + evatwpis.getVat()));
        int lp = evatwpis.getLp();
        evatwpis.setSprawdzony(0);
        symbolWalutyNettoVat = " zł";
        String update = form + ":tablicavat:" + lp + ":netto";
        RequestContext.getCurrentInstance().update(update);
        update = form + ":tablicavat:" + lp + ":vat";
        RequestContext.getCurrentInstance().update(update);
        update = form + ":tablicavat:" + lp + ":brutto";
        RequestContext.getCurrentInstance().update(update);
        String activate = "document.getElementById('" + form + ":tablicavat:" + lp + ":vat_input').select();";
        RequestContext.getCurrentInstance().execute(activate);
    }

    public void updatevat(EVatwpisFK evatwpis, String form) {
        int lp = evatwpis.getLp();
        Waluty w = selected.getWalutadokumentu();
        if (!w.getSymbolwaluty().equals("PLN")) {
            double kurs = selected.getTabelanbp().getKurssredniPrzelicznik();
            evatwpis.setVatwwalucie(Z.z(evatwpis.getVat() / kurs));
        }
        evatwpis.setBrutto(Z.z(evatwpis.getNetto() + evatwpis.getVat()));
        evatwpis.setSprawdzony(0);
        String update = form + ":tablicavat:" + lp + ":brutto";
        RequestContext.getCurrentInstance().update(update);
        String activate = "document.getElementById('" + form + ":tablicavat:" + lp + ":brutto_input').select();";
        RequestContext.getCurrentInstance().execute(activate);
    }

    public void updatenettoRK() {
        EVatwpisFK evatwpis = ewidencjaVatRK;
        double stawkavat = 0.23;
        ustawvat(evatwpis, selected, stawkavat);
        evatwpis.setBrutto(Z.z(evatwpis.getNetto() + evatwpis.getVat()));
        String update = "ewidencjavatRK:netto";
        RequestContext.getCurrentInstance().update(update);
        update = "ewidencjavatRK:vat";
        RequestContext.getCurrentInstance().update(update);
        update = "ewidencjavatRK:brutto";
        RequestContext.getCurrentInstance().update(update);
        String activate = "document.getElementById('ewidencjavatRK:vat_input').select();";
        RequestContext.getCurrentInstance().execute(activate);
    }

    public void updatevatRK() {
        EVatwpisFK e = ewidencjaVatRK;
        Waluty w = selected.getWalutadokumentu();
        double kurs = selected.getTabelanbp().getKurssredniPrzelicznik();
        if (!w.getSymbolwaluty().equals("PLN")) {
            e.setVatwwalucie(Z.z(e.getVat() / kurs));
        }
        e.setBrutto(Z.z(e.getNetto() + e.getVat()));
        String update = "ewidencjavatRK:brutto";
        RequestContext.getCurrentInstance().update(update);
        String activate = "document.getElementById('ewidencjavatRK:brutto_input').select();";
        RequestContext.getCurrentInstance().execute(activate);
    }

//////////////////////////////EWIDENCJE VAT
    public void dodaj() {
        if (selected.getListawierszy().get(selected.getListawierszy().size() - 1).getOpisWiersza().equals("")) {
            komunikatywpisdok = "Probujesz zapisać pusty dokument";
            RequestContext.getCurrentInstance().update("formwpisdokument:komunikatywpisdok");
            return;
        }
        if (selected.getNumerwlasnydokfk() == null || selected.getNumerwlasnydokfk().isEmpty()) {
            komunikatywpisdok = "Brak numeru własnego dokumentu. Nie można zapisać dokumentu.";
            RequestContext.getCurrentInstance().update("formwpisdokument:komunikatywpisdok");
        } else if (ObslugaWiersza.sprawdzSumyWierszy(selected)) {
            if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
                int index = selected.getListawierszy().size() - 1;
                rozliczsaldoWBRK(index);
                //RequestContext.getCurrentInstance().update("formwpisdokument:dataList:" + index + ":saldo");
                selected.setSaldokoncowe(selected.getListawierszy().get(selected.getListawierszy().size() - 1).getSaldoWBRK());

            }
            if (selected.getRodzajedok().getKategoriadokumentu() == 0 && !selected.getWalutadokumentu().getSymbolwaluty().equals("PLN")) {
                
            }
            try {
                selected.setLp(selected.getNrkolejnywserii());
                selected.setPodatnikObj(wpisView.getPodatnikObiekt());
                selected.oznaczVATdokument(sprawdzjakiokresvat());//nanosi zmiany okresu vat
                if ((selected.getRodzajedok().getKategoriadokumentu() == 0 || selected.getRodzajedok().getKategoriadokumentu() == 5) && klientdlaPK != null) {
                    selected.setKontr(klientdlaPK);
                }
                UzupelnijWierszeoDane.uzupelnijWierszeoDate(selected);
                //mialo sprawdzac czy sa rozrachunki, ale jak ich nie ma to wywala komunikat, ktory i tak nie jest wyswietlany...
//                if (selected.sprawdzczynaniesionorozrachunki() == 1) {
//                    komunikatywpisdok = "Brak numeru własnego dokumentu. Nie można zapisać dokumentu.";
//                    RequestContext.getCurrentInstance().update("formwpisdokument:komunikatywpisdok");
//                }
                if (!selected.getRodzajedok().isTylkojpk()) {
                    for (Wiersz p : selected.getListawierszy()) {
                        przepiszWalutyZapisEdycja(p);
                    }

                    oznaczdokumentSTRMK(selected, "0");
                    oznaczdokumentSTRMK(selected, "64");
                    //dodaje roznice kursowa w dokumencie
                    oznaczdokumentRozKurs(selected);
                    //nanieswierszeRRK(selected);
                    selected.przeliczKwotyWierszaDoSumyDokumentu();
                } else {
                    selected.getListawierszy().remove(0);
                }
                selected.setDataujecia(new Date());
                dokDAOfk.dodaj(selected);
                biezacetransakcje = null;
                //Dokfk dodany = dokDAOfk.findDokfkObj(selected);
                wykazZaksiegowanychDokumentow.add(selected);
                resetujDokument();
                Msg.msg("i", "Dokument dodany");
                RequestContext.getCurrentInstance().update("wpisywaniefooter");
                RequestContext.getCurrentInstance().update("rozrachunki");
                RequestContext.getCurrentInstance().update("formwpisdokument");
                RequestContext.getCurrentInstance().execute("r('formwpisdokument:data2DialogWpisywanie').select();");
            } catch (Exception e) {
                E.e(e);
                komunikatywpisdok = "Brak numeru własnego dokumentu. Nie można zapisać dokumentu.";
                RequestContext.getCurrentInstance().update("formwpisdokument:komunikatywpisdok");
                Msg.msg("e", "Nie udało się dodac dokumentu " + e.getMessage());
                RequestContext.getCurrentInstance().execute("powrotdopolaPoNaniesieniuRozrachunkow();");
            }
        } else {
            komunikatywpisdok = "Uzupełnij wiersze o kwoty/konto. Sprawdź numery kont";
            RequestContext.getCurrentInstance().update("formwpisdokument:komunikatywpisdok");
            Msg.msg("w", "Uzupełnij wiersze o kwoty/konto. Sprawdź numery kont");
        }

    }

    public void przepiszWaluty(Wiersz wiersz) {
        try {
            StronaWiersza wn = wiersz.getStronaWn();
            StronaWiersza ma = wiersz.getStronaMa();
            if (wiersz == null) {
                wn.setKwotaPLN(0.0);
                wn.setKwotaWaluta(0.0);
                ma.setKwotaPLN(0.0);
                ma.setKwotaWaluta(0.0);
            } else if (wiersz.getTabelanbp() == null) {
                if (wn != null && wn.getKwotaPLN() == 0.0) {
                    if (wn.getSymbolWalutyBO().equals("PLN")) {
                        wn.setKwotaPLN(wn.getKwota());
                        wn.setKwotaWaluta(wn.getKwota());
                    } else {
                        wn.setKwotaPLN(StronaWierszaBean.przeliczWalutyWnBO(wiersz));
                        wn.setKwotaWaluta(wn.getKwota());
                    }
                }
                if (ma != null && ma.getKwotaPLN() == 0.0) {
                    if (ma.getSymbolWalutyBO().equals("PLN")) {
                        ma.setKwotaPLN(ma.getKwota());
                        ma.setKwotaWaluta(ma.getKwota());
                    } else {
                        ma.setKwotaPLN(StronaWierszaBean.przeliczWalutyMaBO(wiersz));
                        ma.setKwotaWaluta(ma.getKwota());
                    }

                }
            } else if (wiersz.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
                if (wn != null) {
                    wn.setKwotaPLN(wn.getKwota());
                    wn.setKwotaWaluta(wn.getKwota());
                }
                if (ma != null) {
                    ma.setKwotaPLN(ma.getKwota());
                    ma.setKwotaWaluta(ma.getKwota());
                }
            } else {
                if (wn != null) {
                    wn.setKwotaPLN(StronaWierszaBean.przeliczWalutyWn(wiersz));
                    wn.setKwotaWaluta(wn.getKwota());
                }
                if (ma != null) {
                    ma.setKwotaPLN(StronaWierszaBean.przeliczWalutyMa(wiersz));
                    ma.setKwotaWaluta(ma.getKwota());
                }
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("Blad DokfkView przepisz waluty");
        }
    }

    public void przepiszWalutyZapisEdycja(Wiersz wiersz) {
        try {
            StronaWiersza wn = wiersz.getStronaWn();
            StronaWiersza ma = wiersz.getStronaMa();
            if (wiersz.getTabelanbp() == null) {
                if (wn != null && wn.getKwotaPLN() == 0.0) {
                    if (wn.getSymbolWalutyBO().equals("PLN")) {
                        wn.setKwotaPLN(wn.getKwota());
                        wn.setKwotaWaluta(wn.getKwota());
                    } else {
                        wn.setKwotaPLN(StronaWierszaBean.przeliczWalutyWnBO(wiersz));
                        wn.setKwotaWaluta(wn.getKwota());
                    }
                }
                if (ma != null && ma.getKwotaPLN() == 0.0) {
                    if (ma.getSymbolWalutyBO().equals("PLN")) {
                        ma.setKwotaPLN(ma.getKwota());
                        ma.setKwotaWaluta(ma.getKwota());
                    } else {
                        ma.setKwotaPLN(StronaWierszaBean.przeliczWalutyMaBO(wiersz));
                        ma.setKwotaWaluta(ma.getKwota());
                    }

                }
            } else if (wiersz.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
                if (wn != null && wn.getKwotaPLN() == 0.0) {
                    wn.setKwotaPLN(wn.getKwota());
                    wn.setKwotaWaluta(wn.getKwota());
                }
                if (ma != null && ma.getKwotaPLN() == 0.0) {
                    ma.setKwotaPLN(ma.getKwota());
                    ma.setKwotaWaluta(ma.getKwota());
                }
            } else {
                if (wn != null && wn.getKwotaPLN() == 0.0) {
                    wn.setKwotaPLN(StronaWierszaBean.przeliczWalutyWn(wiersz));
                    wn.setKwotaWaluta(wn.getKwota());
                }
                if (ma != null && ma.getKwotaPLN() == 0.0) {
                    ma.setKwotaPLN(StronaWierszaBean.przeliczWalutyMa(wiersz));
                    ma.setKwotaWaluta(ma.getKwota());
                }
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("Blad DokfkView przepisz waluty");
        }
    }

    public void edycja() {
        if (selected.getListawierszy().get(selected.getListawierszy().size() - 1).getOpisWiersza().equals("")) {
            return;
        }
        if (ObslugaWiersza.sprawdzSumyWierszy(selected)) {
            if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
                int index = selected.getListawierszy().size() - 1;
                rozliczsaldoWBRK(index);
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList:" + index + ":saldo");
                selected.setSaldokoncowe(selected.getListawierszy().get(selected.getListawierszy().size() - 1).getSaldoWBRK());

            }
            selected.setwTrakcieEdycji(false);
            try {
                UzupelnijWierszeoDane.uzupelnijWierszeoDate(selected);
                if (selected.getSeriadokfk().equals("BO")) {
                    selected.przepiszWierszeBO();
                }
                selected.setwTrakcieEdycji(false);
                selected.setImportowany(false);
                selected.oznaczVATdokument(sprawdzjakiokresvat());
                if (!selected.getRodzajedok().isTylkojpk()) {
                    for (Wiersz p : selected.getListawierszy()) {
                        przepiszWalutyZapisEdycja(p);
                    }
                    ObslugaWiersza.przenumerujSelected(selected);
                    oznaczdokumentSTRMK(selected, "0");
                    oznaczdokumentSTRMK(selected, "64");
                    //dodaje roznice kursowa w dokumencie
                    oznaczdokumentRozKurs(selected);
                    //nanieswierszeRRK(selected);
                    selected.przeliczKwotyWierszaDoSumyDokumentu();
                } else {
                    selected.getListawierszy().remove(0);
                }
                selected.setDataujecia(new Date());
                dokDAOfk.edit(selected);
                wykazZaksiegowanychDokumentow.remove(selected);
                wykazZaksiegowanychDokumentow.add(dokDAOfk.findDokfkObj(selected));
                Collections.sort(wykazZaksiegowanychDokumentow, new Dokfkcomparator());
                resetujDokument();
                if (totylkoedycjazapis) {
                    //to jest potrzebne w sumie do edycji dokumenty z zapisow konta, tylko ze wywolujemy inita KOmntoZapisy FKView
                    kontoZapisFKView.pobierzzapisy(wpisView.getRokWpisuSt());
                    totylkoedycjazapis = false;
                }
                 if (totylkoedycjaanalityczne) {
                    //to jest potrzebne w sumie do edycji dokumenty z zapisow konta, tylko ze wywolujemy inita KOmntoZapisy FKView
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    SaldoAnalitykaView saldoAnalitykaView = (SaldoAnalitykaView) facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null,"saldoAnalitykaView"); 
                    saldoAnalitykaView.przeliczSaldoKonto(duzyidwierszedycjaodswiezenie);
                    totylkoedycjaanalityczne = false;
                    RequestContext.getCurrentInstance().update("formtablicaanalityczne:tablicasaldaanalityczne:"+duzyidwierszedycjaodswiezenie+":pol1");
                    RequestContext.getCurrentInstance().update("formtablicaanalityczne:tablicasaldaanalityczne:"+duzyidwierszedycjaodswiezenie+":pol2");
                    RequestContext.getCurrentInstance().update("formtablicaanalityczne:tablicasaldaanalityczne:"+duzyidwierszedycjaodswiezenie+":pol3");
                    RequestContext.getCurrentInstance().update("formtablicaanalityczne:tablicasaldaanalityczne:"+duzyidwierszedycjaodswiezenie+":pol4");
                    RequestContext.getCurrentInstance().update("formtablicaanalityczne:tablicasaldaanalityczne:"+duzyidwierszedycjaodswiezenie+":pol5");
                    RequestContext.getCurrentInstance().update("formtablicaanalityczne:tablicasaldaanalityczne:"+duzyidwierszedycjaodswiezenie+":pol6");
                    RequestContext.getCurrentInstance().update("formtablicaanalityczne:tablicasaldaanalityczne:"+duzyidwierszedycjaodswiezenie+":pol7");
                    RequestContext.getCurrentInstance().update("formtablicaanalityczne:tablicasaldaanalityczne:"+duzyidwierszedycjaodswiezenie+":pol8");
                    RequestContext.getCurrentInstance().update("formtablicaanalityczne:tablicasaldaanalityczne:"+duzyidwierszedycjaodswiezenie+":pol9");
                    RequestContext.getCurrentInstance().update("formtablicaanalityczne:tablicasaldaanalityczne:"+duzyidwierszedycjaodswiezenie+":pol10");
                    RequestContext.getCurrentInstance().update("formtablicaanalityczne:tablicasaldaanalityczne:"+duzyidwierszedycjaodswiezenie+":pol11");
                    RequestContext.getCurrentInstance().update("formtablicaanalityczne:tablicasaldaanalityczne:"+duzyidwierszedycjaodswiezenie+":pol12");
                    RequestContext.getCurrentInstance().update("formtablicaanalityczne:tablicasaldaanalityczne:"+duzyidwierszedycjaodswiezenie+":zapisynasaldokonto");
                }
                RequestContext.getCurrentInstance().update("zestawieniedokumentow:dataList:"+idwierszedycjaodswiezenie+":wartoscdokumentuzest");
                RequestContext.getCurrentInstance().update("zestawieniedokumentow:dataList:"+idwierszedycjaodswiezenie+":wartoscdokumentuzest");
                RequestContext.getCurrentInstance().update("zestawieniedokumentow:dataList:"+idwierszedycjaodswiezenie+":walutadokumentuzest");
                RequestContext.getCurrentInstance().update("zestawieniedokumentow:dataList:"+idwierszedycjaodswiezenie+":vatdokumentuzest");
                if (linijkaewidencjiupdate != null) {
                    RequestContext.getCurrentInstance().update(linijkaewidencjiupdate);
                    linijkaewidencjiupdate = null;
                }
                Msg.msg("i", "Pomyślnie zaktualizowano dokument");
                RequestContext.getCurrentInstance().execute("PF('wpisywanie').hide();");
            } catch (Exception e) {
                E.e(e);
                komunikatywpisdok = "Nie udało się zmienic dokumentu ";
                RequestContext.getCurrentInstance().update("formwpisdokument:komunikatywpisdok");
                Msg.msg("e", "Nie udało się zmienić dokumentu " + e.toString());
            }
        } else {
            komunikatywpisdok = "Uzupełnij wiersze o kwoty/konto!";
            RequestContext.getCurrentInstance().update("formwpisdokument:komunikatywpisdok");
        }
    }

    public void edycjaDlaRozrachunkow() {
        try {
            UzupelnijWierszeoDane.uzupelnijWierszeoDate(selected);
            dokDAOfk.edit(selected);
            Msg.msg("i", "Pomyślnie zaktualizowano dokument edycja rozrachunow");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie udało się zmenic dokumentu podczas edycji rozrachunkow " + e.toString());
        }
    }

    public void przygotujdousuniecia(Dokfk dousuniecia) {
        dokumentdousuniecia = dousuniecia;
    }

    public void usundokument() {
        try {
            dokDAOfk.usun(dokumentdousuniecia);
            wykazZaksiegowanychDokumentow.remove(dokumentdousuniecia);
            if (filteredValue != null) {
                filteredValue.remove(dokumentdousuniecia);
            }
            try {
                wykazZaksiegowanychDokumentowimport.remove(dokumentdousuniecia);
            } catch (Exception e1) {
                E.e(e1);
            }
            dokumentdousuniecia = null;
            Msg.msg("i", "Dokument usunięty");
        } catch (EJBException ep) {
            Msg.msg("e", "Nie udało się usunąć dokumentu. Czy nie jest to dokument środka trwałego lub RMK? Czy nie zostal do transakcji wygenerowany rokument roznic kursowych?");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie udało się usunąć dokumentu "+E.e(e));
        }
    }

    
    //usuwa wiersze z dokumentu
    public void usunWierszWDokumencie() {
        try {
            int liczbawierszyWDokumencie = selected.getListawierszy().size();
            Wiersz wierszDoUsuniecia = null;
            if (liczbawierszyWDokumencie > 1) {
                wierszDoUsuniecia = selected.getListawierszy().get(liczbawierszyWDokumencie - 1);
                if (wierszDoUsuniecia.getTypWiersza() == 5) {
                    Msg.msg("e", "Usuń najpierw wiersz z 4.");
                } else {
                    //usunrozrachunki(liczbawierszyWDokumencie);
                    liczbawierszyWDokumencie--;
                    selected.getListawierszy().remove(liczbawierszyWDokumencie);
                    Msg.msg("Wiersz usunięty.");
                }
                for (Iterator<EVatwpisFK> it = selected.getEwidencjaVAT().iterator(); it.hasNext();) {
                    EVatwpisFK p = it.next();
                    if (p.getWiersz() == wierszDoUsuniecia) {
                        it.remove();
                    }
                }
            } else if (liczbawierszyWDokumencie == 1) {
                wierszDoUsuniecia = selected.getListawierszy().get(0);
                try {
                    if (wiersz.getIdporzadkowy() != null) {
                      //  usunrozrachunki(liczbawierszyWDokumencie);
                        selected.setListawierszy(new ArrayList<Wiersz>());
                        liczbawierszyWDokumencie--;
                    } else {
                        //usunrozrachunki(liczbawierszyWDokumencie);
                        selected.getListawierszy().remove(0);
                        liczbawierszyWDokumencie--;
                    }
                    Msg.msg("Wiersz usunięty.");
                } catch (Exception e) {
                    E.e(e);

                }
            }
            if (liczbawierszyWDokumencie == 0) {
                selected.getListawierszy().add(ObslugaWiersza.ustawPierwszyWiersz(selected));
                liczbawierszyWDokumencie = 1;
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("Błąd podczas usuwania wiersza");
        }
    }

//    //***************************************
    public void znajdzduplicatdokumentu() {
        //uruchamiaj tylko jak jest wpisywanie a nie edycja
        if (rodzajBiezacegoDokumentu == 0 || rodzajBiezacegoDokumentu == 5) {
            if (zapisz0edytuj1 == false) {
                Dokfk dokument = null;
                try {
                    dokument = dokDAOfk.findDokfkObj(selected);
                } catch (Exception e) {
                    E.e(e);
                }
                if (dokument != null) {
                    wlaczZapiszButon = false;
                    //RequestContext.getCurrentInstance().execute("znalezionoduplikat();");
                    //Msg.msg("e", "Blad dokument o takim numerze juz istnieje");
                } else {
                    wlaczZapiszButon = true;
                }
            }
        }
    }
    
//zastapilem to javascriptem nie do konca wiec jest hybryda. znikalo jak inne rzeczy odsiwezalem
    public void skopiujopisdopierwszegowiersza() {
        try {
            Wiersz w = selected.getListawierszy().get(0);
            boolean kopiowac = w.getOpisWiersza() == null || w.getOpisWiersza().equals("");
            boolean kopiowac1 = w.getStronyWiersza().size() == 2;
            if (kopiowac || kopiowac1) {
                w.setOpisWiersza(selected.getOpisdokfk());
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            }
        } catch (Exception e) {
            E.e(e);
        }
    }

    public void znajdzduplicatdokumentuKontrahent() {
        wlaczZapiszButon = true;
        if (zapisz0edytuj1 == false && selected.getKontr() != null && !selected.getKontr().getNpelna().equals("nowy kontrahent")) {
            Dokfk dokument = null;
            try {
                dokument = dokDAOfk.findDokfkObjKontrahent(selected);
            } catch (Exception e) {
                E.e(e);
            }
            if (dokument != null) {
                wlaczZapiszButon = false;
            } else {
                wlaczZapiszButon = true;
            }
        }
    }

    public void pobierzopiszpoprzedniegodok() {
        Dokfk poprzedniDokument = dokDAOfk.findDokfkLastofaTypeKontrahent(wpisView.getPodatnikObiekt(), selected.getRodzajedok().getSkrot(), selected.getKontr(), wpisView.getRokWpisuSt());
        if (poprzedniDokument != null && selected.getOpisdokfk() == null) {
            selected.setOpisdokfk(poprzedniDokument.getOpisdokfk());
            Wiersz w = selected.getListawierszy().get(0);
            if (w.getOpisWiersza() == null || w.getOpisWiersza().equals("")) {
                w.setOpisWiersza(selected.getOpisdokfk());
            }
        }
        kontoRozrachunkowe = pobierzKontoRozrachunkowe(kliencifkDAO, selected, wpisView, kontoDAOfk);
    }

    public void dodajklientaautomatRK() {
        try {
            if (ewidencjaVatRK.getKlient().getNpelna().equals("dodaj klienta automatycznie")) {
                Klienci dodany = SzukajDaneBean.znajdzdaneregonAutomat(ewidencjaVatRK.getKlient().getNip(), gUSView);
                ewidencjaVatRK.setKlient(dodany);
                if (!dodany.getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
                    klienciDAO.dodaj(dodany);
                }
                RequestContext.getCurrentInstance().update("ewidencjavatRK:klientRK");
            } else {
                ewidencjaVatRK.setOpisvat(pobierzopis(ewidencjaVatRK.getKlient()));
                RequestContext.getCurrentInstance().update("ewidencjavatRK:opisvat");
            }
        } catch (Exception e) {
            
        }
    }
    
    private String pobierzopis(Klienci klient) {
        String zwrot = null;
        try {
            zwrot = eVatwpisFKDAO.findEVatwpisFKPodatnikKlient(wpisView.getPodatnikObiekt(), klient, wpisView.getRokWpisuSt());
        } catch (Exception e) {
            
        }
        return zwrot;
    }
    
    
    public void pobierzopiszpoprzedniegodokItemSelect() {
        try {
            if (selected.getKontr().getNpelna().equals("dodaj klienta automatycznie")) {
                Klienci dodany = SzukajDaneBean.znajdzdaneregonAutomat(selected.getKontr().getNip(), gUSView);
                if (!dodany.getNpelna().equals("dodaj klienta automatycznie")) {
                    selected.setKontr(dodany);
                    if (!dodany.getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
                        klienciDAO.dodaj(dodany);
                        //null bo dodajemy nowego kontrahenta inaczej wezmie ze starego
                        kontoRozrachunkowe = null;
                        poprzedniDokument = null;
                    }
                }
                RequestContext.getCurrentInstance().update("formwpisdokument:acForce");
            } else {
                if (selected.getKontr() != null) {
                    poprzedniDokument = dokDAOfk.findDokfkLastofaTypeKontrahent(wpisView.getPodatnikObiekt(), selected.getRodzajedok().getSkrot(), selected.getKontr(), wpisView.getRokWpisuSt());
                    if (poprzedniDokument != null) {
                            selected.setOpisdokfk(poprzedniDokument.getOpisdokfk());
                            RequestContext.getCurrentInstance().update("formwpisdokument:opisdokumentu");
                            Wiersz w = selected.getListawierszy().get(0);
                            if (w.getOpisWiersza() == null || w.getOpisWiersza().equals("")) {
                                w.setOpisWiersza(selected.getOpisdokfk());
                            }
                        if(wpisView.getVatokres()==0) {
                            DokFKVATBean.pobierzkontaZpoprzedniegoDokumentu(poprzedniDokument, selected);
                        }
                    } else {
                        kontoRozrachunkowe = null;
                    }
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
    }

    public void wygenerujokreswpisudokumentu(AjaxBehaviorEvent event) {
        komunikatywpisdok = "";
        RequestContext.getCurrentInstance().update("formwpisdokument:komunikatywpisdok");
        //generuje okres wpisu tylko jezeli jest w trybie wpisu, a wiec zapisz0edytuj1 jest false
        if (zapisz0edytuj1 == false) {
            String data = selected.getDataoperacji();
            if (data.length() == 10) {
                String rok = data.split("-")[0];
                if (rok.equals(wpisView.getRokUprzedniSt())) {
                    Msg.msg("e", "Probujesz zaksiegować dokument do poprzedniego roku!");
                    wlaczZapiszButon = false;
                    RequestContext.getCurrentInstance().update("formwpisdokument:panelwpisbutton");
                } else {
                    generujokresy(rok, data);
                }
            }
        }
    }

    private void generujokresy(String rok, String data) {
        if (wlaczZapiszButon == false) {
            wlaczZapiszButon = true;
            RequestContext.getCurrentInstance().update("formwpisdokument:panelwpisbutton");
        }
        if (!rok.equals(selected.getRok())) {
            selected.setRok(rok);
            selected.setVatR(rok);
            RequestContext.getCurrentInstance().update("formwpisdokument:rok");
            RequestContext.getCurrentInstance().update("formwpisdokument:rokVAT");
        }
        String mc = data.split("-")[1];
        if (!mc.equals(selected.getMiesiac())) {
            selected.setMiesiac(mc);
            selected.setVatM(mc);
            RequestContext.getCurrentInstance().update("formwpisdokument:miesiac");
            RequestContext.getCurrentInstance().update("formwpisdokument:miesiacVAT");
        }
    }

    public void skorygujokreswpisudokumentu(ValueChangeEvent event) {
        if (selected.getRodzajedok()!=null && selected.getRodzajedok().getKategoriadokumentu() == 1) {
            //generuje okres wpisu tylko jezeli jest w trybie wpisu, a wiec zapisz0edytuj1 jest false
            if (zapisz0edytuj1 == false) {
                String data = selected.getDatawplywu();
                if (data.length() == 10) {
                    String rok = data.split("-")[0];
                    selected.setRok(rok);
                    String mc = data.split("-")[1];
                    selected.setVatM(mc);
                }
            }
        }
    }

    public void przygotujDokumentWpisywanie() {
        String skrotnazwydokumentu = selected.getRodzajedok().getSkrotNazwyDok();
        selected.setSeriadokfk(skrotnazwydokumentu);
        //pokazuje daty w wierszach
        if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
            pokazPanelWalutowy = true;
        } else {
            pokazPanelWalutowy = false;
        }
        rodzajBiezacegoDokumentu = selected.getRodzajedok().getKategoriadokumentu();
//        try {
//            if (rodzajBiezacegoDokumentu != 1 && rodzajBiezacegoDokumentu != 2) {
//                Klienci k = klienciDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
//                selected.setKontr(k);
//                if (k == null) {
//                    selected.setKontr(new Klienci("222222222222222222222", "BRAK FIRMY JAKO KONTRAHENTA!!!"));
//                }
//            }
//        } catch (Exception e) {
//            E.e(e);
//
//        }
    }

    public void przygotujDokumentEdycja() {
        try {
            selected.setwTrakcieEdycji(true);
            obsluzcechydokumentu();
            RequestContext.getCurrentInstance().update("zestawieniedokumentow:dataList");
            RequestContext.getCurrentInstance().update("wpisywaniefooter");
            Msg.msg("i", "Wybrano dokument do edycji " + selected.toString());
            setZapisz0edytuj1(true);
            if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
                pokazPanelWalutowy = true;
            } else {
                pokazPanelWalutowy = false;
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie wybrano dokumentu do edycji ");
        }
        rodzajBiezacegoDokumentu = selected.getRodzajedok().getKategoriadokumentu();
    }

    public void wybranoWierszMsg() {
        Msg.msg("Wybrano wiesz do edycji lp: " + wybranyWiersz.getIdporzadkowy());
    }

    public void usunWskazanyWierszWymus() {
        if (wybranyWiersz != null) {
            for (Iterator it = selected.getEwidencjaVAT().iterator(); it.hasNext();) {
                EVatwpisFK p = (EVatwpisFK) it.next();
                if (p.getWiersz() == wybranyWiersz) {
                    it.remove();
                }
            }
            selected.getListawierszy().remove(wybranyWiersz);
        }
    }

    public void naprawwiersznkup() {
        if (wybranyWiersz == null) {
            Msg.msg("e", "Nie wybrano wiersza do naprawy.");
        } else {
            StronaWiersza stronaWn = new StronaWiersza(wybranyWiersz, "Wn");
            wybranyWiersz.setStronaWn(stronaWn);
            Msg.msg("i", "Naprawilem wiersz.");
        }
    }

    public void usunWskazanyWiersz() {
        int flag = 0;
        if (wybranyWiersz == null) {
            Msg.msg("e", "Nie wybrano wiersza do usunięcia.");
            flag = 1;
        }
        try {
            if (flag != 1) {
                Wiersz wierszNastepny = selected.nastepnyWiersz(wybranyWiersz);
                if (wybranyWiersz.getLpmacierzystego() == 0 && wierszNastepny.getLpmacierzystego() != 0) {
                    Msg.msg("e", "Jest to wiersz zawierający kwotę rozliczona w dalszych wierszach. Nie można go usunąć");
                    flag = 1;
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
        try {
            if (flag != 1) {
                if (wybranyWiersz.getTypWiersza() == 5) {
                    Msg.msg("e", "Usuń najpierw wiersz z 4.");
                    flag = 1;
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
        if (flag == 0) {
            //9 nie ma wiersza
            String wierszeSasiednie = sprawdzWierszeSasiednie(wybranyWiersz);
            switch (wierszeSasiednie) {
                //usuwamy pierszy wiersz w dokumnecie, nie ma innych
                case "99":
                    selected.getListawierszy().remove(wybranyWiersz);
                    selected.getListawierszy().add(ObslugaWiersza.ustawPierwszyWiersz(selected));
                    break;
                case "00":
                    if (wybranyWiersz.getTypWiersza() == 0) {
                        selected.getListawierszy().remove(wybranyWiersz);
                        ObslugaWiersza.przenumerujSelected(selected);
                        break;
                    } else {
                        selected.getListawierszy().remove(wybranyWiersz);
                        ObslugaWiersza.przenumerujSelected(selected);
//                        ObslugaWiersza.sprawdzKwotePozostala(selected, wybranyWiersz, wierszeSasiednie);
                        break;
                    }
                //usuwamy ostatni wiersz w liscie roznego rodzaju, nie trzeba przenumerowac
                case "09":
                case "19":
                case "29":
                    selected.getListawierszy().remove(wybranyWiersz);
                    break;
                case "05":
                case "15":
                case "25":
                case "55":
                case "65":
                case "75":
                case "95":
                    Set<Wiersz> dolaczonePiatki = wybranyWiersz.getPiatki();
                    for (Wiersz p : dolaczonePiatki) {
                        selected.getListawierszy().remove(p);
                    }
                    //usunrozrachunki(wybranyWiersz);
                    selected.getListawierszy().remove(wybranyWiersz);
                    ObslugaWiersza.przenumerujSelected(selected);
                    break;
                default:
                    //usunrozrachunki(wybranyWiersz);
                    selected.getListawierszy().remove(wybranyWiersz);
                    ObslugaWiersza.przenumerujSelected(selected);
                    break;
            }
            for (Iterator<EVatwpisFK> it = selected.getEwidencjaVAT().iterator(); it.hasNext();) {
                EVatwpisFK p = it.next();
                if (p.getWiersz() == wybranyWiersz) {
                    it.remove();
                }
            }
            int liczbawierszyWDokumencie = selected.getListawierszy().size();
            if (liczbawierszyWDokumencie == 0) {
                selected.getListawierszy().add(ObslugaWiersza.ustawPierwszyWiersz(selected));
                liczbawierszyWDokumencie = 1;
            }
            dokDAOfk.edit(selected);
            Msg.msg("Usunieto wiersz");
        }
    }
    
    public void przewalutujWskazanyWiersz() {
        if (wybranyWiersz == null) {
            Msg.msg("e", "Nie wybrano wiersza do usunięcia.");
            return;
        }
        try {
            DokFKWalutyBean.przewalutujwiersz(wybranyWiersz);
            Msg.msg("Przewalutowano wiersz");
        } catch (Exception e) {
            Msg.dPe();
            E.e(e);
        }
    }

    private String sprawdzWierszeSasiednie(Wiersz wiersz) {
        Wiersz poprzedni = null;
        Wiersz nastepny = null;
        int poprzednizero = 9;
        int nastepnyzero = 9;
        try {
            poprzedni = selected.poprzedniWiersz(wiersz);
            poprzednizero = poprzedni.getTypWiersza();
        } catch (Exception e1) {
        }
        try {
            nastepny = selected.nastepnyWiersz(wiersz);
            nastepnyzero = nastepny.getTypWiersza();
        } catch (Exception e1) {
        }
        String zwrot = String.format("%s%s", poprzednizero, nastepnyzero);
        return zwrot;
    }

    public void przygotujDokumentEdycjaEFK(List<EVatwpisFK> lista, List<EVatwpisSuper> wybranewierszeewidencji, org.primefaces.component.tabview.TabView iTabPanel) {
         EVatwpisFK w = null;
        if (wybranewierszeewidencji!= null && wybranewierszeewidencji.size()==1) {
            w = (EVatwpisFK) wybranewierszeewidencji.get(0);
        } else {
            w = (EVatwpisFK) wybranewierszeewidencji.get(wybranewierszeewidencji.size()-1);
        }
        if (w!=null) {
            pobranecechypodatnik = cechazapisuDAOfk.findPodatnik(wpisView.getPodatnikObiekt());
            pobranecechypodatnikzapas.addAll(pobranecechypodatnik);
            Dokfk dokdk = dokDAOfk.findDokfkObj(w.getDokfk());
            int rowek = 0;
            for (EVatwpisFK s : lista) {
                if (s.equals(w)) {
                    break;
                }
                rowek++;
            }
            linijkaewidencjiupdate = "form:akordeon:akordeon2:"+iTabPanel.getActiveIndex()+":tabela:"+rowek+":polespr";
            przygotujDokumentEdycja(dokdk, 0);
        }
        
    }
    
    
    
    public void przygotujDokumentEdycja(Dokfk wybranyDokfk, Integer row) {
        try {
                selected = dokDAOfk.findDokfkID(wybranyDokfk);
                wybranyDokfk.setwTrakcieEdycji(true);
                idwierszedycjaodswiezenie = row;
                
                //selected.setwTrakcieEdycji(true);
                //dokDAOfk.edit(selected);
                wybranaTabelanbp = selected.getTabelanbp();
                tabelenbp = Collections.synchronizedList(new ArrayList<>());
                tabelenbp.add(wybranaTabelanbp);
                obsluzcechydokumentu();
                zapisz0edytuj1 = true;
                rodzajBiezacegoDokumentu = selected.getRodzajedok().getKategoriadokumentu();
                if (rodzajBiezacegoDokumentu == 0) {
                    pokazPanelWalutowy = true;
                } else {
                    pokazPanelWalutowy = false;
                }
                selected.setLiczbarozliczonych(DokFKTransakcjeBean.sprawdzrozliczoneWiersze(selected.getListawierszy()));
                if (selected.getLiczbarozliczonych() > 0) {
                    selected.setZablokujzmianewaluty(true);
                } else {
                    selected.setZablokujzmianewaluty(false);
                }
                RequestContext.getCurrentInstance().update("formwpisdokument");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie wybrano dokumentu do edycji ");
        }
    }
    
    public void przygotujDokumentEdycjaAnalityka(StronaWiersza strona, Integer duzyrow, Integer row) {
        Dokfk wybranyDokfk = strona.getDokfk();
        try {
                wybranyDokfk.setwTrakcieEdycji(true);
                idwierszedycjaodswiezenie = row;
                duzyidwierszedycjaodswiezenie = duzyrow;
                selected = wybranyDokfk;
                //selected.setwTrakcieEdycji(true);
                //dokDAOfk.edit(selected);
                wybranaTabelanbp = selected.getTabelanbp();
                tabelenbp = Collections.synchronizedList(new ArrayList<>());
                tabelenbp.add(wybranaTabelanbp);
                obsluzcechydokumentu();
                Msg.msg("i", "Wybrano dokument do edycji " + wybranyDokfk.toString());
                zapisz0edytuj1 = true;
                rodzajBiezacegoDokumentu = selected.getRodzajedok().getKategoriadokumentu();
                if (rodzajBiezacegoDokumentu == 0) {
                    pokazPanelWalutowy = true;
                } else {
                    pokazPanelWalutowy = false;
                }
                selected.setLiczbarozliczonych(DokFKTransakcjeBean.sprawdzrozliczoneWiersze(selected.getListawierszy()));
                if (selected.getLiczbarozliczonych() > 0) {
                    selected.setZablokujzmianewaluty(true);
                } else {
                    selected.setZablokujzmianewaluty(false);
                }
                edycjaanalityczne();
                RequestContext.getCurrentInstance().update("formwpisdokument");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie wybrano dokumentu do edycji ");
        }
    }

    public void rezygnujzedycji() {
//        Dokfk odnalezionywbazie = dokDAOfk.findDokfkObj(selected);
//        odnalezionywbazie.setwTrakcieEdycji(false);
//        dokDAOfk.edit(selected);

    }

    public void przygotujDokumentEdycjaImport(Dokfk wybranyDokfk, Integer row) {
        try {
            Dokfk odnalezionywbazie = dokDAOfk.findDokfkObj(wybranyDokfk);
            wierszedytowany = "zestawieniedokumentowimport:dataListImport:" + String.valueOf(row) + ":";
            wybranyDokfk.setwTrakcieEdycji(true);
            selected = wybranyDokfk;
            selected.setwTrakcieEdycji(true);
            wybranaTabelanbp = selected.getTabelanbp();
            tabelenbp = Collections.synchronizedList(new ArrayList<>());
            tabelenbp.add(wybranaTabelanbp);
            obsluzcechydokumentu();
            RequestContext.getCurrentInstance().update(wierszedytowany);
            Msg.msg("i", "Wybrano dokument do edycji " + wybranyDokfk.toString());
            zapisz0edytuj1 = true;
            if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
                pokazPanelWalutowy = true;
            } else {
                pokazPanelWalutowy = false;
            }
            rodzajBiezacegoDokumentu = selected.getRodzajedok().getKategoriadokumentu();
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie wybrano dokumentu do edycji ");
        }
    }

//samo podswietlanie wiersza jest w javscript on compleyte w menucontext pobiera rzad wiersza z wierszDoPodswietlenia
    public void znajdzDokumentOznaczWierszDoPodswietlenia() {
        Dokfk odnalezionywbazie = dokDAOfk.findDokfkObj(wiersz.getDokfk());
        selected = odnalezionywbazie;
        int numer = wiersz.getIdporzadkowy() - 1;
        wierszDoPodswietlenia = numer;
        setZapisz0edytuj1(true);
    }
    
    public void znajdzDokumentOznaczWierszDoPodswietlenia(StronaWiersza stronawiersza) {
        if (stronawiersza != null) {
            Wiersz w = stronawiersza.getWiersz();
            Dokfk odnalezionywbazie = dokDAOfk.findDokfkObj(w.getDokfk());
            selected = odnalezionywbazie;
            int numer = w.getIdporzadkowy() - 1;
            wierszDoPodswietlenia = numer;
            setZapisz0edytuj1(true);
            totylkoedycjazapis = true;
            Msg.msg("Wybrano dokument do edycji: "+selected.getDokfkSN());
//            RequestContext.getCurrentInstance().update("formwpisdokument");
//            RequestContext.getCurrentInstance().update("zestawieniezapisownakontachpola1");
//            String nazwa = "formwpisdokument:dataList:"+numer+":opisdokwpis";
//            String wierszf = "$(document.getElementById('"+nazwa+"')).select()";
//            RequestContext.getCurrentInstance().execute(wierszf);
        }
    }

    public void znajdzDokumentOznaczDoPodswietlenia(List<EVatwpisSuper> stronawiersza) {
        if (stronawiersza != null) {
            Dokfk odnalezionywbazie = stronawiersza.get(0).getDokfk();
            selected = odnalezionywbazie;
            wierszDoPodswietlenia = 0;
            setZapisz0edytuj1(true);
            totylkoedycjazapis = true;
            Msg.msg("Wybrano dokument do edycji: "+selected.getDokfkSN());
//            RequestContext.getCurrentInstance().update("formwpisdokument");
//            RequestContext.getCurrentInstance().update("zestawieniezapisownakontachpola1");
//            String nazwa = "formwpisdokument:dataList:"+numer+":opisdokwpis";
//            String wierszf = "$(document.getElementById('"+nazwa+"')).select()";
//            RequestContext.getCurrentInstance().execute(wierszf);
        }
    }

//
//    //on robi nie tylko to ze przywraca button, on jeszcze resetuje selected
//    //dodalem to tutaj a nie przy funkcji edytuj bo wtedy nie wyswietlalo wiadomosci o edycji
    public void przywrocwpisbutton() {
        setZapisz0edytuj1(false);
        selected.setwTrakcieEdycji(false);
        RequestContext.getCurrentInstance().execute("PF('wpisywanie').hide();");
    }

//    //</editor-fold>
   
    
    

    public void sprawdzsalda(String wybranakategoriadok) {
        if (wybranakategoriadok.startsWith("RK") || wybranakategoriadok.startsWith("WB")) {
            List<Dokfk> wykaz = dokDAOfk.findDokfkPodatnikRokKategoriaOrderByNo(wpisView, wybranakategoriadok);
            List<Konto> kontagrupa1 = kontoDAOfk.findKontaGrupa1(wpisView);
            List<WierszBO> wierszeBO = wierszBODAO.findPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            for (Dokfk dokfk : wykaz) {
                int nrserii = dokfk.getNrkolejnywserii();
                if (dokfk.getRodzajedok().getKontorozrachunkowe()!=null) {
                    Konto kontozdanegoroku = pobierzkontosalda(dokfk.getRodzajedok().getKontorozrachunkowe().getPelnynumer(), kontagrupa1);
                    if (nrserii == 1) {
                        if (kontozdanegoroku != null) {
                            double saldobo = DokFKBean.szukajpobierzwartosczBO(kontozdanegoroku, wierszeBO);
                            dokfk.setSaldopoczatkowe(saldobo);
                        } else {
                            dokfk.setSaldopoczatkowe(-1.0);
                        }
                    } else {
                        Dokfk poprzedni = wykaz.get(wykaz.indexOf(dokfk) - 1);
                        double saldopoprzednie = poprzedni.getSaldokoncowe();
                        dokfk.setSaldopoczatkowe(saldopoprzednie);
                    }
                    for (Wiersz wiersz : dokfk.getListawierszy()) {
                        DialogWpisywanie.naprawsaldo(dokfk, wiersz, kontozdanegoroku);
                    }
                }
            }
            dokDAOfk.editList(wykaz);
            wykazZaksiegowanychDokumentow = wykaz;
            Msg.msg("Zakończono weryfikację sald dokumnetów wb/rk");
        } else {
            Msg.msg("Można sprawdzać salda jedynie dokumentów typu RK i WB");
        }
    }
    
    private Konto pobierzkontosalda(String pelnynumer, List<Konto> konta) {
        Konto zwrot = null;
        for (Konto p : konta) {
            if (p.getPelnynumer().equals(pelnynumer)) {
                zwrot = p;
            }
        }
        return zwrot;
    }

    /**
     * funkcja wywolywana po wybrnaiu w menu odpowiedniej zakladki
     */
    public void odswiezzaksiegowaneInit() {
        if (wpisView.getMiesiacWpisu().equals("CR")) {
            wpisView.setMiesiacWpisu(Data.aktualnyMc());
        }
        //wpisView.setMiesiacWpisu(komunikatywpisdok); nie wiem po co to bylo
        wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
        rodzajedokumentowPodatnika = znajdzrodzajedokaktualne(wykazZaksiegowanychDokumentow);
        //cechydokzlisty = znajdzcechy(wykazZaksiegowanychDokumentow);
        pobranecechypodatnik = cechazapisuDAOfk.findPodatnik(wpisView.getPodatnikObiekt());
        wybranacechadok = null;
        Collections.sort(wykazZaksiegowanychDokumentow, new Dokfkcomparator());
        filteredValue = null;
        miesiacWpisuPokaz = wpisView.getMiesiacWpisu();
    }

    public void odswiezzaksiegowaneRok() {
        wpisView.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        odswiezzaksiegowane();
    }
    
    public void odswiezzaksiegowane() {
        try {
            if (pokazwszystkiedokumenty) {
                wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnik(wpisView);
            } else {
                if (wybranakategoriadok == null) {
                    wybranakategoriadok = "wszystkie";
                }
                if (wybranakategoriadok.equals("wszystkie")) {
                    if (miesiacWpisuPokaz.equals("CR")) {
                        if (pokazsrodkitrwale) {
                            wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokSrodkiTrwale(wpisView);
                            pokazrmk = false;
                        } else if (pokazrmk) {
                            wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokRMK(wpisView);   
                            pokazsrodkitrwale = false;
                        } else {
                            wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRok(wpisView);
                            pokazrmk = false;
                            pokazsrodkitrwale = false;
                        }
                    } else {
                        if (pokazsrodkitrwale) {
                            wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokSrodkiTrwale(wpisView);
                            pokazrmk = false;
                        } else if (pokazrmk) {
                            wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokRMK(wpisView);   
                            pokazsrodkitrwale = false;
                        } else {
                            wpisView.setMiesiacWpisu(miesiacWpisuPokaz);
                            wpisView.wpisAktualizuj();
                            wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
                            pokazrmk = false;
                            pokazsrodkitrwale = false;
                        }
                    }
                } else if (miesiacWpisuPokaz.equals("CR")) {
                    wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokKategoria(wpisView, wybranakategoriadok);
                } else {
                    wpisView.setMiesiacWpisu(miesiacWpisuPokaz);
                    wpisView.wpisAktualizuj();
                    wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokMcKategoria(wpisView, wybranakategoriadok);
                }
                if (wykazZaksiegowanychDokumentow != null && wykazZaksiegowanychDokumentow.size() > 0) {
                    for (Iterator<Dokfk> it = wykazZaksiegowanychDokumentow.iterator(); it.hasNext();) {
                        Dokfk r = (Dokfk) it.next();
                        if (r.isImportowany() == true) {
                            it.remove();
                        } else if (wybranacechadok != null) {
                            boolean dousuniecia = true;
                            if (r.getCechadokumentuLista() != null && r.getCechadokumentuLista().size() > 0) {
                                for (Cechazapisu ch : r.getCechadokumentuLista()) {
                                    if (ch.getNazwacechy().equals(wybranacechadok)) {
                                        dousuniecia = false;
                                    }
                                }
                            }
                            boolean dousuniecia2 = true;
                            if (dousuniecia) {
                                for (StronaWiersza sw : r.getStronyWierszy()) {
                                    if (sw.getCechazapisuLista() != null && sw.getCechazapisuLista().size() > 0) {
                                        for (Cechazapisu ch : sw.getCechazapisuLista()) {
                                            if (ch.getNazwacechy().equals(wybranacechadok)) {
                                                dousuniecia2 = false;
                                            }
                                        }
                                    }
                                }
                            }
                            if (dousuniecia && dousuniecia2) {
                                it.remove();
                            }
                        }
                    }
                }
            }
            rodzajedokumentowPodatnika = znajdzrodzajedokaktualne(wykazZaksiegowanychDokumentow);
            //cechydokzlisty = znajdzcechy(wykazZaksiegowanychDokumentow);
            Collections.sort(wykazZaksiegowanychDokumentow, new Dokfkcomparator());
            filteredValue = null;
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","wystąpił błąd przy pobiernaiu dokumentów");
        }
    }

    public void sumawartosciwybranych() {
        podsumowaniewybranych = 0.0;
        for (Dokfk p : selectedlist) {
            try {
                podsumowaniewybranych += p.getWartoscdokumentu();
            } catch (Exception e) {
            }
        }
    }

    public void odswiezzaksiegowaneimport() {
        wykazZaksiegowanychDokumentowimport = Collections.synchronizedList(new ArrayList<>());
        if (wybranakategoriadokimport == null) {
            wybranakategoriadokimport = "wszystkie";
        }
        if (wybranakategoriadokimport.equals("wszystkie")) {
            if (wpisView.getMiesiacWpisu().equals("CR")) {
                wykazZaksiegowanychDokumentowimport = dokDAOfk.findDokfkPodatnikRok(wpisView);
            } else {
                wpisView.wpisAktualizuj();
                wykazZaksiegowanychDokumentowimport = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
            }
        } else if (wpisView.getMiesiacWpisu().equals("CR")) {
            wykazZaksiegowanychDokumentowimport = dokDAOfk.findDokfkPodatnikRokKategoria(wpisView, wybranakategoriadokimport);
        } else {
            wpisView.wpisAktualizuj();
            wykazZaksiegowanychDokumentowimport = dokDAOfk.findDokfkPodatnikRokMcKategoria(wpisView, wybranakategoriadokimport);
        }
        for (Iterator<Dokfk> p = wykazZaksiegowanychDokumentowimport.iterator(); p.hasNext();) {
            Dokfk r = (Dokfk) p.next();
            if (r.isImportowany() == false) {
                p.remove();
            }
        }
        RequestContext.getCurrentInstance().update("zestawieniedokumentowimport");
    }

    //************************
    //zaznacza po otwaricu rozrachunkow biezaca strone wiersza jako nowa transakcje oraz usuwa po odhaczeniu ze to nowa transakcja
    public void zaznaczOdznaczJakoNowaTransakcja() {
        if (aktualnyWierszDlaRozrachunkow.isNowatransakcja() == true) {
            aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(1);
            aktualnyWierszDlaRozrachunkow.setNowetransakcje(new ArrayList<Transakcja>());
            Msg.msg("i", "Dodano bieżący zapis jako nową transakcję");
        } else if (aktualnyWierszDlaRozrachunkow.getRozliczono() > 0) {
            Msg.msg("e", "Trasakcja rozliczona - nie można usunąć oznaczenia");
        } else {
            aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(0);
            aktualnyWierszDlaRozrachunkow.setPlatnosci(new ArrayList<Transakcja>());
            Msg.msg("i", "Usunięto zapis z listy nowych transakcji");
        }
        selected.setLiczbarozliczonych(DokFKTransakcjeBean.sprawdzrozliczoneWiersze(selected.getListawierszy()));
        if (selected.getLiczbarozliczonych() > 0) {
            selected.setZablokujzmianewaluty(true);
        } else {
            selected.setZablokujzmianewaluty(false);
        }
        if (aktualnyWierszDlaRozrachunkow.isNowatransakcja()== false) {
            String f = "PF('rozrachunki').hide();";
            RequestContext.getCurrentInstance().execute(f);
        }
        RequestContext.getCurrentInstance().update("formcheckbox");
        RequestContext.getCurrentInstance().update("formwpisdokument:panelwalutowywybor");
        RequestContext.getCurrentInstance().update("wpisywaniefooter");
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList:" + lpWierszaWpisywanie + ":kontown");
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList:" + lpWierszaWpisywanie + ":kontoma");
    }

    public void pobranieWiersza(Wiersz wybranywiersz) {
        lpWierszaWpisywanie = wybranywiersz.getIdporzadkowy();
        //Msg.msg("Wiersz "+lpWierszaWpisywanie);
    }

    public void pobranieStronaWiersza(StronaWiersza wybranastronawiersza) {
        selectedStronaWiersza = wybranastronawiersza;
        lpWierszaWpisywanie = wybranastronawiersza.getWiersz().getIdporzadkowy() - 1;
        String nrkonta = null;
        if (wybranastronawiersza.getWnma().equals("Wn")) {
            nrkonta = (String) Params.params("formwpisdokument:dataList:" + lpWierszaWpisywanie + ":kontown_input");
        } else if (wybranastronawiersza.getWnma().equals("Ma")) {
            nrkonta = (String) Params.params("formwpisdokument:dataList:" + lpWierszaWpisywanie + ":kontoma_input");
        }
        if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
                rozliczsaldoWBRK(lpWierszaWpisywanie);
        }
        //11 dodaje nowego klienta
        if (nrkonta.equals("")) {
            //DokFKBean.obsluzWstawKontoWBRK(wybranastronawiersza, selected.getRodzajedok().getKontorozrachunkowe(), lpWierszaWpisywanie);
            //funkcja dziala ale chyba jest zbedna
            //jak nie ma konta to nie ma co uruchamiac
            return;
        } else if (nrkonta.contains("dodaj konto")) {
            jest1niema0_konto = 0;
            return;
        } else if (nrkonta.contains("dodaj kontrahenta")) {
            jest1niema0_konto = 11;
            return;
        } else if (nrkonta.contains("dodaj el.słownika")) {
            jest1niema0_konto = 22;
            return;
        } else {
            jest1niema0_konto = 1;
        }
        try {
            if (aktualnyWierszDlaRozrachunkow != wybranastronawiersza || wybranastronawiersza.getTypStronaWiersza() == 0) {
                String wnma = wybranastronawiersza.getWnma();
                wnmadoprzeniesienia = wybranastronawiersza.getWnma();
                if (wybranastronawiersza.getKonto() != null && wybranastronawiersza.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
                    biezacetransakcje = new ArrayList<>();
                    aktualnyWierszDlaRozrachunkow = wybranastronawiersza;
                    potraktujjakoNowaTransakcje = selected.getRodzajedok().getKategoriadokumentu() == 0 ? false : true;
                    rodzaj = wybranastronawiersza.getTypStronaWiersza();
                    if (wybranastronawiersza.getTypStronaWiersza() == 0) {
                        boolean rachunek = false;
                        if (aktualnyWierszDlaRozrachunkow.getKonto().getPelnynumer().startsWith("201") && wnmadoprzeniesienia.equals("Wn")) {
                            rachunek = true;
                        } else if (aktualnyWierszDlaRozrachunkow.getKonto().getPelnynumer().startsWith("202") && wnmadoprzeniesienia.equals("Ma")) {
                            rachunek = true;
                        } else if (aktualnyWierszDlaRozrachunkow.getKonto().getPelnynumer().startsWith("203") && wnmadoprzeniesienia.equals("Wn")) {
                            rachunek = true;
                        } else if (aktualnyWierszDlaRozrachunkow.getKonto().getPelnynumer().startsWith("204") && wnmadoprzeniesienia.equals("Ma")) {
                            rachunek = true;
                        }
                        rachunekCzyPlatnosc = rachunek == true ? "rachunek" : "płatność";
                        RequestContext.getCurrentInstance().update("formtransakcjawybor:transakcjawybormenu");
                    }
                    //to juz jest jak wciakam art-r no i zostawienie tego powoduje ze przy edycji dokumentu kreuja sie puste transakcje, ktore potem sa zachowywane  bazie
//                    if (wybranastronawiersza.getTypStronaWiersza() == 1) {
//                        biezacetransakcje = tworzenieTransakcjiRachunek(wnma, wybranastronawiersza);
//                        RequestContext.getCurrentInstance().update("rozrachunki");
//                        RequestContext.getCurrentInstance().update("dialogdrugi");
//                        RequestContext.getCurrentInstance().update("formcheckbox:znaczniktransakcji");
//                        //platnosc
//                    } else if (wybranastronawiersza.getTypStronaWiersza() == 2) {
//                        biezacetransakcje = tworzenieTransakcjiPlatnosc(wnma, wybranastronawiersza);
//                        RequestContext.getCurrentInstance().update("rozrachunki");
//                        RequestContext.getCurrentInstance().update("dialogdrugi");
//                        RequestContext.getCurrentInstance().update("formcheckbox:znaczniktransakcji");
//                    } else {
//                    }

                } 
                if (wybranastronawiersza.getKonto() != null && wybranastronawiersza.getKonto().equals(selected.getRodzajedok().getKontorozrachunkowe())) {
                    rozliczsaldoWBRK(lpWierszaWpisywanie);
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
        if (wybranastronawiersza.getKonto() != null && !wybranastronawiersza.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
            potraktujjakoNowaTransakcje = false;
            biezacetransakcje = Collections.synchronizedList(new ArrayList<>());
            wybranastronawiersza.setTypStronaWiersza(0);
            wybranastronawiersza.setNowatransakcja(false);
            wybranastronawiersza.setPlatnosci(new ArrayList<>());
            wybranastronawiersza.setNowetransakcje(new ArrayList<>());
         }
    }

    
    //to pojawia sie na dzien dobry jak ktos wcisnie alt-r
    public void wybranoStronaWierszaCecha() {
        if (pobranecechypodatnikzapas != null) {
            int idwiersza = Integer.parseInt((String) Params.params("wpisywaniefooter:lpwierszaWpisywanie"));
            if (idwiersza > -1) {
                Wiersz wiersz = selected.getListawierszy().get(idwiersza);
                if (wnmadoprzeniesienia.equals("Wn")) {
                    stronaWierszaCechy = wiersz.getStronaWn();
                } else {
                    stronaWierszaCechy = wiersz.getStronaMa();
                }
                pobranecechypodatnik = new ArrayList<>(pobranecechypodatnikzapas);
                List<Cechazapisu> cechyuzyte = stronaWierszaCechy.getCechazapisuLista();
                for (Cechazapisu c : cechyuzyte) {
                    pobranecechypodatnik.remove(c);
                }
                //RequestContext.getCurrentInstance().update("formCHW");
            }
        }
    }

    public void dodajcechedostronawiersza() {
       if (cechazapisudododania != null) {
            pobranecechypodatnik.remove(cechazapisudododania);
            stronaWierszaCechy.getCechazapisuLista().add(cechazapisudododania);
            cechazapisudododania.getStronaWierszaLista().add(stronaWierszaCechy);
       }
    }
    
    public void dodajcechedostronawiersza(Cechazapisu c) {
        pobranecechypodatnik.remove(c);
        stronaWierszaCechy.getCechazapisuLista().add(c);
        c.getStronaWierszaLista().add(stronaWierszaCechy);
    }

    public void usuncechedostronawiersza(Cechazapisu c) {
        pobranecechypodatnik.add(c);
        stronaWierszaCechy.getCechazapisuLista().remove(c);
        c.getStronaWierszaLista().remove(stronaWierszaCechy);
    }

    public void oznaczjakonowatransakcja() {
        oznaczJakoRachunek();
        RequestContext.getCurrentInstance().update("formwpisdokument:panelwalutowywybor");
        RequestContext.getCurrentInstance().update("parametry");
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList:" + lpWierszaWpisywanie + ":kontown");
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList:" + lpWierszaWpisywanie + ":kontoma");
    }
    
    //to sie pojawia jak wciscnie alt-r i wiesz juz jest okreslony
    public void wybranoRachunekPlatnoscCD() {
        //0 oznacza strone niewybrana
        if (aktualnyWierszDlaRozrachunkow == null) {
            Msg.msg("e", "AktualnyWierszDlaRozrachunkow jest pusty wybranoRachunekPlatnoscCD(String stronawiersza)");
            return;
        }
        if (aktualnyWierszDlaRozrachunkow.getTypStronaWiersza() == 0) {
            oznaczJakoPlatnosc();
            RequestContext.getCurrentInstance().update("parametry");
            //RequestContext.getCurrentInstance().update("formcheckbox");
        }
        //nowa transakcja
        if (aktualnyWierszDlaRozrachunkow.getTypStronaWiersza() == 1) {
            biezacetransakcje = tworzenieTransakcjiRachunek(wnmadoprzeniesienia, aktualnyWierszDlaRozrachunkow);
            //RequestContext.getCurrentInstance().update("formcheckbox");
            //RequestContext.getCurrentInstance().update("rozrachunki");
            RequestContext.getCurrentInstance().update("dialogdrugi");
            //RequestContext.getCurrentInstance().update("formcheckbox:znaczniktransakcji");
            //platnosc
        } else if (aktualnyWierszDlaRozrachunkow.getTypStronaWiersza() == 2) {
            biezacetransakcje = tworzenieTransakcjiPlatnosc(wnmadoprzeniesienia, aktualnyWierszDlaRozrachunkow);
            //RequestContext.getCurrentInstance().update("rozrachunki");
            //RequestContext.getCurrentInstance().update("dialogdrugi");
            //RequestContext.getCurrentInstance().update("formcheckbox:znaczniktransakcji");
        } else {
        }
    }

    public void oznaczJakoPlatnosc() {
        aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(2);
        selected.setZablokujzmianewaluty(true);
        rodzaj = 2;
        RequestContext.getCurrentInstance().update("rozrachunki");
        RequestContext.getCurrentInstance().update("formcheckbox");
    }

    public void oznaczJakoRachunek() {
        aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(1);
        aktualnyWierszDlaRozrachunkow.setNowatransakcja(true);
        rodzaj = 1;
        selected.setZablokujzmianewaluty(true);
    }

//    private StronaWiersza pobierzStronaWierszaDlaRozrachunkow(Wiersz wiersz, String stronawiersza) {
//        zablokujprzyciskzapisz = false;
//        try {
//            if (stronawiersza.equals("Wn")) {
//                wiersz.getStronaWn().setWiersz(wiersz);
//                return wiersz.getStronaWn();
//            } else {
//                wiersz.getStronaMa().setWiersz(wiersz);
//                return wiersz.getStronaMa();
//            }
//        } catch (Exception e) {
//            E.e(e);
//            System.out.println("błąd pobierzStronaWierszaDlaRozrachunkow DokfkView 2652");
//            return null;
//        }
//    }
    public List<Transakcja> tworzenieTransakcjiPlatnosc(String stronawiersza, StronaWiersza wybranastronawiersza) {
        List<Transakcja> transakcje = new ArrayList<>();
        List<StronaWiersza> stronyWierszazDokumentu = new ArrayList<>();
        List<StronaWiersza> stronyWierszazBazy = new ArrayList<>();
        try {
            if (StronaWierszaBean.czyKontoJestRozrachunkowe(wybranastronawiersza, stronawiersza)) {
                if (wybranastronawiersza.getKwota() < 0) {
                    if (stronawiersza.equals("Wn")) {
                        stronawiersza = "Ma";
                    } else {
                        stronawiersza = "Wn";
                    }
                }
                stronyWierszazDokumentu = (DokFKTransakcjeBean.pobierzStronaWierszazDokumentu(wybranastronawiersza.getKonto().getPelnynumer(), stronawiersza, wybranastronawiersza.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty(), selected.getListawierszy()));
                stronyWierszazBazy = DokFKTransakcjeBean.pobierzStronaWierszazBazy(wybranastronawiersza, stronawiersza, stronaWierszaDAO, transakcjaDAO);
                transakcje = (DokFKTransakcjeBean.stworznowetransakcjezeZapisanychStronWierszy(stronyWierszazDokumentu, stronyWierszazBazy, wybranastronawiersza, wpisView.getPodatnikWpisu()));
                DokFKTransakcjeBean.naniesKwotyZTransakcjiwPowietrzu(wybranastronawiersza, transakcje, selected.getListawierszy(), stronawiersza);
                Collections.sort(transakcje, new TransakcjacomparatorKwota());
                //trzeba zablokować mozliwosc zmiaktualnyWierszDlaRozrachunkowany nowej transakcji jak sa juz rozliczenia!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                String funkcja;
                //jezeli w pobranych transakcjach sa juz rozliczenia to trzeba zablokowac mozliwosc zaznaczania nowej transakcji
                double saWartosciWprowadzone = 0.0;
                for (Transakcja p : transakcje) {
                    saWartosciWprowadzone += p.getKwotatransakcji();
                }
                wybranastronawiersza.setRozliczeniebiezace(Z.z(saWartosciWprowadzone));
                if (saWartosciWprowadzone > 0) {
                    funkcja = "zablokujcheckbox('true','platnosc');";
                } else {
                    funkcja = "zablokujcheckbox('false','platnosc');";
                }
                RequestContext.getCurrentInstance().execute(funkcja);
                //usunalem aby bylo szybciej
                //zerujemy rzeczy w dialogu
                if (transakcje.size() == 0) {
                    rodzaj = -2;
                    wybranastronawiersza.setTypStronaWiersza(0);
                    RequestContext.getCurrentInstance().update("parametry");
                    RequestContext.getCurrentInstance().execute("PF('rozrachunki').hide();");
                }
            } else {
                Msg.msg("e", "Wybierz najpierw konto rozrachunkowe");
                //zerujemy rzeczy w dialogu
                RequestContext.getCurrentInstance().execute("powrotdopolaPoNaniesieniuRozrachunkow();");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wybierz pole zawierające numer konta");
            //zerujemy rzeczy w dialogu
            RequestContext.getCurrentInstance().execute("powrotdopolaPoNaniesieniuRozrachunkow();");
        }
        return transakcje;
    }

    public List<Transakcja> tworzenieTransakcjiRachunek(String stronawiersza, StronaWiersza wybranastronawiersza) {
        List<Transakcja> transakcje = Collections.synchronizedList(new ArrayList<>());
        try {
            if (StronaWierszaBean.czyKontoJestRozrachunkowe(wybranastronawiersza, stronawiersza)) {
                //tu trzeba wymyslec cos zeby pokazywac istniejace juz rozliczenia dla NOWA Transakcja
                transakcje.addAll(DokFKTransakcjeBean.pobierzbiezaceTransakcjeDlaNowejTransakcji(wybranastronawiersza, stronawiersza));
                Collections.sort(transakcje, new Transakcjacomparator());
                //trzeba zablokować mozliwosc zmiaktualnyWierszDlaRozrachunkowany nowej transakcji jak sa juz rozliczenia!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                String funkcja = "";
                boolean potraktujjakoNowaTransakcje = wybranastronawiersza.getTypStronaWiersza() == 1;
                if (potraktujjakoNowaTransakcje == true) {
                    //jezeli nowa transakcja jest juz gdzies rozliczona to trzeba zablokowac przycisk
                    double czyjuzrozliczono = wybranastronawiersza.getRozliczono();
                    if (czyjuzrozliczono > 0) {
                        funkcja = "zablokujcheckbox('true', 'rachunek');";
                    } else {
                        funkcja = "zablokujcheckbox('false', 'rachunek');";
                    }
                    zablokujprzyciskzapisz = true;
                }
                RequestContext.getCurrentInstance().execute(funkcja);
                RequestContext.getCurrentInstance().update("rozrachunki");
                RequestContext.getCurrentInstance().update("formcheckbox:znaczniktransakcji");
                //chyba nadmiarowe, jest juz w javascript onShow
//                String znajdz = "znadzpasujacepolerozrachunku(" + wybranastronawiersza.getPozostalo() + ")";
//                RequestContext.getCurrentInstance().execute(znajdz);

            } else {
                Msg.msg("e", "Wybierz najpierw konto rozrachunkowe");
                //zerujemy rzeczy w dialogu
                RequestContext.getCurrentInstance().execute("powrotdopolaPoNaniesieniuRozrachunkow();");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wybierz pole zawierające numer konta");
            //zerujemy rzeczy w dialogu
            RequestContext.getCurrentInstance().execute("powrotdopolaPoNaniesieniuRozrachunkow();");
        }
        return transakcje;
    }

    public void zapistransakcji() {
        if (aktualnyWierszDlaRozrachunkow.getTypStronaWiersza() != 1) {
            //oznacza rozliczajacy wiersz jako nowa transakcje??
            aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(2);
        }
//        biezacetransakcje
        Iterator it = aktualnyWierszDlaRozrachunkow.getNowetransakcje().iterator();
        while (it.hasNext()) {
            Transakcja tr = (Transakcja) it.next();
            if (Z.z(tr.getKwotatransakcji()) == 0.0) {
                tr.getNowaTransakcja().getPlatnosci().remove(tr);
                it.remove();
            } else if (aktualnyWierszDlaRozrachunkow.getWiersz().getDataWalutyWiersza() != null) {
                tr.setDatarozrachunku(ustawdatetransakcji());
            } else {
                tr.setDatarozrachunku(aktualnyWierszDlaRozrachunkow.getDataWiersza());
            }
            DokFKTransakcjeBean.wyliczroznicekursowa(tr, tr.getKwotatransakcji());
        }
        if (aktualnyWierszDlaRozrachunkow.getNowetransakcje().isEmpty()) {
            aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(0);
            if (aktualnyWierszDlaRozrachunkow.getWiersz().getTypWiersza()==0 && !aktualnyWierszDlaRozrachunkow.getWiersz().getStronaWn().getSaRozrachunki() && aktualnyWierszDlaRozrachunkow.getWiersz().getStronaMa().getKonto()==null) {
                aktualnyWierszDlaRozrachunkow.getWiersz().setOpisWiersza("wyzerowano rozrachunki");
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList:" + lpWierszaWpisywanie + ":opisdokwpis");
            } else  if (aktualnyWierszDlaRozrachunkow.getWiersz().getTypWiersza()==0 && !aktualnyWierszDlaRozrachunkow.getWiersz().getStronaWn().getSaRozrachunki() && !aktualnyWierszDlaRozrachunkow.getWiersz().getStronaMa().getSaRozrachunki()) {
                aktualnyWierszDlaRozrachunkow.getWiersz().setOpisWiersza("wyzerowano rozrachunki");
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList:" + lpWierszaWpisywanie + ":opisdokwpis");
            }
        } else {
            aktualnyWierszDlaRozrachunkow.setOpis(new HashSet<>());
            for (Transakcja p : biezacetransakcje) {
                przetworzwprowadzonakwoteOpis(p);
            }
            if (aktualnyWierszDlaRozrachunkow.getOpis()!=null && aktualnyWierszDlaRozrachunkow.getOpis().size() > 0) {
                List<String> op = new ArrayList<>(aktualnyWierszDlaRozrachunkow.getOpis());
                String kontrahent = aktualnyWierszDlaRozrachunkow.getKonto().getNazwapelna();
                String opislinia = "f: ";
                for (String p : op) {
                    opislinia = opislinia+p+";";
                }
                opislinia = opislinia+" "+kontrahent;
                opislinia = opislinia.length() >767 ? opislinia.substring(0, 766) : opislinia;
                if (aktualnyWierszDlaRozrachunkow.getWiersz().getTypWiersza()==0 && aktualnyWierszDlaRozrachunkow.getWiersz().getStronaWn().getSaRozrachunki() && aktualnyWierszDlaRozrachunkow.getWiersz().getStronaMa().getSaRozrachunki()) {
                    opislinia = "komp. "+aktualnyWierszDlaRozrachunkow.getWiersz().getOpisWiersza()+" "+opislinia;
                    opislinia = opislinia.length() >767 ? opislinia.substring(0, 766) : opislinia;
                    aktualnyWierszDlaRozrachunkow.getWiersz().setOpisWiersza(opislinia);
                } else {
                    aktualnyWierszDlaRozrachunkow.getWiersz().setOpisWiersza(opislinia);
                }
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList:" + lpWierszaWpisywanie + ":opisdokwpis");
            }
        }
        selected.setLiczbarozliczonych(DokFKTransakcjeBean.sprawdzrozliczoneWiersze(selected.getListawierszy()));
        if (selected.getLiczbarozliczonych() > 0) {
            selected.setZablokujzmianewaluty(true);
        } else {
            selected.setZablokujzmianewaluty(false);
        }
        RequestContext.getCurrentInstance().update("formwpisdokument:panelwalutowy");
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList:" + lpWierszaWpisywanie + ":kontown");
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList:" + lpWierszaWpisywanie + ":wn");
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList:" + lpWierszaWpisywanie + ":kontoma");
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList:" + lpWierszaWpisywanie + ":ma");
        
        //RequestContext.getCurrentInstance().execute("wybierzWierszPoZmianieWaluty();");
    }

    
    
    private String ustawdatetransakcji() {
        String datawiersza;
        if (aktualnyWierszDlaRozrachunkow.getWiersz().getDataWalutyWiersza().length() == 1) {
            datawiersza = "0" + aktualnyWierszDlaRozrachunkow.getWiersz().getDataWalutyWiersza();
        } else {
            datawiersza = aktualnyWierszDlaRozrachunkow.getWiersz().getDataWalutyWiersza();
        }
        return selected.getRok() + "-" + selected.getMiesiac() + "-" + datawiersza;
    }
//
//    //********************
//    //a to jest rodzial dotyczacy walut
//
    public void pobierzkursNBP(ValueChangeEvent el) {
        tabelenbp = Collections.synchronizedList(new ArrayList<>());
        symbolwalutydowiersza = ((Waluty) el.getNewValue()).getSymbolwaluty();
        String nazwawaluty = ((Waluty) el.getNewValue()).getSymbolwaluty();
        Waluty stara = ((Waluty) el.getOldValue());
        String staranazwa = null;
        if (stara!=null) {
            staranazwa = stara.getSymbolwaluty();
        }
        if (staranazwa!=null && !staranazwa.equals("PLN") && !nazwawaluty.equals("PLN") && selected.getListawierszy().get(0).getStronaWn().getKwota() != 0.0) {
            Msg.msg("w", "Prosze przewalutowywać do PLN");
        } else {
            if (!nazwawaluty.equals("PLN")) {
                String datadokumentu = selected.getDataoperacji();
                DateTime dzienposzukiwany = new DateTime(datadokumentu);
                //tu sie dodaje tabele do dokumentu :)
                tabelenbp.add(TabelaNBPBean.pobierzTabeleNBP(dzienposzukiwany, tabelanbpDAO, nazwawaluty, selected));
                tabelenbp.addAll(TabelaNBPBean.pobierzTabeleNieNBP(dzienposzukiwany, tabelanbpDAO, nazwawaluty));
                if (rodzajBiezacegoDokumentu != 0) {
                    pokazRzadWalutowy = true;
                }
                if (staranazwa != null && selected.getListawierszy().get(0).getStronaWn().getKwota() != 0.0) {
                    DokFKWalutyBean.przewalutujzapisy(staranazwa, nazwawaluty, selected, walutyDAOfk);
                    RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
                    selected.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty));
                } else {
                    Waluty waluta = walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty);
                    selected.setWalutadokumentu(waluta);
                    //wpisuje kurs bez przeliczania, to jest dla nowego dokumentu jak sie zmieni walute na euro
                    wybranawaluta = waluta.getSymbolwaluty();
                }
                if (zapisz0edytuj1 == false) {
                    symbolWalutyNettoVat = " " + selected.getTabelanbp().getWaluta().getSkrotsymbolu();
                } else {
                    symbolWalutyNettoVat = " zł";
                }
            } else {
                //najpierw trzeba przewalutowac ze starym kursem, a potem wlepis polska tabele
                if (staranazwa != null && selected.getListawierszy().get(0).getStronaWn().getKwota() != 0.0) {
                    DokFKWalutyBean.przewalutujzapisy(staranazwa, nazwawaluty, selected, walutyDAOfk);
                    RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
                    selected.setWalutadokumentu(domyslnaTabelanbp.getWaluta());
                } else {
                    selected.setWalutadokumentu(domyslnaTabelanbp.getWaluta());
                    //wpisuje kurs bez przeliczania, to jest dla nowego dokumentu jak sie zmieni walute na euro
                    wybranawaluta = domyslnaTabelanbp.getWaluta().getSkrotsymbolu();
                }
                selected.setTabelanbp(domyslnaTabelanbp);
                List<Wiersz> wiersze = selected.getListawierszy();
                for (Wiersz p : wiersze) {
                    p.setTabelanbp(domyslnaTabelanbp);
                }
                pokazRzadWalutowy = false;
                if (zapisz0edytuj1 == false) {
                    symbolWalutyNettoVat = " " + selected.getTabelanbp().getWaluta().getSkrotsymbolu();
                } else {
                    symbolWalutyNettoVat = " zł";
                }
            }
            RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat");
            RequestContext.getCurrentInstance().update("formwpisdokument:panelTabelaNBP");
            RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            RequestContext.getCurrentInstance().execute("r('formwpisdokument:tablicavat:0:netto_input').select();");
        }
    }

//    public void zmienbiezacatabele() {
//        selected.dodajTabeleWalut(wybranaTabelanbp);
//        DokFKWalutyBean.zmienkurswaluty(selected);
//        symbolWalutyNettoVat = wybranaTabelanbp.getWaluta().getSkrotsymbolu();
//    }
    public void przetworzwprowadzonakwote(Transakcja loop, int row) {
        try {
            if (!loop.getNowaTransakcja().getSymbolWalutBOiSW().equals("PLN") || !loop.getRozliczajacy().getSymbolWaluty().equals("PLN")) {
//                String wiersz = "rozrachunki:dataList:" + row + ":kwotarozliczenia_input";
//                String kwotazwiersza = (String) Params.params(wiersz);
//                kwotazwiersza = kwotazwiersza.replaceAll("\\s", "");
//                double placonakwota = Double.parseDouble(kwotazwiersza);
//                DokFKTransakcjeBean.wyliczroznicekursowa(loop, placonakwota);
                DokFKTransakcjeBean.wyliczroznicekursowa(loop, loop.getKwotatransakcji());
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd podczas pobierania tabel NBP. Nie obliczono różnic kursowych");
        }
    }
    
    public void przetworzwprowadzonakwoteOpis(Transakcja loop) {
        try {
            if (Z.z(loop.getKwotatransakcji())!=0.0) {
                pobierzopis(aktualnyWierszDlaRozrachunkow, loop);
                aktualnyWierszDlaRozrachunkow.setKontr(loop.getNowaTransakcja().getKontrahent());
            } else {
                if (aktualnyWierszDlaRozrachunkow.getOpis()!=null && aktualnyWierszDlaRozrachunkow.getOpis().size()>0) {
                    aktualnyWierszDlaRozrachunkow.getOpis().remove(loop.getNowaTransakcja().getNumerwlasnydokfk());
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    private void pobierzopis(StronaWiersza aktualnyWierszDlaRozrachunkow, Transakcja loop) {
        if (aktualnyWierszDlaRozrachunkow.getOpis()==null) {
            aktualnyWierszDlaRozrachunkow.setOpis(new HashSet<>());
        }
        aktualnyWierszDlaRozrachunkow.getOpis().add(loop.getNowaTransakcja().getNumerwlasnydokfk());
    }
    
    public void sprawdzlimity(Transakcja loop, int row) {
        if (aktualnyWierszDlaRozrachunkow.getPozostalo() < 0.0) {
                Msg.msg("e","Rozliczono ponad kwotę płatności. Nie można zapisać rozliczeń");
                RequestContext.getCurrentInstance().execute("r(\"rozrachunki:zapiszrozrachunekButton\").hide();");
            } else {
                RequestContext.getCurrentInstance().execute("r(\"rozrachunki:zapiszrozrachunekButton\").show();");
            }
    }

    public static void main(String[] args) {
        double kwotarozrachunku = Double.parseDouble("18370.80");
        System.out.println(kwotarozrachunku);
        double kwotaAktualnywPLN = Math.round(kwotarozrachunku * 4.2053 * 100);
        kwotaAktualnywPLN /= 100;
        double kwotaSparowanywPLN = Math.round(kwotarozrachunku * 4.1968 * 100);
        kwotaSparowanywPLN /= 100;
        double roznicakursowa = (kwotaAktualnywPLN - kwotaSparowanywPLN);
        System.out.println("aktualny " + kwotaAktualnywPLN);
        System.out.println("sparowany " + kwotaSparowanywPLN);
        roznicakursowa = Math.round(roznicakursowa * 100);
        roznicakursowa /= 100;
    }

    public void obsluzDataWiersza(Wiersz wierszbiezacy) {
        pobierzkursNBPwiersz(wierszbiezacy.getDataWalutyWiersza(), wierszbiezacy);
        przepiszWaluty(wierszbiezacy);
        int lpwtabeli = wierszbiezacy.getIdporzadkowy() - 1;
        String update = "formwpisdokument:dataList:" + lpwtabeli + ":kurswiersza";
        RequestContext.getCurrentInstance().update(update);
    }

    private void skopiujDateZWierszaWyzej(Wiersz wierszbiezacy) {
        int numerwiersza = wierszbiezacy.getIdporzadkowy();
        if (numerwiersza > 1) {
            int numerpoprzedni = numerwiersza - 2;
            int numeraktualny = numerwiersza - 1;
            String dataWierszPoprzedni = selected.getListawierszy().get(numerpoprzedni).getDataWalutyWiersza();
            Wiersz wierszBiezacy = selected.getListawierszy().get(numeraktualny);
            wierszBiezacy.setDataWalutyWiersza(dataWierszPoprzedni);
            Msg.msg("Skopiowano kwote z wiersza poprzedzającego");
        }
    }
//    //a to jest rodzial dotyczacy walut w wierszu

    private void pobierzkursNBPwiersz(String datawiersza, Wiersz wierszbiezacy) {
        String symbolwaluty = selected.getWalutadokumentu().getSymbolwaluty();
        boolean mozna = true;
        if (wierszbiezacy.getTabelanbp() != null && wierszbiezacy.getTabelanbp().isRecznie()) {
            mozna = false;
        }
        if (!symbolwaluty.equals("PLN") && mozna) {
            String datadokumentu;
            datadokumentu = selected.getDatadokumentu();
            if (datawiersza.length() == 1) {
                datawiersza = "0".concat(datawiersza);
            }
            datadokumentu = datadokumentu.substring(0, 8).concat(datawiersza);
            DateTime dzienposzukiwany = new DateTime(datadokumentu);
            boolean znaleziono = false;
            int zabezpieczenie = 0;
            Tabelanbp tabelanbp = null;
            while (!znaleziono && (zabezpieczenie < 365)) {
                dzienposzukiwany = dzienposzukiwany.minusDays(1);
                String doprzekazania = dzienposzukiwany.toString("yyyy-MM-dd");
                Tabelanbp tabelanbppobrana = tabelanbpDAO.findByDateWaluta(doprzekazania, symbolwaluty);
                if (tabelanbppobrana instanceof Tabelanbp) {
                    znaleziono = true;
                    tabelanbp = tabelanbppobrana;
                }
                zabezpieczenie++;
            }
            //wpisuje kurs bez przeliczania, to jest dla nowego dokumentu jak sie zmieni walute na euro
            wierszbiezacy.setTabelanbp(tabelanbp);
        }
    }

    public void skopiujKwoteWndoMa(Wiersz wiersz) {
        try {
            int wierszlp = wiersz.getIdporzadkowy() - 1;
            String coupdate = "formwpisdokument:dataList:" + wierszlp + ":ma";
            if (wiersz.getTypWiersza() == 0) {
                if (wiersz.getStronaMa().getKwota() == 0.0) {
                    wiersz.getStronaMa().setKwota(wiersz.getStronaWn().getKwota());
                    przepiszWaluty(wiersz);
                    RequestContext.getCurrentInstance().update(coupdate);
                }
            } else if (wiersz.getTypWiersza() == 5) {
                if (wiersz.getStronaMa().getKwota() == 0.0) {
                    wiersz.getStronaMa().setKwota(wiersz.getStronaWn().getKwota());
                    przepiszWaluty(wiersz);
                    RequestContext.getCurrentInstance().update(coupdate);
                }
            }

        } catch (Exception e) {
            E.e(e);

        }
    }

    public void skopiujKontoZWierszaWyzej(int numerwiersza, String wnma) {
        try {
            if (numerwiersza > 0) {
                int numerpoprzedni = numerwiersza - 1;
                StronaWiersza wierszPoprzedni = (wnma.equals("Wn") ? selected.getListawierszy().get(numerpoprzedni).getStronaWn() : selected.getListawierszy().get(numerpoprzedni).getStronaMa());
                StronaWiersza wierszBiezacy = (wnma.equals("Wn") ? selected.getListawierszy().get(numerwiersza).getStronaWn() : selected.getListawierszy().get(numerwiersza).getStronaMa());
                Konto kontoPoprzedni = serialclone.SerialClone.clone(wierszPoprzedni.getKonto());
                wierszBiezacy.setKonto(kontoPoprzedni);
                Msg.msg("Skopiowano konto z wiersza poprzedzającego");
            }
        } catch (Exception e) {
            E.e(e);

        }
    }

    public void skopiujKwoteZWierszaWyzej(int numerwiersza, String wnma) {
        //jak nie bylo tego zastrzezenia z opisem to przyprzechodzeniu po polach usuwal ostatni wiersz po dojsciu do konta wn
        if (numerwiersza > 1 && !selected.getListawierszy().get(numerwiersza - 1).getOpisWiersza().equals("")) {
            int numerpoprzedni = numerwiersza - 2;
            int numeraktualny = numerwiersza - 1;
            StronaWiersza wierszPoprzedni = (wnma.equals("Wn") ? selected.getListawierszy().get(numerpoprzedni).getStronaWn() : selected.getListawierszy().get(numerpoprzedni).getStronaMa());
            StronaWiersza wierszBiezacy = (wnma.equals("Wn") ? selected.getListawierszy().get(numeraktualny).getStronaWn() : selected.getListawierszy().get(numeraktualny).getStronaMa());
            if (wierszBiezacy.getKwota() == 0) {
                int typ = selected.getListawierszy().get(numerpoprzedni).getTypWiersza();
                wierszBiezacy.setKwota(wierszPoprzedni.getKwota());
                Msg.msg("Skopiowano kwote z wiersza poprzedzającego");
            }
        }
    }

    public void wygenerujnumerkolejny() {
        String nowynumer = DokFKBean.wygenerujnumerkolejny(selected, wpisView, dokDAOfk, klientdlaPK, wierszBODAO);
        if (zapisz0edytuj1 == false && nowynumer!=null && !nowynumer.equals("") && selected.getNumerwlasnydokfk() == null) {
            selected.setNumerwlasnydokfk(nowynumer);
            RequestContext.getCurrentInstance().update("formwpisdokument:numerwlasny");
        }
        if (selected.getRodzajedok()!=null && (selected.getRodzajedok().getKategoriadokumentu() == 0 || selected.getRodzajedok().getKategoriadokumentu() == 5)) {
            pobierzopiszpoprzedniegodokItemSelect();
        }
    }

    public void dodajPozycjeRKDoEwidencji() {
//        Konto konto = selected.getRodzajedok().getKontorozrachunkowe();
//        if (!selected.getEwidencjaVAT().contains(ewidencjaVatRK)) {
//            selected.getEwidencjaVAT().add(ewidencjaVatRK);
//        }
//        String dzien = ewidencjaVatRK.getDatadokumentu().split("-")[2];
//        wierszRK.setDataWalutyWiersza(dzien);
//        if (ewidencjaVatRK.getEwidencja().getTypewidencji().equals("z")) {
//            wierszRK.getStronaWn().setKwota(ewidencjaVatRK.getNetto());
//            wierszRK.getStronaMa().setKwota(ewidencjaVatRK.getBrutto());
//            if (konto != null) {
//                wierszRK.getStronaMa().setKonto(konto);
//            }
//        } else if (ewidencjaVatRK.getEwidencja().getTypewidencji().equals("s")){
//            wierszRK.getStronaWn().setKwota(ewidencjaVatRK.getBrutto());
//            if (konto != null) {
//                wierszRK.getStronaWn().setKonto(konto);
//            }
//            wierszRK.getStronaMa().setKwota(ewidencjaVatRK.getNetto());
//        }
//        String update = "formwpisdokument:dataList:"+wierszRKindex+":dataWiersza";
//        RequestContext.getCurrentInstance().update(update);
//        update = "formwpisdokument:dataList:"+wierszRKindex+":wn";
//        RequestContext.getCurrentInstance().update(update);
//        update = "formwpisdokument:dataList:"+wierszRKindex+":ma";
//        RequestContext.getCurrentInstance().update(update);
//        update = "formwpisdokument:dataList:"+wierszRKindex+":kontown";
//        RequestContext.getCurrentInstance().update(update);
//        update = "formwpisdokument:dataList:"+wierszRKindex+":kontoma";
//        RequestContext.getCurrentInstance().update(update);
//        RequestContext.getCurrentInstance().execute("odtworzwierszVATRK();");
//        ewidencjaVatRK = null;
//        Msg.msg("Zachowano zapis w ewidencji VAT");
    }

    public void przenumeruj() {
        ObslugaWiersza.przenumerujSelected(selected);
    }

    //to służy do pobierania wiersza do dialgou ewidencji w przypadku edycji ewidencji raportu kasowego
    public void ewidencjaVatRKInit() {
        if (lpwierszaRK != null) {
            lpWierszaWpisywanie = lpwierszaRK;
            if (selected.getRodzajedok().getKategoriadokumentu() == 0 || selected.getRodzajedok().getKategoriadokumentu() == 5) {
                try {
                    //                DataTable d = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formwpisdokument:dataList");
                    //                Object o = d.getLocalSelection();
                    //                wierszRKindex = d.getRowIndex();
                    //                wierszRK = (Wiersz) d.getRowData();
                    //System.out.println("lpwiersza " + lpWierszaWpisywanie);
//                DataTable d = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formwpisdokument:dataList");
                    //                Object o = d.getLocalSelection();
                    //                wierszRKindex = d.getRowIndex();
                    //                wierszRK = (Wiersz) d.getRowData();
                                        wierszRKindex = lpWierszaWpisywanie - 1;
                    wierszRK = selected.getListawierszy().get(wierszRKindex);
                    ewidencjaVatRK = null;
                    for (EVatwpisFK p : selected.getEwidencjaVAT()) {
                        if (p.getWiersz() == wierszRK) {
                            ewidencjaVatRK = p;
                            ewidencjaVATRKzapis0edycja1 = true;
                            break;
                        }
                    }
                    if (ewidencjaVatRK == null) {
                        ewidencjaVatRK = new EVatwpisFK();
                        ewidencjaVatRK.setLp(0);
                        ewidencjaVatRK.setWiersz(wierszRK);
                        ewidencjaVatRK.setDokfk(selected);
                        ewidencjaVatRK.setNetto(0.0);
                        ewidencjaVatRK.setVat(0.0);
                        ewidencjaVatRK.setEwidencja(ewidencjadlaRKDEL);
                        ewidencjaVATRKzapis0edycja1 = false;
                    }
                    RequestContext.getCurrentInstance().update("dialogewidencjavatRK");
                } catch (Exception e) {
                    E.e(e);
                }
            }
        } else {
            ewidencjaVatRK = null;
        }
    }

    public void zmienwalutezapisow() {
        if (pokazzapisywzlotowkach == true) {
            pokazzapisywzlotowkach = false;
        } else {
            pokazzapisywzlotowkach = true;
        }
    }

    public void zmienmiesiac(int innyokres) {
        StronaWiersza p = null;
        Konto k221_3 = kontoDAOfk.findKonto("221-3", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        Konto k221_4 = kontoDAOfk.findKonto("221-4", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        Konto k221_1 = kontoDAOfk.findKonto("221-1", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        Konto k221_2 = kontoDAOfk.findKonto("221-2", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        selected.oznaczVATdokument(sprawdzjakiokresvat());
        for (Wiersz r : selected.getListawierszy()) {
            if (innyokres != 0) {
                if (r.getStronaWn() != null && r.getStronaWn().getKonto().getPelnynumer().equals("221-3")) {
                    r.getStronaWn().setKonto(k221_4);
                } else if (r.getStronaMa() != null && r.getStronaMa().getKonto().getPelnynumer().equals("221-3")) {
                    r.getStronaMa().setKonto(k221_4);
                }
                if (r.getStronaWn() != null && r.getStronaWn().getKonto().getPelnynumer().equals("221-1")) {
                    r.getStronaWn().setKonto(k221_2);
                } else if (r.getStronaMa() != null && r.getStronaMa().getKonto().getPelnynumer().equals("221-1")) {
                    r.getStronaMa().setKonto(k221_2);
                }
            } else {
                if (r.getStronaWn() != null && r.getStronaWn().getKonto().getPelnynumer().equals("221-4")) {
                    r.getStronaWn().setKonto(k221_3);
                } else if (r.getStronaMa() != null && r.getStronaMa().getKonto().getPelnynumer().equals("221-4")) {
                    r.getStronaMa().setKonto(k221_3);
                }
                if (r.getStronaWn() != null && r.getStronaWn().getKonto().getPelnynumer().equals("221-2")) {
                    r.getStronaWn().setKonto(k221_1);
                } else if (r.getStronaMa() != null && r.getStronaMa().getKonto().getPelnynumer().equals("221-2")) {
                    r.getStronaMa().setKonto(k221_1);
                }
            }
        }
    }
    
    public String sprawdzjakiokresvat() {
        Integer rok = wpisView.getRokWpisu();
        Integer mc = Integer.parseInt(wpisView.getMiesiacWpisu());
        List<Parametr> parametry = wpisView.getPodatnikObiekt().getVatokres();
        return ParametrView.zwrocParametr(parametry, rok, mc);
    }
    

    public void dodajcechedodokumentu(Cechazapisu c) {
        pobranecechypodatnik.remove(c);
        selected.getCechadokumentuLista().add(c);
        c.getDokfkLista().add(selected);
    }

    public void usuncechedodokumentu(Cechazapisu c) {
        pobranecechypodatnik.add(c);
        selected.getCechadokumentuLista().remove(c);
        c.getDokfkLista().remove(selected);
    }

    private void obsluzcechydokumentu() {
        //usuwamy z listy dostepnych cech te, ktore sa juz przyporzadkowane do dokumentu
        if (pobranecechypodatnik != null) {
            List<Cechazapisu> cechyuzyte = null;
            if (selected != null) {
                if (selected.getCechadokumentuLista() == null) {
                    cechyuzyte = Collections.synchronizedList(new ArrayList<>());
                } else {
                    cechyuzyte = selected.getCechadokumentuLista();
                }
                pobranecechypodatnik = new ArrayList<>(pobranecechypodatnikzapas);
                for (Cechazapisu c : cechyuzyte) {
                    pobranecechypodatnik.remove(c);
                }
            }
        }
    }

    private double obliczsaldopoczatkowe() {
        List<StronaWiersza> kontozapisy = stronaWierszaDAO.findStronaByPodatnikKontoRokWaluta(wpisView.getPodatnikObiekt(), selected.getRodzajedok().getKontorozrachunkowe(), wpisView.getRokWpisuSt(), selected.getTabelanbp().getWaluta().getSymbolwaluty());
        if (kontozapisy != null && !kontozapisy.isEmpty()) {
            double sumaWn = 0.0;
            double sumaMa = 0.0;
            for (Iterator<StronaWiersza> it = kontozapisy.iterator(); it.hasNext();) {
                StronaWiersza p = it.next();
                if (p.getWiersz().getDokfk().equals(selected)) {
                    it.remove();
                } else if (p.getWiersz().getDokfk().getNrkolejnywserii() > selected.getNrkolejnywserii()) {
                    it.remove();
                } else {
                    switch (p.getWnma()) {
                        case "Wn":
                            sumaWn += p.getKwota();
                            break;
                        case "Ma":
                            sumaMa += p.getKwota();
                            break;
                    }
                }
            }
            return sumaWn - sumaMa;
        } else {
            return 0.0;
        }
    }

//    public void uzupelnijdokumentyodkontrahenta() {
//        try {
//            for (Dokfk p : wykazZaksiegowanychDokumentow) {
//                if (p.getRodzajedok().getKategoriadokumentu() != 1 && p.getRodzajedok().getKategoriadokumentu() != 2 && p.getKontr() == null) {
//                    Klienci k = klienciDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
//                    if (k == null) {
//                        k = new Klienci("222222222222222222222", "BRAK FIRMY JAKO KONTRAHENTA!!!");
//                    }
//                    p.setKontr(k);
//                    dokDAOfk.edit(p);
//                }
//            }
//        } catch (Exception e) {
//            E.e(e);
//
//        }
//    }
    public void oznaczewidencjevat() {
        List<EVatwpisFK> lista = eVatwpisFKDAO.findAll();
        for (EVatwpisFK p : lista) {
            try {
                p.setRokEw(p.getDokfk().getVatR());
                p.setMcEw(p.getDokfk().getVatM());
            } catch (Exception e) {
                E.e(e);

            }
        }
        for (EVatwpisFK p : lista) {
            eVatwpisFKDAO.edit(p);
        }
        Msg.msg("Zmieniono ewidencje");
    }

    public void sprawdzwartoscigrupy(Wiersz wierszzmieniony) {
        try {
            if (wierszzmieniony.getDokfk().getSeriadokfk().equals("BO")) {
                return;
            }
            Wiersz wiersdolnysprawdz = selected.nastepnyWiersz(wierszzmieniony);
            if (wiersdolnysprawdz!=null) {
                Wiersz wierszpodstawowy = wierszzmieniony;
                if (wierszzmieniony.getLpmacierzystego()!=0) {
                    wierszpodstawowy = selected.poprzedniWiersz(wierszzmieniony);
                    do {
                        if (wierszpodstawowy!= null && wierszpodstawowy.getLpmacierzystego() != 0) {
                            wierszpodstawowy = selected.poprzedniWiersz(wierszpodstawowy);
                        } else if (wierszpodstawowy == null){
                            wierszpodstawowy = wierszzmieniony;
                            break;
                        } else {
                            break;
                        }
                    } while (true);
                }
                boolean wypelniony = wierszzmieniony.isWypelniony();
                if (wypelniony) {
                    int nrgr = wierszpodstawowy.getIdporzadkowy();
                    double sumaWn = wierszpodstawowy.getStronaWn().getKwota();
                    double sumaMa = wierszpodstawowy.getStronaMa().getKwota();
                    Wiersz wierszsumowany = selected.nastepnyWiersz(wierszpodstawowy);
                    do {
                        if (wierszsumowany != null && wierszsumowany.getTypWiersza() == 0) {
                            break;
                        }
                        if (wierszsumowany != null) {
                            if (wierszsumowany.getTypWiersza() == 1) {
                                sumaWn += wierszsumowany.getStronaWn().getKwota();
                                typwiersza = 1;
                            } else if (wierszsumowany.getTypWiersza() == 2) {
                                sumaMa += wierszsumowany.getStronaMa().getKwota();
                                typwiersza = 2;
                            }
                            wierszsumowany = selected.nastepnyWiersz(wierszsumowany);
                        } else {
                            wierszsumowany = wierszpodstawowy;
                            break;
                        }
                    } while (true);
                    if (Z.z(sumaWn) != Z.z(sumaMa)) {
                        Wiersz wierszponizej = selected.nastepnyWiersz(wierszzmieniony);
                        if (wierszponizej != null) {
                            ObslugaWiersza.wygenerujWierszRoznicowy(wierszzmieniony, true, nrgr, selected);
                            RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
                        } else {
                            ObslugaWiersza.wygenerujWierszRoznicowy(wierszzmieniony, false, nrgr, selected);
                            RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
                        }
                        selected.przeliczKwotyWierszaDoSumyDokumentu();
                    }
                    rozliczsaldoWBRK(wierszpodstawowy.getIdporzadkowy() - 1);
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
    }

    public void przelicznaklawiszu() {
        selected.przeliczKwotyWierszaDoSumyDokumentu();
        RequestContext.getCurrentInstance().update("formwpisdokument:wartoscdokumentu");
    }

//    public void resetujzaksiegowane() {
//        wykazZaksiegowanychDokumentow = Collections.synchronizedList(new ArrayList<>());
//    }
    public void resetujzaksiegowaneimport() {
        wykazZaksiegowanychDokumentow = Collections.synchronizedList(new ArrayList<>());
    }

    public void niedodawajkonta() {
        niedodawajkontapole = true;
    }

    public void przenumerujDokumentyFK() {
        try {
            List<Dokfk> dokumenty = null;
            List<String> serie = null;
            if (wybranakategoriadok.equals("wszystkie")) {
                serie = dokDAOfk.findZnajdzSeriePodatnik(wpisView);
                dokumenty = dokDAOfk.findDokfkPodatnikRok(wpisView);
            } else {
                serie = Collections.synchronizedList(new ArrayList<>());
                serie.add(wybranakategoriadok);
                dokumenty = dokDAOfk.findDokfkPodatnikRokKategoria(wpisView, wybranakategoriadok);
            }
            nadajnowenumery(serie, dokumenty);
            Msg.dP();
        } catch (Exception e) {
            Msg.dPe();
        }
    }

    private void nadajnowenumery(List<String> serie, List<Dokfk> dokumenty) {
        List<Dokfk> nowadosortowania = null;
        for (String r : serie) {
            nowadosortowania = Collections.synchronizedList(new ArrayList<>());
            for (Dokfk t : dokumenty) {
                if (t.getSeriadokfk().equals(r)) {
                    nowadosortowania.add(t);
                }
            }
            Collections.sort(nowadosortowania, new DokfkLPcomparator());
            int kolejny = 1;
            for (Dokfk f : nowadosortowania) {
                f.setNrkolejnywserii(kolejny++);

            }
        }
        if (nowadosortowania != null) {
            dokDAOfk.editList(nowadosortowania);
        }
    }

    public void usunwszytskieimportowane() {
        for (Dokfk p : wykazZaksiegowanychDokumentowimport) {
            dokDAOfk.destroy(p);
        }
        wykazZaksiegowanychDokumentow = Collections.synchronizedList(new ArrayList<>());
        Msg.msg("Usunięto wszystkie zaimportowane dokumenty");
    }

    private String poledlawaluty;

    public String getPoledlawaluty() {
        return poledlawaluty;
    }

    public void setPoledlawaluty(String poledlawaluty) {
        this.poledlawaluty = poledlawaluty;
    }

    public void pobierzpoledlawaluty() {
        String wierszlp = (String) Params.params("wpisywaniefooter:lpwierszaRK");
        int idwiersza = Integer.parseInt(wierszlp)-1;
        if (!wiersz.equals("")) {
            poledlawaluty = wierszlp;
        }
        if (idwiersza > -1) {
            Wiersz wiersz = selected.getListawierszy().get(idwiersza);
            FacesContext context = FacesContext.getCurrentInstance();
            WalutyViewFK bean = context.getApplication().evaluateExpressionGet(context, "#{walutyViewFK}", WalutyViewFK.class);
            Tabelanbp tab = tabelanbpDAO.findById(wiersz.getTabelanbp().getIdtabelanbp());
            bean.setKurswprowadzonyrecznie(tab);
            bean.setSymbolRecznie(wiersz.getWalutaWiersz());
        }
    }

    public void zamienkursnareczny() {
        try {
            String wierszlp = poledlawaluty;
            if (selected.getRodzajedok().getKategoriadokumentu()==0 && !tabelanbprecznie.getWaluta().equals(selected.getWalutadokumentu())) {
                Msg.msg("e", "Waluta dokumentu WB/RK jest inna niz wybranej tabeli z kursem. Nie zmieniam kursu wiersza");
            } else if (!wiersz.equals("")) {
                int wierszid = Integer.parseInt(wierszlp) - 1;
                Wiersz wiersz = selected.getListawierszy().get(wierszid);
                wiersz.setTabelanbp(tabelanbprecznie);
                przepiszWaluty(wiersz);
                poledlawaluty = "";
            }
        } catch (Exception e) {
            E.e(e);
        }
    }

    public void resetujkursdoNBP() {
        try {
            String wierszlp = poledlawaluty;
            if (!wiersz.equals("")) {
                int wierszid = Integer.parseInt(wierszlp) - 1;
                Wiersz wiersz = selected.getListawierszy().get(wierszid);
                wiersz.setTabelanbp(null);
                wiersz.setDataWalutyWiersza(null);
                przepiszWaluty(wiersz);
                String update = "formwpisdokument:dataList:" + wierszid + ":kurswiersza";
                RequestContext.getCurrentInstance().update(update);
                update = "formwpisdokument:dataList:" + wierszid + ":dataWiersza";
                RequestContext.getCurrentInstance().update(update);
                poledlawaluty = "";
            }
        } catch (Exception e) {
            E.e(e);

        }
    }

    public void zamienkursnareczny(Tabelanbp tabelanbprecznie) {
        try {
            String wierszlp = poledlawaluty;
            if (!wiersz.equals("")) {
                int wierszid = Integer.parseInt(wierszlp) - 1;
                Wiersz wiersz = selected.getListawierszy().get(wierszid);
                wiersz.setTabelanbp(tabelanbprecznie);
                przepiszWaluty(wiersz);
                String update = "formwpisdokument:dataList:" + wierszid + ":kurswiersza";
                RequestContext.getCurrentInstance().update(update);
                poledlawaluty = "";
            }
        } catch (Exception e) {
            E.e(e);

        }
    }

    public void oznaczDokfkJakoWzorzec() {
        if (selectedlist == null || selectedlist.isEmpty()) {
            Msg.msg("e", "Nie wybrano dokumentu");
        } else {
            Dokfk wzorzec = selectedlist.get(0);
            wzorzec.setWzorzec(!wzorzec.isWzorzec());
            dokDAOfk.edit(wzorzec);
            if (wzorzec.isWzorzec() == true) {
                Msg.msg("Oznaczono dokument jako wzorzec.");
            } else {
                Msg.msg("w", "Odznaczono dokument jako wzorzec.");
            }
        }
    }
    
    public void sortujwiersze() {
        if (selectedlist == null || selectedlist.isEmpty()) {
            Msg.msg("e", "Nie wybrano dokumentu");
        } else {
            Dokfk dosortowania = selectedlist.get(0);
            if (dosortowania.getRodzajedok().getKategoriadokumentu()!=0) {
                Msg.msg("e", "Sortuje tylko dokumenty typu WB i RK");
            } else {
                try {
                    dosortowania.sortujwierszeData();
                    //dosortowania.sortujwierszeID();
                    dokDAOfk.edit(dosortowania);
                    Msg.msg("Posortowano wiersze dokumentu");
                } catch (Exception e) {
                    Msg.msg("e", "Wystąpił błąd, nie posortowano wierszy dokumentu");
                }
            }
        }
    }
    
    public void zaksiegujdokumenty() {
        if (wykazZaksiegowanychDokumentow == null || wykazZaksiegowanychDokumentow.isEmpty()) {
            Msg.msg("e", "Nie wybrano dokumentu do zaksięowania");
        } else {
            try {
                for (Dokfk p : wykazZaksiegowanychDokumentow) {
                    if (p.getDataksiegowania() == null) {
                        p.setDataksiegowania(new Date());
                    } else {
                        p.setDataksiegowania(null);
                    }
                }
                dokDAOfk.editList(wykazZaksiegowanychDokumentow);
                Msg.msg("Zaksięgowano dokumenty w liczbie: "+wykazZaksiegowanychDokumentow.size());
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd podczas księgowania dokumentów.");
            }
        }
    }
    
    public void odksiegujdokumenty() {
            try {
                for (Dokfk p : selectedlist) {
                    p.setDataksiegowania(null);
                }
                dokDAOfk.editList(selectedlist);
                Msg.msg("Odksięgowane wybrane dokumenty w liczbie: "+selectedlist.size());
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd podczas odksięgowania dokumentów.");
            }
        }

    public void drukujzaksiegowanydokument() {
        if (wykazZaksiegowanychDokumentow != null && wykazZaksiegowanychDokumentow.size() > 0 && filteredValue.size() == 0) {
            String nazwa = wpisView.getPodatnikObiekt().getNip() + "dokumentzaksiegowane";
            File file = Plik.plik(nazwa, true);
            if (file.isFile()) {
                file.delete();
            }
            wydrukujzestawieniedok(nazwa, wykazZaksiegowanychDokumentow);
        } else {
            Msg.msg("w", "Nie wybrano wierszy do wydruku");
        }
        if (filteredValue != null && filteredValue.size() > 0) {
            for (Dokfk p : filteredValue) {
                String nazwa = wpisView.getPodatnikObiekt().getNip() + "dokumentzaksiegowane" + p.getNrkolejnywserii();
                File file = Plik.plik(nazwa, true);
                if (file.isFile()) {
                    file.delete();
                }
                Uz uz = wpisView.getWprowadzil();
                PdfDokfk.drukujtrescpojedynczegodok(nazwa, p, uz);
                String f = "pokazwydruk('" + nazwa + "');";
                RequestContext.getCurrentInstance().execute(f);
            }
        } else {
            Msg.msg("w", "Nie wybrano wierszy do wydruku");
        }
    }

    private void wydrukujzestawieniedok(String nazwa, List<Dokfk> wiersze) {
        Uz uz = wpisView.getWprowadzil();
        Document document = inicjacjaA4Portrait();
        PdfWriter writer = inicjacjaWritera(document, nazwa);
        naglowekStopkaP(writer);
        otwarcieDokumentu(document, nazwa);
        dodajOpisWstepny(document, "Zestawienie zaksięgowanych dokumentów", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
        dodajTabele(document, testobjects.testobjects.getTabelaZaksiegowane(wiersze), 100, 0);
        finalizacjaDokumentuQR(document,nazwa);
        String f = "wydrukZaksiegowaneLista('" + wpisView.getPodatnikObiekt().getNip() + "');";
        RequestContext.getCurrentInstance().execute(f);
    }

    public void usunzaznaczone() {
        try {
            if (selectedlist != null && selectedlist.size() > 0) {
                for (Dokfk p : selectedlist) {
                    wykazZaksiegowanychDokumentow.remove(p);
                    dokDAOfk.destroy(p);
                }
                selectedlist = null;
            }
            Msg.msg("Usunięto zaznaczone dokumnety");
        } catch (Exception e) {
            Msg.msg("e", "Wystapił błąd poczad usuwania wybranych dokumentów. Spróbuj usunąć je pojedynczo");
        }
    }
    
    
    public void usunspecjalne() {
        try {
            if (selectedlist != null && selectedlist.size() > 0) {
                for (Dokfk pa : selectedlist) {
                    Dokfk p = dokDAOfk.findDokfkID(pa);
                    List<Wiersz> wiersze = p.getListawierszy();
                    List<StronaWiersza> strony = pobierzwiersze(wiersze);
                    List<Transakcja> transakcje = pobierztransakcje(strony);
                    for (Transakcja sz : transakcje) {
                        try {
                            transakcjaDAO.destroy(sz);
                        } catch (Exception e){}
                    }
                    for (StronaWiersza sa : strony) {
                        try {
                            StronaWiersza s = stronaWierszaDAO.findStronaById(sa);
                            //System.out.println("DELETE FROM `pkpir`.`stronawiersza` WHERE `id`='"+sa.getId()+"';");
                            stronaWierszaDAO.destroy(sa);
                        } catch (Exception e){
                            System.out.println("DELETE FROM `pkpir`.`stronawiersza` WHERE `id`='"+sa.getId()+"';");
                        }
                    }
                    for (Wiersz s : wiersze) {
                        try {
                            wierszDAO.destroy(s);
                        } catch (Exception e){
                            System.out.println(""+s.toString());
                        }
                    }
                    dokDAOfk.destroy(p);
                    wykazZaksiegowanychDokumentow.remove(p);
                }
                selectedlist = null;
            }
            Msg.msg("Usunięto zaznaczone dokumnety");
        } catch (Exception e) {
            Msg.msg("e", "Wystapił błąd poczad usuwania wybranych dokumentów. Spróbuj usunąć je pojedynczo");
        }
    }
    
    private List<StronaWiersza> pobierzwiersze(List<Wiersz> wiersze) {
        List<StronaWiersza> zwrot = new ArrayList<>();
        for (Wiersz w : wiersze) {
            zwrot.addAll(w.getStronyWiersza());
        }
        return zwrot;
    }
    
    private List<Transakcja> pobierztransakcje(List<StronaWiersza> strony) {
        List<Transakcja> zwrot = new ArrayList<>();
        for (StronaWiersza w : strony) {
            zwrot.addAll(transakcjaDAO.findByNowaTransakcja(w));
            zwrot.addAll(transakcjaDAO.findByRozliczajacy(w));
        }
        return zwrot;
    }


//<editor-fold defaultstate="collapsed" desc="comment">
    public String getWybranakategoriadok() {
        return wybranakategoriadok;
    }

    public void setWybranakategoriadok(String wybranakategoriadok) {
        this.wybranakategoriadok = wybranakategoriadok;
    }

    public String getMiesiacWpisuPokaz() {
        return miesiacWpisuPokaz;
    }

    public void setMiesiacWpisuPokaz(String miesiacWpisuPokaz) {
        this.miesiacWpisuPokaz = miesiacWpisuPokaz;
    }

    public boolean isPokazwszystkiedokumenty() {
        return pokazwszystkiedokumenty;
    }

    public void setPokazwszystkiedokumenty(boolean pokazwszystkiedokumenty) {
        this.pokazwszystkiedokumenty = pokazwszystkiedokumenty;
    }

    public boolean isTotylkoedycjaanalityczne() {
        return totylkoedycjaanalityczne;
    }

    public void setTotylkoedycjaanalityczne(boolean totylkoedycjaanalityczne) {
        this.totylkoedycjaanalityczne = totylkoedycjaanalityczne;
    }

    public boolean isPokazsrodkitrwale() {
        return pokazsrodkitrwale;
    }

    public void setPokazsrodkitrwale(boolean pokazsrodkitrwale) {
        this.pokazsrodkitrwale = pokazsrodkitrwale;
    }

    public boolean isPokazrmk() {
        return pokazrmk;
    }

    public void setPokazrmk(boolean pokazrmk) {
        this.pokazrmk = pokazrmk;
    }

    
    public KontoZapisFKView getKontoZapisFKView() {
        return kontoZapisFKView;
    }

    public void setKontoZapisFKView(KontoZapisFKView kontoZapisFKView) {
        this.kontoZapisFKView = kontoZapisFKView;
    }

    public Double getPodsumowaniewybranych() {
        return podsumowaniewybranych;
    }

    public void setPodsumowaniewybranych(Double podsumowaniewybranych) {
        this.podsumowaniewybranych = podsumowaniewybranych;
    }

  
    public String getKomunikatywpisdok() {
        return komunikatywpisdok;
    }

    public void setKomunikatywpisdok(String komunikatywpisdok) {
        this.komunikatywpisdok = komunikatywpisdok;
    }

    public int getJest1niema0_konto() {
        return jest1niema0_konto;
    }

    public void setJest1niema0_konto(int jest1niema0_konto) {
        this.jest1niema0_konto = jest1niema0_konto;
    }

    public double getSaldoBO() {
        return saldoBO;
    }

    public void setSaldoBO(double saldoBO) {
        this.saldoBO = saldoBO;
    }

    public List<Tabelanbp> getTabelenbp() {
        return tabelenbp;
    }

    public void setTabelenbp(List<Tabelanbp> tabelenbp) {
        this.tabelenbp = tabelenbp;
    }

    public Integer getNrgrupyaktualny() {
        return nrgrupyaktualny;
    }

    public void setNrgrupyaktualny(Integer nrgrupyaktualny) {
        this.nrgrupyaktualny = nrgrupyaktualny;
    }

    public Integer getNrgrupywierszy() {
        return nrgrupywierszy;
    }

    public void setNrgrupywierszy(Integer nrgrupywierszy) {
        this.nrgrupywierszy = nrgrupywierszy;
    }

    public boolean isNiedodawajkontapole() {
        return niedodawajkontapole;
    }

    public void setNiedodawajkontapole(boolean niedodawajkontapole) {
        this.niedodawajkontapole = niedodawajkontapole;
    }

    public Dokfk getDokumentdousuniecia() {
        return dokumentdousuniecia;
    }

    public void setDokumentdousuniecia(Dokfk dokumentdousuniecia) {
        this.dokumentdousuniecia = dokumentdousuniecia;
    }

    public List<Dokfk> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<Dokfk> filteredValue) {
        this.filteredValue = filteredValue;
    }

    public StronaWiersza getStronaWierszaCechy() {
        return stronaWierszaCechy;
    }

    public void setStronaWierszaCechy(StronaWiersza stronaWierszaCechy) {
        this.stronaWierszaCechy = stronaWierszaCechy;
    }

    public boolean isPokazzapisywzlotowkach() {
        return pokazzapisywzlotowkach;
    }

    public void setPokazzapisywzlotowkach(boolean pokazzapisywzlotowkach) {
        this.pokazzapisywzlotowkach = pokazzapisywzlotowkach;
    }

    public boolean isWlaczZapiszButon() {
        return wlaczZapiszButon;
    }

    public void setWlaczZapiszButon(boolean wlaczZapiszButon) {
        this.wlaczZapiszButon = wlaczZapiszButon;
    }

    public List<Evewidencja> getListaewidencjivatRK() {
        return listaewidencjivatRK;
    }

    public void setListaewidencjivatRK(List<Evewidencja> listaewidencjivatRK) {
        this.listaewidencjivatRK = listaewidencjivatRK;
    }

    public EVatwpisFK getEwidencjaVatRK() {
        return ewidencjaVatRK;
    }

    public void setEwidencjaVatRK(EVatwpisFK ewidencjaVatRK) {
        this.ewidencjaVatRK = ewidencjaVatRK;
    }

    public String getSymbolWalutyNettoVat() {
        return symbolWalutyNettoVat;
    }

    public void setSymbolWalutyNettoVat(String symbolWalutyNettoVat) {
        this.symbolWalutyNettoVat = symbolWalutyNettoVat;
    }

    public int getRodzajBiezacegoDokumentu() {
        return rodzajBiezacegoDokumentu;
    }

    public void setRodzajBiezacegoDokumentu(int rodzajBiezacegoDokumentu) {
        this.rodzajBiezacegoDokumentu = rodzajBiezacegoDokumentu;
    }

    public Wiersz getWybranyWiersz() {
        return wybranyWiersz;
    }

    public void setWybranyWiersz(Wiersz wybranyWiersz) {
        this.wybranyWiersz = wybranyWiersz;
    }

    public int getTypwiersza() {
        return typwiersza;
    }

    public void setTypwiersza(int typwiersza) {
        this.typwiersza = typwiersza;
    }

    public String getRachunekCzyPlatnosc() {
        return rachunekCzyPlatnosc;
    }

    public void setRachunekCzyPlatnosc(String rachunekCzyPlatnosc) {
        this.rachunekCzyPlatnosc = rachunekCzyPlatnosc;
    }

    public StronaWiersza getAktualnyWierszDlaRozrachunkow() {
        return aktualnyWierszDlaRozrachunkow;
    }

    public void setAktualnyWierszDlaRozrachunkow(StronaWiersza aktualnyWierszDlaRozrachunkow) {
        this.aktualnyWierszDlaRozrachunkow = aktualnyWierszDlaRozrachunkow;
    }

    public Integer getLpWierszaWpisywanie() {
        return lpWierszaWpisywanie;
    }

    public void setLpWierszaWpisywanie(Integer lpWierszaWpisywanie) {
        this.lpWierszaWpisywanie = lpWierszaWpisywanie;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Dokfk getSelected() {
        return selected;
    }

    public int getWierszDoPodswietlenia() {
        return wierszDoPodswietlenia;
    }

    public void setWierszDoPodswietlenia(int wierszDoPodswietlenia) {
        this.wierszDoPodswietlenia = wierszDoPodswietlenia;
    }

    public void setSelected(Dokfk selected) {
        this.selected = selected;
    }

    public List<Dokfk> getWykazZaksiegowanychDokumentow() {
        return wykazZaksiegowanychDokumentow;
    }

    public void setWykazZaksiegowanychDokumentow(List<Dokfk> wykazZaksiegowanychDokumentow) {
        this.wykazZaksiegowanychDokumentow = wykazZaksiegowanychDokumentow;
    }

    public List<Dokfk> getWykazZaksiegowanychDokumentowimport() {
        return wykazZaksiegowanychDokumentowimport;
    }

    public void setWykazZaksiegowanychDokumentowimport(List<Dokfk> wykazZaksiegowanychDokumentowimport) {
        this.wykazZaksiegowanychDokumentowimport = wykazZaksiegowanychDokumentowimport;
    }

    public boolean isZapisz0edytuj1() {
        return zapisz0edytuj1;
    }

    public void setZapisz0edytuj1(boolean zapisz0edytuj1) {
        this.zapisz0edytuj1 = zapisz0edytuj1;
    }

    public String getWybranacechadok() {
        return wybranacechadok;
    }

    public void setWybranacechadok(String wybranacechadok) {
        this.wybranacechadok = wybranacechadok;
    }
 

    public Wiersz getWiersz() {
        return wiersz;
    }

    public void setWiersz(Wiersz wiersz) {
        this.wiersz = wiersz;
    }

    public int getIdwierszedycjaodswiezenie() {
        return idwierszedycjaodswiezenie;
    }

    public void setIdwierszedycjaodswiezenie(int idwierszedycjaodswiezenie) {
        this.idwierszedycjaodswiezenie = idwierszedycjaodswiezenie;
    }

    public List<Transakcja> getBiezacetransakcje() {
        return biezacetransakcje;
    }

    public void setBiezacetransakcje(List<Transakcja> biezacetransakcje) {
        this.biezacetransakcje = biezacetransakcje;
    }

    public boolean isZablokujprzyciskzapisz() {
        return zablokujprzyciskzapisz;
    }

    public void setZablokujprzyciskzapisz(boolean zablokujprzyciskzapisz) {
        this.zablokujprzyciskzapisz = zablokujprzyciskzapisz;
    }

    public String getWybranawaluta() {
        return wybranawaluta;
    }

    public void setWybranawaluta(String wybranawaluta) {
        this.wybranawaluta = wybranawaluta;
    }

    public String getSymbolwalutydowiersza() {
        return symbolwalutydowiersza;
    }

    public void setSymbolwalutydowiersza(String symbolwalutydowiersza) {
        this.symbolwalutydowiersza = symbolwalutydowiersza;
    }

    public List<Waluty> getWprowadzonesymbolewalut() {
        return wprowadzonesymbolewalut;
    }

    public void setWprowadzonesymbolewalut(List<Waluty> wprowadzonesymbolewalut) {
        this.wprowadzonesymbolewalut = wprowadzonesymbolewalut;
    }

    public boolean isPokazPanelWalutowy() {
        return pokazPanelWalutowy;
    }

    public void setPokazPanelWalutowy(boolean pokazPanelWalutowy) {
        this.pokazPanelWalutowy = pokazPanelWalutowy;
    }

    public boolean isPokazRzadWalutowy() {
        return pokazRzadWalutowy;
    }

    public void setPokazRzadWalutowy(boolean pokazRzadWalutowy) {
        this.pokazRzadWalutowy = pokazRzadWalutowy;
    }

    public boolean isEwidencjaVATRKzapis0edycja1() {
        return ewidencjaVATRKzapis0edycja1;
    }

    public void setEwidencjaVATRKzapis0edycja1(boolean ewidencjaVATRKzapis0edycja1) {
        this.ewidencjaVATRKzapis0edycja1 = ewidencjaVATRKzapis0edycja1;
    }

    public Tabelanbp getWybranaTabelanbp() {
        return wybranaTabelanbp;
    }

    public void setWybranaTabelanbp(Tabelanbp wybranaTabelanbp) {
        this.wybranaTabelanbp = wybranaTabelanbp;
    }

    public List<Cechazapisu> getPobranecechypodatnik() {
        return pobranecechypodatnik;
    }

    public void setPobranecechypodatnik(List<Cechazapisu> pobranecechypodatnik) {
        this.pobranecechypodatnik = pobranecechypodatnik;
    }

    public boolean isPotraktujjakoNowaTransakcje() {
        return potraktujjakoNowaTransakcje;
    }

    public void setPotraktujjakoNowaTransakcje(boolean potraktujjakoNowaTransakcje) {
        this.potraktujjakoNowaTransakcje = potraktujjakoNowaTransakcje;
    }

//    public static void main(String[] args) {
//        String staranazwa = "EUR";
//        String nazwawaluty = "PLN";
//        double kurs = 4.189;
//        if (!staranazwa.equals("PLN")) {
//            kurs = 1 / kurs * 100000000;
//            kurs = Math.round(kurs);
//            kurs = kurs / 100000000;
//        }
//        double kwota = 100000;
//        kwota = Math.round(kwota * kurs * 10000);
//        kwota = kwota / 10000;
//        System.out.println(kwota);
//        staranazwa = "PLN";
//        nazwawaluty = "EUR";
//        kurs = 4.189;
//        kwota = Math.round(kwota * kurs * 100);
//        kwota = kwota / 100;
//}
    public Dokfk getSelectedimport() {
        return selectedimport;
    }

    public void setSelectedimport(Dokfk selectedimport) {
        this.selectedimport = selectedimport;
    }

    public List<Dokfk> getFilteredValueimport() {
        return filteredValueimport;
    }

    public void setFilteredValueimport(List<Dokfk> filteredValueimport) {
        this.filteredValueimport = filteredValueimport;
    }

    public String getWybranakategoriadokimport() {
        return wybranakategoriadokimport;
    }

    public void setWybranakategoriadokimport(String wybranakategoriadokimport) {
        this.wybranakategoriadokimport = wybranakategoriadokimport;
    }

    public List getDokumentypodatnika() {
        return dokumentypodatnika;
    }

    public void setDokumentypodatnika(List dokumentypodatnika) {
        this.dokumentypodatnika = dokumentypodatnika;
    }

    public Tabelanbp getTabelanbprecznie() {
        return tabelanbprecznie;
    }

    public void setTabelanbprecznie(Tabelanbp tabelanbprecznie) {
        this.tabelanbprecznie = tabelanbprecznie;
    }

    public List<Dokfk> getSelectedlist() {
        return selectedlist;
    }

    public void setSelectedlist(List<Dokfk> selectedlist) {
        this.selectedlist = selectedlist;
    }

    public Integer getLpwierszaRK() {
        return lpwierszaRK;
    }

    public void setLpwierszaRK(Integer lpwierszaRK) {
        this.lpwierszaRK = lpwierszaRK;
    }

    public List getRodzajedokumentowPodatnika() {
        return rodzajedokumentowPodatnika;
    }

    public void setRodzajedokumentowPodatnika(List rodzajedokumentowPodatnika) {
        this.rodzajedokumentowPodatnika = rodzajedokumentowPodatnika;
    }

    public List getCechydokzlisty() {
        return cechydokzlisty;
    }

    public void setCechydokzlisty(List cechydokzlisty) {
        this.cechydokzlisty = cechydokzlisty;
    }

    public int getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(int rodzaj) {
        this.rodzaj = rodzaj;
    }

//</editor-fold>
    public DataModel getDatamodel() {
        DataModel dataModel = new ArrayDataModel<Wiersz>();
        if (selected != null) {
            dataModel.setWrappedData(selected.getListawierszy().toArray());
        }
        return dataModel;
    }

    public void sprawdzmiesiacWpisywanie() {
        if (selected.getMiesiac() != null) {
            String mc = selected.getMiesiac();
            int dl = mc.length();
            if (dl == 1) {
                selected.setMiesiac("0" + mc);
            }
            int mcint = Integer.parseInt(mc);
            if (mcint > 13 || mcint < 1) {
                selected.setMiesiac(wpisView.getMiesiacWpisu());
            }
        }
    }

    public void sprawdzmiesiacWpisywanievat() {
        if (selected.getMiesiac() != null) {
            String mc = selected.getVatM();
            int dl = mc.length();
            if (dl == 1) {
                selected.setVatM("0" + mc);
            }
            int mcint = Integer.parseInt(mc);
            if (mcint > 13 || mcint < 1) {
                selected.setVatM(wpisView.getMiesiacWpisu());
            }
        }
    }

    private List znajdzrodzajedokaktualne(List<Dokfk> wykazZaksiegowanychDokumentow) {
        if (wybranakategoriadok == null || wybranakategoriadok.equals("wszystkie")) {
            List<Rodzajedok> lista =  Collections.synchronizedList(new ArrayList<>());
            wykazZaksiegowanychDokumentow.forEach((p) -> {
                lista.add(p.getRodzajedok());
            });
            List<Rodzajedok> t = new ArrayList<>(new HashSet(lista));
            Collections.sort(t, new Rodzajedokcomparator());
            return t;
        } else {
            return rodzajedokumentowPodatnika;
        }
    }

    private List znajdzcechy(List<Dokfk> wykazZaksiegowanychDokumentow) {
        if (wybranacechadok == null || wybranacechadok.equals("")) {
            List<String> lista =  Collections.synchronizedList(new ArrayList<>());
            wykazZaksiegowanychDokumentow.stream().filter((p) -> (p.getCechadokumentuLista() != null && p.getCechadokumentuLista().size() > 0)).forEachOrdered((p) -> {
                for (Cechazapisu r : p.getCechadokumentuLista()) {
                    lista.add(r.getNazwacechy());
                }
            });
            List<String> t = new ArrayList<>(new HashSet(lista));
            Collections.sort(t);
            return t;
        } else {
            return cechydokzlisty;
        }
    }
    
    
    public void powiekszliste() {
        dataTablezaksiegowane.setStyle("height: 800px;");
    }

    public DataTable getDataTablezaksiegowane() {
        return dataTablezaksiegowane;
    }

    public void setDataTablezaksiegowane(DataTable dataTablezaksiegowane) {
        this.dataTablezaksiegowane = dataTablezaksiegowane;
    }

    public int sortujzaksiegowane(Object obP, Object obW) {
        int ret = 0;
        String dok1 = ((String) obP).split("/")[0];
        String dok2 = ((String) obW).split("/")[0];
        ret = dok1.compareTo(dok2);
        if (ret == 0) {
            ret = porownajdalej((String) obP, (String) obW);
        }
        return ret;
    }

    private int porownajdalej(String obP, String obW) {
        int ret = 0;
        Integer dok1 = Integer.parseInt(obP.split("/")[1]);
        Integer dok2 = Integer.parseInt(obW.split("/")[1]);
        if (dok1 < dok2) {
            ret = -1;
        } else if (dok1 > dok2) {
            ret = 1;
        }
        return ret;
    }

    public String obliczsaldowybranegokonta() {
        String wynik = "";
        double wynikkwota = 0.0;
        if (selectedStronaWiersza != null) {
            Konto k = selectedStronaWiersza.getKonto();
            for (Wiersz p : selected.getListawierszy()) {
                if (p.getStronaWn() != null && p.getStronaWn().getKonto() != null) {
                    if (p.getStronaWn().getKonto().equals(k)) {
                        wynikkwota += p.getStronaWn().getKwota();
                    }
                }
                if (p.getStronaMa() != null && p.getStronaMa().getKonto() != null) {
                    if (p.getStronaMa().getKonto().equals(k)) {
                        wynikkwota -= p.getStronaMa().getKwota();
                    }
                }
            }
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setMinimumFractionDigits(2);
            formatter.setMaximumFractionDigits(2);
            wynik = formatter.format(wynikkwota);
        }
        return wynik;
    }

    public void oznaczdokumentysrodkitrwale() {
        List<Dokfk> oznaczone = Collections.synchronizedList(new ArrayList<>());
        List<Dokfk> l = dokDAOfk.findAll();
        try {
            for (Dokfk p : l) {
                boolean zawierasrodki = false;
                for (Wiersz w : p.getListawierszy()) {
                    if (w.getStronaWn() != null && w.getStronaWn().getKonto() != null && w.getStronaWn().getKonto().getPelnynumer().startsWith("0")) {
                        zawierasrodki = true;
                        break;
                    }
                }
                if (zawierasrodki) {
                    p.setZawierasrodkitrw(true);
                    oznaczone.add(p);
                }
            }
            dokDAOfk.editList(oznaczone);
        } catch (Exception e) {
            E.e(e);
        }
    }

    public void oznaczdokumentyrmk() {
        List<Dokfk> oznaczone = Collections.synchronizedList(new ArrayList<>());
        List<Dokfk> l = dokDAOfk.findAll();
        try {
            for (Dokfk p : l) {
                boolean zawierarmk = false;
                for (Wiersz w : p.getListawierszy()) {
                    if (w.getStronaWn() != null && w.getStronaWn().getKonto() != null && w.getStronaWn().getKonto().getPelnynumer().startsWith("64")) {
                        zawierarmk = true;
                        break;
                    }
                }
                if (zawierarmk) {
                    p.setZawierarmk(true);
                    oznaczone.add(p);
                }
            }
            dokDAOfk.editList(oznaczone);
        } catch (Exception e) {
            E.e(e);
        }
    }

    private void oznaczdokumentSTRMK(Dokfk p, String szukane) {
        boolean zawiera = false;
        for (Wiersz w : p.getListawierszy()) {
            if (w.getStronaWn() != null && w.getStronaWn().getKonto() != null && w.getStronaWn().getKonto().getPelnynumer().startsWith(szukane)) {
                zawiera = true;
                break;
            }
        }
        if (zawiera) {
            if (szukane.equals("0")) {
                p.setZawierasrodkitrw(true);
            } else {
                p.setZawierarmk(true);
            }
        } else if (szukane.equals("0")) {
            p.setZawierasrodkitrw(false);
        } else {
            p.setZawierarmk(false);
        }
    }

    private void oznaczdokumentRozKurs(Dokfk p) {
        boolean zawiera = false;
        for (Wiersz w : p.getListawierszy()) {
            if (w.zawieraRRK()) {
                zawiera = true;
                break;
            }
        }
        if (zawiera) {
            p.setZawierarozkurs(true);
        } else {
            p.setZawierarozkurs(false);
        }

    }

    private void nanieswierszeRRK(Dokfk dokfk) {
        if (dokfk.isZawierarozkurs()) {
            Integer idporzadkowy = dokfk.getListawierszy().size()+1;
            Konto kontoRozniceKursowe = kontoDAOfk.findKonto("755", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
            Konto przychodyfinansowe = kontoDAOfk.findKonto("756", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
            Konto kosztyfinansowe = kontoDAOfk.findKonto("759", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
            List<Transakcja> pobrane = Collections.synchronizedList(new ArrayList<>());
            for (Wiersz p : dokfk.getListawierszy()) {
                if (p.isZawierarozkurs()) {
                    List<Transakcja> t1 = p.getStronaWn().getNowetransakcje();
                    for (Transakcja t1a : t1) {
                        if (t1a.getRoznicekursowe() != 0.0) {
                            pobrane.add(t1a);
                        }
                    }
                }
            }
            if (!pobrane.isEmpty()) {
                for (Transakcja r : pobrane) {
                    DokumentFKBean.naniesPojedynczaTransakcje(idporzadkowy, dokfk, r, tabelanbpDAO, kontoRozniceKursowe, przychodyfinansowe, kosztyfinansowe);
                }
            }
        }
    }

    private void lolo() {

    }

    public void usunspecjalne(Dokfk dokfk, int modyfikator) {
        try {
            if (modyfikator == 1) {
                rozliczsrodkitrw(dokfk);
                dokDAOfk.destroy(dokfk);
            } else {
                rozliczrmk(dokfk);
                dokDAOfk.destroy(dokfk);
            }
            Msg.msg("Usunięto dokument specjalny " + dokfk);
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd, nie usnięto dokumentu");
            E.e(e);
        }
    }

    private void rozliczsrodkitrw(Dokfk dokfk) {
        List<SrodekTrw> srodkidladokumentu = strDAO.findStrPodDokfk(wpisView.getPodatnikWpisu(), dokfk);
        try {
            for (SrodekTrw p : srodkidladokumentu) {
                p.setDokfk(null);
            }
            strDAO.editList(srodkidladokumentu);
        } catch (Exception e) {
            E.e(e);
        }
    }

    private void rozliczrmk(Dokfk dokfk) {
        List<RMK> rmkdladokumentu = rmkDAO.findRMKByPodatnikRokDokfk(wpisView, dokfk);
        try {
            for (RMK p : rmkdladokumentu) {
                p.setDokfk(null);
            }
            rmkDAO.editList(rmkdladokumentu);
        } catch (Exception e) {
            E.e(e);
        }
    }

        
    public void edycjaanalityczne() {
        this.totylkoedycjaanalityczne = true;
    }
    
    public void stronawiersza() {
        try {
            List<Dokfk> listadokumentow = dokDAOfk.findAll();
            java.util.concurrent.ExecutorService executorService = Executors.newFixedThreadPool(100);
            for (Dokfk p : listadokumentow) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        for (StronaWiersza r : p.getStronyWierszy()) {
                        }
                    }
                });
            }
            //executorService.shutdown();
//            final boolean done = executorService.awaitTermination(1, TimeUnit.MINUTES);
//            Logger logger = Logger.getLogger("com.foo");
//            logger.log(Level.INFO,"All e-mails were sent so far? "+done);
            //dokDAOfk.editList(listadokumentow);
            Msg.dP();
        } catch (Exception ex) {
            Logger.getLogger(DokfkView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void dodajewidencjenaprawa() {
        
    }

    public void kopiujdokument() {
        if (selectedlist == null || selectedlist.size() == 0) {
            Msg.msg("e", "Nie wybrano dokuemntu do kopiowania");
        } else {
            selected = serialclone.SerialClone.clone(selectedlist.get(0));
            wygenerujnumerkolejny();
            for (StronaWiersza p : selected.getStronyWierszy()) {
                p.setKwota(0);
                p.setKwotaPLN(0);
                p.setTypStronaWiersza(0);
            }
        }
    }
    
    public void resetrmk() {
        this.pokazrmk = false;
    }
    
    public void resetst() {
        this.pokazsrodkitrwale = false;
    }

    public double[] getSumadokbo() {
        return sumadokbo;
    }

    public void setSumadokbo(double[] sumadokbo) {
        this.sumadokbo = sumadokbo;
    }

    public GUSView getgUSView() {
        return gUSView;
    }

    public void setgUSView(GUSView gUSView) {
        this.gUSView = gUSView;
    }

    public Cechazapisu getCechazapisudododania() {
        return cechazapisudododania;
    }

    public void setCechazapisudododania(Cechazapisu cechazapisudododania) {
        this.cechazapisudododania = cechazapisudododania;
    }

    public void wybierzcechankup() {
        if (pobranecechypodatnik != null) {
            for (Cechazapisu p : pobranecechypodatnik) {
                if (p.getNazwacechy().equals("NKUP")) {
                    cechazapisudododania = p;
                }
            }
        }
    }
//nie wiem czy to nie jest zbedne
//    private void usunrozrachunki(int liczbawierszyWDokumencie) {
//        List<StronaWiersza> strony = Collections.synchronizedList(new ArrayList<>());
//        int rowid = liczbawierszyWDokumencie-1;
//        Wiersz w = selected.getListawierszy().get(rowid);
//        for (StronaWiersza sw : w.getStronyWiersza()) {
//            if (sw.getNowetransakcje()!=null) {
//                for (Transakcja t : sw.getNowetransakcje()) {
//                    StronaWiersza drugastrona = t.getNowaTransakcja();
//                    for (Iterator<Transakcja> itt1 = drugastrona.getPlatnosci().iterator();itt1.hasNext();) {
//                        if (itt1.next().getRozliczajacy().equals(sw)) {
//                            itt1.remove();
//                            if (drugastrona.getId()!=null) {
//                                strony.add(drugastrona);
//                            }
//                        }
//                    }
//                }
//            }
//            if (sw.getPlatnosci()!=null) {
//                for (Transakcja t : sw.getPlatnosci()) {
//                    StronaWiersza drugastrona = t.getRozliczajacy();
//                    for (Iterator<Transakcja> itt1 = drugastrona.getNowetransakcje().iterator();itt1.hasNext();) {
//                        if (itt1.next().getNowaTransakcja().equals(sw)) {
//                            itt1.remove();
//                            if (drugastrona.getId()!=null) {
//                                strony.add(drugastrona);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        if (strony.size()>0) {
//           stronaWierszaDAO.editList(strony);
//        }
//    }
//
//    private void usunrozrachunki(Wiersz w) {
//        List<StronaWiersza> strony = Collections.synchronizedList(new ArrayList<>());
//        for (StronaWiersza sw : w.getStronyWiersza()) {
//            if (sw.getNowetransakcje()!=null) {
//                for (Transakcja t : sw.getNowetransakcje()) {
//                    StronaWiersza drugastrona = t.getNowaTransakcja();
//                    for (Iterator<Transakcja> itt1 = drugastrona.getPlatnosci().iterator();itt1.hasNext();) {
//                        if (itt1.next().getRozliczajacy().equals(sw)) {
//                            itt1.remove();
//                            if (drugastrona.getId()!=null) {
//                                strony.add(drugastrona);
//                            }
//                        }
//                    }
//                }
//            }
//            if (sw.getPlatnosci()!=null) {
//                for (Transakcja t : sw.getPlatnosci()) {
//                    StronaWiersza drugastrona = t.getRozliczajacy();
//                    for (Iterator<Transakcja> itt1 = drugastrona.getNowetransakcje().iterator();itt1.hasNext();) {
//                        if (itt1.next().getNowaTransakcja().equals(sw)) {
//                            itt1.remove();
//                            if (drugastrona.getId()!=null) {
//                                strony.add(drugastrona);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        if (strony.size()>0) {
//           stronaWierszaDAO.editList(strony);
//        }
//    }

    public KlienciConverterView getKlienciConverterView() {
        return klienciConverterView;
    }

    public void setKlienciConverterView(KlienciConverterView klienciConverterView) {
        this.klienciConverterView = klienciConverterView;
    }

    
    


      

    

    
    
    
}
