/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Wierszfakturybaza;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Wierszfakturybazacomparator implements Comparator<Wierszfakturybaza> {

    @Override
    public int compare(Wierszfakturybaza o1, Wierszfakturybaza o2) {
        String datao1 = o1.getNazwa()!=null? o1.getNazwa().toLowerCase(new Locale("pl", "PL")):"";
        String datao2 = o2.getNazwa()!=null? o2.getNazwa().toLowerCase(new Locale("pl", "PL")):"";
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(datao1, datao2);
    }
    
}
