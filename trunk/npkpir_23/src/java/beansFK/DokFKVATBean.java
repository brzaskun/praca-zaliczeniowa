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
import waluty.Z;

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
        double[] wartosciVAT = new double[8];
        for (EVatwpisFK p : ewidencja) {
            wartosciVAT[0] += p.getNetto();
            wartosciVAT[1] += p.getVat();
            wartosciVAT[2] += p.getNettowwalucie();
            wartosciVAT[3] += p.getVatwwalucie();
            double vatplnpolowa = Z.z(p.getVat()/2);
            double vatplnreszta = Z.z(p.getVat()-vatplnpolowa);
            wartosciVAT[4] = vatplnpolowa;
            wartosciVAT[5] = vatplnreszta;
            double vatpolowa = Z.z(p.getVatwwalucie()/2);
            double vatreszta = Z.z(p.getVatwwalucie()-vatpolowa);
            wartosciVAT[6] = vatpolowa;
            wartosciVAT[7] = vatreszta;
        }
        return wartosciVAT;
    }
    
    public static double[] podsumujwartosciVATRK(EVatwpisFK ewidencja) {
        double[] wartosciVAT = new double[4];
        wartosciVAT[0] += ewidencja.getNetto();
        wartosciVAT[1] += ewidencja.getVat();
        wartosciVAT[2] += ewidencja.getNettowwalucie();
        wartosciVAT[3] += ewidencja.getVatwwalucie();
        return wartosciVAT;
    }
}
