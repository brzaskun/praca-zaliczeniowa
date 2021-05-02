/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PodatkiFacade;
import entity.Podatki;
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
public class PodatkiUstawieniaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Podatki selected;
    private List<Podatki> lista;
    @Inject
    private PodatkiFacade podatkiFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista = podatkiFacade.findAll();
        if (lista==null) {
            lista = new ArrayList<>();
        }
    }
    
    public void dodajrok() {
        if (selected!=null && selected.getId()==null) {
            podatkiFacade.create(selected);
            lista.add(selected);
            selected = new Podatki();
            Msg.msg("Dodano stawki za rok");
        } else if (selected!=null && selected.getId()!=null) {
            podatkiFacade.edit(selected);
            selected = new Podatki();
            Msg.msg("Zmieniono stawki za rok");
        }
    }

    public Podatki getSelected() {
        return selected;
    }

    public void setSelected(Podatki selected) {
        this.selected = selected;
    }

    public List<Podatki> getLista() {
        return lista;
    }

    public void setLista(List<Podatki> lista) {
        this.lista = lista;
    }
    
    
    
}
