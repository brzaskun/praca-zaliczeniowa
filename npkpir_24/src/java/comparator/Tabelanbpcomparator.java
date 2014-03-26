/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparator;

import data.Data;
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
        String datao1 = o1.getDatatabeli();
        String datao2 = o2.getDatatabeli();
        return Data.compare(datao1, datao2);
    }
}
