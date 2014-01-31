/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.PozycjaRZiS;
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
     @Inject
    private SessionFacade sessionFacade;

    public PozycjaRZiSDAO() {
        super(PozycjaRZiS.class);
    }
      public  List<PozycjaRZiS> findAll(){
        try {
            System.out.println("Pobieram PozycjaRZiSDAO");
            return sessionFacade.findAll(PozycjaRZiS.class);
        } catch (Exception e) {
            return null;
        }
   }
     
    
}
