/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Konto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@Singleton
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
        } catch (Exception e) {
            return null;
        }
   }
    
    
    public List<Konto> findKontaRozrachunkowe(String podatnik){
       try {
            return kontoFacade.findKontaRozrachunkowe(podatnik);
        } catch (Exception e) {
            return null;
        }
   }
    
     public List<Konto> findKontaRozrachunkoweWszystkie(String podatnik){
       try {
            return kontoFacade.findKontaRozrachunkoweWszystkie(podatnik);
        } catch (Exception e) {
            return null;
        }
   }
    
    public List<Konto> findKontaVAT(String podatnik, int rok){
       try {
            return kontoFacade.findKontaVAT(podatnik, rok);
        } catch (Exception e) {
            return null;
        }
   }
    
    public List<Konto> findKontaSrodkiTrw(String podatnik, int rok){
       try {
            return kontoFacade.findKontaSrodkiTrw(podatnik, rok);
        } catch (Exception e) {
            return null;
        }
   }
    
    public List<Konto> findKontaRMK(String podatnik, int rok){
       try {
            return kontoFacade.findKontaRMK(podatnik, rok);
        } catch (Exception e) {
            return null;
        }
   }
    
    public List<Konto> findKontaRZiS(String p) {
        try {
            return kontoFacade.findKontaRZiS(p);
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Konto> findKontaGrupa3(String p) {
        try {
            return kontoFacade.findlistaKontGrupa3(p);
        } catch (Exception e) {
            return null;
        }
    }
    public List<Konto> findKontaGrupa0(String p) {
        try {
            return kontoFacade.findlistaKontGrupa0(p);
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Konto> findKontaGrupa4(String p, String rok) {
        try {
            return kontoFacade.findlistaKontGrupa4(p, rok);
        } catch (Exception e) {
            return null;
        }
    }
    
     public List<Konto> findKontaGrupa6(String p, String rok) {
        try {
            return kontoFacade.findlistaKontGrupa6(p, rok);
        } catch (Exception e) {
            return null;
        }
    }
    
   public Konto findKonto(String numer, String podatnik){
       try {
            return kontoFacade.findKonto(numer, podatnik);
        } catch (Exception e) {
            return null;
        }
   }
   
   public Konto findKontoNazwaPodatnik(String nazwaskrocona, String podatnik){
       try {
            return kontoFacade.findKontoNazwaPodatnik(nazwaskrocona, podatnik);
        } catch (Exception e) {
            return null;
        }
   }
   
   public Konto findKonto(int id){
       try {
            return kontoFacade.findKonto(id);
        } catch (Exception e) {
            return null;
        }
   }
   
    public List<Konto> findWszystkieKontaPodatnika(String podatnik, String rok){
       try {
            return kontoFacade.findKontoPodatnik(podatnik, rok); 
        } catch (Exception e) {
            return null;
        }
   }
    
    
    public List<Konto> findWszystkieKontaPodatnikaBO(String podatnik, String kategoriaKonta){
       try {
            return kontoFacade.findWszystkieKontaPodatnikaBO(podatnik, kategoriaKonta);
        } catch (Exception e) {
            return null;
        }
   }
    
    public List<Konto> findWszystkieKontaBilansowePodatnika(String podatnik){
       try {
            return kontoFacade.findWszystkieKontaBilansowePodatnika(podatnik);
        } catch (Exception e) {
            return null;
        }
   }
    
    public List<Konto> findKontaBilansowePodatnikaBezPotomkow(String podatnik){
            return kontoFacade.findKontaBilansowePodatnikaBezPotomkow(podatnik);
   }
    
    public List<Konto> findKonto860(String podatnik){
            return kontoFacade.findKonto860(podatnik);
   }
    
    public List<Konto> findKontaWynikowePodatnikaBezPotomkow(String podatnik){
            return kontoFacade.findKontaWynikowePodatnikaBezPotomkow(podatnik);
   }
    
    public Konto findKontoPodatnik490(String podatnik){
       try {
            return kontoFacade.findKontoPodatnik490(podatnik);
        } catch (Exception e) {
            return null;
        }
   }
   
   public List<Konto> findKontaOstAlityka (WpisView wpisView) {
      try {
            return kontoFacade.findKontaOstAlityka(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
        } catch (Exception e) {
            return null;
        } 
   }
   
   public List<Konto> findKontaOstAlityka5 (String podatnik) {
      try {
            return kontoFacade.findKontaOstAlityka5(podatnik);
        } catch (Exception e) {
            return null;
        } 
   }
    public List<Konto> findKontaPrzyporzadkowane (String pozycja, String bilansowewynikowe, String podatnik, boolean aktywa0pasywa1) {
      try {
          String aktywapasywa = "";
          if (aktywa0pasywa1) {
              aktywapasywa = "1";
          } else {
              aktywapasywa = "0";
          }
            return kontoFacade.findKontaPrzyporzadkowane(pozycja, bilansowewynikowe, podatnik, aktywapasywa);
        } catch (Exception e) {
            return null;
        } 
   }

    public List<Konto> findKontaPotomnePodatnik(String podatnik,String macierzyste) {
        try {
            return kontoFacade.findKontaPotomnePodatnik(podatnik, macierzyste);
        } catch (Exception e) {
            return null;
        } 
    }
    
    public List<Konto> findKontaWszystkiePotomnePodatnik(String podatnik,Konto macierzyste) {
        List<Konto> listakontwszystkie = new ArrayList<>();
        try {
            macierzyste.getAllChildren(listakontwszystkie, podatnik, kontoFacade);
            return listakontwszystkie;
        } catch (Exception e) {
            return null;
        } 
    }
    
    
   
    public List<Konto> findKontaPotomne(String podatnik,String macierzyste, String bilansowewynikowe) {
        try {
            return kontoFacade.findKontaPotomne(podatnik, macierzyste, bilansowewynikowe);
        } catch (Exception e) {
            return null;
        } 
    }
    
    public List<Konto> findKontaMaSlownik(String podatnik) {
        try {
            return kontoFacade.findKontaMaSlownik(podatnik);
        } catch (Exception e) {
            return null;
        } 
    }
    
    

    public int resetujKolumneMapotomkow(String podatnikWpisu) {
        try {
            return kontoFacade.resetujKolumneMapotomkow(podatnikWpisu);
        } catch (Exception e) {
            return 1;
        }
    }
    
    public int resetujKolumneZablokowane(String podatnikWpisu) {
        try {
            return kontoFacade.resetujKolumneZablokowane(podatnikWpisu);
        } catch (Exception e) {
            return 1;
        }
    }

    public int policzPotomne(String podatnik, String macierzyste) {
          try {
            return Integer.parseInt(String.valueOf(kontoFacade.findKontaPotomnePodatnikCount(podatnik, macierzyste)));
        } catch (Exception e) {
            return 0;
        } 
    }

    public List<Konto> findListaKontRozrachunkowych(String podatnik) {
         try {
            return kontoFacade.findKontaMaSlownik(podatnik);
        } catch (Exception e) {
            return null;
        } 
    }

    public List<Konto> findKontaNazwaPodatnik(String nip, String nazwapelna) {
         try {
            return kontoFacade.findKontaNazwaPodatnik(nip, nazwapelna);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Konto> findKontaSiostrzanePodatnik(String podatnikWpisu, String pelnynumer) {
           return kontoFacade.findKontaSiostrzanePodatnik(podatnikWpisu, pelnynumer);
        
    }

    public List<Konto> findlistaKontKasaBank(String p) {
        return kontoFacade.findlistaKontKasaBank(p);
    }

    public List<Konto> findWszystkieKontaWzorcowy() {
        try {
            return kontoFacade.findKontaWzorcowy();
        } catch (Exception e) {
            return null;
        } 
    }

    public List<Konto> findWszystkieKontaPodatnikaBez0(String podatnikWpisu, String rokWpisuSt) {
        try {
            return kontoFacade.findKontoPodatnikBez0(podatnikWpisu, rokWpisuSt);
        } catch (Exception e) {
            return null;
        } 
    }

   
 
}
