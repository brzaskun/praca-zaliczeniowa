/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoFK;

import dao.DAO;
import entityfk.Kliencifk;
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
public class KliencifkDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject private SessionFacade sessionFacade;

    public KliencifkDAO() {
        super(Kliencifk.class);
    }

    public KliencifkDAO(Class entityClass) {
        super(entityClass);
    }
    
    
    public Kliencifk znajdzkontofk(String nip, String podatniknip) {
        return (Kliencifk) sessionFacade.znajdzkontofk(nip, podatniknip);
    }
    
    public List<Kliencifk> znajdzkontofkKlient(String podatniknip) {
        return sessionFacade.znajdzkontofkKlient(podatniknip);
    }
    
    
}
