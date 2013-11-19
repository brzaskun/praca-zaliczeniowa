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
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersze;
import entityfk.Zestawienielisttransakcji;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import msg.Msg;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import params.Params;
import viewfk.subroutines.NaniesZapisynaKontaFK;
import viewfk.subroutines.UzupelnijWierszeoDane;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DokfkView implements Serializable {
    private Dokfk selected;
    @Inject private DokDAOfk dokDAOfk;
    private boolean zapisz0edytuj1;
    private String wierszid;
    private String wnlubma;
    private int liczbawierszy;
    private List<Dokfk> wykazZaksiegowanychDokumentow;
    //a to jest w dialog_zapisywdokumentach
    @Inject private Wiersze wiersz;
    private int numerwierszazapisu;
    //************************************zmienne dla rozrachunkow
    @Inject private RozrachunekfkDAO rozrachunekfkDAO;
    @Inject private ZestawienielisttransakcjiDAO zestawienielisttransakcjiDAO;
    private boolean potraktujjakoNowaTransakcje;
    private static List<Rozrachunekfk> rozrachunekNowaTransakcja;
    @Inject private Rozrachunekfk aktualnywierszdorozrachunkow;
    //a to sa listy do sortowanie transakcji na poczatku jedna mega do zbiorki wszystkich dodanych coby nie ginely
    private List<Transakcja> biezacetransakcje;
    private List<Transakcja> transakcjeswiezynki;
    private List<Transakcja> zachowanewczejsniejtransakcje;
    private List<Transakcja> transakcjejakosparowany;
    private boolean zablokujprzyciskzapisz;
    //waltuty
     //waluta wybrana przez uzytkownika
    @Inject private WalutyDAOfk walutyDAOfk;
    @Inject private TabelanbpDAO tabelanbpDAO;
    private String wybranawaluta;
    private List<String> wprowadzonesymbolewalut;   
    private Tabelanbp tabelanbp;

    public DokfkView() {
        resetujDokument();
        this.wykazZaksiegowanychDokumentow = new ArrayList<>();
        rozrachunekNowaTransakcja = new ArrayList<>();

        this.biezacetransakcje = new ArrayList<>();
        this.transakcjeswiezynki = new ArrayList<>();
        this.zachowanewczejsniejtransakcje = new ArrayList<>();
        this.transakcjejakosparowany = new ArrayList<>();
        this.zablokujprzyciskzapisz = false;
        this.potraktujjakoNowaTransakcje = false;
        this.wprowadzonesymbolewalut = new ArrayList<>();
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
    }
    //<editor-fold defaultstate="collapsed" desc="schowane podstawowe funkcje jak dodaj usun itp">
    
    //********************************************funkcje dla ksiegowania dokumentow
    //RESETUJ DOKUMNETFK
    private void resetujDokument() {
        selected = new Dokfk();
        tabelanbp = new Tabelanbp();
        DokfkPK dokfkPK = new DokfkPK();
        selected.setDokfkPK(dokfkPK);
        List<Wiersze> wiersze = new ArrayList<>();
        Wiersze nowywiersz = new Wiersze(1, 0);
        WierszStronafk wierszStronafkWn = new WierszStronafk();
        nowywiersz.setWierszStronaWn(wierszStronafkWn);
        nowywiersz.getWierszStronaWn().getWierszStronafkPK().setNrPorzadkowyWiersza(1);
        WierszStronafk wierszStronafkMa = new WierszStronafk();
        nowywiersz.setWierszStronaMa(wierszStronafkMa);
        nowywiersz.getWierszStronaMa().getWierszStronafkPK().setNrPorzadkowyWiersza(1);
        wiersze.add(nowywiersz);
        selected.setKonta(wiersze);
        liczbawierszy = 1;
        zapisz0edytuj1 = false;
        
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
            selected.getKonta().add(utworzNowyWiersz());
            RequestContext.getCurrentInstance().execute("załadujmodelzachowywaniawybranegopola();");
        } else {
            Msg.msg("w", "Uzuwpełnij dane przed dodaniem nowego wiersza");
        }
    }
    
    private Wiersze utworzNowyWiersz() {
        Wiersze nowywiersz = new Wiersze(liczbawierszy, 0);
        WierszStronafk wierszStronafkWn = new WierszStronafk();
        WierszStronafkPK wierszStronafkPKWn = new WierszStronafkPK();
        wierszStronafkWn.setWierszStronafkPK(dodajdanedowiersza(liczbawierszy, wierszStronafkPKWn, "Wn"));
        nowywiersz.setWierszStronaWn(wierszStronafkWn);
        WierszStronafk wierszStronafkMa = new WierszStronafk();
        WierszStronafkPK wierszStronafkPKMa = new WierszStronafkPK();
        wierszStronafkMa.setWierszStronafkPK(dodajdanedowiersza(liczbawierszy, wierszStronafkPKMa, "Ma"));
        nowywiersz.setWierszStronaMa(wierszStronafkMa);
        return nowywiersz;
    }
    
    private WierszStronafkPK dodajdanedowiersza(int numer, WierszStronafkPK w, String wnma){
        w.setNrPorzadkowyWiersza(numer);
        w.setTypdokumentu(selected.getDokfkPK().getSeriadokfk());
        w.setNrkolejnydokumentu(selected.getDokfkPK().getNrkolejny());
        w.setStronaWnlubMa(wnma);
        return w;
    }
    //wersja dla pierwszegor zedu
    public void dodajdanedowierszaPW(){
        WierszStronafkPK w = selected.getKonta().get(0).getWierszStronaWn().getWierszStronafkPK();
        selected.getKonta().get(0).getWierszStronaWn().setWierszStronafkPK(dodajdanedowiersza(1, w, "Wn"));
        w = selected.getKonta().get(0).getWierszStronaMa().getWierszStronafkPK();
        selected.getKonta().get(0).getWierszStronaMa().setWierszStronafkPK(dodajdanedowiersza(1, w, "Ma"));
    }
    
    //usuwa wiersze z dokumentu
    
    public void liczbawu() {
        if (liczbawierszy > 1) {
            liczbawierszy--;
            usunrozrachunek(selected.getKonta().get(liczbawierszy).getWierszStronaWn());
            usunrozrachunek(selected.getKonta().get(liczbawierszy).getWierszStronaMa());
            selected.getKonta().remove(liczbawierszy);
        }
    }
    
    public void dodaj() {
        try {
            UzupelnijWierszeoDane.uzupelnijwierszeodane(selected);
            //nanosimy zapisy na kontach
            NaniesZapisynaKontaFK.nanieszapisynakontach(selected.getKonta());
            //najpierw zobacz czy go nie ma, jak jest to usun i dodaj
            dokDAOfk.dodaj(selected);
            wykazZaksiegowanychDokumentow.add(selected);
            resetujDokument();
            RequestContext.getCurrentInstance().update("formwpisdokument");
            Msg.msg("i", "Dokument dodany");
        } catch (Exception e) {
            Msg.msg("e", "Nie udało się dodac dokumentu " + e.toString());
        }
    }
    
    public void edycja() {
        try {
            UzupelnijWierszeoDane.uzupelnijwierszeodane(selected);
            NaniesZapisynaKontaFK.nanieszapisynakontach(selected.getKonta());
            dokDAOfk.edit(selected);
            wykazZaksiegowanychDokumentow.clear();
            wykazZaksiegowanychDokumentow = dokDAOfk.findAll();
            Msg.msg("i", "Pomyślnie zaktualizowano dokument");
        } catch (Exception e) {
            System.out.println(e.toString());
            Msg.msg("e", "Nie udało się zmenic dokumentu " + e.toString());
        }
    }
    
    public void usundokument(Dokfk dousuniecia) {
        try {
            int iloscwierszy = selected.getKonta().size();
            for (int i = 0; i < iloscwierszy; i++) {
                usunrozrachunek(selected.getKonta().get(i).getWierszStronaWn());
                usunrozrachunek(selected.getKonta().get(i).getWierszStronaMa());
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
            Rozrachunekfk r = new Rozrachunekfk(wierszStronafk);
            try {
                Rozrachunekfk rU = rozrachunekfkDAO.findRozrachunekfk(r);
                rozrachunekfkDAO.destroy(rU);
                Msg.msg("i", "Usunieto rozrachunek");
            } catch (Exception e) {
                Msg.msg("e", "Nieusunieto rozrachunku");
            }
    }
    
    //***************************************
    public void znajdzduplicatdokumentu() {
        //uruchamiaj tylko jak jest wpisywanie a nie edycja
        if(zapisz0edytuj1==false) {
            Dokfk dokument = dokDAOfk.findDokfkObj(selected);
            if (dokument != null) {
                RequestContext.getCurrentInstance().execute("document.getElementById('formwpisdokument:numer').select();");
                Msg.msg("e", "Blad dokument o takim numerze juz istnieje");
            } else {
                Msg.msg("i", "Go on Master");
            }
        }
    }
    
    public void wygenerujokreswpisudokumentu() {
        String data = (String) Params.params("formwpisdokument:datka");
        String mc = data.split("-")[1];
        selected.setMiesiac(mc);
        RequestContext.getCurrentInstance().update("formwpisdokument:miesiac");
        Msg.msg("i", "Wygenerowano okres dokumentu");
    }
    
    public void pobierzostatninumerdok() {
        try {
            Dokfk ostatnidokumentdanegorodzaju = dokDAOfk.findDokfkLastofaType("Kowalski", selected.getDokfkPK().getSeriadokfk());
            selected.getDokfkPK().setNrkolejny(ostatnidokumentdanegorodzaju.getDokfkPK().getNrkolejny()+1);
            RequestContext.getCurrentInstance().update("formwpisdokument:numer");
        } catch (Exception e) {}
    }
    
    public void wybranodokmessage() {
        Msg.msg("i", "Wybrano dokument do edycji " + selected.getDokfkPK().toString());
        setZapisz0edytuj1(true);
        liczbawierszy = selected.getKonta().size();
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
    public void zapiszusuntransakcje(ValueChangeEvent el){
        boolean newvalue = (boolean) el.getNewValue();
        if (newvalue == true) {
            aktualnywierszdorozrachunkow.setNowatransakcja(true);
            rozrachunekfkDAO.dodaj(aktualnywierszdorozrachunkow);
            rozrachunekNowaTransakcja.add(aktualnywierszdorozrachunkow);
            Msg.msg("i", "Dodano bieżący zapis jako nową transakcję");
        } else {
            aktualnywierszdorozrachunkow.setNowatransakcja(false);
            rozrachunekfkDAO.destroy(aktualnywierszdorozrachunkow);
            rozrachunekNowaTransakcja.remove(aktualnywierszdorozrachunkow);
            Msg.msg("i","Usunięto zapis z listy nowych transakcji");
        }
    }
    
    //********************
    //rozrachunki
    public void rozrachunki(){
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
                    pobierzRozrachunekfkzBazy(aktualnywierszdorozrachunkow.getKontoid().getPelnynumer(),wnma);
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
                if (potraktujjakoNowaTransakcje==true) {
                    zablokujprzyciskzapisz = true;
                }
                RequestContext.getCurrentInstance().update("rozrachunki");
                RequestContext.getCurrentInstance().update("formcheckbox:znaczniktransakcji");
                RequestContext.getCurrentInstance().execute("drugishow();");
                //zerujemy rzeczy w dialogu
                wierszid = ""; wnlubma = "";
                RequestContext.getCurrentInstance().update("formwpisdokument");
                RequestContext.getCurrentInstance().execute("załadujmodelzachowywaniawybranegopola();");
            } else {
                Msg.msg("e", "Wybierz konto rozrachunkowe");
                //zerujemy rzeczy w dialogu
                wierszid = ""; wnlubma = "";
                RequestContext.getCurrentInstance().execute("załadujmodelzachowywaniawybranegopola();");
                RequestContext.getCurrentInstance().execute("powrotdopola();");
            }
        } catch (Exception e) {
            Msg.msg("e", "Wybierz pole zawierające numer konta");
            //zerujemy rzeczy w dialogu
            wierszid = ""; wnlubma = "";
            RequestContext.getCurrentInstance().execute("załadujmodelzachowywaniawybranegopola();");
            RequestContext.getCurrentInstance().execute("powrotdopola();");
        }
    }
    
    private void inicjalizacjaAktualnego(String wnma, int nrwiersza) {
        aktualnywierszdorozrachunkow = new Rozrachunekfk();
            if (wnma.equals("Wn")) {
                    WierszStronafk wierszStronafk = selected.getKonta().get(nrwiersza).getWierszStronaWn();
                    aktualnywierszdorozrachunkow.setWierszStronafk(wierszStronafk);
                    aktualnywierszdorozrachunkow.setKwotapierwotna(wierszStronafk.getKwota());
                    aktualnywierszdorozrachunkow.setPozostalo(wierszStronafk.getKwota());
                    aktualnywierszdorozrachunkow.setKontoid(wierszStronafk.getKonto());
                } else {
                    WierszStronafk wierszStronafk = selected.getKonta().get(nrwiersza).getWierszStronaMa();
                    aktualnywierszdorozrachunkow.setWierszStronafk(wierszStronafk);
                    aktualnywierszdorozrachunkow.setKwotapierwotna(wierszStronafk.getKwota());
                    aktualnywierszdorozrachunkow.setPozostalo(wierszStronafk.getKwota());
                    aktualnywierszdorozrachunkow.setKontoid(wierszStronafk.getKonto());
                }
        Rozrachunekfk pobranyrozrachunek = rozrachunekfkDAO.findRozrachunekfk(aktualnywierszdorozrachunkow);
        if (pobranyrozrachunek instanceof Rozrachunekfk) {
            aktualnywierszdorozrachunkow = pobranyrozrachunek;
        } 
        }
    
       
    private int pobierztransakcjeJakoSparowany() {
        //teraz z zapamietanych czyscimy klucz i liste pobrana wyzej
        //pobieramy listy z bazy
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
            boolean kwoty = x.GetSpRozl()!=0.0 && x.GetSpPoz()!=0.0;
            if (typdokumentu&&nrkolejnydokumentu&&nrPorzadkowyWiersza&&kwoty) {
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
    
    
    //************************* jeli pobierztransakcjeJakoSparowany() == 0 to robimy jakby nie byl nowa transakcja
    private void pobierzRozrachunekfkzBazy(String nrkonta, String wnma) {
          rozrachunekNowaTransakcja = new ArrayList<>();
          rozrachunekNowaTransakcja.addAll(rozrachunekfkDAO.findRozrachunkifkByKonto(nrkonta, wnma));
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
    
    private void pobierzjuzNaniesioneTransakcjeRozliczony(){
        zachowanewczejsniejtransakcje = new ArrayList<>();
        WierszStronafkPK klucz = aktualnywierszdorozrachunkow.getWierszStronafk().getWierszStronafkPK();
        Zestawienielisttransakcji pobranalista = new Zestawienielisttransakcji();
        pobranalista = zestawienielisttransakcjiDAO.findByKlucz(klucz);
        if (pobranalista instanceof Zestawienielisttransakcji) {
            zachowanewczejsniejtransakcje.addAll(pobranalista.getListatransakcji());
        }
    }
    private void naniesinformacjezwczesniejrozliczonych() {
        //sprawdz czy nowoutworzona transakcja nie znajduje sie juz w biezacetransakcje
        //jak jest to uzupelniamy jedynie rozliczenie biezace i archiwalne
        double sumaddlaaktualnego = 0.0;
        for (Transakcja r : transakcjeswiezynki) {
            if (!zachowanewczejsniejtransakcje.contains(r)) {
                biezacetransakcje.add(r);
            }
        }
        for (Transakcja s : zachowanewczejsniejtransakcje) {
            sumaddlaaktualnego += s.getKwotatransakcji();
            biezacetransakcje.add(s);
        }
        //aktualizujemy biezacy wiersz
        double rozliczono = aktualnywierszdorozrachunkow.getRozliczono();
        double pozostalo = aktualnywierszdorozrachunkow.getPozostalo();
        rozliczono = rozliczono + sumaddlaaktualnego;
        pozostalo = pozostalo - sumaddlaaktualnego;
        aktualnywierszdorozrachunkow.setRozliczono(rozliczono);
        aktualnywierszdorozrachunkow.setPozostalo(pozostalo);
    }
    
    private void pobierzjuzNaniesioneTransakcjeSparowane(){
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
     private void sumujdlaNowejTransakcji() {
         double sumaddlaaktualnego = 0.0;
         //czyscimy wartosci
         for(Transakcja p : transakcjejakosparowany) {
             double kwota = p.getKwotatransakcji();
             Transakcja nowa = new Transakcja();
             nowa.setZablokujnanoszenie(true);
             nowa.getTransakcjaPK().setRozliczany(p.getTransakcjaPK().getSparowany());
             nowa.getTransakcjaPK().setSparowany(p.getTransakcjaPK().getRozliczany());
             nowa.SetSpRozl(kwota);
             nowa.SetSpPoz(nowa.GetSpKwotaPier() - kwota);
             biezacetransakcje.add(nowa);
             sumaddlaaktualnego += kwota;
         }
        double rozliczono = aktualnywierszdorozrachunkow.getRozliczono();
        double pozostalo = aktualnywierszdorozrachunkow.getPozostalo();
        rozliczono = rozliczono + sumaddlaaktualnego;
        pozostalo = pozostalo - sumaddlaaktualnego;
        aktualnywierszdorozrachunkow.setRozliczono(rozliczono);
        aktualnywierszdorozrachunkow.setPozostalo(pozostalo);
        transakcjejakosparowany.clear();
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
            } catch (Exception r) {}
        }
        WierszStronafkPK klucz = aktualnywierszdorozrachunkow.getWierszStronafk().getWierszStronafkPK();
        List<Transakcja> doprzechowania = new ArrayList<>();
        doprzechowania.addAll(biezacetransakcje);
        //tu zrobie zapis do bazy danych
        try {
            Zestawienielisttransakcji szukana = zestawienielisttransakcjiDAO.findByKlucz(klucz);
            if (szukana instanceof Zestawienielisttransakcji) {
                zestawienielisttransakcjiDAO.destroy(szukana);
            }
            zestawienielisttransakcjiDAO.dodajListeTransakcji(klucz,biezacetransakcje);
            Msg.msg("i", "Udało się zachować rozrachunki w bazie danych");
        } catch (Exception e) {
            Msg.msg("e", "Nie udało się zachować rozrachunków w bazie danych");
        }
        biezacetransakcje.clear();
    }
    //********************
    //a to jest rodzial dotyczacy walut
    
    public void pobierzkursNBP(ValueChangeEvent  el){
        String nazwawaluty =  (String) el.getNewValue();
        String datadokumentu = selected.getDatawystawienia();
        DateTime dzienposzukiwany = new DateTime(datadokumentu);
        boolean znaleziono = false;
        int zabezpieczenie = 0;
        while (!znaleziono && (zabezpieczenie < 365)) {
            dzienposzukiwany = dzienposzukiwany.minusDays(1);
            String doprzekazania = dzienposzukiwany.toString("yyyy-MM-dd");
            Tabelanbp tabelanbppobrana = tabelanbpDAO.findByDateWaluta(doprzekazania,nazwawaluty);
            if (tabelanbppobrana instanceof Tabelanbp) {
                znaleziono = true;
                tabelanbp = tabelanbppobrana; 
                RequestContext.getCurrentInstance().update("formwpisdokument:panelwalut");
            }
            zabezpieczenie++;
        }
    }
    
    //********************************
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    
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

    public List<String> getWprowadzonesymbolewalut() {
        return wprowadzonesymbolewalut;
    }

    public void setWprowadzonesymbolewalut(List<String> wprowadzonesymbolewalut) {
        this.wprowadzonesymbolewalut = wprowadzonesymbolewalut;
    }

    public Tabelanbp getTabelanbp() {
        return tabelanbp;
    }

    public void setTabelanbp(Tabelanbp tabelanbp) {
        this.tabelanbp = tabelanbp;
    }
    
    //</editor-fold
    
    
  

}
