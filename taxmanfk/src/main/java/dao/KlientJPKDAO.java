/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.KlientJPK;
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
public class KlientJPKDAO  extends DAO implements Serializable {
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

    public KlientJPKDAO() {
        super(KlientJPK.class);
        super.em = this.em;
    }

    private String test;
   

    public void deleteByPodRokMc (Podatnik podatnik, String rok, String mc) {
        getEntityManager().createNamedQuery("KlientJPK.deletePodRokMc").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("mc", mc).executeUpdate();
    }
    public void deleteByPodRokMcAmazon (Podatnik podatnik, String rok, String mc, int amazontax0additional1) {
        getEntityManager().createNamedQuery("KlientJPK.deletePodRokMcAmazon").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("mc", mc).setParameter("amazontax0additional1", amazontax0additional1).executeUpdate();
    }

    public List<KlientJPK> findbyKlientRokMc(Podatnik podatnikObiekt, String rokWpisuSt, String miesiacWpisu) {
        return getEntityManager().createNamedQuery("KlientJPK.findByPodRokMc").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).setParameter("mc", miesiacWpisu).getResultList();
    }
    
    public List<KlientJPK> findbyKlientRokMcAmazon(Podatnik podatnikObiekt, String rokWpisuSt, String miesiacWpisu, int amazontax0additional1) {
        return getEntityManager().createNamedQuery("KlientJPK.findByPodRokMcAmazon").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).setParameter("mc", miesiacWpisu).setParameter("amazontax0additional1", amazontax0additional1).getResultList();
    }
    
    
    
}
