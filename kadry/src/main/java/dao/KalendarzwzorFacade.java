/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FirmaKadry;
import entity.Kalendarzwzor;
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
public class KalendarzwzorFacade extends DAO   {
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

    public KalendarzwzorFacade() {
        super(Kalendarzwzor.class);
        super.em = em;
    }

   

    public Kalendarzwzor findByFirmaRokMc(FirmaKadry firma, String rok, String mc) {
        Kalendarzwzor zwrot = null;
        try {
            zwrot = (Kalendarzwzor) getEntityManager().createNamedQuery("Kalendarzwzor.findByFirmaRokMc").setParameter("firma", firma).setParameter("rok", rok). setParameter("mc", mc).getSingleResult();
        } catch (Exception e){}
        return zwrot;
    }

    public List<Kalendarzwzor> findByFirmaRok(FirmaKadry firma, String rok) {
       return getEntityManager().createNamedQuery("Kalendarzwzor.findByFirmaRok").setParameter("firma", firma).setParameter("rok", rok).getResultList();
    }

   
}
