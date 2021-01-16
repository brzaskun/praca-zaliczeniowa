/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Nieobecnosckodzus;

/**
 *
 * @author Osito
 */
public class NieobecnosckodzusBean {
    
    public static Nieobecnosckodzus choroba;
    public static Nieobecnosckodzus urlop;
    public static Nieobecnosckodzus urlopbezplatny;
    
    public static Nieobecnosckodzus createChoroba() {
        if (choroba==null) {
           choroba = new Nieobecnosckodzus();
           choroba.setKod("331");
           choroba.setOpis("choroba");
           choroba.setOpisskrocony("choroba");
        }
        return choroba;
    }
    
    public static Nieobecnosckodzus createUrlop() {
        if (urlop==null) {
           urlop = new Nieobecnosckodzus();
           urlop.setKod("100");
           urlop.setOpis("urlop wypoczynkowy");
           urlop.setOpisskrocony("urlop wypoczynkowy");
        }
        return urlop;
    }
    
     public static Nieobecnosckodzus createUrlopBezplatny() {
        if (urlopbezplatny==null) {
           urlopbezplatny = new Nieobecnosckodzus();
           urlop.setKod("111");
           urlopbezplatny.setOpis("urlop bezplatny");
           urlopbezplatny.setOpisskrocony("urlop bezplatny");
        }
        return urlopbezplatny;
    }
}
