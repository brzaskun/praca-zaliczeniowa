/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.EVatwpisKJPK;
import entity.Podatnik;
import entityfk.EVatwpisDedra;
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
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import session.SessionFacade;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class EVatwpisIncydentalniDAO   extends DAO implements Serializable{
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

    public EVatwpisIncydentalniDAO() {
        super(EVatwpisDedra.class);
        super.em = this.em;
    }
   
    public List<EVatwpisKJPK> findWierszePodatnikMc(Podatnik podatnik, String rok, String mc) {
        return sessionFacade.getEntityManager().createNamedQuery("EVatwpisKJPK.findByPodatnikRokMc").setParameter("podatnik",podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList();
    }
    
    public List<EVatwpisKJPK> findWierszePodatnikMcZakupSprzedaz(WpisView wpisView, boolean zakup0sprzedaz1) {
        if (zakup0sprzedaz1) {
            return sessionFacade.getEntityManager().createNamedQuery("EVatwpisKJPK.findByPodatnikRokMcSprzedaz").setParameter("podatnik",wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).setParameter("nazwa", "zakup").getResultList();
        } else {
            return sessionFacade.getEntityManager().createNamedQuery("EVatwpisKJPK.findByPodatnikRokMcZakup").setParameter("podatnik",wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).setParameter("nazwa", "zakup").getResultList();
        }
    }

    public List<EVatwpisKJPK> findPodatnikMc(Podatnik podatnik, String rok, String mcod, String mcdo) {
        List<EVatwpisKJPK> l = Collections.synchronizedList(new ArrayList<>());
        List<EVatwpisKJPK> input = getEntityManager().createNamedQuery("EVatwpisKJPK.findByPodatnikRokMcodMcdo").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("mcod", mcod).setParameter("mcdo", mcdo).setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE).getResultList();
        if (input != null && !input.isEmpty()) {
            for (EVatwpisKJPK p : input) {
                try {
                    //error.E.s("ew "+p);
                    if (!"VAT".equals(p.getDokfk().getRodzajedok().getSkrot())) {
                        l.add(p);
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            }
        }
        return l;
    }
}
