/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Konto;
import java.io.Serializable;
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
public class KontoDAOfk extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade kontoFacade;

    public KontoDAOfk() {
        super(Konto.class);
    }

    public KontoDAOfk(Class entityClass) {
        super(entityClass);
    }
    

    public  List<Konto> findAll(){
        try {
            return kontoFacade.findAll(Konto.class);
        } catch (Exception e) {
            return null;
        }
   }
    
   public Konto findKonto(String numer, String podatnik){
       try {
            return kontoFacade.findKonto(numer, podatnik);
        } catch (Exception e) {
            return null;
        }
   }
   
   public Konto findKonto(int id){
       try {
            return kontoFacade.findKonto(id);
        } catch (Exception e) {
            return null;
        }
   }
   
    public List<Konto> findKontoPodatnik(String podatnik){
       try {
            return kontoFacade.findKontoPodatnik(podatnik);
        } catch (Exception e) {
            return null;
        }
   }
   
   public List<Konto> findKontaOstAlityka (String podatnik) {
      try {
            return kontoFacade.findKontaOstAlityka(podatnik);
        } catch (Exception e) {
            return null;
        } 
   }
    public List<Konto> findKontaPrzyporzadkowane (String pozycja, String bilansowewynikowe) {
      try {
            return kontoFacade.findKontaPrzyporzadkowane(pozycja, bilansowewynikowe);
        } catch (Exception e) {
            return null;
        } 
   }

    public List<Konto> findKontaPotomnePodatnik(String podatnik,String macierzyste) {
        try {
            return kontoFacade.findKontaPotomnePodatnik(podatnik, macierzyste);
        } catch (Exception e) {
            return null;
        } 
    }
   
    public List<Konto> findKontaPotomne(String macierzyste, String bilansowewynikowe) {
        try {
            return kontoFacade.findKontaPotomne(macierzyste, bilansowewynikowe);
        } catch (Exception e) {
            return null;
        } 
    }
    
    public List<Konto> findKontaMaSlownik() {
        try {
            return kontoFacade.findKontaMaSlownik();
        } catch (Exception e) {
            return null;
        } 
    }

    public int resetujKolumneMapotomkow(String podatnikWpisu) {
        try {
            return kontoFacade.resetujKolumneMapotomkow(podatnikWpisu);
        } catch (Exception e) {
            return 1;
        }
    }
    
    public int resetujKolumneZablokowane(String podatnikWpisu) {
        try {
            return kontoFacade.resetujKolumneZablokowane(podatnikWpisu);
        } catch (Exception e) {
            return 1;
        }
    }
 
}
