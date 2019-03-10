/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk.subroutines;

import comparator.Wierszcomparator;
import daoFK.KontoDAOfk;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Wiersz;
import error.E;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.context.RequestContext;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named

public class ObslugaWiersza {
    
    //sluzy do sprawdzenia czy wprowadzono wszystkie kwoty
    public static boolean sprawdzSumyWierszy(Dokfk dokfk) {
        if (dokfk.getSeriadokfk().equals("BO")) {
            return true;
        }
        if (dokfk.getRodzajedok().isTylkojpk()) {
            return true;
        }
        double stronalewa = 0.0;
        double stronaprawa = 0.0;
        Konto kontoWn;
        Konto kontoMa;
        List<Wiersz> listawierszy = dokfk.getListawierszy();
        Collections.sort(listawierszy, new Wierszcomparator());
        //na poczatek sprawdzimy czy w ostatnim nie ma pustych kwot
        Wiersz ostatniwiersz = listawierszy.get(listawierszy.size()-1);
        int numerwiersza = ostatniwiersz.getIdporzadkowy()-1;
        String f = "podswietlznalezionywierszzbrakiem("+numerwiersza+")";
        if (ostatniwiersz.getTypWiersza() == 0 || ostatniwiersz.getTypWiersza() == 5) {
                stronalewa += ostatniwiersz.getStronaWn().getKwota();
                stronaprawa += ostatniwiersz.getStronaMa().getKwota();
                kontoWn = ostatniwiersz.getStronaWn().getKonto();
                kontoMa = ostatniwiersz.getStronaMa().getKonto();
                if (stronalewa == 0 || stronaprawa == 0) {
                    RequestContext.getCurrentInstance().execute(f);
                    return false;
                }
                if (!(kontoWn instanceof Konto) || !(kontoMa instanceof Konto)) {
                    RequestContext.getCurrentInstance().execute(f);
                    return false;
                }
                if (kontoWn instanceof Konto && kontoMa instanceof Konto && kontoWn.getPelnynumer().equals(kontoMa.getPelnynumer())) {
                    RequestContext.getCurrentInstance().execute(f);
                    return false;
                }
            } else if (ostatniwiersz.getTypWiersza()== 1 || ostatniwiersz.getTypWiersza() == 6) {
                stronalewa += ostatniwiersz.getStronaWn().getKwota();
                kontoWn = ostatniwiersz.getStronaWn().getKonto();
                if (stronalewa == 0) {
                    RequestContext.getCurrentInstance().execute(f);
                    return false;
                }
                if (!(kontoWn instanceof Konto)) {
                    RequestContext.getCurrentInstance().execute(f);
                    return false;
                }
            } else if (ostatniwiersz.getTypWiersza()== 2 || ostatniwiersz.getTypWiersza() == 7) {
                stronaprawa += ostatniwiersz.getStronaMa().getKwota();
                kontoMa = ostatniwiersz.getStronaMa().getKonto();
                if (stronaprawa == 0) {
                    RequestContext.getCurrentInstance().execute(f);
                    return false;
                }
                if (!(kontoMa instanceof Konto)) {
                    RequestContext.getCurrentInstance().execute(f);
                    return false;
                }
            }
        stronalewa = 0.0;
        stronaprawa = 0.0;
        int numergrupybiezacej = 0;
        int numergrupypoprzedniej = 0;
        int typwierszapoprzedniego = 0;
        //teraz sprawdzamy czy lewa == prawa
        for (Wiersz p : listawierszy) {
            numergrupybiezacej = p.getLpmacierzystego() == 0 ? p.getIdporzadkowy() : p.getLpmacierzystego();
            numerwiersza = numergrupybiezacej-2;
            f = "podswietlznalezionywierszzbrakiem("+numerwiersza+")";
            boolean czynowyzero = false;
            if (p.getTypWiersza() == 0 && typwierszapoprzedniego == 0) {
                czynowyzero = true;
            }
            typwierszapoprzedniego = p.getTypWiersza();
            if (numergrupybiezacej != numergrupypoprzedniej || czynowyzero) {
                numergrupypoprzedniej = numergrupybiezacej;
                if (Z.z(stronalewa) != Z.z(stronaprawa)) {
                    RequestContext.getCurrentInstance().execute(f);
                    return false;
                } else {
                    stronalewa = 0.0;
                    stronaprawa = 0.0;
                }
            }
                if (p.getTypWiersza() == 0 || p.getTypWiersza() == 5) {
                    stronalewa += p.getStronaWn().getKwota();
                    stronaprawa += p.getStronaMa().getKwota();

                } else if (p.getTypWiersza()== 1 || p.getTypWiersza() == 6) {
                    stronalewa += p.getStronaWn().getKwota();
                } else if (p.getTypWiersza()== 2 || p.getTypWiersza() == 7) {
                    stronaprawa += p.getStronaMa().getKwota();
                }
        }
        //teraz sprawdzamy czy konta lewa prawa
        for (Wiersz p : listawierszy) {
            numerwiersza = p.getIdporzadkowy()-1;
            f = "podswietlznalezionywierszzbrakiem("+numerwiersza+")";
            if (p.getTypWiersza() == 0 || p.getTypWiersza() == 5) {
                if (p.getStronaWn().getKonto() == null || p.getStronaMa().getKonto() == null ) {
                    RequestContext.getCurrentInstance().execute(f);
                    return false;
                }
            } else if (p.getTypWiersza()== 1 || p.getTypWiersza() == 6) {
                if (p.getStronaWn().getKonto() == null) {
                    RequestContext.getCurrentInstance().execute(f);
                    return false;
                }
            } else if (p.getTypWiersza()== 2 || p.getTypWiersza() == 7) {
                if (p.getStronaMa().getKonto() == null ) {
                    RequestContext.getCurrentInstance().execute(f);
                    return false;
                }
            }
        }
        return Z.z(stronalewa) == Z.z(stronaprawa);
    }
    
    public static double[] sumujwierszeBO(Dokfk dokfk) {
        double[] sumy = new double[2];
        for (Wiersz p : dokfk.getListawierszy()) {
            if (p.getStronaWn() != null) {
                sumy[0] += p.getKwotaWnPLN();
            }
            if (p.getStronaMa() != null) {
                sumy[1] += p.getKwotaMaPLN();
            }
        }
        return sumy;
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
        nowywiersz.setDataksiegowania(selected.getDatadokumentu());
        nowywiersz.setTabelanbp(selected.getTabelanbp());
        StronaWiersza stronaWn = new StronaWiersza(nowywiersz, "Wn");
        StronaWiersza stronaMa = new StronaWiersza(nowywiersz, "Ma");
        nowywiersz.setStronaWn(stronaWn);
        nowywiersz.setStronaMa(stronaMa);
    }
     public static Wiersz utworzNowyWierszWNT (Dokfk selected, int liczbawierszyWDokumencie, double kwota, int lpmacierzystego)  {
        Wiersz nowywiersz = new Wiersz(liczbawierszyWDokumencie, 0);
        nowywiersz.setDokfk(selected);
        nowywiersz.setTabelanbp(selected.getTabelanbp());
        StronaWiersza stronaMa = new StronaWiersza(nowywiersz, "Ma", kwota);
        nowywiersz.setStronaMa(stronaMa);
        StronaWiersza stronaWn = new StronaWiersza(nowywiersz, "Wn", kwota);
        nowywiersz.setStronaWn(stronaWn);
        nowywiersz.setLpmacierzystego(lpmacierzystego);
        return nowywiersz;
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
    
    public static double obliczkwotepozostala(Dokfk selected, Wiersz wierszbiezacy, int nrgrupyaktualny) {
        Wiersz wierszpodstawowy = selected.getListawierszy().get(nrgrupyaktualny-1);
        double sumaWn = wierszpodstawowy.getStronaWn().getKwota();
        double sumaMa = wierszpodstawowy.getStronaMa().getKwota();
        int typwiersza = 0;
        Wiersz wiersznastepny = null;
        do {
            wiersznastepny = selected.nastepnyWiersz(wierszpodstawowy);
            if (wiersznastepny != null) {
                if (wiersznastepny.getTypWiersza() == 1) {
                    wierszpodstawowy = wiersznastepny;
                    sumaWn += wiersznastepny.getStronaWn().getKwota();
                    typwiersza = 1;
                } else if (wiersznastepny.getTypWiersza() == 2) {
                    wierszpodstawowy = wiersznastepny;
                    sumaMa += wiersznastepny.getStronaMa().getKwota();
                    typwiersza = 2;
                }
            }
            if (wiersznastepny == null || wiersznastepny.getTypWiersza() == 0) {
                break;
            }
        } while(true);
        double roznica = typwiersza== 2 ? Z.z(sumaWn) - Z.z(sumaMa) : Z.z(sumaMa) - Z.z(sumaWn);
        roznica = typwiersza==0 ? Math.abs(roznica) : roznica;
        return roznica;
    }
        
    
    public static Wiersz WierszFaktory(Dokfk selected, int typwiersza, double roznica, int lpmacierzystego) {
        int liczbawierszyWDokumencie = 0;
        try {
            liczbawierszyWDokumencie = selected.getListawierszy().size()+1;
        } catch (Exception e) {
            E.e(e);
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
        List<Wiersz> przenumerowanaLista = Collections.synchronizedList(new ArrayList<>());
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
                //oprocz przenumerowania lp nalezy przenumerowac lp macierzystego
                if (selected.getListawierszy().get(i-1).getLpmacierzystego()!= 0) {
                    selected.getListawierszy().get(i-1).setLpmacierzystego(selected.getListawierszy().get(i-1).getLpmacierzystego()+1);
                }
                przenumerowanaLista.add(selected.getListawierszy().get(i-1));
            }
        }
        selected.setListawierszy(przenumerowanaLista);
    }
    
    public static void dodajiPrzenumerujWiersze5 (Dokfk selected, Wiersz wiersz, int lpmacierzystego) {
        List<Wiersz> przenumerowanaLista = Collections.synchronizedList(new ArrayList<>());
        int nowaliczbawieszy = selected.getListawierszy().size()+1;
        int lpNowegoWiersza = lpmacierzystego;
        List<Wiersz> listawierszy = selected.getListawierszy();
        for (Wiersz s : listawierszy) {
            if (s.getIdporzadkowy() > lpmacierzystego) {
                if (s.getTypWiersza() == 0 && (lpNowegoWiersza == lpmacierzystego)) {
                    lpNowegoWiersza = lpmacierzystego+1;
                    break;
                } else if (s.getTypWiersza() == 5) {
                    lpNowegoWiersza = s.getIdporzadkowy()+1;
                } else if (s.getTypWiersza() == 7) {
                    lpNowegoWiersza += s.getIdporzadkowy()+1;
                } else if (s.getTypWiersza() == 0) {
                    break;
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

       
    public static void przenumerujSelected(Dokfk selected) {
        int lp = 1;
        int tymczasowyMacierzysty = 0;
        for (Wiersz p : selected.getListawierszy()) {
            if (p.getTypWiersza() == 0) {
                tymczasowyMacierzysty = lp;
                p.setLpmacierzystego(0);
                p.setIdporzadkowy(lp);
            } else {
                p.setIdporzadkowy(lp);
                p.setLpmacierzystego(tymczasowyMacierzysty);
            }
            lp++;
        }
    }

  
    public static void wygenerujiDodajWierszUsun(Dokfk selected, int wierszbiezacyIndex, boolean przenumeruj, double roznica, int typwiersza) {
        int lpmacierzystego = znajdzmacierzystyUsun(selected.getListawierszy(), wierszbiezacyIndex);
        Wiersz wiersz = WierszFaktory(selected, typwiersza, roznica, lpmacierzystego);
        if (przenumeruj == false) {
            selected.getListawierszy().add(wiersz);
        } else {
            ObslugaWiersza.dodajiPrzenumerujWiersze(selected, wiersz, lpmacierzystego);
        }
    }
    
     public static void generujNowyWiersz0NaKoncu(Dokfk selected, Wiersz wiersz, boolean przenumeruj, double roznica, int typwiersza) {
        int lpmacierzystego = wiersz.getLpmacierzystego() > 0 ? wiersz.getLpmacierzystego() : wiersz.getIdporzadkowy();
        Wiersz wierszNowy = WierszFaktory(selected, typwiersza, roznica, lpmacierzystego);
        if (przenumeruj == false) {
            if (selected.getRodzajedok().getKontorozrachunkowe() != null) {
                if (wierszNowy.getStronaWn() != null) {
                    wierszNowy.getStronaWn().setKonto(selected.getRodzajedok().getKontorozrachunkowe());
                }
                if (wierszNowy.getStronaMa() != null) {
                    wierszNowy.getStronaMa().setKonto(selected.getRodzajedok().getKontorozrachunkowe());
                }
            }
            selected.getListawierszy().add(wierszNowy);
        } else {
            ObslugaWiersza.dodajiPrzenumerujWiersze(selected, wierszNowy, lpmacierzystego);
        }
    }
     //dla niektorych fukncji generujacych piatki
     public static void wygenerujiDodajWiersz(Dokfk selected, int wierszbiezacyIndex, boolean przenumeruj, double roznica, int typwiersza) {
        int lpmacierzystego = znajdzmacierzysty(selected.getListawierszy(), wierszbiezacyIndex);
        Wiersz wiersz = WierszFaktory(selected, typwiersza, roznica, lpmacierzystego);
        if (przenumeruj == false) {
            selected.getListawierszy().add(wiersz);
        } else {
            ObslugaWiersza.dodajiPrzenumerujWiersze(selected, wiersz, lpmacierzystego);
        }
    }
     
    public static Wiersz wygenerujiDodajWierszRK(Dokfk selected, int wierszbiezacyIndex, boolean przenumeruj, double roznica, int typwiersza) {
        int lpmacierzystego = znajdzmacierzysty(selected.getListawierszy(), wierszbiezacyIndex);
        Wiersz wiersz = WierszFaktory(selected, typwiersza, roznica, lpmacierzystego);
        if (wierszbiezacyIndex == selected.getListawierszy().size()-1) {
            selected.getListawierszy().add(wiersz);
        } else {
            ObslugaWiersza.dodajiPrzenumerujWiersze(selected, wiersz, lpmacierzystego);
        }
        return wiersz;
    }
     
     public static void wygenerujiDodajWierszPiatka(Dokfk selected, int wierszbiezacyIndex, boolean przenumeruj, double roznica, int typwiersza, Wiersz czworka, Konto konto490) {
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
            ObslugaWiersza.dodajiPrzenumerujWiersze5(selected, nowywiersz, sprawdzonaczworka.getIdporzadkowy());
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
        int wierszbiezacyIndex = wierszbiezacyLP;
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

    public static void sprawdzKwotePozostala(Dokfk selected, Wiersz wybranyWiersz, String wierszeSasiednie, int nrgrupywierszy) {
        double roznica = obliczkwotepozostala(selected, wybranyWiersz, nrgrupywierszy);
        if (roznica > 0.0 ) {
            if (wybranyWiersz.getTypWiersza() == 1) {
                wygenerujiDodajWierszUsun(selected, wybranyWiersz.getIdporzadkowy(), true, roznica, 1);
            } else if (wybranyWiersz.getTypWiersza() == 2) {
                wygenerujiDodajWierszUsun(selected, wybranyWiersz.getIdporzadkowy(), true, roznica, 2);
            }
        } if (roznica < 0.0) {
            if (wybranyWiersz.getTypWiersza() == 1) {
                wygenerujiDodajWierszUsun(selected, wybranyWiersz.getIdporzadkowy(), true, roznica, 2);
            } else if (wybranyWiersz.getTypWiersza() == 2) {
                wygenerujiDodajWierszUsun(selected, wybranyWiersz.getIdporzadkowy(), true, roznica, 1);
            }
        }
    }

    public static void usunpuste(Wiersz wiersz, List<Wiersz> listawierszy) {
        int lpmacierzystego = wiersz.getIdporzadkowy();
        for (Iterator<Wiersz> lWiersz = listawierszy.iterator(); lWiersz.hasNext();) {
            Wiersz p = lWiersz.next();
            Konto kWn = p.getStronaWn() != null? p.getStronaWn().getKonto() : null;
            Konto kMa = p.getStronaMa() != null? p.getStronaMa().getKonto() : null;
            boolean pustyopiswiersza = p.getOpisWiersza().equals("");
            if (kWn == null && kMa == null && pustyopiswiersza) {
                lWiersz.remove();
            }
        }
    }

   public static void wygenerujWierszRoznicowy(Wiersz wierszbiezacy, boolean przenumeruj, int nrgrupy, Dokfk selected) {
        Konto kontoWn;
        Konto kontoMa;
        double kwotaWn = 0.0;
        double kwotaMa = 0.0;
        try {
            if (wierszbiezacy.getTypWiersza() == 0) {
//                kontoWn = wierszbiezacy.getStronaWn().getKonto();
//                kontoMa = wierszbiezacy.getStronaMa().getKonto();
//                if (kontoWn instanceof Konto && kontoMa instanceof Konto) {
                    kwotaWn = wierszbiezacy.getStronaWn().getKwota();
                    kwotaMa = wierszbiezacy.getStronaMa().getKwota();
                    double roznica = ObslugaWiersza.obliczkwotepozostala(selected, wierszbiezacy, nrgrupy);
                    try {
                        Wiersz wiersznastepny = selected.nastepnyWiersz(wierszbiezacy);
                        if (wiersznastepny==null) {
                            if (roznica == 0) {
                            //ObslugaWiersza.generujNowyWiersz0NaKoncu(selected, liczbawierszyWDokumencie, wierszbiezacyIndex, przenumeruj, roznica, 0);
                            } else if (kwotaWn > kwotaMa) {
                                ObslugaWiersza.generujNowyWiersz0NaKoncu(selected, wierszbiezacy, przenumeruj, Math.abs(roznica), 2);
                            } else if (kwotaMa > kwotaWn) {
                                ObslugaWiersza.generujNowyWiersz0NaKoncu(selected, wierszbiezacy, przenumeruj, Math.abs(roznica), 1);
                            }
                        } else {
                            int typnastepnego = wiersznastepny.getTypWiersza();
                            if (roznica != 0) {//bo tyko wtedy ma sens dodawanie czegos, inaczej znaczy to ze cala kwto a jets wyczerpana i nie trzeba dodawac
                                if (typnastepnego == 0) {
                                    if (kwotaWn > kwotaMa) {
                                        ObslugaWiersza.generujNowyWiersz0NaKoncu(selected, wierszbiezacy, przenumeruj, roznica, 2);
                                    } else if (kwotaWn < kwotaMa) {
                                        ObslugaWiersza.generujNowyWiersz0NaKoncu(selected, wierszbiezacy, przenumeruj, roznica, 1);
                                    } else {
                                        //wywalam dodawanie nowego wiersza jak sa nastepne
                                        //ObslugaWiersza.generujNowyWiersz0NaKoncu(selected, liczbawierszyWDokumencie, wierszbiezacy, przenumeruj, roznica, 0);
                                    }
                                } else if (typnastepnego == 2) {
                                        ObslugaWiersza.generujNowyWiersz0NaKoncu(selected, wierszbiezacy, przenumeruj, roznica, 2);
                                } else if (typnastepnego == 1) {
                                        ObslugaWiersza.generujNowyWiersz0NaKoncu(selected, wierszbiezacy, przenumeruj, roznica, 1);
                                } else if (typnastepnego == 5) {
                                    int nowyindexzpiatkami = wierszbiezacy.getIdwiersza() + wierszbiezacy.getPiatki().size();
                                    if (kwotaWn > kwotaMa) {
                                        ObslugaWiersza.wygenerujiDodajWiersz(selected, nowyindexzpiatkami, przenumeruj, roznica, 2);
                                    } else if (kwotaWn < kwotaMa) {
                                        ObslugaWiersza.wygenerujiDodajWiersz(selected, nowyindexzpiatkami, przenumeruj, roznica, 1);
                                    }
                                }
                            }
                        }
                    } catch (Exception e1) {
                        E.e(e1);
                        //jezeli nie ma nastepnych to tak robimy, a jak jest inaczej to to co na gorze
//                    }
                }
            } else if (wierszbiezacy.getTypWiersza() == 2) {
                kontoMa = wierszbiezacy.getStronaMa().getKonto();
                if (kontoMa instanceof Konto) {
                    double roznica = ObslugaWiersza.obliczkwotepozostala(selected, wierszbiezacy, nrgrupy);
                    if (roznica != 0.0) {
                        ObslugaWiersza.generujNowyWiersz0NaKoncu(selected, wierszbiezacy, przenumeruj, roznica, 2);
                    }
                }
            } else if (wierszbiezacy.getTypWiersza() == 1) {
                kontoWn = wierszbiezacy.getStronaWn().getKonto();
                if (kontoWn instanceof Konto) {
                    double roznica = ObslugaWiersza.obliczkwotepozostala(selected, wierszbiezacy, nrgrupy);
                        if (roznica != 0.0) {
                            ObslugaWiersza.generujNowyWiersz0NaKoncu(selected, wierszbiezacy, przenumeruj, roznica, 1);
                        }
                }
            }
        } catch (Exception e) {  
            E.e(e);
            Msg.msg("w", "Uzupełnij dane przed dodaniem nowego wiersza");
        }
    }

      //    //dodaje wiersze do dokumentu
    public static void dolaczNowyWierszPiatka(int wierszbiezacyIndex, boolean przenumeruj, Dokfk selected, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        Wiersz wierszbiezacy = selected.getListawierszy().get(wierszbiezacyIndex);
        Konto kontoWn;
        Konto kontoMa;
        boolean czyWszystkoWprowadzono = false;
        double kwotaWn = 0.0;
        double kwotaMa = 0.0;
        try {
            if (wierszbiezacy.getTypWiersza() == 0 || wierszbiezacy.getTypWiersza() == 1) {
                kontoWn = wierszbiezacy.getStronaWn().getKonto();
                if (kontoWn instanceof Konto) {
                    czyWszystkoWprowadzono = true;
                    kwotaWn = wierszbiezacy.getStronaWn().getKwota();
                    double roznica = kwotaWn;
                    try {
                        Wiersz wiersznastepny = selected.getListawierszy().get(wierszbiezacyIndex + 1);
                        Konto konto490 = kontoDAOfk.findKontoPodatnik490(wpisView);
                        ObslugaWiersza.wygenerujiDodajWierszPiatka(selected, wierszbiezacyIndex, true, roznica, 5, wierszbiezacy, konto490);
                    } catch (Exception e) {  E.e(e);
                        Konto konto490 = kontoDAOfk.findKontoPodatnik490(wpisView);
                        ObslugaWiersza.wygenerujiDodajWierszPiatka(selected, wierszbiezacyIndex, false, roznica, 5, wierszbiezacy, konto490);
                    }
                }
            } else if (wierszbiezacy.getTypWiersza() == 5) {
                kontoWn = wierszbiezacy.getStronaWn().getKonto();
                kontoMa = wierszbiezacy.getStronaMa().getKonto();
                if (kontoWn instanceof Konto && kontoMa instanceof Konto) {
                    czyWszystkoWprowadzono = true;
                    kwotaWn = wierszbiezacy.getStronaWn().getKwota();
                    kwotaMa = wierszbiezacy.getStronaMa().getKwota();
                    double roznica = ObslugaWiersza.obliczkwotepozostala5(selected, wierszbiezacy);
                    //jezeli nie ma nastepnych to tak robimy, a jak jest inaczej to to co na gorze
                    if (roznica == 0) {
                        // nie chce wiersza na koncu ni z tego ni z owego
                        try {
                            Wiersz wiersznastepny = selected.getListawierszy().get(wierszbiezacyIndex + 1);
                        } catch (Exception e) {  E.e(e);
                            ObslugaWiersza.generujNowyWiersz0NaKoncu(selected, wierszbiezacy, false, roznica, 0);
                        }
                    } else if (kwotaWn > kwotaMa) {
                        Konto konto490 = kontoDAOfk.findKontoPodatnik490(wpisView);
                        ObslugaWiersza.wygenerujiDodajWierszPiatka(selected, wierszbiezacyIndex, przenumeruj, roznica, 7, wierszbiezacy, konto490);
                    } else if (kwotaMa > kwotaWn) {
                        Konto konto490 = kontoDAOfk.findKontoPodatnik490(wpisView);
                        ObslugaWiersza.wygenerujiDodajWierszPiatka(selected, wierszbiezacyIndex, przenumeruj, roznica, 6, wierszbiezacy, konto490);
                    }
                }
            } else if (wierszbiezacy.getTypWiersza() == 7) {
                kontoMa = wierszbiezacy.getStronaMa().getKonto();
                if (kontoMa instanceof Konto) {
                    double roznica = ObslugaWiersza.obliczkwotepozostala5(selected, wierszbiezacy);
                    czyWszystkoWprowadzono = true;
                    if (roznica > 0.0) {
                        Konto konto490 = kontoDAOfk.findKontoPodatnik490(wpisView);
                        ObslugaWiersza.wygenerujiDodajWierszPiatka(selected, wierszbiezacyIndex, przenumeruj, roznica, 7, wierszbiezacy, konto490);
                    } else {
                        try {
                            Wiersz wiersznastepny = selected.getListawierszy().get(wierszbiezacyIndex + 1);
                        } catch (Exception e) {  E.e(e);
                            ObslugaWiersza.generujNowyWiersz0NaKoncu(selected, wierszbiezacy, false, roznica, 0);
                        }
                    }
                }
            } else if (wierszbiezacy.getTypWiersza() == 6) {
                kontoWn = wierszbiezacy.getStronaWn().getKonto();
                if (kontoWn instanceof Konto) {
                    double roznica = ObslugaWiersza.obliczkwotepozostala5(selected, wierszbiezacy);
                    czyWszystkoWprowadzono = true;
                    if (roznica > 0.0) {
                        Konto konto490 = kontoDAOfk.findKontoPodatnik490(wpisView);
                        ObslugaWiersza.wygenerujiDodajWierszPiatka(selected, wierszbiezacyIndex, przenumeruj, roznica, 6, wierszbiezacy, konto490);
                    } else {
                        try {
                            Wiersz wiersznastepny = selected.getListawierszy().get(wierszbiezacyIndex + 1);
                        } catch (Exception e) {  E.e(e);
                            ObslugaWiersza.generujNowyWiersz0NaKoncu(selected, wierszbiezacy, false, roznica, 0);
                        }
                    }
                }
            }
        } catch (Exception e) {  E.e(e);
            Msg.msg("w", "Uzupełnij dane przed dodaniem nowego wiersza");
        }
//        if (czyWszystkoWprowadzono) {
//            //dzieki temu w wierszu sa dane niezbedne do identyfikacji rozrachunkow
//            selected.przeliczKwotyWierszaDoSumyDokumentu();
//        } else {
//            Msg.msg("e", "Brak wpisanego konta/kont. Nie można dodać nowego wiersza");
//        }
    }
   
}
