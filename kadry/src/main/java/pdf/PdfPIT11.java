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
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Osito
 */
public class PdfPIT11 {
    private static final String INPUTFILE = "C:\\Users\\Osito\\Downloads\\pit-11.pdf";
    private static final String OUTPUTFILE = "C:\\Users\\Osito\\Downloads\\pit-11A.pdf";
    public static void main(String[] args) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(INPUTFILE));
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
        absText(writer, "29/000", 255, 720);
        absText(writer, "30/000", 320, 720);
        absText(writer, "31/000", 390, 720);
        absText(writer, "32/000", 460, 720);
        absText(writer, "33/000", 530, 720);
        absText(writer, "34/000", 255, 660);
        absText(writer, "35/000", 320, 660);
        absText(writer, "36/000", 255, 635);
        absText(writer, "37/000", 320, 635);
        absText(writer, "38/000", 390, 610);
        absText(writer, "39/000", 460, 610);
        absText(writer, "40/000", 530, 610);
        absText(writer, "41/000", 255, 610);
        absText(writer, "42/000", 320, 610);
        absText(writer, "43/000", 255, 580);
        absText(writer, "44/000", 390, 580);
        absText(writer, "45/000", 460, 580);
        absText(writer, "46/000", 530, 580);
        
        absText(writer, "47/000", 255, 540);
        absText(writer, "48/000", 320, 540);
        absText(writer, "49/000", 390, 540);
        absText(writer, "50/000", 530, 540);
        absText(writer, "51/000", 255, 500);
        absText(writer, "52/000", 320, 500);
        absText(writer, "53/000", 390, 500);
        absText(writer, "54/000", 530, 500);
        absText(writer, "55/000", 255, 460);
        absText(writer, "56/000", 320, 460);
        absText(writer, "57/000", 390, 460);
        absText(writer, "58/000", 530, 460);
        
        absText(writer, "59/000", 255, 430);
        absText(writer, "60/000", 390, 420);
        absText(writer, "61/000", 530, 420);
        absText(writer, "62/000", 255, 408);
        absText(writer, "63/000", 320, 408);
        absText(writer, "64/000", 255, 383);
        absText(writer, "65/000", 390, 383);
        absText(writer, "66/000", 530, 383);
        absText(writer, "67/000", 255, 358);
        absText(writer, "68/000", 390, 358);
        absText(writer, "69/000", 530, 358);
        absText(writer, "70/000", 255, 332);
        absText(writer, "71/000", 320, 332);
        absText(writer, "72/000", 390, 332);
        absText(writer, "73/000", 460, 332);
        absText(writer, "74/000", 530, 332);
        absText(writer, "75/000", 530, 305);
        absText(writer, "76/000", 530, 278);
        absText(writer, "77/000", 530, 251);
        absText(writer, "78/000", 530, 226);
        absText(writer, "79/000", 530, 198);
        absText(writer, "80/000", 530, 168);
        absText(writer, "81/000", 530, 90);
        absText(writer, "82/000", 120, 65);
        absText(writer, "83/000", 530, 65);
        
        
        document.newPage();
        absText(writer, "84/000", 120, 790);
        absText(writer, "85/000", 530, 790);
        absText(writer, "86/000", 120, 767);
        absText(writer, "87/000", 530, 767);
        absText(writer, "88/000", 530, 740);
        absText(writer, "89/000", 530, 683);
        absText(writer, "90/000", 530, 658);
        absText(writer, "91/000", 530, 632);
        absText(writer, "92/000", 530, 608);
        absText(writer, "93/000", 530, 583);
        absText(writer, "94/000", 530, 558);
        absText(writer, "95/000", 530, 532);
        
        absText(writer, "X", 251, 502);
        absText(writer, "X", 342, 502);
        absText(writer, "97 Imie nazwisko podpis", 100, 440);
        absText(writer, "98 Imie nazwisko podpis", 100, 370);
        document.newPage();
        absText(writer, " ", 80, 166);
        document.close();
        writer.close();
        PdfReader reader = new PdfReader(INPUTFILE);
        reader.removeUsageRights();
        PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream(OUTPUTFILE));
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
    
      protected static void absText(PdfWriter writer, String text, int x, int y, String f) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
            cb.saveState();
            cb.beginText();
            try {
                Integer.parseInt(text);
                int dl = text.length();
                if (dl > 6) {
                    text = text.substring(0, 1) + " " + text.substring(1, 4) + " " + text.substring(4);
                } else if (dl > 3 && dl <= 6) {
                    text = text.substring(0, dl - 3) + " " + text.substring(dl - 3);
                    x += 6 * (7 - dl);
                } else {
                    x += 6 * (7.5 - dl);
                }
            } catch (Exception e) {
            }
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
}

