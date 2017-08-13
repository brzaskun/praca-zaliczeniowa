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
import entityfk.StronaWiersza;
import entityfk.Wiersz;
import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
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
public class PdfWNTWDTView implements Serializable {
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private UzDAO uzDAO;
    
    public void drukujzaksiegowanydokument(List<Wiersz> wierszewybrane, List<Wiersz> wierszewszystkie) {
        List<Wiersz> wiersze = null;
        if (wierszewybrane.size() > 0) {
            wiersze = wierszewybrane;
        } else {
            wiersze = wierszewszystkie;
        }
        dodajsume(wiersze);
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"dokumentwntwdt";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (wiersze != null && wiersze.size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Landscape();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaL(writer);
            otwarcieDokumentu(document, nazwa);
            dodajTabele(document, testobjects.testobjects.getTabelaWDTWNT(wiersze),98,0);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "wydrukWNTWDT('"+wpisView.getPodatnikObiekt().getNip()+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano wierszy do wydruku");
        }
    }
    
    private void dodajsume(List<Wiersz> wiersze) {
        double kg = 0.0;
        double szt = 0.0;
        double wartoscWnPLN = 0.0;
        double wartoscMaPLN = 0.0;
        for(Iterator<Wiersz> it = wiersze.iterator(); it.hasNext();) {
            Wiersz p = (Wiersz) it.next();
            if (p.getIlosc_kg() == 0.0 && p.getIlosc_szt() == 0.0) {
                it.remove();
            }
            if (p.getOpisWiersza().equals("podsumowanie")) {
                it.remove();
            } else {
                kg += p.getIlosc_kg();
                szt += p.getIlosc_szt();
                wartoscWnPLN += p.getKwotaWnPLN();
                wartoscMaPLN += p.getKwotaMaPLN();
            }
        }
        Wiersz w = new Wiersz();
        w.setIdwiersza(wiersze.get(wiersze.size()-1).getIdwiersza()+1);
        w.setIdporzadkowy(wiersze.size());
        w.setDokfk(new Dokfk());
        w.setDataksiegowania("");
        w.getDokfk().setNumerwlasnydokfk("");
        w.setStronaWn(new StronaWiersza(w, "Wn"));
        w.setStronaMa(new StronaWiersza(w, "Ma"));
        w.setOpisWiersza("podsumowanie");
        w.setIlosc_kg(kg);
        w.setIlosc_szt(szt);
        w.getStronaWn().setKwota(0.0);
        w.getStronaMa().setKwota(0.0);
        w.getStronaWn().setKwotaPLN(wartoscWnPLN);
        w.getStronaMa().setKwotaPLN(wartoscMaPLN);
        wiersze.add(w);
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
}
