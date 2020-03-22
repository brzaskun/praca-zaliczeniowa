/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.FakturaEbay;
import embeddablefk.InterpaperXLS;
import error.E;
import format.F;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.primefaces.PrimeFaces;
import static pdf.PdfXLSImport.dodajsumy;
import pdffk.PdfMain;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.dodajTabele2;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaA4Portrait;
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
public class PdfEbay {
    
    public static void drukuj(List<FakturaEbay> lista, WpisView wpisView, int modyfikator) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"listaimport";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        Document document = new Document(PageSize.A4_LANDSCAPE.rotate(), 15, 15, 20, 20);
        try {
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "EBAY Zestawienie pobranych dokumenrtów firma -  ", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            Set<String> stawki = pobierzstawki(lista);
            for (String stawka : stawki) {
                List<FakturaEbay> sprzedazwaluty = lista.stream().filter((p)->p.getInklusiveMehrwertsteuersatz().equals(stawka)).collect(Collectors.toList());
                if (!sprzedazwaluty.isEmpty()) {
                    dodajtabelestawka(stawka, document, sprzedazwaluty, modyfikator);
                }
            }
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } catch (Exception e) {
            E.e(e);
        } finally {
            finalizacjaDokumentuQR(document,nazwa);
        }
    }
    public static void dodajsumy(List<FakturaEbay> lista, Document document, String stawka) {
        double nettowaluta = 0.0;
        double vatwaluta = 0.0;
        double nettopl = 0.0;
        double vatpl = 0.0;
        for (FakturaEbay p : lista) {
            //nettopl = Z.z(nettopl+p.getNetto());
            //vatpl = Z.z(vatpl+p.getVAT());
            nettowaluta = Z.z(nettowaluta+p.getNetto());
            vatwaluta = Z.z(vatwaluta+p.getVAT());
        }
        //double bruttopln = Z.z(nettopl+vatpl);
        double bruttowal = Z.z(nettowaluta+vatwaluta);
//        String opis = "Razem wartość wybranych dokumentów w PLN";
//        PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
//        opis = "netto: "+F.curr(nettopl)+" vat: "+F.curr(vatpl)+" brutto: "+F.curr(bruttopln);
//        PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
        try {
            String opis = "Razem wartość wybranych dokumentów - stawka "+stawka;
            PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
            opis = "netto wal: "+F.curr(nettowaluta, "EUR")+" vat wal: "+F.curr(vatwaluta, "EUR")+" brutto: "+F.curr(bruttowal, "EUR");
            PdfMain.dodajLinieOpisu(document, opis);
        } catch(Exception e) {}
     }
    
    private static void dodajtabelestawka(String stawka, Document document, List<FakturaEbay> lista, int modyfikator) {
        PdfMain.dodajLinieOpisuBezOdstepuKolor(document, "Stawka "+stawka, BaseColor.BLUE);
        String[] nag1 = new String[]{"lp","data wyst.","klient","kraj","stawka","nr wł","waluta","netto","vat", "paypal", "zapł.", "nazwa tow"};
        int[] nag2 = new int[]{1,2,4,2,1,3,1,3,3,3,2,5};
        dodajTabele2(document, nag1, nag2, lista, 90, modyfikator,"tabelaebay");
        dodajsumy(lista, document, stawka);
    }
    
    private static Set<String> pobierzstawki(List<FakturaEbay> lista) {
        Set<String> zwrot = new HashSet<>();
        for (FakturaEbay p : lista) {
            String poz = p.getInklusiveMehrwertsteuersatz();
            if (poz!=null) {
                zwrot.add(poz);
            }
        }
        return zwrot;
    }

    
}
