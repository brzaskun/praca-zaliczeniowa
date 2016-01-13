/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import static beansPdf.PdfFont.formatujEuro;
import static beansPdf.PdfFont.formatujWaluta;
import static beansPdf.PdfFont.ustawfraze;
import static beansPdf.PdfFont.ustawfrazeAlign;
import beansPdf.PdfHeaderFooter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.DokKsiega;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import msg.Msg;
import org.primefaces.context.RequestContext;
import plik.Plik;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Stateless
public class PdfPkpir {

    public static void drukujksiege(List<DokKsiega> wykaz, WpisView wpisView, String mc) throws DocumentException, FileNotFoundException, IOException {
        Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), 0, 0, 20, 25);
        String nazwapliku = "pkpir" + wpisView.getPodatnikWpisu().trim() + ".pdf";
        PdfWriter writer = PdfWriter.getInstance(pdf, Plik.plikR(nazwapliku));
        int liczydlo = 1;
        PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
        writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
        writer.setPageEvent(headerfoter);
        pdf.addTitle("Podatkowa księga przychodów i rozchodów");
        pdf.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        pdf.addSubject("Wydruk danych z PKPiR");
        pdf.addKeywords("PKPiR, PDF");
        pdf.addCreator("Grzegorz Grzelczyk");
        pdf.open();
        PdfPTable table = generujTabele(wpisView,mc);
        dodajwiersze(wykaz, table);
        pdf.add(table);
        pdf.addAuthor("Biuro Rachunkowe Taxman");
        pdf.close();
        RequestContext.getCurrentInstance().execute("wydrukpkpir('"+wpisView.getPodatnikWpisu().trim()+"');");
        Msg.msg("i", "Wydrukowano księgę", "form:messages");
    }
    
    public static void drukujksiegeRok(Map<String, List<DokKsiega>> ksiegimiesieczne, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), 0, 0, 20, 25);
        String nazwapliku = "pkpir" + wpisView.getPodatnikWpisu().trim() + ".pdf";
        PdfWriter writer = PdfWriter.getInstance(pdf, Plik.plikR(nazwapliku));
        int liczydlo = 1;
        PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
        writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
        writer.setPageEvent(headerfoter);
        pdf.addTitle("Podatkowa księga przychodów i rozchodów");
        pdf.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        pdf.addSubject("Wydruk danych z PKPiR");
        pdf.addKeywords("PKPiR, PDF");
        pdf.addCreator("Grzegorz Grzelczyk");
        pdf.open();
        List<String> mce = new ArrayList<>();
        mce.addAll(ksiegimiesieczne.keySet());
        Collections.sort(mce);
        for (String p : mce) {
            PdfPTable table = generujTabele(wpisView,p);
            if (ksiegimiesieczne.get(p).size()>3) {
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
        PdfPTable table = new PdfPTable(16);
        try {
            table.setWidthPercentage(95);
            table.setWidths(new int[]{1, 2, 2, 4, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2});
            table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 4, 0));
            table.addCell(ustawfraze("wydruk podatkowej księgi przychodów i rozchodów", 4, 0));
            table.addCell(ustawfraze("firma: " + wpisView.getPodatnikWpisu(), 5, 0));
            table.addCell(ustawfraze("za okres: " + wpisView.getRokWpisu() + "/" + mc, 3, 0));
            table.addCell(ustawfraze("lp", 0, 2));
            table.addCell(ustawfraze("Data zdarzenia gosp.", 0, 2));
            table.addCell(ustawfraze("Nr dowodu księgowego", 0, 2));
            table.addCell(ustawfraze("Kontrahent", 2, 0));
            table.addCell(ustawfraze("Opis zdarzenia gospodarcz", 0, 2));
            table.addCell(ustawfraze("Przychody", 3, 0));
            table.addCell(ustawfraze("Zakup towarów handlowych i materiałów wg cen zakupu", 0, 2));
            table.addCell(ustawfraze("Koszty uboczne zakupu", 0, 2));
            table.addCell(ustawfraze("Wydatki(koszty)", 4, 0));
            table.addCell(ustawfraze("Uwagi", 0, 2));
            table.addCell(ustawfrazeAlign("imię i nazwisko (firma)", "center",6));
            table.addCell(ustawfrazeAlign("adres", "center",6));
            table.addCell(ustawfrazeAlign("wartość sprzedanych towarów i usług", "center",6));
            table.addCell(ustawfrazeAlign("pozostałe przychody", "center",6));
            table.addCell(ustawfrazeAlign("razem przychód (7+8)", "center",6));
            table.addCell(ustawfrazeAlign("wynagrodzenia w gotówce i w naturze", "center",6));
            table.addCell(ustawfrazeAlign("pozostałe wydatki", "center",6));
            table.addCell(ustawfrazeAlign("razem wydatki (12+13)", "center",6));
            table.addCell(ustawfrazeAlign("inwestycje", "center",6));
            table.addCell(ustawfrazeAlign("1", "center",6));
            table.addCell(ustawfrazeAlign("2", "center",6));
            table.addCell(ustawfrazeAlign("3", "center",6));
            table.addCell(ustawfrazeAlign("4", "center",6));
            table.addCell(ustawfrazeAlign("5", "center",6));
            table.addCell(ustawfrazeAlign("6", "center",6));
            table.addCell(ustawfrazeAlign("7", "center",6));
            table.addCell(ustawfrazeAlign("8", "center",6));
            table.addCell(ustawfrazeAlign("9", "center",6));
            table.addCell(ustawfrazeAlign("10", "center",6));
            table.addCell(ustawfrazeAlign("11", "center",6));
            table.addCell(ustawfrazeAlign("12", "center",6));
            table.addCell(ustawfrazeAlign("13", "center",6));
            table.addCell(ustawfrazeAlign("14", "center",6));
            table.addCell(ustawfrazeAlign("15", "center",6));
            table.addCell(ustawfrazeAlign("16", "center",6));
            table.addCell(ustawfrazeAlign("1", "center",6));
            table.addCell(ustawfrazeAlign("2", "center",6));
            table.addCell(ustawfrazeAlign("3", "center",6));
            table.addCell(ustawfrazeAlign("4", "center",6));
            table.addCell(ustawfrazeAlign("5", "center",6));
            table.addCell(ustawfrazeAlign("6", "center",6));
            table.addCell(ustawfrazeAlign("7", "center",6));
            table.addCell(ustawfrazeAlign("8", "center",6));
            table.addCell(ustawfrazeAlign("9", "center",6));
            table.addCell(ustawfrazeAlign("10", "center",6));
            table.addCell(ustawfrazeAlign("11", "center",6));
            table.addCell(ustawfrazeAlign("12", "center",6));
            table.addCell(ustawfrazeAlign("13", "center",6));
            table.addCell(ustawfrazeAlign("14", "center",6));
            table.addCell(ustawfrazeAlign("15", "center",6));
            table.addCell(ustawfrazeAlign("16", "center",6));
            table.setHeaderRows(5);
            table.setFooterRows(1);
        } catch (DocumentException ex) {
            Logger.getLogger(PdfPkpir.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }
    
    public static void dodajwiersze(List<DokKsiega> wykaz,PdfPTable table) {
        for (DokKsiega rs : wykaz) {
            if (rs.getNrWpkpir() != 0) {
                table.addCell(ustawfrazeAlign(String.valueOf(rs.getNrWpkpir()), "center",6,22f));
            } else {
                table.addCell(ustawfrazeAlign("", "center",6,22f));
            }
            table.addCell(ustawfrazeAlign(rs.getDataWyst(), "left",6));
            table.addCell(ustawfrazeAlign(rs.getNrWlDk(), "left",6));
            table.addCell(ustawfrazeAlign(rs.getKontr().getNpelna(), "left",6));
            if (rs.getKontr().getKodpocztowy() != null) {
                table.addCell(ustawfrazeAlign(rs.getKontr().getKodpocztowy() + " " + rs.getKontr().getMiejscowosc() + " ul. " + rs.getKontr().getUlica() + " " + rs.getKontr().getDom(), "left",6));
            } else {
                table.addCell(ustawfrazeAlign("", "left",6));
            }
            table.addCell(ustawfrazeAlign(rs.getOpis(), "left",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna7()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna8()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna9()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna10()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna11()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna12()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna13()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna14()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKolumna15()), "right",6));
            if (rs.getTabela() != null && !rs.getTabela().getWaluta().getSymbolwaluty().equals("PLN")) {
                table.addCell(ustawfrazeAlign(formatujEuro(rs.getKwotawwalucie()), "right",6));
            } else {
                table.addCell(ustawfrazeAlign(rs.getUwagi(), "right",6));
            }
        }
    }
}
