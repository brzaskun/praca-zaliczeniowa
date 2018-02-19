/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Rejestrlogowan;
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

public class RejestrlogowanDAO  extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject private SessionFacade sessionFacade;

    public RejestrlogowanDAO() {
        super(Rejestrlogowan.class);
    }

    public RejestrlogowanDAO(Class entityClass) {
        super(entityClass);
    }

    public Rejestrlogowan findByIP(String ip) {
        return sessionFacade.findRejestrlogowanByIP(ip);
    }

    
    public List<Rejestrlogowan> findByLiczbalogowan0() {
        return sessionFacade.RejestrlogowanfindByLiczbalogowan0();
    }

    public List<Rejestrlogowan> findAll() {
        return sessionFacade.findAll(Rejestrlogowan.class);
    }
    
    
    
}
