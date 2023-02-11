/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class WypowiedzenieSposob implements Serializable {
     private static final long serialVersionUID = 1L;
    
    private final static List<String> wypowiedzenieSposob;
     
     static {
        wypowiedzenieSposob = new ArrayList<>();
        wypowiedzenieSposob.add("Rozwiązanie za porozumieniem stron");
        wypowiedzenieSposob.add("Rozwiązanie za wypowiedzeniem przez zakład pracy");
        wypowiedzenieSposob.add("Rozwiązanie za wypowiedzeniem przez pracownika");
        wypowiedzenieSposob.add("Rozwiązanie bez wypowiedzenia");
        wypowiedzenieSposob.add("Rozwiązanie z upływem czasu zawarcia");
        wypowiedzenieSposob.add("Rozwiązanie z dniem ukończenia pracy");
        wypowiedzenieSposob.add("Rozwiązanie z powodu niezdolności do pracy i rehab.");
        wypowiedzenieSposob.add("Zwolnienie grupowe - art. 1 ustawy z dn. 13-03-2003");
        wypowiedzenieSposob.add("Zwolnienie indywidualne - art. 10 ust. z dn. 13-03-2003");
        wypowiedzenieSposob.add("Inne szczególne przypadki (a.23,48,68 lub 201)");
        wypowiedzenieSposob.add("Wygaśnięcia (emerytura)");
        wypowiedzenieSposob.add("Wygaśnięcia (renta)");
        wypowiedzenieSposob.add("Wygaśnięcia (śmierć)");
        wypowiedzenieSposob.add("Wygaśnięcia");

     }

    public static List<String> getWypowiedzenieSposob() {
        return wypowiedzenieSposob;
    }
     
     
}
