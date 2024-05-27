/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beansDok;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named

public class Kolmn implements Serializable{
    
    
    private static final List<String> kolumnList;
    private static final List<String> kolumnZest;
    private static final List<String> kolumnPrzychody;
    private static final List<String> kolumnKoszty;
    private static final List<String> kolumnST;
    private static final List<String> kolumnSTsprz;
    private static final List<String> kolumnRyczalt;
    private static final List<String> kolumnNiemcy;

    static{
        kolumnList = Collections.synchronizedList(new ArrayList<>());
        kolumnList.add("przych. sprz");
        kolumnList.add("pozost. przych.");
        //kolumnList.add("razem. przych.");
        kolumnList.add("zakup tow. i mat.");
        kolumnList.add("koszty ub.zak.");
        kolumnList.add("wynagrodzenia");
        kolumnList.add("poz. koszty");
        //kolumnList.add("razem. koszty.");
        //kolumnList.add("wynik");
        kolumnList.add("inwestycje");
        kolumnList.add("uwagi");
        
        kolumnNiemcy = Collections.synchronizedList(new ArrayList<>());
        kolumnNiemcy.add("przychody Niemcy");
        kolumnNiemcy.add("koszty Niemcy");
        
        kolumnZest = Collections.synchronizedList(new ArrayList<>());
        kolumnZest.add("przych. sprz");
        kolumnZest.add("pozost. przych.");
        kolumnZest.add("zakup tow. i mat.");
        kolumnZest.add("koszty ub.zak.");
        kolumnZest.add("wynagrodzenia");
        kolumnZest.add("poz. koszty");
        kolumnZest.add("inwestycje");
        kolumnZest.add("razem przych.");
        kolumnZest.add("razem koszty");
        kolumnZest.add("wynik");
        
        kolumnPrzychody = Collections.synchronizedList(new ArrayList<>());
        kolumnPrzychody.add("przych. sprz");
        kolumnPrzychody.add("pozost. przych.");
       
        kolumnKoszty = Collections.synchronizedList(new ArrayList<>());
        kolumnKoszty.add("zakup tow. i mat.");
        kolumnKoszty.add("koszty ub.zak.");
        kolumnKoszty.add("wynagrodzenia");
        kolumnKoszty.add("poz. koszty");
        kolumnKoszty.add("uwagi");
        
        kolumnST = Collections.synchronizedList(new ArrayList<>());
        kolumnST.add("inwestycje");
        kolumnST.add("uwagi");
        
        kolumnSTsprz = Collections.synchronizedList(new ArrayList<>());
        kolumnSTsprz.add("pozost. przych.");
        kolumnSTsprz.add("poz. koszty");
       
        kolumnRyczalt = Collections.synchronizedList(new ArrayList<>());
        kolumnRyczalt.add("17%");
        kolumnRyczalt.add("15%");
        kolumnRyczalt.add("14%");
        kolumnRyczalt.add("12.5%");
        kolumnRyczalt.add("12%");
        kolumnRyczalt.add("10%");
        kolumnRyczalt.add("8.5%");
        kolumnRyczalt.add("5.5%");
        kolumnRyczalt.add("3%");
        kolumnRyczalt.add("2%");
        kolumnRyczalt.add("razem");
    }
    
    public static List<String> zwrockolumny(String transakcjiRodzaj) {
        switch (transakcjiRodzaj) {
            case "ryczałt":
            case "ryczałt bez VAT":
                return kolumnRyczalt;
            case "zakup":
            case "import usług":
            case "import towarów":
            case "WNT":
            case "odwrotne obciążenie":
                return kolumnKoszty;
            case "srodek trw":
            case "inwestycja":
                return kolumnST;
            case "srodek trw sprzedaz":
                return kolumnSTsprz;
            case "eksport towarów":
            case "usługi poza ter.":
            case "WDT":
            case "sprzedaz":
                return kolumnPrzychody;
            default:
                return kolumnList;
        }
    }
    
    public static List<String> zwrockolumnyR(String transakcjiRodzaj) {
        switch (transakcjiRodzaj) {
            case "ryczałt":
            case "ryczałt bez VAT":
            case "odwrotne obciążenie sprzedawca":
            case "eksport towarów":
            case "usługi poza ter.":
            case "WDT":
            case "sprzedaz":
                return kolumnRyczalt;
            case "zakup":
            case "import usług":
            case "import towarów":
            case "WNT":
            case "odwrotne obciążenie":
                return kolumnKoszty;
            case "srodek trw":
            case "inwestycja":
                return kolumnST;
            case "srodek trw sprzedaz":
                return kolumnSTsprz;
            default:
                return kolumnRyczalt;
        }
    }
    
    
    public static void main(String[] args) {
        
        String pricesString = "1 100.00zł";
        String prices = pricesString.replaceAll("\\s","");
        Pattern p = Pattern.compile("(\\d*.\\d)");
        Matcher m = p.matcher(prices);
        while (m.find()) {
        }
    }

    public Kolmn() {
    }

    public List<String> getKolumnList() {
        return kolumnList;
    }

    public List<String> getKolumnPrzychody() {
        return kolumnPrzychody;
    }

    public  List<String> getKolumnKoszty() {
        return kolumnKoszty;
    }

    public List<String> getKolumnST() {
        return kolumnST;
    }

    public List<String> getKolumnZest() {
        return kolumnZest;
    }

    public List<String> getKolumnRyczalt() {
        return kolumnRyczalt;
    }
    
    
    
    public List<String> getKolumnSTsprz(){
        return kolumnSTsprz;
    }
    
}
