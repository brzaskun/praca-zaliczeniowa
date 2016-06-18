/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.EvewidencjaDAO;
import daoFK.EVatwpisDedraDAO;
import dedra.Dedraparser;
import entity.Evewidencja;
import entityfk.EVatwpisDedra;
import error.E;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class EVatDedraView  implements Serializable {
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private EVatwpisDedraDAO eVatwpisDedraDAO;
    private List<EVatwpisDedra> wiersze;

    public EVatDedraView() {
    }
    
    @PostConstruct
    private void init() {
        wiersze = evewidencjaDAO.findWierszePodatnikMc(wpisView);
    }
    
    
    public void zachowajZaladowanyPlik(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        String filename = uploadedFile.getFileName();
        Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
        String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
        String nazwapliku = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/resources/uploaded/"+wpisView.getPodatnikObiekt().getNip()+"_"+"vat."+extension;
        File newfile = new File(nazwapliku);
        File oldfile = new File(nazwapliku);
        if (oldfile.isFile()) {
            oldfile.delete();
        }
        try {
           FileUtils.copyInputStreamToFile(uploadedFile.getInputstream(), newfile);
           Evewidencja e = evewidencjaDAO.znajdzponazwie("sprzedaż 23%");
           wiersze = Dedraparser.parsujewidencje(nazwapliku, wpisView.getPodatnikObiekt(), e, wpisView);
           double netto = 0.0;
           double vat = 0.0;
           for (EVatwpisDedra p : wiersze) {
               netto += p.getNetto();
               vat += p.getVat();
           }
           EVatwpisDedra suma = new EVatwpisDedra(netto, vat);
           wiersze.add(suma);
        } catch (Exception e) { 
            E.e(e); 
        }
    }

    public void dodajwierszeewidencji() {
        if (wiersze != null && wiersze.size() > 0) {
            try {
                wiersze.remove(wiersze.size()-1);
                eVatwpisDedraDAO.dodaj(wiersze);
                Msg.msg("Zachowano wiersze ewidencji");
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd podczas zachowywania wierszy ewidencji");
            }
        } else {
            Msg.msg("w", "Lista wierszy jest pusta");
        }
    }
    
    public void usunwierszeewidencji() {
        if (wiersze != null && wiersze.size() > 0) {
            try {
                eVatwpisDedraDAO.destroy(wiersze);
                wiersze.clear();
                Msg.msg("Usunięto wiersze ewidencji");
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd podczas usuwania wierszy ewidencji");
            }
        } else {
            Msg.msg("w", "Lista wierszy jest pusta");
        }
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<EVatwpisDedra> getWiersze() {
        return wiersze;
    }

    public void setWiersze(List<EVatwpisDedra> wiersze) {
        this.wiersze = wiersze;
    }
    
    
    
}
