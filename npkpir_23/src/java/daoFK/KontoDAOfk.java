/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entity.Podatnik;
import entityfk.Kliencifk;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named

public class KontoDAOfk extends DAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SessionFacade kontoFacade;

    public KontoDAOfk() {
        super(Konto.class);
    }

    public KontoDAOfk(Class entityClass) {
        super(entityClass);
    }

    public List<Konto> findAll() {
        try {
            return Collections.synchronizedList(kontoFacade.findAll(Konto.class));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaRozrachunkowe(WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontaRozrachunkowe(wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaRozrachunkoweZpotomkami(WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontaRozrachunkoweZpotomkami(wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaRozrachunkoweWszystkie(WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontaRozrachunkoweWszystkie(wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaVAT(Podatnik podatnik, int rok) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontaVAT(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaSrodkiTrw(Podatnik podatnik, int rok) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontaSrodkiTrw(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaRMK(Podatnik podatnik, int rok) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontaRMK(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaRZiS(WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontaRZiS(wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaGrupa0(WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findlistaKontGrupa0(wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaGrupa0Analityka(WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findlistaKontGrupa0Analityka(wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaGrupa1(WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findlistaKontGrupa1(wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaGrupa2(WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findlistaKontGrupa2(wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaGrupa3(WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findlistaKontGrupa3(wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaGrupa4(WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findlistaKontGrupa4(wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaGrupa5(WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findlistaKontGrupa5(wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaGrupa6(WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findlistaKontGrupa6(wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaGrupa7(WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findlistaKontGrupa7(wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaGrupa8(WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findlistaKontGrupa8(wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Konto findKonto(String numer, Podatnik podatnik, Integer rok) {
        Konto zwrot = null;
        try {
            zwrot = kontoFacade.findKonto(numer, podatnik, rok);
        } catch (Exception e) {
        }
        return zwrot;
    }

    public Konto findKonto(String numer, Podatnik podatnik, String rok) {
        try {
            return kontoFacade.findKonto(numer, podatnik, Integer.parseInt(rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Konto findKontoNazwaPodatnik(String nazwaskrocona, WpisView wpisView) {
        try {
            return kontoFacade.findKontoNazwaPodatnik(nazwaskrocona, wpisView);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Konto findKontoNazwaPelnaPodatnik(String nazwapelna, WpisView wpisView) {
        try {
            return kontoFacade.findKontoNazwaPelnaPodatnik(nazwapelna, wpisView);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Konto findKonto(int id) {
        try {
            return kontoFacade.findKonto(id);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Konto findKonto2(int id) {
        try {
            return kontoFacade.findKonto2(id);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaPodatnika(Podatnik podatnik, String rok) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontoPodatnik(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findWszystkieKontaPodatnikaPobierzRelacje(Podatnik podatnik, String rok) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontoPodatnikImplementacja(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findWszystkieKontaPodatnikaEager(Podatnik podatnik, String rok) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontoPodatnikEager(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findWszystkieKontaPodatnikaKsiegi(Podatnik podatnik, String rok) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontoPodatnikKsiegi(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaPodatnikaBezSlownik(Podatnik podatnik, String rok) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontoPodatnikBezSlownik(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findWszystkieKontaPodatnikaBezSlownikKsiegi(Podatnik podatnik, String rok) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontoPodatnikBezSlownikKsiegi(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaPodatnikaTylkoSlownik(Podatnik podatnik, String rok) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontoPodatnikTylkoSlownik(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaPodatnikaBO(WpisView wpisView, String kategoriaKonta) {
        try {
            return Collections.synchronizedList(kontoFacade.findWszystkieKontaPodatnikaBO(wpisView, kategoriaKonta));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaBilansowePodatnika(Podatnik podatnik, String rok) {
        try {
            return Collections.synchronizedList(kontoFacade.findWszystkieKontaBilansowePodatnika(podatnik, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findWszystkieKontaWynikowePodatnika(Podatnik podatnik, String rok) {
        try {
            return Collections.synchronizedList(kontoFacade.findWszystkieKontaWynikowePodatnika(podatnik, rok));
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
        kontoFacade.wyzerujBoWnBoMawKontach(wpisView, bilansowewynikowe);
    }
    
    public void wyzerujBoWnBoMawKontach(WpisView wpisView) {
        kontoFacade.wyzerujBoWnBoMawKontach(wpisView);
    }

//    public void wyzerujPozycjeWKontachWzorcowy(UkladBR uklad, String bilansowewynikowe) {
//        kontoFacade.wyzerujPozycjeWKontachWzorcowy(uklad, bilansowewynikowe));
//    }
    public List<Konto> findKontaBilansowePodatnikaBezPotomkow(WpisView wpisView) {
        return Collections.synchronizedList(kontoFacade.findKontaBilansowePodatnikaBezPotomkow(wpisView));
    }
    
    public List<Konto> findKontaBilansowePodatnikaBezPotomkowRokPoprzedni(WpisView wpisView) {
        return Collections.synchronizedList(kontoFacade.findKontaBilansowePodatnikaBezPotomkowRokPoprzedni(wpisView));
    }
    
    public List<Konto> findKontaBilansowePodatnikaKwotaBezPotomkow(WpisView wpisView) {
        return Collections.synchronizedList(kontoFacade.findKontaBilansowePodatnikaKwotaBezPotomkow(wpisView));
    }

    public Konto findKonto860(WpisView wpisView) {
        return kontoFacade.findKonto860(wpisView);
    }

    public List<Konto> findKontaWynikowePodatnikaBezPotomkow(WpisView wpisView) {
        return Collections.synchronizedList(kontoFacade.findKontaWynikowePodatnikaBezPotomkow(wpisView));
    }
    public List<Konto> findKontaPodatnikZPotomkani(WpisView wpisView) {
        return Collections.synchronizedList(kontoFacade.findKontaPodatnikZPotomkami(wpisView));
    }
    
    public List<Konto> findKontaWynikowePodatnikaBezPotomkowRokPop(WpisView wpisView) {
        return Collections.synchronizedList(kontoFacade.findKontaWynikowePodatnikaBezPotomkowRokPop(wpisView));
    }

    public Konto findKontoPodatnik490(WpisView wpisView) {
        try {
            return kontoFacade.findKontoPodatnik490(wpisView);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaOstAlityka(WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontaOstAlityka(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findKontaOstAlitykaRokPop(WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontaOstAlityka(wpisView.getPodatnikObiekt(), wpisView.getRokUprzedni()));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaOstAlitykaWynikowe(WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontaOstAlitykaWynikowe(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaOstAlityka5(WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontaOstAlityka5(wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaPrzyporzadkowane(String pozycja, String bilansowewynikowe, WpisView wpisView, boolean aktywa0pasywa1) {
        try {
            String aktywapasywa = "";
            if (aktywa0pasywa1) {
                aktywapasywa = "1";
            } else {
                aktywapasywa = "0";
            }
            return Collections.synchronizedList(kontoFacade.findKontaPrzyporzadkowane(pozycja, bilansowewynikowe, wpisView, aktywapasywa));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findKontaPrzyporzadkowaneAll(String bilansowewynikowe, WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontaPrzyporzadkowaneAll(bilansowewynikowe, wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Konto> findKontaPrzyporzadkowaneWzorcowyAll(String bilansowewynikowe, int rok) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontaPrzyporzadkowaneWzorcowyAll(bilansowewynikowe, rok));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaPrzyporzadkowaneWzorcowy(String pozycja, String bilansowewynikowe, int rok, boolean aktywa0pasywa1) {
        try {
            String aktywapasywa = "";
            if (aktywa0pasywa1) {
                aktywapasywa = "1";
            } else {
                aktywapasywa = "0";
            }
            return Collections.synchronizedList(kontoFacade.findKontaPrzyporzadkowaneWzorcowy(pozycja, bilansowewynikowe, rok, aktywapasywa));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaPotomnePodatnik(Podatnik podatnik, Integer rok, String macierzyste) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontaPotomnePodatnik(podatnik, rok, macierzyste));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaPotomnePodatnik(Podatnik podatnik, String rok, String macierzyste) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontaPotomnePodatnik(podatnik, Integer.parseInt(rok), macierzyste));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaWszystkiePotomnePodatnik(List<Konto> listakontwszystkie, Podatnik podatnik, Integer rok, Konto macierzyste) {
        try {
            macierzyste.getAllChildren(listakontwszystkie, podatnik, rok, kontoFacade);
            return Collections.synchronizedList(listakontwszystkie);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }


    public List<Konto> findKontaPotomne(Podatnik podatnik, Integer rok, String macierzyste, String bilansowewynikowe) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontaPotomne(podatnik, rok, macierzyste, bilansowewynikowe));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaMaSlownik(Podatnik podatnik, Integer rok, int idslownika) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontaMaSlownik(podatnik, rok, idslownika));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public Konto findKontoMacierzystyNrkonta(Podatnik podatnik, Integer rok, Konto kontomacierzyste, String numerkonta) {
        try {
            return kontoFacade.findKontoMacierzystyNrkonta(podatnik, rok, kontomacierzyste, numerkonta);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public int resetujKolumneMapotomkow(WpisView wpisView) {
        try {
            return kontoFacade.resetujKolumneMapotomkow(wpisView);
        } catch (Exception e) {
            E.e(e);
            return 1;
        }
    }

    public int resetujKolumneZablokowane(WpisView wpisView) {
        try {
            return kontoFacade.resetujKolumneZablokowane(wpisView);
        } catch (Exception e) {
            E.e(e);
            return 1;
        }
    }

    public int policzPotomne(Podatnik podatnik, Integer rok, String macierzyste) {
        try {
            return Integer.parseInt(String.valueOf(kontoFacade.findKontaPotomnePodatnikCount(podatnik, rok, macierzyste)));
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
            return Collections.synchronizedList(kontoFacade.findKontaMaSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), 1));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaNazwaPodatnik(String nip, WpisView wpisView) {
        try {
            return Collections.synchronizedList(kontoFacade.findKontaNazwaPodatnik(nip, wpisView));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaSiostrzanePodatnik(WpisView wpisView, String pelnynumer) {
        return Collections.synchronizedList(kontoFacade.findKontaSiostrzanePodatnik(wpisView, pelnynumer));

    }

    public List<Konto> findKontaSiostrzaneWzorcowy(WpisView wpisView, String pelnynumer) {
        return Collections.synchronizedList(kontoFacade.findKontaSiostrzaneWzorcowy(wpisView, pelnynumer));

    }

    public List<Konto> findlistaKontKasaBank(WpisView wpisView) {
        return Collections.synchronizedList(kontoFacade.findlistaKontKasaBank(wpisView));
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
            return Collections.synchronizedList(kontoFacade.findKontoPodatnikBez0(podatnikWpisu, rokWpisuSt));
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public int findMaxLevelPodatnik(Podatnik podatnikWpisu, int rokWpisu) {
        return kontoFacade.findMaxLevelPodatnik(podatnikWpisu, rokWpisu);
    }

    public List<Konto> findKontazLevelu(Podatnik podatnikWpisu, int rokWpisu, int i) {
        return Collections.synchronizedList(kontoFacade.findKontazLevelu(podatnikWpisu, rokWpisu, i));
    }

//    public List<Konto> findKontazLeveluWzorcowy(WpisView wpisView, int i) {
//        return Collections.synchronizedList(kontoFacade.findKontazLeveluWzorcowy(wpisView, i));
//    }

    public List<Konto> findKontazLeveluRok(WpisView wpisView, int i) {
        return Collections.synchronizedList(kontoFacade.findKontazLeveluRok(wpisView, i));
    }

    public List<Konto> findSlownikoweKlienci(WpisView wpisView, Kliencifk kliencifk) {
        return Collections.synchronizedList(kontoFacade.findSlownikoweKlienci(wpisView, kliencifk));
    }

    public void zerujkontazLevelu(WpisView wpisView, int i) {
        kontoFacade.zerujkontazLevelu(wpisView, i);
    }

    public Konto findBySlownikoweMacierzyste(Konto konto, String nrkonta, WpisView wpisView) {
        return kontoFacade.findByKontoSlownikoweMacierzyste(konto, nrkonta, wpisView);
    }

    public void zerujsaldazaksiegowane(WpisView wpisView) {
        kontoFacade.wyzerujSaldaZaksiegowanewKontach(wpisView);
    }

}
