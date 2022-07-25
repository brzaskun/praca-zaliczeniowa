/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Fakturyokresowe;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class FakturyokresoweDAO  extends DAO implements Serializable {

    @Inject
    private SessionFacade fakturyokresoweFacade;
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

    public FakturyokresoweDAO() {
        super(Fakturyokresowe.class);
        super.em = this.em;
    }


      
    public List<Fakturyokresowe> findPodatnik(String podatnik){
        List<Fakturyokresowe> zwrot = Collections.synchronizedList(new ArrayList<>());
        try {
            zwrot = fakturyokresoweFacade.findPodatnik(podatnik);
        } catch (Exception e) { E.e(e); }
        return zwrot;
    }
}
