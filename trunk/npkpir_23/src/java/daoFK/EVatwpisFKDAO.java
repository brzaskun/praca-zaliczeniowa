/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoFK;

import dao.DAO;
import entityfk.EVatwpisFK;
import entityfk.Wiersz;
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
public class EVatwpisFKDAO  extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject private SessionFacade sessionFacade;

    public EVatwpisFKDAO() {
        super(EVatwpisFK.class);
    }
    
    public List<EVatwpisFK> findAll() {
        return sessionFacade.findAll(EVatwpisFK.class);
    }
    
    public EVatwpisFK znajdzEVatwpisFKPoWierszu(Wiersz wiersz) {
        return sessionFacade.znajdzEVatwpisFKPoWierszu(wiersz);
    }
    
}
