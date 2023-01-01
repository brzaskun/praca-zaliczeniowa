/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.RodzajlistyplacFacade;
import entity.Rodzajlistyplac;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class RodzajlistyplacView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private RodzajlistyplacFacade rodzajlistyplacFacade;
    private Rodzajlistyplac selectedlista;
    private List<Rodzajlistyplac> lista;
    private boolean pokazwszytskieskladniki;
    
    
    @PostConstruct
    public void init() {
        lista = rodzajlistyplacFacade.findAll();
        if (pokazwszytskieskladniki==false) {
            lista = lista.stream().filter(p->p.isAktywna()).collect(Collectors.toList());
        }
    }

    public void zachowaj() {
        rodzajlistyplacFacade.editList(lista);
        Msg.msg("Zmiany zachowane");
    }

    public Rodzajlistyplac getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Rodzajlistyplac selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Rodzajlistyplac> getLista() {
        return lista;
    }

    public void setLista(List<Rodzajlistyplac> lista) {
        this.lista = lista;
    }

    public boolean isPokazwszytskieskladniki() {
        return pokazwszytskieskladniki;
    }

    public void setPokazwszytskieskladniki(boolean pokazwszytskieskladniki) {
        this.pokazwszytskieskladniki = pokazwszytskieskladniki;
    }
    
    
    
    
}
