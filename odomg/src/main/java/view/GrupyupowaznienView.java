/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.GrupyupowaznienFacade;
import entity.Grupyupowaznien;
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
public class GrupyupowaznienView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private List<Grupyupowaznien> lista;
    private Grupyupowaznien selected;
    @Inject
    private GrupyupowaznienFacade grupyupowaznienFacade;
    
    @PostConstruct
    private void init() {
        lista = grupyupowaznienFacade.findAll();
    }

    public void onRowEdit(RowEditEvent<Zakladpracy> event) {
        Msg.msg("Zmieniono dane firmy");
    }

    public void onRowCancel(RowEditEvent<Zakladpracy> event) {
        Msg.msg("w","Niezmieniono danych firmy");
    }
    
    public List<Grupyupowaznien> getLista() {
        return lista;
    }

    public void setLista(List<Grupyupowaznien> lista) {
        this.lista = lista;
    }

    public Grupyupowaznien getSelected() {
        return selected;
    }

    public void setSelected(Grupyupowaznien selected) {
        this.selected = selected;
    }
    
    
    
}
