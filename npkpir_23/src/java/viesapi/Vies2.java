/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viesapi;

import eu.europa.ec.taxud.vies.services.checkvat.CheckVatPortType;
import eu.europa.ec.taxud.vies.services.checkvat.CheckVatService;
import javax.jws.WebService;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;


/**
 *
 * @author Osito
 */
@WebService(serviceName = "checkVatService", portName = "checkVatPort", endpointInterface = "eu.europa.ec.taxud.vies.services.checkvat.CheckVatPortType", targetNamespace = "urn:ec.europa.eu:taxud:vies:services:checkVat", wsdlLocation = "WEB-INF/wsdl/Vies2/checkVATService.wsdl")
public class Vies2 {
    

   public static ViesVatRegistration  checkVat(javax.xml.ws.Holder<java.lang.String> countryCode, javax.xml.ws.Holder<java.lang.String> vatNumber, 
           javax.xml.ws.Holder<javax.xml.datatype.XMLGregorianCalendar> requestDate, javax.xml.ws.Holder<Boolean> valid, javax.xml.ws.Holder<java.lang.String> name, javax.xml.ws.Holder<java.lang.String> address) throws ViesVatServiceException {
        CheckVatService chs = new CheckVatService();
        CheckVatPortType checkVatPort = chs.getCheckVatPort();
        try {
            checkVatPort.checkVat(countryCode, vatNumber, requestDate, valid, name, address);
        }
        catch (SOAPFaultException ex) {
            SOAPFault fault = ex.getFault();
            String faultKey = fault.getFaultString();
            String faultMessage = "vies.fault." + faultKey;
            throw new ViesVatServiceException(faultKey, countryCode + "-" + vatNumber + ": " + faultMessage);
        }
        catch (WebServiceException ex) {
            //LOG.error("{}-{} lookup failed", countryCode, vatNumber, ex);
            throw new ViesVatServiceException("WebServiceException", countryCode + "-" + vatNumber + ": " + ex.getMessage());
        }

        if (!valid.value) {
            return null;
        }
        ViesVatRegistration res = new ViesVatRegistration();
        res.setValid(true);
        res.setCountry(countryCode.value);
        res.setVatNumber(vatNumber.value);
        res.setRequestDate(requestDate.value.toGregorianCalendar().getTime());
        res.setName(name.value);
        res.setAddress(address.value);
        //LOG.info("{}-{} : {}", countryCode, vatNumber, res);
        return res;
    }

    public static void checkVatApprox(javax.xml.ws.Holder<java.lang.String> countryCode, javax.xml.ws.Holder<java.lang.String> vatNumber, javax.xml.ws.Holder<java.lang.String> traderName, javax.xml.ws.Holder<java.lang.String> traderCompanyType, javax.xml.ws.Holder<java.lang.String> traderStreet, javax.xml.ws.Holder<java.lang.String> traderPostcode, javax.xml.ws.Holder<java.lang.String> traderCity, java.lang.String requesterCountryCode, java.lang.String requesterVatNumber, javax.xml.ws.Holder<javax.xml.datatype.XMLGregorianCalendar> requestDate, javax.xml.ws.Holder<Boolean> valid, javax.xml.ws.Holder<java.lang.String> traderAddress, javax.xml.ws.Holder<java.lang.String> traderNameMatch, javax.xml.ws.Holder<java.lang.String> traderCompanyTypeMatch, javax.xml.ws.Holder<java.lang.String> traderStreetMatch, javax.xml.ws.Holder<java.lang.String> traderPostcodeMatch, javax.xml.ws.Holder<java.lang.String> traderCityMatch, javax.xml.ws.Holder<java.lang.String> requestIdentifier) {
        CheckVatService chs = new CheckVatService();
        CheckVatPortType checkVatPort = chs.getCheckVatPort();
        countryCode = new Holder<>("PL");
        vatNumber = new Holder<>("8511005008");
        checkVatPort.checkVatApprox(countryCode, vatNumber, traderName, traderCompanyType, traderStreet, traderPostcode, traderCity, requesterCountryCode, requesterVatNumber, requestDate, valid, traderAddress, traderNameMatch, traderCompanyTypeMatch, traderStreetMatch, traderPostcodeMatch, traderCityMatch, requestIdentifier);
    }
    
    
    public static ViesVatRegistration checkVatApproxSimpl(String kraj, String nip)  {
        javax.xml.ws.Holder<java.lang.String> countryCode = new Holder<>(kraj);
        javax.xml.ws.Holder<java.lang.String> vatNumber = new Holder<>(nip);
        javax.xml.ws.Holder<java.lang.String> traderName = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderCompanyType = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderStreet = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderPostcode = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderCity = new Holder<>();
        java.lang.String requesterCountryCode = "PL";
        java.lang.String requesterVatNumber = "8511005008";
        javax.xml.ws.Holder<javax.xml.datatype.XMLGregorianCalendar> requestDate = new Holder<>(data.Data.databiezaca());
        javax.xml.ws.Holder<Boolean> valid = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderAddress = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderNameMatch = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderCompanyTypeMatch = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderStreetMatch = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderPostcodeMatch = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderCityMatch = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> requestIdentifier = new Holder<>();
        CheckVatService chs = new CheckVatService();
        CheckVatPortType checkVatPort = chs.getCheckVatPort();
        ViesVatRegistration res = new ViesVatRegistration();
        res.setCountry(countryCode.value);
        res.setVatNumber(vatNumber.value);
        try {
            checkVatPort.checkVatApprox(countryCode, vatNumber, traderName, traderCompanyType, traderStreet, traderPostcode, traderCity, requesterCountryCode, requesterVatNumber, requestDate, valid, traderAddress, traderNameMatch, traderCompanyTypeMatch, traderStreetMatch, traderPostcodeMatch, traderCityMatch, requestIdentifier);
        }   catch (SOAPFaultException ex) {
            SOAPFault fault = ex.getFault();
            String faultKey = fault.getFaultString();
            //String faultMessage = "vies.fault." + faultKey;
            res.setValid(false);
            res.setUwagi(faultKey);
        }  catch (WebServiceException ex) {
            //LOG.error("{}-{} lookup failed", countryCode, vatNumber, ex);
            res.setValid(false);
            res.setUwagi("błąd programu");
        }

        if (valid.value!=null && valid.value==true) {
            res.setIdentifier(requestIdentifier.value);
            res.setValid(true);
            res.setRequestDate(requestDate.value.toGregorianCalendar().getTime());
            res.setName(traderName.value);
            res.setAddress(traderAddress.value);
            
            //LOG.info("{}-{} : {}", countryCode, vatNumber, res);
            //System.out.println("");
        }
        return res;
    }
    
    
    public static void main(String[] args) {
        javax.xml.ws.Holder<java.lang.String> traderName = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderCompanyType = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderStreet = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderPostcode = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderCity = new Holder<>();
        java.lang.String requesterCountryCode = "PL";
        java.lang.String requesterVatNumber = "8511005008";
        javax.xml.ws.Holder<javax.xml.datatype.XMLGregorianCalendar> requestDate = new Holder<>(data.Data.databiezaca());
        javax.xml.ws.Holder<Boolean> valid = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderAddress = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderNameMatch = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderCompanyTypeMatch = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderStreetMatch = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderPostcodeMatch = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> traderCityMatch = new Holder<>();
        javax.xml.ws.Holder<java.lang.String> requestIdentifier = new Holder<>();
        //try {
            //        try {
//            countryCode = new Holder<>("ES");
//            vatNumber = new Holder<>("B25058942");
//            //checkVat(countryCode, vatNumber,requestDate, valid, traderName, traderAddress);
//        } catch (ViesVatServiceException ex) {
//            java.util.logging.Logger.getLogger(Vies2.class.getName()).log(Level.SEVERE, null, ex);
//        }
//checkVatApprox(countryCode, vatNumber, traderName, traderCompanyType, traderStreet, traderPostcode, traderCity, requesterCountryCode, requesterVatNumber, requestDate, valid, traderAddress, traderNameMatch, traderCompanyTypeMatch, traderStreetMatch, traderPostcodeMatch, traderCityMatch, requestIdentifier);
checkVatApproxSimpl("DE", "197456281");
//        } catch (ViesVatServiceException ex) {
//            java.util.logging.Logger.getLogger(Vies2.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        error.E.s("wynik "+sprawdzNIP.getKomunikat());
//        error.E.s("symbol "+sprawdzNIP.getKod().value());
System.out.println("");
    }

   
}

