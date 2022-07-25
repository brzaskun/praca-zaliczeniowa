/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Podatnik;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Podatnikcomparator1 implements Comparator<Podatnik> {

    @Override
    public int compare(Podatnik o1, Podatnik o2) {
        String imieinaz1 = o1.getNazwisko()+" "+o1.getImie();
        String imieinaz2 = o2.getNazwisko()+" "+o2.getImie();
        String datao1 = imieinaz1.toLowerCase(new Locale("pl", "PL"));
        String datao2 = imieinaz2.toLowerCase(new Locale("pl", "PL"));
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(datao1, datao2);
    }
    
}
