/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Skladnikwynagrodzenia;
import entity.Zmiennawynagrodzenia;

/**
 *
 * @author Osito
 */
public class ZmiennawynagrodzeniaBean {
    
    public static Zmiennawynagrodzenia zmiennawynagrodzenia;
    public static Zmiennawynagrodzenia zmiennawynagrodzenia2;
    public static Zmiennawynagrodzenia zmiennapremia;
    
    public static Zmiennawynagrodzenia createWynagrodzenie(Skladnikwynagrodzenia skladnikwynagrodzenia) {
        if (zmiennawynagrodzenia==null) {
            zmiennawynagrodzenia = new Zmiennawynagrodzenia();
            zmiennawynagrodzenia.setId(0);
            zmiennawynagrodzenia.setDataod("2020-12-01");
            zmiennawynagrodzenia.setDatado("2020-12-11");
            zmiennawynagrodzenia.setNazwa("wynagrodzenie");
            zmiennawynagrodzenia.setKwota(2800.0);
            zmiennawynagrodzenia.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
            ;
        }
        return zmiennawynagrodzenia;
    }
    public static Zmiennawynagrodzenia createWynagrodzenie2(Skladnikwynagrodzenia skladnikwynagrodzenia) {
        if (zmiennawynagrodzenia2==null) {
            zmiennawynagrodzenia2 = new Zmiennawynagrodzenia();
            zmiennawynagrodzenia2.setId(1);
            zmiennawynagrodzenia2.setDataod("2020-12-12");
            zmiennawynagrodzenia2.setDatado("2020-12-29");
            zmiennawynagrodzenia2.setNazwa("wynagrodzenie");
            zmiennawynagrodzenia2.setKwota(2800.0);
            zmiennawynagrodzenia2.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
            ;
        }
        return zmiennawynagrodzenia2;
    }
    
    public static Zmiennawynagrodzenia createPremia() {
        if (zmiennapremia==null) {
            zmiennapremia = new Zmiennawynagrodzenia();
            zmiennapremia.setDataod("2020-12-01");
            zmiennapremia.setDatado("2020-12-31");
            zmiennapremia.setNazwa("premia uznaniowa");
            zmiennapremia.setKwota(100.0);
            zmiennapremia.setSkladnikwynagrodzenia(SkladnikwynagrodzeniaBean.createPremiaUznaniowa());
        }
        return zmiennapremia;
    }
    
    public static Zmiennawynagrodzenia createNadgodziny50DB(Skladnikwynagrodzenia skladnikwynagrodzenia, String dataod, String datado) {
            Zmiennawynagrodzenia zwrot = new Zmiennawynagrodzenia();
            zwrot.setDataod(dataod);
            zwrot.setDatado(datado);
            zwrot.setNazwa("premia uznaniowa");
            zwrot.setKwota(0.0);
            zwrot.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
        return zwrot;
    }
}
