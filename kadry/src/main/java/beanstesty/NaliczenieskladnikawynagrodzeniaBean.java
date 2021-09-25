/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Dzien;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Pasekwynagrodzen;
import entity.Rachunekdoumowyzlecenia;
import entity.Skladnikwynagrodzenia;
import entity.Zmiennawynagrodzenia;
import java.util.List;
import z.Z;

/**
 *
 * @author Osito
 */
public class NaliczenieskladnikawynagrodzeniaBean {
    
    public static Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia;
    public static Naliczenieskladnikawynagrodzenia naliczenieskladnikapremia;
    public static Naliczenieskladnikawynagrodzenia naliczenieskladnikanadgodziny50;
    public static Naliczenieskladnikawynagrodzenia naliczenieskladnikanadgodziny100;
    
    public static Naliczenieskladnikawynagrodzenia createWynagrodzenie() {
        if (naliczenieskladnikawynagrodzenia == null) {
            naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
            naliczenieskladnikawynagrodzenia.setPasekwynagrodzen(PasekwynagrodzenBean.create());
            naliczenieskladnikawynagrodzenia.setKwota(3500.0);
            naliczenieskladnikawynagrodzenia.setKwotabezzus(0.0);
            naliczenieskladnikawynagrodzenia.setKwotazus(3500.0);
            naliczenieskladnikawynagrodzenia.setKwotazredukowana(3500.0);
            naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(SkladnikwynagrodzeniaBean.createWynagrodzenie());
        }
        return naliczenieskladnikawynagrodzenia;
    }
    
    public static Naliczenieskladnikawynagrodzenia createWynagrodzenieDBZlecenie(Pasekwynagrodzen pasekwynagrodzen, Skladnikwynagrodzenia skladnikwynagrodzenia, List<Dzien> listadni, double kurs, Rachunekdoumowyzlecenia rachunekdoumowyzlecenia) {
        Naliczenieskladnikawynagrodzenia zwrot = new Naliczenieskladnikawynagrodzenia();
        double zmiennawynagrodzeniakwota = rachunekdoumowyzlecenia.getKwota();
        zwrot.setPasekwynagrodzen(pasekwynagrodzen);
        zwrot.setKwota(zmiennawynagrodzeniakwota);
        if (rachunekdoumowyzlecenia.getSpoleczne()==true)  {
            zwrot.setKwotazus(zmiennawynagrodzeniakwota);
        } else if (rachunekdoumowyzlecenia.getPodatek()==false) {
            zwrot.setKwotabezzusbezpodatek(zmiennawynagrodzeniakwota);
        } else {
            zwrot.setKwotabezzus(zmiennawynagrodzeniakwota);
        }
        zwrot.setKwotazredukowana(zmiennawynagrodzeniakwota);
        zwrot.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
        return zwrot;
    }
    
    public static Naliczenieskladnikawynagrodzenia createWynagrodzenieDB(Pasekwynagrodzen pasekwynagrodzen, Skladnikwynagrodzenia skladnikwynagrodzenia, List<Dzien> listadni, double kurs) {
        Naliczenieskladnikawynagrodzenia zwrot = new Naliczenieskladnikawynagrodzenia();
        double zmiennawynagrodzeniakwota = 0.0;
        if (skladnikwynagrodzenia.getRodzajwynagrodzenia().getGodzinowe0miesieczne1()==true) {
            List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList = skladnikwynagrodzenia.getZmiennawynagrodzeniaList();
            for (Zmiennawynagrodzenia p : zmiennawynagrodzeniaList) {
                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("11")) {
                    zmiennawynagrodzeniakwota = p.getKwota();
                }
            }
        } else {
            double godzinyoddelegowanie = 0.0;
            for (Dzien p : listadni) {
                if (p.getTypdnia()>-1 && p.getKod().equals("777")) {
                    godzinyoddelegowanie = godzinyoddelegowanie+p.getPrzepracowano() ;
                }
            }
            List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList = skladnikwynagrodzenia.getZmiennawynagrodzeniaList();
            for (Zmiennawynagrodzenia p : zmiennawynagrodzeniaList) {
                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("11")) {
                    zmiennawynagrodzeniakwota = Z.z(p.getKwota()*kurs*godzinyoddelegowanie);
                }
            }
        }
        zwrot.setPasekwynagrodzen(pasekwynagrodzen);
        zwrot.setKwota(zmiennawynagrodzeniakwota);
        zwrot.setKwotabezzus(0.0);
        zwrot.setKwotabezzusbezpodatek(0.0);
        zwrot.setKwotazus(zmiennawynagrodzeniakwota);
        zwrot.setKwotazredukowana(zmiennawynagrodzeniakwota);
        zwrot.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
        return zwrot;
    }
    
    public static Naliczenieskladnikawynagrodzenia createPremia() {
        if (naliczenieskladnikapremia == null) {
            naliczenieskladnikapremia = new Naliczenieskladnikawynagrodzenia();
            naliczenieskladnikapremia.setPasekwynagrodzen(PasekwynagrodzenBean.create());
            naliczenieskladnikapremia.setKwota(100.0);
            naliczenieskladnikapremia.setKwotabezzus(0.0);
            naliczenieskladnikapremia.setKwotazus(100.0);
            naliczenieskladnikapremia.setKwotazredukowana(100.0);
            naliczenieskladnikapremia.setSkladnikwynagrodzenia(SkladnikwynagrodzeniaBean.createPremiaUznaniowa());
        }
        return naliczenieskladnikapremia;
    }
    
    public static Naliczenieskladnikawynagrodzenia createPremiaDB(Pasekwynagrodzen pasekwynagrodzen, Skladnikwynagrodzenia skladnikwynagrodzenia) {
            Naliczenieskladnikawynagrodzenia zwrot = new Naliczenieskladnikawynagrodzenia();
            double zmiennawynagrodzeniakwota = 0.0;
            List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList = skladnikwynagrodzenia.getZmiennawynagrodzeniaList();
            for (Zmiennawynagrodzenia p : zmiennawynagrodzeniaList) {
                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("21")) {
                    zmiennawynagrodzeniakwota = p.getKwota();
                }
            }
            zwrot.setPasekwynagrodzen(pasekwynagrodzen);
            zwrot.setKwota(zmiennawynagrodzeniakwota);
            zwrot.setKwotabezzus(0.0);
            zwrot.setKwotabezzusbezpodatek(0.0);
            zwrot.setKwotazus(zmiennawynagrodzeniakwota);
            zwrot.setKwotazredukowana(zmiennawynagrodzeniakwota);
            zwrot.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
        return zwrot;
    }
    
    
    
    public static Naliczenieskladnikawynagrodzenia createNadgodziny50() {
        if (naliczenieskladnikanadgodziny50 == null) {
            naliczenieskladnikanadgodziny50 = new Naliczenieskladnikawynagrodzenia();
            naliczenieskladnikanadgodziny50.setPasekwynagrodzen(PasekwynagrodzenBean.create());
            naliczenieskladnikanadgodziny50.setKwota(100.0);
            naliczenieskladnikanadgodziny50.setKwotabezzus(0.0);
            naliczenieskladnikanadgodziny50.setKwotazus(100.0);
            naliczenieskladnikanadgodziny50.setKwotazredukowana(100.0);
            naliczenieskladnikanadgodziny50.setSkladnikwynagrodzenia(SkladnikwynagrodzeniaBean.createNadgodziny50());
        }
        return naliczenieskladnikanadgodziny50;
    }
    
    public static Naliczenieskladnikawynagrodzenia createNadgodziny100() {
        if (naliczenieskladnikanadgodziny100 == null) {
            naliczenieskladnikanadgodziny100 = new Naliczenieskladnikawynagrodzenia();
            naliczenieskladnikanadgodziny100.setPasekwynagrodzen(PasekwynagrodzenBean.create());
            naliczenieskladnikanadgodziny100.setKwota(100.0);
            naliczenieskladnikanadgodziny100.setKwotabezzus(0.0);
            naliczenieskladnikanadgodziny100.setKwotazus(100.0);
            naliczenieskladnikanadgodziny100.setKwotazredukowana(100.0);
            naliczenieskladnikanadgodziny100.setSkladnikwynagrodzenia(SkladnikwynagrodzeniaBean.createNadgodziny100());
        }
        return naliczenieskladnikanadgodziny100;
    }
    
   public static Naliczenieskladnikawynagrodzenia createBezZusPodatekDB(Pasekwynagrodzen pasekwynagrodzen, Skladnikwynagrodzenia skladnikwynagrodzenia) {
            Naliczenieskladnikawynagrodzenia zwrot = new Naliczenieskladnikawynagrodzenia();
            double zmiennawynagrodzeniakwota = 0.0;
            List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList = skladnikwynagrodzenia.getZmiennawynagrodzeniaList();
            for (Zmiennawynagrodzenia p : zmiennawynagrodzeniaList) {
                if (p.isAktywna()) {
                    if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("90")) {
                        zmiennawynagrodzeniakwota = p.getKwota();
                        zwrot.setPasekwynagrodzen(pasekwynagrodzen);
                        zwrot.setKwota(zmiennawynagrodzeniakwota);
                        zwrot.setKwotabezzus(0.0);
                        zwrot.setKwotabezzusbezpodatek(zmiennawynagrodzeniakwota);
                        zwrot.setKwotazus(0.0);
                        zwrot.setKwotazredukowana(zmiennawynagrodzeniakwota);
                        zwrot.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                    }
                }
            }
            
        return zwrot;
    }
    
}
