/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import javax.ejb.Stateless;
import javax.jws.WebService;
import pl.gov.mf.uslugibiznesowe.uslugidomenowe.ap.weryfikacjavat._2018._03._01.TKodWeryfikacjiVAT;
import pl.gov.mf.uslugibiznesowe.uslugidomenowe.ap.weryfikacjavat._2018._03._01.TWynikWeryfikacjiVAT;
import pl.gov.mf.uslugibiznesowe.uslugidomenowe.ap.weryfikacjavat._2018._03._01.WeryfikacjaVAT_Service;

/**
 *
 * @author Osito
 */
@WebService(serviceName = "WeryfikacjaVAT", portName = "BasicHttpBinding_WeryfikacjaVAT", endpointInterface = "pl.gov.mf.uslugibiznesowe.uslugidomenowe.ap.weryfikacjavat._2018._03._01.WeryfikacjaVAT", targetNamespace = "http://www.mf.gov.pl/uslugiBiznesowe/uslugiDomenowe/AP/WeryfikacjaVAT/2018/03/01", wsdlLocation = "WEB-INF/wsdl/NIPVATcheck/wsdlnipvat.wsdl")
@Stateless
public class NIPVATcheck {

    public static pl.gov.mf.uslugibiznesowe.uslugidomenowe.ap.weryfikacjavat._2018._03._01.TWynikWeryfikacjiVAT sprawdzNIP(java.lang.String nip) {
        WeryfikacjaVAT_Service w = new WeryfikacjaVAT_Service();
        TWynikWeryfikacjiVAT sprawdzNIP = new TWynikWeryfikacjiVAT();
        sprawdzNIP.setKod(TKodWeryfikacjiVAT.I);
        try {
            sprawdzNIP  = w.getBasicHttpBindingWeryfikacjaVAT().sprawdzNIP(nip);
        } catch (Exception e){}
        return sprawdzNIP;
    }
    
    public static void main(String[] args) {
        TWynikWeryfikacjiVAT sprawdzNIP = NIPVATcheck.sprawdzNIP("6751661857");
        System.out.println("wynik "+sprawdzNIP.getKomunikat());
        System.out.println("symbol "+sprawdzNIP.getKod().value());
    }
}
