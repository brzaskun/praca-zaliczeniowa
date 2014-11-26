/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.persistence.Embeddable;

/**
 *
 * @author Grzegorz Grzelczyk
 */
@Named
@Embeddable
public class Uprawnienia implements Serializable{
    private static final List<String> listaUprawnien;

    static {
        listaUprawnien = new ArrayList<String>();
        listaUprawnien.add("Administrator");
        listaUprawnien.add("Manager");
        listaUprawnien.add("Bookkeeper");
        listaUprawnien.add("BookkeeperFK");
        listaUprawnien.add("GuestFK");
        listaUprawnien.add("Guest");
    }

    public List<String> getListaUprawnien() {
        return listaUprawnien;
    }
    
}
