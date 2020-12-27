/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Rodzajumowy;

/**
 *
 * @author Osito
 */
public class RodzajumowyBean {
    
    public static Rodzajumowy rodzajumowy;
    
    public static Rodzajumowy create() {
        if (rodzajumowy==null) {
            rodzajumowy = new Rodzajumowy();
            rodzajumowy.setNazwa("Umowa o pracę");
        }
        return rodzajumowy;
    }
}
