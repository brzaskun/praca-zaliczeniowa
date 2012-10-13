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
        upr.add("administrator");
        upr.add("kierownik");
        upr.add("księgowa");
        upr.add("klient");
        upr.add("gość");
    }

    public List<String> getUpr() {
        return upr;
    }
    
}
