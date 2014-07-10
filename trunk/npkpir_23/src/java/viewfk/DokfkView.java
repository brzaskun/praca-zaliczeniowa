/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.DokFKBean;
import beansFK.DokFKTransakcjeBean;
import beansFK.DokFKWalutyBean;
import beansFK.RozrachunekFKBean;
import daoFK.DokDAOfk;
import daoFK.RozrachunekfkDAO;
import daoFK.TabelanbpDAO;
import daoFK.TransakcjaDAO;
import daoFK.WalutyDAOfk;
import daoFK.ZestawienielisttransakcjiDAO;
import entityfk.Transakcja;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.Rozrachunekfk;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersze;
import java.io.Serializable;
import java.util.ArrayList;
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
import viewfk.subroutines.ObslugaRozrachunku;
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
    private boolean zapisz0edytuj1;
//    private String wierszid;
//    private String wnlubma;
    protected int liczbawierszyWDokumencie;
    private List<Dokfk> wykazZaksiegowanychDokumentow;
    //a to jest w dialog_zapisywdokumentach
    @Inject
    private Wiersze wiersz;
    private int wierszDoPodswietlenia;
    //************************************zmienne dla rozrachunkow
    @Inject
    protected RozrachunekfkDAO rozrachunekfkDAO;
    @Inject
    protected ZestawienielisttransakcjiDAO zestawienielisttransakcjiDAO;
    @Inject
    protected TransakcjaDAO transakcjaDAO;
    private boolean potraktujjakoNowaTransakcje;
    @Inject
    private Rozrachunekfk aktualnyWierszDlaRozrachunkow;
    //a to sa listy do sortowanie transakcji na poczatku jedna mega do zbiorki wszystkich dodanych coby nie ginely
    private List<Transakcja> biezacetransakcje;
    private List<Transakcja> transakcjejakosparowany;
    private boolean zablokujprzyciskzapisz;
    private boolean zablokujprzyciskrezygnuj;
    private int pierwotnailosctransakcjiwbazie;
    private boolean pokazPanelWalutowy;
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
        pokazPanelWalutowy = false;
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
        selected = new Dokfk();
        selected.ustawNoweSelected(symbolPoprzedniegoDokumentu, wpisView.getPodatnikWpisu());
        try {
            selected.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty("PLN"));
            Tabelanbp tabelanbpPLN = tabelanbpDAO.findByTabelaPLN();
            selected.setTabelanbp(tabelanbpPLN);
            List<Wiersze> wiersze = selected.getListawierszy();
            for (Wiersze p : wiersze) {
                p.setTabelanbp(tabelanbpPLN);
            }
            pokazPanelWalutowy = false;
            biezacetransakcje = null;
            liczbawierszyWDokumencie = 1;
            zapisz0edytuj1 = false;
            zablokujprzyciskrezygnuj = false;
        } catch (Exception e) {
            Msg.msg("e", "Brak tabeli w danej walucie. Sprawdź to.");
        }
        RequestContext.getCurrentInstance().update("formwpisdokument");
        RequestContext.getCurrentInstance().update("wpisywaniefooter");
        RequestContext.getCurrentInstance().execute("$(document.getElementById('formwpisdokument:datka')).select();");
    }
    
     public void resetujDokumentWpis() {
        //kopiuje symbol dokumentu bo nie odkladam go w zmiennej pliku ale dokumentu
        String symbolPoprzedniegoDokumentu = DokFKBean.pobierzSymbolPoprzedniegoDokfk(selected);
        selected = new Dokfk();
        selected.ustawNoweSelected(symbolPoprzedniegoDokumentu, wpisView.getPodatnikWpisu());
        selected.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty("PLN"));
        Tabelanbp tabelanbpPLN = tabelanbpDAO.findByTabelaPLN();
        selected.setTabelanbp(tabelanbpPLN);
        List<Wiersze> wiersze = selected.getListawierszy();
        for (Wiersze p : wiersze) {
            p.setTabelanbp(tabelanbpPLN);
        }
        pokazPanelWalutowy = false;
        biezacetransakcje = null;
        liczbawierszyWDokumencie = 1;
        zapisz0edytuj1 = false;
        zablokujprzyciskrezygnuj = false;
    }
    
   

//    //dodaje wiersze do dokumentu
    public void dolaczNowyWiersz(int wierszbiezacynr) {
        Konto kontowm = selected.getListawierszy().get(wierszbiezacynr).getKontoWn();
        Konto kontoma = selected.getListawierszy().get(wierszbiezacynr).getKontoMa();
        if (kontowm instanceof Konto && kontoma instanceof Konto) {
            double kwotaWn = 0.0;
            double kwotaMa = 0.0;
            try {
                liczbawierszyWDokumencie = selected.getListawierszy().size();
                kwotaWn = selected.getListawierszy().get(liczbawierszyWDokumencie - 1).getKwotaWn();
                kwotaMa = selected.getListawierszy().get(liczbawierszyWDokumencie - 1).getKwotaMa();
            } catch (Exception e) {
                Msg.msg("w", "Uzupełnij dane przed dodaniem nowego wiersza");
            }
            if (kwotaWn != 0 || kwotaMa != 0) {
                liczbawierszyWDokumencie += 1;
                selected.getListawierszy().add(ObslugaWiersza.utworzNowyWiersz(selected, wpisView.getPodatnikWpisu(), liczbawierszyWDokumencie));
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
            //Dokfk obiekt = dokDAOfk.findDokfkObj(dousuniecia);
            //dokDAOfk.usun(obiekt);
            dokDAOfk.usun(dousuniecia);
            wykazZaksiegowanychDokumentow.remove(dousuniecia);
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
                selected.getListawierszy().add(ObslugaWiersza.ustawNowyWiersz(selected));
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
        if (zapisz0edytuj1 == false) {
            String data = (String) Params.params("formwpisdokument:datka");
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
            setZapisz0edytuj1(true);
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
//        for (Wiersze p : zawartoscselected) {
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
        List<Rozrachunekfk> listaRozliczanych = new ArrayList<>();
        boolean zaznaczonoNowaTransakcje = (boolean) el.getNewValue();
        if (zaznaczonoNowaTransakcje == true) {
            aktualnyWierszDlaRozrachunkow.setNowatransakcja(true);
            listaRozliczanych.add(aktualnyWierszDlaRozrachunkow);
            zrobWierszStronafkReadOnly(true);
            zablokujprzyciskrezygnuj = true;
            selected.setLiczbarozliczonych(selected.getLiczbarozliczonych()+1);
            Msg.msg("i", "Dodano bieżący zapis jako nową transakcję");
        } else {
            if (aktualnyWierszDlaRozrachunkow.getRozliczono()>0) {
                Msg.msg("e", "Trasakcja rozliczona - nie można usunąć oznaczenia");
            } else {
                aktualnyWierszDlaRozrachunkow.setNowatransakcja(false);
                listaRozliczanych.remove(aktualnyWierszDlaRozrachunkow);
                zrobWierszStronafkReadOnly(false);
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
        if (zapisz0edytuj1 == true) {
            edycjaDlaRozrachunkow();
        }
        RequestContext.getCurrentInstance().update("formwpisdokument:panelwalutowy");
        RequestContext.getCurrentInstance().update("wpisywaniefooter");
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
        RequestContext.getCurrentInstance().update("formwpisdokument:paneldaneogolnefaktury");
    }
    
    //Nie wiem dlaczego to robie
    private void zrobWierszStronafkReadOnly(boolean wartosc){
//        List<Wiersze> wierszebiezace = selected.getListawierszy();
//        WierszStronafkPK aktualnywiersz = aktualnyWierszDlaRozrachunkow.getWierszStronafk().getWierszStronafkPK();
//        for (Wiersze p : wierszebiezace) {
//            if (p.getWierszStronaWn().getWierszStronafkPK().equals(aktualnywiersz)) {
//                p.setWnReadOnly(wartosc);
//                int i = p.getIdporzadkowy() - 1;
//                String wiersz = String.format("formwpisdokument:dataList:%s:wnReadOnly", i);
//                RequestContext.getCurrentInstance().update(wiersz);
//                break;
//            }
//            if (p.getWierszStronaMa().getWierszStronafkPK().equals(aktualnywiersz)) {
//                p.setMaReadOnly(wartosc);
//                int i = p.getIdporzadkowy() - 1;
//                String wiersz = String.format("formwpisdokument:dataList:%s:maReadOnly", i);
//                RequestContext.getCurrentInstance().update(wiersz);
//                break;
//            }
//        }
    }

//    //porzadkowanie niezaksiegowanych dokumnetow i rozrachunkow z nich
//    private void usunRozrachunkiNiezaksiegowanychDokfk() {
//        try {
//            rozrachunekfkDAO.usunniezaksiegowane(wpisView.getPodatnikWpisu());
//            transakcjaDAO.usunniezaksiegowane(wpisView.getPodatnikWpisu());
//        } catch (Exception e) {
//        }
//    }
//    
//    
    public void tworzenieTransakcjiZWierszy() {
        List<Rozrachunekfk> listaRozliczanych = new ArrayList<>();
        List<Transakcja> transakcjeswiezynki = new ArrayList<>();
        List<Transakcja> zachowanewczejsniejtransakcje = new ArrayList<>();
        numerwiersza = Integer.parseInt((String) Params.params("wpisywaniefooter:wierszid"))-1;
        stronawiersza = (String) Params.params("wpisywaniefooter:wnlubma");
        Msg.msg("nr: "+numerwiersza+" wnma: "+stronawiersza);
        zablokujprzyciskzapisz = false;
        try {
            inicjalizacjaAktualnegoWierszaRozrachunkow();
            boolean czyKontoJestRozrachunkowe = aktualnyWierszDlaRozrachunkow.getKontoid().getZwyklerozrachszczegolne().equals("rozrachunkowe");
            if (czyKontoJestRozrachunkowe) {
                boolean onJestNowaTransakcja = aktualnyWierszDlaRozrachunkow.isNowatransakcja();
                biezacetransakcje = new ArrayList<>();
                if (onJestNowaTransakcja == false) {
                    listaRozliczanych.addAll(DokFKTransakcjeBean.pobierzRozrachunekfkzDokumentu(aktualnyWierszDlaRozrachunkow.getKontoid().getPelnynumer(), stronawiersza, aktualnyWierszDlaRozrachunkow.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty(), selected.getListawierszy()));
                    listaRozliczanych.addAll(DokFKTransakcjeBean.pobierzRozrachunekfkzBazy(aktualnyWierszDlaRozrachunkow.getKontoid(), stronawiersza,  aktualnyWierszDlaRozrachunkow.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty(), rozrachunekfkDAO));
                    transakcjeswiezynki.addAll(DokFKTransakcjeBean.stworznowetransakcjezPobranychstronwierszy(listaRozliczanych, aktualnyWierszDlaRozrachunkow, wpisView.getPodatnikWpisu()));
                    zachowanewczejsniejtransakcje.addAll(DokFKTransakcjeBean.pobierzjuzNaniesioneTransakcjeRozliczony(transakcjaDAO, aktualnyWierszDlaRozrachunkow, zestawienielisttransakcjiDAO));
                    biezacetransakcje.addAll(DokFKTransakcjeBean.naniesInformacjezWczesniejRozliczonych(pierwotnailosctransakcjiwbazie, zachowanewczejsniejtransakcje, transakcjeswiezynki, aktualnyWierszDlaRozrachunkow));
                } else {
                    //tu trzeba wymyslec cos zeby pokazywac istniejace juz rozliczenia dla NOWA Transakcja
                    if (aktualnyWierszDlaRozrachunkow.getIdrozrachunku() != null) {
                        biezacetransakcje.addAll(DokFKTransakcjeBean.pobierzbiezaceTransakcjeDlaNowejTransakcji(transakcjaDAO, aktualnyWierszDlaRozrachunkow.getIdrozrachunku()));
                    }
                    Msg.msg("i", "Jest nową transakcja, pobieram wiersze przeciwne");
                }
                //trzeba zablokować mozliwosc zmiaktualnyWierszDlaRozrachunkowany nowej transakcji jak sa juz rozliczenia!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                String funkcja;
                potraktujjakoNowaTransakcje = aktualnyWierszDlaRozrachunkow.isNowatransakcja();
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
                numerwiersza = 0;
                stronawiersza = "";
                RequestContext.getCurrentInstance().update("formwpisdokument");
                RequestContext.getCurrentInstance().execute("rozrachunkiShow();");
                String znajdz = "znadzpasujacepolerozrachunku("+aktualnyWierszDlaRozrachunkow.getPozostalo()+")";
                RequestContext.getCurrentInstance().execute(znajdz);
            } else {
                Msg.msg("e", "Wybierz konto rozrachunkowe");
                //zerujemy rzeczy w dialogu
                numerwiersza = 0;
                stronawiersza = "";
                RequestContext.getCurrentInstance().execute("powrotdopolaPoNaniesieniuRozrachunkow();");
            }
        } catch (Exception e) {
            Msg.msg("e", "Wybierz pole zawierające numer konta");
            //zerujemy rzeczy w dialogu
                numerwiersza = 0;
                stronawiersza = "";
            RequestContext.getCurrentInstance().execute("powrotdopolaPoNaniesieniuRozrachunkow();");
        }
    }
    
//po wcisnieciu klawisza art-r nastepuje przygotowanie inicjalizacja aktualnego wiersza dla rozrachunkow
    private void inicjalizacjaAktualnegoWierszaRozrachunkow() {
        if (stronawiersza.equals("Wn")) {
            aktualnyWierszDlaRozrachunkow = selected.getListawierszy().get(numerwiersza).getRozrachunekfkWn();
        } else {
            aktualnyWierszDlaRozrachunkow = selected.getListawierszy().get(numerwiersza).getRozrachunekfkMa();
        }
        if (!(aktualnyWierszDlaRozrachunkow instanceof Rozrachunekfk)) {
            potraktujjakoNowaTransakcje = false;
            aktualnyWierszDlaRozrachunkow = RozrachunekFKBean.konstruktorAktualnegoWierszaDlaRozrachunkow(selected, wpisView, stronawiersza, numerwiersza);
            Msg.msg("Utworzyłem nowy rozrachunek");
        } else {
            potraktujjakoNowaTransakcje = aktualnyWierszDlaRozrachunkow.isNowatransakcja();
            RozrachunekFKBean.aktualizatorAktualnegoWierszaDlaRozrachunkow(aktualnyWierszDlaRozrachunkow, selected, wpisView, stronawiersza, numerwiersza);
            Msg.msg("Otworzyłem zachowany rozrachunek rozrachunek");
        }
    }
   
      
      
    public void zapistransakcji() {
        //to jest potrzebne zeby zmiany w jednym rozrachunku byly widoczne jako naniesione w innym
        if (biezacetransakcje != null) {
            for (Transakcja p : biezacetransakcje) {
                double kwotanowa = p.getKwotatransakcji();
                double kwotastara = p.getPoprzedniakwota();
                double roznicaNkSk = kwotanowa - kwotastara;
                if (roznicaNkSk != 0) {
                    double aktualny_rozliczono = p.getRozliczany().getRozliczono();
                    double aktualny_pierwotna = p.getRozliczany().getKwotapierwotna();
                    double sparowany_rozliczono = p.getSparowany().getRozliczono();
                    double sparowany_pierwotna = p.getSparowany().getKwotapierwotna();
                    p.getRozliczany().setRozliczono(aktualny_rozliczono + roznicaNkSk);
                    p.getRozliczany().setPozostalo(aktualny_pierwotna - p.getRozliczany().getRozliczono());
                    //p.getSparowany().setNowatransakcja(true);
                    p.getSparowany().setRozliczono(sparowany_rozliczono + roznicaNkSk);
                    p.getSparowany().setPozostalo(sparowany_pierwotna - p.getSparowany().getRozliczono());
                    //nanieslismy zmiany w biezacyh transakcjach coby wyswietlic, a potem robimy to w bazie danych
                } else if (roznicaNkSk == 0) {
                    //p.getSparowany().setNowatransakcja(false);
                }
                p.setPoprzedniakwota(kwotanowa);
                //to jest w zasadzie tzw. Nowa Transakcja
            }
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
            // to ma blokowac zmianie kwot gdzie sa tworzenieTransakcjiZWierszy
            List<Wiersze> wierszebiezace = selected.getListawierszy();
            Wiersze wiersz = aktualnyWierszDlaRozrachunkow.getWiersz();
            for (Wiersze p : wierszebiezace) {
                if (p == wiersz && aktualnyWierszDlaRozrachunkow.getStronaWnlubMa().equals("Wn")) {
                    p.setWnReadOnly(true);
                    break;
                } else if (p == wiersz && aktualnyWierszDlaRozrachunkow.getStronaWnlubMa().equals("Ma")) {
                    p.setMaReadOnly(true);
                    break;
                }
            }
            if (aktualnyWierszDlaRozrachunkow.isNowatransakcja()) {
                aktualnyWierszDlaRozrachunkow.setTransakcjaSparowany(biezacetransakcje);
            } else {
                aktualnyWierszDlaRozrachunkow.setTransakcjaRozliczany(biezacetransakcje);
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
                        List<Wiersze> wiersze = selected.getListawierszy();
                        for (Wiersze p : wiersze) {
                            p.setTabelanbp(tabelanbppobrana);
                        }
                    }
                    zabezpieczenie++;
                }
                pokazPanelWalutowy = true;
            } else {
                Tabelanbp tabelanbpPLN = new Tabelanbp("000/A/NBP/0000",walutyDAOfk.findWalutaBySymbolWaluty("PLN"),"2014-01-01");
                selected.setTabelanbp(tabelanbpPLN);
                List<Wiersze> wiersze = selected.getListawierszy();
                for (Wiersze p : wiersze) {
                    p.setTabelanbp(tabelanbpPLN);
                }
                pokazPanelWalutowy = false;
            }
            if (staranazwa != null && selected.getListawierszy().get(0).getKwotaWn() != 0.0) {
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
            //pobierzsymbolwaluty(); archeo bo info jest we wierszu
        }
    }
    
//    public void pobierzsymbolwaluty() {
//        try {
//            List<Waluty> wprowadzonewaluty = walutyDAOfk.findAll();
//            if (symbolwalutydowiersza.isEmpty() || symbolwalutydowiersza.equals("PLN")) {
//                symbolwalutydowiersza = "zł";
//            } else {
//                for (Waluty w : wprowadzonewaluty) {
//                    if (w.getSymbolwaluty().equals(symbolwalutydowiersza)) {
//                        symbolwalutydowiersza = w.getSkrotsymbolu();
//                        break;
//                    }
//                }
//            }
//            int iloscwierszy = selected.getListawierszy().size();
//            for (int i = 0; i < iloscwierszy; i++) {
//                String p1 = "formwpisdokument:dataList:" + i + ":symbolWn";
//                String p2 = "formwpisdokument:dataList:" + i + ":symbolMa";
//                RequestContext.getCurrentInstance().update(p1);
//                RequestContext.getCurrentInstance().update(p2);
//            }
//            RequestContext.getCurrentInstance().execute("chowanienapoczatekdok();");
//        } catch (Exception e) {
//            Msg.msg("e", "Nie moglem zmienic symbolu waluty");
//        }
//    }
   
    public void wyliczroznicekursowa(Transakcja loop, int row) {
        double kursAktualny = loop.getRozliczany().getWiersz().getTabelanbp().getKurssredni();
        double kursSparowany = loop.getSparowany().getWiersz().getTabelanbp().getKurssredni();
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
    public void pobierzkursNBPwiersz(String datawiersza, Wiersze wierszbiezacy) {
        String symbolwaluty = selected.getWalutadokumentu().getSymbolwaluty();
        String datadokumentu = (String) Params.params("formwpisdokument:datka");
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
    
    public void skopiujWndoMa(Wiersze wiersze) {
        double kwotaWn = wiersze.getKwotaWn();
        double kwotaMa = wiersze.getKwotaMa();
        if (kwotaWn!=0 && kwotaMa==0) {
            wiersze.setKwotaMa(kwotaWn);
        }
    }
    
    public void skopiujKontoZWierszaWyzej(int numerwiersza, String wnma) {
        if (numerwiersza > 0) {
            int numerpoprzedni = numerwiersza - 1;
            //WierszStronafk wierszPoprzedni = (wnma.equals("Wn") ? selected.getListawierszy().get(numerpoprzedni).getWierszStronaWn() : selected.getListawierszy().get(numerpoprzedni).getWierszStronaMa());
            //WierszStronafk wierszBiezacy = (wnma.equals("Wn") ? selected.getListawierszy().get(numerwiersza).getWierszStronaWn() : selected.getListawierszy().get(numerwiersza).getWierszStronaMa());
//            if (!(wierszBiezacy.getKonto() instanceof Konto)) {
//                Konto kontoPoprzedni = serialclone.SerialClone.clone(wierszPoprzedni.getKonto());
//                wierszBiezacy.setKonto(kontoPoprzedni);
//                Msg.msg("Skopiowano konto z wiersza poprzedzającego");
//            }
        }
    }

    //Nie wiem o co tu chodzi. chyba ARCHEO. na pewno arche  to mialo powodowac podswieteleni pola z kontem i zabnlokowanie automatycznego przejscia eneterem
//    public void handleKontoSelect(SelectEvent event) {
//        Object item = event.getObject();
//        if (stronawiersza.equals("Wn")) {
//            selected.getListawierszy().get(numerwiersza).getWierszStronaWn().setKonto((Konto) item);
//            String pole = "$(document.getElementById('formwpisdokument:dataList:"+numerwiersza+":kontown_input')).select();";
//        } else {
//            selected.getListawierszy().get(numerwiersza).getWierszStronaMa().setKonto((Konto) item);
//        }
//    }
//<editor-fold defaultstate="collapsed" desc="comment">
    
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
    
    public Wiersze getWiersz() {
        return wiersz;
    }
    
    public void setWiersz(Wiersze wiersz) {
        this.wiersz = wiersz;
    }
    
    public Rozrachunekfk getAktualnyWierszDlaRozrachunkow() {
        return aktualnyWierszDlaRozrachunkow;
    }
    
    public void setAktualnyWierszDlaRozrachunkow(Rozrachunekfk aktualnyWierszDlaRozrachunkow) {
        this.aktualnyWierszDlaRozrachunkow = aktualnyWierszDlaRozrachunkow;
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
    public static void main(String[] args) {
        String staranazwa = "EUR";
        String nazwawaluty = "PLN";
        double kurs = 4.189;
        if (!staranazwa.equals("PLN")) {
            kurs = 1 / kurs * 100000000;
            kurs = Math.round(kurs);
            kurs = kurs / 100000000;
        }
        double kwota = 100000;
        kwota = Math.round(kwota * kurs * 10000);
        kwota = kwota / 10000;
        System.out.println(kwota);
        staranazwa = "PLN";
        nazwawaluty = "EUR";
        kurs = 4.189;
        kwota = Math.round(kwota * kurs * 100);
        kwota = kwota / 100;
}        
//</editor-fold>
}
