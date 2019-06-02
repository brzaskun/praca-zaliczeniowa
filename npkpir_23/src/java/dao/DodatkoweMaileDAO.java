/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.DodatkoweMaile;
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
public class DodatkoweMaileDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade sessionFacade;

    public DodatkoweMaileDAO() {
        super(DodatkoweMaile.class);
    }
    
    public  List<DodatkoweMaile> findAll(){
        try {
            return sessionFacade.findAll(DodatkoweMaile.class);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }
}
