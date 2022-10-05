/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Pitpoz;
import entity.Podmiot;
import entityfk.Cechazapisu;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
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
public class PitDAO extends DAO implements Serializable {

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

    public PitDAO() {
        super(Pitpoz.class);
        super.em = this.em;
    }



    public Pitpoz find(String rok, String mc, String pod) {
        Pitpoz zwrot = null;
        try {
            zwrot = (Pitpoz)  getEntityManager().createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).getSingleResult();
        } catch (Exception e){}
        return zwrot;
    }
    
     public Pitpoz findByUdzialowiec(String rok, String mc, String pod, String udzialowiec) {
        Pitpoz zwrot = null;
        try {
            zwrot = (Pitpoz)  getEntityManager().createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik and p.udzialowiec = :udzialowiec")
                    .setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).setParameter("udzialowiec", udzialowiec).getSingleResult();
        } catch (Exception e){}
        return zwrot;
    }
    
        
    public List<Pitpoz> findList(String rok, String mc, String pod) {
        return getEntityManager().createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).getResultList();
    }
    
    
    public Pitpoz find(String rok, String mc, String pod, String udzialowiec, Cechazapisu cecha) {
        Pitpoz tmp = null;
        try {
            if (cecha==null) {
                tmp = (Pitpoz)  getEntityManager().createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik AND p.udzialowiec = :udzialowiec AND p.cechazapisu IS NULL").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).setParameter("udzialowiec", udzialowiec).getSingleResult();
            } else {
                tmp = (Pitpoz)  getEntityManager().createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik AND p.udzialowiec = :udzialowiec AND p.cechazapisu = :cecha").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).setParameter("udzialowiec", udzialowiec).setParameter("cecha", cecha).getSingleResult();
            }
        } catch (Exception e) {
            E.e(e);
        }
        return tmp;
    }
    
    
    public List<Pitpoz> findPitPod(String rok, String pod,Cechazapisu wybranacechadok) {
        List<Pitpoz> tmp = getEntityManager().createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("podatnik", pod).getResultList();
        if (wybranacechadok!=null) {
            tmp = getEntityManager().createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.podatnik = :podatnik AND p.cechazapisu = :cecha").setParameter("pkpirR", rok).setParameter("podatnik", pod).setParameter("cecha", wybranacechadok).getResultList();
        }
        return Collections.synchronizedList(tmp);
    }

    public List<Pitpoz> findByPodmiotRok(Podmiot podmiot, String rokWpisuSt) {
        List<Pitpoz> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.podmiot = :podmiot").setParameter("pkpirR", rokWpisuSt).setParameter("podmiot", podmiot).getResultList();
        } catch (Exception e) {}
        return zwrot;
    }

}
