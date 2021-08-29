/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Pracownik;
import entity.Umowa;
import entity.Urlopprezentacja;
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
public class UrlopprezentacjaFacade extends DAO{

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

    public UrlopprezentacjaFacade() {
        super(Urlopprezentacja.class);
        super.em = em;
    }
    

    public Urlopprezentacja findPracownik(Pracownik pracownik, String rok) {
        Urlopprezentacja zwrot = null;
        try {
            zwrot = (Urlopprezentacja) getEntityManager().createNamedQuery("Urlopprezentacja.findByPracownikRok").setParameter("pracownik", pracownik).setParameter("rok", rok).getSingleResult();
        } catch (Exception e) {}
        if (zwrot == null) {
            zwrot = new Urlopprezentacja(null,rok);
        }
        return zwrot;
    }
    
    public List<Urlopprezentacja> findByUmowa(Umowa umowa) {
        List<Urlopprezentacja> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Urlopprezentacja.findByUmowa").setParameter("umowa", umowa).getResultList();
        return zwrot;
    }
}
