/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparator;

import entityfk.Tabelanbp;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Tabelanbpcomparator implements Comparator<Tabelanbp>{
    
    @Override
    public int compare(Tabelanbp o1, Tabelanbp o2) {
        int roktabeli1 = Integer.parseInt(o1.getDatatabeli().substring(0,4));
        int roktabeli2 = Integer.parseInt(o2.getDatatabeli().substring(0,4));
        if (roktabeli1 < roktabeli2) {
            return - 1;
        } else if (roktabeli1 > roktabeli2){
            return 1;
        } else {
            int nrtabeli1 = Integer.parseInt(o1.getNrtabeli().substring(0,3));
            int nrtabeli2 = Integer.parseInt(o2.getNrtabeli().substring(0,3));
            return nrtabeli1 - nrtabeli2;
        }
    }
}
