/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservicefaktura;

import dao.FakturaDAO;
import entity.Faktura;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Osito
 */
@WebService(serviceName = "FakturaWS")
@Stateless()
public class FakturaWS {
    @Inject
    private FakturaDAO fakturaDAO;
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "nip") String nip) {
        Faktura findByID = fakturaDAO.findByID(1);
        return "Hello nip " + findByID.getDatasprzedazy() + " !";
    }
}
