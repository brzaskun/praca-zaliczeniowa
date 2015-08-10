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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.ejb.Stateless;
import msg.B;
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
public class PdfBilans {

    public static void drukujBilans(TreeNodeExtended rootProjekt, WpisView wpisView, String ap, double sumabilansowa) {
        String nazwa = null;
        if (ap.equals("a")) {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansObliczenieAktywa-"+wpisView.getRokWpisuSt();
        } else {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansobliczeniePasywa-"+wpisView.getRokWpisuSt();
        }
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjekt != null && rootProjekt.getChildren().size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String sumatxt = formatter.format(sumabilansowa);
            if (ap.equals("a")) {
                dodajOpisWstepny(document, B.b("BilansAktywafirmy")+" "+wpisView.getPodatnikWpisu()+" suma aktywów - "+sumatxt, wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            } else {
                dodajOpisWstepny(document, B.b("BilansPasywafirmy")+" "+wpisView.getPodatnikWpisu()+" suma pasywów - "+sumatxt, wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
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
    
    public static void drukujBilansBO(TreeNodeExtended rootProjekt, WpisView wpisView, String ap, double sumabilansowa) {
        String nazwa = null;
        if (ap.equals("a")) {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansBOobliczenieAktywa-"+wpisView.getRokWpisuSt();
        } else {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansBOobliczeniePasywa-"+wpisView.getRokWpisuSt();
        }
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjekt != null && rootProjekt.getChildren().size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String sumatxt = formatter.format(sumabilansowa);
            if (ap.equals("a")) {
                dodajOpisWstepny(document, "Bilans Otwarcia Aktywa firmy "+wpisView.getPodatnikWpisu()+" suma aktywów - "+sumatxt, wpisView.getRokWpisuSt());
            } else {
                dodajOpisWstepny(document, "Bilans Otwarcia Pasywa firmy "+wpisView.getPodatnikWpisu()+" suma pasywów - "+sumatxt, wpisView.getRokWpisuSt());
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
    
    public static void drukujBilansBOPozycje(TreeNodeExtended rootProjekt, WpisView wpisView, String ap, double sumabilansowa) {
        String nazwa = null;
        if (ap.equals("a")) {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansBOPOzobliczenieAktywa-"+wpisView.getRokWpisuSt();
        } else {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansBOPOzobliczeniePasywa-"+wpisView.getRokWpisuSt();
        }
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjekt != null && rootProjekt.getChildren().size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String sumatxt = formatter.format(sumabilansowa);
            if (ap.equals("a")) {
                dodajOpisWstepny(document, "Bilans Otwarcia Aktywa (z nr kont) firmy "+wpisView.getPodatnikWpisu()+" suma aktywów - "+sumatxt, wpisView.getRokWpisuSt());
            } else {
                dodajOpisWstepny(document, "Bilans Otwarcia Pasywa (z nr kont) firmy "+wpisView.getPodatnikWpisu()+" suma pasywów - "+sumatxt, wpisView.getRokWpisuSt());
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
    
    public static void drukujBilansKonta(TreeNodeExtended rootProjekt, WpisView wpisView, String ap, double sumabilansowa) {
        String nazwa = null;
        if (ap.equals("a")) {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansPozobliczenieAktywa-"+wpisView.getRokWpisuSt();
        } else {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"BilansPozobliczeniePasywa-"+wpisView.getRokWpisuSt();
        }
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (rootProjekt != null && rootProjekt.getChildren().size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String sumatxt = formatter.format(sumabilansowa);
            if (ap.equals("a")) {
                dodajOpisWstepny(document, "Bilans Aktywa z nr kont firmy "+wpisView.getPodatnikWpisu()+" suma aktywów - "+sumatxt, wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            } else {
                dodajOpisWstepny(document, "Bilans Pasywa z nr kont firmy "+wpisView.getPodatnikWpisu()+" suma pasywów - "+sumatxt, wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
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
    
    public static void main(String[] args) {
        double sumabilansowa = 233445.11;
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String sumatxt = formatter.format(sumabilansowa);
        System.out.println(sumatxt);
    }
}
