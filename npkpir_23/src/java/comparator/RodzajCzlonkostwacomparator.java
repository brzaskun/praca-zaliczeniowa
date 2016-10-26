/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Klienci;
import entityfk.RodzajCzlonkostwa;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class RodzajCzlonkostwacomparator implements Comparator<RodzajCzlonkostwa> {

    @Override
    public int compare(RodzajCzlonkostwa o1, RodzajCzlonkostwa o2) {
        String datao1 = o1.getNazwa().toLowerCase();
        String datao2 = o2.getNazwa().toLowerCase();
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(datao1, datao2);
    }
    
}
