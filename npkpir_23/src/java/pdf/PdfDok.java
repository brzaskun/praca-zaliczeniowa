/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Dok;
import entityfk.Tabelanbp;
import error.E;
import format.F;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import org.primefaces.context.RequestContext;
import pdffk.PdfMain;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaA4Landscape;
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
            RequestContext.getCurrentInstance().execute(f);
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
            RequestContext.getCurrentInstance().execute(f);
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
        Document document = PdfMain.inicjacjaA4Portrait();
        try {
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Zestawienie zaksięgowanych dokumentów firma -  ", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            PdfMain.dodajLinieOpisu(document, "Waluta pliku jpk "+lista.get(0).getSymbolWaluty());
            PdfMain.dodajLinieOpisuBezOdstepu(document, "SPRZEDAŻ OPODATKOWANA NIEMCY");
            List<Dok> sprzedaz = lista.stream().filter((p)->p.getRodzajedok().getSkrotNazwyDok().equals("SZ")).collect(Collectors.toList());
            dodajTabele(document, testobjects.testobjects.getListaDokImport(sprzedaz),100,modyfikator);
            dodajsumy(sprzedaz, document);
            PdfMain.dodajLinieOpisu(document, "");
            PdfMain.dodajLinieOpisuBezOdstepu(document, "SPRZEDAŻ WDT NIEMCY");
            List<Dok> wdt = lista.stream().filter((p)->p.getRodzajedok().getSkrotNazwyDok().equals("WDT")).collect(Collectors.toList());
            dodajTabele(document, testobjects.testobjects.getListaDokImport(wdt),100,modyfikator);
            dodajsumy(wdt, document);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } catch (Exception e) {
            E.e(e);
        } finally {
            finalizacjaDokumentuQR(document,nazwa);
        }
    }
    
    public static void dodajsumy(List<Dok> lista, Document document) {
        double netto = 0.0;
        double vat = 0.0;
        double nettowaluta = 0.0;
        double vatwaluta = 0.0;
        double nettopl = 0.0;
        double vatpl = 0.0;
        Tabelanbp tab = null;
        for (Dok p : lista) {
            netto = Z.z(netto+p.getNetto());
            vat = Z.z(vat+p.getVat());
            nettowaluta = Z.z(nettowaluta+p.getNettoWaluta());
            vatwaluta = Z.z(vatwaluta+p.getVatWalutaCSV());
            nettopl = Z.z(nettopl+Z.z(p.getNetto()*p.getTabelanbp().getKurssredniPrzelicznik()));
            vatpl = Z.z(vatpl+Z.z(p.getVat()*p.getTabelanbp().getKurssredniPrzelicznik()));
            tab=p.getTabelanbp();
        }
        double brutto = Z.z(netto+vat);
        double bruttopln = Z.z(nettopl+vatpl);
        double bruttowal = Z.z(nettowaluta+vatwaluta);
        String opis = "Razem wartość wybranych dokumentów w PLN";
        PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
        opis = "netto: "+F.curr(nettopl)+" vat: "+F.curr(vatpl)+" brutto: "+F.curr(bruttopln);
        PdfMain.dodajLinieOpisu(document, opis);
        String wal = tab.getWaluta().getSymbolwaluty();
        opis = "Razem wartość wybranych dokumentów - waluta dokumentów "+wal;
        PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
        opis = "netto wal: "+F.curr(netto, wal)+" vat wal: "+F.curr(vat, wal)+" brutto: "+F.curr(brutto, wal);
        PdfMain.dodajLinieOpisu(document, opis);
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
            dodajOpisWstepny(document, "Zestawienie zaksięgowanych dokumentów firma -  ", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
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
            RequestContext.getCurrentInstance().execute(f);
        } catch (Exception e) {
            E.e(e);
        } finally {
            finalizacjaDokumentuQR(document,nazwa);
        }
    }
    
}
