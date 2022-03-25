/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Uczestnicy;
import entity.Zakladpracy;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class UczestnicyFacade extends DAO  implements Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "odomgPU")
    private EntityManager em;
    
    @PreDestroy
    private void preDestroy() {
        em.clear();
        em.close();
        em.getEntityManagerFactory().close();
        em = null;
        
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public UczestnicyFacade() {
        super(Uczestnicy.class);
        super.em = em;
    }

    public List<Uczestnicy> findByEmail(String email) {
        List<Uczestnicy> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Uczestnicy.findByEmail").setParameter("email", email).getResultList();
        } catch (Exception ex){
        }
        return zwrot;
    }
    
    public List<Uczestnicy> findByFirmaId(Zakladpracy firmaId) {
        List<Uczestnicy> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Uczestnicy.findByFirmaId").setParameter("firmaId", firmaId).getResultList();
        } catch (Exception ex){
        }
        return zwrot;
    }

     public List<Uczestnicy> findAllLast() {
        javax.persistence.criteria.CriteriaBuilder builder = em.getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<Uczestnicy> order = cq.from(Uczestnicy.class);
        cq.select(cq.from(Uczestnicy.class));
        cq.orderBy(builder.desc(order.get("id")));
        return getEntityManager().createQuery(cq).getResultList();
    }
   
}
