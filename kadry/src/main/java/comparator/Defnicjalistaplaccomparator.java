/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Definicjalistaplac;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Defnicjalistaplaccomparator implements Comparator<Definicjalistaplac> {

    @Override
    public int compare(Definicjalistaplac o1, Definicjalistaplac o2) {
        String datao1 = o1.getMc();
        String datao2 = o2.getMc();
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(datao1, datao2);
    }
    
}
