/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Dok;
import entity.Inwestycje;
import entity.Inwestycje.Sumazalata;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import msg.Msg;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */

@ManagedBean
public class PdfInwestycja extends Pdf implements Serializable {
    
     public void drukujinwestycje(Inwestycje inwestycja) throws DocumentException, FileNotFoundException, IOException {
        Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), 20, 20, 20, 10);
        PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/inwestycja" + wpisView.getPodatnikWpisu() + ".pdf"));
        //PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/inwestycjaTesting.pdf"));
        Pdf.HeaderFooter event = new Pdf.HeaderFooter();
        writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
        writer.setPageEvent(event);
        pdf.addTitle("Inwestycja - zestawienie");
        pdf.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        pdf.addSubject("Wydruk danych z inwestycji");
        pdf.addKeywords("Inwestycja, PDF");
        pdf.addCreator("Grzegorz Grzelczyk");
        pdf.open();
        BaseFont helvetica = null;
        try {
            helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        Font font = new Font(helvetica, 8);
        Font fontL = new Font(helvetica, 10);
        pdf.setPageSize(PageSize.A4);
        Paragraph miziu = new Paragraph(new Phrase("Rozliczenie wydatków poniesionych na inwestycję w firmie: "+wpisView.getPodatnikWpisu()+" NIP: "+wpisView.getPodatnikObiekt().getNip(), fontL));
        miziu.setAlignment(Element.ALIGN_LEFT);
        pdf.add(miziu);
        pdf.add(new Chunk().NEWLINE);
        //tabela naglowek inwestycji
        PdfPTable tableheader = new PdfPTable(7);
        tableheader.setWidths(new int[]{2, 1, 4, 1, 1, 2, 1});
        try {
            tableheader.addCell(ustawfrazebez("Symbol", "center",8));
            tableheader.addCell(ustawfrazebez("Skrót", "center",8));
            tableheader.addCell(ustawfrazebez("Opis", "center",8));
            tableheader.addCell(ustawfrazebez("Rozpoczęcie", "center",8));
            tableheader.addCell(ustawfrazebez("Zakończenie", "center",8));
            tableheader.addCell(ustawfrazebez("Wartość netto", "center",8));
            tableheader.addCell(ustawfrazebez("Stan", "center",8));
            tableheader.addCell(ustawfrazebez(inwestycja.getSymbol(), "left",8));
            tableheader.addCell(ustawfrazebez(inwestycja.getSkrot(), "left",8));
            tableheader.addCell(ustawfrazebez(inwestycja.getOpis(), "left",8));
            tableheader.addCell(ustawfrazebez(inwestycja.getRokrozpoczecia()+"/"+inwestycja.getMcrozpoczecia(), "center",8));
            tableheader.addCell(ustawfrazebez(inwestycja.getRokzakonczenia()+"/"+inwestycja.getMczakonczenia(), "center",8));
            tableheader.addCell(ustawfrazebez(formatujliczby(inwestycja.getTotal()), "right",8));
            tableheader.addCell(ustawfrazebez(inwestycja.getZakonczona() == false ? "rozpoczęta" : "zakończona", "center",8));
            
        } catch (IOException ex1) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex1);
        }
        pdf.add(tableheader);
        PdfPTable tableyear = new PdfPTable(2);
        tableyear.setWidths(new int[]{1, 2});
        try {
            tableyear.addCell(ustawfrazebez("Rok kalendarzowy", "center",8));
            tableyear.addCell(ustawfrazebez("Suma wydatków w roku kalendarzowym", "center",8));
            for (Sumazalata tmp : inwestycja.getSumazalata()) {
                tableyear.addCell(ustawfrazebez(tmp.getRok(), "center",8));
                tableyear.addCell(ustawfrazebez(formatujliczby(tmp.getKwota()), "right",8));
            }
        } catch (IOException ex1) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex1);
        }
        pdf.add(tableyear);
        pdf.add(new Chunk().NEWLINE);
        miziu = new Paragraph(new Phrase("zestawienie rachunków składających się na sumę inwestycji", fontL));
        miziu.setAlignment(Element.ALIGN_LEFT);
        pdf.add(miziu);
        pdf.add(new Chunk().NEWLINE);
        //tablica z dokumentami
        PdfPTable table = new PdfPTable(9);
        table.setWidths(new int[]{1, 2, 5, 4, 3, 3, 3, 3, 3});
        try {
            table.addCell(ustawfrazebez("lp.", "center",8));
            table.addCell(ustawfrazebez("data wystawienia", "center",8));
            table.addCell(ustawfrazebez("nazwa kontrahenta", "center",8));
            table.addCell(ustawfrazebez("adres", "center",8));
            table.addCell(ustawfrazebez("nip", "center",8));
            table.addCell(ustawfrazebez("nr fakt", "center",8));
            table.addCell(ustawfrazebez("netto", "center",8));
            table.addCell(ustawfrazebez("vat", "center",8));
            table.addCell(ustawfrazebez("brutto", "center",8));
            table.addCell(ustawfrazebez("lp.", "center",8));
            table.addCell(ustawfrazebez("data wystawienia", "center",8));
            table.addCell(ustawfrazebez("nazwa kontrahenta", "center",8));
            table.addCell(ustawfrazebez("adres", "center",8));
            table.addCell(ustawfrazebez("nip", "center",8));
            table.addCell(ustawfrazebez("nr fakt", "center",8));
            table.addCell(ustawfrazebez("netto", "center",8));
            table.addCell(ustawfrazebez("vat", "center",8));
            table.addCell(ustawfrazebez("brutto", "center",8));
            for (Dok p : inwestycja.getDokumenty()) {
                table.addCell(ustawfrazebez(String.valueOf(p.getNrWpkpir()), "center",8));
                table.addCell(ustawfrazebez(p.getDataWyst(), "center",8));
                table.addCell(ustawfrazebez(p.getKontr().getNpelna(), "left",8));
                table.addCell(ustawfrazebez(p.getKontr().getMiejscowosc()+" "+p.getKontr().getUlica()+" "+p.getKontr().getDom(), "left",8));
                table.addCell(ustawfrazebez(p.getKontr().getNip(), "center",8));
                table.addCell(ustawfrazebez(p.getNrWlDk(), "left",8));
                table.addCell(ustawfrazebez(formatujliczby(p.getNetto()), "right",8));
                table.addCell(ustawfrazebez(formatujliczby(p.getBrutto()-p.getNetto()), "right",8));
                table.addCell(ustawfrazebez(formatujliczby(p.getBrutto()), "right",8));
            }
            table.setHeaderRows(2);
            table.setFooterRows(1);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
        pdf.add(table);
        pdf.close();
        RequestContext.getCurrentInstance().execute("wydrukinwestycja('"+wpisView.getPodatnikWpisu()+"');");
        Msg.msg("i", "Wydrukowano wybraną inwestycję", "form:messages");
    }
     
    public static void main (String[] args) throws DocumentException, FileNotFoundException, IOException {
        PdfInwestycja tmp = new PdfInwestycja();
        tmp.drukujinwestycje(new Inwestycje());
    }
}
