/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Pozycjenafakturze;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Pozycjenafakturzecomparator implements Comparator<Pozycjenafakturze> {
    
    @Override
    public int compare(Pozycjenafakturze o1, Pozycjenafakturze o2) {
         int datao1 = o1.getGora();
         int datao2 = o2.getGora();
        return datao1>datao2 ? 1 : (datao1==datao2 ? 0 : -1);
    }
}
