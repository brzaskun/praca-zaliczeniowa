/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparator;

import entity.WierszDRA;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class WierszDRAcomparator implements Comparator<WierszDRA>{
    @Override
    public int compare(WierszDRA o1, WierszDRA o2) {
        String imieinaz1 = o1.getImienazwisko();
        String imieinaz2 = o2.getImienazwisko();
        String datao1 = imieinaz1.toLowerCase(new Locale("pl", "PL"));
        String datao2 = imieinaz2.toLowerCase(new Locale("pl", "PL"));
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(datao1, datao2);
    }
}
