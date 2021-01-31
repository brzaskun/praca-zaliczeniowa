/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Logofaktura;
import entity.Podatnik;
import error.E;
import java.io.Serializable;
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
//pomaga przenosci opisy bo inaczej nie chca sie zachowac. scopy nie pasuja
public class LogofakturaDAO extends DAO implements Serializable {

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

    public LogofakturaDAO() {
        super(Logofaktura.class);
        super.em = this.em;
    }



    public void usun(Podatnik podatnikObiekt) {
        try {
            sessionFacade.usunlogoplik(podatnikObiekt);
        } catch (Exception e) { 
            E.e(e); 
        }
    }

    public Logofaktura findByPodatnik(Podatnik podatnikObiekt) {
        return sessionFacade.findLogoByPodatnik(podatnikObiekt);
    }
    
    
}
