/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entity.Pozycjenafakturze;
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
public class PozycjenafakturzeDAO  extends DAO implements Serializable {
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

    public PozycjenafakturzeDAO() {
        super(Pozycjenafakturze.class);
        super.em = this.em;
    }

  
    /**
     *
     * @param podatnik
     * @return
     */
    public List<Pozycjenafakturze> findFakturyPodatnik(Podatnik podatnik){
        List<Pozycjenafakturze> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Pozycjenafakturze.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }

    public Pozycjenafakturze findPozycjePodatnikCo(Podatnik podatnik, String co) {
        Pozycjenafakturze zwrot = null;
        try {
            zwrot = (Pozycjenafakturze) getEntityManager().createNamedQuery("Pozycjenafakturze.findByNazwaCo").setParameter("podatnik", podatnik).setParameter("nazwa", co).getSingleResult();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
}
