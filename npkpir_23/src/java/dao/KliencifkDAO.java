/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.DAO;
import entityfk.Kliencifk;
import entityfk.Konto;
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

public class KliencifkDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
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

    public KliencifkDAO() {
        super(Kliencifk.class);
        super.em = this.em;
    }
   

    public KliencifkDAO(Class entityClass) {
        super(entityClass);
    }
    
    
    public Kliencifk znajdzkontofk(String nip, String podatniknip) {
        try {
            Kliencifk kf = sessionFacade.znajdzkontofk(nip, podatniknip);
            return kf;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<Kliencifk> znajdzkontofkKlient(String podatniknip) {
        return sessionFacade.znajdzkontofkKlient(podatniknip);
    }
    
    public Kliencifk znajdzkontofkByKonto(Konto konto) {
        return sessionFacade.znajdzkontofkByKonto(konto);
    }
    
    
}
