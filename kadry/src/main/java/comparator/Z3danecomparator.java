/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import embeddable.Z3dane;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Z3danecomparator implements Comparator<Z3dane>  {
       
    @Override
    public int compare(Z3dane obW, Z3dane obP) {
        int rokO1 = Integer.parseInt(obP.getRok());
        int rokO2 = Integer.parseInt(obW.getRok());
        int mcO1 = Integer.parseInt(obP.getMc());
        int mcO2 = Integer.parseInt(obW.getMc());
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
