/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.Dokfk;
import entity.Wiersz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import msg.Msg;
import session.WierszFacade;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class WierszView implements Serializable {
    private Wiersz wiersz;
    private ArrayList<Wiersz> wierszlist;
    @Inject private WierszFacade wierszFacade;
    @Inject private Dokfk dokfk;

    public WierszView() {
        wierszlist = new ArrayList<>();
    }
    

    @PostConstruct
    private void init() {
        wiersz = new Wiersz();
        try {
            wierszlist.addAll(wierszFacade.findAll());
        } catch (Exception e) {}
    }
    
    
    public void dodaj() {
        wierszFacade.create(wiersz);
        Msg.msg("i", "Dodano wiersz "+wiersz.getOpiswiersza());
    }
    
      public void edytujopiswiersza(ValueChangeEvent event) {
            Object source = event.getSource();
            Object oldValue = event.getOldValue();
            Msg.msg("i", "Stara wartosc obiektu: "+source+" to "+oldValue);
            Object newValue = event.getNewValue();
            Msg.msg("i", "Nowa wartosc "+source+" to "+newValue);
            wiersz.setOpiswiersza(newValue.toString());
            wierszFacade.edit(wiersz);
        }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public Wiersz getWiersz() {
        return wiersz;
    }
    
    public void setWiersz(Wiersz wiersz) {
        this.wiersz = wiersz;
    }
    
    public ArrayList<Wiersz> getWierszlist() {
        return wierszlist;
    }
    
    public void setWierszlist(ArrayList<Wiersz> wierszlist) {
        this.wierszlist = wierszlist;
    }
    
    public Dokfk getDokfk() {
        return dokfk;
    }

    public void setDokfk(Dokfk dokfk) {
        this.dokfk = dokfk;
    }
    
    //</editor-fold>

   
    
}
