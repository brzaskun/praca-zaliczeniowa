/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Zmiennapotracenia;

/**
 *
 * @author Osito
 */
public class ZmiennapotraceniaBean {
    
    public static Zmiennapotracenia zmiennapotracenia;
    
    public static Zmiennapotracenia create() {
        if (zmiennapotracenia==null) {
            zmiennapotracenia = new Zmiennapotracenia();
            zmiennapotracenia.setDataod("2020-12-01");
            zmiennapotracenia.setDatado("2020-12-31");
            zmiennapotracenia.setNazwa("PZU");
            zmiennapotracenia.setKwotastala(200.0);
            zmiennapotracenia.setSkladnikpotracenia(SkladnikpotraceniaBean.create());
        }
        return zmiennapotracenia;
    }
}
