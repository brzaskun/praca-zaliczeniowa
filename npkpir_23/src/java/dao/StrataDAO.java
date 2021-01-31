/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entity.Strata;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.SessionFacade;
/**
 *
 * @author Osito
 */
public class StrataDAO  extends DAO implements Serializable {
    @Inject private SessionFacade sessionFacade;
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

    public StrataDAO() {
        super(Strata.class);
        super.em = this.em;
    }

    public List<Strata> findPodatnik(Podatnik podatnikObiekt) {
        return sessionFacade.getEntityManager().createNamedQuery("Strata.findByPodatnik").setParameter("podatnik", podatnikObiekt).getResultList();
    }

    public void usuntensamrok(Strata nowastrata) {
        Strata strata = (Strata) sessionFacade.getEntityManager().createNamedQuery("Strata.findByPodatnikRok").setParameter("podatnik", nowastrata.getPodatnikObj()).setParameter("rok", nowastrata.getRok());
        if (strata != null) {
            remove(strata);
        }
    }
    
}
