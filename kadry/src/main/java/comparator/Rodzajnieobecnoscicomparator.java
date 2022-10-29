/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Rodzajnieobecnosci;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Rodzajnieobecnoscicomparator implements Comparator<Rodzajnieobecnosci> {

    @Override
    public int compare(Rodzajnieobecnosci o1, Rodzajnieobecnosci o2) {
        int datao1 = o1.getKolejnosc();
        int datao2 = o2.getKolejnosc();
        return datao1 < datao2 ? -1 : datao1 == datao2 ? 0 : 1;
    }
    
}
