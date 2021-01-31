/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.DAO;
import entityfk.Waluty;
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

public class WalutyDAOfk extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade walutyFacade;
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

    public WalutyDAOfk() {
        super(Waluty.class);
        super.em = this.em;
    }
      
 
    public Waluty findWalutaBySymbolWaluty(String staranazwa) {
        try {
            return walutyFacade.findWalutaBySymbolWaluty(staranazwa);
        } catch (Exception e) { 
            //E.e(e); 
            //error.E.s(" blad staranazwa "+staranazwa);
            return null;
        }
    }

      
}
