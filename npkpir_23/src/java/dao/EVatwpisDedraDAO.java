/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.DAO;
import entityfk.EVatwpisDedra;
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
public class EVatwpisDedraDAO   extends DAO implements Serializable{
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

    public EVatwpisDedraDAO() {
        super(EVatwpisDedra.class);
        super.em = this.em;
    }
   
    public List<EVatwpisDedra> findWierszePodatnikMc(WpisView wpisView) {
        return sessionFacade.getEntityManager().createNamedQuery("EVatwpisDedra.findByPodatnikRokMc").setParameter("podatnik",wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList();
    }
    
    public List<EVatwpisDedra> findWierszePodatnikMcZakupSprzedaz(WpisView wpisView, boolean zakup0sprzedaz1) {
        if (zakup0sprzedaz1) {
            return sessionFacade.getEntityManager().createNamedQuery("EVatwpisDedra.findByPodatnikRokMcSprzedaz").setParameter("podatnik",wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).setParameter("nazwa", "zakup").getResultList();
        } else {
            return sessionFacade.getEntityManager().createNamedQuery("EVatwpisDedra.findByPodatnikRokMcZakup").setParameter("podatnik",wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).setParameter("nazwa", "zakup").getResultList();
        }
    }
    public List<EVatwpisDedra> findAll() {
        return sessionFacade.findAll(EVatwpisDedra.class);
   }
    public List<EVatwpisDedra> zwrocRokMc(String rokWpisuSt, String mc) {
        return sessionFacade.getEntityManager().createNamedQuery("EVatwpisDedra.findByMcRok").setParameter("rok", rokWpisuSt).setParameter("mc", mc).getResultList();
    }
}
