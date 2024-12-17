/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Dok;
import java.util.Comparator;
import java.util.Date;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Dokcomparator implements Comparator<Dok> {

    @Override
    public int compare(Dok o1, Dok o2) {
        int zwrot = 0;
       Date datao1date = o1.getDataK();
        Date datao2date = o2.getDataK();
        if (datao1date!=null&&datao2date!=null) {
            zwrot = (datao1date.before(datao2date) ? -1 : (datao1date.equals(datao2date) ? 0 : 1));
        }
        return zwrot;
    }
    
}
