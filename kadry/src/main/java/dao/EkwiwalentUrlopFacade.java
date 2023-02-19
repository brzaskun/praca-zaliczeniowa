/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.EkwiwalentUrlop;
import entity.Umowa;
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
public class EkwiwalentUrlopFacade extends DAO  implements Serializable {
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

    public EkwiwalentUrlopFacade() {
        super(EkwiwalentUrlop.class);
        super.em = em;
    }

   public EkwiwalentUrlop findbyRok(String rokWpisu) {
        return (EkwiwalentUrlop) getEntityManager().createNamedQuery("EkwiwalentUrlop.findByRok").setParameter("rok", rokWpisu).getSingleResult();
    }

    public EkwiwalentUrlop findbyUmowa(Umowa umowa) {
        EkwiwalentUrlop zwrot = null;
        try {
            zwrot = (EkwiwalentUrlop) getEntityManager().createNamedQuery("EkwiwalentUrlop.findByUmowa").setParameter("umowa", umowa).getSingleResult();
        } catch (Exception e) {
        }
        return zwrot;
    }

      
}
