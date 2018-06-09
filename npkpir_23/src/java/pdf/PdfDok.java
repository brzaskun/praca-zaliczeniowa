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
import entityfk.Tabelanbp_;
import error.E;
import format.F;
import java.io.File;
import java.io.Serializable;
import java.util.List;
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
    
    public static void drukujDok(List<Dok> lista, WpisView wpisView, int modyfikator) {
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
    
    public static void drukujDokImport(List<Dok> lista, WpisView wpisView, int modyfikator) {
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
            Tabelanbp tab = null;
            for (Dok p : lista) {
                netto = netto+p.getNetto();
                vat = vat+p.getVat();
                nettowaluta = nettowaluta+p.getNettoWaluta();
                vatwaluta = vatwaluta+p.getVatWaluta();
                tab=p.getTabelanbp();
            }
            double brutto = Z.z(netto+vat);
            double bruttowal = Z.z(nettowaluta+vatwaluta);
            String opis = "Razem wartość wybranych dokumentów";
            PdfMain.dodajLinieOpisu(document, opis);
            opis = "netto: "+F.curr(netto)+" vat: "+F.curr(vat)+" brutto: "+F.curr(brutto);
            PdfMain.dodajLinieOpisu(document, opis);
            if (tab!=null && !tab.getWaluta().getSymbolwaluty().equals("PLN")) {
                opis = "waluta dokumentów "+tab.getWaluta().getSymbolwaluty();
                PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
                opis = "netto wal: "+F.curr(nettowaluta, tab.getWaluta().getSymbolwaluty())+" vat wal: "+F.curr(vatwaluta, tab.getWaluta().getSymbolwaluty())+" brutto: "+F.curr(bruttowal, tab.getWaluta().getSymbolwaluty());
                PdfMain.dodajLinieOpisu(document, opis);
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
    
     public static void drukujDokCSV(List<Dok> lista, WpisView wpisView, int modyfikator) {
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
            Tabelanbp tab = null;
            for (Dok p : lista) {
                netto = netto+p.getNetto();
                vat = vat+p.getVat();
                nettowaluta = nettowaluta+p.getNettoWaluta();
                vatwaluta = vatwaluta+p.getVatWalutaCSV();
            }
            double brutto = Z.z(netto+vat);
            double bruttowal = Z.z(nettowaluta+vatwaluta);
            String opis = "Razem wartość wybranych dokumentów";
            PdfMain.dodajLinieOpisu(document, opis);
            opis = "netto: "+F.curr(netto)+" vat: "+F.curr(vat)+" brutto: "+F.curr(brutto);
            PdfMain.dodajLinieOpisu(document, opis);
            if (tab!=null && !tab.getWaluta().getSymbolwaluty().equals("PLN")) {
                opis = "waluta dokumentów "+tab.getWaluta().getSymbolwaluty();
                PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
                opis = "netto wal: "+F.curr(nettowaluta, tab.getWaluta().getSymbolwaluty())+" vat wal: "+F.curr(vatwaluta, tab.getWaluta().getSymbolwaluty())+" brutto: "+F.curr(bruttowal, tab.getWaluta().getSymbolwaluty());
                PdfMain.dodajLinieOpisu(document, opis);
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
