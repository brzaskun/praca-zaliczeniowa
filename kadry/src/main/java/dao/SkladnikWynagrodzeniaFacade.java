/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Skladnikwynagrodzenia;
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
public class SkladnikWynagrodzeniaFacade  {

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

    public SkladnikWynagrodzeniaFacade() {
    }
    
    public void create(Skladnikwynagrodzenia entity) {
        getEntityManager().persist(entity);
    }
    
    public List<Skladnikwynagrodzenia> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Skladnikwynagrodzenia.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
     public void edit(Skladnikwynagrodzenia entity) {
        getEntityManager().merge(entity);
    }
}
