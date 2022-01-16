/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Szkolenie;
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
public class SzkolenieFacade extends DAO  implements Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "odomgPU")
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

    public SzkolenieFacade() {
        super(Szkolenie.class);
        super.em = em;
    }

    public List<Szkolenie> findBynazwa(String nazwaszkolenia) {
        List<Szkolenie> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Szkolenie.findByNazwaszkolenia").setParameter("nazwaszkolenia", nazwaszkolenia).getResultList();
        } catch (Exception ex){
        }
        return zwrot;
    }

       
   
}
