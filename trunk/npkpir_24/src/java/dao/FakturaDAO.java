/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Faktura;
import entity.Klienci;
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
public class FakturaDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade fakturaFacade;

    public FakturaDAO() {
        super(Faktura.class);
    }
    
    public  List<Faktura> findAll(){
        try {
            System.out.println("Pobieram FakturaDAO");
            return fakturaFacade.findAll(Faktura.class);
        } catch (Exception e) {
            return null;
        }
   }
   
   public void dodaj(Faktura faktura){
        fakturaFacade.create(faktura);
   }

    public List<Faktura> findbyKontrahent_nip(String kontrahent_nip, String podatnik) {
         try {
            System.out.println("Pobieram FakturaDAO wg NIP");
            return fakturaFacade.findByKontrahent_nip(kontrahent_nip, podatnik);
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Faktura> findbyKontrahentNipRok(String kontrahentnip, String podatnik, String rok) {
         try {
            System.out.println("Pobieram FakturaDAO wg NIP i rok");
            return fakturaFacade.findByKontrahentNipRok(kontrahentnip, podatnik, rok);
        } catch (Exception e) {
            return null;
        }
    }
    
     public List<Faktura> findbyPodatnik(String podatnik) {
         try {
            System.out.println("Pobieram FakturaDAO wg nazwy Podatnika");
            return fakturaFacade.findByPodatnik(podatnik);
        } catch (Exception e) {
            return null;
        }
    }
     
      public List<Faktura> findbyPodatnikRok(String podatnik, String rok) {
         try {
            System.out.println("Pobieram FakturaDAO wg nazwy Podatnika i Rok");
            return fakturaFacade.findByPodatnikRok(podatnik, rok);
        } catch (Exception e) {
            return null;
        }
    }
      public List<Faktura> findbyPodatnikRokMc(String podatnik, String rok, String mc) {
         try {
            System.out.println("Pobieram FakturaDAO wg nazwy Podatnika i Rok i Mc");
            return fakturaFacade.findByPodatnikRokMc(podatnik, rok, mc);
        } catch (Exception e) {
            return null;
        }
    }
}
