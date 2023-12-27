/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.DeklaracjaPIT11Schowek;
import entity.Kartawynagrodzen;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class DeklaracjaPIT11Schowekcomparator implements Comparator<DeklaracjaPIT11Schowek> {

    @Override
    public int compare(DeklaracjaPIT11Schowek o1, DeklaracjaPIT11Schowek o2) {
        String datao1 = o1.getPracownik().getNazwiskoImie();
        String datao2 = o2.getPracownik().getNazwiskoImie();
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(datao1, datao2);
    }
    
}
