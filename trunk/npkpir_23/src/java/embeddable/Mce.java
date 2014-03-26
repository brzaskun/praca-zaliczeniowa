/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
    private static final Map<Integer, String> mapamcynazwa;
    private static final Map<String,Integer> mapamcyX;
    private static final Map<String,Integer> mapamcyCalendar;

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
        
        mapamcynazwa = new HashMap<>();
        mapamcynazwa.put(1, "styczeń");
        mapamcynazwa.put(2, "luty");
        mapamcynazwa.put(3, "marzec");
        mapamcynazwa.put(4, "kwiecień");
        mapamcynazwa.put(5, "maj");
        mapamcynazwa.put(6, "czerwiec");
        mapamcynazwa.put(7, "lipiec");
        mapamcynazwa.put(8, "sierpień");
        mapamcynazwa.put(9, "wrzesień");
        mapamcynazwa.put(10, "październik");
        mapamcynazwa.put(11, "listopad");
        mapamcynazwa.put(12, "grudzień");
        
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
        
        mapamcyCalendar = new HashMap<>();
        mapamcyCalendar.put("01",Calendar.JANUARY);
        mapamcyCalendar.put("02",Calendar.FEBRUARY);
        mapamcyCalendar.put("03",Calendar.MARCH);
        mapamcyCalendar.put("04",Calendar.APRIL);
        mapamcyCalendar.put("05",Calendar.MAY);
        mapamcyCalendar.put("06",Calendar.JUNE);
        mapamcyCalendar.put("07",Calendar.JULY);
        mapamcyCalendar.put("08",Calendar.AUGUST);
        mapamcyCalendar.put("09",Calendar.SEPTEMBER);
        mapamcyCalendar.put("10",Calendar.OCTOBER);
        mapamcyCalendar.put("11",Calendar.NOVEMBER);
        mapamcyCalendar.put("12",Calendar.DECEMBER);
    }
    
    public static String[] zwiekszmiesiac(String rok, String miesiac) {
        String[] nowedane = new String[2];
        int mcInt = mapamcyX.get(miesiac);
        if (mcInt < 12) {
            nowedane[0] = rok;
            nowedane[1] = mapamcy.get(++mcInt);
        } else {
            int rokInt = Integer.parseInt(rok);
            nowedane[0] = String.valueOf(++rokInt);
            nowedane[1] = "01";
        }
        return nowedane;
    }
    /*
    * zwraca liste uprzednich mcy
    */
    public static List<String> poprzedniemce(String miesiac) {
        List<String> poprzedniemce = new ArrayList<>();
        int miesiacasint = mapamcyX.get(miesiac);
        for (int p : mapamcy.keySet()) {
            if (p < miesiacasint) {
                poprzedniemce.add(mapamcy.get(p));
            }
        }
        return poprzedniemce;
    }
    
    public static Map<Integer, String> getMapamcy() {
        return mapamcy;
    }

    public static Map<String, Integer> getMapamcyX() {
        return mapamcyX;
    }

    public static Map<String, Integer> getMapamcyCalendar() {
        return mapamcyCalendar;
    }

    public static Map<Integer, String> getMapamcynazwa() {
        return mapamcynazwa;
    }

    public Mce() {
    }

    public List<String> getMceList() {
        return mceList;
    }
    
    
}
