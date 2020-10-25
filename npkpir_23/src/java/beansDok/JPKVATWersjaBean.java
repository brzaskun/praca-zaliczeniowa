/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansDok;

import data.Data;
import entity.JPKVATWersja;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @author Osito
 */

public class JPKVATWersjaBean implements Serializable{

    public static int sprawdzScheme(JPKVATWersja jPKVATWersja, List schemyDeklaracjiVat) {
        int jestjuztaka = 0;
        if (jPKVATWersja != null) {
                jestjuztaka = sprawdznazwe(jPKVATWersja, schemyDeklaracjiVat);
            if (jestjuztaka == 0) {
                jestjuztaka = sprawdzokres(jPKVATWersja, schemyDeklaracjiVat);
            }
            if (jestjuztaka == 0) {
                jestjuztaka = sprawdznazwe(jPKVATWersja);
            }
        }
        return jestjuztaka;
    }

    private static int sprawdznazwe(JPKVATWersja jPKVATWersja, List schemyDeklaracjiVat) {
        int jesttakanazwa = 0;
        for (Iterator<JPKVATWersja> it = schemyDeklaracjiVat.iterator(); it.hasNext();) {
                JPKVATWersja p = it.next();
                if (jPKVATWersja.getNazwa().equals(p.getNazwa())) {
                    jesttakanazwa = 1;
                    break;
                }
        }
        return jesttakanazwa;
    }

    private static int sprawdzokres(JPKVATWersja jPKVATWersja, List schemyDeklaracjiVat) {
        int jesttakiokres = 0;
        for (Iterator<JPKVATWersja> it = schemyDeklaracjiVat.iterator(); it.hasNext();) {
            JPKVATWersja p = it.next();
            if (jPKVATWersja.isMc0kw1() == p.isMc0kw1()) {
                int wynik = Data.compare(jPKVATWersja.getRokOd(), jPKVATWersja.getMcOd(), p.getRokOd(), p.getMcOd());
                if (wynik < 1) {
                    jesttakiokres = 2;
                }
            }
        }
        return jesttakiokres;
    }

    private static int sprawdznazwe(JPKVATWersja jPKVATWersja) {
        int jesttakiokres = 0;
            if (jPKVATWersja.getNazwa().isEmpty()) {
                jesttakiokres = 3;
            } else if (!jPKVATWersja.getNazwa().startsWith("JPK")) {
                jesttakiokres = 4;
            }
        return jesttakiokres;
    }
    
    public static JPKVATWersja pobierzJPKswersja(String mcwpisu, String rokwpisu, List<JPKVATWersja> listajpk) {
        JPKVATWersja zwrot = null;
        for (Iterator<JPKVATWersja> it = listajpk.iterator(); it.hasNext();) {
            JPKVATWersja p = it.next();
            int wynik = Data.compare(rokwpisu, mcwpisu, p.getRokOd(), p.getMcOd());
            if (wynik > 1) {
                zwrot = p;
            }
        }
        return zwrot;
    }
}
