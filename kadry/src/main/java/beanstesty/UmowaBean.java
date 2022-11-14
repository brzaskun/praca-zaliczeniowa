/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import comparator.Umowacomparator;
import data.Data;
import static data.Data.getDzien;
import entity.Angaz;
import entity.Slownikszkolazatrhistoria;
import entity.Umowa;
import entity.Umowakodzus;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.Collections;
import java.util.List;
import kadryiplace.Osoba;
import kadryiplace.OsobaZlec;
import kadryiplace.ZatrudHist;

/**
 *
 * @author Osito
 */
public class UmowaBean {
    
    public static Umowa umowa;
    
    public static Umowa create() {
        if (umowa ==null) {
            umowa = new Umowa();
            umowa.setAngaz(AngazBean.create());
            umowa.setChorobowe(Boolean.TRUE);
            umowa.setChorobowedobrowolne(Boolean.FALSE);
            umowa.setDatado("2020-12-31");
            umowa.setDataod("2020-12-01");
            umowa.setDataspoleczne("2020-12-01");
            umowa.setDatazawarcia("2020-12-01");
            umowa.setDatazdrowotne("2020-12-01");
            umowa.setEmerytalne(Boolean.TRUE);
            umowa.setCzastrwania("umowa na okres próbny");
            umowa.setKodzawodu(KodzawoduBean.create());
            umowa.setNfz("16R");
            umowa.setNieliczFGSP(Boolean.FALSE);
            umowa.setNieliczFP(Boolean.FALSE);
            umowa.setRentowe(Boolean.TRUE);
            umowa.setUmowakodzus(UmowakodzusBean.create());
            umowa.setWypadkowe(Boolean.TRUE);
            umowa.setZdrowotne(Boolean.TRUE);
        }
        return umowa;
    }
    
     public static Umowa create(int numerumowy, Osoba osoba, Angaz angaz, ZatrudHist r, Slownikszkolazatrhistoria s, Umowakodzus umowakodzus) {
            Umowa umowa = new Umowa();
            umowa.setAngaz(angaz);
            if (umowakodzus.isPraca()) {
                umowa.setNrkolejny("UP/IMP/"+String.valueOf(numerumowy)+"/"+String.valueOf(angaz.getId()));
            } else if (umowakodzus.isFunkcja()) {
                umowa.setNrkolejny("PF/IMP/"+String.valueOf(numerumowy)+"/"+String.valueOf(angaz.getId()));
            }
            umowa.setDataod(Data.data_yyyyMMddNull(r.getZahDataOd()));
            umowa.setTerminrozpoczeciapracy(Data.data_yyyyMMddNull(r.getZahDataOd()));
            umowa.setDatado(Data.data_yyyyMMddNull(r.getZahDataDo()));
            umowa.setDataspoleczne(Data.data_yyyyMMddNull(r.getZahDataOd()));
            umowa.setDatazawarcia(Data.data_yyyyMMddNull(r.getZahDataOd()));
            umowa.setDatazdrowotne(Data.data_yyyyMMddNull(r.getZahDataOd()));
            umowa.setSlownikszkolazatrhistoria(s);
            umowa.setLiczdourlopu(true);
            if (s.getPraca0nauka1()==false) {
                umowa.setPrzyczynawypowiedzenia(r.getZahZwolUwagi());
                umowa.setChorobowe(Boolean.TRUE);
                umowa.setChorobowedobrowolne(Boolean.FALSE);
                umowa.setEmerytalne(Boolean.TRUE);
                umowa.setCzastrwania("umowa importowana");
                umowa.setNfz(osoba.getOsoKasaKod());
                umowa.setNieliczFGSP(Boolean.FALSE);
                umowa.setNieliczFP(Boolean.FALSE);
                umowa.setRentowe(Boolean.TRUE);
                umowa.setWypadkowe(Boolean.TRUE);
                umowa.setZdrowotne(Boolean.TRUE);
                umowa.setLiczdoemerytury(true);
                umowa.setLiczdonagrody(true);
                umowa.setLiczdostazowego(true);
                umowa.setAktywna(r.getZahStatus().equals('H')?false:true);
                osoba.getOsoUrzSerial();
            }
        return umowa;
    }
        
       
    public static Umowa createzlecenie(int numerumowy, Angaz angaz, OsobaZlec r) {
        Umowa umowa = new Umowa();
        umowa.setAngaz(angaz);
        umowa.setNrkolejny(r.getOzlNrUmowy());
        umowa.setDataod(Data.data_yyyyMMddNull(r.getOzlDataOd()));
        umowa.setDatado(Data.data_yyyyMMddNull(r.getOzlDataDo()));
        umowa.setDataspoleczne(Data.data_yyyyMMddNull(r.getOzlDataOd()));
        umowa.setDatazawarcia(Data.data_yyyyMMddNull(r.getOzlDataOd()));
        umowa.setDatazdrowotne(Data.data_yyyyMMddNull(r.getOzlDataOd()));
        umowa.setLiczdourlopu(false);
        umowa.setChorobowe(Boolean.FALSE);
        umowa.setChorobowedobrowolne(r.getOzlOptSerial().getOptZusChor().equals('T'));
        umowa.setEmerytalne(r.getOzlOptSerial().getOptZusEmer().equals('T'));
        umowa.setCzastrwania("umowa zlecenia import");
        umowa.setNfz(null);
        umowa.setOpiszawodu(r.getOzlPraca1());
        umowa.setNieliczFGSP(r.getOzlOptSerial().getOptZusFgsp().equals('N'));
        umowa.setNieliczFP(r.getOzlOptSerial().getOptZusFp().equals('N'));
        umowa.setRentowe(r.getOzlOptSerial().getOptZusRent().equals('T'));
        umowa.setWypadkowe(r.getOzlOptSerial().getOptZusWyp().equals('T'));
        umowa.setZdrowotne(r.getOzlOptSerial().getOptZusZdro().equals('T'));
        return umowa;
    }

    public static String obliczdatepierwszegozasilku(List<Umowa> umowaList, Umowa selected) {
        String zwrot = selected.getDataod();
        try {
            if (umowaList == null || umowaList.isEmpty()) {
                zwrot = pokazXXdzien(selected.getDataod(), 30);
            } else {
                Collections.sort(umowaList, new Umowacomparator());
                if (czyjestdziesieclatubezpieczenia(umowaList)) {
                    zwrot = selected.getDataod();
                } else {
                    int iledni = 0;
                    for (Umowa u : umowaList) {
                        if (u.getId() != null && !u.getId().equals(selected.getId())) {
                            if (u.getSlownikszkolazatrhistoria().getPraca0nauka1()) {
                                int wciaguiludnipodjetoprace = Data.iletodniKalendarzowych(u.getDatado(), selected.getDataod());
                                if ( wciaguiludnipodjetoprace < 90) {
                                    zwrot = selected.getDataod();
                                    break;
                                }
                            } else {
                                iledni = iledni+Data.iletodniKalendarzowych(u.getDataod(), u.getDatado());
                            }
                        }
                    }
                    if (selected.isChorobowe() == true && iledni<30) {
                        zwrot = pokazXXdzien(selected.getDataod(), 30-iledni);
                    } else if (selected.isChorobowedobrowolne() == true) {
                        zwrot = pokazXXdzien(selected.getDataod(), 90-iledni);
                    }
                }
            }
        } catch (Exception ef) {
        }

        return zwrot;
    }
    
    private static String pokazXXdzien(String dataod, int iledni) {
        LocalDate dzienzero = LocalDate.parse(dataod);
        LocalDate dzientrzydziesty = dzienzero.plusDays(iledni);
        return dzientrzydziesty.toString();
    }
    
//    public static void main (String[] args) {
//            LocalDate dzienzero = LocalDate.parse("2021-04-05");
//            LocalDate dzientrzydziesty = dzienzero.plusDays(30);
//            String toString = dzientrzydziesty.toString();
//            System.out.println("");
//        }

    private static boolean czyjestdziesieclatubezpieczenia(List<Umowa> umowaList) {
        boolean zwrot = false;
        double sumalat = 0.0;
        for (Umowa u : umowaList) {
            if (u.isChorobowe()) {
                sumalat = sumalat + u.getIloscdnitrwaniaumowy();
                if (sumalat>3650) {
                    zwrot = true;
                    break;
                }
            }
        }
        return zwrot;
    }


    public static void main(String[] args) {
        LocalDate dzienzero = LocalDate.parse("2021-01-01");
        LocalDate dzientrzydziesty = dzienzero.plusDays(30);
        String toString = dzientrzydziesty.toString();
        System.out.println(toString);
        LocalDate dzienzakonczenia = LocalDate.parse("2021-01-01");
        LocalDate dzienrozpoczecia = LocalDate.parse("2021-01-02");
        double between = DAYS.between(dzienzakonczenia, dzienrozpoczecia)+1;
        System.out.println(between);
        String dziendo = getDzien("2021-01-02");
        String dzienod = getDzien("2021-01-01");
        int dziendoint = Integer.parseInt(dziendo);
        int dzienodint = Integer.parseInt(dzienod);
        System.out.println(dziendoint-dzienodint+1);
    }
    
}
