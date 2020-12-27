/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Zmiennawynagrodzenia;

/**
 *
 * @author Osito
 */
public class ZmiennawynagrodzeniaBean {
    
    public static Zmiennawynagrodzenia zmiennawynagrodzenia;
    public static Zmiennawynagrodzenia zmiennapremia;
    
    public static Zmiennawynagrodzenia createWynagrodzenie() {
        if (zmiennawynagrodzenia==null) {
            zmiennawynagrodzenia = new Zmiennawynagrodzenia();
            zmiennawynagrodzenia.setDataod("2020-12-01");
            zmiennawynagrodzenia.setDatado("2020-12-31");
            zmiennawynagrodzenia.setNazwa("wynagrodzenie");
            zmiennawynagrodzenia.setKwota(3500.0);
            ;
        }
        return zmiennawynagrodzenia;
    }
    
    public static Zmiennawynagrodzenia createPremia() {
        if (zmiennapremia==null) {
            zmiennapremia = new Zmiennawynagrodzenia();
            zmiennapremia.setDataod("2020-12-01");
            zmiennapremia.setDatado("2020-12-31");
            zmiennapremia.setNazwa("premia uznaniowa");
            zmiennapremia.setKwota(100.0);
        }
        return zmiennapremia;
    }
}
