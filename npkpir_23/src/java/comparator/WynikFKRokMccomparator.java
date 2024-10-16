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
        int rokCompare = o1.getRok().compareTo(o2.getRok());
        
        // Jeśli lata są różne, zwróć wynik porównania lat
        if (rokCompare != 0) {
            return rokCompare;
        }
        
        // Jeśli lata są takie same, porównaj miesiące
        return o1.getMc().compareTo(o2.getMc());
        }
    }
    

