/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Ryczpoz;
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
public class RyczDAO extends DAO implements Serializable {

    @Inject private SessionFacade ryczFacade;
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

    public RyczDAO() {
        super(Ryczpoz.class);
        super.em = this.em;
    }


    public Ryczpoz find(String rok, String mc, String pod) {
        return ryczFacade.findRycz(rok, mc, pod);
    }
    
    public Ryczpoz find(String rok, String mc, String pod, String udzialowiec) {
        return ryczFacade.findRycz(rok, mc, pod, udzialowiec);
    }
    
    public List<Ryczpoz> findRyczPod(String rok, String pod) {
        return ryczFacade.findRyczpodatnik(rok,pod);
    }
    
   
}
