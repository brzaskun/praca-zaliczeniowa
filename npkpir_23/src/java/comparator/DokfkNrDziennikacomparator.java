/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entityfk.Dokfk;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class DokfkNrDziennikacomparator implements Comparator<Dokfk> {

    @Override
    public int compare(Dokfk o1, Dokfk o2) {
        try {
          Integer datao1 = o1.getNrdziennika();
          Integer datao2 = o2.getNrdziennika();
           return datao1.compareTo(datao2);
        } catch (Exception e){}
        return 0;
    }
    
   
    
}
