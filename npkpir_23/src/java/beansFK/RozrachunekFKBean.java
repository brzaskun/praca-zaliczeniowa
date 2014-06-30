/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import daoFK.RozrachunekfkDAO;
import data.Data;
import entityfk.Dokfk;
import entityfk.Rozrachunekfk;
import entityfk.Tabelanbp;
import entityfk.Wiersze;
import javax.ejb.Singleton;
import javax.inject.Named;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class RozrachunekFKBean {
    
    public static Rozrachunekfk pobierzIstniejacyRozrachunek(RozrachunekfkDAO rozrachunekfkDAO, Rozrachunekfk aktualnyWierszDlaRozrachunkow) {
        Rozrachunekfk pobranyrozrachunek = rozrachunekfkDAO.findRozrachunekfk(aktualnyWierszDlaRozrachunkow);
        if (pobranyrozrachunek instanceof Rozrachunekfk) {
            return pobranyrozrachunek;
        }
        return null;
    }
    
    public static void konstruktorAktualnegoWierszaDlaRozrachunkow(Rozrachunekfk aktualnyWierszDlaRozrachunkow, Dokfk selected, WpisView wpisView, String wnma, int nrwiersza) {
        Wiersze wiersz = selected.getListawierszy().get(nrwiersza);
        if (wiersz.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
            if (wnma.equals("Wn")) {
                wiersz.setKwotaPLNWn(wiersz.getKwotaWn());
            } else {
                wiersz.setKwotaPLNMa(wiersz.getKwotaMa());
            }
        } else {
            if (wnma.equals("Wn")) {
                wiersz.setKwotaPLNWn(przeliczWalutyWn(wiersz));
            } else {
                wiersz.setKwotaPLNMa(przeliczWalutyMa(wiersz));
            }
        }
        if (wnma.equals("Wn")) {
            uzupelnijrozrachunkiWn(aktualnyWierszDlaRozrachunkow, wiersz);
        } else {
            uzupelnijrozrachunkiMa(aktualnyWierszDlaRozrachunkow, wiersz);
        }
            aktualnyWierszDlaRozrachunkow.setRok(wpisView.getRokWpisuSt());
            aktualnyWierszDlaRozrachunkow.setMc(wpisView.getMiesiacWpisu());
            aktualnyWierszDlaRozrachunkow.setDatarozrachunku(Data.aktualnyDzien());
            aktualnyWierszDlaRozrachunkow.setNowatransakcja(false);
            aktualnyWierszDlaRozrachunkow.setWiersz(wiersz);
            
    }
    
    private static void uzupelnijrozrachunkiWn(Rozrachunekfk rozrachunekfk, Wiersze wiersz) {
            rozrachunekfk.setKwotapierwotna(wiersz.getKwotaWn());
            rozrachunekfk.setPozostalo(wiersz.getKwotaWn());
            rozrachunekfk.setKontoid(wiersz.getKontoWn());
    }
    
    private static void uzupelnijrozrachunkiMa(Rozrachunekfk rozrachunekfk, Wiersze wiersz) {
            rozrachunekfk.setKwotapierwotna(wiersz.getKwotaMa());
            rozrachunekfk.setPozostalo(wiersz.getKwotaMa());
            rozrachunekfk.setKontoid(wiersz.getKontoMa());
    }

     private static double przeliczWalutyWn(Wiersze wiersz) {
            wiersz.setKwotaWalutaWn(wiersz.getKwotaWn());
            double kurs = wiersz.getTabelanbp().getKurssredni();
            double kwotazlotowki = wiersz.getKwotaWn();
            kwotazlotowki *= kurs;
            kwotazlotowki *= 100;
            kwotazlotowki = Math.round(kwotazlotowki);
            kwotazlotowki /= 100;
            return kwotazlotowki;
        }
     
     private static double przeliczWalutyMa(Wiersze wiersz) {
            wiersz.setKwotaWalutaMa(wiersz.getKwotaMa());
            double kurs = wiersz.getTabelanbp().getKurssredni();
            double kwotazlotowki = wiersz.getKwotaMa();
            kwotazlotowki *= kurs;
            kwotazlotowki *= 100;
            kwotazlotowki = Math.round(kwotazlotowki);
            kwotazlotowki /= 100;
            return kwotazlotowki;
        }
}
