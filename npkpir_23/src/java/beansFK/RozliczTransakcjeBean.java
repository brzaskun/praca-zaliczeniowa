/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import comparator.StronaWierszacomparator;
import data.Data;
import entityfk.StronaWiersza;
import entityfk.Transakcja;
import error.E;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Osito
 */
public class RozliczTransakcjeBean {

    public static StronaWiersza pobierznowatransakcje(List<StronaWiersza> w) {
        StronaWiersza zwrot = null;
        //to jest jak rozliczamy rachunek z BO
        for (Iterator<StronaWiersza> it = w.iterator(); it.hasNext();) {
            StronaWiersza s = it.next();
            if (s.isNowatransakcja() && s.getSymbolWalutyBO() == null) {
                zwrot = s;
                it.remove();
                break;
            }
        }
        if (zwrot != null) {
            for (StronaWiersza p : w) {
                if (p.getSymbolWalutyBO() != null  && p.getNowetransakcje() == null) {
                    p.setNowatransakcja(false);
                    p.setNowetransakcje(new ArrayList<>());
                }
            }
        } else {
            //to jest jak rozliczamy platnosc z BO
            for (Iterator<StronaWiersza> it = w.iterator(); it.hasNext();) {
               StronaWiersza s = it.next();
               if (s.isNowatransakcja() && s.getSymbolWalutyBO() != null) {
                   zwrot = s;
                   it.remove();
                   break;
               }
           }
           boolean bylarozliczana = true;
           for (StronaWiersza p : w) {
                if (p.getSymbolWalutyBO() == null && p.getNowetransakcje() == null) {
                    p.setNowatransakcja(false);
                    p.setNowetransakcje(new ArrayList<>());
                }
            }
        }
        return zwrot;
    }
    
    public static StronaWiersza wybierzjednatransakcje(List<StronaWiersza> w) {
        StronaWiersza zwrot = null;
        for (StronaWiersza p : w) {
            if (p.getNowetransakcje() == null || p.getNowetransakcje().isEmpty()) {
                p.setNowatransakcja(true);
                p.setPlatnosci(new ArrayList<>());
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }
    
    public static List<StronaWiersza> pobierzNoweTransakcje(List<StronaWiersza> w) {
        List<StronaWiersza> zwrot = new ArrayList<>();
        for (StronaWiersza p : w) {
            if (p.isNowatransakcja()) {
                zwrot.add(p);
            }
        }
        return zwrot;
    }
    
    public static List<StronaWiersza> pobierzRozliczenia(List<StronaWiersza> w) {
        List<StronaWiersza> zwrot = new ArrayList<>();
        for (StronaWiersza p : w) {
            if (p.isNowatransakcja()==false) {
                zwrot.add(p);
            }
        }
        return zwrot;
    }
    
    public static StronaWiersza sprawdzczyjestkorekta(List<StronaWiersza> w) {
        StronaWiersza zwrot = null;
        boolean brakkorekty = true;
        if (w.size() == 2) {
            for (Iterator<StronaWiersza> it = w.iterator(); it.hasNext();) {
                StronaWiersza s = it.next();
                if (s.getKwota() < 0.0) {
                    brakkorekty = false;
                }
            }
            if (brakkorekty == false) {
                for (Iterator<StronaWiersza> it = w.iterator(); it.hasNext();) {
                    StronaWiersza s = it.next();
                    if (s.getKwota() < 0.0) {
                        s.setNowatransakcja(false);
                        s.setNowetransakcje(new ArrayList<>());
                    }
                    if (s.getKwota() > 0.0 && !s.isNowatransakcja()) {
                        s.setNowatransakcja(true);
                        s.setPlatnosci(new ArrayList<>());
                    }
                }
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
            if (nowatransakcja != null) {
                if (nowatransakcja.getTypStronaWiersza() != 1) {
                    nowatransakcja.setTypStronaWiersza(1);
                }
                Double limit = nowatransakcja.getPozostalo();
                if (limit>0.0) {
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
                                List<Transakcja> nowalistatransakcji = Collections.synchronizedList(new ArrayList<>());
                                nowalistatransakcji.add(transakcja);
                                nowatransakcja.setPlatnosci(nowalistatransakcji);
                            }
                            if (platnosc.getNowetransakcje() != null) {
                                //ja tego nie bedzie to bedzie w biezacych ale biezace nie sa transkacjami aktualnego
                                platnosc.getNowetransakcje().add(transakcja);
                            } else {
                                List<Transakcja> nowalistaplatnosci = Collections.synchronizedList(new ArrayList<>());
                                nowalistaplatnosci.add(transakcja);
                                platnosc.setNowetransakcje(nowalistaplatnosci);
                            }
                            limit = limit - dorozliczenia;
                        } else {
                            break;
                        }
                    }
                    w.add(nowatransakcja);
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
        return w;
    }
    
    public static List<StronaWiersza> naniestransakcjeRozne(List<StronaWiersza> w) {
        try {
            List<StronaWiersza> noweTransakcje = pobierzNoweTransakcje(w);
            Collections.sort(noweTransakcje, new StronaWierszacomparator());
            List<StronaWiersza> rozliczenia = pobierzRozliczenia(w);
            Collections.sort(rozliczenia, new StronaWierszacomparator());
            if (noweTransakcje.isEmpty() == false && rozliczenia.isEmpty() == false) {
                for (StronaWiersza nowatransakcja : noweTransakcje) {
                    if (nowatransakcja != null) {
                        Double limitNowejTransakcji = nowatransakcja.getPozostalo();
                        if (limitNowejTransakcji > 0.0) {
                            for (Iterator<StronaWiersza> it = rozliczenia.iterator(); it.hasNext();) {
                                StronaWiersza platnosc = it.next();
                                double dorozliczenia = obliczkwotedorozliczenia(limitNowejTransakcji, platnosc);
                                if (dorozliczenia > 0.0 && nowatransakcja.equals(platnosc)==false) {
                                    Transakcja transakcja = new Transakcja(platnosc, nowatransakcja);
                                    transakcja.setDatarozrachunku(wyliczdatetransakcji(platnosc));
                                    transakcja.setKwotatransakcji(dorozliczenia);
                                    transakcja.setKwotawwalucierachunku(dorozliczenia);
                                    RozniceKursoweBean.rozliczroznicekursowe(transakcja);
                                    if (nowatransakcja.getPlatnosci() != null) {
                                        nowatransakcja.getPlatnosci().add(transakcja);
                                    } else {
                                        List<Transakcja> nowalistatransakcji = Collections.synchronizedList(new ArrayList<>());
                                        nowalistatransakcji.add(transakcja);
                                        nowatransakcja.setPlatnosci(nowalistatransakcji);
                                    }
                                    if (platnosc.getNowetransakcje() != null) {
                                        //ja tego nie bedzie to bedzie w biezacych ale biezace nie sa transkacjami aktualnego
                                        platnosc.getNowetransakcje().add(transakcja);
                                    } else {
                                        List<Transakcja> nowalistaplatnosci = Collections.synchronizedList(new ArrayList<>());
                                        nowalistaplatnosci.add(transakcja);
                                        platnosc.setNowetransakcje(nowalistaplatnosci);
                                    }
                                    limitNowejTransakcji = limitNowejTransakcji - dorozliczenia;
                                } else {
                                    it.remove();
                                }
                            }
                        } else {
                            break;
                        }
                        w.add(nowatransakcja);
                    }
                }
            }
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
            zwrot = platnosc.getDokfk().getRok() + "-" + platnosc.getDokfk().getMiesiac() + "-" + datawiersza;
        } else {
            zwrot = platnosc.getDokfk().getDataoperacji();
        }
        return zwrot;
    }

    public static boolean wiecejnizjednatransakcja(List<StronaWiersza> w) {
        boolean jestwiecej = false;
        int liczba = 0;
        for (StronaWiersza p : w) {
            if (p.isNowatransakcja() && p.getSymbolWalutyBO() == null) {
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
