/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.PozycjaBilans;
import entityfk.UkladBilansRZiS;
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
     public  List<PozycjaBilans> findBilansuklad(UkladBilansRZiS bilansuklad){
        try {
            return sessionFacade.findBilansuklad(bilansuklad);
        } catch (Exception e) {
            return null;
        }
   }

    
    
}
