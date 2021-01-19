/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Definicjalistaplac;
import entity.Firma;
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
public class DefinicjalistaplacFacade  implements Serializable {
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

    public DefinicjalistaplacFacade() {
    }
    
    public void create(Definicjalistaplac entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }
    
    public List<Definicjalistaplac> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Definicjalistaplac.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
     public void edit(Definicjalistaplac entity) {
        getEntityManager().merge(entity);
    }

    public List<Definicjalistaplac> findByFirmaRok(Firma firma, String rok) {
        return getEntityManager().createNamedQuery("Definicjalistaplac.findByFirmaRok").setParameter("firma", firma).setParameter("rok", rok).getResultList();
    }
}
