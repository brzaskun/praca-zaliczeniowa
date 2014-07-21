/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entityfk.Konto;
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
public class StronaWierszaDAO extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject private SessionFacade sessionFacade;

    public StronaWierszaDAO() {
        super(StronaWiersza.class);
    }

    public StronaWierszaDAO(SessionFacade sessionFacade, Class entityClass) {
        super(entityClass);
        this.sessionFacade = sessionFacade;
    }

    public StronaWierszaDAO(SessionFacade sessionFacade) {
        this.sessionFacade = sessionFacade;
    }

    public List<StronaWiersza> findStronaByKontoWnMaWaluta(Konto konto, String symbolwaluty, String wnma) {
        String nowewnma;
        if (wnma.equals("Wn")) {
            nowewnma = "Ma";
        } else {
            nowewnma = "Wn";
        }
        return sessionFacade.findStronaWierszaByKontoWnMaWaluta(konto, symbolwaluty, nowewnma);
    }

       
    
    
    
}
