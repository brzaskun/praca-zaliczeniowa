/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.RozrachunkiDAO;
import dao.WierszeDAO;
import daoFK.DokDAOfk;
import daoFK.WalutyDAOfk;
import entityfk.Dokfk;
import entityfk.DokfkPK;
import entityfk.Kontozapisy;
import entityfk.Rozrachunki;
import entityfk.RozrachunkiPK;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersze;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import params.Params;
import viewfk.subroutines.NaniesZapisynaKontaFK;

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
    private String wierszid;
    private String wnlubma;
    List<Wiersze> wierszedoobrobki;
    List<Wiersze> wierszezinnychdokumentow;
    //do wyswietlania w oknie rozrachunkow aktualnywiersz
    private double juzrozliczono;
    private double pozostalodorozliczenia;
    //waluta wybrana przez uzytkownika
    @Inject private WalutyDAOfk walutyDAOfk;
    private String wybranawaluta;
    private List<String> wprowadzonesymbolewalut;

    //<editor-fold defaultstate="collapsed" desc="comment">
    public DokfkView() {
        liczbawierszy = 1;
        selected = new Dokfk();
        DokfkPK dokfkPK = new DokfkPK();
        selected.setDokfkPK(dokfkPK);
        wiersze = new ArrayList<>();
        wiersze.add(new Wiersze(1, 0));
        selected.setKonta(wiersze);
//        List<Kontozapisy> zapisynakoncie = new ArrayList<>();
//        selected.setZapisynakoncie(zapisynakoncie);
        wykaz = new ArrayList<>();
        selecteddokfk = new ArrayList<>();
        zestawienielistrozrachunow = new HashMap<>();
        wierszedoobrobki = new ArrayList<>();
        wierszezinnychdokumentow = new ArrayList<>();
        wprowadzonesymbolewalut = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        wykaz = dokDAOfk.findAll();
        List<Waluty> pobranekursy = walutyDAOfk.findAll();
        for (Waluty p : pobranekursy) {
            wprowadzonesymbolewalut.add(p.getSymbolwaluty());
        }
    }

    //********************************************funkcje dla ksiegowania dokumentow
    //dodaje wiersze do dokumentu
    public void liczbaw() {
        double pierwsze = 0.0;
        double drugie = 0.0;
        try {
            liczbawierszy = selected.getKonta().size();
            pierwsze = selected.getKonta().get(liczbawierszy - 1).getKwotaWn();
            drugie = selected.getKonta().get(liczbawierszy - 1).getKwotaMa();
        } catch (Exception e) {
            Msg.msg("w", "Uzupełnij dane przed dodaniem nowego wiersza");
        }
        if (pierwsze != 0 || drugie != 0) {
            liczbawierszy++;
            selected.getKonta().add(new Wiersze(liczbawierszy, 0));
            RequestContext.getCurrentInstance().execute("załadujmodelzachowywaniawybranegopola();");
        } else {
            Msg.msg("w", "Uzuwpełnij dane przed dodaniem nowego wiersza");
        }

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
            uzupelnijwierszeodaneEdycja();
            //nanosimy zapisy na kontach
            NaniesZapisynaKontaFK.nanieszapisynakontach(selected.getKonta());
            //najpierw zobacz czy go nie ma, jak jest to usun i dodaj
//            Dokfk poszukiwanydokument = dokDAOfk.findDokfk(selected.getDatawystawienia(), selected.getNumer());
//            if (poszukiwanydokument instanceof Dokfk) {
//                dokDAOfk.destroy(poszukiwanydokument);
//                dokDAOfk.dodaj(selected);
//            } else {
//                dokDAOfk.dodaj(selected);
//            }
            zapisznaniesionerozrachunkiwbaziedanych();
            wykaz.add(selected);
            selected = new Dokfk();
            DokfkPK dokfkPK = new DokfkPK();
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

    public void edycja() {
        try {
            uzupelnijwierszeodaneEdycja();
            NaniesZapisynaKontaFK.nanieszapisynakontach(selected.getKonta());
            Dokfk poszukiwanydokument = dokDAOfk.findDokfk(selected.getDatawystawienia(), selected.getNumer());
            if (poszukiwanydokument instanceof Dokfk) {
                dokDAOfk.destroy(poszukiwanydokument);
                dokDAOfk.dodaj(selected);
            } else {
            dokDAOfk.edit(selected);
            }
            zapisznaniesionerozrachunkiwbaziedanych();
            wykaz.clear();
            wykaz = dokDAOfk.findAll();
            Msg.msg("i", "Pomyślnie zaktualizowano dokument");
        } catch (Exception e) {
            System.out.println(e.toString());
            Msg.msg("e", "Nie udało się zmenic dokumentu " + e.toString());
        }
    }

    public void usundokument(Dokfk dousuniecia) {
        try {
            dokDAOfk.usun(dousuniecia);
            wykaz.remove(dousuniecia);
            selected = new Dokfk();
            DokfkPK dokfkPK = new DokfkPK();
            selected.setDokfkPK(dokfkPK);
            wiersze = new ArrayList<>();
            wiersze.add(new Wiersze(1, 0));
            selected.setKonta(wiersze);
            liczbawierszy = 1;
            zestawienielistrozrachunow = new HashMap<>();
            RequestContext.getCurrentInstance().update("formwpisdokument");
            Msg.msg("i", "Dokument usunięty");
        } catch (Exception e) {
            Msg.msg("e", "Nie udało się usunąć dokumentu");
        }
    }
    //uzupeelnia wiersze podczas ich wprowadzania badz edycji i zapisuje do bazy ten z dodawania ma oblsuge Rozliczenie i Pozostało do Rozliczenia

    private void uzupelnijwierszeodaneDodaj() {
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
                    p.setPozostalodorozliczeniaWn(p.getKwotaWn());
                    p.setKwotaMa(0.0);
                    p.setTypwiersza(1);
                    p.setDokfk(selected);
                    p.setZaksiegowane(Boolean.FALSE);
                } else if (opis.contains("kontoma")) {
                    p.setKonto(p.getKontoMa());
                    p.setKontonumer(p.getKontoMa().getPelnynumer());
                    //p.setKontoprzeciwstawne(p.getKontoWn().getPelnynumer());
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.setPozostalodorozliczeniaMa(p.getKwotaMa());
                    p.setKwotaWn(0.0);
                    p.setTypwiersza(2);
                    p.setDokfk(selected);
                    p.setZaksiegowane(Boolean.FALSE);
                } else {
                    p.setKonto(p.getKontoWn().getPelnynumer().startsWith("2") ? p.getKontoWn() : p.getKontoMa());
                    p.setKontonumer(p.getKontoWn().getPelnynumer().startsWith("2") ? p.getKontoWn().getPelnynumer() : p.getKontoMa().getPelnynumer());
                    p.setKontoprzeciwstawne(p.getKontoWn().getPelnynumer().startsWith("2") ? p.getKontoMa().getPelnynumer() : p.getKontoWn().getPelnynumer());
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.setPozostalodorozliczeniaWn(p.getKwotaWn());
                    p.setPozostalodorozliczeniaMa(p.getKwotaMa());
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

    //uzupeelnia wiersze podczas ich wprowadzania badz edycji i zapisuje do bazy, nie rusza pol edycji
    private void uzupelnijwierszeodaneEdycja() {
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
                    p.setKonto(p.getKontoWn().getPelnynumer().startsWith("2") ? p.getKontoWn() : p.getKontoMa());
                    p.setKontonumer(p.getKontoWn().getPelnynumer().startsWith("2") ? p.getKontoWn().getPelnynumer() : p.getKontoMa().getPelnynumer());
                    p.setKontoprzeciwstawne(p.getKontoWn().getPelnynumer().startsWith("2") ? p.getKontoMa().getPelnynumer() : p.getKontoWn().getPelnynumer());
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

    //on robi nie tylko to ze przywraca button, on jeszcze resetuje selected
    //dodalem to tutaj a nie przy funkcji edytuj bo wtedy nie wyswietlalo wiadomosci o edycji
    public void przywrocwpisbutton() {
        setZapisz0edytuj1(false);
        liczbawierszy = 1;
        selected = new Dokfk();
        DokfkPK dokfkPK = new DokfkPK();
        selected.setDokfkPK(dokfkPK);
        wiersze = new ArrayList<>();
        wiersze.add(new Wiersze(1, 0));
        selected.setKonta(wiersze);
        setZapisz0edytuj1(false);
        zestawienielistrozrachunow = new HashMap<>();
        RequestContext.getCurrentInstance().execute("pierwszy.hide();");
    }
    //resetuje pola bo to dialogi sa i jest ViewScoped

    public void zresetujpoladialogu() {
        selected = new Dokfk();
        wiersze = new ArrayList<>();
        wiersze.add(new Wiersze(1, 0));
        selected.setKonta(wiersze);
        zestawienielistrozrachunow = new HashMap<>();
        wierszedoobrobki = new ArrayList<>();
        wierszezinnychdokumentow = new ArrayList<>();
        rozrachunkiwierszewdokumencie = new ArrayList<>();
        //to jest raczej pamieciozerne, moze jakos inaczej to zrobic!!!!!!!!!!!!!!!!!!
        wykaz.clear();
        wykaz = dokDAOfk.findAll();
    }
    //</editor-fold>  
    //********************************************funkcje dla rozrachunkow odtwarzanie i nanoszenie

    //<p:commandButton value="rozrachunki" actionListener="#{dokfkView.rozrachunki}" accesskey="r" update=":rozrachunki"/>
    //wywoluje: uzupelnijwierszeodanewtrakcie(); uzupelnijaktualnywiersz(wnlubma); pobierzwierszezdokumentow(selected.getKonta());
    public void rozrachunki() {
        //bierzemy parametry przekazane przez javascript po kazdorazowym kliknieciu pola konta
        String wnlubma = (String) Params.params("wpisywaniefooter:wnlubma");
        uzupelnijwierszeodanewtrakcie();
        String nrwierszaS = (String) Params.params("wpisywaniefooter:wierszid");
        try {
            Integer nrwiersza = Integer.parseInt(nrwierszaS);
            nrwiersza--;
            aktualnywierszdorozrachunkow = selected.getKonta().get(nrwiersza);
            uzupelnijaktualnywiersz(wnlubma);
            if (aktualnywierszdorozrachunkow.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
                rozrachunkiwierszewdokumencie = new ArrayList<>();
                wierszedoobrobki = selected.getKonta();
                pobierzwierszezdokumentow(wierszedoobrobki);
                //to jest linijak do pobierania wierszy z innych dokumnetow zachowanych w bazie dancyh
                wierszezinnychdokumentow = wierszeDAO.findDokfkRozrachunki(selected.getDokfkPK().getPodatnik(), aktualnywierszdorozrachunkow.getKonto(), aktualnywierszdorozrachunkow.getDokfk().getDokfkPK());
                pobierzwierszezdokumentow(wierszezinnychdokumentow);
                //uzupelniamy sporzadzona liste o uprzednio zapamietane kwoty powstale po uprzednim wpisaniu podczas edycji bo pierwotnie byly z bazy danych
                //a musimy przeciez robic to w trakcie ale moment czy ja po wcisnieciu zapis, nie zapisuje tego w bazie?
                sprawdzczyjuzczegosnienaniesiono();
                podsumujtoconaniesionoizaktualizujpozostale();
                usungdyrozrachunekjestNowaTransakcja();
                RequestContext.getCurrentInstance().update("rozrachunki");
                RequestContext.getCurrentInstance().execute("drugishow();");
                wierszid = "";
                wnlubma = "";
                RequestContext.getCurrentInstance().update("formwpisdokument");
                RequestContext.getCurrentInstance().execute("załadujmodelzachowywaniawybranegopola();");
            } else {
                Msg.msg("e", "Wybierz konto rozrachunkowe");
                wierszid = "";
                wnlubma = "";
                RequestContext.getCurrentInstance().execute("załadujmodelzachowywaniawybranegopola();");
                RequestContext.getCurrentInstance().execute("powrotdopola();");
            }
        } catch (Exception e) {
            Msg.msg("e", "Wybierz pole zawierające numer konta");
            wierszid = "";
            wnlubma = "";
            RequestContext.getCurrentInstance().execute("załadujmodelzachowywaniawybranegopola();");
            RequestContext.getCurrentInstance().execute("powrotdopola();");
        }
    }
    //uzupelnia pozostale wiersze w zaleznosci od tego po ktorej stronie jest wiersz rozliczany
    //takie minimum bo przeciez wiersz podczas wpisu moze byc niepelny

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
            Msg.msg("e", "Błąd w pliku DokfkView w funkcji uzupelnijwierszeodanew trakcie " + e.getMessage());
        }
    }
    //uzupelnia wiersz rozliczanego o dane

    private void uzupelnijaktualnywiersz(String wnlubma) {
        //ladnie uzupelnia informacje o wierszu pk, bez zerowania kwot rozreachunkow one sa ustawiane na 0 podczas inicjalizacji
        Wiersze p = aktualnywierszdorozrachunkow;
        try {
            if (wnlubma.equals("Wn")) {
                p.setKonto(p.getKontoWn());
                p.setKontonumer(p.getKontoWn().getPelnynumer());
                p.setDataksiegowania(selected.getDatawystawienia());
                p.setKwotapierwotna(p.getKwotaWn());
                if (p.getPozostalodorozliczeniaWn() == 0.0) {
                    p.setPozostalodorozliczeniaWn(p.getKwotapierwotna());
                }
                p.setDokfk(selected);
                p.setZaksiegowane(Boolean.FALSE);
                p.setWnlubma("Wn");
            } else {
                p.setKonto(p.getKontoMa());
                p.setKontonumer(p.getKontoMa().getPelnynumer());
                p.setDataksiegowania(selected.getDatawystawienia());
                p.setKwotapierwotna(p.getKwotaMa());
                if (p.getPozostalodorozliczeniaMa() == 0.0) {
                    p.setPozostalodorozliczeniaMa(p.getKwotapierwotna());
                }
                p.setDokfk(selected);
                p.setZaksiegowane(Boolean.FALSE);
                p.setWnlubma("Ma");
            }
        } catch (Exception e) {
            Msg.msg("e", "Błąd w pliku DokfkView w funkcji uzupelnijaktualnywiersz");
        }
    }
    //pobiera wiersze i i pasujace do niego rozrachunki z podanego zrodla: 
    //albo z biezacego dokumentu albo z innych dokumentow, wywoluje sprawdzczyjuzczegosnienaniesiono ()

    private void pobierzwierszezdokumentow(List<Wiersze> wierszezdokumentu) {
        try {
            //pobieram co prawda z bazydanych rozrachunki ale pierwotnie porownuje je tylko z biezacym dokumentem stad rozwiniecie funkcji o dodanie wierszy z bazy
            List<Rozrachunki> zapisanerozrachunkiwbazie = rozrachunkiDAO.findRozliczany(aktualnywierszdorozrachunkow.getIdwiersza());
            for (Wiersze p : wierszezdokumentu) {
                //pobieram tylko te konta ktore nie leza po tej samej stronie dokumetu co konto rozrachunkowe i sa takie same jak konto rozrliczane
                if (aktualnywierszdorozrachunkow.getWnlubma().equals("Wn")) {
                    if (p.getKontoMa().equals(aktualnywierszdorozrachunkow.getKontoWn())) {
                        p.setKonto(p.getKontoMa());
                        p.setKontonumer(p.getKonto().getPelnynumer());
                        //wyrzuca blad podczas wpisywania nowego wiersza niedokonczoneho
                        //p.setKontoprzeciwstawne(p.getKontoWn().getPelnynumer());
                        p.setKwotapierwotna(p.getKwotaMa());
                        //sprawdz czy czegoj juz nie ma baranku!!!
//                        p.setPozostalodorozliczeniaMa(p.getKwotaMa());
//                        p.setRozliczonoMa(0.0);
                        p.setWnlubma("Ma");
                        //szukamy bo moze juz byl taki rozrachunek
                        for (Rozrachunki r : zapisanerozrachunkiwbazie) {
                            //on dodaje kwote rozrachunku tylko z tego jednego wiersza i to jest dobre. pozniej trzeba bedzie je odpowiednio zsumowac z pozostalych i 
                            //uzupelnic pole setRozliczono, setPozostalodorozliczenia w tym ktory teraz pokazujemy
                            if (r.getWierszrozliczany().equals(aktualnywierszdorozrachunkow) && r.getWierszsparowany().equals(p)) {
                                rozrachunkiwierszewdokumencie.add(new RozrachunkiTmp(r.getKwotarozrachunku(), p.getKwotapierwotna(), p, aktualnywierszdorozrachunkow, "Wn", true));
                                //ustawiam flage zeby nie dolozylo tego wiersza ponownie potem go resetuje
                                p.setDodanydorozrachunkow(true);
                            }
                        }
                        //jak nie ma zachowanego rozrachunku to musze wykreowac nowy pusty
                        if (!p.isDodanydorozrachunkow()) {
                            rozrachunkiwierszewdokumencie.add(new RozrachunkiTmp(0, p.getKwotapierwotna(), p, aktualnywierszdorozrachunkow, "Wn", false));
                        }
                        p.setDodanydorozrachunkow(false);
                    }
                } else {
                    if (p.getKontoWn().equals(aktualnywierszdorozrachunkow.getKontoMa())) {
                        p.setKonto(p.getKontoWn());
                        p.setKontonumer(p.getKonto().getPelnynumer());
                        //wyrzuca blad podczas wpisywania nowego wiersza niedokonczoneho
                        //p.setKontoprzeciwstawne(p.getKontoMa().getPelnynumer());
                        p.setKwotapierwotna(p.getKwotaWn());
//                        p.setPozostalodorozliczeniaWn(p.getKwotaWn());
//                        p.setRozliczonoWn(0.0);
                        p.setWnlubma("Wn");
                        //szukamy bo moze juz byl taki rozrachunek
                        for (Rozrachunki r : zapisanerozrachunkiwbazie) {
                            if (r.getWierszrozliczany().equals(aktualnywierszdorozrachunkow) && r.getWierszsparowany().equals(p)) {
                                rozrachunkiwierszewdokumencie.add(new RozrachunkiTmp(r.getKwotarozrachunku(), p.getKwotapierwotna(), p, aktualnywierszdorozrachunkow, "Ma", true));
                                //ustawiam flage zeby nie dolozylo tego wiersza ponownie potem go resetuje
                                p.setDodanydorozrachunkow(true);
                            }
                        }
                        //jak nie ma zachowanego rozrachunku to musze wykreowac nowy pusty
                        if (!p.isDodanydorozrachunkow()) {
                            rozrachunkiwierszewdokumencie.add(new RozrachunkiTmp(0, p.getKwotapierwotna(), p, aktualnywierszdorozrachunkow, "Ma", false));
                        }
                        p.setDodanydorozrachunkow(false);
                    }
                }
            }
        } catch (Exception e) {
            Msg.msg("e", "Blad w DokfkView funkcja pobierzwierszezbiezacegodokumentu");
        }

    }
    //jak sie lazi po kontach to mozna przy otwartym dokumencie wrocic do zapisu przez zapisaniem calego dokumentu
    //to odtwarza wprowadzone zapisy podczas wpisywania na biezaco bez bazy danych
    //ale jakos dziwnie pobiera rozliczany - sprawdizc to trzeba

    private void sprawdzczyjuzczegosnienaniesiono() {
        Wiersze rozliczany = aktualnywierszdorozrachunkow;
        Kluczlistyrozrachunkow tmp = new Kluczlistyrozrachunkow(rozliczany.getIdporzadkowy(), rozliczany.getWnlubma());
        if (zestawienielistrozrachunow.containsKey(tmp)) {
            List<RozrachunkiTmp> listazapamietana = zestawienielistrozrachunow.get(tmp);
            for (RozrachunkiTmp p : listazapamietana) {
                for (RozrachunkiTmp r : rozrachunkiwierszewdokumencie) {
                    //tu tylko nanosimy kwote rozrachunku na biezace rozrachunki w dokumencie ktorej nie ma w wierszach, nie ruszamy wierszy
                    if (p.getWierszrozliczany().getIdporzadkowy() == r.getWierszrozliczany().getIdporzadkowy() && p.getWierszsparowany().getIdporzadkowy() == r.getWierszsparowany().getIdporzadkowy()) {
                        r.setBiezacakwotarozrachunku(p.getBiezacakwotarozrachunku());
                        r.setStarakwotarozrachunku(p.getBiezacakwotarozrachunku());
                        r.setKwotapierwotna(p.getKwotapierwotna());
                        r.setPozostalosparowany(p.getPozostalosparowany());
                    }
                }
            }
        }
        Msg.msg("i", "Odtworzono zapamietane rozrachunki");
    }
    //poniewaz rozrachunki en masse sa zsumowane w aktualnym wierszu wystarczy stad wziac kwote jako sume
    //a potem odjac dany rozrachynek i wpisac roznice do Tylejuzrozliczono, Pozostalodorozliczenie

    private void podsumujtoconaniesionoizaktualizujpozostale() {
        try {
            double rozliczono = 0.0;
            double biezacakwotarozrachunkow = 0.0;
            //tu powstazje problem jak nie ma rozrachunkow a sa kwoty w wierszach
            for (RozrachunkiTmp rozrachunek : rozrachunkiwierszewdokumencie) {
                biezacakwotarozrachunkow += rozrachunek.getBiezacakwotarozrachunku();
                if (rozrachunek.getWierszsparowany().getWnlubma().equals("Wn")) {
                    rozliczono = rozrachunek.getWierszsparowany().getRozliczonoWn();
                } else {
                    rozliczono = rozrachunek.getWierszsparowany().getRozliczonoMa();
                }
                if (rozliczono > 0) {
                    rozrachunek.setRozliczonosparowany(rozliczono - rozrachunek.getBiezacakwotarozrachunku());
                    rozrachunek.setPozostalosparowany(rozrachunek.getKwotapierwotna() - rozrachunek.getRozliczonosparowany());
                } else {
                    rozrachunek.setRozliczonosparowany(0.0);
                    rozrachunek.setPozostalosparowany(rozrachunek.getKwotapierwotna());
                }
            }
            if (aktualnywierszdorozrachunkow.getWnlubma().equals("Wn")) {
                juzrozliczono = (aktualnywierszdorozrachunkow.getRozliczonoWn() - biezacakwotarozrachunkow);
                pozostalodorozliczenia = (aktualnywierszdorozrachunkow.getKwotapierwotna() - juzrozliczono);
            } else {
                juzrozliczono = (aktualnywierszdorozrachunkow.getRozliczonoMa() - biezacakwotarozrachunkow);
                pozostalodorozliczenia = (aktualnywierszdorozrachunkow.getKwotapierwotna() - juzrozliczono);
            }
        } catch (Exception ex) {
            Msg.msg("e", "Blad w DokfkView funkcja podsumujtoconaniesionoizaktualizujpozostale");
        }
    }
    //jezeli biezace rozrachunki sa poste a aktualny jwiersz ma pole rozliczono rozne od zera znaczy ze jest Nowa Transakcja
    //i trzeba jednak usunac pola rozrachunkow

    private void usungdyrozrachunekjestNowaTransakcja() {
        //dezaktywuje kwote do wpisania gdy dany rozrachunek zostal rozliczony chocby czesciowo
        for (RozrachunkiTmp rozrachunek : rozrachunkiwierszewdokumencie) {
            rozrachunek.setPozwolnawpis(true);
        }
        double sumabiezacychkwot = 0.0;
        for (RozrachunkiTmp rozrachunek : rozrachunkiwierszewdokumencie) {
            sumabiezacychkwot += rozrachunek.getBiezacakwotarozrachunku();
        }
        double czynaniesionorozliczenia = 0.0;
        if (aktualnywierszdorozrachunkow.getWnlubma().equals("Wn")) {
            czynaniesionorozliczenia += aktualnywierszdorozrachunkow.getRozliczonoWn();
        } else {
            czynaniesionorozliczenia += aktualnywierszdorozrachunkow.getRozliczonoMa();
        }
        if (sumabiezacychkwot == 0.0 && czynaniesionorozliczenia != 0) {
            for (RozrachunkiTmp rozrachunek : rozrachunkiwierszewdokumencie) {
                rozrachunek.setPozwolnawpis(false);
            }
            List<Rozrachunki> zapisanerozrachunkiwbazie = rozrachunkiDAO.findSparowany(aktualnywierszdorozrachunkow.getIdwiersza());
            Iterator it = rozrachunkiwierszewdokumencie.iterator();
            while (it.hasNext()) {
                int dousuniecia = 0;
                RozrachunkiTmp biezacy = (RozrachunkiTmp) it.next();
                if (biezacy.getRozliczonosparowany() == 0.0) {
                    dousuniecia = 1;
                }
                int znaleziono = 0;
                //w nowych rozracchunkach i tak wyswietlam to co jest w bazie wiec uzwam te rozrachunki co nie sa z bazy
                for (Rozrachunki p : zapisanerozrachunkiwbazie) {
                    boolean pierwszapara = biezacy.getWierszsparowany().getIdwiersza().equals(p.getWierszrozliczany().getIdwiersza());
                    boolean drugapara = biezacy.getWierszrozliczany().getIdwiersza().equals(p.getWierszsparowany().getIdwiersza());
                    if (pierwszapara && drugapara) {
                        znaleziono = 1;
                    }
                }
                if (dousuniecia == 1 || znaleziono == 0) {
                    it.remove();
                }
            }

        }
    }

    //********************************************funkcje dla rozrachunkow zapisywanie rozrachunkow
    //<p:commandButton value="zapisz" actionListener="${dokfkView.zapisanorozrachunek}" zapisywanie listy danego rozrachunku do listy list
    //kluczem jest numer wiersza odraz Wn lub Ma
    //pod koniec wywoluje naniesieniekonsekwencjirozrachunkownawierszach()
    public void zapisanorozrachunek() {
        Wiersze rozliczany = aktualnywierszdorozrachunkow;
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
    //robie to w momencie zamkniecia okrna z rozrachunkami aby aktualizowalo pola do rozliczenia i rozliczono należące do WIERSZA juz w trakcie a nie dopiero
    //podczas wpisywania do bazy,

    private void naniesieniekonsekwencjirozrachunkownawierszach() {
        Set<Kluczlistyrozrachunkow> listakluczyrozrachunkow = zestawienielistrozrachunow.keySet();
        for (Kluczlistyrozrachunkow klucz : listakluczyrozrachunkow) {
            List<RozrachunkiTmp> listazachowanychlistrozrachunkow = zestawienielistrozrachunow.get(klucz);
            for (RozrachunkiTmp p : listazachowanychlistrozrachunkow) {
                double kwotadotychczasowa = p.getStarakwotarozrachunku();
                double kwotanowa = p.getBiezacakwotarozrachunku();
                //jak nowa kwota bedzie mniejsza to wydzie minus a jak nie to bedzie plus albo 0 :)
                double roznicadonaniesienia = kwotanowa - kwotadotychczasowa;
                if (roznicadonaniesienia != 0) {
                    //przekazuje do wyodrebnionych funkcji aby naniesc kwoty najpierw na wiersze z biezacego dokumentu
                    naniesnawierszerozliczane(wierszedoobrobki, p, kwotadotychczasowa, roznicadonaniesienia);
                    naniesnawierszesparowane(wierszedoobrobki, p, kwotadotychczasowa, roznicadonaniesienia);
                    //a nastepnie na wiersze z innych dokumentow
                    naniesnawierszerozliczane(wierszezinnychdokumentow, p, kwotadotychczasowa, roznicadonaniesienia);
                    naniesnawierszesparowane(wierszezinnychdokumentow, p, kwotadotychczasowa, roznicadonaniesienia);
                }
                //ustawiamy biezacy rozrachunek jako stary aby potem zaktualizowac baze.
                p.setStarakwotarozrachunku(p.getBiezacakwotarozrachunku());
            }
        }
        Msg.msg("i", "Wiersza zaktulalizowane o kwoty rozliczone");
    }
    //wyodrebnione z naniesieniekonsekwencjirozrachunkownawierszach bo przechodzimy je dwsa razy dla wierszy w bierzacym dokumencie i innych dokumentach

    private void naniesnawierszerozliczane(List<Wiersze> obrabianewiersze, RozrachunkiTmp obrabianyrozrachunek, double kwotadotychczasowa, double roznicadonaniesienia) {
        //przechodze jeszcze raz przez wiersze zeby rozliczyc rozliczane
        for (Wiersze s : obrabianewiersze) {
            boolean sprawdzPK = s.getDokfk().getDokfkPK().equals(obrabianyrozrachunek.getWierszrozliczany().getDokfk().getDokfkPK());
            boolean sprawdzIdporzadkowy = s.getIdporzadkowy().equals(obrabianyrozrachunek.getWierszrozliczany().getIdporzadkowy());
            if (sprawdzPK && sprawdzIdporzadkowy) {
                if (s.getWnlubma().equals("Wn")) {
                    //tu rozlicza sie to czy zmniejszono czy zwiekszono rozrachunek podczas jego edycji
                    if (s.getKontoWn().getPelnynumer().equals(obrabianyrozrachunek.getWierszrozliczany().getKontonumer())) {
                        double kwotaRozliczeniaNaniesionawWierszu = s.getRozliczonoWn();
                        try {
                            s.setRozliczonoWn(kwotaRozliczeniaNaniesionawWierszu  + roznicadonaniesienia);
                        } catch (Exception e1) {
                            s.setRozliczonoWn(kwotaRozliczeniaNaniesionawWierszu + obrabianyrozrachunek.getBiezacakwotarozrachunku());
                        }
                        s.setPozostalodorozliczeniaWn(s.getKwotaWn() - s.getRozliczonoWn());
                    }
                } else {
                    //tu rozlicza sie to czy zmniejszono czy zwiekszono rozrachunek podczas jego edycji
                    if (s.getKontoMa().getPelnynumer().equals(obrabianyrozrachunek.getWierszrozliczany().getKontonumer())) {
                        double kwotaRozliczeniaNaniesionawWierszu = s.getRozliczonoMa();
                        try {
                            s.setRozliczonoMa(kwotaRozliczeniaNaniesionawWierszu  + roznicadonaniesienia);
                        } catch (Exception e1) {
                            s.setRozliczonoMa(kwotaRozliczeniaNaniesionawWierszu + obrabianyrozrachunek.getBiezacakwotarozrachunku());
                        }
                        s.setPozostalodorozliczeniaMa(s.getKwotaMa() - s.getRozliczonoMa());
                    }
                }
            }
        }
    }
    //wyodrebnione z naniesieniekonsekwencjirozrachunkownawierszach bo przechodzimy je dwsa razy dla wierszy w bierzacym dokumencie i innych dokumentach

    private void naniesnawierszesparowane(List<Wiersze> obrabianewiersze, RozrachunkiTmp obrabianyrozrachunek, double kwotadotychczasowa, double roznicadonaniesienia) {
        //przechodze jeszcze raz przez wiersze zeby rozliczyc sparowane
        for (Wiersze s : obrabianewiersze) {
            boolean sprawdzPK = s.getDokfk().getDokfkPK().equals(obrabianyrozrachunek.getWierszsparowany().getDokfk().getDokfkPK());
            boolean sprawdzIdporzadkowy = s.getIdporzadkowy().equals(obrabianyrozrachunek.getWierszsparowany().getIdporzadkowy());
            if (sprawdzPK && sprawdzIdporzadkowy) {
                if (s.getWnlubma().equals("Wn")) {
                    if (s.getKontoWn().getPelnynumer().equals(obrabianyrozrachunek.getWierszsparowany().getKontonumer())) {
                        double kwotaRozliczeniaNaniesionawWierszu = s.getRozliczonoWn();
                        try {
                            s.setRozliczonoWn(kwotaRozliczeniaNaniesionawWierszu + roznicadonaniesienia);
                        } catch (Exception e1) {
                            s.setRozliczonoWn(kwotaRozliczeniaNaniesionawWierszu + obrabianyrozrachunek.getBiezacakwotarozrachunku());
                        }
                        s.setPozostalodorozliczeniaWn(s.getKwotaWn() - s.getRozliczonoWn());
                    }
                } else {
                    //aby nie podsumowywac wiersza 
                    if (s.getKontoMa().getPelnynumer().equals(obrabianyrozrachunek.getWierszsparowany().getKontonumer())) {
                        double kwotaRozliczeniaNaniesionawWierszu = s.getRozliczonoMa();
                        try {
                            s.setRozliczonoMa(kwotaRozliczeniaNaniesionawWierszu + roznicadonaniesienia);
                        } catch (Exception e1) {
                            s.setRozliczonoMa(kwotaRozliczeniaNaniesionawWierszu + obrabianyrozrachunek.getBiezacakwotarozrachunku());
                        }
                        s.setPozostalodorozliczeniaMa(s.getKwotaMa() - s.getRozliczonoMa());
                    }
                }
            }
        }
    }

    /**
     * **************
     */
    //zapisujemy na koniec rozrachunki w bazie danych na trwałe!!!! KONICOWA
    public void zapisznaniesionerozrachunkiwbaziedanych() {
        dokDAOfk.edit(selected);
        Dokfk doktmp = dokDAOfk.findDokfkObj(selected);
        for (Wiersze a : doktmp.getKonta()) {
            for (Wiersze b : selected.getKonta()) {
                if (b.equals(a)) {
                    b.setIdwiersza(a.getIdwiersza());
                }
            }
        }
        zachowajrozrachunki(selected.getKonta());
        dokDAOfk.edit(selected);
        Msg.msg("i", "Dokument wyedytowany");//ddd
        
        zachowajrozrachunki(wierszezinnychdokumentow);
        for (Wiersze p : wierszezinnychdokumentow) {
            wierszeDAO.edit(p);
        }
        Msg.msg("i", "Wyedytowano wiersze z obcych dokumentow");
    }

    private void zachowajrozrachunki(List<Wiersze> listawierszy) {
        try {
            Set<Kluczlistyrozrachunkow> listakluczyrozrachunkow = zestawienielistrozrachunow.keySet();
            for (Kluczlistyrozrachunkow klucz : listakluczyrozrachunkow) {
                for (RozrachunkiTmp p : zestawienielistrozrachunow.get(klucz)) {
                    for (Wiersze s : listawierszy) {
                        if (s.equals(p.getWierszrozliczany())) {
                            p.getWierszrozliczany().setIdwiersza(s.getIdwiersza());
                        }
                    }
                    RozrachunkiPK rPK = new RozrachunkiPK();
                    rPK.setZapisrozliczany(p.getWierszrozliczany().getIdwiersza());
                    rPK.setZapissparowany(p.getWierszsparowany().getIdwiersza());
                    Rozrachunki r = new Rozrachunki(rPK);
                    r.setKwotarozrachunku(p.getBiezacakwotarozrachunku());
                    r.setWierszrozliczany(p.getWierszrozliczany());
                    r.setWierszsparowany(p.getWierszsparowany());
                    if (p.getWnlubma().equals("Wn")) {
                        r.setKontorozliczanenr(p.getWierszrozliczany().getKontoWn().getPelnynumer());
                        r.setKontosparowanenr(p.getWierszsparowany().getKontoMa().getPelnynumer());
                    } else {
                        r.setKontorozliczanenr(p.getWierszrozliczany().getKontoMa().getPelnynumer());
                        r.setKontosparowanenr(p.getWierszsparowany().getKontoWn().getPelnynumer());
                    }
                    for (Wiersze s : listawierszy) {
                        if (s.getIdwiersza().equals(p.getWierszrozliczany().getIdwiersza())) {
                            if (r.getKwotarozrachunku() == 0.0) {
                                s.getRozrachunkijakorozliczany().remove(r);
                            } else {
                                s.getRozrachunkijakorozliczany().add(r);
                            }
                        }
                    }
                    for (Wiersze s : listawierszy) {
                        if (s.getIdwiersza().equals(p.getWierszsparowany().getIdwiersza())) {
                            if (r.getKwotarozrachunku() == 0.0) {
                                s.getRozrachunkijakosparowany().remove(r);
                            } else {
                                s.getRozrachunkijakosparowany().add(r);
                            }
                        }
                    }
                }
            }
            // edytujposzczegolnewiersze();
            //wierszedorozrachunkow = new ArrayList<>();
            System.out.println("Rozrachunki naniesione");
            Msg.msg("i", "Rozrachunki naniesione");
        } catch (Exception ex) {
            System.out.println("Nie naniesiono rozrachunkow " + ex.getMessage());
            Msg.msg("w", "Nie naniesiono rozrachunkow " + ex.getMessage());
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
    
    //********************klasy pomocnicze
    //zlozony klucz listy przechowujacej listy RozrachunkiTmp podczas biezacego wpisywania
    private static class Kluczlistyrozrachunkow {

        private int idporzadkowyrozliczany;
        private String wnlubma;

        public Kluczlistyrozrachunkow() {
        }

        public Kluczlistyrozrachunkow(int idporzadkowyrozliczany, String wnlubma) {
            this.idporzadkowyrozliczany = idporzadkowyrozliczany;
            this.wnlubma = wnlubma;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 19 * hash + this.idporzadkowyrozliczany;
            hash = 19 * hash + Objects.hashCode(this.wnlubma);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Kluczlistyrozrachunkow other = (Kluczlistyrozrachunkow) obj;
            if (this.idporzadkowyrozliczany != other.idporzadkowyrozliczany) {
                return false;
            }
            if (!Objects.equals(this.wnlubma, other.wnlubma)) {
                return false;
            }
            return true;
        }
    }
    //klasa przechowujaca rozrachunek w czasie wpisywania

    public static class RozrachunkiTmp {

        private double biezacakwotarozrachunku;
        private double starakwotarozrachunku;
        private double kwotapierwotna;
        private Wiersze wierszsparowany;
        private double rozliczonosparowany;
        private double pozostalosparowany;
        private Wiersze wierszrozliczany;
        private String wnlubma;
        private boolean pozwolnawpis;
        private boolean zapiszbazydanych;

        public RozrachunkiTmp() {
        }

        public RozrachunkiTmp(double kwotapierwotna, Wiersze wierszsparowany, Wiersze wierszrozliczany, String wnlubma) {
            this.kwotapierwotna = kwotapierwotna;
            this.wierszsparowany = wierszsparowany;
            this.wierszrozliczany = wierszrozliczany;
            this.wnlubma = wnlubma;
            this.pozwolnawpis = true;
        }

        public RozrachunkiTmp(double biezacakwotarozrachunku, double kwotapierwotna, Wiersze wierszsparowany, Wiersze wierszrozliczany, String wnlubma, boolean zapiszbazydanych) {
            this.biezacakwotarozrachunku = biezacakwotarozrachunku;
            this.starakwotarozrachunku = biezacakwotarozrachunku;
            //odtwarzanie rozrachunku jak jest tylko w bazie a nie w biezacej tablicy
            if (wnlubma.equals("Wn")) {
                this.rozliczonosparowany = wierszsparowany.getRozliczonoMa() - biezacakwotarozrachunku;
                this.pozostalosparowany = kwotapierwotna - this.rozliczonosparowany;
            } else {
                this.rozliczonosparowany = wierszsparowany.getRozliczonoWn() - biezacakwotarozrachunku;
                this.pozostalosparowany = kwotapierwotna - this.rozliczonosparowany;
            }
            this.kwotapierwotna = kwotapierwotna;
            this.wierszsparowany = wierszsparowany;
            this.wierszrozliczany = wierszrozliczany;
            this.wnlubma = wnlubma;
            this.pozwolnawpis = true;
            this.zapiszbazydanych = zapiszbazydanych;
        }

        @Override
        public String toString() {
            return "RozrachunkiTmp{" + ", wierszrozliczany=" + wierszrozliczany + "wierszsparowany=" + wierszsparowany + '}';
        }

        //<editor-fold defaultstate="collapsed" desc="comment">
        public double getKwotapierwotna() {
            return kwotapierwotna;
        }

        public void setKwotapierwotna(double kwotapierwotna) {
            this.kwotapierwotna = kwotapierwotna;
        }

        public boolean isZapiszbazydanych() {
            return zapiszbazydanych;
        }

        public void setZapiszbazydanych(boolean zapiszbazydanych) {
            this.zapiszbazydanych = zapiszbazydanych;
        }

        public double getStarakwotarozrachunku() {
            return starakwotarozrachunku;
        }

        public void setStarakwotarozrachunku(double starakwotarozrachunku) {
            this.starakwotarozrachunku = starakwotarozrachunku;
        }

        public boolean isPozwolnawpis() {
            return pozwolnawpis;
        }

        public void setPozwolnawpis(boolean pozwolnawpis) {
            this.pozwolnawpis = pozwolnawpis;
        }

        public double getBiezacakwotarozrachunku() {
            return biezacakwotarozrachunku;
        }

        public void setBiezacakwotarozrachunku(double biezacakwotarozrachunku) {
            this.biezacakwotarozrachunku = biezacakwotarozrachunku;
        }

        public double getRozliczonosparowany() {
            return rozliczonosparowany;
        }

        public void setRozliczonosparowany(double rozliczonosparowany) {
            this.rozliczonosparowany = rozliczonosparowany;
        }

        public double getPozostalosparowany() {
            return pozostalosparowany;
        }

        public void setPozostalosparowany(double pozostalosparowany) {
            this.pozostalosparowany = pozostalosparowany;
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

    //<editor-fold defaultstate="collapsed" desc="comment">
    //*******************************************************************znajdowanie i wyswietlanie dokumentu
    //funkcja w pliku -  zestawieniezapisowwdokumentach - wyszukuje dokument po kliknieciu
    //<p:ajax event="rowSelect" listener="#{dokfkView.znajdzdokumentzzapisu()}"/>
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

    //mesydz, ze wybrano dokument do edycji
    public void wybranodokmessage() {
        Msg.msg("i", "Wybrano dokument do edycji " + selected.getDokfkPK().toString());
        setZapisz0edytuj1(true);
        liczbawierszy = selected.getKonta().size();
        //nie wiem dlaczego to dziala po dodaniu new Wiersze (1,0) - chodzilo o numery rzedu, zaczela dzialac edycja. Wczesniej szwankowal javascript. 
        //Bez tego jednak dostawalem pusty rzad po wpisaniu tej komenty nagle nie dostaje pustego rzedu tylko dzial kopiowanie do selected
        //totalny odlot. poszedl na to jeden wieczor
        //wierszedowsadzenia.add(new Wiersze(1,0));
        //selected.setKonta(wierszedowsadzenia);
    }
    //</editor-fold>  

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

    public double getJuzrozliczono() {
        return juzrozliczono;
    }

    public void setJuzrozliczono(double juzrozliczono) {
        this.juzrozliczono = juzrozliczono;
    }

    public double getPozostalodorozliczenia() {
        return pozostalodorozliczenia;
    }

    public void setPozostalodorozliczenia(double pozostalodorozliczenia) {
        this.pozostalodorozliczenia = pozostalodorozliczenia;
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
}
