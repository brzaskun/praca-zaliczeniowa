/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import dao.UzDAO;
import entity.Uz;
import entityfk.Dokfk;
import java.io.File;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
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
    
    
    public static void drukujzaksiegowanydokument(WpisView wpisView, List<CechyzapisuPrzegladView.CechaStronaWiersza> wiersze) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"dokumentcechyzapisu";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (wiersze != null && wiersze.size() > 0) {
            List<CechyzapisuPrzegladView.CechaStronaWiersza> wk = wierszeseparacja(wiersze,false);
            List<CechyzapisuPrzegladView.CechaStronaWiersza> wp = wierszeseparacja(wiersze, true);
            Uz uz = wpisView.getWprowadzil();
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
            finalizacjaDokumentu(document);
            String f = "wydrukCechyzapisu('"+wpisView.getPodatnikObiekt().getNip()+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano wierszy do wydruku");
        }
    }

    private static List<CechaStronaWiersza> wierszeseparacja(List<CechaStronaWiersza> wiersze, boolean b) {
        List<CechyzapisuPrzegladView.CechaStronaWiersza> zwrot = new ArrayList<>();
        for (Iterator<CechyzapisuPrzegladView.CechaStronaWiersza> it = wiersze.iterator(); it.hasNext();){
            CechyzapisuPrzegladView.CechaStronaWiersza cecha = it.next();
            if (b == true) {
                if (cecha.getCechazapisu().getCechazapisuPK().getNazwacechy().toLowerCase().equals("pmn") || cecha.getCechazapisu().getCechazapisuPK().getNazwacechy().toLowerCase().equals("npup")) {
                   zwrot.add(cecha);
                }
            } else {
                if (cecha.getCechazapisu().getCechazapisuPK().getNazwacechy().toLowerCase().equals("nkup") || cecha.getCechazapisu().getCechazapisuPK().getNazwacechy().toLowerCase().equals("kupmn")) {
                    zwrot.add(cecha);
                }
            }
        }
        return zwrot;
    }

      
    
}
