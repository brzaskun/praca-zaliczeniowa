/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entity.Rodzajedok;
import error.E;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class RodzajedokDAO extends DAO implements Serializable{

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

    public RodzajedokDAO() {
        super(Rodzajedok.class);
        super.em = this.em;
    }


    public Rodzajedok find(String skrot) {
        Rodzajedok wynik = null;
        wynik = (Rodzajedok)  getEntityManager().createNamedQuery("Rodzajedok.findBySkrot").setParameter("skrot", skrot).getSingleResult();
        return wynik;
    }
   
    public Rodzajedok find(String skrot, Podatnik podatnik, String rok){
        Rodzajedok wynik = null;
        try {
            wynik = (Rodzajedok)  getEntityManager().createNamedQuery("Rodzajedok.findBySkrotPodatnikRok").setParameter("skrot", skrot).setParameter("podatnik", podatnik).setParameter("rok", rok).getSingleResult();
            
        } catch (Exception e) {
            E.e(e);

        }
        return wynik;
    }
    
       
    public  List<Rodzajedok> findListaWspolne(Podatnik podatnik){
        List<Rodzajedok> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Rodzajedok.findByListaWspolna").setParameter("podatnik", podatnik).getResultList();
        } catch (Exception e) { E.e(e); 
        }
        return zwrot;
   }

    
    
    public List<Rodzajedok> findListaPodatnik(Podatnik podatnik, String rok) {
        List<Rodzajedok> zwrot = null;
        try {
            zwrot =  getEntityManager().createNamedQuery("Rodzajedok.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
        } catch (Exception e) { E.e(e); 
        }
        return zwrot;
    }

    public List<Rodzajedok> findListaPodatnikNull(Podatnik podatnik) {
        List<Rodzajedok> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Rodzajedok.findByPodatnikNull").setParameter("podatnik", podatnik).getResultList();
        } catch (Exception e) { E.e(e); 
        }
        return zwrot;
    }
    
    public List<Rodzajedok> findListaPodatnikRO(Podatnik podatnik, String rok) {
        try {
            return getEntityManager().createNamedQuery("Rodzajedok.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE).getResultList();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<Rodzajedok> findListaPodatnikEdycja(Podatnik podatnik, String rok) {
        try {
            List<Rodzajedok> lista = getEntityManager().createNamedQuery("Rodzajedok.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
            if (lista != null) {
                for (Iterator<Rodzajedok> it = lista.iterator(); it.hasNext();) {
                    Rodzajedok p = it.next();
                    if (p.isNiepokazuj()) {
                        //it.remove();
                    }
                }
            }
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
}
