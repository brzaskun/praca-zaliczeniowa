/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Rezerwuj;

import entity.Hotel;
import entity.Klient;
import entity.Pokoj;
import entity.Rezerwacja;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@ManagedBean
public class Rezerwuj implements Serializable {
    @Inject private SessionFacade sessionFacade;
    private Rezerwacja rezerwacja;
    @Inject private Klient klient;
    @Inject private Pokoj pokoj;
    @Inject private Hotel hotel;

    public Rezerwuj() {
        klient = new Klient();
    }
    
    
    
    public int sprawdzklienta(){
        Klient tmp = null;
        try {
            tmp = sessionFacade.findklient(klient.getImie(),klient.getNazwisko(),klient.getNrkarty());
            return tmp.getId();
        } catch (Exception e){
            sessionFacade.create(klient);
        }
        return 0;
    }

    public SessionFacade getSessionFacade() {
        return sessionFacade;
    }

    public void setSessionFacade(SessionFacade sessionFacade) {
        this.sessionFacade = sessionFacade;
    }

    public Rezerwacja getRezerwacja() {
        return rezerwacja;
    }

    public void setRezerwacja(Rezerwacja rezerwacja) {
        this.rezerwacja = rezerwacja;
    }

    public Klient getKlient() {
        return klient;
    }

    public void setKlient(Klient klient) {
        this.klient = klient;
    }

    public Pokoj getPokoj() {
        return pokoj;
    }

    public void setPokoj(Pokoj pokoj) {
        this.pokoj = pokoj;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    
    
}
