/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.PozycjaRZiS;
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
        } catch (Exception e) {
            return null;
        }
   }
     public  List<PozycjaRZiS> findRzisuklad(Rzisuklad rzisuklad){
        try {
            return sessionFacade.findRzisuklad(rzisuklad);
        } catch (Exception e) {
            return null;
        }
   }
    
}
