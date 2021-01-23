/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import data.Data;
import java.io.Serializable;
import java.util.Base64;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;
import zuszla.PobierzRaporty;
import zuszla.PobierzRaportyResponse;
import zuszla.WsdlPlatnikRaportyZlaPortType;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ZusZLAView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
   @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/zuszla.wsdl")
    private zuszla.WsdlPlatnikRaportyZla wsdlPlatnikRaportyZla;
    
   public void zuszla() {
       try {
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
        new javax.net.ssl.HostnameVerifier(){

            public boolean verify(String hostname,
                    javax.net.ssl.SSLSession sslSession) {
                return hostname.equals("193.105.143.40");
            }
        });
        PobierzRaporty parameters = new PobierzRaporty();
        parameters.setNip("8511005008");
        parameters.setLogin("a.barczyk@taxman.biz.pl");
        parameters.setHaslo("Taxman2810*");
        String nowadata = Data.odejmijdniDzis(30);
        parameters.setDataOd(Data.dataoddo(nowadata));
        WsdlPlatnikRaportyZlaPortType port = wsdlPlatnikRaportyZla.getZusChannelPlatnikRaportyZlaWsdlPlatnikRaportyZlaPort();
        BindingProvider prov = (BindingProvider) port;
        prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "b2b_platnik_raporty_zla");
        prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "b2b_platnik_raporty_zla");
        PobierzRaportyResponse pobierzRaporty = port.pobierzRaporty(parameters);
        zuszla.Raporty rap = pobierzRaporty.getRaporty();
        zuszla.Raport raport = rap.getRaport().get(0);
        byte[] encodedString = Base64.getDecoder().decode(raport.getZawartosc());
        
        System.out.println("");
       } catch (Exception e) {
           System.out.println("");
       }
   }
}
