/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Dzien;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Dziencomparator implements Comparator<Dzien> {

    @Override
    public int compare(Dzien o1, Dzien o2) {
        int datao1 = o1.getNrdnia();
        int datao2 = o2.getNrdnia();
        return datao1 > datao2 ? 1 : (datao1 < datao2 ? -1 : 0);
    }
    
}
