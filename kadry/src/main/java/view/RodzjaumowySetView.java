/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.UmowakodzusFacade;
import entity.Umowakodzus;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
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
public class RodzjaumowySetView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private UmowakodzusFacade umowakodzusFacade;
    private Umowakodzus selectedlista;
    private List<Umowakodzus> filtered;
    private List<Umowakodzus> lista;
    private boolean pokazwszytskieskladniki;
    
    @PostConstruct
    public void init() {
        lista = umowakodzusFacade.findAll();
        filtered = null;
        if (pokazwszytskieskladniki==false) {
            lista = lista.stream().filter(p->p.isAktywny()).collect(Collectors.toList());
        }
    }

    public void zachowaj() {
        umowakodzusFacade.editList(f.l.l(lista, filtered, null));
        filtered = null;
        Msg.msg("Zmiany zachowane");
    }
    
    public List<Umowakodzus> getLista() {
        return lista;
    }

    public void setLista(List<Umowakodzus> lista) {
        this.lista = lista;
    }

 

    public Umowakodzus getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Umowakodzus selectedlista) {
        this.selectedlista = selectedlista;
    }

    public boolean isPokazwszytskieskladniki() {
        return pokazwszytskieskladniki;
    }

    public void setPokazwszytskieskladniki(boolean pokazwszytskieskladniki) {
        this.pokazwszytskieskladniki = pokazwszytskieskladniki;
    }

    public List<Umowakodzus> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<Umowakodzus> filtered) {
        this.filtered = filtered;
    }
    
    
}
