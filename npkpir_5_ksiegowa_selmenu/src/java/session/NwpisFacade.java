/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Nwpis;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
public class NwpisFacade extends AbstractFacade<Nwpis> {
    @PersistenceContext(unitName = "npkpir_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NwpisFacade() {
        super(Nwpis.class);
    }
    
}
