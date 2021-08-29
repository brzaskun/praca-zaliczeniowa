/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Umowa;
import java.io.Serializable;
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
public class KalendarzmiesiacFacade  extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;

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

    public KalendarzmiesiacFacade() {
        super(Kalendarzmiesiac.class);
        super.em = em;
    }

   
   
    public Kalendarzmiesiac findByRokMcUmowa(Umowa umowa, String rok, String mc) {
        Kalendarzmiesiac zwrot = null;
        try {
            zwrot = (Kalendarzmiesiac) getEntityManager().createNamedQuery("Kalendarzmiesiac.findByRokMcUmowa").setParameter("rok", rok).setParameter("mc", mc).setParameter("umowa", umowa).getSingleResult();
        } catch (Exception e) {}
        return zwrot;
    }
    
    public List<Kalendarzmiesiac> findByRokUmowa(Umowa umowa, String rok) {
        List<Kalendarzmiesiac> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Kalendarzmiesiac.findByRokUmowa").setParameter("rok", rok).setParameter("umowa", umowa).getResultList();
        } catch (Exception e) {}
        return zwrot;
    }

    public List<Kalendarzmiesiac> findByFirmaRokMc(FirmaKadry firma, String rok, String mc) {
        List<Kalendarzmiesiac> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Kalendarzmiesiac.findByFirmaRokMc").setParameter("rok", rok).setParameter("mc", mc).setParameter("firma", firma).getResultList();
        } catch (Exception e) {}
        return zwrot;
    }

   
}
