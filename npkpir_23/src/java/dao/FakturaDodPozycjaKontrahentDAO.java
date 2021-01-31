/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FakturaDodPozycjaKontrahent;
import entity.Klienci;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class FakturaDodPozycjaKontrahentDAO  extends DAO implements Serializable {

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

    public FakturaDodPozycjaKontrahentDAO() {
        super(FakturaDodPozycjaKontrahent.class);
        super.em = this.em;
    }

    
      
    public  FakturaDodPozycjaKontrahent findById(int id) {
        try {
            return (FakturaDodPozycjaKontrahent) sessionFacade.getEntityManager().createNamedQuery("FakturaDodPozycjaKontrahent.findById").setParameter("id", id).getSingleResult();
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public  List<FakturaDodPozycjaKontrahent> findKontrRokMc(Klienci klienci, String rok, String mc) {
        try {
            return sessionFacade.getEntityManager().createNamedQuery("FakturaDodPozycjaKontrahent.findByKontrahentRokMc").setParameter("kontrahent", klienci).setParameter("rok", rok).setParameter("mc", mc).getResultList();
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

    public  void deleteByID(int id) {
        try {
            sessionFacade.getEntityManager().createNamedQuery("FakturaDodPozycjaKontrahent.deleteById").setParameter("id", id).executeUpdate();
        } catch (Exception e) { E.e(e); 
            
        }
   }

//    public  void aktualizuj(int id) {
//        try {
//            sessionFacade.getEntityManager().createNamedQuery("FakturaDodPozycjaKontrahent.aktualizuj").setParameter("id", id).executeUpdate();
//        } catch (Exception e) { E.e(e); 
//            
//        }
//   }       

    public List<FakturaDodPozycjaKontrahent> findbyKontrahent(Klienci t) {
        List<FakturaDodPozycjaKontrahent> zwrot =  new ArrayList<>();
        try {
            zwrot = sessionFacade.getEntityManager().createNamedQuery("FakturaDodPozycjaKontrahent.findByKontrahent").setParameter("kontrahent", t).getResultList();
        } catch (Exception e) { E.e(e); 
            
        }
        return zwrot;
    }
    
}
