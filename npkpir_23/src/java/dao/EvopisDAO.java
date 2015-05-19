/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Evopis;
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
public class EvopisDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade evopisFacade;

    public EvopisDAO() {
        super(Evopis.class);
    }
    
    public  List<Evopis> findAll(){
        try {
            return evopisFacade.findAll(Evopis.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
}
