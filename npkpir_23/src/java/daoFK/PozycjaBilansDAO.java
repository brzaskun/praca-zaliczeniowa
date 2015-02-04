/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.PozycjaBilans;
import entityfk.UkladBR;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
@Singleton
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
        } catch (Exception e) {
            return null;
        }
   }
     public  List<PozycjaBilans> findBilansukladAktywa(UkladBR bilansuklad){
        try {
            return sessionFacade.findBilansukladAktywa(bilansuklad);
        } catch (Exception e) {
            return null;
        }
   }
     
     public  List<PozycjaBilans> findBilansukladPasywa(UkladBR bilansuklad){
        try {
            return sessionFacade.findBilansukladPasywa(bilansuklad);
        } catch (Exception e) {
            return null;
        }
   }

    
    public void findRemoveBilansuklad(UkladBR ukladBR) {
        try {
            sessionFacade.findRemoveBilansuklad(ukladBR.getUklad(), ukladBR.getPodatnik(), ukladBR.getRok());
        } catch (Exception e) {
        }
    }
    public int findMaxLevelPodatnikAktywa(UkladBR ukladBR) {
        try {
            return sessionFacade.findMaxLevelBilansukladAktywa(ukladBR.getUklad(), ukladBR.getPodatnik(), ukladBR.getRok());
        } catch (Exception e) {
        }
        return 0;
    }
    
    public int findMaxLevelPodatnikPasywa(UkladBR ukladBR) {
        try {
            return sessionFacade.findMaxLevelBilansukladPasywa(ukladBR.getUklad(), ukladBR.getPodatnik(), ukladBR.getRok());
        } catch (Exception e) {
        }
        return 0;
    }

    public PozycjaBilans findBilansLP(int lp) {
         try {
            return sessionFacade.findPozycjaBilansLP(lp);
        } catch (Exception e) {
            return null;
        }
    }
    
    
}
