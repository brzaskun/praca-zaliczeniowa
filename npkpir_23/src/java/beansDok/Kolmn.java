/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beansDok;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
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

    static{
        kolumnList = new ArrayList<>();
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
        
        kolumnZest = new ArrayList<>();
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
        
        kolumnPrzychody = new ArrayList<>();
        kolumnPrzychody.add("przych. sprz");
        kolumnPrzychody.add("pozost. przych.");
       
        kolumnKoszty = new ArrayList<>();
        kolumnKoszty.add("zakup tow. i mat.");
        kolumnKoszty.add("koszty ub.zak.");
        kolumnKoszty.add("wynagrodzenia");
        kolumnKoszty.add("poz. koszty");
        
        kolumnST = new ArrayList<>();
        kolumnST.add("inwestycje");
        kolumnST.add("uwagi");
        
        kolumnSTsprz = new ArrayList<>();
        kolumnSTsprz.add("pozost. przych.");
        kolumnSTsprz.add("poz. koszty");
       
        kolumnRyczalt = new ArrayList<>();
        kolumnRyczalt.add("17%");
        kolumnRyczalt.add("8.5%");
        kolumnRyczalt.add("5.5%");
        kolumnRyczalt.add("3%");
    }
    
    public static List<String> zwrockolumny(String transakcjiRodzaj) {
        switch (transakcjiRodzaj) {
            case "ryczałt":
            case "ryczałt bez VAT":
                return kolumnRyczalt;
            case "zakup":
            case "import usług":
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
                return kolumnRyczalt;
            case "zakup":
            case "import usług":
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
                return kolumnRyczalt;
            default:
                return kolumnList;
        }
    }
    
    
    public static void main(String[] args) {
        
        String pricesString = "1 100.00zł";
        String prices = pricesString.replaceAll("\\s","");
        Pattern p = Pattern.compile("(\\d*.\\d)");
        Matcher m = p.matcher(prices);
        while (m.find()) {
            System.out.println("lolo");
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
