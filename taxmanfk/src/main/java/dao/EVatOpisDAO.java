/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.EVatOpis;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
//pomaga przenosci opisy bo inaczej nie chca sie zachowac. scopy nie pasuja
public class EVatOpisDAO extends DAO implements Serializable {

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

    
    public void clear(){
        Collection c = null;
        c = findAll();
        Iterator it;
        it = c.iterator();
        while(it.hasNext()){
            EVatOpis x = (EVatOpis) it.next();
            remove(x);
        }
    }
    
     public EVatOpis findS(String name){
        return (EVatOpis)  getEntityManager().createNamedQuery("EVatOpis.findByLogin").setParameter("login", name).getSingleResult();
    }
}
