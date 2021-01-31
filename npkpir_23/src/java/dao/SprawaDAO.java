/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Sprawa;
import entity.Uz;
import java.io.Serializable;
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
public class SprawaDAO extends DAO implements Serializable {
    @Inject
    private SessionFacade wpisFacade;
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

    public SprawaDAO() {
        super(Sprawa.class);
        super.em = this.em;
    }
  
 
 
    public List<Sprawa> findSprawaByOdbiorca(Uz odbiorca) {
        return wpisFacade.findSprawaByOdbiorca(odbiorca);
    }

    public List<Sprawa> findSprawaByNadawca(Uz nadawca) {
        return wpisFacade.findSprawaByNadawca(nadawca);
    }
}
