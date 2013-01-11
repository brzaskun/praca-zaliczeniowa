/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Klient;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
public class SessionFacade<T>  {
    @PersistenceContext(unitName = "Hotel2PU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public SessionFacade() {
    }
    
     public List<T> findAll(Class<T> aClass) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(aClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public Klient findklient(String imie, String nazwisko, String nrkarty) {
       return (Klient) em.createQuery("SELECT e FROM Klient e WHERE e.imie = :imie AND e.nazwisko = :nazwisko AND e.nrkarty = :nrkarty").setParameter("imie", imie).setParameter("nazwisko", nazwisko).setParameter("nrkarty", nrkarty).getSingleResult();
    }
     public void create(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }
}
    
