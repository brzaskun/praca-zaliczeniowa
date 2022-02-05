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
public class JPK2022MSprzedazWierszcomparator implements Comparator<pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.SprzedazWiersz> {

    @Override
    public int compare(pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.SprzedazWiersz o1, pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.SprzedazWiersz o2) {
        String k1 = o1.getNazwaKontrahenta()!=null?o1.getNazwaKontrahenta():"BRAK";
        String k2 = o2.getNazwaKontrahenta()!=null?o2.getNazwaKontrahenta():"BRAK";
        String datao1 = k1.toLowerCase(new Locale("pl", "PL"));
        String datao2 = k2.toLowerCase(new Locale("pl", "PL"));
        return datao1.compareTo(datao2);
   }
    
}
