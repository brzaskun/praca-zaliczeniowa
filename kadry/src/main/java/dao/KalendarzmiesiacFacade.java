/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Kalendarzmiesiac;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
public class KalendarzmiesiacFacade  {

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

    public KalendarzmiesiacFacade() {
    }
    
    public void create(Kalendarzmiesiac entity) {
        getEntityManager().persist(entity);
    }
    
    public List<Kalendarzmiesiac> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Kalendarzmiesiac.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
     public void edit(Kalendarzmiesiac entity) {
        getEntityManager().merge(entity);
    }
}
