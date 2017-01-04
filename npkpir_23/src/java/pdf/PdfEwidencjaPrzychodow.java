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
import java.util.logging.Level;
import java.util.logging.Logger;
import msg.Msg;
import org.primefaces.context.RequestContext;
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
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        Font font = new Font(helvetica, 8);
        pdf.setPageSize(PageSize.A4);
        PdfPTable table = generujTabele(wpisView,mc);
        dodajwiersze(wykaz, table);
        pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
        pdf.add(table);
        pdf.addAuthor("Biuro Rachunkowe Taxman");
        pdf.close();
        RequestContext.getCurrentInstance().execute("wydrukpkpir('"+wpisView.getPodatnikWpisu().trim()+"');");
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
        List<String> mce = new ArrayList<>();
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
        RequestContext.getCurrentInstance().execute("wydrukpkpir('"+wpisView.getPodatnikWpisu().trim()+"');");
        Msg.msg("i", "Wydrukowano księgę", "form:messages");
    }
    
    private static PdfPTable generujTabele(WpisView wpisView, String mc) {
        PdfPTable table = new PdfPTable(13);
        try {
            table.setWidths(new int[]{1, 2, 2, 4, 4, 2, 2, 2, 2, 2, 2, 2, 2});
            PdfPCell cell = new PdfPCell();
            table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 4, 0));
            table.addCell(ustawfraze("wydruk ewidencji przychodów", 3, 0));
            table.addCell(ustawfraze("firma: " + wpisView.getPodatnikWpisu(), 4, 0));
            table.addCell(ustawfraze("za okres: " + wpisView.getRokWpisu() + "/" + mc, 2, 0));
            table.addCell(ustawfraze("lp", 0, 2));
            table.addCell(ustawfraze("Data zdarzenia gosp.", 0, 2));
            table.addCell(ustawfraze("Nr dowodu księgowego", 0, 2));
            table.addCell(ustawfraze("Kontrahent", 2, 0));
            table.addCell(ustawfraze("Przychody wg stawek", 5, 0));
            table.addCell(ustawfraze("Razem przychód (5+6+7+8+9)", 0, 2));
            table.addCell(ustawfraze("Uwagi", 0, 2));
            table.addCell(ustawfraze("Kwota przychodu wg stawki", 1, 0));
            table.addCell(ustawfrazeAlign("imię i nazwisko (firma)", "center", 6));
            table.addCell(ustawfrazeAlign("adres", "center", 6));
            table.addCell(ustawfrazeAlign("20%", "center", 6));
            table.addCell(ustawfrazeAlign("17%", "center", 6));
            table.addCell(ustawfrazeAlign("8.5%", "center", 6));
            table.addCell(ustawfrazeAlign("5.5%", "center", 6));
            table.addCell(ustawfrazeAlign("3%", "center", 6));
            table.addCell(ustawfrazeAlign("10%", "center", 6));
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
            table.setHeaderRows(5);
            table.setFooterRows(1);
        } catch (DocumentException ex) {
            Logger.getLogger(PdfPkpir.class.getName()).log(Level.SEVERE, null, ex);
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
            table.addCell(ustawfrazeAlign(rs.getDataWyst(), "left",6));
            table.addCell(ustawfrazeAlign(rs.getNrWlDk(), "left",6));
            table.addCell(ustawfrazeAlign(rs.getKontr().getNpelna(), "left",6));
            if (rs.getKontr().getKodpocztowy() != null) {
                table.addCell(ustawfrazeAlign(rs.getKontr().getKodpocztowy() + " " + rs.getKontr().getMiejscowosc() + " ul. " + rs.getKontr().getUlica() + " " + rs.getKontr().getDom(), "left",6));
            } else {
                table.addCell(ustawfrazeAlign("podsumowanie", "center",6));
            }
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna5() != 0.0 ? rs.getKolumna5() : null), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna6() != 0.0 ? rs.getKolumna6() : null), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna7() != 0.0 ? rs.getKolumna7() : null), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna8() != 0.0 ? rs.getKolumna8() : null), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna9() != 0.0 ? rs.getKolumna9() : null), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna11() != 0.0 ? rs.getKolumna11() : null), "right",6));
            table.addCell(ustawfrazeAlign(rs.getUwagi(), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna10() != 0.0 ? rs.getKolumna10() : null), "right",6));
        }
    }
      
}
