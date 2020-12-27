/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Pracownik;

/**
 *
 * @author Osito
 */
public class PracownikBean {
    
     public static Pracownik pracownik;
     
     public static Pracownik create() {
         if (pracownik==null) {
            pracownik = new Pracownik();
            pracownik.setNazwisko("Kowalski");
            pracownik.setImię("Jan");
         }
        return pracownik;
     }
}
