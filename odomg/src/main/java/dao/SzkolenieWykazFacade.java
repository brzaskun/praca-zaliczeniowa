/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Szkoleniewykaz;
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
public class SzkolenieWykazFacade extends DAO  implements Serializable {
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

    public SzkolenieWykazFacade() {
        super(Szkoleniewykaz.class);
        super.em = em;
    }

    public Szkoleniewykaz findBynazwa(String nazwa) {
        Szkoleniewykaz zwrot = null;
        try {
            zwrot = (Szkoleniewykaz) getEntityManager().createNamedQuery("Szkoleniewykaz.findByNazwa").setParameter("nazwa", nazwa).getSingleResult();
        } catch (Exception ex){
        }
        return zwrot;
    }

       
   
}
