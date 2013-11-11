/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Waluty;
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
public class WalutyDAOfk extends DAO implements Serializable {
    
    @Inject
    private SessionFacade walutyFacade;

    public WalutyDAOfk() {
        super(Waluty.class);
    }
    
    public  List<Waluty> findAll(){
        try {
            System.out.println("Pobieram WalutyDAOfk");
            return walutyFacade.findAll(Waluty.class);
        } catch (Exception e) {
            return null;
        }
   }
    
}
