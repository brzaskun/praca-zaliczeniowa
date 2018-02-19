/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparator;

import entityfk.UkladBR;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class UkladBRcomparator  implements Comparator<UkladBR> {
    
    @Override
    public int compare(UkladBR o1, UkladBR o2) {
        Integer rok1 = Integer.parseInt(o1.getRok());
        Integer rok2 = Integer.parseInt(o2.getRok());
        return rok1 > rok2 ? -1 : (rok1 < rok2 ? 1 : 0);
    }
}
