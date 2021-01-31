/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entity.Statystyka;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ejb.Stateless;import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class StatystykaDAO  extends DAO implements Serializable{
   @Inject private SessionFacade sessionFacade;
      @PersistenceContext(unitName = "npkpir_22PU")
    private EntityManager em;
    
    @PreDestroy
    private void preDestroy() {
        em.clear();
        em.close();
        em.getEntityManagerFactory().close();
        em = null;
        error.E.s("koniec jpa");
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public StatystykaDAO() {
        super(Statystyka.class);
        super.em = this.em;
    }

  
    
    public void usunrok(String rok) {
        sessionFacade.statystykaUsunrok(rok);
        
    }

    public List<Statystyka> findByPodatnik(Podatnik p) {
        return sessionFacade.getEntityManager().createNamedQuery("Statystyka.findByPodatnik").setParameter("podatnik", p).getResultList();
    }
    
    public List<Statystyka> findByRok(String p) {
        return sessionFacade.getEntityManager().createNamedQuery("Statystyka.findByRok").setParameter("rok", p).getResultList();
    }
}
