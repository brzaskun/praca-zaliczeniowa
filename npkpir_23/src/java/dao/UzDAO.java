/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Uz;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ejb.Stateless;import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.SessionFacade;


/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class UzDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade uzFacade;
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
         return uzFacade.findUzNP(login);
     }
    
    public List<Uz> findByUprawnienia(String uprawnienia) {
        return uzFacade.findByUprawnienia(uprawnienia);
    }
   
    public  List<Uz> findAll(){
        try {
            return uzFacade.findAll(Uz.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public List<String> findUzByUprawnienia(String uprawnienia){
         return uzFacade.getEntityManager().createNamedQuery("Uz.findByUzUprawnienia").setParameter("uprawnienia", uprawnienia).getResultList();
     }
    
}

