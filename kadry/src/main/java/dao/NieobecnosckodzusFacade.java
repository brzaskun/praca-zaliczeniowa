/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Nieobecnosckodzus;
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
public class NieobecnosckodzusFacade  {

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

    public NieobecnosckodzusFacade() {
    }
    
    public void create(Nieobecnosckodzus entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }
    
    public List<Nieobecnosckodzus> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Nieobecnosckodzus.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
     public void edit(Nieobecnosckodzus entity) {
        getEntityManager().merge(entity);
    }

    public Nieobecnosckodzus findByKod(String string) {
        return (Nieobecnosckodzus) getEntityManager().createNamedQuery("Nieobecnosckodzus.findByKod").setParameter("kod", string).getSingleResult();
    }
}
