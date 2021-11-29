/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Definicjalistaplac;
import entity.FirmaKadry;
import entity.Rodzajlistyplac;
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
public class DefinicjalistaplacFacade extends DAO implements Serializable {
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

    public DefinicjalistaplacFacade() {
        super(Definicjalistaplac.class);
        super.em = em;
    }

  
    public List<Definicjalistaplac> findByFirmaRok(FirmaKadry firma, String rok) {
        return getEntityManager().createNamedQuery("Definicjalistaplac.findByFirmaRok").setParameter("firma", firma).setParameter("rok", rok).getResultList();
    }
    
    public List<Definicjalistaplac> findByFirmaRokRodzaj(FirmaKadry firma, String rok, Rodzajlistyplac rodzajlistyplac) {
        List<Definicjalistaplac> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Definicjalistaplac.findByFirmaRokRodzaj").setParameter("firma", firma).setParameter("rok", rok).setParameter("rodzajlistplac", rodzajlistyplac).getResultList();
        } catch (Exception e){}
        return zwrot;
    }
}
