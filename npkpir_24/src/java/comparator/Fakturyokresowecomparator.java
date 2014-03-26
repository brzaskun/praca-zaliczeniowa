/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Fakturywystokresowe;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Fakturyokresowecomparator  implements Comparator<Fakturywystokresowe>{
    @Override
    //reverse mode
    public int compare(Fakturywystokresowe o1, Fakturywystokresowe o2) {
        int datao1 = Integer.parseInt(o1.getRok());
        int datao2 = Integer.parseInt(o2.getRok());
        return datao1 < datao2 ? 1 : datao1 == datao2 ? 0 : -1;
    }
}
