/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FirmaKadry;
import entity.Firmabaustelle;
import java.io.Serializable;
import java.util.ArrayList;
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
public class FirmabaustelleFacade extends DAO  implements Serializable {
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

    public FirmabaustelleFacade() {
        super(Firmabaustelle.class);
        super.em = em;
    }

   public List<Firmabaustelle> findbyRokFirma(String rokWpisu, FirmaKadry firmaKadry) {
        List<Firmabaustelle> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Firmabaustelle.findByRokFirma").setParameter("rok", rokWpisu).setParameter("firma", firmaKadry).getResultList();
        } catch (Exception e) {}
        return zwrot;
    }

   
}
