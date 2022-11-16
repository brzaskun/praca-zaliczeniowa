/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.LimitdochodudwaszescFacade;
import entity.Limitdochodudwaszesc;
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
public class LimitdochodudwaszescView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Limitdochodudwaszesc selected;
    private List<Limitdochodudwaszesc> lista;
    @Inject
    private LimitdochodudwaszescFacade limitdochodudwaszescFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista = limitdochodudwaszescFacade.findAll();
        if (lista==null) {
            lista = new ArrayList<>();
        }
    }
    
    public void dodajrok() {
        if (selected!=null && selected.getId()==null) {
            limitdochodudwaszescFacade.create(selected);
            lista.add(selected);
            selected = new Limitdochodudwaszesc();
            Msg.msg("Dodano współczynnik za rok");
        } else if (selected!=null && selected.getId()!=null) {
            limitdochodudwaszescFacade.edit(selected);
            selected = new Limitdochodudwaszesc();
            Msg.msg("Zmieniono współczynnik za rok");
        }
    }

    public Limitdochodudwaszesc getSelected() {
        return selected;
    }

    public void setSelected(Limitdochodudwaszesc selected) {
        this.selected = selected;
    }

    public List<Limitdochodudwaszesc> getLista() {
        return lista;
    }

    public void setLista(List<Limitdochodudwaszesc> lista) {
        this.lista = lista;
    }
    
    
    
}
