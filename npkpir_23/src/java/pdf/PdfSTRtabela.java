/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.ZestawienieRyczalt;
import entity.SrodekTrw;
import entity.Uz;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.ejb.Stateless;
import msg.Msg;
import org.primefaces.context.RequestContext;
import pdffk.PdfMain;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentu;
import static pdffk.PdfMain.inicjacjaA4Portrait;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import testobjects.testobjects;
import view.WpisView;

/**
 *
 * @author Osito
 */

public class PdfSTRtabela {
    
    
     public static void drukujSTRtabela(WpisView wpisView, List<SrodekTrw> pobranesrodki) throws DocumentException, FileNotFoundException, IOException {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"ryczalt";
        File file = new File(nazwa);
        if (file.isFile()) {
            file.delete();
        }
        if (pobranesrodki != null && pobranesrodki.size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = PdfMain.inicjacjaA4Landscape();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Zestawienie posiadanych środków trwałych w firmie: "+wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.getSrodkiTRWlista(pobranesrodki),97,0);
            finalizacjaDokumentu(document);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano Zestawienia Ryczałtu do wydruku");
        }
    }
}
