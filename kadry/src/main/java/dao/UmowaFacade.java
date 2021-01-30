/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Angaz;
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
public class UmowaFacade extends DAO  {

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

    public UmowaFacade() {
        super(Umowa.class);
        super.em = em;
    }
    
    public List<Umowa> findPracownik(Pracownik pracownik) {
        List<Umowa> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Umowa.findByPracownik").setParameter("pracownik", pracownik).getResultList();
        return zwrot;
    }
    public List<Umowa> findByAngaz(Angaz angaz) {
        List<Umowa> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Umowa.findByAngaz").setParameter("angaz", angaz).getResultList();
        return zwrot;
    }
}
