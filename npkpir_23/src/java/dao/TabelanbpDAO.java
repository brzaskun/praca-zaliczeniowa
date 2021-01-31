/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import comparator.Tabelanbpcomparator;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import error.E;
import java.io.Serializable;
import java.util.Collections;
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

public class TabelanbpDAO extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade tabelanbpFacade;
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

    public TabelanbpDAO() {
        super(Tabelanbp.class);
        super.em = this.em;
    }
        
  
    public  List<Tabelanbp> findKursyRokNBP(String rok){
        try {
            String rok1 = rok+"%";
            List<Tabelanbp> nowalista = tabelanbpFacade.getEntityManager().createNamedQuery("Tabelanbp.findAllRok").setParameter("rok", rok1).getResultList();
            return nowalista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public  List<Tabelanbp> findKursyRokNieNBP(String rok){
        try {
            String rok1 = rok+"%";
            List<Tabelanbp> nowalista = tabelanbpFacade.getEntityManager().createNamedQuery("Tabelanbp.findAllRokRecznie").setParameter("rok", rok1).getResultList();
            return nowalista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
   
    public List<Tabelanbp> findLast(){
        try {
            return tabelanbpFacade.findXLast(Tabelanbp.class,1);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public Tabelanbp findLastWalutaMc(String nazwawaluty, String rok, String mc){
        try {
            Tabelanbp zwrot = null;
            List<Tabelanbp> lista = tabelanbpFacade.findByWalutaMcRok(nazwawaluty, mc, rok);
            if (lista!=null) {
                Collections.sort(lista, new Tabelanbpcomparator());
                zwrot = lista.get(lista.size()-1);
            }
            return zwrot;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

    public Tabelanbp findByDateWaluta(String datatabeli, String nazwawaluty) {
         try {
            return tabelanbpFacade.findByDateWaluta(datatabeli, nazwawaluty);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }
    
    public Tabelanbp findById(int id) {
         try {
            return tabelanbpFacade.findById(id);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<Tabelanbp> findByWaluta(Waluty waluta) {
         try {
            return tabelanbpFacade.findByWaluta(waluta);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<Tabelanbp> findByDateWalutaLista(String datatabeli, String nazwawaluty) {
        try {
            return tabelanbpFacade.findByDateWalutaLista(datatabeli, nazwawaluty);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public Tabelanbp findByTabelaPLN() {
        try {
            return tabelanbpFacade.findTabelaPLN();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

    public Tabelanbp findOstatniaTabela(String symbolwaluty) {
        try {
            return tabelanbpFacade.findOstatniaTabela(symbolwaluty);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
}
