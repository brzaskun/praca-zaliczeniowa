/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package f;

import java.util.List;
import org.apache.commons.collections4.CollectionUtils;

/**
 *
 * @author Osito
 */
public class l {
    
    /**
    * @param lista lista podstawowa
    * @param filter lista filtrowana
    * @param wybrane lista wybranych pozycji
    * @return  zwraca liste do przetwarzania jedną z trzech
    */
    public static List l (List lista,List filter, List wybrane) {
        List podstawowa = CollectionUtils.isNotEmpty(filter)? filter: lista;
        podstawowa = CollectionUtils.isNotEmpty(wybrane)? wybrane:podstawowa;
        return podstawowa;
    }
}
