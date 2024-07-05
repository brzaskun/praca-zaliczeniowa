/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import dao.StronaWierszaDAO;
import dao.TransakcjaDAO;
import entityfk.StronaWiersza;
import entityfk.Transakcja;
import entityfk.Wiersz;
import error.E;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.inject.Named;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named

public class DokFKTransakcjeBean implements Serializable{
    
        
     //************************* jeli pobierztransakcjeJakoSparowany() == 0 to robimy jakby nie byl nowa transakcja
    public static List<StronaWiersza> pobierzStronaWierszazBazy(StronaWiersza stronaWiersza, String wnma, StronaWierszaDAO stronaWierszaDAO, TransakcjaDAO transakcjaDAO) {
        List<StronaWiersza> listaStronaWierszazBazy =new ArrayList<>();
// stare = pobiera tylko w walucie dokumentu rozliczeniowego        
//      listaNowychRozrachunkow = stronaWierszaDAO.findStronaByKontoWnMaWaluta(stronaWiersza.getKonto(), stronaWiersza.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty(), stronaWiersza.getWnma());
// nowe pobiera wszystkie waluty      
//bierzez zapisy z bazy bo przeciez rozliczasz saldo konta i dana platnosc moze byc za dowolny zapis na koncie z dowolnego okresu w roku
        listaStronaWierszazBazy = stronaWierszaDAO.findStronaByKontoWnMa(stronaWiersza.getKonto(), wnma);
        //stronaWierszaDAO.refresh(listaStronaWierszazBazy);
        if (listaStronaWierszazBazy != null && !listaStronaWierszazBazy.isEmpty()) {
            try {
                DateFormat formatter;
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                String datarozrachunku = null;
                if (stronaWiersza.getWiersz().getDataWalutyWiersza() != null) {
                    datarozrachunku = stronaWiersza.getWiersz().getDokfk().getRok()+"-"+stronaWiersza.getWiersz().getDokfk().getMiesiac()+"-"+stronaWiersza.getWiersz().getDataWalutyWiersza();
                    } else {
                    datarozrachunku = stronaWiersza.getWiersz().getDokfk().getDatadokumentu();
                }
                Date dataR = formatter.parse(datarozrachunku);
                Iterator it = listaStronaWierszazBazy.iterator();
                while(it.hasNext()) {
                    StronaWiersza stronaWierszaZbazy = (StronaWiersza) it.next();
                    List<Transakcja> zachowaneTransakcje = transakcjaDAO.findByNowaTransakcja(stronaWierszaZbazy);
                    for (Iterator<Transakcja> itx = stronaWierszaZbazy.getPlatnosci().iterator(); itx.hasNext();) {
                        Transakcja transakcjazbazy = (Transakcja) itx.next();
                        if (zachowaneTransakcje == null || zachowaneTransakcje.size() == 0) {
                            itx.remove();
                        } else if (!zachowaneTransakcje.contains(transakcjazbazy)) {
                            itx.remove();
                        }
                    }
                    //nie wiem po co to jest czyz nie zaciagaja sie z bazy?
                    for (Transakcja ta : zachowaneTransakcje) {
                        if (!stronaWierszaZbazy.getPlatnosci().contains(ta)) {
                            stronaWierszaZbazy.getPlatnosci().add(ta);
                        }
                    }
                    if (Z.z(stronaWierszaZbazy.getPozostalo()) <= 0.0) {
                        it.remove();
                    } else {
                        String dataplatnosci;
                        if (stronaWierszaZbazy.getWiersz().getDataWalutyWiersza() != null) {
                            dataplatnosci = stronaWierszaZbazy.getWiersz().getDokfk().getRok()+"-"+stronaWierszaZbazy.getWiersz().getDokfk().getMiesiac()+"-"+stronaWierszaZbazy.getWiersz().getDataWalutyWiersza();
                        } else {
                            dataplatnosci = stronaWierszaZbazy.getWiersz().getDokfk().getDatadokumentu();
                        }
                        Date dataP = formatter.parse(dataplatnosci);
                        if (dataP.compareTo(dataR) > 0)  {
                            it.remove();
                        }
                    }
                }
            } catch (ParseException ex) {
                E.e(ex);
            }
        }
        List<StronaWiersza> stronywierszaBO = stronaWierszaDAO.findStronaByKontoWnMaBO(stronaWiersza.getKonto(), stronaWiersza.getWnma());
        if (stronywierszaBO != null && !stronywierszaBO.isEmpty()) {
            Iterator it = stronywierszaBO.iterator();
                while(it.hasNext()) {
                    StronaWiersza p = (StronaWiersza) it.next();
                    if (Z.z(p.getPozostalo()) <= 0.0) {
                        it.remove();
                    }
                }
            listaStronaWierszazBazy.addAll(stronywierszaBO);
        }
        if (listaStronaWierszazBazy == null) {
            return (new ArrayList<>());
        }
        return listaStronaWierszazBazy;
        //pobrano wiersze - a teraz z nich robie rozrachunki
    }
    
    public static List<StronaWiersza> pobierzStronaWierszazDokumentu(String nrkonta, String wnma, String waluta,List<Wiersz> wiersze) {
        //to jest po to bo w danym dokumencie moga juz byc wiersze z tym kontem, dokument nie jest jeszcze zachowany wiec tych rozrachunkow nie ma jeszcze w bazie nie ma tych transakcji
        List<StronaWiersza> listaWierszyzTymSamymKontem = new ArrayList<>();
        if (wnma.equals("Wn")) {
            for (Wiersz p : wiersze) {
                StronaWiersza r = null;
                if (p.getTypWiersza() == 2 || p.getTypWiersza() == 0) {
                    if (p.getStronaMa().getKonto() != null) {
                        r = p.getStronaMa();
                    }
                } else if (p.getTypWiersza() == 1 && p.getStronaWn().getKwota() < 0) {
                    r = p.getStronaWn();
                }
                if (r != null && r.getKonto().getPelnynumer().equals(nrkonta) && r.getTypStronaWiersza() == 1 && r.getPozostalo() !=0.0) {
                    listaWierszyzTymSamymKontem.add(r);
                }
            }
        } else if (wnma.equals("Ma")) {
            for (Wiersz p : wiersze) {
                StronaWiersza r = null;
                if (p.getTypWiersza() == 1 || p.getTypWiersza() == 0) {
                    r = p.getStronaWn();
                } else if (p.getTypWiersza() == 2 && p.getStronaMa().getKwota() < 0) {
                    r = p.getStronaMa();
                }
                if (r != null && r.getKonto().getPelnynumer().equals(nrkonta) && r.getTypStronaWiersza() == 1 && r.getPozostalo() !=0.0) {
                    listaWierszyzTymSamymKontem.add(r);
                }
            }
        }
        return listaWierszyzTymSamymKontem;
        //pobrano wiersze - a teraz z nich robie rozrachunki
    }
    
//    public static List<StronaWiersza> pobierzZapisaneWBazieStronaWierszazDokumentu(String nrkonta, String wnma, String waluta,List<Wiersz> wiersze) {
//        List<StronaWiersza> listaNowychRozrachunkowDokument = Collections.synchronizedList(new ArrayList<>());
//        if (wnma.equals("Wn")) {
//            for (Wiersz p : wiersze) {
//                if (p.getIdwiersza() != null && p.getStronaMa().getKonto() != null) {
//                    StronaWiersza r = p.getStronaMa();
//                    if (r.getId() != null && r.getKonto().getPelnynumer().equals(nrkonta) && r.getTypStronaWiersza() == 1) {
//                        listaNowychRozrachunkowDokument.add(r);
//                    }
//                }
//            }
//        } else if (wnma.equals("Ma")) {
//            for (Wiersz p : wiersze) {
//                if (p.getIdwiersza() != null) {
//                    StronaWiersza r = p.getStronaWn();
//                    if (r.getId() != null && r.getKonto().getPelnynumer().equals(nrkonta) && r.getTypStronaWiersza() == 1) {
//                        listaNowychRozrachunkowDokument.add(r);
//                    }
//                }
//            }
//        }
//        return listaNowychRozrachunkowDokument;
//        //pobrano wiersze - a teraz z nich robie rozrachunki
//    }
    
    public static List<Transakcja> stworznowetransakcjezeZapisanychStronWierszy(List<StronaWiersza> pobranezDokumentu, List<StronaWiersza> inneStronaWierszazBazy, StronaWiersza aktualnywierszdorozrachunkow, String podatnik, StronaWierszaDAO stronaWierszaDAO) {
        //sprawdzam, czy transakcje z bazy nie sa d okumnecie, a poniewaz te w dokumencie sa bardziej aktualne to usuwamy duplikaty z bazy
        List<Transakcja> transakcjeZAktualnego = Collections.synchronizedList(new ArrayList<>());
        transakcjeZAktualnego = ((aktualnywierszdorozrachunkow).getNowetransakcje());
        //czasa,i trafia sie zerowa transakcje nie wiem dlaczego nie ma pozycji nowaTransakcja usune to
        for (Iterator<Transakcja> it = transakcjeZAktualnego.iterator(); it.hasNext();) {
            Transakcja p = it.next();
            if (p.getNowaTransakcja()==null) {
                aktualnywierszdorozrachunkow.getNowetransakcje().remove(p);
                stronaWierszaDAO.edit(aktualnywierszdorozrachunkow);
                it.remove();
            } 
        }
        for (Transakcja p : transakcjeZAktualnego) {
                 if (inneStronaWierszazBazy.contains(p.getNowaTransakcja())) {
                inneStronaWierszazBazy.remove(p.getNowaTransakcja());
            }
            //jesli to bedzie nowe to nie bedzie usuniete, ale poniewaz pobiera wszystkie to trzeba usunac te co sa juz w transakcjach
            if (pobranezDokumentu.contains(p.getNowaTransakcja())) {
                pobranezDokumentu.remove(p.getNowaTransakcja());
            }
        }
        //jak tego nie bedzie to beda dwie transakjce utworzone
        for (StronaWiersza s : inneStronaWierszazBazy) {
            if (pobranezDokumentu.contains(s)) {
                pobranezDokumentu.remove(s);
            }
        }
        List<StronaWiersza> listaZbiorcza = Collections.synchronizedList(new ArrayList<>());
        //laczymy te stare z bazy i nowe z dokumentu
        listaZbiorcza.addAll(pobranezDokumentu);
        listaZbiorcza.addAll(inneStronaWierszazBazy);
        for (Iterator<Transakcja> it = transakcjeZAktualnego.iterator(); it.hasNext();) {
            Transakcja p = it.next();
            if (p.getNowaTransakcja()==null) {
               
            } else if (p.getKwotatransakcji()==0.0&&p.getNowaTransakcja().getPozostalo()==0.0) {
                 it.remove();
            }
            
        }
        //z pobranych StronWiersza tworzy sie transkakcje laczac rozrachunek rozliczony ze sparowanym
        // nie bedzie duplikatow bo wczesniej je usunelismmy po zaktualizowaniu wartosci w zalaczonych juz transakcjach
        for (StronaWiersza rachunek : listaZbiorcza) {
                Transakcja transakcja = new Transakcja(aktualnywierszdorozrachunkow, rachunek);
                if (rachunek.getPlatnosci().contains(transakcja) && transakcja.getKwotatransakcji() == 0.0) {
                    rachunek.getPlatnosci().remove(transakcja);
                } 
                if (!rachunek.getPlatnosci().contains(transakcja)) {
                    rachunek.getPlatnosci().add(transakcja);
                }
                //ja tego nie bedzie to bedzie w biezacych ale biezace nie sa transkacjami aktualnego
                aktualnywierszdorozrachunkow.getNowetransakcje().add(transakcja);
        }
        return aktualnywierszdorozrachunkow.getNowetransakcje() ;
    }
    
    //wykorzystywane jedynie przy nowej transakcji w celu pokazania podczapionych transakcji, nie jest modyfikowana
     public static List<Transakcja> pobierzbiezaceTransakcjeDlaNowejTransakcji(StronaWiersza stronawiersza, String wnma) {
        List<Transakcja> pobrana = Collections.synchronizedList(new ArrayList<>());
        try {
            pobrana.addAll((stronawiersza).getPlatnosci());
            return pobrana;
        } catch (Exception e) {
            return pobrana;
        }
    }

     //pomyslana jako funkcja 
    public static void naniesKwotyZTransakcjiwPowietrzu(StronaWiersza aktualnyWierszDlaRozrachunkow, List<Transakcja> biezacetransakcje, List<Wiersz> listawierszy, String stronawiersza) {
        List<StronaWiersza> pobraneStronyWiersza = new ArrayList<>();
        //pobieram wiersze z dokumentu do dalszych porownan
        if (stronawiersza.equals("Wn")) {
            for (Wiersz p : listawierszy) {
                if (p.getStronaWn() != aktualnyWierszDlaRozrachunkow) {
                    pobraneStronyWiersza.add(p.getStronaWn());
                }
            }
        } else {
            for (Wiersz p : listawierszy) {
                if (p.getStronaMa() != aktualnyWierszDlaRozrachunkow) {
                    pobraneStronyWiersza.add(p.getStronaMa());
                }
            }
        }
        List<Transakcja> transakcjeWPowietrzu = new ArrayList<>();
        for (StronaWiersza r : pobraneStronyWiersza) {
            if (r != null) {
                for (Transakcja u : r.getNowetransakcje()) {
                    if (u.getId() == null) {
                        transakcjeWPowietrzu.add(u);   
                    }
                }
            }
        }
        //tego nie moze byc bo to usuwa nowa pusta jeszcze transakcje ktora wlasnie dodalismy do rozliczanych!
//        for (Transakcja t : biezacetransakcje) {
//            for(Iterator<Transakcja> it = t.getNowaTransakcja().getPlatnosci().iterator(); it.hasNext();){
//                Transakcja uu = it.next();
//                if (uu.getTransakcjaPK() == null) {
//                    it.remove();
//                }
//            }
//        }

        for (Transakcja s: transakcjeWPowietrzu) {
            for (Transakcja t : biezacetransakcje) {
                if (t.getNowaTransakcja().equals(s.getNowaTransakcja())) {
                    Transakcja sa = serialclone.SerialClone.clone(s);
                    sa.setId(null);
                    sa.setRozliczajacy(null);
                    sa.setNowaTransakcja(null);
                    t.getNowaTransakcja().getPlatnosci().add(sa);
                }
            }
        }
        
    }
    
    public static int sprawdzrozliczoneWiersze(List<Wiersz> listawierszy) {
        int iloscrozliczonychwierszy = 0;
        for (Wiersz p : listawierszy) {
            if (p.getTypWiersza() == 0) {
                iloscrozliczonychwierszy += p.getStronaWn().getTypStronaWiersza();
                iloscrozliczonychwierszy += p.getStronaMa().getTypStronaWiersza();
            } else if (p.getTypWiersza() == 1) {
                iloscrozliczonychwierszy += p.getStronaWn().getTypStronaWiersza();
            } else if (p.getTypWiersza() == 2) {
                iloscrozliczonychwierszy += p.getStronaMa().getTypStronaWiersza();
            }
            if (iloscrozliczonychwierszy > 0) {
                break;
            }
        }
        return iloscrozliczonychwierszy;
    }
    
     public static void wyliczroznicekursowa(Transakcja transakcja, double placonakwota) {
        try {
            if (!transakcja.getRozliczajacy().getSymbolWalutBOiSW().equals("PLN") || !transakcja.getNowaTransakcja().getSymbolWalutBOiSW().equals("PLN")) {
                if (placonakwota == 0.0) {
                    transakcja.setRoznicekursowe(0.0);
                    transakcja.setKwotawwalucierachunku(0.0);
                } else if (placonakwota != 0.0) {
                    double kursPlatnosci = transakcja.getRozliczajacy().getWiersz().getTabelanbp().getKurssredni();
                    double kursRachunku = transakcja.getNowaTransakcja().getKursWalutyBOSW();
                    if (kursPlatnosci == 0.0 && kursRachunku != 1.0) {
                        if (placonakwota > 0.0) {
                            double kwotaPlatnosciwWalucie = Z.z(placonakwota / kursRachunku);
                            double kwotaRachunkuwWalucie = transakcja.getNowaTransakcja().getKwota() - transakcja.getNowaTransakcja().getRozliczono(transakcja);
                            double kwotaRachunkuwPLN = kwotaRachunkuwWalucie * kursRachunku;
                            double roznicakursowa = Z.z(placonakwota - kwotaRachunkuwPLN);
                            if (roznicakursowa > 0.0) {
                                transakcja.setRoznicekursowe(roznicakursowa);
                            } else {
                                transakcja.setRoznicekursowe(0.0);
                            }
                            transakcja.setKwotawwalucierachunku(kwotaPlatnosciwWalucie > kwotaRachunkuwWalucie ? kwotaRachunkuwWalucie : kwotaPlatnosciwWalucie);
                        }
                    } else if (kursPlatnosci == 0.0 && kursRachunku == 0.0) {
                        if (placonakwota > 0.0) {
                            transakcja.setKwotawwalucierachunku(placonakwota);
                        }
                    } else if (kursPlatnosci != 0.0 && kursRachunku == 0.0) {
                        if (placonakwota > 0.0) {
                            double kwotaPlatnosciwPLN = Z.z(placonakwota * kursPlatnosci);
                            double kwotaRachunkuwPLN = transakcja.getNowaTransakcja().getKwota() - transakcja.getNowaTransakcja().getRozliczono(transakcja);
                            double roznicakursowa = Z.z(kwotaPlatnosciwPLN - kwotaRachunkuwPLN);
                            if (roznicakursowa > 0.0) {
                                transakcja.setRoznicekursowe(roznicakursowa);
                            } else {
                                transakcja.setRoznicekursowe(0.0);
                            }
                            transakcja.setKwotawwalucierachunku(kwotaPlatnosciwPLN > kwotaRachunkuwPLN ? kwotaRachunkuwPLN : kwotaPlatnosciwPLN);
                        }
                    } else if (kursPlatnosci != 0.0 && kursRachunku != 0.0 && transakcja.getRozliczajacy().getSymbolWalutBOiSW().equals(transakcja.getNowaTransakcja().getSymbolWalutBOiSW())) {
                        if (placonakwota > 0.0) {
                            double kwotaPlatnosciwPLN = Z.z(placonakwota * kursPlatnosci);
                            double kwotaRachunkuwPLN = Z.z(placonakwota * kursRachunku);
                            double roznicakursowa = Z.z(kwotaPlatnosciwPLN - kwotaRachunkuwPLN);
                            transakcja.setRoznicekursowe(roznicakursowa);
                            transakcja.setKwotawwalucierachunku(placonakwota);
                        }
                    } else if (kursPlatnosci != 0.0 && kursRachunku != 0.0 && !transakcja.getRozliczajacy().getSymbolWalutBOiSW().equals(transakcja.getNowaTransakcja().getSymbolWalutBOiSW())) {
                            double kwotwaPlatnosciwWaluciepierwotnej = placonakwota;
                            double kwotaPlatnosciwPLN = Z.z(placonakwota * kursPlatnosci);
                            double kwotaPlatnosciwwalucieRachunku = Z.z(kwotaPlatnosciwPLN / kursRachunku);
                            //zostało do rozliczenia
                            double kwotaRachunkuwWalRach = transakcja.getNowaTransakcja().getKwota() - transakcja.getNowaTransakcja().getRozliczono(transakcja);
                            double kwotaPlatnosciRozliczanadoLimituwalutaRachunku = kwotaPlatnosciwwalucieRachunku > kwotaRachunkuwWalRach ? kwotaRachunkuwWalRach : kwotaPlatnosciwwalucieRachunku;
                            double kwotaPlatnosciwalutaPlatnoscikalkulacja = Z.z(kwotaPlatnosciRozliczanadoLimituwalutaRachunku * kursRachunku /kursPlatnosci);
                            if (kwotaPlatnosciwalutaPlatnoscikalkulacja<kwotwaPlatnosciwWaluciepierwotnej+0.01||kwotaPlatnosciwalutaPlatnoscikalkulacja>kwotwaPlatnosciwWaluciepierwotnej-0.01) {
                                kwotaPlatnosciwalutaPlatnoscikalkulacja = kwotwaPlatnosciwWaluciepierwotnej;
                            }
                            transakcja.setKwotatransakcji(kwotaPlatnosciwalutaPlatnoscikalkulacja);
                            double kwotaRachunkuwPLN = Z.z(kwotaPlatnosciRozliczanadoLimituwalutaRachunku * kursRachunku);
                            double roznicakursowa = Z.z(kwotaPlatnosciwPLN-kwotaRachunkuwPLN);
                            if (roznicakursowa > 0.0) {
                                transakcja.setRoznicekursowe(roznicakursowa);
                            } else {
                                transakcja.setRoznicekursowe(0.0);
                            }
                            transakcja.setKwotawwalucierachunku(kwotaPlatnosciRozliczanadoLimituwalutaRachunku);
                    }
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
}
