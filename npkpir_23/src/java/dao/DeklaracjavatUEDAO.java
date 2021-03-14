/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.DeklaracjavatUE;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import session.SessionFacade;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class DeklaracjavatUEDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade sessionFacade;
    //tablica wciagnieta z bazy danych
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

    public DeklaracjavatUEDAO() {
        super(DeklaracjavatUE.class);
        super.em = this.em;
    }


    public List<DeklaracjavatUE> findbyPodatnikRok(WpisView wpisView) {
        try {
            return getEntityManager().createNamedQuery("DeklaracjavatUE.findByPodatnikRok").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList();
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }


    public DeklaracjavatUE findbyPodatnikRokMc(WpisView wpisView) {
        DeklaracjavatUE zwrot = null;
        try {
            List<DeklaracjavatUE> lista = getEntityManager().createNamedQuery("DeklaracjavatUE.findByPodatnikRokMc").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("miesiac", wpisView.getMiesiacWpisu()).getResultList();
            int max = -1;
            for (DeklaracjavatUE p : lista) {
                if (p.getNrkolejny() > max) {
                    max = p.getNrkolejny();
                    zwrot = p;
                }
            }
        } catch (Exception e) {}
        return zwrot;
    }

    
    public void usundeklaracjeUE(WpisView wpisView) {
        getEntityManager().createNamedQuery("DeklaracjavatUE.usundeklaracjeUE").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("miesiac", wpisView.getMiesiacWpisu()).executeUpdate();
    }
    
    
    public List<DeklaracjavatUE> findDeklaracjewysylka(WpisView wpisView) {
         return getEntityManager().createNamedQuery("DeklaracjavatUE.findByRokMc").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("miesiac", wpisView.getMiesiacWpisu()).getResultList();
    }
    
}
