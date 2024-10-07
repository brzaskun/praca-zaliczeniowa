/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Limitzus;
import java.io.Serializable;
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
public class LimitzusFacade extends DAO  implements Serializable {
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

    public LimitzusFacade() {
        super(Limitzus.class);
        super.em = em;
    }

   public Limitzus findbyRok(String rokWpisu) {
       Limitzus zwrot = null;
       try {
        zwrot = (Limitzus) getEntityManager().createNamedQuery("Limitzus.findByRok").setParameter("rok", rokWpisu).getSingleResult();
       } catch (Exception e)
       {}
       return zwrot;
    }

      
}
