/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Bilansuklad;
import entityfk.PozycjaRZiSBilans;
import entityfk.Rzisuklad;
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
        super(PozycjaRZiSBilans.class);
    }

    public PozycjaBilansDAO(Class entityClass) {
        super(entityClass);
    }
    
      public  List<PozycjaRZiSBilans> findAll(){
        try {
            return sessionFacade.findAll(PozycjaRZiSBilans.class);
        } catch (Exception e) {
            return null;
        }
   }
     public  List<PozycjaRZiSBilans> findBilansuklad(Bilansuklad bilansuklad){
        try {
            return sessionFacade.findBilansuklad(bilansuklad);
        } catch (Exception e) {
            return null;
        }
   }
    
}
