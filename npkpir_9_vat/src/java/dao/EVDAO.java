/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import embeddable.EVLista;
import embeddable.EVPozycja;
import embeddable.EVidencja;
import embeddable.Trans;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named(value="EVDAO")
public class EVDAO implements Serializable{
    @Inject
    private EVidencja eVidencja;
    @Inject
    private EVLista eVLista;
    @Inject
    private EVPozycja eVPozycja;
    @Inject
    private Trans trans;
            
    private static final List<EVidencja> wykazEwidencji;
    
    static{
        wykazEwidencji = new ArrayList<EVidencja>();
        wykazEwidencji.add(new EVidencja(EVLista.getVList().get(0), EVPozycja.getPoleDeklaracji().get(5),Trans.getTransList().get(0),false));
        wykazEwidencji.add(new EVidencja(EVLista.getVList().get(2), EVPozycja.getPoleDeklaracji().get(4),Trans.getTransList().get(2),false));
        wykazEwidencji.add(new EVidencja(EVLista.getVList().get(3), EVPozycja.getPoleDeklaracji().get(3),Trans.getTransList().get(2),false));
    }

    public static List<EVidencja> getWykazEwidencji() {
        return wykazEwidencji;
    }
    
    
}
