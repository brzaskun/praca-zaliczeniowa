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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean(name="Mce")
@SessionScoped
public class Mce implements Serializable{
    
    private static final List<String> mceList;
    private static final List<String> mcenazwaList;
    private static final List<String> mcenazwaListSlownik;
    private static final Map<Integer, String> numberToMiesiac;
    private static final Map<Integer, String> numberToNazwamiesiaca;
    private static final Map<String, String> stringToNazwamiesiaca;
    private static final Map<String,Integer> miesiacToNumber;
    private static final Map<String,Integer> mapamcyCalendar;
    private static final Map<String, String> mce_pl_de;

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
        
        mcenazwaList = new ArrayList<>();
        mcenazwaList.add("styczeń");
        mcenazwaList.add("luty");
        mcenazwaList.add("marzec");
        mcenazwaList.add("kwiecień");
        mcenazwaList.add("maj");
        mcenazwaList.add("czerwiec");
        mcenazwaList.add("lipiec");
        mcenazwaList.add("sierpień");
        mcenazwaList.add("wrzesień");
        mcenazwaList.add("październik");
        mcenazwaList.add("listopad");
        mcenazwaList.add("grudzień");
        
        mcenazwaListSlownik = new ArrayList<>();
        mcenazwaListSlownik.add("styczeń");
        mcenazwaListSlownik.add("luty");
        mcenazwaListSlownik.add("marzec");
        mcenazwaListSlownik.add("kwiecień");
        mcenazwaListSlownik.add("maj");
        mcenazwaListSlownik.add("czerwiec");
        mcenazwaListSlownik.add("lipiec");
        mcenazwaListSlownik.add("sierpień");
        mcenazwaListSlownik.add("wrzesień");
        mcenazwaListSlownik.add("październik");
        mcenazwaListSlownik.add("listopad");
        mcenazwaListSlownik.add("grudzień");
        mcenazwaListSlownik.add("BO");
        
        numberToMiesiac = new HashMap<>();
        numberToMiesiac.put(1, "01");
        numberToMiesiac.put(2, "02");
        numberToMiesiac.put(3, "03");
        numberToMiesiac.put(4, "04");
        numberToMiesiac.put(5, "05");
        numberToMiesiac.put(6, "06");
        numberToMiesiac.put(7, "07");
        numberToMiesiac.put(8, "08");
        numberToMiesiac.put(9, "09");
        numberToMiesiac.put(10, "10");
        numberToMiesiac.put(11, "11");
        numberToMiesiac.put(12, "12");
        
        
        numberToNazwamiesiaca = new HashMap<>();
        numberToNazwamiesiaca.put(1, "styczeń");
        numberToNazwamiesiaca.put(2, "luty");
        numberToNazwamiesiaca.put(3, "marzec");
        numberToNazwamiesiaca.put(4, "kwiecień");
        numberToNazwamiesiaca.put(5, "maj");
        numberToNazwamiesiaca.put(6, "czerwiec");
        numberToNazwamiesiaca.put(7, "lipiec");
        numberToNazwamiesiaca.put(8, "sierpień");
        numberToNazwamiesiaca.put(9, "wrzesień");
        numberToNazwamiesiaca.put(10, "październik");
        numberToNazwamiesiaca.put(11, "listopad");
        numberToNazwamiesiaca.put(12, "grudzień");
        
        stringToNazwamiesiaca = new HashMap<>();
        stringToNazwamiesiaca.put("01", "styczeń");
        stringToNazwamiesiaca.put("02", "luty");
        stringToNazwamiesiaca.put("03", "marzec");
        stringToNazwamiesiaca.put("04", "kwiecień");
        stringToNazwamiesiaca.put("05", "maj");
        stringToNazwamiesiaca.put("06", "czerwiec");
        stringToNazwamiesiaca.put("07", "lipiec");
        stringToNazwamiesiaca.put("08", "sierpień");
        stringToNazwamiesiaca.put("09", "wrzesień");
        stringToNazwamiesiaca.put("10", "październik");
        stringToNazwamiesiaca.put("11", "listopad");
        stringToNazwamiesiaca.put("12", "grudzień");
        
        miesiacToNumber = new HashMap<>();
        miesiacToNumber.put("01",1);
        miesiacToNumber.put("02",2);
        miesiacToNumber.put("03",3);
        miesiacToNumber.put("04",4);
        miesiacToNumber.put("05",5);
        miesiacToNumber.put("06",6);
        miesiacToNumber.put("07",7);
        miesiacToNumber.put("08",8);
        miesiacToNumber.put("09",9);
        miesiacToNumber.put("10",10);
        miesiacToNumber.put("11",11);
        miesiacToNumber.put("12",12);
        
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
        
        mce_pl_de = new HashMap<>();
        mce_pl_de.put("styczeń","January");
        mce_pl_de.put("luty", "February");
        mce_pl_de.put("marzec", "März");
        mce_pl_de.put("kwiecień", "April");
        mce_pl_de.put("maj", "Mai");
        mce_pl_de.put("czerwiec", "Juni");
        mce_pl_de.put("lipiec", "Juli");
        mce_pl_de.put("sierpień", "August");
        mce_pl_de.put("wrzesień", "September");
        mce_pl_de.put("październik", "Oktober");
        mce_pl_de.put("listopad", "November");
        mce_pl_de.put("grudzień", "Dezember");
        
    }
    
    public static String[] zwiekszmiesiac(String rok, String miesiac) {
        String[] nowedane = new String[2];
        int mcInt = miesiacToNumber.get(miesiac);
        if (mcInt < 12) {
            nowedane[0] = rok;
            nowedane[1] = numberToMiesiac.get(++mcInt);
        } else {
            int rokInt = Integer.parseInt(rok);
            nowedane[0] = String.valueOf(++rokInt);
            nowedane[1] = "01";
        }
        return nowedane;
    }
    
    public static String[] zwiekszmiesiac(String rok, String miesiac, int oilezwiekszyc) {
        String[] nowedane = new String[2];
        int mcInt = miesiacToNumber.get(miesiac)+oilezwiekszyc;
        if (mcInt <= 12) {
            nowedane[0] = rok;
            nowedane[1] = numberToMiesiac.get(mcInt);
        } else if (mcInt <= 24) {
            int rokInt = Integer.parseInt(rok);
            nowedane[0] = String.valueOf(++rokInt);
            nowedane[1] = numberToMiesiac.get(mcInt-12);
        } else if (mcInt <= 36) {
            int rokInt = Integer.parseInt(rok)+2;
            nowedane[0] = String.valueOf(rokInt);
            nowedane[1] = numberToMiesiac.get(mcInt-24);
        } else if (mcInt <= 48) {
            int rokInt = Integer.parseInt(rok)+3;
            nowedane[0] = String.valueOf(rokInt);
            nowedane[1] = numberToMiesiac.get(mcInt-36);
        }
        return nowedane;
    }
    
    public static String[] zmniejszmiesiac(String rok, String miesiac) {
        String[] nowedane = new String[2];
        int mcInt = miesiacToNumber.get(miesiac);
        if (mcInt > 1) {
            nowedane[0] = rok;
            nowedane[1] = numberToMiesiac.get(--mcInt);
        } else {
            int rokInt = Integer.parseInt(rok);
            nowedane[0] = String.valueOf(--rokInt);
            nowedane[1] = numberToMiesiac.get(13-mcInt);;
        }
        return nowedane;
    }
    
    public static String[] zmniejszmiesiac(String rok, String miesiac, int oilezmniejszyc) {
        String[] nowedane = new String[2];
        int mcInt = miesiacToNumber.get(miesiac)-oilezmniejszyc;
        if (mcInt > 1) {
            nowedane[0] = rok;
            nowedane[1] = numberToMiesiac.get(mcInt);
        } else {
            int rokInt = Integer.parseInt(rok);
            nowedane[0] = String.valueOf(--rokInt);
            nowedane[1] = numberToMiesiac.get(13-mcInt);;
        }
        return nowedane;
    }
    /*
    * zwraca liste uprzednich mcy
    */
    public static List<String> poprzedniemce(String miesiac) {
        List<String> poprzedniemce = new ArrayList<>();
        int miesiacasint = miesiacToNumber.get(miesiac);
        for (int p : numberToMiesiac.keySet()) {
            if (p < miesiacasint) {
                poprzedniemce.add(numberToMiesiac.get(p));
            }
        }
        return poprzedniemce;
    }
    
    public static List<String> zakresmiesiecy(String mcOd, String mcDo) {
        List<String> listamiesiecy = new ArrayList<>();
        int mcod = Mce.miesiacToNumber.get(mcOd);
        int mcdo = Mce.miesiacToNumber.get(mcDo);
        if (mcod > mcdo) {
            Msg.msg("e", "Miesiąc Od jest późniejszy od miesiąca Do!");
        }
        for (int i = mcod; i < mcdo+1; i++) {
            listamiesiecy.add(Mce.numberToMiesiac.get(i));
        }
        return listamiesiecy;
    }
   
   public static int odlegloscMcy(String mcOd, String rokOd, String mcAkt, String rokAkt) {
        int mcod = Mce.miesiacToNumber.get(mcOd);
        int rokod = Integer.parseInt(rokOd);
        int mcakt = Mce.miesiacToNumber.get(mcAkt);
        int rokakt = Integer.parseInt(rokAkt);
        int iloscmcy = 0;
        if (rokod > rokakt) {
            return -1;
        }
        int rokgraniczny = rokakt;
        if (rokod < rokgraniczny) {
            iloscmcy += mcakt;
            rokgraniczny -= 1;
            while (rokod < rokgraniczny) {
                iloscmcy += 12;
                rokgraniczny--;
            }
            if (rokod == rokgraniczny) {
                iloscmcy += 12 - mcod;
                return iloscmcy;
            }
        } else if (rokod == rokakt) {
            if (mcod < mcakt) {
                iloscmcy += 12 - mcod;
                return iloscmcy;
            } else if (mcod == mcakt) {
                return iloscmcy;
            }else {
                return -1;
            }
        }
        return -1;
    }
   
    
    public Mce() {
    }
    
//    public static void main (String[] args) {
//        int mcod = 05;
//        int rokod = 2013;
//        int mcakt = 01;
//        int rokakt = 2015;
//        int iloscmcy = 0;
//        if (rokod > rokakt) {
//            //return -1;
//        }
//        int rokgraniczny = rokakt;
//        if (rokod < rokgraniczny) {
//            iloscmcy += mcakt;
//            rokgraniczny -= 1;
//            while (rokod < rokgraniczny) {
//                iloscmcy += 12;
//                rokgraniczny--;
//            }
//            if (rokod == rokgraniczny) {
//                iloscmcy += 12 - mcod;
//                //return iloscmcy;
//            }
//        } else if (rokod == rokakt) {
//            if (mcod < rokakt) {
//                iloscmcy += 12 - mcod;
//                //return iloscmcy;
//            } else {
//                //return -1;
//            }
//        }
//        //return -1;
//    }
    
    public static void main(String[] args) {
        String rok = "2015";
        String mc = "01";
        int oile = 24;
        String[] t = zwiekszmiesiac(rok, mc, oile);
        System.out.println("rok "+t[0]);
        System.out.println("mc "+t[1]);
    }
    
//<editor-fold defaultstate="collapsed" desc="comment">
    public static Map<Integer, String> getNumberToMiesiac() {
        return numberToMiesiac;
    }
    
    public static Map<String, Integer> getMiesiacToNumber() {
        return miesiacToNumber;
    }
    
    public static Map<String, Integer> getMapamcyCalendar() {
        return mapamcyCalendar;
    }
    
    public static Map<Integer, String> getNumberToNazwamiesiaca() {
        return numberToNazwamiesiaca;
    }
    
    public List<String> getMceList() {
        return mceList;
    }
    
    public static List<String> getMceListS() {
        return mceList;
    }

    public static List<String> getMcenazwaListSlownik() {
        return mcenazwaListSlownik;
    }

    public static Map<String, String> getMce_pl_de() {
        return mce_pl_de;
    }

    public static Map<String, String> getStringToNazwamiesiaca() {
        return stringToNazwamiesiaca;
    }
    
    public static List<String> getMcenazwaList() {
        return mcenazwaList;
    }
    
//</editor-fold>

    
}
