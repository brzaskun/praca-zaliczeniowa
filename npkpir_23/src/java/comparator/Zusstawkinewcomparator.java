/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparator;

import entity.Zusstawkinew;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Zusstawkinewcomparator  implements Comparator<Zusstawkinew> {
    @Override
    public int compare(Zusstawkinew obP, Zusstawkinew obW)  {
        int rokO1 = Integer.parseInt(obP.getRok());
        int rokO2 = Integer.parseInt(obW.getRok());
        int mcO1 = Integer.parseInt(obP.getMiesiac());
        int mcO2 = Integer.parseInt(obW.getMiesiac());
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
