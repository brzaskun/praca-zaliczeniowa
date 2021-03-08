/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FakturaRozrachunki;
import entity.Klienci;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import session.SessionFacade;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class FakturaRozrachunkiDAO extends DAO implements Serializable {

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

    public FakturaRozrachunkiDAO() {
        super(FakturaRozrachunki.class);
        super.em = this.em;
    }

      
    public List<FakturaRozrachunki> rozrachunkiZDnia (WpisView wpisView) {
        Date d = new Date();
        try {
            return sessionFacade.rozrachunkiZDnia(d, wpisView);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<FakturaRozrachunki> findByPodatnik(WpisView wpisView) {
        try {
            return sessionFacade.findByPodatnik(wpisView);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<FakturaRozrachunki> findByPodatnikIBAN(WpisView wpisView) {
        try {
            return sessionFacade.getEntityManager().createNamedQuery("FakturaRozrachunki.findByPodatnikIBAN").setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
        
     public List<FakturaRozrachunki> findByPodatnikrokMc(WpisView wpisView) {
        try {
            return sessionFacade.findByPodatnikRokMc(wpisView);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<FakturaRozrachunki> findByPodatnikKontrahent(WpisView wpisView, Klienci kontrahent) {
        try {
            return sessionFacade.findByPodatnikKontrahent(wpisView, kontrahent);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
     public List<FakturaRozrachunki> findByPodatnikKontrahentRok(WpisView wpisView, Klienci kontrahent) {
        try {
            return sessionFacade.findByPodatnikKontrahentRok(wpisView, kontrahent);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
     
     public List<Klienci> findByPodatnikRok(WpisView wpisView) {
        try {
            return sessionFacade.getEntityManager().createNamedQuery("FakturaRozrachunki.findByPodatnikRok").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }


    public List<FakturaRozrachunki> findbyKontrahent(Klienci t) {
        List<FakturaRozrachunki> zwrot = new ArrayList<>();
        try {
            zwrot = sessionFacade.getEntityManager().createNamedQuery("FakturaRozrachunki.findByPodatnikKontrahentID").setParameter("kontrahent", t).getResultList();
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }

}
