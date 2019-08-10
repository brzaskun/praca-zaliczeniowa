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
import msg.B;
import msg.Msg;
import static pdffk.PdfMain.*;
import plik.Plik;
import view.WpisView; import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PdfZaksiegowaneView implements Serializable {
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private UzDAO uzDAO;
    
    public void drukujzaksiegowanydokument(List<Dokfk> wiersze,List<Dokfk> selecteddokfk,List<Dokfk> filterfk) {
        if (selecteddokfk != null && selecteddokfk.size() > 0) {
            for (Dokfk p : selecteddokfk) {
                String nazwa = wpisView.getPodatnikObiekt().getNip()+"dokumentzaksiegowane"+p.getNrkolejnywserii();
                File file = Plik.plik(nazwa, true);
                if (file.isFile()) {
                    file.delete();
                }
                Uz uz = wpisView.getUzer();
                PdfDokfk.drukujtrescpojedynczegodok(nazwa, p, uz);
                String f = "pokazwydruk('"+nazwa+"');";
                PrimeFaces.current().executeScript(f);
            }
        } else if (filterfk != null && filterfk.size() > 0) {
            String nazwa = wpisView.getPodatnikObiekt().getNip()+"dokumentzaksiegowane";
            File file = Plik.plik(nazwa, true);
            if (file.isFile()) {
                file.delete();
            }
            wydrukujzestawieniedok(nazwa, filterfk);
        } else if (wiersze != null && wiersze.size() >0) {
            String nazwa = wpisView.getPodatnikObiekt().getNip()+"dokumentzaksiegowane";
            File file = Plik.plik(nazwa, true);
            if (file.isFile()) {
                file.delete();
            }
            wydrukujzestawieniedok(nazwa, wiersze);
        } else {
            Msg.msg("w", "Nie wybrano wierszy do wydruku");
        }
    }
    
    private void wydrukujzestawieniedok(String nazwa, List<Dokfk> wiersze) {
        try {
            Uz uz = wpisView.getUzer();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, B.b("zestawieniezaksięgowanychdokumentów"), wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getTabelaZaksiegowane(wiersze), 100,0);
            finalizacjaDokumentuQR(document, Plik.getKatalog()+nazwa+".pdf");
            String f = "wydrukZaksiegowaneLista('"+wpisView.getPodatnikObiekt().getNip()+"');";
            PrimeFaces.current().executeScript(f);
        } catch (Exception e) {}
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
   
    
    
}
