/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import dao.PodatnikDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean(name="Trans")
@RequestScoped
public class Trans implements Serializable{

    private static final List<String> transList;
    private static final List<String> transListZO;
    private static final List<String> transListRY;
    static {
        transList = new ArrayList<>();
        transList.add("zakup");
        transList.add("srodek trw");
        transList.add("srodek trw sprzedaz");
        transList.add("sprzedaz");
        transList.add("sprzedaz Niemcy");
        transList.add("inwestycja");
        transList.add("ryczałt");
        transList.add("WDT");
        transList.add("WNT");
        transList.add("import usług");
        transList.add("usługi poza ter.");
        transList.add("eksport towarów");
        transList.add("odwrotne obciążenie");
        transList.add("odwrotne obciążenie sprzedawca");
        transList.add("WB-RK");
        transList.add("PK");
        transListZO = new ArrayList<>();
        transListZO.add("zakup");
        transListZO.add("srodek trw");
        transListZO.add("srodek trw sprzedaz");
        transListZO.add("sprzedaz");
        transListZO.add("sprzedaz Niemcy");
        transListZO.add("inwestycja");
        transListZO.add("WDT");
        transListZO.add("WNT");
        transListZO.add("import usług");
        transListZO.add("usługi poza ter.");
        transListZO.add("eksport towarów");
        transListZO.add("odwrotne obciążenie");
        transListZO.add("odwrotne obciążenie sprzedawca");
        transListRY = new ArrayList<>();
        transListRY.add("ryczałt");
        transListRY.add("sprzedaz Niemcy");
        transListRY.add("zakup");
        transListRY.add("srodek trw");
        transListRY.add("srodek trw sprzedaz");
        transListRY.add("WDT");
        transListRY.add("WNT");
        transListRY.add("import usług");
        transListRY.add("usługi poza ter.");
        transListRY.add("eksport towarów");
        transListRY.add("odwrotne obciążenie");
        transListRY.add("odwrotne obciążenie sprzedawca");
    }

    public static List<String> getTransList() {
        return transList;
    }
    @Inject
    private PodatnikDAO podatnikDAO;
     
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;

    
    public Trans() {
    }

    public List<String> getTransListView() {
        return transList;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    
    
}
