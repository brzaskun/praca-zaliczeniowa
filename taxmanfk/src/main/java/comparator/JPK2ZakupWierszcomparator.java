/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import java.util.Comparator;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class JPK2ZakupWierszcomparator implements Comparator<jpk201701.JPK.ZakupWiersz> {

    @Override
    public int compare(jpk201701.JPK.ZakupWiersz o1, jpk201701.JPK.ZakupWiersz o2) {
        String datao1 = o1.getNazwaDostawcy().toLowerCase(new Locale("pl", "PL"));
        String datao2 = o2.getNazwaDostawcy().toLowerCase(new Locale("pl", "PL"));
        return datao1.compareTo(datao2);
   }
    
}
