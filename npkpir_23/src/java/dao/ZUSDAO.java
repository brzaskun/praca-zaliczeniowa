/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Zusstawki;
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
public class ZUSDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade zusstawkiFacade;
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

    public ZUSDAO() {
        super(Zusstawki.class);
        super.em = this.em;
    }
  
        
    public  List<Zusstawki> findAll(){
        try {
            return zusstawkiFacade.findAll(Zusstawki.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public  List<Zusstawki> findZUS(boolean duzy0maly1){
        try {
            return zusstawkiFacade.findZUS(duzy0maly1);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }
}
