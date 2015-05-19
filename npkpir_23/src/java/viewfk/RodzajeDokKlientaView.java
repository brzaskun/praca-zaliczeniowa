/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.Rodzajedokcomparator;
import dao.RodzajedokDAO;
import entity.Rodzajedok;
import error.E;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
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
public class RodzajeDokKlientaView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Rodzajedok> rodzajedokKlienta;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    
    @PostConstruct
    private void init() {
        try {
            rodzajedokKlienta = rodzajedokDAO.findListaPodatnik(wpisView.getPodatnikObiekt());
            Collections.sort(rodzajedokKlienta, new Rodzajedokcomparator());
        } catch (Exception e) {  E.e(e);
            
        }
    }

    public List<Rodzajedok> getRodzajedokKlienta() {
        return rodzajedokKlienta;
    }

    public void setRodzajedokKlienta(List<Rodzajedok> rodzajedokKlienta) {
        this.rodzajedokKlienta = rodzajedokKlienta;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
}
