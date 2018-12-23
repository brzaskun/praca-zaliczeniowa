/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.PozycjaBilans;
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

public class PozycjaBilansDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;

    public PozycjaBilansDAO() {
        super(PozycjaBilans.class);
    }

    public PozycjaBilansDAO(Class entityClass) {
        super(entityClass);
    }
    
      public  List<PozycjaBilans> findAll(){
        try {
            return sessionFacade.findAll(PozycjaBilans.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
     public  List<PozycjaBilans> findBilansukladAktywa(UkladBR bilansuklad){
        try {
            return sessionFacade.findBilansukladAktywa(bilansuklad);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
      public  List<PozycjaBilans> findBilansuklad(String uklad, String podatnik, String rok){
        try {
            return sessionFacade.findUkladBRBilans(uklad, podatnik, rok);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
     
    public  List<PozycjaBilans> findBilansukladPasywa(UkladBR bilansuklad){
        try {
            return sessionFacade.findBilansukladPasywa(bilansuklad);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
     
    public  List<PozycjaBilans> findBilansukladAktywaPasywa(UkladBR bilansuklad){
        try {
            return sessionFacade.findBilansukladAktywaPasywa(bilansuklad);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

    
    public void findRemoveBilansuklad(UkladBR ukladBR) {
        try {
            sessionFacade.findRemoveBilansuklad(ukladBR.getUklad(), ukladBR.getPodatnik().getNazwapelna(), ukladBR.getRok());
        } catch (Exception e) { E.e(e); 
        }
    }
    public Integer findMaxLevelPodatnikAktywa(UkladBR ukladBR) {
        try {
            return sessionFacade.findMaxLevelBilansukladAktywa(ukladBR.getUklad(), ukladBR.getPodatnik().getNazwapelna(), ukladBR.getRok());
        } catch (Exception e) { E.e(e); 
        }
        return null;
    }
    
    public Integer findMaxLevelPodatnikPasywa(UkladBR ukladBR) {
        try {
            return sessionFacade.findMaxLevelBilansukladPasywa(ukladBR.getUklad(), ukladBR.getPodatnik().getNazwapelna(), ukladBR.getRok());
        } catch (Exception e) { E.e(e); 
        }
        return null;
    }

    public PozycjaBilans findBilansLP(int lp) {
         try {
            return sessionFacade.findPozycjaBilansLP(lp);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    
}
