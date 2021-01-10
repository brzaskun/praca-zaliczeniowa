/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Angaz;

/**
 *
 * @author Osito
 */
public class AngazBean {
    
    public static Angaz angaz;
    
    public static Angaz create() {
        if (angaz==null) {
            angaz = new Angaz();
            angaz.setFirma(FirmaBean.create());
            angaz.setPracownik(PracownikBean.create());
            angaz.setRodzajwynagrodzenia(0);
        }
        return angaz;
    }
}
