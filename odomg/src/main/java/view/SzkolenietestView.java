/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SzkolenietestFacade;
import entity.Szkolenietest;
import entity.Zakladpracy;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class SzkolenietestView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private List<Szkolenietest> lista;
    private Szkolenietest selected;
    @Inject
    private SzkolenietestFacade szkolenietestFacade;
    
    @PostConstruct
    private void init() {
        lista = szkolenietestFacade.findAll();
    }

    
    public void onRowEdit(RowEditEvent<Zakladpracy> event) {
        Msg.msg("Zmieniono dane firmy");
    }

    public void onRowCancel(RowEditEvent<Zakladpracy> event) {
        Msg.msg("w","Niezmieniono danych firmy");
    }

    public List<Szkolenietest> getLista() {
        return lista;
    }

    public void setLista(List<Szkolenietest> lista) {
        this.lista = lista;
    }

    public Szkolenietest getSelected() {
        return selected;
    }

    public void setSelected(Szkolenietest selected) {
        this.selected = selected;
    }
    
    
    
}
