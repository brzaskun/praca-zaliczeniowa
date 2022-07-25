/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansPIT;

import entity.Podstawki;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named

public class WyliczPodatekZasadyOgolne implements Serializable{
    
    public static BigDecimal wyliczopodatek(Podstawki skalaPodatkowaZaDanyRok, BigDecimal dochod) {
        BigDecimal podatek = BigDecimal.ZERO;
        Double stawka;
        Double dochodDouble = dochod.doubleValue();
        if (dochodDouble < skalaPodatkowaZaDanyRok.getProg()) {
            stawka = skalaPodatkowaZaDanyRok.getStawka1();
            podatek = (dochod.multiply(BigDecimal.valueOf(stawka)));
            podatek = podatek.subtract(BigDecimal.valueOf(skalaPodatkowaZaDanyRok.getKwotawolna()));
            podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
        } else if (dochodDouble >= skalaPodatkowaZaDanyRok.getProg()) {
            stawka = skalaPodatkowaZaDanyRok.getStawka2();
            Double roznica = dochodDouble - skalaPodatkowaZaDanyRok.getProg();
            podatek = (BigDecimal.valueOf(roznica).multiply(BigDecimal.valueOf(stawka)));
            podatek = podatek.add(BigDecimal.valueOf(skalaPodatkowaZaDanyRok.getNadwyzka()));
            podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
        }
        return podatek;
    }
    
    public static BigDecimal wyliczopodateksymulacja(Podstawki skalaPodatkowaZaDanyRok, BigDecimal dochod) {
        BigDecimal podatek = BigDecimal.ZERO;
        Double stawka;
        Double dochodDouble = dochod.doubleValue();
        if (dochodDouble < skalaPodatkowaZaDanyRok.getProg()) {
            stawka = skalaPodatkowaZaDanyRok.getStawka1();
            podatek = (dochod.multiply(BigDecimal.valueOf(stawka)));
            podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
        } else if (dochodDouble >= skalaPodatkowaZaDanyRok.getProg()) {
            stawka = skalaPodatkowaZaDanyRok.getStawka2();
            Double roznica = dochodDouble - skalaPodatkowaZaDanyRok.getProg();
            podatek = (BigDecimal.valueOf(roznica).multiply(BigDecimal.valueOf(stawka)));
            podatek = podatek.add(BigDecimal.valueOf(skalaPodatkowaZaDanyRok.getNadwyzka()));
            podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
        }
        return podatek;
    }
}
