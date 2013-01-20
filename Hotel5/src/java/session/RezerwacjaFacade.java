/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Rezerwacja;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
public class RezerwacjaFacade extends AbstractFacade<Rezerwacja> {
    @PersistenceContext(unitName = "Hotel5PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RezerwacjaFacade() {
        super(Rezerwacja.class);
    }
    
}
