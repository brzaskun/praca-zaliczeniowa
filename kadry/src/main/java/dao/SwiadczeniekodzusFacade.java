/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Swiadczeniekodzus;
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
public class SwiadczeniekodzusFacade extends DAO  {

    @PersistenceContext(unitName = "kadryPU")
    private EntityManager em;
    
    @PreDestroy
    private void preDestroy() {
        em.clear();
        em.close();
        em.getEntityManagerFactory().close();
        em = null;
        
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public SwiadczeniekodzusFacade() {
        super(Swiadczeniekodzus.class);
        super.em = em;
    }
    
   
    public Swiadczeniekodzus findByKod(String string) {
        return (Swiadczeniekodzus) getEntityManager().createNamedQuery("Swiadczeniekodzus.findByKod").setParameter("kod", string).getSingleResult();
    }
    
    public Swiadczeniekodzus findByOpis(String opis) {
        return (Swiadczeniekodzus) getEntityManager().createNamedQuery("Swiadczeniekodzus.findByOpis").setParameter("opis", opis).getSingleResult();
    }

    public List<Swiadczeniekodzus> findAktywne() {
        return getEntityManager().createNamedQuery("Swiadczeniekodzus.findByAktywne").getResultList();
    }
}
