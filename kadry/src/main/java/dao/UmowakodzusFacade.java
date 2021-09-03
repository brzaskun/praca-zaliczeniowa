/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Umowakodzus;
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
public class UmowakodzusFacade extends DAO  {

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

    public UmowakodzusFacade() {
        super(Umowakodzus.class);
        super.em = em;
    }
     public Umowakodzus findUmowakodzusByKod(String kod){
         Umowakodzus zwrot = null;
        try {
            zwrot = (Umowakodzus) getEntityManager().createNamedQuery("Umowakodzus.findByKod").setParameter("kod", kod).getSingleResult();
        } catch (Exception e) {
            
        }
        return zwrot;
     }
     
     public Umowakodzus findUmowakodzusByOpis(String opis){
         Umowakodzus zwrot = null;
        try {
            zwrot = (Umowakodzus) getEntityManager().createNamedQuery("Umowakodzus.findByOpis").setParameter("opis", opis).getSingleResult();
        } catch (Exception e) {
            
        }
        return zwrot;
     }
     
     public Umowakodzus findUmowakodzusById(int id){
         Umowakodzus zwrot = null;
        try {
            zwrot = (Umowakodzus) getEntityManager().createNamedQuery("Umowakodzus.findById").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            
        }
        return zwrot;
     }
     
     
}
