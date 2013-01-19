/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import dao.PodatnikDAO;
import dao.UzDAO;
import entity.Podatnik;
import entity.Uz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean(name="Trans")
@SessionScoped
public class Trans implements Serializable{

    @Inject private PodatnikDAO podatnikDAO;
    private static final List<String> transList;
    private static final List<String> transListZO;
    private static final List<String> transListRY;

    static{
        transList = new ArrayList<>();
        transList.add("zakup");
        transList.add("srodek trw");
        transList.add("sprzedaz");
        transList.add("ryczałt");
        transList.add("WDT");
        transList.add("WNT");
        transList.add("import usług");
        transListZO = new ArrayList<>();
        transListZO.add("zakup");
        transListZO.add("srodek trw");
        transListZO.add("sprzedaz");
        transListZO.add("WDT");
        transListZO.add("WNT");
        transListZO.add("import usług");
        transListRY = new ArrayList<>();
        transListRY.add("ryczałt");
        transListRY.add("zakup");
        transListRY.add("srodek trw");
        transListRY.add("WDT");
        transListRY.add("WNT");
        transListRY.add("import usług");
    }
    
    public Trans() {
    }

    public static List<String> getTransList() {
        return transList;
    }
    
    public List<String> getTransListView() {
        try {
        Podatnik tmp = podatnikDAO.find(WpisView.getPodatnikWpisuS());
        int index = tmp.getPodatekdochodowy().size()-1;
        if (tmp.getPodatekdochodowy().get(index).getParametr().equals("ryczałt")){
            return transListRY;
        } else {
            return transListZO;
        }
        } catch (Exception e){
            return transList;
        }
    }
}
