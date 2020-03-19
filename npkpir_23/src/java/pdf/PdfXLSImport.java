/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import embeddablefk.InterpaperXLS;
import entity.Dok;
import entityfk.Tabelanbp;
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
import pdffk.PdfMain;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.dodajTabele2;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
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
public class PdfXLSImport {
    
    public static void drukuj(List<InterpaperXLS> lista, WpisView wpisView, int modyfikator) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"listaimport";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        Document document = PdfMain.inicjacjaA4Landscape();
        try {
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Zestawienie pobranych dokumenrtów firma -  ", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            Set<String> waluty = pobierzwaluty(lista);
            Set<String> kraje = pobierzkraje(lista);
            List tabelazbiorcza = new ArrayList<>();
            for (String kraj : kraje) {
                List<InterpaperXLS> sprzedazkraj = lista.stream().filter((p)->p.getKlientpaństwo().equals(kraj)).collect(Collectors.toList());
                for (String waluta : waluty) {
                    List<InterpaperXLS> sprzedazwaluty = sprzedazkraj.stream().filter((p)->p.getWalutaplatnosci().equals(waluta)).collect(Collectors.toList());
                    if (!sprzedazwaluty.isEmpty()) {
                        dodajtabelekraj(kraj, waluta, document, sprzedazwaluty, modyfikator, tabelazbiorcza);
                    }
                }
            }
            document.newPage();
            String[] nag1 = new String[]{"lp","kraj","waluta","netto","vat","brutto","nettopln","vatpln","bruttopln", "stawka vat"};
            int[] nag2 = new int[]{2,3,3,3,3,3,3,3,3,3};
            dodajTabele2(document, nag1, nag2, tabelazbiorcza, 70, modyfikator,"tabelazorint");
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } catch (Exception e) {
            E.e(e);
        } finally {
            finalizacjaDokumentuQR(document,nazwa);
        }
    }
    
    private static void dodajtabelekraj(String kraj, String waluta, Document document, List<InterpaperXLS> lista, int modyfikator, List tabelazbiorcza) {
        PdfMain.dodajLinieOpisuBezOdstepuKolor(document, "Kraj "+kraj.toUpperCase()+" Waluta "+waluta, BaseColor.BLUE);
        dodajTabele(document, testobjects.testobjects.getListaXLSImport(lista),100,modyfikator);
        dodajsumy(lista, document, waluta, tabelazbiorcza, kraj);
    }
  
    
    
    public static void dodajsumy(List<InterpaperXLS> lista, Document document, String waluta, List tabelazbiorcza,String kraj) {
        double nettowaluta = 0.0;
        double vatwaluta = 0.0;
        double nettopl = 0.0;
        double vatpl = 0.0;
        String vatstawka = null;
        for (InterpaperXLS p : lista) {
            nettopl = Z.z(nettopl+p.getNettoPLN());
            vatpl = Z.z(vatpl+p.getVatPLN());
            nettowaluta = Z.z(nettowaluta+p.getNettowaluta());
            vatwaluta = Z.z(vatwaluta+p.getVatwaluta());
            vatstawka = p.getVatstawka();
        }
        double bruttopln = Z.z(nettopl+vatpl);
        double bruttowal = Z.z(nettowaluta+vatwaluta);
        String opis = "Razem wartość wybranych dokumentów w PLN";
        PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
        opis = "netto: "+F.curr(nettopl)+" vat: "+F.curr(vatpl)+" brutto: "+F.curr(bruttopln);
        PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
        try {
            opis = "Razem wartość wybranych dokumentów - waluta "+waluta;
            PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
            opis = "netto wal: "+F.curr(nettowaluta, waluta)+" vat wal: "+F.curr(vatwaluta, waluta)+" brutto: "+F.curr(bruttowal, waluta);
            PdfMain.dodajLinieOpisu(document, opis);
        } catch(Exception e) {}
        Object[] a = new Object[]{kraj, waluta, nettowaluta, vatwaluta, bruttowal, nettopl, vatpl, bruttopln, vatstawka};
        tabelazbiorcza.add(Arrays.asList(a));
        
    }
    
    private static Set<String> pobierzwaluty(List<InterpaperXLS> lista) {
        Set<String> zwrot = new HashSet<>();
        for (InterpaperXLS p : lista) {
            String poz = p.getWalutaplatnosci();
            if (poz!=null) {
                zwrot.add(poz);
            }
        }
        return zwrot;
    }

    private static Set<String> pobierzkraje(List<InterpaperXLS> lista) {
        Set<String> zwrot = new HashSet<>();
        for (InterpaperXLS p : lista) {
            String poz = p.getKlientpaństwo();
            if (poz!=null) {
                zwrot.add(poz);
            }
        }
        return zwrot;
    }
}
