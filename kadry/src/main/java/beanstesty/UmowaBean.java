/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import data.Data;
import entity.Angaz;
import entity.Slownikszkolazatrhistoria;
import entity.Umowa;
import java.util.ArrayList;
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
            umowa.setDatanfz("2020-12-01");
            umowa.setDataod("2020-12-01");
            umowa.setDataspoleczne("2020-12-01");
            umowa.setDatazawarcia("2020-12-01");
            umowa.setDatazdrowotne("2020-12-01");
            umowa.setEmerytalne(Boolean.TRUE);
            umowa.setCzastrwania("umowa na okres próbny");
            umowa.setKodzawodu(KodzawoduBean.create());
            umowa.setKosztyuzyskaniaprocent(250.0);
            umowa.setNfz("16R");
            umowa.setNieliczFGSP(Boolean.FALSE);
            umowa.setNieliczFP(Boolean.FALSE);
            umowa.setOdliczaculgepodatkowa(Boolean.TRUE);
            umowa.setRentowe(Boolean.TRUE);
            umowa.setUmowakodzus(UmowakodzusBean.create());
            umowa.setWypadkowe(Boolean.TRUE);
            umowa.setZdrowotne(Boolean.TRUE);
            umowa.setSkladnikwynagrodzeniaList(new ArrayList<>());
            umowa.getSkladnikwynagrodzeniaList().add(SkladnikwynagrodzeniaBean.createWynagrodzenie());
            umowa.getSkladnikwynagrodzeniaList().add(SkladnikwynagrodzeniaBean.createPremiaUznaniowa());
            umowa.setSkladnikpotraceniaList(new ArrayList<>());
            umowa.getSkladnikpotraceniaList().add(SkladnikpotraceniaBean.create());
        }
        return umowa;
    }
    
     public static Umowa create(int numerumowy, Osoba osoba, Angaz angaz, ZatrudHist r, Slownikszkolazatrhistoria s) {
            Umowa umowa = new Umowa();
            umowa.setAngaz(angaz);
            umowa.setNrkolejny("UP/IMP/"+String.valueOf(numerumowy)+"/"+String.valueOf(angaz.getId()));
            umowa.setDatanfz(Data.data_yyyyMMddNull(r.getZahDataOd()));
            umowa.setDataod(Data.data_yyyyMMddNull(r.getZahDataOd()));
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
                umowa.setCzastrwania("umowa na okres próbny");
                umowa.setKosztyuzyskaniaprocent(osoba.getOsoWynKosztyProc().doubleValue());
                umowa.setKwotawolnaprocent(osoba.getOsoPodWolnaProc().doubleValue());
                umowa.setNfz("16R");
                umowa.setNieliczFGSP(Boolean.FALSE);
                umowa.setNieliczFP(Boolean.FALSE);
                umowa.setOdliczaculgepodatkowa(Boolean.TRUE);
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
        
        public static void main (String[] args) {
            Umowa zwrot = create();
            System.out.println("");
        }

    public static Umowa createzlecenie(int numerumowy, Angaz angaz, OsobaZlec r) {
        Umowa umowa = new Umowa();
        umowa.setAngaz(angaz);
        umowa.setNrkolejny("UC/IMP/"+String.valueOf(numerumowy)+"/"+String.valueOf(angaz.getId()));
        umowa.setDatanfz(Data.data_yyyyMMddNull(r.getOzlDataOd()));
        umowa.setDataod(Data.data_yyyyMMddNull(r.getOzlDataOd()));
        umowa.setDatado(Data.data_yyyyMMddNull(r.getOzlDataDo()));
        umowa.setDataspoleczne(Data.data_yyyyMMddNull(r.getOzlDataOd()));
        umowa.setDatazawarcia(Data.data_yyyyMMddNull(r.getOzlDataOd()));
        umowa.setDatazdrowotne(Data.data_yyyyMMddNull(r.getOzlDataOd()));
        umowa.setLiczdourlopu(false);
        umowa.setChorobowe(Boolean.TRUE);
        umowa.setChorobowedobrowolne(Boolean.FALSE);
        umowa.setEmerytalne(Boolean.TRUE);
        umowa.setCzastrwania("umowa zlecenia");
        umowa.setKosztyuzyskaniaprocent(r.getOzlKosztProc().doubleValue());
        umowa.setNfz("16R");
        umowa.setNieliczFGSP(Boolean.FALSE);
        umowa.setNieliczFP(Boolean.FALSE);
        umowa.setOdliczaculgepodatkowa(Boolean.TRUE);
        umowa.setRentowe(Boolean.TRUE);
        umowa.setWypadkowe(Boolean.TRUE);
        umowa.setZdrowotne(Boolean.TRUE);
        return umowa;
    }

}
