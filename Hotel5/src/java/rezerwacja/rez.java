/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rezerwacja;

import entity.Hotel;
import entity.Pokoj;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.HotelFacade;
import session.PokojFacade;
import session.RezerwacjaFacade;
import session.StandardFacade;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class rez implements Serializable {
    
    @PersistenceContext
    EntityManager em;
    @EJB private HotelFacade hotelFacade;
    @EJB private PokojFacade pokojFacade;
    @EJB private StandardFacade standardFacade;
    @EJB private RezerwacjaFacade rezerwacjaFacade;
    
    public Hotel[] getHotele(){
        List<Hotel> hotele = hotelFacade.findAll();
        return hotele.toArray(new Hotel[hotele.size()]);
    }
    
    public Pokoj[] getPokoje(){
        List<Pokoj> pokoje = pokojFacade.findAll();
        return pokoje.toArray(new Pokoj[pokoje.size()]);
    }
}
