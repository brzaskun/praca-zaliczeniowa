/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import embeddable.Mce;
import entity.Dzien;
import entity.Kalendarzmiesiac;
import error.E;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Named;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
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
    
    public static String ostatniDzienKalendarz(Kalendarzmiesiac kalendarz) {
        return ostatniDzien(kalendarz.getRok(), kalendarz.getMc());
    }
    
    
    public static String pierwszyDzien(String data) {
        String rok = getRok(data);
        String mc = getMc(data);
        return rok+"-"+mc+"-01";
    }
    
    public static String pierwszyDzien(String rok, String mc) {
        return rok+"-"+mc+"-01";
    }
    
    public static String pierwszyDzienKalendarz(Kalendarzmiesiac kalendarz) {
        return kalendarz.getRok()+"-"+kalendarz.getMc()+"-01";
    }
    
    public static String pierwszyDzien(WpisView wpisView) {
        String rok = wpisView.getRokWpisu();
        String mc = wpisView.getMiesiacWpisu();
        return rok+"-"+mc+"-01";
    }
    
    public static String ostatniDzien(WpisView wpisView) {
       return ostatniDzien(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
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
    
    /**
     * Porównywanie dwóch rokow i mce. Przyjmuje integer
     * 
     * @param rokP rok późniejszy
     * @param mcP miesiąc poźniejszy
     * @param dcP dzień poźniejszy
     * @param rokW rok wcześniejszy
     * @param mcW miesiąc wcześniejszy
     * @param dcW dzień wcześniejszy
     * @return    <code>1</code> jeżeli data póżniejsza jest późniejsza jest wcześniejszej <br/>
     *            <code>0</code> jeżeli lata i mce są równe<br/>
     *            <code>-1</code> jeżeli wcześniejsza jest jednak późniejsza
     */
    private static int doCompare(int rokP, int mcP, int dcP, int rokW, int mcW, int dcW) {
         if (rokP < rokW) {
            return -1;
        } else if (rokP > rokW) {
            return 1;
        } else if (rokP == rokW) {
            if (mcP < mcW) {
                return -1;
            } else if (mcP > mcW){
                return 1;
            } if (mcP == mcW) {
                if (dcP < dcW) {
                    return -1;
                } else if (dcP > dcW){
                    return 1;
                } if (dcP == dcW) {
                   return 0;
                }
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
        int dcP = Integer.parseInt(datapozniejsza.substring(8, 10));
        int rokW = Integer.parseInt(datawczesniejsza.substring(0, 4));
        int mcW = Integer.parseInt(datawczesniejsza.substring(5, 7));
        int dcW = Integer.parseInt(datawczesniejsza.substring(8, 10));
        return doCompare(rokP, mcP, dcP, rokW, mcW, dcW);
    }
    
    
    public static int compare(String[] um, WpisView wpisView) {
        int rokP = Integer.parseInt(um[1]);
        int mcP = Integer.parseInt(um[0]);
        int rokW = wpisView.getRokWpisuInt();
        int mcW = Integer.parseInt(wpisView.getMiesiacWpisu());
        return doCompare(rokP, mcP, rokW, mcW);
    }
    
    public static String aktualnaData() {
        LocalDate dt = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = dt.format(formatter);
        return formattedDate;
    }
    
    public static String aktualnaDataCzas() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String zwrot = formatter.format(dt);
        return zwrot;
    }
    
    public static String aktualnyRok() {
        String zwrot = "błąd";
        LocalDate dt = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = dt.format(formatter);
        if (formattedDate.length() > 0) {
            zwrot = formattedDate.split("-")[0];
        }
        return zwrot;
    }
    
    public static String aktualnyRokShort() {
        String zwrot = "błąd";
        LocalDate dt = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = dt.format(formatter);
        if (formattedDate.length() > 0) {
            zwrot = formattedDate.split("-")[0].substring(2, 4);
        }
        return zwrot;
    }
    
    public static String aktualnyMc() {
        String zwrot = "błąd";
        LocalDate dt = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = dt.format(formatter);
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
    
    public static String[] poprzedniOkres(Kalendarzmiesiac p) {
        return poprzedniOkres(p.getMc(), p.getRok());
    }
    
    public static String[] nastepnyOkres (String mc, String rok) {
        String[] nastepnyOkres = new String[2];
        if (mc.equals("12")) {
            nastepnyOkres[0] = "01";
            Integer rokInt = Integer.parseInt(rok);
            nastepnyOkres[1] = String.valueOf(rokInt+1);
        } else {
            Integer mcInt = Integer.parseInt(mc);
            nastepnyOkres[0] = String.valueOf(Mce.getNumberToMiesiac().get(mcInt+1));
            nastepnyOkres[1] = rok;
        }
        return nastepnyOkres;
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
    
    public static String data_yyyyMMddNull(Date date) {
        String zwrot = null;
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            zwrot = sdf.format(date);
        }
	return zwrot;
    }

    public static boolean czyjestpomiedzy (String dataOd, String dataDo, String rok, String mc) {
        return czyjestpo(dataOd, rok, mc) && czyjestprzed(dataDo, rok, mc);
    }
    
    //chodzi o to czy okres data jest po jakiesc dacie
    public static boolean czyjestpo(String termingraniczny, String badanadata) {
        boolean zwrot = false;
        if (termingraniczny == null || termingraniczny.equals("")) {
            zwrot = false;
        } else {
            zwrot = czydatasiezawiera(termingraniczny, badanadata, true);
        }
        return zwrot;
    }
    
    //chodzi o to czy okres data jest po jakiesc dacie
    public static boolean czyjestprzed(String termingraniczny, String badanadata) {
        boolean zwrot = false;
        if (termingraniczny == null || termingraniczny.equals("")) {
            zwrot = false;
        } else {
            zwrot = czydatasiezawiera(termingraniczny, badanadata, false);
        }
        return zwrot;
    }
    
    public static boolean czyjestpo(Date data, String rokl, String mcl) {
        boolean zwrot = false;
        String rok = Data.getRok(Data.data_yyyyMMdd(data));
        String mc = Data.getMc(Data.data_yyyyMMdd(data));
        String ostatnidzien = ostatniDzien(rokl, mcl);
        zwrot = czyjestpo(ostatnidzien, rok,mc);
        return zwrot;
    }
    
    //chodzi o to czy okres MC, ROK jest po jakiesc dacie
    public static boolean czyjestpo(String termingraniczny, String rokbadanegookresu, String mcbadanegookresu) {
        boolean zwrot = false;
        if (termingraniczny == null || termingraniczny.equals("")) {
            zwrot = false;
        } else {
            zwrot = czydatasiezawiera(termingraniczny, rokbadanegookresu, mcbadanegookresu, true);
        }
        return zwrot;
    }
    /**
     * @param mcgraniczny mcgraniczny
     * @param rokgraniczny rokgraniczny
     * @param mcbadanegookresu mcbadanegookresu
     * @param rokbadanegookresu rokbadanegookresu
     * robi po i ten sam
     */
    public static boolean czyjestpomc(String mcgraniczny, String rokgraniczny, String mcbadanegookresu, String rokbadanegookresu) {
        int wynikporównania = -1;
        wynikporównania  = compare(rokbadanegookresu, mcbadanegookresu,rokbadanegookresu, mcgraniczny);
        return wynikporównania > -1;
    }
    
    public static boolean czyjestpomcnaprawdepo(String mcgraniczny, String rokgraniczny, String mcbadanegookresu, String rokbadanegookresu) {
        int wynikporównania = -1;
        wynikporównania  = compare(rokbadanegookresu, mcbadanegookresu,rokgraniczny, mcgraniczny);
        return wynikporównania > 0;
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
    
    private static boolean czydatasiezawiera(String termingraniczny, String badanadata, boolean przed0po1) {
        int wynikporównania = -1;
        try {
            if (przed0po1) {
                wynikporównania  = compare(badanadata, termingraniczny);
            } else {
                wynikporównania  = compare(termingraniczny, badanadata);
            }
        } catch (Exception e) {}
        return wynikporównania > -1;
    }
    
     public static boolean czydatajestwmcu(String data, String rok, String mc) {
        boolean zwrot = false;
        try {
            String rokdaty = pobierzrok(data);
            String mcdaty = pobierzmc(data);
            if (rok.equals(rokdaty) && mc.equals(mcdaty)) {
                zwrot = true;
            }
        } catch (Exception e) {
        }
        return zwrot;
     }

    private static String pobierzrok(String dataOd) {
        return pobierzokres(dataOd, 0);
    }
    
    private static String pobierzmc(String dataOd) {
        return pobierzokres(dataOd, 1);
    }
    
    private static String pobierzokres(String data, int pole) {
        String zwrot = null;
        if (data.length()>=8) {
            zwrot = data.split("-")[pole];
        }
        return zwrot;
    }

    public static int mcedoproporcji(String datarozpoczecia, WpisView wpisView) {
        int zwrot = 12;
        String rokdata = datarozpoczecia.split("-")[2];
        String mcdata = datarozpoczecia.split("-")[2];
        if (Integer.parseInt(rokdata) > wpisView.getRokWpisuInt()) {
            zwrot = 0;
        } else if (Integer.parseInt(rokdata) == wpisView.getRokWpisuInt()) {
            zwrot = 13 - Integer.parseInt(mcdata);
        }
        return zwrot;
    }

    public static String[] getSplitted(String dataWyst) {
        String[] zwrot = new String[3];
        if (dataWyst!=null) {
            zwrot[0] = getRok(dataWyst);
            zwrot[1] = getMc(dataWyst);
            zwrot[2] = getDzien(dataWyst);
        } else {
            zwrot[0] = getRok("2018-05-01");
            zwrot[1] = getMc("2018-05-01");
            zwrot[2] = getDzien("2018-05-01");
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
    
    public static Integer getDzienI(String data) {
        String zwrot = "";
        if (data != null) {
            zwrot = getCzescDaty(data, 2);
        }
        return Integer.parseInt(zwrot);
    }
    
    public static String getCzescDaty(String data, int rok0mc1d2rokmc3) {
        String zwrot = "";
        switch (rok0mc1d2rokmc3) {
            case 0 :
                zwrot = data.substring(0,4);
                break;
            case 1 :
                zwrot = data.substring(5,7);
                break;
            case 2 :
                zwrot = data.substring(8,10);
                break;
            case 3 :
                zwrot = data.substring(0,8);
                break;
        }
        return zwrot;
    }

    public static String dzienpierwszy(WpisView wpisView) {
        StringBuilder sb = new StringBuilder();
        sb.append(wpisView.getRokWpisu());
        sb.append("-");
        sb.append(wpisView.getMiesiacWpisu());
        sb.append("-");
        sb.append("01");
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
            // Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
   }
   
   public static XMLGregorianCalendar XMLGCinitRok(String rok) {
       XMLGregorianCalendar newXMLGregorianCalendar = null;
        try {
            newXMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            newXMLGregorianCalendar.setYear(Integer.parseInt(rok));
        } catch (DatatypeConfigurationException ex) {
            // Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newXMLGregorianCalendar;
   }
   
   public static XMLGregorianCalendar XMLGCinitRokMc(String rok, String mc) {
       XMLGregorianCalendar newXMLGregorianCalendar = null;
        try {
            newXMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            newXMLGregorianCalendar.setYear(Integer.parseInt(rok));
            newXMLGregorianCalendar.setMonth(Integer.parseInt(mc));
        } catch (DatatypeConfigurationException ex) {
            // Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newXMLGregorianCalendar;
   }
   
   public static XMLGregorianCalendar XMLGCinitMc(String mc) {
       XMLGregorianCalendar newXMLGregorianCalendar = null;
        try {
            newXMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            newXMLGregorianCalendar.setMonth(Integer.parseInt(mc));
        } catch (DatatypeConfigurationException ex) {
            // Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newXMLGregorianCalendar;
   }
   
   public static String calendarToString(XMLGregorianCalendar x) {
       Date date = x.toGregorianCalendar().getTime();
       return data_yyyyMMdd(date);
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
 
    public static XMLGregorianCalendar databiezaca() {
        GregorianCalendar gcal = new GregorianCalendar();
        XMLGregorianCalendar zwrot = null;
        try {
            zwrot = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal).normalize();
        } catch (Exception ex) {
            E.e(ex);
        }
        return zwrot;
    }
    
    public static String odejmijdni(String databiezaca, int iloscdni) {
        return LocalDate.parse(databiezaca).minusDays(iloscdni).toString();
    }
    
    public static String dodajdni(String databiezaca, int iloscdni) {
        return LocalDate.parse(databiezaca).plusDays(iloscdni).toString();
    }
    
    public static String odejmijdniDzis(int iloscdni) {
        return LocalDate.now().minusDays(iloscdni).toString();
    }
            
            
    public static XMLGregorianCalendar dataoddo(String data) {
        String f = "yyyy-MM-dd";
        DateFormat format = new SimpleDateFormat(f);
        XMLGregorianCalendar newXMLGregorianCalendar = null;
        try {
             newXMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(f.format(data));
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newXMLGregorianCalendar;
    }
    
    public static String zmienkolejnosc(String zladata) {
        String dobradata = "";
        if (zladata.length()==10 && !dataodprawejdolewej(zladata)) {
            zladata = zladata.replace("/", "-");
            zladata = zladata.replace(".", "-");
            String rok = pobierzokres(zladata, 2);
            String mc = pobierzokres(zladata, 1);
            String dzien = pobierzokres(zladata, 0);
            StringBuilder sb = new StringBuilder();
            sb.append(rok);
            sb.append("-");
            sb.append(mc);
            sb.append("-");
            sb.append(dzien);
            dobradata = sb.toString();
        } else {
            dobradata = zladata;
        }
        return dobradata;
    }
    
    public static String zmienkolejnosc8(String zladata) {
        String dobradata = "";
        if (zladata.length()==8 && !dataodprawejdolewej(zladata)) {
            zladata = zladata.replace("/", "-");
            zladata = zladata.replace(".", "-");
            String rok = pobierzokres(zladata, 2);
            rok = "20"+rok;
            String mc = pobierzokres(zladata, 1);
            String dzien = pobierzokres(zladata, 0);
            StringBuilder sb = new StringBuilder();
            sb.append(rok);
            sb.append("-");
            sb.append(mc);
            sb.append("-");
            sb.append(dzien);
            dobradata = sb.toString();
        } else if (zladata.length()==10) {
            dobradata = zladata;
        }
        return dobradata;
    }
    
    public static String zmienkolejnoscUS(String zladata) {
        String dobradata = "";
        if (dataamerico(zladata)) {
            zladata = zladata.replace("/", "-");
            zladata = zladata.replace(".", "-");
            String rok = pobierzokres(zladata, 2);
            String mc = pobierzokres(zladata, 0);
            String dzien = pobierzokres(zladata, 1);
            StringBuilder sb = new StringBuilder();
            sb.append(rok);
            sb.append("-");
            mc = mc.length()==2? mc:"0"+mc;
            sb.append(mc);
            sb.append("-");
            dzien = dzien.length()==2? dzien:"0"+dzien;
            sb.append(dzien);
            dobradata = sb.toString();
        } else {
            dobradata = zladata;
        }
        return dobradata;
    }
    
    private static boolean dataodprawejdolewej(String zladata) {
        String wzor = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))";
        Pattern p = Pattern.compile(wzor);//<-- compile( not Compile(
        Matcher m = p.matcher(zladata);
        return m.find();
    }
    
    private static boolean dataamerico(String zladata) {
        String wzor = "(0?[1-9]|1[012])([\\/\\-])(0?[1-9]|[12]\\d|3[01])\\2(\\d{4})";
        Pattern p = Pattern.compile(wzor);//<-- compile( not Compile(
        Matcher m = p.matcher(zladata);
        return m.find();
    }
    
    public static Date stringToDate(String data) {
        Date zwrot = null;
        String dobradata = zmienkolejnosc(data);
        try {
            zwrot = (new SimpleDateFormat("yyyy-MM-dd")).parse(dobradata);
        } catch (ParseException ex) {
            E.e(ex);
        }
        return zwrot;
    }
    /**
     * Generowanie pary poprzedni rok-mc
     * 
     * @param dataod, dataod
     * @param datado datado
     */
    public static int iletodniKalendarzowych(String dataod, String datado) {
        LocalDate dzienzakonczenia = LocalDate.parse(dataod);
        LocalDate dzienrozpoczecia = LocalDate.parse(datado);
        double between = DAYS.between(dzienzakonczenia, dzienrozpoczecia)+1;
        return (int) between;
    }
    
    public static int iletodniRoboczych(String dataod, String datado, List<Dzien> dnilista) {
        int dzienod = Data.getDzienI(dataod);
        int dziendo = Data.getDzienI(datado);
        int dninieobecnoscirobocze = 0;
        for (Dzien p : dnilista) {
            if (p.getTypdnia()==0 && p.getNrdnia()>=dzienod &&p.getNrdnia()<=dziendo) {
                dninieobecnoscirobocze++;
            }
        }
        return dninieobecnoscirobocze;
    }
    
    public static String pelnadata(Kalendarzmiesiac kal, int nrdnia) {
        String zwrot = null;
        if (kal!=null) {
            if (nrdnia <10) {
                zwrot = kal.getRok()+"-"+kal.getMc()+"-0"+nrdnia;
            } else {
                zwrot = kal.getRok()+"-"+kal.getMc()+"-"+nrdnia;
            }
        }
        return zwrot;
    }
//    public static void main(String[] args) {
//        try {
//            String termin = "10-05-2012";
//            String dzis = "2012-05-02";
//            String wzor = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))";
//            Pattern p = Pattern.compile(wzor);//<-- compile( not Compile(
//            Matcher m = p.matcher(termin);
//            boolean find = m.find();
//            error.E.s(" s "+find);
//            m = p.matcher(dzis);
//            find = m.find();
//            error.E.s(" s "+find);
//            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//            
//            Date date1 = format.parse(termin);
//            Date date2 = format.parse(dzis);
//            
//            if (date1.compareTo(date2) <0) {
//                error.E.s("red");
//            }
//        } catch (Exception ex) {
//            // Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
//        }
//}
    
//    public static void main(String[] args) {
//        try {
//            String termin = "2019-12-29";
//            String dzis = "2019-12-28";
//            boolean zwrot = czyjestpo(termin, dzis);
//            if (zwrot) {
//                error.E.s("TRUE");
//            } else {
//                error.E.s("FALSE");
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
//        }
//}
    
     public static void main(String[] args) {
         String zwrot = aktualnaDataCzas();
         System.out.println(zwrot);
         String zwrot2=data_yyyyMMdd(new Date());
         System.out.println(zwrot2);
         LocalDate date = LocalDate.parse("2018-05-05");
         System.out.println(date.toString());
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

    public static boolean jestrownywiekszy(String mcbadany, String mcod) {
        int mcbadanyi = Integer.valueOf(mcbadany);
        int mcodi = Integer.valueOf(mcod);
        boolean zwrot = false;
        if (mcbadanyi>=mcodi) {
            zwrot = true;
        }
        return zwrot;
    }

    public static String zrobdate(int nrdnia, String mc, String rok) {
        String dzien = String.valueOf(nrdnia);
        if (dzien.length()==1) {
            dzien = "0"+dzien;
        }
        dzien = rok+"-"+mc+"-"+dzien;
        return dzien;
    }

    
     public static String dodajdzien(String data, int l) {
        LocalDate datal = LocalDate.parse(data);
        LocalDate tomorrow = datal.plusDays(l);  
        return tomorrow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
     }
     
     public static String odejmijdzien(String data, int l) {
        LocalDate datal = LocalDate.parse(data);
        LocalDate tomorrow = datal.minusDays(l);  
        return tomorrow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
     }
    

    

    

    

    
}
