/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Evewidencja;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Evewidencjalpcomparator implements Comparator<Evewidencja> {

    @Override
    public int compare(Evewidencja o1, Evewidencja o2) {
        String datao1 = o1.getNrpolanetto();
        String datao2 = o2.getNrpolanetto();
        return (datao2.compareTo(datao1));
    }
    
}
