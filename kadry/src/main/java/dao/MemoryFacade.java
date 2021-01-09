/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Memory;
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
public class MemoryFacade  {

    @PersistenceContext(unitName = "kadryPU")
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

    public MemoryFacade() {
    }
    
    public void create(Memory entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }
    
     public void remove(Memory entity) {
        em.remove(em.merge(entity));
    }
    
    public void remove(List<Memory> entityList) {
        for (Memory p : entityList) {
            em.remove(em.merge(p));
        }
    }
    public List<Memory> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Memory.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
     public void edit(Memory entity) {
        getEntityManager().merge(entity);
    }
     
   
}
