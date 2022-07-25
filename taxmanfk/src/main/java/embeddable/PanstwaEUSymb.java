/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.SessionScoped;;
import javax.inject.Named;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Named
@Embeddable
@SessionScoped
public class PanstwaEUSymb implements Serializable {

    private final static List<String> wykazPanstwUE;
    
static{
    wykazPanstwUE = Collections.synchronizedList(new ArrayList<>());
    wykazPanstwUE.add("AT");
    wykazPanstwUE.add("BE");
    wykazPanstwUE.add("BG");
    wykazPanstwUE.add("HR");
    wykazPanstwUE.add("CY");
    wykazPanstwUE.add("CZ");
    wykazPanstwUE.add("DK");
    wykazPanstwUE.add("EE");
    wykazPanstwUE.add("FI");
    wykazPanstwUE.add("FR");
    wykazPanstwUE.add("EL");
    wykazPanstwUE.add("ES");
    wykazPanstwUE.add("NL");
    wykazPanstwUE.add("IE");
    wykazPanstwUE.add("LT");
    wykazPanstwUE.add("LV");
    wykazPanstwUE.add("MT");
    wykazPanstwUE.add("DE");
    wykazPanstwUE.add("PL");
    wykazPanstwUE.add("PT");
    wykazPanstwUE.add("RO");
    wykazPanstwUE.add("SK");
    wykazPanstwUE.add("SI");
    wykazPanstwUE.add("SE");
    wykazPanstwUE.add("HU");
    wykazPanstwUE.add("GB");
    wykazPanstwUE.add("IT");
    
}

 public static List<String> getWykazPanstwUE() {
     return wykazPanstwUE;  
 }  

    public List<String> complete(String query) {  
        List<String> results = new ArrayList<String>();
        String kl = new String();
        for(String p : getWykazPanstwUE()) {  
            if(p.startsWith(query)) {
                results.add(p);
            }
        }
        return results;
    }
   
    
}
