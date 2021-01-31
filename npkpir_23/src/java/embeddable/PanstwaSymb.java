/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.SessionScoped;;
import javax.inject.Named;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Named
@Embeddable
@SessionScoped
public class PanstwaSymb implements Serializable {

    private final static List<String> wykazPanstwS;
    
static{
    wykazPanstwS = Collections.synchronizedList(new ArrayList<>());
    wykazPanstwS.add("AF");
    wykazPanstwS.add("AL");
    wykazPanstwS.add("DZ");
    wykazPanstwS.add("AD");
    wykazPanstwS.add("AO");
    wykazPanstwS.add("AI");
    wykazPanstwS.add("AQ");
    wykazPanstwS.add("AG");
    wykazPanstwS.add("AN");
    wykazPanstwS.add("SA");
    wykazPanstwS.add("AR");
    wykazPanstwS.add("AM");
    wykazPanstwS.add("AW");
    wykazPanstwS.add("AU");
    wykazPanstwS.add("AT");
    wykazPanstwS.add("AZ");
    wykazPanstwS.add("BS");
    wykazPanstwS.add("BH");
    wykazPanstwS.add("BD");
    wykazPanstwS.add("BB");
    wykazPanstwS.add("BE");
    wykazPanstwS.add("BZ");
    wykazPanstwS.add("BJ");
    wykazPanstwS.add("BM");
    wykazPanstwS.add("BT");
    wykazPanstwS.add("BY");
    wykazPanstwS.add("BO");
    wykazPanstwS.add("BA");
    wykazPanstwS.add("BW");
    wykazPanstwS.add("BR");
    wykazPanstwS.add("BN");
    wykazPanstwS.add("IO");
    wykazPanstwS.add("BG");
    wykazPanstwS.add("BF");
    wykazPanstwS.add("BI");
    wykazPanstwS.add("XC");
    wykazPanstwS.add("CL");
    wykazPanstwS.add("CN");
    wykazPanstwS.add("HR");
    wykazPanstwS.add("CY");
    wykazPanstwS.add("TD");
    wykazPanstwS.add("ME");
    wykazPanstwS.add("DK");
    wykazPanstwS.add("DM");
    wykazPanstwS.add("DO");
    wykazPanstwS.add("DJ");
    wykazPanstwS.add("EG");
    wykazPanstwS.add("EC");
    wykazPanstwS.add("ER");
    wykazPanstwS.add("EE");
    wykazPanstwS.add("ET");
    wykazPanstwS.add("FK");
    wykazPanstwS.add("FJ");
    wykazPanstwS.add("PH");
    wykazPanstwS.add("FI");
    wykazPanstwS.add("TF");
    wykazPanstwS.add("FR");
    wykazPanstwS.add("GA");
    wykazPanstwS.add("GM");
    wykazPanstwS.add("GH");
    wykazPanstwS.add("GI");
    wykazPanstwS.add("GR");
    wykazPanstwS.add("GD");
    wykazPanstwS.add("GL");
    wykazPanstwS.add("GE");
    wykazPanstwS.add("GU");
    wykazPanstwS.add("GY");
    wykazPanstwS.add("GT");
    wykazPanstwS.add("GN");
    wykazPanstwS.add("GQ");
    wykazPanstwS.add("GW");
    wykazPanstwS.add("HT");
    wykazPanstwS.add("ES");
    wykazPanstwS.add("HN");
    wykazPanstwS.add("HK");
    wykazPanstwS.add("IN");
    wykazPanstwS.add("ID");
    wykazPanstwS.add("IQ");
    wykazPanstwS.add("IR");
    wykazPanstwS.add("IE");
    wykazPanstwS.add("IS");
    wykazPanstwS.add("IL");
    wykazPanstwS.add("JM");
    wykazPanstwS.add("JP");
    wykazPanstwS.add("YE");
    wykazPanstwS.add("JO");
    wykazPanstwS.add("KY");
    wykazPanstwS.add("KH");
    wykazPanstwS.add("CM");
    wykazPanstwS.add("CA");
    wykazPanstwS.add("QA");
    wykazPanstwS.add("KZ");
    wykazPanstwS.add("KE");
    wykazPanstwS.add("KG");
    wykazPanstwS.add("KI");
    wykazPanstwS.add("CO");
    wykazPanstwS.add("KM");
    wykazPanstwS.add("CG");
    wykazPanstwS.add("CD");
    wykazPanstwS.add("KP");
    wykazPanstwS.add("XK");
    wykazPanstwS.add("CR");
    wykazPanstwS.add("QV");
    wykazPanstwS.add("CU");
    wykazPanstwS.add("KW");
    wykazPanstwS.add("LA");
    wykazPanstwS.add("LS");
    wykazPanstwS.add("LB");
    wykazPanstwS.add("LR");
    wykazPanstwS.add("LY");
    wykazPanstwS.add("LI");
    wykazPanstwS.add("LT");
    wykazPanstwS.add("LU");
    wykazPanstwS.add("LV");
    wykazPanstwS.add("MK");
    wykazPanstwS.add("MG");
    wykazPanstwS.add("YT");
    wykazPanstwS.add("MO");
    wykazPanstwS.add("MW");
    wykazPanstwS.add("MV");
    wykazPanstwS.add("MY");
    wykazPanstwS.add("ML");
    wykazPanstwS.add("MT");
    wykazPanstwS.add("MP");
    wykazPanstwS.add("MA");
    wykazPanstwS.add("MR");
    wykazPanstwS.add("MU");
    wykazPanstwS.add("MX");
    wykazPanstwS.add("XL");
    wykazPanstwS.add("FM");
    wykazPanstwS.add("UM");
    wykazPanstwS.add("MD");
    wykazPanstwS.add("MN");
    wykazPanstwS.add("MS");
    wykazPanstwS.add("MZ");
    wykazPanstwS.add("MM");
    wykazPanstwS.add("NA");
    wykazPanstwS.add("NR");
    wykazPanstwS.add("NP");
    wykazPanstwS.add("NL");
    wykazPanstwS.add("DE");
    wykazPanstwS.add("NE");
    wykazPanstwS.add("NG");
    wykazPanstwS.add("NI");
    wykazPanstwS.add("NU");
    wykazPanstwS.add("NF");
    wykazPanstwS.add("NO");
    wykazPanstwS.add("NC");
    wykazPanstwS.add("NZ");
    wykazPanstwS.add("PS");
    wykazPanstwS.add("OM");
    wykazPanstwS.add("PK");
    wykazPanstwS.add("PW");
    wykazPanstwS.add("PA");
    wykazPanstwS.add("PG");
    wykazPanstwS.add("PY");
    wykazPanstwS.add("PE");
    wykazPanstwS.add("PN");
    wykazPanstwS.add("PF");
    wykazPanstwS.add("PL");
    wykazPanstwS.add("GS");
    wykazPanstwS.add("PT");
    wykazPanstwS.add("CZ");
    wykazPanstwS.add("KR");
    wykazPanstwS.add("ZA");
    wykazPanstwS.add("CF");
    wykazPanstwS.add("RU");
    wykazPanstwS.add("RW");
    wykazPanstwS.add("RO");
    wykazPanstwS.add("SV");
    wykazPanstwS.add("WS");
    wykazPanstwS.add("AS");
    wykazPanstwS.add("SM");
    wykazPanstwS.add("SN");
    wykazPanstwS.add("RS");
    wykazPanstwS.add("SC");
    wykazPanstwS.add("SL");
    wykazPanstwS.add("SG");
    wykazPanstwS.add("SZ");
    wykazPanstwS.add("SK");
    wykazPanstwS.add("SI");
    wykazPanstwS.add("SO");
    wykazPanstwS.add("LK");
    wykazPanstwS.add("PM");
    wykazPanstwS.add("KN");
    wykazPanstwS.add("LC");
    wykazPanstwS.add("VC");
    wykazPanstwS.add("US");
    wykazPanstwS.add("SD");
    wykazPanstwS.add("SR");
    wykazPanstwS.add("SY");
    wykazPanstwS.add("CH");
    wykazPanstwS.add("SE");
    wykazPanstwS.add("SH");
    wykazPanstwS.add("TJ");
    wykazPanstwS.add("TH");
    wykazPanstwS.add("TW");
    wykazPanstwS.add("TZ");
    wykazPanstwS.add("TG");
    wykazPanstwS.add("TK");
    wykazPanstwS.add("TO");
    wykazPanstwS.add("TT");
    wykazPanstwS.add("TN");
    wykazPanstwS.add("TR");
    wykazPanstwS.add("TM");
    wykazPanstwS.add("TC");
    wykazPanstwS.add("TV");
    wykazPanstwS.add("UG");
    wykazPanstwS.add("UA");
    wykazPanstwS.add("UY");
    wykazPanstwS.add("UZ");
    wykazPanstwS.add("VU");
    wykazPanstwS.add("WF");
    wykazPanstwS.add("VA");
    wykazPanstwS.add("VE");
    wykazPanstwS.add("HU");
    wykazPanstwS.add("GB");
    wykazPanstwS.add("VN");
    wykazPanstwS.add("IT");
    wykazPanstwS.add("TL");
    wykazPanstwS.add("CI");
    wykazPanstwS.add("BV");
    wykazPanstwS.add("CX");
    wykazPanstwS.add("CK");
    wykazPanstwS.add("VI");
    wykazPanstwS.add("VG");
    wykazPanstwS.add("HM");
    wykazPanstwS.add("CC");
    wykazPanstwS.add("FO");
    wykazPanstwS.add("MH");
    wykazPanstwS.add("SB");
    wykazPanstwS.add("ST");
    wykazPanstwS.add("ZM");
    wykazPanstwS.add("QR");
    wykazPanstwS.add("CV");
    wykazPanstwS.add("ZW");
    wykazPanstwS.add("AE");


}

 public static List<String> getWykazPanstwS() {
     return wykazPanstwS;  
 }  

    public List<String> complete(String query) {  
        List<String> results = new ArrayList<String>();
        String kl = new String();
        for(String p : getWykazPanstwS()) {  
            if(p.startsWith(query)) {
                results.add(p);
            }
        }
        return results;
    }
   
    
}
