/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import java.util.Comparator;
import java.util.Date;
import kadryiplace.ZatrudHist;

/**
 *
 * @author Osito
 */
public class ZatrudHistComparator  implements Comparator<ZatrudHist> {

    @Override
    public int compare(ZatrudHist o1, ZatrudHist o2) {
        int zwrot = 0;
        Date datao1date = o1.getZahDataOd();
        Date datao2date = o2.getZahDataOd();
        try {
            zwrot = datao1date.before(datao2date) ? -1 : (datao1date.equals(datao2date) ? 0 : 1);
        } catch (Exception e){}
        return zwrot;
    }
    
    
}
