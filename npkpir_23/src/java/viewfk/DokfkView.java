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
import beansFK.DokFKWalutyBean;
import beansFK.StronaWierszaBean;
import beansFK.TabelaNBPBean;
import beansPdf.PdfDokfk;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import comparator.DokfkLPcomparator;
import comparator.Dokfkcomparator;
import comparator.Transakcjacomparator;
import dao.EvewidencjaDAO;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import dao.StronaWierszaDAO;
import daoFK.CechazapisuDAOfk;
import daoFK.DokDAOfk;
import daoFK.EVatwpisFKDAO;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import daoFK.TransakcjaDAO;
import daoFK.WalutyDAOfk;
import daoFK.WierszBODAO;
import data.Data;
import embeddable.Kwartaly;
import embeddable.Mce;
import embeddable.Parametr;
import entity.Evewidencja;
import entity.Klienci;
import entity.Rodzajedok;
import entity.Uz;
import entityfk.Cechazapisu;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Transakcja;
import entityfk.Waluty;
import entityfk.Wiersz;
import error.E;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import msg.Msg;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import org.primefaces.extensions.component.inputnumber.InputNumber;
import params.Params;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentu;
import static pdffk.PdfMain.inicjacjaA4Portrait;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;
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
    private boolean zablokujprzyciskrezygnuj;
    private boolean pokazPanelWalutowy;
    private boolean pokazRzadWalutowy;
    //waltuty
    //waluta wybrana przez uzytkownika
    @Inject
    private WalutyDAOfk walutyDAOfk;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    private String wybranawaluta;
    private String symbolwalutydowiersza;
    private List<Waluty> wprowadzonesymbolewalut;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
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
    private List<Cechazapisu> pobranecechy;
    private StronaWiersza stronaWierszaCechy;
    private List<Dokfk> filteredValue;
    private List<Dokfk> filteredValueimport;
    private String wybranakategoriadok;
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
    private String wierszedytowany;
    private List dokumentypodatnika;
    private double saldoBO;
    private int jest1niema0_konto;
    private String komunikatywpisdok;
    private Integer lpwierszaRK;
    private Klienci klientdlaPK;
    private String miesiacDlaZestawieniaZaksiegowanych;


    public DokfkView() {
        this.wykazZaksiegowanychDokumentow = new ArrayList<>();
        this.biezacetransakcje = new ArrayList<>();
        this.transakcjejakosparowany = new ArrayList<>();
        this.zablokujprzyciskzapisz = false;
        this.wprowadzonesymbolewalut = new ArrayList<>();
        this.symbolwalutydowiersza = "";
        this.zapisz0edytuj1 = false;
        this.listaewidencjivatRK = new ArrayList<>();
        this.pobranecechy = new ArrayList<>();
        this.dokumentypodatnika = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        try {
            //resetujDokument(); //to jest chyba niepotrzebne bo ta funkcja jest wywolywana jak otwieram okienko wpisu i potem po kazdym zachowaniu
            //obsluzcechydokumentu();
            stworzlisteewidencjiRK();
            RequestContext.getCurrentInstance().update("ewidencjavatRK");
            dokumentypodatnika = rodzajedokDAO.findListaPodatnik(wpisView.getPodatnikObiekt());
            wprowadzonesymbolewalut.addAll(walutyDAOfk.findAll());
            klientdlaPK = klDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
            if (klientdlaPK == null) {
                klientdlaPK = new Klienci("222222222222222222222", "BRAK FIRMY JAKO KONTRAHENTA!!!");
            }
        } catch (Exception e) {
            E.e(e);
        }
    }

//    //<editor-fold defaultstate="collapsed" desc="schowane podstawowe funkcje jak dodaj usun itp">
//
//    //********************************************funkcje dla ksiegowania dokumentow
//    //RESETUJ DOKUMNETFK
    public void resetujDokument() {
        //pobieram dane ze starego dokumentu, jeżeli jest
        String symbolPoprzedniegoDokumentu = null;
        Rodzajedok rodzajDokPoprzedni = null;
        Klienci ostatniklient = null;
        komunikatywpisdok = null;
        if (selected != null) {
            symbolPoprzedniegoDokumentu = selected.pobierzSymbolPoprzedniegoDokfk();
            rodzajDokPoprzedni = selected.getRodzajedok();
            selected.setwTrakcieEdycji(false);
            ostatniklient = selected.getKontr();
            RequestContext.getCurrentInstance().update("zestawieniedokumentow:dataList");
            RequestContext.getCurrentInstance().update("zestawieniedokumentowimport:dataListImport");
        }
        try {
            if (ostatniklient == null) {
                ostatniklient = klDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
                if (ostatniklient == null) {
                    ostatniklient = new Klienci("222222222222222222222", "BRAK FIRMY JAKO KONTRAHENTA!!!");
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
        //tworze nowy dokument
        selected = new Dokfk(symbolPoprzedniegoDokumentu, rodzajDokPoprzedni, wpisView, ostatniklient);
        selected.setWprowadzil(wpisView.getWprowadzil().getLogin());
        wygenerujnumerkolejny();
        try {
            DokFKBean.dodajWaluteDomyslnaDoDokumentu(walutyDAOfk, tabelanbpDAO, selected);
            resetprzyciskow();
        } catch (Exception e) {
            E.e(e);
        }
        obsluzcechydokumentu();
        rodzajBiezacegoDokumentu = 1;
        RequestContext.getCurrentInstance().update("formwpisdokument");
        RequestContext.getCurrentInstance().update("wpisywaniefooter");
        RequestContext.getCurrentInstance().execute("$(document.getElementById('formwpisdokument:data2DialogWpisywanie')).select();");
    }

    private void resetprzyciskow() {
        pokazPanelWalutowy = false;
        pokazRzadWalutowy = false;
        biezacetransakcje = null;
        zapisz0edytuj1 = false;
        zablokujprzyciskrezygnuj = false;
        wlaczZapiszButon = true;
        niedodawajkontapole = false;
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
//    public void dodajNowyWierszStronaWnPiatka(Wiersz wiersz) {
//        int indexwTabeli = wiersz.getIdporzadkowy() - 1;
//        if (wiersz.getStronaWn().getKonto().getPelnynumer().startsWith("4") && wiersz.getPiatki().size() == 0) {
//            ObslugaWiersza.dolaczNowyWierszPiatka(indexwTabeli, true, selected, kontoDAOfk, wpisView);
//            //RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
//            return;
//        }
//        if (wiersz.getTypWiersza() != 0) {
//            int licznbawierszy = selected.getListawierszy().size();
//            if (licznbawierszy > 1) {
//                if (wiersz.getTypWiersza() == 5 || wiersz.getTypWiersza() == 6 || wiersz.getTypWiersza() == 7) {
//                    ObslugaWiersza.dolaczNowyWierszPiatka(indexwTabeli, true, selected, kontoDAOfk, wpisView);
//                }
//            }
//        }
//    } 
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
            } catch (Exception e1) {
                E.e(e1);
            }
            if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
                rozliczsaldoWBRK(Integer.parseInt(indexwiersza));
            }
        }
    }

    public void dodajPustyWierszNaKoncu() {
        int dlugosc = selected.getListawierszy().size();

        int wynik = DialogWpisywanie.dodajPustyWierszNaKoncu(selected);
        selected.przeliczKwotyWierszaDoSumyDokumentu();
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
    public void podepnijEwidencjeVat(int rodzaj) {
        boolean nievatowiec = wpisView.getRodzajopodatkowania().contains("bez VAT");
        if (rodzajBiezacegoDokumentu != 0 && rodzajBiezacegoDokumentu != 5 && !nievatowiec) {
            if (selected.iswTrakcieEdycji() == false) {
                if (rodzaj == 0) {
                    this.selected.setEwidencjaVAT(new ArrayList<EVatwpisFK>());
                }
                symbolWalutyNettoVat = " " + selected.getTabelanbp().getWaluta().getSkrotsymbolu();
                /*wyswietlamy ewidencje VAT*/
                List<String> opisewidencji = new ArrayList<>();
                opisewidencji.addAll(listaEwidencjiVat.pobierzOpisyEwidencji(selected.getRodzajedok().getRodzajtransakcji()));
                int k = 0;
                if (rodzaj == 1) {
                    k = this.selected.getEwidencjaVAT().size();
                }
                for (String p : opisewidencji) {
                    EVatwpisFK eVatwpisFK = new EVatwpisFK();
                    eVatwpisFK.setLp(k++);
                    eVatwpisFK.setEwidencja(evewidencjaDAO.znajdzponazwie(p));
                    eVatwpisFK.setNetto(0.0);
                    eVatwpisFK.setVat(0.0);
                    eVatwpisFK.setDokfk(selected);
                    eVatwpisFK.setEstawka("op");
                    eVatwpisFK.setMcEw(selected.getVatM());
                    eVatwpisFK.setRokEw(selected.getVatR());
                    eVatwpisFK.setInnyokres(0);
                    this.selected.getEwidencjaVAT().add(eVatwpisFK);
                }
                RequestContext.getCurrentInstance().update("formwpisdokument:panelzewidencjavat");
            }
        }
    }

    public void usunEwidencjeDodatkowa(EVatwpisFK eVatwpisFK) {
        if (eVatwpisFK.getLp() != 0) {
            for (Iterator<EVatwpisFK> it = selected.getEwidencjaVAT().iterator(); it.hasNext();) {
                EVatwpisFK p = (EVatwpisFK) it.next();
                if (p.getLp() == eVatwpisFK.getLp()) {
                    it.remove();
                }
            }
        }
    }

    private void stworzlisteewidencjiRK() {
        List<String> nazwyewidencji = new ArrayList<>();
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
        //Msg.msg("dolaczWierszeZKwotami");
        Rodzajedok rodzajdok = selected.getRodzajedok();
        double[] wartosciVAT = DokFKVATBean.podsumujwartosciVAT(selected.getEwidencjaVAT());
        if (selected.getListawierszy().size() == 1 && selected.isImportowany() == false) {
            if (rodzajdok.getKategoriadokumentu() == 1) {
                if (selected.getRodzajedok().getProcentvat() != 0.0 && evatwpis.getEwidencja().getTypewidencji().equals("sz")) {
                    //oblicza polowe vat dla faktur samochody osobowe
                    evatwpis.setVat(wartosciVAT[4]);
                    evatwpis.setBrutto(Z.z(evatwpis.getNetto() + evatwpis.getVat()));
                    RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:" + evatwpis.getLp() + ":vat");
                    RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:" + evatwpis.getLp() + ":brutto");
                } else if (selected.getRodzajedok().getProcentvat() != 0.0 && evatwpis.getEwidencja().getTypewidencji().equals("z")) {
                    evatwpis.setVat(Z.z((evatwpis.getNetto() * 0.23) * selected.getRodzajedok().getProcentvat() / 100));
                    evatwpis.setBrutto(Z.z(evatwpis.getNetto() + evatwpis.getVat()));
                    RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:" + evatwpis.getLp() + ":vat");
                    RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:" + evatwpis.getLp() + ":brutto");
                }
                DokFKVATBean.rozliczVatKoszt(evatwpis, wartosciVAT, selected, kliencifkDAO, kontoDAOfk, wpisView, dokDAOfk);
            } else if (selected.getListawierszy().get(0).getStronaWn().getKonto() == null && rodzajdok.getKategoriadokumentu() == 2) {
                DokFKVATBean.rozliczVatPrzychod(evatwpis, wartosciVAT, selected, kliencifkDAO, kontoDAOfk, wpisView, dokDAOfk);
            }
        } else if (selected.getListawierszy().size() > 1 && rodzajdok.getKategoriadokumentu() == 1) {
            DokFKVATBean.rozliczVatKosztEdycja(evatwpis, wartosciVAT, selected, wpisView);
        } else if (selected.getListawierszy().size() > 1 && rodzajdok.getKategoriadokumentu() == 2) {
            DokFKVATBean.rozliczVatPrzychodEdycja(evatwpis, wartosciVAT, selected, wpisView);
        }
        selected.przeliczKwotyWierszaDoSumyDokumentu();
        RequestContext.getCurrentInstance().update("formwpisdokument:panelwpisbutton");
    }

    public void dolaczWierszZKwotamiRK() {
        if (!selected.getEwidencjaVAT().contains(ewidencjaVatRK)) {
            selected.getEwidencjaVAT().add(ewidencjaVatRK);
        }
        String dzien = ewidencjaVatRK.getDatadokumentu().split("-")[2];
        wierszRK.setDataWalutyWiersza(dzien);
        EVatwpisFK evatwpis = ewidencjaVatRK;
        Wiersz w = evatwpis.getWiersz();
        double[] wartosciVAT = DokFKVATBean.podsumujwartosciVATRK(ewidencjaVatRK);
        List<Wiersz> dodanewiersze = null;
        if (ewidencjaVatRK.getEwidencja().getNazwa().equals("zakup")) {
            dodanewiersze = DokFKVATBean.rozliczVatKosztRK(evatwpis, wartosciVAT, selected, wpisView, wierszRKindex, kontoDAOfk);
        } else if (!ewidencjaVatRK.getEwidencja().getNazwa().equals("zakup")) {
            dodanewiersze = DokFKVATBean.rozliczVatPrzychodRK(evatwpis, wartosciVAT, selected, wpisView, wierszRKindex, kontoDAOfk);
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
            double[] wartosciVAT = DokFKVATBean.podsumujwartosciVATRK(ewidencjaVatRK);
            List<Wiersz> dodanewiersze = null;
            if (ewidencjaVatRK.getEwidencja().getNazwa().equals("zakup")) {
                dodanewiersze = DokFKVATBean.rozliczEdytujVatKosztRK(e, wartosciVAT, selected, wpisView, wierszRKindex, kontoDAOfk);
            } else if (!ewidencjaVatRK.getEwidencja().getNazwa().equals("zakup")) {
                dodanewiersze = DokFKVATBean.rozliczEdytujVatPrzychodRK(e, wartosciVAT, selected, wpisView, wierszRKindex, kontoDAOfk);
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
        DokFKVATBean.ustawvat(evatwpis, selected);
        evatwpis.setBrutto(Z.z(evatwpis.getNetto() + evatwpis.getVat()));
        int lp = evatwpis.getLp();
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
            double kurs = selected.getTabelanbp().getKurssredni();
            evatwpis.setVatwwalucie(Z.z(evatwpis.getVat() / kurs));
        }
        evatwpis.setBrutto(Z.z(evatwpis.getNetto() + evatwpis.getVat()));
        String update = form + ":tablicavat:" + lp + ":brutto";
        RequestContext.getCurrentInstance().update(update);
        String activate = "document.getElementById('" + form + ":tablicavat:" + lp + ":brutto_input').select();";
        RequestContext.getCurrentInstance().execute(activate);
    }

    public void updatenettoRK() {
        EVatwpisFK evatwpis = ewidencjaVatRK;
        double stawkavat = 0.23;
        DokFKVATBean.ustawvat(evatwpis, selected, stawkavat);
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
        double kurs = selected.getTabelanbp().getKurssredni();
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
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList:" + index + ":saldo");
                selected.setSaldokoncowe(selected.getListawierszy().get(selected.getListawierszy().size() - 1).getSaldoWBRK());

            }
            try {
                selected.setLp(selected.getDokfkPK().getNrkolejnywserii());
                selected.getDokfkPK().setPodatnik(wpisView.getPodatnikWpisu());
                UzupelnijWierszeoDane.uzupelnijWierszeoDate(selected);
                //nanosimy zapisy na kontach
                if (selected.sprawdzczynaniesionorozrachunki() == 1) {
                    komunikatywpisdok = "Brak numeru własnego dokumentu. Nie można zapisać dokumentu.";
                    RequestContext.getCurrentInstance().update("formwpisdokument:komunikatywpisdok");
                }
                for (Wiersz p : selected.getListawierszy()) {
                    przepiszWalutyZapisEdycja(p);
                }
                selected.oznaczewidencjeVAT();
                selected.przeliczKwotyWierszaDoSumyDokumentu();
                if ((selected.getRodzajedok().getKategoriadokumentu() == 0 || selected.getRodzajedok().getKategoriadokumentu() == 5) && klientdlaPK != null) {
                    selected.setKontr(klientdlaPK);
                }
                dokDAOfk.edit(selected);
                biezacetransakcje = null;
                Dokfk dodany = dokDAOfk.findDokfkObj(selected);
                wykazZaksiegowanychDokumentow.add(dodany);
                resetujDokument();
                Msg.msg("i", "Dokument dodany");
                RequestContext.getCurrentInstance().update("wpisywaniefooter");
                RequestContext.getCurrentInstance().update("rozrachunki");
                RequestContext.getCurrentInstance().update("formwpisdokument");
            } catch (Exception e) {
                E.e(e);
                komunikatywpisdok = "Brak numeru własnego dokumentu. Nie można zapisać dokumentu.";
                RequestContext.getCurrentInstance().update("formwpisdokument:komunikatywpisdok");
                Msg.msg("e", "Nie udało się dodac dokumentu " + e.getMessage());
                RequestContext.getCurrentInstance().execute("powrotdopolaPoNaniesieniuRozrachunkow();");
            }
        } else {
            komunikatywpisdok = "Uzupełnij wiersze o kwoty/konto!";
            RequestContext.getCurrentInstance().update("formwpisdokument:komunikatywpisdok");
            Msg.msg("w", "Uzupełnij wiersze o kwoty/konto!");
        }

    }

    public void przepiszWaluty(Wiersz wiersz) {
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
            } else {
                if (wiersz.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
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
            } else {
                if (wiersz.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
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
            try {
                UzupelnijWierszeoDane.uzupelnijWierszeoDate(selected);
                if (selected.getDokfkPK().getSeriadokfk().equals("BO")) {
                    selected.przepiszWierszeBO();
                }
                selected.przeliczKwotyWierszaDoSumyDokumentu();
                selected.setwTrakcieEdycji(false);
                selected.setImportowany(false);
                for (Wiersz p : selected.getListawierszy()) {
                    przepiszWalutyZapisEdycja(p);
                }
                ObslugaWiersza.przenumerujSelected(selected);
                selected.oznaczewidencjeVAT();
                dokDAOfk.edit(selected);
                wykazZaksiegowanychDokumentow.remove(selected);
                wykazZaksiegowanychDokumentow.add(selected);
                Collections.sort(wykazZaksiegowanychDokumentow, new Dokfkcomparator());
                selected = new Dokfk();
                RequestContext.getCurrentInstance().update("zestawieniedokumentow:dataList");
                RequestContext.getCurrentInstance().update("zestawieniezapisownakontach:dataList");
                Msg.msg("i", "Pomyślnie zaktualizowano dokument");
                RequestContext.getCurrentInstance().execute("PF('wpisywanie').hide();");
            } catch (Exception e) {
                E.e(e);
                komunikatywpisdok = "Nie udało się zmienic dokumentu ";
                RequestContext.getCurrentInstance().update("formwpisdokument:komunikatywpisdok");
                Msg.msg("e", "Nie udało się zmenic dokumentu " + e.toString());
            }
        } else {
            komunikatywpisdok = "Uzupełnij wiersze o kwoty/konto!";
            RequestContext.getCurrentInstance().update("formwpisdokument:komunikatywpisdok");
        }
    }

    public void edycjaDlaRozrachunkow() {
        System.out.println("edycjaDlaRozrachunkow");
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
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie udało się usunąć dokumentu. Czy nie jest to dokument środka trwałego lub RMK?");
        }
    }

    //usuwa wiersze z dokumentu
    public void usunWierszWDokumencie() {
        try {
            int liczbawierszyWDokumencie = selected.getListawierszy().size();
            if (liczbawierszyWDokumencie > 1) {
                Wiersz wierszDoUsuniecia = selected.getListawierszy().get(liczbawierszyWDokumencie - 1);
                if (wierszDoUsuniecia.getTypWiersza() == 5) {
                    Msg.msg("e", "Usuń najpierw wiersz z 4.");
                } else {
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
                Wiersz wiersz = selected.getListawierszy().get(0);
                try {
                    if (wiersz.getIdporzadkowy() != null) {
                        selected.setListawierszy(new ArrayList<Wiersz>());
                        liczbawierszyWDokumencie--;
                    } else {
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
            Msg.msg("Błąd podczas usuwania wiersz");
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

    public void skopiujopisdopierwszegowiersza() {
        try {
            Wiersz w = selected.getListawierszy().get(0);
            if (w.getOpisWiersza() == null) {
                w.setOpisWiersza(selected.getOpisdokfk());
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
        Dokfk poprzedniDokument = dokDAOfk.findDokfkLastofaTypeKontrahent(wpisView.getPodatnikObiekt().getNip(), selected.getRodzajedok().getSkrot(), selected.getKontr(), wpisView.getRokWpisuSt());
        if (poprzedniDokument != null && selected.getOpisdokfk() == null) {
            selected.setOpisdokfk(poprzedniDokument.getOpisdokfk());
        }
    }

    public void pobierzopiszpoprzedniegodokItemSelect() {
        Dokfk poprzedniDokument = dokDAOfk.findDokfkLastofaTypeKontrahent(wpisView.getPodatnikObiekt().getNip(), selected.getRodzajedok().getSkrot(), selected.getKontr(), wpisView.getRokWpisuSt());
        if (poprzedniDokument != null) {
            selected.setOpisdokfk(poprzedniDokument.getOpisdokfk());
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
                if (!rok.equals(selected.getDokfkPK().getRok())) {
                    selected.getDokfkPK().setRok(rok);
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
        }
    }

    public void skorygujokreswpisudokumentu(ValueChangeEvent event) {
        if (selected.getRodzajedok().getKategoriadokumentu() == 1) {
            //generuje okres wpisu tylko jezeli jest w trybie wpisu, a wiec zapisz0edytuj1 jest false
            if (zapisz0edytuj1 == false) {
                String data = selected.getDatawplywu();
                if (data.length() == 10) {
                    String rok = data.split("-")[0];
                    selected.getDokfkPK().setRok(rok);
                    String mc = data.split("-")[1];
                    selected.setVatM(mc);
                }
            }
        }
    }

    public void przygotujDokumentWpisywanie() {
        String skrotnazwydokumentu = selected.getRodzajedok().getRodzajedokPK().getSkrotNazwyDok();
        selected.getDokfkPK().setSeriadokfk(skrotnazwydokumentu);
        //pokazuje daty w wierszach
        if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
            pokazPanelWalutowy = true;
        } else {
            pokazPanelWalutowy = false;
        }
        rodzajBiezacegoDokumentu = selected.getRodzajedok().getKategoriadokumentu();
        try {
            if (rodzajBiezacegoDokumentu != 1 && rodzajBiezacegoDokumentu != 2) {
                Klienci k = klienciDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
                selected.setKontr(k);
                if (k==null) {
                    selected.setKontr(new Klienci("222222222222222222222", "BRAK FIRMY JAKO KONTRAHENTA!!!"));
                }
            }
        } catch (Exception e) {
            E.e(e);

        }
    }

    public void przygotujDokumentEdycja() {
        selected.setwTrakcieEdycji(true);
        obsluzcechydokumentu();
        RequestContext.getCurrentInstance().update("zestawieniedokumentow:dataList");
        RequestContext.getCurrentInstance().update("wpisywaniefooter");
        try {
            Msg.msg("i", "Wybrano dokument do edycji " + selected.getDokfkPK().toString());
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
                    System.out.println("Jest to wiersz zawierający kwotę rozliczona w dalszych wierszach. Nie można go usunąć");
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
                    selected.getListawierszy().remove(wybranyWiersz);
                    ObslugaWiersza.przenumerujSelected(selected);
                    break;
                default:
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

    public void przygotujDokumentEdycja(Dokfk wybranyDokfk, Integer row) {
        try {
            if (wybranyDokfk.iswTrakcieEdycji() == true) {
                wybranyDokfk.setwTrakcieEdycji(true);
                Msg.msg("e", "Dokument został otwarty do edycji przez inną osobę. Nie można go wyedytować");
            } else {
                selected = dokDAOfk.findDokfkObj(wybranyDokfk);
                //selected.setwTrakcieEdycji(true);
                //dokDAOfk.edit(selected);
                wybranaTabelanbp = selected.getTabelanbp();
                tabelenbp = new ArrayList<>();
                tabelenbp.add(wybranaTabelanbp);
                obsluzcechydokumentu();
                Msg.msg("i", "Wybrano dokument do edycji " + wybranyDokfk.getDokfkPK().toString());
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
//                if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
//                    saldoinnedok = obliczsaldopoczatkowe();
//                    selected.setSaldopoczatkowe(Z.z(saldoinnedok));
//                    saldoBO = pobierzwartosczBO(selected.getRodzajedok().getKontorozrachunkowe());
//                    Konto kontorozrachunkowe = selected.getRodzajedok().getKontorozrachunkowe();
//                    DialogWpisywanie.rozliczsalda(selected, saldoBO, saldoinnedok, kontorozrachunkowe);
//                    System.out.println("Udane obliczenie salda");
//                }
            }
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
            if (odnalezionywbazie.iswTrakcieEdycji() == true) {
                wybranyDokfk.setwTrakcieEdycji(true);
                Msg.msg("e", "Dokument został otwarty do edycji przez inną osobę. Nie można go wyedytować");
                RequestContext.getCurrentInstance().update(wierszedytowany);
            } else {
                selected = wybranyDokfk;
                selected.setwTrakcieEdycji(true);
                wybranaTabelanbp = selected.getTabelanbp();
                tabelenbp = new ArrayList<>();
                tabelenbp.add(wybranaTabelanbp);
                obsluzcechydokumentu();
                RequestContext.getCurrentInstance().update(wierszedytowany);
                Msg.msg("i", "Wybrano dokument do edycji " + wybranyDokfk.getDokfkPK().toString());
                zapisz0edytuj1 = true;
                if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
                    pokazPanelWalutowy = true;
                } else {
                    pokazPanelWalutowy = false;
                }
                rodzajBiezacegoDokumentu = selected.getRodzajedok().getKategoriadokumentu();
            }
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
    
    public void znajdzDokumentOznaczWierszDoPodswietlenia(List<StronaWiersza> wybranekontadosumowania) {
        if (wybranekontadosumowania != null && wybranekontadosumowania.size() > 0) {
            StronaWiersza s = wybranekontadosumowania.get(0);
            Wiersz w = s.getWiersz();
            Dokfk odnalezionywbazie = dokDAOfk.findDokfkObj(w.getDokfk());
            selected = odnalezionywbazie;
            int numer = w.getIdporzadkowy() - 1;
            wierszDoPodswietlenia = numer;
            setZapisz0edytuj1(true);
        }
    }

//
//    //on robi nie tylko to ze przywraca button, on jeszcze resetuje selected
//    //dodalem to tutaj a nie przy funkcji edytuj bo wtedy nie wyswietlalo wiadomosci o edycji
    public void przywrocwpisbutton() {
        setZapisz0edytuj1(false);
        resetujDokument();
        RequestContext.getCurrentInstance().execute("PF('wpisywanie').hide();");
    }

//    //</editor-fold>
    public void sprawdzWnMawDokfk() {
        List<Dokfk> listaRozniceWnMa = new ArrayList<>();
        List<Dokfk> listabrakiKontaAnalityczne = new ArrayList<>();
        List<Dokfk> listabraki = new ArrayList<>();
        List<Dokfk> listabrakiPozycji = new ArrayList<>();
        List<Dokfk> listabrakivat = new ArrayList<>();
        List<Dokfk> listapustaewidencja = new ArrayList<>();
        for (Dokfk p : wykazZaksiegowanychDokumentow) {
            if ((p.getRodzajedok().getKategoriadokumentu() != 1 && p.getRodzajedok().getKategoriadokumentu() != 2) && klientdlaPK != null) {
                if (p.getKontr() == null) {
                    p.setKontr(klientdlaPK);
                    dokDAOfk.edit(p);
                } else if (!p.getKontr().equals(klientdlaPK)) {
                    p.setKontr(klientdlaPK);
                    dokDAOfk.edit(p);
                }
            }
            String  kontown = null;
            String kontoma = null;
            if (p.getListawierszy().size()>1) {
                for (Wiersz w : p.getListawierszy()) {
                    StronaWiersza swwn = w.getStronaWn();
                    if (swwn != null && (swwn.getKonto().getPelnynumer().equals("221-3") || swwn.getKonto().getPelnynumer().equals("221-4"))) {
                        kontown = swwn.getKonto().getPelnynumer();
                    }
                    StronaWiersza swma = w.getStronaMa();
                    if (swma != null && (swma.getKonto().getPelnynumer().equals("221-1") || swma.getKonto().getPelnynumer().equals("221-2"))) {
                        kontoma = swma.getKonto().getPelnynumer();
                    }
                }
            }
            double netto = 0.0;
            double vat = 0.0;
            for (EVatwpisFK ew : p.getEwidencjaVAT()) {
                netto += ew.getNetto();
                vat += ew.getNetto();
            }
            netto = Z.z(netto);
            vat = Z.z(vat);
            if (netto == 0.0 && vat == 0.0 && p.getEwidencjaVAT().size() > 0) {
                listapustaewidencja.add(p);
                if (p.getRodzajedok().getKategoriadokumentu() != 1 && p.getRodzajedok().getKategoriadokumentu() != 1) {
                    p.setEwidencjaVAT(new ArrayList<EVatwpisFK>());
                    dokDAOfk.edit(p);
                }
            }
            for (EVatwpisFK ew : p.getEwidencjaVAT()) {
                if (ew.getMcEw() == null) {
                    if (ew.getInnyokres() == 0) {
                        ew.setMcEw(p.getMiesiac());
                        ew.setRokEw(p.getDokfkPK().getRok());
                    } else {
                        String[] nowyokres = Mce.zwiekszmiesiac(p.getDokfkPK().getRok(), p.getMiesiac(), ew.getInnyokres());
                        ew.setRokEw(nowyokres[0]);
                        ew.setMcEw(nowyokres[1]);
                    }
                    dokDAOfk.edit(p);
                } else {
                    if (p.getListawierszy().size() > 1 && (p.getRodzajedok().getKategoriadokumentu() == 1 || p.getRodzajedok().getKategoriadokumentu() == 2)) {
                        if (ew.getInnyokres() == 0) {
                            System.out.println("dok " + p.getDokfkSN());
                            if (ew.getEwidencja().getTypewidencji().equals("z") && kontown != null && !kontown.equals("221-3")) {
                                listabrakivat.add(p);
                            }
                            if (ew.getEwidencja().getTypewidencji().equals("s") && kontoma != null && !kontoma.equals("221-1")) {
                                listabrakivat.add(p);
                            }
                            if (ew.getEwidencja().getTypewidencji().equals("sz") && kontown != null && kontoma != null && !kontown.equals("221-3") && !kontoma.equals("221-1")) {
                                listabrakivat.add(p);
                            }
                        } else {
                            if (ew.getEwidencja().getTypewidencji().equals("z") && kontown != null && !kontown.equals("221-4")) {
                                listabrakivat.add(p);
                            }
                            if (ew.getEwidencja().getTypewidencji().equals("s") && kontoma != null && !kontoma.equals("221-2")) {
                                listabrakivat.add(p);
                            }
                            if (ew.getEwidencja().getTypewidencji().equals("sz") && kontown != null && kontoma != null && !kontown.equals("221-4") && !kontoma.equals("221-2")) {
                                listabrakivat.add(p);
                            }
                        }
                    }
                }
            }
            double sumawn = 0.0;
            double sumama = 0.0;
            boolean jestkontonieostatnieWn = false;
            boolean jestkontonieostatnieMa = false;
            boolean brakwpln = false;
            boolean brakPozycji = false;
            int liczbawierszy = p.getListawierszy().size();
            if (!p.getDokfkPK().getSeriadokfk().equals("BO")) {
                for (Wiersz r : p.getListawierszy()) {
                    StronaWiersza wn = r.getStronaWn();
                    StronaWiersza ma = r.getStronaMa();
                    if (wn != null) {
                        if (wn.getKonto().getPelnynumer().equals("402-1")) {
                            System.out.println("d");
                        }
                        if (wn.getKonto().getKontopozycjaID() == null) {
                            brakPozycji = true;
                        }
                        jestkontonieostatnieWn = wn.getKonto().isMapotomkow();
                        if (wn.getKwota() > 0 && wn.getKwotaPLN() == 0) {
                            brakwpln = true;
                        }
                        if (r.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
                            if (Z.z(wn.getKwota()) != Z.z(wn.getKwotaPLN())) {
                                brakwpln = true;
                            }
                        }
                        sumawn += wn.getKwotaPLN();
                    }
                    if (ma != null) {
                        if (ma.getKonto().getKontopozycjaID() == null) {
                            brakPozycji = true;
                        }
                        jestkontonieostatnieMa = ma.getKonto().isMapotomkow();
                        if (ma.getKwota() > 0 && ma.getKwotaPLN() == 0) {
                            brakwpln = true;
                        }
                        if (r.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
                            if (Z.z(ma.getKwota()) != Z.z(ma.getKwotaPLN())) {
                                brakwpln = true;
                            }
                        }
                        sumama += ma.getKwotaPLN();
                    }
                    if (jestkontonieostatnieWn == true || jestkontonieostatnieMa == true) {
                        if (!listabrakiKontaAnalityczne.contains(p)) {
                            listabrakiKontaAnalityczne.add(p);
                        }
                    }
                }
            }
            if (Z.z(sumawn) != Z.z(sumama)) {
                double roznica = Z.z(Z.z(sumawn) - Z.z(sumama));
                listaRozniceWnMa.add(p);
                    if (liczbawierszy > 1) {
                    StronaWiersza swWn = p.getListawierszy().get(0).getStronaWn();
                    StronaWiersza swMa = p.getListawierszy().get(0).getStronaMa();
                    String symbol = swWn.getSymbolWaluty() != null ? swWn.getSymbolWaluty() : swWn.getSymbolWalutyBO();
                    if (!symbol.equals("PLN")) {
                        if (roznica > 0) {
                            swMa.setKwotaPLN(swMa.getKwotaPLN() + roznica);
                        } else {
                            swWn.setKwotaPLN(swWn.getKwotaPLN() - roznica);
                        }
                    }
                }
            }
            if (brakwpln == true) {
                listabraki.add(p);
            }
            if (brakPozycji == true) {
                listabrakiPozycji.add(p);
            }
        }
        String main = "Występują księgowania na sytnetykach w " + listabrakiKontaAnalityczne.size() + " dokumentach: ";
        StringBuilder b = new StringBuilder();
        b.append(main);
        for (Dokfk p : listabrakiKontaAnalityczne) {
            b.append(p.getDokfkPK().toString2());
            b.append(", ");
        }
        if (listabrakiKontaAnalityczne.size() > 0) {
            Msg.msg("i", b.toString(), b.toString(), "zestawieniedokumentow:wiadomoscsprawdzenie", "zestawieniedokumentow:dataList");
        }
        main = "Występują różnice w stronach Wn i Ma w PLN w " + listaRozniceWnMa.size() + " dokumentach: ";
        b = new StringBuilder();
        b.append(main);
        for (Dokfk p : listaRozniceWnMa) {
            b.append(p.getDokfkPK().toString2());
            b.append(", ");
        }
        if (listaRozniceWnMa.size() > 0) {
            dokDAOfk.editList(listaRozniceWnMa);
            Msg.msg("i", b.toString(), b.toString(), "zestawieniedokumentow:wiadomoscsprawdzenie", "zestawieniedokumentow:dataList");
        }
        main = "Występują braki w kolumnie pln w " + listabraki.size() + " dokumentach: ";
        b = new StringBuilder();
        b.append(main);
        for (Dokfk p : listabraki) {
            for (StronaWiersza sw : p.getStronyWierszy()) {
                String symbol = sw.getSymbolWaluty() != null ? sw.getSymbolWaluty() : sw.getSymbolWalutyBO();
                if (symbol.equals("PLN")) {
                    sw.setKwotaPLN(sw.getKwota());
                }
            }
            b.append(p.getDokfkPK().toString2());
            b.append(", ");
        }
        if (listabraki.size() > 0) {
            dokDAOfk.editList(listabraki);
            Msg.msg("i", b.toString(), b.toString(), "zestawieniedokumentow:wiadomoscsprawdzenie", "zestawieniedokumentow:dataList");
        }
        main = "Konta w dokumencie nie maja przyporzadkowania do Pozycji w " + listaRozniceWnMa.size() + " dokumentach: ";
        b = new StringBuilder();
        b.append(main);
        for (Dokfk p : listabrakiPozycji) {
            b.append(p.getDokfkPK().toString2());
            b.append(", ");
        }
        if (listabrakiPozycji.size() > 0) {
            Msg.msg("i", b.toString(), b.toString(), "zestawieniedokumentow:wiadomoscsprawdzenie", "zestawieniedokumentow:dataList");
        }
        main = "Niezgodność między miesiącem ewidencji vat a typem konta vat w " + listabrakivat.size() + " dokumentach: ";
        b = new StringBuilder();
        b.append(main);
        for (Dokfk p : listabrakivat) {
            b.append(p.getDokfkPK().toString2());
            b.append(", ");
        }
        if (listabrakivat.size() > 0) {
            Msg.msg("i", b.toString(), b.toString(), "zestawieniedokumentow:wiadomoscsprawdzenie", "zestawieniedokumentow:dataList");
        }
        main = "Puste ewidencje vat w " + listapustaewidencja.size() + " dokumentach: ";
        b = new StringBuilder();
        b.append(main);
        for (Dokfk p : listapustaewidencja) {
            b.append(p.getDokfkPK().toString2());
            b.append(", ");
        }
        if (listapustaewidencja.size() > 0) {
            Msg.msg("i", b.toString(), b.toString(), "zestawieniedokumentow:wiadomoscsprawdzenie", "zestawieniedokumentow:dataList");
        }
        init();
    }

    public void sprawdzsalda(String wybranakategoriadok) {
        List<Dokfk> wykaz = dokDAOfk.findDokfkPodatnikRokKategoriaOrderByNo(wpisView, wybranakategoriadok);
        for (Dokfk p : wykaz) {
            int nrserii = p.getDokfkPK().getNrkolejnywserii();
            if (nrserii == 1) {
                double saldobo = DokFKBean.pobierzwartosczBO(p.getRodzajedok().getKontorozrachunkowe(), wpisView, wierszBODAO);
                p.setSaldopoczatkowe(saldobo);
            } else {
                Dokfk poprzedni = wykaz.get(wykaz.indexOf(p) - 1);
                double saldopoprzednie = poprzedni.getSaldokoncowe();
                p.setSaldopoczatkowe(saldopoprzednie);
            }
            for (Wiersz w : p.getListawierszy()) {
                DialogWpisywanie.naprawsaldo(p, w);
            }
        }
        dokDAOfk.editList(wykaz);
        wykazZaksiegowanychDokumentow = wykaz;
    }

    public void odswiezzaksiegowaneInit() {
        miesiacDlaZestawieniaZaksiegowanych = wpisView.getMiesiacWpisu();
        wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
        Collections.sort(wykazZaksiegowanychDokumentow, new Dokfkcomparator());
        filteredValue = null;
    }
    
    public void odswiezzaksiegowane() {
        if (wybranakategoriadok == null) {
            wybranakategoriadok = "wszystkie";
        }
        if (wybranakategoriadok.equals("wszystkie")) {
            if (miesiacDlaZestawieniaZaksiegowanych.equals("CR")) {
                wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRok(wpisView);
            } else {
                wpisView.setMiesiacWpisu(miesiacDlaZestawieniaZaksiegowanych);
                wpisView.wpisAktualizuj();
                wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
            }
        } else {
            if (miesiacDlaZestawieniaZaksiegowanych.equals("CR")) {
                wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokKategoria(wpisView, wybranakategoriadok);
            } else {
                wpisView.setMiesiacWpisu(miesiacDlaZestawieniaZaksiegowanych);
                wpisView.wpisAktualizuj();
                wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokMcKategoria(wpisView, wybranakategoriadok);
            }
        }
        if (wykazZaksiegowanychDokumentow != null && wykazZaksiegowanychDokumentow.size() > 0) {
            for (Iterator<Dokfk> it = wykazZaksiegowanychDokumentow.iterator(); it.hasNext();) {
                Dokfk r = (Dokfk) it.next();
                if (r.isImportowany() == true) {
                    it.remove();
                }
            }
        }
        Collections.sort(wykazZaksiegowanychDokumentow, new Dokfkcomparator());
        filteredValue = null;
        System.out.println("odswiezzaksiegowane()");
    }

    public void odswiezzaksiegowaneimport() {
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
        } else {
            if (wpisView.getMiesiacWpisu().equals("CR")) {
                wykazZaksiegowanychDokumentowimport = dokDAOfk.findDokfkPodatnikRokKategoria(wpisView, wybranakategoriadokimport);
            } else {
                wpisView.wpisAktualizuj();
                wykazZaksiegowanychDokumentowimport = dokDAOfk.findDokfkPodatnikRokMcKategoria(wpisView, wybranakategoriadokimport);
            }
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
    public void zaznaczOdznaczJakoNowaTransakcja(ValueChangeEvent el) {
        boolean oldvalue = (boolean) el.getOldValue();
        boolean newvalue = (boolean) el.getNewValue();
        if (newvalue == true) {
            aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(1);
            aktualnyWierszDlaRozrachunkow.setNowatransakcja(true);
            //trzy poniższe wiersze umożliwiają zrobienie z rozliczajacego nowej transakcji po korekcie kwot
            aktualnyWierszDlaRozrachunkow.setRozliczono(0.0);
            aktualnyWierszDlaRozrachunkow.setPozostalo(0.0);
            aktualnyWierszDlaRozrachunkow.setNowetransakcje(new ArrayList<Transakcja>());
            zablokujprzyciskrezygnuj = true;
            Msg.msg("i", "Dodano bieżący zapis jako nową transakcję");
        } else {
            if (aktualnyWierszDlaRozrachunkow.getRozliczono() > 0) {
                Msg.msg("e", "Trasakcja rozliczona - nie można usunąć oznaczenia");
            } else {
                aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(0);
                aktualnyWierszDlaRozrachunkow.setNowatransakcja(false);
                zablokujprzyciskrezygnuj = false;
                aktualnyWierszDlaRozrachunkow = null;
                Msg.msg("i", "Usunięto zapis z listy nowych transakcji");
            }
        }
        selected.setLiczbarozliczonych(DokFKTransakcjeBean.sprawdzrozliczoneWiersze(selected.getListawierszy()));
        if (selected.getLiczbarozliczonych() > 0) {
            selected.setZablokujzmianewaluty(true);
        } else {
            selected.setZablokujzmianewaluty(false);
        }
        if (oldvalue == true && newvalue == false) {
            String f = "PF('rozrachunki').hide();";
            RequestContext.getCurrentInstance().execute(f);
        }
        RequestContext.getCurrentInstance().update("formwpisdokument:panelwalutowy");
        RequestContext.getCurrentInstance().update("wpisywaniefooter");
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
        RequestContext.getCurrentInstance().update("formwpisdokument:paneldaneogolnefaktury");
    }

    public void pobranieWiersza(Wiersz wybranywiersz) {
        lpWierszaWpisywanie = wybranywiersz.getIdporzadkowy();
        //Msg.msg("Wiersz "+lpWierszaWpisywanie);
        System.out.println("pobrano wiersz " + lpWierszaWpisywanie);
    }

    public void pobranieStronaWiersza(StronaWiersza wybranastronawiersza) {
        lpWierszaWpisywanie = wybranastronawiersza.getWiersz().getIdporzadkowy() - 1;
        String pole = null;
        if (wybranastronawiersza.getWnma().equals("Wn")) {
            pole = (String) Params.params("formwpisdokument:dataList:" + lpWierszaWpisywanie + ":kontown_input");
        } else {
            pole = (String) Params.params("formwpisdokument:dataList:" + lpWierszaWpisywanie + ":kontoma_input");
        }
        //11 dodaje nowego klienta
        if (pole.equals("")) {
            //jak nie ma konta to nie ma co uruchamiac
            return;
        } else if (pole.contains("dodaj konto")) {
            jest1niema0_konto = 0;
            return;
        } else if (pole.contains("dodaj kontrahenta")) {
            jest1niema0_konto = 11;
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
                        RequestContext.getCurrentInstance().update("formtransakcjawybor");
                    }
                    if (wybranastronawiersza.getTypStronaWiersza() == 1) {
                        biezacetransakcje = tworzenieTransakcjiRachunek(wnma, wybranastronawiersza);
                        //platnosc
                    } else if (wybranastronawiersza.getTypStronaWiersza() == 2) {
                        biezacetransakcje = tworzenieTransakcjiPlatnosc(wnma, wybranastronawiersza);
                    } else {
                        System.out.println("Aktualny wiersz nie ma numer 1 lub 2 DokfkView wybranoRachunekPlatnoscCD");
                    }
                    System.out.println(wybranastronawiersza.toString());

                }
                if (wybranastronawiersza.getKonto() != null && wybranastronawiersza.getKonto().equals(selected.getRodzajedok().getKontorozrachunkowe())) {
                    rozliczsaldoWBRK(lpWierszaWpisywanie);
                }
            }
        } catch (Exception e) {
            System.out.println("Blad DokfkView pobranieStronaWiersza");
            E.e(e);
        }
    }

    //to pojawia sie na dzien dobry jak ktos wcisnie alt-r
//    public void wybranoRachunekPlatnosc() {
//        lpWierszaWpisywanie = Integer.parseInt((String) Params.params("wpisywaniefooter:wierszid"))-1;
//        stronawiersza = (String) Params.params("wpisywaniefooter:wnlubma");
//        Wiersz wiersz = selected.getListawierszy().get(lpWierszaWpisywanie);
//        if (wiersz != null ) {
//            System.out.println("Wiersz rozny od null");
//            System.out.println("lpWierszaWpisywanie "+lpWierszaWpisywanie);
//            System.out.println("stronawiersza "+stronawiersza);
//            System.out.println("wybranoRachunekPlatnosc() wiersz dla rozrachunku "+wiersz.tostring2());
//        } else {
//            System.out.println("Wiersz rowny null");
//            System.out.println("lpWierszaWpisywanie "+lpWierszaWpisywanie);
//            System.out.println("stronawiersza "+stronawiersza);
//            System.out.println("wiersz dla rozrachunku NULL");
//        }
//        biezacetransakcje = new ArrayList<>();
//        aktualnyWierszDlaRozrachunkow = pobierzStronaWierszaDlaRozrachunkow(wiersz, stronawiersza);
//        potraktujjakoNowaTransakcje = selected.getRodzajedok().getKategoriadokumentu() == 0 ? false : true;
//        if (aktualnyWierszDlaRozrachunkow.getTypStronaWiersza() == 0) {
//            rachunekCzyPlatnosc = selected.getRodzajedok().getKategoriadokumentu() == 0 ? "płatność" : "rachunek";
//        } else {
//            wybranoRachunekPlatnoscCD(stronawiersza);
//        }
//    }
    //to pojawia sie na dzien dobry jak ktos wcisnie alt-r
    public void wybranoStronaWierszaCecha() {
        int idwiersza = Integer.parseInt((String) Params.params("wpisywaniefooter:wierszid"));
        if (idwiersza > -1) {
            Wiersz wiersz = selected.getListawierszy().get(idwiersza);
            if (wnmadoprzeniesienia.equals("Wn")) {
                stronaWierszaCechy = wiersz.getStronaWn();
            } else {
                stronaWierszaCechy = wiersz.getStronaMa();
            }
            pobranecechy = cechazapisuDAOfk.findAll();
            List<Cechazapisu> cechyuzyte = stronaWierszaCechy.getCechazapisuLista();
            for (Cechazapisu c : cechyuzyte) {
                pobranecechy.remove(c);
            }
            RequestContext.getCurrentInstance().update("formCHW");
        }
    }

    public void dodajcechedostronawiersza(Cechazapisu c) {
        pobranecechy.remove(c);
        stronaWierszaCechy.getCechazapisuLista().add(c);
        c.getStronaWierszaLista().add(stronaWierszaCechy);
    }

    public void usuncechedostronawiersza(Cechazapisu c) {
        pobranecechy.add(c);
        stronaWierszaCechy.getCechazapisuLista().remove(c);
        c.getStronaWierszaLista().remove(stronaWierszaCechy);
    }

    //to sie pojawia jak wciscnie alt-r i wiesz juz jest okreslony
    public void wybranoRachunekPlatnoscCD() {
        //0 oznacza strone niewybrana
        if (aktualnyWierszDlaRozrachunkow == null) {
            Msg.msg("e", "AktualnyWierszDlaRozrachunkow jest pusty wybranoRachunekPlatnoscCD(String stronawiersza)");
            System.out.println("AktualnyWierszDlaRozrachunkow jest pusty wybranoRachunekPlatnoscCD(String stronawiersza)");
            return;
        }
        if (aktualnyWierszDlaRozrachunkow.getTypStronaWiersza() == 0) {
            if (rachunekCzyPlatnosc.equals("rachunek")) {
                oznaczJakoRachunek();
                RequestContext.getCurrentInstance().update("parametry");
                return;
            } else {
                oznaczJakoPlatnosc();
                RequestContext.getCurrentInstance().update("parametry");
            }
        }
        //nowa transakcja
        if (aktualnyWierszDlaRozrachunkow.getTypStronaWiersza() == 1) {
            biezacetransakcje = tworzenieTransakcjiRachunek(wnmadoprzeniesienia, aktualnyWierszDlaRozrachunkow);
            //platnosc
        } else if (aktualnyWierszDlaRozrachunkow.getTypStronaWiersza() == 2) {
            biezacetransakcje = tworzenieTransakcjiPlatnosc(wnmadoprzeniesienia, aktualnyWierszDlaRozrachunkow);
        } else {
            System.out.println("Blad aktualny wiersz ma dziwny numer DokfkView wybranoRachunekPlatnoscCD");
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
                System.out.println("aktualny wiersz dla roarachunku " + wybranastronawiersza.toString());
                stronyWierszazDokumentu = (DokFKTransakcjeBean.pobierzStronaWierszazDokumentu(wybranastronawiersza.getKonto().getPelnynumer(), stronawiersza, wybranastronawiersza.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty(), selected.getListawierszy()));
                stronyWierszazBazy = DokFKTransakcjeBean.pobierzStronaWierszazBazy(wybranastronawiersza, stronawiersza, stronaWierszaDAO, transakcjaDAO);
                transakcje = (DokFKTransakcjeBean.stworznowetransakcjezeZapisanychStronWierszy(stronyWierszazDokumentu, stronyWierszazBazy, wybranastronawiersza, wpisView.getPodatnikWpisu()));
                DokFKTransakcjeBean.naniesKwotyZTransakcjiwPowietrzu(wybranastronawiersza, transakcje, selected.getListawierszy(), stronawiersza);
                Collections.sort(transakcje, new Transakcjacomparator());
                //trzeba zablokować mozliwosc zmiaktualnyWierszDlaRozrachunkowany nowej transakcji jak sa juz rozliczenia!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                String funkcja;
                //jezeli w pobranych transakcjach sa juz rozliczenia to trzeba zablokowac mozliwosc zaznaczania nowej transakcji
                double saWartosciWprowadzone = 0.0;
                for (Transakcja p : transakcje) {
                    saWartosciWprowadzone += p.getKwotatransakcji();
                    if (saWartosciWprowadzone > 0) {
                        break;
                    }
                }
                if (saWartosciWprowadzone > 0) {
                    funkcja = "zablokujcheckbox('true','platnosc');";
                } else {
                    funkcja = "zablokujcheckbox('false','platnosc');";
                }
                RequestContext.getCurrentInstance().execute(funkcja);
                RequestContext.getCurrentInstance().update("rozrachunki");
                RequestContext.getCurrentInstance().update("dialogdrugi");
                RequestContext.getCurrentInstance().update("formcheckbox:znaczniktransakcji");
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
        List<Transakcja> transakcje = new ArrayList<>();
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
            aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(2);
        }
        Iterator it = aktualnyWierszDlaRozrachunkow.getNowetransakcje().iterator();
        while (it.hasNext()) {
            Transakcja tr = (Transakcja) it.next();
            if (tr.getKwotatransakcji() == 0.0) {
                it.remove();
            } else {
                if (aktualnyWierszDlaRozrachunkow.getWiersz().getDataWalutyWiersza() != null) {
                    String datawiersza;
                    if (aktualnyWierszDlaRozrachunkow.getWiersz().getDataWalutyWiersza().length() == 1) {
                        datawiersza = "0" + aktualnyWierszDlaRozrachunkow.getWiersz().getDataWalutyWiersza();
                    } else {
                        datawiersza = aktualnyWierszDlaRozrachunkow.getWiersz().getDataWalutyWiersza();
                    }
                    String data = selected.getDokfkPK().getRok() + "-" + selected.getMiesiac() + "-" + datawiersza;
                    tr.setDatarozrachunku(data);
                } else {
                    tr.setDatarozrachunku(Data.aktualnyDzien());
                }
                //tr.getNowaTransakcja().getPlatnosci().add(tr);
            }
        }
        if (aktualnyWierszDlaRozrachunkow.getNowetransakcje().isEmpty()) {
            aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(0);
        }
        selected.setLiczbarozliczonych(DokFKTransakcjeBean.sprawdzrozliczoneWiersze(selected.getListawierszy()));
        if (selected.getLiczbarozliczonych() > 0) {
            selected.setZablokujzmianewaluty(true);
        } else {
            selected.setZablokujzmianewaluty(false);
        }
        RequestContext.getCurrentInstance().update("formwpisdokument:panelwalutowy");
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
        //RequestContext.getCurrentInstance().execute("wybierzWierszPoZmianieWaluty();");
    }

//    
//    //********************
//    //a to jest rodzial dotyczacy walut
//
    public void pobierzkursNBP(ValueChangeEvent el) {
        tabelenbp = new ArrayList<>();
        symbolwalutydowiersza = ((Waluty) el.getNewValue()).getSymbolwaluty();
        String nazwawaluty = ((Waluty) el.getNewValue()).getSymbolwaluty();
        String staranazwa = ((Waluty) el.getOldValue()).getSymbolwaluty();
        if (!staranazwa.equals("PLN") && !nazwawaluty.equals("PLN")) {
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
                    selected.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty));
                    //wpisuje kurs bez przeliczania, to jest dla nowego dokumentu jak sie zmieni walute na euro
                    wybranawaluta = walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty).getSymbolwaluty();
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
                    selected.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty));
                } else {
                    selected.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty));
                    //wpisuje kurs bez przeliczania, to jest dla nowego dokumentu jak sie zmieni walute na euro
                    wybranawaluta = walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty).getSymbolwaluty();
                }
                Tabelanbp tabelanbpPLN = null;
                try {
                    tabelanbpPLN = tabelanbpDAO.findByDateWaluta("2012-01-01", "PLN");
                    if (tabelanbpPLN == null) {
                        tabelanbpPLN = new Tabelanbp("000/A/NBP/0000", walutyDAOfk.findWalutaBySymbolWaluty("PLN"), "2012-01-01");
                        tabelanbpDAO.dodaj(tabelanbpPLN);
                    }
                } catch (Exception e) {
                    E.e(e);
                }
                selected.setTabelanbp(tabelanbpPLN);
                List<Wiersz> wiersze = selected.getListawierszy();
                for (Wiersz p : wiersze) {
                    p.setTabelanbp(tabelanbpPLN);
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
    public void wyliczroznicekursowa(Transakcja loop, int row) {
        try {
            double kursPlatnosci = loop.getRozliczajacy().getWiersz().getTabelanbp().getKurssredni();
            double kursRachunku;
            if (loop.getNowaTransakcja().getWiersz().getTabelanbp() != null) {
                kursRachunku = loop.getNowaTransakcja().getWiersz().getTabelanbp().getKurssredni();
            } else {
                kursRachunku = loop.getNowaTransakcja().getKursBO();
            }
            String wiersz = "rozrachunki:dataList:" + row + ":kwotarozliczenia_input";
            String kwotazwiersza = (String) Params.params(wiersz);
            kwotazwiersza = kwotazwiersza.replaceAll("\\s", "");
            double placonakwota = Double.parseDouble(kwotazwiersza);
            if (placonakwota == 0.0) {
                loop.setRoznicekursowe(0.0);
                wiersz = "rozrachunki:dataList:" + row + ":roznicakursowa";
                RequestContext.getCurrentInstance().update(wiersz);
                loop.setKwotawwalucierachunku(0.0);
                wiersz = "rozrachunki:dataList:" + row + ":kwotawwalucierachunku";
                RequestContext.getCurrentInstance().update(wiersz);
            }
            if (kursPlatnosci == 0.0 && kursRachunku != 0.0) {
                if (placonakwota > 0.0) {
                    double kwotaPlatnosciwWalucie = Z.z(placonakwota / kursRachunku);
                    double kwotaRachunkuwWalucie = loop.getNowaTransakcja().getKwota() - loop.getNowaTransakcja().getRozliczono()+placonakwota;
                    double kwotaRachunkuwPLN = kwotaRachunkuwWalucie * kursRachunku;
                    double roznicakursowa = Z.z(placonakwota - kwotaRachunkuwPLN);
                    if (roznicakursowa > 0.0) {
                        loop.setRoznicekursowe(roznicakursowa);
                        wiersz = "rozrachunki:dataList:" + row + ":roznicakursowa";
                    } else {
                        loop.setRoznicekursowe(0.0);
                        wiersz = "rozrachunki:dataList:" + row + ":roznicakursowa";
                    }
                    RequestContext.getCurrentInstance().update(wiersz);
                    loop.setKwotawwalucierachunku(kwotaPlatnosciwWalucie > kwotaRachunkuwWalucie ? kwotaRachunkuwWalucie : kwotaPlatnosciwWalucie);
                    wiersz = "rozrachunki:dataList:" + row + ":kwotawwalucierachunku";
                    RequestContext.getCurrentInstance().update(wiersz);
                }
            } else if (kursPlatnosci == 0.0 && kursRachunku == 0.0) {
                if (placonakwota > 0.0) {
                    loop.setKwotawwalucierachunku(placonakwota);
                    wiersz = "rozrachunki:dataList:" + row + ":kwotawwalucierachunku";
                    RequestContext.getCurrentInstance().update(wiersz);
                }
            } else if (kursPlatnosci != 0.0 && kursRachunku == 0.0) {
                if (placonakwota > 0.0) {
                    double kwotaPlatnosciwPLN = Z.z(placonakwota * kursPlatnosci);
                    double kwotaRachunkuwPLN = loop.getNowaTransakcja().getKwota() - loop.getNowaTransakcja().getRozliczono()+placonakwota;
                    double roznicakursowa = Z.z(kwotaPlatnosciwPLN - kwotaRachunkuwPLN);
                    if (roznicakursowa > 0.0) {
                        loop.setRoznicekursowe(roznicakursowa);
                        wiersz = "rozrachunki:dataList:" + row + ":roznicakursowa";
                    } else {
                        loop.setRoznicekursowe(0.0);
                        wiersz = "rozrachunki:dataList:" + row + ":roznicakursowa";
                    }
                    RequestContext.getCurrentInstance().update(wiersz);
                    loop.setKwotawwalucierachunku(kwotaPlatnosciwPLN > kwotaRachunkuwPLN ? kwotaRachunkuwPLN : kwotaPlatnosciwPLN);
                    wiersz = "rozrachunki:dataList:" + row + ":kwotawwalucierachunku";
                    RequestContext.getCurrentInstance().update(wiersz);
                }
            } else if (kursPlatnosci != 0.0 && kursRachunku != 0.0) {
                if (placonakwota > 0.0) {
                    double kwotaPlatnosciwPLN = Z.z(placonakwota * kursPlatnosci);
                    double kwotaRachunkuwPLN = Z.z(placonakwota * kursRachunku);
                    double roznicakursowa = Z.z(kwotaPlatnosciwPLN - kwotaRachunkuwPLN);
                    loop.setRoznicekursowe(roznicakursowa);
                    wiersz = "rozrachunki:dataList:" + row + ":roznicakursowa";
                    RequestContext.getCurrentInstance().update(wiersz);
                    loop.setKwotawwalucierachunku(placonakwota);
                    wiersz = "rozrachunki:dataList:" + row + ":kwotawwalucierachunku";
                    RequestContext.getCurrentInstance().update(wiersz);
                }
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd podczas pobierania tabel NBP. Nie obliczono różnic kursowych");
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
        System.out.println("roznica " + roznicakursowa);
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
        if (!symbolwaluty.equals("PLN")) {

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
            Klienci klient = klienciDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
            if (klient == null) {
                klientdlaPK = new Klienci("222222222222222222222", "BRAK FIRMY JAKO KONTRAHENTA!!!");
            }
            String nowynumer = DokFKBean.wygenerujnumerkolejny(selected, wpisView, dokDAOfk, klient, wierszBODAO);
            if (!nowynumer.isEmpty() && selected.getNumerwlasnydokfk() == null) {
                selected.setNumerwlasnydokfk(nowynumer);
            }
            if (!nowynumer.isEmpty() && selected.getNumerwlasnydokfk().isEmpty()) {
                selected.setNumerwlasnydokfk(nowynumer);
            }
            if (!nowynumer.isEmpty() && zapisz0edytuj1 == false) {
                selected.setNumerwlasnydokfk(nowynumer);
            }
            if (nowynumer.equals("")) {
                selected.setNumerwlasnydokfk("");
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
                    System.out.println("lpwiersza " + lpWierszaWpisywanie);
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
                        ewidencjaVATRKzapis0edycja1 = false;
                    }
                    RequestContext.getCurrentInstance().update("dialogewidencjavatRK");
                    System.out.println("Generowanie ewidencji vat rk");
                } catch (Exception e) {
                    E.e(e);
                }
            }
        } else {
            ewidencjaVatRK = null;
            System.out.println("Blad ewidencjaVatRKInit() lpwiersza == \"\"");
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
        Konto k221_3 = kontoDAOfk.findKonto("221-3", wpisView);
        Konto k221_4 = kontoDAOfk.findKonto("221-4", wpisView);
        String vatokres = sprawdzjakiokresvat();
        if (!vatokres.equals("miesięczne")) {
            Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(selected.getMiesiac())));
            List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
            String[] nowymc = Mce.zwiekszmiesiac(wpisView.getRokWpisuSt(), selected.getMiesiac(), innyokres);
            if (miesiacewkwartale.contains(nowymc[1])) {
                innyokres = 0;
            }
        }
        for (Wiersz r : selected.getListawierszy()) {
            if (innyokres != 0) {
                if (r.getStronaWn() != null && r.getStronaWn().getKonto().getPelnynumer().equals("221-3")) {
                    r.getStronaWn().setKonto(k221_4);
                } else if (r.getStronaMa() != null && r.getStronaMa().getKonto().getPelnynumer().equals("221-3")) {
                    r.getStronaMa().setKonto(k221_4);
                }
            } else {
                if (r.getStronaWn() != null && r.getStronaWn().getKonto().getPelnynumer().equals("221-4")) {
                    r.getStronaWn().setKonto(k221_3);
                } else if (r.getStronaMa() != null && r.getStronaMa().getKonto().getPelnynumer().equals("221-4")) {
                    r.getStronaMa().setKonto(k221_3);
                }
            }
        }
    }
    
    public String sprawdzjakiokresvat() {
        Integer rok = wpisView.getRokWpisu();
        Integer mc = Integer.parseInt(wpisView.getMiesiacWpisu());
        Integer sumaszukana = rok + mc;
        List<Parametr> parametry = wpisView.getPodatnikObiekt().getVatokres();
        //odszukaj date w parametrze - kandydat na metode statyczna
        for (Parametr p : parametry) {
            if (p.getRokDo() != null && !"".equals(p.getRokDo())) {
                int wynikPo = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
                int wynikPrzed = Data.compare(rok, mc, Integer.parseInt(p.getRokDo()), Integer.parseInt(p.getMcDo()));
                if (wynikPo > 0 && wynikPrzed < 0) {
                    return p.getParametr();
                }
            } else {
                int wynik = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
                if (wynik >= 0) {
                    return p.getParametr();
                }
            }
        }
        Msg.msg("e", "Problem z funkcja sprawdzajaca okres rozliczeniowy VAT VatView-269");
        return "blad";
    }

    public void dodajcechedodokumentu(Cechazapisu c) {
        pobranecechy.remove(c);
        selected.getCechadokumentuLista().add(c);
        c.getDokfkLista().add(selected);
    }

    public void usuncechedodokumentu(Cechazapisu c) {
        pobranecechy.add(c);
        selected.getCechadokumentuLista().remove(c);
        c.getDokfkLista().remove(selected);
    }

    private void obsluzcechydokumentu() {
        //usuwamy z listy dostepnych cech te, ktore sa juz przyporzadkowane do dokumentu
        pobranecechy = cechazapisuDAOfk.findAll();
        List<Cechazapisu> cechyuzyte = null;
        if (selected != null) {
            if (selected.getCechadokumentuLista() == null) {
                cechyuzyte = new ArrayList<>();
            } else {
                cechyuzyte = selected.getCechadokumentuLista();
            }
            for (Cechazapisu c : cechyuzyte) {
                pobranecechy.remove(c);
            }
        }
        RequestContext.getCurrentInstance().update("formCH");
    }

    

    private double obliczsaldopoczatkowe() {
        List<StronaWiersza> kontozapisy = stronaWierszaDAO.findStronaByPodatnikKontoRokWaluta(wpisView.getPodatnikObiekt(), selected.getRodzajedok().getKontorozrachunkowe(), wpisView.getRokWpisuSt(), selected.getTabelanbp().getWaluta().getSymbolwaluty());
        if (kontozapisy != null && !kontozapisy.isEmpty()) {
            double sumaWn = 0.0;
            double sumaMa = 0.0;
            for (Iterator<StronaWiersza> it = kontozapisy.iterator(); it.hasNext();) {
                StronaWiersza p = it.next();
                if (p.getWiersz().getDokfk().getDokfkPK().equals(selected.getDokfkPK())) {
                    it.remove();
                } else if (p.getWiersz().getDokfk().getDokfkPK().getNrkolejnywserii() > selected.getDokfkPK().getNrkolejnywserii()) {
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

    public void uzupelnijdokumentyodkontrahenta() {
        try {
            for (Dokfk p : wykazZaksiegowanychDokumentow) {
                if (p.getRodzajedok().getKategoriadokumentu() != 1 && p.getRodzajedok().getKategoriadokumentu() != 2 && p.getKontr() == null) {
                    Klienci k = klienciDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
                    if (k == null) {
                        k = new Klienci("222222222222222222222", "BRAK FIRMY JAKO KONTRAHENTA!!!");
                    }
                    p.setKontr(k);
                    dokDAOfk.edit(p);
                }
            }
        } catch (Exception e) {
            E.e(e);

        }
    }

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

    public void sprawdzwartoscigrupy() {
        if (nrgrupywierszy == null) {
            return;
        }
        try {
            System.out.println("sprawdzwartoscigrupy() grupa nr: " + nrgrupywierszy);
            Wiersz wierszpodstawowy = selected.getListawierszy().get(nrgrupywierszy - 1);
            if (wierszpodstawowy.getDokfk().getDokfkPK().getSeriadokfk().equals("BO")) {
                return;
            }
            double sumaWn = wierszpodstawowy.getStronaWn().getKwota();
            double sumaMa = wierszpodstawowy.getStronaMa().getKwota();
            int typwiersza = 0;
            Wiersz wiersznastepny = null;
            do {
                wiersznastepny = selected.nastepnyWiersz(wierszpodstawowy);
                if (wiersznastepny != null) {
                    if (wiersznastepny.getTypWiersza() == 1) {
                        wierszpodstawowy = wiersznastepny;
                        sumaWn += wiersznastepny.getStronaWn().getKwota();
                        typwiersza = 1;
                        System.out.println("kwotaWn " + wiersznastepny.getStronaWn().getKwota());
                    } else if (wiersznastepny.getTypWiersza() == 2) {
                        wierszpodstawowy = wiersznastepny;
                        sumaMa += wiersznastepny.getStronaMa().getKwota();
                        typwiersza = 2;
                        System.out.println("kwotaMa " + wiersznastepny.getStronaMa().getKwota());
                    }
                }
                if (wiersznastepny == null || wiersznastepny.getTypWiersza() == 0) {
                    break;
                }
            } while (true);
            if (Z.z(sumaWn) != Z.z(sumaMa)) {
                Wiersz wierszpoprzedni = selected.poprzedniWiersz(wiersznastepny);
                if (wiersznastepny != null) {
                    ObslugaWiersza.wygenerujWierszRoznicowy(wierszpoprzedni, true, nrgrupywierszy, selected);
                    RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
                } else {
                    ObslugaWiersza.wygenerujWierszRoznicowy(wierszpoprzedni, false, nrgrupywierszy, selected);
                    RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
                }
                selected.przeliczKwotyWierszaDoSumyDokumentu();
            }
            rozliczsaldoWBRK(wierszpodstawowy.getIdporzadkowy() - 1);
        } catch (Exception e) {
            E.e(e);
            System.out.println("Problem z numerem grupy DokfkView sprawdzwartoscigrupy()");
        }
    }

    public void przelicznaklawiszu() {
        selected.przeliczKwotyWierszaDoSumyDokumentu();
        RequestContext.getCurrentInstance().update("formwpisdokument:wartoscdokumentu");
    }

//    public void resetujzaksiegowane() {
//        wykazZaksiegowanychDokumentow = new ArrayList<>();
//    }
    public void resetujzaksiegowaneimport() {
        wykazZaksiegowanychDokumentow = new ArrayList<>();
    }

    public void niedodawajkonta() {
        niedodawajkontapole = true;
    }

    public void przenumerujDokumentyFK() {
        List<Dokfk> dokumenty = null;
        List<String> serie = null;
        if (wybranakategoriadok.equals("wszystkie")) {
            serie = dokDAOfk.findZnajdzSeriePodatnik(wpisView);
            dokumenty = dokDAOfk.findDokfkPodatnikRok(wpisView);
        } else {
            serie = new ArrayList<>();
            serie.add(wybranakategoriadok);
            dokumenty = dokDAOfk.findDokfkPodatnikRokKategoria(wpisView, wybranakategoriadok);
        }
        nadajnowenumery(serie, dokumenty);
        System.out.println("df");
    }

    private void nadajnowenumery(List<String> serie, List<Dokfk> dokumenty) {
        List<Dokfk> nowadosortowania = null;
        for (String r : serie) {
            nowadosortowania = new ArrayList<>();
            for (Dokfk t : dokumenty) {
                if (t.getDokfkPK().getSeriadokfk().equals(r)) {
                    nowadosortowania.add(t);
                }
            }
            Collections.sort(nowadosortowania, new DokfkLPcomparator());
            int kolejny = 1;
            for (Dokfk f : nowadosortowania) {
                f.setLp(kolejny++);

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
        wykazZaksiegowanychDokumentow = new ArrayList<>();
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
        if (!wiersz.equals("")) {
            poledlawaluty = wierszlp;
        }
    }

    public void zamienkursnareczny() {
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
        if (selected == null) {
            Msg.msg("e", "Nie wybrano dokumentu");
        } else {
            selected.setWzorzec(!selected.isWzorzec());
            dokDAOfk.edit(selected);
            if (selected.isWzorzec() == true) {
                Msg.msg("Oznaczono dokument jako wzorzec.");
            } else {
                Msg.msg("w", "Odznaczono dokument jako wzorzec.");
            }
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
                String nazwa = wpisView.getPodatnikObiekt().getNip() + "dokumentzaksiegowane" + p.getDokfkPK().getNrkolejnywserii();
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
        dodajOpisWstepny(document, "Zestawienie zaksięgowanych dokumentów", wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
        dodajTabele(document, testobjects.testobjects.getTabelaZaksiegowane(wiersze), 100, 0);
        finalizacjaDokumentu(document);
        String f = "wydrukZaksiegowaneLista('" + wpisView.getPodatnikObiekt().getNip() + "');";
        RequestContext.getCurrentInstance().execute(f);
    }

    public void usunzaznaczone() {
        if (selectedlist != null && selectedlist.size() > 0) {
            for (Dokfk p : selectedlist) {
                wykazZaksiegowanychDokumentow.remove(p);
                dokDAOfk.destroy(p);
            }
            selectedlist = null;
        }
    }

//<editor-fold defaultstate="collapsed" desc="comment">
    public String getWybranakategoriadok() {
        return wybranakategoriadok;
    }

    public void setWybranakategoriadok(String wybranakategoriadok) {
        this.wybranakategoriadok = wybranakategoriadok;
    }

    public String getMiesiacDlaZestawieniaZaksiegowanych() {
        return miesiacDlaZestawieniaZaksiegowanych;
    }

    public void setMiesiacDlaZestawieniaZaksiegowanych(String miesiacDlaZestawieniaZaksiegowanych) {
        this.miesiacDlaZestawieniaZaksiegowanych = miesiacDlaZestawieniaZaksiegowanych;
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

    public List<Cechazapisu> getPobranecechy() {
        return pobranecechy;
    }

    public void setPobranecechy(List<Cechazapisu> pobranecechy) {
        this.pobranecechy = pobranecechy;
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

    public Wiersz getWiersz() {
        return wiersz;
    }

    public void setWiersz(Wiersz wiersz) {
        this.wiersz = wiersz;
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

    public boolean isZablokujprzyciskrezygnuj() {
        return zablokujprzyciskrezygnuj;
    }

    public void setZablokujprzyciskrezygnuj(boolean zablokujprzyciskrezygnuj) {
        this.zablokujprzyciskrezygnuj = zablokujprzyciskrezygnuj;
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
                selected.setMiesiac("0"+mc);
            }
            int mcint = Integer.parseInt(mc);
            if ( mcint > 13 || mcint < 1) {
                selected.setMiesiac(wpisView.getMiesiacWpisu());
            }
        }
    }

}
