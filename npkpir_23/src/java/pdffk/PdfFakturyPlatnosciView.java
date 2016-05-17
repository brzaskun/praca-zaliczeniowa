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
import entityfk.Dokfk;
import java.io.File;
import java.io.Serializable;
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

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PdfFakturyPlatnosciView implements Serializable {
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private UzDAO uzDAO;
    
    public void drukujzaksiegowanydokument(List<Faktura> wiersze, String zaplaconyniezaplacony) {
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
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Zestawienie "+zaplaconyniezaplacony+" faktur za okres", wpisView.getPodatnikObiekt(),wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getTabelaFakturyPlatnosci(wiersze, zaplaconyniezaplacony),100,0);
            finalizacjaDokumentu(document);
            String f = null;
            if (zaplaconyniezaplacony.equals("zapłaconych")) {
                f = "wydrukFakturyPlatnosci('"+wpisView.getPodatnikObiekt().getNip()+"');";
            } else {
                f = "wydrukFakturyPlatnosciN('"+wpisView.getPodatnikObiekt().getNip()+"');";
            }
            RequestContext.getCurrentInstance().execute(f);
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
