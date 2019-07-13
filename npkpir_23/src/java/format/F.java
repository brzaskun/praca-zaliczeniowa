/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package format;

import java.text.NumberFormat;
import java.util.Currency;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class F {
    public static String number(double n) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setGroupingUsed(true);
        return formatter.format(n);
    }
    
    public static String numberS(double n) {
        String zwrot = "";
        if (n != 0.0) {
            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMaximumFractionDigits(2);
            formatter.setMinimumFractionDigits(2);
            formatter.setGroupingUsed(true);
            zwrot =  formatter.format(n);
        }
        return zwrot;
    }
    
    public static String curr(double n) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setGroupingUsed(true);
        return formatter.format(n);
    }
    public static String curr(double n, String symboluwaluty) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        Currency currency = Currency.getInstance(symboluwaluty);
        formatter.setCurrency(currency);
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setGroupingUsed(true);
        return formatter.format(n);
    }
    
    public static String kurs(double n) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(4);
        formatter.setMinimumFractionDigits(4);
        formatter.setGroupingUsed(true);
        return formatter.format(n);
    }
    
    public static double kwota(String f) {
        String f1 = f.trim();
        f1 = f1.replace(",", ".");
        f1 = f1.replace(" ", "");
        return Z.z(Double.valueOf(f1).doubleValue());
    }
}
