/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Rodzajwynagrodzenia;
import error.E;
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
public class RodzajwynagrodzeniaFacade    implements Serializable {
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

    public RodzajwynagrodzeniaFacade() {
    }
    
    public void create(Rodzajwynagrodzenia entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }
    
    public List<Rodzajwynagrodzenia> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Rodzajwynagrodzenia.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
     public void edit(Rodzajwynagrodzenia entity) {
        getEntityManager().merge(entity);
    }
    
     public void edit(List<Rodzajwynagrodzenia> entityList) {
        for (Rodzajwynagrodzenia p : entityList) {
            try {
                getEntityManager().merge(p);
            } catch (Exception e) {
                E.e(e);
            }
        }
    }

    public Rodzajwynagrodzenia findZasadnicze() {
        return (Rodzajwynagrodzenia) getEntityManager().createNamedQuery("Rodzajwynagrodzenia.findByOpispelny").setParameter("opispelny", "Wynagrodzenie zasadnicze").getSingleResult();
    }

  
}
