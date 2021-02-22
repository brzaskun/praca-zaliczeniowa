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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class UkladBRDAO extends DAO implements Serializable{
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

    protected EntityManager getEntityManager() {
        return em;
    }

    public UkladBRDAO() {
        super(UkladBR.class);
        super.em = this.em;
    }

    public UkladBR findukladBR(UkladBR ukladBR) {
        return (UkladBR)  getEntityManager().createNamedQuery("UkladBR.findByUkladPodRok").setParameter("uklad", ukladBR.getUklad()).setParameter("podatnik", ukladBR.getPodatnik()).setParameter("rok", ukladBR.getRok()).getSingleResult();
    }

    public List<UkladBR> findPodatnik(Podatnik nazwapelna) {
        try {
            return getEntityManager().createNamedQuery("UkladBR.findByPodatnik").setParameter("podatnik", nazwapelna).getResultList();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

    public List<UkladBR> findPodatnikRok(Podatnik podatnik, String rok) {
        try {
            return getEntityManager().createNamedQuery("UkladBR.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<UkladBR> findUkladByRok(String rok) {
        try {
            return getEntityManager().createNamedQuery("UkladBR.findByRok").setParameter("rok", rok).getResultList();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    

    public List<UkladBR> findRokUkladnazwa(String rok, String ukladnazwa) {
        try {
            return getEntityManager().createNamedQuery("UkladBR.findByRokNazwa").setParameter("ukladnazwa", ukladnazwa).setParameter("rok", rok).getResultList();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    
    public List<UkladBR> findukladBRPodatnikRok(Podatnik podatnikWpisu, String rokWpisuSt) {
         try {
            return getEntityManager().createNamedQuery("UkladBR.findByPodatnikRok").setParameter("podatnik", podatnikWpisu).setParameter("rok", rokWpisuSt).getResultList();
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }

    public UkladBR findukladBRPodatnikRokPodstawowy(Podatnik podatnikWpisu, String rokWpisuSt) {
         try {
            return (UkladBR)  getEntityManager().createNamedQuery("UkladBR.findByPodatnikRokPodstawowy").setParameter("podatnik", podatnikWpisu).setParameter("rok", rokWpisuSt).getSingleResult();
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }


    public UkladBR findukladBRPodatnikRokAktywny(Podatnik podatnikWpisu, String rokWpisuSt) {
        UkladBR uklad = null;
         try {
            uklad =  (UkladBR)  getEntityManager().createNamedQuery("UkladBR.findByPodatnikRokAktywny").setParameter("podatnik", podatnikWpisu).setParameter("rok", rokWpisuSt).getSingleResult();
            if (uklad == null) {
                uklad = this.findukladBRPodatnikRokPodstawowy(podatnikWpisu, rokWpisuSt);
                uklad.setAktualny(true);
                edit(uklad);
            }
        } catch (Exception e) { 
            E.e(e); 
        }
         return uklad;
    }

    public void ustawnieaktywne(Podatnik podatnik) {
         try {
            getEntityManager().createNamedQuery("UkladBR.ustawNieaktywne").setParameter("podatnik", podatnik).executeUpdate();
        } catch (Exception e) {
            E.e(e); 
        }
    }

    public List<UkladBR> findWszystkie() {
         try {
            return getEntityManager().createNamedQuery("UkladBR.findAll").getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
}
