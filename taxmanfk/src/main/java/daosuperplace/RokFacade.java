/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daosuperplace;


import java.io.Serializable;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import kadryiplace.Rok;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class RokFacade extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "microsoft1")
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

    public RokFacade() {
        super(Rok.class);
        super.em = em;
    }
    
    public Rok findByFirmaRok(kadryiplace.Firma firma, Integer rok) {
        return (Rok) getEntityManager().createNamedQuery("Rok.findByRokFirma").setParameter("firma", firma).setParameter("rok", rok.shortValue()).getSingleResult();
    }
   
}
