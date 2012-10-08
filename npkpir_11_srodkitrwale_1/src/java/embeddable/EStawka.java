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
public class EStawka {
    private static final List<String> stawkaVAT;
    
    static{
        stawkaVAT = new ArrayList<String>();
        stawkaVAT.add("23%");
        stawkaVAT.add("8%");
        stawkaVAT.add("5%");
    }

    public List<String> getStawkaVAT() {
        return stawkaVAT;
    }
    
    
}
