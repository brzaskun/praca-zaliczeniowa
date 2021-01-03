/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Kalendarzmiesiac;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Skladnikwynagrodzenia;
import entity.Zmiennawynagrodzenia;

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
            naliczenieskladnikawynagrodzenia.setKalendarzmiesiac(KalendarzmiesiacBean.create());
            naliczenieskladnikawynagrodzenia.setKwota(3500.0);
            naliczenieskladnikawynagrodzenia.setKwotabezzus(0.0);
            naliczenieskladnikawynagrodzenia.setKwotazus(3500.0);
            naliczenieskladnikawynagrodzenia.setKwotazredukowana(3500.0);
            naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(SkladnikwynagrodzeniaBean.createWynagrodzenie());
        }
        return naliczenieskladnikawynagrodzenia;
    }
    
    public static Naliczenieskladnikawynagrodzenia createWynagrodzenie(Kalendarzmiesiac kalendarzmiesiac, Skladnikwynagrodzenia skladnikwynagrodzenia) {
        Naliczenieskladnikawynagrodzenia zwrot = new Naliczenieskladnikawynagrodzenia();
        zwrot.setKalendarzmiesiac(kalendarzmiesiac);
        Zmiennawynagrodzenia zmiennawynagrodzenia = skladnikwynagrodzenia.getZmiennawynagrodzeniaList().get(0);
        zwrot.setKwota(zmiennawynagrodzenia.getKwota());
        zwrot.setKwotabezzus(0.0);
        zwrot.setKwotazus(zmiennawynagrodzenia.getKwota());
        zwrot.setKwotazredukowana(zmiennawynagrodzenia.getKwota());
        zwrot.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
        return zwrot;
    }
    
    public static Naliczenieskladnikawynagrodzenia createPremia() {
        if (naliczenieskladnikapremia == null) {
            naliczenieskladnikapremia = new Naliczenieskladnikawynagrodzenia();
            naliczenieskladnikapremia.setKalendarzmiesiac(KalendarzmiesiacBean.create());
            naliczenieskladnikapremia.setKwota(100.0);
            naliczenieskladnikapremia.setKwotabezzus(0.0);
            naliczenieskladnikapremia.setKwotazus(100.0);
            naliczenieskladnikapremia.setKwotazredukowana(100.0);
            naliczenieskladnikapremia.setSkladnikwynagrodzenia(SkladnikwynagrodzeniaBean.createPremiaUznaniowa());
        }
        return naliczenieskladnikapremia;
    }
    
    public static Naliczenieskladnikawynagrodzenia createNadgodziny50() {
        if (naliczenieskladnikanadgodziny50 == null) {
            naliczenieskladnikanadgodziny50 = new Naliczenieskladnikawynagrodzenia();
            naliczenieskladnikanadgodziny50.setKalendarzmiesiac(KalendarzmiesiacBean.create());
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
            naliczenieskladnikanadgodziny100.setKalendarzmiesiac(KalendarzmiesiacBean.create());
            naliczenieskladnikanadgodziny100.setKwota(100.0);
            naliczenieskladnikanadgodziny100.setKwotabezzus(0.0);
            naliczenieskladnikanadgodziny100.setKwotazus(100.0);
            naliczenieskladnikanadgodziny100.setKwotazredukowana(100.0);
            naliczenieskladnikanadgodziny100.setSkladnikwynagrodzenia(SkladnikwynagrodzeniaBean.createNadgodziny100());
        }
        return naliczenieskladnikanadgodziny100;
    }
    
   
    
}
