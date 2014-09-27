/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoFK;

import dao.DAO;
import entityfk.WierszBO;
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
public class WierszBODAO extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade wierszBOFacade;
    
    public List<WierszBO> lista(String grupa) {
//        try {
//            return wierszBOFacade.findBOLista0(grupa);
//        } catch (Exception e) {
            return null;
        //}
    }
    
}
