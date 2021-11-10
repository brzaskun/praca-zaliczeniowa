/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.DeklaracjaSchowek;
import entity.Pracownik;
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
public class DeklaracjaSchowekFacade extends DAO  implements Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "kadryPU")
    private EntityManager em;
    
    @PreDestroy
    private void preDestroy() {
        em.clear();
        em.close();
        em.getEntityManagerFactory().close();
        em = null;
        
    }

    protected EntityManager getEntityManager() {
        return em;
    }

   public DeklaracjaSchowekFacade() {
        super(DeklaracjaSchowek.class);
        super.em = em;
    }
    
    

    public List<DeklaracjaSchowek> findByNrwrokuByData(String rok, Pracownik pracownik) {
        return getEntityManager().createNamedQuery("DeklaracjaSchowek.findByRokPracownik").setParameter("rok", rok).setParameter("pracownik", pracownik).getResultList();
    }
}
