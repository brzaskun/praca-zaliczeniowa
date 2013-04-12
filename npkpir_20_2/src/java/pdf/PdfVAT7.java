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
import dao.PodatnikDAO;
import embeddable.PozycjeSzczegoloweVAT;
import embeddable.Vatpoz;
import entity.Deklaracjevat;
import entity.Podatnik;
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
    
    public void drukuj(Deklaracjevat dkl) throws DocumentException, FileNotFoundException, IOException {
        System.out.println("Drukuje " + dkl);
        Vatpoz v = dkl.getSelected();
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_20_2/build/web/vat/vat7"+v.getPodatnik()+".pdf"));
        document.addTitle("Polecenie księgowania");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk deklaracji VAT " + dkl.getPodatnik());
        document.addKeywords("VAT-7, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(helvetica, 12);
        Font fontM = new Font(helvetica, 10);
        pierwszastrona(writer,v,dkl);
        document.newPage();
        drugastrona(writer,v,dkl);
        document.close();
        PdfReader reader = new PdfReader("C:/Users/Osito/Documents/NetBeansProjects/npkpir_20_2/build/web/vat/vat7"+v.getPodatnik()+".pdf");
        reader.removeUsageRights();
        PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_20_2/build/web/vat/vat7-13"+dkl.getPodatnik()+".pdf"));
        PdfContentByte underContent = pdfStamper.getUnderContent(1);
        Image image = Image.getInstance("C:/Users/Osito/Documents/NetBeansProjects/npkpir_20_2/build/web/vat/VAT-71-p1.jpg");
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        underContent.add(underContent);
        underContent.addImage(image);
        PdfContentByte overContent = pdfStamper.getOverContent(1);
        image = Image.getInstance("c:/vat/golab.png");
        image.scaleToFit(50,50);
        image.setAbsolutePosition(450f, 770f);
        overContent.addImage(image);
        underContent = pdfStamper.getUnderContent(2);
        image = Image.getInstance("C:/Users/Osito/Documents/NetBeansProjects/npkpir_20_2/build/web/vat/VAT-72-p1.jpg");
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        underContent.add(underContent);
        underContent.addImage(image);
        pdfStamper.close();
        reader.close();
        writer.close();

    }
  
    
   private void pierwszastrona(PdfWriter writer,Vatpoz d,Deklaracjevat l){
        Podatnik p = podatnikDAO.find(d.getPodatnik());
        PozycjeSzczegoloweVAT o = d.getPozycjeszczegolowe();
        absText(writer, p.getNip(), 70, 790);
        absText(writer, d.getMiesiac(), 200, 745);
        absText(writer, d.getRok(), 270, 745);
        absText(writer, d.getNazwaurzedu(), 70, 674);
        if(d.getCelzlozenia().equals("1")){
            absText(writer, "X", 368, 674);
        } else {
            absText(writer, "X", 482, 674);
        }
        absText(writer, "X", 388, 624);
        absText(writer, p.getImie()+ " "+p.getNazwisko(), 70, 600);
        absText(writer, p.getPesel(), 368, 600);
        absText(writer, "Polska", 70, 576);
        absText(writer, p.getWojewodztwo(), 170, 576);
        absText(writer, p.getPowiat(), 400, 576);
        absText(writer, p.getGmina(), 70, 552);
        absText(writer, p.getUlica(), 200, 552);
        absText(writer, p.getNrdomu(), 470, 552);
        absText(writer, p.getNrlokalu(), 535, 552);
        absText(writer, p.getMiejscowosc(), 70, 528);
        absText(writer, p.getKodpocztowy(), 300, 528);
        absText(writer, p.getPoczta(), 380, 528);
        absText(writer, o.getPole20(), 330, 486);
        absText(writer, o.getPole21(), 330, 462);
        absText(writer, o.getPole22(), 330, 438);
        absText(writer, o.getPole23(), 330, 414);
        absText(writer, o.getPole24(), 330, 390);
        absText(writer, o.getPole25(), 330, 366); absText(writer, o.getPole26(), 490, 366);
        absText(writer, o.getPole27(), 330, 342); absText(writer, o.getPole28(), 490, 342);
        absText(writer, o.getPole29(), 330, 318); absText(writer, o.getPole30(), 490, 318);
        absText(writer, o.getPole31(), 330, 296);
        absText(writer, o.getPole32(), 330, 272);
        absText(writer, o.getPole33(), 330, 248); absText(writer, o.getPole34(), 490, 248);
        absText(writer, o.getPole35(), 330, 224); absText(writer, o.getPole36(), 490, 224);
        absText(writer, o.getPole37(), 330, 200); absText(writer, o.getPole37(), 490, 200);
        absText(writer, o.getPole39(), 330, 176); absText(writer, o.getPole40(), 490, 176);
        absText(writer, o.getPole41(), 330, 152); absText(writer, o.getPole42(), 490, 152);
                                                  absText(writer, o.getPole43(), 490, 128);
                                                  absText(writer, o.getPole44(), 490, 104);
        absText(writer, o.getPole45(), 330, 80);  absText(writer, o.getPole46(), 490, 80);
        absText(writer, "Status "+l.getStatus(), 490, 790);
        absText(writer, "Data potwierdzenia ", 490, 780,6);
        absText(writer, l.getDataupo().toString(), 490, 770,6);
        absText(writer, l.getOpis(), 455, 760,6);
   }
   
   private void drugastrona(PdfWriter writer,Vatpoz d,Deklaracjevat l){
        Podatnik p = podatnikDAO.find(d.getPodatnik());
        PozycjeSzczegoloweVAT o = d.getPozycjeszczegolowe();
                                                  absText(writer, o.getPole47(), 490, 750);
                                                  absText(writer, o.getPole48(), 490, 726);
        absText(writer, o.getPole49(), 330, 678); absText(writer, o.getPole50(), 490, 678);
        absText(writer, o.getPole51(), 330, 654); absText(writer, o.getPole52(), 490, 654);
                                                  absText(writer, o.getPole53(), 490, 610);
                                                  absText(writer, o.getPole54(), 490, 586);
                                                  absText(writer, o.getPole55(), 490, 562);
                                                  absText(writer, o.getPole56(), 490, 515);
                                                  absText(writer, o.getPole57(), 490, 491);
                                                  absText(writer, o.getPole58(), 490, 467);
                                                  absText(writer, o.getPole59(), 490, 443);
                                                  absText(writer, o.getPole60(), 490, 419);
                                                  absText(writer, o.getPole61(), 490, 395);
        absText(writer, o.getPole62(), 190, 368); absText(writer, o.getPole63(), 330, 368); absText(writer, o.getPole64(), 490, 368);
                                                  absText(writer, o.getPole65(), 490, 347);
        absText(writer, p.getImie(), 80, 166); absText(writer, p.getNazwisko(), 210, 166);
        absText(writer, "91 8120976", 80, 142); 
        try{
            absText(writer, l.getDatazlozenia().toString(), 210, 142);  absText(writer, l.getSporzadzil(), 400, 150);
        } catch (Exception e){}
   }
    
    static String INPUTFILE = "c:/vat/pk1.pdf";
    static String INPUTFILEM = "c:/vat/vat7.pdf";
    
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
    
    private static void absText(PdfWriter writer,String text, int x, int y, int font) {
    try {
      PdfContentByte cb = writer.getDirectContent();
      BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
      cb.saveState();
      cb.beginText();
      cb.moveText(x, y);
      cb.setFontAndSize(bf, font);
      cb.showText(text);
      cb.endText();
      cb.restoreState();
    } catch (DocumentException | IOException e) {
    }
  }
     
    public static void main(String[] args) throws IOException, DocumentException{
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/vat/pk1.pdf"));
        document.addTitle("Polecenie księgowania");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk danych z PKPiR");
        document.addKeywords("PKPiR, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(helvetica,12);  
        Font fontM = new Font(helvetica,10);
        absText(writer, "8511005008", 70, 790);
        absText(writer, "03", 200, 745);
        absText(writer, "2013", 270, 745);
        absText(writer, "Pierwszy Urząd Skarbowy w Szczecinie", 70, 674);
        absText(writer, "X", 368, 674);
        absText(writer, "X", 482, 674);
        absText(writer, "X", 388, 624);
        absText(writer, "Imie i nazwisko", 70, 600);
        absText(writer, "Pesel", 368, 600);
        absText(writer, "Kraj", 70, 576);
        absText(writer, "Województwo", 170, 576);
        absText(writer, "Powiat", 400, 576);
        absText(writer, "Gmina", 70, 552);
        absText(writer, "Uica", 200, 552);
        absText(writer, "Nr d", 470, 552);
        absText(writer, "Nr l", 535, 552);
        absText(writer, "Miejscowość", 70, 528);
        absText(writer, "Kod", 300, 528);
        absText(writer, "Poczta", 380, 528);
        absText(writer, "Pole 20/000", 330, 486);
        absText(writer, "Pole 21/000", 330, 462);
        absText(writer, "Pole 22/000", 330, 438);
        absText(writer, "Pole 23/000", 330, 414);
        absText(writer, "Pole 24/000", 330, 390);
        absText(writer, "Pole 25/000", 330, 366); absText(writer, "Pole 26/000", 470, 366);
        absText(writer, "Pole 27/000", 330, 342); absText(writer, "Pole 28/000", 470, 342);
        absText(writer, "Pole 29/000", 330, 318); absText(writer, "Pole 30/000", 470, 318);
        absText(writer, "Pole 31/000", 330, 296);
        absText(writer, "Pole 32/000", 330, 272);
        absText(writer, "Pole 33/000", 330, 248); absText(writer, "Pole 34/000", 470, 248);
        absText(writer, "Pole 35/000", 330, 224); absText(writer, "Pole 36/000", 470, 224);
        absText(writer, "Pole 37/000", 330, 200); absText(writer, "Pole 38/000", 470, 200);
        absText(writer, "Pole 39/000", 330, 176); absText(writer, "Pole 40/000", 470, 176);
        absText(writer, "Pole 41/000", 330, 152); absText(writer, "Pole 42/000", 470, 152);
                                                  absText(writer, "Pole 43/000", 470, 128);
                                                  absText(writer, "Pole 44/000", 470, 104);
        absText(writer, "Pole 45/000", 330, 80);  absText(writer, "Pole 46/000", 470, 80);
        absText(writer, "Status 200", 490, 790);
        absText(writer, "Data potwierdzebia", 490, 780,6);
        absText(writer, "2013-05-05 124885", 490, 770,6);
        absText(writer, "Opis", 490, 760,6);
        document.newPage();
                                                  absText(writer, "Pole 47/000", 470, 750);
                                                  absText(writer, "Pole 48/000", 470, 726);
        absText(writer, "Pole 49/000", 330, 678); absText(writer, "Pole 50/000", 470, 678);
        absText(writer, "Pole 51/000", 330, 654); absText(writer, "Pole 52/000", 470, 654);
                                                  absText(writer, "Pole 53/000", 470, 610);
                                                  absText(writer, "Pole 54/000", 470, 586);
                                                  absText(writer, "Pole 55/000", 470, 562);
                                                  absText(writer, "Pole 56/000", 470, 515);
                                                  absText(writer, "Pole 57/000", 470, 491);
                                                  absText(writer, "Pole 58/000", 470, 467);
                                                  absText(writer, "Pole 59/000", 470, 443);
                                                  absText(writer, "Pole 60/000", 470, 419);
                                                  absText(writer, "Pole 61/000", 470, 395);
        absText(writer, "Pole 62/000", 190, 368); absText(writer, "Pole 63/000", 330, 368); absText(writer, "Pole 64/000", 470, 368);
                                                  absText(writer, "Pole 65/000", 470, 347);
        absText(writer, "Imie", 80, 166); absText(writer, "Nazwisko", 210, 166);
        absText(writer, "Telefon", 80, 142); absText(writer, "Data", 210, 142);  absText(writer, "Podpis", 400, 150);
        
        
        document.close();
        PdfReader reader = new PdfReader(INPUTFILE);
        reader.removeUsageRights();
        PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream("c:/vat/pka.pdf"));
        PdfContentByte underContent = pdfStamper.getUnderContent(1);
        Image image = Image.getInstance("c:/vat/VAT-71-p1.jpg");
        image.scaleToFit(610,850);
        image.setAbsolutePosition(0f, 0f);
        underContent.addImage(image);
        PdfContentByte overContent = pdfStamper.getOverContent(1);
        image = Image.getInstance("c:/vat/golab.png");
        image.scaleToFit(50,50);
        image.setAbsolutePosition(450f, 770f);
        overContent.addImage(image);
        underContent = pdfStamper.getUnderContent(2);
        image = Image.getInstance("c:/vat/VAT-72-p1.jpg");
        image.scaleToFit(610,850);
        image.setAbsolutePosition(0f, 0f);
        underContent.addImage(image);
        pdfStamper.close();
        reader.close();
        writer.close();
       
      }
}
