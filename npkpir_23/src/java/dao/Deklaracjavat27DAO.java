/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Deklaracjavat27;
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
public class Deklaracjavat27DAO extends DAO implements Serializable{

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

    public Deklaracjavat27DAO() {
        super(Deklaracjavat27.class);
        super.em = this.em;
    }


    public List<Deklaracjavat27> findbyPodatnikRok(WpisView wpisView) {
        try {
            return getEntityManager().createNamedQuery("Deklaracjavat27.findByPodatnikRok").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList();
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }

    public Deklaracjavat27 findbyPodatnikRokMc(WpisView wpisView) {
        Deklaracjavat27 zwrot = null;
        try {
            List<Deklaracjavat27> lista = getEntityManager().createNamedQuery("Deklaracjavat27.findByPodatnikRokMc").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("miesiac", wpisView.getMiesiacWpisu()).getResultList();
            int max = -1;
            for (Deklaracjavat27 p : lista) {
                if (p.getNrkolejny() > max) {
                    max = p.getNrkolejny();
                    zwrot = p;
                }
            }
        } catch (Exception e) {
        }
        return zwrot;
    }

    public void usundeklaracje27(WpisView wpisView) {
        getEntityManager().createNamedQuery("Deklaracjavat27.usundeklaracje27").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("miesiac", wpisView.getMiesiacWpisu()).executeUpdate();
    }

    public List<Deklaracjavat27> findDeklaracjewysylka(WpisView wpisView) {
         return getEntityManager().createNamedQuery("Deklaracjavat27.findByRokMc").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("miesiac", wpisView.getMiesiacWpisu()).getResultList();
    }
    
    
    
}
