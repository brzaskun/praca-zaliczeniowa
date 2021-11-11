/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.TKodUS;
import f.F;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import msg.Msg;
import pl.gov.crd.wzor._2021._03._04._10477.Deklaracja;
import pl.gov.crd.wzor._2021._03._04._10477.TIdentyfikatorOsobyNiefizycznej;
import pl.gov.crd.wzor._2021._03._04._10477.TNaglowek;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2020._07._06.ed.definicjetypy.TIdentyfikatorOsobyFizycznej1;

/**
 *
 * @author Osito
 */
public class PdfPIT4 {

    public static final String OUTPUTFILE = "pit-4F.pdf";

    public static String drukuj(pl.gov.crd.wzor._2021._03._04._10477.Deklaracja deklaracja, String sporzadzajacy) {
        String nazwapliku = null;
        if (deklaracja != null) {
            try {
                ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String realPath = ctx.getRealPath("/")+"resources\\pdf\\";
                Document document = new Document();
                ByteArrayOutputStream pdfSM = new ByteArrayOutputStream();
                PdfWriter writer = PdfWriter.getInstance(document, pdfSM);
                document.addTitle("PIT11 V27");
                document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
                document.addSubject("Wydruk deklaracji pracowniczej PIT-11");
                document.addKeywords("PDF");
                document.addCreator("Grzegorz Grzelczyk");
                document.open();
                BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
                Font font = new Font(helvetica, 12);
                Font fontM = new Font(helvetica, 10);
                TNaglowek naglowek = deklaracja.getNaglowek();
                Deklaracja.Podmiot1 podmiot1 = deklaracja.getPodmiot1();
                if (podmiot1.getOsobaNiefizyczna() !=null) {
                    TIdentyfikatorOsobyNiefizycznej osobaNiefizyczna = podmiot1.getOsobaNiefizyczna();
                    absText(writer, osobaNiefizyczna.getNIP(), 150, 790);
                    absText(writer, "X", 133, 431);
                    absText(writer, osobaNiefizyczna.getPelnaNazwa(), 133, 407);
                } else if (podmiot1.getOsobaFizyczna() !=null) {
                    TIdentyfikatorOsobyFizycznej1 osobaFizyczna = podmiot1.getOsobaFizyczna();
                    absText(writer, osobaFizyczna.getNIP(), 150, 790);
                    absText(writer, "X", 389, 431);
                    absText(writer, osobaFizyczna.getNazwisko()+" "+osobaFizyczna.getImiePierwsze()+" "+osobaFizyczna.getDataUrodzenia().toString(), 133, 377);
                }
                absText(writer, naglowek.getRok().toString(), 300, 705);
                absText(writer, TKodUS.getNazwaUrzedu(naglowek.getKodUrzedu()), 120, 505);
                if (naglowek.getCelZlozenia().getValue()==(byte)1) {
                    absText(writer, "X", 214, 483);
                } else if (naglowek.getCelZlozenia().getValue()==(byte)2) {
                    absText(writer, "X", 328, 483);
                }
                //tutaj podatnik pole P_11 jest w szczegolowych
                Deklaracja.PozycjeSzczegolowe pse = deklaracja.getPozycjeSzczegolowe();
                if (pse.getP11()==(byte)1) {
                    absText(writer, "X", 990, 326);
                } else if (pse.getP11()==(byte)2) {
                    absText(writer, "X", 351, 326);
                }
                Deklaracja.Podmiot2 prac = deklaracja.getPodmiot2();
                Deklaracja.Podmiot2.OsobaFizyczna osobaFizyczna = prac.getOsobaFizyczna();
                if (osobaFizyczna.getPESEL()!=null) {
                    absText(writer, osobaFizyczna.getPESEL(), 100, 292);
                    nazwapliku = osobaFizyczna.getPESEL();
                } else {
                    absText(writer, osobaFizyczna.getNIP(), 100, 292);
                    nazwapliku = osobaFizyczna.getNIP();
                }
                if (osobaFizyczna.getNrId()!=null) {
                    absText(writer, osobaFizyczna.getNrId().getValue(), 350, 292);
                    nazwapliku = osobaFizyczna.getNrId().getValue();
//			<xsd:enumeration value="1">
//                        <xsd:documentation>numer identyfikacyjny TIN</xsd:documentation>
//			<xsd:enumeration value="2">
//                        <xsd:documentation>numer ubezpieczeniowy</xsd:documentation>
//			<xsd:enumeration value="3">
//                        <xsd:documentation>paszport</xsd:documentation>
//			<xsd:enumeration value="4">
//                        <xsd:documentation>urzędowy dokument stwierdzający tożsamość</xsd:documentation>
//			<xsd:enumeration value="8">
//                        <xsd:documentation>inny rodzaj identyfikacji podatkowej</xsd:documentation>
//			<xsd:enumeration value="9">
//                        <xsd:documentation>inny dokument potwierdzający tożsamość</xsd:documentation>
                    if (osobaFizyczna.getRodzajNrId().getValue()==(byte)1) {
                        absText(writer, "numer identyfikacyjny TIN", 100, 266);
                    } else if (osobaFizyczna.getRodzajNrId().getValue()==(byte)2) {
                        absText(writer, "numer ubezpieczeniowy", 100, 266);
                    } else if (osobaFizyczna.getRodzajNrId().getValue()==(byte)3) {
                        absText(writer, "paszport", 100, 266);
                    } else if (osobaFizyczna.getRodzajNrId().getValue()==(byte)4) {
                        absText(writer, "urzędowy dokument stwierdzający tożsamość", 100, 266);
                    } else if (osobaFizyczna.getRodzajNrId().getValue()==(byte)8) {
                        absText(writer, "inny rodzaj identyfikacji podatkowej", 100, 266);
                    } else if (osobaFizyczna.getRodzajNrId().getValue()==(byte)9) {
                        absText(writer, "inny dokument potwierdzający tożsamość", 100, 266);
                    }
                    absText(writer, "Zagraniczny numer", 350, 292);
                    absText(writer, osobaFizyczna.getKodKrajuWydania().getValue().name(), 370, 266);
                }
                absText(writer, osobaFizyczna.getNazwisko(), 100, 240);
                absText(writer, osobaFizyczna.getImiePierwsze(), 370, 240);
                absText(writer, osobaFizyczna.getDataUrodzenia().toString(), 70, 216);
                Deklaracja.Podmiot2.AdresZamieszkania adresZamieszkania = prac.getAdresZamieszkania();
                absText(writer, adresZamieszkania.getKodKraju().getValue().name(), 250, 216);
                absText(writer, adresZamieszkania.getWojewodztwo(), 430, 216);
                absText(writer, adresZamieszkania.getPowiat(), 100, 192);
                absText(writer, adresZamieszkania.getGmina(), 410, 192);
                absText(writer, adresZamieszkania.getUlica().getValue(), 100, 168);
                absText(writer, adresZamieszkania.getNrDomu().getValue(), 470, 168);
                absText(writer, adresZamieszkania.getNrLokalu().getValue(), 535, 168);
                absText(writer, adresZamieszkania.getMiejscowosc().getValue(), 100, 144);
                absText(writer, adresZamieszkania.getKodPocztowy().getValue(), 470, 144);
                Deklaracja.PozycjeSzczegolowe ps = deklaracja.getPozycjeSzczegolowe();
                if (ps.getP28()!=null) {
                    if (ps.getP28()==(byte)1) {
                        absText(writer, "X", 56, 85);
                    } else if (ps.getP28()==(byte)2) {
                        absText(writer, "X", 329, 85);
                    } else if (ps.getP28()==(byte)3) {
                        absText(writer, "X", 56, 69);
                    } else if (ps.getP28()==(byte)3) {
                        absText(writer, "X", 329, 68);
                    }
                }
                absText(writer, "Data potwierdzebia", 490, 780, 6);
                absText(writer, "2013-05-05 124885", 490, 770, 6);
                absText(writer, "Opis", 490, 760, 6);
                absText(writer, "Nr potwierdzenia:", 460, 750, 6);
                absText(writer, "ijijiiijiiji", 460, 740, 6);
                document.newPage();
                absTextW(writer, pobierz(ps.getP29()), 245, 700);
                absTextW(writer, pobierz(ps.getP30()), 315, 700);
                absTextW(writer, pobierz(ps.getP31()), 380, 690);
                absTextW(writer, pobierz(ps.getP32()), 450, 690);
                absTextW(writer, pobierzI(ps.getP33()), 520, 690);
                absTextW(writer, pobierz(ps.getP34()), 245, 660);
                absTextW(writer, pobierz(ps.getP35()), 315, 660);
                absTextW(writer, pobierz(ps.getP36()), 245, 635);
                absTextW(writer, pobierz(ps.getP37()), 315, 635);
                absTextW(writer, pobierz(ps.getP38()), 380, 610);
                absTextW(writer, pobierz(ps.getP39()), 450, 610);
                absTextW(writer, pobierzI(ps.getP40()), 520, 610);
                absTextW(writer, pobierz(ps.getP41()), 245, 610);
                absTextW(writer, pobierz(ps.getP42()), 315, 610);
                absTextW(writer, pobierz(ps.getP43()), 245, 580);
                absTextW(writer, pobierz(ps.getP44()), 380, 580);
                absTextW(writer, pobierz(ps.getP45()), 450, 580);
                absTextW(writer, pobierzI(ps.getP46()), 520, 580);
                
                absTextW(writer, pobierz(ps.getP47()), 245, 540);
                absTextW(writer, pobierz(ps.getP48()), 315, 540);
                absTextW(writer, pobierz(ps.getP49()), 380, 540);
                absTextW(writer, pobierzI(ps.getP50()), 520, 540);
                absTextW(writer, pobierz(ps.getP51()), 245, 500);
                absTextW(writer, pobierz(ps.getP52()), 315, 500);
                absTextW(writer, pobierz(ps.getP53()), 380, 500);
                absTextW(writer, pobierzI(ps.getP54()), 520, 500);
                absTextW(writer, pobierz(ps.getP55()), 245, 460);
                absTextW(writer, pobierz(ps.getP56()), 315, 460);
                absTextW(writer, pobierz(ps.getP57()), 380, 460);
                absTextW(writer, pobierzI(ps.getP58()), 520, 460);
                
                absTextW(writer, pobierz(ps.getP59()), 245, 430);
                absTextW(writer, pobierz(ps.getP60()), 380, 420);
                absTextW(writer, pobierzI(ps.getP61()), 520, 420);
                absTextW(writer, pobierz(ps.getP62()), 245, 408);
                absTextW(writer, pobierz(ps.getP63()), 315, 408);
                absTextW(writer, pobierz(ps.getP64()), 245, 383);
                absTextW(writer, pobierz(ps.getP65()), 380, 383);
                absTextW(writer, pobierzI(ps.getP66()), 520, 383);
                absTextW(writer, pobierz(ps.getP67()), 245, 358);
                absTextW(writer, pobierz(ps.getP68()), 380, 358);
                absTextW(writer, pobierzI(ps.getP69()), 520, 358);
                absTextW(writer, pobierz(ps.getP70()), 245, 332);
                absTextW(writer, pobierz(ps.getP71()), 315, 332);
                absTextW(writer, pobierz(ps.getP72()), 380, 332);
                absTextW(writer, pobierz(ps.getP73()), 450, 332);
                absTextW(writer, pobierzI(ps.getP74()), 520, 332);
                absTextW(writer, pobierz(ps.getP75()), 520, 305);
                absTextW(writer, pobierz(ps.getP76()), 520, 278);
                absTextW(writer, pobierz(ps.getP77()), 520, 251);
                absTextW(writer, pobierz(ps.getP78()), 520, 226);
                absTextW(writer, pobierz(ps.getP79()), 520, 198);
                absTextW(writer, pobierz(ps.getP80()), 520, 168);
                absTextW(writer, pobierz(ps.getP81()), 520, 90);
                absText(writer, ps.getP82(), 120, 65);
                absTextW(writer, pobierz(ps.getP83()), 520, 65);
                
                document.newPage();
                absText(writer, ps.getP84(), 120, 790);
                absTextW(writer, pobierz(ps.getP85()), 520, 790);
                absText(writer, ps.getP86(), 120, 767);
                absTextW(writer, pobierz(ps.getP87()), 520, 767);
                absTextW(writer, pobierz(ps.getP88()), 520, 740);
                absTextW(writer, pobierz(ps.getP89()), 520, 683);
                absTextW(writer, pobierz(ps.getP90()), 520, 658);
                absTextW(writer, pobierz(ps.getP91()), 520, 632);
                absTextW(writer, pobierz(ps.getP92()), 520, 608);
                absTextW(writer, pobierz(ps.getP93()), 520, 583);
                absTextW(writer, pobierz(ps.getP94()), 520, 558);
                absTextW(writer, pobierz(ps.getP95()), 520, 532);
                if (ps.getP96()==(byte)1) {
                    absText(writer, "X", 251, 502);
                } else if (ps.getP96()==(byte)2) {
                    absText(writer, "X", 342, 502);
                }
                
                absText(writer, "Grzegorz Grzelczyk", 100, 440);
                absText(writer, sporzadzajacy, 100, 370);
                document.newPage();
                absText(writer, " ", 80, 166);
                document.close();
                writer.close();
                byte[] pdfoutput = pdfSM.toByteArray();
                PdfReader reader = new PdfReader(pdfoutput);
                reader.removeUsageRights();
                nazwapliku = nazwapliku+"R"+naglowek.getRok().toString()+"_PIT11";
                PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream(realPath+nazwapliku));
                PdfContentByte underContent = pdfStamper.getUnderContent(1);
                Image image = Image.getInstance(realPath+"PIT-111.png");
                image.scaleToFit(610, 850);
                image.setAbsolutePosition(0f, 0f);
                underContent.addImage(image);
                underContent = pdfStamper.getUnderContent(2);
                image = Image.getInstance(realPath+"PIT-112.png");
                image.scaleToFit(610, 850);
                image.setAbsolutePosition(0f, 0f);
                underContent.addImage(image);
                underContent = pdfStamper.getUnderContent(3);
                image = Image.getInstance(realPath+"PIT-113.png");
                image.scaleToFit(610, 850);
                image.setAbsolutePosition(0f, 0f);
                underContent.addImage(image);
                underContent = pdfStamper.getUnderContent(4);
                image = Image.getInstance(realPath+"PIT-114.png");
                image.scaleToFit(610, 850);
                image.setAbsolutePosition(0f, 0f);
                underContent.addImage(image);
                underContent.closePath();
                pdfStamper.close();
                reader.close();
                Msg.msg("Wydrukowano deklaracje");
            } catch (Exception ex) {
                Logger.getLogger(PdfPIT4.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Msg.msg("e", "Brak deklaracji");
        }
        return nazwapliku;
    }

    public static void main(String[] args) throws IOException, DocumentException {
        String ynputfile = "C:\\Users\\Osito\\Downloads\\pit-4.pdf";
        String alputfile = "C:\\Users\\Osito\\Downloads\\pit-4a.pdf";
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(ynputfile));
        document.addTitle("Polecenie księgowania");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk danych z PKPiR");
        document.addKeywords("PKPiR, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(helvetica, 12);
        Font fontM = new Font(helvetica, 10);
        absText(writer, "8511005008", 150, 790);
        absText(writer, "2020", 300, 705);
        absText(writer, "Pierwszy Urząd Skarbowy w Szczecinie", 120, 545);
        absText(writer, "X", 215, 519);
        absText(writer, "X", 329, 519);
        absText(writer, "X", 65, 492);
        absText(writer, "X", 299, 492);
        absText(writer, "X", 133, 432);
        absText(writer, "X", 388, 432);
        absText(writer, "Nazwa pełna", 133, 407);
        
        absText(writer, "10/000", 180, 340);
        absText(writer, "11/000", 250, 340);
        absText(writer, "12/000", 320, 340);
        absText(writer, "13/000", 390, 340);
        absText(writer, "14/000", 458, 340);
        absText(writer, "15/000", 528, 340);
        absText(writer, "16/000", 180, 313);
        absText(writer, "17/000", 250, 313);
        absText(writer, "18/000", 320, 313);
        absText(writer, "29/000", 390, 313);
        absText(writer, "20/000", 458, 313);
        absText(writer, "21/000", 528, 313);
        
        absText(writer, "22/000", 180, 280);
        absText(writer, "23/000", 250, 280);
        absText(writer, "24/000", 320, 280);
        absText(writer, "25/000", 390, 280);
        absText(writer, "26/000", 458, 280);
        absText(writer, "27/000", 528, 280);
        absText(writer, "28/000", 180, 253);
        absText(writer, "29/000", 250, 253);
        absText(writer, "30/000", 320, 253);
        absText(writer, "31/000", 390, 253);
        absText(writer, "32/000", 458, 253);
        absText(writer, "33/000", 528, 253);
        
        absText(writer, "46/000", 180, 142);
        absText(writer, "47/000", 250, 142);
        absText(writer, "48/000", 320, 142);
        absText(writer, "49/000", 390, 142);
        absText(writer, "50/000", 458, 142);
        absText(writer, "51/000", 528, 142);
        absText(writer, "52/000", 180, 108);
        absText(writer, "53/000", 250, 108);
        absText(writer, "54/000", 320, 108);
        absText(writer, "55/000", 390, 108);
        absText(writer, "56/000", 458, 108);
        absText(writer, "57/000", 528, 108);
        
        
        document.newPage();
        absText(writer, "70/000", 180, 705);
        absText(writer, "71/000", 250, 705);
        absText(writer, "72/000", 320, 705);
        absText(writer, "73/000", 390, 705);
        absText(writer, "74/000", 458, 705);
        absText(writer, "75/000", 528, 705);
        absText(writer, "76/000", 180, 670);
        absText(writer, "77/000", 250, 670);
        absText(writer, "78/000", 320, 670);
        absText(writer, "79/000", 390, 670);
        absText(writer, "80/000", 458, 670);
        absText(writer, "81/000", 528, 670);
        
        absText(writer, "122/000", 180, 340);
        absText(writer, "123/000", 250, 340);
        absText(writer, "124/000", 320, 340);
        absText(writer, "125/000", 390, 340);
        absText(writer, "126/000", 458, 340);
        absText(writer, "127/000", 528, 340);
        absText(writer, "128/000", 180, 305);
        absText(writer, "129/000", 250, 305);
        absText(writer, "130/000", 320, 305);
        absText(writer, "131/000", 390, 305);
        absText(writer, "132/000", 458, 305);
        absText(writer, "133/000", 528, 305);
        
        absText(writer, "146/000", 180, 192);
        absText(writer, "147/000", 250, 192);
        absText(writer, "148/000", 320, 192);
        absText(writer, "149/000", 390, 192);
        absText(writer, "150/000", 458, 192);
        absText(writer, "151/000", 528, 192);
        absText(writer, "152/000", 180, 156);
        absText(writer, "153/000", 250, 156);
        absText(writer, "154/000", 320, 156);
        absText(writer, "155/000", 390, 156);
        absText(writer, "156/000", 458, 156);
        absText(writer, "157/000", 528, 156);

        document.newPage();
        absText(writer, "158 wyjaśnienie różmnic", 70, 720);
        absText(writer, "171 Imie nazwisko podpis", 100, 455);
        document.close();
        writer.close();
        PdfReader reader = new PdfReader(ynputfile);
        reader.removeUsageRights();
        PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream(alputfile));
        PdfContentByte underContent = pdfStamper.getUnderContent(1);
        Image image = Image.getInstance("C:\\Users\\Osito\\Downloads\\pit4\\PIT-41.png");
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        underContent.addImage(image);
        underContent = pdfStamper.getUnderContent(2);
        image = Image.getInstance("C:\\Users\\Osito\\Downloads\\pit4\\PIT-42.png");
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        underContent.addImage(image);
        underContent = pdfStamper.getUnderContent(3);
        image = Image.getInstance("C:\\Users\\Osito\\Downloads\\pit4\\PIT-43.png");
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        underContent.addImage(image);
        underContent.closePath();
        pdfStamper.close();
        reader.close();
        System.out.println("koniec");
    }

    protected static void absText(PdfWriter writer, String text, int x, int y) {
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

    
    protected static void absTextW(PdfWriter writer, String text, int x, int y) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
            cb.saveState();
            cb.beginText();
            cb.moveText(x, y);
            cb.setFontAndSize(bf, 12);
            if (text!=null&&!text.equals("")) {
               cb.showText(F.number(Double.valueOf(text)));
            } else {
               cb.showText(text); 
            }
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
        }
    }
    
    
    protected static void absText(PdfWriter writer, String text, int x, int y, String f) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
            cb.saveState();
            cb.beginText();
//            try {
//                Integer.parseInt(text);
//                int dl = text.length();
//                if (dl > 6) {
//                    text = text.substring(0, 1) + " " + text.substring(1, 4) + " " + text.substring(4);
//                } else if (dl > 3 && dl <= 6) {
//                    text = text.substring(0, dl - 3) + " " + text.substring(dl - 3);
//                    x += 6 * (7 - dl);
//                } else {
//                    x += 6 * (7.5 - dl);
//                }
//            } catch (Exception e) {
//            }
            cb.moveText(x, y);
            cb.setFontAndSize(bf, 12);
            cb.showText(text);
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
        }
    }

    protected static void absText(PdfWriter writer, String text, int x, int y, int font) {
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

    private static String pobierz(BigDecimal p) {
        String zwrot = "";
        if (p!=null) {
            zwrot = p.toString();
        }
        return zwrot;
    }

    private static String pobierzI(BigInteger p) {
        String zwrot = "";
        if (p!=null) {
            zwrot = p.toString();
        }
        return zwrot;
    }
}
