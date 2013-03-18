/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PlatnosciDAO;
import entity.Platnosci;
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
public class PlatnosciTablicaView implements Serializable{
    List<Platnosci> lista;
    @Inject PlatnosciDAO platnosciDAO;
    @Inject private WpisView wpisView;

    public PlatnosciTablicaView() {
        lista = new ArrayList<>();
    }
    
    @PostConstruct
    private void init(){
        String rok = wpisView.getRokWpisu().toString();
        String podatnik = wpisView.getPodatnikWpisu();
        try{
            lista = platnosciDAO.findPodRok(rok, podatnik);
        } catch (Exception e){}
    }

    public List<Platnosci> getLista() {
        return lista;
    }

    public void setLista(List<Platnosci> lista) {
        this.lista = lista;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
}
