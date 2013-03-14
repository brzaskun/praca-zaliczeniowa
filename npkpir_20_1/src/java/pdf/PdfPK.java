/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Dok;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import javax.faces.bean.ManagedBean;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
public class PdfPK extends Pdf implements Serializable {

    public void drukujPK(Dok selected) throws DocumentException, FileNotFoundException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("c:/adrukPK.pdf")).setInitialLeading(16);
        document.open();
            Rectangle rect = new Rectangle(0, 832, 136, 800);
            rect.setBackgroundColor(BaseColor.RED);
            document.add(rect);
            document.add(new Chunk("Biuro Rachunkowe"));
            document.add(new Chunk(" "));
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica,14);  
            Font fontM = new Font(helvetica,10);
            Font fontS = new Font(helvetica,6);
            Chunk id = new Chunk("Taxman", font);
            id.setBackground(BaseColor.BLACK, 1f, 0.5f, 1f, 1.5f);
            document.add(id);
            document.add(Chunk.NEWLINE);
            Paragraph miziu = new Paragraph(new Phrase("Szczecin, dnia 18 maja 2000r.",font));
            miziu.setAlignment(Element.ALIGN_RIGHT);
            miziu.setLeading(50);
            document.add(miziu);
            document.add(new Chunk().NEWLINE);
            Paragraph miziu1 = new Paragraph(new Phrase("Polecenie księgowania "+selected.getNrWlDk(),font));
            miziu1.setAlignment(Element.ALIGN_CENTER);
            document.add(miziu1);
            PdfPTable table = new PdfPTable(6);
            table.setWidths(new int[]{1, 5, 2, 2, 2, 2});
            PdfPCell cell = new PdfPCell();
            try {
                table.addCell(ustawfrazebez("lp","center",8));
                table.addCell(ustawfrazebez("opis","center",8));
                table.addCell(ustawfrazebez("netto","center",8));
                table.addCell(ustawfrazebez("vat","center",8));
                table.addCell(ustawfrazebez("brutto","center",8));
                table.addCell(ustawfrazebez("uwagi","center",8));
            } catch (DocumentException | IOException e){
                
            }
                table.addCell(ustawfrazebez(String.valueOf(selected.getNrWpkpir()),"center",8));
                table.addCell(ustawfrazebez(selected.getOpis(),"left",8));
                table.addCell(ustawfrazebez(String.valueOf(selected.getNetto()),"right",8));
                table.addCell(ustawfrazebez("0.00","right",8));
                table.addCell(ustawfrazebez(String.valueOf(selected.getNetto()),"right",8));
                table.addCell(ustawfrazebez("","right",8));
//                NumberFormat formatter = NumberFormat.getCurrencyInstance();
//                String moneyString = formatter.format(1020.28);
//                table.addCell(ustawfrazebez(moneyString));
//                table.addCell(ustawfrazebez("adres kontr"));
           
            document.add(Chunk.NEWLINE);
            document.add(table);
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph(selected.getWprowadzil(),fontM));
            document.add(new Paragraph("___________________________",fontM));
            document.add(new Paragraph("sporządził",fontM));
        document.close();
        Msg.msg("i", "Wydrukowano PK", "form:messages");
    }
}
