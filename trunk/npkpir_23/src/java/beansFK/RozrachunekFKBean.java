/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import data.Data;
import entityfk.Dokfk;
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
public class RozrachunekFKBean {
    
//    public static Rozrachunekfk pobierzIstniejacyRozrachunek(RozrachunekfkDAO rozrachunekfkDAO, Rozrachunekfk aktualnyWierszDlaRozrachunkow) {
//        Rozrachunekfk pobranyrozrachunek = rozrachunekfkDAO.findRozrachunekfk(aktualnyWierszDlaRozrachunkow);
//        if (pobranyrozrachunek instanceof Rozrachunekfk) {
//            return pobranyrozrachunek;
//        }
//        return null;
//    }
    
//    public static Rozrachunekfk konstruktorAktualnegoWierszaDlaRozrachunkow(Dokfk selected, WpisView wpisView, String wnma, int nrwiersza) {
//        Rozrachunekfk aktualnyWierszDlaRozrachunkow = new Rozrachunekfk();
//        Wiersz wiersz = selected.getListawierszy().get(nrwiersza);
//        if (wiersz.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
//            if (wnma.equals("Wn")) {
//                wiersz.setKwotaPLNWn(wiersz.getKwotaWn());
//            } else {
//                wiersz.setKwotaPLNMa(wiersz.getKwotaMa());
//            }
//        } else {
//            if (wnma.equals("Wn")) {
//                wiersz.setKwotaPLNWn(przeliczWalutyWn(wiersz));
//            } else {
//                wiersz.setKwotaPLNMa(przeliczWalutyMa(wiersz));
//            }
//        }
//        if (wnma.equals("Wn")) {
//            aktualnyWierszDlaRozrachunkow.setStronaWnlubMa("Wn");
//            uzupelnijrozrachunkiWn(aktualnyWierszDlaRozrachunkow, wiersz);
//            wiersz.setRozrachunekfkWn(aktualnyWierszDlaRozrachunkow);
//        } else {
//            aktualnyWierszDlaRozrachunkow.setStronaWnlubMa("Ma");
//            uzupelnijrozrachunkiMa(aktualnyWierszDlaRozrachunkow, wiersz);
//            wiersz.setRozrachunekfkMa(aktualnyWierszDlaRozrachunkow);
//        }
//            aktualnyWierszDlaRozrachunkow.setRok(wpisView.getRokWpisuSt());
//            aktualnyWierszDlaRozrachunkow.setMc(wpisView.getMiesiacWpisu());
//            aktualnyWierszDlaRozrachunkow.setDatarozrachunku(Data.aktualnyDzien());
//            aktualnyWierszDlaRozrachunkow.setNowatransakcja(false);
//            aktualnyWierszDlaRozrachunkow.setWiersz(wiersz);
//            return aktualnyWierszDlaRozrachunkow;
//    }
//    
//    public static Rozrachunekfk aktualizatorAktualnegoWierszaDlaRozrachunkow(Rozrachunekfk aktualnyWierszDlaRozrachunkow, Dokfk selected, WpisView wpisView, String wnma, int nrwiersza) {
//        Wiersz wiersz = selected.getListawierszy().get(nrwiersza);
//        if (wiersz.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
//            if (wnma.equals("Wn")) {
//                wiersz.setKwotaPLNWn(wiersz.getKwotaWn());
//            } else {
//                wiersz.setKwotaPLNMa(wiersz.getKwotaMa());
//            }
//        } else {
//            if (wnma.equals("Wn")) {
//                wiersz.setKwotaPLNWn(przeliczWalutyWn(wiersz));
//            } else {
//                wiersz.setKwotaPLNMa(przeliczWalutyMa(wiersz));
//            }
//        }
//        if (wnma.equals("Wn")) {
//            aktualnyWierszDlaRozrachunkow.setStronaWnlubMa("Wn");
//            uzupelnijrozrachunkiWn(aktualnyWierszDlaRozrachunkow, wiersz);
//            wiersz.setRozrachunekfkWn(aktualnyWierszDlaRozrachunkow);
//        } else {
//            aktualnyWierszDlaRozrachunkow.setStronaWnlubMa("Ma");
//            uzupelnijrozrachunkiMa(aktualnyWierszDlaRozrachunkow, wiersz);
//            wiersz.setRozrachunekfkMa(aktualnyWierszDlaRozrachunkow);
//        }
//            aktualnyWierszDlaRozrachunkow.setRok(wpisView.getRokWpisuSt());
//            aktualnyWierszDlaRozrachunkow.setMc(wpisView.getMiesiacWpisu());
//            aktualnyWierszDlaRozrachunkow.setDatarozrachunku(Data.aktualnyDzien());
//            aktualnyWierszDlaRozrachunkow.setNowatransakcja(false);
//            aktualnyWierszDlaRozrachunkow.setWiersz(wiersz);
//            return aktualnyWierszDlaRozrachunkow;
//    }
//    
//    private static void uzupelnijrozrachunkiWn(Rozrachunekfk rozrachunekfk, Wiersz wiersz) {
//            rozrachunekfk.setKwotapierwotna(wiersz.getKwotaWn());
//            rozrachunekfk.setPozostalo(wiersz.getKwotaWn());
//            rozrachunekfk.setKontoid(wiersz.getKontoWn());
//    }
//    
//    private static void uzupelnijrozrachunkiMa(Rozrachunekfk rozrachunekfk, Wiersz wiersz) {
//            rozrachunekfk.setKwotapierwotna(wiersz.getKwotaMa());
//            rozrachunekfk.setPozostalo(wiersz.getKwotaMa());
//            rozrachunekfk.setKontoid(wiersz.getKontoMa());
//    }
//
//     private static double przeliczWalutyWn(Wiersz wiersz) {
//            wiersz.setKwotaWalutaWn(wiersz.getKwotaWn());
//            double kurs = wiersz.getTabelanbp().getKurssredni();
//            double kwotazlotowki = wiersz.getKwotaWn();
//            kwotazlotowki *= kurs;
//            kwotazlotowki *= 100;
//            kwotazlotowki = Math.round(kwotazlotowki);
//            kwotazlotowki /= 100;
//            return kwotazlotowki;
//        }
//     
//     private static double przeliczWalutyMa(Wiersz wiersz) {
//            wiersz.setKwotaWalutaMa(wiersz.getKwotaMa());
//            double kurs = wiersz.getTabelanbp().getKurssredni();
//            double kwotazlotowki = wiersz.getKwotaMa();
//            kwotazlotowki *= kurs;
//            kwotazlotowki *= 100;
//            kwotazlotowki = Math.round(kwotazlotowki);
//            kwotazlotowki /= 100;
//            return kwotazlotowki;
//        }
}
