/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.DAO;
import entity.Podatnik;
import entityfk.WynikFKRokMc;
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
        return sessionFacade.findWynikFKRokMc(wynikFKRokMc);
    }
    
    public WynikFKRokMc findWynikFKRokMcFirma(WynikFKRokMc wynikFKRokMc) {
        return sessionFacade.findWynikFKRokMcFirma(wynikFKRokMc);
    }
    public List<WynikFKRokMc> findWynikFKPodatnikRokFirma(WpisView wpisView) {
        return sessionFacade.findWynikFKPodatnikRokFirma(wpisView);
    }

    public List<WynikFKRokMc> findWynikFKPodatnikRok(WpisView wpisView) {
        return sessionFacade.findWynikFKPodatnikRok(wpisView);
    }
    
    public List<WynikFKRokMc> findWynikFKPodatnikRokUdzialowiec(WpisView wpisView) {
        return sessionFacade.findWynikFKPodatnikRokUdzialowiec(wpisView);
    }

    public WynikFKRokMc findWynikFKPodatnikRokUdzialowiec(WynikFKRokMc wynikFKRokMc) {
        return sessionFacade.findWynikFKRokMcUdzialowiec(wynikFKRokMc);
    }
    
    public WynikFKRokMc findWynikFKPodatnikRokUdzialowiec(Podatnik podatnik, String rok, String mc, String udzialowiec) {
        return sessionFacade.findWynikFKRokMcUdzialowiec(podatnik, rok, mc, udzialowiec);
    }
    

    public List<WynikFKRokMc> findAll() {
        return sessionFacade.findAll(WynikFKRokMc.class);
    }
    
}
