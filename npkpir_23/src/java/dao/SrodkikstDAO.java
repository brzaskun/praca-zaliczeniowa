/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Srodkikst;
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
public class SrodkikstDAO extends DAO implements Serializable{
     @Inject private SessionFacade srodkikstFacade;
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

    public SrodkikstDAO() {
        super(Srodkikst.class);
        super.em = this.em;
    }
     
    
   
    public List<Srodkikst> finsStr(String nazwa){
        return srodkikstFacade.findSrodekkst(nazwa);
    }
    
    public Srodkikst finsStr1(String nazwa){
        return srodkikstFacade.findSrodekkst1(nazwa);
    }
    
    public Srodkikst find(Srodkikst srodek){
        return srodkikstFacade.findSr(srodek);
    }
    
    public  List<Srodkikst> findAll(){
        try {
            return srodkikstFacade.findAll(Srodkikst.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
}
