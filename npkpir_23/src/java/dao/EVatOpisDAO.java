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
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ejb.Stateless;import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
//pomaga przenosci opisy bo inaczej nie chca sie zachowac. scopy nie pasuja
public class EVatOpisDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade eVatOpisFacade;
      @PersistenceContext(unitName = "npkpir_22PU")
    private EntityManager em;
    
    @PreDestroy
    private void preDestroy() {
        em.clear();
        em.close();
        em.getEntityManagerFactory().close();
        em = null;
        error.E.s("koniec jpa");
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public EVatOpisDAO() {
        super(EVatOpis.class);
        super.em = this.em;
    }

        
    public  List<EVatOpis> findAll(){
        try {
            return eVatOpisFacade.findAll(EVatOpis.class);
        } catch (Exception e) { 
            E.e(e); 
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
