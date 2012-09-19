/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Dok;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
public class DokFacade extends AbstractFacade<Dok> {
    @PersistenceContext(unitName = "npkpir_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DokFacade() {
        super(Dok.class);
    }
    
   
}
