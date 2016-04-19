/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package em;

import error.E;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Osito
 */
public class Em {

    private static EntityManager em;
    
   
    
    
    public static void save(EntityManager em, Object t) {
        try {
            // begin transaction 
            em.getTransaction().begin();
            if (!em.contains(t)) {
                // persist object - add to entity manager
                em.persist(t);
                // flush em - save to DB
                em.flush();
            }
            // commit transaction at all
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            E.e(e);
        }
    }
    
    public static void saveList(EntityManager em, List<Object> t) {
        try {
            // begin transaction 
            em.getTransaction().begin();
            for (Object p : t) {
                if (!em.contains(p)) {
                    // persist object - add to entity manager
                    em.persist(p);
                }
            }
            // flush em - save to DB
            em.flush();
            // commit transaction at all
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            E.e(e);
        }
    }

    public static EntityManager getEm() {
        if (em == null) {
            EntityManagerFactory emfH2 = javax.persistence.Persistence.createEntityManagerFactory("fkKonto1");
            em = emfH2.createEntityManager();
        }
        return em;
    }
    
}
