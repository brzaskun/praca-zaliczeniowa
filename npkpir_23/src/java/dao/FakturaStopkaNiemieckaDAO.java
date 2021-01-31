/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FakturaStopkaNiemiecka;
import entity.Podatnik;
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
public class FakturaStopkaNiemieckaDAO  extends DAO implements Serializable {

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

    public FakturaStopkaNiemieckaDAO() {
        super(FakturaStopkaNiemiecka.class);
        super.em = this.em;
    }
    
  
    public FakturaStopkaNiemiecka findByPodatnik(Podatnik podatnikObiekt) {
        FakturaStopkaNiemiecka zwrot = null;
        try {
            zwrot = sessionFacade.findStopkaNiemieckaByPodatnik(podatnikObiekt);
        } catch (Exception e) {
            //E.e(e); 
        }
        return zwrot;
    }
    
}
