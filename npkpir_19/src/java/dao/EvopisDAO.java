/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Evopis;
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
            System.out.println("Pobieram EvopisDAO");
            return evopisFacade.findAll(Evopis.class);
        } catch (Exception e) {
            return null;
        }
   }
}
