/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entityfk.Kliencifk;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.eclipse.persistence.config.CascadePolicy;
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

public class KontoDAOfk extends DAO implements Serializable {

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

    public KontoDAOfk() {
        super(Konto.class);
        super.em = this.em;
    }
   


    public List<Konto> findKontaRozrachunkowe(WpisView wpisView) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByRozrachunkowePodatnik").setParameter("zwyklerozrachszczegolne", "rozrachunkowe").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaRozrachunkoweZpotomkami(WpisView wpisView) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByRozrachunkowePodatnikZpotomkami").setParameter("zwyklerozrachszczegolne", "rozrachunkowe").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaRozrachunkoweWszystkie(WpisView wpisView) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByRozrachunkiPodatnikWszystkie").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaVAT(Podatnik podatnik, int rok) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByVATPodatnik").setParameter("zwyklerozrachszczegolne", "vat").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }


    public List<Konto> findKontaSrodkiTrw(Podatnik podatnik, int rok) {
        try {
            return getEntityManager().createNamedQuery("Konto.findBySrodkiTrwPodatnik").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaRMK(Podatnik podatnik, int rok) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByRMKPodatnik").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaRZiS(WpisView wpisView) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByBilansowewynikowePodatnik").setParameter("bilansowewynikowe", "wynikowe").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaGrupa(WpisView wpisView, String grupa) {
        try {
            return getEntityManager().createNamedQuery("Konto.findlistaKontGrupa").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("grupa", grupa).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaGrupaAnalityka(WpisView wpisView, String grupa) {
        try {
            return getEntityManager().createNamedQuery("Konto.findlistaKontGrupaAnalityka").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("grupa", grupa).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

//    public List<Konto> findKontaGrupa1(WpisView wpisView) {
//        try {
//            return Collections.synchronizedList(kontoFacade.findlistaKontGrupa1(wpisView));
//        } catch (Exception e) {
//            E.e(e);
//            return null;
//        }
//    }
//
//    public List<Konto> findKontaGrupa2(WpisView wpisView) {
//        try {
//            return Collections.synchronizedList(kontoFacade.findlistaKontGrupa2(wpisView));
//        } catch (Exception e) {
//            E.e(e);
//            return null;
//        }
//    }
//
//    public List<Konto> findKontaGrupa3(WpisView wpisView) {
//        try {
//            return Collections.synchronizedList(kontoFacade.findlistaKontGrupa3(wpisView));
//        } catch (Exception e) {
//            E.e(e);
//            return null;
//        }
//    }
//
//    public List<Konto> findKontaGrupa4(WpisView wpisView) {
//        try {
//            return Collections.synchronizedList(kontoFacade.findlistaKontGrupa4(wpisView));
//        } catch (Exception e) {
//            E.e(e);
//            return null;
//        }
//    }
//
//    public List<Konto> findKontaGrupa5(WpisView wpisView) {
//        try {
//            return Collections.synchronizedList(kontoFacade.findlistaKontGrupa5(wpisView));
//        } catch (Exception e) {
//            E.e(e);
//            return null;
//        }
//    }
//
//    public List<Konto> findKontaGrupa6(WpisView wpisView) {
//        try {
//            return Collections.synchronizedList(kontoFacade.findlistaKontGrupa6(wpisView));
//        } catch (Exception e) {
//            E.e(e);
//            return null;
//        }
//    }
//
//    public List<Konto> findKontaGrupa7(WpisView wpisView) {
//        try {
//            return Collections.synchronizedList(kontoFacade.findlistaKontGrupa7(wpisView));
//        } catch (Exception e) {
//            E.e(e);
//            return null;
//        }
//    }
//
//    public List<Konto> findKontaGrupa8(WpisView wpisView) {
//        try {
//            return Collections.synchronizedList(kontoFacade.findlistaKontGrupa8(wpisView));
//        } catch (Exception e) {
//            E.e(e);
//            return null;
//        }
//    }

    public Konto findKonto(String numer, Podatnik podatnik, Integer rok) {
        Konto zwrot = null;
        try {
            zwrot = (Konto)  getEntityManager().createNamedQuery("Konto.findByPelnynumerPodatnik").setParameter("pelnynumer", numer).setParameter("podatnik", podatnik).setParameter("rok", rok).getSingleResult();
        } catch (Exception e) {
        }
        return zwrot;
    }


    public Konto findKontoNazwaPodatnik(String nazwaskrocona, WpisView wpisView) {
        try {
            return (Konto)  getEntityManager().createNamedQuery("Konto.findByNazwaPodatnik").setParameter("nazwaskrocona", nazwaskrocona).setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }


    public Konto findKontoNazwaPelnaPodatnik(String nazwapelna, WpisView wpisView) {
        try {
            return (Konto)  getEntityManager().createNamedQuery("Konto.findByNazwaPelnaPodatnik").setParameter("nazwapelna", nazwapelna).setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Konto findKonto(int id) {
        try {
            return (Konto)  getEntityManager().createNamedQuery("Konto.findById").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Konto findKonto2(int id) {
         Konto k = null;
        try {
            k = (Konto)  getEntityManager().createNamedQuery("Konto.findById").setParameter("id", id).getSingleResult();
        } catch (Exception e) {

        }
        return k;
    }

    
    public List<Konto> findWszystkieKontaPodatnika(Podatnik podatnik, String rok) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaPodatnikaBezPrzyporzadkowania(Podatnik podatnik, String rok) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByPodatnikRokBezPrzyporzadkowania").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaPodatnikaRO(Podatnik podatnik, String rok) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaPodatnikaPobierzRelacje(Podatnik podatnik, String rok) {
        try {
             LoadGroup lg = new LoadGroup();
            lg.addAttribute("kontokategoria");
            lg.addAttribute("kontomacierzyste");
            lg.setIsConcurrent(Boolean.TRUE);
            return  getEntityManager().createNamedQuery("Konto.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok))
                    .setHint(QueryHints.REFRESH_CASCADE, CascadePolicy.CascadeAllParts)
                    .setHint(QueryHints.LOAD_GROUP, lg).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

      
    public List<Konto> findWszystkieKontaPodatnikaKsiegi(Podatnik podatnik, String rok) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByPodatnikKsiegi").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaPodatnikaBezSlownik(Podatnik podatnik, String rok) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByPodatnikBezSlownik").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findWszystkieKontaPodatnikaBezSlownikEdycja(Podatnik podatnik, String rok) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByPodatnikBezSlownik").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaPodatnikaBezSlownikKsiegi(Podatnik podatnik, String rok) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByPodatnikBezSlownikKsiegi").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaPodatnikaTylkoSlownik(Podatnik podatnik, String rok) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByPodatnikTylkoSlownik").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaPodatnikaBO(WpisView wpisView, String kategoriaKonta) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByKontaPodatnikaBO").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("wzorzec", kategoriaKonta).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaBilansowePodatnika(Podatnik podatnik, String rok) {
        try {
            LoadGroup lg = new LoadGroup();
            lg.addAttribute("kontokategoria");
            lg.addAttribute("kontomacierzyste");
            return getEntityManager().createNamedQuery("Konto.findByPodatnikBilansowe").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok))
                    .setHint(QueryHints.LOAD_GROUP, lg).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaWynikowePodatnika(Podatnik podatnik, String rok) {
        try {
            LoadGroup lg = new LoadGroup();
            lg.addAttribute("kontokategoria");
            lg.addAttribute("kontomacierzyste");
           return getEntityManager().createNamedQuery("Konto.findByPodatnikWynikowe").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok))
                    .setHint(QueryHints.LOAD_GROUP, lg).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findWszystkieKontaSlownikowePodatnika(Podatnik podatnik, Integer rok) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByPodatnikTylkoSlownikZero").setParameter("rok", rok).setParameter("podatnik", podatnik).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }


    public void wyzerujBoWnBoMawKontach(WpisView wpisView, String bilansowewynikowe) {
        getEntityManager().createNamedQuery("Konto.wyzerujBoWnwKontach").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("bilansowewynikowe", bilansowewynikowe).executeUpdate();
         getEntityManager().createNamedQuery("Konto.wyzerujBoMawKontach").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("bilansowewynikowe", bilansowewynikowe).executeUpdate();
    }
  
    public void wyzerujBoWnBoMawKontach(WpisView wpisView) {
        getEntityManager().createNamedQuery("Konto.wyzerujBowKontach").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).executeUpdate();
    }

    
    public List<Konto> findKontaBilansowePodatnikaBezPotomkow(WpisView wpisView) {
        return getEntityManager().createNamedQuery("Konto.findByPodatnikBilansoweBezPotomkow").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }

    public List<Konto> findKontaBilansowePodatnikaBezPotomkowRokPoprzedni(WpisView wpisView) {
        return getEntityManager().createNamedQuery("Konto.findByPodatnikBilansoweBezPotomkow").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokUprzedni()).getResultList();
    }

    public List<Konto> findKontaBilansowePodatnikaKwotaBezPotomkow(WpisView wpisView) {
        return getEntityManager().createNamedQuery("Konto.findByPodatnikBilansoweKwotaBezPotomkow").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }


    public Konto findKonto860(WpisView wpisView) {
        return (Konto)  getEntityManager().createNamedQuery("Konto.findByKonto860").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getSingleResult();
    }

    public List<Konto> findKontaWynikowePodatnikaBezPotomkow(WpisView wpisView) {
        return getEntityManager().createNamedQuery("Konto.findByPodatnikWynikoweBezPotomkow").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }
    

    public List<Konto> findKontaPodatnikZPotomkani(WpisView wpisView) {
        return getEntityManager().createNamedQuery("Konto.findByPodatnikZPotomkami").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }

    public List<Konto> findKontaWynikowePodatnikaBezPotomkowRokPop(WpisView wpisView) {
        return getEntityManager().createNamedQuery("Konto.findByPodatnikWynikoweBezPotomkow").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokUprzedni()).getResultList();
    }

    public Konto findKontoPodatnik490(WpisView wpisView) {
        try {
            return (Konto)  getEntityManager().createNamedQuery("Konto.findByPodatnik490").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaOstAlityka(Podatnik podatnik, Integer rok) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByMapotomkowMaSlownikPodatnik").setParameter("mapotomkow", false).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    

    public List<Konto> findKontaOstAlitykaWynikowe(WpisView wpisView) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByMapotomkowMaSlownikPodatnikWynikowe").setParameter("mapotomkow", false).setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaOstAlityka5(WpisView wpisView) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByMapotomkowMaSlownikPodatnik5").setParameter("mapotomkow", false).setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaPrzyporzadkowane(String pozycja, String bilansowewynikowe, Podatnik podatnik, Integer rok, boolean aktywa0pasywa1) {
        try {
            String aktywapasywa = "";
            if (aktywa0pasywa1) {
                aktywapasywa = "1";
            } else {
                aktywapasywa = "0";
            }
            if (bilansowewynikowe.equals("bilansowe")) {
                return getEntityManager().createNamedQuery("Konto.findByPozycjaBilansowe").setParameter("pozycja", pozycja).setParameter("aktywa0pasywa1", aktywa0pasywa1).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
            } else {
                return getEntityManager().createNamedQuery("Konto.findByPozycjaWynikowe").setParameter("pozycja", pozycja).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
            }
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
  
    public List<Konto> findKontaPrzyporzadkowaneAll(String bilansowewynikowe, Podatnik podatnik, Integer rok) {
        try {
             LoadGroup lg = new LoadGroup();
            lg.addAttribute("kontokategoria");
            if (bilansowewynikowe.equals("bilansowe")) {
                return Collections.synchronizedList( getEntityManager().createNamedQuery("Konto.findByPozycjaBilansoweAll").setParameter("podatnik", podatnik).setParameter("rok", rok)

                    .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
                //return Collections.synchronizedList( getEntityManager().createNamedQuery("Konto.findByPozycjaBilansoweAll").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
            } else {
                return Collections.synchronizedList( getEntityManager().createNamedQuery("Konto.findByPozycjaWynikoweAll").setParameter("podatnik", podatnik).setParameter("rok", rok)

                    .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
    //            return Collections.synchronizedList( getEntityManager().createNamedQuery("Konto.findByPozycjaWynikoweAll").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
            }
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    

    public List<Konto> findKontaPotomnePodatnik(Podatnik podatnik, Integer rok, Konto macierzyste) {
        try {
            LoadGroup lg = new LoadGroup();
            lg.addAttribute("kontokategoria");
            return getEntityManager().createNamedQuery("Konto.findByMacierzysteBOPodatnik").setParameter("macierzyste", macierzyste).setParameter("podatnik", podatnik).setParameter("rok", rok).setHint(QueryHints.LOAD_GROUP, lg).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

  
    public List<Konto> findKontaWszystkiePotomnePodatnik(List<Konto> listakontwszystkie, Podatnik podatnik, Integer rok, Konto macierzyste) {
        try {
            macierzyste.getAllChildren(listakontwszystkie, podatnik, rok, this);
            return listakontwszystkie;
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }


    public List<Konto> findKontaPotomne(Podatnik podatnik, Integer rok, Konto macierzyste, String bilansowewynikowe) {
        try {
            if (macierzyste == null) {
                if (bilansowewynikowe.equals("bilansowe")) {
                    return Collections.synchronizedList(getEntityManager().createNamedQuery("Konto.findByMacierzysteBilansoweNull").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
                } else {
                    return Collections.synchronizedList(getEntityManager().createNamedQuery("Konto.findByMacierzysteWynikoweNull").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
                }
            } else {
                if (bilansowewynikowe.equals("bilansowe")) {
                    return Collections.synchronizedList(getEntityManager().createNamedQuery("Konto.findByMacierzysteBilansowe").setParameter("macierzyste", macierzyste).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
                } else {
                    return Collections.synchronizedList(getEntityManager().createNamedQuery("Konto.findByMacierzysteWynikowe").setParameter("macierzyste", macierzyste).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
                }
            }
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }


    public List<Konto> findKontaMaSlownik(Podatnik podatnik, Integer rok, int idslownika) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByMaSlownik").setParameter("idslownika", idslownika).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Konto findKontoMacierzystyNrkonta(Podatnik podatnik, Integer rok, Konto kontomacierzyste, String numerkonta) {
        try {
            return (Konto)  getEntityManager().createNamedQuery("Konto.findKontoMacierzystyNrkonta").setParameter("kontomacierzyste", kontomacierzyste).setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("nrkonta", numerkonta).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public int resetujKolumneMapotomkow(WpisView wpisView) {
        try {
            return getEntityManager().createNamedQuery("Konto.updateMapotomkow").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).executeUpdate();
        } catch (Exception e) {
            E.e(e);
            return 1;
        }
    }

    public int resetujKolumneZablokowane(WpisView wpisView) {
        try {
            return getEntityManager().createNamedQuery("Konto.updateZablokowane").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).executeUpdate();
        } catch (Exception e) {
            E.e(e);
            return 1;
        }
    }

    public int policzPotomne(Podatnik podatnik, Integer rok, Konto macierzyste) {
        try {
            return Integer.parseInt(String.valueOf(getEntityManager().createNamedQuery("Konto.findByMacierzystePodatnikCOUNT").setParameter("macierzyste", macierzyste).setParameter("podatnik", podatnik).setParameter("rok", rok).getSingleResult()));
        } catch (Exception e) {
            E.e(e);
            return 0;
        }
    }

    public List<Konto> findListaKontRozrachunkowych(WpisView wpisView) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByMaSlownik").setParameter("idslownika", 1).setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaNazwaPodatnik(String nip, WpisView wpisView) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByNazwaPodatnik").setParameter("nazwaskrocona", nip).setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaSiostrzanePodatnik(Podatnik podatnik, Integer rok, Konto pelnynumer) {
        return getEntityManager().createNamedQuery("Konto.findBySiostrzaneBOPodatnik").setParameter("macierzyste", pelnynumer).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();

    }



    public List<Konto> findlistaKontKasaBank(WpisView wpisView) {
        return getEntityManager().createNamedQuery("Konto.findlistaKontKasaBank").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }

//    public List<Konto> findWszystkieKontaWzorcowy(WpisView wpisView) {
//        try {
//            return Collections.synchronizedList(kontoFacade.findKontaWzorcowy(wpisView));
//        } catch (Exception e) {
//            E.e(e);
//            return null;
//        }
//    }
//    
//    public List<Konto> findWszystkieKontaWzorcowy(Integer rok) {
//        try {
//            return Collections.synchronizedList(kontoFacade.findKontaWzorcowy(rok));
//        } catch (Exception e) {
//            E.e(e);
//            return null;
//        }
//    }


    public List<Konto> findWszystkieKontaPodatnikaBez0(Podatnik podatnik, String rok) {
        try {
            return getEntityManager().createNamedQuery("Konto.findByPodatnikBez0").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE).setHint(QueryHints.READ_ONLY, HintValues.TRUE).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public int findMaxLevelPodatnik(Podatnik podatnik, int rok) {
        return (int)  getEntityManager().createNamedQuery("Konto.findByMaxLevelPodatnik").setParameter("podatnik", podatnik).setParameter("rok", rok).getSingleResult();
    }

    public List<Konto> findKontazLevelu(Podatnik podatnikWpisu, int rokWpisu, int i) {
        return getEntityManager().createNamedQuery("Konto.findByLevelPodatnik").setParameter("level", i).setParameter("podatnik", podatnikWpisu).setParameter("rok", rokWpisu).getResultList();
    }

//    public List<Konto> findKontazLeveluWzorcowy(WpisView wpisView, int i) {
//        return Collections.synchronizedList(kontoFacade.findKontazLeveluWzorcowy(wpisView, i));
//    }

    public List<Konto> findSlownikoweKlienci(WpisView wpisView, Kliencifk kliencifk) {
        return getEntityManager().createNamedQuery("Konto.findByPodatnikKliencifk").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("nazwa", kliencifk.getNazwa()).setParameter("nip", kliencifk.getNip()).getResultList();
    }

//    public void zerujkontazLevelu(WpisView wpisView, int i) {
//        kontoFacade.zerujkontazLevelu(wpisView, i);
//    }

    public Konto findBySlownikoweMacierzyste(Konto konto, String nrkonta, WpisView wpisView) {
        return (Konto)  getEntityManager().createNamedQuery("Konto.findBySlownikoweMacierzyste").setParameter("kontomacierzyste", konto).setParameter("nrkonta", nrkonta).setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getSingleResult();
    }

    public void zerujsaldazaksiegowane(WpisView wpisView) {
        getEntityManager().createNamedQuery("Konto.wyzerujSaldaZaksiegowanewKontach").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).executeUpdate();
    }

}
