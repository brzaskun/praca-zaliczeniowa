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
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajTabele(document, testobjects.testobjects.getTabelaCechyZapisow(wiersze),100,0);
            double razem = 0.0;
            for (CechaStronaWiersza p : wiersze) {
                razem += p.getStronaWiersza().getKwotaPLN();
            }
            NumberFormat format = getNumberFormater();
            dodajLinieOpisu(document, "razem: "+format.format(razem));
            finalizacjaDokumentu(document);
            String f = "wydrukCechyzapisu('"+wpisView.getPodatnikObiekt().getNip()+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano wierszy do wydruku");
        }
    }

      
    
}
