/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.LimitzusFacade;
import entity.Limitzus;
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
public class LimitzusView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Limitzus selected;
    private List<Limitzus> lista;
    @Inject
    private LimitzusFacade limitzusFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista = limitzusFacade.findAll();
        if (lista==null) {
            lista = new ArrayList<>();
        }
    }
    
    public void dodajrok() {
        if (selected!=null && selected.getId()==null) {
            limitzusFacade.create(selected);
            lista.add(selected);
            selected = new Limitzus();
            Msg.msg("Dodano współczynnik za rok");
        } else if (selected!=null && selected.getId()!=null) {
            limitzusFacade.edit(selected);
            selected = new Limitzus();
            Msg.msg("Zmieniono współczynnik za rok");
        }
    }

    public Limitzus getSelected() {
        return selected;
    }

    public void setSelected(Limitzus selected) {
        this.selected = selected;
    }

    public List<Limitzus> getLista() {
        return lista;
    }

    public void setLista(List<Limitzus> lista) {
        this.lista = lista;
    }
    
    
    
}
