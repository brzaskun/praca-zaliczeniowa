/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Konto;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class KontoDAOfk extends DAO implements Serializable{
    @Inject
    private SessionFacade kontoFacade;

    public KontoDAOfk() {
        super(Konto.class);
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
   
   public List<Konto> findKontaOstAlityka () {
      try {
            return kontoFacade.findKontaOstAlityka();
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

    public List<Konto> findKontaPotomne(String macierzyste) {
        try {
            return kontoFacade.findKontaPotomne(macierzyste);
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
 
}
