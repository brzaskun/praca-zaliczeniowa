/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import data.Data;
import embeddable.Parametr;
import entity.ParamSuper;
import java.util.List;

/**
 *
 * @author Osito
 */
public class ParamBean {
    
    public static String zwrocParametr(List parametry, Integer rok, Integer mc) {
        for (Object x : parametry) {
            ParamSuper p = (ParamSuper) x;
            if (p.getRokDo() != null && !"".equals(p.getRokDo())) {
                int wynikPo = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
                int wynikPrzed = Data.compare(rok, mc, Integer.parseInt(p.getRokDo()), Integer.parseInt(p.getMcDo()));
                if (wynikPo > -1 && wynikPrzed < 1) {
                    return p.getParametr();
                }
            } else {
                int wynik = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
                if (wynik >= 0) {
                    return p.getParametr();
                }
            }
        }
        return "blad";
    }
    
     public static String zwrocParametr(List parametry, Integer rok, String mcS) {
        int mc = Integer.parseInt(mcS);
        for (Object x : parametry) {
            ParamSuper p = (ParamSuper) x;
            if (p.getRokDo() != null && !"".equals(p.getRokDo())) {
                int wynikPo = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
                int wynikPrzed = Data.compare(rok, mc, Integer.parseInt(p.getRokDo()), Integer.parseInt(p.getMcDo()));
                if (wynikPo > -1 && wynikPrzed < 1) {
                    return p.getParametr();
                }
            } else {
                int wynik = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
                if (wynik >= 0) {
                    return p.getParametr();
                }
            }
        }
        return "blad";
    }
    
}
