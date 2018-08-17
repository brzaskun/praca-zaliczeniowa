/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

import embeddable.Daneteleadresowe;
import embeddable.Vatpoz;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author Osito
 */
class PodmiotFirma {
    static String Podmiot;
    
    
    String NIP;
    String Nazwa;
    String REGON;
    

    public PodmiotFirma(Vatpoz selected) {
        Daneteleadresowe adres = selected.getAdres();
        NIP = adres.getNIP();
        Nazwa = StringEscapeUtils.escapeXml(selected.getPodatnik());
        REGON = selected.getRegon();
        Integer rok = Integer.parseInt(selected.getRok());
        Integer mc = Integer.parseInt(selected.getMiesiac());
        if (rok <= 2018 && mc<7) {
            Podmiot = "<Podmiot1 rola=\"Podatnik\"><etd:OsobaNiefizyczna><etd:NIP>"+NIP
            +"</etd:NIP><etd:PelnaNazwa>"+Nazwa+"</etd:PelnaNazwa><etd:REGON>"
            +REGON+"</etd:REGON></etd:OsobaNiefizyczna></Podmiot1>"; 
        } else {
            Podmiot = "<Podmiot1 rola=\"Podatnik\"><OsobaNiefizyczna><NIP>"+NIP
            +"</NIP><PelnaNazwa>"+Nazwa+"</PelnaNazwa></OsobaNiefizyczna></Podmiot1>"; 
        }
    }

    public static String getPodmiot() {
        return Podmiot;
    }

    public static void setPodmiot(String Podmiot) {
        PodmiotFirma.Podmiot = Podmiot;
    }
    
    
}
