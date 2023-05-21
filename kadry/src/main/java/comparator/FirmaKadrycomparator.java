/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.FirmaKadry;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class FirmaKadrycomparator implements Comparator<FirmaKadry> {

    @Override
    public int compare(FirmaKadry o1, FirmaKadry o2) {
        String datao1 = o1.getNazwa();
        String datao2 = o2.getNazwa();
        datao1 = datao1.replace("\"", "");
        datao2 = datao2.replace("\"", "");
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(datao1, datao2);
    }
    
}
