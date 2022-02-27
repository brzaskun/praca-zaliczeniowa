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
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.queries.LoadGroup;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class StronaWierszaDAO extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    @PersistenceContext(unitName = "npkpir_22PU")
    private EntityManager em;
    
    @PreDestroy
    private void preDestroy() {
        em.clear();
        em.close();
        em.getEntityManagerFactory().close();
        em = null;
        error.E.s("koniec jpa");
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public StronaWierszaDAO() {
        super(StronaWiersza.class);
        super.em = this.em;
    }


    public StronaWiersza findStronaById(StronaWiersza strona) {
        return (StronaWiersza)  getEntityManager().createNamedQuery("StronaWiersza.findById").setParameter("id", strona.getId()).getSingleResult();
    }
    
    public List<StronaWiersza> findStronaByKontoOnly(Konto konto) {
        try {
            return  getEntityManager().createNamedQuery("StronaWiersza.findByStronaWierszaKontoOnly").setParameter("konto", konto).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public StronaWiersza findStronaByWierszBO(WierszBO w) {
        try {
            return (StronaWiersza) getEntityManager().createNamedQuery("StronaWiersza.findByStronaWierszaWierszBO").setParameter("wierszbo", w).getSingleResult();
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
        List<StronaWiersza> pobranestrony = findStronaWierszaByKontoWnMa(konto, nowewnma);
        List<StronaWiersza> pobranestronykorekty = findStronaWierszaByKontoWnMaKorekta(konto, wnma);
        if (pobranestronykorekty != null && pobranestronykorekty.size() > 0) {
            pobranestrony.addAll(pobranestronykorekty);
        }
        return Collections.synchronizedList(pobranestrony);
    }
    
    private List<StronaWiersza> findStronaWierszaByKontoWnMa(Konto konto, String wnma) {
        try {
            LoadGroup lg = new LoadGroup();
            lg.addAttribute("nowetransakcje");
            return  getEntityManager().createNamedQuery("StronaWiersza.findByStronaWierszaKonto").setParameter("konto", konto).setParameter("wnma", wnma)
                    .setHint(QueryHints.LOAD_GROUP, lg).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    private List<StronaWiersza> findStronaWierszaByKontoWnMaKorekta(Konto konto, String wnma) {
        try {
            LoadGroup lg = new LoadGroup();
            lg.addAttribute("nowetransakcje");
            return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByStronaWierszaKontoKorekta").setParameter("konto", konto).setParameter("wnma", wnma)
                    .setHint(QueryHints.LOAD_GROUP, lg).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<StronaWiersza> findStronaByKontoWnMaWaluta(Konto konto, String symbolwaluty, String wnma) {
        String nowewnma;
        if (wnma.equals("Wn")) {
            nowewnma = "Ma";
        } else {
            nowewnma = "Wn";
        }
        List<StronaWiersza> pobranestrony = findStronaWierszaByKontoWnMaWaluta(konto, symbolwaluty, nowewnma);
        List<StronaWiersza> pobranestronykorekty = findStronaWierszaByKontoWnMaWalutaKorekta(konto, symbolwaluty, wnma);
        if (pobranestronykorekty != null && pobranestronykorekty.size() > 0) {
            pobranestrony.addAll(pobranestronykorekty);
        }
//        for (StronaWiersza p : pobranestrony) {
//            sessionFacade.refresh(p);
//        }
        return Collections.synchronizedList(pobranestrony);
    }
    
     private List<StronaWiersza> findStronaWierszaByKontoWnMaWaluta(Konto konto, String symbolwaluty, String wnma) {
        try {
            
            return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByStronaWierszaKontoWaluta").setParameter("konto", konto).setParameter("symbolwaluty", symbolwaluty).setParameter("wnma", wnma).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

   
    private List<StronaWiersza> findStronaWierszaByKontoWnMaWalutaKorekta(Konto konto, String symbolwaluty, String wnma) {
        try {
            return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByStronaWierszaKontoWalutaKorekta").setParameter("konto", konto).setParameter("symbolwaluty", symbolwaluty).setParameter("wnma", wnma).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<StronaWiersza> findStronaByKontoWnMaBO(Konto konto, String wnma) {
        String nowewnma;
        if (wnma.equals("Wn")) {
            nowewnma = "Ma";
        } else {
            nowewnma = "Wn";
        }
        try {
            return getEntityManager().createNamedQuery("StronaWiersza.findByStronaWierszaKontoBO").setParameter("konto", konto).setParameter("wnma", nowewnma).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<StronaWiersza> findStronaByKontoWnMaWalutaBO(Konto konto, String symbolwaluty, String wnma) {
        String nowewnma;
        if (wnma.equals("Wn")) {
            nowewnma = "Ma";
        } else {
            nowewnma = "Wn";
        }
        try {
             return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByStronaWierszaKontoWalutaBO").setParameter("konto", konto).setParameter("symbolwaluty", symbolwaluty).setParameter("wnma", nowewnma).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWaluta(Podatnik podatnik, Konto konto, String rok, String skrotWaluty) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).getResultList();
    }
    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteRokWaluta(Podatnik podatnik, Konto konto, String rok, String skrotWaluty, MiejsceKosztow p) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoMacierzysteRokWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).setParameter("nazwapelna", p.getOpismiejsca()).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteRok(Podatnik podatnik, Konto konto, String rok) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoMacierzysteRok").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).getResultList();
    }
    public List<StronaWiersza> findStronaByPodatnikKontoSyntetyczneRok(Podatnik podatnik, Konto konto, String rok) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoSyntetyczneRok").setParameter("podatnikObj", podatnik).setParameter("kontonumer", konto.getPelnynumer()).setParameter("konto", konto).setParameter("rok", rok).getResultList();
    }
    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteMcWaluta(Podatnik podatnik, Konto konto, String mc, String skrotWaluty, MiejsceKosztow p) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoMacierzysteMcWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("mc", mc).setParameter("symbolwaluty", skrotWaluty).setParameter("nazwapelna", p.getOpismiejsca()).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteMcWalutaPojazdy(Podatnik podatnik, Konto konto, String mc, String skrotWaluty, Pojazdy p) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoMacierzysteMcWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto.getPelnynumer()).setParameter("mc", mc).setParameter("symbolwaluty", skrotWaluty).setParameter("nazwapelna", p.getNrrejestracyjny()).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteMcWalutaDelegacja(Podatnik podatnik, Konto konto, String mc, String skrotWaluty, Delegacja p) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoMacierzysteMcWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto.getPelnynumer()).setParameter("mc", mc).setParameter("symbolwaluty", skrotWaluty).setParameter("nazwapelna", p.getOpisdlugi()).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoRokWalutaWszystkie(Podatnik podatnik, Konto konto, String rok) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutaWszystkie").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).getResultList();
    }
    public List<StronaWiersza> findStronaByPodatnikKontoRokMCWalutaWszystkie(Podatnik podatnik, Konto konto, WpisView wpisView) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokMcWalutaWszystkie").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoRokWszystkieNT(Podatnik podatnik, Konto konto, String rok) {
         //List lista =  getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWszystkieNT").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).getResultList();
        //this.refresh(lista);
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("nowetransakcje");
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWszystkieNT").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok)
                .setHint(QueryHints.LOAD_GROUP, lg).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoRokWszystkieR(Podatnik podatnik, Konto konto, String rok) {
         //List lista =  getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWszystkieR").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).getResultList();
        //this.refresh(lista);
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("platnosci");
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWszystkieR").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok)
                .setHint(QueryHints.LOAD_GROUP, lg).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
    }
    public List<StronaWiersza> findStronaByPodatnikRokWalutaWynik(Podatnik podatnik, String rok, String skrotWaluty) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokWalutaWynik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).getResultList();
    }
    public List<StronaWiersza> findStronaByPodatnikRokWynik(Podatnik podatnik, String rok, String mc) {
         //return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokWynik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList());
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("wiersz.dokfk");
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokWynik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mc", mc)
                .setHint(QueryHints.READ_ONLY, HintValues.TRUE)
                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
                
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList();
    }
    public List<StronaWiersza> findStronaByPodatnikRokMetodaKasowa(Podatnik podatnik, String rok, String mc) {
            LoadGroup lg = new LoadGroup();
            lg.addAttribute("wiersz.dokfk");
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokMcMetodaKasowa").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mc", mc)
                .setHint(QueryHints.READ_ONLY, HintValues.TRUE)
                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
                
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList();
    }
    
    
    public List<StronaWiersza> findStronaByPodatnikRokWynikBO(Podatnik podatnik, String rok) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokWynikBO").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikWynikCecha(Podatnik podatnik) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikWynikCecha").setParameter("podatnikObj", podatnik).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikWynikCechaRokMc(Podatnik podatnik, String rok, String mc) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikWynikCechaRokMc").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList();
    }
    
      
    public List<StronaWiersza> findStronaByPodatnikRokMcWynik(Podatnik podatnik, String rok, String mc) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokMcWynik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokMcWynikSlownik(Podatnik podatnik, String rok, String mc) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokMcWynikSlownik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokWynikSlownik(Podatnik podatnik, String rok) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokWynikSlownik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList();
    }
    
     public List<StronaWiersza> findStronaByPodatnikRokMcodMcdo(Podatnik podatnik, String rok, String mcod, String mcdo) {
        //return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRok").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList());
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("wiersz.dokfk");
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokMcodMcdo").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mcod", mcod).setParameter("mcdo", mcdo)
                .setHint(QueryHints.READ_ONLY, HintValues.TRUE)
                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
                .setHint(QueryHints.REFRESH, HintValues.TRUE)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList();
    }
     
     public List<StronaWiersza> findStronaByPodatnikRokMcodMcdoWynik(Podatnik podatnik, String rok, String mcod, String mcdo) {
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("wiersz.dokfk");
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokMcodMcdoWynik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mcod", mcod).setParameter("mcdo", mcdo)
                .setHint(QueryHints.READ_ONLY, HintValues.TRUE)
                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
                .setHint(QueryHints.REFRESH, HintValues.TRUE)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokRO(Podatnik podatnik, String rok) {
         //return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRok").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList());
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("wiersz");
        lg.addAttribute("wiersz.dokfk");
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRok").setParameter("podatnikObj", podatnik).setParameter("rok", rok)
                .setHint(QueryHints.READ_ONLY, HintValues.TRUE)
                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList();
    }
     
    public List<Konto> findStronaByPodatnikRokKontoDist(Podatnik podatnik, String rok) {
        return getEntityManager().createNamedQuery("StronaWiersza.findStronaByPodatnikRokKontoDist").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("rokK", Integer.parseInt(rok)).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokWalutaBilans(Podatnik podatnik, String rok, String skrotWaluty) {
         LoadGroup lg = new LoadGroup();
        lg.addAttribute("wiersz.dokfk");
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokWalutaBilans").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty)
                .setHint(QueryHints.READ_ONLY, HintValues.TRUE)
                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
                .setHint(QueryHints.REFRESH, HintValues.TRUE)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList();
    }
    
    
    public List<StronaWiersza> findStronaByPodatnikRokBilans(Podatnik podatnik, String rok, String mc) {
         return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokBilans").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mc", mc)
                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
                .setHint(QueryHints.REFRESH, HintValues.TRUE).setHint(QueryHints.READ_ONLY, HintValues.TRUE).getResultList();
    }
    
    
    public List<Konto> findKontoByPodatnikRokBilans(Podatnik podatnik, String rok) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByKontoDistinctPodatnikRokBilans").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokWalutaBilansBO(Podatnik podatnik, String rok, String skrotWaluty) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokWalutaBilansBO").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokBilansBO(Podatnik podatnik, String rok) {
         return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokBilansBO").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWalutaWszystkieNT(Podatnik podatnik, String wybranaWalutaDlaKonta, Konto konto, String rok) {
         //List lista =  getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutyWszystkieNT").setParameter("podatnikObj", podatnik).setParameter("wybranaWalutaDlaKonta", wybranaWalutaDlaKonta).setParameter("konto", konto).setParameter("rok", rok).getResultList();
        //this.refresh(lista);
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("nowetransakcje");
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutyWszystkieNT").setParameter("podatnikObj", podatnik).setParameter("wybranaWalutaDlaKonta", wybranaWalutaDlaKonta).setParameter("konto", konto).setParameter("rok", rok)
                .setHint(QueryHints.LOAD_GROUP, lg).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
    }
    
     public List<StronaWiersza> findStronaByPodatnikKontoRokWalutaWszystkieR(Podatnik podatnik, String wybranaWalutaDlaKonta, Konto konto, String rok) {
     //List lista =  getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutyWszystkieR").setParameter("podatnikObj", podatnik).setParameter("wybranaWalutaDlaKonta", wybranaWalutaDlaKonta).setParameter("konto", konto).setParameter("rok", rok).getResultList();
        //this.refresh(lista);
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("platnosci");
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutyWszystkieR").setParameter("podatnikObj", podatnik).setParameter("wybranaWalutaDlaKonta", wybranaWalutaDlaKonta).setParameter("konto", konto).setParameter("rok", rok)
                .setHint(QueryHints.LOAD_GROUP, lg).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWszystkie(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutyWszystkie").setParameter("podatnikObj", podatnikObiekt).setParameter("konto", konto).setParameter("rok", rokWpisuSt).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoStartRokWszystkie(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt, String mcod, String mcdo) {
        String like = "";
        if (konto.isMapotomkow()) {
            like = konto.getPelnynumer()+"%";
        } else {
            like = konto.getPelnynumer();
        }
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("platnosci");
        lg.addAttribute("konto");
        lg.addAttribute("wiersz.dokfk");
        lg.addAttribute("wiersz.tabelanbp");
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoStartRokWalutyWszystkie")
                .setParameter("podatnikObj", podatnikObiekt)
                .setParameter("konto", konto)
                .setParameter("rok", rokWpisuSt)
                .setParameter("mcod", mcod)
                .setParameter("mcdo", mcdo)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoStartRokWalutyWszystkieOdswiez(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt, String mcod, String mcdo) {
        String like = "";
        if (konto.isMapotomkow()) {
            like = konto.getPelnynumer()+"%";
        } else {
            like = konto.getPelnynumer();
        }
         //t.platnosci t.wiersz.dokfk t.wiersz.tabelanbp
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("platnosci");
        lg.addAttribute("konto");
        lg.addAttribute("wiersz.dokfk");
        lg.addAttribute("wiersz.tabelanbp");
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoStartRokWalutyWszystkie")
                .setParameter("podatnikObj", podatnikObiekt)
                .setParameter("konto", konto)
                .setParameter("rok", rokWpisuSt)
                .setParameter("mcod", mcod)
                .setParameter("mcdo", mcdo)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoRokMcWszystkie(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt, String mc) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokMcWalutyWszystkie").setParameter("podatnikObj", podatnikObiekt).setParameter("konto", konto).setParameter("rok", rokWpisuSt).setParameter("mc", mc).getResultList();
    }
    public List<StronaWiersza> findStronaByPodatnikKontoRokMcVAT(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt, String mc) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokMcVAT").setParameter("podatnikObj", podatnikObiekt).setParameter("konto", konto).setParameter("rok", rokWpisuSt).setParameter("mc", mc).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoRokVAT(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokVAT").setParameter("podatnikObj", podatnikObiekt).setParameter("konto", konto).setParameter("rok", rokWpisuSt).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikKontoBOWaluta(Podatnik podatnik, Konto konto, String rok, String skrotWaluty) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoBOWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).getResultList();
    }
    public List<StronaWiersza> findStronaByPodatnikKontoBOWalutaWszystkie(Podatnik podatnik, Konto konto, String rok) {
        return getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoBOWalutaWszystkie").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).getResultList();
    }
    
    public static void main(String[] args) {
        String kopnto = "202-2-5";
        int ind = kopnto.lastIndexOf("-");
        error.E.s(""+ind);
        kopnto = kopnto.substring(0,ind);
        error.E.s(""+kopnto);
    }
}
