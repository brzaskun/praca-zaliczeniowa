/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
public class UprawnieniaFacade extends DAO  {

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

    public UprawnieniaFacade() {
        super(UprawnieniaUz.class);
        super.em = em;
    }
    
    public UprawnieniaUz findByNazwa(String nazwa) {
        return (UprawnieniaUz) getEntityManager().createNamedQuery("Uprawnienia.findByNazwa").setParameter("nazwa", nazwa).getSingleResult();
    }

     
   
}
