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
import formatpdf.F;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import msg.Msg;
import org.apache.commons.lang3.RandomStringUtils;
import org.primefaces.PrimeFaces;
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
        
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"faktrozrach"+PdfMain.losowanazwa();
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
            dodajOpisWstepnyFaktury(document, "Zadłużenie wobec/debt to/Schulden gegenüber  ",wpisView.getPodatnikObiekt().getNazwadlafaktury(), wpisView.getPodatnikObiekt().getNip(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajLinieOpisu(document, "kontrahent/client/Mandant "+szukanyklient.getNpelna());
            dodajLinieOpisu(document, "NIP: "+szukanyklient.getNip());
            dodajTabele(document, testobjects.testobjects.getFakturaRozrachunki(nowepozycje, 0),70,0);
            double faktura = 0.0;
            double przelew = 0.0;
            double saldo = 0.0;
            for (FakturaPodatnikRozliczenie fr : nowepozycje) {
                 if (fr.isFaktura0rozliczenie1()) {
                    przelew = przelew + fr.getKwota();
                } else {
                    faktura = faktura + fr.getKwota();
                }
            }
            saldo = faktura-przelew;
            if (saldo > 0) {
                dodajLinieOpisu(document, " ");
//                if (n.getSaldo()!=0) {
//                    dodajLinieOpisu(document, "total/insgesamt/kwota do zapłaty na dzień sporządzenia faktur w Euro : "+F.curr(n.getSaldo(),"EUR"));
//                    dodajLinieOpisu(document, "total/insgesamt/kwota do zapłaty na dzień sporządzenia w przeliczeniu na PLN: "+F.curr(n.getSaldopln()));
//                }
                dodajLinieOpisu(document, "total/insgesamt/kwota do zapłaty na dzień sporządzenia w PLN: "+F.curr(saldo));
                
                dodajLinieOpisu(document, "");
                dodajLinieOpisu(document, "Szczecin "+Data.aktualnaData());
            }
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Błąd wydruku rozrachunków z klientem");
        }
    }
    
    public static String drukujKlienciSilent(Klienci szukanyklient, List<FakturaPodatnikRozliczenie> nowepozycje, WpisView wpisView, double saldo) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"faktrozrach"+PdfMain.losowanazwa();
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
            dodajOpisWstepnyFaktury(document, "Zadłużenie wobec/debt to/Schulden gegenüber  ",wpisView.getPodatnikObiekt().getNazwadlafaktury(), wpisView.getPodatnikObiekt().getNip(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajLinieOpisuBezOdstepu(document, "firma: "+szukanyklient.getNpelna());
            dodajLinieOpisu(document, "NIP: "+szukanyklient.getNip());
            dodajTabele(document, testobjects.testobjects.getFakturaRozrachunki(nowepozycje, 0),60,0);
            if (saldo > 0) {
                dodajLinieOpisu(document, " ");
//                if (n.getSaldo()!=0) {
//                    dodajLinieOpisu(document, "total/insgesamt/kwota do zapłaty na dzień sporządzenia faktur w Euro : "+F.curr(n.getSaldo(),"EUR"));
//                    dodajLinieOpisu(document, "total/insgesamt/kwota do zapłaty na dzień sporządzenia w przeliczeniu na PLN: "+F.curr(n.getSaldopln()));
//                }
                dodajLinieOpisu(document, "total/insgesamt/kwota do zapłaty na dzień sporządzenia: "+F.curr(saldo));
                dodajLinieOpisu(document, " ");
                dodajLinieOpisu(document, "proszę sprawdzić saldo i przelać je niezwłocznie na nr konta podany na fakturze");
                dodajLinieOpisu(document, "dziękuję");
                dodajLinieOpisu(document, " ");
                dodajLinieOpisu(document, "please check the total and make the bank transfer ASAP");
                dodajLinieOpisu(document, "thank you");
                dodajLinieOpisu(document, " ");
                dodajLinieOpisu(document, "bitte prüfen Sie die Summe und tätigen Sie die Überweisung ASAP");
                dodajLinieOpisu(document, "danke schön");
                dodajLinieOpisu(document, " ");
                dodajLinieOpisu(document, "Szczecin, "+Data.aktualnaData());
            }
            finalizacjaDokumentuQR(document,nazwa);
        } else {
            Msg.msg("w", "Błąd wydruku rozrachunków z klientem");
        }
        return nazwa;
    }
    
    public static void drukujKlienciZbiorcze(List<FakturaPodatnikRozliczenie> zbiorcze, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"faktrozrach"+PdfMain.losowanazwa();
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
    
    public static void main(String[] arg) {
        int length = 10;
    boolean useLetters = true;
    boolean useNumbers = false;
    String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
 
    error.E.s(generatedString);
    }

    public static void drukujKliencihurt(Map<Klienci, List<FakturaPodatnikRozliczenie>> klista, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"faktrozrach"+PdfMain.losowanazwa();
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (klista != null && klista.size() > 0) {
            Uz uz = wpisView.getUzer();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            Iterator<Map.Entry<Klienci, List<FakturaPodatnikRozliczenie>>> itr = klista.entrySet().iterator(); 
            while(itr.hasNext()) {
                Map.Entry<Klienci, List<FakturaPodatnikRozliczenie>> entry = itr.next(); 
                Klienci szukanyklient = entry.getKey();
                List<FakturaPodatnikRozliczenie> nowepozycje = entry.getValue();
                dodajOpisWstepnyFaktury(document, "Rozrachunki  z ",wpisView.getPodatnikObiekt().getNazwadlafaktury(), wpisView.getPodatnikObiekt().getNip(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
                dodajLinieOpisuBezOdstepu(document, "firma: "+szukanyklient.getNpelna());
                dodajLinieOpisu(document, "NIP: "+szukanyklient.getNip());
                dodajTabele(document, testobjects.testobjects.getFakturaRozrachunki(nowepozycje, 0),90,0);
                FakturaPodatnikRozliczenie n = nowepozycje.get(nowepozycje.size()-1);
                if (n.getSaldopln()> 0) {
                    dodajLinieOpisu(document, " ");
                    if (n.getSaldo()!=0) {
                        dodajLinieOpisu(document, "kwota do zapłaty na dzień sporządzenia faktur w Euro : "+F.curr(n.getSaldo(),"EUR"));
                        dodajLinieOpisu(document, "kwota do zapłaty na dzień sporządzenia w przeliczeniu na pln: "+F.curr(n.getSaldopln()));
                    }
                    dodajLinieOpisu(document, "kwota do zapłaty na dzień sporządzenia w pln: "+F.curr(n.getSaldopln()));
                    dodajLinieOpisu(document, "proszę sprawdzić saldo i przelać je niezwłocznie na nr konta podany na fakturze");
                    dodajLinieOpisu(document, "dziękuję");
                    dodajLinieOpisu(document, " ");
                    dodajLinieOpisu(document, "sporządzono dnia "+Data.aktualnaData());
                }
                document.newPage();
            }
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Błąd wydruku rozrachunków z klientem");
        }
    }
}
