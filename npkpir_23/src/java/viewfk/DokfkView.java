/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.DokFKBean;
import beansFK.DokFKTransakcjeBean;
import beansFK.DokFKWalutyBean;
import daoFK.DokDAOfk;
import daoFK.RozrachunekfkDAO;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import daoFK.ZestawienielisttransakcjiDAO;
import embeddablefk.Transakcja;
import embeddablefk.WierszStronafk;
import embeddablefk.WierszStronafkPK;
import entityfk.Dokfk;
import entityfk.DokfkPK;
import entityfk.Konto;
import entityfk.Rozrachunekfk;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersze;
import entityfk.Zestawienielisttransakcji;
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
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import params.Params;
import view.WpisView;
import viewfk.subroutines.NaniesZapisynaKontaFK;
import viewfk.subroutines.ObslugaRozrachunku;
import viewfk.subroutines.ObslugaTransakcji;
import viewfk.subroutines.ObslugaWiersza;
import viewfk.subroutines.UzupelnijWierszeoDane;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DokfkView implements Serializable {
    private List<Rozrachunekfk> listaNowychRozrachunkow;
    private int numerwiersza = 0;
    private String stronawiersza;
    protected Dokfk selected;
    @Inject
    private DokDAOfk dokDAOfk;
    private boolean zapisz0edytuj1;
    private String wierszid;
    private String wnlubma;
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
    private boolean potraktujjakoNowaTransakcje;
    @Inject
    private Rozrachunekfk aktualnyWierszDlaRozrachunkow;
    //a to sa listy do sortowanie transakcji na poczatku jedna mega do zbiorki wszystkich dodanych coby nie ginely
    private List<Transakcja> biezacetransakcje;
    private List<Transakcja> transakcjeswiezynki;
    private List<Transakcja> zachowanewczejsniejtransakcje;
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
    private List<String> wprowadzonesymbolewalut;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
    


    public DokfkView() {
        this.wykazZaksiegowanychDokumentow = new ArrayList<>();
        this.listaNowychRozrachunkow = new ArrayList<>();
        this.pierwotnailosctransakcjiwbazie = 0;
        this.biezacetransakcje = new ArrayList<>();
        this.transakcjeswiezynki = new ArrayList<>();
        this.zachowanewczejsniejtransakcje = new ArrayList<>();
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
        List<Waluty> pobranekursy = walutyDAOfk.findAll();
        for (Waluty p : pobranekursy) {
            wprowadzonesymbolewalut.add(p.getSymbolwaluty());
        }
        usunRozrachunkiNiezaksiegowanychDokfk();
    }
    //<editor-fold defaultstate="collapsed" desc="schowane podstawowe funkcje jak dodaj usun itp">

    //********************************************funkcje dla ksiegowania dokumentow
    //RESETUJ DOKUMNETFK
    public void resetujDokument() {
        //kopiuje symbol dokumentu bo nie odkladam go w zmiennej pliku ale dokumentu
        String symbolPoprzedniegoDokumentu = DokFKBean.pobierzSymbolPoprzedniegoDokfk(selected);
        selected = new Dokfk();
        selected.ustawNoweSelected(symbolPoprzedniegoDokumentu);
        biezacetransakcje = null;
        liczbawierszyWDokumencie = 1;
        zapisz0edytuj1 = false;
        zablokujprzyciskrezygnuj = false;
        RequestContext.getCurrentInstance().update("formwpisdokument");
        RequestContext.getCurrentInstance().update("wpisywaniefooter");
        RequestContext.getCurrentInstance().execute("$(document.getElementById('formwpisdokument:datka')).select();");
    }
    
     public void resetujDokumentWpis() {
        //kopiuje symbol dokumentu bo nie odkladam go w zmiennej pliku ale dokumentu
        String symbolPoprzedniegoDokumentu = DokFKBean.pobierzSymbolPoprzedniegoDokfk(selected);
        selected = new Dokfk();
        selected.ustawNoweSelected(symbolPoprzedniegoDokumentu);
        biezacetransakcje = null;
        liczbawierszyWDokumencie = 1;
        zapisz0edytuj1 = false;
        zablokujprzyciskrezygnuj = false;
    }
    
   

    //dodaje wiersze do dokumentu
    public void dolaczNowyWiersz(int wierszbiezacynr) {
        Konto kontowm = selected.getListawierszy().get(wierszbiezacynr).getWierszStronaWn().getKonto();
        Konto kontoma = selected.getListawierszy().get(wierszbiezacynr).getWierszStronaMa().getKonto();
        if (kontowm instanceof Konto && kontoma instanceof Konto) {
            double kwotaWn = 0.0;
            double kwotaMa = 0.0;
            try {
                liczbawierszyWDokumencie = selected.getListawierszy().size();
                kwotaWn = selected.getListawierszy().get(liczbawierszyWDokumencie - 1).getWierszStronaWn().getKwota();
                kwotaMa = selected.getListawierszy().get(liczbawierszyWDokumencie - 1).getWierszStronaMa().getKwota();
            } catch (Exception e) {
                Msg.msg("w", "Uzupełnij dane przed dodaniem nowego wiersza");
            }
            if (kwotaWn != 0 || kwotaMa != 0) {
                liczbawierszyWDokumencie++;
                String walutadokS = selected.getWalutadokumentu();
                if (walutadokS.equals("PLN")) {
                    selected.getListawierszy().add(ObslugaWiersza.utworzNowyWiersz(selected, wpisView.getPodatnikWpisu(), liczbawierszyWDokumencie, "zł"));
                } else {
                    Waluty walutadokumentu = walutyDAOfk.findByName(walutadokS);
                    selected.getListawierszy().add(ObslugaWiersza.utworzNowyWiersz(selected, wpisView.getPodatnikWpisu(), liczbawierszyWDokumencie, walutadokumentu.getSkrotsymbolu()));
                }
                int nowyWiersz = liczbawierszyWDokumencie - 1;
                int poprzedniWiersz = liczbawierszyWDokumencie - 2;
                selected.getListawierszy().get(nowyWiersz).setDatawaluty(selected.getListawierszy().get(poprzedniWiersz).getDatawaluty());
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
    

    //wersja dla pierwszegor rzedu i
    //wersja dla nastepnych

    public void dodajdanedowiersza() {
        if (selected.getListawierszy().size() == 1) {
            int lpwiersza = liczbawierszyWDokumencie - 1;
            WierszStronafk wiersz = selected.getListawierszy().get(0).getWierszStronaWn();
            selected.getListawierszy().get(0).setWierszStronaWn(ObslugaWiersza.uzupelnijdaneWwierszu(selected, 1, wiersz, "Wn", lpwiersza, wpisView.getPodatnikWpisu()));
            wiersz = selected.getListawierszy().get(0).getWierszStronaMa();
            selected.getListawierszy().get(0).setWierszStronaMa(ObslugaWiersza.uzupelnijdaneWwierszu(selected, 1, wiersz, "Ma", lpwiersza, wpisView.getPodatnikWpisu()));
        } else {
            int lpwiersza = liczbawierszyWDokumencie - 1;
            WierszStronafk wiersz = selected.getListawierszy().get(lpwiersza).getWierszStronaWn();
            selected.getListawierszy().get(lpwiersza).setWierszStronaWn(ObslugaWiersza.uzupelnijdaneWwierszu(selected, liczbawierszyWDokumencie, wiersz, "Wn", lpwiersza, wpisView.getPodatnikWpisu()));
            wiersz = selected.getListawierszy().get(lpwiersza).getWierszStronaMa();
            selected.getListawierszy().get(lpwiersza).setWierszStronaMa(ObslugaWiersza.uzupelnijdaneWwierszu(selected, liczbawierszyWDokumencie, wiersz, "Ma", lpwiersza, wpisView.getPodatnikWpisu()));
        }
    }

   

    public void dodaj() {
        try {
            selected.getDokfkPK().setPodatnik(wpisView.getPodatnikWpisu());
            UzupelnijWierszeoDane.uzupelnijwierszeodane(selected);
            //nanosimy zapisy na kontach
            NaniesZapisynaKontaFK.naniesZapisyNaKontach(selected);
            selected.dodajKwotyWierszaDoSumyDokumentu(selected.getListawierszy().size()-1);
            dokDAOfk.dodaj(selected);
            wykazZaksiegowanychDokumentow.add(selected);
            //zazanczamy ze nowe transakcje wprowadzone podczas tworzenia dokumentu maja byc zachowane bo dokument w efekcje zostal zapisany
            List<Rozrachunekfk> listaNowoDodanychRozrachunkow = rozrachunekfkDAO.findByDokfk(selected.getDokfkPK().getSeriadokfk(),selected.getDokfkPK().getNrkolejny(), wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            ObslugaRozrachunku.utrwalNoweRozachunki(listaNowoDodanychRozrachunkow, rozrachunekfkDAO);
            //zaznaczamy sparowae jako wprowadzone i zaksiegowane
            for (Wiersze p : selected.getListawierszy()) {
                ObslugaTransakcji.zaksiegujSparowaneTransakcje(p.getWierszStronaWn(), zestawienielisttransakcjiDAO);
                ObslugaTransakcji.zaksiegujSparowaneTransakcje(p.getWierszStronaMa(), zestawienielisttransakcjiDAO);
            }
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
            int iloscwierszy = dousuniecia.getListawierszy().size();
            for (int i = 0; i < iloscwierszy; i++) {
                try{
                ObslugaRozrachunku.usunrozrachunek(dousuniecia.getListawierszy().get(i).getWierszStronaWn(), rozrachunekfkDAO);
                ObslugaRozrachunku.usunrozrachunek(dousuniecia.getListawierszy().get(i).getWierszStronaMa(), rozrachunekfkDAO);
                ObslugaRozrachunku.usuntransakcje(dousuniecia.getListawierszy().get(i).getWierszStronaWn(), zestawienielisttransakcjiDAO);
                ObslugaRozrachunku.usuntransakcje(dousuniecia.getListawierszy().get(i).getWierszStronaMa(), zestawienielisttransakcjiDAO);
                            Msg.msg("i", "Usunieto rozrachunek");
                } catch (Exception e) {
                    Msg.msg("e", "Nieusunieto rozrachunku");
                }
            }
            dokDAOfk.usun(dousuniecia);
            wykazZaksiegowanychDokumentow.remove(dousuniecia);
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
            if (liczbawierszyWDokumencie > 1) {
                liczbawierszyWDokumencie--;
                ObslugaRozrachunku.usunrozrachunek(selected.getListawierszy().get(liczbawierszyWDokumencie).getWierszStronaWn(), rozrachunekfkDAO);
                ObslugaRozrachunku.usunrozrachunek(selected.getListawierszy().get(liczbawierszyWDokumencie).getWierszStronaMa(), rozrachunekfkDAO);
                ObslugaRozrachunku.usuntransakcje(selected.getListawierszy().get(liczbawierszyWDokumencie).getWierszStronaWn(), zestawienielisttransakcjiDAO);
                ObslugaRozrachunku.usuntransakcje(selected.getListawierszy().get(liczbawierszyWDokumencie).getWierszStronaMa(), zestawienielisttransakcjiDAO);
                selected.getListawierszy().remove(liczbawierszyWDokumencie);
            }
        } catch (Exception e) {
            Msg.msg("Błąd podczas usuwania wiersz");
        }
    }

    //***************************************
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
                selected.getDokfkPK().setNrkolejny(ostatnidokumentdanegorodzaju.getDokfkPK().getNrkolejny() + 1);
            } catch (Exception e) {
                selected.getDokfkPK().setNrkolejny(1);
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
            }
            liczbawierszyWDokumencie = selected.getListawierszy().size();
        } catch (Exception e) {
            Msg.msg("e", "Nie wybrano dokumentu do edycji ");
        }
    }

    //samo podswietlanie wiersza jest w javscript on compleyte w menucontext pobiera rzad wiersza z wierszDoPodswietlenia
    public void znajdzDokumentOznaczWierszDoPodswietlenia() {
        selected = wiersz.getDokfk();
        String szukanafrazazzapisu = wiersz.getOpis();
        liczbawierszyWDokumencie = selected.getListawierszy().size();
        List<Wiersze> zawartoscselected = new ArrayList<>();
        zawartoscselected = selected.getListawierszy();
        for (Wiersze p : zawartoscselected) {
            if (szukanafrazazzapisu.equals(p.getOpis())) {
                wierszDoPodswietlenia = p.getIdporzadkowy();
                wierszDoPodswietlenia--;
            }
        }
        setZapisz0edytuj1(true);
        RequestContext.getCurrentInstance().update("formwpisdokument");
        RequestContext.getCurrentInstance().update("zestawieniezapisownakontach");
    }

    //on robi nie tylko to ze przywraca button, on jeszcze resetuje selected
    //dodalem to tutaj a nie przy funkcji edytuj bo wtedy nie wyswietlalo wiadomosci o edycji
    public void przywrocwpisbutton() {
        setZapisz0edytuj1(false);
        resetujDokument();
        RequestContext.getCurrentInstance().execute("pierwszy.hide();");
    }

    //</editor-fold>
    //************************
    //zaznacza po otwaricu rozrachunkow biezaca strone wiersza jako nowa transakcje oraz usuwa po odhaczeniu ze to nowa transakcja
    public void zaznaczOdznaczJakoNowaTransakcja(ValueChangeEvent el) {
        boolean zaznaczonoNowaTransakcje = (boolean) el.getNewValue();
        if (zaznaczonoNowaTransakcje == true) {
            aktualnyWierszDlaRozrachunkow.setNowatransakcja(true);
            rozrachunekfkDAO.dodaj(aktualnyWierszDlaRozrachunkow);
            listaNowychRozrachunkow.add(aktualnyWierszDlaRozrachunkow);
            zrobWierszStronafkReadOnly(true);
            zablokujprzyciskrezygnuj = true;
            selected.setLiczbarozliczonych(selected.getLiczbarozliczonych()+1);
            Msg.msg("i", "Dodano bieżący zapis jako nową transakcję");
        } else {
            aktualnyWierszDlaRozrachunkow.setNowatransakcja(false);
            rozrachunekfkDAO.destroy(aktualnyWierszDlaRozrachunkow);
            listaNowychRozrachunkow.remove(aktualnyWierszDlaRozrachunkow);
            zrobWierszStronafkReadOnly(false);
            zablokujprzyciskrezygnuj = false;
            selected.setLiczbarozliczonych(selected.getLiczbarozliczonych()-1);
            Msg.msg("i", "Usunięto zapis z listy nowych transakcji");
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

    //porzadkowanie niezaksiegowanych dokumnetow i rozrachunkow z nich
    private void usunRozrachunkiNiezaksiegowanychDokfk() {
        try {
            rozrachunekfkDAO.usunniezaksiegowane();
            zestawienielisttransakcjiDAO.usunniezaksiegowane();
        } catch (Exception e) {
        }
    }
    
    //********************
    //przygotowuje transakcje do wyswietlenia, uruchamiana klawiszami ALT-R
    public void tworzenieTransakcjiZWierszy() {
        //bierzemy parametry przekazane przez javascript po kazdorazowym kliknieciu pola konta
        String wnma = (String) Params.params("wpisywaniefooter:wnlubma");
        String nrwierszaS = (String) Params.params("wpisywaniefooter:wierszid");
        zablokujprzyciskzapisz = false;
        try {
            Integer nrwiersza = Integer.parseInt(nrwierszaS) - 1;
            inicjalizacjaAktualnyWierszDlaRozrachunkow(wnma, nrwiersza);
            if (aktualnyWierszDlaRozrachunkow.getWierszStronafk().getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
                boolean onJestNowaTransakcja = aktualnyWierszDlaRozrachunkow.isNowatransakcja();
                biezacetransakcje = new ArrayList<>();
                transakcjeswiezynki = new ArrayList<>();
                listaNowychRozrachunkow = new ArrayList<>();
                if (onJestNowaTransakcja == false) {
                    listaNowychRozrachunkow.addAll(DokFKTransakcjeBean.pobierzRozrachunekfkzBazy(aktualnyWierszDlaRozrachunkow.getKontoid().getPelnynumer(), wnma, aktualnyWierszDlaRozrachunkow.getWalutarozrachunku(), rozrachunekfkDAO));
                    transakcjeswiezynki.addAll(DokFKTransakcjeBean.stworznowetransakcjezPobranychstronwierszy(listaNowychRozrachunkow, aktualnyWierszDlaRozrachunkow));
                    DokFKTransakcjeBean.pobierzjuzNaniesioneTransakcjeRozliczony(zachowanewczejsniejtransakcje, aktualnyWierszDlaRozrachunkow, zestawienielisttransakcjiDAO);
                    DokFKTransakcjeBean.naniesInformacjezWczesniejRozliczonych(pierwotnailosctransakcjiwbazie, zachowanewczejsniejtransakcje, biezacetransakcje, transakcjeswiezynki, aktualnyWierszDlaRozrachunkow);
                    DokFKTransakcjeBean.pobierzjuzNaniesioneTransakcjeSparowane(listaNowychRozrachunkow, rozrachunekfkDAO, biezacetransakcje);
                } else {
                    Msg.msg("i", "Jest nową transakcja, pobieram wiersze przeciwne");
                    DokFKTransakcjeBean.pobierztransakcjeJakoSparowany(transakcjejakosparowany, biezacetransakcje, zestawienielisttransakcjiDAO, aktualnyWierszDlaRozrachunkow);
                    DokFKTransakcjeBean.sumujdlaNowejTransakcji(transakcjejakosparowany, biezacetransakcje);
                }
                //trzeba zablokować mozliwosc zmiany nowej transakcji jak sa juz rozliczenia!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                potraktujjakoNowaTransakcje = aktualnyWierszDlaRozrachunkow.isNowatransakcja();
                if (potraktujjakoNowaTransakcje == true) {
                    zablokujprzyciskzapisz = true;
                }
                RequestContext.getCurrentInstance().update("rozrachunki");
                RequestContext.getCurrentInstance().update("formcheckbox:znaczniktransakcji");
                //zerujemy rzeczy w dialogu
                wierszid = "";
                wnlubma = "";
                RequestContext.getCurrentInstance().update("formwpisdokument");
                RequestContext.getCurrentInstance().execute("drugishow();");
                String znajdz = "znadzpasujacepolerozrachunku("+aktualnyWierszDlaRozrachunkow.getPozostalo()+")";
                RequestContext.getCurrentInstance().execute(znajdz);
            } else {
                Msg.msg("e", "Wybierz konto rozrachunkowe");
                //zerujemy rzeczy w dialogu
                wierszid = "";
                wnlubma = "";
                RequestContext.getCurrentInstance().execute("powrotdopola();");
            }
        } catch (Exception e) {
            Msg.msg("e", "Wybierz pole zawierające numer konta");
            //zerujemy rzeczy w dialogu
            wierszid = "";
            wnlubma = "";
            RequestContext.getCurrentInstance().execute("powrotdopola();");
        }
    }

    private void inicjalizacjaAktualnyWierszDlaRozrachunkow(String wnma, int nrwiersza) {
        aktualnyWierszDlaRozrachunkow = new Rozrachunekfk();
        WierszStronafk wierszStronafk = new WierszStronafk();
        if (wnma.equals("Wn")) {
            wierszStronafk = selected.getListawierszy().get(nrwiersza).getWierszStronaWn();
            uzupelnikWierszStronafkWaluty(wierszStronafk);
        } else {
            wierszStronafk = selected.getListawierszy().get(nrwiersza).getWierszStronaMa();
            uzupelnikWierszStronafkWaluty(wierszStronafk);
        }
            aktualnyWierszDlaRozrachunkow.setWierszStronafk(wierszStronafk);
            aktualnyWierszDlaRozrachunkow.setKwotapierwotna(wierszStronafk.getKwota());
            aktualnyWierszDlaRozrachunkow.setPozostalo(wierszStronafk.getKwota());
            aktualnyWierszDlaRozrachunkow.setKontoid(wierszStronafk.getKonto());
            aktualnyWierszDlaRozrachunkow.setWalutarozrachunku(wierszStronafk.getSymbolwaluty());
            aktualnyWierszDlaRozrachunkow.setRok(wpisView.getRokWpisuSt());
            aktualnyWierszDlaRozrachunkow.setMc(wpisView.getMiesiacWpisu());
            DateTime dt = new DateTime();
            LocalDate dataRozrachunku = dt.toLocalDate();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
            String formattedDate = formatter.print(dataRozrachunku);
            aktualnyWierszDlaRozrachunkow.setDatarozrachunku(formattedDate);
       //sprawdza czy nowy rozrachunek nie jest juz w bazie, jak jest to go pobiera
        Rozrachunekfk pobranyrozrachunek = rozrachunekfkDAO.findRozrachunekfk(aktualnyWierszDlaRozrachunkow);
        if (pobranyrozrachunek instanceof Rozrachunekfk) {
            aktualnyWierszDlaRozrachunkow = pobranyrozrachunek;
        }
    }
    
    private void uzupelnikWierszStronafkWaluty(WierszStronafk wierszStronafk) {
        String symbolwaluty = wierszStronafk.getSymbolwaluty();
        if (symbolwaluty.equals("PLN")) {
            wierszStronafk.setKwotaPLN(wierszStronafk.getKwota());
        } else {
            wierszStronafk.setKwotaWaluta(wierszStronafk.getKwota());
            double kurs = wierszStronafk.getKurswaluty();
            double kwotazlotowki = wierszStronafk.getKwota();
            kwotazlotowki *= kurs;
            kwotazlotowki *= 100;
            kwotazlotowki = Math.round(kwotazlotowki);
            kwotazlotowki /= 100;
            wierszStronafk.setKwotaPLN(kwotazlotowki);
        }
    }

    
    //*************************
    //nanosi transakcje z kwotami na tworzenieTransakcjiZWierszy
    public void zapistransakcji() {
        //to jest potrzebne zeby zmiany w jednym rozrachunku byly widoczne jako naniesione w innym
        for (Transakcja p : biezacetransakcje) {
            double kwotanowa = p.getKwotatransakcji();
            double kwotastara = p.getPoprzedniakwota();
            double roznicaNkSk = kwotanowa - kwotastara;
            double aktualny_rozliczono = p.getTransakcjaPK().getRozliczany().getRozliczono();
            double aktualny_pierwotna = p.getTransakcjaPK().getRozliczany().getKwotapierwotna();
            double sparowany_rozliczono = p.getTransakcjaPK().getSparowany().getRozliczono();
            double sparowany_pierwotna = p.getTransakcjaPK().getSparowany().getKwotapierwotna();
            if (roznicaNkSk != 0) {
                p.getTransakcjaPK().getRozliczany().setRozliczono(aktualny_rozliczono + roznicaNkSk);
                p.getTransakcjaPK().getRozliczany().setPozostalo(aktualny_pierwotna - p.getTransakcjaPK().getRozliczany().getRozliczono());
                p.getTransakcjaPK().getSparowany().setNowatransakcja(true);
                p.getTransakcjaPK().getSparowany().setRozliczono(sparowany_rozliczono + roznicaNkSk);
                p.getTransakcjaPK().getSparowany().setPozostalo(sparowany_pierwotna - p.getTransakcjaPK().getSparowany().getRozliczono());
            } else if (roznicaNkSk == 0) {
                p.getTransakcjaPK().getSparowany().setNowatransakcja(false);
            }
            p.setPoprzedniakwota(kwotanowa);
            try {
                Rozrachunekfk dotyczyrozrachunku = rozrachunekfkDAO.findRozrachunkifkByWierszStronafk(p.idSparowany());
                if (roznicaNkSk != 0) {
                    dotyczyrozrachunku.setRozliczono(dotyczyrozrachunku.getRozliczono() + roznicaNkSk);
                    dotyczyrozrachunku.setPozostalo(dotyczyrozrachunku.getKwotapierwotna() - dotyczyrozrachunku.getRozliczono());
                }
                rozrachunekfkDAO.edit(dotyczyrozrachunku);
            } catch (Exception r) {
            }
        }
        //usuwam rozliczenie jak wyzerowano transakcje
        Iterator it = biezacetransakcje.iterator();
        while (it.hasNext()) {
            Transakcja p = (Transakcja) it.next();
            if (p.getKwotatransakcji() == 0.0) {
                it.remove();
            }
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
        WierszStronafkPK aktualnywiersz = aktualnyWierszDlaRozrachunkow.getWierszStronafk().getWierszStronafkPK();
        for (Wiersze p : wierszebiezace) {
            if (p.getWierszStronaWn().getWierszStronafkPK().equals(aktualnywiersz)) {
                p.setWnReadOnly(saRozrachunki);
                break;
            } else if (p.getWierszStronaMa().getWierszStronafkPK().equals(aktualnywiersz)) {
                p.setMaReadOnly(saRozrachunki);
                break;
            }
        }
        RequestContext.getCurrentInstance().update("formwpisdokument:panelwalutowy");
        WierszStronafkPK klucz = aktualnyWierszDlaRozrachunkow.getWierszStronafk().getWierszStronafkPK();
        //Nie wiem po co to jest, chyba jakies archeo
//        List<Transakcja> doprzechowania = new ArrayList<>();
//        doprzechowania.addAll(biezacetransakcje);
        //tu zrobie zapis do bazy danych
        try {
            Zestawienielisttransakcji szukana = zestawienielisttransakcjiDAO.findByKlucz(klucz);
            if (szukana instanceof Zestawienielisttransakcji) {
                zestawienielisttransakcjiDAO.destroy(szukana);
            }
            zestawienielisttransakcjiDAO.dodajListeTransakcji(klucz, biezacetransakcje);
            Msg.msg("i", "Udało się zachować rozrachunki w bazie danych");
        } catch (Exception e) {
            Msg.msg("e", "Nie udało się zachować rozrachunków w bazie danych");
        }
        if (zapisz0edytuj1 == true) {
            edycjaDlaRozrachunkow();
        }
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
    }
    
    //********************
    //a to jest rodzial dotyczacy walut

    public void pobierzkursNBP(ValueChangeEvent el) {
        String nazwawaluty = (String) el.getNewValue();
        symbolwalutydowiersza = (String) el.getNewValue();
        String staranazwa = (String) el.getOldValue();
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
                    }
                    zabezpieczenie++;
                }
            }
            if (staranazwa != null && selected.getListawierszy().get(0).getWierszStronaWn().getKwota() != 0.0) {
                DokFKWalutyBean.przewalutujzapisy(staranazwa, nazwawaluty, selected, walutyDAOfk);
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
                selected.setWalutadokumentu(nazwawaluty);
            } else {
                selected.setWalutadokumentu(nazwawaluty);
                //wpisuje kurs bez przeliczania, to jest dla nowego dokumentu jak sie zmieni walute na euro
                Waluty wybranawaluta = walutyDAOfk.findByName(nazwawaluty);
                List<Wiersze> wiersze = selected.getListawierszy();
                for (Wiersze p : wiersze) {
                    DokFKWalutyBean.uzupelnijwierszprzyprzewalutowaniu(p.getWierszStronaWn(), wybranawaluta, selected.getTabelanbp());
                    DokFKWalutyBean.uzupelnijwierszprzyprzewalutowaniu(p.getWierszStronaMa(), wybranawaluta, selected.getTabelanbp());
                }
            }
            RequestContext.getCurrentInstance().update("formwpisdokument:w01");
            RequestContext.getCurrentInstance().update("formwpisdokument:w11");
            RequestContext.getCurrentInstance().update("formwpisdokument:w02");
            RequestContext.getCurrentInstance().update("formwpisdokument:w12");
            RequestContext.getCurrentInstance().update("formwpisdokument:w03");
            RequestContext.getCurrentInstance().update("formwpisdokument:w13");
            pobierzsymbolwaluty();
        }
    }
    
    public void pobierzsymbolwaluty() {
        try {
            List<Waluty> wprowadzonewaluty = walutyDAOfk.findAll();
            if (symbolwalutydowiersza.isEmpty() || symbolwalutydowiersza.equals("PLN")) {
                symbolwalutydowiersza = "zł";
            } else {
                for (Waluty w : wprowadzonewaluty) {
                    if (w.getSymbolwaluty().equals(symbolwalutydowiersza)) {
                        symbolwalutydowiersza = w.getSkrotsymbolu();
                        break;
                    }
                }
            }
            int iloscwierszy = selected.getListawierszy().size();
            for (int i = 0; i < iloscwierszy; i++) {
                String p1 = "formwpisdokument:dataList:" + i + ":symbolWn";
                String p2 = "formwpisdokument:dataList:" + i + ":symbolMa";
                RequestContext.getCurrentInstance().update(p1);
                RequestContext.getCurrentInstance().update(p2);
            }
            RequestContext.getCurrentInstance().execute("chowanienapoczatekdok();");
        } catch (Exception e) {
            Msg.msg("e", "Nie moglem zmienic symbolu waluty");
        }
    }
   
    public void wyliczroznicekursowa(Transakcja loop, int row) {
        double kursAktualny = loop.getTransakcjaPK().getRozliczany().getWierszStronafk().getKurswaluty();
        double kursSparowany = loop.getTransakcjaPK().getSparowany().getWierszStronafk().getKurswaluty();
        String wiersz = "rozrachunki:dataList:"+row+":kwotarozliczenia_input";
        String zwartywiersz = (String) Params.params(wiersz);
        zwartywiersz = zwartywiersz.replaceAll("\\s","");
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
    
    //a to jest rodzial dotyczacy walut w wierszu
    public void pobierzkursNBPwiersz(String datawiersza, Wiersze wierszbiezacy) {
        String nazwawaluty = selected.getWalutadokumentu();
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
            Tabelanbp tabelanbppobrana = tabelanbpDAO.findByDateWaluta(doprzekazania, nazwawaluty);
            if (tabelanbppobrana instanceof Tabelanbp) {
                znaleziono = true;
                tabelanbp = tabelanbppobrana;
            }
            zabezpieczenie++;
        }
        //wpisuje kurs bez przeliczania, to jest dla nowego dokumentu jak sie zmieni walute na euro
        Waluty wybranawaluta = walutyDAOfk.findByName(nazwawaluty);
        DokFKWalutyBean.uzupelnijwierszprzyprzewalutowaniu(wierszbiezacy.getWierszStronaWn(), wybranawaluta, tabelanbp);
        DokFKWalutyBean.uzupelnijwierszprzyprzewalutowaniu(wierszbiezacy.getWierszStronaMa(), wybranawaluta, tabelanbp);
    }
    public void pobierzNumerWnMaWiersza(int numer, String wnma) {
        numerwiersza = numer;
        stronawiersza = wnma;
    }
    
    public void skopiujWndoMa(Wiersze wiersze) {
        double kwotaWn = wiersze.getWierszStronaWn().getKwota();
        double kwotaMa = wiersze.getWierszStronaMa().getKwota();
        if (kwotaWn!=0 && kwotaMa==0) {
            wiersze.getWierszStronaMa().setKwota(kwotaWn);
        }
    }
    
    public void skopiujKontoZWierszaWyzej(int numerwiersza, String wnma) {
        if (numerwiersza > 0) {
            int numerpoprzedni = numerwiersza - 1;
            WierszStronafk wierszPoprzedni = (wnma.equals("wn") ? selected.getListawierszy().get(numerpoprzedni).getWierszStronaWn() : selected.getListawierszy().get(numerpoprzedni).getWierszStronaMa());
            WierszStronafk wierszBiezacy = (wnma.equals("wn") ? selected.getListawierszy().get(numerwiersza).getWierszStronaWn() : selected.getListawierszy().get(numerwiersza).getWierszStronaMa());
            if (!(wierszBiezacy.getKonto() instanceof Konto)) {
                Konto kontoPoprzedni = serialclone.SerialClone.clone(wierszPoprzedni.getKonto());
                wierszBiezacy.setKonto(kontoPoprzedni);
                Msg.msg("Skopiowano konto z wiersza poprzedzającego");
            }
        }
    }

    //Nie wiem o co tu chodzi. chyba ARCHEO. na pewno arche  to mialo powodowac podswieteleni pola z kontem i zabnlokowanie automatycznego przejscia eneterem
//    public void handleKontoSelect(SelectEvent event) {
//        Object item = event.getObject();
//        if (stronawiersza.equals("wn")) {
//            selected.getListawierszy().get(numerwiersza).getWierszStronaWn().setKonto((Konto) item);
//            String pole = "$(document.getElementById('formwpisdokument:dataList:"+numerwiersza+":kontown_input')).select();";
//        } else {
//            selected.getListawierszy().get(numerwiersza).getWierszStronaMa().setKonto((Konto) item);
//        }
//    }
    
//<editor-fold defaultstate="collapsed" desc="comment">
    
    
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
    
    public String getWierszid() {
        return wierszid;
    }
    
    public void setWierszid(String wierszid) {
        this.wierszid = wierszid;
    }
    
    public String getWnlubma() {
        return wnlubma;
    }
    
    public void setWnlubma(String wnlubma) {
        this.wnlubma = wnlubma;
    }
    
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
    
    public List<Rozrachunekfk> getListaNowychRozrachunkow() {
        return listaNowychRozrachunkow;
    }
    
    public void setListaNowychRozrachunkow(List<Rozrachunekfk> listaNowychRozrachunkow) {
        this.listaNowychRozrachunkow = listaNowychRozrachunkow;
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
    
    public List<String> getWprowadzonesymbolewalut() {
        return wprowadzonesymbolewalut;
    }
    
    public void setWprowadzonesymbolewalut(List<String> wprowadzonesymbolewalut) {
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
