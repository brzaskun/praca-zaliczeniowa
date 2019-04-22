/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Uz;
import error.E;
import java.io.File;
import java.util.List;
import msg.Msg;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaA4Portrait;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;
import vies.Vies;
import view.WpisView; import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */
public class PdfVIES {
    public static void drukujVIES(List<Vies> wykaz, WpisView wpisView) {
        try {
            String nazwa = wpisView.getPodatnikObiekt().getNip()+"vies";
            File file = Plik.plik(nazwa, true);
            if (file.isFile()) {
                file.delete();
            }
            if (wykaz != null && wykaz.size() > 0) {
                Uz uz = wpisView.getUzer();
                Document document = inicjacjaA4Portrait();
                PdfWriter writer = inicjacjaWritera(document, nazwa);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                dodajOpisWstepny(document, "Potwierdzenia VIES ", wpisView.getPodatnikObiekt(),wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
                PdfMain.dodajTabeleVies(document, testobjects.testobjects.getVies(wykaz),95,0);
                finalizacjaDokumentuQR(document,nazwa);
                String f = "pokazwydruk('"+nazwa+"');";
                PrimeFaces.current().executeScript(f);
            } else {
                Msg.msg("w", "Nie ma VIES do wydruku");
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
}
