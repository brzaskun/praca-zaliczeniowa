/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.DAO;
import entityfk.Dokfk;
import entityfk.RMK;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ejb.Stateless;import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.SessionFacade;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Stateless
@Transactional

public class RMKDAO extends DAO implements Serializable {

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

    public RMKDAO() {
        super(RMK.class);
        super.em = this.em;
    }
   

    public List<RMK> findAll() {
        try {
            return sessionFacade.findAll(RMK.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<RMK> findRMKByPodatnikRok(WpisView wpisView) {
        try {
            return sessionFacade.findRMKByPodatnikRok(wpisView);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }
    
    public List<RMK> findRMKByPodatnikRokDokfk(WpisView wpisView, Dokfk dokfk) {
        try {
            return sessionFacade.findRMKByPodatnikRokDokfk(wpisView, dokfk);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }

}
