/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entityfk.WynikFKRokMc;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
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
public class WynikFKRokMcDAO extends DAO implements Serializable {

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

    public WynikFKRokMcDAO() {
        super(WynikFKRokMc.class);
        super.em = this.em;
    }
   

    public WynikFKRokMc findWynikFKRokMc(WynikFKRokMc wynikFKRokMc) {
        return (WynikFKRokMc)  getEntityManager().createNamedQuery("WynikFKRokMc.findPodatnikRokMc").setParameter("podatnik", wynikFKRokMc.getPodatnikObj()).setParameter("rok", wynikFKRokMc.getRok()).setParameter("mc", wynikFKRokMc.getMc()).getSingleResult();
    }
    
    public WynikFKRokMc findWynikFKRokMcFirma(WynikFKRokMc wynikFKRokMc) {
         WynikFKRokMc zwrot = null;
        try {
            zwrot = (WynikFKRokMc)  getEntityManager().createNamedQuery("WynikFKRokMc.findPodatnikRokMcFirma").setParameter("podatnik", wynikFKRokMc.getPodatnikObj()).setParameter("rok", wynikFKRokMc.getRok()).setParameter("mc", wynikFKRokMc.getMc()).getSingleResult();
        } catch (Exception e){}
        return zwrot;
    }
    public List<WynikFKRokMc> findWynikFKPodatnikRokFirma(WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("WynikFKRokMc.findPodatnikRokFirma").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList());
    }

    public List<WynikFKRokMc> findWynikFKPodatnikRok(WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("WynikFKRokMc.findPodatnikRok").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList());
    }
    
    public List<WynikFKRokMc> findWynikFKPodatnikRokUdzialowiec(WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("WynikFKRokMc.findPodatnikRokUdzialowiec").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList());
    }

    public WynikFKRokMc findWynikFKPodatnikRokUdzialowiec(WynikFKRokMc wynikFKRokMc) {
        WynikFKRokMc zwrot = null;
        try {
            zwrot = (WynikFKRokMc)  getEntityManager().createNamedQuery("WynikFKRokMc.findPodatnikRokMcUdzialowiec").setParameter("podatnik", wynikFKRokMc.getPodatnikObj()).setParameter("rok", wynikFKRokMc.getRok()).setParameter("mc", wynikFKRokMc.getMc()).setParameter("udzialowiec", wynikFKRokMc.getUdzialowiec()).getSingleResult();
        } catch (Exception e) {
        }
        return zwrot;
    }
    
    public WynikFKRokMc findWynikFKPodatnikRokUdzialowiec(Podatnik podatnik, String rok, String mc, String udzialowiec) {
        return (WynikFKRokMc)  getEntityManager().createNamedQuery("WynikFKRokMc.findPodatnikRokMcUdzialowiec").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("mc", mc).setParameter("udzialowiec", udzialowiec).getSingleResult();
    }
  
    public WynikFKRokMc findByPodatnikAndDateRange(Podatnik podatnik, Date startDate, Date endDate) {
    return getEntityManager().createQuery(
                "SELECT w FROM WynikFKRokMc w WHERE w.podatnikObj = :podatnik AND w.data >= :startDate AND w.data < :endDate", WynikFKRokMc.class)
            .setParameter("podatnik", podatnik)
            .setParameter("startDate", startDate)
            .setParameter("endDate", endDate)
            .getResultStream()
            .findFirst()
            .orElse(null);
}
    
}
