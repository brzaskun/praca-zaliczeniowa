/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entityfk.Kontozapisy;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Kontozapisycomparator implements Comparator<Kontozapisy>  {
    
    @Override
    public int compare(Kontozapisy o1, Kontozapisy o2) {
        int datao1 = o1.getWiersz().getIdwiersza();
        int datao2 = o2.getWiersz().getIdwiersza();
        return datao1 < datao2 ? -1 : datao1 == datao2 ? 0 : 1;
    }
    
}
