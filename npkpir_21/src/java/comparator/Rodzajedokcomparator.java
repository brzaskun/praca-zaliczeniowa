/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Rodzajedok;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Rodzajedokcomparator implements Comparator<Rodzajedok> {

    @Override
    public int compare(Rodzajedok o1, Rodzajedok o2) {
        String datao1 = o1.getNazwa().toLowerCase();
        String datao2 = o2.getNazwa().toLowerCase();
        return datao1.compareTo(datao2);
    }
    
}
