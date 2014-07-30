/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.DokFKBean;
import beansFK.DokFKTransakcjeBean;
import beansFK.DokFKWalutyBean;
import beansFK.StronaWierszaBean;
import dao.StronaWierszaDAO;
import daoFK.DokDAOfk;
import daoFK.TabelanbpDAO;
import daoFK.TransakcjaDAO;
import daoFK.WalutyDAOfk;
import daoFK.ZestawienielisttransakcjiDAO;
import data.Data;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Transakcja;
import entityfk.Waluty;
import entityfk.Wiersz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import msg.Msg;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import params.Params;
import view.WpisView;
import viewfk.subroutines.NaniesZapisynaKontaFK;
import viewfk.subroutines.ObslugaWiersza;
import viewfk.subroutines.UzupelnijWierszeoDane;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DokfkView implements Serializable {
    
    private int numerwiersza;
    private String stronawiersza;
    protected Dokfk selected;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    private boolean zapisz0edytuj1;
//    private String wierszid;
//    private String wnlubma;
    protected int liczbawierszyWDokumencie;
    private List<Dokfk> wykazZaksiegowanychDokumentow;
    //a to jest w dialog_zapisywdokumentach
    @Inject
    private Wiersz wiersz;
    private int wierszDoPodswietlenia;
    //************************************zmienne dla rozrachunkow
//    @Inject
//    protected RozrachunekfkDAO rozrachunekfkDAO;
    @Inject
    protected ZestawienielisttransakcjiDAO zestawienielisttransakcjiDAO;
    @Inject
    protected TransakcjaDAO transakcjaDAO;
    private boolean potraktujjakoNowaTransakcje;
    private StronaWiersza aktualnyWierszDlaRozrachunkow;
    //a to sa listy do sortowanie transakcji na poczatku jedna mega do zbiorki wszystkich dodanych coby nie ginely
    private List<Transakcja> biezacetransakcje;
    private List<Transakcja> transakcjejakosparowany;
    private boolean zablokujprzyciskzapisz;
    private boolean zablokujprzyciskrezygnuj;
    private int pierwotnailosctransakcjiwbazie;
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
    
    


    public DokfkView() {
        this.wykazZaksiegowanychDokumentow = new ArrayList<>();
        this.pierwotnailosctransakcjiwbazie = 0;
        this.biezacetransakcje = new ArrayList<>();
        this.transakcjejakosparowany = new ArrayList<>();
        this.zablokujprzyciskzapisz = false;
        this.potraktujjakoNowaTransakcje = false;
        this.wprowadzonesymbolewalut = new ArrayList<>();
        this.symbolwalutydowiersza = "";
        this.zapisz0edytuj1 = false;
    }

    @PostConstruct
    private void init() {
        try {
            resetujDokument();
            wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnik(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        } catch (Exception e) {
        }
        wprowadzonesymbolewalut.addAll(walutyDAOfk.findAll());
        //usunRozrachunkiNiezaksiegowanychDokfk();
    }
//    //<editor-fold defaultstate="collapsed" desc="schowane podstawowe funkcje jak dodaj usun itp">
//
//    //********************************************funkcje dla ksiegowania dokumentow
//    //RESETUJ DOKUMNETFK
    public void resetujDokument() {
        //kopiuje symbol dokumentu bo nie odkladam go w zmiennej pliku ale dokumentu
        String symbolPoprzedniegoDokumentu = DokFKBean.pobierzSymbolPoprzedniegoDokfk(selected);
        selected = null;
        selected = new Dokfk(symbolPoprzedniegoDokumentu, wpisView.getPodatnikWpisu());
        try {
            DokFKBean.dodajWalutyDoDokumentu(walutyDAOfk, tabelanbpDAO, selected);
            selected.getDokfkPK().setRok(wpisView.getRokWpisuSt());
            RequestContext.getCurrentInstance().update("formwpisdokument:rok");
            pokazPanelWalutowy = false;
            pokazRzadWalutowy = false;
            biezacetransakcje = null;
            liczbawierszyWDokumencie = 1;
            zapisz0edytuj1 = false;
            zablokujprzyciskrezygnuj = false;
        } catch (Exception e) {
            Msg.msg("e", "Brak tabeli w danej walucie. Wystąpił błąd przy inicjalizacji dokumentu. Sprawdź to.");
        }
        RequestContext.getCurrentInstance().update("formwpisdokument");
        RequestContext.getCurrentInstance().update("wpisywaniefooter");
        RequestContext.getCurrentInstance().execute("$(document.getElementById('formwpisdokument:dataDialogWpisywanie')).select();");
    }
    
     public void resetujDokumentWpis() {
        //kopiuje symbol dokumentu bo nie odkladam go w zmiennej pliku ale dokumentu
        String symbolPoprzedniegoDokumentu = DokFKBean.pobierzSymbolPoprzedniegoDokfk(selected);
        selected = null;
        selected = new Dokfk(symbolPoprzedniegoDokumentu, wpisView.getPodatnikWpisu());
        try {
            DokFKBean.dodajWalutyDoDokumentu(walutyDAOfk, tabelanbpDAO, selected);
            pokazPanelWalutowy = false;
            pokazRzadWalutowy = false;
            biezacetransakcje = null;
            liczbawierszyWDokumencie = 1;
            zapisz0edytuj1 = false;
            zablokujprzyciskrezygnuj = false;
        } catch (Exception e) {
            Msg.msg("e", "Brak tabeli w danej walucie. Wystąpił błąd przy inicjalizacji dokumentu. Sprawdź to.");
        }
    }
    
   

//    //dodaje wiersze do dokumentu
    public void dolaczNowyWiersz(int wierszbiezacynr) {
        Konto kontowm = selected.getListawierszy().get(wierszbiezacynr).getStronaWn().getKonto();
        Konto kontoma = selected.getListawierszy().get(wierszbiezacynr).getStronaMa().getKonto();
        if (kontowm instanceof Konto && kontoma instanceof Konto) {
            double kwotaWn = 0.0;
            double kwotaMa = 0.0;
            try {
                liczbawierszyWDokumencie = selected.getListawierszy().size();
                kwotaWn = selected.getListawierszy().get(liczbawierszyWDokumencie - 1).getStronaWn().getKwota();
                kwotaMa = selected.getListawierszy().get(liczbawierszyWDokumencie - 1).getStronaMa().getKwota();
            } catch (Exception e) {
                Msg.msg("w", "Uzupełnij dane przed dodaniem nowego wiersza");
            }
            if (kwotaWn != 0 || kwotaMa != 0) {
                liczbawierszyWDokumencie += 1;
                selected.getListawierszy().add(ObslugaWiersza.utworzNowyWiersz(selected, liczbawierszyWDokumencie));
                int nowyWiersz = liczbawierszyWDokumencie - 1;
                int poprzedniWiersz = liczbawierszyWDokumencie - 2;
                selected.getListawierszy().get(nowyWiersz).setDataWalutyWiersza(selected.getListawierszy().get(poprzedniWiersz).getDataWalutyWiersza());
                //dzieki temu w wierszu sa dane niezbedne do identyfikacji rozrachunkow
                selected.uzupelnijwierszeodane();
                selected.przeliczKwotyWierszaDoSumyDokumentu();
            } else {
                Msg.msg("w", "Uzupełnij dane przed dodaniem nowego wiersza");
            }
        } else {
            Msg.msg("e", "Brak wpisanego konta/kont. Nie można dodać nowego wiersza");
        }
    }
    

   

    public void dodaj() {
        try {
            selected.getDokfkPK().setPodatnik(wpisView.getPodatnikWpisu());
            UzupelnijWierszeoDane.uzupelnijwierszeodane(selected);
            //nanosimy zapisy na kontach
            NaniesZapisynaKontaFK.naniesZapisyNaKontach(selected);
            selected.dodajKwotyWierszaDoSumyDokumentu(selected.getListawierszy().size()-1);
            dokDAOfk.edit(selected);
            Dokfk dodany = dokDAOfk.findDokfkObj(selected);
            wykazZaksiegowanychDokumentow.add(dodany);
            //utrwalTransakcje();
            Msg.msg("i", "Dokument dodany");
            resetujDokumentWpis();
        } catch (Exception e) {
            Msg.msg("e", "Nie udało się dodac dokumentu " + e.getMessage());
        }
    }
    
    

    public void edycja() {
        try {
            UzupelnijWierszeoDane.uzupelnijwierszeodane(selected);
            NaniesZapisynaKontaFK.naniesZapisyNaKontach(selected);
            selected.setWartoscdokumentu(0.0);
            selected.przeliczKwotyWierszaDoSumyDokumentu();
            dokDAOfk.edit(selected);
            wykazZaksiegowanychDokumentow.clear();
            wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnik(wpisView.getPodatnikWpisu(),wpisView.getRokWpisuSt());
            resetujDokument();
            Msg.msg("i", "Pomyślnie zaktualizowano dokument");
        } catch (Exception e) {
            Msg.msg("e", "Nie udało się zmenic dokumentu " + e.toString());
        }
    }
    
   
    public void edycjaDlaRozrachunkow() {
        System.out.println("lll");
        try {
            UzupelnijWierszeoDane.uzupelnijwierszeodane(selected);
            NaniesZapisynaKontaFK.naniesZapisyNaKontach(selected);
            dokDAOfk.edit(selected);
            wykazZaksiegowanychDokumentow.clear();
            wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnik(wpisView.getPodatnikWpisu(),wpisView.getRokWpisuSt());
            Msg.msg("i", "Pomyślnie zaktualizowano dokument edycja rozrachunow");
        } catch (Exception e) {
            Msg.msg("e", "Nie udało się zmenic dokumentu podczas edycji rozrachunkow " + e.toString());
        }
    }

    public void usundokument(Dokfk dousuniecia) {
        try {
            dokDAOfk.usun(dokDAOfk.findDokfkObj(dousuniecia));
            wykazZaksiegowanychDokumentow.clear();
            wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnik(wpisView.getPodatnikWpisu(),wpisView.getRokWpisuSt());
            resetujDokument();
            RequestContext.getCurrentInstance().update("formwpisdokument");
            Msg.msg("i", "Dokument usunięty");
        } catch (Exception e) {
            Msg.msg("e", "Nie udało się usunąć dokumentu");
        }
    }

     //usuwa wiersze z dokumentu
    public void usunWierszWDokumencie() {
        try {
            if (liczbawierszyWDokumencie > 0) {
                liczbawierszyWDokumencie--;
                selected.getListawierszy().remove(liczbawierszyWDokumencie);
            } 
            if (liczbawierszyWDokumencie == 0) {
                selected.getListawierszy().add(ObslugaWiersza.ustawPierwszyWiersz(selected));
                liczbawierszyWDokumencie++;
            }
            Msg.msg("Wiersz usunięty.");
        } catch (Exception e) {
            Msg.msg("Błąd podczas usuwania wiersz");
        }
    }

//    //***************************************
    public void znajdzduplicatdokumentu() {
        //uruchamiaj tylko jak jest wpisywanie a nie edycja
        if (zapisz0edytuj1 == false) {
            Dokfk dokument = null;
            try {
                dokument = dokDAOfk.findDokfkObj(selected);
            } catch (Exception e) {
            }
            if (dokument != null) {
                RequestContext.getCurrentInstance().execute("document.getElementById('formwpisdokument:numer').select();");
                Msg.msg("e", "Blad dokument o takim numerze juz istnieje");
            } else {
                Msg.msg("i", "Go on Master");
            }
        }
    }

    public void wygenerujokreswpisudokumentu(AjaxBehaviorEvent event) {
        //generuje okres wpisu tylko jezeli jest w trybie wpisu, a wiec zapisz0edytuj1 jest false
        if (zapisz0edytuj1 == false) {
            String data = (String) Params.params("formwpisdokument:dataDialogWpisywanie");
            if (data.length()==10) {
                String rok = data.split("-")[0];
                selected.getDokfkPK().setRok(rok);
                String mc = data.split("-")[1];
                selected.setMiesiac(mc);
                RequestContext.getCurrentInstance().update("formwpisdokument:rok");
                RequestContext.getCurrentInstance().update("formwpisdokument:miesiac");
                Msg.msg("i", "Wygenerowano okres dokumentu");
            }
        }
        RequestContext.getCurrentInstance().execute("chowanienapoczatekdok();");
    }

    public void przygotujDokumentWpisywanie() {
        String skrotnazwydokumentu = selected.getDokfkPK().getSeriadokfk();
        //zeby nadawal nowy numer tylko przy edycji
        if (zapisz0edytuj1 == false) {
            try {
                Dokfk ostatnidokumentdanegorodzaju = dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikWpisu(), skrotnazwydokumentu);
                selected.getDokfkPK().setNrkolejnywserii(ostatnidokumentdanegorodzaju.getDokfkPK().getNrkolejnywserii() + 1);
            } catch (Exception e) {
                selected.getDokfkPK().setNrkolejnywserii(1);
            }
            RequestContext.getCurrentInstance().update("formwpisdokument:numer");
        }
        //pokazuje daty w wierszach
        if (skrotnazwydokumentu.equals("WB")) {
            pokazPanelWalutowy = true;
        } else {
            pokazPanelWalutowy = false;
        }
        RequestContext.getCurrentInstance().update("formwpisdokument:panelwalutowy");
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
        RequestContext.getCurrentInstance().execute("chowanienapoczatekdok();");
    }

    public void przygotujDokumentEdycja() {
        try {
            Msg.msg("i", "Wybrano dokument do edycji " + selected.getDokfkPK().toString());
            setZapisz0edytuj1(true);
            if (selected.getDokfkPK().getSeriadokfk().equals("WB")) {
                pokazPanelWalutowy = true;
            } else {
                pokazPanelWalutowy = false;
            }
            liczbawierszyWDokumencie = selected.getListawierszy().size();
        } catch (Exception e) {
            Msg.msg("e", "Nie wybrano dokumentu do edycji ");
        }
    }
    
    public void przygotujDokumentEdycja(Dokfk item) {
        selected = item;
        try {
            Msg.msg("i", "Wybrano dokument do edycji " + item.getDokfkPK().toString());
            zapisz0edytuj1 = true;
            if (item.getDokfkPK().getSeriadokfk().equals("WB")) {
                pokazPanelWalutowy = true;
            } else {
                pokazPanelWalutowy = false;
            }
            liczbawierszyWDokumencie = item.getListawierszy().size();
        } catch (Exception e) {
            Msg.msg("e", "Nie wybrano dokumentu do edycji ");
        }
    }

//    //samo podswietlanie wiersza jest w javscript on compleyte w menucontext pobiera rzad wiersza z wierszDoPodswietlenia
//    public void znajdzDokumentOznaczWierszDoPodswietlenia() {
//        selected = wiersz.getDokfk();
//        String szukanafrazazzapisu = wiersz.getOpisWiersza();
//        liczbawierszyWDokumencie = selected.getListawierszy().size();
//        List<Wiersze> zawartoscselected = new ArrayList<>();
//        zawartoscselected = selected.getListawierszy();
//        for (Wiersz p : zawartoscselected) {
//            if (szukanafrazazzapisu.equals(p.getOpisWiersza())) {
//                wierszDoPodswietlenia = p.getIdporzadkowy();
//                wierszDoPodswietlenia--;
//            }
//        }
//        setZapisz0edytuj1(true);
//        RequestContext.getCurrentInstance().update("formwpisdokument");
//        RequestContext.getCurrentInstance().update("zestawieniezapisownakontach");
//    }
//
//    //on robi nie tylko to ze przywraca button, on jeszcze resetuje selected
//    //dodalem to tutaj a nie przy funkcji edytuj bo wtedy nie wyswietlalo wiadomosci o edycji
    public void przywrocwpisbutton() {
        setZapisz0edytuj1(false);
        resetujDokument();
        RequestContext.getCurrentInstance().execute("PF('wpisywanie').hide();");
    }

//    //</editor-fold>
    //************************
    //zaznacza po otwaricu rozrachunkow biezaca strone wiersza jako nowa transakcje oraz usuwa po odhaczeniu ze to nowa transakcja
    public void zaznaczOdznaczJakoNowaTransakcja(ValueChangeEvent el) {
        List<StronaWiersza> listaRozliczanych = new ArrayList<>();
        boolean zaznaczonoNowaTransakcje = (boolean) el.getNewValue();
        if (zaznaczonoNowaTransakcje == true) {
            aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(1);
            aktualnyWierszDlaRozrachunkow.setNowatransakcja(true);
            //trzy poniższe wiersze umożliwiają zrobienie z rozliczajacego nowej transakcji po korekcie kwot
            aktualnyWierszDlaRozrachunkow.setRozliczono(0.0);
            aktualnyWierszDlaRozrachunkow.setPozostalo(0.0);
            aktualnyWierszDlaRozrachunkow.setNowetransakcje(new ArrayList<Transakcja>());
            listaRozliczanych.add(aktualnyWierszDlaRozrachunkow);
            zablokujprzyciskrezygnuj = true;
            selected.setLiczbarozliczonych(selected.getLiczbarozliczonych()+1);
            Msg.msg("i", "Dodano bieżący zapis jako nową transakcję");
        } else {
            if (aktualnyWierszDlaRozrachunkow.getRozliczono()>0) {
                Msg.msg("e", "Trasakcja rozliczona - nie można usunąć oznaczenia");
            } else {
                aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(1);
                aktualnyWierszDlaRozrachunkow.setNowatransakcja(false);
                listaRozliczanych.remove(aktualnyWierszDlaRozrachunkow);
                zablokujprzyciskrezygnuj = false;
                selected.setLiczbarozliczonych(selected.getLiczbarozliczonych()-1);
                Msg.msg("i", "Usunięto zapis z listy nowych transakcji");
            }
        }
        if (selected.getLiczbarozliczonych() > 0) {
            selected.setZablokujzmianewaluty(true);
        } else {
            selected.setZablokujzmianewaluty(false);
        }
        //transakcje po zapisaniu dokumentu sa oznaczone jako zaksiegowane, wiec w przypadku edycji, np odhaczeniu ze to nowa transakcja
        //trzeba to odpowienio zaznaczycw rozrachunkach, umowliwa to zmiane rozrachunku w wierszach po kolei bez konieczosci 
        //otwierania dokuemntu co chwile po zmianie jednego wiersza
//        if (zapisz0edytuj1 == true) {
//            edycjaDlaRozrachunkow();
//        }
        RequestContext.getCurrentInstance().update("formwpisdokument:panelwalutowy");
        RequestContext.getCurrentInstance().update("wpisywaniefooter");
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
        RequestContext.getCurrentInstance().update("formwpisdokument:paneldaneogolnefaktury");
    }
    
    public void rachunekPlatnosc() {
        if (rachunekCzyPlatnosc.equals("rachunek")) {
            oznaczJakoRachunek();
        } else {
            oznaczJakoPlatnosc();
        }
    }
    
    public void oznaczJakoPlatnosc() {
        aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(2);
        RequestContext.getCurrentInstance().update("rozrachunki");
        RequestContext.getCurrentInstance().update("formcheckbox:znaczniktransakcji");
        RequestContext.getCurrentInstance().execute("PF('transakcjawybor').hide();");
        RequestContext.getCurrentInstance().execute("PF('rozrachunki').show();");
        String znajdz = "znadzpasujacepolerozrachunku("+aktualnyWierszDlaRozrachunkow.getPozostalo()+")";
        RequestContext.getCurrentInstance().execute(znajdz);
    }
   
    public void oznaczJakoRachunek() {
        aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(1);
        aktualnyWierszDlaRozrachunkow.setNowatransakcja(true);
        RequestContext.getCurrentInstance().execute("PF('transakcjawybor').hide();");
    }
    
    public void tworzenieTransakcjiZWierszy() {
        List<StronaWiersza> swiezezDokumentu = new ArrayList<>();
        List<StronaWiersza> innezBazy = new ArrayList<>();
        List<Transakcja> transakcjeswiezynki = new ArrayList<>();
        List<Transakcja> zachowanewczejsniejtransakcje = new ArrayList<>();
        numerwiersza = Integer.parseInt((String) Params.params("wpisywaniefooter:wierszid"))-1;
        stronawiersza = (String) Params.params("wpisywaniefooter:wnlubma");
        Msg.msg("nr: "+numerwiersza+" wnma: "+stronawiersza);
        zablokujprzyciskzapisz = false;
        try {
            aktualnyWierszDlaRozrachunkow = null;
            aktualnyWierszDlaRozrachunkow = inicjalizacjaAktualnegoWierszaRozrachunkow();
            if (aktualnyWierszDlaRozrachunkow.getTypStronaWiersza() ==0) {
                rachunekCzyPlatnosc = "płatność";
                RequestContext.getCurrentInstance().update("transakcjawybor");
            }
            if (StronaWierszaBean.czyKontoJestRozrachunkowe(aktualnyWierszDlaRozrachunkow, stronawiersza)) {
                boolean onJestNowaTransakcja = aktualnyWierszDlaRozrachunkow.isNowatransakcja();
                biezacetransakcje = new ArrayList<>();
                if (onJestNowaTransakcja == false) {
                    swiezezDokumentu.addAll(DokFKTransakcjeBean.pobierzNoweStronaWierszazDokumentu(aktualnyWierszDlaRozrachunkow.getKonto().getPelnynumer(), stronawiersza, aktualnyWierszDlaRozrachunkow.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty(), selected.getListawierszy()));
                    innezBazy.addAll(DokFKTransakcjeBean.pobierzStronaWierszazBazy(aktualnyWierszDlaRozrachunkow, stronawiersza, stronaWierszaDAO));
                    biezacetransakcje.addAll(DokFKTransakcjeBean.stworznowetransakcjezeZapisanychStronWierszy(swiezezDokumentu, innezBazy, aktualnyWierszDlaRozrachunkow, wpisView.getPodatnikWpisu()));
                    DokFKTransakcjeBean.naniesInformacjezWczesniejRozliczonych(pierwotnailosctransakcjiwbazie, biezacetransakcje, aktualnyWierszDlaRozrachunkow, stronaWierszaDAO);
                } else {
                    //tu trzeba wymyslec cos zeby pokazywac istniejace juz rozliczenia dla NOWA Transakcja
                    biezacetransakcje.addAll(DokFKTransakcjeBean.pobierzbiezaceTransakcjeDlaNowejTransakcji(aktualnyWierszDlaRozrachunkow, stronawiersza));
                    DokFKTransakcjeBean.naniesInformacjezWczesniejRozliczonych(pierwotnailosctransakcjiwbazie, biezacetransakcje, aktualnyWierszDlaRozrachunkow, stronaWierszaDAO);
                    Msg.msg("i", "Jest nową transakcja, pobieram wiersze przeciwne");
                }
                //trzeba zablokować mozliwosc zmiaktualnyWierszDlaRozrachunkowany nowej transakcji jak sa juz rozliczenia!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                String funkcja;
                potraktujjakoNowaTransakcje = aktualnyWierszDlaRozrachunkow.getTypStronaWiersza() == 1;
                if (potraktujjakoNowaTransakcje == true) {
                    //jezeli nowa transakcja jest juz gdzies rozliczona to trzeba zablokowac przycisk
                    double czyjuzrozliczono = aktualnyWierszDlaRozrachunkow.getRozliczono();
                    if (czyjuzrozliczono > 0) {
                        funkcja = "zablokujcheckbox('true');";
                    } else {
                        funkcja = "zablokujcheckbox('false');";
                    }
                    zablokujprzyciskzapisz = true;
                } else {
                    //jezeli w pobranych transakcjach sa juz rozliczenia to trzeba zablokowac mozliwosc zaznaczania nowej transakcji
                    double saWartosciWprowadzone = 0.0;
                    for (Transakcja p : biezacetransakcje) {
                        saWartosciWprowadzone += p.getKwotatransakcji();
                    }
                    if (saWartosciWprowadzone > 0) {
                        funkcja = "zablokujcheckbox('true');";
                    } else {
                        funkcja = "zablokujcheckbox('false');";
                    }
                }
                RequestContext.getCurrentInstance().execute(funkcja);
                RequestContext.getCurrentInstance().update("rozrachunki");
                RequestContext.getCurrentInstance().update("formcheckbox:znaczniktransakcji");
                //zerujemy rzeczy w dialogu
                RequestContext.getCurrentInstance().update("formwpisdokument");
                if (aktualnyWierszDlaRozrachunkow.getTypStronaWiersza()==0) {
                    RequestContext.getCurrentInstance().execute("PF('transakcjawybor').show();");
                } else {
                    RequestContext.getCurrentInstance().execute("PF('rozrachunki').show();");
                    String znajdz = "znadzpasujacepolerozrachunku("+aktualnyWierszDlaRozrachunkow.getPozostalo()+")";
                    RequestContext.getCurrentInstance().execute(znajdz);
                }
            } else {
                Msg.msg("e", "Wybierz najpierw konto rozrachunkowe");
                //zerujemy rzeczy w dialogu
                RequestContext.getCurrentInstance().execute("powrotdopolaPoNaniesieniuRozrachunkow();");
            }
        } catch (Exception e) {
            Msg.msg("e", "Wybierz pole zawierające numer konta");
            //zerujemy rzeczy w dialogu
            RequestContext.getCurrentInstance().execute("powrotdopolaPoNaniesieniuRozrachunkow();");
        }
    }
    
//po wcisnieciu klawisza art-r nastepuje przygotowanie inicjalizacja aktualnego wiersza dla rozrachunkow
    private StronaWiersza inicjalizacjaAktualnegoWierszaRozrachunkow() {
        Wiersz wiersz = selected.getListawierszy().get(numerwiersza);
        StronaWiersza stronaWiersza;
        StronaWiersza stronaWn = wiersz.getStronaWn();
        StronaWiersza stronaMa = wiersz.getStronaMa();
        if (stronawiersza.equals("Wn")) {
            stronaWiersza = stronaWn;
            potraktujjakoNowaTransakcje = stronaWn.isNowatransakcja();
        } else {
            stronaWiersza = stronaMa;
            potraktujjakoNowaTransakcje = stronaMa.isNowatransakcja();
        }
        if (wiersz.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
            if (stronawiersza.equals("Wn")) {
                stronaWn.setKwotaPLN(stronaWn.getKwota());
                stronaWn.setPozostalo(stronaWn.getKwota());
            } else {
                stronaMa.setKwotaPLN(stronaMa.getKwota());
                stronaMa.setPozostalo(stronaMa.getKwota());
            }
        } else {
            if (stronawiersza.equals("Wn")) {
                stronaWn.setKwotaPLN(StronaWierszaBean.przeliczWalutyWn(wiersz));
                stronaWn.setPozostalo(stronaWn.getKwota());
            } else {
                stronaMa.setKwotaPLN(StronaWierszaBean.przeliczWalutyMa(wiersz));
                stronaMa.setPozostalo(stronaMa.getKwota());
            }
        }
        stronaWiersza.setDatarozrachunku(Data.aktualnyDzien());
        stronaWiersza.setWiersz(wiersz);
        Msg.msg("Rozrachunek zainicjalizowany");
        return stronaWiersza;
    }
   
      
      
    public void zapistransakcji() {
        if (aktualnyWierszDlaRozrachunkow.getTypStronaWiersza()!=1) {
            aktualnyWierszDlaRozrachunkow.setTypStronaWiersza(2);
        }
        Iterator it = aktualnyWierszDlaRozrachunkow.getNowetransakcje().iterator();
        while (it.hasNext()) {
            Transakcja tr = (Transakcja) it.next();
            if (tr.getKwotatransakcji() == 0.0) {
                it.remove();
            }
        }
        //to jest potrzebne zeby zmiany w jednym rozrachunku byly widoczne jako naniesione w innym
        if (biezacetransakcje != null) {
            //zanosze ze jest rozliczony
            int iletransakcjidodano = biezacetransakcje.size() - pierwotnailosctransakcjiwbazie;
            boolean saRozrachunki = false;
            if (iletransakcjidodano != 0) {
                selected.setLiczbarozliczonych(selected.getLiczbarozliczonych() + iletransakcjidodano);
            }
            if (selected.getLiczbarozliczonych() > 0) {
                selected.setZablokujzmianewaluty(true);
                saRozrachunki = true;
            } else {
                selected.setZablokujzmianewaluty(false);
            }
            //nie moze tu byc tego bo nie bedzie co utrwalic
            //biezacetransakcje.clear();
            RequestContext.getCurrentInstance().update("formwpisdokument:panelwalutowy");
        }
    }
    

//    
//    //********************
//    //a to jest rodzial dotyczacy walut
//
    public void pobierzkursNBP(ValueChangeEvent el) {
        String nazwawaluty = ((Waluty) el.getNewValue()).getSymbolwaluty();
        symbolwalutydowiersza = ((Waluty) el.getNewValue()).getSymbolwaluty();
        String staranazwa = ((Waluty) el.getOldValue()).getSymbolwaluty();
        if (!staranazwa.equals("PLN") && !nazwawaluty.equals("PLN")) {
            Msg.msg("w", "Prosze przewalutowywać do PLN");
        } else {
            if (!nazwawaluty.equals("PLN")) {
                String datadokumentu = selected.getDatawystawienia();
                DateTime dzienposzukiwany = new DateTime(datadokumentu);
                boolean znaleziono = false;
                int zabezpieczenie = 0;
                while (!znaleziono && (zabezpieczenie < 365)) {
                    dzienposzukiwany = dzienposzukiwany.minusDays(1);
                    String doprzekazania = dzienposzukiwany.toString("yyyy-MM-dd");
                    Tabelanbp tabelanbppobrana = tabelanbpDAO.findByDateWaluta(doprzekazania, nazwawaluty);
                    if (tabelanbppobrana instanceof Tabelanbp) {
                        znaleziono = true;
                        selected.setTabelanbp(tabelanbppobrana);
                        List<Wiersz> wiersze = selected.getListawierszy();
                        for (Wiersz p : wiersze) {
                            p.setTabelanbp(tabelanbppobrana);
                        }
                        break;
                    }
                    zabezpieczenie++;
                }
                pokazRzadWalutowy= true;
            } else {
                Tabelanbp tabelanbpPLN = null;
                try {
                    tabelanbpPLN = tabelanbpDAO.findByDateWaluta("2014-01-01", "PLN");
                } catch (Exception e) {
                    tabelanbpPLN = new Tabelanbp("000/A/NBP/0000",walutyDAOfk.findWalutaBySymbolWaluty("PLN"),"2014-01-01");
                    tabelanbpDAO.dodaj(tabelanbpPLN);
                }
                selected.setTabelanbp(tabelanbpPLN);
                List<Wiersz> wiersze = selected.getListawierszy();
                for (Wiersz p : wiersze) {
                    p.setTabelanbp(tabelanbpPLN);
                }
                pokazRzadWalutowy = false;
            }
            if (staranazwa != null && selected.getListawierszy().get(0).getStronaWn().getKwota() != 0.0) {
                DokFKWalutyBean.przewalutujzapisy(staranazwa, nazwawaluty, selected, walutyDAOfk);
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
                selected.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty));
            } else {
                selected.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty));
                //wpisuje kurs bez przeliczania, to jest dla nowego dokumentu jak sie zmieni walute na euro
                Waluty wybranawaluta = walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty);
            }
            RequestContext.getCurrentInstance().update("formwpisdokument:panelwalutowy");
            RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
        }
    }
    
   
    public void wyliczroznicekursowa(Transakcja loop, int row) {
        double kursAktualny = loop.getRozliczajacy().getWiersz().getTabelanbp().getKurssredni();
        double kursSparowany = loop.getNowaTransakcja().getWiersz().getTabelanbp().getKurssredni();
        if (kursAktualny != 0.0 && kursSparowany != 0.0) {
            String wiersz = "rozrachunki:dataList:"+row+":kwotarozliczenia_input";
            String zwartywiersz = (String) Params.params(wiersz);
            zwartywiersz = zwartywiersz.replaceAll("\\s","");
            if (zwartywiersz.length() > 0) {
                double kwotarozrachunku = Double.parseDouble(zwartywiersz);
                double kwotaAktualnywPLN = kwotarozrachunku * kursAktualny;
                double kwotaSparowanywPLN = kwotarozrachunku * kursSparowany;
                double roznicakursowa = (kwotaAktualnywPLN - kwotaSparowanywPLN)*100;
                roznicakursowa = Math.round(roznicakursowa);
                roznicakursowa /= 100;
                Transakcja analizowanatransakcja = biezacetransakcje.get(row);
                analizowanatransakcja.setRoznicekursowe(roznicakursowa);
                wiersz = "rozrachunki:dataList:"+row+":roznicakursowa";
                RequestContext.getCurrentInstance().update(wiersz);
            }
        }
    }
    
//    //a to jest rodzial dotyczacy walut w wierszu
    public void pobierzkursNBPwiersz(String datawiersza, Wiersz wierszbiezacy) {
        String symbolwaluty = selected.getWalutadokumentu().getSymbolwaluty();
        String datadokumentu = (String) Params.params("formwpisdokument:dataDialogWpisywanie");
        if (datawiersza.length()==1) {
            datawiersza = "0".concat(datawiersza);
        }
        datadokumentu = datadokumentu.substring(0,8).concat(datawiersza);
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
        Waluty wybranawaluta = walutyDAOfk.findWalutaBySymbolWaluty(symbolwaluty);
        //DokFKWalutyBean.uzupelnijwierszprzyprzewalutowaniu(wierszbiezacy.getWierszStronaWn(), wybranawaluta, tabelanbp);
        //DokFKWalutyBean.uzupelnijwierszprzyprzewalutowaniu(wierszbiezacy.getWierszStronaMa(), wybranawaluta, tabelanbp);
    }
    
    public void skopiujWndoMa(Wiersz wiersze) {
        double kwotaWn = wiersze.getStronaWn().getKwota();
        double kwotaMa = wiersze.getStronaMa().getKwota();
        if (kwotaWn!=0 && kwotaMa==0) {
            wiersze.getStronaMa().setKwota(kwotaWn);
        }
    }
    
    public void skopiujKontoZWierszaWyzej(int numerwiersza, String wnma) {
        if (numerwiersza > 0) {
            int numerpoprzedni = numerwiersza - 1;
            StronaWiersza wierszPoprzedni = (wnma.equals("Wn") ? selected.getListawierszy().get(numerpoprzedni).getStronaWn() : selected.getListawierszy().get(numerpoprzedni).getStronaMa());
            StronaWiersza wierszBiezacy = (wnma.equals("Wn") ? selected.getListawierszy().get(numerwiersza).getStronaWn() : selected.getListawierszy().get(numerwiersza).getStronaMa());
            if (!(wierszBiezacy.getKonto() instanceof Konto)) {
                Konto kontoPoprzedni = serialclone.SerialClone.clone(wierszPoprzedni.getKonto());
                wierszBiezacy.setKonto(kontoPoprzedni);
                Msg.msg("Skopiowano konto z wiersza poprzedzającego");
            }
        }
    }

//<editor-fold defaultstate="collapsed" desc="comment">
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

    public int getNumerwiersza() {
        return numerwiersza;
    }

    public void setNumerwiersza(int numerwiersza) {
        this.numerwiersza = numerwiersza;
    }

    public String getStronawiersza() {
        return stronawiersza;
    }

    public void setStronawiersza(String stronawiersza) {
        this.stronawiersza = stronawiersza;
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
    
    public int getLiczbawierszyWDokumencie() {
        return liczbawierszyWDokumencie;
    }
    
    public void setLiczbawierszyWDokumencie(int liczbawierszyWDokumencie) {
        this.liczbawierszyWDokumencie = liczbawierszyWDokumencie;
    }
    
//    public String getWierszid() {
//        return wierszid;
//    }
//    
//    public void setWierszid(String wierszid) {
//        this.wierszid = wierszid;
//    }
//    
//    public String getWnlubma() {
//        return wnlubma;
//    }
//    
//    public void setWnlubma(String wnlubma) {
//        this.wnlubma = wnlubma;
//    }
    
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
    
    public boolean isPotraktujjakoNowaTransakcje() {
        return potraktujjakoNowaTransakcje;
    }
    
    public void setPotraktujjakoNowaTransakcje(boolean potraktujjakoNowaTransakcje) {
        this.potraktujjakoNowaTransakcje = potraktujjakoNowaTransakcje;
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
    
    
    
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="comment">
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
//</editor-fold>

    public boolean isPokazRzadWalutowy() {
        return pokazRzadWalutowy;
    }

    public void setPokazRzadWalutowy(boolean pokazRzadWalutowy) {
        this.pokazRzadWalutowy = pokazRzadWalutowy;
    }
    
    
}
