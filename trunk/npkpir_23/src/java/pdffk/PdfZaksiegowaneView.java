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
import view.WpisView;
import viewfk.DokfkView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PdfZaksiegowaneView implements Serializable {
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private List<Dokfk> selecteddokfk;
    @Inject
    private UzDAO uzDAO;
    
    public void drukujzaksiegowanydokument(List<Dokfk> wiersze) {
        if (wiersze != null && wiersze.size() > 0 && selecteddokfk.size() == 0) {
            String nazwa = wpisView.getPodatnikObiekt().getNip()+"dokumentzaksiegowane";
            File file = new File(nazwa);
            if (file.isFile()) {
                file.delete();
            }
            wydrukujzestawieniedok(nazwa, wiersze);
        } else {
            Msg.msg("w", "Nie wybrano wierszy do wydruku");
        }
        if (selecteddokfk != null && selecteddokfk.size() > 0) {
            for (Dokfk p : selecteddokfk) {
                String nazwa = wpisView.getPodatnikObiekt().getNip()+"dokumentzaksiegowane"+p.getDokfkPK().getNrkolejnywserii();
                File file = new File(nazwa);
                if (file.isFile()) {
                    file.delete();
                }
                Uz uz = wpisView.getWprowadzil();
                PdfDokfk.drukujtrescpojedynczegodok(nazwa, p, uz);
                String f = "pokazwydruk('"+nazwa+"');";
                RequestContext.getCurrentInstance().execute(f);
            }
        } else {
            Msg.msg("w", "Nie wybrano wierszy do wydruku");
        }
    }
    
    private void wydrukujzestawieniedok(String nazwa, List<Dokfk> wiersze) {
        Uz uz = wpisView.getWprowadzil();
        Document document = inicjacjaA4Portrait();
        PdfWriter writer = inicjacjaWritera(document, nazwa);
        naglowekStopkaP(writer);
        otwarcieDokumentu(document, nazwa);
        dodajOpisWstepny(document, "Zestawienie zaksięgowanych dokumentów", wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
        dodajTabele(document, testobjects.testobjects.getTabelaZaksiegowane(wiersze), 100,0);
        finalizacjaDokumentu(document);
        String f = "wydrukZaksiegowaneLista('"+wpisView.getPodatnikObiekt().getNip()+"');";
        RequestContext.getCurrentInstance().execute(f);
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Dokfk> getSelecteddokfk() {
        return selecteddokfk;
    }

    public void setSelecteddokfk(List<Dokfk> selecteddokfk) {
        this.selecteddokfk = selecteddokfk;
    }

   
    
    
}
