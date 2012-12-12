/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.EVatOpis;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
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
}
