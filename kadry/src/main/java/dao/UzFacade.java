/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Uz;
import java.util.Collections;
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
public class UzFacade extends DAO {
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

    public UzFacade() {
        super(Uz.class);
        super.em = em;
    }
    
    
   
    public Uz findUzByLogin(String login){
         Uz zwrot = null;
        try {
            zwrot = (Uz) getEntityManager().createNamedQuery("Uz.findByLogin").setParameter("login", login).getSingleResult();
        } catch (Exception e) {
            
        }
        return zwrot;
     }
    
    public List<Uz> findByUprawnienia(String uprawnienia) {
        return Collections.synchronizedList(getEntityManager().createNamedQuery("Uz.findByUprawnienia").setParameter("uprawnienia", uprawnienia).getResultList());
    }
   
     
    public List<String> findUzByUprawnienia(String uprawnienia){
         return Collections.synchronizedList(getEntityManager().createNamedQuery("Uz.findByUprawnienia").setParameter("uprawnienia", uprawnienia).getResultList());
     }

    public Uz findUzByPesel(String pesel) {
        Uz zwrot = null;
        try {
            zwrot = (Uz) getEntityManager().createNamedQuery("Uz.findByPesel").setParameter("pesel", pesel).getSingleResult();
        } catch (Exception e) {}
        return zwrot;
    }
    
}

