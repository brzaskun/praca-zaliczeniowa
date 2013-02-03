/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.VatDAO;
import entity.Vatpoz;
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
public class Vat7DKView implements Serializable {
    private List<Vatpoz> lista;
    @Inject private VatDAO vatDAO;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @Inject Vatpoz selected;

    public Vat7DKView() {
        lista = new ArrayList<>();
    }
    
    
    
    @PostConstruct
    private void init(){
        lista = (List<Vatpoz>) vatDAO.findVatPod(wpisView.getRokWpisu().toString(), wpisView.getPodatnikWpisu());
    }
    
    public void oblicz(){
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        selected.setPodatnik(podatnik);
        selected.setRok(rok);
        selected.setMiesiac(mc);
        selected.setKodurzedu("kodurzedu");
                
    }

    public List<Vatpoz> getLista() {
        return lista;
    }

    public void setLista(List<Vatpoz> lista) {
        this.lista = lista;
    }

    public VatDAO getVatDAO() {
        return vatDAO;
    }

    public void setVatDAO(VatDAO vatDAO) {
        this.vatDAO = vatDAO;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Vatpoz getSelected() {
        return selected;
    }

    public void setSelected(Vatpoz selected) {
        this.selected = selected;
    }
    
    
}

