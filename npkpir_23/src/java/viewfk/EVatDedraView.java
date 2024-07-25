/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.EVatwpisDedraDAO;
import dao.EvewidencjaDAO;
import dedra.Dedraparser;
import entity.Evewidencja;
import entityfk.EVatwpisDedra;
import error.E;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.servlet.ServletContext;
import msg.Msg;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import pdffk.PdfEVatDedra;
import view.WpisView;
import interceptor.ConstructorInterceptor;
/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class EVatDedraView  implements Serializable {
    @Inject
    private WpisView wpisView;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private EVatwpisDedraDAO eVatwpisDedraDAO;
    private List<EVatwpisDedra> wiersze;
    private List<EVatwpisDedra> zakupy;
    private Evewidencja zakup;
    @Inject
    private EVatwpisDedra selected;

    public EVatDedraView() {
    }
    
    @PostConstruct
    private void init() { //E.m(this);
        wiersze = eVatwpisDedraDAO.findWierszePodatnikMcZakupSprzedaz(wpisView, true);
        zakup = evewidencjaDAO.znajdzponazwie("zakup");
        if (!wiersze.isEmpty()) {
            wiersze.add(sumuj());
        }
        zakupy = eVatwpisDedraDAO.findWierszePodatnikMcZakupSprzedaz(wpisView, false);
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
           FileUtils.copyInputStreamToFile(uploadedFile.getInputStream(), newfile);
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
                eVatwpisDedraDAO.createList(wiersze);
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
                eVatwpisDedraDAO.removeList(wiersze);
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
     
    public void dodajwierszzakupu(){
        try {
            EVatwpisDedra p = Dedraparser.dodajwierszzakupu(selected, wpisView, wpisView.getPodatnikObiekt(), zakup, eVatwpisDedraDAO);
            if (zakupy==null) {
                zakupy = new ArrayList<>();
            }
            zakupy.add(p);
            Msg.msg("Dodano wieresz zakupu");
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd. Nie dodano wiersza");
        }
    }
    
    public void usun(EVatwpisDedra item) {
         try {
            eVatwpisDedraDAO.remove(item);
            zakupy.remove(item);
            Msg.msg("Usunięto wieresz zakupu");
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd. Nie usunięto wiersza");
        }
    }
    
    private void aktualizuj(){
        wpisView.naniesDaneDoWpis();
    }
    
    public void drukujewidencje() {
        if (wiersze != null && wiersze.size() > 0) {
            PdfEVatDedra.drukuj(wiersze, wpisView);
        }
    }
    
    public void drukujewidencjezakupy() {
        if (zakupy != null && zakupy.size() > 0) {
            PdfEVatDedra.drukuj(zakupy, wpisView);
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

    public List<EVatwpisDedra> getZakupy() {
        return zakupy;
    }

    public void setZakupy(List<EVatwpisDedra> zakupy) {
        this.zakupy = zakupy;
    }

    public EVatwpisDedra getSelected() {
        return selected;
    }

    public void setSelected(EVatwpisDedra selected) {
        this.selected = selected;
    }

    
    
    
}
