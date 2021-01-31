/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.DodatkoweMaile;
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
public class DodatkoweMaileDAO extends DAO implements Serializable {

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

    public DodatkoweMaileDAO() {
        super(DodatkoweMaile.class);
        super.em = this.em;
    }

       
    public  List<DodatkoweMaile> findAll(){
        try {
            return sessionFacade.findAll(DodatkoweMaile.class);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }
}
