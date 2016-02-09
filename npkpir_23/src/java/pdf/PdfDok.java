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
import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.primefaces.context.RequestContext;
import pdffk.PdfMain;
import static pdffk.PdfMain.dodajDate;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.dodajpodpis;
import static pdffk.PdfMain.finalizacjaDokumentu;
import static pdffk.PdfMain.infooFirmie;
import static pdffk.PdfMain.informacjaoZaksiegowaniu;
import static pdffk.PdfMain.inicjacjaA4Landscape;
import static pdffk.PdfMain.inicjacjaA4Portrait;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import static pdffk.PdfMain.saldokoncowe;
import static pdffk.PdfMain.saldopoczatkowe;
import plik.Plik;
import view.WpisView;

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
            dodajOpisWstepny(document, "Zestawienie zaksięgowanych dokumentów firma -  "+wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt()+"/"+wpisView.getMiesiacWpisu());
            dodajTabele(document, testobjects.testobjects.getListaDok(lista),100,0);
            finalizacjaDokumentu(document);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } catch (Exception e) {
            E.e(e);
        } finally {
            finalizacjaDokumentu(document);
        }
        
    }
    
}
