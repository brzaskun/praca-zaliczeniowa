/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.MiejscePrzychodowDAO;
import entityfk.MiejscePrzychodow;
import entityfk.StowNaliczenie;
import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class StowNaliczenieView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<StowNaliczenie> lista;
    @Inject
    private MiejscePrzychodowDAO miejscePrzychodowDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public StowNaliczenieView() {
        this.lista = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        List<MiejscePrzychodow> czlonkowiestowarzyszenia = miejscePrzychodowDAO.findCzlonkowieStowarzyszenia(wpisView.getPodatnikObiekt());
        for (MiejscePrzychodow p : czlonkowiestowarzyszenia) {
            lista.add(new StowNaliczenie(p));
        }
    }
    
    public List<StowNaliczenie> getLista() {
        return lista;
    }

    public void setLista(List<StowNaliczenie> lista) {
        this.lista = lista;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
    
}
