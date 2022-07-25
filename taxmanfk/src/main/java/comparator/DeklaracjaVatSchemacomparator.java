/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.DeklaracjaVatSchema;
import java.util.Comparator;

/**
 *
 * @author Osito
 */
public class DeklaracjaVatSchemacomparator  implements Comparator<DeklaracjaVatSchema> {

    @Override
    public int compare(DeklaracjaVatSchema o1, DeklaracjaVatSchema o2) {
        String datao1 = o1.getNazwaschemy();
        String datao2 = o2.getNazwaschemy();
        return datao1.compareTo(datao2);
    }
    
}
