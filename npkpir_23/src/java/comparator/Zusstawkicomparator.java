/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparator;

import entity.Zusstawki;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Zusstawkicomparator  implements Comparator<Zusstawki> {
    @Override
    public int compare(Zusstawki obP, Zusstawki obW)  {
        int rokO1 = Integer.parseInt(obP.getZusstawkiPK().getRok());
        int rokO2 = Integer.parseInt(obW.getZusstawkiPK().getRok());
        int mcO1 = Integer.parseInt(obP.getZusstawkiPK().getMiesiac());
        int mcO2 = Integer.parseInt(obW.getZusstawkiPK().getMiesiac());
        if (rokO1 < rokO2) {
            return -1;
        } else if (rokO1 > rokO2) {
            return 1;
        } else if (rokO1 == rokO2) {
            if (mcO1 == mcO2) {
                return 0;
            } else if (mcO1 < mcO2) {
                return -1;
            } else {
                return 1;
            }
        }
        return 0;
    }
    
}
