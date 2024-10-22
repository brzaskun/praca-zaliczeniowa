/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.MultiuserSettings;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class MultiuserSettingscomparator implements Comparator<MultiuserSettings> {

    @Override
    public int compare(MultiuserSettings o1, MultiuserSettings o2) {
        String datao1 = o1.getPodatnik().getPrintnazwa()!=null? o1.getPodatnik().getPrintnazwa().toLowerCase(new Locale("pl", "PL")):"";
        String datao2 = o2.getPodatnik().getPrintnazwa()!=null? o2.getPodatnik().getPrintnazwa().toLowerCase(new Locale("pl", "PL")):"";
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(datao1, datao2);
    }
    
}
