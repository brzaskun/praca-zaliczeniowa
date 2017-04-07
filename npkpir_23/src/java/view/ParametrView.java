/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import data.Data;
import embeddable.Parametr;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named

public class ParametrView implements Serializable {
    /**
     * 
     * @param parametry lista paramentów z której bedzie łuskane
     * @param rok rok okresu
     * @param mc miesiąc luskane
     * @return String dany parameter
     */
     public static String zwrocParametr(List<Parametr> parametry, Integer rok, Integer mc) {
        for (Parametr p : parametry) {
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
     
     public static String zwrocParametr(List<Parametr> parametry, Integer rok, String mcS) {
        int mc = Integer.parseInt(mcS);
        for (Parametr p : parametry) {
            if (p.getRokDo() != null && !"".equals(p.getRokDo())) {
                int wynikPo = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
                int wynikPrzed = Data.compare(Integer.parseInt(p.getRokDo()), Integer.parseInt(p.getMcDo()), rok, mc);
                if (wynikPo > -1 && wynikPrzed < 1) {
                    return p.getParametr();
                }
            } else {
                int wynik = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
                if (wynik > 0) {
                    return p.getParametr();
                }
            }
        }
        return "blad";
    }
}
