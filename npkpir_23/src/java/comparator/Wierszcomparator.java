/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparator;

import entityfk.Wiersz;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Wierszcomparator implements Comparator<Wiersz> {
    @Override
    public int compare(Wiersz o1, Wiersz o2) {
        int datao1 = o1.getIdporzadkowy();
        int datao2 = o2.getIdporzadkowy();
        return datao1 > datao2 ? 1 : datao1 == datao2 ? 0 : -1;
    }
}
