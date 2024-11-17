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
import entity.KlientJPK;
import entity.Podatnik;
import entity.PodsumowanieAmazonOSS;
import entityfk.Tabelanbp;
import error.E;
import formatpdf.F;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.primefaces.PrimeFaces;
import pdffk.PdfMain;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaA4Landscape;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;
import static plik.Plik.plikRBA;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class PdfDok extends Pdf implements Serializable {
    
    public static byte[] drukujDok(List<Dok> lista, WpisView wpisView, int modyfikator, String wybranacechadok, String apendix) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"listadok"+apendix;
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        Document document = inicjacjaA4Landscape();
        try {
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Zestawienie zaksięgowanych dokumentów - ", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            if (wybranacechadok != null) {
                PdfMain.dodajLinieOpisuBezOdstepu(document, "wybrano dokumenty z cechą: "+wybranacechadok);
            }
            dodajTabele(document, testobjects.testobjects.getListaDok(lista),100,modyfikator);
            double netto = 0.0;
            double vat = 0.0;
            Set<String> waluty = new HashSet<>();
            for (Dok p : lista) {
                String sw = p.getSymbolWaluty();
                if (!sw.equals("PLN")) {
                    waluty.add(sw);
                }
                netto = netto+p.getNetto();
                vat = vat+p.getVat();
            }
            Map<String,Sumawwalucie> lisatwal = new HashMap<>();
            for (String r : waluty) {
                lisatwal.put(r, new Sumawwalucie(r));
                for (Dok p : lista) {
                    String sw = p.getSymbolWaluty();
                    if (sw.equals(r)) {
                       lisatwal.get(r).netto = Z.z(lisatwal.get(r).netto+p.getNettoWaluta());
                        lisatwal.get(r).vat = Z.z(lisatwal.get(r).vat+p.getVatWalutaCSV());
                        lisatwal.get(r).brutto = Z.z(lisatwal.get(r).netto+p.getNettoWaluta()+lisatwal.get(r).vat+p.getVatWalutaCSV());
                    }
                }
            }
            double brutto = Z.z(netto+vat);
            String opis = "Razem wartość wybranych dokumentów";
            PdfMain.dodajLinieOpisu(document, opis);
            opis = "netto: "+F.curr(netto)+" vat: "+F.curr(vat)+" brutto: "+F.curr(brutto);
            PdfMain.dodajLinieOpisu(document, opis);
            for (String x : lisatwal.keySet()) {
                Sumawwalucie xx = lisatwal.get(x);
                opis = "netto waluta: "+F.curr(xx.netto,x)+" vat waluta: "+F.curr(xx.vat,x)+" brutto: "+F.curr(xx.brutto,x);
                PdfMain.dodajLinieOpisu(document, opis);
            }
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } catch (Exception e) {
            E.e(e);
        } finally {
            finalizacjaDokumentuQR(document,nazwa);
        }
        nazwa = nazwa+".pdf";
        return plikRBA(nazwa);
    }

   
    
    static class Sumawwalucie {
        String wal;
        double netto;
        double vat;
        double brutto;

        private Sumawwalucie(String r) {
            this.wal = r;
        }

//<editor-fold defaultstate="collapsed" desc="comment">
        public String getWal() {
            return wal;
        }
        
        public void setWal(String wal) {
            this.wal = wal;
        }
        
        public double getNetto() {
            return netto;
        }
        
        public void setNetto(double netto) {
            this.netto = netto;
        }
        
        public double getVat() {
            return vat;
        }
        
        public void setVat(double vat) {
            this.vat = vat;
        }
        
        public double getBrutto() {
            return brutto;
        }
        
        public void setBrutto(double brutto) {
            this.brutto = brutto;
        }
//</editor-fold>
        
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
        PdfMain.dodajLinieOpisuBezOdstepuKolor(document, "SPRZEDAŻ OPODATKOWANA "+kraj.toUpperCase()+" WALUTA "+waluta, BaseColor.BLACK);
        dodajTabele(document, testobjects.testobjects.getListaDokImport(lista),100,modyfikator);
        dodajsumy(lista, document, waluta);
    }
    private static void dodajtabeleWDT(String kraj, String waluta, Document document, List<Dok> lista, int modyfikator) {
        PdfMain.dodajLinieOpisuBezOdstepuKolor(document, "SPRZEDAŻ WDT "+kraj.toUpperCase()+" WALUTA "+waluta, BaseColor.GREEN);
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
    
    
     
    
   
    
    public static PodsumowanieAmazonOSS dodajsumyfk(List<KlientJPK> lista, Document document, String waluta, List tabelazbiorcza, Podatnik podatnik, String rok, String mc) {
        double netto = 0.0;
        double vat = 0.0;
        double nettowaluta = 0.0;
        double vatwaluta = 0.0;
        double vatstawka = 0.0;
        String jurys=  "";
        for (KlientJPK p : lista) {
            netto = Z.z(netto+p.getNetto());
            vat = Z.z(vat+p.getVat());
            nettowaluta = Z.z(nettowaluta+p.getNettowaluta());
            vatwaluta = Z.z(vatwaluta+p.getVatwaluta());
            jurys = p.getJurysdykcja();
            vatstawka = p.getStawkavat();
        }
        double brutto = Z.z(netto+vat);
        double bruttowaluta = Z.z(nettowaluta+vatwaluta);
        String opis = "Podsumowanie jurysdykcja "+jurys;
        PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
        opis = "Razem wartość wybranych dokumentów w PLN";
        PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
        opis = "netto: "+F.curr(netto)+" vat: "+F.curr(vat)+" brutto: "+F.curr(brutto);
        PdfMain.dodajLinieOpisu(document, opis);
        opis = "Razem wartość wybranych dokumentów - waluta "+waluta;
        PdfMain.dodajLinieOpisuBezOdstepu(document, opis);
        if (waluta!=null&&!waluta.equals("")) {
            opis = "netto wal: "+F.curr(nettowaluta, waluta)+" vat wal: "+F.curr(vatwaluta, waluta)+" brutto: "+F.curr(bruttowaluta, waluta);
        } else {
            opis = "netto wal: "+F.curr(nettowaluta)+" vat wal: "+F.curr(vatwaluta)+" brutto: "+F.curr(bruttowaluta);
        }
        PdfMain.dodajLinieOpisu(document, opis);
        double kurs = nettowaluta!=0.0 ? Z.z6(netto/nettowaluta):0.0;
        PodsumowanieAmazonOSS podsumowanie = new PodsumowanieAmazonOSS(jurys, waluta, nettowaluta, vatwaluta, netto, vat, vatstawka, kurs, podatnik, rok, mc);
        Object[] a = new Object[]{jurys, waluta, nettowaluta, vatwaluta, netto, vat, vatstawka, kurs};
        tabelazbiorcza.add(Arrays.asList(a));
        return podsumowanie;
    }
    
    public static void main(String[] args) {
        Object[] a = new Object[]{"jeden", "dwa", 3};
        List lista = Arrays.asList(a);
        List zbiorcza = new ArrayList();
        zbiorcza.add(lista);
        Object get = zbiorcza.get(0);
        //((List)get).get(0)
    }
}
