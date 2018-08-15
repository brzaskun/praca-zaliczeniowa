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
import data.Data;
import embeddable.PozycjeSzczegoloweVAT;
import embeddable.Vatpoz;
import entity.Deklaracjevat;
import entity.Podatnik;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.context.RequestContext;
import static pdf.PdfVAT7.absText;
import plik.Plik;

/**
 *
 * @author Osito
 */

public class PdfVAT7K {

    private static String vat71kw;
    private static String vat72kw;
    private static final String golab = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/golab.png";

    public static void drukujVAT7K(Deklaracjevat dkl, Podatnik p, int wiersz) throws DocumentException, FileNotFoundException, IOException {
        try {
            if (dkl.getWzorschemy().equals("K-7")) {
                vat71kw = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/VAT-7K1-p1.jpg";
                vat72kw = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/VAT-7K2-p1.jpg";
            } else {
                vat71kw = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/VAT-7K8-1-p1.jpg";
                vat72kw = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/VAT-7K8-2-p1.jpg";
            }
        } catch (Exception es) {
            vat71kw = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/VAT-7K1-p1.jpg";
            vat72kw = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/VAT-7K2-p1.jpg";
        }
        Vatpoz v = dkl.getSelected();
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR("vat7" + v.getPodatnik() + ".pdf"));
        document.addTitle("Polecenie księgowania");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk deklaracji VAT " + dkl.getPodatnik());
        document.addKeywords("VAT-7, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(helvetica, 12);
        Font fontM = new Font(helvetica, 10);
        pierwszastrona(writer, v, dkl, p);
        document.newPage();
        drugastrona(writer, v, dkl, p);
        document.close();
        PdfReader reader = new PdfReader("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vat7" + v.getPodatnik() + ".pdf");
        reader.removeUsageRights();
        PdfStamper pdfStamper = new PdfStamper(reader, Plik.plikR("vat7-13" + dkl.getPodatnik() + ".pdf"));
        PdfContentByte underContent = pdfStamper.getUnderContent(1);
        Image image;
        image = Image.getInstance(vat71kw);
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        //underContent.add(underContent);
        underContent.addImage(image);
        PdfContentByte overContent = pdfStamper.getOverContent(1);
        image = Image.getInstance(golab);
        image.scaleToFit(50, 50);
        image.setAbsolutePosition(430f, 810f);
        overContent.addImage(image);
        underContent = pdfStamper.getUnderContent(2);
        image = Image.getInstance(vat72kw);
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        //underContent.add(underContent);
        underContent.addImage(image);
        pdfStamper.close();
        reader.close();
        writer.close();
        try {
            String ordzu = dkl.getOrdzu();
            String vatzt = dkl.getVatzt();
            if (ordzu != null) {
                PdfORDZU.drukujORDZU(dkl, p);
            } else if (vatzt != null) {
                PdfVATZT.drukujZT(dkl, p);
            }
            if (ordzu != null && vatzt == null) {
                kombinuj(v.getPodatnik(), "ordzu");
            } else if (ordzu == null && vatzt != null) {
                kombinuj(v.getPodatnik(), "vatzt");
            } else if (ordzu != null && vatzt != null) {
                kombinuj(v.getPodatnik(), "ordzu+vatzt");
            } else {
                kombinuj(v.getPodatnik(), "nic");
            }
        } catch (Exception e) {
            kombinuj(v.getPodatnik(), "nic");
        }
        RequestContext.getCurrentInstance().execute("wydrukvat7('" + dkl.getPodatnik() + "', " + wiersz + ");");
    }

    public static void drukujwysVAT7K(Deklaracjevat dkl, Podatnik p) throws DocumentException, FileNotFoundException, IOException {
        try {
            if (dkl.getWzorschemy().equals("K-7")) {
                vat71kw = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/VAT-7K1-p1.jpg";
                vat72kw = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/VAT-7K2-p1.jpg";
            } else {
                vat71kw = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/VAT-7K8-1-p1.jpg";
                vat72kw = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/VAT-7K8-2-p1.jpg";
            }
        } catch (Exception es) {
            vat71kw = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/VAT-7K1-p1.jpg";
            vat72kw = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/VAT-7K2-p1.jpg";
        }
        Vatpoz v = dkl.getSelected();
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR("vat7" + v.getPodatnik() + ".pdf"));
        document.addTitle("Polecenie księgowania");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk deklaracji tetsowej VAT " + dkl.getPodatnik());
        document.addKeywords("VAT-7 test, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(helvetica, 12);
        Font fontM = new Font(helvetica, 10);
        pierwszastronawys(writer, v, dkl, p);
        document.newPage();
        drugastronawys(writer, v, dkl, p);
        document.close();
        PdfReader reader = new PdfReader("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vat7" + v.getPodatnik() + ".pdf");
        reader.removeUsageRights();
        PdfStamper pdfStamper = new PdfStamper(reader, Plik.plikR("vat7-13" + dkl.getPodatnik() + ".pdf"));
        PdfContentByte underContent = pdfStamper.getUnderContent(1);
        Image image;
        image = Image.getInstance(vat71kw);
        image.scaleToFit(610, 850);
        image.setAbsolutePosition(0f, 0f);
        underContent.add(underContent);
        underContent.addImage(image);
        PdfContentByte overContent = pdfStamper.getOverContent(1);
        image = Image.getInstance(golab);
        image.scaleToFit(50, 50);
        image.setAbsolutePosition(430f, 810f);
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
        try {
            String ordzu = dkl.getOrdzu();
            String vatzt = dkl.getVatzt();
            if (ordzu != null) {
                PdfORDZU.drukujORDZU(dkl, p);
            } else if (vatzt != null) {
                PdfVATZT.drukujZT(dkl, p);
            }
            if (ordzu != null && vatzt == null) {
                kombinuj(v.getPodatnik(), "ordzu");
            } else if (ordzu == null && vatzt != null) {
                kombinuj(v.getPodatnik(), "vatzt");
            } else if (ordzu != null && vatzt != null) {
                kombinuj(v.getPodatnik(), "ordzu+vatzt");
            } else {
                kombinuj(v.getPodatnik(), "nic");
            }
        } catch (Exception e) {
            kombinuj(v.getPodatnik(), "nic");
        }
    }

    private static void pierwszastrona(PdfWriter writer, Vatpoz d, Deklaracjevat l, Podatnik p) {
        try {
            if (l.getWzorschemy().equals("K-8")) {
                PozycjeSzczegoloweVAT o = d.getPozycjeszczegolowe();
                absText(writer, p.getNip(), 70, 788);
                if (l.isMiesiackwartal() == true) {
                    absText(writer, l.getNrkwartalu(), 218, 740);
                } else {
                    absText(writer, d.getMiesiac(), 218, 740);
                }
                absText(writer, d.getRok(), 275, 740);
                absText(writer, d.getNazwaurzedu(), 70, 665);
                if (d.getCelzlozenia().equals("1")) {
                    absText(writer, "X", 374, 665);
                } else {
                    absText(writer, "X", 503, 665);
                }
                absText(writer, "X", 420, 609);
                absText(writer, p.getImie() + " " + p.getNazwisko(), 70, 585);
                absText(writer, p.getPesel(), 368, 585);

                absText(writer, o.getPole20(), 330, 548, "f");
                absText(writer, o.getPole21(), 330, 523, "f");
                absText(writer, o.getPole22(), 330, 499, "f");
                absText(writer, o.getPole23(), 330, 445, "f");
                absText(writer, o.getPole24(), 330, 451, "f");
                absText(writer, o.getPole25(), 330, 427, "f");
                absText(writer, o.getPole26(), 490, 427, "f");
                absText(writer, o.getPole27(), 330, 403, "f");
                absText(writer, o.getPole28(), 490, 403, "f");
                absText(writer, o.getPole29(), 330, 379, "f");
                absText(writer, o.getPole30(), 490, 379, "f");
                absText(writer, o.getPole31(), 330, 355, "f");
                absText(writer, o.getPole32(), 330, 332, "f");
                absText(writer, o.getPole33(), 330, 307, "f");
                absText(writer, o.getPole34(), 490, 307, "f");
                absText(writer, o.getPole35(), 330, 283, "f");
                absText(writer, o.getPole36(), 490, 283, "f");
                absText(writer, o.getPole37(), 330, 259, "f");
                absText(writer, o.getPole37(), 490, 259, "f");
                absText(writer, o.getPole39(), 330, 235, "f");
                absText(writer, o.getPole40(), 490, 235, "f");
                absText(writer, o.getPole41(), 330, 213, "f");
                absText(writer, o.getPole42(), 490, 213, "f");
                absText(writer, o.getPole43(), 490, 189, "f");
                absText(writer, o.getPole44(), 490, 165, "f");
                absText(writer, o.getPole45(), 330, 142, "f");
                absText(writer, o.getPole46(), 490, 142, "f");
                absText(writer, o.getPole47(), 490, 76, "f");
                absText(writer, o.getPole48(), 490, 52, "f");
                absText(writer, "Status " + l.getStatus(), 470, 810);
                if (l.getUpo().contains("system testowy")) {
                    absText(writer, "DEKLARACJA TESTOWA ", 470, 800, 6);
                } else {
                    absText(writer, "Data potwierdzenia ", 470, 800, 6);
                }
                try {
                    absText(writer, Data.data_ddMMMMyyyy(l.getDataupo()), 470, 790, 6);
                } catch (Exception e) {
                }
                absText(writer, l.getOpis(), 440, 780, 6);
                absText(writer, "Nr potwierdzenia:", 440, 770, 6);
                absText(writer, l.getIdentyfikator(), 440, 760, 6);
            }
        } catch (Exception e) {
            PozycjeSzczegoloweVAT o = d.getPozycjeszczegolowe();
            absText(writer, p.getNip(), 70, 790);
            if (l.isMiesiackwartal() == true) {
                absText(writer, l.getNrkwartalu(), 200, 745);
            } else {
                absText(writer, d.getMiesiac(), 200, 745);
            }
            absText(writer, d.getRok(), 270, 745);
            absText(writer, d.getNazwaurzedu(), 70, 674);
            if (d.getCelzlozenia().equals("1")) {
                absText(writer, "X", 368, 674);
            } else {
                absText(writer, "X", 482, 674);
            }
            absText(writer, "X", 388, 604);
            absText(writer, p.getImie() + " " + p.getNazwisko(), 70, 578);
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
            absText(writer, p.getPoczta(), 380, 487, "f");
            absText(writer, o.getPole20(), 330, 439, "f");
            absText(writer, o.getPole21(), 330, 415, "f");
            absText(writer, o.getPole22(), 330, 391, "f");
            absText(writer, o.getPole23(), 330, 367, "f");
            absText(writer, o.getPole24(), 330, 343, "f");
            absText(writer, o.getPole25(), 330, 319, "f");
            absText(writer, o.getPole26(), 490, 319, "f");
            absText(writer, o.getPole27(), 330, 297, "f");
            absText(writer, o.getPole28(), 490, 297, "f");
            absText(writer, o.getPole29(), 330, 273, "f");
            absText(writer, o.getPole30(), 490, 273, "f");
            absText(writer, o.getPole31(), 330, 249, "f");
            absText(writer, o.getPole32(), 330, 225, "f");
            absText(writer, o.getPole33(), 330, 201, "f");
            absText(writer, o.getPole34(), 490, 201, "f");
            absText(writer, o.getPole35(), 330, 177, "f");
            absText(writer, o.getPole36(), 490, 177, "f");
            absText(writer, o.getPole37(), 330, 153, "f");
            absText(writer, o.getPole37(), 490, 153, "f");
            absText(writer, o.getPole39(), 330, 129, "f");
            absText(writer, o.getPole40(), 490, 129, "f");
            absText(writer, o.getPole41(), 330, 105, "f");
            absText(writer, o.getPole42(), 490, 105, "f");
            absText(writer, o.getPole43(), 490, 82, "f");
            absText(writer, o.getPole44(), 490, 59, "f");

            absText(writer, "Status " + l.getStatus(), 470, 810);
            if (l.getUpo().contains("system testowy")) {
                absText(writer, "DEKLARACJA TESTOWA ", 470, 800, 6);
            } else {
                absText(writer, "Data potwierdzenia ", 470, 800, 6);
            }
            try {
                absText(writer, Data.data_ddMMMMyyyy(l.getDataupo()), 470, 790, 6);
            } catch (Exception e1) {
            }
            absText(writer, l.getOpis(), 440, 780, 6);
            absText(writer, "Nr potwierdzenia:", 440, 770, 6);
            absText(writer, l.getIdentyfikator(), 440, 760, 6);

        }
    }

    private static void drugastrona(PdfWriter writer, Vatpoz d, Deklaracjevat l, Podatnik p) {
        try {
            if (l.getWzorschemy().equals("K-8")) {
                PozycjeSzczegoloweVAT o = d.getPozycjeszczegolowe();
                absText(writer, o.getPole49(), 330, 764, "f");
                absText(writer, o.getPole50(), 490, 764, "f");
                absText(writer, o.getPole51(), 330, 740, "f");
                absText(writer, o.getPole52(), 490, 740, "f");
                absText(writer, o.getPole53(), 490, 696, "f");
                absText(writer, o.getPole54(), 490, 672, "f");
                absText(writer, o.getPole55(), 490, 648, "f");
                absText(writer, o.getPole56(), 490, 601, "f");
                absText(writer, o.getPole57(), 490, 577, "f");
                absText(writer, o.getPole58(), 490, 553, "f");
                absText(writer, o.getPole59(), 490, 529, "f");
                absText(writer, o.getPole60(), 490, 505, "f");
                absText(writer, o.getPole61(), 490, 476, "f");
                absText(writer, o.getPole62(), 190, 447, "f");
                absText(writer, o.getPole63(), 330, 447, "f");
                absText(writer, o.getPole64(), 490, 447, "f");
                absText(writer, o.getPole65(), 490, 427, "f");
                if (o.getPoleI62() > 0 || o.getPoleI63() > 0 || o.getPoleI64() > 0) {
                    absText(writer, "X", 150, 382);
                }
                absText(writer, p.getImie(), 80, 218);
                absText(writer, p.getNazwisko(), 210, 218);
                absText(writer, "91 8120976", 80, 195);
                try {
                    absText(writer, l.getDatazlozenia().toString(), 210, 195);
                    absText(writer, l.getSporzadzil(), 400, 195);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            PozycjeSzczegoloweVAT o = d.getPozycjeszczegolowe();
            absText(writer, o.getPole45(), 330, 790, "f");
            absText(writer, o.getPole46(), 490, 790, "f");
            absText(writer, o.getPole47(), 490, 726, "f");
            absText(writer, o.getPole48(), 490, 702, "f");
            absText(writer, o.getPole49(), 330, 654, "f");
            absText(writer, o.getPole50(), 490, 654, "f");
            absText(writer, o.getPole51(), 330, 630, "f");
            absText(writer, o.getPole52(), 490, 630, "f");
            absText(writer, o.getPole53(), 490, 586, "f");
            absText(writer, o.getPole54(), 490, 562, "f");
            absText(writer, o.getPole55(), 490, 538, "f");
            absText(writer, o.getPole56(), 490, 491, "f");
            absText(writer, o.getPole57(), 490, 467, "f");
            absText(writer, o.getPole58(), 490, 443, "f");
            absText(writer, o.getPole59(), 490, 419, "f");
            absText(writer, o.getPole60(), 490, 395, "f");
            absText(writer, o.getPole61(), 490, 368, "f");
            absText(writer, o.getPole62(), 190, 344, "f");
            absText(writer, o.getPole63(), 330, 344, "f");
            absText(writer, o.getPole64(), 490, 344, "f");
            absText(writer, o.getPole65(), 490, 323, "f");
            if (o.getPoleI62() > 0 || o.getPoleI63() > 0 || o.getPoleI64() > 0) {
                absText(writer, "X", 150, 382);
            }
            absText(writer, p.getImie(), 80, 178);
            absText(writer, p.getNazwisko(), 210, 178);
            absText(writer, "91 8120976", 80, 154);
            try {
                absText(writer, l.getDatazlozenia().toString(), 210, 154);
                absText(writer, l.getSporzadzil(), 400, 165);
            } catch (Exception eF) {
            }
        }
    }

    private static void pierwszastronawys(PdfWriter writer, Vatpoz d, Deklaracjevat l, Podatnik p) {
        try {
            if (l.getWzorschemy().equals("K-8")) {

                PozycjeSzczegoloweVAT o = d.getPozycjeszczegolowe();
                absText(writer, p.getNip(), 70, 788);
                if (l.isMiesiackwartal() == true) {
                    absText(writer, l.getNrkwartalu(), 218, 740);
                } else {
                    absText(writer, d.getMiesiac(), 218, 740);
                }
                absText(writer, d.getRok(), 275, 740);
                absText(writer, d.getNazwaurzedu(), 70, 665);
                if (d.getCelzlozenia().equals("1")) {
                    absText(writer, "X", 374, 665);
                } else {
                    absText(writer, "X", 503, 665);
                }
                absText(writer, "X", 420, 609);
                absText(writer, p.getImie() + " " + p.getNazwisko(), 70, 585);
                absText(writer, p.getPesel(), 368, 585);

                absText(writer, o.getPole20(), 330, 548, "f");
                absText(writer, o.getPole21(), 330, 523, "f");
                absText(writer, o.getPole22(), 330, 499, "f");
                absText(writer, o.getPole23(), 330, 445, "f");
                absText(writer, o.getPole24(), 330, 451, "f");
                absText(writer, o.getPole25(), 330, 427, "f");
                absText(writer, o.getPole26(), 490, 427, "f");
                absText(writer, o.getPole27(), 330, 403, "f");
                absText(writer, o.getPole28(), 490, 403, "f");
                absText(writer, o.getPole29(), 330, 379, "f");
                absText(writer, o.getPole30(), 490, 379, "f");
                absText(writer, o.getPole31(), 330, 355, "f");
                absText(writer, o.getPole32(), 330, 332, "f");
                absText(writer, o.getPole33(), 330, 307, "f");
                absText(writer, o.getPole34(), 490, 307, "f");
                absText(writer, o.getPole35(), 330, 283, "f");
                absText(writer, o.getPole36(), 490, 283, "f");
                absText(writer, o.getPole37(), 330, 259, "f");
                absText(writer, o.getPole37(), 490, 259, "f");
                absText(writer, o.getPole39(), 330, 235, "f");
                absText(writer, o.getPole40(), 490, 235, "f");
                absText(writer, o.getPole41(), 330, 213, "f");
                absText(writer, o.getPole42(), 490, 213, "f");
                absText(writer, o.getPole43(), 490, 189, "f");
                absText(writer, o.getPole44(), 490, 165, "f");
                absText(writer, o.getPole45(), 330, 142, "f");
                absText(writer, o.getPole46(), 490, 142, "f");
                absText(writer, o.getPole47(), 490, 76, "f");
                absText(writer, o.getPole48(), 490, 52, "f");
                absText(writer, "Status: do wysyłki", 480, 790);

            }
        } catch (Exception e) {
            PozycjeSzczegoloweVAT o = d.getPozycjeszczegolowe();
            absText(writer, p.getNip(), 70, 790);
            if (l.isMiesiackwartal() == true) {
                absText(writer, l.getNrkwartalu(), 200, 745);
            } else {
                absText(writer, d.getMiesiac(), 200, 745);
            }
            absText(writer, d.getRok(), 270, 745);
            absText(writer, d.getNazwaurzedu(), 70, 674);
            if (d.getCelzlozenia().equals("1")) {
                absText(writer, "X", 368, 674);
            } else {
                absText(writer, "X", 482, 674);
            }
            absText(writer, "X", 388, 604);
            absText(writer, p.getImie() + " " + p.getNazwisko(), 70, 578);
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
            absText(writer, p.getPoczta(), 380, 487, "f");
            absText(writer, o.getPole20(), 330, 439, "f");
            absText(writer, o.getPole21(), 330, 415, "f");
            absText(writer, o.getPole22(), 330, 391, "f");
            absText(writer, o.getPole23(), 330, 367, "f");
            absText(writer, o.getPole24(), 330, 343, "f");
            absText(writer, o.getPole25(), 330, 319, "f");
            absText(writer, o.getPole26(), 490, 319, "f");
            absText(writer, o.getPole27(), 330, 297, "f");
            absText(writer, o.getPole28(), 490, 297, "f");
            absText(writer, o.getPole29(), 330, 273, "f");
            absText(writer, o.getPole30(), 490, 273, "f");
            absText(writer, o.getPole31(), 330, 249, "f");
            absText(writer, o.getPole32(), 330, 225, "f");
            absText(writer, o.getPole33(), 330, 201, "f");
            absText(writer, o.getPole34(), 490, 201, "f");
            absText(writer, o.getPole35(), 330, 177, "f");
            absText(writer, o.getPole36(), 490, 177, "f");
            absText(writer, o.getPole37(), 330, 153, "f");
            absText(writer, o.getPole37(), 490, 153, "f");
            absText(writer, o.getPole39(), 330, 129, "f");
            absText(writer, o.getPole40(), 490, 129, "f");
            absText(writer, o.getPole41(), 330, 105, "f");
            absText(writer, o.getPole42(), 490, 105, "f");
            absText(writer, o.getPole43(), 490, 82, "f");
            absText(writer, o.getPole44(), 490, 59, "f");
            absText(writer, "Status: do wysyłki", 490, 790);

        }
    }

    private static void drugastronawys(PdfWriter writer, Vatpoz d, Deklaracjevat l, Podatnik p) {
        try {
            if (l.getWzorschemy().equals("K-8")) {
                PozycjeSzczegoloweVAT o = d.getPozycjeszczegolowe();
                absText(writer, o.getPole49(), 330, 764, "f");
                absText(writer, o.getPole50(), 490, 764, "f");
                absText(writer, o.getPole51(), 330, 740, "f");
                absText(writer, o.getPole52(), 490, 740, "f");
                absText(writer, o.getPole53(), 490, 696, "f");
                absText(writer, o.getPole54(), 490, 672, "f");
                absText(writer, o.getPole55(), 490, 648, "f");
                absText(writer, o.getPole56(), 490, 601, "f");
                absText(writer, o.getPole57(), 490, 577, "f");
                absText(writer, o.getPole58(), 490, 553, "f");
                absText(writer, o.getPole59(), 490, 529, "f");
                absText(writer, o.getPole60(), 490, 505, "f");
                absText(writer, o.getPole61(), 490, 476, "f");
                absText(writer, o.getPole62(), 190, 447, "f");
                absText(writer, o.getPole63(), 330, 447, "f");
                absText(writer, o.getPole64(), 490, 447, "f");
                absText(writer, o.getPole65(), 490, 427, "f");
                absText(writer, p.getImie(), 80, 218);
                absText(writer, p.getNazwisko(), 210, 218);
                absText(writer, "91 8120976", 80, 195);
            }
        } catch (Exception e) {

            PozycjeSzczegoloweVAT o = d.getPozycjeszczegolowe();
            absText(writer, o.getPole45(), 330, 790, "f");
            absText(writer, o.getPole46(), 490, 790, "f");
            absText(writer, o.getPole47(), 490, 726, "f");
            absText(writer, o.getPole48(), 490, 702, "f");
            absText(writer, o.getPole49(), 330, 654, "f");
            absText(writer, o.getPole50(), 490, 654, "f");
            absText(writer, o.getPole51(), 330, 630, "f");
            absText(writer, o.getPole52(), 490, 630, "f");
            absText(writer, o.getPole53(), 490, 586, "f");
            absText(writer, o.getPole54(), 490, 562, "f");
            absText(writer, o.getPole55(), 490, 538, "f");
            absText(writer, o.getPole56(), 490, 491, "f");
            absText(writer, o.getPole57(), 490, 467, "f");
            absText(writer, o.getPole58(), 490, 443, "f");
            absText(writer, o.getPole59(), 490, 419, "f");
            absText(writer, o.getPole60(), 490, 395, "f");
            absText(writer, o.getPole61(), 490, 368, "f");
            absText(writer, o.getPole62(), 190, 344, "f");
            absText(writer, o.getPole63(), 330, 344, "f");
            absText(writer, o.getPole64(), 490, 344, "f");
            absText(writer, o.getPole65(), 490, 323, "f");
            absText(writer, p.getImie(), 80, 178);
            absText(writer, p.getNazwisko(), 210, 178);
            absText(writer, "91 8120976", 80, 154);
        }
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
    private static void kombinuj(String kto, String zalaczniki) {
        try {
            List<String> files = new ArrayList<>();
            switch (zalaczniki) {
                case "nic":
                    files.add("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vat7-13" + kto + ".pdf");
                    break;
                case "ordzu":
                    files.add("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vat7-13" + kto + ".pdf");
                    files.add("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/ord-zu" + kto + ".pdf");
                    break;
                case "vatzt":
                    files.add("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vat7-13" + kto + ".pdf");
                    files.add("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vat-zt" + kto + ".pdf");
                    break;
            }
            Document PDFCombineUsingJava = new Document();
            PdfCopy copy = new PdfCopy(PDFCombineUsingJava, Plik.plikR("VAT7Comb" + kto + ".pdf"));
            PDFCombineUsingJava.open();
            int number_of_pages;
            for (String p : files) {
                PdfReader ReadInputPDF = new PdfReader(p);
                number_of_pages = ReadInputPDF.getNumberOfPages();
                for (int page = 0; page < number_of_pages;) {
                    copy.addPage(copy.getImportedPage(ReadInputPDF, ++page));
                }
                ReadInputPDF.close();
            }
            copy.close();
            PDFCombineUsingJava.close();
        } catch (Exception i) {
        }
    }

}
