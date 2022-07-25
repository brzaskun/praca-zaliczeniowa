/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturaStopkaNiemieckaDAO;
import entity.FakturaStopkaNiemiecka;
import error.E;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

@Named
@ViewScoped
public class FakturaStopkaNiemieckaView implements Serializable {

    private static final long serialVersionUID = 1L;
    private FakturaStopkaNiemiecka fakturaStopkaNiemiecka;
    @Inject
    private FakturaStopkaNiemieckaDAO fakturaStopkaNiemieckaDAO;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() { //E.m(this);
        try {
            fakturaStopkaNiemiecka = fakturaStopkaNiemieckaDAO.findByPodatnik(wpisView.getPodatnikObiekt());
        } catch (Exception e) {
        }
        if (fakturaStopkaNiemiecka == null) {
            fakturaStopkaNiemiecka = new FakturaStopkaNiemiecka();
            fakturaStopkaNiemiecka.setPodatnik(wpisView.getPodatnikObiekt());
        }
    }
    
    public void zachowajzmiany() {
        try {
            fakturaStopkaNiemieckaDAO.edit(fakturaStopkaNiemiecka);
            Msg.dP();
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }

    public FakturaStopkaNiemiecka getFakturaStopkaNiemiecka() {
        return fakturaStopkaNiemiecka;
    }

    public void setFakturaStopkaNiemiecka(FakturaStopkaNiemiecka fakturaStopkaNiemiecka) {
        this.fakturaStopkaNiemiecka = fakturaStopkaNiemiecka;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    

}
