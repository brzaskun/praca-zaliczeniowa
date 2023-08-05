/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservicefaktura;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.WebServiceRef;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class FakturyKsiegowoscView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private int iloscfaktur;
    @WebServiceRef
    private webservicefaktura.FakturaWS_Service fakturaWS_Service;
    
    @PostConstruct
    private void init() {
        FakturaWS fakturaWSPort = fakturaWS_Service.getFakturaWSPort();
        String hello = fakturaWSPort.hello("8511005008");
        System.out.println("pobrano "+hello);
    }

    public int getIloscfaktur() {
        return iloscfaktur;
    }

    public void setIloscfaktur(int iloscfaktur) {
        this.iloscfaktur = iloscfaktur;
    }
    
    
    

    
}
