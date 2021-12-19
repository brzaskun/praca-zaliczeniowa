/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Memory;
import entity.Uz;
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
public class MemoryFacade extends DAO  implements Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "kadryPU")
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

    public MemoryFacade() {
        super(Memory.class);
        super.em = em;
    }

   

    public Memory findByUzer(Uz uzer) {
        Memory zwrot = null;
        try {
            zwrot = (Memory) getEntityManager().createNamedQuery("Memory.findByUzer").setParameter("uzer", uzer).getSingleResult();
        } catch (Exception ex){
        }
        return zwrot;
    }
     
   
}
