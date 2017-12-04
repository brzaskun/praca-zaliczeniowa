/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Podatnik;
import java.io.File;
import java.util.List;
import msg.Msg;
import org.primefaces.context.RequestContext;
import static pdffk.PdfMain.*;
import plik.Plik;
import view.WpisView;

/**
 *
 * @author Osito
 */
public class PdfPodatnicy {
    public static void drukuj(List<Podatnik> podatnicy, WpisView wpisView) {
        String nazwa = "podatnicywykaz";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (podatnicy != null && podatnicy.size() > 0) {
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Wykaz podatników", wpisView.getPodatnikObiekt(),null, wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getPodatnicy(podatnicy),95,2);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano Planu kont do wydruku");
        }
    }
}
