/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Rodzajlistyplac;
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
public class RodzajlistyplacFacade extends DAO  {

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

    public RodzajlistyplacFacade() {
        super(Rodzajlistyplac.class);
        super.em = em;
    }
    
    public List<Rodzajlistyplac> findAktywne() {
        return getEntityManager().createNamedQuery("Rodzajlistyplac.findAktywne").getResultList();
    }
    
    public Rodzajlistyplac findUmowaoPrace() {
        return (Rodzajlistyplac) getEntityManager().createNamedQuery("Rodzajlistyplac.findByUmowaoPrace").getSingleResult();
    }
    
    public Rodzajlistyplac findUmowaZlecenia() {
        return (Rodzajlistyplac) getEntityManager().createNamedQuery("Rodzajlistyplac.findByUmowaZlecenia").getSingleResult();
    }
    
    public Rodzajlistyplac findUmowaFunkcja() {
        return (Rodzajlistyplac) getEntityManager().createNamedQuery("Rodzajlistyplac.findByUmowaFunkcja").getSingleResult();
    }
     
    public Rodzajlistyplac findByTyt_serial(Integer lis_tyt_serial) {
        return (Rodzajlistyplac) getEntityManager().createNamedQuery("Rodzajlistyplac.findByTyt_serial").setParameter("tyt_serial", lis_tyt_serial).getSingleResult();
    }

}
