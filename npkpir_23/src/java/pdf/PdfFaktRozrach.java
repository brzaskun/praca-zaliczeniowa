/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.FakturaPodatnikRozliczenie;
import entity.Klienci;
import entity.Uz;
import entityfk.Konto;
import java.io.File;
import java.util.List;
import msg.Msg;
import org.primefaces.context.RequestContext;
import pdffk.PdfMain;
import static pdffk.PdfMain.*;
import plik.Plik;
import view.WpisView;

/**
 *
 * @author Osito
 */
public class PdfFaktRozrach {
    
    public static void drukujKlienci(Klienci szukanyklient, List<FakturaPodatnikRozliczenie> nowepozycje, List<FakturaPodatnikRozliczenie> archiwum, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"faktrozrach";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (nowepozycje != null && nowepozycje.size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Rozrachunki  "+wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            dodajLinieOpisu(document, "kontrahent "+szukanyklient.getNpelna());
            dodajTabele(document, testobjects.testobjects.getFakturaRozrachunki(nowepozycje, 0),90,0);
            finalizacjaDokumentu(document);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Błąd wydruku rozrachunków z klientem");
        }
    }
    
    public static void drukujKlienciZbiorcze(List<FakturaPodatnikRozliczenie> zbiorcze, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"faktrozrach";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (zbiorcze != null && zbiorcze.size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Rozrachunki  "+wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            dodajLinieOpisu(document, "wszyscy kontrahenci podatnika");
            dodajTabele(document, testobjects.testobjects.getFakturaRozrachunki(zbiorcze, 1),90,1);
            finalizacjaDokumentu(document);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Błąd wydruku zestawienia rozrachunków zbiorczych");
        }
    }
}
