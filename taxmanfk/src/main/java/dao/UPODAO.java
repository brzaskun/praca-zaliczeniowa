/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.UPO;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class UPODAO extends DAO implements Serializable{
    
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

    public UPODAO() {
        super(UPO.class);
        super.em = this.em;
    }

    
    public List<UPO> findPodatnikRok(WpisView wpisView) {
        try {
            List<UPO> lista = getEntityManager().createNamedQuery("UPO.findUPOPodatnikRok").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList();
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }


    public List<UPO> findUPORokMc(WpisView wpisView) {
        try {
            List<UPO> lista = getEntityManager().createNamedQuery("UPO.findUPORokMc").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList();
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<UPO> findUPORokMc(String rok, String mc) {
        try {
            List<UPO> lista = getEntityManager().createNamedQuery("UPO.findUPORokMc").setParameter("rok", rok).setParameter("mc", mc).getResultList();
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

    public List<UPO> findUPOBez200() {
        try {
            List<UPO> lista = getEntityManager().createNamedQuery("UPO.findUPOBez200").getResultList();
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

   
    
    
}
