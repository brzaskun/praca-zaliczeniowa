/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import data.Data;
import entity.Statystyka;
import java.io.File;
import java.util.List;
import msg.Msg;
import org.primefaces.context.RequestContext;
import static pdffk.PdfMain.dodajLinieOpisu;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaA4Portrait;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;

/**
 *
 * @author Osito
 */
public class PdfKlienciKalkulacja {
    
     public static void drukuj(List<Statystyka> wykaz) {
        String nazwa = Data.aktualnaData()+"_kalkulacja";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (wykaz != null && wykaz.size() > 0) {
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajLinieOpisu(document, "Zestawienie wskaźników opłacalności klientów");
            dodajTabele(document, testobjects.testobjects.getTabelaStatystyka(wykaz),95,0);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Błąd podczas wydruku");
        }
    }
     
}
