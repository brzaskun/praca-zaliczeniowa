/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Faktura;
import entity.Klienci;
import entity.Podatnik;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
public class FakturaDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade fakturaFacade;
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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FakturaDAO() {
        super(Faktura.class);
        super.em = this.em;
    }


   
   public Faktura findFaktura(Faktura f) {
       return fakturaFacade.findfaktura(f);
   }
   public void dodaj(Faktura faktura){
        create(faktura);
   }

    public List<Faktura> findbyKontrahent_nip(String kontrahent_nip, Podatnik podatnik) {
         try {
            return fakturaFacade.findByKontrahent_nip(kontrahent_nip, podatnik);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<Faktura> findbyKontrahentNipRok(String kontrahentnip, Podatnik podatnik, String rok) {
         try {
            return fakturaFacade.findByKontrahentNipRok(kontrahentnip, podatnik, rok);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<Faktura> findbyKontrahentNipPo2015(String kontrahentnip, Podatnik podatnik) {
         try {
            return fakturaFacade.findByKontrahentNipPo2015(kontrahentnip, podatnik);
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
    
       
    public List<Faktura> findFakturyByRokPodatnik(String rok, Podatnik wystawcanazwa) {
         try {
            return fakturaFacade.findFakturyByRokPodatnik(rok, wystawcanazwa);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public Long liczFakturyByRokPodatnik(String rok, Podatnik wystawcanazwa) {
         try {
            return fakturaFacade.liczFakturyByRokPodatnik(rok, wystawcanazwa);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public Faktura findOstatniaFakturaByRokPodatnik(String rok, Podatnik wystawcanazwa) {
         try {
            return fakturaFacade.findOstatniaFakturaByRokPodatnik(rok, wystawcanazwa);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<Faktura> findbyPodatnik(Podatnik podatnik) {
         try {
            return fakturaFacade.findByPodatnik(podatnik);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    public Faktura findbyNumerPodatnik(String numerkolejny, Podatnik podatnik) {
        Faktura zwrot = null;
        try {
            zwrot = fakturaFacade.findByNumerPodatnik(numerkolejny, podatnik);
        } catch (Exception e) {
        }
        return zwrot;
    }
    public Faktura findbyNumerPodatnikDlaOkresowej(String numerkolejny, Podatnik podatnik) {
        Faktura zwrot = null;
        try {
            zwrot = fakturaFacade.findByNumerPodatnikDlaOkresowej(numerkolejny, podatnik);
        } catch (Exception e) {
        }
        return zwrot;
    }
     
      public List<Faktura> findbyPodatnikRok(Podatnik podatnik, String rok) {
         try {
            return fakturaFacade.findByPodatnikRok(podatnik, rok);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

    public List<Faktura> findbyPodatnikRokMc(Podatnik podatnik, String rok, String mc) {
        List<Faktura> zwrot = new ArrayList<>();
         try {
            zwrot = getEntityManager().createNamedQuery("Faktura.findByWystawcanazwaRokMc").setParameter("wystawcanazwa", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList();
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }
    
    public List<Faktura> findbyPodatnikRokMcPlatnosci(Podatnik podatnik, String rok, String mc, boolean niezaplacone0zaplacone1) {
         try {
            return fakturaFacade.findByPodatnikRokMcPlatnosci(podatnik, rok, mc, niezaplacone0zaplacone1);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

    public List<Klienci> findKontrahentFakturyRok(Podatnik podatnikObiekt, String rok) {
        try {
            return fakturaFacade.getEntityManager().createNamedQuery("Faktura.findByKonrahentPodatnikRok").setParameter("podatnik", podatnikObiekt).setParameter("rok", rok).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public Collection<? extends Klienci> findKontrahentFaktury(Podatnik podatnikObiekt) {
        try {
            return fakturaFacade.findKontrahentFaktury(podatnikObiekt); 
        } catch (Exception e) {
            return null;
        }
    }
    
    public Collection<? extends Klienci> findKontrahentFakturyRO(Podatnik podatnikObiekt) {
        try {
            return fakturaFacade.findKontrahentFakturyRO(podatnikObiekt); 
        } catch (Exception e) {
            return null;
        }
    }

    public List<Faktura> findbyKontrahent(Klienci t) {
        List<Faktura> zwrot = new ArrayList<>();
        try {
            zwrot = fakturaFacade.findKontrahentIDFakt(t); 
        } catch (Exception e) {
            
        }
        return zwrot;
    }

    public Faktura findByID(int faktid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
