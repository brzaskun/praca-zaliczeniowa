/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansSrodkiTrwale;

import embeddable.Mce;
import embeddable.Parametr;
import embeddable.Umorzenie;
import entity.Podatnik;
import entity.SrodekTrw;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import msg.Msg;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named

public class SrodkiTrwBean implements Serializable {

    public static double sumujumorzenia(List<Umorzenie> umorzenia) {
        double kwotaumorzenia = 0.0;
        Iterator it = umorzenia.iterator();
        while (it.hasNext()) {
            Umorzenie tmp = (Umorzenie) it.next();
            kwotaumorzenia += tmp.getKwota().doubleValue();
        }
        return Z.z(kwotaumorzenia);
    }

    public static List<Umorzenie> generujumorzeniadlasrodka(SrodekTrw srodek, WpisView wpisView) {
        List<Umorzenie> umorzenia = new ArrayList<>();
        if (srodek.getZlikwidowany() == 0) {
            List<Double> planowane = new ArrayList<>();
            planowane.addAll(srodek.getUmorzPlan());
            Integer rokOd = Integer.parseInt(srodek.getDataprzek().substring(0, 4));
            Integer mcOd = 0;
            if (srodek.getStawka() == 100) {
                mcOd = Integer.parseInt(srodek.getDataprzek().substring(5, 7));
            } else {
                String pob = srodek.getDataprzek().substring(5, 7);
                // bo jest od miesiaca nastepnego po miesiacu
                mcOd = Integer.parseInt(pob) + 1;
                if (mcOd == 13) {
                    rokOd++;
                    mcOd = 1;
                } else {
                    mcOd = Integer.parseInt(srodek.getDataprzek().substring(5, 7)) + 1;
                }
            }

            Iterator itX;
            itX = planowane.iterator();
            int i = 1;
            while (itX.hasNext()) {
                Integer[] mcrok = new Integer[2];
                mcrok[0] = mcOd;
                mcrok[1] = rokOd;
                danymiesiacniejestzawieszenie(mcrok, wpisView);
                mcOd = mcrok[0];
                rokOd = mcrok[1];
                Double kwotaodpisMC = (Double) itX.next();
                Umorzenie odpisZaDanyOkres = new Umorzenie();
                odpisZaDanyOkres.setKwota(BigDecimal.valueOf(kwotaodpisMC.doubleValue()));
                odpisZaDanyOkres.setRokUmorzenia(rokOd);
                odpisZaDanyOkres.setMcUmorzenia(mcOd);
                odpisZaDanyOkres.setNrUmorzenia(i);
                odpisZaDanyOkres.setNazwaSrodka(srodek.getNazwa());
                i++;
                if (mcOd == 12) {
                    rokOd++;
                    mcOd = 1;
                } else {
                    mcOd++;
                }
                umorzenia.add(odpisZaDanyOkres);
            }
        }
        return umorzenia;
    }
    private static void danymiesiacniejestzawieszenie(Integer[] mcrok, WpisView wpisView) {
        Integer badanymiesiac = mcrok[0];
        Integer badanyrok = mcrok[1];
        Podatnik pod = wpisView.getPodatnikObiekt();
        List<Parametr> listaparametrow = new ArrayList<>();
        if (pod.getZawieszeniedzialalnosci() != null) {
            listaparametrow.addAll(pod.getZawieszeniedzialalnosci());
            Iterator it = listaparametrow.iterator();
            while (it.hasNext()) {
                Parametr par = (Parametr) it.next();
                if (!par.getRokOd().equals(wpisView.getRokWpisuSt())) {
                    it.remove();
                }
            }
            if (listaparametrow.size() > 0) {
                List<String> miesiacezawieszeniawroku = new ArrayList<>();
                for (Parametr s : listaparametrow) {
                    try {
                        miesiacezawieszeniawroku.addAll(Mce.zakresmiesiecy(s.getMcOd(), s.getMcDo()));
                    } catch (Exception e) {
                       Msg.msg("e", "Miesiąc Od jest późniejszy od miesiąca Do!");
                    }
                }
                String ostatnimiesiaczlisty = miesiacezawieszeniawroku.get(miesiacezawieszeniawroku.size() - 1);
                if (miesiacezawieszeniawroku.contains(Mce.getNumberToMiesiac().get(badanymiesiac))) {
                    if (ostatnimiesiaczlisty.equals("12")) {
                        mcrok[0] = 1;
                        mcrok[1] += 1;
                    } else {
                        int ostatnimiesiacint = Mce.getMiesiacToNumber().get(ostatnimiesiaczlisty) + 1;
                        mcrok[0] = ostatnimiesiacint;
                    }
                }
            }
        }
    }
}
