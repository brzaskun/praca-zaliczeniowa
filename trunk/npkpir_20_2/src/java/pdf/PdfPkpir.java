/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.DokKsiega;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
public class PdfPkpir extends Pdf implements Serializable {

    public void drukujksiege() throws DocumentException, FileNotFoundException, IOException {
        Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), -20, -20, 20, 10);
        PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_20_2/build/web/wydruki/pkpir" + wpisView.getPodatnikWpisu() + ".pdf"));
        HeaderFooter event = new HeaderFooter();
        writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
        writer.setPageEvent(event);
        pdf.open();
        BaseFont helvetica = null;
        try {
            helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        Font font = new Font(helvetica, 8);
        pdf.setPageSize(PageSize.A4);
        PdfPTable table = new PdfPTable(16);
        table.setWidths(new int[]{1, 2, 2, 4, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2});
        PdfPCell cell = new PdfPCell();
        try {

            table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 4, 0));
            table.addCell(ustawfraze("wydruk podatkowej księgi przychodów i rozchodów", 4, 0));
            table.addCell(ustawfraze("firma: " + wpisView.getPodatnikWpisu(), 5, 0));
            table.addCell(ustawfraze("za okres: " + wpisView.getRokWpisu() + "/" + wpisView.getMiesiacWpisu(), 3, 0));
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

            table.addCell(ustawfrazebez("imię i nazwisko (firma)", "center",6));
            table.addCell(ustawfrazebez("adres", "center",6));
            table.addCell(ustawfrazebez("wartość sprzedanych towarów i usług", "center",6));
            table.addCell(ustawfrazebez("pozostałe przychody", "center",6));
            table.addCell(ustawfrazebez("razem przychód (7+8)", "center",6));
            table.addCell(ustawfrazebez("wynagrodzenia w gotówce i w naturze", "center",6));
            table.addCell(ustawfrazebez("pozostałe wydatki", "center",6));
            table.addCell(ustawfrazebez("razem wydatki (12+13)", "center",6));
            table.addCell(ustawfrazebez("inwestycje", "center",6));

            table.addCell(ustawfrazebez("1", "center",6));
            table.addCell(ustawfrazebez("2", "center",6));
            table.addCell(ustawfrazebez("3", "center",6));
            table.addCell(ustawfrazebez("4", "center",6));
            table.addCell(ustawfrazebez("5", "center",6));
            table.addCell(ustawfrazebez("6", "center",6));
            table.addCell(ustawfrazebez("7", "center",6));
            table.addCell(ustawfrazebez("8", "center",6));
            table.addCell(ustawfrazebez("9", "center",6));
            table.addCell(ustawfrazebez("10", "center",6));
            table.addCell(ustawfrazebez("11", "center",6));
            table.addCell(ustawfrazebez("12", "center",6));
            table.addCell(ustawfrazebez("13", "center",6));
            table.addCell(ustawfrazebez("14", "center",6));
            table.addCell(ustawfrazebez("15", "center",6));
            table.addCell(ustawfrazebez("16", "center",6));

            table.addCell(ustawfrazebez("1", "center",6));
            table.addCell(ustawfrazebez("2", "center",6));
            table.addCell(ustawfrazebez("3", "center",6));
            table.addCell(ustawfrazebez("4", "center",6));
            table.addCell(ustawfrazebez("5", "center",6));
            table.addCell(ustawfrazebez("6", "center",6));
            table.addCell(ustawfrazebez("7", "center",6));
            table.addCell(ustawfrazebez("8", "center",6));
            table.addCell(ustawfrazebez("9", "center",6));
            table.addCell(ustawfrazebez("10", "center",6));
            table.addCell(ustawfrazebez("11", "center",6));
            table.addCell(ustawfrazebez("12", "center",6));
            table.addCell(ustawfrazebez("13", "center",6));
            table.addCell(ustawfrazebez("14", "center",6));
            table.addCell(ustawfrazebez("15", "center",6));
            table.addCell(ustawfrazebez("16", "center",6));
            table.setHeaderRows(5);
            table.setFooterRows(1);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<DokKsiega> wykaz = ksiegaView.getLista();
        for (DokKsiega rs : wykaz) {
            if (rs.getNrWpkpir() != 0) {
                table.addCell(ustawfrazebez(String.valueOf(rs.getNrWpkpir()), "center",6));
            } else {
                table.addCell(ustawfrazebez("", "center",6));
            }
            table.addCell(ustawfrazebez(rs.getDataWyst(), "left",6));
            table.addCell(ustawfrazebez(rs.getNrWlDk(), "left",6));
            table.addCell(ustawfrazebez(rs.getKontr().getNpelna(), "left",6));
            if (rs.getKontr().getKodpocztowy() != null) {
                table.addCell(ustawfrazebez(rs.getKontr().getKodpocztowy() + " " + rs.getKontr().getMiejscowosc() + " ul. " + rs.getKontr().getUlica() + " " + rs.getKontr().getDom(), "left",6));
            } else {
                table.addCell(ustawfrazebez("", "left",6));
            }
            table.addCell(ustawfrazebez(rs.getOpis(), "left",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getKolumna7()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getKolumna8()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getKolumna9()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getKolumna10()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getKolumna11()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getKolumna12()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getKolumna13()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getKolumna14()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getKolumna15()), "right",6));
            table.addCell(ustawfrazebez(rs.getUwagi(), "right",6));
        }
        pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
        pdf.add(table);
        pdf.addAuthor("Biuro Rachunkowe Taxman");
        pdf.close();
        Msg.msg("i", "Wydrukowano księgę", "form:messages");
    }
}
