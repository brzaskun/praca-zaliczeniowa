/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Definicjalistaplac;

/**
 *
 * @author Osito
 */
public class DefinicjalistaplacBean {
    
    public static Definicjalistaplac definicjalistaplac;
    
    public static Definicjalistaplac create() {
        if (definicjalistaplac == null) {
            definicjalistaplac = new Definicjalistaplac();
            definicjalistaplac.setDatasporzadzenia("2020-12-31");
            definicjalistaplac.setDatapodatek("2021-01-20");
            definicjalistaplac.setDatazus("2021-01-15");
            definicjalistaplac.setRok("2020");
            definicjalistaplac.setMc("12");
            definicjalistaplac.setOpis("Lista miesięczna");
            definicjalistaplac.setNrkolejny("12/2020");
        }
        return definicjalistaplac;
    }
    
}
