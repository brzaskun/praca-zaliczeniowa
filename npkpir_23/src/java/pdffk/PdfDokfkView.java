/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import beansPdf.PdfDokfk;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import dao.UzDAO;
import entity.Uz;
import entityfk.Dokfk;
import f.l;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaA4Portrait;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PdfDokfkView implements Serializable {
    @Inject
    private WpisView wpisView;
    @Inject
    private UzDAO uzDAO;
    
    public void drukujzaksiegowanydokument(Dokfk selected) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"dokument";
        Uz uz = wpisView.getUzer();
        PdfDokfk.drukujtrescpojedynczegodok(nazwa, selected, uz);
    }

    public void drukujzaksiegowanydokumentlista(List<Dokfk> wiersze,List<Dokfk> selecteddokfk,List<Dokfk> filterfk) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"dokument";
        List<Dokfk> lis = l.l(wiersze, filterfk, selecteddokfk);
        if (lis!=null && lis.size()>0) {
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            for (Dokfk dok : lis) {
                PdfDokfk.drukujtrescpojedynczegodoklista(document, dok);
            }
            finalizacjaDokumentuQR(document,nazwa);
        }
        
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
}
