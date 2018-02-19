/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansPIT;

import embeddable.Parametr;
import entity.Podatnik;
import java.util.List;
import javax.ejb.Stateless;
import view.WpisView;

/**
 *
 * @author Osito
 */

public class RoznicaRemanentowBean {
    
    public static double obliczrozniceremanentow(WpisView wpisView) {
        double remanentPoczRoku = 0;
        double remanentKoniecRoku = 0;
        double roznica = 0;
        try {
            Podatnik pod = wpisView.getPodatnikObiekt();
            Integer rok = wpisView.getRokWpisu();
            Integer rokNext = wpisView.getRokWpisu()+1;
            try {
                List<Parametr> remanentLista = pod.getRemanent();
                if (remanentLista.isEmpty()) {
                     return 0.0;
                } else {
                    Parametr tmp = zwrocparametrzRoku(remanentLista, rok);
                    if (tmp instanceof Parametr) {
                        remanentPoczRoku = Double.valueOf(tmp.getParametr());
                    } else {
                        return 0.0;
                    }
                    tmp = zwrocparametrzRoku(remanentLista, rokNext);
                    if (tmp instanceof Parametr) {
                        remanentKoniecRoku = Double.valueOf(tmp.getParametr());
                        roznica = remanentPoczRoku - remanentKoniecRoku;
                    } else {
                        return 0.0;
                    }
                    //remnierem = "Wartość ostatniego remanentu za " + tmp.getRokOd() + " wynosi: " + tmp.getParametr();
                }
            } catch (Exception e) {
                    return 0.0;
            }
        } catch (Exception ex) {
            return 0.0;
        }
        return roznica;
    }
    
     private static Parametr zwrocparametrzRoku(List<Parametr> lista, Integer szukanyRok) {
        String rokStr = String.valueOf(szukanyRok);
        for (Parametr p : lista) {
            if (p.getRokOd().equals(rokStr)) {
                return p;
            }
        }
        return null;
    }
}
