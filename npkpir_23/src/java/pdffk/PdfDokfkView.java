/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import beansPdf.PdfDokfk;
import dao.UzDAO;
import entity.Uz;
import entityfk.Dokfk;
import java.io.Serializable;
import javax.inject.Inject;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
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

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
}
