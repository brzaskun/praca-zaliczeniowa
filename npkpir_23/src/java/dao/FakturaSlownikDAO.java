/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Fakturaslownik;
import entity.Podatnik;
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
public class FakturaSlownikDAO extends DAO implements Serializable{
     private static final long serialVersionUID = 1L;

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

    public FakturaSlownikDAO() {
        super(Fakturaslownik.class);
        super.em = this.em;
    }

 

    public List<Fakturaslownik> findByPodatnik(Podatnik podatnik) {
        return getEntityManager().createNamedQuery("Fakturaslownik.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
    }
}
