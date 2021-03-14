/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import data.Data;
import embeddable.Mce;
import entity.Amodok;
import error.E;
import java.io.Serializable;
import java.util.Iterator;
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
public class AmoDokDAO extends DAO implements Serializable {

    
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

    public AmoDokDAO() {
        super(Amodok.class);
        super.em = this.em;
    }

    
     public  Amodok findMR(String pod, Integer rok, String mc){
        try {
            Integer miesiac = Integer.parseInt(mc);
            return (Amodok)  getEntityManager().createNamedQuery("Amodok.findByPMR").setParameter("podatnik", pod).setParameter("rok", rok).setParameter("mc", miesiac).getSingleResult();
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }


    public void destroy(String podatnik){
        List<Amodok> lista = getEntityManager().createNamedQuery("Amodok.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
        Iterator it;
        it = lista.iterator();
        while(it.hasNext()){
            Amodok tmp = (Amodok) it.next();
            remove(tmp);
        }
    }

    public void usun(String podatnik, int rok, int mc){
        try {
           getEntityManager().createNamedQuery("Amodok.usunAmoDokByMcRok").setParameter("podatnik",podatnik).setParameter("rok", rok).setParameter("mc", mc).executeUpdate();
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    //Usuwa wszystkie pozniejsze
    public void destroy(String podatnik, int rok, int mc){
        List<Amodok> lista = getEntityManager().createNamedQuery("Amodok.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
        for(Amodok tmp : lista){
            int wynikporownywania = Data.compare(tmp.getAmodokPK().getRok(), tmp.getAmodokPK().getMc(), rok, mc);
            if(wynikporownywania >= 0) {
                remove(tmp);
            }
        }
    }

    public List<Amodok> amodokklient(String podatnik){
        return getEntityManager().createNamedQuery("Amodok.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
    }

    public List<Amodok> amodokKlientRok(String podatnik, String rok){
        return getEntityManager().createNamedQuery("Amodok.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).getResultList();
    }

    public Amodok amodokBiezacy(String podatnik, String mc, Integer rok){
        return (Amodok)  getEntityManager().createNamedQuery("Amodok.findByPodatnikMcRok").setParameter("podatnik", podatnik).setParameter("mc", Mce.getMiesiacToNumber().get(mc)).setParameter("rok", rok).getSingleResult();
    }
}

