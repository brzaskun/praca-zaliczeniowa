/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import comparator.Evewidencjacomparator;
import entity.Evewidencja;
import entity.Evpozycja;
import error.E;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
public class EvewidencjaDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade evewidencjaFacade;
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

    public EvewidencjaDAO() {
        super(Evewidencja.class);
        super.em = this.em;
    }

   

     public  List<Evewidencja> findAll(){
        try {
            return evewidencjaFacade.findAll(Evewidencja.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    public  Map<String, Evewidencja> findAllMap(){
        try {
            List<Evewidencja> pobraneewidencje = findAll();
            Collections.sort(pobraneewidencje, new Evewidencjacomparator());
            Map<String,Evewidencja> mapaewidencji = new ConcurrentHashMap<>();
            for (Evewidencja p : pobraneewidencje) {
                mapaewidencji.put(p.getNazwa(), p);
            }
            return mapaewidencji;
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public  Map<String, Evewidencja> findAllMapByPole(){
        try {
            List<Evewidencja> pobraneewidencje = findAll();
            Collections.sort(pobraneewidencje, new Evewidencjacomparator());
            Map<String,Evewidencja> mapaewidencji = new ConcurrentHashMap<>();
            for (Evewidencja p : pobraneewidencje) {
                mapaewidencji.put(p.getPole(), p);
            }
            return mapaewidencji;
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public Evewidencja znajdzponazwie(String nazwa)  {
        Evewidencja tmp = new Evewidencja();
        try {
            tmp = evewidencjaFacade.findEvewidencjaByName(nazwa);
            return tmp;
        } catch (Exception e) { E.e(e); 
             return null;
        }
    }
    
    public List<Evewidencja> znajdzpotransakcji(String nazwa) {
        try {
            return evewidencjaFacade.findEvewidencjaByTransakcja(nazwa);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

    public Evewidencja znajdzponazwiePola(Evpozycja macierzysty) {
        try {
            return evewidencjaFacade.findEvewidencjaByPole(macierzysty);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }

    
    
}
