/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SlownikpotraceniaFacade;
import entity.Slownikpotracenia;
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
    private SlownikpotraceniaFacade slownikpotraceniaFacade;
    private Slownikpotracenia selectedlista;
    private List<Slownikpotracenia> lista;
    
    @PostConstruct
    private void init() {
        lista = slownikpotraceniaFacade.findAll();
    }

    public void zachowaj() {
        slownikpotraceniaFacade.editList(lista);
        Msg.msg("Zmiany zachowane");
    }

    public Slownikpotracenia getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Slownikpotracenia selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Slownikpotracenia> getLista() {
        return lista;
    }

    public void setLista(List<Slownikpotracenia> lista) {
        this.lista = lista;
    }
    
  
    
}
