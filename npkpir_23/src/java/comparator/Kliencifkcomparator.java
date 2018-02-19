/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entityfk.Kliencifk;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Kliencifkcomparator implements Comparator<Kliencifk> {

    @Override
    public int compare(Kliencifk o1, Kliencifk o2) {
        Integer datao1 = Integer.parseInt(o1.getNrkonta());
        Integer datao2 = Integer.parseInt(o2.getNrkonta());
        return datao1.compareTo(datao2);
    }
    
}
