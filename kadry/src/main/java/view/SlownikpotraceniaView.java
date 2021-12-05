/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.RodzajpotraceniaFacade;
import entity.Rodzajpotracenia;
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
public class SlownikpotraceniaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private RodzajpotraceniaFacade slownikpotraceniaFacade;
    private Rodzajpotracenia selectedlista;
    private List<Rodzajpotracenia> lista;
    
    @PostConstruct
    private void init() {
        lista = slownikpotraceniaFacade.findAll();
    }

    public void zachowaj() {
        slownikpotraceniaFacade.editList(lista);
        Msg.msg("Zmiany zachowane");
    }

    public Rodzajpotracenia getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Rodzajpotracenia selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Rodzajpotracenia> getLista() {
        return lista;
    }

    public void setLista(List<Rodzajpotracenia> lista) {
        this.lista = lista;
    }
    
  
    
}
