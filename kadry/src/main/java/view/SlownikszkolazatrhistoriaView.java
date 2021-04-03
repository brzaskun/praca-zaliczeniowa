/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SlownikszkolazatrhistoriaFacade;
import entity.Slownikszkolazatrhistoria;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class SlownikszkolazatrhistoriaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SlownikszkolazatrhistoriaFacade  slownikszkolazatrhistoriaFacade;
    private List<Slownikszkolazatrhistoria> lista;
    
    @PostConstruct
    private void init() {
        lista = slownikszkolazatrhistoriaFacade.findAll();
    }

    public List<Slownikszkolazatrhistoria> getLista() {
        return lista;
    }

    public void setLista(List<Slownikszkolazatrhistoria> lista) {
        this.lista = lista;
    }
    
    
}
