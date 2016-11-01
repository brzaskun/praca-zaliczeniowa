/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import data.Data;
import entity.Uz;
import entityfk.Konto;
import entityfk.StronaWiersza;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            String kontrahent = stronyWiersza.get(0).getKonto().getNazwapelna();
            dodajOpisWstepny(document, B.b("zestawienierozrachunków")+" "+kontrahent,wpisView.getPodatnikObiekt(), null, wpisView.getRokWpisuSt());
            dodajLinieOpisu(document, "wydruk na dzień "+Data.ostatniDzien(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
            for (StronaWiersza p : stronyWiersza) {
                List<StronaWiersza> l = new ArrayList<>();
                l.add(p);
                try {
                    int kat = p.getDokfk().getRodzajedok().getKategoriadokumentu();
                    String transakcja = kat == 2 || kat == 4 ? "należność dla nas" : "zobowiązanie względem kontrahenta";
                    PdfMain.dodajLinieOpisuBezOdstepu(document, transakcja);
                    document.add(dodajSubTabele(testobjects.testobjects.getTabelaRozrachunki(l),95,1,8));
                    PdfMain.dodajLinieOpisu(document, " ");
                } catch (DocumentException ex) {
                    Logger.getLogger(PDFRozrachunki.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            String sp = wpisView.getWprowadzil().getImie()+" "+wpisView.getWprowadzil().getNazw();
            dodajLinieOpisu(document, "..................... ");
            dodajLinieOpisu(document, "sporządzający "+wpisView.getWprowadzil().getImieNazwisko()+" dnia "+Data.aktualnaData());
            dodajLinieOpisu(document, " ");
            dodajLinieOpisu(document, "..................... ");
            dodajLinieOpisu(document, "salda zgodnie potwierdzam");
            dodajLinieOpisu(document, kontrahent);
            finalizacjaDokumentu(document);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano Planu kont do wydruku");
        }
    }
    
//    public static void drukujRozrachunki(List<StronaWiersza> stronyWiersza, WpisView wpisView) {
//        String nazwa = wpisView.getPodatnikObiekt().getNip()+"rozrachunki";
//        File file = Plik.plik(nazwa, true);
//        if (file.isFile()) {
//            file.delete();
//        }
//        if (stronyWiersza != null && stronyWiersza.size() > 0) {
//            Document document = inicjacjaA4Portrait();
//            PdfWriter writer = inicjacjaWritera(document, nazwa);
//            naglowekStopkaP(writer);
//            otwarcieDokumentu(document, nazwa);
//            dodajOpisWstepny(document, B.b("zestawienierozrachunków"),wpisView.getPodatnikObiekt(), null, wpisView.getRokWpisuSt());
//            dodajTabele(document, testobjects.testobjects.getTabelaRozrachunki(stronyWiersza),95,1);
//            finalizacjaDokumentu(document);
//            String f = "pokazwydruk('"+nazwa+"');";
//            RequestContext.getCurrentInstance().execute(f);
//        } else {
//            Msg.msg("w", "Nie wybrano Planu kont do wydruku");
//        }
//    }
}
