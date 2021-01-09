/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Definicjalistaplac;
import entity.Kalendarzmiesiac;
import entity.Pasekwynagrodzen;
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
public class PasekwynagrodzenFacade   implements Serializable {
    private static final long serialVersionUID = 1L;

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

    public PasekwynagrodzenFacade() {
    }
    
    public void create(Pasekwynagrodzen entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }
    
    public List<Pasekwynagrodzen> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Pasekwynagrodzen.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public void edit(Pasekwynagrodzen entity) {
        getEntityManager().merge(entity);
    }
     
    public void remove(Pasekwynagrodzen entity) {
        em.remove(em.merge(entity));
    }
    
    public void remove(List<Pasekwynagrodzen> entityList) {
        for (Pasekwynagrodzen p : entityList) {
            em.remove(em.merge(p));
        }
    }

    public Pasekwynagrodzen findByDefKal(Definicjalistaplac definicjalistaplac, Kalendarzmiesiac kalendarzmiesiac) {
        return (Pasekwynagrodzen) getEntityManager().createNamedQuery("Pasekwynagrodzen.findByDefKal").setParameter("definicjalistaplac", definicjalistaplac).setParameter("kalendarzmiesiac", kalendarzmiesiac).getSingleResult();
    }
}
