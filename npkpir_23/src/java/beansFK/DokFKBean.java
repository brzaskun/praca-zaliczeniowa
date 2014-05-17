/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import daoFK.WalutyDAOfk;
import embeddablefk.WierszStronafk;
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
public class DokFKBean implements Serializable{
    
    public static void przewalutujzapisy(String staranazwa, String nazwawaluty, Dokfk selected, WalutyDAOfk walutyDAOfk) {
        double kurs;
        if (staranazwa.equals("PLN")) {
            Waluty wybranawaluta = walutyDAOfk.findByName(nazwawaluty);
            kurs = selected.getTabelanbp().getKurssredni();
            kurs = Math.round((1 / kurs) * 100000000);
            kurs /= 100000000;
            List<Wiersze> wiersze = selected.getListawierszy();
            for (Wiersze p : wiersze) {
                uzupelnijwierszprzyprzewalutowaniu(p.getWierszStronaWn(), wybranawaluta, selected.getTabelanbp());
                uzupelnijwierszprzyprzewalutowaniu(p.getWierszStronaMa(), wybranawaluta, selected.getTabelanbp());
                if (p.getWierszStronaWn().getKwota() != 0.0) {
                    double kwota = p.getWierszStronaWn().getKwota();
                    p.getWierszStronaWn().setKwotaPLN(kwota+0.0);
                    kwota = Math.round(kwota * kurs * 10000);
                    kwota /= 10000;
                    p.getWierszStronaWn().setKwota(kwota);
                    p.getWierszStronaWn().setKwotaWaluta(kwota);
                }
                if (p.getWierszStronaMa().getKwota() != 0.0) {
                    double kwota = p.getWierszStronaMa().getKwota();
                    p.getWierszStronaMa().setKwotaPLN(kwota+0.0);
                    kwota = Math.round(kwota * kurs * 10000);
                    kwota /= 10000;
                    p.getWierszStronaMa().setKwota(kwota);
                    p.getWierszStronaMa().setKwotaWaluta(kwota);
                }
            }
        } else {
            //robimy w zlotowkach
            kurs = selected.getTabelanbp().getKurssredni();
            List<Wiersze> wiersze = selected.getListawierszy();
            for (Wiersze p : wiersze) {
                uzupelnijwierszprzyprzewalutowaniuPLN(p.getWierszStronaWn());
                uzupelnijwierszprzyprzewalutowaniuPLN(p.getWierszStronaMa());
                p.getWierszStronaWn().setGrafikawaluty("zł");
                p.getWierszStronaMa().setGrafikawaluty("zł");
                if (p.getWierszStronaWn().getKwota() != 0.0) {
                    double kwota = p.getWierszStronaWn().getKwota();
                    kwota = Math.round(kwota * kurs * 100);
                    kwota /= 100;
                    p.getWierszStronaWn().setKwota(kwota);
                    p.getWierszStronaWn().setKwotaPLN(kwota);
                    p.getWierszStronaWn().setKwotaWaluta(0.0);
                }
                if (p.getWierszStronaMa().getKwota() != 0.0) {
                    double kwota = p.getWierszStronaMa().getKwota();
                    kwota = Math.round(kwota * kurs * 100);
                    kwota /= 100;
                    p.getWierszStronaMa().setKwota(kwota);
                    p.getWierszStronaMa().setKwotaPLN(kwota);
                    p.getWierszStronaMa().setKwotaWaluta(0.0);
                }
        }
        }
    }

    public static void uzupelnijwierszprzyprzewalutowaniu(WierszStronafk wierszStronafk, Waluty wybranawaluta, Tabelanbp tabelanbp) {
            wierszStronafk.setGrafikawaluty(wybranawaluta.getSkrotsymbolu());
            wierszStronafk.setNrtabelinbp(tabelanbp.getTabelanbpPK().getNrtabeli());
            wierszStronafk.setKurswaluty(tabelanbp.getKurssredni());
            wierszStronafk.setSymbolwaluty(tabelanbp.getTabelanbpPK().getSymbolwaluty());
            wierszStronafk.setDatawaluty(tabelanbp.getDatatabeli());
    }
    
    public static void uzupelnijwierszprzyprzewalutowaniuPLN(WierszStronafk wierszStronafk) {
            wierszStronafk.setGrafikawaluty("zł");
            Tabelanbp tabelanbp = null;
            wierszStronafk.setNrtabelinbp(null);
            wierszStronafk.setKurswaluty(1);
            wierszStronafk.setSymbolwaluty("PLN");
            wierszStronafk.setDatawaluty(null);
    }
}

