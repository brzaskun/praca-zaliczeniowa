/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.WalutyDAOfk;
import entityfk.Waluty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class WalutyViewFK implements Serializable {
    
    @Inject private WalutyDAOfk walutyDAOfk;
    private List<Waluty> pobranewaluty;
    @Inject private Waluty nowawaluta;

    public WalutyViewFK() {
        pobranewaluty = new ArrayList<>();
    }
    
    @PostConstruct
    private void init() {
        pobranewaluty = walutyDAOfk.findAll();
    }
    
    public void dodajnowawalute() {
        try {
            nowawaluta.setSymbolwaluty(nowawaluta.getSymbolwaluty().toUpperCase(new Locale("pl")));
            nowawaluta.setNazwawaluty(nowawaluta.getNazwawaluty().toLowerCase(new Locale("pl")));
            walutyDAOfk.dodaj(nowawaluta);
            pobranewaluty.add(nowawaluta);
            Msg.msg("i", "Dodano nową walute");
        } catch (Exception e) {
            Msg.msg("e", "Nie dodano nowej waluty");
        }
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public List<Waluty> getPobranewaluty() {
        return pobranewaluty;
    }
    
    public void setPobranewaluty(List<Waluty> pobranewaluty) {
        this.pobranewaluty = pobranewaluty;
    }
    
    public Waluty getNowawaluta() {
        return nowawaluta;
    }
    
    public void setNowawaluta(Waluty nowawaluta) {
        this.nowawaluta = nowawaluta;
    }
    
    
    //</editor-fold>
    
}
