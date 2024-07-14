/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparator;

import entityfk.PozycjaRZiSBilans;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class PozycjaRZiSBilanscomparator  implements Comparator<PozycjaRZiSBilans> {
    
    @Override
    public int compare(PozycjaRZiSBilans o1, PozycjaRZiSBilans o2) {
        return o1.getPozycjaString().compareTo(o2.getPozycjaString());
    }
}
