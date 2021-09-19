/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Rodzajwynagrodzenia;
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
public class RodzajwynagrodzeniaFacade extends DAO    implements Serializable {
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

    public RodzajwynagrodzeniaFacade() {
        super(Rodzajwynagrodzenia.class);
        super.em = em;
    }
    
  
    public Rodzajwynagrodzenia findZasadniczePraca() {
        return (Rodzajwynagrodzenia) getEntityManager().createNamedQuery("Rodzajwynagrodzenia.findByOpispelny").setParameter("opispelny", "Wynagrodzenie zasadnicze").getSingleResult();
    }
    
    public Rodzajwynagrodzenia findZasadniczeZlecenie() {
        return (Rodzajwynagrodzenia) getEntityManager().createNamedQuery("Rodzajwynagrodzenia.findByOpispelny").setParameter("opispelny", "Umowa zlecenia").getSingleResult();
    }
    
    public Rodzajwynagrodzenia findGodzinowePraca() {
        return (Rodzajwynagrodzenia) getEntityManager().createNamedQuery("Rodzajwynagrodzenia.findById").setParameter("id", 30).getSingleResult();
    }
    
    public Rodzajwynagrodzenia findGodzinoweZlecenie() {
        return (Rodzajwynagrodzenia) getEntityManager().createNamedQuery("Rodzajwynagrodzenia.findById").setParameter("id", 72).getSingleResult();
    }

  
}
