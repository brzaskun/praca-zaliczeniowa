/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beansPodatnik;

import entity.Podatnik;

/**
 *
 * @author Osito
 */
public class PodatnikBean {

    public static void uzupelnijdanedofaktury(Podatnik podatnik) {
        try {
            podatnik.setMiejscewystawienia(podatnik.getMiejscowosc());
            podatnik.setPlatnoscwdni("14");
            podatnik.setNazwadlafaktury(podatnik.getPrintnazwa());
            podatnik.setAdresdlafaktury(podatnik.getAdres());
            podatnik.setNipdlafaktury(podatnik.getNip());
            podatnik.setWystawcafaktury(podatnik.getNazwiskoImie());
            podatnik.setSchematnumeracji("N/m/r");
        } catch (Exception e) {}
    }
    
}
