/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import daoFK.WierszBODAO;
import entity.Podatnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class BilansWprowadzanieView implements Serializable{
    @Inject
    private WierszBODAO wierszBODAO;
    List<WierszBO> lista0;
    List<WierszBO> lista1;
    List<WierszBO> lista2;
    List<WierszBO> lista3;
    List<WierszBO> lista6;
    List<WierszBO> lista8;
    
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public BilansWprowadzanieView() {
        this.lista0 = new ArrayList<>();
        this.lista1 = new ArrayList<>();
        this.lista2 = new ArrayList<>();
        this.lista3 = new ArrayList<>();
        this.lista6 = new ArrayList<>();
        this.lista8 = new ArrayList<>();
    }
    
    @PostConstruct
    private void init() {
        Podatnik p = wpisView.getPodatnikObiekt();
        String r = wpisView.getRokWpisuSt();
        this.lista0 = wierszBODAO.lista("0%");
        this.lista0.add(new WierszBO(p,r));
        this.lista1.add(new WierszBO(p,r));
        this.lista2.add(new WierszBO(p,r));
        this.lista3.add(new WierszBO(p,r));
        this.lista6.add(new WierszBO(p,r));
        this.lista8.add(new WierszBO(p,r));
    }
    
    public void dodajwiersz(int kategoria) {
        Podatnik p = wpisView.getPodatnikObiekt();
        String r = wpisView.getRokWpisuSt();
        switch(kategoria) {
            case 0 :
                this.lista0.add(new WierszBO(p,r));
                break;
            case 1 :
                this.lista1.add(new WierszBO(p,r));
                break;
        }
    }
    
    public void usunwiersz(int kategoria) {
        switch(kategoria) {
            case 0 :
                if (this.lista0.size()>1) {
                    this.lista0.remove(this.lista0.size()-1);
                }
                break;
            case 1 :
                if (this.lista1.size()>1) {
                    this.lista1.remove(this.lista1.size()-1);
                }
                break;
        }
    }
    
    public void zapiszBilansBOdoBazy() {
        for (WierszBO p : lista0) {
            try {
                wierszBODAO.dodaj(p);
            } catch (Exception e) {
                wierszBODAO.edit(p);
            }
        }
        Msg.msg("Naniesiono zapisy BO");
    }

    public List<WierszBO> getLista0() {
        return lista0;
    }

    public void setLista0(List<WierszBO> lista0) {
        this.lista0 = lista0;
    }

    public List<WierszBO> getLista1() {
        return lista1;
    }

    public void setLista1(List<WierszBO> lista1) {
        this.lista1 = lista1;
    }

    public List<WierszBO> getLista2() {
        return lista2;
    }

    public void setLista2(List<WierszBO> lista2) {
        this.lista2 = lista2;
    }

    public List<WierszBO> getLista3() {
        return lista3;
    }

    public void setLista3(List<WierszBO> lista3) {
        this.lista3 = lista3;
    }

    public List<WierszBO> getLista6() {
        return lista6;
    }

    public void setLista6(List<WierszBO> lista6) {
        this.lista6 = lista6;
    }

    public List<WierszBO> getLista8() {
        return lista8;
    }

    public void setLista8(List<WierszBO> lista8) {
        this.lista8 = lista8;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
    
}
