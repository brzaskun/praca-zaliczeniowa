/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.PodatekPlatnosc;
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
public class PodatekPlatnoscDAO extends DAO implements Serializable {

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

    public PodatekPlatnoscDAO() {
        super(PodatekPlatnosc.class);
        super.em = this.em;
    }

     public List<PodatekPlatnosc> findByPodatnikRok(String podatnik, String rok) {
         List<PodatekPlatnosc> zwrot = null;
         try {
            zwrot = getEntityManager().createNamedQuery("PodatekPlatnosc.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
         } catch (Exception ex){}
         return zwrot;
    }

}
