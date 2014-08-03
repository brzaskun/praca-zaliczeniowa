/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk.subroutines;

import com.sun.msv.datatype.xsd.Comparator;
import comparator.Wierszcomparator;
import entityfk.Dokfk;
import entityfk.StronaWiersza;
import entityfk.Wiersz;
import java.util.Collections;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class ObslugaWiersza {
    
    public static boolean sprawdzSumyWierszy(Dokfk dokfk) {
        double stronalewa = 0.0;
        double stronaprawa = 0.0;
        List<Wiersz> listawierszy = dokfk.getListawierszy();
        for (Wiersz p : listawierszy) {
            if (p.getTypWiersza() == 0) {
                stronalewa += p.getStronaWn().getKwota();
                stronaprawa += p.getStronaMa().getKwota();
            } else if (p.getTypWiersza()== 1) {
                stronalewa += p.getStronaWn().getKwota();
            } else if (p.getTypWiersza()== 2) {
                stronaprawa += p.getStronaMa().getKwota();
            }
        }
        return stronalewa == stronaprawa;
    }
    
    public static Wiersz utworzNowyWiersz(Dokfk selected, int liczbawierszyWDokumencie)  {
        Wiersz nowywiersz = new Wiersz(liczbawierszyWDokumencie, 0);
        uzupelnijDane(nowywiersz, selected);
        return nowywiersz;
    }
    
    public static Wiersz ustawPierwszyWiersz(Dokfk dokfk) {
        Wiersz nowywiersz =  new Wiersz(1, 0);
        uzupelnijDane(nowywiersz, dokfk);
        return nowywiersz;
    }
    
    private static void uzupelnijDane (Wiersz nowywiersz, Dokfk selected) {
        nowywiersz.setDokfk(selected);
        nowywiersz.setTabelanbp(selected.getTabelanbp());
        StronaWiersza stronaWn = new StronaWiersza(nowywiersz, "Wn");
        StronaWiersza stronaMa = new StronaWiersza(nowywiersz, "Ma");
        nowywiersz.setStronaWn(stronaWn);
        nowywiersz.setStronaMa(stronaMa);
    }
    
    public static Wiersz utworzNowyWierszMa(Dokfk selected, int liczbawierszyWDokumencie, double kwota)  {
        Wiersz nowywiersz = new Wiersz(liczbawierszyWDokumencie, 2);
        nowywiersz.setDokfk(selected);
        nowywiersz.setTabelanbp(selected.getTabelanbp());
        StronaWiersza stronaMa = new StronaWiersza(nowywiersz, "Ma", kwota);
        nowywiersz.setStronaMa(stronaMa);
        return nowywiersz;
    }
    
    public static Wiersz utworzNowyWierszWn(Dokfk selected, int liczbawierszyWDokumencie, double kwota)  {
        Wiersz nowywiersz = new Wiersz(liczbawierszyWDokumencie, 1);
        nowywiersz.setDokfk(selected);
        nowywiersz.setTabelanbp(selected.getTabelanbp());
        StronaWiersza stronaWn = new StronaWiersza(nowywiersz, "Wn", kwota);
        nowywiersz.setStronaWn(stronaWn);
        return nowywiersz;
    }

    public static double obliczkwotepozostala(Dokfk selected, Wiersz wierszbiezacy) {
        List<Wiersz> lista = selected.getListawierszy();
        Collections.sort(lista, new Wierszcomparator());
        int numerwiersza = wierszbiezacy.getIdporzadkowy();
        double kwotawielka = 0.0;
        double sumaczastowych = 0.0;
        for (int i = numerwiersza; i > 0; i--) {
            if(wierszbiezacy.getTypWiersza() == 2) {
                if (lista.get(i-1).getTypWiersza()==2) {
                    sumaczastowych += lista.get(i-1).getStronaMa().getKwota();
                } else if (lista.get(i-1).getTypWiersza()==0) {
                    sumaczastowych += lista.get(i-1).getStronaMa().getKwota();
                    kwotawielka +=  lista.get(i-1).getStronaWn().getKwota();
                    break;
                }
            } else if (wierszbiezacy.getTypWiersza() == 1) {
                if (lista.get(i-1).getTypWiersza()==1) {
                    sumaczastowych += lista.get(i-1).getStronaWn().getKwota();
                } else if (lista.get(i-1).getTypWiersza()==0) {
                    sumaczastowych += lista.get(i-1).getStronaWn().getKwota();
                    kwotawielka +=  lista.get(i-1).getStronaMa().getKwota();
                    break;
                }
            } else if (wierszbiezacy.getTypWiersza() == 0) {
                kwotawielka = wierszbiezacy.getStronaWn().getKwota();
            }
        }
        return kwotawielka-sumaczastowych;
    }
}
