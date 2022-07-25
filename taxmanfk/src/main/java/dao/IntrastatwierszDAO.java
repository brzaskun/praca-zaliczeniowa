/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entityfk.Intrastatwiersz;
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
public class IntrastatwierszDAO extends DAO implements Serializable {
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

    public IntrastatwierszDAO() {
        super(Intrastatwiersz.class);
        super.em = this.em;
    }

   public void deleteByPodRokMc (Podatnik podatnik, String rok, String mc) {
        getEntityManager().createNamedQuery("Intrastatwiersz.deletePodRokMc").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("mc", mc).executeUpdate();
    }

    public List<Intrastatwiersz> findbyKlientRokMc(Podatnik podatnikObiekt, String rokWpisuSt, String miesiacWpisu) {
        return getEntityManager().createNamedQuery("Intrastatwiersz.findByPodRokMc").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).setParameter("mc", miesiacWpisu).getResultList();
    }
    
      

}
