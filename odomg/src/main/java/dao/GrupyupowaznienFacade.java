/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Grupyupowaznien;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class GrupyupowaznienFacade extends DAO  implements Serializable {
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

    public GrupyupowaznienFacade() {
        super(Grupyupowaznien.class);
        super.em = em;
    }

    public List<Grupyupowaznien> findById(int idzakladu) {
        List<Grupyupowaznien> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Grupyupowaznien.findByFirmaId").setParameter("firmaId", idzakladu).getResultList();
        } catch (Exception ex){
        }
        return zwrot;
    }

       
   
}
