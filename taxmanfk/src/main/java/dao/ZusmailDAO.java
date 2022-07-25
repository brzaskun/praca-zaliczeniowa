/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Zusmail;
import error.E;
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
public class ZusmailDAO extends DAO implements Serializable {
    
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

    public ZusmailDAO() {
        super(Zusmail.class);
        super.em = this.em;
    }

   
    
    public Zusmail findZusmail(Zusmail zusmail) {
        try {
            return sessionFacade.findZusmail(zusmail);
        } catch (Exception e) { E.e(e); 
        }
        return null;
    }
    
    public List<Zusmail> findZusRokMc(String rok, String mc) {
        try {
            return sessionFacade.findZusRokMc(rok,mc);
        } catch (Exception e) { E.e(e); 
        }
        return null;
    }
    
}
