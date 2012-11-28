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
@ManagedBean(name="Trans")
@SessionScoped
public class Trans implements Serializable{

    private static final List<String> transList;

    static{
        transList = new ArrayList<String>();
        transList.add("zakup");
        transList.add("srodek trw");
        transList.add("sprzedaz");
        transList.add("WDT");
        transList.add("WNT");
        transList.add("import usług");
    }
    
    public Trans() {
    }

    public static List<String> getTransList() {
        return transList;
    }
    
    public List<String> getTransListView() {
        return transList;
    }
}
