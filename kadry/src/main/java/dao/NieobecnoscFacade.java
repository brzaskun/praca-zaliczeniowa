/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Angaz;
import entity.Nieobecnosc;
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
public class NieobecnoscFacade extends DAO    implements Serializable {
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

    public NieobecnoscFacade() {
        super(Nieobecnosc.class);
        super.em = em;
    }
    
   

    public List<Nieobecnosc> findByAngaz(Angaz angaz) {
        return getEntityManager().createNamedQuery("Nieobecnosc.findByAngaz").setParameter("angaz", angaz).getResultList();
    }
    
    public List<Nieobecnosc> findByAngaz200(Angaz angaz) {
        return getEntityManager().createNamedQuery("Nieobecnosc.findByAngaz200").setParameter("angaz", angaz).getResultList();
    }
}
