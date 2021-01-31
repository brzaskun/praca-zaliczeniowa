/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Evpozycja;
import error.E;
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
public class EvpozycjaDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade evpozycjaFacade;
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

  
    
    public Evpozycja find(String nazwapola){
        return evpozycjaFacade.findEvpozycjaByName(nazwapola);
    }
    
    public  List<Evpozycja> findAll(){
        try {
            return evpozycjaFacade.findAll(Evpozycja.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
}
