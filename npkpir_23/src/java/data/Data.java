/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import embeddable.Mce;
import entity.UmorzenieN;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
public class Data implements Serializable {

    /**
     * Zwraca string bedacy ostatnim dniem miesiaca dla wydrukow i pk
     * @param rok
     * @param mc
     * @return <code>String</code> data w formacie yyyy-MM-dd
     */
    public static String ostatniDzien(String rok, String mc) {
        switch (mc) {
            case "01":
            case "03":
            case "05":
            case "07":
            case "08":
            case "10":
            case "12":
                return rok + "-" + mc + "-31";
            case "02":
                return rok + "-" + mc + "-28";
            default:
                return rok + "-" + mc + "-30";
        }
    }
    
    /**
     * Porównywanie dwóch rokow i mce. Przyjmuje integer
     * 
     * @param rokP rok późniejszy
     * @param mcP miesiąc poźniejszy
     * @param rokW rok wcześniejszy
     * @param mcW miesiąc wcześniejszy
     * @return    <code>1</code> jeżeli data póżniejsza jest późniejsza jest wcześniejszej <br/>
     *            <code>0</code> jeżeli lata i mce są równe<br/>
     *            <code>-1</code> jeżeli wcześniejsza jest jednak późniejsza
     */
    private static int doCompare(int rokP, int mcP, int rokW, int mcW) {
         if (rokP < rokW) {
            return -1;
        } else if (rokP > rokW) {
            return 1;
        } else if (rokP == rokW) {
            if (mcP == mcW) {
                return 0;
            } else if (mcP < mcW) {
                return -1;
            } else {
                return 1;
            }
        }
        return 0;
    }
    
   
    
    public static int compare(int rokP, int mcP, int rokW, int mcW) {
       return doCompare(rokP, mcP, rokW, mcW);
    }
    
    /**
     * Porównywanie dwóch rokow i mce. Przyjmuje String
     * 
     * @param rokP1 rok późniejszy
     * @param mcP1 miesiąc poźniejszy
     * @param rokW1 rok wcześniejszy
     * @param mcW1 miesiąc wcześniejszy
     * @return    <code>1</code> jeżeli data póżniejsza jest późniejsza od wcześniejszej <br/>
     *            <code>0</code> jeżeli lata i mce są równe<br/>
     *            <code>-1</code> jeżeli wcześniejsza jest jednak późniejsza
     */
    public static int compare(String rokP1, String mcP1, String rokW1, String mcW1) {
        int rokP = Integer.parseInt(rokP1);
        int mcP = Integer.parseInt(mcP1);
        int rokW = Integer.parseInt(rokW1);
        int mcW = Integer.parseInt(mcW1);
        return doCompare(rokP, mcP, rokW, mcW);
    }
    
     /**
     * Porównywanie dwóch dat. Data późniejsza i data wcześniejsza.
     * Data w formacie rrrr-MM-dd
     * 
     * @param datapozniejsza data późniejsza do porównywania
     * @param datawczesniejsza data wcześniejsza do porównywania
     * @return    <code>1</code> jeżeli data póżniejsza jest późniejsza jest wcześniejszej<br/>
     *            <code>0</code> jeżeli daty są równe<br/>
     *            <code>-1</code> jeżeli wcześniejsza jest jednak późniejsza
     */
    public static int compare(String datapozniejsza, String datawczesniejsza) {
        int rokP = Integer.parseInt(datapozniejsza.substring(0,4));
        int mcP = Integer.parseInt(datapozniejsza.substring(5, 7));
        int rokW = Integer.parseInt(datawczesniejsza.substring(0, 4));
        int mcW = Integer.parseInt(datawczesniejsza.substring(5, 7));
        return doCompare(rokP, mcP, rokW, mcW);
    }
    
    public static int compare(UmorzenieN um, WpisView wpisView) {
        int rokP = um.getRokUmorzenia();
        int mcP = um.getMcUmorzenia();
        int rokW = wpisView.getRokWpisu();
        int mcW = Integer.parseInt(wpisView.getMiesiacWpisu());
        return doCompare(rokP, mcP, rokW, mcW);
    }
    
    public static String aktualnaData() {
        DateTime dt = new DateTime();
        LocalDate dataRozrachunku = dt.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        String formattedDate = formatter.print(dataRozrachunku);
        return formattedDate;
    }
    
    public static String aktualnyRok() {
        String zwrot = "błąd";
        DateTime dt = new DateTime();
        LocalDate dataRozrachunku = dt.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        String formattedDate = formatter.print(dataRozrachunku);
        if (formattedDate.length() > 0) {
            zwrot = formattedDate.split("-")[0];
        }
        return zwrot;
    }
    
    public static String aktualnyMc() {
        String zwrot = "błąd";
        DateTime dt = new DateTime();
        LocalDate dataRozrachunku = dt.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        String formattedDate = formatter.print(dataRozrachunku);
        if (formattedDate.length() > 0) {
            zwrot = formattedDate.split("-")[1];
        }
        return zwrot;
    }
    
    public static String[] poprzedniOkres (String mc, String rok) {
        String[] poprzedniOkres = new String[2];
        if (mc.equals("01")) {
            poprzedniOkres[0] = "12";
            Integer rokInt = Integer.parseInt(rok);
            poprzedniOkres[1] = String.valueOf(rokInt-1);
        } else {
            Integer mcInt = Integer.parseInt(mc);
            poprzedniOkres[0] = String.valueOf(Mce.getNumberToMiesiac().get(mcInt-1));
            poprzedniOkres[1] = rok;
        }
        return poprzedniOkres;
    }
    
    public static String data_ddMMMMyyyy(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
	return sdf.format(date); 
    }
    
    public static String data_yyyyMMdd(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	return sdf.format(date); 
    }

    public static boolean czyjestpo(String dataOd, WpisView wpisView) {
        boolean zwrot = false;
        if (dataOd == null) {
            zwrot = false;
        } else {
            zwrot = czydatasiezawiera(dataOd, wpisView, true);
        }
        return zwrot;
    }

    public static boolean czyjestprzed(String dataDo, WpisView wpisView) {
        boolean zwrot = false;
        if (dataDo == null) {
            zwrot = true;
        } else {
            zwrot = czydatasiezawiera(dataDo, wpisView, false);
        }
        return zwrot;
    }
    
    private static boolean czydatasiezawiera(String data, WpisView wpisView, boolean przed0po1) {
        String rokdaty = pobierzrok(data);
        String mcdaty = pobierzmc(data);
        int wynikporównania;
        if (przed0po1) {
            wynikporównania  = compare(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), rokdaty, mcdaty);
        } else {
            wynikporównania  = compare(rokdaty, mcdaty, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        }
        return wynikporównania > -1;
    }

    private static String pobierzrok(String dataOd) {
        return pobierzokres(dataOd, 0);
    }
    
    private static String pobierzmc(String dataOd) {
        return pobierzokres(dataOd, 1);
    }
    
    private static String pobierzokres(String data, int pole) {
        String zwrot = null;
        if (data.length()==10) {
            zwrot = data.split("-")[1];
        }
        return zwrot;
    }
}
