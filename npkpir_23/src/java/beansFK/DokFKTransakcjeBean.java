/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import dao.StronaWierszaDAO;
import daoFK.TransakcjaDAO;
import entityfk.StronaWiersza;
import entityfk.Transakcja;
import entityfk.Wiersz;
import error.E;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import msg.Msg;
import params.Params;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named

public class DokFKTransakcjeBean implements Serializable{
    
        
     //************************* jeli pobierztransakcjeJakoSparowany() == 0 to robimy jakby nie byl nowa transakcja
    public static List<StronaWiersza> pobierzStronaWierszazBazy(StronaWiersza stronaWiersza, String wnma, StronaWierszaDAO stronaWierszaDAO, TransakcjaDAO transakcjaDAO) {
        List<StronaWiersza> listaStronaWierszazBazy = new ArrayList<>();
// stare = pobiera tylko w walucie dokumentu rozliczeniowego        
//      listaNowychRozrachunkow = stronaWierszaDAO.findStronaByKontoWnMaWaluta(stronaWiersza.getKonto(), stronaWiersza.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty(), stronaWiersza.getWnma());
// nowe pobiera wszystkie waluty        
        listaStronaWierszazBazy = stronaWierszaDAO.findStronaByKontoWnMa(stronaWiersza.getKonto(), wnma);
        if (listaStronaWierszazBazy != null && !listaStronaWierszazBazy.isEmpty()) {
            try {
                DateFormat formatter;
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                String datarozrachunku = null;
                if (stronaWiersza.getWiersz().getDataWalutyWiersza() != null) {
                    datarozrachunku = stronaWiersza.getWiersz().getDokfk().getDokfkPK().getRok()+"-"+stronaWiersza.getWiersz().getDokfk().getMiesiac()+"-"+stronaWiersza.getWiersz().getDataWalutyWiersza();
                    } else {
                    datarozrachunku = stronaWiersza.getWiersz().getDokfk().getDatadokumentu();
                }
                Date dataR = formatter.parse(datarozrachunku);
                Iterator it = listaStronaWierszazBazy.iterator();
                while(it.hasNext()) {
                    StronaWiersza wierszZbazy = (StronaWiersza) it.next();
                    List<Transakcja> odnalezione = transakcjaDAO.findByNowaTransakcja(wierszZbazy);
                    for (Iterator<Transakcja> itx = wierszZbazy.getPlatnosci().iterator(); itx.hasNext();) {
                        Transakcja t = (Transakcja) itx.next();
                        if (odnalezione == null || odnalezione.size() == 0) {
                            itx.remove();
                        } else if (!odnalezione.contains(t)) {
                            itx.remove();
                        }
                    }
                    for (Transakcja ta : odnalezione) {
                        if (!wierszZbazy.getPlatnosci().contains(ta)) {
                            wierszZbazy.getPlatnosci().add(ta);
                        }
                    }
                    if (Z.z(wierszZbazy.getPozostalo()) <= 0.0) {
                        it.remove();
                    } else {
                        String dataplatnosci;
                        if (wierszZbazy.getWiersz().getDataWalutyWiersza() != null) {
                            dataplatnosci = wierszZbazy.getWiersz().getDokfk().getDokfkPK().getRok()+"-"+wierszZbazy.getWiersz().getDokfk().getMiesiac()+"-"+wierszZbazy.getWiersz().getDataWalutyWiersza();
                        } else {
                            dataplatnosci = wierszZbazy.getWiersz().getDokfk().getDatadokumentu();
                        }
                        Date dataP = formatter.parse(dataplatnosci);
                        if (dataP.compareTo(dataR) > 0)  {
                            it.remove();
                        }
                    }
                }
            } catch (ParseException ex) {

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
        List<StronaWiersza> listaNowychRozrachunkowDokument = new ArrayList<>();
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
                    listaNowychRozrachunkowDokument.add(r);
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
                    listaNowychRozrachunkowDokument.add(r);
                }
            }
        }
        return listaNowychRozrachunkowDokument;
        //pobrano wiersze - a teraz z nich robie rozrachunki
    }
    
//    public static List<StronaWiersza> pobierzZapisaneWBazieStronaWierszazDokumentu(String nrkonta, String wnma, String waluta,List<Wiersz> wiersze) {
//        List<StronaWiersza> listaNowychRozrachunkowDokument = new ArrayList<>();
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
    
    public static List<Transakcja> stworznowetransakcjezeZapisanychStronWierszy(List<StronaWiersza> pobranezDokumentu, List<StronaWiersza> inneStronaWierszazBazy, StronaWiersza aktualnywierszdorozrachunkow, String podatnik) {
        //sprawdzam, czy transakcje z bazy nie sa d okumnecie, a poniewaz te w dokumencie sa bardziej aktualne to usuwamy duplikaty z bazy
        List<Transakcja> transakcjeZAktualnego = new ArrayList<>();
        transakcjeZAktualnego = ((aktualnywierszdorozrachunkow).getNowetransakcje());
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
        List<StronaWiersza> listaZbiorcza = new ArrayList<>();
        //laczymy te stare z bazy i nowe z dokumentu
        listaZbiorcza.addAll(pobranezDokumentu);
        listaZbiorcza.addAll(inneStronaWierszazBazy);
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
        List<Transakcja> pobrana = new ArrayList<>();
        try {
            pobrana.addAll((stronawiersza).getPlatnosci());
            return pobrana;
        } catch (Exception e) {
            System.out.println("Blad " + e.getStackTrace()[0].toString());
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
                    if (u.getTransakcjaPK() == null) {
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
                    sa.setRozliczajacy(null);
                    sa.setNowaTransakcja(null);
                    sa.setTransakcjaPK(null);
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
        }
        return iloscrozliczonychwierszy;
    }
    
     public static void wyliczroznicekursowa(Transakcja loop, double placonakwota) {
        try {
            if (!loop.getRozliczajacy().getSymbolWalut().equals("PLN") || !loop.getNowaTransakcja().getSymbolWalut().equals("PLN")) {
                if (placonakwota == 0.0) {
                    loop.setRoznicekursowe(0.0);
                    loop.setKwotawwalucierachunku(0.0);
                } else if (placonakwota != 0.0) {
                    double kursPlatnosci = loop.getRozliczajacy().getWiersz().getTabelanbp().getKurssredni();
                    double kursRachunku = loop.getNowaTransakcja().getKursWalutyBOSW();
                    if (kursPlatnosci == 0.0 && kursRachunku != 0.0) {
                        if (placonakwota > 0.0) {
                            double kwotaPlatnosciwWalucie = Z.z(placonakwota / kursRachunku);
                            double kwotaRachunkuwWalucie = loop.getNowaTransakcja().getKwota() - loop.getNowaTransakcja().getRozliczono(loop);
                            double kwotaRachunkuwPLN = kwotaRachunkuwWalucie * kursRachunku;
                            double roznicakursowa = Z.z(placonakwota - kwotaRachunkuwPLN);
                            if (roznicakursowa > 0.0) {
                                loop.setRoznicekursowe(roznicakursowa);
                            } else {
                                loop.setRoznicekursowe(0.0);
                            }
                            loop.setKwotawwalucierachunku(kwotaPlatnosciwWalucie > kwotaRachunkuwWalucie ? kwotaRachunkuwWalucie : kwotaPlatnosciwWalucie);
                        }
                    } else if (kursPlatnosci == 0.0 && kursRachunku == 0.0) {
                        if (placonakwota > 0.0) {
                            loop.setKwotawwalucierachunku(placonakwota);
                        }
                    } else if (kursPlatnosci != 0.0 && kursRachunku == 0.0) {
                        if (placonakwota > 0.0) {
                            double kwotaPlatnosciwPLN = Z.z(placonakwota * kursPlatnosci);
                            double kwotaRachunkuwPLN = loop.getNowaTransakcja().getKwota() - loop.getNowaTransakcja().getRozliczono(loop);
                            double roznicakursowa = Z.z(kwotaPlatnosciwPLN - kwotaRachunkuwPLN);
                            if (roznicakursowa > 0.0) {
                                loop.setRoznicekursowe(roznicakursowa);
                            } else {
                                loop.setRoznicekursowe(0.0);
                            }
                            loop.setKwotawwalucierachunku(kwotaPlatnosciwPLN > kwotaRachunkuwPLN ? kwotaRachunkuwPLN : kwotaPlatnosciwPLN);
                        }
                    } else if (kursPlatnosci != 0.0 && kursRachunku != 0.0 && loop.getRozliczajacy().getSymbolWalut().equals(loop.getNowaTransakcja().getSymbolWalut())) {
                        if (placonakwota > 0.0) {
                            double kwotaPlatnosciwPLN = Z.z(placonakwota * kursPlatnosci);
                            double kwotaRachunkuwPLN = Z.z(placonakwota * kursRachunku);
                            double roznicakursowa = Z.z(kwotaPlatnosciwPLN - kwotaRachunkuwPLN);
                            loop.setRoznicekursowe(roznicakursowa);
                            loop.setKwotawwalucierachunku(placonakwota);
                        }
                    } else if (kursPlatnosci != 0.0 && kursRachunku != 0.0 && !loop.getRozliczajacy().getSymbolWalut().equals(loop.getNowaTransakcja().getSymbolWalut())) {
                            double kwotaPlatnosciwPLN = Z.z(placonakwota * kursPlatnosci);
                            double kwotaPlatnosciwwalucieRachunku = Z.z(kwotaPlatnosciwPLN / kursRachunku);
                            double kwotadoRozlwWalRach = loop.getNowaTransakcja().getKwota() - loop.getNowaTransakcja().getRozliczono(loop);
                            double kwotaRachunkuwPLN = kwotadoRozlwWalRach * kursRachunku;
                            double roznicakursowa = Z.z(kwotaPlatnosciwPLN-kwotaRachunkuwPLN);
                            if (roznicakursowa > 0.0) {
                                loop.setRoznicekursowe(roznicakursowa);
                            } else {
                                loop.setRoznicekursowe(0.0);
                            }
                            loop.setKwotawwalucierachunku(kwotaPlatnosciwwalucieRachunku > kwotadoRozlwWalRach ? kwotadoRozlwWalRach : kwotaPlatnosciwwalucieRachunku);
                    }
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
}
