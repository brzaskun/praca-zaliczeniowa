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
import static pdffk.PdfMain.dodajDate;
import static pdffk.PdfMain.dodajNaglowek;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajStopke;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.dodajpodpis;
import static pdffk.PdfMain.finalizacjaDokumentu;
import static pdffk.PdfMain.infooFirmie;
import static pdffk.PdfMain.informacjaoZaksiegowaniu;
import static pdffk.PdfMain.inicjacjaDokumentu;
import static pdffk.PdfMain.inicjacjaWritera;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PdfView implements Serializable {
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private UzDAO uzDAO;
    
    public void drukujzaksiegowanydokument(Dokfk selected) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"dokument";
        Uz uz = wpisView.getWprowadzil();
        Document document = inicjacjaDokumentu();
        PdfWriter writer = inicjacjaWritera(document, nazwa, "Wydruk zaksięgowanego dokumentu");
        dodajNaglowek(writer);
        informacjaoZaksiegowaniu(document, String.valueOf(selected.getNrdziennika()));
        dodajDate(document, selected.getDatawplywu());
        dodajOpisWstepny(document, selected);
        infooFirmie(document, selected);
        dodajTabele(document, testobjects.testobjects.getTabelaKonta(selected.getListawierszy()));
        dodajpodpis(document, uz.getImie(), uz.getNazw());
        dodajStopke(writer);
        finalizacjaDokumentu(document);
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
}
