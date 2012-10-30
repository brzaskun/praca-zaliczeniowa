/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Pitpoz;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
public class PitpozFacade extends AbstractFacade<Pitpoz> {
    @PersistenceContext(unitName = "npkpir_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PitpozFacade() {
        super(Pitpoz.class);
    }
    
}
