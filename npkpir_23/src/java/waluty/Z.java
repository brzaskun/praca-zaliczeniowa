/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package waluty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Osito
 */
public class Z implements Serializable {
    public static double z(double l) {
        double m = Math.round(l * 100);
        m /= 100;
        return m;
    }
    
    //obcina kwoty po przeciku typu 1,49999 = 1
    public static double z0(double l) {
        double m = Math.round(l);
        return m;
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
    
//    public static void main(String[] args) {
//        double kwota = 123.64;
//        double m = Math.round(kwota);
//        m /= 1;
//        System.out.println(m);
//    }
    
     public static void main(String[] args) {
         BigDecimal b = new BigDecimal(1000);
         Integer c = 500;
         int suma = Z.zUD((int) c +b.intValue());
         System.out.println("s "+suma);
    }
}
