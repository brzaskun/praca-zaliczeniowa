/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Vieskontrahent;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
//pomaga przenosci opisy bo inaczej nie chca sie zachowac. scopy nie pasuja
public class VieskontrahentDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade sessionFacade;
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

    public VieskontrahentDAO() {
        super(Vieskontrahent.class);
        super.em = this.em;
    }
    
    public List<Vieskontrahent> findByRokMcTyd(String rok, String mc, String tydzien) {
        List<Vieskontrahent> zwrot = null;
         try {
            zwrot = getEntityManager().createNamedQuery("Vieskontrahent.findByRokMcTyd").setParameter("rok", rok).setParameter("mc", mc).setParameter("tydzien", tydzien).getResultList();
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
  
    
    
}
