/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Pismoadmin;
import error.E;
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
public class PismoadminFacade extends DAO  implements Serializable {
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

    public PismoadminFacade() {
        super(Pismoadmin.class);
        super.em = em;
    }
  
     
    public  List<Pismoadmin> findBiezace(){
        try {
            List<Pismoadmin> lista = getEntityManager().createNamedQuery("Pismoadmin.findByNOTStatus").setParameter("status", "archiwalna").getResultList();
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

    public List<Pismoadmin> findNowe() {
        try {
            List<Pismoadmin> lista = getEntityManager().createNamedQuery("Pismoadmin.findByStatus").setParameter("status", "wysłana").getResultList();
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
}
