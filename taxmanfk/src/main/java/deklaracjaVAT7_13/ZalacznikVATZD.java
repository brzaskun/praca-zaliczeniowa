/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

/**
 *
 * @author Osito
 */
public class ZalacznikVATZD {
    static String ZalacznikVATZD;
    
    String WariantFormularza;
    String P_10;
    String P_11;
    

    public ZalacznikVATZD() {
        WariantFormularza = "1";
        P_10 = "1607687863";
        P_11 = "516525652";
        ZalacznikVATZD = "<ns:Zalaczniki><vzd:Wniosek_VAT-ZD><vzd:Naglowek><vzd:KodFormularza kodSystemowy=\"VAT-ZD (1)\" wersjaSchemy=\"1-0E\">VAT-ZD</vzd:KodFormularza><vzd:WariantFormularza>"+WariantFormularza
                +"</vzd:WariantFormularza></vzd:Naglowek><vzd:PozycjeSzczegolowe><vzd:P_10>"+P_10
                +"</vzd:P_10><vzd:P_11>"+P_11
                +"</vzd:P_11></vzd:PozycjeSzczegolowe></vzd:Wniosek_VAT-ZD></ns:Zalaczniki>";
    }

    public String getWariantFormularza() {
        return WariantFormularza;
    }

    public void setWariantFormularza(String WariantFormularza) {
        this.WariantFormularza = WariantFormularza;
    }

    public String getP_10() {
        return P_10;
    }

    public void setP_10(String P_10) {
        this.P_10 = P_10;
    }

    public String getP_11() {
        return P_11;
    }

    public void setP_11(String P_11) {
        this.P_11 = P_11;
    }

    public String getZalacznikVATZD() {
        return ZalacznikVATZD;
    }
    
}
