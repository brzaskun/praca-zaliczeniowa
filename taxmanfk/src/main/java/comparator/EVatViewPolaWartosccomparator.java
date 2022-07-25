/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;


import entity.EVatwpisSuper;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class EVatViewPolaWartosccomparator implements Comparator<EVatwpisSuper> {

    @Override
    public int compare(EVatwpisSuper o1, EVatwpisSuper o2) {
        double datao1 = o1.getVat();
        double datao2 = o2.getVat();
        return datao1 < datao2 ? -1 : datao1 == datao2 ? 0 : 1;
    }
    
}
