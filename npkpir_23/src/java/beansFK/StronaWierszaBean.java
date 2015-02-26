/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import dao.StronaWierszaDAO;
import data.Data;
import embeddable.Mce;
import entityfk.Dokfk;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Wiersz;
import java.util.ArrayList;
import java.util.List;
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
       
    public static double przeliczWalutyWn(Wiersz wiersz) {
            wiersz.getStronaWn().setKwotaWaluta(wiersz.getStronaWn().getKwota());
            double kurs = wiersz.getTabelanbp().getKurssredni();
            double kwotazlotowki = wiersz.getStronaWn().getKwota();
            kwotazlotowki = Math.round(kwotazlotowki * kurs * 100);
            kwotazlotowki /= 100;
            return kwotazlotowki;
        }
     
     public static double przeliczWalutyMa(Wiersz wiersz) {
            wiersz.getStronaMa().setKwotaWaluta(wiersz.getStronaMa().getKwota());
            double kurs = wiersz.getTabelanbp().getKurssredni();
            double kwotazlotowki = wiersz.getStronaMa().getKwota();
            kwotazlotowki = Math.round(kwotazlotowki * kurs * 100);
            kwotazlotowki /= 100;
            return kwotazlotowki;
        }
     
     public static List<StronaWiersza> pobraniezapisowwynikowe(StronaWierszaDAO stronaWierszaDAO, WpisView wpisView) {
        return stronaWierszaDAO.findStronaByPodatnikRokMcWynik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
    }
     
     public static void main(String[] args) {
            double kwotawaluta = 6851.63;
            double kurs = 4.2295;
            double kwotazlotowki = 0.0;
            kwotazlotowki = Math.round(kwotawaluta * kurs * 100);
            kwotazlotowki /= 100;
            System.out.println(kwotazlotowki);
            kwotawaluta = 137032.50;
            kwotazlotowki = 0.0;
            kwotazlotowki = Math.round(kwotawaluta * kurs * 100);
            kwotazlotowki /= 100;
            System.out.println(kwotazlotowki);
            double vatpln = kwotazlotowki * 0.05;
     }
}
