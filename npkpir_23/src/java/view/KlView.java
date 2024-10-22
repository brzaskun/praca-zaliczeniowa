package view;

import beansRegon.SzukajDaneBean;
import dao.KlienciDAO;
import embeddable.PanstwaMap;
import entity.Faktura;
import entity.Klienci;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import error.E;
import gus.GUSView;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
 import params.Params;
import viewfk.KliencifkView;

/**
 *
 * @author Osito
 */
@Named(value = "KlView")
@ViewScoped
public class KlView implements Serializable {

    private static final long serialVersionUID = 1L;
    final static String FILE_NAME = "C:\\Temp\\dane.txt";
    final static String OUTPUT_FILE_NAME = "C:\\Temp\\outputdane.txt";
    final static Charset ENCODING = StandardCharsets.UTF_8;
    private List<Klienci> kl1;
    private List<Klienci> klienciFiltered;
    private Klienci doUsuniecia;
   
    private boolean edycja;
    @Inject
    private KliencifkView kliencifkView;
    @Inject
    private GUSView gUSView;
    @Inject
    private PanstwaMap panstwaMapa;


//    public static void main(String[] args) {
//        String mse = "XX0000000001";
//        mse = mse.substring(2);
//        mse = String.valueOf(Integer.parseInt(mse));
//    }
    @Inject
    private KlienciDAO klDAO;
    @Inject
    private Klienci selected;
    private Klienci selectedtabela;
//    @Inject
//    private Klienci selectedtablica;
    @Inject
    PanstwaMap ps1;
    private Integer ilesrodkow;

    @PostConstruct
    private void init() { //E.m(this);
        try {
            kl1 = klDAO.findAll();
        } catch (Exception ex) {

        }
        //selected.setKrajnazwa("Polska");
        //selected.setKrajkod(panstwaMapa.getWykazPanstwSX().get("Polska"));
    }

    public void wyszukajduplikat(ValueChangeEvent e) {
        String klient = (String) e.getNewValue();
        Klienci klientznaleziony = null;
        try {
            klientznaleziony = klDAO.findKlientByNazwa(klient);
        } catch (Exception e1) {

        }
        if (klientznaleziony != null) {
            selected.setNpelna("");
            selected.setNskrocona("");
            Msg.msg("e", "Klient o takiej nazwie jest już w bazie");
            PrimeFaces.current().ajax().update("formnkfaktura");
            PrimeFaces.current().executeScript("fakturaduplikatklienta()");
        }

    }

    public void wyszukajduplikatkontrahent() {
        String klient = selected.getNpelna();
        Klienci klientznaleziony = null;
        try {
            klientznaleziony = klDAO.findKlientByNazwa(klient);
        } catch (Exception e1) {

        }
        if (klientznaleziony != null) {
            if (edycja) {
                Msg.msg("w", "Klient o takiej nazwie jest już w bazie");
            } else {
                selected = new Klienci();
                Msg.msg("e", "Klient o takiej nazwie jest już w bazie");
                PrimeFaces.current().executeScript("fakturaduplikatklientakontrahent()");
            }
        }

    }

    public void dodajKlienta() {
        try {
            boolean wygenerowanonip = false;
            if (selected.getNip().isEmpty()) {
                wygenerowanonip = wygenerujnip();
            } else {
                wygenerowanonip = true;
            }
            if (wygenerowanonip) {
                selected.setNskrocona(selected.getNpelna());
                if (selected.getKrajnazwa() == null) {
                    selected.setKrajnazwa("Polska");
                } 
                String formatka = selected.getUlica().substring(0, 1).toUpperCase();
                formatka = formatka.concat(selected.getUlica().substring(1));
                selected.setUlica(formatka);
                String kraj = selected.getKrajnazwa();
                String symbol = ps1.getWykazPanstwSX().get(kraj);
                selected.setKrajkod(symbol);
                if (selected.getLokal() == null || selected.getLokal().equals("")) {
                    selected.setLokal("-");
                }
                poszukajDuplikatNip();
                poszukajDuplikatNazwa();
                klDAO.create(selected);
                kl1.add(selected);
                Msg.msg("i", "Dodano nowego klienta" + selected.getNpelna());
                selected = new Klienci();
                //selected.setKrajnazwa("Polska");
                //selected.setKrajkod(panstwaMapa.getWykazPanstwSX().get("Polska"));
            } else {
                Msg.msg("e", "Błąd przy genrowaniu nr NIP. Nie dodano klienta");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie dodano nowego klienta. Klient o takim Nip/Nazwie pełnej juz istnieje");
        }

    }
    
    public void dodajKlientaFaktura(Faktura faktura) {
        try {
            boolean wygenerowanonip = false;
            if (selected.getNip().isEmpty()) {
                wygenerowanonip = wygenerujnip();
            } else {
                wygenerowanonip = true;
            }
            if (wygenerowanonip) {
                selected.setNskrocona(selected.getNpelna());
                if (selected.getKrajnazwa() == null) {
                    selected.setKrajnazwa("Polska");
                } 
                String formatka = selected.getUlica().substring(0, 1).toUpperCase();
                formatka = formatka.concat(selected.getUlica().substring(1));
                selected.setUlica(formatka);
                String kraj = selected.getKrajnazwa();
                String symbol = ps1.getWykazPanstwSX().get(kraj);
                selected.setKrajkod(symbol);
                if (selected.getLokal() == null || selected.getLokal().equals("")) {
                    selected.setLokal("-");
                }
                poszukajDuplikatNip();
                poszukajDuplikatNazwa();
                klDAO.create(selected);
                if (faktura!=null) {
                    faktura.setKontrahent(selected);
                    faktura.setKontrahent_nip(selected.getNip());
                }
                kl1.add(selected);
                Msg.msg("i", "Dodano nowego klienta" + selected.getNpelna());
                selected = new Klienci();
                //selected.setKrajnazwa("Polska");
                //selected.setKrajkod(panstwaMapa.getWykazPanstwSX().get("Polska"));
            } else {
                Msg.msg("e", "Błąd przy genrowaniu nr NIP. Nie dodano klienta");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie dodano nowego klienta. Klient o takim Nip/Nazwie pełnej juz istnieje");
        }

    }

    public void dodajKlientafk(Dokfk dokfk, EVatwpisFK evfk) {
        try {
            if (selected.getNip().isEmpty()) {
                wygenerujnip();
            }
            selected.setNskrocona(selected.getNpelna());
            if (selected.getKrajnazwa() == null) {
                selected.setKrajnazwa("Polska");
            } 
            String kraj = selected.getKrajnazwa();
            String symbol = ps1.getWykazPanstwSX().get(kraj);
            selected.setKrajkod(symbol);
            if (selected.getLokal() == null || selected.getLokal().equals("")) {
                selected.setLokal("-");
            }
            poszukajDuplikatNip();
            poszukajDuplikatNazwa();
            klDAO.create(selected);
            kl1.add(selected);
            //planKontCompleteView.init(); to jest zbedne, po co pobierac konta jeszcze raz skoro dodano tylko pozycje do kartoteki klientow a nie kont.
            Msg.msg("i", "Dodano nowego klienta" + selected.getNpelna());
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie dodano nowego klienta. Klient o takim Nip/Nazwie pełnej juz istnieje");
        }
        if (evfk != null) {
            evfk.setKlient(selected);
            PrimeFaces.current().ajax().update("ewidencjavatRK:klientRK");
        }
        //jeżeli funkcja jest wywolana z wpisywania dokumnetu to zerujemy pola
        if (dokfk != null) {
            dokfk.setKontr(selected);
            PrimeFaces.current().ajax().update("formwpisdokument:acForce");
            PrimeFaces.current().ajax().update("formXNowyKlient:polawprowadzania");
            PrimeFaces.current().ajax().update("formXNowyKlient:polawprowadzania1");
        }
        try {
            kliencifkView.setWybranyklient(serialclone.SerialClone.clone(selected));
            kliencifkView.setWybranyklient1(serialclone.SerialClone.clone(selected));
            kliencifkView.pobieraniekontaFK();
            PrimeFaces.current().ajax().update("kliencifk");
            PrimeFaces.current().ajax().update("kontoformV");
            PrimeFaces.current().ajax().update("kontoformE");
        } catch (Exception e) {}
        PrimeFaces.current().ajax().update("form_dialog_wpisywanie_znajdzkontrahenta");
        selected = new Klienci();
        PrimeFaces.current().ajax().update("formXNowyKlient");
    }
    
    public void dodajKlientafkSilent(Dokfk dokfk, EVatwpisFK evfk) {
        try {
            if (selected.getNip().isEmpty()) {
                wygenerujnip();
            }
            String formatka = selected.getNskrocona().toUpperCase();
            selected.setNskrocona(formatka);
            if (selected.getKrajnazwa() == null) {
                Msg.msg("e", "Nie dodano nowego klienta. Nie wprowadzono kraju");
                return;
            } else {
                String kraj = selected.getKrajnazwa();
                String symbol = ps1.getWykazPanstwSX().get(kraj);
                selected.setKrajkod(symbol);
                poszukajDuplikatNip();
                poszukajDuplikatNazwa();
                klDAO.create(selected);
                kl1.add(selected);
                //planKontCompleteView.init(); to jest zbedne, po co pobierac konta jeszcze raz skoro dodano tylko pozycje do kartoteki klientow a nie kont.
                Msg.msg("i", "Dodano nowego klienta" + selected.getNpelna());
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie dodano nowego klienta. Klient o takim Nip/Nazwie pełnej juz istnieje");
        }
        if (evfk != null) {
            evfk.setKlient(selected);
        }
        //jeżeli funkcja jest wywolana z wpisywania dokumnetu to zerujemy pola
        if (dokfk != null) {
            dokfk.setKontr(selected);
        }
        try {
            kliencifkView.setWybranyklient(serialclone.SerialClone.clone(selected));
            kliencifkView.setWybranyklient1(serialclone.SerialClone.clone(selected));
            kliencifkView.pobieraniekontaFK();
        } catch (Exception e) {}
        selected = new Klienci();
    }

//    public void pobierzklientazPliku() throws IOException {
//        readLargerTextFile(FILE_NAME);
//
//    }

//    public void readLargerTextFile(String aFileName) throws IOException {
//        Path path = Paths.get(aFileName);
//        try (Scanner scanner = new Scanner(path, "Windows-1250")) {
//            int i = 0;
//            while (scanner.hasNextLine()) {
//                String tmp = String.valueOf(scanner.nextLine());
//                while (i < 28) {
//                    if (tmp.contains("bnazwa")) {
//                        while (!tmp.contains("Kontrahent")) {
//                            tmp = String.valueOf(scanner.nextLine());
//                        }
//                        i = 27;
//                        break;
//                    } else if (tmp.contains("nazwa")) {
//                        i++;
//                        tmp = tmp.replace("\"", "");
//                        tmp = tmp.replace("'", "");
//                        selected.setNpelna(tmp.substring(8));
//                        break;
//                    } else if (tmp.contains("miejscowosc")) {
//                        i++;
//                        selected.setMiejscowosc(tmp.substring(14));
//                        break;
//                    } else if (tmp.contains("ulica")) {
//                        i++;
//                        selected.setUlica(tmp.substring(8));
//                        break;
//                    } else if (tmp.contains("dom")) {
//                        i++;
//                        selected.setDom(tmp.substring(6));
//                        break;
//                    } else if (tmp.contains("lokal")) {
//                        i++;
//                        selected.setLokal(tmp.substring(8));
//                        break;
//                    } else if (tmp.contains("kodPocztowy")) {
//                        i++;
//                        selected.setKodpocztowy(tmp.substring(14));
//                        break;
//                    } else if (tmp.contains("kod")) {
//                        i++;
//                        tmp = tmp.replace("\"", "");
//                        tmp = tmp.replace("'", "");
//                        selected.setNskrocona(tmp.substring(6));
//                        break;
//                    } else if (tmp.contains("NIP")) {
//                        i++;
//                        tmp = tmp.replace("-", "");
//                        selected.setNip(tmp.substring(6));
//                        break;
//                    } else if (tmp.contains("krajKod")) {
//                        i++;
//                        selected.setKrajkod(tmp.substring(10));
//                        break;
//                    } else if (tmp.contains("krajNazwa")) {
//                        i++;
//                        selected.setKrajnazwa(tmp.substring(12));
//                        break;
//                    } else {
//                        i++;
//                        break;
//                    }
//                }
//                if (i == 27) {
//                    Klienci knazwa = null;
//                    try {
//                        knazwa = klDAO.findKlientByNazwa(selected.getNpelna());
//                    } catch (Exception e1) {
//
//                    }
//                    Klienci knip = null;
//                    try {
//                        knip = klDAO.findKlientByNip(selected.getNip());
//                    } catch (Exception e1) {
//
//                    }
//                    try {
//                        if (knazwa == null && knip == null) {
//                            klDAO.create(selected);
//                        }
//                    } catch (Exception es) {
//                    }
//                    i = 0;
//                }
//            }
//            Msg.msg("Skonczylem import");
//        }
//    }

    public List<Klienci> complete(String query) {
        List<Klienci> results = Collections.synchronizedList(new ArrayList<>());
        try {
            String q = query.substring(0, 1);
            int i = Integer.parseInt(q);
            results.addAll(kl1.parallelStream().filter(p->p.getNip().startsWith(query)).collect(Collectors.toList()));
        } catch (NumberFormatException e) {
            results.addAll(kl1.parallelStream().filter(p->p.getNpelna().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList()));
        }
        results.add(new Klienci(-1, "nowy klient", "nowy klient", "0123456789", "11-111", "miejscowosc", "ulica", "1", "1"));
        return results;
    }

    public void edit() {
        try {
            //sformatuj();
            String kraj = selected.getKrajnazwa();
            if (kraj !=null) {
                String symbol = ps1.getWykazPanstwSX().get(kraj);
                selected.setKrajkod(symbol);
            }
            klDAO.edit(selected);
            edycja = false;
            //refresh();
            //kl1 = Collections.synchronizedList(new ArrayList<>());
            //kl1.addAll(klDAO.findAll());
            Msg.msg("Zapisano zmienione dane klienta "+ selected.getNpelna());
            selected = new Klienci();
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił błąd. Nie zapisano zmienionych danych klienta", E.e(e));
        }
    }

    public void wybranodoedycji(SelectEvent ex) {
        edycja = true;
        selected = selectedtabela;
        if (selected.getNskrocona()==null ||selected.getNskrocona().equals("")) {
            selected.setNskrocona(selected.getNpelna());
            klDAO.edit(selected);
            Msg.msg("dodano nazwę skróconą");
        }
        //selected = (Klienci) ex.getObject();
        Msg.msg("Wybrano klienta do edycji: " + selected.getNpelna());
    }

    public void destroy(Klienci selDok) {
        doUsuniecia = new Klienci();
        doUsuniecia = selDok;

    }

    public void destroy2() {
        try {
            klDAO.remove(doUsuniecia);
            kl1.remove(doUsuniecia);
            klienciFiltered.remove(doUsuniecia);
            PrimeFaces.current().ajax().update("formY:");
            Msg.msg("i", "Usunięto wskazanego klienta", "formX:mess_add");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("i", "Nie można usunąć klienta. Nazwa klienta występuje w zaksiękowancyh dokumentach.");
        }

    }

//    public void dodajpustegomaila() {
//        (selected).setEmail("niema@maila.pl");
//        PrimeFaces.current().ajax().update("formX:emailpole");
//    }

    private void poszukajDuplikatNip() throws Exception {
        String nippoczatkowy = ((Klienci) selected).getNip();
        if (!nippoczatkowy.equals("0000000000")) {
            List<String> kliencitmp = klDAO.findNIP();
            if (!kliencitmp.isEmpty()) {
                if (kliencitmp.contains(nippoczatkowy)) {
                    throw new Exception();
                }
            }
        }
    }

    public void poszukajDuplikatNipWTrakcie() throws Exception {
        if (selected.getId()==null) {
            String nippoczatkowy = selected.getNip();
            if (nippoczatkowy !=null && nippoczatkowy.startsWith("XX")) {
                PrimeFaces.current().executeScript("rj('formX:nipPole').value = 'Nieprawidłowy numer NIP';");
                Msg.msg("e", "Numer NIP nie może zaczynać się od XX");
            } else  if (nippoczatkowy !=null && !nippoczatkowy.equals("0000000000") && !nippoczatkowy.equals("")) {
                List<String> kliencitmp = klDAO.findNIP();
                if (!kliencitmp.isEmpty()) {
                    if (kliencitmp.contains(nippoczatkowy)) {
                        if (edycja) {
                            Msg.msg("w", "Klient o takim numerze NIP juz istnieje!");
                        } else {
                            PrimeFaces.current().executeScript("rj('formX:nipPole').value = 'taki nip jest już w bazie';");
                            Msg.msg("e", "Klient o takim numerze NIP juz istnieje!");
                        }
                    }
                }
            }
        }
    }

    private void poszukajDuplikatNazwa() throws Exception {
        String nowanazwa = selected.getNpelna();
        List<String> kliencitmp = klDAO.findNazwaPelna(nowanazwa.trim());
        if (!kliencitmp.isEmpty()) {
            throw new Exception();
        }
    }

    public void poszukajDuplikatNazwaWTrakcie() throws Exception {
        String nowanazwa = (String) Params.params("formX:nazwaPole");
        List<String> kliencitmp = klDAO.findNazwaPelna(nowanazwa.trim());
        if (!kliencitmp.isEmpty()) {
            Msg.msg("e", "Klient o takim numerze NIP juz istnieje!");
        }
    }

    private boolean wygenerujnip() {
        boolean zwrot = false;
        Pattern pattern = Pattern.compile("X{2}\\d{10}");
        try {
            List<String> nipy = klDAO.findKlientByNipXX();
            Collections.sort(nipy);
            Integer max = 0;
            boolean szukaj = true;
            int licznik = 1;
            int nipysize = nipy.size();
            while (szukaj && licznik<nipysize) {
                if (nipysize > 0) {
                    String pozycja = nipy.get(nipysize - licznik);
                    Matcher m = pattern.matcher(pozycja.toUpperCase());
                    boolean czypasuje = m.matches();
                    if (czypasuje) {
                        max = Integer.parseInt(pozycja.substring(2));
                        max++;
                        szukaj = false;
                        break;
                    } else {
                        licznik++;
                    }
                }
            }
            //uzupelnia o zera i robi stringa;
            String wygenerowanynip = max.toString();
            while (wygenerowanynip.length() < 10) {
                wygenerowanynip = "0" + wygenerowanynip;
            }
            wygenerowanynip = "XX" + wygenerowanynip;
            selected.setNip(wygenerowanynip);
            zwrot = true;
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
    public void znajdzdaneregon(String formularz) {
        try {
            String nip = selected.getNip();
            if (isValidPolishNIP(nip)) {
                SzukajDaneBean.znajdzdaneregon(formularz, (Klienci) selected);
                PrimeFaces.current().executeScript("$(\".uibuttonmui\").show()");
            } else {
                Msg.msg("e","Klient zagraniczny/osoba fizyczna. Nie ma w bazie GUS");
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public void znajdzdaneregonAutomat(Klienci klientfaktura) {
        try {
            String nip = klientfaktura.getNip();
            if (isValidPolishNIP(nip)) {
                Klienci aktualizuj = SzukajDaneBean.znajdzdaneregonAutomat(nip);
                klientfaktura.setNpelna(aktualizuj.getNpelna());
                klientfaktura.setMiejscowosc(aktualizuj.getMiejscowosc());
                klientfaktura.setUlica(aktualizuj.getUlica());
                klientfaktura.setDom(aktualizuj.getDom());
                klientfaktura.setLokal(aktualizuj.getLokal());
                klientfaktura.setKodpocztowy(aktualizuj.getKodpocztowy());
                klientfaktura.setNskrocona(aktualizuj.getNpelna());
                klDAO.edit(klientfaktura);
                Msg.msg("Zaktualizowano dane klienta pobranymi z GUS");
            } else {
                Msg.msg("e","Klient zagraniczny/osoba fizyczna. Nie ma w bazie GUS");
            }
        } catch (Exception e) {
            Msg.msg("e","Błąd, niezaktualizowano dane klienta pobranymi z GUS");
            E.e(e);
        }
    }
    
    public boolean isValidPolishNIP(String nip) {
        // Usunięcie białych znaków i przekształcenie na wielkie litery (jeśli jest przedrostek)
        nip = nip.replaceAll("\\s+", "").toUpperCase();

        // Sprawdzenie przedrostka "PL" i usunięcie go
        if (nip.startsWith("PL")) {
            nip = nip.substring(2);
        }

        // Sprawdzenie, czy długość NIP to 10 cyfr
        if (nip.length() != 10 || !nip.matches("\\d+")) {
            return false;
        }

        // Tablica wag dla cyfr NIP
        int[] weights = {6, 5, 7, 2, 3, 4, 5, 6, 7};

        // Obliczanie sumy kontrolnej
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(nip.charAt(i)) * weights[i];
        }

        // Wyciągnięcie cyfry kontrolnej
        int controlDigit = sum % 11;

        // Sprawdzenie, czy cyfra kontrolna zgadza się z ostatnią cyfrą NIP
        return controlDigit == Character.getNumericValue(nip.charAt(9));
    }
    
    
    public void wstawkreske(int wstaw) {
        if (wstaw==0) {
            if (selected.getDom()==null || selected.getDom().isEmpty()) {
                selected.setDom("-");
            }
        } else if (wstaw==1) {
            if (selected.getLokal()==null || selected.getLokal().isEmpty()) {
                selected.setLokal("-");
            }
        } else {
            if (selected.getUlica()==null || selected.getUlica().isEmpty()) {
                selected.setUlica("-");
            }
        }
    }
    
 
    

    public Klienci getSelected() {
        return selected;
    }

    public void setSelected(Klienci selected) {
        this.selected = selected;
    }

    public Integer getIlesrodkow() {
        return ilesrodkow;
    }

    public void setIlesrodkow(Integer ilesrodkow) {
        this.ilesrodkow = ilesrodkow;
    }

    public KlienciDAO getKlDAO() {
        return klDAO;
    }

    public void setKlDAO(KlienciDAO klDAO) {
        this.klDAO = klDAO;
    }

    public List<Klienci> getKl1() {
        return kl1;
    }

    public void setKl1(List<Klienci> kl1) {
        this.kl1 = kl1;
    }

    public List<Klienci> getKlienciFiltered() {
        return klienciFiltered;
    }

    public void setKlienciFiltered(List<Klienci> klienciFiltered) {
        this.klienciFiltered = klienciFiltered;
    }

    

    public void setKlienciFiltered(ArrayList<Klienci> klienciFiltered) {
        this.klienciFiltered = klienciFiltered;
    }

    public Klienci getDoUsuniecia() {
        return doUsuniecia;
    }

    public void setDoUsuniecia(Klienci doUsuniecia) {
        this.doUsuniecia = doUsuniecia;
    }

    public boolean isEdycja() {
        return edycja;
    }

    public void setEdycja(boolean edycja) {
        this.edycja = edycja;
    }

    public Klienci getSelectedtabela() {
        return selectedtabela;
    }

    public void setSelectedtabela(Klienci selectedtabela) {
        this.selectedtabela = selectedtabela;
    }

    
        public KliencifkView getKliencifkView() {
        return kliencifkView;
    }

    public void setKliencifkView(KliencifkView kliencifkView) {
        this.kliencifkView = kliencifkView;
    }

    public GUSView getgUSView() {
        return gUSView;
    }

    public void setgUSView(GUSView gUSView) {
        this.gUSView = gUSView;
    }


    public static void main(String[] args) {
        Pattern p = Pattern.compile("^[a-zA-Z]+$");//<-- compile( not Compile(
        Matcher m = p.matcher("851100".substring(0,1));  //<-- matcher( not Matcher
        if(!m.find()) {
        }
    }
    
}
