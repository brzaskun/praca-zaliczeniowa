/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparator;

import entityfk.WierszBO;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class WierszBOcomparator  implements Comparator<WierszBO> {
    
    @Override
    public int compare(WierszBO o1, WierszBO o2) {
        Kontocomparator k = new Kontocomparator();
        return k.compare(o1.getKonto(), o2.getKonto());
    }
}
