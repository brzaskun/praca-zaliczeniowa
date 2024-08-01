/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparator;

import entity.Pismoadmin;
import java.util.Comparator;
import java.util.Date;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Pismoadmincomparator implements Comparator<Pismoadmin>{
    
    @Override
    public int compare(Pismoadmin o1, Pismoadmin o2) {
        Date datao1date = o1.getDatawiadomosci();;
        Date datao2date = o2.getDatawiadomosci();;
        try {
            if (datao1date.before(datao2date)) {
                return 1;
            } else if (datao1date.after(datao2date)) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }
}
