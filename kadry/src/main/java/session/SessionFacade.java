/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import error.E;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author Osito
 * @param <T>
 */
@Stateless
public class SessionFacade<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @PreDestroy
    private void preDestroy() {
        em.clear();
        em.close();
        em = null;
        error.E.s("koniec jpa");
    }

    @PersistenceContext(unitName = "kadryPU")
    private EntityManager em;

    public SessionFacade() {
       // error.E.s("SessionFacade init");
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public List<T> findAll(Class<T> entityClass) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return Collections.synchronizedList(getEntityManager().createQuery(cq).getResultList());
    }
    
    
    
    public List<T> findAllReadOnly(Class<T> entityClass) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return Collections.synchronizedList(getEntityManager().createQuery(cq).getResultList());
    }
    
//    
//    public List<T> findAllReadOnlyXX(Class<T> entityClass) {
//        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        cq.select(cq.from(entityClass));
//        return Collections.synchronizedList(getEntityManager().createQuery(cq).setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE).setHint(QueryHints.READ_ONLY, HintValues.TRUE).getResultList());
//    }

    public void create(T entity) {
        getEntityManager().persist(entity);
        
    }
    
    public void createList(List<T> entityList) {
        for (T p : entityList) {
            try {
                getEntityManager().persist(p);
            } catch (Exception e) {
                E.e(e);
            }
        }
        
    }
    
//    public void refresh(List<T> entityList) {
//        for (T p : entityList) {
//            try {
//                getEntityManager().refresh(p);
//            } catch(Exception e){}
//        }
//        
//    }

//    public void refresh(T entity) {
//        getEntityManager().refresh(getEntityManager().merge(entity));
//    }

    public T findEntity(Class<T> entityClass, T entityPK) {
        T find = getEntityManager().find(entityClass, entityPK);
        return find;
    }

    public void remove(T entity) {
        
        em.remove(em.merge(entity));
        
    }
    
    public void remove(List<T> entityList) {
        
        for (T p : entityList) {
            em.remove(em.merge(p));
        }
        
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
        
        
    }

    public void edit(List<T> entityList) {
        for (T p : entityList) {
            try {
                getEntityManager().merge(p);
            } catch (Exception e) {
                E.e(e);
            }
        }
        
    }
    
    public void editLateflush(List<T> entityList) {
        for (T p : entityList) {
            try {
                getEntityManager().merge(p);
            } catch (Exception e) {
                E.e(e);
            }
        }
        
    }

//    //to jest po to, ze jk juz jest cos w np. planie kont to 
//    //wywali blad w jednym, ele reszte zasejwuje w bazie :)
//    public void createRefresh(List<T> entityList) {
//        for (T p : entityList) {
//            try {
//                if (p!=null) {
//                    getEntityManager().persist(p);
//                }
//            } catch (Exception e) {
//                E.e(e);
//            }
//        }
//        
//    }

    public List<T> findXLast(Class<T> entityClass, int ile) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        int ilosc = ((Number) q.getSingleResult()).intValue();
        int kontrolailosci = ilosc - ile;
        if (kontrolailosci < 0) {
            kontrolailosci = 0;
        }
        int[] range = {ilosc, kontrolailosci};
        cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        q = getEntityManager().createQuery(cq);
        q.setMaxResults(ile);
        q.setFirstResult(range[1]);
        return Collections.synchronizedList(q.getResultList());
    }

    

    

    
}
