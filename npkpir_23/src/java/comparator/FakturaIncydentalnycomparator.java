/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Faktura;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class FakturaIncydentalnycomparator implements Comparator<Faktura> {

    @Override
    public int compare(Faktura f1, Faktura f2) {
        if (f1 == null || f2 == null) {
            throw new IllegalArgumentException("Obiekty Faktura nie mogą być null");
        }

        // Porównaj nazwiskoimieincydentalny
        int result = compareNullableStrings(f1.getNazwiskoimieincydent(), f2.getNazwiskoimieincydent());
        if (result != 0) {
            return result;
        }

        // Porównaj adres1
        result = compareNullableStrings(f1.getAdres1(), f2.getAdres1());
        if (result != 0) {
            return result;
        }

        // Porównaj adres2
        return compareNullableStrings(f1.getAdres2(), f2.getAdres2());
    }

    private int compareNullableStrings(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return 0;
        }
        if (s1 == null) {
            return -1;
        }
        if (s2 == null) {
            return 1;
        }
        return s1.compareTo(s2);
    }
    
}
