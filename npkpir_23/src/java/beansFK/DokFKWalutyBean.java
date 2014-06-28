/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import daoFK.WalutyDAOfk;
import entityfk.WierszStronafk;
import entityfk.Dokfk;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersze;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class DokFKWalutyBean implements Serializable{
    
    public static void przewalutujzapisy(String staranazwa, String nazwawaluty, Dokfk selected, WalutyDAOfk walutyDAOfk) {
        double kurs;
        if (staranazwa.equals("PLN")) {
            Waluty wybranawaluta = walutyDAOfk.findByName(nazwawaluty);
            kurs = selected.getTabelanbp().getKurssredni();
            kurs = Math.round((1 / kurs) * 100000000);
            kurs /= 100000000;
            List<Wiersze> wiersze = selected.getListawierszy();
            for (Wiersze p : wiersze) {
                if (p.getKwotaWn() != 0.0) {
                    double kwota = p.getKwotaWn();
                    p.setKwotaPLNWn(kwota+0.0);
                    kwota = Math.round(kwota * kurs * 10000);
                    kwota /= 10000;
                    p.setKwotaWn(kwota);
                    p.setKwotaWalutaWn(kwota);
                }
                if (p.getKwotaMa() != 0.0) {
                    double kwota = p.getKwotaMa();
                    p.setKwotaPLNMa(kwota+0.0);
                    kwota = Math.round(kwota * kurs * 10000);
                    kwota /= 10000;
                    p.setKwotaMa(kwota);
                    p.setKwotaWalutaMa(kwota);
                }
            }
        } else {
            //robimy w zlotowkach
            kurs = selected.getTabelanbp().getKurssredni();
            List<Wiersze> wiersze = selected.getListawierszy();
            for (Wiersze p : wiersze) {
                if (p.getKwotaWn() != 0.0) {
                    double kwota = p.getKwotaWn();
                    kwota = Math.round(kwota * kurs * 100);
                    kwota /= 100;
                    p.setKwotaWn(kwota);
                    p.setKwotaPLNWn(kwota);
                    p.setKwotaWalutaWn(0.0);
                }
                if (p.getKwotaMa() != 0.0) {
                    double kwota = p.getKwotaMa();
                    kwota = Math.round(kwota * kurs * 100);
                    kwota /= 100;
                    p.setKwotaMa(kwota);
                    p.setKwotaPLNMa(kwota);
                    p.setKwotaWalutaMa(0.0);
                }
        }
        }
    }

//    public static void uzupelnijwierszprzyprzewalutowaniu(WierszStronafk wierszStronafk, Waluty wybranawaluta, Tabelanbp tabelanbp) {
//            wierszStronafk.setGrafikawaluty(wybranawaluta.getSkrotsymbolu());
//            wierszStronafk.setNrtabelinbp(tabelanbp.getTabelanbpPK().getNrtabeli());
//            wierszStronafk.setKurswaluty(tabelanbp.getKurssredni());
//            wierszStronafk.setSymbolwaluty(tabelanbp.getTabelanbpPK().getSymbolwaluty());
//            wierszStronafk.setDatawaluty(tabelanbp.getDatatabeli());
//    }
//    
//    public static void uzupelnijwierszprzyprzewalutowaniuPLN(WierszStronafk wierszStronafk) {
//            wierszStronafk.setGrafikawaluty("zł");
//            Tabelanbp tabelanbp = null;
//            wierszStronafk.setNrtabelinbp(null);
//            wierszStronafk.setKurswaluty(1);
//            wierszStronafk.setSymbolwaluty("PLN");
//            wierszStronafk.setDatawaluty(null);
//    }
}

