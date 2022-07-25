/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.JPKVATWersja;
import entity.JPKvatwersjaEvewidencja;
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
public class JPKvatwersjaEvewidencjaDAO extends DAO implements Serializable{
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

    public JPKvatwersjaEvewidencjaDAO() {
        super(JPKvatwersjaEvewidencja.class);
        super.em = this.em;
    }


    public List<JPKvatwersjaEvewidencja> findJPKEwidencje(JPKVATWersja selected) {
        return sessionFacade.getEntityManager().createNamedQuery("JPKvatwersjaEvewidencja.findyByJPKvatwersja").setParameter("jpkvatwersja", selected).getResultList();
    }
    
}
