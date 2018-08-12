/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Uz;
import entityfk.WierszBO;
import java.io.File;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import msg.Msg;
import org.apache.commons.collections.HashBag;
import org.primefaces.context.RequestContext;
import static pdffk.PdfMain.*;
import plik.Plik;
import view.WpisView;

/**
 *
 * @author Osito
 */

public class PdfWierszBO {
    public static void drukujWierszeBO(List<WierszBO> pobranetransakcje, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"wierszBO";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (pobranetransakcje != null && pobranetransakcje.size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Zestawienie wierszy BO w firmie", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getTabelaWierszBO(pobranetransakcje),97,0);
            String[] lysta = obliczsumy(pobranetransakcje);
            int size = lysta.length;
            for (int i =0; i < size; i++) {
                dodajLinieOpisu(document, lysta[i]);
            }
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano wierszy BO do wydruku");
        }
    }
    
    public static void drukujWierszeObroty(List<WierszBO> pobranetransakcje, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"obrotyRozp";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (pobranetransakcje != null && pobranetransakcje.size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Zestawienie obrotów rozpoczęcia w firmie", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getTabelaWierszObrotyRozp(pobranetransakcje),97,2);
            String[] lysta = obliczsumy(pobranetransakcje);
            int size = lysta.length;
            for (int i =0; i < size; i++) {
                dodajLinieOpisu(document, lysta[i]);
            }
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano wierszy obrotów do wydruku");
        }
    }
    
    public static void drukujListaKonsolidacyjna(List<WierszBO> pobranetransakcje, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"wierszBO";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (pobranetransakcje != null && pobranetransakcje.size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Zestawienie wierszy BO w firmie", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getTabelaWierszBOKonsolidacyjna(pobranetransakcje),97,1);
            String[] lysta = obliczsumy(pobranetransakcje);
            int size = lysta.length;
            for (int i =0; i < size; i++) {
                dodajLinieOpisu(document, lysta[i]);
            }
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano wierszy BO do wydruku");
        }
    }

    private static String[] obliczsumy(List<WierszBO> pobranetransakcje) {
        double wnpln = 0.0;
        double mapln = 0.0;
        Set<String> waluty = new HashSet<>();
        for (WierszBO p : pobranetransakcje) {
            wnpln += p.getKwotaWnPLN();
            mapln += p.getKwotaMaPLN();
            if (p.getWaluta()!=null && !p.getWaluta().getSymbolwaluty().equals("PLN")) {
                waluty.add(p.getWaluta().getSymbolwaluty());
            }
        }
        NumberFormat formatter = getNumberFormater();
        String[] zwrot = new String[1+waluty.size()];
        zwrot[0] = "Suma wnpln: "+formatter.format(wnpln)+", suma mapln: "+formatter.format(mapln);
        int i =1;
        for (String w : waluty) {
            double wn = 0.0;
            double ma = 0.0;
            for (WierszBO p : pobranetransakcje) {
                if (p.getWaluta()!=null && p.getWaluta().getSymbolwaluty().equals(w)) {
                    wn += p.getKwotaWn(); 
                    ma += p.getKwotaMa();
                }
            }
            zwrot[i] = "Suma "+w+": wn "+formatter.format(wn)+", ma: "+formatter.format(ma);
            i++;
        }
        return zwrot;
    }
}
