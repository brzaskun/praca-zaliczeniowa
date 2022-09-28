/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.ZaswiadczeniaFacade;
import entity.Zakladpracy;
import entity.Zaswiadczenia;
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
public class ZaswiadczenieView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private List<Zaswiadczenia> lista;
    private Zaswiadczenia selected;
    @Inject
    private ZaswiadczeniaFacade zaswiadczeniaFacade;
    
    @PostConstruct
    private void init() {
        lista = zaswiadczeniaFacade.findAll();
    }

    public void onRowEdit(RowEditEvent<Zakladpracy> event) {
        Msg.msg("Zmieniono dane firmy");
    }

    public void onRowCancel(RowEditEvent<Zakladpracy> event) {
        Msg.msg("w","Niezmieniono danych firmy");
    }
    
    public List<Zaswiadczenia> getLista() {
        return lista;
    }

    public void setLista(List<Zaswiadczenia> lista) {
        this.lista = lista;
    }

    public Zaswiadczenia getSelected() {
        return selected;
    }

    public void setSelected(Zaswiadczenia selected) {
        this.selected = selected;
    }
    
    
    
}
