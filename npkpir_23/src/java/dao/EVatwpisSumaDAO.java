/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.EVatwpisSuma;
import entity.Podatnik;
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
public class EVatwpisSumaDAO extends DAO implements Serializable {

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

    public EVatwpisSumaDAO() {
        super(EVatwpisSuma.class);
        super.em = this.em;
    }

    public void usunPodatnikRokMc(Podatnik podatnik, String rok, String mc) {
        try {
           getEntityManager().createNamedQuery("EVatwpisSuma.usunByMcRok").setParameter("podatnik",podatnik).setParameter("rok", rok).setParameter("mc", mc).executeUpdate();
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public List<EVatwpisSuma> findPodatnikRok(Podatnik podatnik, String rok) {
        List<EVatwpisSuma> zwrot = null;
        try {
           zwrot = getEntityManager().createNamedQuery("EVatwpisSuma.findByPodatnikRok").setParameter("podatnik",podatnik).setParameter("rok", rok).getResultList();
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
}
