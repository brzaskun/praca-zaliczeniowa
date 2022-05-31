/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Ryczpoz;
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
public class RyczDAO extends DAO implements Serializable {

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

    public RyczDAO() {
        super(Ryczpoz.class);
        super.em = this.em;
    }


    public Ryczpoz find(String rok, String mc, String pod) {
        Ryczpoz zwrot = null;
        try {
            zwrot = (Ryczpoz)  getEntityManager().createQuery("SELECT p FROM Ryczpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).getSingleResult();
        } catch (Exception e){}
        return zwrot;
    }
    
    public Ryczpoz findByUdzialowiec(String rok, String mc, String pod, String udzialowiec) {
        Ryczpoz zwrot = null;
        try {
            zwrot = (Ryczpoz)  getEntityManager().createQuery("SELECT p FROM Ryczpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik AND p.udzialowiec = :udzialowiec").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).setParameter("udzialowiec", udzialowiec).getSingleResult();
        } catch (Exception e){}
        return zwrot;
    }
    
    public List<Ryczpoz> findRyczPod(String rok, String pod) {
        List<Ryczpoz> zwrot = null;
        try {
            zwrot = getEntityManager().createQuery("SELECT p FROM Ryczpoz p WHERE p.pkpirR = :pkpirR AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("podatnik", pod).getResultList();
        } catch (Exception e){}
        return zwrot;
    }
    
    public List<Ryczpoz> findList(String rok, String mc, String pod) {
        return getEntityManager().createQuery("SELECT p FROM Ryczpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).getResultList();
    }
    


}
