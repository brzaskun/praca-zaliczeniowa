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
            System.out.println("Pobieram KontoDAO");
            return kontoFacade.findAll(Konto.class);
        } catch (Exception e) {
            return null;
        }
   }
    
   public Konto findKonto(String numer){
       try {
            System.out.println("Szukam Konto "+numer);
            return kontoFacade.findKonto(numer);
        } catch (Exception e) {
            return null;
        }
   }
   
   public List<Konto> findKontaOstAlityka () {
      try {
            System.out.println("Pobieram KontoDAO ostatnie analityki");
            return kontoFacade.findKontaOstAlityka();
        } catch (Exception e) {
            return null;
        } 
   }

    public List<Konto> findKontaPotomne(String macierzyste) {
        try {
            System.out.println("Pobieram KontoDAO ostatnie analityki");
            return kontoFacade.findKontaPotomne(macierzyste);
        } catch (Exception e) {
            return null;
        } 
    }
   
 
}
