/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk.subroutines;

import com.sun.msv.datatype.xsd.Comparator;
import comparator.Wierszcomparator;
import entityfk.Dokfk;
import entityfk.Konto;
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
        Konto kontoWn;
        Konto kontoMa;
        List<Wiersz> listawierszy = dokfk.getListawierszy();
        Collections.sort(listawierszy, new Wierszcomparator());
        //na poczatek sprawdzimy czy w ostatnim nie ma pustych kwot
        Wiersz ostatniwiersz = listawierszy.get(listawierszy.size()-1);
        if (ostatniwiersz.getTypWiersza() == 0 || ostatniwiersz.getTypWiersza() == 5) {
                stronalewa += ostatniwiersz.getStronaWn().getKwota();
                stronaprawa += ostatniwiersz.getStronaMa().getKwota();
                kontoWn = ostatniwiersz.getStronaWn().getKonto();
                kontoMa = ostatniwiersz.getStronaMa().getKonto();
                if (stronalewa == 0 || stronaprawa == 0) {
                    return false;
                }
                if (!(kontoWn instanceof Konto) || !(kontoMa instanceof Konto)) {
                    return false;
                }
            } else if (ostatniwiersz.getTypWiersza()== 1 || ostatniwiersz.getTypWiersza() == 6) {
                stronalewa += ostatniwiersz.getStronaWn().getKwota();
                kontoWn = ostatniwiersz.getStronaWn().getKonto();
                if (stronalewa == 0) {
                    return false;
                }
                if (!(kontoWn instanceof Konto)) {
                    return false;
                }
            } else if (ostatniwiersz.getTypWiersza()== 2 || ostatniwiersz.getTypWiersza() == 7) {
                stronaprawa += ostatniwiersz.getStronaMa().getKwota();
                kontoMa = ostatniwiersz.getStronaMa().getKonto();
                if (stronaprawa == 0) {
                    return false;
                }
                if (!(kontoMa instanceof Konto)) {
                    return false;
                }
            }
        stronalewa = 0.0;
        stronaprawa = 0.0;
        //teraz sprawdzamy czy lewa == prawa
        for (Wiersz p : listawierszy) {
            if (p.getTypWiersza() == 0 || p.getTypWiersza() == 5) {
                stronalewa += p.getStronaWn().getKwota();
                stronaprawa += p.getStronaMa().getKwota();
            } else if (p.getTypWiersza()== 1 || p.getTypWiersza() == 6) {
                stronalewa += p.getStronaWn().getKwota();
            } else if (p.getTypWiersza()== 2 || p.getTypWiersza() == 7) {
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
    
    public static Wiersz utworzNowyWierszMa5(Dokfk selected, int liczbawierszyWDokumencie, double kwota, int lpmacierzystego)  {
        Wiersz nowywiersz = new Wiersz(liczbawierszyWDokumencie, 7);
        nowywiersz.setDokfk(selected);
        nowywiersz.setTabelanbp(selected.getTabelanbp());
        StronaWiersza stronaMa = new StronaWiersza(nowywiersz, "Ma", kwota);
        nowywiersz.setStronaMa(stronaMa);
        nowywiersz.setLpmacierzystego(lpmacierzystego);
        return nowywiersz;
    }
    
     public static Wiersz utworzNowyWiersz5(Dokfk selected, int liczbawierszyWDokumencie, double kwota, int lpmacierzystego)  {
        Wiersz nowywiersz = new Wiersz(liczbawierszyWDokumencie, 5);
        nowywiersz.setDokfk(selected);
        nowywiersz.setTabelanbp(selected.getTabelanbp());
        StronaWiersza stronaWn = new StronaWiersza(nowywiersz, "Wn");
        nowywiersz.setStronaWn(stronaWn);
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
    
    public static Wiersz utworzNowyWierszWn6(Dokfk selected, int liczbawierszyWDokumencie, double kwota, int lpmacierzystego)  {
        Wiersz nowywiersz = new Wiersz(liczbawierszyWDokumencie, 6);
        nowywiersz.setDokfk(selected);
        nowywiersz.setTabelanbp(selected.getTabelanbp());
        StronaWiersza stronaWn = new StronaWiersza(nowywiersz, "Wn", kwota);
        nowywiersz.setStronaWn(stronaWn);
        nowywiersz.setLpmacierzystego(lpmacierzystego);
        return nowywiersz;
    }
    
    public static Wiersz utworzNowyWierszWn5(Dokfk selected, int liczbawierszyWDokumencie, double kwota, int lpmacierzystego)  {
        Wiersz nowywiersz = new Wiersz(liczbawierszyWDokumencie, 6);
        nowywiersz.setDokfk(selected);
        nowywiersz.setTabelanbp(selected.getTabelanbp());
        StronaWiersza stronaWn = new StronaWiersza(nowywiersz, "Wn", kwota);
        nowywiersz.setStronaWn(stronaWn);
        nowywiersz.setLpmacierzystego(lpmacierzystego);
        return nowywiersz;
    }

    public static double obliczkwotepozostala5(Dokfk selected, Wiersz wierszbiezacy) {
        List<Wiersz> lista = selected.getListawierszy();
        Collections.sort(lista, new Wierszcomparator());
        int lpmerwiersza = wierszbiezacy.getIdporzadkowy();
        double kwotawielka = 0.0;
        double sumaczastowych = 0.0;
        //idziemy w gore i sumujemy
        for (int i = lpmerwiersza; i > 0; i--) {
            //jest i-2 bo i-1 jest usuniety i na jego miejsce wpasl nizej polozony wiersz
            int iW = i-2;
            if(wierszbiezacy.getTypWiersza() == 7) {
                if (lista.get(iW).getTypWiersza()==2 || lista.get(iW).getTypWiersza()==7) {
                    sumaczastowych += lista.get(iW).getStronaMa().getKwota();
                } else if (lista.get(iW).getTypWiersza()==0 || lista.get(iW).getTypWiersza()==5) {
                    sumaczastowych += lista.get(iW).getStronaMa().getKwota();
                    kwotawielka +=  lista.get(iW).getStronaWn().getKwota();
                    break;
                }
            } else if (wierszbiezacy.getTypWiersza() == 6) {
                if (lista.get(iW).getTypWiersza()==1 || lista.get(iW).getTypWiersza()==6) {
                    sumaczastowych += lista.get(iW).getStronaWn().getKwota();
                } else if (lista.get(iW).getTypWiersza()==0 || lista.get(iW).getTypWiersza()==5) {
                    sumaczastowych += lista.get(iW).getStronaWn().getKwota();
                    kwotawielka +=  lista.get(iW).getStronaMa().getKwota();
                    break;
                }
            } else if (wierszbiezacy.getTypWiersza() == 5) {
                //jak tego nie bedzie to wyjda minusy potem, bo return jest bez abs
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
        try {
            //moze nie byc nic na koniec
            for (int i = lpmerwiersza; i < ostatnielpwiersza; i++) {
                int iW = i-1;
                if(wierszbiezacy.getTypWiersza() == 7) {
                    if (lista.get(iW).getTypWiersza()==2 || wierszbiezacy.getTypWiersza() == 7) {
                        sumaczastowych += lista.get(iW).getStronaMa().getKwota();
                    } else if (lista.get(iW).getTypWiersza()==0) {
                        break;
                    }
                } else if (wierszbiezacy.getTypWiersza() == 6) {
                    if (lista.get(iW).getTypWiersza()==1 || wierszbiezacy.getTypWiersza() == 6) {
                        sumaczastowych += lista.get(iW).getStronaWn().getKwota();
                    } else if (lista.get(iW).getTypWiersza()==0) {
                        // bo dotarlismy do nastepnego macierzystego
                        break;
                    }
                    //to jest bo mozemy dodawac tuz od wiersza 0 z podczepionymi innymi
                } else if (wierszbiezacy.getTypWiersza() == 5 && lista.get(i).getTypWiersza() != 5) {
                    if (wierszbiezacy.getTypWiersza() == 7) {
                        sumaczastowych += lista.get(i).getStronaMa().getKwota();
                    } else if (wierszbiezacy.getTypWiersza() == 6){
                        sumaczastowych += lista.get(i).getStronaWn().getKwota();
                    }
                } else {
                    break;
                }
                }
            } catch (Exception e) {
                    
                    }
        return kwotawielka-sumaczastowych;
    }
    
    public static double obliczkwotepozostala(Dokfk selected, Wiersz wierszbiezacy) {
        List<Wiersz> lista = selected.getListawierszy();
        Collections.sort(lista, new Wierszcomparator());
        int lpmerwiersza = wierszbiezacy.getIdporzadkowy();
        double kwotawielka = 0.0;
        double sumaczastowych = 0.0;
        //idziemy w gore i sumujemy
        for (int i = lpmerwiersza; i > 0; i--) {
            //jest i-2 bo i-1 jest usuniety i na jego miejsce wpasl nizej polozony wiersz
            int iW = i-2;
            if(wierszbiezacy.getTypWiersza() == 2) {
                if (lista.get(iW).getTypWiersza()==2 || lista.get(iW).getTypWiersza()==7) {
                    sumaczastowych += lista.get(iW).getStronaMa().getKwota();
                } else if (lista.get(iW).getTypWiersza()==0 || lista.get(iW).getTypWiersza()==5) {
                    sumaczastowych += lista.get(iW).getStronaMa().getKwota();
                    kwotawielka +=  lista.get(iW).getStronaWn().getKwota();
                    break;
                }
            } else if (wierszbiezacy.getTypWiersza() == 1) {
                if (lista.get(iW).getTypWiersza()==1 || lista.get(iW).getTypWiersza()==6) {
                    sumaczastowych += lista.get(iW).getStronaWn().getKwota();
                } else if (lista.get(iW).getTypWiersza()==0 || lista.get(iW).getTypWiersza()==5) {
                    sumaczastowych += lista.get(iW).getStronaWn().getKwota();
                    kwotawielka +=  lista.get(iW).getStronaMa().getKwota();
                    break;
                }
            } else if (wierszbiezacy.getTypWiersza() == 0) {
                //jak tego nie bedzie to wyjda minusy potem, bo return jest bez abs
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
        try {
            //moze nie byc nic na koniec
            for (int i = lpmerwiersza; i < ostatnielpwiersza; i++) {
                int iW = i-1;
                if(wierszbiezacy.getTypWiersza() == 2) {
                    if (lista.get(iW).getTypWiersza()==2) {
                        sumaczastowych += lista.get(iW).getStronaMa().getKwota();
                    } else if (lista.get(iW).getTypWiersza()==0) {
                        break;
                    }
                } else if (wierszbiezacy.getTypWiersza() == 1) {
                    if (lista.get(iW).getTypWiersza()==1) {
                        sumaczastowych += lista.get(iW).getStronaWn().getKwota();
                    } else if (lista.get(iW).getTypWiersza()==0) {
                        // bo dotarlismy do nastepnego macierzystego
                        break;
                    }
                    //to jest bo mozemy dodawac tuz od wiersza 0 z podczepionymi innymi
                } else if (wierszbiezacy.getTypWiersza() == 0  && lista.get(i).getTypWiersza() != 0) {
                    if (lista.get(i).getTypWiersza() == 2) {
                        sumaczastowych += lista.get(i).getStronaMa().getKwota();
                    } else if (lista.get(i).getTypWiersza() == 1){
                        sumaczastowych += lista.get(i).getStronaWn().getKwota();
                    }
                } else {
                    break;
                }
                }
            } catch (Exception e) {
                    
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
            case 5:
                return utworzNowyWiersz5(selected, liczbawierszyWDokumencie, roznica, lpmacierzystego);
            case 6:
                return utworzNowyWierszWn5(selected, liczbawierszyWDokumencie, roznica, lpmacierzystego);
            case 7:
                return utworzNowyWierszMa5(selected, liczbawierszyWDokumencie, roznica, lpmacierzystego);
            default:
                return utworzNowyWiersz(selected, liczbawierszyWDokumencie);
        }
    }
    
    public static void dodajiPrzenumerujWiersze (Dokfk selected, Wiersz wiersz, int lpmacierzystego) {
        List<Wiersz> przenumerowanaLista = new ArrayList<>();
        int nowaliczbawieszy = selected.getListawierszy().size()+1;
        int lpNowegoWiersza = lpmacierzystego;
        List<Wiersz> listawierszy = selected.getListawierszy();
        for (Wiersz s : listawierszy) {
            if (s.getIdporzadkowy() > lpmacierzystego) {
                if (s.getTypWiersza() == 0 && (lpNowegoWiersza == lpmacierzystego)) {
                    lpNowegoWiersza = lpmacierzystego+1;
                    break;
                } else if (s.getTypWiersza() != 0 && (lpNowegoWiersza == lpmacierzystego)) {
                    lpNowegoWiersza += 2;
                } else if (s.getTypWiersza() == 0) {
                    break;
                } else {
                    lpNowegoWiersza++;
                }
            }
        }
        int indexNowegoWiersza = lpNowegoWiersza-1;
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
    
    public static void dodajiPrzenumerujWiersze5 (Dokfk selected, Wiersz wiersz, int lpmacierzystego) {
        List<Wiersz> przenumerowanaLista = new ArrayList<>();
        int nowaliczbawieszy = selected.getListawierszy().size()+1;
        int lpNowegoWiersza = lpmacierzystego;
        List<Wiersz> listawierszy = selected.getListawierszy();
        for (Wiersz s : listawierszy) {
            if (s.getIdporzadkowy() > lpmacierzystego) {
                if (s.getTypWiersza() == 5 && (lpNowegoWiersza == lpmacierzystego)) {
                    lpNowegoWiersza = lpmacierzystego+1;
                    break;
                } else if (s.getTypWiersza() == 7 && (lpNowegoWiersza == lpmacierzystego)) {
                    lpNowegoWiersza += 2;
                } else if (s.getTypWiersza() == 5) {
                    break;
                } else {
                    lpNowegoWiersza++;
                }
                //dodajemy to bo jest zawsze przenumeruj. w zwuklym, wierszu nie ma przenumeruj i wtedy daje jako numer ostatni numer wiersza
            } 
        }
        if (lpNowegoWiersza==lpmacierzystego) {
                lpNowegoWiersza = lpmacierzystego+1;
        }
        int indexNowegoWiersza = lpNowegoWiersza-1;
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
  
    public static void wygenerujiDodajWierszUsun(Dokfk selected, int liczbawierszyWDokumencie, int wierszbiezacyIndex, boolean przenumeruj, double roznica, int typwiersza) {
        int lpmacierzystego = znajdzmacierzystyUsun(selected.getListawierszy(), wierszbiezacyIndex);
        Wiersz wiersz = WierszFaktory(selected, typwiersza, roznica, lpmacierzystego);
        if (przenumeruj == false) {
            selected.getListawierszy().add(wiersz);
        } else {
            ObslugaWiersza.dodajiPrzenumerujWiersze(selected, wiersz, lpmacierzystego);
        }
    }
    
     public static void wygenerujiDodajWiersz(Dokfk selected, int liczbawierszyWDokumencie, int wierszbiezacyIndex, boolean przenumeruj, double roznica, int typwiersza) {
        int lpmacierzystego = znajdzmacierzysty(selected.getListawierszy(), wierszbiezacyIndex);
        Wiersz wiersz = WierszFaktory(selected, typwiersza, roznica, lpmacierzystego);
        if (przenumeruj == false) {
            selected.getListawierszy().add(wiersz);
        } else {
            ObslugaWiersza.dodajiPrzenumerujWiersze(selected, wiersz, lpmacierzystego);
        }
    }
     
     public static void wygenerujiDodajWierszPiatka(Dokfk selected, int liczbawierszyWDokumencie, int wierszbiezacyIndex, boolean przenumeruj, double roznica, int typwiersza, Wiersz czworka, Konto konto490) {
        int lpmacierzystego = znajdzmacierzysty5(selected.getListawierszy(), wierszbiezacyIndex);
        Wiersz nowywiersz = WierszFaktory(selected, typwiersza, roznica, lpmacierzystego);
        Wiersz sprawdzonaczworka;
        if (czworka.getTypWiersza() == 0 || czworka.getTypWiersza() == 1 ||czworka.getTypWiersza() == 2) {
            nowywiersz.setCzworka(czworka);
            sprawdzonaczworka = czworka;
            sprawdzonaczworka.getPiatki().add(nowywiersz);
        } else {
            nowywiersz.setCzworka(czworka.getCzworka());
            sprawdzonaczworka = czworka.getCzworka();
            sprawdzonaczworka.getPiatki().add(nowywiersz);
        }
        if (typwiersza == 5) {
            nowywiersz.getStronaMa().setKonto(konto490);
            nowywiersz.getStronaMa().setTypStronaWiersza(5);
        }
        if (przenumeruj == false) {
            selected.getListawierszy().add(nowywiersz);
        } else {
            ObslugaWiersza.dodajiPrzenumerujWiersze5(selected, nowywiersz, lpmacierzystego);
        }
    }
    
    private static int znajdzmacierzystyUsun(List<Wiersz> listawierszy, int wierszbiezacyIndex) {
        for (int i = wierszbiezacyIndex; i > 0; i--) {
            if (listawierszy.get(i-2).getTypWiersza()==0) {
                return listawierszy.get(i-2).getIdporzadkowy();
            }
        }
        return 0;
    }
    private static int znajdzmacierzysty(List<Wiersz> listawierszy, int wierszbiezacyLP) {
        int wierszbiezacyIndex = wierszbiezacyLP -1;
        for (int i = wierszbiezacyIndex; i > -1; i--) {
            if (listawierszy.get(i).getTypWiersza()==0) {
                return listawierszy.get(i).getIdporzadkowy();
            }
        }
        return 0;
    }
    
    private static int znajdzmacierzysty5(List<Wiersz> listawierszy, int wierszbiezacyLP) {
        int wierszbiezacyIndex = wierszbiezacyLP;
        for (int i = wierszbiezacyIndex; i > -1; i--) {
            if (listawierszy.get(i).getTypWiersza()==5) {
                return listawierszy.get(i).getIdporzadkowy();
            }
        }
        return 0;
    }

    public static void sprawdzKwotePozostala(Dokfk selected, Wiersz wybranyWiersz, String wierszeSasiednie) {
        double roznica = obliczkwotepozostala(selected, wybranyWiersz);
        if (roznica > 0.0 ) {
            if (wybranyWiersz.getTypWiersza() == 1) {
                wygenerujiDodajWierszUsun(selected, selected.getListawierszy().size(), wybranyWiersz.getIdporzadkowy(), true, roznica, 1);
            } else if (wybranyWiersz.getTypWiersza() == 2) {
                wygenerujiDodajWierszUsun(selected, selected.getListawierszy().size(), wybranyWiersz.getIdporzadkowy(), true, roznica, 2);
            }
        } if (roznica < 0.0) {
            if (wybranyWiersz.getTypWiersza() == 1) {
                wygenerujiDodajWierszUsun(selected, selected.getListawierszy().size(), wybranyWiersz.getIdporzadkowy(), true, roznica, 2);
            } else if (wybranyWiersz.getTypWiersza() == 2) {
                wygenerujiDodajWierszUsun(selected, selected.getListawierszy().size(), wybranyWiersz.getIdporzadkowy(), true, roznica, 1);
            }
        }
    }

   
}
