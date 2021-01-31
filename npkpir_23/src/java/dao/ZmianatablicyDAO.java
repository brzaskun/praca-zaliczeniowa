/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Zmianatablicy;
import error.E;
import java.io.Serializable;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ejb.Stateless;import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class ZmianatablicyDAO extends DAO implements Serializable{
    
    @Inject private SessionFacade sessionFacade;
    
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

    public ZmianatablicyDAO() {
        super(Zmianatablicy.class);
        super.em = this.em;
    }

    
    @Inject private Zmianatablicy zmianatablicy;

        
      public void dodaj(String param, boolean zmiana) {
        try {
            if(sessionFacade==null){
            } else {
            }
            zmianatablicy.setNazwatablicy(param);
            zmianatablicy.setZmiana(zmiana);
            sessionFacade.create(zmianatablicy);
        } catch (Exception e) { E.e(e); 
            throw new PersistenceException();
        }
    }
    
      public void edytuj(String param, boolean zmiana) {
        try {
            if(sessionFacade==null){
            } else {
            }
            zmianatablicy.setNazwatablicy(param);
            zmianatablicy.setZmiana(zmiana);
            sessionFacade.edit(zmianatablicy);
        } catch (Exception e) { E.e(e); 
            throw new PersistenceException();
        }
    }
}
