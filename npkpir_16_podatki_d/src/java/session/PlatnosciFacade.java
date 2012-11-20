/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Platnosci;
import entity.PlatnosciPK;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
public class PlatnosciFacade extends AbstractFacade<Platnosci> {
    @PersistenceContext(unitName = "npkpir_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlatnosciFacade() {
        super(Platnosci.class);
    }
    
    public Platnosci findPK(PlatnosciPK key) throws Exception{
        Platnosci tmp = (Platnosci) em.createNamedQuery("Platnosci.findByKey").setParameter("podatnik",key.getPodatnik()).setParameter("rok",key.getRok()).setParameter("miesiac",key.getMiesiac()).getSingleResult();
        return tmp;
    }
}
