/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparator;

import entity.Uz;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Uzcomparator  implements Comparator<Uz> {
    
    @Override
    public int compare(Uz o1, Uz o2) {
        return o1.getLogin().compareTo(o2.getLogin());
    }
}
