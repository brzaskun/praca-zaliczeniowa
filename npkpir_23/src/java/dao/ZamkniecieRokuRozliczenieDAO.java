/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.ZamkniecieRokuRozliczenie;
import error.E;
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
public class ZamkniecieRokuRozliczenieDAO extends DAO implements Serializable {
    @Inject
    private SessionFacade wpisFacade;
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

    public ZamkniecieRokuRozliczenieDAO() {
        super(ZamkniecieRokuRozliczenie.class);
        super.em = this.em;
    }
   

    
    public  List<ZamkniecieRokuRozliczenie> findAll(){
        try {
            return wpisFacade.findAll(ZamkniecieRokuRozliczenie.class);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }

    public List<ZamkniecieRokuRozliczenie> findByRokPodatnik(WpisView wpisView) {
        return wpisFacade.findZakmniecieRokuByRokPodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
    }
}
