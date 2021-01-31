/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entityfk.Vatuepodatnik;
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

public class VatuepodatnikDAO extends DAO<Vatuepodatnik> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
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

    public VatuepodatnikDAO() {
        super(Vatuepodatnik.class);
        super.em = this.em;
    }
  
    
    public  List<Vatuepodatnik> findAll(){
        try {
            return findAll();
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

    public Vatuepodatnik find(String rokWpisu, String miesiacWpisu, String podatnikWpisu) {
        try {
            return (Vatuepodatnik) sessionFacade.findVatuepodatnik(rokWpisu, miesiacWpisu, podatnikWpisu);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
}
