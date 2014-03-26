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
public class ORDZU implements Serializable{
    private String ordzu;

    public ORDZU() {
    }

    public ORDZU(String oswiadczenie, int innezalaczniki) {
         if (innezalaczniki == 0 ) {
            ordzu = "<Zalaczniki><zzu:Zalacznik_ORD-ZU xmlns:zzu=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2011/10/07/eD/ORDZU/\"><zzu:Naglowek><zzu:KodFormularza kodSystemowy=\"ORD-ZU (2)\" wersjaSchemy=\"2-0E\">ORD-ZU</zzu:KodFormularza><zzu:WariantFormularza>2</zzu:WariantFormularza></zzu:Naglowek><zzu:PozycjeSzczegolowe><zzu:P_13>"+oswiadczenie+"</zzu:P_13></zzu:PozycjeSzczegolowe></zzu:Zalacznik_ORD-ZU></Zalaczniki>";
         } else {
            ordzu = "<zzu:Zalacznik_ORD-ZU xmlns:zzu=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2011/10/07/eD/ORDZU/\"><zzu:Naglowek><zzu:KodFormularza kodSystemowy=\"ORD-ZU (2)\" wersjaSchemy=\"2-0E\">ORD-ZU</zzu:KodFormularza><zzu:WariantFormularza>2</zzu:WariantFormularza></zzu:Naglowek><zzu:PozycjeSzczegolowe><zzu:P_13>"+oswiadczenie+"</zzu:P_13></zzu:PozycjeSzczegolowe></zzu:Zalacznik_ORD-ZU>"; 
         }
    }
    
    

    public String getOrdzu() {
        return ordzu;
    }

    public void setOrdzu(String ordzu) {
        this.ordzu = ordzu;
    }

}
