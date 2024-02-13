/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FirmaKadry;
import entity.Kadryfakturapozycja;
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
public class KadryfakturapozycjaFacade extends DAO  {

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

    public KadryfakturapozycjaFacade() {
        super(Kadryfakturapozycja.class);
        super.em = em;
    }

    public List<Kadryfakturapozycja> findByRok(String rok) {
        List<Kadryfakturapozycja> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Kadryfakturapozycja.findByRok").setParameter("rok", rok).getResultList();
        } catch (Exception e){}
        return zwrot;
    }
   
}
