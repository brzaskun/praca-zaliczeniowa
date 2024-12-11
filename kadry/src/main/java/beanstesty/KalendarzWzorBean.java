/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Dzien;
import entity.Kalendarzwzor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
        
        public static void dodajdnidokalendarza(Kalendarzwzor kalendarzwzor) {
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

        public static Set<LocalDate> getPolishHolidays(int year) {
        Set<LocalDate> holidays = new HashSet<>();

        // Święta stałe:
        holidays.add(LocalDate.of(year, 1, 1));   // Nowy Rok
        holidays.add(LocalDate.of(year, 1, 6));   // Trzech Króli
        holidays.add(LocalDate.of(year, 5, 1));   // Święto Pracy
        holidays.add(LocalDate.of(year, 5, 3));   // Święto Konstytucji 3 Maja
        holidays.add(LocalDate.of(year, 8, 15));  // Wniebowzięcie NMP
        holidays.add(LocalDate.of(year, 11, 1));  // Wszystkich Świętych
        holidays.add(LocalDate.of(year, 11, 11)); // Święto Niepodległości
        holidays.add(LocalDate.of(year, 12, 25)); // Boże Narodzenie (1 dzień)
        holidays.add(LocalDate.of(year, 12, 26)); // Boże Narodzenie (2 dzień)

        // Święta ruchome:
        LocalDate easterSunday = getEasterDate(year);
        LocalDate easterMonday = easterSunday.plusDays(1);
        holidays.add(easterSunday);  // Niedziela Wielkanocna (choć to i tak niedziela)
        holidays.add(easterMonday);  // Poniedziałek Wielkanocny

        // Boże Ciało (60 dni po Wielkanocy)
        LocalDate bozeCialo = easterSunday.plusDays(60);
        holidays.add(bozeCialo);

        return holidays;
    }

    // Przykładowa implementacja algorytmu obliczającego Wielkanoc
    // (Meeus/Jones/Butcher)
    public static LocalDate getEasterDate(int year) {
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int month = (h + l - 7 * m + 114) / 31;
        int day = ((h + l - 7 * m + 114) % 31) + 1;

        return LocalDate.of(year, month, day);
    }
    
}
