/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Kodyzawodow;

/**
 *
 * @author Osito
 */
public class KodzawoduBean {
    
    public static Kodyzawodow kodyzawodow;
    
    public static Kodyzawodow create() {
        if (kodyzawodow==null) {
            kodyzawodow = new Kodyzawodow();
            kodyzawodow.setNazwa("kadrowa");
            kodyzawodow.setSymbolkzis("242307");
            kodyzawodow.setSymbolus("24230706");
        }
        return kodyzawodow;
    }
}
