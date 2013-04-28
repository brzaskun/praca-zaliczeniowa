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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Dok;
import entity.Klienci;
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
public class PdfObroty extends Pdf implements Serializable {

    public void drukuj() throws DocumentException, FileNotFoundException, IOException {
        Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), -20, -20, 20, 10);
        PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream("C:/npkpir_21/build/web/wydruki/obroty" + wpisView.getPodatnikWpisu() + ".pdf"));
        HeaderFooter event = new HeaderFooter();
        writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
        writer.setPageEvent(event);
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
        try {
            table.addCell(ustawfrazebez("nr kolejny", "center",8));
            table.addCell(ustawfrazebez("data wystawienia", "center",8));
            table.addCell(ustawfrazebez("kontrahent", "center",8));
            table.addCell(ustawfrazebez("transakcja", "center",8));
            table.addCell(ustawfrazebez("symbol dok.", "center",8));
            table.addCell(ustawfrazebez("nr własny", "center",8));
            table.addCell(ustawfrazebez("opis", "center",8));
            table.addCell(ustawfrazebez("netto", "center",8));
            table.addCell(ustawfrazebez("brutto", "center",8));
            
            table.setHeaderRows(1);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Dok> wykaz = obliczsume(obrotyView.getGoscwybral());
        for (Dok rs : wykaz) {
            if (rs.getNrWpkpir() != 0) {
                table.addCell(ustawfrazebez(String.valueOf(rs.getNrWpkpir()), "center",8));
            } else {
                table.addCell(ustawfrazebez("", "center",8));
            }
            table.addCell(ustawfrazebez(rs.getDataWyst(), "left",8));
            table.addCell(ustawfrazebez(rs.getKontr().getNpelna(), "left",8));
            table.addCell(ustawfrazebez(rs.getRodzTrans(), "left",8));
            table.addCell(ustawfrazebez(rs.getTypdokumentu(), "left",8));
            table.addCell(ustawfrazebez(rs.getNrWlDk(), "left",8));
            table.addCell(ustawfrazebez(rs.getOpis(), "left",8));
            table.addCell(ustawfrazebez(formatujliczby(rs.getNetto()), "right",8));
            table.addCell(ustawfrazebez(formatujliczby(rs.getBrutto()), "right",8));
        }
        pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
        pdf.add(new Chunk());
        Paragraph miziu1 = new Paragraph(new Phrase("Biuro Rachunkowe Taxman. Wydruk obrotów z kontrahentem dla klienta: "+wpisView.getPodatnikWpisu()+" od "+wpisView.getMiesiacOd()+"/"+wpisView.getRokWpisu()+" do "+wpisView.getMiesiacDo()+"/"+wpisView.getRokWpisu(),font));
        miziu1.setAlignment(Element.ALIGN_CENTER);
        pdf.add(miziu1);
        pdf.add(new Chunk().NEWLINE);
        pdf.add(table);
        pdf.addAuthor("Biuro Rachunkowe Taxman");
        pdf.close();
        Msg.msg("i", "Wydrukowano obroty", "form:messages");
    }

    private List<Dok> obliczsume(List<Dok> wykaz) {
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
