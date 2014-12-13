/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import daoFK.TransakcjaDAO;
import entityfk.Transakcja;
import java.io.Serializable;
import java.util.ArrayList;
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
public class RozniceKursoweFKView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Transakcja> pobranetransakcje;
    @Inject
    private TransakcjaDAO transakcjaDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public RozniceKursoweFKView() {
        pobranetransakcje = new ArrayList<>();
    }
    
    @PostConstruct
    private void init() {
        pobranetransakcje = transakcjaDAO.findPodatnikRok(wpisView);
        pobranetransakcje.addAll(transakcjaDAO.findPodatnikBO(wpisView));
        
    }

    public List<Transakcja> getPobranetransakcje() {
        return pobranetransakcje;
    }

    public void setPobranetransakcje(List<Transakcja> pobranetransakcje) {
        this.pobranetransakcje = pobranetransakcje;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
    
}
