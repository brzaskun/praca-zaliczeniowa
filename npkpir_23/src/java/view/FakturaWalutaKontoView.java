/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturaWalutaKontoDAO;
import entity.FakturaWalutaKonto;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
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
    private List<FakturaWalutaKonto> listakont;
    private List<FakturaWalutaKonto> listakontaktywne;
    @Inject
    private FakturaWalutaKonto selected;
    
    @PostConstruct
    private void init() {
        listakont = fakturaWalutaKontoDAO.findPodatnik(wpisView);
        listakontaktywne = fakturaWalutaKontoDAO.findPodatnik(wpisView);
    }
    
    public void dodaj() {
        try {
            selected.setPodatnik(wpisView.getPodatnikObiekt());
            fakturaWalutaKontoDAO.dodaj(selected);
            listakont.add(selected);
            listakontaktywne.add(selected);
            selected = new FakturaWalutaKonto();
            Msg.dP();
        } catch (Exception e) {
            Msg.dPe();
        }
    }
    
    public void usun(FakturaWalutaKonto u) {
        try {
            fakturaWalutaKontoDAO.destroy(u);
            listakont.remove(u);
            listakontaktywne.remove(u);
            Msg.dP();
        } catch (Exception e) {
            Msg.dPe();
        }
    }

    public void dezaktywacja(FakturaWalutaKonto u) {
        try {
            if (u.isNieaktywny()) {
                u.setNieaktywny(false);
                fakturaWalutaKontoDAO.edit(u);
            } else {
                u.setNieaktywny(true);
                fakturaWalutaKontoDAO.edit(u);
            }
            Msg.dP();
        } catch (Exception e) {
            Msg.dPe();
        }
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

    public FakturaWalutaKonto getSelected() {
        return selected;
    }

    public void setSelected(FakturaWalutaKonto selected) {
        this.selected = selected;
    }
    
    
    
    
}
