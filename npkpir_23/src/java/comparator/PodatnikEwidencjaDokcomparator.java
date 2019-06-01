/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import embeddable.Okresrozliczeniowy;
import entity.PodatnikEwidencjaDok;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class PodatnikEwidencjaDokcomparator  implements Comparator<PodatnikEwidencjaDok>  {
    @Override
    public int compare(PodatnikEwidencjaDok obW, PodatnikEwidencjaDok obP) {
        int rokO1 = obP.getKolejnosc();
        int rokO2 = obW.getKolejnosc();
        if (rokO1 < rokO2) {
            return 1;
        } else if (rokO1 > rokO2) {
            return -1;
        } else if (rokO1 == rokO2) {
          return 0;
        }
        return 0;
    }
}
