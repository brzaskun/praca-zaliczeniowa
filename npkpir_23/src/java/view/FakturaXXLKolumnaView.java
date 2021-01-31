/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturaXXLKolumnaDAO;
import entity.FakturaXXLKolumna;
import error.E;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class FakturaXXLKolumnaView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private FakturaXXLKolumna selected;
    @Inject
    private FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO;
    
    @PostConstruct
    private void init() { //E.m(this);
        try {
            selected = fakturaXXLKolumnaDAO.findXXLByPodatnik(wpisView.getPodatnikObiekt());
        } catch (Exception e) { E.e(e); 

        }
    }
    
    public void edytujdanefaktxxl() {
        try {
            selected.setPodatnik(wpisView.getPodatnikObiekt());
            fakturaXXLKolumnaDAO.edit(selected);
            Msg.msg("Naniesiono opisy kolumn dla faktury xxl");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd. Nie naniesiono zmian.");
        }
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public FakturaXXLKolumna getSelected() {
        return selected;
    }

    public void setSelected(FakturaXXLKolumna selected) {
        this.selected = selected;
    }
    
    
    
}
