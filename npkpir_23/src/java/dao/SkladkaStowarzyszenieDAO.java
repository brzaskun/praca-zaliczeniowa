/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entityfk.SkladkaStowarzyszenie;
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
public class SkladkaStowarzyszenieDAO extends DAO implements Serializable{
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

    public SkladkaStowarzyszenieDAO() {
        super(SkladkaStowarzyszenie.class);
        super.em = this.em;
    }
 
    public List<SkladkaStowarzyszenie> findByPodatnikRok(WpisView wpisView) {
        return sessionFacade.getEntityManager().createNamedQuery("SkladkaStowarzyszenie.findByPodatnikRok").setParameter("podatnikObj", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList();
    }
    
    public List<SkladkaStowarzyszenie> findByPodatnik(WpisView wpisView) {
        return sessionFacade.getEntityManager().createNamedQuery("SkladkaStowarzyszenie.findByPodatnik").setParameter("podatnikObj", wpisView.getPodatnikObiekt()).getResultList();
    }
    
}
