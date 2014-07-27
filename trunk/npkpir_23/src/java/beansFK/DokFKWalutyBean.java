/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import daoFK.WalutyDAOfk;
import entityfk.Dokfk;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersz;
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
            Waluty wybranawaluta = walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty);
            kurs = selected.getTabelanbp().getKurssredni();
            kurs = Math.round((1 / kurs) * 100000000);
            kurs /= 100000000;
            List<Wiersz> wiersze = selected.getListawierszy();
            for (Wiersz p : wiersze) {
                if (p.getStronaWn().getKwota() != 0.0) {
                    double kwota = p.getStronaWn().getKwota();
                    p.getStronaWn().setKwotaPLN(kwota+0.0);
                    kwota = Math.round(kwota * kurs * 10000);
                    kwota /= 10000;
                    p.getStronaWn().setKwota(kwota);
                    p.getStronaWn().setKwotaWaluta(kwota);
                }
                if (p.getStronaMa().getKwota() != 0.0) {
                    double kwota = p.getStronaMa().getKwota();
                    p.getStronaMa().setKwotaPLN(kwota+0.0);
                    kwota = Math.round(kwota * kurs * 10000);
                    kwota /= 10000;
                    p.getStronaMa().setKwota(kwota);
                    p.getStronaMa().setKwotaWaluta(kwota);
                }
            }
        } else {
            //robimy w zlotowkach
            kurs = selected.getTabelanbp().getKurssredni();
            List<Wiersz> wiersze = selected.getListawierszy();
            for (Wiersz p : wiersze) {
                if (p.getStronaWn().getKwota() != 0.0) {
                    double kwota = p.getStronaWn().getKwota();
                    kwota = Math.round(kwota * kurs * 100);
                    kwota /= 100;
                    p.getStronaWn().setKwota(kwota);
                    p.getStronaWn().setKwotaPLN(kwota);
                    p.getStronaWn().setKwotaWaluta(0.0);
                }
                if (p.getStronaMa().getKwota() != 0.0) {
                    double kwota = p.getStronaMa().getKwota();
                    kwota = Math.round(kwota * kurs * 100);
                    kwota /= 100;
                    p.getStronaMa().setKwota(kwota);
                    p.getStronaMa().setKwotaPLN(kwota);
                    p.getStronaMa().setKwotaWaluta(0.0);
                }
        }
        }
    }

    
//    public static void uzupelnijwierszprzyprzewalutowaniuPLN(StronaWiersza wierszStronafk) {
//            wierszStronafk.setGrafikawaluty("zł");
//            Tabelanbp tabelanbp = null;
//            wierszStronafk.setNrtabelinbp(null);
//            wierszStronafk.setKurswaluty(1);
//            wierszStronafk.setSymbolwaluty("PLN");
//            wierszStronafk.setDatawaluty(null);
//    }
}

