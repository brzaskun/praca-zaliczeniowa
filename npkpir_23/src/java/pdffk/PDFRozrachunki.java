/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import data.Data;
import entityfk.Kliencifk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import format.F;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import msg.B;
import msg.Msg;
import org.primefaces.PrimeFaces;
import static pdffk.PdfMain.*;
import plik.Plik;
import slownie.Slownie;
import view.WpisView;

/**
 *
 * @author Osito
 */

public class PDFRozrachunki {
    
    public static void drukujRozrachunki(Konto wybranekonto, List<StronaWiersza> stronyWiersza, WpisView wpisView, Kliencifk k) {
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
            dodajOpisWstepny(document, B.b("zestawienierozrachunków")+" "+kontrahent+" NIP: "+k.getNip(),wpisView.getPodatnikObiekt(), null, wpisView.getRokWpisuSt());
            dodajLinieOpisu(document, "wydruk na dzień "+Data.ostatniDzien(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
           String wezwanie =  "Na podstawie art. 29 ustawy o rachunkowości z dnia 29 września 1994 r. (Dz. U. z 2013 r. poz."
                +"330 ze zm.), zwracamy się z prośbą o potwierdzenie, na jednym egzemplarzu zestawienia, w ciągu 14 dni,"
                +"sald wynikających z naszych ksiąg rachunkowych na wskazany dzień. "
                +"W przypadku niezgodności salda prosimy o przesłanie specyfikacji transakcji składających się"
                +" na różnice.";
           dodajLinieOpisu(document, wezwanie);
            double naleznosci = 0.0;
            double zobowiazania = 0.0;
            String symbolwaluty = "PLN";
            String skrótsymbolu = "zł";
            int kat = 0;
            for (StronaWiersza p : stronyWiersza) {
                List<StronaWiersza> l = Collections.synchronizedList(new ArrayList<>());
                l.add(p);
                symbolwaluty = p.getSymbolWalutBOiSW();
                skrótsymbolu = p.getSkrotSymbolWalutBOiSW();
                try {
                    kat = wybranekonto.getPelnynumer().startsWith("201")||wybranekonto.getPelnynumer().startsWith("203")?0:1;
                    String transakcja = kat == 0 ? "należność dla nas" : "zobowiązanie względem kontrahenta";
                    if (kat == 0) {
                        naleznosci += p.getPozostalo();
                    } else {
                        zobowiazania += p.getPozostalo();
                    }
                    PdfMain.dodajLinieOpisuBezOdstepu(document, transakcja);
                    document.add(dodajSubTabele(testobjects.testobjects.getTabelaRozrachunki(l),95,1,8));
                    PdfMain.dodajLinieOpisu(document, " ");
                } catch (DocumentException ex) {
                    // Logger.getLogger(PDFRozrachunki.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            double wartosc = Math.abs(naleznosci-zobowiazania);
            dodajLinieOpisuBezOdstepu(document, "Symbol konta: "+stronyWiersza.get(0).getKonto().getPelnynumer());
            String transakcja1 = "Razem wartość nierozliczonych faktur na dobro";
            String transakcja2 = kat == 0 ? wpisView.getPrintNazwa()+" NIP: "+wpisView.getPodatnikObiekt().getNip()+": " : kontrahent+" NIP: "+k.getNip()+": ";
            dodajLinieOpisuBezOdstepu(document, transakcja1);
            dodajLinieOpisuBezOdstepu(document, transakcja2);
            dodajLinieOpisuBezOdstepu(document, "kwota: "+F.curr(wartosc,symbolwaluty));
            dodajLinieOpisu(document, "Słownie: "+Slownie.slownie(String.valueOf(wartosc), skrótsymbolu));
            String sp = wpisView.getUzer().getImie()+" "+wpisView.getUzer().getNazw();
            dodajLinieOpisu(document, " ");
            dodajLinieOpisuBezOdstepu(document, "..................... ");
            dodajLinieOpisuBezOdstepu(document, "sporządzający "+wpisView.getUzer().getImieNazwisko()+" dnia "+Data.aktualnaData());
            dodajLinieOpisu(document, "telefon: "+wpisView.getUzer().getNrtelefonu());
            dodajLinieOpisu(document, " ");
            dodajLinieOpisuBezOdstepu(document, "..................... ");
            dodajLinieOpisu(document, "salda zgodnie potwierdzam");
            dodajLinieOpisu(document, kontrahent);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano rozrachunków do wydruku");
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
//            finalizacjaDokumentuQR(document,nazwa);
//            String f = "pokazwydruk('"+nazwa+"');";
//            PrimeFaces.current().executeScript(f);
//        } else {
//            Msg.msg("w", "Nie wybrano Planu kont do wydruku");
//        }
//    }
}
