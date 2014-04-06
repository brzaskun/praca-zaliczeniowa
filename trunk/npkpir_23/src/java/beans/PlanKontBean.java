/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import entityfk.Kliencifk;
import entityfk.Konto;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class PlanKontBean {
   
     public static int dodajsyntetyczne(Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, String podatnik) {
         nowekonto.setSyntetyczne("syntetyczne");
         nowekonto.setPodatnik(podatnik);
         nowekonto.setRok(2014);
         nowekonto.setMacierzyste("0");
         nowekonto.setLevel(0);
         nowekonto.setMacierzysty(0);
         nowekonto.setMapotomkow(false);
         nowekonto.setPelnynumer(nowekonto.getNrkonta());
         return zachowajkonto(nowekonto, kontoDAOfk, podatnik);
     }
     
     public static int dodajanalityczne(Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, String podatnik) {
         nowekonto.setSyntetyczne("analityczne");
         nowekonto.setPodatnik(podatnik);
         nowekonto.setRok(2014);
         nowekonto.setBilansowewynikowe(macierzyste.getBilansowewynikowe());
         nowekonto.setZwyklerozrachszczegolne(macierzyste.getZwyklerozrachszczegolne());
         nowekonto.setNrkonta(oblicznumerkonta(nowekonto, macierzyste, kontoDAOfk));
         nowekonto.setMapotomkow(false);
         nowekonto.setMacierzyste(macierzyste.getPelnynumer());
         nowekonto.setMacierzysty(macierzyste.getLp());
         nowekonto.setLevel(obliczlevel(nowekonto.getMacierzyste()));
         nowekonto.setPelnynumer(nowekonto.getMacierzyste() + "-" + nowekonto.getNrkonta());
         return zachowajkonto(nowekonto, kontoDAOfk, podatnik);
    }
    
    public static int dodajanalityczne(Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, String numerkonta, String podatnik) {
         nowekonto.setSyntetyczne("analityczne");
         nowekonto.setPodatnik(podatnik);
         nowekonto.setRok(2014);
         nowekonto.setBilansowewynikowe(macierzyste.getBilansowewynikowe());
         nowekonto.setZwyklerozrachszczegolne(macierzyste.getZwyklerozrachszczegolne());
         nowekonto.setNrkonta(numerkonta);
         nowekonto.setMapotomkow(false);
         nowekonto.setMacierzyste(macierzyste.getPelnynumer());
         nowekonto.setMacierzysty(macierzyste.getLp());
         nowekonto.setLevel(obliczlevel(nowekonto.getMacierzyste()));
         nowekonto.setPelnynumer(nowekonto.getMacierzyste() + "-" + nowekonto.getNrkonta());
         return zachowajkonto(nowekonto, kontoDAOfk, podatnik);
    }
     
    public static int dodajslownik(Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, String podatnik) {
         nowekonto.setNazwapelna("Słownik kontrahenci");
         nowekonto.setNazwaskrocona("Kontrahenci");
         nowekonto.setBlokada(true);
         nowekonto.setSyntetyczne("analityczne");
         nowekonto.setPodatnik(podatnik);
         nowekonto.setRok(2014);
         nowekonto.setSlownikowe(true);
         nowekonto.setBilansowewynikowe(macierzyste.getBilansowewynikowe());
         nowekonto.setZwyklerozrachszczegolne(macierzyste.getZwyklerozrachszczegolne());
         nowekonto.setNrkonta("0");
         nowekonto.setMapotomkow(false);
         nowekonto.setMacierzyste(macierzyste.getPelnynumer());
         nowekonto.setMacierzysty(macierzyste.getLp());
         nowekonto.setLevel(obliczlevel(nowekonto.getMacierzyste()));
         nowekonto.setPelnynumer(nowekonto.getMacierzyste() + "-" + nowekonto.getNrkonta());
         return zachowajkonto(nowekonto, kontoDAOfk, podatnik);
    }
    
    public static int dodajelementyslownika(Konto kontomacierzyste, KontoDAOfk kontoDAO, KliencifkDAO kliencifkDAO, String podatnik) {
        List<Kliencifk> listaprzyporzadkowanychklientow = kliencifkDAO.znajdzkontofkKlient("8511005008");
        if (listaprzyporzadkowanychklientow != null) {
            for (Kliencifk p : listaprzyporzadkowanychklientow) {
                Konto nowekonto = new Konto();
                nowekonto.setNazwapelna(p.getNazwa());
                nowekonto.setNazwaskrocona(p.getNip());
                nowekonto.setSlownikowe(true);
                nowekonto.setBlokada(true);
                int wynikdodania = PlanKontBean.dodajanalityczne(nowekonto, kontomacierzyste, kontoDAO, p.getNrkonta());
                if (wynikdodania == 1) {
                    return 1;
                }
            }
            return 0;
        } else {
            return 0;
        }
    }
    public static int aktualizujslownik(Kliencifk kliencifk, KontoDAOfk kontoDAO, String podatnik) {
        List<Konto> kontamacierzyste = kontoDAO.findKontaMaSlownik();
        for (Konto p : kontamacierzyste) {
            Konto nowekonto = new Konto();
            nowekonto.setNazwapelna(kliencifk.getNazwa());
            nowekonto.setNazwaskrocona(kliencifk.getNip());
            nowekonto.setSlownikowe(true);
            nowekonto.setBlokada(true);
            int wynikdodania = PlanKontBean.dodajanalityczne(nowekonto, p, kontoDAO, kliencifk.getNrkonta(), podatnik);
            if (wynikdodania == 1) {
                return 1;
            }
        }
        return 0;
    }
            
    public static int usunelementyslownika(String kontomacierzyste, KontoDAOfk kontoDAO, String podatnik) {
        List<Konto> listakont = kontoDAO.findKontaPotomne(kontomacierzyste);
        if (listakont != null) {
            for (Konto p : listakont) {
                kontoDAO.destroy(p);
            }
            return 0;
        } else {
            return 0;
        }
    }
    
    private static int obliczlevel(String macierzyste) {
         int i = 1;
         i += StringUtils.countMatches(macierzyste, "-");
         return i;
    }
     
    private static int znajdzduplikat(Konto nowe, KontoDAOfk kontoDAOfk, String podatnik) {
        List<Konto> wykazkont = kontoDAOfk.findKontoPodatnik(podatnik);
        if (wykazkont.contains(nowe)) {
            return 1;
        } else {
            return 0;
        }
    }

    private static int zachowajkonto(Konto nowekonto, KontoDAOfk kontoDAOfk, String podatnik) {
        if (0 == znajdzduplikat(nowekonto, kontoDAOfk, podatnik)) {
            kontoDAOfk.dodaj(nowekonto);
            return 0;
        } else {
            return 1;
        }
    }
    
    private static String oblicznumerkonta(Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk) {
        ArrayList<Konto> lista = new ArrayList<>();
        List<Konto> wykazkont = kontoDAOfk.findAll();
        for (Konto p : wykazkont) {
            if (p.getMacierzyste().equals(macierzyste.getPelnynumer())) {
                lista.add(p);
            }
        }
        if (lista.size() > 0) {
            return String.valueOf(Integer.parseInt(lista.get(lista.size() - 1).getNrkonta()) + 1);
        } else {
            return "1";
        }
    }

   
}
