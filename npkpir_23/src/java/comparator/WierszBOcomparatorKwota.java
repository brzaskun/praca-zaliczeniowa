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
public class WierszBOcomparatorKwota  implements Comparator<WierszBO> {
    
    @Override
    public int compare(WierszBO o1, WierszBO o2) {
        double datao1 = o1.getKwotaWn() != 0.0 ? o1.getKwotaWn() : o1.getKwotaMa();
        double datao2 = o2.getKwotaWn() != 0.0 ? o2.getKwotaWn() : o2.getKwotaMa();
        return datao1 < datao2 ? -1 : datao1 == datao2 ? 0 : 1;
    }
}
