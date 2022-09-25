/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Testwykaz;
import java.io.Serializable;
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
public class TestWykazFacade extends DAO  implements Serializable {
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

    public TestWykazFacade() {
        super(Testwykaz.class);
        super.em = em;
    }

    public Testwykaz findBynazwa(String nazwa) {
        Testwykaz zwrot = null;
        try {
            zwrot = (Testwykaz) getEntityManager().createNamedQuery("Testwykaz.findByNazwa").setParameter("nazwa", nazwa).getSingleResult();
        } catch (Exception ex){
        }
        return zwrot;
    }

       
   
}
