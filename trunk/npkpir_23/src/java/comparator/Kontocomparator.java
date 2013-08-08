/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entityfk.Konto;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Kontocomparator implements Comparator<Konto> {

    @Override
    public int compare(Konto o1, Konto o2) {
        String datao1 = o1.getPelnynumer();
        String datao2 = o2.getPelnynumer();
        return datao1.compareTo(datao2);
    }
    
}
