/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Uz;
import java.util.Collections;
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
public class UzFacade {
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
    
    public void create(Uz entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }
    
    public List<Uz> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Uz.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
     public void edit(Uz entity) {
        getEntityManager().merge(entity);
    }
    
     public void remove(Uz entity) {
        em.remove(em.merge(entity));
    }
    
    public void remove(List<Uz> entityList) {
        for (Uz p : entityList) {
            em.remove(em.merge(p));
        }
    }

   
    public Uz findUzByLogin(String login){
         Uz zwrot = null;
        try {
            zwrot = (Uz) getEntityManager().createNamedQuery("Uz.findByLogin").setParameter("login", login).getSingleResult();
        } catch (Exception e) {
            
        }
        return zwrot;
     }
    
    public List<Uz> findByUprawnienia(String uprawnienia) {
        return Collections.synchronizedList(getEntityManager().createNamedQuery("Uz.findByUprawnienia").setParameter("uprawnienia", uprawnienia).getResultList());
    }
   
     
    public List<String> findUzByUprawnienia(String uprawnienia){
         return Collections.synchronizedList(getEntityManager().createNamedQuery("Uz.findByUprawnienia").setParameter("uprawnienia", uprawnienia).getResultList());
     }

    public Uz findUzByPesel(String pesel) {
        return (Uz) getEntityManager().createNamedQuery("Uz.findByPesel").setParameter("pesel", pesel).getSingleResult();
    }
    
}

