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
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import static pdffk.PdfMain.*;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PdfDokfkView implements Serializable {
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private UzDAO uzDAO;
    
    public void drukujzaksiegowanydokument(Dokfk selected) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"dokument";
        Uz uz = wpisView.getWprowadzil();
        Document document = inicjacjaA4Portrait();
        PdfWriter writer = inicjacjaWritera(document, nazwa);
        naglowekStopkaP(writer);
        otwarcieDokumentu(document, nazwa);
        informacjaoZaksiegowaniu(document, String.valueOf(selected.getNrdziennika()));
        dodajDate(document, selected.getDatawplywu());
        dodajOpisWstepny(document, selected);
        infooFirmie(document, selected);
        dodajTabele(document, testobjects.testobjects.getTabelaKonta(selected.getListawierszy()),100,0);
        dodajpodpis(document, uz.getImie(), uz.getNazw());
        finalizacjaDokumentu(document);
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
}
