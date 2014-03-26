/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Klienci;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Kliencicomparator implements Comparator<Klienci> {

    @Override
    public int compare(Klienci o1, Klienci o2) {
        String datao1 = o1.getNpelna().toLowerCase();
        String datao2 = o2.getNpelna().toLowerCase();
        return datao1.compareTo(datao2);
    }
    
}
