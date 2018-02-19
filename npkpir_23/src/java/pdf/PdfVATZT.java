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
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.Vatpoz;
import entity.Deklaracjevat;
import entity.Podatnik;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static pdf.PdfVAT7.absText;
import plik.Plik;

/**
 *
 * @author Osito
 */

public class PdfVATZT {
    private static final String ordz = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/VAT-ZT.jpg";
    
    public static void drukujZT(Deklaracjevat dkl, Podatnik p) throws DocumentException, FileNotFoundException, IOException {
        Vatpoz v = dkl.getSelected();
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR("vatzt"+v.getPodatnik()+".pdf"));
        document.addTitle("Załącznik VAT-ZT");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk załącznika do deklaracji VAT " + dkl.getPodatnik());
        document.addKeywords("VAT-ZT, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(helvetica, 12);
        Font fontM = new Font(helvetica, 10);
        pierwszastrona(writer,v,dkl,p);
        document.close();
        PdfReader reader = new PdfReader("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vatzt"+v.getPodatnik()+".pdf");
        PdfStamper pdfStamper = new PdfStamper(reader, Plik.plikR("vat-zt"+dkl.getPodatnik()+".pdf"));
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
        absText(writer, p.getNip(), 70, 785);
        absText(writer, p.getUrzadskarbowy(), 50, 690);
        absText(writer, "X", 356, 612);
        absText(writer, p.getNazwisko(), 60, 585);absText(writer, p.getImie(), 170, 585);
        absText(writer, p.getDataurodzenia(), 300, 585);
        absText(writer, l.getPozycjeszczegolowe().getPole62(), 60, 525);
        ColumnText ct = new ColumnText(writer.getDirectContent());
        ct.setSimpleColumn(500,200,60,500);
        Paragraph par =new Paragraph();
        par.add(l.getVatzt());
        ct.addElement(par);
        ct.go();
         absText(writer, p.getImie(), 80, 186); absText(writer, p.getNazwisko(), 400, 186);
        try{
            absText(writer, l.getDatazlozenia().toString(), 120, 158);  absText(writer, l.getSporzadzil(), 400, 158);
        } catch (Exception e){} 
   }
   
public static void main(String[] args) throws IOException, DocumentException{
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/vatzt1.pdf"));
        document.addTitle("Załącznik ORDZU");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk załącnzika do deklaracji VAT ");
        document.addKeywords("ORD-ZU test, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(helvetica, 12);
        Font fontM = new Font(helvetica, 10);
        absText(writer, "8511005008", 70, 785);
        absText(writer, "Pierwszy Urząd Skarbowy w Szczecinie", 50, 690);
        absText(writer, "X", 356, 612);
        absText(writer, "nazwisko", 60, 585);absText(writer, "imie", 170, 585);
        absText(writer, "data urowdznia", 300, 585);
        absText(writer, "KWOTA", 60, 525);
        ColumnText ct = new ColumnText(writer.getDirectContent());
        ct.setSimpleColumn(500,200,60,500);
        Paragraph p=new Paragraph();
        p.add("lolo");
        ct.addElement(p);
        ct.go();
        absText(writer, "Imie", 80, 186); absText(writer, "Nazwisko", 400, 186);
        try{
            absText(writer, "Data sporzadzil", 120, 158);  absText(writer, "Spoorzadzajacy", 400, 158);
        } catch (Exception e){} 
        document.close();
        PdfReader reader = new PdfReader("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/vatzt1.pdf");
        PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/vat-ztfinal.pdf"));
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

//        try {
//            String[] files = {"C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vat7-13GRZELCZYK.pdf","C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/ord-zufinal.pdf"};
//            Document PDFCombineUsingJava = new Document();
//            PdfCopy copy = new PdfCopy(PDFCombineUsingJava, Plik.plikR("CombinedPDFDocument.pdf"));
//            PDFCombineUsingJava.open();
//            PdfReader ReadInputPDF;
//            int number_of_pages;
//            for (int i = 0; i < files.length; i++) {
//                ReadInputPDF = new PdfReader(files[i]);
//                number_of_pages = ReadInputPDF.getNumberOfPages();
//                for (int page = 0; page < number_of_pages;) {
//                    copy.addPage(copy.getImportedPage(ReadInputPDF, ++page));
//                }
//            }
//            PDFCombineUsingJava.close();
//        } catch (Exception i) {
//            System.out.println(i);
//        }
}
}
