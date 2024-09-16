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
import formatpdf.F;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import msg.Msg;
import org.primefaces.PrimeFaces;
import static pdf.PdfVAT7.absText;
import pdffk.PdfMain;
import static pdffk.PdfMain.inicjacjaA4Landscape;
import plik.Plik;
import slownie.Slownie;
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
        if (fakturaRozrachunki.isZaplata0korekta1()==false) {
            pokwitowanieKP(fakturaRozrachunki, wpisView, writer,760);
            absText(writer, "....................................................................................................................................................", 60, 450, 10);
            pokwitowanieKP(fakturaRozrachunki, wpisView, writer,400);
        } else {
            pokwitowanieKW(fakturaRozrachunki, wpisView, writer,760);
            absText(writer, "....................................................................................................................................................", 60, 450, 10);
            pokwitowanieKW(fakturaRozrachunki, wpisView, writer,400);
        }
        document.close();
        PdfMain.dodajQR(nazwapliku);
        PrimeFaces.current().executeScript("pokazwydruk('"+nazwapliku+"');");
        Msg.msg("i", "Wydrukowano kw.kp");
    }
    
    public static void pokwitowanieKP(FakturaRozrachunki fakturaRozrachunki, WpisView wpisView, PdfWriter writer, int h) {
        try {
            String firma = wpisView.getPodatnikObiekt().getPrintnazwa();
            Podatnik pod = wpisView.getPodatnikObiekt();
            String ulica = pod.getUlica() + " " + pod.getNrdomu();
            String miejscowosc = pod.getKodpocztowy()+" " + pod.getMiejscowosc();
            String nip = "NIP: " + pod.getNip();
            int w1 = 250;
            int w23 = 120;
            int p1 = 60;
            int p2 = p1+w1;
            int p3 = p2+w23;
            int hw1 = 70;
            int hw2 = 26;
            int hw3 = 14;
            PdfPTable table = wygenerujtabliceWystawcaOdbiorca(firma,ulica, miejscowosc, nip, w1, hw1, 10);
            table.writeSelectedRows(0, table.getRows().size(), p1, h, writer.getDirectContent());
            String miejsce = "Szczecin, dn. "+fakturaRozrachunki.getData();
            table = wygenerujtabliceLeft(miejsce, w23, hw1, 10);
            table.writeSelectedRows(0, table.getRows().size(), p2, h, writer.getDirectContent());
            String wplatawyplata = "KP - WPŁATA, nr "+fakturaRozrachunki.getNrdokumentu();
            table = wygenerujtabliceLeft(wplatawyplata, w23, hw1, 10);
            table.writeSelectedRows(0, table.getRows().size(), p3, h, writer.getDirectContent());
            h = h-hw1;
            String odkogo = "Od kogo: "+fakturaRozrachunki.getWplacajacy();
            table = wygenerujtabliceLeft(odkogo, w1, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p1, h, writer.getDirectContent());
            String kasawn = "Kasa Wn";
            table = wygenerujtabliceLeft(kasawn, w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p2, h, writer.getDirectContent());
            String kontoma = "Konto Ma";
            table = wygenerujtabliceLeft(kontoma, w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p3, h, writer.getDirectContent());
            h = h-hw2;
            String zaco = "za co";
            table = wygenerujtabliceCenter(zaco, w1, hw3, 10);
            table.writeSelectedRows(0, table.getRows().size(), p1, h, writer.getDirectContent());
            String pln = "pln";
            table = wygenerujtabliceCenter(pln, w23, hw3, 10);
            table.writeSelectedRows(0, table.getRows().size(), p2, h, writer.getDirectContent());
            String numer = "numer";
            table = wygenerujtabliceCenter(numer, w23, hw3, 10);
            table.writeSelectedRows(0, table.getRows().size(), p3, h, writer.getDirectContent());
            h = h-hw3;
            String nazwa = fakturaRozrachunki.getKontrahent().getNpelna()+" "+fakturaRozrachunki.getUwagi();
            table = wygenerujtabliceCenter(nazwa, w1, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p1, h, writer.getDirectContent());
            double kwota = fakturaRozrachunki.getKwotapln();
            table = wygenerujtabliceCenter(F.curr(kwota), w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p2, h, writer.getDirectContent());
            String konto = "";
            table = wygenerujtabliceCenter(konto, w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p3, h, writer.getDirectContent());
            //        h = h-hw2;
            //        pustewiersze("", writer, h, w1, w23, p1, p2, p3, hw2);
            h = h-hw2;
            pustewiersze("", writer, h, w1, w23, p1, p2, p3, hw2);
            h = h-hw2;
            String slownie = "kwota wpłaty słownie: "+Slownie.slownie(String.valueOf(fakturaRozrachunki.getKwotapln()),"zł");
            table = wygenerujtabliceLeft(slownie, w1+w23+w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p1, h, writer.getDirectContent());
            h = h-hw2;
            String wys = "wystawił";
            table = wygenerujtabliceCenter(wys, w1, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p1, h, writer.getDirectContent());
            String spr = "sprawdził";
            table = wygenerujtabliceCenter(spr, w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p2, h, writer.getDirectContent());
            String zatw = "zatwierdził";
            table = wygenerujtabliceCenter(zatw, w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p3, h, writer.getDirectContent());
            h = h-hw2;
            pustewiersze(wpisView.getUzer().getNazwiskoImie(), writer, h, w1, w23, p1, p2, p3, hw2);
            h = h-hw2;
            String potw = "kwotę powyższą otrzymałem - podpis kasjera";
            table = wygenerujtabliceCenter(potw, w1+w23+w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p1, h, writer.getDirectContent());
            h = h-hw2;
            table = wygenerujtabliceCenter("", w1+w23+w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p1, h, writer.getDirectContent());
        } catch (DocumentException ex) {
            Logger.getLogger(PdfRaportKasowy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PdfRaportKasowy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void pokwitowanieKW(FakturaRozrachunki fakturaRozrachunki, WpisView wpisView, PdfWriter writer, int h) {
        try {
            String firma = wpisView.getPodatnikObiekt().getPrintnazwa();
            Podatnik pod = wpisView.getPodatnikObiekt();
            String ulica = pod.getUlica() + " " + pod.getNrdomu();
            String miejscowosc = pod.getKodpocztowy()+" " + pod.getMiejscowosc();
            String nip = "NIP: " + pod.getNip();
            int w1 = 250;
            int w23 = 120;
            int p1 = 60;
            int p2 = p1+w1;
            int p3 = p2+w23;
            int hw1 = 70;
            int hw2 = 26;
            int hw3 = 14;
            PdfPTable table = wygenerujtabliceWystawcaOdbiorca(firma,ulica, miejscowosc, nip, w1, hw1, 10);
            table.writeSelectedRows(0, table.getRows().size(), p1, h, writer.getDirectContent());
            String miejsce = "Szczecin, dn. "+fakturaRozrachunki.getData();
            table = wygenerujtabliceLeft(miejsce, w23, hw1, 10);
            table.writeSelectedRows(0, table.getRows().size(), p2, h, writer.getDirectContent());
            String wplatawyplata = "KW - WYPŁATA, nr "+fakturaRozrachunki.getNrdokumentu();
            table = wygenerujtabliceLeft(wplatawyplata, w23, hw1, 10);
            table.writeSelectedRows(0, table.getRows().size(), p3, h, writer.getDirectContent());
            h = h-hw1;
            String odkogo = "Dla kogo: "+fakturaRozrachunki.getWplacajacy();
            table = wygenerujtabliceLeft(odkogo, w1, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p1, h, writer.getDirectContent());
            String kasawn = "Kasa Ma";
            table = wygenerujtabliceLeft(kasawn, w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p2, h, writer.getDirectContent());
            String kontoma = "Konto Wn";
            table = wygenerujtabliceLeft(kontoma, w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p3, h, writer.getDirectContent());
            h = h-hw2;
            String zaco = "za co";
            table = wygenerujtabliceCenter(zaco, w1, hw3, 10);
            table.writeSelectedRows(0, table.getRows().size(), p1, h, writer.getDirectContent());
            String pln = "pln";
            table = wygenerujtabliceCenter(pln, w23, hw3, 10);
            table.writeSelectedRows(0, table.getRows().size(), p2, h, writer.getDirectContent());
            String numer = "numer";
            table = wygenerujtabliceCenter(numer, w23, hw3, 10);
            table.writeSelectedRows(0, table.getRows().size(), p3, h, writer.getDirectContent());
            h = h-hw3;
            String nazwa = fakturaRozrachunki.getKontrahent().getNpelna()+" "+fakturaRozrachunki.getUwagi();
            table = wygenerujtabliceCenter(nazwa, w1, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p1, h, writer.getDirectContent());
            double kwota = fakturaRozrachunki.getKwotapln();
            table = wygenerujtabliceCenter(F.curr(kwota), w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p2, h, writer.getDirectContent());
            String konto = "";
            table = wygenerujtabliceCenter(konto, w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p3, h, writer.getDirectContent());
            //        h = h-hw2;
            //        pustewiersze("", writer, h, w1, w23, p1, p2, p3, hw2);
            h = h-hw2;
            pustewiersze("", writer, h, w1, w23, p1, p2, p3, hw2);
            h = h-hw2;
            String slownie = "kwota wypłaty słownie: "+Slownie.slownie(String.valueOf(fakturaRozrachunki.getKwotapln()),"zł");
            table = wygenerujtabliceLeft(slownie, w1+w23+w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p1, h, writer.getDirectContent());
            h = h-hw2;
            String wys = "wystawił";
            table = wygenerujtabliceCenter(wys, w1, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p1, h, writer.getDirectContent());
            String spr = "sprawdził";
            table = wygenerujtabliceCenter(spr, w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p2, h, writer.getDirectContent());
            String zatw = "zatwierdził";
            table = wygenerujtabliceCenter(zatw, w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p3, h, writer.getDirectContent());
            h = h-hw2;
            pustewiersze(wpisView.getUzer().getNazwiskoImie(), writer, h, w1, w23, p1, p2, p3, hw2);
            h = h-hw2;
            String potw = "kwotę powyższą wypłaciłem - podpis kasjera     |      kwotę otrzymałem";
            table = wygenerujtabliceCenter(potw, w1+w23+w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p1, h, writer.getDirectContent());
            h = h-hw2;
            table = wygenerujtabliceCenter("", w1+w23+w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p1, h, writer.getDirectContent());
        } catch (DocumentException ex) {
            Logger.getLogger(PdfRaportKasowy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PdfRaportKasowy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void pustewiersze(String tekst1, PdfWriter writer, int vert, int w1, int w23, int p1, int p2, int p3, int hw2) {
        try {
            PdfPTable table = wygenerujtabliceCenter(tekst1, w1, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p1, vert, writer.getDirectContent());
            table = wygenerujtabliceCenter("", w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p2, vert, writer.getDirectContent());
            table = wygenerujtabliceCenter("", w23, hw2, 10);
            table.writeSelectedRows(0, table.getRows().size(), p3, vert, writer.getDirectContent());
        } catch (DocumentException ex) {
            Logger.getLogger(PdfRaportKasowy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PdfRaportKasowy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public static PdfPTable wygenerujtabliceLeft(String text, int szerokosc, int wysokosc, int czcionkasize) throws DocumentException, IOException {
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
