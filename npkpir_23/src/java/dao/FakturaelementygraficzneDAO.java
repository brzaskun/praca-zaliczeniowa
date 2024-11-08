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
public class FakturaelementygraficzneDAO  extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
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

   public Fakturaelementygraficzne findFaktElementyGraficznePodatnik(String podatnik) {
        try {
            return (Fakturaelementygraficzne)  getEntityManager().createNamedQuery("Fakturaelementygraficzne.findByPodatnikLogo").setParameter("podatnik", podatnik).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
   public Fakturaelementygraficzne findFaktElementyGraficznedodatkowe(String podatnik) {
        try {
            return (Fakturaelementygraficzne)  getEntityManager().createNamedQuery("Fakturaelementygraficzne.findByPodatnikElementGraficzny").setParameter("podatnik", podatnik).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
   
}
