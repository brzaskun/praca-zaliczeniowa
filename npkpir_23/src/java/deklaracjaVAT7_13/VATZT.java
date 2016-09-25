/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

import java.io.Serializable;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class VATZT implements Serializable{
    
    private static String vatzt;

    public VATZT() {
    }
    
    public VATZT(String kwota, String informacja, int innezalaczniki) {
        if (innezalaczniki == 0 ) {
            vatzt = "<Zalaczniki><vzt:Wniosek_VAT-ZT xmlns:vzt=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZT/\"><vzt:Naglowek><vzt:KodFormularza kodSystemowy=\"VAT-ZT (5)\" wersjaSchemy=\"2-0E\">VAT-ZT</vzt:KodFormularza><vzt:WariantFormularza>5</vzt:WariantFormularza></vzt:Naglowek><vzt:PozycjeSzczegolowe><vzt:P_10>"+kwota+"</vzt:P_10><vzt:P_11>"+informacja+"</vzt:P_11></vzt:PozycjeSzczegolowe></vzt:Wniosek_VAT-ZT></Zalaczniki>";
//          vatzt = "<Zalaczniki><vzt:Wniosek_VAT-ZT xmlns:vzt=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2012/08/28/eD/VATZT/\"><vzt:Naglowek><vzt:KodFormularza kodSystemowy=\"VAT-ZT (5)\" wersjaSchemy=\"1-0E\">VAT-ZT</vzt:KodFormularza><vzt:WariantFormularza>5</vzt:WariantFormularza></vzt:Naglowek><vzt:PozycjeSzczegolowe><vzt:P_10>"+kwota+"</vzt:P_10><vzt:P_11>"+informacja+"</vzt:P_11></vzt:PozycjeSzczegolowe></vzt:Wniosek_VAT-ZT></Zalaczniki>";
        } else {
            vatzt = "<vzt:Wniosek_VAT-ZT xmlns:vzt=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZT/\"><vzt:Naglowek><vzt:KodFormularza kodSystemowy=\"VAT-ZT (5)\" wersjaSchemy=\"2-0E\">VAT-ZT</vzt:KodFormularza><vzt:WariantFormularza>5</vzt:WariantFormularza></vzt:Naglowek><vzt:PozycjeSzczegolowe><vzt:P_10>"+kwota+"</vzt:P_10><vzt:P_11>"+informacja+"</vzt:P_11></vzt:PozycjeSzczegolowe></vzt:Wniosek_VAT-ZT>";
//          vatzt = "<vzt:Wniosek_VAT-ZT xmlns:vzt=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2012/08/28/eD/VATZT/\"><vzt:Naglowek><vzt:KodFormularza kodSystemowy=\"VAT-ZT (5)\" wersjaSchemy=\"1-0E\">VAT-ZT</vzt:KodFormularza><vzt:WariantFormularza>5</vzt:WariantFormularza></vzt:Naglowek><vzt:PozycjeSzczegolowe><vzt:P_10>"+kwota+"</vzt:P_10><vzt:P_11>"+informacja+"</vzt:P_11></vzt:PozycjeSzczegolowe></vzt:Wniosek_VAT-ZT>";
        }
    }

    public String getVatzt() {
        return vatzt;
    }

    public void setVatzt(String vatzt) {
        VATZT.vatzt = vatzt;
    }
    
    
}
