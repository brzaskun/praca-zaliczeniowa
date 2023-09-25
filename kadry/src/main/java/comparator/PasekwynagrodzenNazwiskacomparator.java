/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Pasekwynagrodzen;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class PasekwynagrodzenNazwiskacomparator implements Comparator<Pasekwynagrodzen>  {
       
    @Override
    public int compare(Pasekwynagrodzen obW, Pasekwynagrodzen obP) {
        int zwrot = 0;
        try {
            String datao1 = obW.getNazwiskoImie();
            String datao2 = obW.getNazwiskoImie();
            Collator collator = Collator.getInstance(new Locale("pl", "PL"));
            collator.setStrength(Collator.PRIMARY);
            zwrot = collator.compare(datao1, datao2);
        } catch (Exception e){}
        return zwrot;
    }
}
