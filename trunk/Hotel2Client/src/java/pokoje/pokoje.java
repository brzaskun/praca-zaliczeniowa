/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokoje;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import service.Pokoj;

/**
 *
 * @author Osito
 */
@Named
public class pokoje implements Serializable {
    public List<Pokoj> lista;

    public pokoje() {
        lista = new ArrayList<Pokoj>();
    }
    
    @PostConstruct
    public void init(){
        lista.addAll(getPokoje());
    }

    private static java.util.List<service.Pokoj> getPokoje() {
        service.Rezerwuj_Service service = new service.Rezerwuj_Service();
        service.Rezerwuj port = service.getRezerwujPort();
        return port.getPokoje();
    }

    public List<Pokoj> getLista() {
        return lista;
    }

    public void setLista(List<Pokoj> lista) {
        this.lista = lista;
    }
    
    
}
