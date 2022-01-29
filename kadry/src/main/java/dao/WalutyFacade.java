/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Tabelanbp;
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
public class WalutyFacade extends DAO  implements Serializable {
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

    public WalutyFacade() {
        super(Tabelanbp.class);
        super.em = em;
    }
  
    public Tabelanbp findOstatniaTabela(String symbolwaluty) {
        try {
            return (Tabelanbp)  getEntityManager().createNamedQuery("Tabelanbp.findBySymbolWalutyOstatnia").setParameter("symbolwaluty", symbolwaluty).setMaxResults(1).getSingleResult();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
}
