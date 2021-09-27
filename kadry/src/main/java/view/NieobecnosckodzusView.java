/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.NieobecnosckodzusFacade;
import entity.Nieobecnosckodzus;
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
public class NieobecnosckodzusView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private NieobecnosckodzusFacade nieobecnosckodzusFacade;
    private Nieobecnosckodzus selectedlista;
    private List<Nieobecnosckodzus> lista;
    
    @PostConstruct
    private void init() {
        lista = nieobecnosckodzusFacade.findAll();
    }

    public void zachowaj() {
        nieobecnosckodzusFacade.editList(lista);
        Msg.msg("Zmiany zachowane");
    }

    public Nieobecnosckodzus getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Nieobecnosckodzus selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Nieobecnosckodzus> getLista() {
        return lista;
    }

    public void setLista(List<Nieobecnosckodzus> lista) {
        this.lista = lista;
    }
    
  
    
}
