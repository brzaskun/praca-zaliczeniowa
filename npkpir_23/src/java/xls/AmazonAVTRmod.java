package xls;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Osito
 */

import entity.KlientJPK;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class AmazonAVTRmod {

    public static List<KlientJPK> importFromExcel(String filePath) throws IOException {
        List<KlientJPK> klientRecords = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

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

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                KlientJPK klientJPK = new KlientJPK();

                // Mapping columns by names to KlientJPK fields
                klientJPK.setSerial(getCellStringValue(row, columnIndices.get("TRANSACTION_EVENT_ID")));
                System.out.println(klientJPK.getSerial());
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
                klientJPK.setKurs(getCellDoubleValue(row, columnIndices.get("VAT_INV_EXCHANGE_RATE")));
                klientJPK.setRok(data.Data.getRok(klientJPK.getDataSprzedazy()));
                klientJPK.setMc(data.Data.getMc(klientJPK.getDataSprzedazy()));
                klientJPK.setWaluta(getCellStringValue(row, columnIndices.get("TRANSACTION_CURRENCY_CODE")));
                // Setting eksport and importt fields based on DEPARTURE_COUNTRY and ARRIVAL_COUNTRY
                String departureCountry = getCellStringValue(row, columnIndices.get("DEPARTURE_COUNTRY"));
                String arrivalCountry = getCellStringValue(row, columnIndices.get("ARRIVAL_COUNTRY"));
                klientJPK.setEksport("PL".equals(departureCountry) && !"PL".equals(arrivalCountry));
                klientJPK.setImportt(!"PL".equals(departureCountry) && "PL".equals(arrivalCountry));
                klientJPK.setOpissprzedaz(getCellStringValue(row, columnIndices.get("ITEM_DESCRIPTION")));
                

                klientRecords.add(klientJPK);
            }
        }
        
        return klientRecords;
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
    if (columnIndex == null) return null;
    Cell cell = row.getCell(columnIndex);
    if (cell == null) return 0.0;

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
        default:
            return null;
    }
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


    public static void main(String[] args) {
        try {            List<KlientJPK> records = importFromExcel("d:/avtr.xlsx");
            //records.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String pobierznumerkontrahenta(String rodzajtransakcji, Row row, Map<String, Integer> columnIndices) {
        if (rodzajtransakcji.equals("FC_TRANSFER")) {
            return getCellStringValue(row, columnIndices.get("SELLER_ARRIVAL_COUNTRY_VAT_NUMBER"));
        } else {
            return getCellStringValue(row, columnIndices.get("TRANSACTION_SELLER_VAT_NUMBER"));
        }
    }

    private static String pobierznazwekontrahenta(String rodzajtransakcji, Row row, Map<String, Integer> columnIndices) {
        String zwrot = getCellStringValue(row, columnIndices.get("BUYER_NAME"));
        if (zwrot == null) {
            zwrot = getCellStringValue(row, columnIndices.get("SELLER_SKU"));
        }
        return zwrot;
    }
}


