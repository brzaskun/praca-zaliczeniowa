/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturaopisuslugiFacade;
import entity.Fakturaopisuslugi;
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
public class FakturaopisuslugiView  implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private List<Fakturaopisuslugi> lista;
    @Inject
    private FakturaopisuslugiFacade fakturaopisuslugiFacade;
    @Inject
    private Fakturaopisuslugi selected;
    
    @PostConstruct
    public void init() {
        lista = fakturaopisuslugiFacade.findAll();
        if (lista ==null) {
            lista = new ArrayList<>();
        }
    }

    public void dodaj() {
        if (selected.getOpis()!=null) {
            try {
                fakturaopisuslugiFacade.create(selected);
                lista.add(selected);
                selected = new Fakturaopisuslugi();
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
            fakturaopisuslugiFacade.remove(selected);
            lista.remove(selected);
            Msg.msg("Usunięto opis");
            
        }
     }
     
     public void edytuj(Fakturaopisuslugi selected) {
        if (selected!=null&&selected.getOpis().length()>0) {
            fakturaopisuslugiFacade.edit(selected);
            Msg.msg("Zmieniono opis");
            
        }
     }
    

    public List<Fakturaopisuslugi> getLista() {
        return lista;
    }

    public void setLista(List<Fakturaopisuslugi> lista) {
        this.lista = lista;
    }

    public Fakturaopisuslugi getSelected() {
        return selected;
    }

    public void setSelected(Fakturaopisuslugi selected) {
        this.selected = selected;
    }
    
}
