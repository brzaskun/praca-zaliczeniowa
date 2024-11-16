/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import embeddable.PodatnikRecord;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class PodatnikRecordcomparator implements Comparator<PodatnikRecord> {

    @Override
    public int compare(PodatnikRecord o1, PodatnikRecord o2) {
        String datao1 = o1.getPodatnik()!=null? o1.getPodatnik().getPrintnazwa().toLowerCase(new Locale("pl", "PL")):"";
        String datao2 = o2.getPodatnik()!=null? o2.getPodatnik().getPrintnazwa().toLowerCase(new Locale("pl", "PL")):"";
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(datao1, datao2);
    }
    
}
