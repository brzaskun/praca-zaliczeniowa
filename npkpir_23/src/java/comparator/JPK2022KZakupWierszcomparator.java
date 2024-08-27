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
public class JPK2022KZakupWierszcomparator implements Comparator<pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.ZakupWiersz> {

    @Override
    public int compare(pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.ZakupWiersz o1, pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.ZakupWiersz o2) {
        String nazwa1 = o1.getNazwaDostawcy()!=null?o1.getNazwaDostawcy().toLowerCase(new Locale("pl", "PL")):"";
        String nazwa2 = o2.getNazwaDostawcy()!=null?o2.getNazwaDostawcy().toLowerCase(new Locale("pl", "PL")):"";
        String datao1 = nazwa1;
        String datao2 = nazwa2;
        return datao1.compareTo(datao2);
   }
    
}
