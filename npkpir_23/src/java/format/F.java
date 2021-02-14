/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package format;

import java.nio.charset.StandardCharsets;
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
        double zwrot = 0.0;
        String f1 = f.trim();
        f1 = f1.replace(",", ".");
        f1 = f1.replaceAll("[^0-9.]", "");
        zwrot = Z.z(Double.valueOf(f1));
        return zwrot;
    }
    
     public static void main(String[] args)  {
            byte[] tabela = new byte[]{49,-62,-96,50,51,53,46,50,54};
         String text = new String(tabela, StandardCharsets.UTF_8);
         System.out.println("text "+text);
         String f1 = text;
         f1 = f1.replace(",", ".");
         String f2 = f1.replaceAll("[^0-9.]","");
         double f3 = kwota(f2);
        //f1.getBytes();
        //System.out.println(f3);
     }
}
