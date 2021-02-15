/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.FirmaKadry;

/**
 *
 * @author Osito
 */
public class FirmaBean {
 
    public static FirmaKadry firma;
    
    public static FirmaKadry create() {
        if (firma==null) {
            firma = new FirmaKadry();
            firma.setNazwa("Firma Testowa");
            firma.setNip("8511005008");
        }
        return firma;
    }
}
