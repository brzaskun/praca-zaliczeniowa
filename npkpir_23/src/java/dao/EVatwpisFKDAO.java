/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Klienci;
import entity.Podatnik;
import entityfk.EVatwpisFK;
import entityfk.Wiersz;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional

public class EVatwpisFKDAO  extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject private SessionFacade sessionFacade;
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

    public EVatwpisFKDAO() {
        super(EVatwpisFK.class);
        super.em = this.em;
    }
    public List<EVatwpisFK> findbyKontrahent(Klienci t) {
        List<EVatwpisFK> zwrot = new ArrayList<>();
        try {
            zwrot = sessionFacade.getEntityManager().createNamedQuery("EVatwpisFK.findByKlient").setParameter("klient", t).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
    
     public List<EVatwpisFK> findPodatnik(Podatnik podatnik) {
        return sessionFacade.findEVatwpisFKByPodatnik(podatnik);
    }
    
    public List<EVatwpisFK> findPodatnikMc(Podatnik podatnik, String rok, String mcod, String mcdo) {
        List<EVatwpisFK> l = Collections.synchronizedList(new ArrayList<>());
        List<EVatwpisFK> input = sessionFacade.findEVatwpisFKByPodatnikRokMcodMcdo(podatnik, String.valueOf(rok), mcod, mcdo);
        if (input != null && !input.isEmpty()) {
            for (EVatwpisFK p : input) {
                try {
                    //error.E.s("ew "+p);
                    if (!"VAT".equals(p.getDokfk().getRodzajedok().getSkrot())) {
                        l.add(p);
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            }
        }
        return l;
    }
    
    public List<EVatwpisFK> findPodatnikMcInnyOkres(Podatnik podatnik, String rok, String mcod, String mcdo) {
        //pobieramy ewidencje z tego roku i mca i badamy ich dokumenty, jezeli sa rozne to pobieramy
        List<EVatwpisFK> l = Collections.synchronizedList(new ArrayList<>());
        List<EVatwpisFK> input = sessionFacade.findEVatwpisFKByPodatnikRokInnyOkres(podatnik, rok);
        int rokdeklaracji = Integer.parseInt(rok);
        if (input != null && !input.isEmpty()) {
            int dok_dolnagranica = Integer.parseInt(mcod)-1;
            int dok_gornagranica = Integer.parseInt(mcdo)+1;
            for (EVatwpisFK p : input) {
                try {
                    int mcdok = Integer.parseInt(p.getDokfk().getMiesiac());
                    int rokdok = Integer.parseInt(p.getDokfk().getRok());
                    if ((rokdok < rokdeklaracji) || (mcdok <= dok_dolnagranica && p.getVat() != 0.0)) {
                        int mc_ewidencji = Integer.parseInt(p.getMcEw());
                        if (mc_ewidencji > dok_dolnagranica && mc_ewidencji < dok_gornagranica) {
                            l.add(p);
                        }
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            }
        }
        return l;
    }
    
//    public List<EVatwpisFK> findPodatnikMcOdDo(Podatnik podatnik, String rok, String mcod, String mcdo) {
//        //pobieramy ewidencje z tego roku i mca i badamy ich dokumenty, jezeli sa rozne to pobieramy
//        List<EVatwpisFK> l = Collections.synchronizedList(new ArrayList<>());
//        List<EVatwpisFK> input = sessionFacade.findEVatwpisFKByPodatnikRokMcodMcdo(podatnik, rok);
//        if (input != null && !input.isEmpty()) {
//            int dg = Integer.parseInt(mcod)-1;
//            int gg = Integer.parseInt(mcdo)+1;
//            for (EVatwpisFK p : input) {
//                try {
//                    int mcdok = Integer.parseInt(p.getDokfk().getMiesiac());
//                    if (mcod.equals("01")) {
//                        mcdok = 12 - mcdok;
//                    }
//                    if (mcdok <= dg && p.getVat() != 0.0) {
//                        int p_mc = Integer.parseInt(p.getMcEw());
//                        if (p_mc > dg && p_mc < gg) {
//                            l.add(p);
//                        }
//                    }
//                } catch (Exception e) {
//                    E.e(e);
//                }
//            }
//        }
//        return l;
//    }
    
    public List<EVatwpisFK> findPodatnikDalszeMce(Podatnik podatnik, Integer rok, String mcod, String mcdo) {
        List<EVatwpisFK> l = Collections.synchronizedList(new ArrayList<>());
        List<EVatwpisFK> wierszeewidencji = sessionFacade.findEVatwpisFKByPodatnikRokInnyOkres(podatnik, String.valueOf(rok));
        if (wierszeewidencji != null && !wierszeewidencji.isEmpty()) {
            int mc_gorna_granica = Integer.parseInt(mcdo);
            if (mc_gorna_granica == 13) {
                mc_gorna_granica = 1;
                rok = rok+1;
            }
            for (EVatwpisFK p : wierszeewidencji) {
                try {
                    int mcdok = Integer.parseInt(p.getDokfk().getMiesiac());
                    int mc_ewidencji = Integer.parseInt(p.getMcEw());
                    if (mcdok != mc_ewidencji && p.getVat() != 0.0) {
                        int rok_ewidencji = Integer.parseInt(p.getRokEw());
                        if (mc_ewidencji > mc_gorna_granica && rok_ewidencji >= rok && mcdok <= mc_gorna_granica) {
                            l.add(p);
                        }
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            }
        }
        return l;
    }
    
    public EVatwpisFK znajdzEVatwpisFKPoWierszu(Wiersz wiersz) {
        return sessionFacade.znajdzEVatwpisFKPoWierszu(wiersz);
    }

    public String findEVatwpisFKPodatnikKlient(Podatnik podatnikObiekt, Klienci klient, String rok) {
        return sessionFacade.findEVatwpisFKPodatnikKlient(podatnikObiekt, klient, rok);
    }
    
    public List<EVatwpisFK> zwrocRok(String rokWpisuSt) {
        return sessionFacade.getEntityManager().createNamedQuery("EVatwpisFK.findByRok").setParameter("rok", rokWpisuSt).getResultList();
    }
    
    public List<EVatwpisFK> zwrocNULL() {
        return sessionFacade.getEntityManager().createNamedQuery("EVatwpisFK.findEwidencjaNUll").getResultList();
    }
    
    public List<EVatwpisFK> zwrocRokMc(String rokWpisuSt, String mc) {
        return sessionFacade.getEntityManager().createNamedQuery("EVatwpisFK.findByMcRok").setParameter("rok", rokWpisuSt).setParameter("mc", mc).getResultList();
    }
}
