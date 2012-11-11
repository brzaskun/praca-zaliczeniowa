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
@ManagedBean(name="Mce")
@SessionScoped
public class Mce implements Serializable{

    private static final List<String> mceList;
    private static final Map<Integer, String> mapamcy;
    private static final Map<String,Integer> mapamcyX;

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
        
        mapamcy = new HashMap<>();
        mapamcy.put(1, "01");
        mapamcy.put(2, "02");
        mapamcy.put(3, "03");
        mapamcy.put(4, "04");
        mapamcy.put(5, "05");
        mapamcy.put(6, "06");
        mapamcy.put(7, "07");
        mapamcy.put(8, "08");
        mapamcy.put(9, "09");
        mapamcy.put(10, "10");
        mapamcy.put(11, "11");
        mapamcy.put(12, "12");
        
        mapamcyX = new HashMap<>();
        mapamcyX.put("01",1);
        mapamcyX.put("02",2);
        mapamcyX.put("03",3);
        mapamcyX.put("04",4);
        mapamcyX.put("05",5);
        mapamcyX.put("06",6);
        mapamcyX.put("07",7);
        mapamcyX.put("08",8);
        mapamcyX.put("09",9);
        mapamcyX.put("10",10);
        mapamcyX.put("11",11);
        mapamcyX.put("12",12);
    }
    
    public Mce() {
    }

    public List<String> getMceList() {
        return mceList;
    }

    public static Map<Integer, String> getMapamcy() {
        return mapamcy;
    }

    public static Map<String, Integer> getMapamcyX() {
        return mapamcyX;
    }
    
    
}
