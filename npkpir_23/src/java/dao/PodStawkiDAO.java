/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podstawki;
import java.io.Serializable;
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
public class PodStawkiDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade podstawkiFacade;
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

    public PodStawkiDAO() {
        super(Podstawki.class);
        super.em = this.em;
    }

   

    public Podstawki find(Integer rok){
        return podstawkiFacade.findPodstawkiyear(rok);
     }
   
   
}
