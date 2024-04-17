/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entity.Remanent;
import java.io.Serializable;
import java.util.ArrayList;
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
public class RemanentDAO  extends DAO implements Serializable {
    

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

    public RemanentDAO() {
        super(Remanent.class);
        super.em = this.em;
    }

    public List<Remanent> findPodatnik(Podatnik podatnik) {
        List<Remanent> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Remanent.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
        } catch (Exception ex) {
            
        }
        return zwrot;
    }
    
    public Remanent findPodatnikRok(Podatnik podatnik, String rok) {
        Remanent zwrot = null;
        try {
            zwrot = (Remanent) getEntityManager().createNamedQuery("Remanent.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getSingleResult();
        } catch (Exception ex) {
            
        }
        return zwrot;
    }
       
    
}
