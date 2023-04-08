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
public class WypowiedzenieZlecenieSposob implements Serializable {
     private static final long serialVersionUID = 1L;
    
    private final static List<String> wypowiedzenieSposob;
     
     static {
        wypowiedzenieSposob = new ArrayList<>();
        wypowiedzenieSposob.add("Rozwiązanie za porozumieniem stron");
        wypowiedzenieSposob.add("Rozwiązanie za wypowiedzeniem przez zleceniodawcę");
        wypowiedzenieSposob.add("Rozwiązanie za wypowiedzeniem przez zleceniobiorcę");
        wypowiedzenieSposob.add("Rozwiązanie bez wypowiedzenia");
        wypowiedzenieSposob.add("Rozwiązanie z upływem czasu zawarcia");
        wypowiedzenieSposob.add("Rozwiązanie z dniem ukończenia zlecenia");
        wypowiedzenieSposob.add("Wygaśnięcia (śmierć)");
        wypowiedzenieSposob.add("Wygaśnięcia");

     }

    public static List<String> getWypowiedzenieSposob() {
        return wypowiedzenieSposob;
    }
     
     
}
