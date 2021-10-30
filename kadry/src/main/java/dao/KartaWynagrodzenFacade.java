/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Angaz;
import entity.Kartawynagrodzen;
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
public class KartaWynagrodzenFacade extends DAO  implements Serializable {
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

    public KartaWynagrodzenFacade() {
        super(Kartawynagrodzen.class);
        super.em = em;
    }

    public List<Kartawynagrodzen> findByAngazRok(Angaz selectedangaz, String rok) {
        return getEntityManager().createNamedQuery("Kartawynagrodzen.findByAngazRok").setParameter("angaz", selectedangaz).setParameter("rok", rok).getResultList();
    }

   

}
