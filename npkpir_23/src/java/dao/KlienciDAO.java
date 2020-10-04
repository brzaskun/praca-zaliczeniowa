/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Klienci;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class KlienciDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade klienciFacade;

    public KlienciDAO() {
        super(Klienci.class);
    }
    
    public  List<Klienci> findAll(){
        try {
            return klienciFacade.findAll(Klienci.class);
        } catch (Exception e) {
            E.e(e); 
            return null;
        }
   }
    
    public  List<Klienci> findAllReadOnly(){
        try {
            return klienciFacade.findAllReadOnly(Klienci.class);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }
    
    public  List<Klienci> findAllReadOnlyContains(String pole){
        String nazwa = "%"+pole+"%";
        String nip = pole+"%";
        try {
            return Collections.synchronizedList(klienciFacade.getEntityManager().createNamedQuery("Klienci.findKlienciNipNazwa").setParameter("npelna", nazwa).setParameter("nip", nip).setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE).setHint(QueryHints.READ_ONLY, HintValues.TRUE).getResultList());
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }
    
    public  List<Klienci> findDoplery(){
        List<Klienci> zwrot = new ArrayList<>();
        try {
            zwrot = Collections.synchronizedList(klienciFacade.getEntityManager().createNamedQuery("Klienci.findKlienciNipNazwa").setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE).setHint(QueryHints.READ_ONLY, HintValues.TRUE).getResultList());
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }
    
    public  Klienci findAllReadOnlyID(String pole){
        Klienci zwrot = null;
        try {
            Integer id = null;
            if (pole!=null) {
                id = Integer.valueOf(pole);
                zwrot = (Klienci) klienciFacade.getEntityManager().createNamedQuery("Klienci.findById").setParameter("id", id).setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE).setHint(QueryHints.READ_ONLY, HintValues.TRUE).getSingleResult();
            }
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
   }
    
    
    public  List<String> findNIP(){
        try {
            return klienciFacade.findKlienciNIP();
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public  List<String> findNazwaPelna(String nowanazwa){
        try {
            return klienciFacade.findNazwaPelna(nowanazwa);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public Klienci findKlientByNazwa(String nazwapelna) {
            return klienciFacade.findKlientByNazwa(nazwapelna);
    }
    
    public Klienci findKlientByNip(String nip) {
        return klienciFacade.findKlientByNip(nip);
    }
    
    public List<String> findKlientByNipXX() {
        return klienciFacade.findKlientByNipXX();
    }
    
    public Klienci findKlientByNipImport(String nip) {
        return klienciFacade.findKlientByNipImport(nip);
    }

    public Klienci findKlientById(int i) {
        return klienciFacade.findKlientById(i);
    }
   
   
}
