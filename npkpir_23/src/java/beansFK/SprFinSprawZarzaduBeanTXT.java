/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import static beansPdf.PdfFont.ustawfrazeAlign;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entityfk.SprFinKwotyInfDod;
import view.WpisView;
import java.io.File;
import pdffk.PdfMain;
import static pdffk.PdfMain.*;
import com.itextpdf.text.pdf.PdfPTable;
import embeddablefk.SaldoKonto;
import entity.Podatnik;
import entityfk.Konto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import plik.Plik;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class SprFinSprawZarzaduBeanTXT {

    static void naglowekglowny(Document document, String rok) {
        PdfMain.dodajLinieOpisuBezOdstepu(document, "INFORMACJA DODATKOWA", Element.ALIGN_CENTER);
        PdfMain.dodajLinieOpisuBezOdstepu(document, "Za rok podatkowy "+rok, Element.ALIGN_CENTER);
        PdfMain.dodajLinieOpisu(document, "WG ZAŁĄCZNIKA NR 1 DO USTAWY O RACHUNKOWOŚCI", Element.ALIGN_CENTER);
    }

    static void podnaglowek1(Document document) {
        PdfMain.dodajLinieOpisu(document, "I. WPROWADZENIE DO SPRAWOZDANIA FINANSOWEGO.", Element.ALIGN_LEFT);
        PdfMain.dodajLinieOpisu(document, "1. PODSTAWOWE DANE SPÓŁKI:", Element.ALIGN_LEFT);
    }

    
    
}
