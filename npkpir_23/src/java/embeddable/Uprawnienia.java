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
        listaUprawnien.add("Noobie");
        listaUprawnien.add("Zablokowany");
        listaUprawnien.add("Administrator");
        listaUprawnien.add("Bookkeeper");
        listaUprawnien.add("BookkeeperFK");
        listaUprawnien.add("Dedra");
        listaUprawnien.add("Guest");
        listaUprawnien.add("GuestFaktura");
        listaUprawnien.add("GuestFK");
        listaUprawnien.add("GuestFKBook");
        listaUprawnien.add("Manager");
        listaUprawnien.add("Multiuser");
        listaUprawnien.add("MultiuserBook");
        listaUprawnien.add("Stowarzyszenie");
        listaUprawnien.add("ZUS");
    }

    public List<String> getListaUprawnien() {
        return listaUprawnien;
    }
    
}
