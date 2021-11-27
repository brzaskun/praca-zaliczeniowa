/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Angaz;
import entity.Definicjalistaplac;
import entity.Kalendarzmiesiac;
import entity.Pasekwynagrodzen;
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
public class PasekwynagrodzenFacade extends DAO   implements Serializable {
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

    public PasekwynagrodzenFacade() {
        super(Pasekwynagrodzen.class);
        super.em = em;
    }
    

    public Pasekwynagrodzen findByDefKal(Definicjalistaplac definicjalistaplac, Kalendarzmiesiac kalendarzmiesiac) {
        return (Pasekwynagrodzen) getEntityManager().createNamedQuery("Pasekwynagrodzen.findByDefKal").setParameter("definicjalistaplac", definicjalistaplac).setParameter("kalendarzmiesiac", kalendarzmiesiac).getSingleResult();
    }
    public List<Pasekwynagrodzen> findByDef(Definicjalistaplac definicjalistaplac) {
        return getEntityManager().createNamedQuery("Pasekwynagrodzen.findByDef").setParameter("definicjalistaplac", definicjalistaplac).getResultList();
    }

    public List<Pasekwynagrodzen> findByRokAngaz(String rok, Kalendarzmiesiac p) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        try {
            return getEntityManager().createNamedQuery("Pasekwynagrodzen.findByRokAngaz").setParameter("rok", rok).setParameter("angaz", p.getUmowa().getAngaz()).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
    
    public List<Pasekwynagrodzen> findByRokAngaz(String rok, Angaz p) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        try {
            return getEntityManager().createNamedQuery("Pasekwynagrodzen.findByRokAngaz").setParameter("rok", rok).setParameter("angaz", p).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
    
    public List<Pasekwynagrodzen> findByRokWyplAngaz(String rok, Angaz p) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        try {
            return getEntityManager().createNamedQuery("Pasekwynagrodzen.findByRokWyplAngaz").setParameter("rok", rok).setParameter("angaz", p).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
    public List<Pasekwynagrodzen> findByRokMcAngaz(String rok, String mc, Angaz p) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        try {
            return getEntityManager().createNamedQuery("Pasekwynagrodzen.findByRokMcAngaz").setParameter("rok", rok).setParameter("mc", mc).setParameter("angaz", p).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
    
    public List<Pasekwynagrodzen> findByRokMcWyplAngaz(String rok, String mc, Angaz p) {
        List<Pasekwynagrodzen> zwrot = new ArrayList<>();
        try {
            return getEntityManager().createNamedQuery("Pasekwynagrodzen.findByRokMcWyplAngaz").setParameter("rok", rok).setParameter("mc", mc).setParameter("angaz", p).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
}
