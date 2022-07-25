/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
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

public class PozycjaRZiSDAO extends DAO implements Serializable{
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

    public PozycjaRZiSDAO() {
        super(PozycjaRZiS.class);
        super.em = this.em;
    }
  
    public PozycjaRZiSDAO(Class entityClass) {
        super(entityClass);
    }
 
    public  PozycjaRZiS findRzisLP(int lp){
        try {
            return sessionFacade.findPozycjaRZiSLP(lp);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
      
    public  List<PozycjaRZiS> findRzisuklad(UkladBR rzisuklad){
        try {
            return sessionFacade.findUkladBR(rzisuklad);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

    public  List<PozycjaRZiS> findRzisuklad(String uklad, String podatnik, String rok){
        try {
            return sessionFacade.findUkladBR(uklad, podatnik, rok);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public List<PozycjaRZiS> findRzisukladAll(String uklad, String podatnik) {
        try {
            return sessionFacade.findUkladBRAll(uklad, podatnik);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public void findRemoveRzisuklad(UkladBR ukladBR) {
        try {
            sessionFacade.findRemoveRzisuklad(ukladBR.getUklad(), ukladBR.getPodatnik().getNazwapelna(), ukladBR.getRok());
        } catch (Exception e) { E.e(e); 
        }
    }

    public Integer findMaxLevelPodatnik(UkladBR ukladBR) {
        try {
            return sessionFacade.findMaxLevelRzisuklad(ukladBR.getUklad(), ukladBR.getPodatnik().getNazwapelna(), ukladBR.getRok());
        } catch (Exception e) { E.e(e); 
        }
        return 0;
    }

    public List<PozycjaRZiSBilans> findRZiSPozString(String pozycjaString, String rokWpisuSt, String uklad) {
        return sessionFacade.findRZiSPozString(pozycjaString, rokWpisuSt, uklad);
    }
    
    
}
