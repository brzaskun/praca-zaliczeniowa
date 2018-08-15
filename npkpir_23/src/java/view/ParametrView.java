/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import data.Data;
import embeddable.Parametr;
import entity.ParamSuper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
     public static String zwrocParametr(List parametry, Integer rok, Integer mc) {
        String zwrot = "blad";
        if (parametry != null) {
            for (Object x : parametry) {
                ParamSuper p = (ParamSuper) x;
                if (p.getRokDo() != null && !"".equals(p.getRokDo())) {
                    int wynikPo = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
                    int wynikPrzed = Data.compare(rok, mc, Integer.parseInt(p.getRokDo()), Integer.parseInt(p.getMcDo()));
                    if (wynikPo > -1 && wynikPrzed < 1) {
                        zwrot = p.getParametr();
                    }
                } else {
                    int wynik = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
                    if (wynik >= 0) {
                        zwrot = p.getParametr();
                    }
                }
            }
        }
        return zwrot;
    }
     
     public static String zwrocParametr(List parametry, Integer rok, String mcS) {
        String zwrot = "blad";
        if (parametry != null) {
            int mc = Integer.parseInt(mcS);
            for (Object x : parametry) {
                ParamSuper p = (ParamSuper) x;
                if (p.getRokDo() != null && !"".equals(p.getRokDo())) {
                    int wynikPo = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
                    int wynikPrzed = Data.compare(rok, mc, Integer.parseInt(p.getRokDo()), Integer.parseInt(p.getMcDo()));
                    if (wynikPo > -1 && wynikPrzed < 1) {
                        zwrot = p.getParametr();
                    }
                } else {
                    int wynik = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
                    if (wynik >= 0) {
                        zwrot = p.getParametr();
                    }
                }
            }
        }
        return zwrot;
    }
     
    public static int sprawdzrok(ParamSuper parametr, List stare) {
        if (stare.isEmpty()) {
            parametr.setMcOd("01");
            parametr.setMcDo("12");
            parametr.setRokDo(parametr.getRokOd());
            return 0;
        } else {
            ParamSuper ostatniparametr = (ParamSuper) stare.get(stare.size() - 1);
            Integer old_rokDo = Integer.parseInt(ostatniparametr.getRokDo());
            Integer new_rokOd = Integer.parseInt(parametr.getRokOd());
            if (old_rokDo == new_rokOd - 1) {
                parametr.setMcOd("01");
                parametr.setMcDo("12");
                parametr.setRokDo(parametr.getRokOd());
                return 0;
            } else {
                return 1;
            }
        }
    }
    public static void main(String[] args) {
        List<Parametr> lista = Collections.synchronizedList(new ArrayList<>());
        lista.add(new Parametr("01","2017","12","2017","miesiecznie"));
        lista.add(new Parametr("01","2016","12","2016","kwartalnie"));
        String param = zwrocParametr(lista, 2016, "12");
        System.out.println("param "+param);
        param = zwrocParametr(lista, 2017, "01");
        System.out.println("param "+param);
        param = zwrocParametr(lista, 2016, 12);
        System.out.println("param2 "+param);
        param = zwrocParametr(lista, 2016, 1);
        System.out.println("param2 "+param);
        param = zwrocParametr(lista, 2017, 1);
    }
}
