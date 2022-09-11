/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import embeddable.Mce;
import embeddable.Okres;
import entity.Nieobecnosc;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Osito
 */
public class OkresBean {

    static List<Okres> generujokresy(Nieobecnosc nieobecnosc) {
        List<Okres> zwrot = new ArrayList<>();
        Integer rokod = Integer.parseInt(nieobecnosc.getRokod());
        Integer rokdo = Integer.parseInt(nieobecnosc.getRokdo());
        Integer mcod = Integer.parseInt(nieobecnosc.getMcod());
        Integer mcdo = Integer.parseInt(nieobecnosc.getMcdo());
        for (int rok = rokod;rok<=rokdo;rok++) {
            for (int mc = mcod;mc<=mcdo;mc++) {
                Okres nowy = new Okres(String.valueOf(rok),Mce.getNumberToMiesiac().get(mc));
                zwrot.add(nowy);
            }
        }
        return zwrot;
    }
    
}
