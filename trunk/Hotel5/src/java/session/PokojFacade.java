/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Pokoj;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
public class PokojFacade extends AbstractFacade<Pokoj> {
    @PersistenceContext(unitName = "Hotel5PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PokojFacade() {
        super(Pokoj.class);
    }
    
}
