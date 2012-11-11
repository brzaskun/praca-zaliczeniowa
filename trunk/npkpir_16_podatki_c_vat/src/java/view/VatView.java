/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.Dok;
import entity.Pitpoz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class VatView implements Serializable{
    @ManagedProperty(value="#{DokTabView}")
    private DokTabView dokTabView;
    private List<Dok> listadokvat;
    @Inject
    private Dok selected;

    public VatView() {
        listadokvat = new ArrayList<>();
    }

    
    @PostConstruct
    private void init(){
        listadokvat.addAll(dokTabView.getDokvatmc());
    }
    
    
    public DokTabView getDokTabView() {
        return dokTabView;
    }

    public void setDokTabView(DokTabView dokTabView) {
        this.dokTabView = dokTabView;
    }

    public List<Dok> getListadokvat() {
        return listadokvat;
    }

    public void setListadokvat(List<Dok> listadokvat) {
        this.listadokvat = listadokvat;
    }

    public Dok getSelected() {
        return selected;
    }

    public void setSelected(Dok selected) {
        this.selected = selected;
    }
    
    
    
    
}
