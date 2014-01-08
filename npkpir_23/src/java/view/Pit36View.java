/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PitDAO;
import dao.RyczDAO;
import entity.Pitpoz;
import entity.Ryczpoz;
import java.io.Serializable;
import java.util.ArrayList;
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
public class Pit36View implements Serializable {

    private List<Pitpoz> lista;
     private List<Ryczpoz> listaryczalt;
    @Inject
    private PitDAO pitDAO;
    @Inject private RyczDAO ryczDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public Pit36View() {
        lista = new ArrayList<>();
        listaryczalt = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        lista = (List<Pitpoz>) pitDAO.findPitPod(wpisView.getRokWpisu().toString(), wpisView.getPodatnikWpisu());
        listaryczalt =  (List<Ryczpoz>) ryczDAO.findRyczPod(wpisView.getRokWpisu().toString(), wpisView.getPodatnikWpisu());
    }

    public List<Pitpoz> getLista() {
        return lista;
    }

    public void setLista(List<Pitpoz> lista) {
        this.lista = lista;
    }

    public List<Ryczpoz> getListaryczalt() {
        return listaryczalt;
    }

    public void setListaryczalt(List<Ryczpoz> listaryczalt) {
        this.listaryczalt = listaryczalt;
    }

    
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
}
