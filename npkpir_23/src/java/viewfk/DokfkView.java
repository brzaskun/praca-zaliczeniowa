/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.DokDAOfk;
import daoFK.RozrachunekfkDAO;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import daoFK.ZestawienielisttransakcjiDAO;
import entityfk.Rozrachunekfk;
import embeddablefk.Transakcja;
import embeddablefk.WierszStronafk;
import embeddablefk.WierszStronafkPK;
import entityfk.Dokfk;
import entityfk.DokfkPK;
import entityfk.Konto;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersze;
import entityfk.Zestawienielisttransakcji;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EventListener;
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
import org.primefaces.event.SelectEvent;
import params.Params;
import view.WpisView;
import viewfk.subroutines.NaniesZapisynaKontaFK;
import viewfk.subroutines.UzupelnijWierszeoDane;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DokfkView implements Serializable {
    private static List<Rozrachunekfk> rozrachunekNowaTransakcja;
    private static int numerwiersza = 0;
    private static String stronawiersza;

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
        System.out.println(kwota);
    }

    private Dokfk selected;
    @Inject
    private DokDAOfk dokDAOfk;
    private boolean zapisz0edytuj1;
    private String wierszid;
    private String wnlubma;
    private int liczbawierszy;
    private List<Dokfk> wykazZaksiegowanychDokumentow;
    //a to jest w dialog_zapisywdokumentach
    @Inject
    private Wiersze wiersz;
    private int numerwierszazapisu;
    //************************************zmienne dla rozrachunkow
    @Inject
    private RozrachunekfkDAO rozrachunekfkDAO;
    @Inject
    private ZestawienielisttransakcjiDAO zestawienielisttransakcjiDAO;
    private boolean potraktujjakoNowaTransakcje;
    @Inject
    private Rozrachunekfk aktualnywierszdorozrachunkow;
    //a to sa listy do sortowanie transakcji na poczatku jedna mega do zbiorki wszystkich dodanych coby nie ginely
    private List<Transakcja> biezacetransakcje;
    private List<Transakcja> transakcjeswiezynki;
    private List<Transakcja> zachowanewczejsniejtransakcje;
    private List<Transakcja> transakcjejakosparowany;
    private boolean zablokujprzyciskzapisz;
    private boolean zablokujprzyciskrezygnuj;
    private int pierwotnailosctransakcjiwbazie;
    private boolean zablokujpanelwalutowy;
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
        resetujDokument();
        this.wykazZaksiegowanychDokumentow = new ArrayList<>();
        rozrachunekNowaTransakcja = new ArrayList<>();
        this.pierwotnailosctransakcjiwbazie = 0;
        this.biezacetransakcje = new ArrayList<>();
        this.transakcjeswiezynki = new ArrayList<>();
        this.zachowanewczejsniejtransakcje = new ArrayList<>();
        this.transakcjejakosparowany = new ArrayList<>();
        this.zablokujprzyciskzapisz = false;
        this.potraktujjakoNowaTransakcje = false;
        this.wprowadzonesymbolewalut = new ArrayList<>();
        this.symbolwalutydowiersza = "";
    }

    @PostConstruct
    private void init() {
        try {
            wykazZaksiegowanychDokumentow = dokDAOfk.findAll();
        } catch (Exception e) {
        }
        List<Waluty> pobranekursy = walutyDAOfk.findAll();
        for (Waluty p : pobranekursy) {
            wprowadzonesymbolewalut.add(p.getSymbolwaluty());
        }
        zapisz0edytuj1 = false;
        usunrozrachunkiniezaksiegowanychdok();
    }
    //<editor-fold defaultstate="collapsed" desc="schowane podstawowe funkcje jak dodaj usun itp">

    //********************************************funkcje dla ksiegowania dokumentow
    //RESETUJ DOKUMNETFK
    public void resetujDokument() {
        biezacetransakcje = null;
        //kopiuje symbol dokumentu bo nie odkladam go w zmiennej pliku ale dokumentu
        String symbolPoprzedniegoDokumentu ="";
        try {
            symbolPoprzedniegoDokumentu = new String(selected.getDokfkPK().getSeriadokfk());
        } catch (Exception e) {}
        selected = new Dokfk();
        DokfkPK dokfkPK = new DokfkPK();
        dokfkPK.setSeriadokfk(symbolPoprzedniegoDokumentu);
        selected.setDokfkPK(dokfkPK);
        List<Wiersze> wiersze = new ArrayList<>();
        Wiersze nowywiersz = new Wiersze(1, 0);
        WierszStronafk wierszStronafkWn = new WierszStronafk();
        wierszStronafkWn.setGrafikawaluty("zł");
        nowywiersz.setWierszStronaWn(wierszStronafkWn);
        nowywiersz.getWierszStronaWn().getWierszStronafkPK().setNrPorzadkowyWiersza(1);
        WierszStronafk wierszStronafkMa = new WierszStronafk();
        wierszStronafkMa.setGrafikawaluty("zł");
        nowywiersz.setWierszStronaMa(wierszStronafkMa);
        nowywiersz.getWierszStronaMa().getWierszStronafkPK().setNrPorzadkowyWiersza(1);
        wiersze.add(nowywiersz);
        selected.setWalutadokumentu("PLN");
        selected.setKonta(wiersze);
        selected.setZablokujzmianewaluty(false);
        liczbawierszy = 1;
        zapisz0edytuj1 = false;
        zablokujprzyciskrezygnuj = false;
        RequestContext.getCurrentInstance().update("formwpisdokument");
        RequestContext.getCurrentInstance().update("wpisywaniefooter");
        RequestContext.getCurrentInstance().execute("$('#formwpisdokument\\\\:datka').select();");
    }

    //dodaje wiersze do dokumentu
    public void liczbaw() {
        double pierwsze = 0.0;
        double drugie = 0.0;
        try {
            liczbawierszy = selected.getKonta().size();
            pierwsze = selected.getKonta().get(liczbawierszy - 1).getWierszStronaWn().getKwota();
            drugie = selected.getKonta().get(liczbawierszy - 1).getWierszStronaMa().getKwota();
        } catch (Exception e) {
            Msg.msg("w", "Uzupełnij dane przed dodaniem nowego wiersza");
        }
        if (pierwsze != 0 || drugie != 0) {
            liczbawierszy++;
            String walutadokS = selected.getWalutadokumentu();
            if (walutadokS.equals("PLN")) {
                selected.getKonta().add(utworzNowyWiersz("zł"));
            } else {
                Waluty walutadokumentu = walutyDAOfk.findByName(walutadokS);
                selected.getKonta().add(utworzNowyWiersz(walutadokumentu.getSkrotsymbolu()));
            }
            selected.getKonta().get(liczbawierszy-1).setDatawaluty(selected.getKonta().get(liczbawierszy-2).getDatawaluty());
            selected.uzupelnijwierszeodane();
            selected.dodajwartoscwiersza(liczbawierszy-2);
        } else {
            Msg.msg("w", "Uzuwpełnij dane przed dodaniem nowego wiersza");
        }
    }
    


    private Wiersze utworzNowyWiersz(String grafikawaluty) {
        Wiersze nowywiersz = new Wiersze(liczbawierszy, 0);
        WierszStronafk wierszStronafkWn = new WierszStronafk();
        WierszStronafkPK wierszStronafkPKWn = new WierszStronafkPK();
        wierszStronafkWn.setWierszStronafkPK(dodajdanedowiersza(liczbawierszy, wierszStronafkPKWn, "Wn"));
        wierszStronafkWn.setGrafikawaluty(grafikawaluty);
        nowywiersz.setWierszStronaWn(wierszStronafkWn);
        WierszStronafk wierszStronafkMa = new WierszStronafk();
        WierszStronafkPK wierszStronafkPKMa = new WierszStronafkPK();
        wierszStronafkMa.setWierszStronafkPK(dodajdanedowiersza(liczbawierszy, wierszStronafkPKMa, "Ma"));
        wierszStronafkMa.setGrafikawaluty(grafikawaluty);
        nowywiersz.setWierszStronaMa(wierszStronafkMa);
        return nowywiersz;
    }

    private WierszStronafkPK dodajdanedowiersza(int numer, WierszStronafkPK w, String wnma) {
        w.setNrPorzadkowyWiersza(numer);
        w.setTypdokumentu(selected.getDokfkPK().getSeriadokfk());
        w.setNrkolejnydokumentu(selected.getDokfkPK().getNrkolejny());
        w.setStronaWnlubMa(wnma);
        return w;
    }

    private WierszStronafk uzupelnijdane(int numer, WierszStronafk w, String wnma, String tresc, String nrwlasnydokfk, String opiswiersza) {
        w.setNrwlasnydokumentu(nrwlasnydokfk);
        w.setOpisdokumentu(tresc);
        w.setOpiswiersza(opiswiersza);
        //rzeczy dotyczace waluty
        WierszStronafkPK wPK = w.getWierszStronafkPK();
        wPK.setNrPorzadkowyWiersza(numer);
        wPK.setTypdokumentu(selected.getDokfkPK().getSeriadokfk());
        wPK.setNrkolejnydokumentu(selected.getDokfkPK().getNrkolejny());
        wPK.setStronaWnlubMa(wnma);
        //uzupelniamy dane do walut jezeli sa
        try {
            w.setSymbolwaluty(selected.getWalutadokumentu());
            w.setDatawaluty(selected.getTabelanbp().getDatatabeli());
            w.setKurswaluty(selected.getTabelanbp().getKurssredni());
            w.setNrtabelinbp(selected.getTabelanbp().getTabelanbpPK().getNrtabeli());
            w.setKwotaWaluta(w.getKwota());
        } catch (Exception e) {
            
        }
        return w;
    }
    //wersja dla pierwszegor rzedu i
    //wersja dla nastepnych

    public void dodajdanedowiersza() {
        if (selected.getKonta().size() == 1) {
            WierszStronafk w = selected.getKonta().get(0).getWierszStronaWn();
            selected.getKonta().get(0).setWierszStronaWn(uzupelnijdane(1, w, "Wn", selected.getTresc(), selected.getNumer(), selected.getKonta().get(0).getOpis()));
            w = selected.getKonta().get(0).getWierszStronaMa();
            selected.getKonta().get(0).setWierszStronaMa(uzupelnijdane(1, w, "Ma", selected.getTresc(), selected.getNumer(), selected.getKonta().get(0).getOpis()));
        } else {
            int wiersz = liczbawierszy - 1;
            WierszStronafk w = selected.getKonta().get(wiersz).getWierszStronaWn();
            selected.getKonta().get(wiersz).setWierszStronaWn(uzupelnijdane(liczbawierszy, w, "Wn", selected.getTresc(), selected.getNumer(), selected.getKonta().get(wiersz).getOpis()));
            w = selected.getKonta().get(wiersz).getWierszStronaMa();
            selected.getKonta().get(wiersz).setWierszStronaMa(uzupelnijdane(liczbawierszy, w, "Ma", selected.getTresc(), selected.getNumer(), selected.getKonta().get(wiersz).getOpis()));
        }
    }

    //usuwa wiersze z dokumentu
    public void liczbawu() {
        if (liczbawierszy > 1) {
            liczbawierszy--;
            usunrozrachunek(selected.getKonta().get(liczbawierszy).getWierszStronaWn());
            usunrozrachunek(selected.getKonta().get(liczbawierszy).getWierszStronaMa());
            usuntransakcje(selected.getKonta().get(liczbawierszy).getWierszStronaWn());
            usuntransakcje(selected.getKonta().get(liczbawierszy).getWierszStronaMa());
            selected.getKonta().remove(liczbawierszy);
        }
    }

    public void dodaj() {
        try {
            UzupelnijWierszeoDane.uzupelnijwierszeodane(selected);
            //nanosimy zapisy na kontach
            NaniesZapisynaKontaFK.nanieszapisynakontach(selected.getKonta());
            selected.dodajwartoscwiersza(selected.getKonta().size()-1);
            //najpierw zobacz czy go nie ma, jak jest to usun i dodaj
            dokDAOfk.dodaj(selected);
            wykazZaksiegowanychDokumentow.add(selected);
            //zazanczamy ze nowe transakcje wprowadzone podczas tworzenia dokumentu maja byc zachowane bo dokument w efekcje zostal zapisany
            List<Rozrachunekfk> pobierznowododane = rozrachunekfkDAO.findByDokfk(selected.getDokfkPK().getSeriadokfk(), selected.getDokfkPK().getNrkolejny());
            for (Rozrachunekfk p : pobierznowododane) {
                p.setZaksiegowanodokument(true);
                rozrachunekfkDAO.edit(p);
            }
            //zaznaczamy sparowae jako wprowadzone i zaksiegowane
            for (Wiersze p : selected.getKonta()) {
                naniesnasparowane(p.getWierszStronaWn());
                naniesnasparowane(p.getWierszStronaMa());
            }
            resetujDokument();
            RequestContext.getCurrentInstance().update("formwpisdokument");
            Msg.msg("i", "Dokument dodany");
        } catch (Exception e) {
            Msg.msg("e", "Nie udało się dodac dokumentu " + e.toString());
        }
    }
    
    private void naniesnasparowane(WierszStronafk wierszStronafk) {
            if (wierszStronafk.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
                    WierszStronafkPK wierszStronafkPK = wierszStronafk.getWierszStronafkPK();
                    Zestawienielisttransakcji zestawienielisttransakcji = zestawienielisttransakcjiDAO.findByKlucz(wierszStronafk.getWierszStronafkPK());
                    if (zestawienielisttransakcji instanceof Zestawienielisttransakcji) {
                        zestawienielisttransakcji.setZaksiegowanodokument(true);
                        zestawienielisttransakcjiDAO.edit(zestawienielisttransakcji);
                    }
            }
    }

    public void edycja() {
        try {
            UzupelnijWierszeoDane.uzupelnijwierszeodane(selected);
            NaniesZapisynaKontaFK.nanieszapisynakontach(selected.getKonta());
            if (selected.getKonta().size()==1) {
                selected.dodajwartoscwiersza(0);
            }
            dokDAOfk.edit(selected);
            wykazZaksiegowanychDokumentow.clear();
            wykazZaksiegowanychDokumentow = dokDAOfk.findAll();
            resetujDokument();
            Msg.msg("i", "Pomyślnie zaktualizowano dokument");
        } catch (Exception e) {
            System.out.println(e.toString());
            Msg.msg("e", "Nie udało się zmenic dokumentu " + e.toString());
        }
    }
    
    public void edycjaDlaRozrachunkow() {
        try {
            UzupelnijWierszeoDane.uzupelnijwierszeodane(selected);
            NaniesZapisynaKontaFK.nanieszapisynakontach(selected.getKonta());
            dokDAOfk.edit(selected);
            wykazZaksiegowanychDokumentow.clear();
            wykazZaksiegowanychDokumentow = dokDAOfk.findAll();
            Msg.msg("i", "Pomyślnie zaktualizowano dokument edycja rozrachunow");
        } catch (Exception e) {
            System.out.println(e.toString());
            Msg.msg("e", "Nie udało się zmenic dokumentu podczas edycji rozrachunkow " + e.toString());
        }
    }

    public void usundokument(Dokfk dousuniecia) {
        try {
            int iloscwierszy = dousuniecia.getKonta().size();
            for (int i = 0; i < iloscwierszy; i++) {
                usunrozrachunek(dousuniecia.getKonta().get(i).getWierszStronaWn());
                usunrozrachunek(dousuniecia.getKonta().get(i).getWierszStronaMa());
                usuntransakcje(dousuniecia.getKonta().get(i).getWierszStronaWn());
                usuntransakcje(dousuniecia.getKonta().get(i).getWierszStronaMa());
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

    private void usunrozrachunek(WierszStronafk wierszStronafk) {
        Rozrachunekfk r = new Rozrachunekfk(wierszStronafk);
        try {
            Rozrachunekfk rU = rozrachunekfkDAO.findRozrachunekfk(r);
            rozrachunekfkDAO.destroy(rU);
            Msg.msg("i", "Usunieto rozrachunek");
        } catch (Exception e) {
            Msg.msg("e", "Nieusunieto rozrachunku");
        }
    }

    private void usuntransakcje(WierszStronafk wierszStronafk) {
        WierszStronafkPK wierszPK = wierszStronafk.getWierszStronafkPK();
        try {
            Zestawienielisttransakcji znaleziona = zestawienielisttransakcjiDAO.findByKlucz(wierszPK);
            zestawienielisttransakcjiDAO.destroy(znaleziona);
            Msg.msg("i", "Usunieto transakcje nienowe transakcje");
        } catch (Exception e) {
            Msg.msg("e", "Nie usunieto transakcje nienowe transakcje");
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
            String mc = data.split("-")[1];
            selected.setMiesiac(mc);
            RequestContext.getCurrentInstance().update("formwpisdokument:miesiac");
            Msg.msg("i", "Wygenerowano okres dokumentu");
        }
    }

    public void przygotujdokument() {
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
            zablokujpanelwalutowy = true;
        } else {
            zablokujpanelwalutowy = false;
        }
        RequestContext.getCurrentInstance().update("formwpisdokument:panelwalutowy");
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
    }

    public void wybranodokmessage() {
        try {
            Msg.msg("i", "Wybrano dokument do edycji " + selected.getDokfkPK().toString());
            setZapisz0edytuj1(true);
            if (selected.getDokfkPK().getSeriadokfk().equals("WB")) {
                zablokujpanelwalutowy = true;
            }
            liczbawierszy = selected.getKonta().size();
        } catch (Exception e) {
            Msg.msg("e", "Nie wybrano dokumentu do edycji ");
        }
    }

    public void znajdzdokumentzzapisu() {
        selected = wiersz.getDokfk();
        String szukanafrazazzapisu = wiersz.getOpis();
        liczbawierszy = selected.getKonta().size();
        List<Wiersze> zawartoscselected = new ArrayList<>();
        zawartoscselected = selected.getKonta();
        for (Wiersze p : zawartoscselected) {
            if (szukanafrazazzapisu.equals(p.getOpis())) {
                numerwierszazapisu = p.getIdporzadkowy();
                numerwierszazapisu--;
            }
        }
        setZapisz0edytuj1(true);
        String makrozaznaczajacepole = "#formwpisdokument\\:dataList\\:" + String.valueOf(numerwierszazapisu) + "\\:opis";
        RequestContext.getCurrentInstance().update("formwpisdokument");
        RequestContext.getCurrentInstance().update("zestawieniezapisownakontach");
        //RequestContext.getCurrentInstance().execute("$(#formwpisdokument\\\\:dataList\\\\:5\\\\:opis).select()");
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
    //zaznacza w biezacym rozrachunku ze jest nowa transakcja
    public void zapiszusuntransakcje(ValueChangeEvent el) {
        boolean newvalue = (boolean) el.getNewValue();
        if (newvalue == true) {
            aktualnywierszdorozrachunkow.setNowatransakcja(true);
            rozrachunekfkDAO.dodaj(aktualnywierszdorozrachunkow);
            rozrachunekNowaTransakcja.add(aktualnywierszdorozrachunkow);
            zrobWierszStronafkReadOnly(true);
            zablokujprzyciskrezygnuj = true;
            selected.setLiczbarozliczonych(selected.getLiczbarozliczonych()+1);
            Msg.msg("i", "Dodano bieżący zapis jako nową transakcję");
        } else {
            aktualnywierszdorozrachunkow.setNowatransakcja(false);
            rozrachunekfkDAO.destroy(aktualnywierszdorozrachunkow);
            rozrachunekNowaTransakcja.remove(aktualnywierszdorozrachunkow);
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
        if (zapisz0edytuj1 == true) {
            edycjaDlaRozrachunkow();
        }
        RequestContext.getCurrentInstance().update("formwpisdokument:panelwalutowy");
        RequestContext.getCurrentInstance().update("wpisywaniefooter");
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
        RequestContext.getCurrentInstance().update("formwpisdokument:paneldaneogolnefaktury");
    }
    
    private void zrobWierszStronafkReadOnly(boolean wartosc){
        List<Wiersze> wierszebiezace = selected.getKonta();
        WierszStronafkPK aktualnywiersz = aktualnywierszdorozrachunkow.getWierszStronafk().getWierszStronafkPK();
        for (Wiersze p : wierszebiezace) {
            if (p.getWierszStronaWn().getWierszStronafkPK().equals(aktualnywiersz)) {
                p.setWnReadOnly(wartosc);
                int i = p.getIdporzadkowy() - 1;
                String wiersz = String.format("formwpisdokument:dataList:%s:wnReadOnly", i);
                RequestContext.getCurrentInstance().update(wiersz);
                break;
            }
            if (p.getWierszStronaMa().getWierszStronafkPK().equals(aktualnywiersz)) {
                p.setMaReadOnly(wartosc);
                int i = p.getIdporzadkowy() - 1;
                String wiersz = String.format("formwpisdokument:dataList:%s:maReadOnly", i);
                RequestContext.getCurrentInstance().update(wiersz);
                break;
            }
        }
    }

    //porzadkowanie niezaksiegowanych dokumnetow i rozrachunkow z nich
    public void usunrozrachunkiniezaksiegowanychdok() {
        rozrachunekfkDAO.usunniezaksiegowane();
        zestawienielisttransakcjiDAO.usunniezaksiegowane();
    }
    
    //********************
    //rozrachunki
    public void rozrachunki() {
        //bierzemy parametry przekazane przez javascript po kazdorazowym kliknieciu pola konta
        String wnma = (String) Params.params("wpisywaniefooter:wnlubma");
        String nrwierszaS = (String) Params.params("wpisywaniefooter:wierszid");
        zablokujprzyciskzapisz = false;
        try {
            Integer nrwiersza = Integer.parseInt(nrwierszaS) - 1;
            inicjalizacjaAktualnego(wnma, nrwiersza);
            if (aktualnywierszdorozrachunkow.getWierszStronafk().getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
                boolean onjestnowatransakcja = aktualnywierszdorozrachunkow.isNowatransakcja();
                biezacetransakcje = new ArrayList<>();
                if (onjestnowatransakcja == false) {
                    pobierzRozrachunekfkzBazy(aktualnywierszdorozrachunkow.getKontoid().getPelnynumer(), wnma, aktualnywierszdorozrachunkow.getWalutarozrachunku());
                    stworznowetransakcjezPobranychstronwierszy();
                    pobierzjuzNaniesioneTransakcjeRozliczony();
                    naniesinformacjezwczesniejrozliczonych();
                    pobierzjuzNaniesioneTransakcjeSparowane();
                } else {
                    Msg.msg("i", "Jest nową transakcja, pobieram wiersze przeciwne");
                    pobierztransakcjeJakoSparowany();
                    sumujdlaNowejTransakcji();
                }
                //trzeba zablokować mozliwosc zmiany nowej transakcji jak sa juz rozliczenia!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                potraktujjakoNowaTransakcje = aktualnywierszdorozrachunkow.isNowatransakcja();
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
                String znajdz = "znadzpasujacepolerozrachunku("+aktualnywierszdorozrachunkow.getPozostalo()+")";
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

    private void inicjalizacjaAktualnego(String wnma, int nrwiersza) {
        aktualnywierszdorozrachunkow = new Rozrachunekfk();
        WierszStronafk wierszStronafk = new WierszStronafk();
        if (wnma.equals("Wn")) {
            wierszStronafk = selected.getKonta().get(nrwiersza).getWierszStronaWn();
        } else {
            wierszStronafk = selected.getKonta().get(nrwiersza).getWierszStronaMa();
        }
            aktualnywierszdorozrachunkow.setWierszStronafk(wierszStronafk);
            aktualnywierszdorozrachunkow.setKwotapierwotna(wierszStronafk.getKwota());
            aktualnywierszdorozrachunkow.setPozostalo(wierszStronafk.getKwota());
            aktualnywierszdorozrachunkow.setKontoid(wierszStronafk.getKonto());
            aktualnywierszdorozrachunkow.setWalutarozrachunku(wierszStronafk.getSymbolwaluty());
        //sprawdza czy nowy rozrachunek nie jest juz w bazie, jak jest to go pobiera
        Rozrachunekfk pobranyrozrachunek = rozrachunekfkDAO.findRozrachunekfk(aktualnywierszdorozrachunkow);
        if (pobranyrozrachunek instanceof Rozrachunekfk) {
            aktualnywierszdorozrachunkow = pobranyrozrachunek;
        }
    }

    //************************* jeli pobierztransakcjeJakoSparowany() == 0 to robimy jakby nie byl nowa transakcja
    private void pobierzRozrachunekfkzBazy(String nrkonta, String wnma, String waluta) {
        rozrachunekNowaTransakcja = new ArrayList<>();
        rozrachunekNowaTransakcja.addAll(rozrachunekfkDAO.findRozrachunkifkByKonto(nrkonta, wnma, waluta));
        //pobrano wiersze - a teraz z nich robie rozrachunki
    }

    private void stworznowetransakcjezPobranychstronwierszy() {
        //z utworzonych rozrachunkow tworzy sie transkakcje laczac rozrachunek rozliczony ze sparowanym
        transakcjeswiezynki = new ArrayList<>();
        for (Rozrachunekfk nowatransakcjazbazy : rozrachunekNowaTransakcja) {
            Transakcja transakcja = new Transakcja();
            transakcja.getTransakcjaPK().setRozliczany(aktualnywierszdorozrachunkow);
            transakcja.getTransakcjaPK().setSparowany(nowatransakcjazbazy);
            transakcjeswiezynki.add(transakcja);
        }
    }

    private void pobierzjuzNaniesioneTransakcjeRozliczony() {
        zachowanewczejsniejtransakcje = new ArrayList<>();
        WierszStronafkPK klucz = aktualnywierszdorozrachunkow.getWierszStronafk().getWierszStronafkPK();
        Zestawienielisttransakcji pobranalista = new Zestawienielisttransakcji();
        pobranalista = zestawienielisttransakcjiDAO.findByKlucz(klucz);
        if (pobranalista instanceof Zestawienielisttransakcji) {
            zachowanewczejsniejtransakcje.addAll(pobranalista.getListatransakcji());
        }
    }

    private void naniesinformacjezwczesniejrozliczonych() {
        pierwotnailosctransakcjiwbazie = 0;
        //sprawdz czy nowoutworzona transakcja nie znajduje sie juz w biezacetransakcje
        //jak jest to uzupelniamy jedynie rozliczenie biezace i archiwalne
        double sumaddlaaktualnego = 0.0;
        for (Transakcja s : zachowanewczejsniejtransakcje) {
            sumaddlaaktualnego += s.getKwotatransakcji();
            biezacetransakcje.add(s);
            pierwotnailosctransakcjiwbazie++;
        }
        for (Transakcja r : transakcjeswiezynki) {
            if (!zachowanewczejsniejtransakcje.contains(r)) {
                biezacetransakcje.add(r);
            }
        }
        //aktualizujemy biezacy wiersz nie bedacy nowa transakcja
        double rozliczono = aktualnywierszdorozrachunkow.getRozliczono();
        double pozostalo = aktualnywierszdorozrachunkow.getPozostalo();
        rozliczono = rozliczono + sumaddlaaktualnego;
        pozostalo = pozostalo - sumaddlaaktualnego;
        aktualnywierszdorozrachunkow.setRozliczono(rozliczono);
        aktualnywierszdorozrachunkow.setPozostalo(pozostalo);
    }

    private void pobierzjuzNaniesioneTransakcjeSparowane() {
        rozrachunekNowaTransakcja = new ArrayList<>();
        rozrachunekNowaTransakcja.addAll(rozrachunekfkDAO.findAll());
        for (Rozrachunekfk p : rozrachunekNowaTransakcja) {
            for (Transakcja r : biezacetransakcje) {
                if (r.idSparowany().equals(p.getWierszStronafk().getWierszStronafkPK())) {
                    r.getTransakcjaPK().setSparowany(p);
                }
            }
        }

    }

    //*************************
    //************************* jeli pobierztransakcjeJakoSparowany() == 1 to robimy jakby byl nowa transakcja
    private int pobierztransakcjeJakoSparowany() {
        //teraz z zapamietanych czyscimy klucz i liste pobrana wyzej
        //pobieramy listy z bazy
        transakcjejakosparowany = new ArrayList<>();
        List<Zestawienielisttransakcji> pobranelisty = new ArrayList<>();
        List<Transakcja> kolekcje = new ArrayList<>();
        pobranelisty = zestawienielisttransakcjiDAO.findAll();
        for (Zestawienielisttransakcji p : pobranelisty) {
            kolekcje.addAll(p.getListatransakcji());
        }
        int wynikszukania = 0;
        for (Transakcja x : kolekcje) {
            WierszStronafkPK idAktualny = aktualnywierszdorozrachunkow.getWierszStronafk().getWierszStronafkPK();
            boolean typdokumentu = x.idSparowany().getTypdokumentu().equals(idAktualny.getTypdokumentu());
            boolean nrkolejnydokumentu = x.idSparowany().getNrkolejnydokumentu() == idAktualny.getNrkolejnydokumentu();
            boolean nrPorzadkowyWiersza = x.idSparowany().getNrPorzadkowyWiersza() == idAktualny.getNrPorzadkowyWiersza();
            boolean kwoty = x.GetSpRozl() != 0.0 && x.GetSpPoz() != 0.0;
            if (typdokumentu && nrkolejnydokumentu && nrPorzadkowyWiersza && kwoty) {
                Msg.msg("w", "on byl jako sparowany");
                transakcjejakosparowany.add(x);
                wynikszukania = 1;
            }
        }
        //nie znaleziono transakcji gdzie aktualnybylby sparowanym
        if (wynikszukania == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    private void sumujdlaNowejTransakcji() {
        double sumaddlaaktualnego = 0.0;
        //czyscimy wartosci
        for (Transakcja p : transakcjejakosparowany) {
            double kwota = p.getKwotatransakcji();
            if (kwota > 0) {
                Transakcja nowa = new Transakcja();
                nowa.setZablokujnanoszenie(true);
                nowa.getTransakcjaPK().setRozliczany(p.getTransakcjaPK().getSparowany());
                nowa.getTransakcjaPK().setSparowany(p.getTransakcjaPK().getRozliczany());
                nowa.SetSpRozl(kwota);
                nowa.SetSpPoz(nowa.GetSpKwotaPier() - kwota);
                biezacetransakcje.add(nowa);
            }
        }
    }

    //*************************
    //nanosi transakcje z kwotami na rozrachunki
    public void zapistransakcji() {
        //to jest potrzebne zeby zmiany w jednym rozrachunku byly widoczne jako naniesione w innym
        for (Transakcja p : biezacetransakcje) {
            double kwotanowa = p.getKwotatransakcji();
            double kwotastara = p.getPoprzedniakwota();
            double roznica = kwotanowa - kwotastara;
            double aktualny_rozliczono = p.getTransakcjaPK().getRozliczany().getRozliczono();
            double aktualny_pierwotna = p.getTransakcjaPK().getRozliczany().getKwotapierwotna();
            double sparowany_rozliczono = p.getTransakcjaPK().getSparowany().getRozliczono();
            double sparowany_pierwotna = p.getTransakcjaPK().getSparowany().getKwotapierwotna();
            if (roznica != 0) {
                p.getTransakcjaPK().getRozliczany().setRozliczono(aktualny_rozliczono + roznica);
                p.getTransakcjaPK().getRozliczany().setPozostalo(aktualny_pierwotna - p.getTransakcjaPK().getRozliczany().getRozliczono());
                p.getTransakcjaPK().getSparowany().setNowatransakcja(true);
                p.getTransakcjaPK().getSparowany().setRozliczono(sparowany_rozliczono + roznica);
                p.getTransakcjaPK().getSparowany().setPozostalo(sparowany_pierwotna - p.getTransakcjaPK().getSparowany().getRozliczono());
            } else if (roznica == 0) {
                p.getTransakcjaPK().getSparowany().setNowatransakcja(false);
            }
            p.setPoprzedniakwota(kwotanowa);
            try {
                Rozrachunekfk dotyczyrozrachunku = rozrachunekfkDAO.findRozrachunkifkByWierszStronafk(p.idSparowany());
                if (roznica != 0) {
                    dotyczyrozrachunku.setRozliczono(dotyczyrozrachunku.getRozliczono() + roznica);
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
        boolean wartosc = false;
        if (iletransakcjidodano != 0) {
            selected.setLiczbarozliczonych(selected.getLiczbarozliczonych() + iletransakcjidodano);
        }
        if (selected.getLiczbarozliczonych() > 0) {
            selected.setZablokujzmianewaluty(true);
            wartosc = true;
        } else {
            selected.setZablokujzmianewaluty(false);
        }
        // to ma blokowac zmianie kwot gdzie sa rozrachunki
        List<Wiersze> wierszebiezace = selected.getKonta();
        WierszStronafkPK aktualnywiersz = aktualnywierszdorozrachunkow.getWierszStronafk().getWierszStronafkPK();
        for (Wiersze p : wierszebiezace) {
            if (p.getWierszStronaWn().getWierszStronafkPK().equals(aktualnywiersz)) {
                p.setWnReadOnly(wartosc);
                break;
            } else if (p.getWierszStronaMa().getWierszStronafkPK().equals(aktualnywiersz)) {
                p.setMaReadOnly(wartosc);
                break;
            }
        }
        RequestContext.getCurrentInstance().update("formwpisdokument:panelwalutowy");
        WierszStronafkPK klucz = aktualnywierszdorozrachunkow.getWierszStronafk().getWierszStronafkPK();
        List<Transakcja> doprzechowania = new ArrayList<>();
        doprzechowania.addAll(biezacetransakcje);
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
            if (staranazwa != null && selected.getKonta().get(0).getWierszStronaWn().getKwota() != 0.0) {
                przewalutujzapisy(staranazwa, nazwawaluty);
                selected.setWalutadokumentu(nazwawaluty);
            } else {
                selected.setWalutadokumentu(nazwawaluty);
                //wpisuje kurs bez przeliczania, to jest dla nowego dokumentu jak sie zmieni walute na euro
                Waluty wybranawaluta = walutyDAOfk.findByName(nazwawaluty);
                List<Wiersze> wiersze = selected.getKonta();
                for (Wiersze p : wiersze) {
                    uzupelnijwierszprzyprzewalutowaniu(p.getWierszStronaWn(), wybranawaluta, selected.getTabelanbp());
                    uzupelnijwierszprzyprzewalutowaniu(p.getWierszStronaMa(), wybranawaluta, selected.getTabelanbp());
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

    private void przewalutujzapisy(String staranazwa, String nazwawaluty) {
        double kurs = 0.0;
        if (staranazwa.equals("PLN")) {
            Waluty wybranawaluta = walutyDAOfk.findByName(nazwawaluty);
            kurs = selected.getTabelanbp().getKurssredni();
            kurs = Math.round((1 / kurs) * 100000000);
            kurs = kurs / 100000000;
            List<Wiersze> wiersze = selected.getKonta();
            for (Wiersze p : wiersze) {
                uzupelnijwierszprzyprzewalutowaniu(p.getWierszStronaWn(), wybranawaluta, selected.getTabelanbp());
                uzupelnijwierszprzyprzewalutowaniu(p.getWierszStronaMa(), wybranawaluta, selected.getTabelanbp());
                if (p.getWierszStronaWn().getKwota() != 0.0) {
                    double kwota = p.getWierszStronaWn().getKwota();
                    p.getWierszStronaWn().setKwotaPLN(kwota+0.0);
                    kwota = Math.round(kwota * kurs * 10000);
                    kwota = kwota / 10000;
                    p.getWierszStronaWn().setKwota(kwota);
                    p.getWierszStronaWn().setKwotaWaluta(kwota);
                }
                if (p.getWierszStronaMa().getKwota() != 0.0) {
                    double kwota = p.getWierszStronaMa().getKwota();
                    p.getWierszStronaMa().setKwotaPLN(kwota+0.0);
                    kwota = Math.round(kwota * kurs * 10000);
                    kwota = kwota / 10000;
                    p.getWierszStronaMa().setKwota(kwota);
                    p.getWierszStronaMa().setKwotaWaluta(kwota);
                }
            }
        } else {
            //robimy w zlotowkach
            kurs = selected.getTabelanbp().getKurssredni();
            List<Wiersze> wiersze = selected.getKonta();
            for (Wiersze p : wiersze) {
                uzupelnijwierszprzyprzewalutowaniuPLN(p.getWierszStronaWn());
                uzupelnijwierszprzyprzewalutowaniuPLN(p.getWierszStronaMa());
                p.getWierszStronaWn().setGrafikawaluty("zł");
                p.getWierszStronaMa().setGrafikawaluty("zł");
                if (p.getWierszStronaWn().getKwota() != 0.0) {
                    double kwota = p.getWierszStronaWn().getKwota();
                    kwota = Math.round(kwota * kurs * 100);
                    kwota = kwota / 100;
                    p.getWierszStronaWn().setKwota(kwota);
                    p.getWierszStronaWn().setKwotaPLN(kwota);
                    p.getWierszStronaWn().setKwotaWaluta(0.0);
                }
                if (p.getWierszStronaMa().getKwota() != 0.0) {
                    double kwota = p.getWierszStronaMa().getKwota();
                    kwota = Math.round(kwota * kurs * 100);
                    kwota = kwota / 100;
                    p.getWierszStronaMa().setKwota(kwota);
                    p.getWierszStronaMa().setKwotaPLN(kwota);
                    p.getWierszStronaMa().setKwotaWaluta(0.0);
                }
        }
        }
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
    }

    private void uzupelnijwierszprzyprzewalutowaniu(WierszStronafk wierszStronafk, Waluty wybranawaluta, Tabelanbp tabelanbp) {
            wierszStronafk.setGrafikawaluty(wybranawaluta.getSkrotsymbolu());
            wierszStronafk.setNrtabelinbp(tabelanbp.getTabelanbpPK().getNrtabeli());
            wierszStronafk.setKurswaluty(tabelanbp.getKurssredni());
            wierszStronafk.setSymbolwaluty(tabelanbp.getTabelanbpPK().getSymbolwaluty());
            wierszStronafk.setDatawaluty(tabelanbp.getDatatabeli());
    }
    
     private void uzupelnijwierszprzyprzewalutowaniuPLN(WierszStronafk wierszStronafk) {
            wierszStronafk.setGrafikawaluty("zł");
            Tabelanbp tabelanbp = null;
            wierszStronafk.setNrtabelinbp(null);
            wierszStronafk.setKurswaluty(1);
            wierszStronafk.setSymbolwaluty("PLN");
            wierszStronafk.setDatawaluty(null);
    }
    
    public void pobierzsymbolwaluty() {
        try {
            List<Waluty> wprowadzonewaluty = walutyDAOfk.findAll();
            if (symbolwalutydowiersza.equals("") || symbolwalutydowiersza.equals("PLN")) {
                symbolwalutydowiersza = "zł";
            } else {
                for (Waluty w : wprowadzonewaluty) {
                    if (w.getSymbolwaluty().equals(symbolwalutydowiersza)) {
                        symbolwalutydowiersza = w.getSkrotsymbolu();
                        break;
                    }
                }
            }
            int iloscwierszy = selected.getKonta().size();
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
        roznicakursowa = roznicakursowa/100;
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
        uzupelnijwierszprzyprzewalutowaniu(wierszbiezacy.getWierszStronaWn(), wybranawaluta, tabelanbp);
        uzupelnijwierszprzyprzewalutowaniu(wierszbiezacy.getWierszStronaMa(), wybranawaluta, tabelanbp);
    }
    public void handleKontoRow(int numer, String wnma) {
        numerwiersza = numer;
        stronawiersza = wnma;
    }

    
    public void handleKontoSelect(SelectEvent event) {
        Object item = event.getObject();
        if (stronawiersza.equals("wn")) {
            selected.getKonta().get(numerwiersza).getWierszStronaWn().setKonto((Konto) item);
            String pole = "$(document.getElementById('formwpisdokument:dataList:"+numerwiersza+":kontown_input')).select();";
        } else {
            selected.getKonta().get(numerwiersza).getWierszStronaMa().setKonto((Konto) item);
        }
    }
    
    //********************************
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public Dokfk getSelected() {
        return selected;
    }

    public int getNumerwierszazapisu() {
        return numerwierszazapisu;
    }

    public void setNumerwierszazapisu(int numerwierszazapisu) {
        this.numerwierszazapisu = numerwierszazapisu;
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

    public int getLiczbawierszy() {
        return liczbawierszy;
    }

    public void setLiczbawierszy(int liczbawierszy) {
        this.liczbawierszy = liczbawierszy;
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

    public Rozrachunekfk getAktualnywierszdorozrachunkow() {
        return aktualnywierszdorozrachunkow;
    }

    public void setAktualnywierszdorozrachunkow(Rozrachunekfk aktualnywierszdorozrachunkow) {
        this.aktualnywierszdorozrachunkow = aktualnywierszdorozrachunkow;
    }

    public List<Rozrachunekfk> getRozrachunekNowaTransakcja() {
        return rozrachunekNowaTransakcja;
    }

    public void setRozrachunekNowaTransakcja(List<Rozrachunekfk> rozrachunekNowaTransakcja) {
        this.rozrachunekNowaTransakcja = rozrachunekNowaTransakcja;
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

    //</editor-fold
    public boolean isZablokujpanelwalutowy() {
        return zablokujpanelwalutowy;
    }

    public void setZablokujpanelwalutowy(boolean zablokujpanelwalutowy) {
        this.zablokujpanelwalutowy = zablokujpanelwalutowy;
    }


}
