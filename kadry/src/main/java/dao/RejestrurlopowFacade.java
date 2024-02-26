/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Angaz;
import entity.FirmaKadry;
import entity.Rejestrurlopow;
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
public class RejestrurlopowFacade extends DAO  {

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

    public RejestrurlopowFacade() {
        super(Rejestrurlopow.class);
        super.em = em;
    }
    
    public Rejestrurlopow findByAngaz(Angaz angaz, String rok) {
        Rejestrurlopow zwrot = null;
        try {
            zwrot = (Rejestrurlopow) getEntityManager().createNamedQuery("Rejestrurlopow.findByAngazRok").setParameter("angaz", angaz).setParameter("rok", rok).getSingleResult();
        } catch (Exception e){
            
        }
        return zwrot;
    }
    
    public List<Rejestrurlopow> findByFirmaRok(FirmaKadry firma, String rok) {
        List<Rejestrurlopow> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Rejestrurlopow.findByFirmaRok").setParameter("firma", firma).setParameter("rok", rok).getResultList();
        } catch (Exception e){
            
        }
        return zwrot;
    }

     
   
}
