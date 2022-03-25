/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Uczestnikgrupy;
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
public class UczestnikGrupyFacade extends DAO  implements Serializable {
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

    public UczestnikGrupyFacade() {
        super(Uczestnikgrupy.class);
        super.em = em;
    }

    public List<Uczestnikgrupy> findByidUczestnik(int idUczestnik) {
        List<Uczestnikgrupy> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Uczestnikgrupy.findByIdUczestnik").setParameter("idUczestnik", idUczestnik).getResultList();
        } catch (Exception ex){
        }
        return zwrot;
    }

       
   
}
