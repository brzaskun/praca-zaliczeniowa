/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Angaz;
import entity.Nieobecnoscprezentacja;
import entity.Pracownik;
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
public class NieobecnoscprezentacjaFacade extends DAO{

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

    public NieobecnoscprezentacjaFacade() {
        super(Nieobecnoscprezentacja.class);
        super.em = em;
    }
    

    public Nieobecnoscprezentacja findPracownik(Pracownik pracownik, String rok) {
        Nieobecnoscprezentacja zwrot = null;
        try {
            zwrot = (Nieobecnoscprezentacja) getEntityManager().createNamedQuery("Urlopprezentacja.findByPracownikRok").setParameter("pracownik", pracownik).setParameter("rok", rok).getSingleResult();
        } catch (Exception e) {}
        if (zwrot == null) {
            zwrot = new Nieobecnoscprezentacja(null,rok);
        }
        return zwrot;
    }
    
    public List<Nieobecnoscprezentacja> findByAngaz(Angaz angaz) {
        List<Nieobecnoscprezentacja> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Urlopprezentacja.findByAngaz").setParameter("umowa", angaz).getResultList();
        return zwrot;
    }
}
