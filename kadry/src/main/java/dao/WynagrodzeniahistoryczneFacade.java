/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Angaz;
import entity.Pracownik;
import entity.Wynagrodzeniahistoryczne;
import error.E;
import java.util.ArrayList;
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
public class WynagrodzeniahistoryczneFacade  {

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

    public WynagrodzeniahistoryczneFacade() {
    }
    
    public void create(Wynagrodzeniahistoryczne entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }
    
    public List<Wynagrodzeniahistoryczne> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Wynagrodzeniahistoryczne.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
     public void edit(Wynagrodzeniahistoryczne entity) {
        getEntityManager().merge(entity);
    }
     
    public void editList(List<Wynagrodzeniahistoryczne> entityList) {
        for (Wynagrodzeniahistoryczne p : entityList) {
            try {
                getEntityManager().merge(p);
            } catch (Exception e) {
                E.e(e);
            }
        }
    }

    public List<Wynagrodzeniahistoryczne> findPracownik(Pracownik pracownik) {
        List<Wynagrodzeniahistoryczne> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Wynagrodzeniahistoryczne.findByPracownik").setParameter("pracownik", pracownik).getResultList();
        return zwrot;
    }
  public void remove(Wynagrodzeniahistoryczne entity) {
        Wynagrodzeniahistoryczne merge = em.merge(entity);
        em.remove(merge);
    }
    
    public void remove(List<Wynagrodzeniahistoryczne> entityList) {
        for (Wynagrodzeniahistoryczne p : entityList) {
            em.remove(em.merge(p));
        }
    }

    public List<Wynagrodzeniahistoryczne> findByAngaz(Angaz angaz) {
        List<Wynagrodzeniahistoryczne> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Wynagrodzeniahistoryczne.findByAngaz").setParameter("angaz", angaz).getResultList();
        return zwrot;
    }
}
