/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Zmiennawynagrodzenia;
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
public class ZmiennaWynagrodzeniaFacade  {

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

    public ZmiennaWynagrodzeniaFacade() {
    }
    
    public void create(Zmiennawynagrodzenia entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }
    
    public List<Zmiennawynagrodzenia> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Zmiennawynagrodzenia.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
     public void edit(Zmiennawynagrodzenia entity) {
        getEntityManager().merge(entity);
    }
}
