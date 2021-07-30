/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entityfk.Wiersz;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class WierszeDAO extends DAO implements Serializable{
    
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

    public WierszeDAO() {
        super(Wiersz.class);
        super.em = this.em;
    }

    
    public List<Wiersz> findWierszeZapisy(String podatnik, String konto) {
         try {
           return getEntityManager().createNamedQuery("Wiersz.findByZapisy").setParameter("podatnik", podatnik).setParameter("konto", konto).getResultList();
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Wiersz> findWierszePodatnikMcRok(Podatnik podatnik, WpisView wpisView) {
         try {
           return getEntityManager().createNamedQuery("Wiersz.findByPodatnikMcRok").setParameter("podatnik", podatnik).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList();
       } catch (Exception e ){
           return null;
       }
    }
    
    
    public List<Wiersz> findWierszePodatnikMcRokWNTWDT(Podatnik podatnik, WpisView wpisView, String wntwdt) {
         try {
           return getEntityManager().createNamedQuery("Wiersz.findByPodatnikMcRokWNTWDT").setParameter("podatnik", podatnik).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).setParameter("wntwdt", wntwdt).getResultList();
       } catch (Exception e ){
           return null;
       }
    }
    

    public List<Wiersz> findWierszePodatnikRok(Podatnik podatnik, WpisView wpisView) {
         try {
           return getEntityManager().createNamedQuery("Wiersz.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", wpisView.getRokWpisuSt()).getResultList();
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Wiersz> findWierszePodatnikRokMCDo(Podatnik podatnik, WpisView wpisView) {
         try {
           return getEntityManager().createNamedQuery("Wiersz.findByPodatnikRokMcDo").setParameter("podatnik", podatnik).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mcdo", wpisView.getMiesiacUprzedni()).getResultList();
       } catch (Exception e ){
           return null;
       }
    }
}
