/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import static beansPdf.PdfFont.formatujWaluta;
import static beansPdf.PdfFont.ustawfraze;
import static beansPdf.PdfFont.ustawfrazeAlign;
import beansPdf.PdfHeaderFooter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.DokEwidPrzych;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import msg.Msg;
import org.primefaces.PrimeFaces;
import plik.Plik;
import view.WpisView;

/**
 *
 * @author Osito
 */

public class PdfEwidencjaPrzychodow {

    public static void drukujksiege(List<DokEwidPrzych> wykaz, WpisView wpisView, String mc) throws DocumentException, FileNotFoundException, IOException {
        Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), -20, -20, 20, 10);
        PdfWriter writer = PdfWriter.getInstance(pdf, Plik.plikR("pkpir" + wpisView.getPodatnikWpisu().trim() + ".pdf"));
        int liczydlo = 1;
        PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
        writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
        writer.setPageEvent(headerfoter);
        pdf.addTitle("Ewidencja przychodu");
        pdf.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        pdf.addSubject("Wydruk danych z PKPiR");
        pdf.addKeywords("Ryczałt, PDF");
        pdf.addCreator("Grzegorz Grzelczyk");
        pdf.open();
        BaseFont helvetica = null;
        try {
            helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (IOException ex) {
            // Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        Font font = new Font(helvetica, 8);
        pdf.setPageSize(PageSize.A4);
        PdfPTable table = generujTabele(wpisView,mc);
        dodajwiersze(wykaz, table);
        pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
        pdf.add(table);
        pdf.addAuthor("Biuro Rachunkowe Taxman");
        pdf.close();
        PrimeFaces.current().executeScript("wydrukpkpir('"+wpisView.getPodatnikWpisu().trim()+"');");
        Msg.msg("i", "Wydrukowano ewidencję przychodów", "form:messages");
        
        
    }
    
    public static void drukujksiegeRok(Map<String, List<DokEwidPrzych>> ksiegimiesieczne, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), 0, 0, 20, 25);
        String nazwapliku = "pkpir" + wpisView.getPodatnikWpisu().trim() + ".pdf";
        PdfWriter writer = PdfWriter.getInstance(pdf, Plik.plikR(nazwapliku));
        int liczydlo = 1;
        PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
        writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
        writer.setPageEvent(headerfoter);
        pdf.addTitle("Ewidencja przychodu");
        pdf.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        pdf.addSubject("Wydruk danych z PKPiR");
        pdf.addKeywords("Ryczałt, PDF");
        pdf.addCreator("Grzegorz Grzelczyk");
        pdf.open();
        List<String> mce = Collections.synchronizedList(new ArrayList<>());
        mce.addAll(ksiegimiesieczne.keySet());
        Collections.sort(mce);
        for (String p : mce) {
            PdfPTable table = generujTabele(wpisView,p);
            if (ksiegimiesieczne.get(p).size()>1) {
                dodajwiersze(ksiegimiesieczne.get(p), table);
                pdf.add(table);
                pdf.newPage();
            }
        }
        pdf.addAuthor("Biuro Rachunkowe Taxman");
        pdf.close();
        PrimeFaces.current().executeScript("wydrukpkpir('"+wpisView.getPodatnikWpisu().trim()+"');");
        Msg.msg("i", "Wydrukowano księgę", "form:messages");
    }
    
    private static PdfPTable generujTabele(WpisView wpisView, String mc) {
        PdfPTable table = new PdfPTable(17);
        table.setWidthPercentage(92);
        try {
            table.setWidths(new int[]{1, 2, 2, 4, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2});
            PdfPCell cell = new PdfPCell();
            table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 3, 0));
            table.addCell(ustawfraze("wydruk ewidencji przychodów", 2, 0));
            table.addCell(ustawfraze("firma: " + wpisView.getPrintNazwa(), 6, 0));
            table.addCell(ustawfraze("NIP: " + wpisView.getPodatnikObiekt().getNip(), 3, 0));
            table.addCell(ustawfraze("za okres: " + wpisView.getRokWpisu() + "/" + mc, 3, 0));
            table.addCell(ustawfraze("lp", 0, 2));
            table.addCell(ustawfraze("Data zdarzenia gosp.", 0, 2));
            table.addCell(ustawfraze("Nr dowodu księgowego", 0, 2));
            table.addCell(ustawfraze("Kontrahent", 2, 0));
            table.addCell(ustawfraze("Przychody wg stawek", 10, 0));
            table.addCell(ustawfraze("Razem przychód (6-15)", 0, 2));
            table.addCell(ustawfraze("Uwagi", 0, 2));
            table.addCell(ustawfrazeAlign("imię i nazwisko (firma)", "center", 7));
            table.addCell(ustawfrazeAlign("adres", "center", 7));
            table.addCell(ustawfrazeAlign("17%", "center", 7));
            table.addCell(ustawfrazeAlign("15%", "center", 7));
            table.addCell(ustawfrazeAlign("14%", "center", 7));
            table.addCell(ustawfrazeAlign("12.5%", "center", 7));
            table.addCell(ustawfrazeAlign("12%", "center", 7));
            table.addCell(ustawfrazeAlign("10%", "center", 7));
            table.addCell(ustawfrazeAlign("8.5%", "center", 7));
            table.addCell(ustawfrazeAlign("5.5%", "center", 7));
            table.addCell(ustawfrazeAlign("3%", "center", 7));
            table.addCell(ustawfrazeAlign("2%", "center", 7));
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
            table.addCell(ustawfrazeAlign("15", "center", 6));
            table.addCell(ustawfrazeAlign("16", "center", 6));
            table.addCell(ustawfrazeAlign("17", "center", 6));
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
            table.addCell(ustawfrazeAlign("15", "center", 6));
            table.addCell(ustawfrazeAlign("16", "center", 6));
            table.addCell(ustawfrazeAlign("17", "center", 6));
            table.setHeaderRows(5);
            table.setFooterRows(1);
        } catch (DocumentException ex) {
            // Logger.getLogger(PdfPkpir.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }

    public static void dodajwiersze(List<DokEwidPrzych> wykaz,PdfPTable table) {
        for (DokEwidPrzych rs : wykaz) {
            if (rs.getNrWpkpir() != 0) {
                table.addCell(ustawfrazeAlign(String.valueOf(rs.getNrWpkpir()), "center",6));
            } else {
                table.addCell(ustawfrazeAlign("", "center",6));
            }
            table.addCell(ustawfrazeAlign(rs.getDataWyst(), "left",6, 17f));
            table.addCell(ustawfrazeAlign(rs.getNrWlDk(), "left",6));
            table.addCell(ustawfrazeAlign(rs.getKontr().getNpelna(), "left",6));
            if (rs.getKontr().getKodpocztowy() != null) {
                table.addCell(ustawfrazeAlign(rs.getKontr().getKodpocztowy() + " " + rs.getKontr().getMiejscowosc() + " ul. " + rs.getKontr().getUlica() + " " + rs.getKontr().getDom(), "left",6));
            } else {
                table.addCell(ustawfrazeAlign("podsumowanie", "center",6));
            }
//            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna5() != 0.0 ? rs.getKolumna5() : null), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna_17() != 0.0 ? rs.getKolumna_17() : null), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna_15() != 0.0 ? rs.getKolumna_15() : null), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna_14() != 0.0 ? rs.getKolumna_14() : null), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna_125() != 0.0 ? rs.getKolumna_125() : null), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna_12() != 0.0 ? rs.getKolumna_12() : null), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna_10() != 0.0 ? rs.getKolumna_10() : null), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna_85() != 0.0 ? rs.getKolumna_85() : null), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna_55() != 0.0 ? rs.getKolumna_55() : null), "right",7));   
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna_3() != 0.0 ? rs.getKolumna_3() : null), "right",7));   
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna_2() != 0.0 ? rs.getKolumna_2() : null), "right",7));   
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getRazem() != 0.0 ? rs.getRazem() : null), "right",7));   
            table.addCell(ustawfrazeAlign(rs.getUwagi(), "right",6));
            
        }
    }
      
}
