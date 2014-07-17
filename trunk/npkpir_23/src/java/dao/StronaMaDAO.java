/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entityfk.Konto;
import entityfk.StronaMa;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class StronaMaDAO  extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject private SessionFacade sessionFacade;

    public StronaMaDAO() {
        super(StronaMa.class);
    }

    public StronaMaDAO(SessionFacade sessionFacade, Class entityClass) {
        super(entityClass);
        this.sessionFacade = sessionFacade;
    }

    public StronaMaDAO(SessionFacade sessionFacade) {
        this.sessionFacade = sessionFacade;
    }

    public List<StronaWiersza> findStronaMaByKontoWnMaWaluta(Konto konto, String symbolwaluty) {
        return sessionFacade.findStronaMaByKontoWnMaWaluta(konto, symbolwaluty);
    }
    
    
    
    
}
