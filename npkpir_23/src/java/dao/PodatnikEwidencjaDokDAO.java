/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entity.PodatnikEwidencjaDok;
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
public class PodatnikEwidencjaDokDAO  extends DAO implements Serializable{
    @Inject
    private SessionFacade session1Facade;
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

    public PodatnikEwidencjaDokDAO() {
        super(PodatnikEwidencjaDok.class);
        super.em = this.em;
    }


    
    public List<PodatnikEwidencjaDok> findOpodatkowaniePodatnik(Podatnik podatnik) {
        return session1Facade.findPodatnikEwidencjaByPodatnik(podatnik);
    }
  
    
}
