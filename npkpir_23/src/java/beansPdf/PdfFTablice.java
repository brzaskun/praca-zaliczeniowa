/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansPdf;

import static beansPdf.PdfFont.ustawfrazeAlign;
import static beansPdf.PdfFont.ustawfrazeAlignBold;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class PdfFTablice {
    public static PdfPTable wygenerujtabliceNrfaktury(String text, String text1, int szerokosc, int wysokosc, int czcionkasize) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        float x1 = (float) (szerokosc*.4);
        float x2 = (float) (szerokosc*.6);
        table.setTotalWidth(new float[]{x1,x2});
        table.setLockedWidth(true);
        table.getDefaultCell().setBorder(Rectangle.ALIGN_CENTER);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(ustawfrazeAlignBold(text, "left", czcionkasize, wysokosc, 10f));
        table.addCell(ustawfrazeAlignBold(text1, "left", czcionkasize, wysokosc,10f));
        table.completeRow();
        return table;
    }
    
    public static PdfPTable wygenerujtablicePlatnosc(String[] text, String[] text1, String[] text2, String[] text3, int szerokosc, int wysokosc, int czcionkasize) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        float x1 = (float) (szerokosc*.4);
        float x2 = (float) (szerokosc*.6);
        table.setTotalWidth(new float[]{x1,x2});
        table.setLockedWidth(true);
        table.getDefaultCell().setBorder(Rectangle.ALIGN_CENTER);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        PdfPCell cell = ustawfrazeAlign(text[0], "left", czcionkasize, (float)(wysokosc*.25), 10f);
        cell.disableBorderSide(PdfPCell.RIGHT);
        cell.disableBorderSide(PdfPCell.BOTTOM);
        table.addCell(cell);
        cell = ustawfrazeAlign(text[1], "left", czcionkasize, (float)(wysokosc*.25), 10f);
        cell.disableBorderSide(PdfPCell.LEFT);
        cell.disableBorderSide(PdfPCell.BOTTOM);
        table.addCell(cell);
        cell = ustawfrazeAlign(text1[0], "left", czcionkasize, (float)(wysokosc*.25), 10f);
        cell.disableBorderSide(PdfPCell.RIGHT);
        cell.disableBorderSide(PdfPCell.BOTTOM);
        cell.disableBorderSide(PdfPCell.TOP);
        table.addCell(cell);
        cell = ustawfrazeAlign(text1[1], "left", czcionkasize, (float)(wysokosc*.25), 10f);
        cell.disableBorderSide(PdfPCell.LEFT);
        cell.disableBorderSide(PdfPCell.BOTTOM);
        cell.disableBorderSide(PdfPCell.TOP);
        table.addCell(cell);
        cell = ustawfrazeAlign(text2[0], "left", czcionkasize, (float)(wysokosc*.25), 10f);
        cell.disableBorderSide(PdfPCell.RIGHT);
        cell.disableBorderSide(PdfPCell.BOTTOM);
        cell.disableBorderSide(PdfPCell.TOP);
        table.addCell(cell);
        cell = ustawfrazeAlign(text2[1], "left", czcionkasize, (float)(wysokosc*.25), 10f);
        cell.disableBorderSide(PdfPCell.LEFT);
        cell.disableBorderSide(PdfPCell.BOTTOM);
        cell.disableBorderSide(PdfPCell.TOP);
        table.addCell(cell);
        cell = ustawfrazeAlign(text3[0], "left", czcionkasize, (float)(wysokosc*.25), 10f);
        cell.disableBorderSide(PdfPCell.RIGHT);
        cell.disableBorderSide(PdfPCell.TOP);
        table.addCell(cell);
        cell = ustawfrazeAlign(text3[1], "left", czcionkasize, (float)(wysokosc*.25), 10f);
        cell.disableBorderSide(PdfPCell.LEFT);
        cell.disableBorderSide(PdfPCell.TOP);
        table.addCell(cell);
        table.completeRow();
        return table;
    }
    
    public static PdfPTable wygenerujtabliceDozaplaty(String[] text, String[] text1, int szerokosc, int wysokosc, int czcionkasize) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        float x1 = (float) (szerokosc*.4);
        float x2 = (float) (szerokosc*.6);
        table.setTotalWidth(new float[]{x1,x2});
        table.setLockedWidth(true);
        table.getDefaultCell().setBorder(Rectangle.ALIGN_CENTER);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        PdfPCell cell = ustawfrazeAlign(text[0], "left", czcionkasize, (float)(wysokosc*.25), 10f);
        cell.disableBorderSide(PdfPCell.RIGHT);
        cell.disableBorderSide(PdfPCell.BOTTOM);
        table.addCell(cell);
        cell = ustawfrazeAlign(text[1], "left", czcionkasize, (float)(wysokosc*.25), 10f);
        cell.disableBorderSide(PdfPCell.LEFT);
        cell.disableBorderSide(PdfPCell.BOTTOM);
        table.addCell(cell);
        cell = ustawfrazeAlign(text1[0], "left", czcionkasize, (float)(wysokosc*.25), 10f);
        cell.disableBorderSide(PdfPCell.RIGHT);
        cell.disableBorderSide(PdfPCell.TOP);
        table.addCell(cell);
        cell = ustawfrazeAlign(text1[1], "left", czcionkasize, (float)(wysokosc*.25), 10f);
        cell.disableBorderSide(PdfPCell.LEFT);
        cell.disableBorderSide(PdfPCell.TOP);
        table.addCell(cell);
        table.completeRow();
        return table;
    }
    
    public static PdfPTable wygenerujtabliceDaty(String text, int szerokosc, int wysokosc, int czcionkasize) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(1);
        float x1 = (float) (szerokosc);
        table.setTotalWidth(x1);
        table.setLockedWidth(true);
        table.getDefaultCell().setBorder(Rectangle.ALIGN_CENTER);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setPaddingLeft(5);
        table.addCell(ustawfrazeAlign(text, "left", czcionkasize, wysokosc, 10f));
        table.completeRow();
        return table;
    }
    
    public static PdfPTable wygenerujtabliceUwagi(String text, int szerokosc, int wysokosc, int czcionkasize) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(1);
        float x1 = (float) (szerokosc);
        table.setTotalWidth(x1);
        table.setLockedWidth(true);
        table.getDefaultCell().setBorder(Rectangle.ALIGN_CENTER);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setPaddingLeft(5);
        table.addCell(ustawfrazeAlign(text, "left", czcionkasize, wysokosc, 10f));
        table.completeRow();
        return table;
    }
    
    
    public static PdfPTable wygenerujtabliceWystawcaOdbiorca(String text, String text1, String text2, String text3, int szerokosc, int wysokosc, int czcionkasize) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(1);
        float x1 = (float) (szerokosc);
        table.setTotalWidth(x1);
        table.setLockedWidth(true);
        table.getDefaultCell().setBorder(Rectangle.ALIGN_CENTER);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setPaddingLeft(5);
        PdfPCell cell = ustawfrazeAlign(text, "left", czcionkasize, (float)(wysokosc*.25), 10f);
        cell.disableBorderSide(PdfPCell.BOTTOM);
        table.addCell(cell);
        cell = ustawfrazeAlignBold(text1, "left", czcionkasize, (float)(wysokosc*.25), 10f);
        cell.disableBorderSide(PdfPCell.BOTTOM);
        cell.disableBorderSide(PdfPCell.TOP);
        table.addCell(cell);
        cell = ustawfrazeAlign(text2, "left", czcionkasize, (float)(wysokosc*.25), 10f);
        cell.disableBorderSide(PdfPCell.BOTTOM);
        cell.disableBorderSide(PdfPCell.TOP);
        table.addCell(cell);
        cell = ustawfrazeAlign(text3, "left", czcionkasize, (float)(wysokosc*.25), 10f);
        cell.disableBorderSide(PdfPCell.TOP);
        table.addCell(cell);
        table.completeRow();
        return table;
    }
    
    public static void main(String[] args) throws DocumentException, FileNotFoundException, IOException {
        Document document = new Document();
        String filenazwa = "d:\\testowa.pdf";
        BufferedOutputStream fileOutputStream = fileOutputStream = new BufferedOutputStream(new FileOutputStream(filenazwa));
        PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);
        int liczydlo = 1;
        PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
        writer.setBoxSize("art", new Rectangle(800, 830, 0, 0));
        //writer.setPageEvent(headerfoter);
        writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
        //document.setMargins(0, 0, 400, 20);
        document.addTitle("Faktura");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk faktury w formacie pdf");
        document.addKeywords("Faktura, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        try {
            document.setMargins(0, 0, 40, 20);
            String[] ttext = new String[2];
            ttext[0] = "Test1";
            ttext[1] = "Ekstencja Test1";
            PdfPTable table = wygenerujtabliceWystawcaOdbiorca("data sprzedaży: 2012/05","dlaugha ajnjcewce ecvcdfec dececedn qasd qswdiqdnqwdi duidn duquwd firm ato jest","adres firmy", "nip", 250, 80, 8);
            //PdfPTable table = wygenerujtabliceDozaplaty(ttext, ttext,250, 80, 8);
            //document.add(table);
            table.writeSelectedRows(0, table.getRows().size(), 20, 700, writer.getDirectContent());
            document.close();
            writer.close();
 
        } catch (Exception e) {
            document.close();
        }
//        PdfReader reader = new PdfReader(filenazwa);
//        Rectangle pagesize = reader.getPageSizeWithRotation(1);
//        Rectangle mediabox = reader.getPageSize(1);
//        System.out.println("");
    }
}
