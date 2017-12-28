/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.EvewidencjaDAO;
import dao.WpisDAO;
import daoFK.EVatwpisDedraDAO;
import dedra.Dedraparser;
import entity.Evewidencja;
import entity.Wpis;
import entityfk.EVatwpisDedra;
import error.E;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import msg.Msg;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import pdffk.PdfEVatDedra;
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
    @Inject 
    private WpisDAO wpisDAO;
    private List<EVatwpisDedra> wiersze;

    public EVatDedraView() {
    }
    
    @PostConstruct
    private void init() {
        wiersze = eVatwpisDedraDAO.findWierszePodatnikMc(wpisView);
        if (!wiersze.isEmpty()) {
            wiersze.add(sumuj());
        }
    }
    
    
    public void zachowajZaladowanyPlik(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        String filename = uploadedFile.getFileName();
        String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/");
        String nazwapliku = realPath+"resources/uploaded/"+wpisView.getPodatnikObiekt().getNip()+"_"+"vat."+extension;
        File newfile = new File(nazwapliku);
        File oldfile = new File(nazwapliku);
        if (oldfile.isFile()) {
            oldfile.delete();
        }
        try {
           FileUtils.copyInputStreamToFile(uploadedFile.getInputstream(), newfile);
           Evewidencja e = evewidencjaDAO.znajdzponazwie("sprzedaż 23%");
           Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
           wiersze = Dedraparser.parsujewidencje(nazwapliku, wpisView.getPodatnikObiekt(), e, wpisView);
           wiersze.add(sumuj());
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
    private EVatwpisDedra sumuj() {
        double netto = 0.0;
        double vat = 0.0;
        for (EVatwpisDedra p : wiersze) {
            netto += p.getNetto();
            vat += p.getVat();
        }
        return new EVatwpisDedra(netto, vat);
    }

    public void dodajwierszeewidencji() {
        if (wiersze != null && wiersze.size() > 0) {
            try {
                wiersze.remove(wiersze.size()-1);
                eVatwpisDedraDAO.dodaj(wiersze);
                wiersze.add(sumuj());
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
    
    public void aktualizujTabeleTabela(AjaxBehaviorEvent e) throws IOException {
        wiersze.clear();
        aktualizuj();
        init();
        Msg.msg("i","Udana zamiana klienta. Aktualny klient to: " +wpisView.getPodatnikWpisu()+" okres rozliczeniowy: "+wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu(),"form:messages");
    }
     
    private void aktualizuj(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setPodatnikWpisu(wpisView.getPodatnikWpisu());
        wpisDAO.edit(wpistmp);
        wpisView.naniesDaneDoWpis();
    }
    
    public void drukujewidencje() {
        if (wiersze != null && wiersze.size() > 0) {
            PdfEVatDedra.drukuj(wiersze, wpisView);
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
