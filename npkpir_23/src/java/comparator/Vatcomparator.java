/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Deklaracjevat;
import java.util.Comparator;
import java.util.Date;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Vatcomparator implements Comparator<Deklaracjevat> {

    @Override
    public int compare(Deklaracjevat o1, Deklaracjevat o2) {
        int o1suma = Integer.parseInt(o1.getRok())+Integer.parseInt(o1.getMiesiac());
        int o2suma = Integer.parseInt(o2.getRok())+Integer.parseInt(o2.getMiesiac());
        Date datao1date = o1.getDatazlozenia();
        Date datao2date = o2.getDatazlozenia();
        return (o1suma>o2suma ? -1 : (o1suma<o2suma ? 1 : (datao1date.before(datao2date) ? 1 : (datao1date.equals(datao2date) ? 0 : -1))));
    }
    
}
