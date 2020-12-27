/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Dzien;
import entity.KalendarzWzor;
import java.util.ArrayList;

/**
 *
 * @author Osito
 */
public class KalendarzWzorBean {
    
    public static KalendarzWzor kalendarzwzor;
    
    public static KalendarzWzor create() {
        if (kalendarzwzor==null) {
            kalendarzwzor = new KalendarzWzor();
            kalendarzwzor.setRok("2020");
            kalendarzwzor.setMc("12");
            kalendarzwzor.setDzienList(new ArrayList<>());
            kalendarzwzor.getDzienList().add(new Dzien(1, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(2, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(3, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(4, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(5, 1, 0));
            kalendarzwzor.getDzienList().add(new Dzien(6, 2, 0));
            kalendarzwzor.getDzienList().add(new Dzien(7, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(8, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(9, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(10, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(11, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(12, 1, 0));
            kalendarzwzor.getDzienList().add(new Dzien(13, 2, 0));
            kalendarzwzor.getDzienList().add(new Dzien(14, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(15, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(16, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(17, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(18, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(19, 1, 0));
            kalendarzwzor.getDzienList().add(new Dzien(20, 2, 0));
            kalendarzwzor.getDzienList().add(new Dzien(21, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(22, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(23, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(24, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(25, 3, 0));
            kalendarzwzor.getDzienList().add(new Dzien(26, 3, 0));
            kalendarzwzor.getDzienList().add(new Dzien(27, 2, 0));
            kalendarzwzor.getDzienList().add(new Dzien(28, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(29, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(30, 0, 8));
            kalendarzwzor.getDzienList().add(new Dzien(31, 0, 8));
        }
        return kalendarzwzor;
    }

    
}
