/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entityfk.BankImportWzory;
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
public class BankImportWzoryDAO extends DAO implements Serializable {

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

    public BankImportWzoryDAO() {
        super(BankImportWzory.class);
        super.em = this.em;
    }


    public List<BankImportWzory> findByBank(String wybranybankimport) {
        return sessionFacade.getEntityManager().createNamedQuery("BankImportWzory.findByBank").setParameter("bank", wybranybankimport).getResultList();
    }
    
  
}

