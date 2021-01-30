/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Angaz;
import entity.Firma;
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
public class AngazFacade extends DAO{

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

    public AngazFacade() {
        super(Firma.class);
        super.em = em;
    }

        
     public List<Angaz> findByFirma(Firma firma) {
         List<Angaz> zwrot = new ArrayList<>();
         try {
             zwrot = getEntityManager().createNamedQuery("Angaz.findByFirma").setParameter("firma", firma).getResultList();
         } catch (Exception e){}
         return zwrot;
     }
     
     public List<Pracownik> findPracownicyByFirma(Firma firma) {
         List<Pracownik> zwrot = new ArrayList<>();
         try {
             zwrot = getEntityManager().createNamedQuery("Angaz.findPracownikByFirma").setParameter("firma", firma).getResultList();
         } catch (Exception e){}
         return zwrot;
     }
}
