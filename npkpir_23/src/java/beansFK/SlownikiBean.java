/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import dao.KontoDAOfk;
import entity.Podatnik;
import entityfk.Delegacja;
import entityfk.Kliencifk;
import entityfk.Konto;
import entityfk.MiejsceKosztow;
import entityfk.MiejscePrzychodow;
import entityfk.Pojazdy;
import java.util.List;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named

public class SlownikiBean {
    
     public static void aktualizujkontapoedycji(Object obiekt, int nrslownika, Podatnik podatnik, Integer rok, KontoDAOfk kontoDAOfk) {
        String[] pola = pobierzpola(obiekt);
        List<Konto> kontaslownik = null;
        kontaslownik = kontoDAOfk.findKontaMaSlownik(podatnik, rok, nrslownika);
        for (Konto p : kontaslownik) {
            List<Konto> kontapotomne = kontoDAOfk.findKontaPotomnePodatnik(podatnik, rok, p);
            if (kontapotomne != null) {
                for (Konto r : kontapotomne) {
                    if (r.getNrkonta().equals(pola[0])) {
                        r.setNazwapelna(pola[1]);
                        r.setNazwaskrocona(pola[2]);
                    }
                } 
                kontoDAOfk.editList(kontapotomne);
            }
        }
    }
     
     private static String[] pobierzpola(Object obiekt) {
        String[] pola = new String[3];
        if (obiekt instanceof Delegacja) {
            pola[0] = ((Delegacja) obiekt).getNrkonta();
            pola[1] = ((Delegacja) obiekt).getOpisdlugi();
            pola[2] = ((Delegacja) obiekt).getOpiskrotki();
        }
        if (obiekt instanceof MiejsceKosztow) {
            pola[0] = ((MiejsceKosztow) obiekt).getNrkonta();
            pola[1] = ((MiejsceKosztow) obiekt).getOpismiejsca();
            pola[2] = ((MiejsceKosztow) obiekt).getOpisskrocony();
        }
        if (obiekt instanceof Pojazdy) {
            pola[0] = ((Pojazdy) obiekt).getNrkonta();
            pola[1] = ((Pojazdy) obiekt).getNrrejestracyjny();
            pola[2] = ((Pojazdy) obiekt).getNazwapojazdu();
        }
        if (obiekt instanceof Kliencifk) {
            pola[0] = ((Kliencifk) obiekt).getNrkonta();
            pola[1] = ((Kliencifk) obiekt).getNazwa();
            pola[2] = ((Kliencifk) obiekt).getNip();
        }
        if (obiekt instanceof MiejscePrzychodow) {
            pola[0] = ((MiejscePrzychodow) obiekt).getNrkonta();
            pola[1] = ((MiejscePrzychodow) obiekt).getOpismiejsca();
            pola[2] = ((MiejscePrzychodow) obiekt).getOpisskrocony();
        }
        return pola;
     }
     
      public static void ukryjkontapodeycji(Object obiekt, int nrslownika, Podatnik podatnik, Integer rok, KontoDAOfk kontoDAOfk, boolean niewidoczne) {
        String[] pola = pobierzpola(obiekt);
        List<Konto> kontaslownik = null;
        kontaslownik = kontoDAOfk.findKontaMaSlownik(podatnik, rok, nrslownika);
        for (Konto p : kontaslownik) {
            List<Konto> kontapotomne = kontoDAOfk.findKontaPotomnePodatnik(podatnik, rok, p);
            if (kontapotomne != null) {
                for (Konto r : kontapotomne) {
                    if (r.getNrkonta().equals(pola[0])) {
                        r.setNiewidoczne(niewidoczne);
                    }
                }
               kontoDAOfk.editList(kontapotomne);
            }
        }
    }
}
