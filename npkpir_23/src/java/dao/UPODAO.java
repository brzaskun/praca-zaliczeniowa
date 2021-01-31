/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Pismoadmin;
import entity.UPO;
import error.E;
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
public class UPODAO extends DAO implements Serializable{
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

    public UPODAO() {
        super(Pismoadmin.class);
        super.em = this.em;
    }

  
    
    public List<UPO> findPodatnikRok(WpisView wpisView) {
        try {
            List<UPO> lista = sessionFacade.findUPOPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<UPO> findUPORokMc(WpisView wpisView) {
        try {
            List<UPO> lista = sessionFacade.findUPORokMc(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<UPO> findUPORokMc(String rok, String mc) {
        try {
            List<UPO> lista = sessionFacade.findUPORokMc(rok, mc);
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<UPO> findUPOBez200() {
        try {
            List<UPO> lista = sessionFacade.findUPOBez200();
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

   
    
    
}
