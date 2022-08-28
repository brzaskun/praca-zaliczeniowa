/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Mobiledok;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class MobiledokDAO extends DAO implements Serializable{
     private static final long serialVersionUID = 1L;

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

    public MobiledokDAO() {
        super(Mobiledok.class);
        super.em = this.em;
    }

 

    public List<Mobiledok> findByNip(String nip) {
        //return getEntityManager().createNamedQuery("Mobiledok.findByNip").setParameter("nip", nip).getResultList();
        return getEntityManager().createQuery("SELECT f FROM Mobiledok f WHERE f.nip = :nip").setParameter("nip", nip).getResultList();
    }
}
