/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import static beansPdf.PdfFont.formatujWaluta;
import static beansPdf.PdfFont.ustawfrazeAlign;
import beansPdf.PdfHeaderFooter;
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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Dok;
import entity.Klienci;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import msg.Msg;
import org.primefaces.context.RequestContext;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Stateless
public class PdfObroty  {

    public static void drukuj(List<Dok> goscwybral, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), -20, -20, 20, 10);
        PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/obroty" + wpisView.getPodatnikWpisu() + ".pdf"));
        int liczydlo = 1;
        PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
        writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
        writer.setPageEvent(headerfoter);
        pdf.addTitle("Obroty z kontrahentami");
        pdf.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        pdf.addSubject("Wydruk danych z PKPiR");
        pdf.addKeywords("PKPiR, PDF");
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
        PdfPTable table = new PdfPTable(9);
        table.setWidths(new int[]{1, 2, 4, 2, 2, 2, 2, 2, 2});
        PdfPCell cell = new PdfPCell();
        table.addCell(ustawfrazeAlign("nr kolejny", "center",8));
        table.addCell(ustawfrazeAlign("data wystawienia", "center",8));
        table.addCell(ustawfrazeAlign("kontrahent", "center",8));
        table.addCell(ustawfrazeAlign("transakcja", "center",8));
        table.addCell(ustawfrazeAlign("symbol dok.", "center",8));
        table.addCell(ustawfrazeAlign("nr własny", "center",8));
        table.addCell(ustawfrazeAlign("opis", "center",8));
        table.addCell(ustawfrazeAlign("netto", "center",8));
        table.addCell(ustawfrazeAlign("brutto", "center",8));
        table.setHeaderRows(1);

        List<Dok> wykaz = obliczsume(goscwybral);
        for (Dok rs : wykaz) {
            if (rs.getNrWpkpir() != 0) {
                table.addCell(ustawfrazeAlign(String.valueOf(rs.getNrWpkpir()), "center",8));
            } else {
                table.addCell(ustawfrazeAlign("", "center",8));
            }
            table.addCell(ustawfrazeAlign(rs.getDataWyst(), "left",8));
            table.addCell(ustawfrazeAlign(rs.getKontr().getNpelna(), "left",8));
            table.addCell(ustawfrazeAlign(rs.getRodzTrans(), "left",8));
            table.addCell(ustawfrazeAlign(rs.getTypdokumentu(), "left",8));
            table.addCell(ustawfrazeAlign(rs.getNrWlDk(), "left",8));
            table.addCell(ustawfrazeAlign(rs.getOpis(), "left",8));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getNetto()), "right",8));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getBrutto()), "right",8));
        }
        pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
        pdf.add(new Chunk());
        Paragraph miziu1 = new Paragraph(new Phrase("Wydruk obrotów z kontrahentem dla klienta: "+wpisView.getPodatnikWpisu()+" od "+wpisView.getMiesiacOd()+"/"+wpisView.getRokWpisu()+" do "+wpisView.getMiesiacDo()+"/"+wpisView.getRokWpisu(),font));
        miziu1.setAlignment(Element.ALIGN_CENTER);
        pdf.add(miziu1);
        pdf.add(Chunk.NEWLINE);
        pdf.add(table);
        pdf.addAuthor("Biuro Rachunkowe Taxman");
        pdf.close();
        RequestContext.getCurrentInstance().execute("wydrukobroty('"+wpisView.getPodatnikWpisu()+"');");
        Msg.msg("i", "Wydrukowano obroty", "form:messages");
    }

    private static List<Dok> obliczsume(List<Dok> wykaz) {
        Double nettosuma = 0.0;
        Double bruttosuma = 0.0;
        for(Dok p : wykaz){
            nettosuma += p.getNetto();
            bruttosuma += p.getBrutto();
        }
        Dok suma = new Dok();
        suma.setNrWpkpir(0);
        suma.setNrWlDk("");
        suma.setKontr(new Klienci());
        suma.setRodzTrans("");
        suma.setOpis("podsumowanie");
        suma.setNetto(nettosuma);
        suma.setBrutto(bruttosuma);
        wykaz.add(suma);
        return wykaz;
    }
}
