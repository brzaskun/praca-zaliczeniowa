/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Firma;
import entity.Kalendarzwzor;
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
public class KalendarzwzorFacade   implements Serializable {
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

    public KalendarzwzorFacade() {
    }
    
    public void create(Kalendarzwzor entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }
    
    public List<Kalendarzwzor> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Kalendarzwzor.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
     public void edit(Kalendarzwzor entity) {
        getEntityManager().merge(entity);
    }

    public Kalendarzwzor findByFirmaRokMc(Firma firma, String rok, String mc) {
        Kalendarzwzor zwrot = null;
        try {
            zwrot = (Kalendarzwzor) getEntityManager().createNamedQuery("Kalendarzwzor.findByFirmaRokMc").setParameter("firma", firma).setParameter("rok", rok). setParameter("mc", mc).getSingleResult();
        } catch (Exception e){}
        return zwrot;
    }

    public List<Kalendarzwzor> findByFirmaRok(Firma firma, String rok) {
       return getEntityManager().createNamedQuery("Kalendarzwzor.findByFirmaRok").setParameter("firma", firma).setParameter("rok", rok).getResultList();
    }
}
