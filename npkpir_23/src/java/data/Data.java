/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import embeddable.Mce;
import entity.UmorzenieN;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
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
        Year currentYear = Year.of(Integer.parseInt(rok));
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
                if (currentYear.isLeap()) {
                    return rok + "-" + mc + "-29";
                } else {
                    return rok + "-" + mc + "-28";
                }
                
            default:
                return rok + "-" + mc + "-30";
        }
    }
    
    public static String ostatniDzien(WpisView wpisView) {
       return pobierzdzien(wpisView);
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
    
    public static String aktualnaDataCzas() {
        DateTime dt = new DateTime();
        LocalDate dataRozrachunku = dt.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        String formattedDate = formatter.print(dataRozrachunku);
        LocalTime czas = dt.toLocalTime();
        formatter = DateTimeFormat.forPattern("hh:mm");
        String formattedCzas = formatter.print(czas);
        String zwrot = formattedDate+" "+formattedCzas;
        return zwrot;
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
    
    public static String aktualnyRokShort() {
        String zwrot = "błąd";
        DateTime dt = new DateTime();
        LocalDate dataRozrachunku = dt.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        String formattedDate = formatter.print(dataRozrachunku);
        if (formattedDate.length() > 0) {
            zwrot = formattedDate.split("-")[0].substring(2, 4);
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
    /**
     * Generowanie pary poprzedni rok-mc
     * 
     * @param mc miesiac obecny
     * @param rok rok obecny
     * @return    array[0] mc poprzedni
     * @return    array[1] rok poprzedni
     */
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
        String zwrot = "";
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            zwrot = sdf.format(date);
        }
	return zwrot;
    }

    public static boolean czyjestpomiedzy (String dataOd, String dataDo, String rok, String mc) {
        return czyjestpo(dataOd, rok, mc) && czyjestprzed(dataDo, rok, mc);
    }
    
     
    public static boolean czyjestpo(String dataOd, String rok, String mc) {
        boolean zwrot = false;
        if (dataOd == null || dataOd.equals("")) {
            zwrot = false;
        } else {
            zwrot = czydatasiezawiera(dataOd, rok, mc, true);
        }
        return zwrot;
    }

    public static boolean czyjestprzed(String dataDo, String rok, String mc) {
        boolean zwrot = false;
        if (dataDo == null || dataDo.equals("")) {
            zwrot = true;
        } else {
            zwrot = czydatasiezawiera(dataDo, rok, mc, false);
        }
        return zwrot;
    }
    
    private static boolean czydatasiezawiera(String data, String rok, String mc, boolean przed0po1) {
        int wynikporównania = -1;
        try {
            String rokdaty = pobierzrok(data);
            String mcdaty = pobierzmc(data);
            
            if (przed0po1) {
                wynikporównania  = compare(rok, mc, rokdaty, mcdaty);
            } else {
                wynikporównania  = compare(rokdaty, mcdaty, rok, mc);
            }
        } catch (Exception e) {}
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
            zwrot = data.split("-")[pole];
        }
        return zwrot;
    }

    public static int mcedoproporcji(String datarozpoczecia, WpisView wpisView) {
        int zwrot = 12;
        String rokdata = datarozpoczecia.split("-")[2];
        String mcdata = datarozpoczecia.split("-")[2];
        if (Integer.parseInt(rokdata) > wpisView.getRokWpisu()) {
            zwrot = 0;
        } else if (Integer.parseInt(rokdata) == wpisView.getRokWpisu()) {
            zwrot = 13 - Integer.parseInt(mcdata);
        }
        return zwrot;
    }

    public static String getRok(String data) {
        String zwrot = "";
        if (data != null) {
            zwrot = getCzescDaty(data, 0);
        }
        return zwrot;
    }
    
    public static String getMc(String data) {
        String zwrot = "";
        if (data != null) {
            zwrot = getCzescDaty(data, 1);
        }
        return zwrot;
    }
    
    public static String getDzien(String data) {
        String zwrot = "";
        if (data != null) {
            zwrot = getCzescDaty(data, 2);
        }
        return zwrot;
    }
    
    public static String getCzescDaty(String data, int rok0mc1d2) {
        String zwrot = "";
        switch (rok0mc1d2) {
            case 0 :
                zwrot = data.substring(0,4);
                break;
            case 1 :
                zwrot = data.substring(5,7);
                break;
            case 2 :
                zwrot = data.substring(8,10);
                break;
        }
        return zwrot;
    }

    public static String dzienpierwszy(WpisView wpisView) {
        StringBuilder sb = new StringBuilder();
        sb.append(wpisView.getRokWpisuSt());
        sb.append("-");
        sb.append(wpisView.getMiesiacWpisu());
        sb.append("-");
        sb.append("01");
        return sb.toString();
    }

    public static String dzienostatni(WpisView wpisView) {
        return pobierzdzien(wpisView);
    }

    private static String pobierzdzien(WpisView wpisView) {
        String dzien = null;
        String mc = wpisView.getMiesiacWpisu();
        Integer rok = wpisView.getRokWpisu();
        int mod = Integer.parseInt(mc) % 2;
        Year currentYear = Year.of(rok);
        boolean jestprzestepny = currentYear.isLeap();
        if (mc.equals("02")) {
            if (jestprzestepny) {
                dzien = "29";
            } else {
                dzien = "28";
            }
        } else {
            if (mc.equals("07") || mc.equals("12")) {
                dzien = "31";
            } else if (mod==0) {
                dzien = "30";
            } else {
                dzien = "31";
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(rok);
        sb.append("-");
        sb.append(mc);
        sb.append("-");
        sb.append(dzien);
        return sb.toString();
   }
    
   public static int czyczterymiesiace(String datawplywu, String dataoperacji, String mcwpisu, String rokwpisu) {
       //fakture WNT z operacja w styczniu(data obowiazku podatkowego) mozna musi rozliczyc asymetrycznie nalezny/naliczony
       //w deklaracji za kwiecien jesli otrzymal ja do 25.maja lub za maj, jesli po 25.maja
       //jezeli koniecmca.dataotrzymania > koniecmca.dataoperacji+3 to
       //jezeli dzien.miesiac.dataotrzymania < 25.koniecmca.dataoperacji+4 to rozliczenie a mkoniecmca.dataoperacji+3
       //inaczej koniecmca.dataoperacji+4
       return Mce.odlegloscMcy(Data.getMc(dataoperacji), Data.getRok(dataoperacji), Data.getMc(datawplywu), Data.getRok(datawplywu));
   }
   public static int compareDay(String dateString1, String dateString2) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date date1 = format.parse(dateString1);
            Date date2 = format.parse(dateString2);
            return date1.compareTo(date2);
         } catch (ParseException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
   }
   
   public static XMLGregorianCalendar XMLGCinitRok(String rok) {
       XMLGregorianCalendar newXMLGregorianCalendar = null;
        try {
            newXMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            newXMLGregorianCalendar.setYear(Integer.parseInt(rok));
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newXMLGregorianCalendar;
   }
   
   public static XMLGregorianCalendar XMLGCinitMc(String mc) {
       XMLGregorianCalendar newXMLGregorianCalendar = null;
        try {
            newXMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            newXMLGregorianCalendar.setMonth(Integer.parseInt(mc));
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newXMLGregorianCalendar;
   }
   
   public static boolean sprawdzpoprawnoscdaty(String data) {
        boolean zwrot=true;
        try {
            String f = "yyyy-MM-dd";
            DateFormat format = new SimpleDateFormat(f);
            DatatypeFactory.newInstance().newXMLGregorianCalendar(f.format(data));
        } catch (Exception e) {
            zwrot=false;
        }
        return zwrot;
    }
   
    public static void main(String[] args) {
        try {
            String termin = "10-05-2012";
            String dzis = "11-05-2012";
            
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            
            Date date1 = format.parse(termin);
            Date date2 = format.parse(dzis);
            
            if (date1.compareTo(date2) <0) {
                System.out.println("red");
            }   } catch (ParseException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
}
//   public static void main(String[] args) {
//        String dzien = null;
//        String mc = "05";
//        Integer rok = 2017;
//        int mod = Integer.parseInt(mc) % 2;
//        Year currentYear = Year.of(rok);
//        boolean jestprzestepny = currentYear.isLeap();
//        if (mc.equals("02")) {
//            if (jestprzestepny) {
//                dzien = "29";
//            } else {
//                dzien = "28";
//            }
//        } else {
//            if (mc.equals("07")) {
//                dzien = "31";
//            } else if (mod==0) {
//                dzien = "30";
//            } else {
//                dzien = "31";
//            }
//        }
//        StringBuilder sb = new StringBuilder();
//        sb.append(rok);
//        sb.append("-");
//        sb.append(mc);
//        sb.append("-");
//        sb.append(dzien);
//   }
//   public static Date asDate(LocalDate localDate) {
//    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
//  }
//
//  public static Date asDate(LocalDateTime localDateTime) {
//    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
//  }
//
//  public static LocalDate asLocalDate(Date date) {
//    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
//  }
//
//  public static LocalDateTime asLocalDateTime(Date date) {
//    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
//  }
}
