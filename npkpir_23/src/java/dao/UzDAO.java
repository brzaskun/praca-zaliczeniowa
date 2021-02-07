/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Uz;
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
public class UzDAO extends DAO implements Serializable{
    @PersistenceContext(unitName = "npkpir_22PU")
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

    public UzDAO() {
        super(Uz.class);
        super.em = this.em;
    }

 
    public Uz findUzByLogin(String login){
        Uz zwrot = null;
        try {
            zwrot = (Uz)  getEntityManager().createNamedQuery("Uz.findByLogin").setParameter("login", login).getSingleResult();
        } catch (Exception e) {
            
        }
        return zwrot;
     }

    public List<Uz> findByUprawnienia(String uprawnienia) {
        List<Uz> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Uz.findByUprawnienia").setParameter("uprawnienia", uprawnienia).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
   
  
    public List<String> findUzByUprawnienia(String uprawnienia){
        List<String> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Uz.findByUzUprawnienia").setParameter("uprawnienia", uprawnienia).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
     }
    
}

