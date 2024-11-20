package xls;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Osito
 */
import beansFK.TabelaNBPBean;
import dao.IntrastatwierszDAO;
import dao.KlientJPKDAO;
import dao.PodsumowanieAmazonOSSDAO;
import dao.TabelanbpDAO;
import data.Data;
import embeddable.Mce;
import entity.KlientJPK;
import entity.Podatnik;
import entity.PodsumowanieAmazonOSS;
import entity.Uz;
import entityfk.Intrastatwiersz;
import entityfk.Tabelanbp;
import error.E;
import f.l;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import msg.Msg;
import org.apache.poi.ss.usermodel.*;
import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.apache.poi.ss.usermodel.CellType.FORMULA;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import pdf.PdfAmazon;
import pl.gov.mf.xsd.intrastat.ist.IST;
import pl.gov.mf.xsd.intrastat.ist.ObjectFactory;
import view.WpisView;
import waluty.Z;
import xml.XMLValid;

@Named
@ViewScoped
public class AmazonAVTRmod implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<KlientJPK> lista;
    private List<KlientJPK> listafilter;
    private List<Intrastatwiersz> listaintrastat;
    @Inject
    private PodsumowanieAmazonOSSDAO podsumowanieAmazonOSSDAO;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private KlientJPKDAO klientJPKDAO;
    @Inject
    private IntrastatwierszDAO intrastatwierszDAO;
    private Map<String, Double> kursMap;
    @Inject
    private WpisView wpisView;

    public void init() {

    }

    public void importujsprzedaz(FileUploadEvent event) {
        try {
//            dokumenty = Collections.synchronizedList(new ArrayList<>());
//            klienci = Collections.synchronizedList(new ArrayList<>());
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            importFromExcel(uploadedFile);
            //dokumenty = stworzdokumenty(amazonCSV);
            if (!lista.isEmpty()) {
                Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
            }
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e", "Wystąpił błąd. Nie udało się załadowanać pliku");
        }
        PrimeFaces.current().executeScript("PF('dialogAjaxCzekaj').hide()");
    }

    public void importFromExcel(UploadedFile uploadedFile) throws IOException {
        lista = new ArrayList<>();
        listafilter = new ArrayList<>();
        listaintrastat = new ArrayList<>();
        kursMap = new ConcurrentHashMap<>();
        try (InputStream is = uploadedFile.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Build column index map based on header row
            Map<String, Integer> columnIndices = new HashMap<>();
            if (rowIterator.hasNext()) {
                Row headerRow = rowIterator.next();
                for (Cell cell : headerRow) {
                    columnIndices.put(cell.getStringCellValue(), cell.getColumnIndex());
                }
            }
            int rowa = 1;
            while (rowIterator.hasNext()) {
                try {
                    Row row = rowIterator.next();
                    KlientJPK klientJPK = new KlientJPK();
                    rowa++;
                    // Mapping columns by names to KlientJPK fields
                    klientJPK.setSerial(getCellStringValue(row, columnIndices.get("TRANSACTION_EVENT_ID")));
                    //System.out.println(klientJPK.getSerial());
                    klientJPK.setPodatnik(wpisView.getPodatnikObiekt());
                    klientJPK.setRodzajtransakcji(getCellStringValue(row, columnIndices.get("TRANSACTION_TYPE")));
                    klientJPK.setKodKrajuNadania(getCellStringValue(row, columnIndices.get("DEPARTURE_COUNTRY")));
                    klientJPK.setKodKrajuDoreczenia(getCellStringValue(row, columnIndices.get("ARRIVAL_COUNTRY")));
                    klientJPK.setJurysdykcja(getCellStringValue(row, columnIndices.get("TAXABLE_JURISDICTION")));
                    klientJPK.setNrKontrahenta(pobierznumerkontrahenta(klientJPK.getRodzajtransakcji(), row, columnIndices));
                    klientJPK.setNazwaKontrahenta(pobierznazwekontrahenta(klientJPK.getRodzajtransakcji(), row, columnIndices));
                    klientJPK.setDowodSprzedazy(getCellStringValue(row, columnIndices.get("TRANSACTION_EVENT_ID")));
                    klientJPK.setDataWystawienia(data.Data.zmienkolejnosc(getCellStringValue(row, columnIndices.get("TAX_CALCULATION_DATE"))));
                    klientJPK.setDataSprzedazy(data.Data.zmienkolejnosc(getCellStringValue(row, columnIndices.get("TRANSACTION_COMPLETE_DATE"))));
                    klientJPK.setWaluta(getCellStringValue(row, columnIndices.get("TRANSACTION_CURRENCY_CODE")));
                    double kurs = pobierzkurs(klientJPK.getDataSprzedazy(), klientJPK.getWaluta());
                    klientJPK.setKurs(kurs);
                    double netto = getCellDoubleValue(row, columnIndices.get("TOTAL_ACTIVITY_VALUE_AMT_VAT_EXCL"));
                    double nettopln = Z.z(netto*kurs);
                    double vat = getCellDoubleValue(row, columnIndices.get("TOTAL_ACTIVITY_VALUE_VAT_AMT"));
                    double vatpln = Z.z(vat*kurs);
                    klientJPK.setNetto(nettopln);
                    klientJPK.setVat(vatpln);
                    klientJPK.setNettowaluta(getCellDoubleValue(row, columnIndices.get("TOTAL_ACTIVITY_VALUE_AMT_VAT_EXCL")));
                    klientJPK.setVatwaluta(getCellDoubleValue(row, columnIndices.get("TOTAL_ACTIVITY_VALUE_VAT_AMT")));
                    klientJPK.setStawkavat(getCellDoubleValue(row, columnIndices.get("PRICE_OF_ITEMS_VAT_RATE_PERCENT")));
                    //klientJPK.setKurs(getCellDoubleValue(row, columnIndices.get("VAT_INV_EXCHANGE_RATE")));
                    klientJPK.setRok(data.Data.getRok(klientJPK.getDataSprzedazy()));
                    klientJPK.setMc(data.Data.getMc(klientJPK.getDataSprzedazy()));
                    klientJPK.setAmazontax0additional1(0);
                    // Setting eksport and importt fields based on DEPARTURE_COUNTRY and ARRIVAL_COUNTRY
                    String departureCountry = getCellStringValue(row, columnIndices.get("DEPARTURE_COUNTRY"));
                    String arrivalCountry = getCellStringValue(row, columnIndices.get("ARRIVAL_COUNTRY"));
                    if (klientJPK.getRodzajtransakcji().equals("FC TRANSFER")==false) {
                        klientJPK.setWdt("PL".equals(departureCountry) && !"PL".equals(arrivalCountry));
                        klientJPK.setWnt(!"PL".equals(departureCountry) && "PL".equals(arrivalCountry));
                    }
                    klientJPK.setOpissprzedaz(getCellStringValue(row, columnIndices.get("ITEM_DESCRIPTION")));
                    //dane do intrastat
                    klientJPK.setIlosc(getCellDoubleValue(row, columnIndices.get("QTY")));
                    klientJPK.setWaga(getCellDoubleValue(row, columnIndices.get("ITEM_WEIGHT")));
                    Integer kodtowaru = getCellintegerValue(row, columnIndices.get("COMMODITY_CODE"));
                    if (kodtowaru!=null) {
                        klientJPK.setKodtowaru(kodtowaru);
                    } else {
                        klientJPK.setKodtowaru(99999999);
                    }
                    lista.add(klientJPK);
                    importujdaneintrastat();
                } catch (Exception e) {
                    System.out.println("wiersz " + rowa);
                    System.out.println(E.e(e));
                    if (rowa > 1000) {
                        break;
                    }
                }
            }
        }

    }
    
     public void zaksiegujWDTjpk() {
        klientJPKDAO.deleteByPodRokMcAmazon(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(),0);
        List<KlientJPK> selekcjafctransfer = lista.stream().filter(item->item.getRodzajtransakcji().equals("FC_TRANSFER")).collect(Collectors.toList());
        int duplikaty = 0;
        if (selekcjafctransfer==null || selekcjafctransfer.isEmpty()) {
            Msg.msg("e","W danym okresie nie ma transakcji FC_TRNASFER");
        } else {
            if (!selekcjafctransfer.isEmpty()) {
                klientJPKDAO.createList(selekcjafctransfer);
                Msg.msg("Zaksięgowano dokumenty dla JPK");
            }
        }
    }
    
     public void usunmiesiac() {
         klientJPKDAO.deleteByPodRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
         Msg.msg("Usunięto dokumenty dla JPK z miesiąca");
     }

    private static String getCellStringValue(Row row, Integer columnIndex) {
        if (columnIndex == null) {
            return null;
        }
        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());  // Konwersja liczb na String
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());  // Konwersja boolean na String
            case FORMULA:
            try {
                return cell.getStringCellValue();
            } catch (IllegalStateException e) {
                return String.valueOf(cell.getNumericCellValue());
            }
            default:
                return null;
        }
    }

    private static Double getCellDoubleValue(Row row, Integer columnIndex) {
        if (columnIndex == null) {
            return null;
        }
        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            return 0.0;
        }
        try {
            switch (cell.getCellType()) {
                case NUMERIC:
                    return cell.getNumericCellValue();
                case STRING:
                try {
                    return Double.parseDouble(cell.getStringCellValue());  // Konwersja String do Double
                } catch (NumberFormatException e) {
                    return null;
                }
                case FORMULA:
                try {
                    return cell.getNumericCellValue();
                } catch (IllegalStateException e) {
                    return null;
                }
                case BLANK:
                    return 0.0;
                default:
                    return null;
            }
        } catch (Exception e) {
            System.out.println(E.e(e));
        }
        return null;
    }

    
     private static Integer getCellintegerValue(Row row, Integer columnIndex) {
        if (columnIndex == null) {
            return null;
        }
        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            return 0;
        }
        try {
            switch (cell.getCellType()) {
                case NUMERIC:
                    return (int)cell.getNumericCellValue();
                case STRING:
                try {
                    return Integer.parseInt(cell.getStringCellValue());  // Konwersja String do Double
                } catch (NumberFormatException e) {
                    return null;
                }
                case FORMULA:
                try {
                    return (int)cell.getNumericCellValue();
                } catch (IllegalStateException e) {
                    return null;
                }
                case BLANK:
                    return 0;
                default:
                    return null;
            }
        } catch (Exception e) {
            System.out.println(E.e(e));
        }
        return null;
    }

    private static Boolean getCellBooleanValue(Row row, Integer columnIndex) {
        if (columnIndex == null) {
            return null;
        }
        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case STRING:
                String cellValue = cell.getStringCellValue().toLowerCase();
                if (cellValue.equals("true") || cellValue.equals("yes")) {
                    return true;
                } else if (cellValue.equals("false") || cellValue.equals("no")) {
                    return false;
                }
                return null;
            case NUMERIC:
                return cell.getNumericCellValue() != 0;  // 0 = false, inne wartości = true
            default:
                return null;
        }
    }

    public void drukujfk() {
        try {
            podsumowanieAmazonOSSDAO.usunmiesiacrok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            List<PodsumowanieAmazonOSS> sumy = PdfAmazon.drukujDokAmazonfk(l.l(lista, listafilter, null), wpisView, 1);
            if (!sumy.isEmpty()) {
                podsumowanieAmazonOSSDAO.createList(sumy);
                Msg.msg("Zaksięgowani sum zaimportowanych dokumentów");
            }
            Msg.msg("Wydrukowano zestawienie zaimportowanych dokumentów");
        } catch (Exception e) {

        }
    }
    
    private void importujdaneintrastat() {
        if (lista!=null) {
            listaintrastat = new ArrayList<>();
            Iterator<KlientJPK> rowIterator = lista.iterator();
            while (rowIterator.hasNext()) {
                KlientJPK row = rowIterator.next();
                    Intrastatwiersz w = new Intrastatwiersz(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), wpisView.getPodatnikObiekt());
                    String transakcja = row.getRodzajtransakcji();
                    if (transakcja != null && transakcja.equals("FC_TRANSFER") && (row.getKodKrajuNadania().equals("PL")||row.getKodKrajuDoreczenia().equals("PL"))) {
                        w.setNumerkolejny(row.getSerial());
                        w.setIlosc(String.valueOf(row.getIlosc()));
                        w.setMasanettokg(String.valueOf(row.getWaga()));
                        w.setWartoscfaktury(Z.zUDI(row.getNetto()+row.getVat()));
                        w.setKodtowaru(row.getKodtowaru());
                        w.setRodzajtransakcji(11);
                        w.setOpistowaru(row.getOpissprzedaz());
                        w.setKrajprzeznaczenia(row.getKodKrajuDoreczenia());
                        w.setVatuekontrahenta(row.getNrKontrahenta());
                        listaintrastat.add(w);
                    }
                }
            }
    }

    public void zaksiegujDaneIntrastat() {
        try {
            intrastatwierszDAO.deleteByPodRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            intrastatwierszDAO.createList(listaintrastat);
            Msg.msg("Zachowano wiersze");
        } catch (Exception e) {
        }
    }
    
    public IST generujIntrastat() {
        IST ist = new IST();
        if (listaintrastat.isEmpty()) {
            Msg.msg("e", "Brak zaimportowanych wierszy nie można wygenerować Intrastat");
        } else {
            try {
                ist.setEmail(wpisView.getUzer().getEmail());
                ObjectFactory ob = new ObjectFactory();
                IST.Deklaracja deklaracja = ob.createISTDeklaracja();
                deklaracja.setData(Data.databiezaca());
                deklaracja.setLacznaLiczbaPozycji(lista.size());
                deklaracja.setLacznaWartoscFaktur(podsumuj(listaintrastat));
                deklaracja.setMiejscowosc("Szczecin");
                deklaracja.setMiesiac(Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu()));
                deklaracja.setNrWlasny("1D" + wpisView.getRokWpisuSt() + wpisView.getMiesiacWpisu() + "W1");
                deklaracja.setNumer(1);
                deklaracja.setPodmiotZobowiazany(zrobpodmiot(wpisView));
                deklaracja.setZglaszajacy(zrobzglaszajacy(wpisView));
                deklaracja.setRodzaj("D");
                deklaracja.setRok(Short.valueOf(wpisView.getRokWpisuSt()));
                deklaracja.setTyp("W");
                deklaracja.setUC(420000);
                deklaracja.setWersja(1);
                deklaracja.setWypelniajacy(zrobwypelniajacy(wpisView));
                dodajwiersze(deklaracja, listaintrastat);
                ist.setDeklaracja(deklaracja);
                String sciezka = marszajuldoplikuxml(wpisView.getPodatnikObiekt(), ist);
            } catch (Exception e) {
                String wiad = "Wystąpił błąd. Nie wygenerowano Intrastat ";
                Msg.msg("e", wiad);
            }
        }
        return ist;
    }

    private String marszajuldoplikuxml(Podatnik podatnik, IST ist) {
        String sciezka = null;
        try {
            JAXBContext context = JAXBContext.newInstance(ist.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            String mainfilename = "intrastat" + podatnik.getNip() + "mcrok" + wpisView.getMiesiacWpisu() + wpisView.getRokWpisuSt() + ".xml";
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/") + "resources\\xml\\";
            try (FileOutputStream fileStream = new FileOutputStream(new File(realPath + mainfilename))) {
                OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
                marshaller.marshal(ist, writer);
                Object[] walidacja = XMLValid.walidujIntrastat(mainfilename);
                String[] zwrot = new String[2];
    //            if (walidacja!=null && walidacja[0]==Boolean.TRUE) {
                zwrot[0] = mainfilename;
                zwrot[1] = "ok";
    //                Msg.msg("Walidacja Intrastat pomyślna");
                Msg.msg("Zachowano Intrastat");
                String exec = "wydrukJPK('" + mainfilename + "')";
                PrimeFaces.current().executeScript(exec);
    //            } else if (walidacja!=null && walidacja[0]==Boolean.FALSE){
    //                zwrot[0] = mainfilename;
    //                zwrot[1] = null;
    //                Msg.msg("Nie zachowano Intrastat");
    //                Msg.msg("e", (String) walidacja[1]);
    //            }
                sciezka = mainfilename;
            } catch (IOException e) {
                // Obsługa wyjątku
            }
        } catch (Exception ex) {
            E.e(ex);
        }
        return sciezka;
    }
    
     private Short podsumuj(List<Intrastatwiersz> lista) {
        int suma = 0;
        for (Intrastatwiersz p : lista) {
            suma = suma + p.getWartoscfaktury();
        }
        return (short) suma;
    }

    private IST.Deklaracja.PodmiotZobowiazany zrobpodmiot(WpisView wpisView) {
        ObjectFactory ob = new ObjectFactory();
        IST.Deklaracja.PodmiotZobowiazany pod = ob.createISTDeklaracjaPodmiotZobowiazany();
        Podatnik p = wpisView.getPodatnikObiekt();
        pod.setNazwa(p.getPrintnazwa());
        pod.setMiejscowosc(p.getMiejscowosc());
        pod.setKodPocztowy(p.getKodpocztowy());
        pod.setNip(Long.parseLong(p.getNip()));
        pod.setRegon(Long.parseLong(p.getRegon() + "00000"));
        pod.setUlicaNumer(p.getUlica() + " " + p.getNrdomu());
        return pod;
    }

    private IST.Deklaracja.Zglaszajacy zrobzglaszajacy(WpisView wpisView) {
        ObjectFactory ob = new ObjectFactory();
        IST.Deklaracja.Zglaszajacy pod = ob.createISTDeklaracjaZglaszajacy();
        Podatnik p = wpisView.getPodatnikObiekt();
        pod.setNazwa(p.getPrintnazwa());
        pod.setMiejscowosc(p.getMiejscowosc());
        pod.setKodPocztowy(p.getKodpocztowy());
        pod.setNip(Long.parseLong(p.getNip()));
        pod.setRegon(Long.parseLong(p.getRegon() + "00000"));
        pod.setUlicaNumer(p.getUlica() + " " + p.getNrdomu());
        return pod;
    }

    private IST.Deklaracja.Wypelniajacy zrobwypelniajacy(WpisView wpisView) {
        ObjectFactory ob = new ObjectFactory();
        IST.Deklaracja.Wypelniajacy wyp = ob.createISTDeklaracjaWypelniajacy();
        Uz w = wpisView.getUzer();
        wyp.setNazwiskoImie(w.getImieNazwisko());
        wyp.setEmail(w.getEmail());
        wyp.setTelefon(Integer.parseInt(w.getNrtelefonu()));
        return wyp;
    }

    private void dodajwiersze(IST.Deklaracja deklaracja, List<Intrastatwiersz> lista) {
        Integer i = 1;
        for (Intrastatwiersz p : lista) {
            try {
                IST.Deklaracja.Towar tow = new IST.Deklaracja.Towar();
                tow.setIdKontrahenta(p.getVatuekontrahenta());
                int ilosc = Z.zUDI(Double.parseDouble(p.getIlosc()));
                tow.setIloscUzupelniajacaJm(ilosc);
                tow.setKodTowarowy(p.getKodtowaru());
                tow.setKrajPrzeznaczeniaWysylki(p.getKrajprzeznaczenia());
                Integer masa = (int) Double.parseDouble(p.getMasanettokg());
                tow.setMasaNetto(masa);
                tow.setOpisTowaru(p.getOpistowaru());
                tow.setPozId(i++);
                tow.setRodzajTransakcji(p.getRodzajtransakcji());
                tow.setWartoscFaktury(p.getWartoscfaktury().shortValue());
                deklaracja.getTowar().add(tow);
            } catch (Exception e) {
                System.out.println(E.e(e));
            }
        }
    }

    public static void main(String[] args) {
        try {
//            List<KlientJPK> records = importFromExcel("d:/avtr.xlsx");
//            records.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String pobierznumerkontrahenta(String rodzajtransakcji, Row row, Map<String, Integer> columnIndices) {
        if (rodzajtransakcji.equals("FC_TRANSFER")) {
            return getCellStringValue(row, columnIndices.get("SELLER_ARRIVAL_COUNTRY_VAT_NUMBER"));
        } else {
            return getCellStringValue(row, columnIndices.get("TRANSACTION_SELLER_VAT_NUMBER"));
        }
    }

    private String pobierznazwekontrahenta(String rodzajtransakcji, Row row, Map<String, Integer> columnIndices) {
        String zwrot = getCellStringValue(row, columnIndices.get("BUYER_NAME"));
        if (zwrot == null) {
            zwrot = getCellStringValue(row, columnIndices.get("SELLER_SKU"));
        }
        return zwrot;
    }

    private double pobierzkurs(String data, String waluta) {
        String key = data + "-" + waluta;
        if (kursMap.containsKey(key)) {
            return kursMap.get(key);
        } else {
            double zwrot = 0.0;
            DateTime dzienPoszukiwany = new DateTime(data);
            // Tu się dodaje tabelę do dokumentu :)
            Tabelanbp tabela = TabelaNBPBean.pobierzTabeleNBP(dzienPoszukiwany, tabelanbpDAO, waluta);
            if (tabela!=null) {
                zwrot = Z.z6(tabela.getKurssredniPrzelicznik());
                // Dodaj kurs do mapy
                kursMap.put(key, zwrot);
            }
            return zwrot;
        }
    }
    
   

    public List<KlientJPK> getLista() {
        return lista;
    }

    public void setLista(List<KlientJPK> lista) {
        this.lista = lista;
    }

    public List<KlientJPK> getListafilter() {
        return listafilter;
    }

    public void setListafilter(List<KlientJPK> listafilter) {
        this.listafilter = listafilter;
    }

    public List<Intrastatwiersz> getListaintrastat() {
        return listaintrastat;
    }

    public void setListaintrastat(List<Intrastatwiersz> listaintrastat) {
        this.listaintrastat = listaintrastat;
    }
    
    

}
