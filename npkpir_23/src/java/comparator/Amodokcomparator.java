/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Amodok;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Amodokcomparator implements Comparator<Amodok> {
    @Override
    public int compare(Amodok o1, Amodok o2) {
    int rokO1 = o1.getAmodokPK().getRok();
    int rokO2 = o2.getAmodokPK().getRok();
    int mcO1 = o1.getAmodokPK().getMc();
    int mcO2 = o2.getAmodokPK().getMc();
    if (rokO1 < rokO2) {
        return -1;
    } else if (rokO1 > rokO2){
        return 1;
    } else if (rokO1 == rokO2) {
        if (mcO1 == mcO2) {
            return 0;
        } else if (mcO1 < mcO2){
            return -1;
        } else {
            return 1;
        }
    }
    return 0;
   }
}
