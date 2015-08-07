/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import embeddablefk.TreeNodeExtended;
import entity.Uz;
import java.io.File;
import javax.ejb.Stateless;
import msg.Msg;
import org.primefaces.context.RequestContext;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentu;
import static pdffk.PdfMain.inicjacjaA4Portrait;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Stateless
public class PdfRZiS {

    public static void drukujRZiS(TreeNodeExtended rootProjektRZiS, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"RZiSobliczenie-"+wpisView.getRokWpisuSt();
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjektRZiS != null && rootProjektRZiS.getChildren().size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Rachunek Zysków i Strat firmy "+wpisView.getPodatnikWpisu(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getTabelaRZiS(rootProjektRZiS),75,0);
            finalizacjaDokumentu(document);
            String f = "wydrukRZiS('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano RZiS do wydruku");
        }
    }
    
    public static void drukujRZiSPozycje(TreeNodeExtended rootProjektRZiS, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"RZiSobliczenie-"+wpisView.getRokWpisuSt();
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjektRZiS != null && rootProjektRZiS.getChildren().size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Rachunek Zysków i Strat firmy "+wpisView.getPodatnikWpisu(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getTabelaRZiSKonta(rootProjektRZiS),95,1);
            finalizacjaDokumentu(document);
            String f = "wydrukRZiS('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano RZiS do wydruku");
        }
    }
    
    public static void drukujRZiSKonta(TreeNodeExtended rootProjektRZiS, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"RZiSobliczenie-"+wpisView.getRokWpisuSt();
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjektRZiS != null && rootProjektRZiS.getChildren().size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Rachunek Zysków i Strat z nr kont", wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getTabelaRZiSKontaPrzyporządkowane(rootProjektRZiS),75,2);
            finalizacjaDokumentu(document);
            String f = "wydrukRZiS('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano RZiS do wydruku");
        }
    }
    
}
