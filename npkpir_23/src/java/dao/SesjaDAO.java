/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Sesja;
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
public class SesjaDAO extends DAO implements Serializable {
   @Inject private SessionFacade sesjaFacade;
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

    public SesjaDAO() {
        super(Sesja.class);
        super.em = this.em;
    } 
   

    public SesjaDAO(Class entityClass) {
        super(entityClass);
    }
    
 
   
   public  List<Sesja> findUser(String user){
        try {
            return sesjaFacade.findUser(user);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public Sesja find(String nrsesji) {
        try {
            return sesjaFacade.findSesja(nrsesji);
        } catch (Exception e) {
            E.e(e);
            return null;
        }

    }

    public List<Sesja> findSesjaZalogowani() {
        try {
            return sesjaFacade.findSesjaZalogowani();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
}
