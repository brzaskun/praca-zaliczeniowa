/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturaWalutaKontoDAO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class FakturaWalutaKontoView implements Serializable{
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private FakturaWalutaKontoDAO fakturaWalutaKontoDAO;
    private List listakont;
    private List listakontaktywne;
    private FakturaWalutaKonto selected;
    
    @PostConstruct
    private void init() {
        listakont = fakturaWalutaKontoDAO.findPodatnik(wpisView);
        listakontaktywne = fakturaWalutaKontoDAO.findPodatnik(wpisView);
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List getListakont() {
        return listakont;
    }

    public void setListakont(List listakont) {
        this.listakont = listakont;
    }

    public List getListakontaktywne() {
        return listakontaktywne;
    }

    public void setListakontaktywne(List listakontaktywne) {
        this.listakontaktywne = listakontaktywne;
    }
    
    
    
    
}
