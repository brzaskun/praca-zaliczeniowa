/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Zusstawki;
import error.E;
import java.io.Serializable;
import java.util.List;
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
public class ZUSDAO extends DAO implements Serializable {

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

    public ZUSDAO() {
        super(Zusstawki.class);
        super.em = this.em;
    }
  
    public  List<Zusstawki> findZUS(int duzy0maly1){
        try {
            return getEntityManager().createNamedQuery("Zusstawki.findZUS").setParameter("rodzajzus", duzy0maly1).getResultList();
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }
    

}
