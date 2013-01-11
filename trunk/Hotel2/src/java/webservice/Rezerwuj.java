/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import DAO.HotelDAO;
import entity.Pokoj;
import entity.Rezerwacja;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@WebService(serviceName = "Rezerwuj")
public class Rezerwuj {
    @EJB
    private HotelDAO ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "init")
    @Oneway
    public void init() {
        ejbRef.init();
    }

    @WebMethod(operationName = "getPokoje")
    public List<Pokoj> getPokoje() {
        return ejbRef.getPokoje();
    }

    @WebMethod(operationName = "setPokoje")
    @Oneway
    public void setPokoje(@WebParam(name = "pokoje") List<Pokoj> pokoje) {
        ejbRef.setPokoje(pokoje);
    }

    @WebMethod(operationName = "getRezerwacje")
    public List<Rezerwacja> getRezerwacje() {
        return ejbRef.getRezerwacje();
    }

    @WebMethod(operationName = "setRezerwacje")
    @Oneway
    public void setRezerwacje(@WebParam(name = "rezerwacje") List<Rezerwacja> rezerwacje) {
        ejbRef.setRezerwacje(rezerwacje);
    }

    @WebMethod(operationName = "getSessionFacade")
    public SessionFacade getSessionFacade() {
        return ejbRef.getSessionFacade();
    }

    @WebMethod(operationName = "setSessionFacade")
    @Oneway
    public void setSessionFacade(@WebParam(name = "sessionFacade") SessionFacade sessionFacade) {
        ejbRef.setSessionFacade(sessionFacade);
    }
    
}
