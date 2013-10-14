/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Wiersz;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
public class WierszFacade extends AbstractFacade<Wiersz> {
    @PersistenceContext(unitName = "BazadanychPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WierszFacade() {
        super(Wiersz.class);
    }
    
}
