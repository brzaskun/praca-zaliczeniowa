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
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean(name="Kwartaly")
@SessionScoped
public class Kwartaly implements Serializable{

    private static final List<String> mceList;
    private static final List<String> kwList;
    private static final Map<Integer, String> numberToKw;
    private static final Map<Integer, String> kwToostMc;
    private static final Map<Integer, String> mapanrkw;
    private static final Map<String, String> mapamckw;
    private static final Map<String, Integer> mapamckwint;
    private static final Map<String, String> mapamcMcwkw;
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
        
        numberToKw = new HashMap<>();
        numberToKw.put(1, "1");
        numberToKw.put(2, "2");
        numberToKw.put(3, "3");
        numberToKw.put(4, "4");
        
        kwToostMc = new HashMap<>();
        kwToostMc.put(1,"03");
        kwToostMc.put(2,"06");
        kwToostMc.put(3,"09");
        kwToostMc.put(4,"12");
        
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
        
        mapamckw = new HashMap<>();
        mapamckw.put("01", "1");
        mapamckw.put("02", "1");
        mapamckw.put("03", "1");
        mapamckw.put("04", "2");
        mapamckw.put("05", "2");
        mapamckw.put("06", "2");
        mapamckw.put("07", "3");
        mapamckw.put("08", "3");
        mapamckw.put("09", "3");
        mapamckw.put("10", "4");
        mapamckw.put("11", "4");
        mapamckw.put("12", "4");
        
        mapamckwint = new HashMap<>();
        mapamckwint.put("01", 1);
        mapamckwint.put("02", 1);
        mapamckwint.put("03", 1);
        mapamckwint.put("04", 2);
        mapamckwint.put("05", 2);
        mapamckwint.put("06", 2);
        mapamckwint.put("07", 3);
        mapamckwint.put("08", 3);
        mapamckwint.put("09", 3);
        mapamckwint.put("10", 4);
        mapamckwint.put("11", 4);
        mapamckwint.put("12", 4);
        
        mapamcMcwkw = new HashMap<>();
        mapamcMcwkw.put("01", "03");
        mapamcMcwkw.put("02", "03");
        mapamcMcwkw.put("03", "03");
        mapamcMcwkw.put("04", "06");
        mapamcMcwkw.put("05", "06");
        mapamcMcwkw.put("06", "06");
        mapamcMcwkw.put("07", "09");
        mapamcMcwkw.put("08", "09");
        mapamcMcwkw.put("09", "09");
        mapamcMcwkw.put("10", "12");
        mapamcMcwkw.put("11", "12");
        mapamcMcwkw.put("12", "12");
        
        mapakwnr = new HashMap<>();
        mapakwnr.put(1, Szeregmcy.kwmce1);
        mapakwnr.put(2, Szeregmcy.kwmce2);
        mapakwnr.put(3, Szeregmcy.kwmce3);
        mapakwnr.put(4, Szeregmcy.kwmce4);
    }
    
    public static List<String> mctoMcewKw(String mc) {
        int nrmca = Mce.getMiesiacToNumber().get(mc);
        String kwartal = mapanrkw.get(nrmca);
        return mapakwnr.get(Integer.parseInt(kwartal));
    }
    
    public static Integer[] zwiekszkwartal(WpisView wpisView) {
        Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
        Integer[] nowedane = new Integer[2];
        if (kwartal < 4) {
            nowedane[0] = wpisView.getRokWpisu();
            nowedane[1] = kwartal+1;
        } else {
            int rokInt = wpisView.getRokWpisu();
            nowedane[0] = ++rokInt;
            nowedane[1] = 1;
        }
        return nowedane;
    }
    
     public static String[] zwiekszkwartal(String rok, String miesiac, int oilezwiekszyc) {
        String[] nowedane = new String[2];
        int kwInt = mapamckwint.get(miesiac)+oilezwiekszyc;
        if (kwInt <= 0) {
            int rokInt = Integer.parseInt(rok);
            nowedane[0] = String.valueOf(rokInt-1);
            nowedane[1] = kwToostMc.get(4-Math.abs(kwInt));
        } else if (kwInt <= 4) {
            nowedane[0] = rok;
            nowedane[1] = kwToostMc.get(kwInt);
        } else if (kwInt > 4) {
            int rokInt = Integer.parseInt(rok);
            nowedane[0] = String.valueOf(++rokInt);
            nowedane[1] = kwToostMc.get(kwInt-4);
        }
        return nowedane;
    }
    
    public static void main(String[] args) {
        String rok = "2017";
        String miesiac = "04";
        int oilezwiekszyc = 5;
        String[] nowedane = zwiekszkwartal(rok, miesiac, oilezwiekszyc);
        System.out.println("rok "+nowedane[0]);
        System.out.println("mc "+nowedane[1]);
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

    public static Map<String, String> getMapamckw() {
        return mapamckw;
    }

    public static Map<String, String> getMapamcMcwkw() {
        return mapamcMcwkw;
    }

    public static Map<Integer, String> getKwToostMc() {
        return kwToostMc;
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
