/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Ryczpoz;
import entity.Uz;
import java.io.File;
import java.util.List;
import org.primefaces.context.RequestContext;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentu;
import static pdffk.PdfMain.inicjacjaA4Portrait;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;
import view.WpisView;

/**
 *
 * @author Osito
 */

public class PdfRyczpoz {
    
      
    public static void drukujryczalt(String nazwa, List<Ryczpoz> listaryczalt, WpisView wpisView) {
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        Uz uz = wpisView.getWprowadzil();
        Document document = inicjacjaA4Portrait();
        PdfWriter writer = inicjacjaWritera(document, nazwa);
        naglowekStopkaP(writer);
        otwarcieDokumentu(document, nazwa);
        dodajOpisWstepny(document, "Zestawienie rozliczeń miesięcznych ryczałtowych podatnika " +wpisView.getPodatnikWpisu(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
        dodajTabele(document, testobjects.testobjects.getTabelaRyczpoz(listaryczalt), 100,0);
        finalizacjaDokumentu(document);
        String f = "pokazwydruk('"+nazwa+"');";
        RequestContext.getCurrentInstance().execute(f);
    }
 
}
