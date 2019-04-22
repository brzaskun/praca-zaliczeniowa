/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Dok;
import entityfk.Tabelanbp;
import error.E;
import format.F;
import java.io.File;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import pdffk.PdfMain;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaA4Landscape;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;
import view.WpisView; import org.primefaces.PrimeFaces;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class PdfDok extends Pdf implements Serializable {
    
    public static void drukujDok(List<Dok> lista, WpisView wpisView, int modyfikator, String wybranacechadok) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"listadok";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        Document document = inicjacjaA4Landscape();
        try {
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Zestawienie zaksięgowanych dokumentów firma  - "+wpisView.getPrintNazwa(), wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            if (wybranacechadok != null) {
                PdfMain.dodajLinieOpisuBezOdstepu(document, "wybrano dokumenty z cechą: "+wybranacechadok);
            }
            dodajTabele(document, testobjects.testobjects.getListaDok(lista),100,modyfikator);
            double netto = 0.0;
            double vat = 0.0;
            for (Dok p : lista) {
                netto = netto+p.getNetto();
                vat = vat+p.getVat();
            }
            double brutto = Z.z(netto+vat);
            String opis = "Razem wartość wybranych dokumentów";
            PdfMain.dodajLinieOpisu(document, opis);
            opis = "netto: "+F.curr(netto)+" vat: "+F.curr(vat)+" brutto: "+F.curr(brutto);
            PdfMain.dodajLinieOpisu(document, opis);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } catch (Exception e) {
            E.e(e);
        } finally {
            finalizacjaDokumentuQR(document,nazwa);
        }
    }
    
    public static void drukujDokGuest(List<Dok> lista, WpisView wpisView, int modyfikator, String cecha) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"listadok";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        Document document = inicjacjaA4Landscape();
        try {
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Zestawienie zaksięgowanych dokumentów firma - "+wpisView.getPrintNazwa(), wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            if (cecha!=null) {
                PdfMain.dodajLinieOpisu(document, "Cecha: "+cecha);
            }
            dodajTabele(document, testobjects.testobjects.getListaDok(lista),100,modyfikator);
            double przychody = 0.0;
            double koszty = 0.0;
            for (Dok p : lista) {
                if (p.getRodzajedok().getKategoriadokumentu() == 2 || p.getRodzajedok().getKategoriadokumentu() == 4) {
                    przychody = przychody+p.getNetto();
                } else {
                    koszty = koszty+p.getNetto();
                }
            }
            double roznica = Z.z(przychody+koszty);
            String opis = "Razem wartość wybranych dokumentów";
            PdfMain.dodajLinieOpisu(document, opis);
            opis = "przychody: "+F.curr(przychody)+" koszty: "+F.curr(koszty)+" różnica: "+F.curr(roznica);
            PdfMain.dodajLinieOpisu(document, opis);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } catch (Exception e) {
            E.e(e);
        } finally {
            finalizacjaDokumentuQR(document,nazwa);
        }
    }
    
    public static void drukujDokAmazon(List<Dok> lista, WpisView wpisView, int modyfikator) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"listadok";
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
            PdfMain.dodajLinieOpisu(document, "Waluta pliku jpk "+lista.get(0).getSymbolWaluty());
            Set<String> waluty = pobierzwaluty(lista);
            Set<String> kraje = pobierzkraje(lista);
            List<Dok> sprzedaz = lista.stream().filter((p)->p.getRodzajedok().getSkrotNazwyDok().equals("SZ")).collect(Collectors.toList());
            for (String kraj : kraje) {
                List<Dok> sprzedazkraj = sprzedaz.stream().filter((p)->p.getAmazonCSV().getJurisdictionName().equals(kraj)).collect(Collectors.toList());
                for (String waluta : waluty) {
                    List<Dok> sprzedazwaluty = sprzedazkraj.stream().filter((p)->p.getAmazonCSV().getCurrency().equals(waluta)).collect(Collectors.toList());
                    if (!sprzedazwaluty.isEmpty()) {
                        dodajtabelekraj(kraj, waluta, document, sprzedazwaluty, modyfikator);
                    }
                }
            }
            PdfMain.dodajLinieOpisu(document, "");
            List<Dok> wdt = lista.stream().filter((p)->p.getRodzajedok().getSkrotNazwyDok().equals("WDT")).collect(Collectors.toList());
            if (wdt.isEmpty()) {
                PdfMain.dodajLinieOpisuBezOdstepu(document, "BRAK SPRZEDAŻY WDT");
            } else {
                for (String kraj : kraje) {
                List<Dok> sprzedazkraj = wdt.stream().filter((p)->p.getAmazonCSV().getJurisdictionName().equals(kraj)).collect(Collectors.toList());
                for (String waluta : waluty) {
                    List<Dok> sprzedazwaluty = sprzedazkraj.stream().filter((p)->p.getAmazonCSV().getCurrency().equals(waluta)).collect(Collectors.toList());
                    if (!sprzedazwaluty.isEmpty()) {
                        dodajtabeleWDT(kraj, waluta, document, sprzedazwaluty, modyfikator);
                    }
                }
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
    
    private static void dodajtabelekraj(String kraj, String waluta, Document document, List<Dok> lista, int modyfikator) {
        PdfMain.dodajLinieOpisuBezOdstepuKolor(document, "SPRZEDAŻ OPODATKOWANA "+kraj.toUpperCase()+" WALUTA "+waluta, BaseColor.BLUE);
        dodajTabele(document, testobjects.testobjects.getListaDokImport(lista),100,modyfikator);
        dodajsumy(lista, document, waluta);
    }
    private static void dodajtabeleWDT(String kraj, String waluta, Document document, List<Dok> lista, int modyfikator) {
        PdfMain.dodajLinieOpisuBezOdstepuKolor(document, "SPRZEDAŻ WDT "+kraj.toUpperCase()+" WALUTA "+waluta, BaseColor.BLUE);
        dodajTabele(document, testobjects.testobjects.getListaDokImport(lista),100,modyfikator);
        dodajsumy(lista, document, waluta);
    }
    
    
    public static void dodajsumy(List<Dok> lista, Document document, String waluta) {
        double netto = 0.0;
        double vat = 0.0;
        double nettowaluta = 0.0;
        double vatwaluta = 0.0;
        double nettopl = 0.0;
        double vatpl = 0.0;
        Tabelanbp tab = null;
        String invoicelevelcurrencycode = waluta;
        for (Dok p : lista) {
            netto = Z.z(netto+p.getNetto());
            vat = Z.z(vat+p.getVat());
            nettowaluta = Z.z(nettowaluta+p.getNettoWaluta());
            vatwaluta = Z.z(vatwaluta+p.getVatWalutaCSV());
            nettopl = Z.z(nettopl+Z.z(p.getNetto()*p.getTabelanbp().getKurssredniPrzelicznik()));
            vatpl = Z.z(vatpl+Z.z(p.getVat()*p.getTabelanbp().getKurssredniPrzelicznik()));
            if (p.getTabelanbp()!=null) {
                tab=p.getTabelanbp();
                invoicelevelcurrencycode = p.getAmazonCSV().getInvoiceLevelCurrencyCode();
            }
        }
        double brutto = Z.z(netto+vat);
        double bruttopln = Z.z(nettopl+vatpl);
        double bruttowal = Z.z(nettowaluta+vatwaluta);
        String opis = "Razem wartość wybranych dokumentów w PLN";
        PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
        opis = "netto: "+F.curr(nettopl)+" vat: "+F.curr(vatpl)+" brutto: "+F.curr(bruttopln);
        PdfMain.dodajLinieOpisu(document, opis);
        if (tab!=null) {
            
            if (!waluta.equals(invoicelevelcurrencycode) && !invoicelevelcurrencycode.equals("")) {
                opis = "Razem wartość wybranych dokumentów - kraju jurysdykcji "+invoicelevelcurrencycode;
                PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
                opis = "netto wal: "+F.curr(netto, invoicelevelcurrencycode)+" vat wal: "+F.curr(vat, invoicelevelcurrencycode)+" brutto: "+F.curr(brutto, invoicelevelcurrencycode);
                PdfMain.dodajLinieOpisu(document, opis);
                String wal = tab.getWaluta().getSymbolwaluty();
                opis = "Razem wartość wybranych dokumentów - waluta dokumentu "+waluta;
                PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
                opis = "netto wal: "+F.curr(nettowaluta, waluta)+" vat wal: "+F.curr(vatwaluta, waluta)+" brutto: "+F.curr(bruttowal, waluta);
                PdfMain.dodajLinieOpisu(document, opis);
            } else {
                String wal = tab.getWaluta().getSymbolwaluty();
                opis = "Razem wartość wybranych dokumentów - waluta "+waluta;
                PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
                opis = "netto wal: "+F.curr(netto, waluta)+" vat wal: "+F.curr(vat, waluta)+" brutto: "+F.curr(brutto, waluta);
                PdfMain.dodajLinieOpisu(document, opis);
            }
        }
    }
    
     public static void drukujJPK_FA(List<Dok> lista, WpisView wpisView, int modyfikator, boolean deklaracjaniemiecka) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"listadok";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        Document document = PdfMain.inicjacjaA4Portrait();
        try {
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Zestawienie zaksięgowanych dokumentów firma -  "+wpisView.getPrintNazwa(), wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            PdfMain.dodajLinieOpisu(document, "Waluta pliku jpk "+lista.get(0).getSymbolWaluty());
            dodajTabele(document, testobjects.testobjects.getListaDokImport(lista),100,modyfikator);
            double netto = 0.0;
            double vat = 0.0;
            double nettowaluta = 0.0;
            double vatwaluta = 0.0;
            double nettopl = 0.0;
            double vatpl = 0.0;
            Tabelanbp tab = null;
            for (Dok p : lista) {
                netto = netto+p.getNetto();
                vat = vat+p.getVat();
                nettowaluta = nettowaluta+p.getNettoWaluta();
                vatwaluta = vatwaluta+p.getVatWalutaCSV();
                if (deklaracjaniemiecka) {
                    nettopl = nettopl+Z.z(p.getNetto()*p.getTabelanbp().getKurssredniPrzelicznik());
                    vatpl = vatpl+Z.z(p.getVat()*p.getTabelanbp().getKurssredniPrzelicznik());
                }
                tab = p.getTabelanbp() !=null? p.getTabelanbp() : null;
            }
            double brutto = Z.z(netto+vat);
            double bruttowal = Z.z(nettowaluta+vatwaluta);
            if (deklaracjaniemiecka) {
                String opis = "Razem wartość wybranych dokumentów w PLN";
                PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
                opis = "netto: "+F.curr(nettopl)+" vat: "+F.curr(vatpl)+" brutto: "+F.curr(Z.z(nettopl+vatpl));
                PdfMain.dodajLinieOpisu(document, opis);
                String wal = tab.getWaluta().getSymbolwaluty();
                opis = "Razem wartość wybranych dokumentów - waluta dokumentów "+wal;
                PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
                opis = "netto wal: "+F.curr(nettowaluta, wal)+" vat wal: "+F.curr(vatwaluta, wal)+" brutto: "+F.curr(bruttowal, wal);
                PdfMain.dodajLinieOpisu(document, opis);
            } else {
                String opis = "Razem wartość wybranych dokumentów";
                PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
                opis = "netto: "+F.curr(netto)+" vat: "+F.curr(vat)+" brutto: "+F.curr(brutto);
                PdfMain.dodajLinieOpisu(document, opis);
                if (tab!=null && !tab.getWaluta().getSymbolwaluty().equals("PLN")) {
                    opis = "waluta dokumentów "+tab.getWaluta().getSymbolwaluty();
                    PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
                    opis = "netto wal: "+F.curr(nettowaluta, tab.getWaluta().getSymbolwaluty())+" vat wal: "+F.curr(vatwaluta, tab.getWaluta().getSymbolwaluty())+" brutto: "+F.curr(bruttowal, tab.getWaluta().getSymbolwaluty());
                    PdfMain.dodajLinieOpisu(document, opis);
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

    private static Set<String> pobierzwaluty(List<Dok> lista) {
        Set<String> zwrot = new HashSet<>();
        for (Dok p : lista) {
            String poz = p.getAmazonCSV().getCurrency();
            if (poz!=null) {
                zwrot.add(poz);
            }
        }
        return zwrot;
    }

    private static Set<String> pobierzkraje(List<Dok> lista) {
        Set<String> zwrot = new HashSet<>();
        for (Dok p : lista) {
            String poz = p.getAmazonCSV().getJurisdictionName();
            if (poz!=null) {
                zwrot.add(poz);
            }
        }
        return zwrot;
    }
    
}
