/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Odsetki;
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
public class OdsetkiDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade odsetkiFacade;

    public OdsetkiDAO() {
        super(Odsetki.class);
    }
    
    public  List<Odsetki> findAll(){
        try {
            return odsetkiFacade.findAll(Odsetki.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
}
