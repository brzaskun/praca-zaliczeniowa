/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.JPKoznaczenia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class JPKOznaczeniaDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;
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

    public JPKOznaczeniaDAO() {
        super(JPKoznaczenia.class);
        super.em = this.em;
    }


    public List<JPKoznaczenia> findByKlasa(int klasa) {
        List<JPKoznaczenia> zwrot = new ArrayList<>();
        try {
            zwrot = sessionFacade.getEntityManager().createNamedQuery("JPKoznaczenia.findyByKlasa").setParameter("klasa", klasa).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
    
    
}
