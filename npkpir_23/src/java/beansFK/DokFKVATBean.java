/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import entityfk.EVatwpisFK;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Singleton;

/**
 *
 * @author Osito
 */
@Singleton
public class DokFKVATBean {
    
    public static Double pobierzstawke(EVatwpisFK e, String form) {
        String stawkavat = null;
        try {
            stawkavat = e.getEwidencja().getNazwa().replaceAll("[^\\d]", "");
            return Double.parseDouble(stawkavat) / 100;
        } catch (Exception e1) {

        }
        stawkavat = "23";
        return Double.parseDouble(stawkavat) / 100;
    }
            
    public static double[] podsumujwartosciVAT(List<EVatwpisFK> ewidencja) {
        double[] wartosciVAT = new double[3];
        for (EVatwpisFK p : ewidencja) {
            wartosciVAT[0] += p.getNetto();
            wartosciVAT[1] += p.getVat();
            wartosciVAT[2] += p.getNettowwalucie();
        }
        return wartosciVAT;
    }
}
