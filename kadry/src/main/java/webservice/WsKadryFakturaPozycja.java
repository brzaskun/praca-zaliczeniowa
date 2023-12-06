/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package webservice;

import dao.FirmaKadryFacade;
import dao.WierszFakturyFacade;
import entity.FirmaKadry;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Osito
 */
@WebService(serviceName = "WsKadryFakturaPozycja")
public class WsKadryFakturaPozycja implements Serializable {
    
    @Inject
    private WierszFakturyFacade wierszFakturyFacade;
    @Inject
    private FirmaKadryFacade firmaKadryFacade;

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
    public List<WierszFaktury> kadryfakturapozycjamcrok(@WebParam(name = "nip") String nip, @WebParam(name = "rok") String rok, @WebParam(name = "mc") String mc) {
        List<WierszFaktury> zwrotbaza = wierszFakturyFacade.findbyNipRokMc(nip, rok, mc);
        List<FirmaKadry> firmy = firmaKadryFacade.findAll();
        if (zwrotbaza.isEmpty()) {
            System.out.println("baza pusta");
        } else {
            System.out.println("pobrano pusta");
        }
        WierszFaktury zwrot = new WierszFaktury();
        zwrot.setId(1);
        zwrot.setRok(rok);
        zwrot.setMc(mc);
        zwrot.setIlosc(111111);
        zwrot.setOpis("krowa");
        zwrot.setSymbolwaluty("PLN");
        zwrot.setKwota(22);
        zwrot.setNazwa("KOPANA");
        zwrot.setNip("nipupupu");
        return zwrotbaza;
    }
}
