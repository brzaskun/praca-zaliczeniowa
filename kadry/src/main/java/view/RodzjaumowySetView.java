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
public class RodzjaumowySetView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private UmowakodzusFacade umowakodzusFacade;
    private Umowakodzus selectedlista;
    private List<Umowakodzus> lista;
    
    @PostConstruct
    private void init() {
        lista = umowakodzusFacade.findAll();
    }

    public void zachowaj() {
        umowakodzusFacade.editList(lista);
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
    
    
}
