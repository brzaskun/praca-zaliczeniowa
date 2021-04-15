/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Evpozycja;
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
public class EvpozycjaDAO extends DAO implements Serializable {

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

    public EvpozycjaDAO() {
        super(Evpozycja.class);
        super.em = this.em;
    }

     public Evpozycja find(String nazwapola) {
        return (Evpozycja)  getEntityManager().createNamedQuery("Evpozycja.findByNazwapola").setParameter("nazwapola", nazwapola).getSingleResult();
    }

}
