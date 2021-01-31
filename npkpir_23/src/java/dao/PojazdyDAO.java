/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.DAO;
import entity.Podatnik;
import entityfk.Pojazdy;
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

public class PojazdyDAO extends DAO implements Serializable{
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

    public PojazdyDAO() {
        super(Pojazdy.class);
        super.em = this.em;
    }
       

    public List<Pojazdy> findAll() {
        return sessionFacade.findAll(Pojazdy.class);
    }

    public List<Pojazdy> findPojazdyPodatnik(Podatnik podatnikObiekt) {
        return sessionFacade.findPojazdyPodatnik(podatnikObiekt);
    }

    public int countPojazdy(Podatnik podatnikObiekt) {
        return (int) sessionFacade.countPojazdy(podatnikObiekt);
    }
}
