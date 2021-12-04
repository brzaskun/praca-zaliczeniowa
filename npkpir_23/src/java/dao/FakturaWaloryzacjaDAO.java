/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FakturaWaloryzacja;
import entity.Klienci;
import entity.Podatnik;
import java.io.Serializable;
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
public class FakturaWaloryzacjaDAO extends DAO implements Serializable {

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

    public FakturaWaloryzacjaDAO() {
        super(FakturaWaloryzacja.class);
        super.em = this.em;
    }

     public FakturaWaloryzacja findByPodatnikKontrahentRok(Podatnik podatnik, Klienci kontrahent, String rok) {
        FakturaWaloryzacja zwrot = null;
        try {
            zwrot = (FakturaWaloryzacja)  getEntityManager().createNamedQuery("FakturaWaloryzacja.findbyPodatnikKontrahentRok").setParameter("podatnik", podatnik).setParameter("kontrahent", kontrahent).setParameter("rok", rok).getSingleResult();
        } catch (Exception e){}
        return zwrot;
    }

}
