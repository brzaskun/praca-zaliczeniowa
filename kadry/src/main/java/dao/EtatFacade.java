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
public class EtatFacade extends DAO{

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
        super(Etat.class);
        super.em = em;
    }
    

    public List<Etat> findPracownik(Pracownik pracownik) {
        List<Etat> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Etat.findByPracownik").setParameter("pracownik", pracownik).getResultList();
        return zwrot;
    }
    
    public List<Etat> findByUmowa(Umowa umowa) {
        List<Etat> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Etat.findByUmowa").setParameter("umowa", umowa).getResultList();
        return zwrot;
    }
}
