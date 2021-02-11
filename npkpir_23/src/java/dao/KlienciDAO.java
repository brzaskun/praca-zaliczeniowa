/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Klienci;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
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
public class KlienciDAO extends DAO implements Serializable {

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

    public KlienciDAO() {
        super(Klienci.class);
        super.em = this.em;
    }


    
    public  List<Klienci> findAllReadOnly(){
        try {
            return findAllReadOnly(Klienci.class);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }
    
    public  List<Klienci> findAllReadOnlyContains(String pole){
        String nazwa = "%"+pole+"%";
        String nip = pole+"%";
        try {
            return getEntityManager().createNamedQuery("Klienci.findKlienciNipNazwa").setParameter("npelna", nazwa).setParameter("nip", nip).setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE).setHint(QueryHints.READ_ONLY, HintValues.TRUE).getResultList();
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }
    
    public  List<Klienci> findDoplery(int ile){
        List<Klienci> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Klienci.findDoplery").setParameter("ile", ile).getResultList();
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }
    
     public  List<Klienci> findKlienciNipSpacja(){
        List<Klienci> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Klienci.findKlienciNipSpacja").getResultList();
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }

    public  List<Klienci> findKlienciByNip(String nip){
        List<Klienci> wynik = null;
        try {
            wynik =  getEntityManager().createNamedQuery("Klienci.findByNip").setParameter("nip", nip).getResultList();
        } catch (Exception e) {
            E.e(e);
        }
        return wynik;
    }
    
    
    public  Klienci findAllReadOnlyID(String pole){
        Klienci zwrot = null;
        try {
            Integer id = null;
            if (pole!=null) {
                id = Integer.valueOf(pole);
                zwrot = (Klienci) getEntityManager().createNamedQuery("Klienci.findById").setParameter("id", id).setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE).setHint(QueryHints.READ_ONLY, HintValues.TRUE).getSingleResult();
            }
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
   }
    
    public  List<String> findNIP(){
        try {
            return getEntityManager().createNamedQuery("Klienci.findKlienciNip").getResultList();
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

    public  List<String> findNazwaPelna(String nowanazwa){
        try {
            return getEntityManager().createNamedQuery("Klienci.findByNpelna").setParameter("npelna", nowanazwa).getResultList();
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }


    public Klienci findKlientByNazwa(String nazwapelna) {
            return (Klienci)  getEntityManager().createNamedQuery("Klienci.findByNpelna").setParameter("npelna", nazwapelna).getSingleResult();
    }
    
    public Klienci findKlientByNip(String nip) {
        Klienci zwrot = null;
        try {
            zwrot = (Klienci)  getEntityManager().createNamedQuery("Klienci.findByNip").setParameter("nip", nip).getSingleResult();
        } catch (Exception e) {
        }
        return zwrot;
    }

    public List<String> findKlientByNipXX() {
        try {
            return  getEntityManager().createNamedQuery("Klienci.findByNipXX").setParameter("nip", "XX%").getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Klienci findKlientByNipImport(String nip) {
        List<Klienci> wynik = null;
        try {
            wynik =  getEntityManager().createNamedQuery("Klienci.findByNip").setParameter("nip", nip).getResultList();
            if (!wynik.isEmpty() && wynik.size()==1) {
                return wynik.get(0);
            } else if (!wynik.isEmpty() && wynik.size()>1){
                return wynik.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }


    public Klienci findKlientById(int i) {
        return (Klienci)  getEntityManager().createNamedQuery("Klienci.findById").setParameter("id", i).getSingleResult();
    }
   
   
}
