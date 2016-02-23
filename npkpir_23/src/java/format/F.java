/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package format;

import java.text.NumberFormat;
import static pdffk.PdfMain.dodajLinieOpisu;

/**
 *
 * @author Osito
 */
public class F {
    public static String n(double n) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setGroupingUsed(true);
        return formatter.format(n);
    }
    
    public static String c(double n) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setGroupingUsed(true);
        return formatter.format(n);
    }
    
    public static String k(double n) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(4);
        formatter.setMinimumFractionDigits(4);
        formatter.setGroupingUsed(true);
        return formatter.format(n);
    }
}
