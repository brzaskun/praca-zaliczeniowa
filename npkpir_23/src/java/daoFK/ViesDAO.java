/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.*;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;
import vies.Vies;

/**
 *
 * @author Osito
 */
@Named
//pomaga przenosci opisy bo inaczej nie chca sie zachowac. scopy nie pasuja
public class ViesDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade sessionFacade;

    public ViesDAO() {
        super(Vies.class);
    }
    
    public  List<Vies> findAll(){
        try {
            return sessionFacade.findAll(Vies.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    
}
