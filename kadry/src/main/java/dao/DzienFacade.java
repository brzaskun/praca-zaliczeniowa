/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Dzien;
import entity.FirmaKadry;
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
public class DzienFacade extends DAO  implements Serializable {
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

   public DzienFacade() {
        super(Dzien.class);
        super.em = em;
    }
    
    

    public List<Dzien> findByNrwrokuByData(String dataod, String datado, FirmaKadry firmaKadry) {
        return getEntityManager().createNamedQuery("Dzien.findByDatastring").setParameter("dataod", dataod).setParameter("datado", datado).setParameter("firma", firmaKadry).getResultList();
    }
}
