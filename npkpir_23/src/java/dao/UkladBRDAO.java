/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entityfk.UkladBR;
import error.E;
import java.io.Serializable;
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

public class UkladBRDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;
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

    public UkladBRDAO() {
        super(UkladBR.class);
        super.em = this.em;
    }
   
    public UkladBR findukladBR(UkladBR ukladBR) {
        return sessionFacade.findUkladBRUklad(ukladBR);
    }
  
    public List<UkladBR> findPodatnik(Podatnik nazwapelna) {
        try {
            return sessionFacade.findUkladBRPodatnik(nazwapelna);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
     
    
    public List<UkladBR> findPodatnikRok(Podatnik podatnik, String rok) {
        try {
            return sessionFacade.findUkladBRPodatnikRok(podatnik, rok);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<UkladBR> findUkladByRok(String rok) {
        try {
            return sessionFacade.getEntityManager().createNamedQuery("UkladBR.findByRok").setParameter("rok", rok).getResultList();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
     
    public List<UkladBR> findRokUkladnazwa(String rok, String ukladnazwa) {
        try {
            return sessionFacade.findRokUkladnazwa(rok, ukladnazwa);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    

//    public List<UkladBR> findukladBRWzorcowyRok(String rokWpisu) {
//        try {
//            return sessionFacade.findUkladBRWzorcowyRok(rokWpisu);
//        } catch (Exception e) { E.e(e); 
//            return null;
//        }
//    }

    public List<UkladBR> findukladBRPodatnikRok(Podatnik podatnikWpisu, String rokWpisuSt) {
         try {
            return sessionFacade.findukladBRPodatnikRok(podatnikWpisu, rokWpisuSt);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }
    
    public UkladBR findukladBRPodatnikRokPodstawowy(Podatnik podatnikWpisu, String rokWpisuSt) {
         try {
            return sessionFacade.findukladBRPodatnikRokPodstawowy(podatnikWpisu, rokWpisuSt);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }
    
    public UkladBR findukladBRPodatnikRokAktywny(Podatnik podatnikWpisu, String rokWpisuSt) {
        UkladBR uklad = null;
         try {
            uklad =  sessionFacade.findukladBRPodatnikRokAktywny(podatnikWpisu, rokWpisuSt);
            if (uklad == null) {
                uklad = sessionFacade.findukladBRPodatnikRokPodstawowy(podatnikWpisu, rokWpisuSt);
                uklad.setAktualny(true);
                sessionFacade.edit(uklad);
            }
        } catch (Exception e) { 
            E.e(e); 
        }
         return uklad;
    }

    public void ustawnieaktywne(Podatnik podatnik) {
         try {
            sessionFacade.ukladBRustawnieaktywne(podatnik);
        } catch (Exception e) {
            E.e(e); 
        }
    }

    public List<UkladBR> findWszystkie() {
         try {
            return sessionFacade.findWszystkieUkladBR();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
}
