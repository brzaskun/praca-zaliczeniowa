/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import embeddable.ERodzajZakupu;
import embeddable.EVLista;
import embeddable.EVPozycja;
import embeddable.EVidencja;
import embeddable.Trans;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named(value="EVDAO")
public class EVDAO implements Serializable{
              
    private static final List<EVidencja> wykazEwidencji;
    
    static{
        wykazEwidencji = new ArrayList<EVidencja>();
        wykazEwidencji.add(new EVidencja(EVLista.getVList().get(0), EVPozycja.getPoleDeklaracji().get(7),Trans.getTransList().get(0),ERodzajZakupu.getRodzajZakupu().get(0),false));
        wykazEwidencji.add(new EVidencja(EVLista.getVList().get(1), EVPozycja.getPoleDeklaracji().get(6),Trans.getTransList().get(1),ERodzajZakupu.getRodzajZakupu().get(0),false));
        wykazEwidencji.add(new EVidencja(EVLista.getVList().get(2), EVPozycja.getPoleDeklaracji().get(5),Trans.getTransList().get(2),ERodzajZakupu.getRodzajZakupu().get(0),false));
        wykazEwidencji.add(new EVidencja(EVLista.getVList().get(3), EVPozycja.getPoleDeklaracji().get(4),Trans.getTransList().get(2),ERodzajZakupu.getRodzajZakupu().get(0),false));
        wykazEwidencji.add(new EVidencja(EVLista.getVList().get(4), EVPozycja.getPoleDeklaracji().get(3),Trans.getTransList().get(2),ERodzajZakupu.getRodzajZakupu().get(0),false));
    }

    public static List<EVidencja> getWykazEwidencji() {
        return wykazEwidencji;
    }
    
    public static EVidencja znajdzponazwie(String nazwa) throws Exception{
        EVidencja tmp = new EVidencja();
        Iterator it = wykazEwidencji.listIterator();
        while(it.hasNext()){
            EVidencja tmpx = (EVidencja) it.next();
            if(tmpx.getNazwaEwidencji().equals(nazwa)){
                return tmpx;
            }
        }
        throw new Exception();
    }

}
