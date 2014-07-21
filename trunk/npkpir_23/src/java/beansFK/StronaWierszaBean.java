/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import data.Data;
import entityfk.Dokfk;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Wiersz;
import java.util.ArrayList;
import javax.ejb.Singleton;
import javax.inject.Named;
import view.WpisView;
import viewmanager.AktywacjaPodatnikow;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class StronaWierszaBean {
    
    public static boolean czyKontoJestRozrachunkowe(StronaWiersza aktualnyWierszDlaRozrachunkow, String stronawiersza) {
        if (stronawiersza.equals("Wn")) {
                return (aktualnyWierszDlaRozrachunkow).getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe");
            } else {
                return (aktualnyWierszDlaRozrachunkow).getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe");
            }
    }
    
    public static int czyKontoJestNowaTransakcja(StronaWiersza aktualnyWierszDlaRozrachunkow, String stronawiersza) {
           return aktualnyWierszDlaRozrachunkow.getTypwiersza();
    }

    public static StronaWiersza aktualizatorAktualnegoWierszaDlaRozrachunkow(StronaWiersza aktualnyWierszDlaRozrachunkow, Dokfk selected, WpisView wpisView, String wnma, int nrwiersza) {
        Wiersz wiersz = selected.getListawierszy().get(nrwiersza);
        StronaWiersza stronaWn = wiersz.getStronaWn();
        StronaWiersza stronaMa = wiersz.getStronaMa();
         if (wiersz.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
            if (wnma.equals("Wn")) {
                stronaWn.setKwotaPLN(stronaWn.getKwota());
                stronaWn.setPozostalo(stronaWn.getKwota());
            } else {
                stronaMa.setKwotaPLN(stronaMa.getKwota());
                stronaMa.setPozostalo(stronaMa.getKwota());
            }
        } else {
            if (wnma.equals("Wn")) {
                stronaWn.setKwotaPLN(przeliczWalutyWn(wiersz));
                stronaWn.setPozostalo(stronaWn.getKwota());
            } else {
                stronaMa.setKwotaPLN(przeliczWalutyMa(wiersz));
                stronaMa.setPozostalo(stronaMa.getKwota());
            }
        }
        aktualnyWierszDlaRozrachunkow.setDatarozrachunku(Data.aktualnyDzien());
        aktualnyWierszDlaRozrachunkow.setWiersz(wiersz);
        return aktualnyWierszDlaRozrachunkow;
    }
    
    private static double przeliczWalutyWn(Wiersz wiersz) {
            wiersz.getStronaWn().setKwotaWaluta(wiersz.getStronaWn().getKwota());
            double kurs = wiersz.getTabelanbp().getKurssredni();
            double kwotazlotowki = wiersz.getStronaWn().getKwota();
            kwotazlotowki *= kurs;
            kwotazlotowki *= 100;
            kwotazlotowki = Math.round(kwotazlotowki);
            kwotazlotowki /= 100;
            return kwotazlotowki;
        }
     
     private static double przeliczWalutyMa(Wiersz wiersz) {
            wiersz.getStronaMa().setKwotaWaluta(wiersz.getStronaMa().getKwota());
            double kurs = wiersz.getTabelanbp().getKurssredni();
            double kwotazlotowki = wiersz.getStronaMa().getKwota();
            kwotazlotowki *= kurs;
            kwotazlotowki *= 100;
            kwotazlotowki = Math.round(kwotazlotowki);
            kwotazlotowki /= 100;
            return kwotazlotowki;
        }
}
