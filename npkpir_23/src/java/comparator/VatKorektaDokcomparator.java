/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparator;

import embeddable.VatKorektaDok;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class VatKorektaDokcomparator  implements Comparator<VatKorektaDok> {

    @Override
    public int compare(VatKorektaDok o1, VatKorektaDok o2) {
        int datao1 = o1.getId();
        int datao2 = o2.getId();
        return datao1 < datao2 ? 1 : datao1 == datao2 ? 0 : -1;
    
    }
}
