/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.KlientJPK;
import entity.Podatnik;
import java.io.Serializable;
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
public class KlientJPKDAO  extends DAO implements Serializable {
     @Inject
    private SessionFacade sessionFacade;
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

    public KlientJPKDAO() {
        super(KlientJPK.class);
        super.em = this.em;
    }

    private String test;
   

    
    public void deleteByPodRokMc (Podatnik podatnik, String rok, String mc) {
        sessionFacade.klientJPKdeleteByPodRokMc(podatnik, rok, mc);
    }

    public List<KlientJPK> findbyKlientRokMc(Podatnik podatnikObiekt, String rokWpisuSt, String miesiacWpisu) {
        return sessionFacade.getEntityManager().createNamedQuery("KlientJPK.findByPodRokMc").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).setParameter("mc", miesiacWpisu).getResultList();
    }
    
    
    
}
