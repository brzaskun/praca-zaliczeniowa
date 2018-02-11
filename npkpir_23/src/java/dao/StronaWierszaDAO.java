/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Podatnik;
import entityfk.Delegacja;
import entityfk.Konto;
import entityfk.MiejsceKosztow;
import entityfk.Pojazdy;
import entityfk.StronaWiersza;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;
import view.WpisView;

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
    
    public List<StronaWiersza> findStronaByKontoOnly(Konto konto) {
        return sessionFacade.findStronaWierszaByKontoOnly(konto);
    }

    public List<StronaWiersza> findStronaByKontoWnMa(Konto konto, String wnma) {
        String nowewnma;
        if (wnma.equals("Wn")) {
            nowewnma = "Ma";
        } else {
            nowewnma = "Wn";
        }
        List<StronaWiersza> pobranestrony = sessionFacade.findStronaWierszaByKontoWnMa(konto, nowewnma);
        List<StronaWiersza> pobranestronykorekty = sessionFacade.findStronaWierszaByKontoWnMaKorekta(konto, wnma);
        if (pobranestronykorekty != null && pobranestronykorekty.size() > 0) {
            pobranestrony.addAll(pobranestronykorekty);
        }
        return pobranestrony;
    }
    
    public List<StronaWiersza> findStronaByKontoWnMaWaluta(Konto konto, String symbolwaluty, String wnma) {
        String nowewnma;
        if (wnma.equals("Wn")) {
            nowewnma = "Ma";
        } else {
            nowewnma = "Wn";
        }
        List<StronaWiersza> pobranestrony = sessionFacade.findStronaWierszaByKontoWnMaWaluta(konto, symbolwaluty, nowewnma);
        List<StronaWiersza> pobranestronykorekty = sessionFacade.findStronaWierszaByKontoWnMaWalutaKorekta(konto, symbolwaluty, wnma);
        if (pobranestronykorekty != null && pobranestronykorekty.size() > 0) {
            pobranestrony.addAll(pobranestronykorekty);
        }
        for (StronaWiersza p : pobranestrony) {
            sessionFacade.refresh(p);
        }
        return pobranestrony;
    }
    
    public List<StronaWiersza> findStronaByKontoWnMaBO(Konto konto, String wnma) {
        String nowewnma;
        if (wnma.equals("Wn")) {
            nowewnma = "Ma";
        } else {
            nowewnma = "Wn";
        }
        return sessionFacade.findStronaWierszaByKontoWnMaBO(konto, nowewnma);
    }
    
    public List<StronaWiersza> findStronaByKontoWnMaWalutaBO(Konto konto, String symbolwaluty, String wnma) {
        String nowewnma;
        if (wnma.equals("Wn")) {
            nowewnma = "Ma";
        } else {
            nowewnma = "Wn";
        }
        return sessionFacade.findStronaWierszaByKontoWnMaWalutaBO(konto, symbolwaluty, nowewnma);
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWaluta(Podatnik podatnik, Konto konto, String rok, String skrotWaluty) {
        return sessionFacade.findStronaByPodatnikKontoRokWaluta(podatnik, konto, rok, skrotWaluty);
    }
    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteRokWaluta(Podatnik podatnik, Konto konto, String rok, String skrotWaluty, MiejsceKosztow p) {
        return sessionFacade.findStronaByPodatnikKontoMacierzysteRokWaluta(podatnik, konto, rok, skrotWaluty,p);
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteRok(Podatnik podatnik, Konto konto, String rok) {
        return sessionFacade.findStronaByPodatnikKontoMacierzysteRok(podatnik, konto, rok);
    }
    public List<StronaWiersza> findStronaByPodatnikKontoSyntetyczneRok(Podatnik podatnik, Konto konto, String rok) {
        return sessionFacade.findStronaByPodatnikKontoSyntetyczneRok(podatnik, konto, rok);
    }
    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteMcWaluta(Podatnik podatnik, Konto konto, String mc, String skrotWaluty, MiejsceKosztow p) {
        return sessionFacade.findStronaByPodatnikKontoMacierzysteMcWaluta(podatnik, konto, mc, skrotWaluty,p);
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteMcWalutaPojazdy(Podatnik podatnik, Konto konto, String mc, String skrotWaluty, Pojazdy p) {
        return sessionFacade.findStronaByPodatnikKontoMacierzysteMcWalutaPojazdy(podatnik, konto, mc, skrotWaluty,p);
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteMcWalutaDelegacja(Podatnik podatnik, Konto konto, String mc, String skrotWaluty, Delegacja p) {
        return sessionFacade.findStronaByPodatnikKontoMacierzysteMcWalutaDelegacja(podatnik, konto, mc, skrotWaluty,p);
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoRokWalutaWszystkie(Podatnik podatnik, Konto konto, String rok) {
        return sessionFacade.findStronaByPodatnikKontoRokWalutaWszystkie(podatnik, konto, rok);
    }
    public List<StronaWiersza> findStronaByPodatnikKontoRokMCWalutaWszystkie(Podatnik podatnik, Konto konto, WpisView wpisView) {
        return sessionFacade.findStronaByPodatnikKontoRokMcWalutaWszystkie(podatnik, konto, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoRokWszystkieNT(Podatnik podatnik, Konto konto, String rok) {
        return sessionFacade.findStronaByPodatnikKontoRokWszystkieNT(podatnik, konto, rok);
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoRokWszystkieR(Podatnik podatnik, Konto konto, String rok) {
        return sessionFacade.findStronaByPodatnikKontoRokWszystkieR(podatnik, konto, rok);
    }
    public List<StronaWiersza> findStronaByPodatnikRokWalutaWynik(Podatnik podatnik, String rok, String skrotWaluty) {
        return sessionFacade.findStronaByPodatnikRokWalutaWynik(podatnik, rok, skrotWaluty);
    }
    public List<StronaWiersza> findStronaByPodatnikRokWynik(Podatnik podatnik, String rok) {
        return sessionFacade.findStronaByPodatnikRokWynik(podatnik, rok);
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokWynikBO(Podatnik podatnik, String rok) {
        return sessionFacade.findStronaByPodatnikRokWynikBO(podatnik, rok);
    }
    
    public List<StronaWiersza> findStronaByPodatnikWynikCecha(Podatnik podatnik) {
        return sessionFacade.findStronaByPodatnikWynikCecha(podatnik);
    }
    
    public List<StronaWiersza> findStronaByPodatnikWynikCechaRokMc(Podatnik podatnik, String rok, String mc) {
        return sessionFacade.findStronaByPodatnikWynikCechaRokMc(podatnik, rok, mc);
    }
    
      
    public List<StronaWiersza> findStronaByPodatnikRokMcWynik(Podatnik podatnik, String rok, String mc) {
        return sessionFacade.findStronaByPodatnikRokMcWynik(podatnik, rok, mc);
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokMcWynikSlownik(Podatnik podatnik, String rok, String mc) {
        return sessionFacade.findStronaByPodatnikRokMcWynikSlownik(podatnik, rok, mc);
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokWynikSlownik(Podatnik podatnik, String rok) {
        return sessionFacade.getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokWynikSlownik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList();
    }
    
     public List<StronaWiersza> findStronaByPodatnikRok(Podatnik podatnik, String rok) {
        return sessionFacade.findStronaByPodatnikRok(podatnik, rok);
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokWalutaBilans(Podatnik podatnik, String rok, String skrotWaluty) {
        return sessionFacade.findStronaByPodatnikRokWalutaBilans(podatnik, rok, skrotWaluty);
    }
    
    
    public List<StronaWiersza> findStronaByPodatnikRokBilans(Podatnik podatnik, String rok) {
        return sessionFacade.findStronaByPodatnikRokBilans(podatnik, rok);
    }
    
    public List<Konto> findKontoByPodatnikRokBilans(Podatnik podatnik, String rok) {
        return sessionFacade.findKontoByPodatnikRokBilans(podatnik, rok);
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokWalutaBilansBO(Podatnik podatnik, String rok, String skrotWaluty) {
        return sessionFacade.findStronaByPodatnikRokWalutaBilansBO(podatnik, rok, skrotWaluty);
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokBilansBO(Podatnik podatnik, String rok) {
        return sessionFacade.findStronaByPodatnikRokBilansBO(podatnik, rok);
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWalutaWszystkieNT(Podatnik podatnikObiekt, String wybranaWalutaDlaKont, Konto wybranekonto, String rokWpisuSt) {
        return sessionFacade.findStronaByPodatnikKontoRokWalutyWszystkieNT(podatnikObiekt, wybranaWalutaDlaKont, wybranekonto, rokWpisuSt);
    }
    
     public List<StronaWiersza> findStronaByPodatnikKontoRokWalutaWszystkieR(Podatnik podatnikObiekt, String wybranaWalutaDlaKont, Konto wybranekonto, String rokWpisuSt) {
        return sessionFacade.findStronaByPodatnikKontoRokWalutyWszystkieR(podatnikObiekt, wybranaWalutaDlaKont, wybranekonto, rokWpisuSt);
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWszystkie(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt) {
        return sessionFacade.findStronaByPodatnikKontoRokWalutyWszystkie(podatnikObiekt, konto, rokWpisuSt);
    }
    public List<StronaWiersza> findStronaByPodatnikKontoRokMcWszystkie(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt, String mc) {
        return sessionFacade.findStronaByPodatnikKontoRokMcWalutyWszystkie(podatnikObiekt, konto, rokWpisuSt, mc);
    }
    public List<StronaWiersza> findStronaByPodatnikKontoRokMcVAT(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt, String mc) {
        return sessionFacade.findStronaByPodatnikKontoRokMcVAT(podatnikObiekt, konto, rokWpisuSt, mc);
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoRokVAT(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt) {
        return sessionFacade.findStronaByPodatnikKontoRokVAT(podatnikObiekt, konto, rokWpisuSt);
    }

    public List<StronaWiersza> findStronaByPodatnikKontoBOWaluta(Podatnik podatnik, Konto konto, String rok, String skrotWaluty) {
        return sessionFacade.findStronaByPodatnikKontoBOWaluta(podatnik, konto, rok, skrotWaluty);
    }
    public List<StronaWiersza> findStronaByPodatnikKontoBOWalutaWszystkie(Podatnik podatnik, Konto konto, String rok) {
        return sessionFacade.findStronaByPodatnikKontoBOWalutaWszystkie(podatnik, konto, rok);
    }
}
