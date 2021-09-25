/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Rachunekdoumowyzlecenia;
import entity.Umowa;
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
public class RachunekdoumowyzleceniaFacade extends DAO  {

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

    public RachunekdoumowyzleceniaFacade() {
        super(Rachunekdoumowyzlecenia.class);
        super.em = em;
    }

    public List<Rachunekdoumowyzlecenia> findByRokUmowa(String rokWpisu, Umowa umowa) {
        return  getEntityManager().createNamedQuery("Rachunekdoumowyzlecenia.findByRokUmowa").setParameter("rok", rokWpisu).setParameter("umowa", umowa).getResultList();
    }
    
    public Rachunekdoumowyzlecenia findByRokMcUmowa(String rokWpisu, String mc, Umowa umowa) {
        Rachunekdoumowyzlecenia zwrot = null;
        try {
            zwrot = (Rachunekdoumowyzlecenia) getEntityManager().createNamedQuery("Rachunekdoumowyzlecenia.findByRokMcUmowa").setParameter("rok", rokWpisu).setParameter("mc", mc).setParameter("umowa", umowa).getSingleResult();
        } catch (Exception e) {}
        return zwrot;
    }
    
}
