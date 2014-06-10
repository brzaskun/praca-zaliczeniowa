/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Kontozapisy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class KontoZapisyFKDAO extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade kontozapisyFacade;

    public KontoZapisyFKDAO() {
        super(Kontozapisy.class);
    }

    public KontoZapisyFKDAO(Class entityClass) {
        super(entityClass);
    }
    
    
     public  List<Kontozapisy> findAll(){
        try {
            return kontozapisyFacade.findAll(Kontozapisy.class);
        } catch (Exception e) {
            return null;
        }
   }

    public List<Kontozapisy> findZapisy(String numer) {
        try {
            return kontozapisyFacade.findZapisyNumer(numer);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Kontozapisy> findZapisyKonto(String numerkonta) {
        try {
            return kontozapisyFacade.findZapisyKonto(numerkonta);
        } catch (Exception e) {
            return null;
        }
    }
    
     public List<Kontozapisy> findZapisyWierszID (int wierszID) {
        try {
            return kontozapisyFacade.findZapisyWierszID(wierszID);
        } catch (Exception e) {
            return null;
        }
    }
    
     public List<Kontozapisy> findZapisyKontoPodatnik(String podatnik, String numerkonta, String symbolwaluty) {
        try {
            return kontozapisyFacade.findZapisyKontoPodatnik(podatnik, numerkonta, symbolwaluty);
        } catch (Exception e) {
            return null;
        }
    }
    
     public List<Kontozapisy> findZapisyKontoBOPodatnik(String podatnik, String numerkonta) {
        try {
            return kontozapisyFacade.findZapisyKontoBOPodatnik(podatnik, numerkonta);
        } catch (Exception e) {
            return null;
        }
    }
     
    public List<Kontozapisy> findZapisyPodatnikRok(String podatnik, String rok) {
        try {
            return kontozapisyFacade.findZapisyPodatnikRok(podatnik, rok);
        } catch (Exception e) {
            return null;
        }
    }
     

    public List<Kontozapisy> findZapisyKontoMacierzyste(String pelnynumer) {
        List<Kontozapisy> zwrot = new ArrayList<>(); 
        try {
            List<Kontozapisy> worek = kontozapisyFacade.findAll(Kontozapisy.class);
            for(Kontozapisy p : worek){
                if(p.getKontoobiekt().getMacierzyste().equals(pelnynumer)){
                    zwrot.add(p);
                }
            }
            return zwrot;
        } catch (Exception e) {
            return null;
        }
    }

    
}
