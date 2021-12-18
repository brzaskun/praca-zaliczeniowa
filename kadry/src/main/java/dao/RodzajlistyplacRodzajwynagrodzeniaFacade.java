/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Rodzajlistyplac;
import entity.RodzajlistyplacRodzajwynagrodzenia;
import entity.Rodzajwynagrodzenia;
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
public class RodzajlistyplacRodzajwynagrodzeniaFacade extends DAO  implements Serializable {
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

   public RodzajlistyplacRodzajwynagrodzeniaFacade() {
        super(RodzajlistyplacRodzajwynagrodzenia.class);
        super.em = em;
    }
    
    

    public List<RodzajlistyplacRodzajwynagrodzenia> findByRodzajwynagrodzenia(Rodzajlistyplac rodzajlistyplac) {
        return getEntityManager().createNamedQuery("RodzajlistyplacRodzajwynagrodzenia.findByRodzajwynagrodzenia").setParameter("rodzajlistyplac", rodzajlistyplac).getResultList();
    }
    
    public List<RodzajlistyplacRodzajwynagrodzenia> findByRodzajwynagrodzeniaNOT(Rodzajlistyplac rodzajlistyplac) {
        return getEntityManager().createNamedQuery("RodzajlistyplacRodzajwynagrodzenia.findByRodzajwynagrodzeniaNOT").setParameter("rodzajlistyplac", rodzajlistyplac).getResultList();
    }

    public void usun(Rodzajwynagrodzenia rodzajwynagrodzenia) {
        getEntityManager().createNamedQuery("RodzajlistyplacRodzajwynagrodzenia.deleteByRodzajwynagrodzenia").setParameter("rodzajwynagrodzenia", rodzajwynagrodzenia).executeUpdate();
    }
}
