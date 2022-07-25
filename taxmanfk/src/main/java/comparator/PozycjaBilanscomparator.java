/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparator;

import entityfk.PozycjaBilans;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class PozycjaBilanscomparator  implements Comparator<PozycjaBilans> {
    
    @Override
    public int compare(PozycjaBilans o1, PozycjaBilans o2) {
        return o1.getPozycjaString().compareTo(o2.getPozycjaString());
    }
}
