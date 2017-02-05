/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import data.Data;
import entityfk.StronaWiersza;
import entityfk.Transakcja;
import error.E;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Osito
 */
public class RozliczTransakcjeBean {

    public static StronaWiersza pobierznowatransakcje(List<StronaWiersza> w) {
        StronaWiersza zwrot = null;
        for (Iterator<StronaWiersza> it = w.iterator(); it.hasNext();) {
            StronaWiersza s = it.next();
            if (s.isNowatransakcja()) {
                zwrot = s;
                it.remove();
                break;
            }
        }
        return zwrot;
    }
    
    public static StronaWiersza sprawdznowatransakcje(List<StronaWiersza> w) {
        StronaWiersza zwrot = null;
        for (Iterator<StronaWiersza> it = w.iterator(); it.hasNext();) {
            StronaWiersza s = it.next();
            if (s.isNowatransakcja()) {
                zwrot = s;
                break;
            }
        }
        return zwrot;
    }

    public static List<StronaWiersza> naniestransakcje(List<StronaWiersza> w) {
        try {
            StronaWiersza nowatransakcja = pobierznowatransakcje(w);
            if (nowatransakcja.getTypStronaWiersza() != 1) {
                nowatransakcja.setTypStronaWiersza(1);
            }
            Double limit = nowatransakcja.getPozostalo();
            for (StronaWiersza platnosc : w) {
                double dorozliczenia = obliczkwotedorozliczenia(limit, platnosc);
                if (dorozliczenia > 0.0) {
                    platnosc.setTypStronaWiersza(2);
                    Transakcja transakcja = new Transakcja(platnosc, nowatransakcja);
                    transakcja.setDatarozrachunku(wyliczdatetransakcji(platnosc));
                    transakcja.setKwotatransakcji(dorozliczenia);
                    transakcja.setKwotawwalucierachunku(dorozliczenia);
                    RozniceKursoweBean.rozliczroznicekursowe(transakcja);
                    if (nowatransakcja.getPlatnosci() != null) {
                        nowatransakcja.getPlatnosci().add(transakcja);
                    } else {
                        List<Transakcja> nowalistatransakcji = new ArrayList<>();
                        nowalistatransakcji.add(transakcja);
                        nowatransakcja.setPlatnosci(nowalistatransakcji);
                    }
                    if (platnosc.getNowetransakcje() != null) {
                        //ja tego nie bedzie to bedzie w biezacych ale biezace nie sa transkacjami aktualnego
                        platnosc.getNowetransakcje().add(transakcja);
                    } else {
                        List<Transakcja> nowalistaplatnosci = new ArrayList<>();
                        nowalistaplatnosci.add(transakcja);
                        platnosc.setNowetransakcje(nowalistaplatnosci);
                    }
                    limit = limit - dorozliczenia;
                } else {
                    break;
                }
            }
            w.add(nowatransakcja);
        } catch (Exception e) {
            E.e(e);
        }
        return w;
    }

    private static double obliczkwotedorozliczenia(Double limit, StronaWiersza pl) {
        double kwotadorozliczenia = limit;
        if (pl.getPozostalo() <= limit) {
            kwotadorozliczenia = pl.getPozostalo();
        }
        return kwotadorozliczenia;
    }

    private static String wyliczdatetransakcji(StronaWiersza platnosc) {
        String zwrot = Data.aktualnaData();
        if (platnosc.getWiersz().getDataWalutyWiersza() != null) {
            String datawiersza;
            if (platnosc.getWiersz().getDataWalutyWiersza().length() == 1) {
                datawiersza = "0" + platnosc.getWiersz().getDataWalutyWiersza();
            } else {
                datawiersza = platnosc.getWiersz().getDataWalutyWiersza();
            }
            zwrot = platnosc.getDokfk().getDokfkPK().getRok() + "-" + platnosc.getDokfk().getMiesiac() + "-" + datawiersza;
        } else {
            zwrot = platnosc.getDokfk().getDataoperacji();
        }
        return zwrot;
    }

    public static boolean wiecejnizjednatransakcja(List<StronaWiersza> w) {
        boolean jestwiecej = false;
        int liczba = 0;
        for (StronaWiersza p : w) {
            if (p.isNowatransakcja()) {
                liczba += 1;
                if (liczba > 1) {
                    jestwiecej = true;
                    break;
                }
            }
        }
        return jestwiecej;
    }
    
}
