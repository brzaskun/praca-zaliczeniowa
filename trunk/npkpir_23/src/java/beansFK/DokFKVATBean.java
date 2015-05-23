/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.Waluty;
import error.E;
import java.util.List;
import javax.ejb.Stateless;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Stateless
public class DokFKVATBean {
    
    public static Double pobierzstawke(EVatwpisFK evatwpis) {
        String stawkavat = null;
        try {
            stawkavat = evatwpis.getEwidencja().getNazwa().replaceAll("[^\\d]", "");
            return Double.parseDouble(stawkavat) / 100;
        } catch (Exception e) {
            E.e(e);
            stawkavat = "23";
            return Double.parseDouble(stawkavat) / 100;
        }
        
    }
    
    public static void ustawvat(EVatwpisFK evatwpis,  Dokfk selected) {
        Waluty w = selected.getWalutadokumentu();
        double kurs = selected.getTabelanbp().getKurssredni();
        //obliczamy VAT/NETTO w PLN i zachowujemy NETTO w walucie
        String rodzajdok = selected.getRodzajedok().getSkrot();
        double stawkavat = DokFKVATBean.pobierzstawke(evatwpis);
         if (!w.getSymbolwaluty().equals("PLN")) {
            double obliczonenettowpln = Z.z(evatwpis.getNetto()/kurs);
            if (evatwpis.getNettowwalucie()!= obliczonenettowpln || evatwpis.getNettowwalucie() == 0) {
                evatwpis.setNettowwalucie(evatwpis.getNetto());
                evatwpis.setNetto(Z.z(evatwpis.getNetto()*kurs));
            }
        }
        if (rodzajdok.contains("WDT") || rodzajdok.contains("UPTK") || rodzajdok.contains("EXP")) {
            evatwpis.setVat(0.0);
        } else {
            evatwpis.setVat(Z.z(evatwpis.getNetto()* stawkavat));
        }
        if (!w.getSymbolwaluty().equals("PLN")) {
            //ten vat tu musi byc bo inaczej bylby onblur przy vat i cykliczne odswiezanie
            evatwpis.setVatwwalucie(Z.z(evatwpis.getVat()/kurs));
        }
    }
    public static void ustawvat(EVatwpisFK evatwpis, Dokfk selected, double stawkavat) {
        String skrotRT = selected.getDokfkPK().getSeriadokfk();
        int lp = evatwpis.getLp();
        Waluty w = selected.getWalutadokumentu();
        double kurs = selected.getTabelanbp().getKurssredni();
        //obliczamy VAT/NETTO w PLN i zachowujemy NETTO w walucie
        String opis = evatwpis.getEwidencja().getNazwa();
        if (!w.getSymbolwaluty().equals("PLN")) {
            double obliczonenettowpln = Z.z(evatwpis.getNetto() / kurs);
            if (evatwpis.getNettowwalucie() != obliczonenettowpln || evatwpis.getNettowwalucie() == 0) {
                evatwpis.setNettowwalucie(evatwpis.getNetto());
                evatwpis.setNetto(Z.z(evatwpis.getNetto() * kurs));
            }
        }
        if (opis.contains("WDT") || opis.contains("UPTK") || opis.contains("EXP")) {
            evatwpis.setVat(0.0);
        } else if (skrotRT.contains("ZZP")) {
            evatwpis.setVat(Z.z((evatwpis.getNetto() * 0.23) / 2));
        } else {
            evatwpis.setVat(Z.z(evatwpis.getNetto() * stawkavat));
        }
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
