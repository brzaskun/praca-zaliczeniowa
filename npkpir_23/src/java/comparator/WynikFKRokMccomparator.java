/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entityfk.WynikFKRokMc;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class WynikFKRokMccomparator implements Comparator<WynikFKRokMc> {

    @Override
    public int compare(WynikFKRokMc o1, WynikFKRokMc o2) {
        if (o1.getMc().equals("R") || o2.getMc().equals("R")) {
            return 1;
        } else {
            int o1suma = Integer.parseInt(o1.getMc());
            int o2suma = Integer.parseInt(o2.getMc());
            return (o1suma>o2suma ? 1 : (o1suma<o2suma ? -1 : 0));
        }
    }
    
}
