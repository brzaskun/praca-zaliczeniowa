/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.UkladBR;
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

public class PozycjaRZiSDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;

    public PozycjaRZiSDAO() {
        super(PozycjaRZiS.class);
    }

    public PozycjaRZiSDAO(Class entityClass) {
        super(entityClass);
    }
    
      public  List<PozycjaRZiS> findAll(){
        try {
            return sessionFacade.findAll(PozycjaRZiS.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
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
