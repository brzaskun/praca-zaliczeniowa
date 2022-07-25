/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entityfk.MiejscePrzychodow;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class MiejscePrzychodowcomparator implements Comparator<MiejscePrzychodow> {

    @Override
    public int compare(MiejscePrzychodow o1, MiejscePrzychodow o2) {
        String datao1 = o1.getOpismiejsca().toLowerCase(new Locale("pl", "PL"));
        String datao2 = o2.getOpismiejsca().toLowerCase(new Locale("pl", "PL"));
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(datao1, datao2);
    }
    
}
