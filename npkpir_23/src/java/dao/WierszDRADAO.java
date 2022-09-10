/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entity.WierszDRA;
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
//pomaga przenosci opisy bo inaczej nie chca sie zachowac. scopy nie pasuja
public class WierszDRADAO extends DAO implements Serializable {

    
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

    public WierszDRADAO() {
        super(WierszDRA.class);
        super.em = this.em;
    }

    
    public List<WierszDRA> findByRok(String rok){
        return getEntityManager().createNamedQuery("WierszDRA.findByRok").setParameter("rok", rok).getResultList();
    }
    
    public List<WierszDRA> findByRokPodatnik(String rok, Podatnik podatnik){
        return getEntityManager().createNamedQuery("WierszDRA.findByRokPodatnik").setParameter("rok", rok).setParameter("podatnik", podatnik).getResultList();
    }
    
    public List<WierszDRA> findByRokMc(String rok, String mc){
        return getEntityManager().createNamedQuery("WierszDRA.findByRokMc").setParameter("rok", rok).setParameter("mc", mc).getResultList();
    }
    
   
    
    
}
