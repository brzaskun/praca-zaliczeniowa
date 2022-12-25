/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FirmaKadry;
import entity.Wypadkowefirma;
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
public class WypadkowefirmaFacade extends DAO  implements Serializable {
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

    public WypadkowefirmaFacade() {
        super(Wypadkowefirma.class);
        super.em = em;
    }
    
    public List<Wypadkowefirma> findByFirma(FirmaKadry firma) {
        List<Wypadkowefirma> zwrot = new ArrayList<>();
        zwrot = getEntityManager().createNamedQuery("Wypadkowefirma.findByFirma").setParameter("firma", firma).getResultList();
        return zwrot;
    }   
   
}
