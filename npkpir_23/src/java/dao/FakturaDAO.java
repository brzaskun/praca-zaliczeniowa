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
import java.util.Collections;
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
public class FakturaDAO extends DAO implements Serializable {

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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FakturaDAO() {
        super(Faktura.class);
        super.em = this.em;
    }


//   public void dodaj(Faktura faktura){
//        create(faktura);
//   }
//
//    public List<Faktura> findbyKontrahent_nip(String kontrahent_nip, Podatnik podatnik) {
//         try {
//            return fakturaFacade.findByKontrahent_nip(kontrahent_nip, podatnik);
//        } catch (Exception e) { E.e(e); 
//            return null;
//        }
//    }
 
    public List<Faktura> findbyKontrahentNipRok(String kontrahentnip, Podatnik podatnik, String rok) {
         try {
            return getEntityManager().createNamedQuery("Faktura.findByKontrahentRok").setParameter("kontrahent_nip", kontrahentnip).setParameter("wystawcanazwa", podatnik).setParameter("rok", rok).getResultList();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
//    public List<Faktura> findbyKontrahentNipPo2015(String kontrahentnip, Podatnik podatnik) {
//         try {
//            return fakturaFacade.findByKontrahentNipPo2015(kontrahentnip, podatnik);
//        } catch (Exception e) { E.e(e); 
//            return null;
//        }
//    }
    
//    public List<Faktura> findFakturyByRok(String rok) {
//         try {
//            return fakturaFacade.findFakturyByRok(rok);
//        } catch (Exception e) { E.e(e); 
//            return null;
//        }
//    }
    

    public List<Faktura> findFakturyByRokPodatnik(String rok, Podatnik wystawcanazwa) {
        List<Faktura> zwrot = null;
         try {
            zwrot = getEntityManager().createNamedQuery("Faktura.findByRokPodatnik").setParameter("rok", rok).setParameter("wystawcanazwa", wystawcanazwa).getResultList();
        } catch (Exception e) { E.e(e); 
            
        }
        return zwrot;
    }
    
    public Faktura findOstatniaFakturaByRokPodatnik(String rok, Podatnik wystawcanazwa) {
         try {
            return (Faktura)  getEntityManager().createNamedQuery("Faktura.findOstatniaFakturaByRokPodatnik").setParameter("rok", rok).setParameter("wystawcanazwa", wystawcanazwa).setMaxResults(1).getSingleResult();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

    public List<Faktura> findbyPodatnik(Podatnik podatnik) {
         try {
            return getEntityManager().createNamedQuery("Faktura.findByWystawcanazwa").setParameter("wystawcanazwa", podatnik).getResultList();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    

    public Faktura findbyNumerPodatnik(String numerkolejny, Podatnik podatnik) {
        Faktura zwrot = null;
        try {
            zwrot = (Faktura) getEntityManager().createNamedQuery("Faktura.findByNumerkolejnyWystawcanazwa").setParameter("wystawcanazwa", podatnik).setParameter("numerkolejny", numerkolejny).getSingleResult();
        } catch (Exception e) {
        }
        return zwrot;
    }
    public Faktura findbyNumerPodatnikDlaOkresowej(String numerkolejny, Podatnik podatnik) {
        Faktura zwrot = null;
        try {
            zwrot = (Faktura)  getEntityManager().createNamedQuery("Faktura.findByNumerkolejnyWystawcanazwaDlaOkresowej").setParameter("wystawcanazwa", podatnik).setParameter("numerkolejny", numerkolejny).getSingleResult();
        } catch (Exception e) {
        }
        return zwrot;
    }

    
      public List<Faktura> findbyPodatnikRok(Podatnik podatnik, String rok) {
         try {
            return getEntityManager().createNamedQuery("Faktura.findByWystawcanazwaRok").setParameter("wystawcanazwa", podatnik).setParameter("rok", rok).getResultList();
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
            if (niezaplacone0zaplacone1 == false) {
                return Collections.synchronizedList( getEntityManager().createNamedQuery("Faktura.findByWystawcanazwaRokMcNiezaplacone").setParameter("wystawcanazwa", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList());
            } else {
                return Collections.synchronizedList( getEntityManager().createNamedQuery("Faktura.findByWystawcanazwaRokMcZaplacone").setParameter("wystawcanazwa", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList());
            }
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

    public List<Klienci> findKontrahentFakturyRok(Podatnik podatnikObiekt, String rok) {
        try {
            return getEntityManager().createNamedQuery("Faktura.findByKonrahentPodatnikRok").setParameter("podatnik", podatnikObiekt).setParameter("rok", rok).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
   
    public Collection<? extends Klienci> findKontrahentFaktury(Podatnik podatnikObiekt) {
        try {
            return getEntityManager().createNamedQuery("Faktura.findByKonrahentPodatnik").setParameter("podatnik", podatnikObiekt).getResultList(); 
        } catch (Exception e) {
            return null;
        }
    }

    public Collection<? extends Klienci> findKontrahentFakturyRO(Podatnik podatnikObiekt) {
        try {
            return getEntityManager().createNamedQuery("Faktura.findByKonrahentPodatnik").setParameter("podatnik", podatnikObiekt).setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE).setHint(QueryHints.READ_ONLY, HintValues.TRUE).getResultList(); 
        } catch (Exception e) {
            return null;
        }
    }

    public List<Faktura> findbyKontrahent(Klienci t) {
        List<Faktura> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Faktura.findByKontrahentID").setParameter("kontrahent", t).getResultList(); 
        } catch (Exception e) {
            
        }
        return zwrot;
    }

    public Faktura findByID(int faktid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
