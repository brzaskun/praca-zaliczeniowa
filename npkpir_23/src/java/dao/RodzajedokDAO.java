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
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class RodzajedokDAO extends DAO implements Serializable{

    @Inject
    private SessionFacade rodzajedokFacade;
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


    
    public Rodzajedok find(String skrot){
        return rodzajedokFacade.findRodzajedok(skrot);
    }
    
    public Rodzajedok find(String skrot, Podatnik podatnik, String rok){
        return rodzajedokFacade.findRodzajedokPodatnikRok(skrot, podatnik, rok);
    }
    
       
    public  List<Rodzajedok> findListaWspolne(Podatnik podatnik){
        try {
            return rodzajedokFacade.findListaWspolne(podatnik);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

    public List<Rodzajedok> findListaPodatnik(Podatnik podatnik, String rok) {
        try {
            return rodzajedokFacade.findListaPodatnik(podatnik, rok);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<Rodzajedok> findListaPodatnikNull(Podatnik podatnik) {
        try {
            return rodzajedokFacade.findListaPodatnikNull(podatnik);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<Rodzajedok> findListaPodatnikRO(Podatnik podatnik, String rok) {
        try {
            return rodzajedokFacade.findListaPodatnikRO(podatnik, rok);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<Rodzajedok> findListaPodatnikEdycja(Podatnik podatnik, String rok) {
        try {
            List<Rodzajedok> lista = rodzajedokFacade.findListaPodatnik(podatnik, rok);
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
