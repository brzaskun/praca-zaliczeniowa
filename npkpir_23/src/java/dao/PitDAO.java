/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Pitpoz;
import entityfk.Cechazapisu;
import error.E;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class PitDAO extends DAO implements Serializable {

    @Inject private SessionFacade pitpozFacade;
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
        return pitpozFacade.findPitpoz(rok, mc, pod);
    }
    
    public List<Pitpoz> findList(String rok, String mc, String pod) {
        return pitpozFacade.findPitpozLista(rok, mc, pod);
    }
    
    public Pitpoz find(String rok, String mc, String pod, String udzialowiec, Cechazapisu cecha) {
        try {
            return pitpozFacade.findPitpoz(rok, mc, pod, udzialowiec, cecha);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Pitpoz> findPitPod(String rok, String pod,Cechazapisu wybranacechadok) {
        List<Pitpoz> tmp = pitpozFacade.getEntityManager().createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("podatnik", pod).getResultList();
        if (wybranacechadok!=null) {
            tmp = pitpozFacade.getEntityManager().createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.podatnik = :podatnik AND p.cechazapisu = :cecha").setParameter("pkpirR", rok).setParameter("podatnik", pod).setParameter("cecha", wybranacechadok).getResultList();
        }
        return Collections.synchronizedList(tmp);
    }

}
