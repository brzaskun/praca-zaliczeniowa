/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Podstawki;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
public class PodstawkiFacade extends AbstractFacade<Podstawki> {
    @PersistenceContext(unitName = "npkpir_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PodstawkiFacade() {
        super(Podstawki.class);
    }
    
    public Podstawki findyear(Integer rok){
        Podstawki tmp = (Podstawki) em.createNamedQuery("Podstawki.findByRok").setParameter("rok", rok).getSingleResult();
        return tmp;
    }
}
