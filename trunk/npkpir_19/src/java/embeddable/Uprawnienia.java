/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.persistence.Embeddable;

/**
 *
 * @author Grzegorz Grzelczyk
 */
@Named(value="Uprawnienia")
@Embeddable
public class Uprawnienia implements Serializable{
    private static final List<String> upr;

    static {
        upr = new ArrayList<String>();
        upr.add("Administrator");
        upr.add("Manager");
        upr.add("Bookkeeper");
        upr.add("klient");
        upr.add("Guest");
    }

    public List<String> getUpr() {
        return upr;
    }
    
}
