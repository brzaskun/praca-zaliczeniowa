/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.StornoDok;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class StornoDokDAO extends DAO implements Serializable {
   @Inject private SessionFacade stornoFacade;
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

    public StornoDokDAO() {
        super(StornoDok.class);
        super.em = this.em;
    }
  
   
   
  public StornoDok find(Integer rok, String mc, String podatnik){
      try{
         return stornoFacade.findStornoDok(rok,mc,podatnik);
      } catch (Exception e) { E.e(e); 
          return null;
      }
     }
  
  public List<StornoDok> find(Integer rok, String podatnik){
         return stornoFacade.findStornoDok(rok,podatnik);
     }
      
}
