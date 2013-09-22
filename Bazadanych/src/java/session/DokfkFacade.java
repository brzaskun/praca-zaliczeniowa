/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Dokfk;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
public class DokfkFacade extends AbstractFacade<Dokfk> {
    @PersistenceContext(unitName = "BazadanychPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DokfkFacade() {
        super(Dokfk.class);
    }
    
    
}
