/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Dzien;
import entity.Kalendarzwzor;
import java.util.ArrayList;

/**
 *
 * @author Osito
 */
public class KalendarzWzorBean {
    
    public static Kalendarzwzor kalendarzwzor;
    
    public static Kalendarzwzor create() {
        if (kalendarzwzor==null) {
            kalendarzwzor = new Kalendarzwzor();
            kalendarzwzor.setRok("2020");
            kalendarzwzor.setMc("12");
            kalendarzwzor.setDzienList(new ArrayList<>());
            kalendarzwzor.getDzienList().add(new Dzien(1, "2020-12-01", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(2, "2020-12-02", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(3, "2020-12-03", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(4, "2020-12-04", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(5, "2020-12-05", 1, 0, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(6, "2020-12-06", 2, 0, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(7, "2020-12-07", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(8, "2020-12-08", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(9, "2020-12-09", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(10, "2020-12-10", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(11, "2020-12-11", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(12, "2020-12-12", 1, 0, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(13, "2020-12-13", 2, 0, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(14, "2020-12-14", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(15, "2020-12-15", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(16, "2020-12-16", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(17, "2020-12-17", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(18, "2020-12-18", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(19, "2020-12-19", 1, 0, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(20, "2020-12-20", 2, 0, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(21, "2020-12-21", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(22, "2020-12-22", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(23, "2020-12-23", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(24, "2020-12-24", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(25, "2020-12-25", 3, 0, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(26, "2020-12-26", 3, 0, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(27, "2020-12-27", 2, 0, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(28, "2020-12-28", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(29, "2020-12-29", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(30, "2020-12-30", 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(31, "2020-12-31", 0, 8, kalendarzwzor));
        }
        return kalendarzwzor;
    }
        
        public static void create(Kalendarzwzor kalendarzwzor) {
            kalendarzwzor.setDzienList(new ArrayList<>());
            String data = kalendarzwzor.getRok()+"-"+kalendarzwzor.getMc()+"-";
            String data2 = kalendarzwzor.getRok()+"-"+kalendarzwzor.getMc()+"-0";
            int licznik = 1;
            kalendarzwzor.getDzienList().add(new Dzien(1, data2+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(2, data2+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(3, data2+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(4, data2+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(5, data2+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(6, data2+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(7, data2+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(8, data2+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(9, data2+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(10, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(11, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(12, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(13, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(14, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(15, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(16, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(17, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(18, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(19, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(20, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(21, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(22, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(23, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(24, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(25, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(26, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(27, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(28, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(29, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(30, data+licznik++, 0, 8, kalendarzwzor));
            kalendarzwzor.getDzienList().add(new Dzien(31, data+licznik++, 0, 8, kalendarzwzor));
    }

    
}
