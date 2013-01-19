/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.inject.Named;
import org.primefaces.component.datatable.DataTable;

/**
 *
 * @author Osito
 */
@Named
public class pdf {
    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
    Document pdf = (Document) document;
    pdf.setPageSize(PageSize.A4.rotate());
    pdf.setMargins(10, -5, 10, -5);
    Phrase ft = new Phrase("podatnik:");
    HeaderFooter hf = new HeaderFooter(new Phrase("Biuro Rachunkowe Taxman - wydruk", new Font(Font.COURIER,5)), true);
    HeaderFooter hfx = new HeaderFooter(new Phrase("data: ", new Font(Font.COURIER,5)), true);
    pdf.setHeader(hf);
    pdf.setFooter(hfx);
    
  
} 
  
   public static void main(String[] args) throws DocumentException, FileNotFoundException{
    Rectangle rct = new Rectangle(PageSize.A4);
    rct.enableBorderSide(2);
    rct.setBackgroundColor(Color.yellow);
    rct.rotate();
    Document pdf = new Document(rct);
    PdfWriter.getInstance(pdf, new FileOutputStream("c:/filename.pdf"));
    pdf.open();  
    pdf.add(new Paragraph("Hi, this is your PDF file!"));
    pdf.setPageSize(PageSize.A4);
    Phrase hd = new Phrase("Biuro Rachunkowe Taxman - wydruk");
    Phrase ft = new Phrase("podatnik:");
    HeaderFooter hf = new HeaderFooter(hd,null);
    HeaderFooter hfx = new HeaderFooter(ft,null);
    pdf.setHeader(hf);
    pdf.setFooter(hfx);
    pdf.addAuthor("Biuro Rachunkowe Taxman");
    pdf.close();
   }      
}
