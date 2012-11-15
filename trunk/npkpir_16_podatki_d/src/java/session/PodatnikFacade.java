/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Podatnik;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
public class PodatnikFacade extends AbstractFacade<Podatnik> {
    @PersistenceContext(unitName = "npkpir_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PodatnikFacade() {
        super(Podatnik.class);
    }
    
    public Podatnik findNP(String np){
        Podatnik tmp = (Podatnik) em.createNamedQuery("Podatnik.findByNazwapelna").setParameter("nazwapelna",np).getSingleResult();
        return tmp;
    }
    
     public Podatnik findNPN(String np){
        Podatnik tmp = (Podatnik) em.createNamedQuery("Podatnik.findByNip").setParameter("nip",np).getSingleResult();
        return tmp;
    }
    
}
