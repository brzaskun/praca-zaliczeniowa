/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Podatnik;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Podatnikcomparator implements Comparator<Podatnik> {

    @Override
    public int compare(Podatnik o1, Podatnik o2) {
        String datao1 = o1.getNazwapelna().toLowerCase();
        String datao2 = o2.getNazwapelna().toLowerCase();
        return datao1.compareTo(datao2);
    }
    
}
