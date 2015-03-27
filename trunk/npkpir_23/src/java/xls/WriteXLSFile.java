/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Osito
 */
public class WriteXLSFile {
    private static final String FILE_PATH = "c://temp//xlsfile.xlsx";
    //We are making use of a single instance to prevent multiple write access to same file.
    private static final WriteXLSFile INSTANCE = new WriteXLSFile();

    public static WriteXLSFile getInstance() {
        return INSTANCE;
    }

    private WriteXLSFile() {
    }

    public static void main(String args[]){

        List studentList = new ArrayList();
        studentList.add(new Student("Magneto",90.23,"100","80"));
        studentList.add(new Student("Wolverine",1234.32,"60","90"));
        studentList.add(new Student("ProfX",12563521.33,"100","100"));
        List headersList = new ArrayList();
        headersList.add("Nazwisko");
        headersList.add("Maths");
        headersList.add("Science");
        headersList.add("English");
        writeStudentsListToExcel(headersList, studentList);

    }

    public static void writeStudentsListToExcel(List<String> headersList, List<Student> studentList){

        // Using XSSF for xlsx format, for xls use HSSF
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Students");
        insertPrintHeader(sheet);
        drawATable(workbook, sheet, headersList, studentList);
        //write this workbook in excel file.
        try {
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            workbook.write(fos);
            fos.close();

            System.out.println(FILE_PATH + " is successfully written");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    
    private static <T> void drawATable(Workbook workbook, Sheet sheet, List headers, List<T> elements) {
        int rowIndex = 0;
        int cellIndex = 0;
        
        Row rowH = sheet.createRow(rowIndex++);
        for(Iterator it = headers.iterator(); it.hasNext();){
            String header = (String) it.next();
            createHeaderCell(workbook, rowH, (short) cellIndex++, CellStyle.ALIGN_CENTER, CellStyle.ALIGN_CENTER, header);
        }
        for(Iterator it = elements.iterator(); it.hasNext();){
            T st = (T) it.next();
            Row row = sheet.createRow(rowIndex++);
            cellIndex = 0;
            ustawWiersz(workbook, row, cellIndex, st);
        //first place in row is name
            
        }
        String formula = "SUM(B2:B"+rowIndex+")";
        Row row = sheet.createRow(rowIndex++);
        createFormulaCell(workbook, row, (short) 1, CellStyle.ALIGN_RIGHT, CellStyle.VERTICAL_CENTER, formula);
        autoAlign(sheet);
    }
    
    private static <T> void ustawWiersz(Workbook workbook, Row row, int cellIndex, T ob) {
        Class c = ob.getClass();
        if (c.getName().contains("Student")) {
            Student st = (Student) ob;
            createCell(workbook, row, (short) cellIndex++, CellStyle.ALIGN_RIGHT, CellStyle.VERTICAL_CENTER, st.getName());
            createCell(workbook, row, (short) cellIndex++, CellStyle.ALIGN_RIGHT, CellStyle.VERTICAL_CENTER, st.getMaths());
            createCell(workbook, row, (short) cellIndex++, CellStyle.ALIGN_RIGHT, CellStyle.VERTICAL_CENTER, st.getScience());
            createCell(workbook, row, (short) cellIndex++, CellStyle.ALIGN_RIGHT, CellStyle.VERTICAL_CENTER, st.getEnglish());
        }
    }
    
    private static void insertPrintHeader(Sheet studentsSheet) {
        //do druku
        Header header = studentsSheet.getHeader();
        header.setCenter("Center Header");
        header.setLeft("Left Header");
        header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") +
                        HSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");

    }
    
    private static void createHeaderCell(Workbook wb, Row row, short column, short halign, short valign, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(new XSSFRichTextString(value));
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setFont(headerfont(wb));
        cell.setCellStyle(cellStyle);
    }
    
    private static void createFormulaCell(Workbook wb, Row row, short column, short halign, short valign, String formula) {
        Cell cell = row.createCell(column);
        addFormula(cell, formula);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat((short) 7);
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cell.setCellStyle(cellStyle);
    }
    
    private static void createCell(Workbook wb, Row row, short column, short halign, short valign, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(new XSSFRichTextString(value));
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cell.setCellStyle(cellStyle);
    }
    private static void createCell(Workbook wb, Row row, short column, short halign, short valign, double value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat((short) 7);
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cell.setCellStyle(cellStyle);
    }
    private static Font headerfont(Workbook wb) {
     // Create a new font and alter it.
        Font font = wb.createFont();
        font.setFontHeightInPoints((short)11);
        font.setFontName("Arial");
        font.setBold(true);
        return font;
    }
    private static void addFormula(Cell cell, String formula) {
        cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
        cell.setCellFormula(formula);
    }
    
    private static void autoAlign(Sheet sheet) {
        sheet.autoSizeColumn((short) 0);
        sheet.autoSizeColumn((short) 1);
        sheet.autoSizeColumn((short) 2);
        sheet.autoSizeColumn((short) 3);
    }
}
