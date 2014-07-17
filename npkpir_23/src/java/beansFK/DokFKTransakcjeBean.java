/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import dao.StronaMaDAO;
import dao.StronaWnDAO;
import daoFK.TransakcjaDAO;
import daoFK.ZestawienielisttransakcjiDAO;
import entityfk.Konto;
import entityfk.StronaMa;
import entityfk.StronaWiersza;
import entityfk.StronaWn;
import entityfk.Transakcja;
import entityfk.Wiersz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.Singleton;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class DokFKTransakcjeBean implements Serializable{
    
    //archeo - zbedne wobec innego zachowywania rozrachunkow
//    public static void sumujdlaNowejTransakcji(List<Transakcja> transakcjejakosparowany,List<Transakcja> biezacetransakcje) {
//        double sumaddlaaktualnego = 0.0;
//        //czyscimy wartosci
//        for (Transakcja p : transakcjejakosparowany) {
//            double kwota = p.getKwotatransakcji();
//            if (kwota > 0) {
//                Transakcja nowa = new Transakcja();
//                nowa.setZablokujnanoszenie(true);
//                nowa.setRozliczany(p.getSparowany());
//                nowa.setSparowany(p.getRozliczany());
//                nowa.SetSpRozl(kwota);
//                nowa.SetSpPoz(nowa.GetSpKwotaPier() - kwota);
//                biezacetransakcje.add(nowa);
//            }
//        }
//    }
//
//    //************************* jeli pobierztransakcjeJakoSparowany() == 1 to robimy jakby byl nowa transakcja
//    public static int pobierztransakcjeJakoSparowany(List<Transakcja> transakcjejakosparowany, List<Transakcja> biezacetransakcje, ZestawienielisttransakcjiDAO zestawienielisttransakcjiDAO, StronaWiersza aktualnywierszdorozrachunkow) {
//        //teraz z zapamietanych czyscimy klucz i liste pobrana wyzej
//        //pobieramy listy z bazy
//        transakcjejakosparowany = new ArrayList<>();
//        List<Zestawienielisttransakcji> pobranelisty = new ArrayList<>();
//        List<Transakcja> kolekcje = new ArrayList<>();
//        pobranelisty = zestawienielisttransakcjiDAO.findAll();
//        for (Zestawienielisttransakcji p : pobranelisty) {
//            //kolekcje.addAll(p.getListatransakcji());
//        }
//        int wynikszukania = 0;
//        for (Transakcja x : kolekcje) {
//            WierszStronafkPK idAktualny = aktualnywierszdorozrachunkow.getWierszStronafk().getWierszStronafkPK();
//            boolean typdokumentu = x.idSparowany().getTypdokumentu().equals(idAktualny.getTypdokumentu());
//            boolean nrkolejnydokumentu = x.idSparowany().getNrkolejnydokumentu() == idAktualny.getNrkolejnydokumentu();
//            boolean nrPorzadkowyWiersza = x.idSparowany().getNrPorzadkowyWiersza() == idAktualny.getNrPorzadkowyWiersza();
//            boolean kwoty = x.GetSpRozl() != 0.0 && x.GetSpPoz() != 0.0;
//            if (typdokumentu && nrkolejnydokumentu && nrPorzadkowyWiersza && kwoty) {
//                Msg.msg("w", "on byl jako sparowany");
//                transakcjejakosparowany.add(x);
//                wynikszukania = 1;
//            }
//        }
//        //nie znaleziono transakcji gdzie aktualnybylby sparowanym
//        if (wynikszukania == 1) {
//            return 1;
//        } else {
//            return 0;
//        }
//    }
     
     //************************* jeli pobierztransakcjeJakoSparowany() == 0 to robimy jakby nie byl nowa transakcja
    public static List<StronaWiersza> pobierzStronaWierszazBazy(StronaWiersza stronaWiersza, String wnma, StronaWnDAO stronaWnDAO, StronaMaDAO stronaMaDAO) {
        List<StronaWiersza> listaNowychRozrachunkow;
        if (stronaWiersza instanceof StronaWn) {
            StronaWn stronaWn = (StronaWn) stronaWiersza;
            listaNowychRozrachunkow = stronaMaDAO.findStronaMaByKontoWnMaWaluta(stronaWn.getKonto(), stronaWn.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty());
        } else {
            StronaMa stronaMa = (StronaMa) stronaWiersza;
            listaNowychRozrachunkow = stronaWnDAO.findStronaWnByKontoWnMaWaluta(stronaMa.getKonto(), stronaMa.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty());
        }
        if (listaNowychRozrachunkow == null) {
            return (new ArrayList<>());
        } 
        return listaNowychRozrachunkow;
        //pobrano wiersze - a teraz z nich robie rozrachunki
    }
    
    public static List<StronaWiersza> pobierzStronaWierszazDokumentu(String nrkonta, String wnma, String waluta,List<Wiersz> wiersze) {
        List<StronaWiersza> listaNowychRozrachunkowDokument = new ArrayList<>();
        for (Wiersz p : wiersze) {
            if (wnma.equals("Wn")) {
                StronaWiersza rozrachunekMa = p.getStronaWn();
                if (rozrachunekMa != null) {
                    listaNowychRozrachunkowDokument.add(rozrachunekMa);
                }
            } else if (wnma.equals("Ma")){
                StronaWiersza rozrachunekWn = p.getStronaMa();
                if (rozrachunekWn != null) {
                    listaNowychRozrachunkowDokument.add(rozrachunekWn);
                }
            }
        }
        if (!listaNowychRozrachunkowDokument.isEmpty()) {
            Iterator it = listaNowychRozrachunkowDokument.iterator();
            while (it.hasNext()) {
                StronaWiersza r = (StronaWiersza) it.next();
                if (r.getId()!= null) {
                    it.remove();
                } else if (!r.getKonto().getPelnynumer().equals(nrkonta) || r.isNowatransakcja() == false) {
                    it.remove();
                }
            }
        }
        return listaNowychRozrachunkowDokument;
        //pobrano wiersze - a teraz z nich robie rozrachunki
    }

    public static List<Transakcja> stworznowetransakcjezPobranychstronwierszy(List<StronaWiersza> listaNowychRozrachunkow, StronaWiersza aktualnywierszdorozrachunkow, String podatnik) {
        //z utworzonych rozrachunkow tworzy sie transkakcje laczac rozrachunek rozliczony ze sparowanym
        List<Transakcja> transakcjeswiezynki = new ArrayList<>();
        for (StronaWiersza nowatransakcjazbazy : listaNowychRozrachunkow) {
            Transakcja transakcja = new Transakcja();
            if (aktualnywierszdorozrachunkow instanceof StronaWn) {
                transakcja.setStronaWn((StronaWn) aktualnywierszdorozrachunkow);
                transakcja.setStronaMa((StronaMa) nowatransakcjazbazy);
                ((StronaWn) aktualnywierszdorozrachunkow).getTransakcje().add(transakcja);
            } else if (aktualnywierszdorozrachunkow instanceof StronaMa){
                transakcja.setStronaMa((StronaMa) aktualnywierszdorozrachunkow);
                transakcja.setStronaWn((StronaWn) nowatransakcjazbazy);
                ((StronaMa) aktualnywierszdorozrachunkow).getTransakcje().add(transakcja);
                ((StronaWn) nowatransakcjazbazy).getTransakcje().add(transakcja);
            }
            
            transakcjeswiezynki.add(transakcja);
        }
        return transakcjeswiezynki;
    }

    
    public static List<Transakcja> pobierzjuzNaniesioneTransakcjeRozliczony(StronaWiersza aktualnywierszdorozrachunkow, String wnma) {
        List<Transakcja> pobranalista = new ArrayList<>();
        try {
            if (wnma.equals("Wn")) {
                pobranalista.addAll(((StronaWn) aktualnywierszdorozrachunkow).getTransakcje());
            } else {
                pobranalista.addAll(((StronaMa) aktualnywierszdorozrachunkow).getTransakcje());
            } 
        } catch (Exception e) {
            
        }
        return pobranalista;
    }

    public static List<Transakcja> naniesInformacjezWczesniejRozliczonych(int pierwotnailosctransakcjiwbazie,List<Transakcja> zachowanewczejsniejtransakcje, List<Transakcja> transakcjeswiezynki,StronaWiersza aktualnywierszdorozrachunkow ) {
        List<Transakcja> biezacetransakcje = new ArrayList<>();
        pierwotnailosctransakcjiwbazie = 0;
        for (Transakcja r : transakcjeswiezynki) {
               if (!zachowanewczejsniejtransakcje.contains(r)) {
                   biezacetransakcje.add(r);
               }
        }
        if (zachowanewczejsniejtransakcje.size() > 0) {
            //sprawdz czy nowoutworzona transakcja nie znajduje sie juz w biezacetransakcje
            //jak jest to uzupelniamy jedynie rozliczenie biezace i archiwalne
            double sumaStornoRozliczajacego = 0.0;
            for (Transakcja s : zachowanewczejsniejtransakcje) {
                sumaStornoRozliczajacego += s.getKwotatransakcji();
                biezacetransakcje.add(s);
                pierwotnailosctransakcjiwbazie++;
            }
            //bylo zbedne ale jest z powrotem niezbedne. korygujemy kowte rozliczona o kwoty z biezacych pobranych do wyswietlenia transakcji, 
            //zeby nam pola ugory dialogu rozrachunkow wkazywaly ile jest do rozliczenia w biezacym kliknieciu
            double pozostalo = aktualnywierszdorozrachunkow.getKwota();
            double rozliczono = sumaStornoRozliczajacego;
            pozostalo = pozostalo - sumaStornoRozliczajacego;
            aktualnywierszdorozrachunkow.setRozliczono(rozliczono);
            aktualnywierszdorozrachunkow.setPozostalo(pozostalo);
        }
        return biezacetransakcje;
    }
    
    public static List<Transakcja> pobierzbiezaceTransakcjeDlaNowejTransakcji(StronaWiersza stronawiersza, String wnma) {
        List<Transakcja> pobrana = new ArrayList<>();
        try {
            if (stronawiersza.equals("Wn")) {
                ((StronaWn) stronawiersza).getTransakcje();
            } else {
                ((StronaMa) stronawiersza).getTransakcje();
            }
            return pobrana;
        } catch (Exception e) {
            return pobrana;
        }
    }
    
//    public static List<Transakcja> pobierzbiezaceTransakcjePrzegladRozrachunkow(TransakcjaDAO transakcjaDAO, StronaWiersza rozrachunek) {
//        List<Transakcja> pobrana = new ArrayList<>();
//        boolean czyJaJestemNowaTransakcja = rozrachunek.isNowatransakcja();
//        if (czyJaJestemNowaTransakcja) {
//            pobrana.addAll(transakcjaDAO.findBySparowanyID(rozrachunek.getIdrozrachunku()));
//        } else {
//            pobrana.addAll(transakcjaDAO.findByRozliczonyID(rozrachunek.getIdrozrachunku()));
//             for (Transakcja p : pobrana) {
//                StronaWiersza rozliczany = p.getSparowany();
//                StronaWiersza sparowany = p.getRozliczany();
//                p.setRozliczany(rozliczany);
//                p.setSparowany(sparowany);
//                p.setZablokujnanoszenie(Boolean.TRUE);
//            }
//        }
//        return pobrana;
//        
//    }
    
    // to jest archeo w nowej konfiguracji. tutaj aktualizowalismy rozrachunek w liscie transakcji
    // teraz jest to zbedne bo one sa w tabeli rozrachunki
//    public static void pobierzjuzNaniesioneTransakcjeSparowane(List<StronaWiersza> listaNowychRozrachunkow, StronaWierszaDAO rozrachunekfkDAO, List<Transakcja> biezacetransakcje, String podatnik) {
//        listaNowychRozrachunkow = new ArrayList<>();
//        listaNowychRozrachunkow.addAll(rozrachunekfkDAO.findRozrachybekfkByPodatnik(podatnik));
//        for (StronaWiersza p : listaNowychRozrachunkow) {
//            for (Transakcja r : biezacetransakcje) {
//                if (r.idSparowany().equals(p.getWierszStronafk().getWierszStronafkPK())) {
//                    r.setSparowany(p);
//                }
//            }
//        }
//
//    }
    
}
