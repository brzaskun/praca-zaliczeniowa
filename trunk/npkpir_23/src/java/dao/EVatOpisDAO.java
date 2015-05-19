/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.EVatOpis;
import error.E;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
//pomaga przenosci opisy bo inaczej nie chca sie zachowac. scopy nie pasuja
public class EVatOpisDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade eVatOpisFacade;

    public EVatOpisDAO() {
        super(EVatOpis.class);
    }
    
    public  List<EVatOpis> findAll(){
        try {
            return eVatOpisFacade.findAll(EVatOpis.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public void clear(){
        Collection c = null;
        c = eVatOpisFacade.findAll(EVatOpis.class);
        Iterator it;
        it = c.iterator();
        while(it.hasNext()){
            EVatOpis x = (EVatOpis) it.next();
            eVatOpisFacade.remove(x);
        }
    }
    
    public EVatOpis findS(String name){
        return eVatOpisFacade.findEVatOpis(name);
    }
}
