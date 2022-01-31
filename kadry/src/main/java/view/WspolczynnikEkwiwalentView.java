/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.WspolczynnikEkwiwalentFacade;
import entity.WspolczynnikEkwiwalent;
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
public class WspolczynnikEkwiwalentView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WspolczynnikEkwiwalent selected;
    private List<WspolczynnikEkwiwalent> lista;
    @Inject
    private WspolczynnikEkwiwalentFacade wspolczynnikEkwiwalentFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista = wspolczynnikEkwiwalentFacade.findAll();
        if (lista==null) {
            lista = new ArrayList<>();
        }
    }
    
    public void dodajrok() {
        if (selected!=null && selected.getId()==null) {
            wspolczynnikEkwiwalentFacade.create(selected);
            lista.add(selected);
            selected = new WspolczynnikEkwiwalent();
            Msg.msg("Dodano współczynnik za rok");
        } else if (selected!=null && selected.getId()!=null) {
            wspolczynnikEkwiwalentFacade.edit(selected);
            selected = new WspolczynnikEkwiwalent();
            Msg.msg("Zmieniono współczynnik za rok");
        }
    }

    public WspolczynnikEkwiwalent getSelected() {
        return selected;
    }

    public void setSelected(WspolczynnikEkwiwalent selected) {
        this.selected = selected;
    }

    public List<WspolczynnikEkwiwalent> getLista() {
        return lista;
    }

    public void setLista(List<WspolczynnikEkwiwalent> lista) {
        this.lista = lista;
    }
    
    
    
}
