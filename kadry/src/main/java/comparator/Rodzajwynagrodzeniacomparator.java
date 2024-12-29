/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Rodzajwynagrodzenia;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Rodzajwynagrodzeniacomparator implements Comparator<Rodzajwynagrodzenia> {

    @Override
    public int compare(Rodzajwynagrodzenia o1, Rodzajwynagrodzenia o2) {
        String datao1 = o1.getOpispelny().toLowerCase(new Locale("pl", "PL"));
        String datao2 = o2.getOpispelny().toLowerCase(new Locale("pl", "PL"));
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(datao1, datao2);
    }
    
}
