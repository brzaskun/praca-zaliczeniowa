/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansPdf;

import static beansPdf.PdfGrafika.prost;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Fakturadodelementy;
import java.io.IOException;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class PdfFP {

    public static void dodajnaglowekstopka(PdfWriter writer, List<Fakturadodelementy> elementydod) {
        if (czydodatkowyelementjestAktywny("nagłówek", elementydod)) {
            //naglowek
            absText(writer, pobierzelementdodatkowy("nagłówek", elementydod), 15, 820, 6);
            prost(writer.getDirectContent(), 12, 817, 560, 10);
        }
        if (czydodatkowyelementjestAktywny("stopka", elementydod)) {
            //stopka
            absText(writer, pobierzelementdodatkowy("stopka", elementydod), 15, 26, 6);
            prost(writer.getDirectContent(), 12, 15, 560, 20);
        }
    }

    public static boolean czydodatkowyelementjestAktywny(String element, List<Fakturadodelementy> fdod) {
        for (Fakturadodelementy p : fdod) {
            if (p.getFakturadodelementyPK().getNazwaelementu().equals(element)) {
                return p.getAktywny();
            }
        }
        return false;
    }

    public static String pobierzelementdodatkowy(String element, List<Fakturadodelementy> fdod) {
        for (Fakturadodelementy p : fdod) {
            if (p.getFakturadodelementyPK().getNazwaelementu().equals(element)) {
                return p.getTrescelementu();
            }
        }
        return "nie odnaleziono";
    }

    private static void absText(PdfWriter writer, String text, int x, int y, int font) {
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
