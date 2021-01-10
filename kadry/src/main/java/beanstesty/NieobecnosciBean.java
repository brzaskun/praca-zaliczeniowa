/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Nieobecnosc;

/**
 *
 * @author Osito
 */
public class NieobecnosciBean {
    
    public static Nieobecnosc choroba;
    public static Nieobecnosc urlop;
    public static Nieobecnosc urlopbezplatny;
    
    public static Nieobecnosc createChoroba() {
        if (choroba==null) {
           choroba = new Nieobecnosc();
           choroba.setDataod("2020-12-01");
           choroba.setDatado("2020-12-05");
           choroba.setKod("331");
           choroba.setNazwa("choroba");
        }
        return choroba;
    }
    
    public static Nieobecnosc createUrlop() {
        if (urlop==null) {
           urlop = new Nieobecnosc();
           urlop.setDataod("2020-12-06");
           urlop.setDatado("2020-12-11");
           urlop.setKod("001");
           urlop.setNazwa("urlop wypoczynkowy");
        }
        return urlop;
    }
    
     public static Nieobecnosc createUrlopBezplatny() {
        if (urlopbezplatny==null) {
           urlopbezplatny = new Nieobecnosc();
           urlopbezplatny.setDataod("2020-12-25");
           urlopbezplatny.setDatado("2020-12-31");
           urlopbezplatny.setKod("002");
           urlopbezplatny.setNazwa("urlop bezplatny");
        }
        return urlopbezplatny;
    }
}
