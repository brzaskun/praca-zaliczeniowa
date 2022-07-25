/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Klienci;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Klienci1comparator implements Comparator<Klienci> {

    @Override
    public int compare(Klienci o1, Klienci o2) {
        String datao1 = o1.getNazwapodatnika().toLowerCase().replace("\"", "");
        String datao2 = o2.getNazwapodatnika().toLowerCase().replace("\"", "");
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(datao1, datao2);
    }
    
}
