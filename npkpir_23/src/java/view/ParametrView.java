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

/**
 *
 * @author Osito
 */
public class ParametrView implements Serializable {
     public static String zwrocParametr(List<Parametr> parametry, Integer rok, Integer mc) {
        for (Parametr p : parametry) {
            if (p.getRokDo() != null && !"".equals(p.getRokDo())) {
                int wynikPo = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
                int wynikPrzed = Data.compare(rok, mc, Integer.parseInt(p.getRokDo()), Integer.parseInt(p.getMcDo()));
                if (wynikPo > 1 && wynikPrzed < 0) {
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
