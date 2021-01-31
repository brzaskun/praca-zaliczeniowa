/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Fakturaelementygraficzne;
import error.E;
import java.io.Serializable;
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
public class FakturaelementygraficzneDAO  extends DAO implements Serializable {
    
    @Inject
    private SessionFacade fakturaelementygraficzneFacade;
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

    public FakturaelementygraficzneDAO() {
        super(Fakturaelementygraficzne.class);
        super.em = this.em;
    }

   
    
    public  Fakturaelementygraficzne findFaktElementyGraficznePodatnik(String podatnik){
        try {
            return fakturaelementygraficzneFacade.findFaktElementyGraficzne(podatnik);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
}
