/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entityfk.Wiersze;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class WierszeDAO extends DAO implements Serializable{
    
    @Inject private SessionFacade wierszeFacade;
    
    public WierszeDAO() {
        super(Wiersze.class);
    }
    
    public  List<Wiersze> findAll(){
        try {
            System.out.println("Pobieram WierszeDAO");
            return wierszeFacade.findAll(Wiersze.class);
        } catch (Exception e) {
            return null;
        }
   }
    
}
