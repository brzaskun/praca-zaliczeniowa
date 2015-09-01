/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Faktura;
import error.E;
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
            return fakturaFacade.findAll(Faktura.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
   
   public Faktura findFaktura(Faktura f) {
       return fakturaFacade.findfaktura(f);
   }
   public void dodaj(Faktura faktura){
        fakturaFacade.create(faktura);
   }

    public List<Faktura> findbyKontrahent_nip(String kontrahent_nip, String podatnik) {
         try {
            return fakturaFacade.findByKontrahent_nip(kontrahent_nip, podatnik);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<Faktura> findbyKontrahentNipRok(String kontrahentnip, String podatnik, String rok) {
         try {
            return fakturaFacade.findByKontrahentNipRok(kontrahentnip, podatnik, rok);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<Faktura> findFakturyByRok(String rok) {
         try {
            return fakturaFacade.findFakturyByRok(rok);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
       
    public List<Faktura> findFakturyByRokPodatnik(String rok, String wystawcanazwa) {
         try {
            return fakturaFacade.findFakturyByRokPodatnik(rok, wystawcanazwa);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public Long liczFakturyByRokPodatnik(String rok, String wystawcanazwa) {
         try {
            return fakturaFacade.liczFakturyByRokPodatnik(rok, wystawcanazwa);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public Faktura findOstatniaFakturaByRokPodatnik(String rok, String wystawcanazwa) {
         try {
            return fakturaFacade.findOstatniaFakturaByRokPodatnik(rok, wystawcanazwa);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<Faktura> findbyPodatnik(String podatnik) {
         try {
            return fakturaFacade.findByPodatnik(podatnik);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    public Faktura findbyNumerPodatnik(String numerkolejny, String podatnik) {
         try {
            return fakturaFacade.findByNumerPodatnik(numerkolejny, podatnik);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
     
      public List<Faktura> findbyPodatnikRok(String podatnik, String rok) {
         try {
            return fakturaFacade.findByPodatnikRok(podatnik, rok);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<Faktura> findbyPodatnikRokMc(String podatnik, String rok, String mc) {
         try {
            return fakturaFacade.findByPodatnikRokMc(podatnik, rok, mc);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<Faktura> findbyPodatnikRokMcPlatnosci(String podatnik, String rok, String mc, boolean niezaplacone0zaplacone1) {
         try {
            return fakturaFacade.findByPodatnikRokMcPlatnosci(podatnik, rok, mc, niezaplacone0zaplacone1);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
}
