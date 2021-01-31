/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FakturaXXLKolumna;
import entity.Podatnik;
import error.E;
import java.io.Serializable;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class FakturaXXLKolumnaDAO extends DAO implements Serializable{
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

    public FakturaXXLKolumnaDAO() {
        super(FakturaXXLKolumna.class);
        super.em = this.em;
    }

  

    public FakturaXXLKolumnaDAO(Class entityClass) {
        super(entityClass);
    }
    
    public FakturaXXLKolumna findXXLByPodatnik(Podatnik p) {
        FakturaXXLKolumna zwrot = null;
        try {
            zwrot = sessionFacade.findXXLByPodatnik(p);
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
}
