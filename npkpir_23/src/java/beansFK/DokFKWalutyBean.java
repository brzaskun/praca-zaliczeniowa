/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import daoFK.WalutyDAOfk;
import embeddable.EVatwpis_;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.Waluty;
import entityfk.Wiersz;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named

public class DokFKWalutyBean implements Serializable{
    
    public static void przewalutujzapisy(String staranazwa, String nazwawaluty, Dokfk selected, WalutyDAOfk walutyDAOfk) {
        double kurs;
        if (staranazwa.equals("PLN")) {
            Waluty wybranawaluta = walutyDAOfk.findWalutaBySymbolWaluty(nazwawaluty);
            kurs = selected.getTabelanbp().getKurssredniPrzelicznik();
            kurs = Math.round((1 / kurs) * 100000000);
            kurs /= 100000000;
            List<Wiersz> wiersze = selected.getListawierszy();
            for (Wiersz p : wiersze) {
                if (p.getTypWiersza() == 0 || p.getTypWiersza() == 1) {
                    if (p.getStronaWn().getKwota() != 0.0) {
                        double kwota = p.getStronaWn().getKwota();
                        p.getStronaWn().setKwotaPLN(kwota+0.0);
                        kwota = Math.round(kwota * kurs * 100);
                        kwota /= 100;
                        p.getStronaWn().setKwota(kwota);
                        p.getStronaWn().setKwotaWaluta(kwota);
                    }
                }
                if (p.getTypWiersza() == 0 || p.getTypWiersza() == 2) {
                    if (p.getStronaMa().getKwota() != 0.0) {
                        double kwota = p.getStronaMa().getKwota();
                        p.getStronaMa().setKwotaPLN(kwota+0.0);
                        kwota = Math.round(kwota * kurs * 100);
                        kwota /= 100;
                        p.getStronaMa().setKwota(kwota);
                        p.getStronaMa().setKwotaWaluta(kwota);
                    }
                }
            }
        } else {
            //robimy w zlotowkach
            kurs = selected.getTabelanbp().getKurssredniPrzelicznik();
            List<Wiersz> wiersze = selected.getListawierszy();
            for (Wiersz p : wiersze) {
                if (p.getTypWiersza() == 0 || p.getTypWiersza() == 1) {
                    if (p.getStronaWn().getKwota() != 0.0) {
                        double kwota = p.getStronaWn().getKwota();
                        kwota = Math.round(kwota * kurs * 100);
                        kwota /= 100;
                        p.getStronaWn().setKwota(kwota);
                        p.getStronaWn().setKwotaPLN(kwota);
                        p.getStronaWn().setKwotaWaluta(0.0);
                    }
                }
                if (p.getTypWiersza() == 0 || p.getTypWiersza() == 2) {
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
    }
    
    public static void zmienkurswaluty(Dokfk selected) {
          //robimy w zlotowkach
        double kurs = selected.getTabelanbp().getKurssredniPrzelicznik();
        List<Wiersz> wiersze = selected.getListawierszy();
        for (Wiersz p : wiersze) {
            if (p.getTypWiersza() == 0 || p.getTypWiersza() == 1) {
                if (p.getStronaWn().getKwota() != 0.0) {
                    double kwotaPLN = Z.z(p.getStronaWn().getKwota()*kurs);
                    p.getStronaWn().setKwotaPLN(kwotaPLN);
                }
            }
            if (p.getTypWiersza() == 0 || p.getTypWiersza() == 2) {
                if (p.getStronaMa().getKwota() != 0.0) {
                    double kwotaPLN = Z.z(p.getStronaMa().getKwota()*kurs);
                    p.getStronaMa().setKwotaPLN(kwotaPLN);
                }
            }
        }
        for (EVatwpisFK r : selected.getEwidencjaVAT()) {
            r.setNetto(Z.z(r.getNettowwalucie()*kurs));
            r.setVat(Z.z(r.getVatwwalucie()*kurs)); 
            r.setBrutto(Z.z(r.getNetto()+r.getVat()));
        }
    }
    
    public static void przewalutujwiersz(Wiersz p) {
          //robimy w zlotowkach
        double kurs = p.getTabelanbp().getKurssredniPrzelicznik();
        if (p.getTypWiersza() == 0 || p.getTypWiersza() == 1) {
            if (p.getStronaWn().getKwota() != 0.0) {
                double kwotaPLN = Z.z(p.getStronaWn().getKwota()*kurs);
                p.getStronaWn().setKwotaPLN(kwotaPLN);
            }
        }
        if (p.getTypWiersza() == 0 || p.getTypWiersza() == 2) {
            if (p.getStronaMa().getKwota() != 0.0) {
                double kwotaPLN = Z.z(p.getStronaMa().getKwota()*kurs);
                p.getStronaMa().setKwotaPLN(kwotaPLN);
            }
        }
    }
    
    
}

