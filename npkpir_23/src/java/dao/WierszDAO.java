/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entityfk.Tabelanbp;
import entityfk.Wiersz;
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
public class WierszDAO extends DAO implements Serializable {
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

    public WierszDAO() {
        super(Wiersz.class);
        super.em = this.em;
    }  
 
    public List<Wiersz> findWierszeRok(String rok){
        return sessionFacade.getEntityManager().createNamedQuery("Wiersz.findByRok").setParameter("rok", rok).getResultList();
    }
    
    public List<Wiersz> pobierzWiersze(Tabelanbp tabelanbp, Podatnik podatnik, String rok) {
        List<Wiersz> zwrot = null;
        try {
            zwrot = sessionFacade.getEntityManager().createNamedQuery("Wiersz.findByPodatnikRokTabela").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("tabelanbp", tabelanbp).getResultList();
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
    public List<Wiersz> pobierzWierszeMcDok(Podatnik podatnik, String rok, String mc, String rodzajdok) {
        List<Wiersz> zwrot = null;
        try {
            zwrot = sessionFacade.getEntityManager().createNamedQuery("Wiersz.findByPodatnikRokMcSeria").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("mc", mc).setParameter("rodzajdok", rodzajdok).getResultList();
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
    public List<Wiersz> pobierzWierszeMcDokImportIBAN(Podatnik podatnik, String rok) {
        List<Wiersz> zwrot = null;
        try {
            zwrot = sessionFacade.getEntityManager().createNamedQuery("Wiersz.findByPodatnikRokMcImport").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
    public List<Wiersz> pobierzWierszeRok(Podatnik podatnik, String rok) {
        List<Wiersz> zwrot = null;
        try {
            zwrot = sessionFacade.getEntityManager().createNamedQuery("Wiersz.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
}
