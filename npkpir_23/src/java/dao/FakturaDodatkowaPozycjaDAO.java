/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FakturaDodatkowaPozycja;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class FakturaDodatkowaPozycjaDAO  extends DAO implements Serializable {

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

    public FakturaDodatkowaPozycjaDAO() {
        super(FakturaDodatkowaPozycja.class);
        super.em = this.em;
    }

   
    
     public  List<FakturaDodatkowaPozycja> findAll(){
        try {
            return sessionFacade.findAll(FakturaDodatkowaPozycja.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
}
