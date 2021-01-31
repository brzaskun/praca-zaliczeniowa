/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.DAO;
import entity.Podatnik;
import entityfk.MiejscePrzychodow;
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
public class MiejscePrzychodowDAO extends DAO implements Serializable{
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

    public MiejscePrzychodowDAO() {
        super(MiejscePrzychodow.class);
        super.em = this.em;
    }
      

    public List<MiejscePrzychodow> findAll() {
        return sessionFacade.findAll(MiejscePrzychodow.class);
    }

    public List<MiejscePrzychodow> findMiejscaPodatnikRok(Podatnik podatnikObiekt, int rok) {
        return sessionFacade.findMiejscaPrzychodowPodatnikRok(podatnikObiekt, rok);
    }
    
    public List<MiejscePrzychodow> findMiejscaPodatnik(Podatnik podatnikObiekt) {
        return sessionFacade.findMiejscaPrzychodowPodatnik(podatnikObiekt);
    }
    
    public List<MiejscePrzychodow> findMiejscaPodatnikWszystkie(Podatnik podatnikObiekt) {
        return sessionFacade.findMiejscaPrzychodowPodatnikWszystkie(podatnikObiekt);
    }
    
    public List<MiejscePrzychodow> findCzlonkowieStowarzyszenia(Podatnik podatnikObiekt) {
        return sessionFacade.getEntityManager().createNamedQuery("MiejscePrzychodow.findCzlonekStowarzyszenia").setParameter("podatnik", podatnikObiekt).getResultList();
    }

}
