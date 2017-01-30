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
import waluty.Z;

/**
 *
 * @author Osito
 */
public class RozniceKursoweBean {
    
     public static List<StronaWiersza> naniestransakcje(List<StronaWiersza> w) {
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
                rozliczroznicekursowe(transakcja);
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
        return w;
    }
    
    private static void rozliczroznicekursowe(Transakcja loop) {
        try {
            if (!loop.getRozliczajacy().getSymbolWaluty().equals("PLN") || !loop.getNowaTransakcja().getSymbolWaluty().equals("PLN")) {
                double placonakwota = loop.getKwotatransakcji();
                if (placonakwota != 0.0) {
                    double kursPlatnosci = loop.getRozliczajacy().getWiersz().getTabelanbp().getKurssredni();
                    double kursRachunku = loop.getNowaTransakcja().getKursWalutyBOSW();
                    if (kursPlatnosci == 0.0 && kursRachunku != 0.0) {
                        if (placonakwota > 0.0) {
                            double kwotaPlatnosciwWalucie = Z.z(placonakwota / kursRachunku);
                            double kwotaRachunkuwWalucie = loop.getNowaTransakcja().getKwota() - loop.getNowaTransakcja().getRozliczono() + placonakwota;
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
                            double kwotaRachunkuwPLN = loop.getNowaTransakcja().getKwota() - loop.getNowaTransakcja().getRozliczono() + placonakwota;
                            double roznicakursowa = Z.z(kwotaPlatnosciwPLN - kwotaRachunkuwPLN);
                            if (roznicakursowa > 0.0) {
                                loop.setRoznicekursowe(roznicakursowa);
                            } else {
                                loop.setRoznicekursowe(0.0);
                            }
                            loop.setKwotawwalucierachunku(kwotaPlatnosciwPLN > kwotaRachunkuwPLN ? kwotaRachunkuwPLN : kwotaPlatnosciwPLN);
                        }
                    } else if (kursPlatnosci != 0.0 && kursRachunku != 0.0) {
                        if (placonakwota > 0.0) {
                            double kwotaPlatnosciwPLN = Z.z(placonakwota * kursPlatnosci);
                            double kwotaRachunkuwPLN = Z.z(placonakwota * kursRachunku);
                            double roznicakursowa = Z.z(kwotaPlatnosciwPLN - kwotaRachunkuwPLN);
                            loop.setRoznicekursowe(roznicakursowa);
                            loop.setKwotawwalucierachunku(placonakwota);
                        }
                    }
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
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
    
    private static StronaWiersza pobierznowatransakcje(List<StronaWiersza> w) {
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
