/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@ManagedBean
@Embeddable
public class ERodzajZakupu implements Serializable {
    private static final List<String> rodzajZakupu;
    static{
        rodzajZakupu = Collections.synchronizedList(new ArrayList<>());
        rodzajZakupu.add("opodatkowane");
        rodzajZakupu.add("opodatkowane i zwolnione");
        rodzajZakupu.add("zwolnione");
        rodzajZakupu.add("kasa rejestrująca");
    }

    public static List<String> getRodzajZakupu() {
        return rodzajZakupu;
    }
    public List<String> getRodzajZakupuView() {
        return rodzajZakupu;
    }
    
}
