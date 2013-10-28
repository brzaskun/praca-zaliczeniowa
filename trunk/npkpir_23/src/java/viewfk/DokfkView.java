/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.RozrachunkiDAO;
import dao.WierszeDAO;
import daoFK.DokDAOfk;
import entityfk.Dokfk;
import entityfk.DokfkPK;
import entityfk.Rozrachunki;
import entityfk.RozrachunkiPK;
import entityfk.Wiersze;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.extensions.event.EventDataWrapper;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SelectableDataModelWrapper;
import org.primefaces.util.ComponentUtils;
import params.Params;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DokfkView implements Serializable {

    protected Dokfk selected;
    private int liczbawierszy;
    private List<Wiersze> wiersze;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private WierszeDAO wierszeDAO;
    @Inject
    private Wiersze wiersz;
    @Inject
    private Wiersze aktualnywierszdorozrachunkow;
    private static List<Dokfk> selecteddokfk;
    private List<Dokfk> wykaz;
    private boolean zapisz0edytuj1;
    private int numerwierszazapisu;
    //to jest tablica dla przechowywania rozrachunkow podczas otwierania konkretnego zapisu
    private List<RozrachunkiTmp> rozrachunkiwierszewdokumencie;
    //to jest zbior list rozrachunkow wykorzystywany do zachowywania list podczas lazenia po kontach aby pozniej zachowac je w bazie po
    //wcisnieciu klawisza dodaj albo edytuj
    private Map<Kluczlistyrozrachunkow, List<RozrachunkiTmp>> zestawienielistrozrachunow;
    @Inject
    private RozrachunkiDAO rozrachunkiDAO;
    private DokfkDataModel listadatamodel;

    public DokfkView() {
        liczbawierszy = 1;
        selected = new Dokfk();
        DokfkPK dokfkPK = new DokfkPK();
        dokfkPK.setRok("2013");
        selected.setDokfkPK(dokfkPK);
        wiersze = new ArrayList<>();
        wiersze.add(new Wiersze(1, 0));
        selected.setKonta(wiersze);
//        List<Kontozapisy> zapisynakoncie = new ArrayList<>();
//        selected.setZapisynakoncie(zapisynakoncie);
        wykaz = new ArrayList<>();
        selecteddokfk = new ArrayList<>();
        zestawienielistrozrachunow = new HashMap<>();
        
    }

    @PostConstruct
    private void init() {
        wykaz = dokDAOfk.findAll();
        listadatamodel = new DokfkDataModel(wykaz);
    }

    public void liczbaw() {
        liczbawierszy = selected.getKonta().size();
        double pierwsze = selected.getKonta().get(liczbawierszy - 1).getKwotaWn();
        double drugie = selected.getKonta().get(liczbawierszy - 1).getKwotaMa();
        if (pierwsze != 0 || drugie != 0) {
            liczbawierszy++;
            selected.getKonta().add(new Wiersze(liczbawierszy, 0));
        }
    }

//   /**
//    * Usuwanie wierszy z dokumenu ksiegowego
//    */
    public void liczbawu() {
        if (liczbawierszy > 1) {
            liczbawierszy--;
            selected.getKonta().remove(liczbawierszy);
        }
    }

    public void edycja() {
        try {
            uzupelnijwierszeodane();
            //nanieszapisynakontach();
            dokDAOfk.edit(selected);
            wykaz.clear();
            wykaz = dokDAOfk.findAll();
            rozlicznaniesionerozrachunki();
            Msg.msg("i", "Pomyślnie zaktualizowano dokument");
        } catch (Exception e) {
            System.out.println(e.toString());
            Msg.msg("e", "Nie udało się zmenic dokumentu " + e.toString());
        }
    }

    //on robi nie tylko to ze przywraca button, on jeszcze resetuje selected
    //dodalem to tutaj a nie przy funkcji edytuj bo wtedy nie wyswietlalo wiadomosci o edycji
    public void przywrocwpisbutton() {
        setZapisz0edytuj1(false);
        liczbawierszy = 1;
        selected = new Dokfk();
        DokfkPK dokfkPK = new DokfkPK();
        dokfkPK.setRok("2013");
        selected.setDokfkPK(dokfkPK);
        wiersze = new ArrayList<>();
        wiersze.add(new Wiersze(1, 0));
        selected.setKonta(wiersze);
        setZapisz0edytuj1(false);
        zestawienielistrozrachunow = new HashMap<>();
        RequestContext.getCurrentInstance().execute("pierwszy.hide();");
    }

    public void dodaj() {
        try {
            uzupelnijwierszeodane();
            //nanosimy zapisy na kontach i dodajemy jako pozycję dokumentu fk
            //nanieszapisynakontach();
            //najpierw zobacz czy go nie ma, jak jest to usun i dodaj
            Dokfk poszukiwanydokument = dokDAOfk.findDokfk(selected.getDatawystawienia(), selected.getNumer());
            if (poszukiwanydokument instanceof Dokfk) {
                dokDAOfk.destroy(poszukiwanydokument);
                dokDAOfk.dodaj(selected);
            } else {
                dokDAOfk.dodaj(selected);
            }
            wykaz.add(selected);
            rozlicznaniesionerozrachunki();
            selected = new Dokfk();
            DokfkPK dokfkPK = new DokfkPK();
            dokfkPK.setRok("2013");
            selected.setDokfkPK(dokfkPK);
            wiersze = new ArrayList<>();
            wiersze.add(new Wiersze(1, 0));
            selected.setKonta(wiersze);
            liczbawierszy = 1;
            zestawienielistrozrachunow = new HashMap<>();
            RequestContext.getCurrentInstance().update("formwpisdokument");
            Msg.msg("i", "Dokument dodany");
        } catch (Exception e) {
            Msg.msg("e", "Nie udało się dodac dokumentu " + e.toString());
        }
    }

    public void zresetujpoladialogu() {
        selected = new Dokfk();
        wiersze = new ArrayList<>();
        wiersze.add(new Wiersze(1, 0));
        selected.setKonta(wiersze);
    }

    private void uzupelnijwierszeodane() {
        //ladnie uzupelnia informacje o wierszu pk
        String opisdoprzekazania = "";
        List<Wiersze> wierszewdokumencie = selected.getKonta();
        try {
            for (Wiersze p : wierszewdokumencie) {
                String opis = p.getOpis();
                if (opis.contains("kontown")) {
                    p.setKonto(p.getKontoWn());
                    p.setKontonumer(p.getKontoWn().getPelnynumer());
                    p.setKontoprzeciwstawne(p.getKontoMa().getPelnynumer());
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.setKwotaMa(0.0);
                    p.setTypwiersza(1);
                    p.setDokfk(selected);
                    p.setZaksiegowane(Boolean.FALSE);
                } else if (opis.contains("kontoma")) {
                    p.setKonto(p.getKontoMa());
                    p.setKontonumer(p.getKontoMa().getPelnynumer());
                    //p.setKontoprzeciwstawne(p.getKontoWn().getPelnynumer());
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.setKwotaWn(0.0);
                    p.setTypwiersza(2);
                    p.setDokfk(selected);
                    p.setZaksiegowane(Boolean.FALSE);
                } else {
                    p.setKonto(p.getKontoWn().getNazwaskrocona().startsWith("2") ? p.getKontoWn() : p.getKontoMa());
                    p.setKontonumer(p.getKontoWn().getNazwaskrocona().startsWith("2") ? p.getKontoWn().getPelnynumer() : p.getKontoMa().getPelnynumer());
                    p.setKontoprzeciwstawne(p.getKontoWn().getNazwaskrocona().startsWith("2") ? p.getKontoMa().getPelnynumer() : p.getKontoWn().getPelnynumer());
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.setTypwiersza(0);
                    p.setDokfk(selected);
                    opisdoprzekazania = p.getOpis();
                    p.setZaksiegowane(Boolean.FALSE);
                }
            }
        } catch (Exception e) {
            Msg.msg("e", "Błąd w pliku DokfkView w funkcji uzupelnijwierszeodane");
        }
    }

    public void usundokument(Dokfk dousuniecia) {
        try {
            dokDAOfk.usun(dousuniecia);
            wykaz.remove(dousuniecia);
            Msg.msg("i", "Dokument usunięty");
        } catch (Exception e) {
            Msg.msg("e", "Nie udało się usunąć dokumentu");
        }
    }

    public void rozrachunki() {
        String wnlubma = (String) Params.params("wpisywaniefooter:wnlubma");
        uzupelnijwierszeodanewtrakcie();
        Dokfk sprawdz = selected;
        String nrwierszaS = (String) Params.params("wpisywaniefooter:wierszid");
        Integer nrwiersza = Integer.parseInt(nrwierszaS);
        nrwiersza--;
        aktualnywierszdorozrachunkow = selected.getKonta().get(nrwiersza);
        uzupelnijaktualnywiersz(wnlubma);
        pobierzwierszezbiezacegodokumentu();
        if (aktualnywierszdorozrachunkow.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
            RequestContext.getCurrentInstance().update("rozrachunki");
            RequestContext.getCurrentInstance().execute("drugishow();");
        } else {
            Msg.msg("e", "Wybierz konto rozrachunkowe");
        }
    }

    private void uzupelnijwierszeodanewtrakcie() {
        //ladnie uzupelnia informacje o wierszu pk
        List<Wiersze> wierszewdokumencie = selected.getKonta();
        try {
            for (Wiersze p : wierszewdokumencie) {
                p.setDataksiegowania(selected.getDatawystawienia());
                p.setDokfk(selected);
                p.setZaksiegowane(Boolean.FALSE);
            }
        } catch (Exception e) {
            Msg.msg("e", "Błąd w pliku DokfkView w funkcji uzupelnijwierszeodane");
        }
    }

    private void uzupelnijaktualnywiersz(String wnlubma) {
        //ladnie uzupelnia informacje o wierszu pk
        Wiersze p = aktualnywierszdorozrachunkow;
        try {
            if (wnlubma.equals("Wn")) {
                p.setKonto(p.getKontoWn());
                p.setKontonumer(p.getKontoWn().getPelnynumer());
                p.setDataksiegowania(selected.getDatawystawienia());
                p.setKwotapierwotna(p.getKwotaWn());
                p.setDokfk(selected);
                p.setZaksiegowane(Boolean.FALSE);
                p.setWnlubma("Wn");
            } else {
                p.setKonto(p.getKontoMa());
                p.setKontonumer(p.getKontoMa().getPelnynumer());
                p.setDataksiegowania(selected.getDatawystawienia());
                p.setKwotapierwotna(p.getKwotaMa());
                p.setDokfk(selected);
                p.setZaksiegowane(Boolean.FALSE);
                p.setWnlubma("Ma");
            }
        } catch (Exception e) {
            Msg.msg("e", "Błąd w pliku DokfkView w funkcji uzupelnijwierszeodane");
        }
    }

    private void pobierzwierszezbiezacegodokumentu() {
        rozrachunkiwierszewdokumencie = new ArrayList<>();
        List<Wiersze> wierszezdokumentu = selected.getKonta();
        try {
            List<Rozrachunki> zapisanerozrachunkiwbazie = rozrachunkiDAO.findRozliczany(aktualnywierszdorozrachunkow.getIdwiersza());
            for (Wiersze p : wierszezdokumentu) {
                //pobieram tylko te konta ktore nie leza po tej samej stronie dokumetu co konto rozrachunkowe i sa takie same jak konto rozrliczane
                if (aktualnywierszdorozrachunkow.getWnlubma().equals("Wn")) {
                    if (p.getKontoMa().equals(aktualnywierszdorozrachunkow.getKontoWn())) {
                        aktualnywierszdorozrachunkow.setWnlubma("Wn");
                        //szukamy bo moze juz byl taki rozrachunek
                        for (Rozrachunki r : zapisanerozrachunkiwbazie) {
                            if (r.getWierszrozliczany().equals(aktualnywierszdorozrachunkow) && r.getWierszsparowany().equals(p)) {
                                rozrachunkiwierszewdokumencie.add(new RozrachunkiTmp(r.getKwotarozrachunku(), r.getKwotarozrachunku(), p, aktualnywierszdorozrachunkow, "Wn"));
                                //ustawiam flage zeby nie dolozylo tego wiersza ponownie potem go resetuje
                                p.setDodanydorozrachunkow(true);
                            }
                        }
                        //jak nie ma zachowanego rozrachunku to musze wykreowac nowy pusty
                        if (!p.isDodanydorozrachunkow()) {
                            rozrachunkiwierszewdokumencie.add(new RozrachunkiTmp(p, aktualnywierszdorozrachunkow, "Wn"));
                        }
                        p.setDodanydorozrachunkow(false);
                        p.setKonto(p.getKontoMa());
                        p.setKontonumer(p.getKonto().getNazwapelna());
                        p.setKontoprzeciwstawne(p.getKontoWn().getPelnynumer());
                        p.setKwotapierwotna(p.getKwotaMa());
                        p.setWnlubma("Ma");
                    }
                } else {
                    if (p.getKontoWn().equals(aktualnywierszdorozrachunkow.getKontoMa())) {
                        aktualnywierszdorozrachunkow.setWnlubma("Ma");
                        //szukamy bo moze juz byl taki rozrachunek
                        for (Rozrachunki r : zapisanerozrachunkiwbazie) {
                            if (r.getWierszrozliczany().equals(aktualnywierszdorozrachunkow) && r.getWierszsparowany().equals(p)) {
                                rozrachunkiwierszewdokumencie.add(new RozrachunkiTmp(r.getKwotarozrachunku(), r.getKwotarozrachunku(), p, aktualnywierszdorozrachunkow, "Ma"));
                                //ustawiam flage zeby nie dolozylo tego wiersza ponownie potem go resetuje
                                p.setDodanydorozrachunkow(true);
                            }
                        }
                        //jak nie ma zachowanego rozrachunku to musze wykreowac nowy pusty
                        if (!p.isDodanydorozrachunkow()) {
                            rozrachunkiwierszewdokumencie.add(new RozrachunkiTmp(p, aktualnywierszdorozrachunkow, "Ma"));
                        }
                        p.setDodanydorozrachunkow(false);
                        p.setKonto(p.getKontoWn());
                        p.setKontonumer(p.getKonto().getNazwapelna());
                        p.setKontoprzeciwstawne(p.getKontoMa().getPelnynumer());
                        p.setKwotapierwotna(p.getKwotaWn());
                        p.setWnlubma("Wn");
                    }
                }
            }
            //uzupelniamy sporzadzona liste o uprzednio zapamietane kwoty powstale po uprzednim wpisaniu podczas edycji
            sprawdzczyjuzczegosnienaniesiono();
        } catch (Exception e) {
            Msg.msg("e", "Blad w DokfkView funkcja pobierzwierszezbiezacegodokumentu");
        }
    }

    public static class RozrachunkiTmp {

        private double kwotarozrachunku;
        private double kwotapierwotna;
        private Wiersze wierszsparowany;
        private Wiersze wierszrozliczany;
        private String wnlubma;

        public RozrachunkiTmp() {
        }

        public RozrachunkiTmp(Wiersze wierszsparowany, Wiersze wierszrozliczany, String wnlubma) {
            this.wierszsparowany = wierszsparowany;
            this.wierszrozliczany = wierszrozliczany;
            this.wnlubma = wnlubma;
        }

      
        public RozrachunkiTmp(double kwotarozrachunku, double kwotapierwotna, Wiersze wierszsparowany, Wiersze wierszrozliczany, String wnlubma) {
            this.kwotarozrachunku = kwotarozrachunku;
            this.kwotapierwotna = kwotapierwotna;
            this.wierszsparowany = wierszsparowany;
            this.wierszrozliczany = wierszrozliczany;
            this.wnlubma = wnlubma;
        }

       

        //<editor-fold defaultstate="collapsed" desc="comment">
        public double getKwotapierwotna() {
            return kwotapierwotna;
        }
        
        public void setKwotapierwotna(double kwotapierwotna) {
            this.kwotapierwotna = kwotapierwotna;
        }
        
        public double getKwotarozrachunku() {
            return kwotarozrachunku;
        }
        
        public void setKwotarozrachunku(double kwotarozrachunku) {
            this.kwotarozrachunku = kwotarozrachunku;
        }
        
        public Wiersze getWierszsparowany() {
            return wierszsparowany;
        }
        
        public void setWierszsparowany(Wiersze wierszsparowany) {
            this.wierszsparowany = wierszsparowany;
        }
        
        public Wiersze getWierszrozliczany() {
            return wierszrozliczany;
        }
        
        public void setWierszrozliczany(Wiersze wierszrozliczany) {
            this.wierszrozliczany = wierszrozliczany;
        }
        
        public String getWnlubma() {
            return wnlubma;
        }
        
        public void setWnlubma(String wnlubma) {
            this.wnlubma = wnlubma;
        }
        //</editor-fold>
        
        
    }
    
//zapisujemy na koniec rozrachunki w bazie danych na trwałe
    public void rozlicznaniesionerozrachunki() {
        try {
            Set<Kluczlistyrozrachunkow> listakluczyrozrachunkow = zestawienielistrozrachunow.keySet();
            for (Kluczlistyrozrachunkow klucz : listakluczyrozrachunkow) {
                for (RozrachunkiTmp p : zestawienielistrozrachunow.get(klucz)) {
                    RozrachunkiPK rPK = new RozrachunkiPK();
                    rPK.setZapisrozliczany(p.getWierszrozliczany().getIdwiersza());
                    rPK.setZapissparowany(p.getWierszsparowany().getIdwiersza());
                    Rozrachunki r = new Rozrachunki(rPK);
                    r.setKwotarozrachunku(p.getKwotarozrachunku());
                    r.setWierszrozliczany(p.getWierszrozliczany());
                    r.setWierszsparowany(p.getWierszsparowany());
                    for (Wiersze s : selected.getKonta()) {
                        if (s.getIdwiersza().equals(p.getWierszrozliczany().getIdwiersza())) {
                            s.getRozrachunkijakorozliczany().add(r);
                        }
                    }
                    for (Wiersze s : selected.getKonta()) {
                        if (s.getIdwiersza().equals(p.getWierszsparowany().getIdwiersza())) {
                            s.getRozrachunkijakosparowany().add(r);
                        }
                    }
                    dokDAOfk.edit(selected);
                    Msg.msg("i", "Rozrachunki naniesione");
                }
            }
        } catch (Exception ex) {
            Msg.msg("w", "Nie naniesiono rozrachunkow");
        }
    }
    
    //robie to w momencie zamkniecia okrna z rozrachunkami aby aktualizowalo pola do rozliczenia i rozliczono juz w trakcie a nie dopiero
    //podczas wpisywania do bazy
    private void naniesieniekonsekwencjirozrachunkownawierszach() {
        wyczyscdotychczasowezapisyrozrachunkow();
        Set<Kluczlistyrozrachunkow> listakluczyrozrachunkow = zestawienielistrozrachunow.keySet();
        for (Kluczlistyrozrachunkow klucz : listakluczyrozrachunkow) {
            for (RozrachunkiTmp p : zestawienielistrozrachunow.get(klucz)) {
                for (Wiersze s : selected.getKonta()) {
                    if (s.getIdwiersza().equals(p.getWierszrozliczany().getIdwiersza())) {
                        if (s.getWnlubma().equals("Wn")) {
                            //tu rozlicza sie to czy zmniejszono czy zwiekszono rozrachunek podczas jego edycji
                            try {
                                s.setRozliczonoWn(s.getRozliczonoWn() + p.getKwotarozrachunku());
                            } catch (Exception e1) {
                                s.setRozliczonoWn(p.getKwotarozrachunku());
                            }
                            s.setPozostalodorozliczeniaWn(s.getKwotaWn() - s.getRozliczonoWn());
                        } else {
                            //tu rozlicza sie to czy zmniejszono czy zwiekszono rozrachunek podczas jego edycji
                            try {
                                s.setRozliczonoMa(s.getRozliczonoMa() + p.getKwotarozrachunku());
                            } catch (Exception e1) {
                                s.setRozliczonoMa(p.getKwotarozrachunku());
                            }
                            s.setPozostalodorozliczeniaMa(s.getKwotaMa() - s.getRozliczonoMa());
                        }
                    }
                }
                for (Wiersze s : selected.getKonta()) {
                    if (s.getIdwiersza().equals(p.getWierszsparowany().getIdwiersza())) {
                        if (s.getWnlubma().equals("Wn")) {
                            double roznica = p.getKwotarozrachunku() - p.getKwotapierwotna();
                            try {
                                s.setRozliczonoWn(s.getRozliczonoWn() + roznica);
                            } catch (Exception e1) {
                                s.setRozliczonoWn(p.getKwotarozrachunku());
                            }
                            s.setPozostalodorozliczeniaWn(s.getKwotaWn() - s.getRozliczonoWn());
                        } else {
                            try {
                                s.setRozliczonoMa(s.getRozliczonoMa() + p.getKwotarozrachunku());
                            } catch (Exception e1) {
                                s.setRozliczonoMa(p.getKwotarozrachunku());
                            }
                            s.setPozostalodorozliczeniaMa(s.getKwotaMa() - s.getRozliczonoMa());
                        }
                    }
                }
                dokDAOfk.edit(selected);
            }
        }
        Msg.msg("i", "Wiersza zaktulalizowane o kwoty rozliczone");
    }

    //czyszcze info o rozliczonych i pozostalych do rozliczenia aby wprowadzic nowe wartosc sumowane od nowa
    private void wyczyscdotychczasowezapisyrozrachunkow() {
        List<Wiersze> listadowyczyszczenia = selected.getKonta();
        for (Wiersze p : listadowyczyszczenia) {
            p.setRozliczonoMa(0.0);
            p.setRozliczonoWn(0.0);
            p.setPozostalodorozliczeniaWn(0.0);
            p.setPozostalodorozliczeniaMa(0.0);
        }
    }
    
    public void zapisanorozrachunek() {
        Wiersze rozliczany = rozrachunkiwierszewdokumencie.get(0).wierszrozliczany;
        Kluczlistyrozrachunkow tmp = new Kluczlistyrozrachunkow(rozliczany.getIdporzadkowy(), rozliczany.getWnlubma());
        if (zestawienielistrozrachunow.containsKey(tmp)) {
            zestawienielistrozrachunow.remove(tmp);
            zestawienielistrozrachunow.put(tmp, rozrachunkiwierszewdokumencie);
        } else {
            zestawienielistrozrachunow.put(tmp, rozrachunkiwierszewdokumencie);
        }
        naniesieniekonsekwencjirozrachunkownawierszach();
        Msg.msg("i", "Zapisano rozrachunki");
    }
    
    //jak sie lazi po kontach to mozna przy otwartym dokumencie wrocic do zapisu przez zapisaniem calego dokumentu
    //to odtwarza wprowadzone zapisy
    private void sprawdzczyjuzczegosnienaniesiono () {
        Wiersze rozliczany = rozrachunkiwierszewdokumencie.get(0).wierszrozliczany;
        Kluczlistyrozrachunkow tmp = new Kluczlistyrozrachunkow(rozliczany.getIdporzadkowy(), rozliczany.getWnlubma());
        if (zestawienielistrozrachunow.containsKey(tmp)) {
             List<RozrachunkiTmp> listazapamietana = zestawienielistrozrachunow.get(tmp);
            for (RozrachunkiTmp p : listazapamietana) {
                for (RozrachunkiTmp r : rozrachunkiwierszewdokumencie) {
                    if (p.getWierszrozliczany().getIdporzadkowy()==r.getWierszrozliczany().getIdporzadkowy()&&p.getWierszsparowany().getIdporzadkowy()==r.getWierszsparowany().getIdporzadkowy()&&p.getWnlubma().equals(p.getWnlubma())) {
                        r.setKwotarozrachunku(p.getKwotarozrachunku());
                    }
                }
            }
        }
        Msg.msg("i", "Odtworzono zapamietane rozrachunki");
    }

//     public void nanieszapisynakontach(){
//         if (!selected.getZapisynakoncie().isEmpty()){
//            usunistniejacezapisy(selected.getZapisynakoncie());
//         }
//         List<Kontozapisy> zapisynakontach = new ArrayList<>();
//         String opis = "";
//         Dokfk x = selected;
//         List<Wiersze> wierszewdokumencie = x.getKonta();
//         for(Wiersze p : wierszewdokumencie){
//         if(p.getTypwiersza()==1){
//             dodajwn(p, x, opis, zapisynakontach);
//         } else if(p.getTypwiersza()==2) {
//             dodajma(p, x, opis, zapisynakontach);
//         } else {
//             opis = p.getOpis();
//             dodajwn(p, x, opis, zapisynakontach);
//             dodajma(p, x, opis, zapisynakontach);
//         }
//         }
//         x.setNaniesionezapisy(true);
//         x.setZapisynakoncie(zapisynakontach);
//         RequestContext.getCurrentInstance().update("zestawieniedokumentow:dataList");
//         Msg.msg("i", "Zapisy na kontacg wygenerowane "+x.getNumer());
//         
//     }
//     private void usunistniejacezapisy(List<Kontozapisy> zachowanezapisy){
//         try {
//         for(Kontozapisy p : zachowanezapisy){
//             kontoZapisyFKDAO.destroy(p);
//         }
//         } catch (Exception ex) {
//             Msg.msg("e", "Błąd przy usuwaniu istniejących zapisó na kontach");
//         }
//     }
//     private void dodajwn(Wiersze p,Dokfk x, String opis, List<Kontozapisy> zapisynakontach){
//             Kontozapisy kontozapisy = new Kontozapisy();
//             kontozapisy.setKonto(p.getKontoWn().getPelnynumer());
//             kontozapisy.setKontoob(p.getKontoWn());
//             kontozapisy.setKontoprzeciwstawne(p.getKontoMa().getPelnynumer());
//             kontozapisy.setNumer(x.getNumer());
//             kontozapisy.setOpis(opis);
//             kontozapisy.setKontown(p.getKontoWn().getNazwapelna());
//             kontozapisy.setKontoma(p.getKontoMa().getNazwapelna());
//             kontozapisy.setKwotawn(p.getKwotaWn());
//             kontozapisy.setKwotama(0);
//             kontozapisy.setDokfk(x);
//             kontozapisy.setWartoscpierwotna(p.getKwotaWn());
//             kontozapisy.setDorozliczenia(0);
//             zapisynakontach.add(kontozapisy);             
//     }
//     
//     private void dodajma(Wiersze p,Dokfk x, String opis,  List<Kontozapisy> zapisynakontach){
//             Kontozapisy kontozapisy = new Kontozapisy();
//             kontozapisy.setKonto(p.getKontoMa().getPelnynumer());
//             kontozapisy.setKontoob(p.getKontoMa());
//             kontozapisy.setKontoprzeciwstawne(p.getKontoWn().getPelnynumer());
//             kontozapisy.setNumer(x.getNumer());
//             kontozapisy.setOpis(opis);
//             kontozapisy.setKontown(p.getKontoWn().getNazwapelna());
//             kontozapisy.setKontoma(p.getKontoMa().getNazwapelna());
//             kontozapisy.setKwotawn(0);
//             kontozapisy.setKwotama(p.getKwotaMa());
//             kontozapisy.setDokfk(x);
//             kontozapisy.setWartoscpierwotna(p.getKwotaMa());
//             kontozapisy.setDorozliczenia(0);
//             zapisynakontach.add(kontozapisy);
//     }
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

    public void wybranodokmessage() {
        Msg.msg("i", "Wybrano dokument do edycji "+selected.getDokfkPK().toString());
        setZapisz0edytuj1(true);
        liczbawierszy = selected.getKonta().size();
        //nie wiem dlaczego to dziala po dodaniu new Wiersze (1,0) - chodzilo o numery rzedu, zaczela dzialac edycja. Wczesniej szwankowal javascript. 
        //Bez tego jednak dostawalem pusty rzad po wpisaniu tej komenty nagle nie dostaje pustego rzedu tylko dzial kopiowanie do selected
        //totalny odlot. poszedl na to jeden wieczor
        //wierszedowsadzenia.add(new Wiersze(1,0));
        //selected.setKonta(wierszedowsadzenia);
    }


    //<editor-fold defaultstate="collapsed" desc="comment">
    public int getLiczbawierszy() {
        return liczbawierszy;
    }

    public void setLiczbawierszy(int liczbawierszy) {
        this.liczbawierszy = liczbawierszy;
    }

    public List<Wiersze> getWiersze() {
        return wiersze;
    }

    public void setWiersze(List<Wiersze> wiersze) {
        this.wiersze = wiersze;
    }

    public int getNumerwierszazapisu() {
        return numerwierszazapisu;
    }

    public void setNumerwierszazapisu(int numerwierszazapisu) {
        this.numerwierszazapisu = numerwierszazapisu;
    }

    public Dokfk getSelected() {
        return selected;
    }

    public void setSelected(Dokfk selected) {
        this.selected = selected;
    }

    public List<Dokfk> getWykaz() {
        return wykaz;
    }

    public void setWykaz(List<Dokfk> wykaz) {
        this.wykaz = wykaz;
    }

    public List<Dokfk> getSelecteddokfk() {
        return selecteddokfk;
    }

    public void setSelecteddokfk(List<Dokfk> selecteddokfk) {
        DokfkView.selecteddokfk = selecteddokfk;
    }

    public Wiersze getWiersz() {
        return wiersz;
    }

    public void setWiersz(Wiersze wiersz) {
        this.wiersz = wiersz;
    }

    public boolean isZapisz0edytuj1() {
        return zapisz0edytuj1;
    }

    public void setZapisz0edytuj1(boolean zapisz0edytuj1) {
        this.zapisz0edytuj1 = zapisz0edytuj1;
    }

    public Wiersze getAktualnywierszdorozrachunkow() {
        return aktualnywierszdorozrachunkow;
    }

    public void setAktualnywierszdorozrachunkow(Wiersze aktualnywierszdorozrachunkow) {
        this.aktualnywierszdorozrachunkow = aktualnywierszdorozrachunkow;
    }

    public List<RozrachunkiTmp> getRozrachunkiwierszewdokumencie() {
        return rozrachunkiwierszewdokumencie;
    }

    public void setRozrachunkiwierszewdokumencie(List<RozrachunkiTmp> rozrachunkiwierszewdokumencie) {
        this.rozrachunkiwierszewdokumencie = rozrachunkiwierszewdokumencie;
    }

  
    //</editor-fold>
//klasa potrzebna do rozrozniania list rozrachunkow podczas lazenia po kontach
    private static class Kluczlistyrozrachunkow {
        private int idporzadkowyrozliczany;
        private String wnlubma;
        
        public Kluczlistyrozrachunkow() {
            
        }

        public Kluczlistyrozrachunkow(int idporzadkowyrozliczany, String wnlubma) {
            this.idporzadkowyrozliczany = idporzadkowyrozliczany;
            this.wnlubma = wnlubma;
        }
        
    }
    
    private DataTable tablicazdokumentami;
    
    public void moveDown() {
//        DokfkDataModel cl =   (DokfkDataModel) tablicazdokumentami.getValue();
//        List<Dokfk> lista = (List<Dokfk>) cl.getWrappedData();
//        int wybrany = tablicazdokumentami.getRowIndex();
//        int wierszy = tablicazdokumentami.getRowCount();
        tablicazdokumentami.setRowIndex(1);
        tablicazdokumentami.resetValue();
        tablicazdokumentami.resolveSelectionMode();
            
        setSelected(wykaz.get(1));
        Msg.msg("w", "moveDown() "+selected.getDokfkPK().toString());
        //tablicazdokumentami.setRowIndex(1);
        RequestContext.getCurrentInstance().update("formwpisdokument:dataList:0");
//        String p_code = selected.getNumer();
//        for (int i = 0; i < cl.size(); i++) {
//            if (p_code.equalsIgnoreCase(cl.get(i).getNumer())) {
//                if (i == cl.size() - 1) {
//                    selected = cl.get(0);
//                } else {
//                    selected = cl.get(i + 1);
//                }
//                break;
//            }
//        }
    }

    public DataTable getTablicazdokumentami() {
        return tablicazdokumentami;
    }

    public void setTablicazdokumentami(DataTable tablicazdokumentami) {
        this.tablicazdokumentami = tablicazdokumentami;
    }

    
    

    public DokfkDataModel getListadatamodel() {
        return listadatamodel;
    }

    public void setListadatamodel(DokfkDataModel listadatamodel) {
        this.listadatamodel = listadatamodel;
    }

   
    
   public static class DokfkDataModel extends ListDataModel<Dokfk> implements SelectableDataModel<Dokfk> {    
  
    public DokfkDataModel() {  
    }  
  
    public DokfkDataModel(List<Dokfk> data) {  
        super(data);  
    }  
      
    @Override  
    public Dokfk getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<Dokfk> cars = (List<Dokfk>) getWrappedData();  
          
        for(Dokfk car : cars) {  
            if(car.getDokfkPK().equals(rowKey))  
                return car;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(Dokfk car) {  
        return car.getDokfkPK();  
    }  
}  
            
    

}

