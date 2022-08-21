/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Podmiot;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Podmiotcomparator implements Comparator<Podmiot> {

    @Override
    public int compare(Podmiot o1, Podmiot o2) {
        String datao1 = o1.getNazwanazwisko().toLowerCase(new Locale("pl", "PL"));
        String datao2 = o2.getNazwanazwisko().toLowerCase(new Locale("pl", "PL"));
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(datao1, datao2);
    }
    
}
