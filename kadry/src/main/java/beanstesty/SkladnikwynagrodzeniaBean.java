/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Kalendarzmiesiac;
import entity.Skladnikwynagrodzenia;

/**
 *
 * @author Osito
 */
public class SkladnikwynagrodzeniaBean {
    
    public static Skladnikwynagrodzenia skladnikwynagrodzenia;
    public static Skladnikwynagrodzenia skladnikpremiauznaniowa;
    public static Skladnikwynagrodzenia skladniknadgodziny50;
        public static Skladnikwynagrodzenia skladniknadgodziny100;
    
    public static Skladnikwynagrodzenia createWynagrodzenie() {
        if (skladnikwynagrodzenia==null) {
            skladnikwynagrodzenia = new Skladnikwynagrodzenia();
            skladnikwynagrodzenia.setUwagi("wynagrodzenie zasadnicze");
            skladnikwynagrodzenia.setUmowa(UmowaBean.create());
            skladnikwynagrodzenia.setGodzinowe0miesieczne1(Boolean.TRUE);
            skladnikwynagrodzenia.setStala0zmienna1(Boolean.FALSE);
            skladnikwynagrodzenia.setRedukowanyzaczasnieobecnosci(Boolean.TRUE);
            skladnikwynagrodzenia.setKod("10");
            skladnikwynagrodzenia.getZmiennawynagrodzeniaList().add(ZmiennawynagrodzeniaBean.createWynagrodzenie());
        }
        return skladnikwynagrodzenia;
    }
    
    public static Skladnikwynagrodzenia createPremiaUznaniowa() {
        if (skladnikpremiauznaniowa==null) {
            skladnikpremiauznaniowa = new Skladnikwynagrodzenia();
            skladnikpremiauznaniowa.setUwagi("premia uznaniowa");
            skladnikpremiauznaniowa.setUmowa(UmowaBean.create());
            skladnikpremiauznaniowa.setGodzinowe0miesieczne1(Boolean.TRUE);
            skladnikpremiauznaniowa.setStala0zmienna1(Boolean.TRUE);
            skladnikpremiauznaniowa.setRedukowanyzaczasnieobecnosci(Boolean.FALSE);
            skladnikpremiauznaniowa.setKod("20");
            skladnikpremiauznaniowa.getZmiennawynagrodzeniaList().add(ZmiennawynagrodzeniaBean.createPremia());
        }
        return skladnikpremiauznaniowa;
    }
    
    public static Skladnikwynagrodzenia createNadgodziny50() {
        if (skladniknadgodziny50==null) {
            skladniknadgodziny50 = new Skladnikwynagrodzenia();
            skladniknadgodziny50.setUwagi("nadgodziny 50%");
            skladniknadgodziny50.setUmowa(UmowaBean.create());
            skladniknadgodziny50.setGodzinowe0miesieczne1(Boolean.FALSE);
            skladniknadgodziny50.setStala0zmienna1(Boolean.TRUE);
            skladniknadgodziny50.setRedukowanyzaczasnieobecnosci(Boolean.FALSE);
            skladniknadgodziny50.setKod("30");
            skladniknadgodziny50.getZmiennawynagrodzeniaList().add(ZmiennawynagrodzeniaBean.createPremia());
        }
        return skladniknadgodziny50;
    }
    
    public static Skladnikwynagrodzenia createNadgodziny50DB(Kalendarzmiesiac kalendarz, String dataod, String datado) {
            Skladnikwynagrodzenia zwrot = new Skladnikwynagrodzenia();
            zwrot.setUwagi("nadgodziny 50%");
            zwrot.setUmowa(kalendarz.getUmowa());
            zwrot.setGodzinowe0miesieczne1(Boolean.FALSE);
            zwrot.setStala0zmienna1(Boolean.TRUE);
            zwrot.setRedukowanyzaczasnieobecnosci(Boolean.FALSE);
            zwrot.setKod("30");
            zwrot.getZmiennawynagrodzeniaList().add(ZmiennawynagrodzeniaBean.createNadgodziny50DB(zwrot, dataod, datado));
            return zwrot;
    }
    
    
    public static Skladnikwynagrodzenia createNadgodziny100() {
        if (skladniknadgodziny100==null) {
            skladniknadgodziny100 = new Skladnikwynagrodzenia();
            skladniknadgodziny100.setUwagi("nadgodziny 100%");
            skladniknadgodziny100.setUmowa(UmowaBean.create());
            skladniknadgodziny100.setGodzinowe0miesieczne1(Boolean.FALSE);
            skladniknadgodziny100.setStala0zmienna1(Boolean.TRUE);
            skladniknadgodziny100.setRedukowanyzaczasnieobecnosci(Boolean.FALSE);
            skladniknadgodziny100.setKod("31");
            skladniknadgodziny100.getZmiennawynagrodzeniaList().add(ZmiennawynagrodzeniaBean.createPremia());
        }
        return skladniknadgodziny100;
    }
        
}
