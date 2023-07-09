/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.KadryfakturapozycjaFacade;
import entity.Fakturaopisuslugi;
import entity.Kadryfakturapozycja;
import java.io.Serializable;
import java.util.ArrayList;
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
    
    @PostConstruct
    public void init() {
        lista = kadryfakturapozycjaFacade.findAll();
        if (lista ==null) {
            lista = new ArrayList<>();
        }
    }

    public void dodaj() {
        if (selected.getFirmakadry()!=null) {
            try {
                kadryfakturapozycjaFacade.create(selected);
                lista.add(selected);
                selected = new Kadryfakturapozycja();
                Msg.msg("Wprowadzono nowy opis");
            } catch (Exception e) {
                Msg.msg("e","Taki opis już istnieje");
            }
        } else {
            Msg.msg("e","Brak wprowadzonej nazwy usługi");
        }
    }
    
     public void usun(Fakturaopisuslugi selected) {
        if (selected!=null) {
            kadryfakturapozycjaFacade.remove(selected);
            lista.remove(selected);
            Msg.msg("Usunięto opis");
            
        }
     }
     
     public void edytuj(Fakturaopisuslugi selected) {
        if (selected!=null&&selected.getOpis().length()>0) {
            kadryfakturapozycjaFacade.edit(selected);
            Msg.msg("Zmieniono opis");
            
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
    

    
}
