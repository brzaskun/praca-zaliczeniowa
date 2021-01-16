/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Kalendarzmiesiac;
import entity.Rodzajwynagrodzenia;

/**
 *
 * @author Osito
 */
public class RodzajwynagrodzeniaBean {
    
    public static Rodzajwynagrodzenia rodzajwynagrodzenia;
    public static Rodzajwynagrodzenia skladnikpremiauznaniowa;
    public static Rodzajwynagrodzenia rodzajnadgodziny50;
        public static Rodzajwynagrodzenia rodzajnadgodziny100;
    
    public static Rodzajwynagrodzenia createWynagrodzenie() {
        if (rodzajwynagrodzenia==null) {
            rodzajwynagrodzenia = new Rodzajwynagrodzenia();
            rodzajwynagrodzenia.setGodzinowe0miesieczne1(Boolean.TRUE);
            rodzajwynagrodzenia.setStale0zmienne1(Boolean.FALSE);
            rodzajwynagrodzenia.setRedukowany(Boolean.TRUE);
            rodzajwynagrodzenia.setKod("10");
        }
        return rodzajwynagrodzenia;
    }
    
    public static Rodzajwynagrodzenia createPremiaUznaniowa() {
        if (skladnikpremiauznaniowa==null) {
            skladnikpremiauznaniowa = new Rodzajwynagrodzenia();
            skladnikpremiauznaniowa.setGodzinowe0miesieczne1(Boolean.TRUE);
            skladnikpremiauznaniowa.setStale0zmienne1(Boolean.TRUE);
            skladnikpremiauznaniowa.setRedukowany(Boolean.FALSE);
            skladnikpremiauznaniowa.setKod("20");
        }
        return skladnikpremiauznaniowa;
    }
    
    public static Rodzajwynagrodzenia createNadgodziny50() {
        if (rodzajnadgodziny50==null) {
            rodzajnadgodziny50 = new Rodzajwynagrodzenia();
            rodzajnadgodziny50.setGodzinowe0miesieczne1(Boolean.FALSE);
            rodzajnadgodziny50.setStale0zmienne1(Boolean.TRUE);
            rodzajnadgodziny50.setRedukowany(Boolean.FALSE);
            rodzajnadgodziny50.setKod("30");
        }
        return rodzajnadgodziny50;
    }
    
    public static Rodzajwynagrodzenia createNadgodziny50DB(Kalendarzmiesiac kalendarz, String dataod, String datado) {
            Rodzajwynagrodzenia zwrot = new Rodzajwynagrodzenia();
            zwrot.setGodzinowe0miesieczne1(Boolean.FALSE);
            zwrot.setStale0zmienne1(Boolean.TRUE);
            zwrot.setRedukowany(Boolean.FALSE);
            zwrot.setKod("30");
            return zwrot;
    }
    
    
    public static Rodzajwynagrodzenia createNadgodziny100() {
        if (rodzajnadgodziny100==null) {
            rodzajnadgodziny100 = new Rodzajwynagrodzenia();
            rodzajnadgodziny100.setGodzinowe0miesieczne1(Boolean.FALSE);
            rodzajnadgodziny100.setStale0zmienne1(Boolean.TRUE);
            rodzajnadgodziny100.setRedukowany(Boolean.FALSE);
            rodzajnadgodziny100.setKod("31");
        }
        return rodzajnadgodziny100;
    }
        
}
