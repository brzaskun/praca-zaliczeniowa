/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Angaz;
import entity.Dokumenty;
import entity.FirmaKadry;
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
public class DokumentyFacade extends DAO implements Serializable {
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

    public DokumentyFacade() {
        super(Dokumenty.class);
        super.em = em;
    }
       
    public List<Dokumenty> findByAngaz(Angaz angaz) {
        List<Dokumenty> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Dokumenty.findByAngaz").setParameter("angaz", angaz).getResultList();
        return zwrot;
    }
    
    public List<Dokumenty> findByFirma(FirmaKadry firma) {
        List<Dokumenty> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Dokumenty.findByFirma").setParameter("firma", firma).getResultList();
        } catch (Exception e) {}
        return zwrot;
    }
}
