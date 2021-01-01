/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Uz;
import java.util.Collections;
import java.util.List;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author Osito
 */
@Named
public class UzDAO  extends AbstractFacade<Uz> {
    @PersistenceContext(unitName = "kadryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UzDAO() {
        super(Uz.class);
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
    
}

