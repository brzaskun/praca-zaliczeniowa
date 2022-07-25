/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.DAO;
import entity.Podatnik;
import entityfk.Konto;
import entityfk.KontopozycjaZapis;
import entityfk.UkladBR;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ejb.Stateless;import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional

public class KontopozycjaZapisDAO extends DAO implements Serializable{
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

    public KontopozycjaZapisDAO() {
        super(KontopozycjaZapis.class);
        super.em = this.em;
    }
    
    
    
    public List<KontopozycjaZapis> findKontaPozycjaZapisPodatnikUklad (UkladBR uklad, String rb) {
       try {
            return sessionFacade.findKontaZapisPodatnikUklad(uklad, rb);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
//    public List<KontopozycjaZapis> findKontaPozycjaZapisPodatnikUkladWzorzec (UkladBR uklad, String rb) {
//       try {
//            return sessionFacade.findKontaZapisPodatnikUkladWzorzec(uklad, rb);
//        } catch (Exception e) { E.e(e); 
//            return null;
//        }
//    }
    
    public void usunZapisaneKontoPozycjaPodatnikUklad(UkladBR uklad, String rb) {
        try {
            sessionFacade.usunZapisaneKontoPozycjaPodatnikUklad(uklad, rb);
        } catch (Exception e) {
            E.e(e); 
        }
    }
    
    public void usunZapisaneKontoPozycjaPodatnikUklad(UkladBR uklad, String rb, boolean aktywa0pasywa1) {
        try {
            sessionFacade.usunZapisaneKontoPozycjaPodatnikUklad(uklad, rb);
        } catch (Exception e) {
            E.e(e); 
        }
    }
    
     public void usunKontoPozycjaPodatnikUladKonto(UkladBR wybranyuklad, List<Konto> wykazkontf) {
        if (wykazkontf!=null) {
            for (Konto p : wykazkontf) {
                sessionFacade.usunZapisaneKontoPozycjaPodatnikUkladByKonto(wybranyuklad, p);
            }
        }
    }
    
        
    public KontopozycjaZapis findByKonto(Konto konto, UkladBR ukladBR) {
        KontopozycjaZapis kontopozycjaZapis = null;
        try {
            kontopozycjaZapis = sessionFacade.fintKontoPozycjaZapisByKonto(konto, ukladBR);
        } catch (Exception e) {
            E.e(e);
        }
        return kontopozycjaZapis;
    }

    public List<KontopozycjaZapis> findByKontoOnly(Konto konto) {
        List<KontopozycjaZapis> kontopozycjaZapis = Collections.synchronizedList(new ArrayList<>());
        try {
            kontopozycjaZapis = sessionFacade.findByKontoOnly(konto);
        } catch (Exception e) {
            E.e(e);
        }
        return kontopozycjaZapis;
    }

    public List<KontopozycjaZapis> findKontaPozycjaZapisPodatnikRok(Podatnik podatnik, String rok) {
        try {
            return sessionFacade.findKontaPozycjaZapisPodatnikRok(podatnik, rok);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

    public List<KontopozycjaZapis> findKontoPozycjaByRokUkladBilans(String rokWpisuSt, String uklad) {
        return sessionFacade.findKontoPozycjaByRokUkladBilans(rokWpisuSt, uklad);
    }

    public List<KontopozycjaZapis> findKontoPozycjaByRokUkladRzis(String rokWpisuSt, String uklad) {
        return sessionFacade.findKontoPozycjaByRokUkladRZiS(rokWpisuSt, uklad);
    }
    
}
