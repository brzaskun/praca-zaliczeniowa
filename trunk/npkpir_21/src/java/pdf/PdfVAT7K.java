/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.PozycjeSzczegoloweVAT;
import embeddable.Vatpoz;
import entity.Deklaracjevat;
import entity.Podatnik;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
public class PdfVAT7K extends PdfVAT7 implements Serializable{
    static String vat71kw = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_21/build/web/vat/VAT-7K1-p1.jpg";
    static String vat72kw = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_21/build/web/vat/VAT-7K2-p1.jpg";
            
            
    public static  void drukujVAT7K(Deklaracjevat dkl, Podatnik p) throws DocumentException, FileNotFoundException, IOException {
        System.out.println("Drukuje " + dkl);
        Vatpoz v = dkl.getSelected();
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_21/build/web/vat/vat7"+v.getPodatnik()+".pdf"));
        document.addTitle("Polecenie księgowania");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk deklaracji VAT " + dkl.getPodatnik());
        document.addKeywords("VAT-7, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(helvetica, 12);
        Font fontM = new Font(helvetica, 10);
        pierwszastrona(writer,v,dkl,p);
        document.newPage();
        drugastrona(writer,v,dkl,p);
        document.close();
        PdfReader reader = new PdfReader("C:/Users/Osito/Documents/NetBeansProjects/npkpir_21/build/web/vat/vat7"+v.getPodatnik()+".pdf");
        reader.removeUsageRights();
        PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_21/build/web/vat/vat7-13"+dkl.getPodatnik()+".pdf"));
        PdfContentByte underContent = pdfStamper.getUnderContent(1);
        Image image;
        image = Image.getInstance(vat71kw);
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        underContent.add(underContent);
        underContent.addImage(image);
        PdfContentByte overContent = pdfStamper.getOverContent(1);
        image = Image.getInstance(golab);
        image.scaleToFit(50,50);
        image.setAbsolutePosition(450f, 770f);
        overContent.addImage(image);
        underContent = pdfStamper.getUnderContent(2);
        image = Image.getInstance(vat72kw);
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        underContent.add(underContent);
        underContent.addImage(image);
        pdfStamper.close();
        reader.close();
        writer.close();
         try{
            String czek = dkl.getOrdzu();
            if(czek!=null){
                PdfORDZU.drukujORDZU(dkl, p);
                kombinuj(v.getPodatnik(),2);
            } else {
                kombinuj(v.getPodatnik(),1);
            }
        } catch(Exception e){
            kombinuj(v.getPodatnik(),1);
        }
         RequestContext.getCurrentInstance().update("formX");
    }
    
     public static void drukujwysVAT7K(Deklaracjevat dkl, Podatnik p) throws DocumentException, FileNotFoundException, IOException {
        System.out.println("Drukuje " + dkl);
        Vatpoz v = dkl.getSelected();
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_21/build/web/vat/vat7"+v.getPodatnik()+".pdf"));
        document.addTitle("Polecenie księgowania");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk deklaracji tetsowej VAT " + dkl.getPodatnik());
        document.addKeywords("VAT-7 test, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(helvetica, 12);
        Font fontM = new Font(helvetica, 10);
        pierwszastronawys(writer,v,dkl,p);
        document.newPage();
        drugastronawys(writer,v,dkl,p);
        document.close();
        PdfReader reader = new PdfReader("C:/Users/Osito/Documents/NetBeansProjects/npkpir_21/build/web/vat/vat7"+v.getPodatnik()+".pdf");
        reader.removeUsageRights();
        PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_21/build/web/vat/vat7-13"+dkl.getPodatnik()+".pdf"));
        PdfContentByte underContent = pdfStamper.getUnderContent(1);
        Image image;
        image = Image.getInstance(vat71kw);
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        underContent.add(underContent);
        underContent.addImage(image);
        PdfContentByte overContent = pdfStamper.getOverContent(1);
        image = Image.getInstance(golab);
        image.scaleToFit(50,50);
        image.setAbsolutePosition(450f, 770f);
        overContent.addImage(image);
        underContent = pdfStamper.getUnderContent(2);
        image = Image.getInstance(vat72kw);
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        underContent.add(underContent);
        underContent.addImage(image);
        pdfStamper.close();
        reader.close();
        writer.close();
         try{
            String czek = dkl.getOrdzu();
            if(czek!=null){
                PdfORDZU.drukujORDZU(dkl, p);
                kombinuj(v.getPodatnik(),2);
            } else {
                kombinuj(v.getPodatnik(),1);
            }
        } catch(Exception e){
            kombinuj(v.getPodatnik(),1);
        }
         RequestContext.getCurrentInstance().update("formX");
    }
  
    
   private static void pierwszastrona(PdfWriter writer,Vatpoz d,Deklaracjevat l, Podatnik p){
        PozycjeSzczegoloweVAT o = d.getPozycjeszczegolowe();
        absText(writer, p.getNip(), 70, 790);
        if(l.isMiesiackwartal()==true){
            absText(writer, l.getNrkwartalu(), 200, 745);
        } else {
            absText(writer, d.getMiesiac(), 200, 745);
        }
        absText(writer, d.getRok(), 270, 745);
        absText(writer, d.getNazwaurzedu(), 70, 674);
        if(d.getCelzlozenia().equals("1")){
            absText(writer, "X", 368, 674);
        } else {
            absText(writer, "X", 482, 674);
        }
        absText(writer, "X", 388, 604);
        absText(writer, p.getImie()+ " "+p.getNazwisko(), 70, 578);
        absText(writer, p.getPesel(), 368, 578);
        absText(writer, "Polska", 70, 535);
        absText(writer, p.getWojewodztwo(), 170, 535);
        absText(writer, p.getPowiat(), 400, 535);
        absText(writer, p.getGmina(), 70, 509);
        absText(writer, p.getUlica(), 200, 509);
        absText(writer, p.getNrdomu(), 470, 509);
        absText(writer, p.getNrlokalu(), 535, 509);
        absText(writer, p.getMiejscowosc(), 70, 487);
        absText(writer, p.getKodpocztowy(), 300, 487);
        absText(writer, p.getPoczta(), 380, 487,"f");
        absText(writer, o.getPole20(), 330, 439,"f");
        absText(writer, o.getPole21(), 330, 415,"f");
        absText(writer, o.getPole22(), 330, 391,"f");
        absText(writer, o.getPole23(), 330, 367,"f");
        absText(writer, o.getPole24(), 330, 343,"f");
        absText(writer, o.getPole25(), 330, 319,"f"); absText(writer, o.getPole26(), 490, 319,"f");
        absText(writer, o.getPole27(), 330, 297,"f"); absText(writer, o.getPole28(), 490, 297,"f");
        absText(writer, o.getPole29(), 330, 273,"f"); absText(writer, o.getPole30(), 490, 273,"f");
        absText(writer, o.getPole31(), 330, 249,"f");
        absText(writer, o.getPole32(), 330, 225,"f");
        absText(writer, o.getPole33(), 330, 201,"f"); absText(writer, o.getPole34(), 490, 201,"f");
        absText(writer, o.getPole35(), 330, 177,"f"); absText(writer, o.getPole36(), 490, 177,"f");
        absText(writer, o.getPole37(), 330, 153,"f"); absText(writer, o.getPole37(), 490, 153,"f");
        absText(writer, o.getPole39(), 330, 129,"f"); absText(writer, o.getPole40(), 490, 129,"f");
        absText(writer, o.getPole41(), 330, 105,"f"); absText(writer, o.getPole42(), 490, 105,"f");
                                                  absText(writer, o.getPole43(), 490, 82,"f");
                                                  absText(writer, o.getPole44(), 490, 59,"f");
        
        absText(writer, "Status "+l.getStatus(), 490, 790);
        if(l.getUpo().contains("system testowy")){
            absText(writer, "DEKLARACJA TESTOWA ", 490, 780,6);
        } else {
            absText(writer, "Data potwierdzenia ", 490, 780,6);
        }
        try{
        absText(writer, l.getDataupo().toString(), 490, 770,6);
        } catch (Exception e){}
        absText(writer, l.getOpis(), 460, 760,6);
        absText(writer, "Nr potwierdzenia:", 460, 750,6);
        absText(writer, l.getIdentyfikator(), 460, 740,6);
   }
   
   private static void drugastrona(PdfWriter writer,Vatpoz d,Deklaracjevat l, Podatnik p){
        PozycjeSzczegoloweVAT o = d.getPozycjeszczegolowe();
        absText(writer, o.getPole45(), 330, 790,"f");  absText(writer, o.getPole46(), 490, 790,"f");
                                                  absText(writer, o.getPole47(), 490, 726,"f");
                                                  absText(writer, o.getPole48(), 490, 702,"f");
        absText(writer, o.getPole49(), 330, 654,"f"); absText(writer, o.getPole50(), 490, 654,"f");
        absText(writer, o.getPole51(), 330, 630,"f"); absText(writer, o.getPole52(), 490, 630,"f");
                                                  absText(writer, o.getPole53(), 490, 586,"f");
                                                  absText(writer, o.getPole54(), 490, 562,"f");
                                                  absText(writer, o.getPole55(), 490, 538,"f");
                                                  absText(writer, o.getPole56(), 490, 491,"f");
                                                  absText(writer, o.getPole57(), 490, 467,"f");
                                                  absText(writer, o.getPole58(), 490, 443,"f");
                                                  absText(writer, o.getPole59(), 490, 419,"f");
                                                  absText(writer, o.getPole60(), 490, 495,"f");
                                                  absText(writer, o.getPole61(), 490, 368,"f");
        absText(writer, o.getPole62(), 190, 344,"f"); absText(writer, o.getPole63(), 330, 344,"f"); absText(writer, o.getPole64(), 490, 344,"f");
                                                  absText(writer, o.getPole65(), 490, 323,"f");
        absText(writer, p.getImie(), 80, 178); absText(writer, p.getNazwisko(), 210, 178);
        absText(writer, "91 8120976", 80, 154); 
        try{
            absText(writer, l.getDatazlozenia().toString(), 210, 154);  absText(writer, l.getSporzadzil(), 400, 165);
        } catch (Exception e){}
   }
    
     private static void pierwszastronawys(PdfWriter writer,Vatpoz d,Deklaracjevat l, Podatnik p){
        PozycjeSzczegoloweVAT o = d.getPozycjeszczegolowe();
        absText(writer, p.getNip(), 70, 790);
        if(l.isMiesiackwartal()==true){
            absText(writer, l.getNrkwartalu(), 200, 745);
        } else {
            absText(writer, d.getMiesiac(), 200, 745);
        }
        absText(writer, d.getRok(), 270, 745);
        absText(writer, d.getNazwaurzedu(), 70, 674);
        if(d.getCelzlozenia().equals("1")){
            absText(writer, "X", 368, 674);
        } else {
            absText(writer, "X", 482, 674);
        }
        absText(writer, "X", 388, 604);
        absText(writer, p.getImie()+ " "+p.getNazwisko(), 70, 578);
        absText(writer, p.getPesel(), 368, 578);
        absText(writer, "Polska", 70, 535);
        absText(writer, p.getWojewodztwo(), 170, 535);
        absText(writer, p.getPowiat(), 400, 535);
        absText(writer, p.getGmina(), 70, 509);
        absText(writer, p.getUlica(), 200, 509);
        absText(writer, p.getNrdomu(), 470, 509);
        absText(writer, p.getNrlokalu(), 535, 509);
        absText(writer, p.getMiejscowosc(), 70, 487);
        absText(writer, p.getKodpocztowy(), 300, 487);
        absText(writer, p.getPoczta(), 380, 487,"f");
        absText(writer, o.getPole20(), 330, 439,"f");
        absText(writer, o.getPole21(), 330, 415,"f");
        absText(writer, o.getPole22(), 330, 391,"f");
        absText(writer, o.getPole23(), 330, 367,"f");
        absText(writer, o.getPole24(), 330, 343,"f");
        absText(writer, o.getPole25(), 330, 319,"f"); absText(writer, o.getPole26(), 490, 319,"f");
        absText(writer, o.getPole27(), 330, 297,"f"); absText(writer, o.getPole28(), 490, 297,"f");
        absText(writer, o.getPole29(), 330, 273,"f"); absText(writer, o.getPole30(), 490, 273,"f");
        absText(writer, o.getPole31(), 330, 249,"f");
        absText(writer, o.getPole32(), 330, 225,"f");
        absText(writer, o.getPole33(), 330, 201,"f"); absText(writer, o.getPole34(), 490, 201,"f");
        absText(writer, o.getPole35(), 330, 177,"f"); absText(writer, o.getPole36(), 490, 177,"f");
        absText(writer, o.getPole37(), 330, 153,"f"); absText(writer, o.getPole37(), 490, 153,"f");
        absText(writer, o.getPole39(), 330, 129,"f"); absText(writer, o.getPole40(), 490, 129,"f");
        absText(writer, o.getPole41(), 330, 105,"f"); absText(writer, o.getPole42(), 490, 105,"f");
                                                  absText(writer, o.getPole43(), 490, 82,"f");
                                                  absText(writer, o.getPole44(), 490, 59,"f");
        absText(writer, "Status: do wysyłki", 490, 790);
        
   }
   
   private static void drugastronawys(PdfWriter writer,Vatpoz d,Deklaracjevat l, Podatnik p){
        PozycjeSzczegoloweVAT o = d.getPozycjeszczegolowe();
        absText(writer, o.getPole45(), 330, 790,"f");  absText(writer, o.getPole46(), 490, 790,"f");
                                                  absText(writer, o.getPole47(), 490, 726,"f");
                                                  absText(writer, o.getPole48(), 490, 702,"f");
        absText(writer, o.getPole49(), 330, 654,"f"); absText(writer, o.getPole50(), 490, 654,"f");
        absText(writer, o.getPole51(), 330, 630,"f"); absText(writer, o.getPole52(), 490, 630,"f");
                                                  absText(writer, o.getPole53(), 490, 586,"f");
                                                  absText(writer, o.getPole54(), 490, 562,"f");
                                                  absText(writer, o.getPole55(), 490, 538,"f");
                                                  absText(writer, o.getPole56(), 490, 491,"f");
                                                  absText(writer, o.getPole57(), 490, 467,"f");
                                                  absText(writer, o.getPole58(), 490, 443,"f");
                                                  absText(writer, o.getPole59(), 490, 419,"f");
                                                  absText(writer, o.getPole60(), 490, 495,"f");
                                                  absText(writer, o.getPole61(), 490, 368,"f");
        absText(writer, o.getPole62(), 190, 344,"f"); absText(writer, o.getPole63(), 330, 344,"f"); absText(writer, o.getPole64(), 490, 344,"f");
                                                  absText(writer, o.getPole65(), 490, 323,"f");
        absText(writer, p.getImie(), 80, 178); absText(writer, p.getNazwisko(), 210, 178);
        absText(writer, "91 8120976", 80, 154); 
   }
   
 
   
   
     
//    public static void main(String[] args) throws IOException, DocumentException{
//        Document document = new Document();
//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/vat/pk1.pdf"));
//        document.addTitle("Polecenie księgowania");
//        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
//        document.addSubject("Wydruk danych z PKPiR");
//        document.addKeywords("PKPiR, PDF");
//        document.addCreator("Grzegorz Grzelczyk");
//        document.open();
//        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
//        Font font = new Font(helvetica,12);  
//        Font fontM = new Font(helvetica,10);
//        absText(writer, "8511005008", 70, 790);
//        absText(writer, "03", 200, 745);
//        absText(writer, "2013", 270, 745);
//        absText(writer, "Pierwszy Urząd Skarbowy w Szczecinie", 70, 674);
//        absText(writer, "X", 368, 674);
//        absText(writer, "X", 482, 674);
//        absText(writer, "X", 388, 602);
//        absText(writer, "Imie i nazwisko", 70, 578);
//        absText(writer, "Pesel", 368, 578);
//        absText(writer, "Kraj", 70, 534);
//        absText(writer, "Województwo", 170, 534);
//        absText(writer, "Powiat", 400, 534);
//        absText(writer, "Gmina", 70, 508);
//        absText(writer, "Uica", 200, 508);
//        absText(writer, "Nr d", 470, 508);
//        absText(writer, "Nr l", 535, 508);
//        absText(writer, "Miejscowość", 70, 486);
//        absText(writer, "Kod", 300, 486);
//        absText(writer, "Poczta", 380, 486);
//        absText(writer, "1234567", 330, 438);
//        absText(writer, "123456", 330, 414);
//        absText(writer, "12345", 330, 390);
//        absText(writer, "1234", 330, 366);
//        absText(writer, "123", 330, 342);
//        absText(writer, "Pole 25/000", 330, 318); absText(writer, "Pole 26/000", 470, 318);
//        absText(writer, "Pole 27/000", 330, 296); absText(writer, "Pole 28/000", 470, 296);
//        absText(writer, "Pole 29/000", 330, 272); absText(writer, "Pole 30/000", 470, 272);
//        absText(writer, "Pole 31/000", 330, 248);
//        absText(writer, "Pole 32/000", 330, 224);
//        absText(writer, "Pole 33/000", 330, 200); absText(writer, "Pole 34/000", 470, 200);
//        absText(writer, "Pole 35/000", 330, 176); absText(writer, "Pole 36/000", 470, 176);
//        absText(writer, "Pole 37/000", 330, 152); absText(writer, "Pole 38/000", 470, 152);
//        absText(writer, "Pole 39/000", 330, 128); absText(writer, "Pole 40/000", 470, 128);
//        absText(writer, "Pole 41/000", 330, 104); absText(writer, "Pole 42/000", 470, 104);
//                                                  absText(writer, "Pole 43/000", 470, 81);
//                                                  absText(writer, "Pole 44/000", 470, 58);
//
//        absText(writer, "Status 200", 490, 790);
//        absText(writer, "Data potwierdzebia", 490, 780,6);
//        absText(writer, "2013-05-05 124885", 490, 770,6);
//        absText(writer, "Opis", 490, 760,6);
//        absText(writer, "Nr potwierdzenia:", 460, 750,6);
//        absText(writer, "ijijiiijiiji", 460, 740,6);
//        document.newPage();
//        absText(writer, "Pole 45/000", 330, 790);  absText(writer, "Pole 46/000", 470, 790);
//                                                  absText(writer, "Pole 47/000", 470, 726);
//                                                  absText(writer, "Pole 48/000", 470, 702);
//        absText(writer, "Pole 49/000", 330, 654); absText(writer, "Pole 50/000", 470, 654);
//        absText(writer, "Pole 51/000", 330, 630); absText(writer, "Pole 52/000", 470, 630);
//                                                  absText(writer, "Pole 53/000", 470, 586);
//                                                  absText(writer, "Pole 54/000", 470, 562);
//                                                  absText(writer, "Pole 55/000", 470, 538);
//                                                  absText(writer, "Pole 56/000", 470, 491);
//                                                  absText(writer, "Pole 57/000", 470, 467);
//                                                  absText(writer, "Pole 58/000", 470, 443);
//                                                  absText(writer, "Pole 59/000", 470, 419);
//                                                  absText(writer, "Pole 60/000", 470, 395);
//                                                  absText(writer, "Pole 61/000", 470, 368);
//        absText(writer, "Pole 62/000", 190, 344); absText(writer, "Pole 63/000", 330, 344); absText(writer, "Pole 64/000", 470, 344);
//                                                  absText(writer, "Pole 65/000", 470, 323);
//        absText(writer, "Imie", 80, 178); absText(writer, "Nazwisko", 210, 178);
//        absText(writer, "Telefon", 80, 154); absText(writer, "Data", 210, 154);  absText(writer, "Podpis", 400, 165);
//        
//        
//        document.close();
//        PdfReader reader = new PdfReader(INPUTFILE);
//        reader.removeUsageRights();
//        PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream("c:/vat/pka.pdf"));
//        PdfContentByte underContent = pdfStamper.getUnderContent(1);
//        Image image = Image.getInstance("c:/vat/VAT-7K1-p1.jpg");
//        image.scaleToFit(610,850);
//        image.setAbsolutePosition(0f, 0f);
//        underContent.addImage(image);
//        PdfContentByte overContent = pdfStamper.getOverContent(1);
//        image = Image.getInstance("c:/vat/golab.png");
//        image.scaleToFit(50,50);
//        image.setAbsolutePosition(450f, 770f);
//        overContent.addImage(image);
//        underContent = pdfStamper.getUnderContent(2);
//        image = Image.getInstance("c:/vat/VAT-7K2-p1.jpg");
//        image.scaleToFit(610,850);
//        image.setAbsolutePosition(0f, 0f);
//        underContent.addImage(image);
//        pdfStamper.close();
//        reader.close();
//        writer.close();
       
//      }
    private static void kombinuj(String kto, int ile) {
          try {
            List<String> files = new ArrayList<>();
            if(ile==1){
                files.add("C:/Users/Osito/Documents/NetBeansProjects/npkpir_21/build/web/vat/vat7-13"+kto+".pdf");
            } else {
                files.add("C:/Users/Osito/Documents/NetBeansProjects/npkpir_21/build/web/vat/vat7-13"+kto+".pdf");
                files.add("C:/Users/Osito/Documents/NetBeansProjects/npkpir_21/build/web/vat/ord-zu"+kto+".pdf");
            }
            Document PDFCombineUsingJava = new Document();
            PdfCopy copy = new PdfCopy(PDFCombineUsingJava, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_21/build/web/vat/VAT7Comb"+kto+".pdf"));
            PDFCombineUsingJava.open();
            PdfReader ReadInputPDF;
            int number_of_pages;
            for(String p : files) {
                ReadInputPDF = new PdfReader(p);
                number_of_pages = ReadInputPDF.getNumberOfPages();
                for (int page = 0; page < number_of_pages;) {
                    copy.addPage(copy.getImportedPage(ReadInputPDF, ++page));
                }
            }
            PDFCombineUsingJava.close();
        } catch (Exception i) {
            System.out.println(i);
        }
    }
     
          
}
