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
        PdfDokfk.drukujtrescpojedynczegodok(nazwa, selected, uz);
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
}
