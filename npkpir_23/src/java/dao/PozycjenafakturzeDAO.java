/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
    public List<Pozycjenafakturze> findFakturyPodatnik(String podatnik){
        return getEntityManager().createNamedQuery("Pozycjenafakturze.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
    }
}
