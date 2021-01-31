/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Adminmail;
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
public class AdminmailDAO  extends DAO implements Serializable {
    
    @Inject
    private SessionFacade adminmailFacade;
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

    public AdminmailDAO() {
        super(Adminmail.class);
        super.em = this.em;
    }

    
    
    public  List<Adminmail> findAll(){
        try {
            return adminmailFacade.findAll(Adminmail.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

   
    
    
}
