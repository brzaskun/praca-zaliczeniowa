/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.WynagrodzenieminimalneFacade;
import entity.Wynagrodzenieminimalne;
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
public class WynagrodzenieMinimalneView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Wynagrodzenieminimalne selected;
    private List<Wynagrodzenieminimalne> lista;
    @Inject
    private WynagrodzenieminimalneFacade wynagrodzenieminimalneFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista = wynagrodzenieminimalneFacade.findAll();
        if (lista==null) {
            lista = new ArrayList<>();
        }
    }
    
    public void dodajrok() {
        if (selected!=null && selected.getId()==null) {
            wynagrodzenieminimalneFacade.create(selected);
            lista.add(selected);
            selected = new Wynagrodzenieminimalne();
            Msg.msg("Dodano wynagrodzenie minimalne za rok");
        } else if (selected!=null && selected.getId()!=null) {
            wynagrodzenieminimalneFacade.edit(selected);
            selected = new Wynagrodzenieminimalne();
            Msg.msg("Zmieniono wynagrodzenie minimalne za rok");
        }
    }

    public Wynagrodzenieminimalne getSelected() {
        return selected;
    }

    public void setSelected(Wynagrodzenieminimalne selected) {
        this.selected = selected;
    }

    public List<Wynagrodzenieminimalne> getLista() {
        return lista;
    }

    public void setLista(List<Wynagrodzenieminimalne> lista) {
        this.lista = lista;
    }
    
    
    
}
