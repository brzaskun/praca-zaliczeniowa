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
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.Vatpoz;
import entity.Deklaracjevat;
import entity.Podatnik;
import java.io.FileNotFoundException;
import java.io.IOException;
import plik.Plik;

/**
 *
 * @author Osito
 */

public class PdfORDZU extends PdfVAT7{
    static String ordz = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/ORD-ZU.jpg";
    
    public static void drukujORDZU(Deklaracjevat dkl, Podatnik p) throws DocumentException, FileNotFoundException, IOException {
        Vatpoz v = dkl.getSelected();
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR("ordzu"+v.getPodatnik()+".pdf"));
        document.addTitle("Załącznik ORDZU");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk załącnzika do deklaracji VAT " + dkl.getPodatnik());
        document.addKeywords("ORD-ZU test, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(helvetica, 12);
        Font fontM = new Font(helvetica, 10);
        pierwszastrona(writer,v,dkl,p);
        document.close();
        PdfReader reader = new PdfReader("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/ordzu"+v.getPodatnik()+".pdf");
        PdfStamper pdfStamper = new PdfStamper(reader, Plik.plikR("ord-zu"+dkl.getPodatnik()+".pdf"));
        PdfContentByte underContent = pdfStamper.getUnderContent(1);
        Image image;
        image = Image.getInstance(ordz);
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        underContent.add(underContent);
        underContent.addImage(image);
        pdfStamper.close();
        reader.close();
        writer.close();
 
    }
  
    
   private static void pierwszastrona(PdfWriter writer,Vatpoz d,Deklaracjevat l, Podatnik p) throws DocumentException{
        absText(writer, p.getNip(), 70, 790);
        absText(writer, p.getNazwisko(), 70, 625);absText(writer, p.getImie(), 370, 625);
        absText(writer, p.getDataurodzenia(), 70, 600);absText(writer, p.getPesel(), 370, 600);
        ColumnText ct = new ColumnText(writer.getDirectContent());
        ct.setSimpleColumn(500,200,50,500);
        Paragraph par =new Paragraph();
        par.add(l.getOrdzu());
        ct.addElement(par);
        ct.go();
   }
   
public static void main(String[] args) throws IOException, DocumentException{
//        Document document = new Document();
//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/ordzu1.pdf"));
//        document.addTitle("Załącznik ORDZU");
//        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
//        document.addSubject("Wydruk załącnzika do deklaracji VAT ");
//        document.addKeywords("ORD-ZU test, PDF");
//        document.addCreator("Grzegorz Grzelczyk");
//        document.open();
//        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
//        Font font = new Font(helvetica, 12);
//        Font fontM = new Font(helvetica, 10);
//        absText(writer, "8511005008", 70, 790);
//        absText(writer, "nazwisko", 70, 625);absText(writer, "imie", 370, 625);
//        absText(writer, "data urowdznia", 70, 600);absText(writer, "Pesel", 370, 600);
//        ColumnText ct = new ColumnText(writer.getDirectContent());
//        ct.setSimpleColumn(500,200,50,500);
//        Paragraph p=new Paragraph();
//        p.add("lolo");
//        ct.addElement(p);
//        ct.go();
//        document.close();
//        PdfReader reader = new PdfReader("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/ordzu1.pdf");
//        PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/ord-zufinal.pdf"));
//        PdfContentByte underContent = pdfStamper.getUnderContent(1);
//        Image image;
//        image = Image.getInstance(ordz);
//        image.scaleToFit(610, 850);
//        image.setAbsolutePosition(0f, 0f);
//        underContent.add(underContent);
//        underContent.addImage(image);
//        pdfStamper.close();
//        reader.close();
//        writer.close();

        try {
            String[] files = {"C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vat7-13GRZELCZYK.pdf","C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/ord-zufinal.pdf"};
            Document PDFCombineUsingJava = new Document();
            PdfCopy copy = new PdfCopy(PDFCombineUsingJava, Plik.plikR("CombinedPDFDocument.pdf"));
            PDFCombineUsingJava.open();
            PdfReader ReadInputPDF;
            int number_of_pages;
            for (int i = 0; i < files.length; i++) {
                ReadInputPDF = new PdfReader(files[i]);
                number_of_pages = ReadInputPDF.getNumberOfPages();
                for (int page = 0; page < number_of_pages;) {
                    copy.addPage(copy.getImportedPage(ReadInputPDF, ++page));
                }
            }
            PDFCombineUsingJava.close();
        } catch (Exception i) {
        }
}
}
