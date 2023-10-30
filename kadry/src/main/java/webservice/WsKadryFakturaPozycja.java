/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Osito
 */
@WebService(serviceName = "WsKadryFakturaPozycja")
public class WsKadryFakturaPozycja {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "kadryfakturapozycjamcrok")
    public WierszFaktury kadryfakturapozycjamcrok(@WebParam(name = "nip") String nip, @WebParam(name = "rok") String rok, @WebParam(name = "mc") String mc) {
        WierszFaktury zwrot = new WierszFaktury();
        zwrot.setId(1);
        zwrot.setRok(rok);
        zwrot.setMc(mc);
        zwrot.setIlosc(111111);
        zwrot.setOpis("krowa");
        zwrot.setSymbolwaluty("PLN");
        zwrot.setKwota(22);
        return zwrot;
    }
}
