/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class StowNaliczenieKategorie implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> lista;

    public StowNaliczenieKategorie() {
        this.lista = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        this.lista.add("składka");
        this.lista.add("energia");
        this.lista.add("hangar");
        this.lista.add("media");
        this.lista.add("pod. od nieruch.");
        this.lista.add("ścieki");
        this.lista.add("woda");
    }
    
    public List<String> getLista() {
        return lista;
    }

    public void setLista(List<String> lista) {
        this.lista = lista;
    }
    
    
    
}
