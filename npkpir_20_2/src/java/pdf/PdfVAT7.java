/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Osito
 */
@ManagedBean
public class PdfVAT7 extends Pdf implements Serializable{
    public void drukuj() throws DocumentException, FileNotFoundException, IOException{
    
    }   
    
     static String INPUTFILE = "c:/pk.pdf";
    
    private static void absText(PdfWriter writer,String text, int x, int y) {
    try {
      PdfContentByte cb = writer.getDirectContent();
      BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
      cb.saveState();
      cb.beginText();
      cb.moveText(x, y);
      cb.setFontAndSize(bf, 12);
      cb.showText(text);
      cb.endText();
      cb.restoreState();
    } catch (DocumentException | IOException e) {
    }
  }
     
    public static void main(String[] args) throws IOException, DocumentException{
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/pk.pdf"));
        document.addTitle("Polecenie księgowania");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk danych z PKPiR");
        document.addKeywords("PKPiR, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(helvetica,12);  
        Font fontM = new Font(helvetica,10);
        document.add(new Paragraph("8511005008",font));
        absText(writer, "03", 200, 745);
        absText(writer, "2013", 270, 745);
        absText(writer, "Pierwszy Urząd Skarbowy w Szczecinie", 70, 674);
        absText(writer, "X", 368, 674);
        absText(writer, "X", 388, 624);
        absText(writer, "Imie i nazwisko", 70, 600);
        absText(writer, "Pesel", 368, 600);
        absText(writer, "Kraj", 70, 576);
        absText(writer, "Województwo", 170, 576);
        absText(writer, "Powiat", 400, 576);
        document.close();
        PdfReader reader = new PdfReader(INPUTFILE);
        reader.removeUsageRights();
        PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream("c:/pk1.pdf"));
        PdfContentByte underContent = pdfStamper.getUnderContent(1);
        Image image = Image.getInstance("c:/VAT-71-p1.jpg");
        image.scaleToFit(610,850);
        image.setAbsolutePosition(0f, 0f);
        underContent.add(underContent);
        underContent.addImage(image);
        pdfStamper.close();
       
      }
}
