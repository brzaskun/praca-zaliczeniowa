/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Dok;
import error.E;
import format.F;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.primefaces.context.RequestContext;
import pdffk.PdfMain;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaA4Landscape;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class PdfDok extends Pdf implements Serializable {
    
    public static void drukujDok(List<Dok> lista, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"listadok";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        Document document = inicjacjaA4Landscape();
        try {
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Zestawienie zaksięgowanych dokumentów firma -  ", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getListaDok(lista),100,0);
            double netto = 0.0;
            double vat = 0.0;
            for (Dok p : lista) {
                netto = netto+p.getNetto();
                vat = vat+p.getVat();
            }
            double brutto = Z.z(netto+vat);
            String opis = "Razem wartość wybranych dokumentów";
            PdfMain.dodajLinieOpisu(document, opis);
            opis = "netto: "+F.curr(netto)+" vat: "+F.curr(vat)+" brutto: "+F.curr(brutto);
            PdfMain.dodajLinieOpisu(document, opis);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } catch (Exception e) {
            E.e(e);
        } finally {
            finalizacjaDokumentuQR(document,nazwa);
        }
    }
    
}
