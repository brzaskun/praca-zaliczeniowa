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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional

public class TabelanbpDAO extends DAO implements Serializable {
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

    public TabelanbpDAO() {
        super(Tabelanbp.class);
        super.em = this.em;
    }
        
  
    public  List<Tabelanbp> findKursyRokNBP(String rok){
        List<Tabelanbp> nowalista = null;
        try {
            String rok1 = rok+"%";
            nowalista = getEntityManager().createNamedQuery("Tabelanbp.findAllRok").setParameter("rok", rok1).getResultList();
            
        } catch (Exception e) { 
            E.e(e); 
        }
        return nowalista;
   }
    
    public  List<Tabelanbp> findKursyRokNieNBP(String rok){
        List<Tabelanbp> nowalista = null;
        try {
            String rok1 = rok+"%";
            nowalista = getEntityManager().createNamedQuery("Tabelanbp.findAllRokRecznie").setParameter("rok", rok1).getResultList();
        } catch (Exception e) { 
            E.e(e); 
        }
        return nowalista;
   }
   


    public Tabelanbp findLastWalutaMc(String nazwawaluty, String rok, String mc){
        Tabelanbp zwrot = null;
        try {
            String likedatatabeli = rok+"-"+mc+"-%";
            List<Tabelanbp> lista = getEntityManager().createNamedQuery("Tabelanbp.findBySymbolWalutyRokMc").setParameter("symbolwaluty", nazwawaluty).setParameter("likedatatabeli", likedatatabeli).getResultList();
            if (lista!=null) {
                Collections.sort(lista, new Tabelanbpcomparator());
                zwrot = lista.get(lista.size()-1);
            }
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }

    public Tabelanbp findByDateWaluta(String datatabeli, String nazwawaluty) {
         try {
            return (Tabelanbp)  getEntityManager().createNamedQuery("Tabelanbp.findByDatatabeliSymbolwaluty").setParameter("datatabeli", datatabeli).setParameter("symbolwaluty", nazwawaluty).getSingleResult();
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }

    public Tabelanbp findById(int id) {
         try {
            return (Tabelanbp)  getEntityManager().createNamedQuery("Tabelanbp.findByIdtabelanbp").setParameter("idtabelanbp", id).getSingleResult();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
 
    
    public List<Tabelanbp> findByWaluta(Waluty waluta) {
         try {
            return getEntityManager().createNamedQuery("Tabelanbp.findByWaluta").setParameter("waluta", waluta).getResultList();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

    public List<Tabelanbp> findByDateWalutaLista(String datatabeli, String nazwawaluty) {
        try {
            return  getEntityManager().createNamedQuery("Tabelanbp.findByDatatabeliSymbolwaluty").setParameter("datatabeli", datatabeli).setParameter("symbolwaluty", nazwawaluty).getResultList();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
        


    public Tabelanbp findByTabelaPLN() {
        try {
            return (Tabelanbp)  getEntityManager().createNamedQuery("Tabelanbp.findBySymbolWaluty").setParameter("symbolwaluty", "PLN").getSingleResult();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

    public Tabelanbp findOstatniaTabela(String symbolwaluty) {
        try {
            return (Tabelanbp)  getEntityManager().createNamedQuery("Tabelanbp.findBySymbolWalutyOstatnia").setParameter("symbolwaluty", symbolwaluty).setMaxResults(1).getSingleResult();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
}
