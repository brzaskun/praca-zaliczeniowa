/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Umowakodzus;

/**
 *
 * @author Osito
 */
public class UmowakodzusBean {
    
    public static Umowakodzus umowakodzus;
    
    public static Umowakodzus create() {
        if (umowakodzus==null) {
            umowakodzus = new Umowakodzus();
            umowakodzus.setOpis("Umowa o pracę");
            umowakodzus.setKod("0110");
        }
        return umowakodzus;
    }
}
