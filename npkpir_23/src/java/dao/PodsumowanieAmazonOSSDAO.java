/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Granica;
import entity.Podatnik;
import entity.PodsumowanieAmazonOSS;
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
public class PodsumowanieAmazonOSSDAO extends DAO implements Serializable{
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

    public PodsumowanieAmazonOSSDAO() {
        super(Granica.class);
        super.em = this.em;
    }

 

    public List<PodsumowanieAmazonOSS> findByRok(String rokWpisuSt) {
        return getEntityManager().createNamedQuery("Granica.findByRok").setParameter("rok", rokWpisuSt).getResultList();
    }

    public void usunmiesiacrok(Podatnik podatnikObiekt, String rokWpisuSt, String miesiacWpisu) {
        getEntityManager().createNamedQuery("PodsumowanieAmazonOSS.DeleteByPodatnikRokMc").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).setParameter("mc", miesiacWpisu).executeUpdate();
    }

    public List<PodsumowanieAmazonOSS> findByPodatnikRokMc(Podatnik podatnikObiekt, String rokWpisuSt, String miesiacWpisu) {
        return getEntityManager().createNamedQuery("PodsumowanieAmazonOSS.findByPodatnikRokMc").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).setParameter("mc", miesiacWpisu).getResultList();
    }
}
