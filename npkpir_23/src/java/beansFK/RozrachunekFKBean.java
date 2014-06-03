/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import daoFK.RozrachunekfkDAO;
import data.Data;
import embeddablefk.WierszStronafk;
import entityfk.Dokfk;
import entityfk.Rozrachunekfk;
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
    
    public static void inicjalizacjaAktualnyWierszDlaRozrachunkow(Rozrachunekfk aktualnyWierszDlaRozrachunkow, Dokfk selected, WpisView wpisView, String wnma, int nrwiersza) {
        WierszStronafk wierszStronafk = new WierszStronafk();
        if (wnma.equals("Wn")) {
            wierszStronafk = selected.getListawierszy().get(nrwiersza).getWierszStronaWn();
            uzupelnikWierszStronafkWaluty(wierszStronafk);
        } else {
            wierszStronafk = selected.getListawierszy().get(nrwiersza).getWierszStronaMa();
            uzupelnikWierszStronafkWaluty(wierszStronafk);
        }
            aktualnyWierszDlaRozrachunkow.setWierszStronafk(wierszStronafk);
            aktualnyWierszDlaRozrachunkow.setKwotapierwotna(wierszStronafk.getKwota());
            aktualnyWierszDlaRozrachunkow.setPozostalo(wierszStronafk.getKwota());
            aktualnyWierszDlaRozrachunkow.setKontoid(wierszStronafk.getKonto());
            aktualnyWierszDlaRozrachunkow.setWalutarozrachunku(wierszStronafk.getSymbolwaluty());
            aktualnyWierszDlaRozrachunkow.setRok(wpisView.getRokWpisuSt());
            aktualnyWierszDlaRozrachunkow.setMc(wpisView.getMiesiacWpisu());
            aktualnyWierszDlaRozrachunkow.setDatarozrachunku(Data.aktualnyDzien());
    }
    
     private static void uzupelnikWierszStronafkWaluty(WierszStronafk wierszStronafk) {
        String symbolwaluty = wierszStronafk.getSymbolwaluty();
        if (symbolwaluty.equals("PLN")) {
            wierszStronafk.setKwotaPLN(wierszStronafk.getKwota());
        } else {
            wierszStronafk.setKwotaWaluta(wierszStronafk.getKwota());
            double kurs = wierszStronafk.getKurswaluty();
            double kwotazlotowki = wierszStronafk.getKwota();
            kwotazlotowki *= kurs;
            kwotazlotowki *= 100;
            kwotazlotowki = Math.round(kwotazlotowki);
            kwotazlotowki /= 100;
            wierszStronafk.setKwotaPLN(kwotazlotowki);
        }
    }
}
