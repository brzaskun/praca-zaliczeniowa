/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import error.E;
import java.util.List;
import static pdffk.PdfMain.przygotujtabele;
import static pdffk.PdfMain.ustawnaglowki;
import static pdffk.PdfMain.obliczKolumny;

/**
 *
 * @author Osito
 */
public class PdfDodajTabele {
    public static void dodajTabele(Document document, List[] tabela, int perc, int modyfikator, String rok, String mc) {
        try {
            List naglowki = tabela[0];
            List wiersze = tabela[1];
            if (wiersze != null && wiersze.size() > 0) {
                String nazwaklasy = wiersze.get(0).getClass().getName();
                int[] col = obliczKolumny(naglowki.size(), nazwaklasy, modyfikator);
                PdfPTable table = przygotujtabele(naglowki.size(),col, perc, 2f, 3f);
                ustawnaglowki(table, naglowki);
                PdfUstawWiersze.ustawwiersze(table,wiersze, nazwaklasy, modyfikator, rok, mc);
                document.add(table);
            }
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }
}
