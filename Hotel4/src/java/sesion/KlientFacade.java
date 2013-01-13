/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sesion;

import entity.Klient;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
public class KlientFacade extends AbstractFacade<Klient> {
    @PersistenceContext(unitName = "Hotel4PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KlientFacade() {
        super(Klient.class);
    }
    
}
