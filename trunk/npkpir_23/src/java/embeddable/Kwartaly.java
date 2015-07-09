/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Osito
 */
@ManagedBean(name="Kwartaly")
@SessionScoped
public class Kwartaly implements Serializable{

    private static final List<String> mceList;
    private static final List<String> kwList;
    private static final Map<Integer, String> mapanrkw;
    private static final Map<Integer, List<String>> mapakwnr;

    static{
        mceList = new ArrayList<>();
        mceList.add("01");
        mceList.add("02");
        mceList.add("03");
        mceList.add("04");
        mceList.add("05");
        mceList.add("06");
        mceList.add("07");
        mceList.add("08");
        mceList.add("09");
        mceList.add("10");
        mceList.add("11");
        mceList.add("12");
        
        kwList = new ArrayList<>();
        kwList.add("1");
        kwList.add("2");
        kwList.add("3");
        kwList.add("4");
        
        mapanrkw = new HashMap<>();
        mapanrkw.put(1, "1");
        mapanrkw.put(2, "1");
        mapanrkw.put(3, "1");
        mapanrkw.put(4, "2");
        mapanrkw.put(5, "2");
        mapanrkw.put(6, "2");
        mapanrkw.put(7, "3");
        mapanrkw.put(8, "3");
        mapanrkw.put(9, "3");
        mapanrkw.put(10, "4");
        mapanrkw.put(11, "4");
        mapanrkw.put(12, "4");
        
        mapakwnr = new HashMap<>();
        mapakwnr.put(1, Szeregmcy.kwmce1);
        mapakwnr.put(2, Szeregmcy.kwmce2);
        mapakwnr.put(3, Szeregmcy.kwmce3);
        mapakwnr.put(4, Szeregmcy.kwmce4);
    }
    
    public static List<String> mctoMcwKw(String mc) {
        int nrmca = Mce.getMiesiacToNumber().get(mc);
        String kwartal = mapanrkw.get(nrmca);
        return mapakwnr.get(Integer.parseInt(kwartal));
    }
    
    public static List<String> getKwList() {
        return kwList;
    }

    public static Map<Integer, String> getMapanrkw() {
        return mapanrkw;
    }

    public static Map<Integer, List<String>> getMapakwnr() {
        return mapakwnr;
    }

    public Kwartaly() {
    }

    public List<String> getMceList() {
        return mceList;
    }

    public static class Szeregmcy {

        private static final List<String> kwmce1;
        private static final List<String> kwmce2;
        private static final List<String> kwmce3;
        private static final List<String> kwmce4;
        static {
            kwmce1 = new ArrayList<>();
            kwmce1.add("01");
            kwmce1.add("02");
            kwmce1.add("03");
            kwmce2 = new ArrayList<>();
            kwmce2.add("04");
            kwmce2.add("05");
            kwmce2.add("06");
            kwmce3 = new ArrayList<>();
            kwmce3.add("07");
            kwmce3.add("08");
            kwmce3.add("09");
            kwmce4 = new ArrayList<>();
            kwmce4.add("10");
            kwmce4.add("11");
            kwmce4.add("12");
        }
    }
 
}
