/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Sesja;
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
public class SesjaDAO extends DAO implements Serializable {
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

    public SesjaDAO() {
        super(Sesja.class);
        super.em = this.em;
    } 
   

    public SesjaDAO(Class entityClass) {
        super(entityClass);
    }
    
 
   public  List<Sesja> findUser(String user){
       List<Sesja> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Sesja.findByUzytkownik").setParameter("uzytkownik", user).getResultList();
        } catch (Exception e) { E.e(e); 
        }
        return zwrot;
   }


    public Sesja find(String nrsesji) {
        Sesja zwrot = null;
        try {
            zwrot = (Sesja)  getEntityManager().createNamedQuery("Sesja.findByNrsesji").setParameter("nrsesji", nrsesji).getSingleResult();
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }

    public List<Sesja> findSesjaZalogowani() {
        List<Sesja> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Sesja.findByZalogowani").getResultList();
        } catch (Exception e) { E.e(e); 
            
        }
        return zwrot;
    }
}
