/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturaopisuslugiFacade;
import dao.FirmaKadryFacade;
import dao.KadryfakturapozycjaFacade;
import dao.WalutyFacade;
import entity.Fakturaopisuslugi;
import entity.FirmaKadry;
import entity.Kadryfakturapozycja;
import entity.Waluty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class KadryfakturapozycjaView  implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private List<Kadryfakturapozycja> lista;
    @Inject
    private KadryfakturapozycjaFacade kadryfakturapozycjaFacade;
    @Inject
    private Kadryfakturapozycja selected;
    @Inject
    private WalutyFacade walutyFacade;
    private List<FirmaKadry> listafirm;
    private List<Fakturaopisuslugi> listauslug;
    private List<Waluty> listawaluty;
    
    @Inject
    private FirmaKadryFacade firmaKadryFacade;
     @Inject
    private FakturaopisuslugiFacade fakturaopisuslugiFacade;
    
    @PostConstruct
    public void init() {
        lista = kadryfakturapozycjaFacade.findAll();
        if (lista ==null) {
            lista = new ArrayList<>();
        }
        listafirm = firmaKadryFacade.findAll();
        listauslug = fakturaopisuslugiFacade.findAll();
        listawaluty = walutyFacade.findAll();
        selected.setWaluta(listawaluty.stream().filter(p->p.getSymbolwaluty().equals("PLN")).findFirst().get());
    }

    public void dodaj() {
        if (selected.getFirmakadry()!=null) {
            try {
                selected.setUz(wpisView.getUzer());
                selected.setDatadodania(new Date());
                kadryfakturapozycjaFacade.create(selected);
                lista.add(selected);
                selected = new Kadryfakturapozycja();
                Msg.msg("Wprowadzono nową pozycję");
            } catch (Exception e) {
                Msg.msg("e","Taka pozycja już istnieje");
            }
        } else {
            Msg.msg("e","Wystąpił błąd");
        }
    }
    
     public void usun(Kadryfakturapozycja selected) {
        if (selected!=null) {
            kadryfakturapozycjaFacade.remove(selected);
            lista.remove(selected);
            Msg.msg("Usunięto pozycję");
            
        }
     }
     
     public void edytuj(Kadryfakturapozycja selected) {
        if (selected!=null&&selected.getCena()!=0) {
            selected.setDatadodania(new Date());
            kadryfakturapozycjaFacade.edit(selected);
            Msg.msg("Zmieniono pozycję");
            
        }
     }

    public List<Kadryfakturapozycja> getLista() {
        return lista;
    }

    public void setLista(List<Kadryfakturapozycja> lista) {
        this.lista = lista;
    }

    public Kadryfakturapozycja getSelected() {
        return selected;
    }

    public void setSelected(Kadryfakturapozycja selected) {
        this.selected = selected;
    }

    public List<FirmaKadry> getListafirm() {
        return listafirm;
    }

    public void setListafirm(List<FirmaKadry> listafirm) {
        this.listafirm = listafirm;
    }

    public List<Fakturaopisuslugi> getListauslug() {
        return listauslug;
    }

    public void setListauslug(List<Fakturaopisuslugi> listauslug) {
        this.listauslug = listauslug;
    }

    public List<Waluty> getListawaluty() {
        return listawaluty;
    }

    public void setListawaluty(List<Waluty> listawaluty) {
        this.listawaluty = listawaluty;
    }
    

    
}
