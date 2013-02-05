/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PitDAO;
import entity.Pitpoz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScope
public class PitView implements Serializable {
    private List<Pitpoz> lista;
    @Inject private PitDAO pitDAO;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;

    public PitView() {
        lista = new ArrayList<>();
    }
    
    
    
    @PostConstruct
    private void init(){
        lista = (List<Pitpoz>) pitDAO.findPitPod(wpisView.getRokWpisu().toString(), wpisView.getPodatnikWpisu());
    }

    public List<Pitpoz> getLista() {
        return lista;
    }

    public void setLista(List<Pitpoz> lista) {
        this.lista = lista;
    }

    public PitDAO getPitDAO() {
        return pitDAO;
    }

    public void setPitDAO(PitDAO pitDAO) {
        this.pitDAO = pitDAO;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
}

