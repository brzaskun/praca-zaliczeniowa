/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Sumypkpir;
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
public class SumypkpirDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade sumypkpirFacade;
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

    public SumypkpirDAO() {
        super(Sumypkpir.class);
        super.em = this.em;
    }

   
    
    public  List<Sumypkpir> findAll(){
        try {
            return sumypkpirFacade.findAll(Sumypkpir.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
   public  List<Sumypkpir> findS(String podatnik, String rok){
        try {
            return sumypkpirFacade.findSumy(podatnik, rok);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
   
   public void usun(String podatnik, String rok, String mc) {
       sumypkpirFacade.usunSumyPKPiR(podatnik, rok, mc);
   }
}
