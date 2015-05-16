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
import view.WpisView;

/**
 *
 * @author Osito
 */
@Stateless
public class PdfBilans {

    public static void drukujBilans(TreeNodeExtended rootProjekt, WpisView wpisView, String ap) {
        String nazwa = null;
        if (ap.equals("a")) {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansobliczenieA";
        } else {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansobliczenieP";
        }
        File file = new File(nazwa);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjekt != null && rootProjekt.getChildren().size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            if (ap.equals("a")) {
                dodajOpisWstepny(document, "Bilans Aktywa firmy "+wpisView.getPodatnikWpisu(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            } else {
                dodajOpisWstepny(document, "Bilans Pasywa firmy "+wpisView.getPodatnikWpisu(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            }
            dodajTabele(document, testobjects.testobjects.getTabelaBilans(rootProjekt),75,0);
            finalizacjaDokumentu(document);
            String f = null;
            if (ap.equals("a")) {
                f = "pokazwydruk('"+nazwa+"');";
            } else {
                f = "pokazwydruk('"+nazwa+"');";
            }
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano strony Bilansu do wydruku");
        }
    }
    
    public static void drukujBilansBO(TreeNodeExtended rootProjekt, WpisView wpisView, String ap) {
        String nazwa = null;
        if (ap.equals("a")) {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansBOobliczenieA";
        } else {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansBOobliczenieP";
        }
        File file = new File(nazwa);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjekt != null && rootProjekt.getChildren().size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            if (ap.equals("a")) {
                dodajOpisWstepny(document, "Bilans Otwarcia Aktywa firmy "+wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            } else {
                dodajOpisWstepny(document, "Bilans Otwarcia Pasywa firmy "+wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            }
            dodajTabele(document, testobjects.testobjects.getTabelaBilans(rootProjekt),75,0);
            finalizacjaDokumentu(document);
            String f = null;
            if (ap.equals("a")) {
                f = "pokazwydruk('"+nazwa+"');";
            } else {
                f = "pokazwydruk('"+nazwa+"');";
            }
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano strony Bilansu do wydruku");
        }
    }
    
    public static void drukujBilansBOPozycje(TreeNodeExtended rootProjekt, WpisView wpisView, String ap) {
        String nazwa = null;
        if (ap.equals("a")) {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansBOPOzobliczenieA";
        } else {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansBOPOzobliczenieP";
        }
        File file = new File(nazwa);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjekt != null && rootProjekt.getChildren().size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            if (ap.equals("a")) {
                dodajOpisWstepny(document, "Bilans Otwarcia Aktywa (z nr kont) firmy "+wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            } else {
                dodajOpisWstepny(document, "Bilans Otwarcia Pasywa (z nr kont) firmy "+wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            }
            dodajTabele(document, testobjects.testobjects.getTabelaBilansKonta(rootProjekt),95,1);
            finalizacjaDokumentu(document);
            String f = null;
            if (ap.equals("a")) {
                f = "pokazwydruk('"+nazwa+"');";
            } else {
                f = "pokazwydruk('"+nazwa+"');";
            }
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano strony Bilansu do wydruku");
        }
    }
    
    public static void drukujBilansKonta(TreeNodeExtended rootProjekt, WpisView wpisView, String ap) {
        String nazwa = null;
        if (ap.equals("a")) {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansPozobliczenieA";
        } else {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansPozobliczenieP";
        }
        File file = new File(nazwa);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjekt != null && rootProjekt.getChildren().size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            if (ap.equals("a")) {
                dodajOpisWstepny(document, "Bilans Aktywa z nr kont firmy "+wpisView.getPodatnikWpisu(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            } else {
                dodajOpisWstepny(document, "Bilans Pasywa z nr kont firmy "+wpisView.getPodatnikWpisu(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            }
            dodajTabele(document, testobjects.testobjects.getTabelaBilansKontaPrzyporzadkowane(rootProjekt),95,2);
            finalizacjaDokumentu(document);
            String f = null;
            if (ap.equals("a")) {
                f = "pokazwydruk('"+nazwa+"');";
            } else {
                f = "pokazwydruk('"+nazwa+"');";
            }
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano strony Bilansu do wydruku");
        }
    }
    
}
