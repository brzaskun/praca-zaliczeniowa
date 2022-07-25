/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class PitKolumn implements Serializable{

    private static final List<String> kolumnList;
  
    
    static{
        kolumnList = new ArrayList<String>();
        kolumnList.add("podatnik");
        kolumnList.add("pkpirR");
        kolumnList.add("pkpirM");
        kolumnList.add("przychody");
        kolumnList.add("koszty");
        kolumnList.add("wynik");
        kolumnList.add("strata z lat ub");
        kolumnList.add("ZUS 51");
        kolumnList.add("podstawa opod.");
        kolumnList.add("podatek");
        kolumnList.add("ZUS 52");
        kolumnList.add("podatek należny od pocz rok");
        kolumnList.add("zaliczki za pop.m-ce");
        kolumnList.add("należna zal. za m-c");
        kolumnList.add("do zapłaty");
        kolumnList.add("termin płatności");
        kolumnList.add("przelano");
        kolumnList.add("zamknięty");
    } 
 
    
    public PitKolumn() {
    }

    public List<String> getKolumnList() {
        return kolumnList;
    }

   
    
}
