/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import data.Data;
import embeddable.FakturaPodatnikRozliczenie;
import entity.Klienci;
import entity.Uz;
import format.F;
import java.io.File;
import java.util.List;
import msg.Msg;import static pdffk.PdfMain.*;
import plik.Plik;
import view.WpisView; import org.primefaces.PrimeFaces;

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
            Uz uz = wpisView.getUzer();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepnyFaktury(document, "Rozrachunki  ",wpisView.getPodatnikObiekt().getNazwadlafaktury(), wpisView.getPodatnikObiekt().getNip(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajLinieOpisu(document, "kontrahent "+szukanyklient.getNpelna());
            dodajTabele(document, testobjects.testobjects.getFakturaRozrachunki(nowepozycje, 0),90,0);
            FakturaPodatnikRozliczenie n = nowepozycje.get(nowepozycje.size()-1);
            if (n.getSaldo() > 0) {
                dodajLinieOpisu(document, "kwota do zapłaty na dzień sporządzenia: "+F.curr(n.getSaldo()));
                dodajLinieOpisu(document, "");
                dodajLinieOpisu(document, "sporządzono dnia "+Data.aktualnaData());
            }
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Błąd wydruku rozrachunków z klientem");
        }
    }
    
    public static void drukujKlienciSilent(Klienci szukanyklient, List<FakturaPodatnikRozliczenie> nowepozycje, List<FakturaPodatnikRozliczenie> archiwum, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"faktrozrach";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (nowepozycje != null && nowepozycje.size() > 0) {
            Uz uz = wpisView.getUzer();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepnyFaktury(document, "Rozrachunki  z ",wpisView.getPodatnikObiekt().getNazwadlafaktury(), wpisView.getPodatnikObiekt().getNip(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajLinieOpisuBezOdstepu(document, "dłużnik: "+szukanyklient.getNpelna());
            dodajLinieOpisu(document, "NIP: "+szukanyklient.getNip());
            dodajTabele(document, testobjects.testobjects.getFakturaRozrachunki(nowepozycje, 0),90,0);
            FakturaPodatnikRozliczenie n = nowepozycje.get(nowepozycje.size()-1);
            if (n.getSaldo() > 0) {
                dodajLinieOpisu(document, "kwota do zapłaty na dzień sporządzenia faktur w Euro : "+F.curr(n.getSaldo(),"EUR"));
                dodajLinieOpisu(document, "kwota do zapłaty na dzień sporządzenia w przeliczeniu na pln: "+F.curr(n.getSaldopln()));
                dodajLinieOpisu(document, "proszę sprawdzić saldo i przelać je niezwłocznie na nr konta podany na fakturze");
                dodajLinieOpisu(document, "dziękuję");
                dodajLinieOpisu(document, " ");
                dodajLinieOpisu(document, "sporządzono dnia "+Data.aktualnaData());
            }
            finalizacjaDokumentuQR(document,nazwa);
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
            Uz uz = wpisView.getUzer();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Rozrachunki  ", wpisView.getPodatnikObiekt(), null, wpisView.getRokWpisuSt());
            dodajLinieOpisu(document, "wszyscy kontrahenci podatnika");
            dodajTabele(document, testobjects.testobjects.getFakturaRozrachunki(zbiorcze, 1),90,1);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Błąd wydruku zestawienia rozrachunków zbiorczych");
        }
    }
}
