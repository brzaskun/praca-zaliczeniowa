/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DodatkoweMaileDAO;
import entity.DodatkoweMaile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DodatkoweMaileView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private DodatkoweMaileDAO dodatkoweMaileDAO;
    private List<DodatkoweMaile> lista;
    @Inject
    private DodatkoweMaile selected;
    
    @PostConstruct
    private void init() {
        lista = dodatkoweMaileDAO.findAll();
        if (lista==null) {
            lista = new ArrayList<>();
        }
    }
    
    public void dodaj() {
        if (selected!=null && selected.getMail()!=null && selected.getNazwa()!=null) {
            dodatkoweMaileDAO.dodaj(selected);
            lista.add(selected);
            selected = new DodatkoweMaile();
            Msg.msg("Dodano adres");
        } else {
            Msg.msg("e","Nie wprowadzono danych");
        }
    }
    
    public void usun(DodatkoweMaile p) {
        try {
            dodatkoweMaileDAO.destroy(p);
            lista.remove(p);
            Msg.msg("Usunięto maila");
        } catch (Exception e) {
            Msg.msg("e","Nie usunięto maila");
        }
    }

    public List<DodatkoweMaile> getLista() {
        return lista;
    }

    public void setLista(List<DodatkoweMaile> lista) {
        this.lista = lista;
    }

    public DodatkoweMaile getSelected() {
        return selected;
    }

    public void setSelected(DodatkoweMaile selected) {
        this.selected = selected;
    }
    
    
    
    
}
