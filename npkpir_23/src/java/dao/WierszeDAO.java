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
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.SessionFacade;
import view.WpisView;
/**
 *
 * @author Osito
 */
public class WierszeDAO extends DAO implements Serializable{
    
    @Inject private SessionFacade wierszeFacade;
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
           return wierszeFacade.findWierszeZapisy(podatnik, konto);
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Wiersz> findWierszePodatnikMcRok(Podatnik podatnik, WpisView wpisView) {
         try {
           return wierszeFacade.findWierszePodatnikMcRok(podatnik, wpisView);
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Wiersz> findWierszePodatnikMcRokWNTWDT(Podatnik podatnik, WpisView wpisView, String wntwdt) {
         try {
           return wierszeFacade.findWierszePodatnikMcRokWNTWDT(podatnik, wpisView, wntwdt);
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Wiersz> findWierszePodatnikRok(Podatnik podatnik, WpisView wpisView) {
         try {
           return wierszeFacade.findWierszePodatnikRok(podatnik, wpisView);
       } catch (Exception e ){
           return null;
       }
    }
}
