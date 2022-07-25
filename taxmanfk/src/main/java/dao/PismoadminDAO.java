/*
 * To change this template, choose Tools | Templates
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
public class PismoadminDAO extends DAO implements Serializable{
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

    public PismoadminDAO() {
        super(Pismoadmin.class);
        super.em = this.em;
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
