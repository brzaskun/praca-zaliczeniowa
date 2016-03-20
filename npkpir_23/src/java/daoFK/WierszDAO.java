/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import error.E;
import java.io.Serializable;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class WierszDAO extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;
    
//    public void usunWiersz(int id) {
//        try {
//            sessionFacade.usunWierszById(id);
//        } catch (Exception e) {
//            E.e(e);
//        }
//    }
    
}
