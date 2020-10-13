/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package error;

import java.time.LocalDate;

/**
 *
 * @author Osito
 */

public class E {
 
    public static String e(Exception e) {
        String zwrot = "";
        if (e.getStackTrace() != null && e.getStackTrace().length > 0) {
            StringBuilder p = new StringBuilder();
            p.append("Blad ");
            p.append(e.getStackTrace()[0].toString());
            zwrot += p.toString();
            if (e.getStackTrace()[1] != null) {
                p = new StringBuilder();
                p.append("cd. ");
                p.append(e.getStackTrace()[1].toString());
                zwrot += p.toString();
            }
            if (e.getStackTrace()[2] != null) {
                p = new StringBuilder();
                p.append("cd. ");
                p.append(e.getStackTrace()[2].toString());
                zwrot += p.toString();
            }
        } else {
            StringBuilder p = new StringBuilder();
            p.append("Blad ");
            p.append(e.toString());
            zwrot += p.toString();
        }
        return zwrot;
    }
    
    public static void e(Exception e, String s) {
        if (e.getStackTrace() != null && e.getStackTrace().length > 0) {
            StringBuilder p = new StringBuilder();
            p.append("Blad ");
            p.append(e.getStackTrace()[0].toString());
            if (e.getStackTrace()[1] != null) {
                p = new StringBuilder();
                p.append("cd. ");
                p.append(e.getStackTrace()[1].toString());
            }
            if (e.getStackTrace()[2] != null) {
                p = new StringBuilder();
                p.append("cd. ");
                p.append(e.getStackTrace()[2].toString());
            }
        } else {
            StringBuilder p = new StringBuilder();
            p.append("Blad ");
            p.append(e.toString());
        }
    }
    
     public static void m(Object e) {
            StringBuilder p = new StringBuilder();
            p.append("Init klasa ");
            p.append(e.getClass().toString());
            error.E.s(p.toString());
    }
     
     public static void s (String s) {
         System.out.println(s);
     }
     public static void s (Integer s) {
         System.out.println(s);
     }
     public static void s (double s) {
         System.out.println(s);
     }
     public static void s (boolean s) {
         System.out.println(s);
     }
    public static void s(Class<?> superclass) {
        System.out.println(superclass);
    }
    public static void s(LocalDate ld) {
        System.out.println(ld);
    }
   
}
