/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;


import daoFK.EVatDeklaracjaPlikDAO;
import data.Data;
import entityfk.EVatDeklaracjaPlik;
import error.E;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import msg.Msg;import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.file.UploadedFile;
import view.WpisView;
/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class EVatDeklaracjaPlikView  implements Serializable {
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private EVatDeklaracjaPlikDAO eVatDeklaracjaPlikDAO;
    private List<EVatDeklaracjaPlik> wiersze;

    public EVatDeklaracjaPlikView() {
    }
    
    @PostConstruct
    private void init() { //E.m(this);
        wiersze = eVatDeklaracjaPlikDAO.findDeklaracjePodatnikMc(wpisView);
    }
    
    
    public void zachowajZaladowanyPlik(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        String filename = uploadedFile.getFileName();
        String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
        Date d = new Date();
        String dt = String.valueOf(d.getTime());
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/");
        String nazwapliku = realPath+"resources/uploaded/deklaracjevat/"+wpisView.getPodatnikObiekt().getNip()+"_"+dt+"_"+"dekl."+extension;
        String nazwakrotka = wpisView.getPodatnikObiekt().getNip()+"_"+dt+"_"+"dekl."+extension;
        File newfile = new File(nazwapliku);
        File oldfile = new File(nazwapliku);
        if (oldfile.isFile()) {
            oldfile.delete();
        }
        try {
           FileUtils.copyInputStreamToFile(uploadedFile.getInputStream(), newfile);
           String dzis = Data.aktualnaData();
           EVatDeklaracjaPlik e = new EVatDeklaracjaPlik(wpisView, nazwakrotka, dzis);
           eVatDeklaracjaPlikDAO.dodaj(e);
           wiersze.add(e);
           Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
        } catch (Exception e) { 
            E.e(e); 
        }
    }


    
    public void usunwierszeewidencji(EVatDeklaracjaPlik sel) {
        if (wiersze != null && wiersze.size() > 0) {
            try {
                eVatDeklaracjaPlikDAO.destroy(sel);
                wiersze.clear();
                Msg.msg("Usunięto plik deklaracji");
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd podczas usuwania pliku deklaracji");
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
        wpisView.naniesDaneDoWpis();
    }
    
    public void zachowajzmiany(RowEditEvent ex) {
        try {
            eVatDeklaracjaPlikDAO.edit((EVatDeklaracjaPlik) ex.getObject());
            Msg.msg("Naniesiono numer UPO");
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e","Wystąpił błąd, nienaniesiono numeru UPO");
        }
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<EVatDeklaracjaPlik> getWiersze() {
        return wiersze;
    }

    public void setWiersze(List<EVatDeklaracjaPlik> wiersze) {
        this.wiersze = wiersze;
    }
    
    
    
}
