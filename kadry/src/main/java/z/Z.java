/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package z;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author Osito
 */
public class Z implements Serializable {
    public static double z_old(double l) {
        double m = Math.round(l * 100);
        m /= 100;
        return m;
    }
    
    public static double zAbs(double l) {
        double m = Math.round(l * 100);
        m /= 100;
        return Math.abs(m);
    }
    
    public static double z(BigDecimal l) {
        double m = Math.round(l.doubleValue() * 100);
        m /= 100;
        return m;
    }
    
    public static double z4(double l) {
        float baba = (float)l;
        BigDecimal nowa = new BigDecimal(baba).setScale(4, RoundingMode.HALF_EVEN);
        return nowa.doubleValue();
    }
    
    public static double z6(double l) {
        float baba = (float)l*10000f;
        BigDecimal nowa = new BigDecimal(baba).setScale(6, RoundingMode.HALF_EVEN);
        return nowa.doubleValue()/10000;
    }
    
    //obcina kwoty po przeciku typu 1,49999 = 1
    public static double z0(double l) {
        double m = Math.round(l);
        return m;
    }
    //obcina kwoty po przeciku typu 1,49999 = 1
    public static double zm1(double l) {
        double m = Math.round(l/10);
        return m*10;
    }
    
    //zaokragla kwoty po przeciku do int typu 1,49999 = 2
     public static int zUD(int l) {
        double m = Math.round(l * 100);
        m /= 100;
        return (int) Math.round(m);
    }
     
    
     public static int zUD(double l) {
        double m = Math.round(l * 100);
        m /= 100;
        return (int) Math.round(m);
    }
     
     public static Integer zUDI(double l) {
        double m = Math.round(l * 100);
        m /= 100;
        Integer zwrot = (int) Math.round(m);
        return zwrot;
    }
    
     public static BigDecimal zBD2(double l) {
         float baba = (float)l;
         return new BigDecimal(baba).setScale(2, RoundingMode.HALF_EVEN);
     }
     
     
     public static double z(double l) {
        float baba = (float)l*100f;
        BigDecimal nowa = new BigDecimal(baba).setScale(0, RoundingMode.HALF_UP);
        double pierwszy = nowa.doubleValue()/100.0;
        return pierwszy;
     }
     
    
    public static void main(String[] args) {
        double kwota = 26.175;
        error.E.s(kwota);
        float babab = (float)kwota*100f;
        error.E.s(babab);
        BigDecimal nowa = new BigDecimal(babab).setScale(4, RoundingMode.HALF_UP);
        double nowanowa = nowa.doubleValue();
        nowa = new BigDecimal(nowanowa).setScale(0, RoundingMode.HALF_UP);
        error.E.s(nowa.doubleValue()/100.0);
        String aaa= nowa.toString();
//        double m = Math.round(kwota);
//        m /= 1;
        double kwota1 = 113.425;
        error.E.s(kwota1);
        float babab1 = (float)kwota1*100f;
        error.E.s(babab1);
        BigDecimal nowa1 = new BigDecimal(babab1).setScale(4, RoundingMode.HALF_UP);
        double nowanowa1 = nowa1.doubleValue();
        nowa1 = new BigDecimal(nowanowa1).setScale(0, RoundingMode.HALF_UP);
        error.E.s(nowa1.doubleValue()/100.0);
        
    }
    
//     public static void main(String[] args) {
//         BigDecimal b = new BigDecimal(1000);
//         Integer c = 500;
//         int suma = Z.zUD((int) c +b.intValue());
//         error.E.s("s "+suma);
//    }
     
//      public static void main(String[] args) {
//        //double kurswyliczony = Math.round(555354.35 / 133434.49 * 10000);
//        //kurswyliczony /= 10000;
//        String va= "0,19".replace(",", ".");
//        double dab = Double.valueOf(va);
//         //error.E.s(dab);
//         error.E.s(zUD(12.51));
//         // Przykładowe daty początkowe i końcowe umowy
//LocalDate startDate = LocalDate.of(2022, 12, 10);
//LocalDate endDate = LocalDate.of(2023, 11, 10);
//
//// Obliczenie liczby miesięcy pomiędzy datami
//long monthsBetween = ChronoUnit.MONTHS.between(startDate, endDate);
//
//// Wyświetlenie wyniku
//System.out.println("Umowa trwa " + monthsBetween + " miesięcy");
//// Przykładowe daty początkowe i końcowe umowy
//LocalDate startDate1 = LocalDate.of(2022, 12, 1);
//LocalDate endDate1 = LocalDate.of(2023, 11, 30);
//
//// Inicjalizacja listy na miesiące
//List<YearMonth> months = new ArrayList<>();
//
//// Pętla przechodząca przez wszystkie miesiące pomiędzy datami
//for (YearMonth month = YearMonth.from(startDate1); month.isBefore(YearMonth.from(endDate1)); month = month.plusMonths(1)) {
//  // Dodanie miesiąca do listy
//  months.add(month);
//}
//
//// Wyświetlenie zawartości listy
//System.out.println("Umowa trwa w miesiącach: " + months);

// Przykładowa data urodzenia
LocalDate birthDate = LocalDate.of(2000, 1, 1);

// Pobranie bieżącej daty
LocalDate currentDate = LocalDate.now();

// Obliczenie liczby lat
long years = ChronoUnit.YEARS.between(birthDate, currentDate);

// Obliczenie daty urodzenia w bieżącym roku
LocalDate birthDateThisYear = birthDate.withYear(currentDate.getYear());

// Obliczenie liczby miesięcy i dni w bieżącym roku
long months = ChronoUnit.MONTHS.between(birthDateThisYear, currentDate) % 12;
long days = ChronoUnit.DAYS.between(birthDateThisYear, currentDate) % 30;

// Wyświetlenie wyniku
//System.out.println("Osoba urodziła się w " + birthDate + " i ma " + years + " lat, " + months + " miesięcy i " + days + " dni");


  //  }

    
    
}
