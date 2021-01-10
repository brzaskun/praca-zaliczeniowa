/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Firma;

/**
 *
 * @author Osito
 */
public class FirmaBean {
 
    public static Firma firma;
    
    public static Firma create() {
        if (firma==null) {
            firma = new Firma();
            firma.setNazwa("Firma Testowa");
            firma.setNip("8511005008");
        }
        return firma;
    }
}
