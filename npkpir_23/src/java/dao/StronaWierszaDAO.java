/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Podatnik;
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

    public List<StronaWiersza> findStronaByPodatnikKontoRokWaluta(Podatnik podatnik, Konto konto, String rok, String skrotWaluty) {
        return sessionFacade.findStronaByPodatnikKontoRokWaluta(podatnik, konto, rok, skrotWaluty);
    }
    public List<StronaWiersza> findStronaByPodatnikKontoRokWalutaWszystkie(Podatnik podatnik, Konto konto, String rok) {
        return sessionFacade.findStronaByPodatnikKontoRokWalutaWszystkie(podatnik, konto, rok);
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoRokWszystkieNT(Podatnik podatnik, Konto konto, String rok) {
        return sessionFacade.findStronaByPodatnikKontoRokWszystkieNT(podatnik, konto, rok);
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokWalutaWynik(Podatnik podatnik, String rok, String skrotWaluty) {
        return sessionFacade.findStronaByPodatnikRokWalutaWynik(podatnik, rok, skrotWaluty);
    }
    public List<StronaWiersza> findStronaByPodatnikRokWynik(Podatnik podatnik, String rok) {
        return sessionFacade.findStronaByPodatnikRokWynik(podatnik, rok);
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokWalutaBilans(Podatnik podatnik, String rok, String skrotWaluty) {
        return sessionFacade.findStronaByPodatnikRokWalutaBilans(podatnik, rok, skrotWaluty);
    }
    public List<StronaWiersza> findStronaByPodatnikRokBilans(Podatnik podatnik, String rok) {
        return sessionFacade.findStronaByPodatnikRokBilans(podatnik, rok);
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokWalutaBilansBO(Podatnik podatnik, String rok, String skrotWaluty) {
        return sessionFacade.findStronaByPodatnikRokWalutaBilansBO(podatnik, rok, skrotWaluty);
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWalutaWszystkieNT(Podatnik podatnikObiekt, String wybranaWalutaDlaKont, Konto wybranekonto, String rokWpisuSt) {
        return sessionFacade.findStronaByPodatnikKontoRokWalutyWszystkieNT(podatnikObiekt, wybranaWalutaDlaKont, wybranekonto, rokWpisuSt);
    }
    
}
