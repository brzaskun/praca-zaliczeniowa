/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Kliencifk;
import entityfk.Konto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class KontoDAOfk extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade kontoFacade;

    public KontoDAOfk() {
        super(Konto.class);
    }

    public KontoDAOfk(Class entityClass) {
        super(entityClass);
    }
    

    public  List<Konto> findAll(){
        try {
            return kontoFacade.findAll(Konto.class);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
   }
    
    
    public List<Konto> findKontaRozrachunkowe(WpisView wpisView){
       try {
            return kontoFacade.findKontaRozrachunkowe(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
   }
    
     public List<Konto> findKontaRozrachunkoweWszystkie(WpisView wpisView){
       try {
            return kontoFacade.findKontaRozrachunkoweWszystkie(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
   }
    
    public List<Konto> findKontaVAT(String podatnik, int rok){
       try {
            return kontoFacade.findKontaVAT(podatnik, rok);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
   }
    
    public List<Konto> findKontaSrodkiTrw(String podatnik, int rok){
       try {
            return kontoFacade.findKontaSrodkiTrw(podatnik, rok);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
   }
    
    public List<Konto> findKontaRMK(String podatnik, int rok){
       try {
            return kontoFacade.findKontaRMK(podatnik, rok);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
   }
    
    public List<Konto> findKontaRZiS(WpisView wpisView) {
        try {
            return kontoFacade.findKontaRZiS(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
    }
    
    public List<Konto> findKontaGrupa0(WpisView wpisView) {
        try {
            return kontoFacade.findlistaKontGrupa0(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
    }
    
    public List<Konto> findKontaGrupa1(WpisView wpisView) {
        try {
            return kontoFacade.findlistaKontGrupa1(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
    }
    
    public List<Konto> findKontaGrupa2(WpisView wpisView) {
        try {
            return kontoFacade.findlistaKontGrupa2(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
    }
    
    public List<Konto> findKontaGrupa3(WpisView wpisView) {
        try {
            return kontoFacade.findlistaKontGrupa3(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
    }
    
    public List<Konto> findKontaGrupa4(WpisView wpisView) {
        try {
            return kontoFacade.findlistaKontGrupa4(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
    }
    
    public List<Konto> findKontaGrupa5(WpisView wpisView) {
        try {
            return kontoFacade.findlistaKontGrupa5(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
    }
    
     public List<Konto> findKontaGrupa6(WpisView wpisView) {
        try {
            return kontoFacade.findlistaKontGrupa6(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
    }
     
     public List<Konto> findKontaGrupa7(WpisView wpisView) {
        try {
            return kontoFacade.findlistaKontGrupa7(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
    }
     
     public List<Konto> findKontaGrupa8(WpisView wpisView) {
        try {
            return kontoFacade.findlistaKontGrupa8(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
    }
    
   public Konto findKonto(String numer, WpisView wpisView){
       try {
            return kontoFacade.findKonto(numer, wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
   }
   
   public Konto findKontoWzorcowy(String numer, WpisView wpisView){
       try {
            return kontoFacade.findKontoWzorcowy(numer, wpisView.getRokWpisu());
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
   }
   
   public Konto findKontoNazwaPodatnik(String nazwaskrocona, WpisView wpisView){
       try {
            return kontoFacade.findKontoNazwaPodatnik(nazwaskrocona, wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
   }
   
   public Konto findKonto(int id){
       try {
            return kontoFacade.findKonto(id);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
   }
   
    public List<Konto> findWszystkieKontaPodatnika(String podatnik, String rok){
       try {
            return kontoFacade.findKontoPodatnik(podatnik, rok); 
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
   }
    
    
    public List<Konto> findWszystkieKontaPodatnikaBO(WpisView wpisView, String kategoriaKonta){
       try {
            return kontoFacade.findWszystkieKontaPodatnikaBO(wpisView, kategoriaKonta);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
   }
    
    public List<Konto> findWszystkieKontaBilansowePodatnika(WpisView wpisView){
       try {
            return kontoFacade.findWszystkieKontaBilansowePodatnika(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
   }
    
    public List<Konto> findWszystkieKontaWynikowePodatnika(WpisView wpisView){
       try {
            return kontoFacade.findWszystkieKontaWynikowePodatnika(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
   }
    
    public void wyzerujPozycjeWKontach(WpisView wpisView, String bilansowewynikowe) {
        kontoFacade.wyzerujPozycjeWKontach(wpisView, bilansowewynikowe);
    }
    
    public List<Konto> findKontaBilansowePodatnikaBezPotomkow(WpisView wpisView){
            return kontoFacade.findKontaBilansowePodatnikaBezPotomkow(wpisView);
   }
    
    public Konto findKonto860(WpisView wpisView){
            return kontoFacade.findKonto860(wpisView);
   }
    
    public List<Konto> findKontaWynikowePodatnikaBezPotomkow(WpisView wpisView){
            return kontoFacade.findKontaWynikowePodatnikaBezPotomkow(wpisView);
   }
    
    public Konto findKontoPodatnik490(WpisView wpisView){
       try {
            return kontoFacade.findKontoPodatnik490(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
   }
   
   public List<Konto> findKontaOstAlityka (WpisView wpisView) {
      try {
            return kontoFacade.findKontaOstAlityka(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        } 
   }
   
   public List<Konto> findKontaOstAlitykaWynikowe(WpisView wpisView) {
      try {
            return kontoFacade.findKontaOstAlitykaWynikowe(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        } 
   }
   
   public List<Konto> findKontaOstAlityka5 (WpisView wpisView) {
      try {
            return kontoFacade.findKontaOstAlityka5(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        } 
   }
    public List<Konto> findKontaPrzyporzadkowane (String pozycja, String bilansowewynikowe, WpisView wpisView, boolean aktywa0pasywa1) {
      try {
          String aktywapasywa = "";
          if (aktywa0pasywa1) {
              aktywapasywa = "1";
          } else {
              aktywapasywa = "0";
          }
            return kontoFacade.findKontaPrzyporzadkowane(pozycja, bilansowewynikowe, wpisView, aktywapasywa);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        } 
   }
    
    public List<Konto> findKontaPrzyporzadkowaneWzorcowy (String pozycja, String bilansowewynikowe, WpisView wpisView, boolean aktywa0pasywa1) {
      try {
          String aktywapasywa = "";
          if (aktywa0pasywa1) {
              aktywapasywa = "1";
          } else {
              aktywapasywa = "0";
          }
            return kontoFacade.findKontaPrzyporzadkowaneWzorcowy(pozycja, bilansowewynikowe, wpisView, aktywapasywa);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        } 
   }
    

    public List<Konto> findKontaPotomnePodatnik(WpisView wpisView,String macierzyste) {
        try {
            return kontoFacade.findKontaPotomnePodatnik(wpisView, macierzyste);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        } 
    }
    
     public List<Konto> findKontaPotomneWzorcowy(WpisView wpisView,String macierzyste) {
        try {
            return kontoFacade.findKontaPotomneWzorcowy(wpisView, macierzyste);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        } 
    }
    
    public List<Konto> findKontaWszystkiePotomnePodatnik(WpisView wpisView,Konto macierzyste) {
        List<Konto> listakontwszystkie = new ArrayList<>();
        try {
            macierzyste.getAllChildren(listakontwszystkie, wpisView, kontoFacade);
            return listakontwszystkie;
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        } 
    }
    
    public List<Konto> findKontaWszystkiePotomneWzorcowy(WpisView wpisView,Konto macierzyste) {
        List<Konto> listakontwszystkie = new ArrayList<>();
        try {
            macierzyste.getAllChildrenWzorcowy(listakontwszystkie, wpisView, kontoFacade);
            return listakontwszystkie;
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        } 
    }
    public List<Konto> findKontaWszystkiePotomneRok(WpisView wpisView,Konto macierzyste) {
        List<Konto> listakontwszystkie = new ArrayList<>();
        try {
            macierzyste.getAllChildrenRok(listakontwszystkie, wpisView, kontoFacade);
            return listakontwszystkie;
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        } 
    }
    
   
    
    
   
    public List<Konto> findKontaPotomne(WpisView wpisView,String macierzyste, String bilansowewynikowe) {
        try {
            return kontoFacade.findKontaPotomne(wpisView, macierzyste, bilansowewynikowe);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        } 
    }
    
    public List<Konto> findKontaPotomneWzorcowy(WpisView wpisView,String macierzyste, String bilansowewynikowe) {
        try {
            return kontoFacade.findKontaPotomneWzorcowy(wpisView, macierzyste, bilansowewynikowe);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        } 
    }
    
    public List<Konto> findKontaMaSlownik(WpisView wpisView, int idslownika) {
        try {
            return kontoFacade.findKontaMaSlownik(wpisView, idslownika);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        } 
    }
    
    

    public int resetujKolumneMapotomkow(WpisView wpisView) {
        try {
            return kontoFacade.resetujKolumneMapotomkow(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return 1;
        }
    }
    
    public int resetujKolumneZablokowane(WpisView wpisView) {
        try {
            return kontoFacade.resetujKolumneZablokowane(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return 1;
        }
    }

    public int policzPotomne(WpisView wpisView, String macierzyste) {
          try {
            return Integer.parseInt(String.valueOf(kontoFacade.findKontaPotomnePodatnikCount(wpisView, macierzyste)));
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return 0;
        } 
    }
    public int policzPotomneWzorcowy(WpisView wpisView, String macierzyste) {
          try {
            return Integer.parseInt(String.valueOf(kontoFacade.findKontaPotomneWzorcowyCount(wpisView, macierzyste)));
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return 0;
        } 
    }
    

    public List<Konto> findListaKontRozrachunkowych(WpisView wpisView) {
         try {
            return kontoFacade.findKontaMaSlownik(wpisView, 1);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        } 
    }

    public List<Konto> findKontaNazwaPodatnik(String nip, WpisView wpisView) {
         try {
            return kontoFacade.findKontaNazwaPodatnik(nip, wpisView);
        } catch (Exception e) { 
            System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
    }

    public List<Konto> findKontaSiostrzanePodatnik(WpisView wpisView, String pelnynumer) {
           return kontoFacade.findKontaSiostrzanePodatnik(wpisView, pelnynumer);
        
    }
    
    public List<Konto> findKontaSiostrzaneWzorcowy(WpisView wpisView, String pelnynumer) {
           return kontoFacade.findKontaSiostrzaneWzorcowy(wpisView, pelnynumer);
        
    }

    public List<Konto> findlistaKontKasaBank(WpisView wpisView) {
        return kontoFacade.findlistaKontKasaBank(wpisView);
    }

    public List<Konto> findWszystkieKontaWzorcowy(WpisView wpisView) {
        try {
            return kontoFacade.findKontaWzorcowy(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        } 
    }
    
    public List<Konto> findWszystkieKontaWynikoweWzorcowy(WpisView wpisView) {
        try {
            return kontoFacade.findKontaWynikoweWzorcowy(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        } 
    }
    
     public List<Konto> findWszystkieKontaBilansoweWzorcowy(WpisView wpisView) {
        try {
            return kontoFacade.findKontaBilansoweWzorcowy(wpisView);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        } 
    }

    public List<Konto> findWszystkieKontaPodatnikaBez0(String podatnikWpisu, String rokWpisuSt) {
        try {
            return kontoFacade.findKontoPodatnikBez0(podatnikWpisu, rokWpisuSt);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        } 
    }
    
    public int findMaxLevelPodatnik(String podatnikWpisu, int rokWpisu) {
        return kontoFacade.findMaxLevelPodatnik(podatnikWpisu, rokWpisu);
    }

    public List<Konto> findKontazLevelu(WpisView wpisView, int i) {
        return kontoFacade.findKontazLevelu(wpisView, i);
    }
    
    public List<Konto> findKontazLeveluRok(WpisView wpisView, int i) {
        return kontoFacade.findKontazLeveluRok(wpisView, i);
    }
   
    public List<Konto> findSlownikoweKlienci(WpisView wpisView, Kliencifk kliencifk) {
        return kontoFacade.findSlownikoweKlienci(wpisView, kliencifk);
    }

    public void zerujkontazLevelu(WpisView wpisView, int i) {
        kontoFacade.zerujkontazLevelu(wpisView, i);
    }
 
}
