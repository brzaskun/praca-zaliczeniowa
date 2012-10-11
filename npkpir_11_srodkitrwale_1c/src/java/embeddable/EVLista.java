/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class EVLista {
    private static final List<String> VList;
    
    static{
        VList = new ArrayList<String>();
        VList.add("zakup");
        VList.add("srodki trwale");
        VList.add("sprzedaz 23%");
        VList.add("sprzedaz 8%");
        VList.add("sprzedaz 5%");
    }

    public static List<String> getVList() {
        return VList;
    }
    
    
}
