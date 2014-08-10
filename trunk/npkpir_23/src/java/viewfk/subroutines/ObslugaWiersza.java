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
        nowywiersz.setLpmacierzystego(0);
        uzupelnijDane(nowywiersz, selected);
        return nowywiersz;
    }
    
    public static Wiersz ustawPierwszyWiersz(Dokfk dokfk) {
        Wiersz nowywiersz =  new Wiersz(1, 0);
        nowywiersz.setLpmacierzystego(0);
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
    
    public static Wiersz utworzNowyWierszMa(Dokfk selected, int liczbawierszyWDokumencie, double kwota, int lpmacierzystego)  {
        Wiersz nowywiersz = new Wiersz(liczbawierszyWDokumencie, 2);
        nowywiersz.setDokfk(selected);
        nowywiersz.setTabelanbp(selected.getTabelanbp());
        StronaWiersza stronaMa = new StronaWiersza(nowywiersz, "Ma", kwota);
        nowywiersz.setStronaMa(stronaMa);
        nowywiersz.setLpmacierzystego(lpmacierzystego);
        return nowywiersz;
    }
    
    public static Wiersz utworzNowyWierszWn(Dokfk selected, int liczbawierszyWDokumencie, double kwota, int lpmacierzystego)  {
        Wiersz nowywiersz = new Wiersz(liczbawierszyWDokumencie, 1);
        nowywiersz.setDokfk(selected);
        nowywiersz.setTabelanbp(selected.getTabelanbp());
        StronaWiersza stronaWn = new StronaWiersza(nowywiersz, "Wn", kwota);
        nowywiersz.setStronaWn(stronaWn);
        nowywiersz.setLpmacierzystego(lpmacierzystego);
        return nowywiersz;
    }

    public static double obliczkwotepozostala(Dokfk selected, Wiersz wierszbiezacy) {
        List<Wiersz> lista = selected.getListawierszy();
        Collections.sort(lista, new Wierszcomparator());
        int lpmerwiersza = wierszbiezacy.getIdporzadkowy();
        double kwotawielka = 0.0;
        double sumaczastowych = 0.0;
        //idziemy w gore i sumujemy
        for (int i = lpmerwiersza; i > 0; i--) {
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
                    if (wierszbiezacy.getStronaWn().getKwota() > wierszbiezacy.getStronaMa().getKwota()) {
                        kwotawielka = wierszbiezacy.getStronaWn().getKwota();
                        sumaczastowych = wierszbiezacy.getStronaMa().getKwota();
                        break;
                    } else {
                        kwotawielka = wierszbiezacy.getStronaMa().getKwota();
                        sumaczastowych = wierszbiezacy.getStronaWn().getKwota();
                        break;
                    }
            }
        }
        int ostatnielpwiersza = selected.getListawierszy().size()+1;
        //idziemy w dol i sumujemy/wiersz moze byc po srodku
        for (int i = lpmerwiersza+1; i < ostatnielpwiersza; i++) {
            if(wierszbiezacy.getTypWiersza() == 2) {
                if (lista.get(i-1).getTypWiersza()==2) {
                    sumaczastowych += lista.get(i-1).getStronaMa().getKwota();
                } else if (lista.get(i-1).getTypWiersza()==0) {
                    break;
                }
            } else if (wierszbiezacy.getTypWiersza() == 1) {
                if (lista.get(i-1).getTypWiersza()==1) {
                    sumaczastowych += lista.get(i-1).getStronaWn().getKwota();
                } else if (lista.get(i-1).getTypWiersza()==0) {
                    // bo dotarlismy do nastepnego macierzystego
                    break;
                }
                //to jest bo mozemy dodawac tuz od wiersza 0 z podczepionymi innymi
            } else if (wierszbiezacy.getTypWiersza() == 0 && (lista.get(i-1).getTypWiersza() != 0)) {
                if (lista.get(i-1).getTypWiersza() == 2) {
                    sumaczastowych += lista.get(i-1).getStronaMa().getKwota();
                } else {
                    sumaczastowych += lista.get(i-1).getStronaWn().getKwota();
                }
            } else {
                break;
            }
        }
        return kwotawielka-sumaczastowych;
    }
    
    public static Wiersz WierszFaktory(Dokfk selected, int typwiersza, double roznica, int lpmacierzystego) {
        int liczbawierszyWDokumencie = 0;
        try {
            liczbawierszyWDokumencie = selected.getListawierszy().size()+1;
        } catch (Exception e) {
        }
        switch (typwiersza) {
            case 0:
                return utworzNowyWiersz(selected, liczbawierszyWDokumencie);
            case 1:
                return utworzNowyWierszWn(selected, liczbawierszyWDokumencie, roznica, lpmacierzystego);
            case 2:
                return utworzNowyWierszMa(selected, liczbawierszyWDokumencie, roznica, lpmacierzystego);
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

    public static void przenumerujWierszePoUsunieciu(Dokfk selected) {
        List<Wiersz> wiersze = selected.getListawierszy();
        int i = 1;
        for (Wiersz p : wiersze) {
            p.setIdporzadkowy(i++);
        }
                
    }
  
    public static void wygenerujiDodajWiersz(Dokfk selected, int liczbawierszyWDokumencie, int wierszbiezacyIndex, boolean przenumeruj, double roznica, int typwiersza) {
        int lpmacierzystego = znajdzmacierzysty(selected.getListawierszy(), wierszbiezacyIndex);
        Wiersz wiersz = WierszFaktory(selected, typwiersza, roznica, lpmacierzystego);
        if (przenumeruj == false) {
            selected.getListawierszy().add(wiersz);
        } else {
            ObslugaWiersza.dodajiPrzenumerujWiersze(selected, wiersz, wierszbiezacyIndex);
        }
    }
    
    private static int znajdzmacierzysty(List<Wiersz> listawierszy, int wierszbiezacyIndex) {
        int nowaliczbawieszy = listawierszy.size()+1;
        int lpwiersza = wierszbiezacyIndex+1;
        for (int i = lpwiersza; i > 0; i--) {
            if (listawierszy.get(i-1).getTypWiersza()==0) {
                return listawierszy.get(i-1).getIdporzadkowy();
            }
        }
        return 0;
    }

    public static void sprawdzKwotePozostala(Dokfk selected, Wiersz wybranyWiersz) {
        double roznica = obliczkwotepozostala(selected, wybranyWiersz);
        if (roznica != 0.0 ) {
            if (wybranyWiersz.getTypWiersza() == 1) {
                wygenerujiDodajWiersz(selected, selected.getListawierszy().size(), wybranyWiersz.getIdporzadkowy()-1, true, roznica, 1);
            } else if (wybranyWiersz.getTypWiersza() == 2) {
                wygenerujiDodajWiersz(selected, selected.getListawierszy().size(), wybranyWiersz.getIdporzadkowy()-1, true, roznica, 2);
            }
        }
    }

   
}
