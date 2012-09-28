/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Named
@Embeddable
public class EVPozycja {
    private static final List<String> poleDeklaracji;
    
    static{
        poleDeklaracji = new ArrayList<String>();
        poleDeklaracji.add("20");
        poleDeklaracji.add("21");
        poleDeklaracji.add("22");
        poleDeklaracji.add("27");
        poleDeklaracji.add("29");
        poleDeklaracji.add("51");
    }

    public static List<String> getPoleDeklaracji() {
        return poleDeklaracji;
    }


}
