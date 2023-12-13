/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import beansPdf.PdfFTablice;
import static beansPdf.PdfFont.ustawfrazeAlign;
import static beansPdf.PdfFont.ustawfrazeAlignBold;
import beansPdf.PdfHeaderFooter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.FakturaRozrachunki;
import entity.Podatnik;
import java.io.FileNotFoundException;
import java.io.IOException;
import msg.Msg;
import org.primefaces.PrimeFaces;
import pdffk.PdfMain;
import static pdffk.PdfMain.inicjacjaA4Landscape;
import plik.Plik;
import view.WpisView;

/**
 *
 * @author Osito
 */
public class PdfRaportKasowy {
    public static void drukuj(FakturaRozrachunki fakturaRozrachunki, WpisView wpisView)  throws DocumentException, FileNotFoundException, IOException{
        Document document = PdfMain.inicjacjaA4Portrait(60,20);
        String nazwapliku = "kpkw" + wpisView.getPodatnikWpisu().trim();
        PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR(nazwapliku+".pdf"));
        int liczydlo = 1;
        PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
        writer.setBoxSize("art", new Rectangle(500, 800, 40, 60));
        writer.setPageEvent(headerfoter);
        document.addTitle("Raport kasowy");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk danych raport kasowy");
        document.addKeywords("Raport Kasowy, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        document.addAuthor("Biuro Rachunkowe Taxman");
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(helvetica, 12);
        Font fontM = new Font(helvetica, 10);
        Font fontS = new Font(helvetica, 6);
        String firma = wpisView.getPodatnikObiekt().getPrintnazwa();
        Podatnik pod = wpisView.getPodatnikObiekt();
        String ulica = pod.getUlica() + " " + pod.getNrdomu();
        String miejscowosc = pod.getKodpocztowy()+" " + pod.getMiejscowosc();
        String nip = "NIP: " + pod.getNip();
        PdfPTable table = wygenerujtabliceWystawcaOdbiorca(firma,ulica, miejscowosc, nip, 180, 70, 10);
        table.writeSelectedRows(0, table.getRows().size(), 60, 600, writer.getDirectContent());
        String miejsce = "Szczecin, dn. "+fakturaRozrachunki.getData();
        table = wygenerujtabliceMiejscowosc(miejsce, 140, 70, 10);
        table.writeSelectedRows(0, table.getRows().size(), 240, 600, writer.getDirectContent());
        String wplatawyplata = "KP - WPŁATA, nr "+fakturaRozrachunki.getNrdokumentu();
        table = wygenerujtabliceMiejscowosc(wplatawyplata, 140, 70, 10);
        table.writeSelectedRows(0, table.getRows().size(), 380, 600, writer.getDirectContent());
        String odkogo = "Od kogo: "+fakturaRozrachunki.getKontrahent().getNpelna();
        table = wygenerujtabliceMiejscowosc(odkogo, 180, 30, 10);
        table.writeSelectedRows(0, table.getRows().size(), 60, 530, writer.getDirectContent());
        String kasawn = "Kasa Wn";
        table = wygenerujtabliceMiejscowosc(kasawn, 140, 30, 10);
        table.writeSelectedRows(0, table.getRows().size(), 240, 530, writer.getDirectContent());
        String kontoma = "Konto Ma";
        table = wygenerujtabliceMiejscowosc(kontoma, 140, 30, 10);
        table.writeSelectedRows(0, table.getRows().size(), 380, 530, writer.getDirectContent());
        String zaco = "za co";
        table = wygenerujtabliceCenter(zaco, 180, 20, 10);
        table.writeSelectedRows(0, table.getRows().size(), 60, 500, writer.getDirectContent());
        String pln = "pln";
        table = wygenerujtabliceCenter(pln, 140, 20, 10);
        table.writeSelectedRows(0, table.getRows().size(), 240, 500, writer.getDirectContent());
        String numer = "numer";
        table = wygenerujtabliceCenter(numer, 140, 20, 10);
        table.writeSelectedRows(0, table.getRows().size(), 380, 500, writer.getDirectContent());
        document.close();
        PdfMain.dodajQR(nazwapliku);
        PrimeFaces.current().executeScript("pokazwydruk('"+nazwapliku+"');");
        Msg.msg("i", "Wydrukowano kw.kp");
    }
    
     public static PdfPTable wygenerujtabliceMiejscowosc(String text, int szerokosc, int wysokosc, int czcionkasize) throws DocumentException, IOException {
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
     
      public static PdfPTable wygenerujtabliceCenter(String text, int szerokosc, int wysokosc, int czcionkasize) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(1);
        float x1 = (float) (szerokosc);
        table.setTotalWidth(x1);
        table.setLockedWidth(true);
        table.getDefaultCell().setBorder(Rectangle.ALIGN_CENTER);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setPaddingLeft(5);
        table.addCell(ustawfrazeAlign(text, "center", czcionkasize, wysokosc, 10f));
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
}
