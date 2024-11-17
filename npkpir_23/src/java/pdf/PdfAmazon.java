/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import entity.KlientJPK;
import entity.Podatnik;
import entity.PodsumowanieAmazonOSS;
import error.E;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.primefaces.PrimeFaces;
import static pdf.PdfDok.dodajsumyfk;
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

/**
 *
 * @author Osito
 */
public class PdfAmazon extends Pdf implements Serializable {
    
    public static List<PodsumowanieAmazonOSS> drukujDokAmazonfk(List<KlientJPK> lista, WpisView wpisView, int modyfikator) {
        List<PodsumowanieAmazonOSS> sumy = new ArrayList<>();
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"AmazonRaport"+wpisView.getRokWpisuSt()+wpisView.getMiesiacWpisu();
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        Document document = PdfMain.inicjacjaA4Landscape();
        try {
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Zestawienie zaksięgowanych dokumentów firma -  ", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            Set<String> waluty = pobierzwalutyfk(lista);
            Set<String> kraje = pobierzkrajefk(lista);
            List tabelazbiorcza = new ArrayList<>();
            List tabelazbiorczawdt = new ArrayList<>();
            List tabelazbiorczaexport = new ArrayList<>();
            List<KlientJPK> sprzedaz = lista.stream().filter((p) -> p.isWdt() == false && p.isEksport() == false).collect(Collectors.toList());
            for (String kraj : kraje) {
                List<KlientJPK> sprzedazkraj = sprzedaz.stream().filter((p) -> p.getJurysdykcja()!=null&&p.getJurysdykcja().equals(kraj)).collect(Collectors.toList());
                for (String waluta : waluty) {
                    List<KlientJPK> sprzedazwaluty = sprzedazkraj.stream().filter((p) -> p.getWaluta().equals(waluta)).collect(Collectors.toList());
                    if (!sprzedazwaluty.isEmpty()) {
                        PodsumowanieAmazonOSS zwrot = dodajtabelekrajfk(kraj, waluta, document, sprzedazwaluty, modyfikator, tabelazbiorcza, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                        if (zwrot.getPodatnik()!=null) {
                            sumy.add(zwrot);
                        }
                    }
                }
            }
            PdfMain.dodajLinieOpisu(document, "");
            List<KlientJPK> wdt = lista.stream().filter((p) -> p.isWdt()).collect(Collectors.toList());
            if (wdt.isEmpty()) {
                PdfMain.dodajLinieOpisuBezOdstepu(document, "BRAK SPRZEDAŻY WDT");
            } else {
                for (String kraj : kraje) {
                    List<KlientJPK> sprzedazkraj = wdt.stream().filter((p) -> p.getJurysdykcja()!=null&&p.getJurysdykcja().equals(kraj)).collect(Collectors.toList());
                    for (String waluta : waluty) {
                        List<KlientJPK> sprzedazwaluty = sprzedazkraj.stream().filter((p) -> p.getWaluta().equals(waluta)).collect(Collectors.toList());
                        if (!sprzedazwaluty.isEmpty()) {
                            dodajtabeleWDTfk(kraj, waluta, document, sprzedazwaluty, modyfikator, tabelazbiorczawdt);
                        }
                    }
                }
            }
            PdfMain.dodajLinieOpisu(document, "");
            List<KlientJPK> export = lista.stream().filter((p) -> p.isEksport()).collect(Collectors.toList());
            if (export.isEmpty()) {
                PdfMain.dodajLinieOpisuBezOdstepu(document, "BRAK SPRZEDAŻY EXPORT");
            } else {
                for (String kraj : kraje) {
                    List<KlientJPK> sprzedazkraj = export.stream().filter((p) -> p.getJurysdykcja()!=null&&p.getJurysdykcja().equals(kraj)).collect(Collectors.toList());
                    for (String waluta : waluty) {
                        List<KlientJPK> sprzedazwaluty = sprzedazkraj.stream().filter((p) -> p.getWaluta().equals(waluta)).collect(Collectors.toList());
                        if (!sprzedazwaluty.isEmpty()) {
                            dodajtabeleExportfk(kraj, waluta, document, sprzedazwaluty, modyfikator, tabelazbiorczaexport);
                        }
                    }
                }
                sumujtabelazbiorcza(tabelazbiorczaexport);
            }
            document.newPage();
            PdfMain.dodajLinieOpisu(document, "SUMY SPRZEDAŻY DETALICZNEJ");
            String[] nag1 = new String[]{"lp","kraj","waluta","netto","vat","netto pln","vat pln", "stawka vat", "kurs"};
            int[] nag2 = new int[]{2,3,3,3,3,3,3,3,3};
            dodajsume(tabelazbiorcza);
            dodajTabele2(document, nag1, nag2, tabelazbiorcza, 70, modyfikator,"tabelaklientjpkfk");
            document.newPage();
            PdfMain.dodajLinieOpisu(document, "SUMY SPRZEDAŻY WDT");
            dodajsume(tabelazbiorczawdt);
            dodajTabele2(document, nag1, nag2, tabelazbiorczawdt, 70, modyfikator,"tabelaklientjpkfk");
            document.newPage();
            PdfMain.dodajLinieOpisu(document, "SUMY SPRZEDAŻY EXPORT");
            dodajsume(tabelazbiorczaexport);
            dodajTabele2(document, nag1, nag2, tabelazbiorczaexport, 70, modyfikator,"tabelaklientjpkfk");
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } catch (Exception e) {
            E.e(e);
        } finally {
            finalizacjaDokumentuQR(document,nazwa);
        }
        return sumy;
    }
    
    
     private static void dodajsume(List tabelazbiorcza) {
        double netto = 0.0;
        double vat = 0.0;
        double nettopln = 0.0;
        double vatpln = 0.0;
        for (Object p : tabelazbiorcza) {
            List lysta = (List)p;
            netto = netto + (double)lysta.get(2);
            vat = vat + (double)lysta.get(3);
            nettopln = nettopln + (double)lysta.get(4);
            vatpln = vatpln + (double)lysta.get(5);
        }
        Object[] a = new Object[]{"", "razem", netto, vat, nettopln, vatpln, 0.0, 0.0};
        tabelazbiorcza.add(Arrays.asList(a));
               
    }

      private static Set<String> pobierzwalutyfk(List<KlientJPK> lista) {
        Set<String> zwrot = new HashSet<>();
        for (KlientJPK p : lista) {
            String poz = p.getWaluta();
            if (poz!=null) {
                zwrot.add(poz);
            }
        }
        return zwrot;
    }

    private static Set<String> pobierzkrajefk(List<KlientJPK> lista) {
        Set<String> zwrot = new HashSet<>();
        for (KlientJPK p : lista) {
            String poz = p.getJurysdykcja();
            if (poz!=null) {
                zwrot.add(poz);
            }
        }
        return zwrot;
    }
    private static PodsumowanieAmazonOSS dodajtabelekrajfk(String kraj, String waluta, Document document, List<KlientJPK> lista, int modyfikator, List tabelazbiorcza, Podatnik podatnik, String rok, String mc) {
        PdfMain.dodajLinieOpisuBezOdstepuKolor(document, "SPRZEDAŻ OPODATKOWANA "+kraj.toUpperCase()+" WALUTA "+waluta, BaseColor.BLACK);
        dodajTabele(document, testobjects.testobjects.getListaDokJPK(lista),100,modyfikator);
        PodsumowanieAmazonOSS zwrot = dodajsumyfk(lista, document, waluta, tabelazbiorcza, podatnik, rok, mc);
        return zwrot;
    }
    private static void dodajtabeleWDTfk(String kraj, String waluta, Document document, List<KlientJPK> lista, int modyfikator,List tabelazbiorcza) {
        PdfMain.dodajLinieOpisuBezOdstepuKolor(document, "SPRZEDAŻ WDT "+kraj.toUpperCase()+" WALUTA "+waluta, BaseColor.BLACK);
        dodajTabele(document, testobjects.testobjects.getListaDokJPK(lista),100,modyfikator);
        dodajsumyfk(lista, document, waluta, tabelazbiorcza, null, null, null);
    }
    
    private static void dodajtabeleExportfk(String kraj, String waluta, Document document, List<KlientJPK> lista, int modyfikator,List tabelazbiorcza) {
        PdfMain.dodajLinieOpisuBezOdstepuKolor(document, "SPRZEDAŻ EXPORT "+kraj.toUpperCase()+" WALUTA "+waluta, BaseColor.BLACK);
        dodajTabele(document, testobjects.testobjects.getListaDokJPK(lista),100,modyfikator);
        dodajsumyfk(lista, document, waluta, tabelazbiorcza, null, null, null);
    } 
    private static void sumujtabelazbiorcza(List tabelazbiorczaexport) {
//         
//         if (tabelazbiorczaexport !=null) {
//            for (List a : tabelazbiorczaexport) {
//               Object[] p = a.toArray();
//               //new Object[]{jurys, waluta, nettowaluta, vatwaluta, netto, vat, vatstawka, kurs};
//            }
//         }
    }
     
}
