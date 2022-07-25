/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparator;

import entityfk.UkladBR;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class UkladBRNamecomparator  implements Comparator<UkladBR> {
    
    @Override
    public int compare(UkladBR o1, UkladBR o2) {
        String datao1 = o1.getPodatnik().getPrintnazwa().toLowerCase(new Locale("pl", "PL"));
        String datao2 = o2.getPodatnik().getPrintnazwa().toLowerCase(new Locale("pl", "PL"));
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(datao1, datao2);
        
    }
}
