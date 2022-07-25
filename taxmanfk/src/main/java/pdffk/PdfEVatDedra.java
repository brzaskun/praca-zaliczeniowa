/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Uz;
import entityfk.EVatwpisDedra;
import java.io.File;
import java.util.List;
import msg.B;
import msg.Msg;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaA4Portrait;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;
import view.WpisView; import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */
public class PdfEVatDedra {
    
    public static void drukuj(List<EVatwpisDedra> wykazkont, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"eVatwpisDedra";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (wykazkont != null && wykazkont.size() > 0) {
            Uz uz = wpisView.getUzer();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, B.b("EwidencjaVAT")+" "+B.b("firma"), wpisView.getPodatnikObiekt(),null, wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getTabelaEVatwpisDedra(wykazkont),95,0);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano Planu kont do wydruku");
        }
    }

}
