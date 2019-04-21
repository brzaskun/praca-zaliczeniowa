/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import static beansPdf.PdfFont.formatujWaluta;
import static beansPdf.PdfFont.ustawfrazeAlign;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Dok;
import entity.Uz;
import entityfk.Cechazapisu;
import error.E;
import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import msg.Msg;
import org.primefaces.context.RequestContext;
import static pdffk.PdfMain.*;
import plik.Plik;
import view.WpisView;
import viewfk.CechyzapisuPrzegladView;
import viewfk.CechyzapisuPrzegladView.CechaStronaWiersza;

/**
 *
 * @author Osito
 */

public class PdfCechyZapisow {
    
    
    public static void drukujlistaCech(WpisView wpisView, List<CechyzapisuPrzegladView.CechaStronaWiersza> wiersze) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"dokumentcechyzapisu";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (wiersze != null && wiersze.size() > 0) {
            try {
                List<CechyzapisuPrzegladView.CechaStronaWiersza> wk = wierszeseparacja(wiersze, 0);
                List<CechyzapisuPrzegladView.CechaStronaWiersza> wp = wierszeseparacja(wiersze, 1);
                List<CechyzapisuPrzegladView.CechaStronaWiersza> ws = wierszeseparacja(wiersze, 2);
                Uz uz = wpisView.getUzer();
                Document document = inicjacjaA4Portrait();
                PdfWriter writer = inicjacjaWritera(document, nazwa);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                if (!wk.isEmpty()) {
                    dodajLinieOpisu(document, "Korekta kosztów");
                    dodajTabele(document, testobjects.testobjects.getTabelaCechyZapisow(wk),100,0);
                    double razem = 0.0;
                    for (CechaStronaWiersza p : wk) {
                        razem += p.getStronaWiersza().getKwotaPLN();
                    }
                    NumberFormat format = getNumberFormater();
                    dodajLinieOpisu(document, "razem: "+format.format(razem));
                }
                if (!wp.isEmpty()) {
                    dodajLinieOpisu(document, "");
                    dodajLinieOpisu(document, "Korekta przychodów");
                    dodajTabele(document, testobjects.testobjects.getTabelaCechyZapisow(wp),100,0);
                    double razem = 0.0;
                    for (CechaStronaWiersza p : wp) {
                        razem += p.getStronaWiersza().getKwotaPLN();
                    }
                    NumberFormat format = getNumberFormater();
                    dodajLinieOpisu(document, "razem: "+format.format(razem));
                }
                if (!ws.isEmpty()) {
                    dodajLinieOpisu(document, "");
                    dodajLinieOpisu(document, "Cechy statystyczne");
                    dodajTabele(document, testobjects.testobjects.getTabelaCechyZapisow(ws),100,0);
                    double razem = 0.0;
                    for (CechaStronaWiersza p : ws) {
                        razem += p.getStronaWiersza().getKwotaPLN();
                    }
                    NumberFormat format = getNumberFormater();
                    dodajLinieOpisu(document, "razem: "+format.format(razem));
                }
                finalizacjaDokumentuQR(document,nazwa);
                String f = "wydrukCechyzapisu('"+wpisView.getPodatnikObiekt().getNip()+"');";
                RequestContext.getCurrentInstance().execute(f);
                Msg.msg("Wydrukowano zestawienie");
            } catch (Exception e) {
                String el = E.e(e);
                System.out.println(el);
                Msg.msg("e","Wystąpił błąd podczas generowanai wydruku "+el);
            }
        } else {
            Msg.msg("w", "Nie wybrano wierszy do wydruku");
        }
    }

    private static List<CechaStronaWiersza> wierszeseparacja(List<CechaStronaWiersza> wiersze, int b) {
        List<CechyzapisuPrzegladView.CechaStronaWiersza> zwrot = Collections.synchronizedList(new ArrayList<>());
        for (Iterator<CechyzapisuPrzegladView.CechaStronaWiersza> it = wiersze.iterator(); it.hasNext();){
            CechyzapisuPrzegladView.CechaStronaWiersza cecha = it.next();
            if (b == 0) {
                if (cecha.getCechazapisu().getNazwacechy().toLowerCase().equals("pmn") || cecha.getCechazapisu().getNazwacechy().toLowerCase().equals("npup")) {
                   zwrot.add(cecha);
                }
            } else if (b == 1){
                if (cecha.getCechazapisu().getNazwacechy().toLowerCase().equals("nkup") || cecha.getCechazapisu().getNazwacechy().toLowerCase().equals("kupmn")) {
                    zwrot.add(cecha);
                }
            } else {
                if (!cecha.getCechazapisu().getNazwacechy().toLowerCase().equals("nkup") && !cecha.getCechazapisu().getNazwacechy().toLowerCase().equals("kupmn")
                        && !cecha.getCechazapisu().getNazwacechy().toLowerCase().equals("pmn") && !cecha.getCechazapisu().getNazwacechy().toLowerCase().equals("npup")) {
                    zwrot.add(cecha);
                }
            }
        }
        return zwrot;
    }

    
      public static void drukujcechy(WpisView wpisView, List<Cechazapisu> wiersze) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"dokumentcechyzapisuzest";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (wiersze != null && wiersze.size() > 0) {
            Uz uz = wpisView.getUzer();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajLinieOpisu(document, "Zestawienie przychodów i kosztów wg cech "+wpisView.getPrintNazwa()+" za okres "+wpisView.getRokWpisuSt()+"/"+wpisView.getMiesiacWpisu());
            dodajTabele(document, testobjects.testobjects.getTabelaCechyZapisowZest(wiersze),100,0);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano wierszy do wydruku");
        }
    }
      
      public static void drukujcechyszczegoly(WpisView wpisView, List<Cechazapisu> wiersze) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"dokumentcechyzapisuzest";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (wiersze != null && wiersze.size() > 0) {
            Uz uz = wpisView.getUzer();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajLinieOpisu(document, "Zestawienie przychodów i kosztów wg cech "+wpisView.getPrintNazwa()+" za okres "+wpisView.getRokWpisuSt()+"/"+wpisView.getMiesiacWpisu());
            for (Cechazapisu cz : wiersze) {
                List<Cechazapisu> nowa = Collections.synchronizedList(new ArrayList<>());
                nowa.add(cz);
                dodajTabele(document, testobjects.testobjects.getTabelaCechyZapisowZest(nowa),100,1);
                dodajLinieOpisu(document, "");
            }
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano wierszy do wydruku");
        }
    }

    static PdfPTable tabeladokumenty(Cechazapisu p) {
        PdfPTable table = new PdfPTable(9);
        try {
            table.setWidthPercentage(95);
            table.setWidths(new int[]{1, 2, 5, 2, 1, 2, 2, 2, 2});
            PdfPCell cell = new PdfPCell();
            table.addCell(ustawfrazeAlign("nr kolejny", "center",8));
            table.addCell(ustawfrazeAlign("data wystawienia", "center",8));
            table.addCell(ustawfrazeAlign("kontrahent", "center",8));
            table.addCell(ustawfrazeAlign("transakcja", "center",8));
            table.addCell(ustawfrazeAlign("symb. dok.", "center",8));
            table.addCell(ustawfrazeAlign("nr własny", "center",8));
            table.addCell(ustawfrazeAlign("opis", "center",8));
            table.addCell(ustawfrazeAlign("netto", "center",8));
            table.addCell(ustawfrazeAlign("brutto", "center",8));
            table.setHeaderRows(1);
            int i = 1;
            for (Dok rs : p.getDokLista()) {
                table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",8));
                table.addCell(ustawfrazeAlign(rs.getDataWyst(), "left",8));
                table.addCell(ustawfrazeAlign(rs.getKontr().getNpelna(), "left",8));
                table.addCell(ustawfrazeAlign(rs.getRodzajedok() != null ? rs.getRodzajedok().getSkrot():"", "center",8));
                table.addCell(ustawfrazeAlign(rs.getRodzajedok() != null ? rs.getRodzajedok().getRodzajtransakcji():"", "center",8));
                table.addCell(ustawfrazeAlign(rs.getNrWlDk(), "left",8));
                table.addCell(ustawfrazeAlign(rs.getOpis(), "left",8));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getNetto()), "right",8));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getBrutto()), "right",8));
            }
        } catch (Exception ex) {
            Logger.getLogger(PdfCechyZapisow.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }
    
}
