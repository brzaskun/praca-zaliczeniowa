/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Platnosci;
import entity.PlatnosciPK;
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
public class PlatnosciDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade platnosciFacade;
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

    public PlatnosciDAO() {
        super(Platnosci.class);
        super.em = this.em;
    }

  
  
     public Platnosci findPK(PlatnosciPK key) throws Exception{
        return platnosciFacade.findPlatnosciPK(key);
     }
   
     public List<Platnosci> findPodRok(String rok, String podatnik) throws Exception{
        return platnosciFacade.findPlatnosciPodRok(rok, podatnik);
     }
     
  
}
