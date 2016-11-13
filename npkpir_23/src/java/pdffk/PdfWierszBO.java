/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Uz;
import entityfk.WierszBO;
import java.io.File;
import java.text.NumberFormat;
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

public class PdfWierszBO {
    public static void drukujWierszeBO(List<WierszBO> pobranetransakcje, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"wierszBO";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (pobranetransakcje != null && pobranetransakcje.size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Zestawienie wierszy BO w firmie", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getTabelaWierszBO(pobranetransakcje),97,0);
            dodajLinieOpisu(document, obliczsumy(pobranetransakcje));
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano wierszy BO do wydruku");
        }
    }
    
    public static void drukujListaKonsolidacyjna(List<WierszBO> pobranetransakcje, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"wierszBO";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (pobranetransakcje != null && pobranetransakcje.size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Zestawienie wierszy BO w firmie", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getTabelaWierszBOKonsolidacyjna(pobranetransakcje),97,1);
            dodajLinieOpisu(document, obliczsumy(pobranetransakcje));
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano wierszy BO do wydruku");
        }
    }

    private static String obliczsumy(List<WierszBO> pobranetransakcje) {
        double wn = 0.0;
        double ma = 0.0;
        double wnpln = 0.0;
        double mapln = 0.0;
        for (WierszBO p : pobranetransakcje) {
            wn += p.getKwotaWn();
            ma += p.getKwotaMa();
            wnpln += p.getKwotaWnPLN();
            mapln += p.getKwotaMaPLN();
        }
        NumberFormat formatter = getNumberFormater();
        return "Suma wn: "+formatter.format(wn)+", suma ma: "+formatter.format(ma)+", suma wnpln: "+formatter.format(wnpln)+", suma mapln: "+formatter.format(mapln);
    }
}
