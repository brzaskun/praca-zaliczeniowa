/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
public class Session <T>{
    @PersistenceContext(unitName = "TestGlassfish4PU")
    private EntityManager em;
    
    
     public Session() {
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    
    public List<T> findAll(Class<T> entityClass) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public void create(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }
    
    public List<T> findXLast(Class<T> entityClass,int ile) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        int ilosc = ((Long) q.getSingleResult()).intValue();
        int kontrolailosci = ilosc-ile;
        if(kontrolailosci<0){
            kontrolailosci=0;
        }
        int[] range = {ilosc, kontrolailosci};
        cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        q = getEntityManager().createQuery(cq);
        q.setMaxResults(ile);
        q.setFirstResult(range[1]);
        return q.getResultList();
    }
    
    
}
