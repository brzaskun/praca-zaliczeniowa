/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pluginkadry;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class WsKadryFakturaPozycjaView implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @WebServiceRef(wsdlLocation = "http://localhost:8080/kadry/WsKadryFakturaPozycja")
    private WsKadryFakturaPozycja_Service wsKadryFakturaPozycja_Service;
    
    public void init() {
        try {
            wsKadryFakturaPozycja_Service = new WsKadryFakturaPozycja_Service();
            WsKadryFakturaPozycja wsKadryFakturaPozycjaPort = wsKadryFakturaPozycja_Service.getWsKadryFakturaPozycjaPort();
            String hello = wsKadryFakturaPozycjaPort.hello("lolo");
            System.out.println("odp: "+hello);
            List<WierszFaktury> wiersze = wsKadryFakturaPozycjaPort.kadryfakturapozycjamcrok("8513282893", "2023", "10");
            if (wiersze.isEmpty()==false) {
                System.out.println("odp: "+wiersze.get(0).nip+" nazwa: "+wiersze.get(0).nazwa);
            } else {
                System.out.println("odebrałem pusta baze");
            }
        } catch (Exception ex) {
            Logger.getLogger(WsKadryFakturaPozycjaView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
