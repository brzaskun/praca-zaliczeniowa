/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import dao.StronaWierszaDAO;
import entityfk.StronaWiersza;
import entityfk.Transakcja;
import entityfk.Wiersz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    public static List<StronaWiersza> pobierzStronaWierszazBazy(StronaWiersza stronaWiersza, String wnma, StronaWierszaDAO stronaWierszaDAO) {
        List<StronaWiersza> listaNowychRozrachunkow = new ArrayList<>();
        listaNowychRozrachunkow = stronaWierszaDAO.findStronaByKontoWnMaWaluta(stronaWiersza.getKonto(), stronaWiersza.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty(), stronaWiersza.getWnma());
        if (listaNowychRozrachunkow == null) {
            return (new ArrayList<>());
        }
        return listaNowychRozrachunkow;
        //pobrano wiersze - a teraz z nich robie rozrachunki
    }
    
    public static List<StronaWiersza> pobierzNoweStronaWierszazDokumentu(String nrkonta, String wnma, String waluta,List<Wiersz> wiersze) {
        List<StronaWiersza> listaNowychRozrachunkowDokument = new ArrayList<>();
        for (Wiersz p : wiersze) {
            if (wnma.equals("Wn")) {
                if (p.getIdwiersza() == null && p.getStronaMa().getKonto() != null) {
                    listaNowychRozrachunkowDokument.add(p.getStronaMa());
                }
            } else if (wnma.equals("Ma")){
                if (p.getIdwiersza() == null) {
                    listaNowychRozrachunkowDokument.add(p.getStronaWn());
                }
            }
        }
        if (!listaNowychRozrachunkowDokument.isEmpty()) {
            Iterator it = listaNowychRozrachunkowDokument.iterator();
            while (it.hasNext()) {
                StronaWiersza r = (StronaWiersza) it.next();
                if (r.getId()!= null) {
                    it.remove();
                } 
                try {
                    if (!r.getKonto().getPelnynumer().equals(nrkonta) || r.getTypStronaWiersza() != 1) {
                        it.remove();
                    }
                } catch (Exception ff) {}
            }
        }
        return listaNowychRozrachunkowDokument;
        //pobrano wiersze - a teraz z nich robie rozrachunki
    }
    
    public static List<StronaWiersza> pobierzZapisaneWBazieStronaWierszazDokumentu(String nrkonta, String wnma, String waluta,List<Wiersz> wiersze) {
        List<StronaWiersza> listaNowychRozrachunkowDokument = new ArrayList<>();
        for (Wiersz p : wiersze) {
            if (wnma.equals("Wn")) {
                if (p.getIdwiersza() != null && p.getStronaMa().getKonto() != null) {
                    listaNowychRozrachunkowDokument.add(p.getStronaMa());
                }
            } else if (wnma.equals("Ma")){
                if (p.getIdwiersza() != null) {
                    listaNowychRozrachunkowDokument.add(p.getStronaWn());
                }
            }
        }
        if (!listaNowychRozrachunkowDokument.isEmpty()) {
            Iterator it = listaNowychRozrachunkowDokument.iterator();
            while (it.hasNext()) {
                StronaWiersza r = (StronaWiersza) it.next();
                if (r.getId()== null) {
                    it.remove();
                } 
                try {
                    if (!r.getKonto().getPelnynumer().equals(nrkonta) || r.getTypStronaWiersza() != 1) {
                        it.remove();
                    }
                } catch (Exception ff) {
                }
            }
        }
        return listaNowychRozrachunkowDokument;
        //pobrano wiersze - a teraz z nich robie rozrachunki
    }

    public static List<Transakcja> stworznowetransakcjezeSwiezychstronwierszy(List<StronaWiersza> listaNowychRozrachunkow, StronaWiersza aktualnywierszdorozrachunkow, String podatnik) {
        //z utworzonych rozrachunkow tworzy sie transkakcje laczac rozrachunek rozliczony ze sparowanym
        List<Transakcja> transakcjezeswiezych = new ArrayList<>();
        for (StronaWiersza nowatransakcjazbazy : listaNowychRozrachunkow) {
            Transakcja transakcja = new Transakcja();
            transakcja.setRozliczajacy(aktualnywierszdorozrachunkow);
            transakcja.setNowaTransakcja(nowatransakcjazbazy);
            aktualnywierszdorozrachunkow.dodajTransakcjeNowe(transakcja);
            aktualnywierszdorozrachunkow.setTypStronaWiersza(2);
            nowatransakcjazbazy.dodajPlatnosci(transakcja);
            transakcjezeswiezych.add(transakcja);
        }
        return transakcjezeswiezych;
    }
    
    public static List<Transakcja> stworznowetransakcjezeZapisanychStronWierszy(List<StronaWiersza> listaStronaWierszaDokument, List<StronaWiersza> listaStronaWierszaBazaDanych, StronaWiersza aktualnywierszdorozrachunkow, String podatnik) {
        //sprawdzam, czy wiersze z bazy nie sa d okumnecie, a poniewaz te w dokumencie sa bardziej aktualne to usuwamy duplikaty z bazy
        for (StronaWiersza p : listaStronaWierszaDokument) {
            if (listaStronaWierszaBazaDanych.contains(p)) {
                listaStronaWierszaBazaDanych.remove(p);
            }
        }
        List<StronaWiersza> listaZbiorcza = new ArrayList<>();
        //laczymy te stare z dokumentu i te z bazy po edycji
        listaZbiorcza.addAll(listaStronaWierszaDokument);
        listaZbiorcza.addAll(listaStronaWierszaBazaDanych);
        List<Transakcja> transakcjeZAktualnego = new ArrayList<>();
        //aktualizujemy transakcje o dane z biezacych stronwiersza (to gdyby w wiersu byla inna sytuacja niz w zachowanej transakcji
        transakcjeZAktualnego.addAll((aktualnywierszdorozrachunkow).getNowetransakcje());
        for (Transakcja t : transakcjeZAktualnego) {
            if (listaZbiorcza.contains(t.getNowaTransakcja())) {
                //t.setNowaTransakcja((listaZbiorcza.get(listaZbiorcza.indexOf(t.getNowaTransakcja()))));
                listaZbiorcza.remove(t.getNowaTransakcja());
            }
        }
        //z utworzonych rozrachunkow tworzy sie transkakcje laczac rozrachunek rozliczony ze sparowanym
        // nie bedzie duplikatow bo wczesniej je usunelismmy po zaktualizowaniu wartosci w zalaczonych juz transakcjach
        List<Transakcja> transakcjezbazyiwiersza = new ArrayList<>();
        for (StronaWiersza nowatransakcjazbazy : listaZbiorcza) {
            Transakcja transakcja = new Transakcja();
                transakcja.setRozliczajacy(aktualnywierszdorozrachunkow);
                transakcja.setNowaTransakcja(nowatransakcjazbazy);
                aktualnywierszdorozrachunkow.dodajTransakcjeNowe(transakcja);
                aktualnywierszdorozrachunkow.setTypStronaWiersza(2);
                nowatransakcjazbazy.dodajPlatnosci(transakcja);
                transakcjezbazyiwiersza.add(transakcja);
        }
        transakcjezbazyiwiersza.addAll(transakcjeZAktualnego);
        return transakcjezbazyiwiersza;
    }
    
//    public static List<Transakcja> stworznowetransakcjezPobranychstronwierszy(List<StronaWiersza> listaStronaWierszaZBazy, StronaWiersza aktualnywierszdorozrachunkow, String podatnik, List<Transakcja> biezacetransakcje) {
//        //z utworzonych rozrachunkow tworzy sie transkakcje laczac rozrachunek rozliczony ze sparowanym
//        Iterator it = biezacetransakcje.iterator();
//        while (it.hasNext()) {
//            Transakcja p = (Transakcja) it.next();
//            if (listaStronaWierszaZBazy.contains(p.getNowaTransakcja())) {
//                listaStronaWierszaZBazy.remove(p.getNowaTransakcja());
//            }
//        }
//        List<Transakcja> transakcjeswiezynki = new ArrayList<>();
//        for (StronaWiersza nowatransakcjazbazy : listaStronaWierszaZBazy) {
//            Transakcja transakcja = new Transakcja();
//                transakcja.setRozliczajacy(aktualnywierszdorozrachunkow);
//                transakcja.setNowaTransakcja(nowatransakcjazbazy);
//                aktualnywierszdorozrachunkow.dodajTransakcjeNowe(transakcja);
//                aktualnywierszdorozrachunkow.setTypStronaWiersza(2);
//                nowatransakcjazbazy.dodajPlatnosci(transakcja);
//                transakcjeswiezynki.add(transakcja);
//        }
//        return transakcjeswiezynki;
//    }

    
//    public static List<Transakcja> pobierzjuzNaniesioneTransakcjeRozliczony(StronaWiersza aktualnywierszdorozrachunkow, String wnma) {
//        List<Transakcja> pobranalista = new ArrayList<>();
//        try {
//            pobranalista.addAll((aktualnywierszdorozrachunkow).getNowetransakcje());
//        } catch (Exception e) {
//            
//        }
//        return pobranalista;
//    }
     public static List<Transakcja> pobierzbiezaceTransakcjeDlaNowejTransakcji(StronaWiersza stronawiersza, String wnma) {
        List<Transakcja> pobrana = new ArrayList<>();
        try {
            pobrana.addAll((stronawiersza).getPlatnosci());
            return pobrana;
        } catch (Exception e) {
            return pobrana;
        }
    }

    public static List<Transakcja> naniesInformacjezWczesniejRozliczonych(int pierwotnailosctransakcjiwbazie, List<Transakcja> biezacetransakcje,StronaWiersza aktualnywierszdorozrachunkow, StronaWierszaDAO stronaWierszaDAO) {
        pierwotnailosctransakcjiwbazie = 0;
        if (biezacetransakcje.size() > 0) {
            //sprawdz czy nowoutworzona transakcja nie znajduje sie juz w biezacetransakcje
            //jak jest to uzupelniamy jedynie rozliczenie biezace i archiwalne
            double sumaStornoRozliczajacego = 0.0;
            for (Transakcja s : biezacetransakcje) {
                sumaStornoRozliczajacego += s.getKwotatransakcji();
                pierwotnailosctransakcjiwbazie++;
            }
            //bylo zbedne ale jest z powrotem niezbedne. korygujemy kowte rozliczona o kwoty z biezacych pobranych do wyswietlenia transakcji, 
            //zeby nam pola ugory dialogu rozrachunkow wkazywaly ile jest do rozliczenia w biezacym kliknieciu
//            double pozostalo = aktualnywierszdorozrachunkow.getKwota();
//            double rozliczono = sumaStornoRozliczajacego;
//            pozostalo = pozostalo - sumaStornoRozliczajacego;
//            aktualnywierszdorozrachunkow.setRozliczono(rozliczono);
//            aktualnywierszdorozrachunkow.setPozostalo(pozostalo);
        }
        return biezacetransakcje;
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
