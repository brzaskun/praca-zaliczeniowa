/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparator;

import entity.Strata;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Stratacomparator implements Comparator<Strata>{
    @Override
    public int compare(Strata o1, Strata o2) {
        int lp1 = o1.getRok();
        int lp2 = o2.getRok();
        int zwrot = lp1 == lp2 ? 0 : lp1 > lp2 ? 1 : -1;;
        return zwrot;
    }
}
