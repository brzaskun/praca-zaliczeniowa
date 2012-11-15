/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Uz;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class UzFacade extends AbstractFacade<Uz> {
    @PersistenceContext(unitName = "npkpir_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UzFacade() {
        super(Uz.class);
    }
    
    public Uz findNP(String np){
        Uz tmp = (Uz) em.createNamedQuery("Uz.findByLogin").setParameter("login",np).getSingleResult();
        return tmp;
    }
}
