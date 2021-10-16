/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Kalendarzmiesiac;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class KalendarzmiesiacLastcomparator implements Comparator<Kalendarzmiesiac> {

    @Override
    public int compare(Kalendarzmiesiac o1, Kalendarzmiesiac o2) {
        int datao1 = Integer.parseInt(o1.getRokMc());
        int datao2 = Integer.parseInt(o2.getRokMc());
        return datao1 > datao2 ? -1 : (datao1 < datao2 ? 1 : 0);
    }
    
}
