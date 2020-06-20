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
import entityfk.WierszBO;
import error.E;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.queries.LoadGroup;
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
    
    public StronaWiersza findStronaById(StronaWiersza strona) {
        return sessionFacade.findStronaWierszaById(strona);
    }
    
    public List<StronaWiersza> findStronaByKontoOnly(Konto konto) {
        return Collections.synchronizedList(sessionFacade.findStronaWierszaByKontoOnly(konto));
    }
    
    public StronaWiersza findStronaByWierszBO(WierszBO w) {
        try {
            return (StronaWiersza) sessionFacade.getEntityManager().createNamedQuery("StronaWiersza.findByStronaWierszaWierszBO").setParameter("wierszbo", w).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
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
        return Collections.synchronizedList(pobranestrony);
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
//        for (StronaWiersza p : pobranestrony) {
//            sessionFacade.refresh(p);
//        }
        return Collections.synchronizedList(pobranestrony);
    }
    
    public List<StronaWiersza> findStronaByKontoWnMaBO(Konto konto, String wnma) {
        String nowewnma;
        if (wnma.equals("Wn")) {
            nowewnma = "Ma";
        } else {
            nowewnma = "Wn";
        }
        return Collections.synchronizedList(sessionFacade.findStronaWierszaByKontoWnMaBO(konto, nowewnma));
    }
    
    public List<StronaWiersza> findStronaByKontoWnMaWalutaBO(Konto konto, String symbolwaluty, String wnma) {
        String nowewnma;
        if (wnma.equals("Wn")) {
            nowewnma = "Ma";
        } else {
            nowewnma = "Wn";
        }
        return Collections.synchronizedList(sessionFacade.findStronaWierszaByKontoWnMaWalutaBO(konto, symbolwaluty, nowewnma));
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWaluta(Podatnik podatnik, Konto konto, String rok, String skrotWaluty) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoRokWaluta(podatnik, konto, rok, skrotWaluty));
    }
    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteRokWaluta(Podatnik podatnik, Konto konto, String rok, String skrotWaluty, MiejsceKosztow p) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoMacierzysteRokWaluta(podatnik, konto, rok, skrotWaluty,p));
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteRok(Podatnik podatnik, Konto konto, String rok) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoMacierzysteRok(podatnik, konto, rok));
    }
    public List<StronaWiersza> findStronaByPodatnikKontoSyntetyczneRok(Podatnik podatnik, Konto konto, String rok) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoSyntetyczneRok(podatnik, konto, rok));
    }
    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteMcWaluta(Podatnik podatnik, Konto konto, String mc, String skrotWaluty, MiejsceKosztow p) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoMacierzysteMcWaluta(podatnik, konto, mc, skrotWaluty,p));
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteMcWalutaPojazdy(Podatnik podatnik, Konto konto, String mc, String skrotWaluty, Pojazdy p) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoMacierzysteMcWalutaPojazdy(podatnik, konto, mc, skrotWaluty,p));
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteMcWalutaDelegacja(Podatnik podatnik, Konto konto, String mc, String skrotWaluty, Delegacja p) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoMacierzysteMcWalutaDelegacja(podatnik, konto, mc, skrotWaluty,p));
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoRokWalutaWszystkie(Podatnik podatnik, Konto konto, String rok) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoRokWalutaWszystkie(podatnik, konto, rok));
    }
    public List<StronaWiersza> findStronaByPodatnikKontoRokMCWalutaWszystkie(Podatnik podatnik, Konto konto, WpisView wpisView) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoRokMcWalutaWszystkie(podatnik, konto, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoRokWszystkieNT(Podatnik podatnik, Konto konto, String rok) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoRokWszystkieNT(podatnik, konto, rok));
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoRokWszystkieR(Podatnik podatnik, Konto konto, String rok) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoRokWszystkieR(podatnik, konto, rok));
    }
    public List<StronaWiersza> findStronaByPodatnikRokWalutaWynik(Podatnik podatnik, String rok, String skrotWaluty) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikRokWalutaWynik(podatnik, rok, skrotWaluty));
    }
    public List<StronaWiersza> findStronaByPodatnikRokWynik(Podatnik podatnik, String rok, String mc) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikRokWynik(podatnik, rok, mc));
    }
    public List<StronaWiersza> findStronaByPodatnikRokMetodaKasowa(Podatnik podatnik, String rok, String mc) {
            LoadGroup lg = new LoadGroup();
            lg.addAttribute("wiersz.dokfk");
        return Collections.synchronizedList(sessionFacade.getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokMcMetodaKasowa").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mc", mc)
                .setHint(QueryHints.READ_ONLY, HintValues.TRUE)
                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
                
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokWynikRO(Podatnik podatnik, String rok, String mc) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikRokWynik(podatnik, rok, mc));
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokWynikBO(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikRokWynikBO(podatnik, rok));
    }
    
    public List<StronaWiersza> findStronaByPodatnikWynikCecha(Podatnik podatnik) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikWynikCecha(podatnik));
    }
    
    public List<StronaWiersza> findStronaByPodatnikWynikCechaRokMc(Podatnik podatnik, String rok, String mc) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikWynikCechaRokMc(podatnik, rok, mc));
    }
    
      
    public List<StronaWiersza> findStronaByPodatnikRokMcWynik(Podatnik podatnik, String rok, String mc) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikRokMcWynik(podatnik, rok, mc));
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokMcWynikSlownik(Podatnik podatnik, String rok, String mc) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikRokMcWynikSlownik(podatnik, rok, mc));
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokWynikSlownik(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(sessionFacade.getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokWynikSlownik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList());
    }
    
     public List<StronaWiersza> findStronaByPodatnikRokMcodMcdo(Podatnik podatnik, String rok, String mcod, String mcdo) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikRokMcodMcdo(podatnik, rok, mcod, mcdo));
    }
     
    
    public List<StronaWiersza> findStronaByPodatnikRokRO(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikRokRO(podatnik, rok));
    }
     
    public List<Konto> findStronaByPodatnikRokKontoDist(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikRokKontoDist(podatnik, rok));
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokWalutaBilans(Podatnik podatnik, String rok, String skrotWaluty) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikRokWalutaBilans(podatnik, rok, skrotWaluty));
    }
    
    
    public List<StronaWiersza> findStronaByPodatnikRokBilans(Podatnik podatnik, String rok, String mc) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikRokBilans(podatnik, rok, mc));
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokBilansRO(Podatnik podatnik, String rok, String mc) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikRokBilans(podatnik, rok, mc));
    }
    
    
    public List<Konto> findKontoByPodatnikRokBilans(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(sessionFacade.findKontoByPodatnikRokBilans(podatnik, rok));
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokWalutaBilansBO(Podatnik podatnik, String rok, String skrotWaluty) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikRokWalutaBilansBO(podatnik, rok, skrotWaluty));
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokBilansBO(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikRokBilansBO(podatnik, rok));
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWalutaWszystkieNT(Podatnik podatnikObiekt, String wybranaWalutaDlaKont, Konto wybranekonto, String rokWpisuSt) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoRokWalutyWszystkieNT(podatnikObiekt, wybranaWalutaDlaKont, wybranekonto, rokWpisuSt));
    }
    
     public List<StronaWiersza> findStronaByPodatnikKontoRokWalutaWszystkieR(Podatnik podatnikObiekt, String wybranaWalutaDlaKont, Konto wybranekonto, String rokWpisuSt) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoRokWalutyWszystkieR(podatnikObiekt, wybranaWalutaDlaKont, wybranekonto, rokWpisuSt));
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWszystkie(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoRokWalutyWszystkie(podatnikObiekt, konto, rokWpisuSt));
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoStartRokWszystkie(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt, String mcod, String mcdo) {
        String like = "";
        if (konto.isMapotomkow()) {
            like = konto.getPelnynumer()+"%";
        } else {
            like = konto.getPelnynumer();
        }
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoStartRokWalutyWszystkie(podatnikObiekt, like, rokWpisuSt, mcod, mcdo));
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoStartRokWalutyWszystkieOdswiez(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt, String mcod, String mcdo) {
        String like = "";
        if (konto.isMapotomkow()) {
            like = konto.getPelnynumer()+"%";
        } else {
            like = konto.getPelnynumer();
        }
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoStartRokWalutyWszystkieOdswiez(podatnikObiekt, like, rokWpisuSt, mcod, mcdo));
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoRokMcWszystkie(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt, String mc) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoRokMcWalutyWszystkie(podatnikObiekt, konto, rokWpisuSt, mc));
    }
    public List<StronaWiersza> findStronaByPodatnikKontoRokMcVAT(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt, String mc) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoRokMcVAT(podatnikObiekt, konto, rokWpisuSt, mc));
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoRokVAT(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoRokVAT(podatnikObiekt, konto, rokWpisuSt));
    }

    public List<StronaWiersza> findStronaByPodatnikKontoBOWaluta(Podatnik podatnik, Konto konto, String rok, String skrotWaluty) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoBOWaluta(podatnik, konto, rok, skrotWaluty));
    }
    public List<StronaWiersza> findStronaByPodatnikKontoBOWalutaWszystkie(Podatnik podatnik, Konto konto, String rok) {
        return Collections.synchronizedList(sessionFacade.findStronaByPodatnikKontoBOWalutaWszystkie(podatnik, konto, rok));
    }
    
    public static void main(String[] args) {
        String kopnto = "202-2-5";
        int ind = kopnto.lastIndexOf("-");
        error.E.s(""+ind);
        kopnto = kopnto.substring(0,ind);
        error.E.s(""+kopnto);
    }
}
