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
import java.util.ArrayList;
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
    
    //sluzy do sprawdzenia czy wprowadzono wszystkie kwoty
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
    
    public static Wiersz WierszFaktory(Dokfk selected, int typwiersza, double roznica) {
        int liczbawierszyWDokumencie = 0;
        try {
            liczbawierszyWDokumencie = selected.getListawierszy().size()+1;
        } catch (Exception e) {
        }
        switch (typwiersza) {
            case 0:
                return utworzNowyWiersz(selected, liczbawierszyWDokumencie);
            case 1:
                return utworzNowyWierszWn(selected, liczbawierszyWDokumencie, roznica);
            case 2:
                return utworzNowyWierszMa(selected, liczbawierszyWDokumencie, roznica);
            default:
                return utworzNowyWiersz(selected, liczbawierszyWDokumencie);
        }
    }
    
    public static void dodajiPrzenumerujWiersze (Dokfk selected, Wiersz wiersz, int wierszbiezacyIndex) {
        List<Wiersz> przenumerowanaLista = new ArrayList<>();
        int nowaliczbawieszy = selected.getListawierszy().size()+1;
        int indexNowegoWiersza = wierszbiezacyIndex+1;
        for (int i = 0; i < nowaliczbawieszy; i++) {
            if (i < indexNowegoWiersza) {
                przenumerowanaLista.add(selected.getListawierszy().get(i));
            } else if (i == indexNowegoWiersza) {
                wiersz.setIdporzadkowy(i+1);
                przenumerowanaLista.add(wiersz);
            } else if (i > indexNowegoWiersza) {
                selected.getListawierszy().get(i-1).setIdporzadkowy(i+1);
                przenumerowanaLista.add(selected.getListawierszy().get(i-1));
            }
        }
        selected.setListawierszy(przenumerowanaLista);
    }

  
    public static void wygenerujiDodajWiersz(Dokfk selected, int liczbawierszyWDokumencie, int wierszbiezacyIndex, boolean przenumeruj, double roznica, int typwiersza) {
        Wiersz wiersz = WierszFaktory(selected, typwiersza, roznica);
        if (przenumeruj == false) {
            selected.getListawierszy().add(wiersz);
        } else {
            ObslugaWiersza.dodajiPrzenumerujWiersze(selected, wiersz, wierszbiezacyIndex);
        }
    }
}
