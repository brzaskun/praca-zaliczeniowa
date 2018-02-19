/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.SprawozdanieUklad;
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

public class SprawozdanieUkladDAO  extends DAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SessionFacade sessionFacade;

    public SprawozdanieUkladDAO() {
        super(SprawozdanieUklad.class);
    }
    
     public List<SprawozdanieUklad> findAll() {
        try {
            return sessionFacade.findAll(SprawozdanieUklad.class);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }
    
}
