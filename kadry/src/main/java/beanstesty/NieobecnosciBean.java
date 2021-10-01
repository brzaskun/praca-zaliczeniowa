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
    public static Nieobecnosc choroba2;
    public static Nieobecnosc urlop;
    public static Nieobecnosc urlopbezplatny;
    
    public static Nieobecnosc createChoroba() {
        if (choroba==null) {
           choroba = new Nieobecnosc();
           choroba.setDataod("2020-12-01");
           choroba.setDatado("2020-12-05");
           choroba.setNieobecnosckodzus(NieobecnosckodzusBean.createChoroba());
        }
        return choroba;
    }
    
    public static Nieobecnosc createChoroba2() {
        if (choroba2==null) {
           choroba2 = new Nieobecnosc();
           choroba2.setDataod("2020-12-15");
           choroba2.setDatado("2020-12-17");
           choroba2.setNieobecnosckodzus(NieobecnosckodzusBean.createChoroba());
        }
        return choroba2;
    }
    
    public static Nieobecnosc createUrlop() {
        if (urlop==null) {
           urlop = new Nieobecnosc();
           urlop.setDataod("2020-12-18");
           urlop.setDatado("2020-12-22");
           urlop.setDnikalendarzowe(4);
           urlop.setDnirobocze(3);
           urlop.setNieobecnosckodzus(NieobecnosckodzusBean.createUrlop());
        }
        return urlop;
    }
    
     public static Nieobecnosc createUrlopBezplatny() {
        if (urlopbezplatny==null) {
           urlopbezplatny = new Nieobecnosc();
           urlopbezplatny.setDataod("2020-12-25");
           urlopbezplatny.setDatado("2020-12-31");
           urlopbezplatny.setNieobecnosckodzus(NieobecnosckodzusBean.createUrlopBezplatny());
        }
        return urlopbezplatny;
    }
}
