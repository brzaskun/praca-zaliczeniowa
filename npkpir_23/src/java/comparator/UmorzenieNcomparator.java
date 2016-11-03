/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparator;

import entity.UmorzenieN;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class UmorzenieNcomparator  implements Comparator<UmorzenieN> {
    
    @Override
    public int compare(UmorzenieN o1, UmorzenieN o2) {
        Integer rok1 = o2.getNrUmorzenia();
        Integer rok2 = o1.getNrUmorzenia();
        return rok1 > rok2 ? -1 : (rok1 < rok2 ? 1 : 0);
    }
}
