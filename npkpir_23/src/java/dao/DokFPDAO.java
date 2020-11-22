/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.DokFP;
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
public class DokFPDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade sessionFacade;

    public DokFPDAO() {
        super(DokFP.class);
    }
    
    public  List<DokFP> findAll(){
        try {
            return sessionFacade.findAll(DokFP.class);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }
}
