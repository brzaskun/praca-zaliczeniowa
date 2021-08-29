/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Skladnikpotracenia;
import entity.Umowa;
import entity.Zmiennapotracenia;
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
public class ZmiennaPotraceniaFacade extends DAO implements Serializable {
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

    public ZmiennaPotraceniaFacade() {
        super(Zmiennapotracenia.class);
        super.em = em;
    }

    public List<Zmiennapotracenia> findByUmowa(Umowa umowa) {
        return getEntityManager().createNamedQuery("Zmiennapotracenia.findByUmowa").setParameter("umowa", umowa).getResultList();
    }

    public List<Zmiennapotracenia> findBySkladnik(Skladnikpotracenia skladnikpotracenia) {
        return getEntityManager().createNamedQuery("Zmiennapotracenia.findBySkladnik").setParameter("skladnikpotracenia", skladnikpotracenia).getResultList();
    }
}
