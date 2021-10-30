/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.PITPola;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class PITPolacomparator implements Comparator<PITPola> {

    @Override
    public int compare(PITPola o1, PITPola o2) {
        String datao1 = o1.getPracownik().getNazwiskoImie();
        String datao2 = o2.getPracownik().getNazwiskoImie();
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(datao1, datao2);
    }
    
}
