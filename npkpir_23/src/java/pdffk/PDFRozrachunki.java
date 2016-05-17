/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Uz;
import entityfk.Konto;
import entityfk.StronaWiersza;
import java.io.File;
import java.util.List;
import javax.ejb.Stateless;
import msg.B;
import msg.Msg;
import org.primefaces.context.RequestContext;
import static pdffk.PdfMain.*;
import plik.Plik;
import view.WpisView;

/**
 *
 * @author Osito
 */

public class PDFRozrachunki {
    
    public static void drukujRozrachunki(List<StronaWiersza> stronyWiersza, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"rozrachunki";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (stronyWiersza != null && stronyWiersza.size() > 0) {
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, B.b("zestawienierozrachunków"),wpisView.getPodatnikObiekt(), null, wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getTabelaRozrachunki(stronyWiersza),95,1);
            finalizacjaDokumentu(document);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano Planu kont do wydruku");
        }
    }
}
