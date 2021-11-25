/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Swiadczeniekodzus;

/**
 *
 * @author Osito
 */
public class NieobecnosckodzusBean {
    
    public static Swiadczeniekodzus choroba;
    public static Swiadczeniekodzus korektakalendarza;
    public static Swiadczeniekodzus urlop;
    public static Swiadczeniekodzus urlopbezplatny;
    
    public static Swiadczeniekodzus createChoroba() {
        if (choroba==null) {
           choroba = new Swiadczeniekodzus();
           choroba.setKod("331");
           choroba.setOpis("choroba");
           choroba.setOpisskrocony("choroba");
        }
        return choroba;
    }
    
    public static Swiadczeniekodzus createUrlop() {
        if (urlop==null) {
           urlop = new Swiadczeniekodzus();
           urlop.setKod("100");
           urlop.setOpis("urlop wypoczynkowy");
           urlop.setOpisskrocony("urlop wypoczynkowy");
        }
        return urlop;
    }
    
     public static Swiadczeniekodzus createUrlopBezplatny() {
        if (urlopbezplatny==null) {
           urlopbezplatny = new Swiadczeniekodzus();
           urlopbezplatny.setKod("111");
           urlopbezplatny.setOpis("urlop bezplatny");
           urlopbezplatny.setOpisskrocony("urlop bezplatny");
        }
        return urlopbezplatny;
    }
     
     public static Swiadczeniekodzus createKorektakalendarza() {
        if (korektakalendarza==null) {
           korektakalendarza = new Swiadczeniekodzus();
           korektakalendarza.setKod("200");
           korektakalendarza.setOpis("korekta kalendarza");
           korektakalendarza.setOpisskrocony("korekta kalendarza");
        }
        return korektakalendarza;
    }
}
