/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import embeddablefk.ImportJPKSprzedaz;
import entity.Uz;
import entityfk.Wiersz;
import java.io.File;
import java.util.List;
import msg.Msg;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;
import view.WpisView; import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */
public class PdfJPKSprzedaz {
    
     public static void drukuj(List<ImportJPKSprzedaz> wiersze, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"jpksprzedaz";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (wiersze != null && wiersze.size() > 0) {
            Uz uz = wpisView.getUzer();
            Document document = PdfMain.inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Zestawienie importu z JPK_FA ", wpisView.getPodatnikObiekt(),null, wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getTabelaImportJPKSprzedaz(wiersze),95,0);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano wierszy do wydruku");
        }
    }
}
