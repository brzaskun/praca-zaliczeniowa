/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import data.Data;
import entity.Amodok;
import error.E;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class AmoDokDAO extends DAO implements Serializable {

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

    public AmoDokDAO() {
        super(Amodok.class);
        super.em = this.em;
    }

    
     public  Amodok findMR(String pod, Integer rok, String mc){
        try {
            return sessionFacade.findMR(pod,rok,mc);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public void destroy(String podatnik){
        List<Amodok> lista = sessionFacade.findAmodok(podatnik);
        Iterator it;
        it = lista.iterator();
        while(it.hasNext()){
            Amodok tmp = (Amodok) it.next();
            sessionFacade.remove(tmp);
        }
    }
    
     
    public void usun(String podatnik, int rok, int mc){
        try {
            sessionFacade.usunAmoDokByMcRok(podatnik,rok,mc);
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    //Usuwa wszystkie pozniejsze
    public void destroy(String podatnik, int rok, int mc){
        List<Amodok> lista = sessionFacade.findAmodok(podatnik);
        for(Amodok tmp : lista){
            int wynikporownywania = Data.compare(tmp.getAmodokPK().getRok(), tmp.getAmodokPK().getMc(), rok, mc);
            if(wynikporownywania >= 0) {
                sessionFacade.remove(tmp);
            }
        }
    }
    
    public List<Amodok> amodokklient(String klient){
        return sessionFacade.findPod(klient);
    }
    
    public List<Amodok> amodokKlientRok(String klient, String rok){
        return sessionFacade.AmoDokPodRok(klient, rok);
    }
    
    public Amodok amodokBiezacy(String klient, String mc, Integer rok){
        return sessionFacade.AmoDokPodMcRok(klient,mc, rok);
    }
}

