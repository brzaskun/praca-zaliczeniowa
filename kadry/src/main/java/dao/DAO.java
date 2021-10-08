/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import error.E;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Osito
 * @param <T>
 */
public abstract class  DAO<T> {

    EntityManager em;
    Class<T> entityClass;

    protected DAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }


    protected abstract EntityManager getEntityManager();

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

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }
    
    public void editList(List<T> entityList) {
        for (T p : entityList) {
            try {
                getEntityManager().merge(p);
            } catch (Exception e) {
                E.e(e);
            }
        }
        
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
    
     public void removeList(List<T> entityList) {
        for (T p : entityList) {
            try {
                getEntityManager().remove(getEntityManager().merge(p));
            } catch (Exception e) {
                E.e(e);
            }
        }
        
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
