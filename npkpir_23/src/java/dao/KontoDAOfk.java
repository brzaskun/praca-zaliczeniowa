/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.DAO;
import entity.Podatnik;
import entityfk.Kliencifk;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ejb.Stateless;import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.SessionFacade;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Stateless
@Transactional

public class KontoDAOfk extends DAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SessionFacade sessionFacade;
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
   

    public List<Konto> findAll() {
        try {
            return Collections.synchronizedList(sessionFacade.findAll(Konto.class));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaRozrachunkowe(WpisView wpisView) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaRozrachunkowe(wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaRozrachunkoweZpotomkami(WpisView wpisView) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaRozrachunkoweZpotomkami(wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaRozrachunkoweWszystkie(WpisView wpisView) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaRozrachunkoweWszystkie(wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaVAT(Podatnik podatnik, int rok) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaVAT(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaSrodkiTrw(Podatnik podatnik, int rok) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaSrodkiTrw(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaRMK(Podatnik podatnik, int rok) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaRMK(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaRZiS(WpisView wpisView) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaRZiS(wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaGrupa(WpisView wpisView, String grupa) {
        try {
            return Collections.synchronizedList(sessionFacade.findlistaKontGrupa(wpisView, grupa));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaGrupaAnalityka(WpisView wpisView, String grupa) {
        try {
            return Collections.synchronizedList(sessionFacade.findlistaKontGrupaAnalityka(wpisView, grupa));
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
            zwrot = sessionFacade.findKonto(numer, podatnik, rok);
        } catch (Exception e) {
        }
        return zwrot;
    }


    public Konto findKontoNazwaPodatnik(String nazwaskrocona, WpisView wpisView) {
        try {
            return sessionFacade.findKontoNazwaPodatnik(nazwaskrocona, wpisView);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Konto findKontoNazwaPelnaPodatnik(String nazwapelna, WpisView wpisView) {
        try {
            return sessionFacade.findKontoNazwaPelnaPodatnik(nazwapelna, wpisView);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Konto findKonto(int id) {
        try {
            return sessionFacade.findKonto(id);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Konto findKonto2(int id) {
        try {
            return sessionFacade.findKonto2(id);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaPodatnika(Podatnik podatnik, String rok) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontoPodatnik(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findWszystkieKontaPodatnikaBezPrzyporzadkowania(Podatnik podatnik, String rok) {
        try {
            return sessionFacade.findKontoPodatnikBezPrzyporzadkowania(podatnik, rok);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findWszystkieKontaPodatnikaRO(Podatnik podatnik, String rok) {
        try {
            return sessionFacade.findKontoPodatnikRO(podatnik, rok);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findWszystkieKontaPodatnikaPobierzRelacje(Podatnik podatnik, String rok) {
        try {
            return sessionFacade.findKontoPodatnikRelacje(podatnik, rok);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
      
    public List<Konto> findWszystkieKontaPodatnikaKsiegi(Podatnik podatnik, String rok) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontoPodatnikKsiegi(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaPodatnikaBezSlownik(Podatnik podatnik, String rok) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontoPodatnikBezSlownik(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findWszystkieKontaPodatnikaBezSlownikEdycja(Podatnik podatnik, String rok) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontoPodatnikBezSlownikEdycja(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findWszystkieKontaPodatnikaBezSlownikKsiegi(Podatnik podatnik, String rok) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontoPodatnikBezSlownikKsiegi(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaPodatnikaTylkoSlownik(Podatnik podatnik, String rok) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontoPodatnikTylkoSlownik(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaPodatnikaBO(WpisView wpisView, String kategoriaKonta) {
        try {
            return Collections.synchronizedList(sessionFacade.findWszystkieKontaPodatnikaBO(wpisView, kategoriaKonta));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaBilansowePodatnika(Podatnik podatnik, String rok) {
        try {
            return Collections.synchronizedList(sessionFacade.findWszystkieKontaBilansowePodatnika(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaWynikowePodatnika(Podatnik podatnik, String rok) {
        try {
            return Collections.synchronizedList(sessionFacade.findWszystkieKontaWynikowePodatnika(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findWszystkieKontaSlownikowePodatnika(Podatnik podatnik, Integer rok) {
        try {
            return Collections.synchronizedList(sessionFacade.getEntityManager().createNamedQuery("Konto.findByPodatnikTylkoSlownikZero").setParameter("rok", rok).setParameter("podatnik", podatnik).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }


    public void wyzerujBoWnBoMawKontach(WpisView wpisView, String bilansowewynikowe) {
        sessionFacade.wyzerujBoWnBoMawKontach(wpisView, bilansowewynikowe);
    }
    
    public void wyzerujBoWnBoMawKontach(WpisView wpisView) {
        sessionFacade.wyzerujBoWnBoMawKontach(wpisView);
    }

//    public void wyzerujPozycjeWKontachWzorcowy(UkladBR uklad, String bilansowewynikowe) {
//        kontoFacade.wyzerujPozycjeWKontachWzorcowy(uklad, bilansowewynikowe));
//    }
    public List<Konto> findKontaBilansowePodatnikaBezPotomkow(WpisView wpisView) {
        return Collections.synchronizedList(sessionFacade.findKontaBilansowePodatnikaBezPotomkow(wpisView));
    }
    
    public List<Konto> findKontaBilansowePodatnikaBezPotomkowRokPoprzedni(WpisView wpisView) {
        return Collections.synchronizedList(sessionFacade.findKontaBilansowePodatnikaBezPotomkowRokPoprzedni(wpisView));
    }
    
    public List<Konto> findKontaBilansowePodatnikaKwotaBezPotomkow(WpisView wpisView) {
        return Collections.synchronizedList(sessionFacade.findKontaBilansowePodatnikaKwotaBezPotomkow(wpisView));
    }

    public Konto findKonto860(WpisView wpisView) {
        return sessionFacade.findKonto860(wpisView);
    }

    public List<Konto> findKontaWynikowePodatnikaBezPotomkow(WpisView wpisView) {
        return Collections.synchronizedList(sessionFacade.findKontaWynikowePodatnikaBezPotomkow(wpisView));
    }
    public List<Konto> findKontaPodatnikZPotomkani(WpisView wpisView) {
        return Collections.synchronizedList(sessionFacade.findKontaPodatnikZPotomkami(wpisView));
    }
    
    public List<Konto> findKontaWynikowePodatnikaBezPotomkowRokPop(WpisView wpisView) {
        return Collections.synchronizedList(sessionFacade.findKontaWynikowePodatnikaBezPotomkowRokPop(wpisView));
    }

    public Konto findKontoPodatnik490(WpisView wpisView) {
        try {
            return sessionFacade.findKontoPodatnik490(wpisView);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaOstAlityka(WpisView wpisView) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaOstAlityka(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findKontaOstAlityka(Podatnik podatnik, Integer rok) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaOstAlityka(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findKontaOstAlitykaRO(WpisView wpisView) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaOstAlitykaRO(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findKontaOstAlitykaRokPop(WpisView wpisView) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaOstAlityka(wpisView.getPodatnikObiekt(), wpisView.getRokUprzedni()));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findKontaOstAlitykaRokPop(Podatnik podatnik, Integer rokuprzedni) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaOstAlityka(podatnik, rokuprzedni));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findKontaOstAlitykaRokPopRO(WpisView wpisView) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaOstAlitykaRO(wpisView.getPodatnikObiekt(), wpisView.getRokUprzedni()));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaOstAlitykaWynikowe(WpisView wpisView) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaOstAlitykaWynikowe(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaOstAlityka5(WpisView wpisView) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaOstAlityka5(wpisView));
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
            return Collections.synchronizedList(sessionFacade.findKontaPrzyporzadkowane(pozycja, bilansowewynikowe, podatnik, rok, aktywapasywa));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findKontaPrzyporzadkowaneAll(String bilansowewynikowe, Podatnik podatnik, Integer rok) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaPrzyporzadkowaneAll(bilansowewynikowe, podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
   
    public List<Konto> findKontaPotomnePodatnik(Podatnik podatnik, Integer rok, Konto macierzyste) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaPotomnePodatnik(podatnik, rok, macierzyste));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaPotomnePodatnik(Podatnik podatnik, String rok, Konto macierzyste) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaPotomnePodatnik(podatnik, Integer.parseInt(rok), macierzyste));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaWszystkiePotomnePodatnik(List<Konto> listakontwszystkie, Podatnik podatnik, Integer rok, Konto macierzyste) {
        try {
            macierzyste.getAllChildren(listakontwszystkie, podatnik, rok, sessionFacade);
            return Collections.synchronizedList(listakontwszystkie);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }


    public List<Konto> findKontaPotomne(Podatnik podatnik, Integer rok, Konto macierzyste, String bilansowewynikowe) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaPotomne(podatnik, rok, macierzyste, bilansowewynikowe));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaMaSlownik(Podatnik podatnik, Integer rok, int idslownika) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaMaSlownik(podatnik, rok, idslownika));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public Konto findKontoMacierzystyNrkonta(Podatnik podatnik, Integer rok, Konto kontomacierzyste, String numerkonta) {
        try {
            return sessionFacade.findKontoMacierzystyNrkonta(podatnik, rok, kontomacierzyste, numerkonta);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public int resetujKolumneMapotomkow(WpisView wpisView) {
        try {
            return sessionFacade.resetujKolumneMapotomkow(wpisView);
        } catch (Exception e) {
            E.e(e);
            return 1;
        }
    }

    public int resetujKolumneZablokowane(WpisView wpisView) {
        try {
            return sessionFacade.resetujKolumneZablokowane(wpisView);
        } catch (Exception e) {
            E.e(e);
            return 1;
        }
    }

    public int policzPotomne(Podatnik podatnik, Integer rok, Konto macierzyste) {
        try {
            return Integer.parseInt(String.valueOf(sessionFacade.findKontaPotomnePodatnikCount(podatnik, rok, macierzyste)));
        } catch (Exception e) {
            E.e(e);
            return 0;
        }
    }

//    public int policzPotomneWzorcowy(WpisView wpisView, String macierzyste) {
//        try {
//            return Integer.parseInt(String.valueOf(kontoFacade.findKontaPotomneWzorcowyCount(wpisView, macierzyste)));
//        } catch (Exception e) {
//            E.e(e);
//            return 0;
//        }
//    }

    public List<Konto> findListaKontRozrachunkowych(WpisView wpisView) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaMaSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), 1));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaNazwaPodatnik(String nip, WpisView wpisView) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontaNazwaPodatnik(nip, wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaSiostrzanePodatnik(Podatnik podatnik, Integer rok, Konto pelnynumer) {
        return Collections.synchronizedList(sessionFacade.findKontaSiostrzanePodatnik(podatnik, rok, pelnynumer));

    }



    public List<Konto> findlistaKontKasaBank(WpisView wpisView) {
        return Collections.synchronizedList(sessionFacade.findlistaKontKasaBank(wpisView));
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

    public List<Konto> findWszystkieKontaPodatnikaBez0(Podatnik podatnikWpisu, String rokWpisuSt) {
        try {
            return Collections.synchronizedList(sessionFacade.findKontoPodatnikBez0(podatnikWpisu, rokWpisuSt));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public int findMaxLevelPodatnik(Podatnik podatnikWpisu, int rokWpisu) {
        return sessionFacade.findMaxLevelPodatnik(podatnikWpisu, rokWpisu);
    }

    public List<Konto> findKontazLevelu(Podatnik podatnikWpisu, int rokWpisu, int i) {
        return Collections.synchronizedList(sessionFacade.findKontazLevelu(podatnikWpisu, rokWpisu, i));
    }

//    public List<Konto> findKontazLeveluWzorcowy(WpisView wpisView, int i) {
//        return Collections.synchronizedList(kontoFacade.findKontazLeveluWzorcowy(wpisView, i));
//    }

    public List<Konto> findKontazLeveluRok(WpisView wpisView, int i) {
        return Collections.synchronizedList(sessionFacade.findKontazLeveluRok(wpisView, i));
    }

    public List<Konto> findSlownikoweKlienci(WpisView wpisView, Kliencifk kliencifk) {
        return Collections.synchronizedList(sessionFacade.findSlownikoweKlienci(wpisView, kliencifk));
    }

//    public void zerujkontazLevelu(WpisView wpisView, int i) {
//        kontoFacade.zerujkontazLevelu(wpisView, i);
//    }

    public Konto findBySlownikoweMacierzyste(Konto konto, String nrkonta, WpisView wpisView) {
        return sessionFacade.findByKontoSlownikoweMacierzyste(konto, nrkonta, wpisView);
    }

    public void zerujsaldazaksiegowane(WpisView wpisView) {
        sessionFacade.wyzerujSaldaZaksiegowanewKontach(wpisView);
    }

}
