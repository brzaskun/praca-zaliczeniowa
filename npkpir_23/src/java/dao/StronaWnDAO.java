/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.StronaWn;
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
public class StronaWnDAO extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject private SessionFacade sessionFacade;

    public StronaWnDAO() {
        super(StronaWn.class);
    }

    public StronaWnDAO(SessionFacade sessionFacade, Class entityClass) {
        super(entityClass);
        this.sessionFacade = sessionFacade;
    }

    public StronaWnDAO(SessionFacade sessionFacade) {
        this.sessionFacade = sessionFacade;
    }

    public List<StronaWiersza> findStronaMaByKontoWnMaWaluta(Konto konto, String symbolwaluty) {
        return sessionFacade.findStronaMaByKontoWnMaWaluta(konto, symbolwaluty);
    }

       
    
    
    
}
