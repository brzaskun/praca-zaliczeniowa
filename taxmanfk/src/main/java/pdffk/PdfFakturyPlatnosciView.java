/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import dao.UzDAO;
import entity.Faktura;
import entity.Uz;
import format.F;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import static pdffk.PdfMain.*;
import plik.Plik;
import view.WpisView; import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PdfFakturyPlatnosciView implements Serializable {
    @Inject
    private WpisView wpisView;
    @Inject
    private UzDAO uzDAO;
    
    public void drukujzaksiegowanydokument(List<Faktura> wierszeA, List<Faktura> wierszeF, String zaplaconyniezaplacony) {
        List<Faktura> wiersze = Collections.synchronizedList(new ArrayList<>());
        if (wierszeF!=null && wierszeF.size()>0) {
            wiersze.addAll(wierszeF);
        } else {
            wiersze.addAll(wierszeA);
        }
        double suma = 0.0;
        String nazwa = null;
        if (zaplaconyniezaplacony.equals("zapłaconych")) {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"fakturyplatnosci";
        } else {
            nazwa = wpisView.getPodatnikObiekt().getNip()+"fakturyplatnosciN";
        }
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (wiersze != null && wiersze.size() > 0) {
            for (Faktura f : wiersze) {
                suma += f.getBruttoFaktura();
            }
            Uz uz = wpisView.getUzer();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Zestawienie "+zaplaconyniezaplacony+" faktur za okres", wpisView.getPodatnikObiekt(),wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getTabelaFakturyPlatnosci(wiersze, zaplaconyniezaplacony),100,0);
            
            dodajLinieOpisu(document, "Razem: "+F.curr(suma));
            finalizacjaDokumentuQR(document,nazwa);
            String f = null;
            if (zaplaconyniezaplacony.equals("zapłaconych")) {
                f = "wydrukFakturyPlatnosci('"+wpisView.getPodatnikObiekt().getNip()+"');";
            } else {
                f = "wydrukFakturyPlatnosciN('"+wpisView.getPodatnikObiekt().getNip()+"');";
            }
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano wierszy do wydruku");
        }
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
}
