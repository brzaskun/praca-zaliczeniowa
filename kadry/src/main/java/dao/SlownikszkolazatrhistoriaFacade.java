/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Slownikszkolazatrhistoria;
import entity.UprawnieniaUz;
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
public class SlownikszkolazatrhistoriaFacade extends DAO  {

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

    public SlownikszkolazatrhistoriaFacade() {
        super(Slownikszkolazatrhistoria.class);
        super.em = em;
    }
    
    public UprawnieniaUz findBySymbol(String symbol) {
        return (UprawnieniaUz) getEntityManager().createNamedQuery("Slownikszkolazatrhistoria.findBySymbol").setParameter("symbol", symbol).getSingleResult();
    }

     
   
}
