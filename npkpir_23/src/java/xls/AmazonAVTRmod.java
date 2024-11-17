package xls;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Osito
 */

import dao.PodsumowanieAmazonOSSDAO;
import entity.KlientJPK;
import entity.PodsumowanieAmazonOSS;
import error.E;
import f.l;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.*;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.apache.poi.ss.usermodel.*;
import static org.apache.poi.ss.usermodel.CellType.FORMULA;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import pdf.PdfAmazon;
import view.WpisView;

@Named
@ViewScoped
public class AmazonAVTRmod implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<KlientJPK> lista;
    private List<KlientJPK> listafilter;
    @Inject
    private PodsumowanieAmazonOSSDAO podsumowanieAmazonOSSDAO;
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
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
        PrimeFaces.current().executeScript("PF('dialogAjaxCzekaj').hide()");
    }       

    public void importFromExcel(UploadedFile uploadedFile) throws IOException {
        lista = new ArrayList<>();
        try (InputStream is = uploadedFile.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

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
                    klientJPK.setRodzajtransakcji(getCellStringValue(row, columnIndices.get("TRANSACTION_TYPE")));
                    klientJPK.setKodKrajuNadania(getCellStringValue(row, columnIndices.get("DEPARTURE_COUNTRY")));
                    klientJPK.setKodKrajuDoreczenia(getCellStringValue(row, columnIndices.get("ARRIVAL_COUNTRY")));
                    klientJPK.setJurysdykcja(getCellStringValue(row, columnIndices.get("TAXABLE_JURISDICTION")));
                    klientJPK.setNrKontrahenta(pobierznumerkontrahenta(klientJPK.getRodzajtransakcji(),row, columnIndices));
                    klientJPK.setNazwaKontrahenta(pobierznazwekontrahenta(klientJPK.getRodzajtransakcji(),row, columnIndices));
                    klientJPK.setDowodSprzedazy(getCellStringValue(row, columnIndices.get("TRANSACTION_EVENT_ID")));
                    klientJPK.setDataWystawienia(data.Data.zmienkolejnosc(getCellStringValue(row, columnIndices.get("TAX_CALCULATION_DATE"))));
                    klientJPK.setDataSprzedazy(data.Data.zmienkolejnosc(getCellStringValue(row, columnIndices.get("TRANSACTION_DEPART_DATE"))));
                    klientJPK.setNetto(getCellDoubleValue(row, columnIndices.get("TOTAL_ACTIVITY_VALUE_AMT_VAT_EXCL")));
                    klientJPK.setVat(getCellDoubleValue(row, columnIndices.get("TOTAL_ACTIVITY_VALUE_VAT_AMT")));
                    klientJPK.setNettowaluta(getCellDoubleValue(row, columnIndices.get("TOTAL_ACTIVITY_VALUE_AMT_VAT_EXCL")));
                    klientJPK.setVatwaluta(getCellDoubleValue(row, columnIndices.get("TOTAL_ACTIVITY_VALUE_VAT_AMT")));
                    klientJPK.setStawkavat(getCellDoubleValue(row, columnIndices.get("PRICE_OF_ITEMS_VAT_RATE_PERCENT")));
                    //klientJPK.setKurs(getCellDoubleValue(row, columnIndices.get("VAT_INV_EXCHANGE_RATE")));
                    klientJPK.setRok(data.Data.getRok(klientJPK.getDataSprzedazy()));
                    klientJPK.setMc(data.Data.getMc(klientJPK.getDataSprzedazy()));
                    klientJPK.setWaluta(getCellStringValue(row, columnIndices.get("TRANSACTION_CURRENCY_CODE")));
                    // Setting eksport and importt fields based on DEPARTURE_COUNTRY and ARRIVAL_COUNTRY
                    String departureCountry = getCellStringValue(row, columnIndices.get("DEPARTURE_COUNTRY"));
                    String arrivalCountry = getCellStringValue(row, columnIndices.get("ARRIVAL_COUNTRY"));
                    klientJPK.setEksport("PL".equals(departureCountry) && !"PL".equals(arrivalCountry));
                    klientJPK.setImportt(!"PL".equals(departureCountry) && "PL".equals(arrivalCountry));
                    klientJPK.setOpissprzedaz(getCellStringValue(row, columnIndices.get("ITEM_DESCRIPTION")));
                    lista.add(klientJPK);
                } catch (Exception e) {
                    System.out.println("wiersz "+rowa);
                    System.out.println(E.e(e));
                    if (rowa>1000) {
                        break;
                    }
                }
            }
        }

    }

    private static String getCellStringValue(Row row, Integer columnIndex) {
    if (columnIndex == null) return null;
    Cell cell = row.getCell(columnIndex);
    if (cell == null) return null;

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

private static Boolean getCellBooleanValue(Row row, Integer columnIndex) {
    if (columnIndex == null) return null;
    Cell cell = row.getCell(columnIndex);
    if (cell == null) return null;

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

    public static void main(String[] args) {
        try {            
//            List<KlientJPK> records = importFromExcel("d:/avtr.xlsx");
//            records.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  String pobierznumerkontrahenta(String rodzajtransakcji, Row row, Map<String, Integer> columnIndices) {
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
    
    
}


