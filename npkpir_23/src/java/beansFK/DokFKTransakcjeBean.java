/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import daoFK.RozrachunekfkDAO;
import daoFK.ZestawienielisttransakcjiDAO;
import entityfk.Transakcja;
import embeddablefk.WierszStronafkPK;
import entityfk.Rozrachunekfk;
import entityfk.Zestawienielisttransakcji;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class DokFKTransakcjeBean implements Serializable{
    
    public static void sumujdlaNowejTransakcji(List<Transakcja> transakcjejakosparowany,List<Transakcja> biezacetransakcje) {
        double sumaddlaaktualnego = 0.0;
        //czyscimy wartosci
        for (Transakcja p : transakcjejakosparowany) {
            double kwota = p.getKwotatransakcji();
            if (kwota > 0) {
                Transakcja nowa = new Transakcja();
                nowa.setZablokujnanoszenie(true);
                nowa.setRozliczany(p.getSparowany());
                nowa.setSparowany(p.getRozliczany());
                nowa.SetSpRozl(kwota);
                nowa.SetSpPoz(nowa.GetSpKwotaPier() - kwota);
                biezacetransakcje.add(nowa);
            }
        }
    }

    //************************* jeli pobierztransakcjeJakoSparowany() == 1 to robimy jakby byl nowa transakcja
    public static int pobierztransakcjeJakoSparowany(List<Transakcja> transakcjejakosparowany, List<Transakcja> biezacetransakcje, ZestawienielisttransakcjiDAO zestawienielisttransakcjiDAO, Rozrachunekfk aktualnywierszdorozrachunkow) {
        //teraz z zapamietanych czyscimy klucz i liste pobrana wyzej
        //pobieramy listy z bazy
        transakcjejakosparowany = new ArrayList<>();
        List<Zestawienielisttransakcji> pobranelisty = new ArrayList<>();
        List<Transakcja> kolekcje = new ArrayList<>();
        pobranelisty = zestawienielisttransakcjiDAO.findAll();
        for (Zestawienielisttransakcji p : pobranelisty) {
            //kolekcje.addAll(p.getListatransakcji());
        }
        int wynikszukania = 0;
        for (Transakcja x : kolekcje) {
            WierszStronafkPK idAktualny = aktualnywierszdorozrachunkow.getWierszStronafk().getWierszStronafkPK();
            boolean typdokumentu = x.idSparowany().getTypdokumentu().equals(idAktualny.getTypdokumentu());
            boolean nrkolejnydokumentu = x.idSparowany().getNrkolejnydokumentu() == idAktualny.getNrkolejnydokumentu();
            boolean nrPorzadkowyWiersza = x.idSparowany().getNrPorzadkowyWiersza() == idAktualny.getNrPorzadkowyWiersza();
            boolean kwoty = x.GetSpRozl() != 0.0 && x.GetSpPoz() != 0.0;
            if (typdokumentu && nrkolejnydokumentu && nrPorzadkowyWiersza && kwoty) {
                Msg.msg("w", "on byl jako sparowany");
                transakcjejakosparowany.add(x);
                wynikszukania = 1;
            }
        }
        //nie znaleziono transakcji gdzie aktualnybylby sparowanym
        if (wynikszukania == 1) {
            return 1;
        } else {
            return 0;
        }
    }
     
     //************************* jeli pobierztransakcjeJakoSparowany() == 0 to robimy jakby nie byl nowa transakcja
    public static List<Rozrachunekfk> pobierzRozrachunekfkzBazy(String nrkonta, String wnma, String waluta,RozrachunekfkDAO rozrachunekfkDAO) {
        List<Rozrachunekfk> listaNowychRozrachunkow = new ArrayList<>();
        listaNowychRozrachunkow.addAll(rozrachunekfkDAO.findRozrachunkifkByKonto(nrkonta, wnma, waluta));
        assert listaNowychRozrachunkow.size() > 0;
        return listaNowychRozrachunkow;
        //pobrano wiersze - a teraz z nich robie rozrachunki
    }

    public static List<Transakcja> stworznowetransakcjezPobranychstronwierszy(List<Rozrachunekfk> listaNowychRozrachunkow, Rozrachunekfk aktualnywierszdorozrachunkow, String podatnik) {
        //z utworzonych rozrachunkow tworzy sie transkakcje laczac rozrachunek rozliczony ze sparowanym
        List<Transakcja> transakcjeswiezynki = new ArrayList<>();
        for (Rozrachunekfk nowatransakcjazbazy : listaNowychRozrachunkow) {
            Transakcja transakcja = new Transakcja();
            transakcja.setPodatnik(podatnik);
            transakcja.setRozliczany(aktualnywierszdorozrachunkow);
            transakcja.setSparowany(nowatransakcjazbazy);
            transakcjeswiezynki.add(transakcja);
        }
        return transakcjeswiezynki;
    }

    public static void pobierzjuzNaniesioneTransakcjeRozliczony(List<Transakcja> zachowanewczejsniejtransakcje, Rozrachunekfk aktualnywierszdorozrachunkow, ZestawienielisttransakcjiDAO zestawienielisttransakcjiDAO) {
        zachowanewczejsniejtransakcje = new ArrayList<>();
        WierszStronafkPK klucz = aktualnywierszdorozrachunkow.getWierszStronafk().getWierszStronafkPK();
        Zestawienielisttransakcji pobranalista = new Zestawienielisttransakcji();
        pobranalista = zestawienielisttransakcjiDAO.findByKlucz(klucz);
        if (pobranalista instanceof Zestawienielisttransakcji) {
           // zachowanewczejsniejtransakcje.addAll(pobranalista.getListatransakcji());
        }
    }

    public static void naniesInformacjezWczesniejRozliczonych(int pierwotnailosctransakcjiwbazie,List<Transakcja> zachowanewczejsniejtransakcje,List<Transakcja> biezacetransakcje, List<Transakcja> transakcjeswiezynki,Rozrachunekfk aktualnywierszdorozrachunkow ) {
        pierwotnailosctransakcjiwbazie = 0;
        //sprawdz czy nowoutworzona transakcja nie znajduje sie juz w biezacetransakcje
        //jak jest to uzupelniamy jedynie rozliczenie biezace i archiwalne
        double sumaddlaaktualnego = 0.0;
        for (Transakcja s : zachowanewczejsniejtransakcje) {
            sumaddlaaktualnego += s.getKwotatransakcji();
            biezacetransakcje.add(s);
            pierwotnailosctransakcjiwbazie++;
        }
        for (Transakcja r : transakcjeswiezynki) {
            if (!zachowanewczejsniejtransakcje.contains(r)) {
                biezacetransakcje.add(r);
            }
        }
        //aktualizujemy biezacy wiersz nie bedacy nowa transakcja
        double rozliczono = aktualnywierszdorozrachunkow.getRozliczono();
        double pozostalo = aktualnywierszdorozrachunkow.getPozostalo();
        rozliczono = rozliczono + sumaddlaaktualnego;
        pozostalo = pozostalo - sumaddlaaktualnego;
        aktualnywierszdorozrachunkow.setRozliczono(rozliczono);
        aktualnywierszdorozrachunkow.setPozostalo(pozostalo);
    }

    public static void pobierzjuzNaniesioneTransakcjeSparowane(List<Rozrachunekfk> listaNowychRozrachunkow, RozrachunekfkDAO rozrachunekfkDAO, List<Transakcja> biezacetransakcje, String podatnik) {
        listaNowychRozrachunkow = new ArrayList<>();
        listaNowychRozrachunkow.addAll(rozrachunekfkDAO.findRozrachybekfkByPodatnik(podatnik));
        for (Rozrachunekfk p : listaNowychRozrachunkow) {
            for (Transakcja r : biezacetransakcje) {
                if (r.idSparowany().equals(p.getWierszStronafk().getWierszStronafkPK())) {
                    r.setSparowany(p);
                }
            }
        }

    }
    
}
