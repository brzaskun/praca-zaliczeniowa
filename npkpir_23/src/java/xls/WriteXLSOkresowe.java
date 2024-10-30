/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xls;

/**
 *
 * @author Osito
 */
import entity.Fakturywystokresowe;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteXLSOkresowe implements Serializable {

    private static final long serialVersionUID = 1L;

    public static void exportToExcel(List<Fakturywystokresowe> fakturyList) {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/");
        String FILE_PATH1 = "wydruki/taxmanokresowe.xlsx";
        String FILE_PATH = realPath + FILE_PATH1;
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Fakturywystokresowe");

        // Create header row
        String[] headers = {
            "ID", "Firma", "Rok", "NIP Odbiorcy", "Brutto", "Kwota Rok Następny",
            "Kwota Praca", "Kwota Zlecenie", "Trudny", "Wabank", "Skok Dokumentow",
            "Klient Zaakceptowal", "Wygenerowano Rok Następny", "Data Zalatwione","Uwagi"
        };

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(createHeaderCellStyle(workbook));
        }

        // Tworzenie stylu walutowego
        CellStyle currencyStyle = workbook.createCellStyle();
        currencyStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));
// Styl zielonego checkboxa
        CellStyle checkmarkStyle = workbook.createCellStyle();
        checkmarkStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        checkmarkStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setFontName("Wingdings");
        font.setFontHeightInPoints((short) 12);
        checkmarkStyle.setFont(font);

        // Populate data rows
        int rowNum = 1;
        for (Fakturywystokresowe faktura : fakturyList) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(faktura.getId() != null ? faktura.getId() : 0);
            row.createCell(1).setCellValue(faktura.getPodid() != null ? faktura.getPodid().getPrintnazwa() : "");
            row.createCell(2).setCellValue(faktura.getRok() != null ? faktura.getRok() : "");
            row.createCell(3).setCellValue(faktura.getNipodbiorcy() != null ? faktura.getNipodbiorcy() : "");

            // Komórki walutowe
            Cell bruttoCell = row.createCell(4);
            bruttoCell.setCellValue(faktura.getBrutto() != null ? faktura.getBrutto() : 0.0);
            bruttoCell.setCellStyle(currencyStyle);

            Cell kwotaRokNastepnyCell = row.createCell(5);
            kwotaRokNastepnyCell.setCellValue(faktura.getKwotaroknastepny());
            kwotaRokNastepnyCell.setCellStyle(currencyStyle);

            Cell kwotaPracaCell = row.createCell(6);
            kwotaPracaCell.setCellValue(faktura.getKwotapraca());
            kwotaPracaCell.setCellStyle(currencyStyle);

            Cell kwotaZlecenieCell = row.createCell(7);
            kwotaZlecenieCell.setCellValue(faktura.getKwotazlecenie());
            kwotaZlecenieCell.setCellStyle(currencyStyle);

            // Komórki boolean z checkboxem
            createCheckboxCell(row.createCell(8), faktura.isTrudny(), checkmarkStyle);
            createCheckboxCell(row.createCell(9), faktura.isWabank(), checkmarkStyle);
            createCheckboxCell(row.createCell(10), faktura.isSkokdokumentow(), checkmarkStyle);
            createCheckboxCell(row.createCell(11), faktura.isKlientzaakceptowal(), checkmarkStyle);
            createCheckboxCell(row.createCell(12), faktura.isWygenerowanoroknastepny(), checkmarkStyle);

            row.createCell(14).setCellValue(faktura.getUwagi() != null ? faktura.getUwagi() : "");
            row.createCell(13).setCellValue(faktura.getDatazalatwione() != null ? faktura.getDatazalatwione().toString() : "");
        }

        // Adjust column widths for better readability
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to file
        try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH)) {
            //workbook.write(fileOut);
            System.out.println("Export to Excel completed successfully!");
            String filename = "taxmanokresowe.xlsx";
            // Inform JSF that response is completed and it thus doesn't have to navigate.
            downloadExcel(workbook);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Funkcja pomocnicza do tworzenia komórek z checkboxem
private static void createCheckboxCell(Cell cell, boolean value, CellStyle checkmarkStyle) {
    if (value) {
        cell.setCellValue("\u2713"); // Unicode dla checkmarka
        cell.setCellStyle(checkmarkStyle);
    } else {
        cell.setCellValue(""); // Pusta komórka dla false
    }
}
    
    public static void downloadExcel(Workbook workbook) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        try {
            // Ustawienia nagłówków odpowiedzi
            externalContext.setResponseContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"plik.xlsx\"");

            // Pobranie strumienia wyjściowego i zapisanie Workbook do strumienia
            OutputStream outputStream = externalContext.getResponseOutputStream();
            workbook.write(outputStream);

            outputStream.flush();  // Wypchnięcie zawartości do przeglądarki
            facesContext.responseComplete();  // Zakończenie odpowiedzi
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Zamknij Workbook, aby zwolnić zasoby
            try {
                workbook.close();  // Zamyka Workbook
            } catch (IOException e) {
                e.printStackTrace();
            }
            facesContext.responseComplete(); // Na wszelki wypadek kończy odpowiedź
        }
    }

    // Helper method to create header cell style
    private static CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    public static void main(String[] args) {
        // Sample usage with a list of Fakturywystokresowe
//        List<Fakturywystokresowe> fakturyList = fetchData(); // Assume this method fetches data
        //exportToExcel(fakturyList, "Fakturywystokresowe.xlsx");
    }
}
