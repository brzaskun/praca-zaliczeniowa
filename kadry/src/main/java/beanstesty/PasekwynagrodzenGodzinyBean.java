/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import comparator.Dziencomparator;
import entity.Dzien;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Osito
 */
public class PasekwynagrodzenGodzinyBean {
    /**
    * Zwraca mape
    * @param  kalendarzmiesiac  kalendarzmiesiac
    * @param  kalendarzwzor kalendarzwzor
    * @return  Map<String, Double>   dniroboczenominalnewmiesiacu, godzinyroboczenominalnewmiesiacu
    */
    public static Map<String, Double> godzinynormatywne(Kalendarzmiesiac kalendarzmiesiac, Kalendarzwzor kalendarzwzor) {
        double dniroboczenominalnewmiesiacu = 0.0;
        double godzinyroboczenominalnewmiesiacu = 0.0;
        List<Dzien> biezacedni = kalendarzmiesiac.getDzienList();
        Collections.sort(biezacedni, new Dziencomparator());
        for (Dzien p : kalendarzwzor.getDzienList()) {
            if (p.getTypdnia() == 0) {
                dniroboczenominalnewmiesiacu++;
                double normagodzin = p.getNormagodzin();
                Dzien etat=  kalendarzmiesiac.getDzienList().get(p.getNrdnia()-1);
                normagodzin = normagodzin*etat.getEtat1()/etat.getEtat2();
                godzinyroboczenominalnewmiesiacu = godzinyroboczenominalnewmiesiacu + normagodzin;
            }
        }
        Map<String, Double> zwrot = new HashMap<>();
        zwrot.put("dniroboczenominalnewmiesiacu", dniroboczenominalnewmiesiacu);
        zwrot.put("godzinyroboczenominalnewmiesiacu", godzinyroboczenominalnewmiesiacu);
        return zwrot;
    }
    
}
