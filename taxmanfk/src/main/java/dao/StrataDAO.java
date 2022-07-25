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
public class StrataDAO  extends DAO implements Serializable {
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
        return getEntityManager().createNamedQuery("Strata.findByPodatnik").setParameter("podatnik", podatnikObiekt).getResultList();
    }

    public void usuntensamrok(Strata nowastrata) {
        Strata strata = (Strata) getEntityManager().createNamedQuery("Strata.findByPodatnikRok").setParameter("podatnik", nowastrata.getPodatnikObj()).setParameter("rok", nowastrata.getRok());
        if (strata != null) {
            remove(strata);
        }
    }

    public Strata findPodatnikRok(Podatnik podatnikObiekt, int rokWpisuSt) {
        Strata zwrot = null;
        try {
            zwrot = (Strata) getEntityManager().createNamedQuery("Strata.findByPodatnikRok").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).getSingleResult();
        } catch (Exception e) {}
        return zwrot;
    }

 
    
}
