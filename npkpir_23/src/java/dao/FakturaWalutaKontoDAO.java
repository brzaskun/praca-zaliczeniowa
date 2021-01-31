/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FakturaWalutaKonto;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import session.SessionFacade;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class FakturaWalutaKontoDAO extends DAO implements Serializable{
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

    public FakturaWalutaKontoDAO() {
        super(FakturaWalutaKonto.class);
        super.em = this.em;
    }

   
    public FakturaWalutaKontoDAO(Class entityClass) {
        super(entityClass);
    }
    
    public List<FakturaWalutaKonto> findAll() {
        return findAll();
    }

    public List<FakturaWalutaKonto> findPodatnik(WpisView wpisView) {
        return sessionFacade.getEntityManager().createNamedQuery("FakturaWalutaKonto.findByPodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList();
    }
    
    public List<FakturaWalutaKonto> findPodatnikAktywne(WpisView wpisView) {
        return sessionFacade.getEntityManager().createNamedQuery("FakturaWalutaKonto.findByPodatnikAktywne").setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList();
    }
}
