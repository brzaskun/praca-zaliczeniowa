/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.DokDAOfk;
import embeddablefk.Rozrachunekfk;
import embeddablefk.Transakcja;
import embeddablefk.WierszStronafk;
import embeddablefk.WierszStronafkPK;
import entityfk.Dokfk;
import entityfk.DokfkPK;
import entityfk.Wiersze;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
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
    //zmienne dla rozrachunkow
    @Inject private Rozrachunekfk aktualnywierszdorozrachunkow;
    private List<Rozrachunekfk> pobranestronywierszy;
    //a to sa listy do sortowanie transakcji na poczatku jedna mega do zbiorki wszystkich dodanych coby nie ginely
    private Map<WierszStronafkPK, List<Transakcja>> zestawienielisttransakcji;
    private List<Transakcja> biezacetransakcje;
    private List<Transakcja> transakcjeswiezynki;

    public DokfkView() {
        resetujDokument();
        this.wykazZaksiegowanychDokumentow = new ArrayList<>();
        this.pobranestronywierszy = new ArrayList<>();
        this.zestawienielisttransakcji = new HashMap<>();
        this.biezacetransakcje = new ArrayList<>();
        this.transakcjeswiezynki = new ArrayList<>();
    }
    
    @PostConstruct
    private void init() {
        try {
             wykazZaksiegowanychDokumentow = dokDAOfk.findAll();
        } catch (Exception e) {
        }
        zapisz0edytuj1 = false;
    }
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    //********************************************funkcje dla ksiegowania dokumentow
    //RESETUJ DOKUMNETFK
    private void resetujDokument() {
        selected = new Dokfk();
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
            dokDAOfk.usun(dousuniecia);
            wykazZaksiegowanychDokumentow.remove(dousuniecia);
            resetujDokument();
            RequestContext.getCurrentInstance().update("formwpisdokument");
            Msg.msg("i", "Dokument usunięty");
        } catch (Exception e) {
            Msg.msg("e", "Nie udało się usunąć dokumentu");
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
   
    //********************
    //rozrachunki
    public void rozrachunki(){
        //bierzemy parametry przekazane przez javascript po kazdorazowym kliknieciu pola konta
        String wnma = (String) Params.params("wpisywaniefooter:wnlubma");
        String nrwierszaS = (String) Params.params("wpisywaniefooter:wierszid");
        pobranestronywierszy = new ArrayList<>();
        try {
            Integer nrwiersza = Integer.parseInt(nrwierszaS) - 1;
            inicjalizacjaAktualnego(wnma, nrwiersza);
            if (aktualnywierszdorozrachunkow.getWierszStronafk().getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
                int onjestnowatransakcja = 0;
                onjestnowatransakcja = sprawdzczynieBylJakoSparowany();
                if (onjestnowatransakcja == 0) {
                    pobierzwierszezdokumentow(aktualnywierszdorozrachunkow.getWierszStronafk().getWierszStronafkPK(),selected.getKonta(),wnma);
                    stworznowetransakcjezPobranychstronwierszy();
                    naniesinformacjezwczesniejrozliczonych();
                } else {
                    
                }
                RequestContext.getCurrentInstance().update("rozrachunki");
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
            } else {
                WierszStronafk wierszStronafk = selected.getKonta().get(nrwiersza).getWierszStronaMa();
                aktualnywierszdorozrachunkow.setWierszStronafk(wierszStronafk);
                aktualnywierszdorozrachunkow.setKwotapierwotna(wierszStronafk.getKwota());
                aktualnywierszdorozrachunkow.setPozostalo(wierszStronafk.getKwota());
            }
    }
    
    private int sprawdzczynieBylJakoSparowany() {
        //teraz z zapamietanych czyscimy klucz i liste pobrana wyzej
        Collection<List<Transakcja>> kolekcje = zestawienielisttransakcji.values();
        for (List listazkolekcji : kolekcje) {
            for (Object danatransakcja : listazkolekcji) {
                Transakcja x = (Transakcja) danatransakcja;
                WierszStronafkPK idAktualny = aktualnywierszdorozrachunkow.getWierszStronafk().getWierszStronafkPK();
                boolean typdokumentu = x.idSparowany().getTypdokumentu().equals(idAktualny.getTypdokumentu());
                boolean nrkolejnydokumentu = x.idSparowany().getNrkolejnydokumentu() == idAktualny.getNrkolejnydokumentu();
                boolean nrPorzadkowyWiersza = x.idSparowany().getNrPorzadkowyWiersza() == idAktualny.getNrPorzadkowyWiersza();
                if (typdokumentu&&nrkolejnydokumentu&&nrPorzadkowyWiersza) {
                    Msg.msg("w", "on byl jako sparowany");
                    return 1;
                }
            }
        }
        //nie znaleziono transakcji gdzie aktualnybylby sparowanym
        Msg.msg("w", "on NIE byl jako sparowany");
        return 0;
    }
    
    
    //************************* jeli sprawdzczynieBylJakoSparowany() == 0 to robimy jakby nie byl nowa transakcja
    private void pobierzwierszezdokumentow(WierszStronafkPK wierszStronafkPK, List<Wiersze> wierszezdokumentu, String wnma) {
        List<WierszStronafk> wybraneStronyWierszy = new ArrayList<>();
        if (wierszezdokumentu.size() > 1) {
        for (Wiersze p : wierszezdokumentu) {
            if (wnma.equals("Wn")) {
                WierszStronafk r = p.getWierszStronaMa();
                if (!r.getWierszStronafkPK().equals(wierszStronafkPK)&&r.getKonto().getPelnynumer().equals(aktualnywierszdorozrachunkow.getWierszStronafk().getKonto().getPelnynumer())) {
                    wybraneStronyWierszy.add(p.getWierszStronaWn());
                }
            } else {
                WierszStronafk r = p.getWierszStronaWn();
                if (!r.getWierszStronafkPK().equals(wierszStronafkPK)&&r.getKonto().getPelnynumer().equals(aktualnywierszdorozrachunkow.getWierszStronafk().getKonto().getPelnynumer())) {
                    wybraneStronyWierszy.add(p.getWierszStronaMa());
                }
            }
        }
        //pobrano wiersze - a teraz z nich robie rozrachunki
        for (WierszStronafk s : wybraneStronyWierszy) {
            Rozrachunekfk tmp = new Rozrachunekfk();
            tmp.setWierszStronafk(s);
            tmp.setKwotapierwotna(s.getKwota());
            tmp.setPozostalo(s.getKwota());
            pobranestronywierszy.add(tmp);
            
        }
        }
    }
    
    
    private void stworznowetransakcjezPobranychstronwierszy() {
        //z utworzonych rozrachunkow tworzy sie transkakcje laczac rozrachunek rozliczony ze sparowanym
        transakcjeswiezynki = new ArrayList<>();
        for (Rozrachunekfk p : pobranestronywierszy) {
            Transakcja transakcja = new Transakcja();
            transakcja.getTransakcjaPK().setRozliczany(aktualnywierszdorozrachunkow);
            transakcja.getTransakcjaPK().setSparowany(p);
            transakcjeswiezynki.add(transakcja);
        }
    }
    
    private void naniesinformacjezwczesniejrozliczonych() {
        List<Transakcja> staretransakcjePasujace = new ArrayList<>();
        //pobieramy do biezacych zachowane rozrachunki z kluczem jakim jest PK aktualnegowiersza
        WierszStronafkPK klucz = aktualnywierszdorozrachunkow.getWierszStronafk().getWierszStronafkPK();
        if (zestawienielisttransakcji.containsKey(klucz)) {
            staretransakcjePasujace.addAll(zestawienielisttransakcji.get(klucz));
            zestawienielisttransakcji.remove(klucz);
        }
        //sprawdz czy nowoutworzona transakcja nie znajduje sie juz w biezacetransakcje
        //jak jest to uzupelniamy jedynie rozliczenie biezace i archiwalne
        Iterator it = transakcjeswiezynki.iterator();
        while (it.hasNext()) {
            Transakcja r = (Transakcja) it.next();
            if (staretransakcjePasujace.contains(r)) {
                int index = staretransakcjePasujace.indexOf(r);
                Transakcja donaniesienia = staretransakcjePasujace.get(index);
                //ja jest to dodaj kwoty ze starych
                double kwota = donaniesienia.getKwotatransakcji();
                r.setKwotatransakcji(kwota);
                r.setPoprzedniakwota(kwota);
                staretransakcjePasujace.remove(r);
            }
        }
        //teraz z zapamietanych czyscimy klucz i liste pobrana wyzej
        Collection<List<Transakcja>> kolekcje = zestawienielisttransakcji.values();
        List<Transakcja> pozostaletransakcje = new ArrayList<>();
        for (List p : kolekcje) {
            for (Object r : p) {
                pozostaletransakcje.add((Transakcja) r);
            }
        }
        //a teraz przechodze stare jeszcze raz (wczesniej usunalem identyczne0 i kopiuje rozliczenie biezace do juz rozliczonych
        double sumaddlaaktualnego = 0.0;
        Iterator itX = transakcjeswiezynki.iterator();
        while (itX.hasNext()) {
            Transakcja r = (Transakcja) itX.next();
            for (Transakcja s : pozostaletransakcje) {
                //ja jest to dodaj kwoty ze starych
                if (s.idSparowany().equals(r.idSparowany())) {
                    double kwota = s.getKwotatransakcji();
                    sumaddlaaktualnego += kwota;
                    r.SetSpRozl(r.GetSpRozl() + kwota);
                    r.SetSpPoz(r.GetSpKwotaPier() - r.GetSpRozl());
                }
            }
        }
        //aktualizujemy biezacy wiersz
        zaktualizujaktualnywiersz(sumaddlaaktualnego);
        //dodajemy nowe transakcje do istniejacej
        if (transakcjeswiezynki.size() > 0) {
            for (Transakcja s : transakcjeswiezynki) {
                biezacetransakcje.add(s);//te co nie istnieja dodaj do listy biezace transakcje
            }
        }
    }
    
     private void zaktualizujaktualnywiersz(double kwota) {
        double rozliczono = aktualnywierszdorozrachunkow.getRozliczono();
        double pozostalo = aktualnywierszdorozrachunkow.getPozostalo();
        rozliczono = rozliczono + kwota;
        pozostalo = pozostalo - kwota;
        aktualnywierszdorozrachunkow.setRozliczono(rozliczono);
        aktualnywierszdorozrachunkow.setPozostalo(pozostalo);
    }
     //*************************
     //************************* jeli sprawdzczynieBylJakoSparowany() == 1 to robimy jakby byl nowa transakcja
     
     //*************************
    //nanosi transakcje z kwotami na rozrachunki
    public void zapistransakcji() {
        for (Transakcja p : biezacetransakcje) {
            double kwotanowa = p.getKwotatransakcji();
            double kwotastara = p.getPoprzedniakwota();
            double roznica = kwotastara - kwotanowa;
            double aktualny = p.getTransakcjaPK().getRozliczany().getRozliczono();
            double aktualny_pierwotna = p.getTransakcjaPK().getRozliczany().getKwotapierwotna();
            double sparowany = p.getTransakcjaPK().getSparowany().getRozliczono();
            double sparowany_pierwotna = p.getTransakcjaPK().getSparowany().getKwotapierwotna();
            if (roznica > 0) {
                p.getTransakcjaPK().getRozliczany().setRozliczono(aktualny + roznica);
                p.getTransakcjaPK().getRozliczany().setPozostalo(aktualny_pierwotna - roznica);
                p.getTransakcjaPK().getSparowany().setRozliczono(sparowany + roznica);
                p.getTransakcjaPK().getSparowany().setPozostalo(sparowany_pierwotna - roznica);
            } else if (roznica < 0) {
                p.getTransakcjaPK().getRozliczany().setRozliczono(aktualny - roznica);
                p.getTransakcjaPK().getRozliczany().setPozostalo(aktualny_pierwotna + roznica);
                p.getTransakcjaPK().getSparowany().setRozliczono(sparowany - roznica);
                p.getTransakcjaPK().getSparowany().setPozostalo(sparowany_pierwotna + roznica);
            }
            p.setPoprzedniakwota(kwotanowa);
        }
        WierszStronafkPK klucz = aktualnywierszdorozrachunkow.getWierszStronafk().getWierszStronafkPK();
        List<Transakcja> doprzechowania = new ArrayList<>();
        doprzechowania.addAll(biezacetransakcje);
        if (zestawienielisttransakcji.containsKey(klucz)) {
            zestawienielisttransakcji.remove(klucz);
            zestawienielisttransakcji.put(klucz, doprzechowania);
        } else {
            zestawienielisttransakcji.put(klucz, doprzechowania);
        }
        biezacetransakcje.clear();
    }
    //********************
    
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
 
    public List<Rozrachunekfk> getPobranestronywierszy() {
        return pobranestronywierszy;
    }

    public void setPobranestronywierszy(List<Rozrachunekfk> pobranestronywierszy) {
        this.pobranestronywierszy = pobranestronywierszy;
    }

    public List<Transakcja> getBiezacetransakcje() {
        return biezacetransakcje;
    }

    public void setBiezacetransakcje(List<Transakcja> biezacetransakcje) {
        this.biezacetransakcje = biezacetransakcje;
    }
   
     
    //</editor-fold>

    

  
  

}
