/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Constants;
import entity.Podatnik;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Podatnikcomparator implements Comparator<Podatnik> {

    @Override
    public int compare(Podatnik o1, Podatnik o2) {
        String datao1 = o1.getNazwapelna().toLowerCase(new Locale("pl", "PL"));
        String datao2 = o2.getNazwapelna().toLowerCase(new Locale("pl", "PL"));
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(datao1, datao2);
    }
    
}
