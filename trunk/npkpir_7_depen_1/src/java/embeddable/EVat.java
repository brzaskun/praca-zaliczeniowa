/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Osito
 */
@ManagedBean(name="EVat")
@SessionScoped
public class EVat implements Serializable{

    private static final List<String> evatList;

    static{
        evatList = new ArrayList<String>();
        evatList.add("zakup");
        evatList.add("srodek trw");
        evatList.add("sprzedaz 23%");
        evatList.add("sprzedaz 8%");
        evatList.add("sprzedaz 5%");
    }
    
    public EVat() {
    }

    public List<String> getEvatList() {
        return evatList;
    }
    
}
