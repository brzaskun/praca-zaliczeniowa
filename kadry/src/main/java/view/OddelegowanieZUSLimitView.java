/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.OddelegowanieZUSLimitFacade;
import entity.OddelegowanieZUSLimit;
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
public class OddelegowanieZUSLimitView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private OddelegowanieZUSLimit selected;
    private List<OddelegowanieZUSLimit> lista;
    @Inject
    private OddelegowanieZUSLimitFacade oddelegowanieZUSLimitFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista = oddelegowanieZUSLimitFacade.findAll();
        if (lista==null) {
            lista = new ArrayList<>();
        }
    }
    
    public void dodajrok() {
        if (selected!=null && selected.getId()==null) {
            oddelegowanieZUSLimitFacade.create(selected);
            lista.add(selected);
            selected = new OddelegowanieZUSLimit();
            Msg.msg("Dodano stawki za rok");
        } else if (selected!=null && selected.getId()!=null) {
            oddelegowanieZUSLimitFacade.edit(selected);
            selected = new OddelegowanieZUSLimit();
            Msg.msg("Zmieniono stawki za rok");
        }
    }

    public OddelegowanieZUSLimit getSelected() {
        return selected;
    }

    public void setSelected(OddelegowanieZUSLimit selected) {
        this.selected = selected;
    }

    public List<OddelegowanieZUSLimit> getLista() {
        return lista;
    }

    public void setLista(List<OddelegowanieZUSLimit> lista) {
        this.lista = lista;
    }
    
    
    
}
