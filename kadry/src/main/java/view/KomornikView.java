/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.NaliczeniepotracenieFacade;
import dao.SkladnikPotraceniaFacade;
import entity.Naliczeniepotracenie;
import entity.Skladnikpotracenia;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class KomornikView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private List<Skladnikpotracenia> skladnikpotracenialist;
    private List<Naliczeniepotracenie> potracenialist;
    @Inject
    private NaliczeniepotracenieFacade naliczeniepotracenieFacade;
    @Inject
    private SkladnikPotraceniaFacade skladnikPotraceniaFacade;
    private Skladnikpotracenia selected;

    
    public void init() {
        if (wpisView.getUmowa()!=null) {
            skladnikpotracenialist = skladnikPotraceniaFacade.findByAngaz(wpisView.getAngaz());
            pobierzdane();
        }
    }

        
       
    public void pobierzdane() {
        if (selected!=null) {
            potracenialist = pobierzpotracenia(selected);
            Msg.msg("Pobrano dane potrąceń");
        }
    }
    
    private List<Naliczeniepotracenie> pobierzpotracenia(Skladnikpotracenia selected) {
        List<Naliczeniepotracenie> pobrane = naliczeniepotracenieFacade.findByAngaz(selected.getAngaz());
        double suma = 0.0;
        for (Naliczeniepotracenie p : pobrane) {
            suma= suma +p.getKwota();
            p.setKwotanarastajaco(suma);
        }
        return pobrane;
    }
    

    public List<Naliczeniepotracenie> getPotracenialist() {
        return potracenialist;
    }

    public void setPotracenialist(List<Naliczeniepotracenie> potracenialist) {
        this.potracenialist = potracenialist;
    }

    public List<Skladnikpotracenia> getSkladnikpotracenialist() {
        return skladnikpotracenialist;
    }

    public void setSkladnikpotracenialist(List<Skladnikpotracenia> skladnikpotracenialist) {
        this.skladnikpotracenialist = skladnikpotracenialist;
    }

    public Skladnikpotracenia getSelected() {
        return selected;
    }

    public void setSelected(Skladnikpotracenia selected) {
        this.selected = selected;
    }


   
    
    

   
    
    
}
