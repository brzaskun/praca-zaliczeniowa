/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rezerwacja;

import entity.Hotel;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.HotelFacade;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class rez implements Serializable {
    
    @PersistenceContext
    EntityManager em;
    @EJB
    private HotelFacade hotelFacade;
    
    public Hotel[] getHotel(){
        List<Hotel> hotele = hotelFacade.findAll();
        return hotele.toArray(new Hotel[hotele.size()]);
    }
    
}
