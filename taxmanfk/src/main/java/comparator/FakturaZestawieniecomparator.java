/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import embeddable.FakturaZestawienie;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class FakturaZestawieniecomparator implements Comparator<FakturaZestawienie> {

    @Override
    public int compare(FakturaZestawienie o1, FakturaZestawienie o2) {
        String datao1 = o1.getPodatnik()!=null ? o1.getPodatnik().getPrintnazwa() : o1.getKontrahent().getNpelna();
        String datao2 = o2.getPodatnik()!=null ? o2.getPodatnik().getPrintnazwa() : o2.getKontrahent().getNpelna();
        return datao1.compareTo(datao2);
    }
    
}
