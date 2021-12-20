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
public class PdfPIT11 {

    public static final String OUTPUTFILE = "pit-11F.pdf";

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
                absText(writer, TKodUS.getNazwaUrzedu(TKodUS.getNazwaUrzedu(naglowek.getKodUrzedu())), 120, 505);
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
                Logger.getLogger(PdfPIT11.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Msg.msg("e", "Brak deklaracji");
        }
        return nazwapliku;
    }

    public static void main(String[] args) throws IOException, DocumentException {
        String ynputfile = "C:\\Users\\Osito\\Downloads\\pit-11.pdf";
        String alputfile = "C:\\Users\\Osito\\Downloads\\pit-11a.pdf";
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
        absText(writer, "Pierwszy Urząd Skarbowy w Szczecinie", 120, 505);
        absText(writer, "X", 214, 483);
        absText(writer, "X", 328, 483);
        absText(writer, "X", 133, 431);
        absText(writer, "X", 389, 431);
        absText(writer, "Nazwa pełna", 133, 407);
        absText(writer, "Nazwisko i mie, data urodzenia", 133, 377);
        absText(writer, "X", 990, 326);
        absText(writer, "X", 351, 326);
        absText(writer, "Pesel", 100, 292);
        absText(writer, "Zagraniczny numer", 350, 292);
        absText(writer, "Rodzaj dokumentu", 100, 266);
        absText(writer, "Kraj wydania", 370, 266);
        absText(writer, "Nazwisko", 100, 240);
        absText(writer, "Pierwsze imię", 370, 240);
        absText(writer, "Data urodzenia", 70, 216);
        absText(writer, "Kraj", 250, 216);
        absText(writer, "Województwo", 430, 216);
        absText(writer, "Powiat", 100, 192);
        absText(writer, "Gmina", 410, 192);
        absText(writer, "Uica", 100, 168);
        absText(writer, "Nr dom", 470, 168);
        absText(writer, "Nr l", 535, 168);
        absText(writer, "Miejscowość", 100, 144);
        absText(writer, "Kod", 470, 144);
        absText(writer, "X", 56, 85);
        absText(writer, "X", 329, 85);
        absText(writer, "X", 56, 69);
        absText(writer, "X", 329, 68);
        absText(writer, "Data potwierdzebia", 490, 780, 6);
        absText(writer, "2013-05-05 124885", 490, 770, 6);
        absText(writer, "Opis", 490, 760, 6);
        absText(writer, "Nr potwierdzenia:", 460, 750, 6);
        absText(writer, "ijijiiijiiji", 460, 740, 6);
        document.newPage();
        absText(writer, "29/000", 245, 700);
        absText(writer, "30/000", 315, 700);
        absText(writer, "31/000", 380, 690);
        absText(writer, "32/000", 450, 690);
        absText(writer, "33/000", 520, 690);
        absText(writer, "34/000", 245, 660);
        absText(writer, "35/000", 315, 660);
        absText(writer, "36/000", 245, 635);
        absText(writer, "37/000", 315, 635);
        absText(writer, "38/000", 380, 610);
        absText(writer, "39/000", 450, 610);
        absText(writer, "40/000", 520, 610);
        absText(writer, "41/000", 245, 610);
        absText(writer, "42/000", 315, 610);
        absText(writer, "43/000", 245, 580);
        absText(writer, "44/000", 380, 580);
        absText(writer, "45/000", 450, 580);
        absText(writer, "46/000", 520, 580);

        absText(writer, "47/000", 245, 540);
        absText(writer, "48/000", 315, 540);
        absText(writer, "49/000", 380, 540);
        absText(writer, "50/000", 520, 540);
        absText(writer, "51/000", 245, 500);
        absText(writer, "52/000", 315, 500);
        absText(writer, "53/000", 380, 500);
        absText(writer, "54/000", 520, 500);
        absText(writer, "55/000", 245, 460);
        absText(writer, "56/000", 315, 460);
        absText(writer, "57/000", 380, 460);
        absText(writer, "58/000", 520, 460);

        absText(writer, "59/000", 245, 430);
        absText(writer, "60/000", 380, 420);
        absText(writer, "61/000", 520, 420);
        absText(writer, "62/000", 245, 408);
        absText(writer, "63/000", 315, 408);
        absText(writer, "64/000", 245, 383);
        absText(writer, "65/000", 380, 383);
        absText(writer, "66/000", 520, 383);
        absText(writer, "67/000", 245, 358);
        absText(writer, "68/000", 380, 358);
        absText(writer, "69/000", 520, 358);
        absText(writer, "70/000", 245, 332);
        absText(writer, "71/000", 315, 332);
        absText(writer, "72/000", 380, 332);
        absText(writer, "73/000", 450, 332);
        absText(writer, "74/000", 520, 332);
        absText(writer, "75/000", 520, 305);
        absText(writer, "76/000", 520, 278);
        absText(writer, "77/000", 520, 251);
        absText(writer, "78/000", 520, 226);
        absText(writer, "79/000", 520, 198);
        absText(writer, "80/000", 520, 168);
        absText(writer, "81/000", 520, 90);
        absText(writer, "82/000", 120, 65);
        absText(writer, "83/000", 520, 65);

        document.newPage();
        absText(writer, "84/000", 120, 790);
        absText(writer, "85/000", 520, 790);
        absText(writer, "86/000", 120, 767);
        absText(writer, "87/000", 520, 767);
        absText(writer, "88/000", 520, 740);
        absText(writer, "89/000", 520, 683);
        absText(writer, "90/000", 520, 658);
        absText(writer, "91/000", 520, 632);
        absText(writer, "92/000", 520, 608);
        absText(writer, "93/000", 520, 583);
        absText(writer, "94/000", 520, 558);
        absText(writer, "95/000", 520, 532);

        absText(writer, "X", 251, 502);
        absText(writer, "X", 342, 502);
        absText(writer, "97 Imie nazwisko podpis", 100, 440);
        absText(writer, "98 Imie nazwisko podpis", 100, 370);
        document.newPage();
        absText(writer, " ", 80, 166);
        document.close();
        writer.close();
        PdfReader reader = new PdfReader(ynputfile);
        reader.removeUsageRights();
        PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream(alputfile));
        PdfContentByte underContent = pdfStamper.getUnderContent(1);
        Image image = Image.getInstance("C:\\Users\\Osito\\Downloads\\pit11\\PIT-111.png");
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        underContent.addImage(image);
        underContent = pdfStamper.getUnderContent(2);
        image = Image.getInstance("C:\\Users\\Osito\\Downloads\\pit11\\PIT-112.png");
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        underContent.addImage(image);
        underContent = pdfStamper.getUnderContent(3);
        image = Image.getInstance("C:\\Users\\Osito\\Downloads\\pit11\\PIT-113.png");
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        underContent.addImage(image);
        underContent = pdfStamper.getUnderContent(4);
        image = Image.getInstance("C:\\Users\\Osito\\Downloads\\pit11\\PIT-114.png");
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        underContent.addImage(image);
        underContent.closePath();
        pdfStamper.close();
        reader.close();
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
