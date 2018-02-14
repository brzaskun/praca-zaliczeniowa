/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparator;

import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class PozycjaRZiScomparator  implements Comparator<PozycjaRZiS> {
    
    @Override
    public int compare(PozycjaRZiS o1, PozycjaRZiS o2) {
        return o1.getPozycjaString().compareTo(o2.getPozycjaString());
    }
}
