/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.Dokfk;
import entity.Wiersz;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import msg.Msg;
import session.WierszFacade;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class WierszView implements Serializable {
    @Inject private Wiersz wiersz;
    private ArrayList<Wiersz> wierszlist;
    @Inject private WierszFacade wierszFacade;
    @Inject private Dokfk dokfk;

    public WierszView() {
        wierszlist = new ArrayList<>();
    }
    

    @PostConstruct
    private void init() {
        try {
            wierszlist.addAll(wierszFacade.findAll());
        } catch (Exception e) {}
    }
    
    
    public void dodaj() {
        wierszFacade.create(wiersz);
        Msg.msg("i", "Dodano wiersz "+wiersz.getOpiswiersza());
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
