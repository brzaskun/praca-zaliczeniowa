/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import embeddable.EVLista;
import embeddable.EVatViewPola;
import embeddable.EVatwpis;
import entity.Dok;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
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
    private List<EVatViewPola> listadokvatprzetworzona;
    @Inject
    private Dok selected;
    @Inject
    private EVLista eVLista;

    public VatView() {
        listadokvat = new ArrayList<>();
        listadokvatprzetworzona = new ArrayList<>();
    }

    
    @PostConstruct
    private void init(){
        listadokvat.addAll(dokTabView.getDokvatmc());
        Iterator it;
        it = listadokvat.iterator();
        while(it.hasNext()){
            Dok tmp = (Dok) it.next();
            if(tmp.getEwidencjaVAT()!=null){
                List<EVatwpis> ewidencja = new ArrayList<>();
                ewidencja.addAll(tmp.getEwidencjaVAT());
                    Iterator itx;
                    itx = ewidencja.iterator();
                    while(itx.hasNext()){
                        EVatwpis ewidwiersz = (EVatwpis) itx.next();
                        if(ewidwiersz.getNetto()>0){
                        EVatViewPola wiersz = new EVatViewPola();
                        wiersz.setId(tmp.getNrWpkpir());
                        wiersz.setDataSprz(tmp.getDataSprz());
                        wiersz.setDataWyst(tmp.getDataWyst());
                        wiersz.setKontr(tmp.getKontr());
                        wiersz.setNrWlDk(tmp.getNrWlDk());
                        wiersz.setOpis(tmp.getOpis());
                        wiersz.setNazwaewidencji(ewidwiersz.getEwidencja().getNazwaEwidencji());
                        wiersz.setNetto(ewidwiersz.getNetto());
                        wiersz.setVat(ewidwiersz.getVat());
                        wiersz.setOpizw(ewidwiersz.getEstawka());
                        listadokvatprzetworzona.add(wiersz);
                        }
            }
        }
    }
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

    public EVLista geteVLista() {
        return eVLista;
    }

    public void seteVLista(EVLista eVLista) {
        this.eVLista = eVLista;
    }

    public List<EVatViewPola> getListadokvatprzetworzona() {
        return listadokvatprzetworzona;
    }

    public void setListadokvatprzetworzona(List<EVatViewPola> listadokvatprzetworzona) {
        this.listadokvatprzetworzona = listadokvatprzetworzona;
    }
    
    
    
    
}
