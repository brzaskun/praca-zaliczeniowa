/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import static beansPdf.PdfFont.formatujWaluta;
import static beansPdf.PdfFont.ustawfraze;
import static beansPdf.PdfFont.ustawfrazeAlign;
import beansPdf.PdfHeaderFooter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Faktura;
import error.E;
import java.awt.GraphicsEnvironment;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import msg.Msg;import plik.Plik;
import view.WpisView; import org.primefaces.PrimeFaces;
import waluty.Z;


/**
 *
 * @author Osito
 */

public class PdfFakturySporzadzone {

    public static void drukujzapisy(WpisView wpisView, List<Faktura> wybranefaktury) throws DocumentException, FileNotFoundException, IOException {
        try {
            Document document = new Document(PageSize.A4_LANDSCAPE.rotate(), 0, 0, 40, 25);
            PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR("fakturysporzadzone-" + wpisView.getPodatnikWpisu() + ".pdf"));
            int liczydlo = 1;
            PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
            writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
            writer.setPageEvent(headerfoter);
            document.addTitle("Zestawienie sporządzonych faktur sprzedaży za  " + wpisView.getRokWpisuSt() + "/" + wpisView.getMiesiacWpisu());
            document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
            document.addSubject("Zestawienie sporządzonych faktur");
            document.addKeywords("VAT, PDF");
            document.addCreator("Grzegorz Grzelczyk");
            document.open();
            BaseFont helvetica = null;
            try {
                helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            } catch (IOException ex) {
                Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
            }
            document.setPageSize(PageSize.A4);
            PdfPTable table = new PdfPTable(14);
            table.setWidths(new int[]{1, 2, 4, 4, 2, 3, 3, 2, 2, 2, 2, 2, 2, 1});
            table.setWidthPercentage(98);
            table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 4, 0));
            table.addCell(ustawfraze("wydruk zestawienie sporządzonych faktur sprzedaży", 3, 0));
            table.addCell(ustawfraze("firma: " + wpisView.getPrintNazwa(), 5, 0));
            table.addCell(ustawfraze("za okres: " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt(), 2, 0));
            table.addCell(ustawfraze("lp", 0, 1));
            table.addCell(ustawfraze("nr własny", 0, 1));
            table.addCell(ustawfraze("kontrahent", 0, 1));
            table.addCell(ustawfraze("adres", 0, 1));
            table.addCell(ustawfraze("nip", 0, 1));
            table.addCell(ustawfraze("opis", 0, 1));
            table.addCell(ustawfraze("zamówienie", 0, 1));
            table.addCell(ustawfraze("data sprzed.", 0, 1));
            table.addCell(ustawfraze("data wyst.", 0, 1));
            table.addCell(ustawfraze("termin płat.", 0, 1));
            table.addCell(ustawfraze("netto", 0, 1));
            table.addCell(ustawfraze("vat", 0, 1));
            table.addCell(ustawfraze("brutto", 0, 1));
            table.addCell(ustawfraze("uwagi", 0, 1));
            table.addCell(ustawfrazeAlign("1", "center", 6));
            table.addCell(ustawfrazeAlign("2", "center", 6));
            table.addCell(ustawfrazeAlign("3", "center", 6));
            table.addCell(ustawfrazeAlign("4", "center", 6));
            table.addCell(ustawfrazeAlign("5", "center", 6));
            table.addCell(ustawfrazeAlign("6", "center", 6));
            table.addCell(ustawfrazeAlign("7", "center", 6));
            table.addCell(ustawfrazeAlign("8", "center", 6));
            table.addCell(ustawfrazeAlign("9", "center", 6));
            table.addCell(ustawfrazeAlign("10", "center", 6));
            table.addCell(ustawfrazeAlign("11", "center", 6));
            table.addCell(ustawfrazeAlign("12", "center", 6));
            table.addCell(ustawfrazeAlign("13", "center", 6));
            table.addCell(ustawfrazeAlign("14", "center", 6));
            table.addCell(ustawfrazeAlign("1", "center", 6));
            table.addCell(ustawfrazeAlign("2", "center", 6));
            table.addCell(ustawfrazeAlign("3", "center", 6));
            table.addCell(ustawfrazeAlign("4", "center", 6));
            table.addCell(ustawfrazeAlign("5", "center", 6));
            table.addCell(ustawfrazeAlign("6", "center", 6));
            table.addCell(ustawfrazeAlign("7", "center", 6));
            table.addCell(ustawfrazeAlign("8", "center", 6));
            table.addCell(ustawfrazeAlign("9", "center", 6));
            table.addCell(ustawfrazeAlign("10", "center", 6));
            table.addCell(ustawfrazeAlign("11", "center", 6));
            table.addCell(ustawfrazeAlign("12", "center", 6));
            table.addCell(ustawfrazeAlign("13", "center", 6));
            table.addCell(ustawfrazeAlign("14", "center", 6));
            table.setHeaderRows(3);
            table.setFooterRows(1);
            Integer i = 1;
            if (wybranefaktury.size() == 0) {
                table.addCell(ustawfrazeAlign("", "center", 7));
                table.addCell(ustawfrazeAlign("", "center", 7));
                table.addCell(ustawfrazeAlign("nie wybrano faktur do wydruku", "center", 7));
                table.addCell(ustawfrazeAlign("", "center", 7));
                table.addCell(ustawfrazeAlign("", "center", 7));
                table.addCell(ustawfrazeAlign("", "center", 7));
                table.addCell(ustawfrazeAlign("", "center", 7));
                table.addCell(ustawfrazeAlign("", "center", 7));
                table.addCell(ustawfrazeAlign("", "center", 7));
                table.addCell(ustawfrazeAlign("", "center", 7));
                table.addCell(ustawfrazeAlign("", "center", 7));
                table.addCell(ustawfrazeAlign("", "center", 7));
                table.addCell(ustawfrazeAlign("", "center", 7));
                table.addCell(ustawfrazeAlign("", "center", 7));
            } else {
                for (Faktura rs : wybranefaktury) {
                    table.addCell(ustawfrazeAlign(i.toString(), "center", 7));
                    table.addCell(ustawfrazeAlign(rs.getNumerkolejny(), "center", 7));
                    table.addCell(ustawfrazeAlign(rs.getKontrahent().getNpelna(), "left", 7));
                    table.addCell(ustawfrazeAlign(rs.getKontrahent().getAdres(), "left", 7));
                    table.addCell(ustawfrazeAlign(rs.getKontrahent_nip(), "center", 7));
                    String opis = rs.getNazwa() != null ? rs.getNazwa() : rs.getPozycjenafakturze().get(0).getNazwa();
                    table.addCell(ustawfrazeAlign(opis, "left", 6));
                    table.addCell(ustawfrazeAlign(rs.getNumerzamowienia(), "left", 6));
                    table.addCell(ustawfrazeAlign(rs.getDatasprzedazy(), "center", 6));
                    table.addCell(ustawfrazeAlign(rs.getDatawystawienia(), "center", 6));
                    table.addCell(ustawfrazeAlign(rs.getTerminzaplaty(), "center", 6));
                    double netto = rs.getNetto();
                    double vat = rs.getVat();
                    double brutto = rs.getBrutto();
                    if (rs.getPozycjepokorekcie() != null) {
                        netto = Z.z(rs.getNettopk() - rs.getNetto());
                        vat = Z.z(rs.getVatpk() - rs.getVat());
                        brutto = Z.z(rs.getBruttopk() - rs.getBrutto());
                    }
                    table.addCell(ustawfrazeAlign(formatujWaluta(netto), "right", 7));
                    table.addCell(ustawfrazeAlign(formatujWaluta(vat), "right", 7));
                    table.addCell(ustawfrazeAlign(formatujWaluta(brutto), "right", 7));
                    table.addCell(ustawfrazeAlign("", "right", 7));
                    i++;
                }
            }
            document.setPageSize(PageSize.A4_LANDSCAPE.rotate());
            document.add(table);
            document.close();
            String funkcja = "wydrukfakturysporzadzone('" + wpisView.getPodatnikWpisu() + "');";
            PrimeFaces.current().executeScript(funkcja);
            Msg.msg("i", "Wydrukowano zestawienie wybranych faktur");
        } catch (Exception e) {
            E.e(e);
        }
    }

//    public static void main(String[] args) {
//        try {
//            Document document = new Document(PageSize.A4_LANDSCAPE.rotate(), 0, 0, 40, 5);
//            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("d:/zapiskonto.pdf"));
//            document.addTitle("Zapisy na koncie");
//            document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
//            document.addSubject("Wydruk zapisów na koncie");
//            document.addKeywords("VAT, PDF");
//            document.addCreator("Grzegorz Grzelczyk");
//            document.open();
//            BaseFont helvetica = null;
//            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.WHITE);
//            java.awt.Font[] fontsa = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
//            //Integer name = fontsa["Calibri"];
//            font = new Font(helvetica, 8);
//            document.setPageSize(PageSize.A4);
//            PdfPTable table = new PdfPTable(13);
//            table.setWidths(new int[]{1, 2, 2, 1, 4, 2, 2, 2, 2, 2, 2, 1, 2});
//            table.setWidthPercentage(98);
//            try {
//                table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 3, 0, fontsa));
//                table.addCell(ustawfraze("wydruk zapisów na koncie ", 3, 0));
//                table.addCell(ustawfraze("firma: nazwafirmy", 5, 0));
//                table.addCell(ustawfraze("za okres: 12/2015", 2, 0));
//
//                table.addCell(ustawfraze("lp", 0, 1));
//                table.addCell(ustawfraze("Data zdarzenia gosp.", 0, 1));
//                table.addCell(ustawfraze("Nr dowodu księgowego", 0, 1));
//                table.addCell(ustawfraze("Wiersz", 0, 1));
//                table.addCell(ustawfraze("Opis zdarzenia gospodarcz", 0, 1));
//                table.addCell(ustawfraze("Kurs", 0, 1));
//                table.addCell(ustawfraze("Tabela", 0, 1));
//                table.addCell(ustawfraze("Wn", 0, 1));
//                table.addCell(ustawfraze("Wn PLN", 0, 1));
//                table.addCell(ustawfraze("Ma", 0, 1));
//                table.addCell(ustawfraze("Ma PLN", 0, 1));
//                table.addCell(ustawfraze("Waluta", 0, 1));
//                table.addCell(ustawfraze("Konto przec.", 0, 1));
//
//                table.addCell(ustawfrazeAlign("1", "center", 6));
//                table.addCell(ustawfrazeAlign("2", "center", 6));
//                table.addCell(ustawfrazeAlign("3", "center", 6));
//                table.addCell(ustawfrazeAlign("4", "center", 6));
//                table.addCell(ustawfrazeAlign("5", "center", 6));
//                table.addCell(ustawfrazeAlign("6", "center", 6));
//                table.addCell(ustawfrazeAlign("7", "center", 6));
//                table.addCell(ustawfrazeAlign("8", "center", 6));
//                table.addCell(ustawfrazeAlign("9", "center", 6));
//                table.addCell(ustawfrazeAlign("10", "center", 6));
//                table.addCell(ustawfrazeAlign("11", "center", 6));
//                table.addCell(ustawfrazeAlign("12", "center", 6));
//                table.addCell(ustawfrazeAlign("13", "center", 6));
//
//                table.addCell(ustawfrazeAlign("1", "center", 6));
//                table.addCell(ustawfrazeAlign("2", "center", 6));
//                table.addCell(ustawfrazeAlign("3", "center", 6));
//                table.addCell(ustawfrazeAlign("4", "center", 6));
//                table.addCell(ustawfrazeAlign("5", "center", 6));
//                table.addCell(ustawfrazeAlign("6", "center", 6));
//                table.addCell(ustawfrazeAlign("7", "center", 6));
//                table.addCell(ustawfrazeAlign("8", "center", 6));
//                table.addCell(ustawfrazeAlign("9", "center", 6));
//                table.addCell(ustawfrazeAlign("10", "center", 6));
//                table.addCell(ustawfrazeAlign("11", "center", 6));
//                table.addCell(ustawfrazeAlign("12", "center", 6));
//                table.addCell(ustawfrazeAlign("13", "center", 6));
//
//                table.setHeaderRows(3);
//                table.setFooterRows(1);
//            } catch (Exception e) {
//
//            }
//            document.setPageSize(PageSize.A4_LANDSCAPE.rotate());
//            document.add(table);
//            document.close();
//        } catch (Exception e) {
//
//        }
//    }

}
