/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Etat;
import entity.Pracownik;
import entity.Umowa;
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
public class EtatFacade  {

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

    public EtatFacade() {
    }
    
    public void create(Etat entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }
    
    public List<Etat> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Etat.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
     public void edit(Etat entity) {
        getEntityManager().merge(entity);
    }

    public List<Etat> findPracownik(Pracownik pracownik) {
        List<Etat> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Etat.findByPracownik").setParameter("pracownik", pracownik).getResultList();
        return zwrot;
    }
  public void remove(Etat entity) {
        Etat merge = em.merge(entity);
        em.remove(merge);
    }
    
    public void remove(List<Etat> entityList) {
        for (Etat p : entityList) {
            em.remove(em.merge(p));
        }
    }

    public List<Etat> findByUmowa(Umowa umowa) {
        List<Etat> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Etat.findByUmowa").setParameter("umowa", umowa).getResultList();
        return zwrot;
    }
}
