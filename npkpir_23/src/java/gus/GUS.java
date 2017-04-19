/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gus;

import javax.jws.WebService;
import javax.xml.ws.BindingType;

/**
 *
 * @author Osito
 */
@WebService(serviceName = "UslugaBIRzewnPubl", portName = "e3", endpointInterface = "org.tempuri.IUslugaBIRzewnPubl", targetNamespace = "http://tempuri.org/", wsdlLocation = "WEB-INF/wsdl/GUS/UslugaBIRzewnPubl1.wsdl")
@BindingType(value = "http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")
public class GUS {

    public java.lang.String pobierzCaptcha() {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public java.lang.Boolean sprawdzCaptcha(java.lang.String pCaptcha) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.lang.String getValue(java.lang.String pNazwaParametru) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.lang.String setValue(java.lang.String pNazwaParametru, java.lang.String pWartoscParametru) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.lang.String zaloguj(java.lang.String pKluczUzytkownika) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.lang.Boolean wyloguj(java.lang.String pIdentyfikatorSesji) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.lang.String daneSzukaj(cis.bir.publ._2014._07.datacontract.ParametryWyszukiwania pParametryWyszukiwania) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.lang.String danePobierzPelnyRaport(java.lang.String pRegon, java.lang.String pNazwaRaportu) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.lang.String daneKomunikat() {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
