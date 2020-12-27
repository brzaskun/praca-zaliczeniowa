/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

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
            skladnikwynagrodzenia.setNazwa("wynagrodzenie zasadnicze");
            skladnikwynagrodzenia.setUmowa(UmowaBean.create());
            skladnikwynagrodzenia.setZmiennawynagrodzenia(ZmiennawynagrodzeniaBean.createWynagrodzenie());
            skladnikwynagrodzenia.setGodzinowe0miesieczne1(Boolean.TRUE);
            skladnikwynagrodzenia.setStala0zmienna1(Boolean.FALSE);
            skladnikwynagrodzenia.setRedukowanyzaczasnieobecnosci(Boolean.TRUE);
            skladnikwynagrodzenia.setKodzmiennawynagrodzenia("10");
        }
        return skladnikwynagrodzenia;
    }
    
    public static Skladnikwynagrodzenia createPremiaUznaniowa() {
        if (skladnikpremiauznaniowa==null) {
            skladnikpremiauznaniowa = new Skladnikwynagrodzenia();
            skladnikpremiauznaniowa.setNazwa("premia uznaniowa");
            skladnikpremiauznaniowa.setUmowa(UmowaBean.create());
            skladnikpremiauznaniowa.setZmiennawynagrodzenia(ZmiennawynagrodzeniaBean.createPremia());
            skladnikpremiauznaniowa.setGodzinowe0miesieczne1(Boolean.TRUE);
            skladnikpremiauznaniowa.setStala0zmienna1(Boolean.TRUE);
            skladnikpremiauznaniowa.setRedukowanyzaczasnieobecnosci(Boolean.FALSE);
            skladnikpremiauznaniowa.setKodzmiennawynagrodzenia("20");
        }
        return skladnikpremiauznaniowa;
    }
    
    public static Skladnikwynagrodzenia createNadgodziny50() {
        if (skladniknadgodziny50==null) {
            skladniknadgodziny50 = new Skladnikwynagrodzenia();
            skladniknadgodziny50.setNazwa("nadgodziny 50%");
            skladniknadgodziny50.setUmowa(UmowaBean.create());
            skladniknadgodziny50.setZmiennawynagrodzenia(null);
            skladniknadgodziny50.setGodzinowe0miesieczne1(Boolean.FALSE);
            skladniknadgodziny50.setStala0zmienna1(Boolean.TRUE);
            skladniknadgodziny50.setRedukowanyzaczasnieobecnosci(Boolean.FALSE);
            skladniknadgodziny50.setKodzmiennawynagrodzenia("30");
        }
        return skladniknadgodziny50;
    }
    public static Skladnikwynagrodzenia createNadgodziny100() {
        if (skladniknadgodziny100==null) {
            skladniknadgodziny100 = new Skladnikwynagrodzenia();
            skladniknadgodziny100.setNazwa("nadgodziny 100%");
            skladniknadgodziny100.setUmowa(UmowaBean.create());
            skladniknadgodziny100.setZmiennawynagrodzenia(null);
            skladniknadgodziny100.setGodzinowe0miesieczne1(Boolean.FALSE);
            skladniknadgodziny100.setStala0zmienna1(Boolean.TRUE);
            skladniknadgodziny100.setRedukowanyzaczasnieobecnosci(Boolean.FALSE);
            skladniknadgodziny100.setKodzmiennawynagrodzenia("31");
        }
        return skladniknadgodziny100;
    }
        
}
