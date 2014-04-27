/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Waluty;
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
public class WalutyDAOfk extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade walutyFacade;

    public WalutyDAOfk() {
        super(Waluty.class);
    }

    public WalutyDAOfk(Class entityClass) {
        super(entityClass);
    }
    
    
    public  List<Waluty> findAll(){
        try {
            return walutyFacade.findAll(Waluty.class);
        } catch (Exception e) {
            return null;
        }
   }

    public Waluty findByName(String staranazwa) {
        try {
            return walutyFacade.findWalutaByName(staranazwa);
        } catch (Exception e) {
            return null;
        }
    }

      
}
